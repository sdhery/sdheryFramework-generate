package com.sdhery.generate.util;

import com.sdhery.generate.bean.CodeVo;
import com.sdhery.generate.bean.Column;
import org.apache.commons.lang.StringUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: sdhery
 * Date: 13-6-19
 * Time: 下午4:09
 * To change this template use File | Settings | File Templates.
 */
public class DBUtil {
    public static String DRIVERNAME = "com.mysql.jdbc.Driver";

    public static void close(Connection connection, Statement stmt, ResultSet rs) {
        if (connection != null) {
            try {
                connection.close();
                if (stmt != null) {
                    stmt.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static String formatField(String field) {
        String[] strs = field.split("_");
        field = "";
        int m = 0;
        for (int length = strs.length; m < length; m++) {
            if (m > 0) {
                String tempStr = strs[m].toLowerCase();
                tempStr = tempStr.substring(0, 1).toUpperCase() +
                        tempStr.substring(1, tempStr.length());
                field = field + tempStr;
            } else {
                field = field + strs[m].toLowerCase();
            }
        }
        return field;
    }

    public static String formatDataType(String dataType, String precision, String scale) {
        if (dataType.contains("char"))
            dataType = "String";
        else if (dataType.contains("int"))
            dataType = "Integer";
        else if (dataType.contains("float"))
            dataType = "Float";
        else if (dataType.contains("double"))
            dataType = "Double";
        else if (dataType.contains("number")) {
            if ((StringUtils.isNotBlank(scale)) && (Integer.parseInt(scale) > 0))
                dataType = "BigDecimal";
            else if ((StringUtils.isNotBlank(precision)) && (Integer.parseInt(precision) > 6))
                dataType = "Long";
            else
                dataType = "Integer";
        } else if (dataType.contains("decimal"))
            dataType = "BigDecimal";
        else if (dataType.contains("date"))
            dataType = "Date";
        else if (dataType.contains("time"))
            dataType = "Timestamp";
        else if (dataType.contains("clob"))
            dataType = "Clob";
        else {
            dataType = "java.lang.Object";
        }
        return dataType;
    }
}
