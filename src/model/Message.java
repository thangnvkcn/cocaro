/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Admin
 */
import java.io.Serializable;

/**
 *
 * @author Admin
 */
//Thông điệp bao gồm mà thông điệp và nội dung thông điệp
public class Message implements Serializable{
    private int type;
    private Object content;

    public Message(int type, Object content) {
        this.type = type;
        this.content = content;
    }
    
    /**
     * @return the type
     */
    public int getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(int type) {
        this.type = type;
    }

    /**
     * @return the object
     */
    public Object getContent() {
        return content;
    }

    /**
     * @param object the object to set
     */
    public void setObject(Object object) {
        this.content = object;
    }
        
}