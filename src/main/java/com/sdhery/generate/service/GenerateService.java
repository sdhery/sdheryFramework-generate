package com.sdhery.generate.service;

import com.sdhery.generate.bean.CodeVo;
import com.sdhery.generate.bean.Column;
import com.sdhery.generate.dao.GenerateDao;
import freemarker.template.Configuration;
import freemarker.template.Template;
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
            if(generateDao.checkTableExist(codeVo)){
                generateTemplateCode(generateDao,codeVo);
                flag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    void generateTemplateCode(GenerateDao generateDao,CodeVo codeVo){
        List<Column> columntList = generateDao.readTableColumn(codeVo);
        Map data = new HashMap();
        data.put("codeVo", codeVo);
        data.put("nowDate", new Date());
        data.put("columnList", columntList);
        try {
            Configuration cfg = getConfiguration();
            //实体类生成开始
            Template temp = cfg.getTemplate("domain.ftl");
            File makedir = new File(codeVo.getGetPath() + "/"+codeVo.getPackageValue().replace(".", "/")+ "/domain");
            FileUtils.forceMkdir(makedir);//目录不存在，利用FileUtils工具类自动创建
            Writer out = new OutputStreamWriter(new FileOutputStream(codeVo.getGetPath() + "/"+codeVo.getPackageValue().replace(".", "/")  + "/domain/"+ codeVo.getDomain()+".java"));
            temp.process(data, out);
            out.flush();
            //实体类生成结束
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Configuration getConfiguration()throws IOException {
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
        String path = getClassPath() + "/template";;
        return path;
    }
}
