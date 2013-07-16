<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${codeVo.packageValue}.dao.impl.${codeVo.domain}Dao">

    <resultMap id="BaseResultMap" type="${codeVo.packageValue}.domain.${codeVo.domain}">
    <#list columnList as column>
        <id column="${column.fieldDbName}" property="${column.fieldName}"/>
    </#list>
    </resultMap>

    <sql id="Base_Column_List"><#list columnList as column>${column.fieldDbName}<#if column_has_next>,</#if></#list></sql>

    <insert id="insert" parameterType="${codeVo.packageValue}.domain.${codeVo.domain}">
        insert into ${codeVo.tableName}(<#list columnList as column>${column.fieldDbName}<#if column_has_next>,</#if></#list>) values (<#list columnList as column>${'#\{'}${column.fieldName}${'}'}<#if column_has_next>,</#if></#list>)
    </insert>

    <select id="getById" resultMap="BaseResultMap" parameterType="java.lang.Integer">
        SELECT <include refid="Base_Column_List" /> FROM ${codeVo.tableName} where <#list columnList as column><#if column.pri=true>${column.fieldDbName}</#if></#list> = ${'#\{'}${'id'}${'}'}
    </select>

    <update id="update" parameterType="${codeVo.packageValue}.domain.${codeVo.domain}">
        UPDATE ${codeVo.tableName} set <#list columnList as column><#if column.pri==false>${column.fieldDbName}=${'#\{'}${column.fieldName}${'}'}<#if column_has_next>,</#if></#if></#list> where <#list columnList as column><#if column.pri=true>${column.fieldDbName}=${'#\{'}${column.fieldName}${'}'}</#if></#list>
    </update>

    <delete id="deleteById" parameterType="java.lang.Integer">DELETE FROM ${codeVo.tableName} WHERE <#list columnList as column><#if column.pri=true>${column.fieldDbName}</#if></#list> = ${'#\{'}${'id'}${'}'}</delete>

    <select id="count" resultType="java.lang.Integer" parameterType="com.sdhery.module.core.commons.Condition">
        select <if test="distinct">distinct</if> count(1) from ${codeVo.tableName} <if test="_parameter != null"><include refid="simpleConditionWhere"/></if>
    </select>

    <select id="search" resultMap="BaseResultMap" parameterType="com.sdhery.module.core.commons.Condition">
        SELECT
        <if test="distinct">distinct</if>
        <include refid="Base_Column_List"/>
        FROM ${codeVo.tableName}
        <if test="_parameter != null">
            <include refid="simpleConditionWhere"/>
        </if>
        <if test="orderByClause != null">order by ${'$\{'}orderByClause${'}'}</if>
    </select>

    <sql id="simpleConditionWhere">
        <where>
            <if test="valid">
                <foreach collection="conditionItems" item="conditionItem" separator="or">
                    <trim prefix="(" prefixOverrides="and" suffix=")">
                        <choose>
                            <when test="conditionItem.noValue">
                                and ${'$\{'}conditionItem.condition${'}'}
                            </when>
                            <when test="conditionItem.singleValue">
                                and ${'$\{'}conditionItem.condition${'}'} ${'#\{'}conditionItem.value${'}'}
                            </when>
                            <when test="conditionItem.betweenValue">
                                and ${'$\{'}conditionItem.condition${'}'} ${'#\{'}conditionItem.value${'}'} and ${'#\{'}conditionItem.secondValue${'}'}
                            </when>
                            <when test="conditionItem.listValue">
                                and ${'$\{'}conditionItem.condition${'}'}
                                <foreach close=")" collection="conditionItem.value" item="listItem" open="("
                                         separator=",">
                                ${'#\{'}listItem${'}'}
                                </foreach>
                            </when>
                        </choose>
                    </trim>
                </foreach>
            </if>
        </where>
    </sql>
</mapper>