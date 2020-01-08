/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;

/**
 *
 * @author Admin
 */
public class User implements Serializable{
    private int Id;
    private String username;
    private String password;
    private int win;
    private int lose;
    private int score;

    public User(int Id, String username, String password, int win, int lose, int score) {
        this.Id = Id;
        this.username = username;
        this.password = password;
        this.win = win;
        this.lose = lose;
        this.score = score;
    }
   

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
   
    
    public User()
    {
    
    }
    
  
    
    public int getId()
    {
        return Id;
    }
    public String getUsername()
    {
        return username;
    }
    public String getPassword()
    {
        return password;
    }                    
    public int getWin()
    {
        return win;
    }        
    public int getLose()
    {
        return lose;
    }
    public int getScore()
    {
        return score;
    }
    public void setId(int id)
    {
        this.Id = id;
    }
    public void setUsername(String username)
    {
         this.username = username;
    }
    public void setPassword(String password)
    {
        this.password = password;
    }                    
    public void setWin(int win)
    {
        this.win = win;
    }        
    public void setLose(int lose)
    {
        this.lose = lose;
    }
    public void setScore(int score)
    {
        this.score = score;
    }
}
