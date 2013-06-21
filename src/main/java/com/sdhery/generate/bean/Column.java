package com.sdhery.generate.bean;

/**
 * Created with IntelliJ IDEA.
 * User: sdhery
 * Date: 13-6-19
 * Time: 下午4:35
 * To change this template use File | Settings | File Templates.
 */
public class Column {
    private String fieldDbName;
    private String fieldName;
    private String fieldType;//字段java类型
    private String precision;
    private String scale;
    private String setName;
    private boolean pri = false;//是否主键盘,默认为false不是主键


    public String getFieldDbName() {
        return fieldDbName;
    }

    public void setFieldDbName(String fieldDbName) {
        this.fieldDbName = fieldDbName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public String getPrecision() {
        return precision;
    }

    public void setPrecision(String precision) {
        this.precision = precision;
    }

    public String getScale() {
        return scale;
    }

    public void setScale(String scale) {
        this.scale = scale;
    }

    public String getSetName() {
        return setName;
    }

    public void setSetName(String setName) {
        this.setName = setName;
    }

    public boolean isPri() {
        return pri;
    }

    public void setPri(boolean pri) {
        this.pri = pri;
    }
}
