package ${codeVo.packageValue}.dao;

import com.sdhery.module.core.dao.EntityDao;
import ${codeVo.packageValue}.domain.${codeVo.domain};

/**
* @Title:数据库操作类接口
* @Description: ${codeVo.description}
* @author ${codeVo.author}
* @Date:${nowDate?string("yyyy-MM-dd HH:mm:ss")}
*/
public interface I${codeVo.domain}Dao extends EntityDao<${codeVo.domain},Integer> {
}
