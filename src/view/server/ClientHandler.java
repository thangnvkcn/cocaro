/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.server;



import controller.UserDAO;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Message;
import model.Position;
import model.Room;
import model.User;


/**
 *
 * @author Admin
 */
//class này để xử lý các yêu cầu từ client gửi đến và trả về kết quả cho client 
public class ClientHandler extends Thread {
        public Room room = null;
        private List<User> listUser = new ArrayList<User>();
        private Socket socket;
        ObjectInputStream inputStream;
        ObjectOutputStream outputStream;
        
        public User user;
        
        Boolean execute = true;
        //Tham số là 1 socket
        ClientHandler(Socket socket) throws IOException {
            this.socket = socket;
            //khai báo và khởi tạo đọc đầu vào
            inputStream = new ObjectInputStream(socket.getInputStream());
            //khai báo và khởi tạo luồng ghi đầu ra
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            execute = true;
        }

    
        //Nhận message
        void ReceiveMessage(Message msg) throws IOException {
            
            switch (msg.getType()) {
                //nếu message là có type =  0  thì thực hiện chức kiểm tra đăng nhập  
                case 0: {
                    //Nhận từ client 1 User 
                    User temp = (User)msg.getContent();
                    UserDAO userDAO = new UserDAO();
                    user = userDAO.checkLogin(temp.getUsername(), temp.getPassword());
                    //nếu tồn tại user trong csdl
                    if(user != null)
                    {
                        Boolean flag = true;
                        // Kiểm tra xem đã có ai đăng nhập user, pass này hay chưa
                        for (ClientHandler cli : Main.listClient) {
                            //nếu đã có người đăng nhập vào rồi
                            if (cli!=this && cli.user!=null && cli.user.getUsername().equalsIgnoreCase(user.getUsername()))
                            {
                                user.setId(0);
                                break;
                            }
                        }
                        //nếu chưa có ai đăng nhập vào
                        if (user!=null&&user.getId()!=0)
                            System.out.println("Server: Hello " + user.getUsername());
                    }
                    //gửi thông báo đến client
                    SendMessage(0, user);
                    break;
                }
                //Nếu message có type = 1 thì kiểm tra xem username đã tồn tại hay chưa
                case 1: {
                    User temp = (User)msg.getContent();
                    UserDAO userDAO = new UserDAO();
                    boolean success;
                    success = userDAO.checkAva(userDAO.getId(temp.getUsername()));
                    //nếu username đã tồn tại trong csdl
                    if (success == true) {
                        SendMessage(1,  " tài khoản này đã được sử dụng. Vui lòng chọn tài khoản khác");
                        return;
                    }
                    //ngược lại chưa có trong csdl thì cho người dùng đăng ký
                    else{
                    success = userDAO.register(temp.getUsername(), temp.getPassword());
                    //nếu thành công thì gửi về cho client để hiển thị lên giao diện
                    if (success == true) {
                        SendMessage(1, "Đăng ký thành công!");
                    }
                    }
                    break;
                }
                //nếu message có type = 10 thì sẽ thực hiện việc chat
//                case 10: 
//                {
//                    System.out.println(msg.getContent().toString());
//                    break;
//                }
               //nếu message có type = 20 thì sẽ thực hiện thao tác với room 
                case 20: 
                {   
                    //lấy ra phòng cần
                    room = Main.listRoom.get(Integer.parseInt(msg.getContent().toString()));
                    //Kiểm tra nếu phòng đã đầy
                    if (room.add(this)==false) 
                    {
                        //cập nhật lại số chỗ còn trống của mỗi phòng
                        int[] arrRoom = new int[Main.listRoom.size()];
                        for (int i=0; i<Main.listRoom.size(); i++)
                        {
                            arrRoom[i] = Main.listRoom.get(i).countAvailable();
                        }
                        //gửi về client mảng danh sách chứa số lượng chỗ cho các phòng
                        SendMessage(22, arrRoom);
                    }
                    else
                        //Nếu thêm được thì get bàn cờ
                        SendMessage(20, null);
                    
                    break;
                }
                //nếu message có type = 21 thì sẽ thực hiện việc lấy về toàn bộ danh sách room
                case 21: 
                {
                    int[] arrRoom = new int[Main.listRoom.size()];
                    for (int i=0; i<Main.listRoom.size(); i++)
                    {
                        // đếm số lượng chỗ còn trống của mỗi phòng và lưu vào mảng arrRoom (mảng chứa số lượng chỗ còn của mỗi phòng)
                        arrRoom[i] = Main.listRoom.get(i).countAvailable();
                    }
                    //gửi mảng arrRoom về để hiện thị lên giao diện
                    SendMessage(21, arrRoom);
                    break;
                }
                //nếu message có type = 28 thì sẽ thực hiện việc lấy về toàn
                case 28:
                {
                    if (room.client1!=null && room.client2!=null)
                    {
                        User[] arrUser = new User[2];
                        arrUser[0] = room.client1.user;
                        arrUser[1] = room.client2.user;
                        room.client1.SendMessage(34, arrUser); //lấy về thông tin người thứ nhất
                        room.client2.SendMessage(34, arrUser); //lấy về thông tin người thứ hai
                        room.client2.SendMessage(36, null);
                    }
                    break;
                }
                // nếu mesage có type  = 30 sẽ thực hiện lấy bàn cờ
               
                case 30: 
                {
                    Position pos = (Position)msg.getContent();
                    if (pos!=null)
                        room.put(this, pos);
                    
                    if (room != null) {
                       
                        for (ClientHandler cli : room.lstClientView) {
                                cli.SendMessage(30, room.pieceses);
                        }
                    }
        
                    break;
                }
                case 39: //Exit room
                {
                    if (room!=null)
                    {
                        room.clientExit(this);
                    }
                    break;
                }
                case 40: //Chat
                {
                    if (room!=null)
                    {
                        // Gui cho 2 client
                        if (room.client1!=this)
                            room.client1.SendMessage(msg);
                        if (room.client2!=this)
                            room.client2.SendMessage(msg);

                        for (ClientHandler cli : room.lstClientView) {
                            if (cli!=this)
                            {
                                cli.SendMessage(msg);
                            }
                        }
                    }
                    break;
                }
                case 41: //View
                {
                    room = Main.listRoom.get(Integer.parseInt(msg.getContent().toString()));
                    room.lstClientView.add(this);
                    SendMessage(20, null);
                    break;
                }
                case 42: 
                {
                   
                    UserDAO userDAO = new UserDAO();
                    listUser = userDAO.getUserList();
                    SendMessage(42, listUser);
                    break;
                }
                //khi kết thúc 1 ván mà người chơi không muốn chơi tiếp
                case 43:
                {
                    if (room!=null)
                    {
                        room.clientPlayFinish(this);
                    }
                    break;
                }
                
            }
        }

        public void SendMessage(int ty, Object content) throws IOException {
            Message message = new Message(ty, content);
            SendMessage(message);
        }
                
        public void SendMessage(Message message) throws IOException {
            //reset một outputStream
            outputStream.reset();
            // ghi đối tượng vào luồng
            outputStream.writeObject(message);
        }
        
        public Boolean closeClient() throws Throwable
        {
            
            
            if (room!=null) // Thông báo thoát room
            {
                try {
                    room.lstClientView.remove(this);
                } catch (Exception e) {
                    
                }
                
                room.clientExit(this);
            }
            
            Main.listClient.remove(this);
            try {
                this.socket.close();
            } catch (IOException ex) {
                Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("Client Exit");
            execute = false;
            
            
            return true;
        }
        
        @Override
        public void run() {
            //khi client vẫn trong phòng
            while (execute) {
                
                try {
                    Object o = inputStream.readObject();
                    if (o != null) {
                        ReceiveMessage((Message)o);
                    }
                    //Guilai();
                } catch (IOException e) {
                    try {
                        closeClient();
                    } catch (Throwable ex) {
                        Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

            }
        }


    }