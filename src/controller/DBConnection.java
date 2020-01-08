/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

/**
 *
 * @author Admin
 */
import java.sql.Connection;
import java.sql.DriverManager;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Admin
 */
public class DBConnection {

    public static Connection getConnection() {
        Connection cons = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            cons = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/caro", "root", "thang18121997");
//			System.out.println("success");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cons;
    }

    public static void main(String[] args) {
//        System.out.println(getConnection());
    }
}
