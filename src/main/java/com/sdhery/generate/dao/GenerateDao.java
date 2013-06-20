package com.sdhery.generate.dao;

import com.sdhery.generate.bean.CodeVo;
import com.sdhery.generate.bean.Column;
import com.sdhery.generate.util.DBUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: sdhery
 * Date: 13-6-20
 * Time: 上午10:09
 * To change this template use File | Settings | File Templates.
 */
public class GenerateDao {

    public boolean checkTableExist(CodeVo codeVo) {
        boolean flag = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            Class.forName(DBUtil.DRIVERNAME);
            connection = DriverManager.getConnection(codeVo.getJdbcUrl(), codeVo.getDataBaseUserName(), codeVo.getDataBasePW());
            StringBuilder sql = new StringBuilder();
            sql.append("select column_name,data_type,column_comment,0,0,character_maximum_length from information_schema.columns where table_name = '");
            sql.append(codeVo.getTableName());
            sql.append("'");
            preparedStatement = connection.prepareStatement(sql.toString());
            resultSet = preparedStatement.executeQuery();

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
            DBUtil.close(connection, preparedStatement, resultSet);
        }
        return flag;
    }

    public List<Column> readTableColumn(CodeVo codeVo) {
        List<Column> result = new ArrayList<Column>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            Class.forName(DBUtil.DRIVERNAME);
            connection = DriverManager.getConnection(codeVo.getJdbcUrl(), codeVo.getDataBaseUserName(), codeVo.getDataBasePW());
            StringBuilder sql = new StringBuilder();
            sql.append("select column_name,data_type,column_comment,0,0,character_maximum_length from information_schema.columns where table_name = '");
            sql.append(codeVo.getTableName());
            sql.append("'");
            preparedStatement = connection.prepareStatement(sql.toString());
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Column column = new Column();
                String fieldName = DBUtil.formatField(resultSet.getString(1).toLowerCase());
                column.setFieldName(DBUtil.formatField(resultSet.getString(1).toLowerCase()));

                column.setFieldDbName(resultSet.getString(1).toUpperCase());
                column.setPrecision(resultSet.getString(4));
                column.setScale(resultSet.getString(5));
                column.setSetName(fieldName.substring(0,1).toUpperCase()+fieldName.substring(1,fieldName.length()));
                column.setFieldType(DBUtil.formatDataType(resultSet.getString(2).toLowerCase(), column.getPrecision(), column.getScale()));
                result.add(column);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(connection, preparedStatement, resultSet);
        }
        return result;
    }
}
