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
    public static String DIVER_NAME = "com.mysql.jdbc.Driver";
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    public boolean checkTableExist(CodeVo codeVo) {
        boolean flag = false;

        try {
            readTable(codeVo);
            if (resultSet != null) {
                resultSet.last();
                int fieldNum = resultSet.getRow();
                int n = fieldNum;
                if (n > 0) {
                    flag = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(connection, preparedStatement, resultSet);
        }
        return flag;
    }

    public List<Column> readTableColumn(CodeVo codeVo) {
        List<Column> result = new ArrayList<Column>();
        try {
            readTable(codeVo);
            while (resultSet.next()) {
                Column column = new Column();
                column.setFieldName(formatField(resultSet.getString(1).toLowerCase()));

                column.setFieldDbName(resultSet.getString(1).toUpperCase());
                column.setFieldType(formatField(resultSet.getString(2).toLowerCase()));

                column.setPrecision(resultSet.getString(4));
                column.setScale(resultSet.getString(5));
                result.add(column);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(connection, preparedStatement, resultSet);
        }
        return result;
    }

    void readTable(CodeVo codeVo) throws ClassNotFoundException, SQLException {
        Class.forName(DIVER_NAME);
        connection = DriverManager.getConnection(codeVo.getJdbcUrl(), codeVo.getDataBaseUserName(), codeVo.getDataBasePW());

        StringBuilder sql = new StringBuilder();
        sql.append("select column_name,data_type,column_comment,0,0,character_maximum_length from information_schema.columns where table_name = '");
        sql.append(codeVo.getTableName());
        sql.append("'");
        preparedStatement = connection.prepareStatement(sql.toString());
        resultSet = preparedStatement.executeQuery();
    }

    void close(Connection connection, Statement stmt, ResultSet rs) {
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

    private String formatDataType(String dataType, String precision, String scale) {
        if (dataType.contains("char"))
            dataType = "java.lang.String";
        else if (dataType.contains("int"))
            dataType = "java.lang.Integer";
        else if (dataType.contains("float"))
            dataType = "java.lang.Float";
        else if (dataType.contains("double"))
            dataType = "java.lang.Double";
        else if (dataType.contains("number")) {
            if ((StringUtils.isNotBlank(scale)) && (Integer.parseInt(scale) > 0))
                dataType = "java.math.BigDecimal";
            else if ((StringUtils.isNotBlank(precision)) && (Integer.parseInt(precision) > 6))
                dataType = "java.lang.Long";
            else
                dataType = "java.lang.Integer";
        } else if (dataType.contains("decimal"))
            dataType = "BigDecimal";
        else if (dataType.contains("date"))
            dataType = "java.util.Date";
        else if (dataType.contains("time"))
            dataType = "java.sql.Timestamp";
        else if (dataType.contains("clob"))
            dataType = "java.sql.Clob";
        else {
            dataType = "java.lang.Object";
        }
        return dataType;
    }
}
