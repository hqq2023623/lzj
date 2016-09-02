package zj.test.util;

import java.sql.*;

/**
 * 测试用dbutil
 *
 * @author lzj 2014/8/3
 */
public class MyDbUtil {

    private static final String MYSQL_CONN = "jdbc:mysql://localhost:3306/test?sessionVariables=FOREIGN_KEY_CHECKS=0";

    private static final String USER_NAME = "root";

    private static final String PASSWORD = "m123";

    /**
     * 获取mysql连接
     */
    public static Connection getConnection() throws SQLException {
        Connection con = null;
        con = DriverManager.getConnection(MYSQL_CONN, USER_NAME, PASSWORD);
        return con;
    }

    /**
     * 关闭连接
     */
    public static void close(Connection con) {
        try {
            if (con != null)
                con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭statement
     */
    public static void close(PreparedStatement ps) {
        try {
            if (ps != null)
                ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭resultSet
     */
    public static void close(ResultSet rs) {
        try {
            if (rs != null)
                rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
