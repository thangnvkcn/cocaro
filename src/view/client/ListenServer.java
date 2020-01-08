/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.client;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import model.Message;
import model.User;

/**
 *
 * @author Admin
 */
//Class để lắng nghe các kết nối để gửi tới server sử dụng đa luồng
public class ListenServer extends Thread {
        //Khai báo socket và các kiểu đọc,ghi dữ liệu vào ra 
        Socket socket;
        ObjectOutputStream objectOutputStream;
        ObjectInputStream objectInputStream;
        public List<User> listUser = new ArrayList<User>();
        public User user;
        //khai báo 1 interface để thực thi phương thức receiveMessage
        public inReceiveMessage receive;
        //hàm khởi tạo
        ListenServer(Socket socket) throws IOException {
            this.socket = socket;
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectInputStream = new ObjectInputStream(socket.getInputStream());

        }
//ghi đè phương thức run của Thread class
        @Override
        public void run() {
            do {
                try {
                    Object o = objectInputStream.readObject();
                    if (o != null && receive!=null) {
                        receive.ReceiveMessage((Message) o);
                    }
                    

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
            }while (true);
    
        }
        //Phương thức để gửi 1 thông điệp chứa mã thông điệp và nội dung thông điệp
        public void SendMessage(int ty, Object content) throws IOException {
            //Đóng gói thông điệp
            Message message = new Message(ty, content);
            //gửi thông điệp
            SendMessage(message);
        }
        
        public void SendMessage(Message message) throws IOException {
            //reset lại luồng đầu ra
            objectOutputStream.reset();
            //ghi 1 đối tượng 
            objectOutputStream.writeObject(message);
        }
    }
    