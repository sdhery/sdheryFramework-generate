package com.sdhery.generate.service;

import com.sdhery.generate.bean.CodeVo;
import com.sdhery.generate.bean.Column;
import com.sdhery.generate.dao.GenerateDao;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: sdhery
 * Date: 13-6-20
 * Time: 上午10:46
 * To change this template use File | Settings | File Templates.
 */
public class GenerateService {
    static String DOMAINPATH = "/domain";
    static String IDAOPATH = "/dao";
    static String DAOPATH = "/dao/impl";
    static String ISERVICEPATH = "/service";
    static String SERVICEPATH = "/service/impl";
    static String CONFIGPATH = "/config/spring";
    static String CONFIGMAPPERPATH = "/config/mapper";
    static String DOMAINTEMPLATEFILENAME = "domain.ftl";
    static String IDAOTEFILENAME = "IDao.ftl";
    static String DAOTEMPLATEFILENAME = "Dao.ftl";
    static String ISERVICETEFILENAME = "IService.ftl";
    static String SERVICETEFILENAME = "Service.ftl";
    static String LOG4JTEFILENAME = "log4j.ftl";
    static String DATASOURCETEFILENAME = "datasource.ftl";
    static String MYBATISTEFILENAME = "mybatis.ftl";
    static String RESOURCETEFILENAME = "resource.ftl";
    static String SERVICECONFIGTEFILENAME = "serviceConfig.ftl";
    static String MAPPERTEFILENAME = "mapper.ftl";

    /**
     * 当为假时表示要生成的表不存在，真是运行正常无错误
     *
     * @param codeVo
     * @return
     */
    public boolean generateCode(CodeVo codeVo) {
        boolean flag = false;
        try {
            GenerateDao generateDao = new GenerateDao();
            if (generateDao.checkTableExist(codeVo)) {
                generateTemplateCode(generateDao, codeVo);
                flag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    void generateTemplateCode(GenerateDao generateDao, CodeVo codeVo) {
        List<Column> columntList = generateDao.readTableColumn(codeVo);
        Map data = new HashMap();
        data.put("codeVo", codeVo);
        data.put("nowDate", new Date());
        data.put("columnList", columntList);
        try {
            //实体类开始
            String basePath = codeVo.getGetPath() + "/" + codeVo.getPackageValue().replace(".", "/");
            String configPath = codeVo.getConfigPath() + "/";
            generateFile(DOMAINTEMPLATEFILENAME, basePath + DOMAINPATH, codeVo.getDomain(), ".java", data);
            //实体类结束
            //数据库开始
            generateFile(IDAOTEFILENAME, basePath + IDAOPATH, "I" + codeVo.getDomain() + "Dao", ".java", data);
            generateFile(DAOTEMPLATEFILENAME, basePath + DAOPATH, codeVo.getDomain() + "Dao", ".java", data);
            //数据库结束
            //业务类开始
            generateFile(ISERVICETEFILENAME, basePath + ISERVICEPATH, "I" + codeVo.getDomain() + "Service", ".java", data);
            generateFile(SERVICETEFILENAME, basePath + SERVICEPATH, codeVo.getDomain() + "Service", ".java", data);
            //业务类结束
            //配置文件开始
            //log4j
            generateFile(LOG4JTEFILENAME, configPath + "/config", "log4j", ".properties", data);
            //datasource配置
            generateFile(DATASOURCETEFILENAME, configPath + CONFIGPATH, "applicationContext-datasource", ".xml", data);
            //mybatis配置
            generateFile(MYBATISTEFILENAME, configPath + CONFIGPATH, "applicationContext-myBatis", ".xml", data);
            //resource配置
            generateFile(RESOURCETEFILENAME, configPath + CONFIGPATH, "applicationContext-resource", ".xml", data);
            //service配置
            generateFile(SERVICECONFIGTEFILENAME, configPath + CONFIGPATH, "applicationContext-service", ".xml", data);
            //mapper配置
            generateFile(MAPPERTEFILENAME, configPath + CONFIGMAPPERPATH, codeVo.getDomain() + "Dao", ".xml", data);
            //配置文件结束
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void generateFile(String templateFileName, String classPath, String className, String extension, Map data) throws IOException, TemplateException {
        Configuration cfg = getConfiguration();
        //实体类生成开始
        Template temp = cfg.getTemplate(templateFileName);
        File makeDir = new File(classPath);
        FileUtils.forceMkdir(makeDir);//目录不存在，利用FileUtils工具类自动创建
        Writer out = new OutputStreamWriter(new FileOutputStream(classPath + "/" + className + extension));
        temp.process(data, out);
        out.flush();
    }

    public Configuration getConfiguration() throws IOException {
        Configuration cfg = new Configuration();
        String path = getTemplatePath();
        File templateDirFile = new File(path);
        cfg.setDirectoryForTemplateLoading(templateDirFile);
        cfg.setLocale(Locale.CHINA);
        cfg.setDefaultEncoding("UTF-8");
        return cfg;
    }

    String getClassPath() {
        String path = Thread.currentThread().getContextClassLoader().getResource("./").getPath();
        return path;
    }

    String getTemplatePath() {
        String path = getClassPath() + "/template";
        ;
        return path;
    }
}
