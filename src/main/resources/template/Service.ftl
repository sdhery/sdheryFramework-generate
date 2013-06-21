package ${codeVo.packageValue}.service.impl;


import com.sdhery.module.core.dao.EntityDao;
import com.sdhery.module.core.service.impl.BaseService;
import ${codeVo.packageValue}.dao.I${codeVo.domain}Dao;
import ${codeVo.packageValue}.domain.${codeVo.domain};
import ${codeVo.packageValue}.service.I${codeVo.domain}Service;

/**
* @Title:实现业务操作类
* @Description: ${codeVo.description}
* @author ${codeVo.author}
* @Date:${nowDate?string("yyyy-MM-dd HH:mm:ss")}
*/
public class ${codeVo.domain}Service extends BaseService<${codeVo.domain}, Integer> implements I${codeVo.domain}Service {
    I${codeVo.domain}Dao ${codeVo.lowerClassName}Dao;

    public void set${codeVo.domain}Dao(I${codeVo.domain}Dao ${codeVo.lowerClassName}Dao) {
        this.${codeVo.lowerClassName}Dao = ${codeVo.lowerClassName}Dao;
    }

    protected EntityDao<${codeVo.domain}, Integer> getEntityDao() {
        return ${codeVo.lowerClassName}Dao;
    }
}