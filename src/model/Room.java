/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;


import controller.UserDAO;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import view.server.ClientHandler;

/**
 *
 * @author Admin
 */
public class Room implements Serializable{
    int id = 0;
    public ClientHandler client1 = null;
    public ClientHandler client2 = null;
    public ArrayList<ClientHandler> lstClientView = null;

    public CPiece[][] pieceses;
    
    UserDAO userDAO;
    
    static final int NOT5        = 0;
    static final int OK5        = 1;
    int Dx[];
    int Dy[];
    //Đặt chỉ số cho 8 hướng di chuyển
    static final int D_UP        = 0;
    static final int D_UPRIGHT    = 1;
    static final int D_RIGHT    = 2;
    static final int D_DOWNRIGHT= 3;
    static final int D_DOWN        = 4;
    static final int D_DOWNLEFT    = 5;
    static final int D_LEFT        = 6;
    static final int D_UPLEFT    = 7;
    
    static final int BOARDSIZE = 25;
    void NewGame()
    {
        pieceses = new CPiece[25][25];
        for (int i = 0; i < 25; i++) {
            for (int j = 0; j < 25; j++) {
                pieceses[i][j] = new CPiece();
            }
        }
    }
    
    public Room(int _id) {
        id = _id;
        lstClientView = new ArrayList<ClientHandler>();
        NewGame();
        userDAO = new UserDAO();
        // ma trận direction
        Dx = new int[8];
        Dy = new int[8];
        // thiết lập 2 mảng 1 chiều Dx, Dy cho 8 hướng đánh
        Dx[0] =  0;  Dy[0] = -1;
        Dx[1] =  1;  Dy[1] = -1;
        Dx[2] =  1;  Dy[2] =  0;
        Dx[3] =  1;  Dy[3] =  1;
        Dx[4] =  0;  Dy[4] =  1;
        Dx[5] = -1;  Dy[5] =  1;
        Dx[6] = -1;  Dy[6] =  0;
        Dx[7] = -1;  Dy[7] = -1;
    }
    
    // lấy về chuỗi các ô cùng màu theo 1 hướng chỉ định
    public int GetSequence(int color,int x,int y,int direction) {
        int num = 0;
        int dx = Dx[direction];
        int dy = Dy[direction];

        Boolean Space = false;

        while(pieceses[x][y].State == color) {
            num++;
            x += dx; y += dy;
            //Nếu ô đang xét ra khỏi bàn cờ thì dừng
            if( x < 0 || x >= BOARDSIZE || y < 0 || y >= BOARDSIZE ) break;
            //Nếu ô đang xét chưa được đánh dấu thì dừng
            if(pieceses[x][y].State == CPiece.EMPTY) {
                Space = true;
                break;
            }
        }
        // Trả về số lượng ô liên tiếp có cùng x hoặc o
        return num;
    }
    
