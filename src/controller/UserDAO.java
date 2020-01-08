/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.sun.security.ntlm.Server;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.User;

/**
 *
 * @author Admin
 */
public class UserDAO {
    Connection con = DBConnection.getConnection();
    //lấy toàn bộ user trong database
    public List<User> getUserList() {
       
        PreparedStatement stm = null;
        ResultSet rs = null;
        
        try {
            String sql = "SELECT * FROM users";
            stm = con.prepareStatement(sql);
            rs = stm.executeQuery();
            
            List<User> uslist = new ArrayList<User>();
            
            while (rs.next()) {
                User us = new User();
                us.setId(rs.getInt("Id"));
                us.setUsername(rs.getString("username"));
                us.setPassword(rs.getString("password"));
                us.setWin(rs.getInt("win"));
                us.setLose(rs.getInt("lose"));
                us.setScore(rs.getInt("score"));
                uslist.add(us);
            }
            return uslist;
        } catch (SQLException ex) {
                  
        }    
        return null;
    }
    //kiểm tra xem username đã được login hay chưa?
    public User checkLogin(String username, String password) {
        try {
                    
            String sql = "SELECT * FROM users WHERE username = ? and password = ?";
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setString(1, username);
            stm.setString(2, password);
            ResultSet rs = stm.executeQuery();
            boolean result = rs.next();
            rs.close();
            stm.close();
            if (result) {
                return getUser(username);        
            }
        } catch (Exception e) {
        }
        return null;
    }
    // thêm 1 user vào database
    public boolean register(String username, String password) {
         
                   
            Statement statement;           
            String sql = "INSERT INTO users(username,password)VALUES('"+ username+"','"+password+"')" ;
                        
            if (con != null) {
                try {
                    statement = con.createStatement();
                    statement.executeUpdate(sql);
                    return true;
                } catch (SQLException ex) {
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
                        
       return false; 
}
    // Kiểm tra xem user đã có trong database hay chưa?
    public boolean checkAva(int id) {
        try {

            String sql = "SELECT * FROM users WHERE Id = ?";
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            boolean result = rs.next();
            rs.close();
            stm.close();
            if (result) {
                return true;
            }
            
        } catch (Exception e) {
        }
        return false;
    }
    
    public boolean updateUser(User user) throws SQLException 
    {
            String sql =
            "update users " +
            "set username = ?,"  +
            "password = ?,"  +
            "win = ?,"  +
            "lose = ?,"  +
            "score = ?"  +
            " where Id = ?";
            PreparedStatement stm  = con.prepareStatement(sql);
            stm.setString(1, user.getUsername());
            stm.setString(2, user.getPassword());
            stm.setInt(3, user.getWin());
            stm.setInt(4, user.getLose());
            stm.setInt(5, user.getScore());
            stm.setInt(6, user.getId());
            stm.executeUpdate();
                              
            return true;
    }
    //Cập nhật trận thắng
    public boolean updateWin(int  id, int win) throws SQLException 
    {
    
        
            if(checkAva(id) == false)
                return false;
            
            String sql =
            "update users " +
            "set win = ?"  +
            " where Id = ?";
            PreparedStatement stm  = con.prepareStatement(sql);

            stm.setInt(1, win);
            stm.setInt(2, id);

            stm.executeUpdate();
                              
            return true;
    }
        //Cập nhật trận thua
    public boolean updateLose(int id, int lose) throws SQLException 
    {
    
        
            if(checkAva(id) == false)
                return false;
            
            String sql =
            "update users " +
            "set lose = ?"  +
            " where Id = ?"; 
            PreparedStatement stm  = con.prepareStatement(sql);

            stm.setInt(1, lose);
            stm.setInt(2, id);

            stm.executeUpdate();
                              
            return true;
    }
    //Lấy về id
    public int getId(String username)    {
        PreparedStatement stm = null;
        ResultSet rs = null;
        int result = 0;
        try {
            String sql = "SELECT * FROM users WHERE username = ?";
            stm = con.prepareStatement(sql);
            stm.setString(1, username);
            rs = stm.executeQuery();
            while (rs.next()) {
               result = rs.getInt("Id");
               break;
            }
            rs.close();
            stm.close();
        } catch (SQLException ex) {
                  
        }    
        return result;
    
    }
    //Lấy về thông tin user
    public User getUser(String username)    {
        PreparedStatement stm = null;
        ResultSet rs = null;
                try {
            String sql = "SELECT * FROM users WHERE username = ?";
            stm = con.prepareStatement(sql);
            stm.setString(1, username);
            rs = stm.executeQuery();
            while (rs.next()) {
       
                    User us = new User();
                    us.setId(rs.getInt("Id"));
                    us.setUsername(rs.getString("username"));
                    us.setPassword(rs.getString("password"));
                    us.setWin(rs.getInt("win"));
                    us.setLose(rs.getInt("lose"));
                    us.setScore(rs.getInt("score"));
                    return us;
        }
           
        } catch (SQLException ex) {
                  
        }    
        return null;
    }
    public boolean DeleteUser(int Id) throws SQLException
    {
            PreparedStatement stm = null;
            String sql = "DELETE FROM users WHERE Id = ?";
            stm = con.prepareStatement(sql);
            stm.setInt(1, Id);               
            stm.executeUpdate();
        return false;
    
    }
  
    public static void main(String[] args) {
        
    }
}
