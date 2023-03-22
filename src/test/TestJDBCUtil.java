package test;

import database.JDBCUtil;

import java.sql.Connection;
import java.sql.DatabaseMetaData;

public class TestJDBCUtil {
    public static void main(String[] args) {
        Connection connection = JDBCUtil.getConnection();

        JDBCUtil.closeConnection(connection);

    }
}