    //Kiểm tra xem có khối 5 liên tiếp hay không
    public int Find5Block(int color,int x,int y) {

        int max,a;

        max = GetSequence(color,x,y,D_UP) + GetSequence(color,x,y,D_DOWN) - 1 ; //phải trừ 1 do tính vị trí x, y 2 lần
        a =GetSequence(color,x,y,D_LEFT) + GetSequence(color,x,y,D_RIGHT) - 1 ;
        max = Math.max(max,a);
        a = GetSequence(color,x,y,D_UPLEFT) + GetSequence(color,x,y,D_DOWNRIGHT) -1 ;
        max = Math.max(max,a);
        a = GetSequence(color,x,y,D_UPRIGHT) + GetSequence(color,x,y,D_DOWNLEFT) - 1 ;
        max = Math.max(max,a);
        //Nếu có khối 5 liên tiếp trả về 
        if( max >= 5)
          
            return OK5;
        //Nếu ko có trả về 0
        return NOT5;
    }
    //cập nhật thông tin user khi kết thúc ván đấu
    public void clientWinLose(ClientHandler client, Boolean isWin)
    {
        int sum = client.user.getScore();
        if (isWin)
        {
            sum+=100;
            client.user.setWin(client.user.getWin()+1);
        }
        else
        {
            sum-=100;
            client.user.setLose(client.user.getLose()+1);
        }
        client.user.setScore(sum);
        try {
            userDAO.updateUser(client.user);
        } catch (SQLException ex) {
            Logger.getLogger(Room.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    //Trang thái ván đấu khi 1 người chơi đánh
    public int put(ClientHandler client, Position position) throws IOException
    {
        //Nếu người đánh là người 1
        if (client == client1)
        {
            pieceses[position.x][position.y].State = CPiece.BLACK;
            if (Find5Block(CPiece.BLACK, position.x, position.y)==OK5)
            {
                System.out.printf("Black win");
                clientWinLose(client1, true);
                //ở hàm client.Main nó gọi hàm này để hiển thị lên lable là người chơi đã thắng
                client1.SendMessage(35, "win");
                clientWinLose(client2, false);
                client2.SendMessage(35, "lose");
                //vẽ ra bàn cờ sau mỗi lần đánh
                client1.SendMessage(30, pieceses);
                client2.SendMessage(30, pieceses);
                //hỏi người chơi có muốn nữa ko?
                client1.SendMessage(100,null);
                client2.SendMessage(100,null);
                //load game mới
                NewGame();     
                //thông báo lượt đánh của người chơi
                client1.SendMessage(31, null); 
                client2.SendMessage(36, null);
                //Lấy thông tin của người chơi thứ nhất (tên+ điểm)
                client1.SendMessage(28, null);
                //Lấy thông tin của người chơi thứ 2 (tên+ điểm)
                client2.SendMessage(28, null);
            }
            else
            {
                client2.SendMessage(31, null);
                client1.SendMessage(36, null);
            }
        }
        else
        {
            pieceses[position.x][position.y].State = CPiece.WHITE;
            if (Find5Block(CPiece.WHITE, position.x, position.y)==OK5)
            {
                System.out.printf("WHITE win");
                clientWinLose(client2, true);
                
                client2.SendMessage(35, "win");
                clientWinLose(client1, false);
                client1.SendMessage(35, "lose");
                //vẽ ra bàn cờ sau mỗi lần đánh
                client1.SendMessage(30, pieceses);
                client2.SendMessage(30, pieceses);
                
                client1.SendMessage(100,null);
                client2.SendMessage(100,null);
                
                NewGame();
                client1.SendMessage(31, null); 
                client2.SendMessage(36, null);
                client1.SendMessage(28, null);
                client2.SendMessage(28, null);
            }
            else
            {
                client1.SendMessage(31, null);
                client2.SendMessage(36, null);
            }
        }
        
        client1.SendMessage(30, pieceses);
        client2.SendMessage(30, pieceses);
        
        return 1;
    }
    //đếm số chỗ có sẵn cho mỗi phòng
    public int countAvailable()
    {
        //khởi tạo mặc định mỗi phòng có 2 người chơi
        int n = 2;
        //Nếu có 1 người vào thì n-1
        if (client1 != null)
            n--;
        if (client2 != null)
            n--;
        return n;
    }
    //Khi có người chơi vào phòng
    public Boolean add(ClientHandler client) throws IOException
    {
        //Nếu đang chơi chủ phòng thoát ra ngoài thì người chơi thứ 2 sẽ làm chủ của phòng mới
        if (client1==null)
        {
            client1 = client2;
            client2 = client;
           //Nếu phòng đủ 2 người chơi thì game bắt đầu
           if(countAvailable()==0){
               NewGame();
 
               client1.SendMessage(31, null);
               client2.SendMessage(36, null);
               
           }
            return true;
        }
 
        if (client2==null)
        {
            client2 = client;
            
            if(countAvailable()==0){
               NewGame();
               client1.SendMessage(31, null);
               client2.SendMessage(36, null);
             
           }
            
            return true;
        }
        
        return false;
    }
    //khi có người chơi ra khỏi phòng
    public void clientExit(ClientHandler clientHandler) throws IOException
    {
        //khi phòng chỉ mới có 1 người vào, nếu người đó thoát ra luôn thì ko bị xử thua, mà chỉ cập nhật lại phòng
        if (countAvailable()==1)
        {
            if (client1!=null)
                client1.room = null;
            client1 = null;
            if (client2!=null)
                client2.room = null;
            client2 = null;
            
        }
        //khi phòng đang có 2 người chơi mà một người thoát ra
        else if (countAvailable()==0)
        {
            //Nếu người chơi thoát ra là người thứ nhất
            if (client1==clientHandler)
            {
                clientWinLose(client2, true);
                client2.SendMessage(35, "win");
                clientWinLose(client1, false);
                client1.SendMessage(35, "lose");
                client1.room = null;
                client1 = null;
                NewGame();
                //vẽ lại bàn cờ
                client2.SendMessage(30, pieceses);
                client2.SendMessage(45, null);
         
                countAvailable();
               
            }
            else
            {
                clientWinLose(client1, true);
                client1.SendMessage(35, "win");
                clientWinLose(client2, false);
                client2.SendMessage(35, "lose");
                 client2.room = null;
                client2 = null;
                NewGame();
                //vẽ lại bàn cờ
                client1.SendMessage(30, pieceses);
                //thông báo thắng
                client1.SendMessage(45, null);
    
                countAvailable();
      
            }
          
             
        }
        
        
    }
     //khi có người chơi ra khỏi phòng
    public void clientPlayFinish(ClientHandler clientHandler) throws IOException
    {
        //khi phòng chỉ mới có 1 người vào, nếu người đó thoát ra luôn thì ko bị xử thua, mà chỉ cập nhật lại phòng
        if (countAvailable()==1)
        {
            if (client1!=null)
                client1.room = null;
            client1 = null;
            if (client2!=null)
                client2.room = null;
            client2 = null;
            
        }
        //khi phòng đang có 2 người chơi mà một người thoát ra
        else if (countAvailable()==0)
        {
            //Nếu người chơi thoát ra là người thứ nhất
            if (client1==clientHandler)
            {
                client1.room = null;
                client1 = null;
                NewGame();
                //vẽ lại bàn cờ
                client2.SendMessage(30, pieceses);
                client2.SendMessage(45, null);
                countAvailable();
               
            }
            else
            {
                client2.room = null;
                client2 = null;
                NewGame();
                //vẽ lại bàn cờ
                client1.SendMessage(30, pieceses);
                //thông báo thắng
                client1.SendMessage(45, null);
                countAvailable();
      
            }
                      
        }
        
        
    }
    @Override
    public String toString()
    {
        int n = 2;
        if (client1 != null)
            n--;
        if (client2 != null)
            n--;
        if(n==0){
            return "Phòng " + id + ": " + n + " đã đầy";
        }
        else{
            return "Phòng " + id + ": có " + n + " chỗ trống";
        }
    }
}
