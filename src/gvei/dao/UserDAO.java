package gvei.dao;

import gvei.User;
import gvei.DBHelper;
import java.sql.*;

public class UserDAO {

    // Login verification
    public User getUserByEmailAndPassword(String email, String password) throws Exception {
        Connection c = DBHelper.getConnection();
        String sql = "SELECT * FROM users WHERE email=? AND password=?";
        PreparedStatement ps = c.prepareStatement(sql);
        ps.setString(1, email);
        ps.setString(2, password);
        ResultSet rs = ps.executeQuery();

        User user = null;
        if(rs.next()) {
            user = new User(
                    rs.getInt("user_id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("password"),
                    rs.getString("role")
            );
        }

        rs.close();
        ps.close();
        c.close();
        return user;
    }

    // Register new user
    public boolean register(String name, String email, String password, String role) throws Exception {
        Connection c = DBHelper.getConnection();
        String sql = "INSERT INTO users(name,email,password,role) VALUES(?,?,?,?)";
        PreparedStatement ps = c.prepareStatement(sql);
        ps.setString(1, name);
        ps.setString(2, email);
        ps.setString(3, password);
        ps.setString(4, role);

        int rows = ps.executeUpdate();
        ps.close();
        c.close();
        return rows > 0;
    }
}
