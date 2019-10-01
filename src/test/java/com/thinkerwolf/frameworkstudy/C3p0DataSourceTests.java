package com.thinkerwolf.frameworkstudy;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;

import java.sql.ResultSet;
import java.sql.SQLException;

public class C3p0DataSourceTests {


    public boolean login() {
        String username = "2";
        String password = "1213";
        String sql = "select * from user where username = ? and password = ?";
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        QueryRunner qr = new QueryRunner(dataSource);

        try {
            return qr.query(sql, new ResultSetHandler<Boolean>() {
                @Override
                public Boolean handle(ResultSet resultSet) throws SQLException {
                    // 处理逻辑....
                    return resultSet.next();
                }
            }, username, password);

        } catch (Exception e) {

        }
        return false;
    }


}
