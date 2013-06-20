package ${codeVo.packageValue}.domain;

import com.sdhery.module.core.domain.BaseEntity;

/**
* @Title:实体类
* @Description: ${codeVo.description}
* @author ${codeVo.author}
* @Date:${nowDate?string("yyyy-MM-dd HH:mm:ss")}
*/

public class ${codeVo.domain} extends BaseEntity {

<#list columnList as column>
    ${column.fieldType} ${column.fieldName};
</#list>

<#list columnList as column>
    public ${column.fieldType} get${column.setName}() {
        return ${column.fieldName};
    }

    public void set${column.setName}(${column.fieldType} ${column.fieldName}) {
        this.${column.fieldName} = ${column.fieldName};
    }
</#list>
}