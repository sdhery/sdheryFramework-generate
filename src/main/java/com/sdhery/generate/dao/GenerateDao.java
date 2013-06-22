package com.sdhery.generate.dao;

import com.sdhery.generate.bean.CodeVo;
import com.sdhery.generate.bean.Column;
import com.sdhery.generate.util.DBUtil;
import org.apache.commons.lang.StringUtils;

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
            String sql = "select COUNT(1) num from information_schema.columns where table_name = ? and TABLE_SCHEMA = ? ";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, codeVo.getTableName());
            preparedStatement.setString(2, codeVo.getDataBaseName());
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt("num");
                if (count > 0) {
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
            String sql = "select column_name,data_type,COLUMN_KEY,column_comment,character_maximum_length from information_schema.columns where table_name = ? and TABLE_SCHEMA = ? ";
            preparedStatement = connection.prepareStatement(sql.toString());
            preparedStatement.setString(1, codeVo.getTableName());
            preparedStatement.setString(2, codeVo.getDataBaseName());
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Column column = new Column();
                String fieldName = DBUtil.formatField(resultSet.getString("column_name").toLowerCase());
                column.setFieldName(fieldName);
                String desc = resultSet.getString("column_comment");
                if (StringUtils.isNotBlank(desc)) {
                    column.setDesc(desc);
                }
                String pk = resultSet.getString("COLUMN_KEY");
                if (StringUtils.isNotBlank(pk) && pk.equals("PRI")) {
                    column.setPri(true);
                }
                column.setFieldDbName(resultSet.getString("column_name"));
                column.setSetName(fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1, fieldName.length()));
                column.setFieldType(DBUtil.formatDataType(resultSet.getString("data_type").toLowerCase(), column.getPrecision(), column.getScale()));
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
