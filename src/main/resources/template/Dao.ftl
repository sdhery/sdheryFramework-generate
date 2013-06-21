package ${codeVo.packageValue}.dao.impl;

import com.sdhery.module.core.dao.impl.BaseMybatisDao;
import ${codeVo.packageValue}.dao.I${codeVo.domain}Dao;
import ${codeVo.packageValue}.domain.${codeVo.domain};

/**
* @Title:数据库操作实现类
* @Description: ${codeVo.description}
* @author ${codeVo.author}
* @Date:${nowDate?string("yyyy-MM-dd HH:mm:ss")}
*/
public class ${codeVo.domain}Dao extends BaseMybatisDao<${codeVo.domain}, Integer> implements I${codeVo.domain}Dao{
}
