/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.client;

import java.io.IOException;
import model.Message;

/**
 *
 * @author Admin
 */
public interface inReceiveMessage {
    //phương thức để nhận về 1 thông điệp
    public void ReceiveMessage(Message msg) throws IOException;
}
