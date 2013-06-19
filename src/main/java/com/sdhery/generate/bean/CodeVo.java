package com.sdhery.generate.bean;

/**
 * Created with IntelliJ IDEA.
 * User: sdhery
 * Date: 13-3-8
 * Time: 下午5:09
 * To change this template use File | Settings | File Templates.
 */
public class CodeVo {
    String jdbcUrl;//数据库连接
    String dataBaseUserName;//数据库用户名
    String dataBasePW;//数据库密码
    String tableName;//表
    String packageValue;//包名
    String domain;//包路径
    String author;//作者
    String description;//描述
    String getPath;//生成路径

    public String getJdbcUrl() {
        return jdbcUrl;
    }

    public void setJdbcUrl(String jdbcUrl) {
        this.jdbcUrl = jdbcUrl;
    }

    public String getDataBaseUserName() {
        return dataBaseUserName;
    }

    public void setDataBaseUserName(String dataBaseUserName) {
        this.dataBaseUserName = dataBaseUserName;
    }

    public String getDataBasePW() {
        return dataBasePW;
    }

    public void setDataBasePW(String dataBasePW) {
        this.dataBasePW = dataBasePW;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getPackageValue() {
        return packageValue;
    }

    public void setPackageValue(String packageValue) {
        this.packageValue = packageValue;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGetPath() {
        return getPath;
    }

    public void setGetPath(String getPath) {
        this.getPath = getPath;
    }
}
