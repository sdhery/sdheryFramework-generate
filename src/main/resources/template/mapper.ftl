<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${codeVo.packageValue}.dao.impl.SysUserDao">

    <resultMap id="BaseResultMap" type="${codeVo.packageValue}.domain.${codeVo.domain}">
    <#list columnList as column>
        <id column="${column.fieldDbName}" property="${column.fieldName}"/>
    </#list>
    </resultMap>

    <sql id="Base_Column_List"><#list columnList as column>${column.fieldDbName}<#if column_has_next>,</#if></#list></sql>

    <insert id="insert" parameterType="${codeVo.packageValue}.domain.${codeVo.domain}">
        insert into ${codeVo.tableName}(<#list columnList as column>${column.fieldDbName}<#if column_has_next>,</#if></#list>) values (<#list columnList as column>${'#\{'}${column.fieldName}${'}'}<#if column_has_next>,</#if></#list>)
    </insert>

    <select id="getById" resultMap="BaseResultMap" resultType="java.lang.Integer">
        SELECT <include refid="Base_Column_List" /> FROM ${codeVo.tableName} where <#list columnList as column><#if column.pri=true>${column.fieldDbName}</#if></#list> = ${'#\{'}${'id'}${'}'}
    </select>

    <update id="update" parameterType="${codeVo.packageValue}.domain.${codeVo.domain}">
        UPDATE ${codeVo.tableName} set <#list columnList as column><#if column.pri==false>${column.fieldDbName}=${'#\{'}${column.fieldName}${'}'}<#if column_has_next>,</#if></#if></#list> where <#list columnList as column><#if column.pri=true>${column.fieldDbName}=${'#\{'}${column.fieldName}${'}'}</#if></#list>
    </update>

    <delete id="deleteById" parameterType="java.lang.Integer">DELETE FROM ${codeVo.tableName} WHERE <#list columnList as column><#if column.pri=true>${column.fieldDbName}</#if></#list> = ${'#\{'}${'id'}${'}'}</delete>
</mapper>