/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import model.Board;
import model.CPiece;
import model.Message;
import model.Position;
import model.User;

/**
 *
 * @author Admin
 */
public class Main extends javax.swing.JFrame implements inReceiveMessage {

    ListenServer listenServer;
    //PanelBoard windowPanel;

    Board board;

    JScrollPane jScroll;

    int GameState = 0;
    static final int WAIT = 0;
    static final int MY_TURN = 1;
    static final int YOU_WIN = 2;
    static final int YOU_LOSE = 3;
    
    /**
     * Creates new form Main
     */
    public Main(ListenServer listenServer) {
        initComponents();

        setTitle("Game Caro");

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(700, 565);
        setLayout(new BorderLayout());

        InitGame();
        setLocationRelativeTo(null);

        this.listenServer = listenServer;
        this.listenServer.receive = this;

        try {
            listenServer.SendMessage(28, null); //Lấy thông tin player
            listenServer.SendMessage(30, null); //lấy bàn cờ
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void InitGame() {

        board = new Board();
        board.setPreferredSize(new Dimension(500, 500));

        panelCaro.setViewportView(board);

        board.init(500, 500);
        board.Initialize();
        board.Draw();
    }

  
    void putStatus(String strStt) {
        lblStatus.setText(strStt);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g); //To change body of generated methods, choose Tools | Templates.        

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblName1 = new javax.swing.JLabel();
        lblName2 = new javax.swing.JLabel();
        lblStatus = new javax.swing.JLabel();
        lblScore1 = new javax.swing.JLabel();
        lblScore2 = new javax.swing.JLabel();
        txtChat = new javax.swing.JTextField();
        txtSendChat = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        chatArea = new javax.swing.JTextArea();
        btnExitRoom = new javax.swing.JButton();
        panelCaro = new javax.swing.JScrollPane();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Giao diện p");

        lblName1.setText("Người chơi 1");

        lblName2.setText("Người chơi 2");

        lblStatus.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblStatus.setText("Đợi người chơi thứ 2");

        lblScore1.setText("Score: 0");

        lblScore2.setText("Score: 0");

        txtChat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtChatActionPerformed(evt);
            }
        });

        txtSendChat.setText("Send");
        txtSendChat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtSendChatMouseClicked(evt);
            }
        });
        txtSendChat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSendChatActionPerformed(evt);
            }
        });

        chatArea.setEditable(false);
        chatArea.setColumns(20);
        chatArea.setRows(5);
        jScrollPane1.setViewportView(chatArea);

        btnExitRoom.setText("Exit Room");
        btnExitRoom.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnExitRoomMouseClicked(evt);
            }
        });
        btnExitRoom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExitRoomActionPerformed(evt);
            }
        });

        panelCaro.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelCaroMouseClicked(evt);
            }
        });

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/user.png"))); // NOI18N

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/user.png"))); // NOI18N

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/cross.png"))); // NOI18N

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/circle.png"))); // NOI18N

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/title.PNG"))); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 373, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnExitRoom, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(panelCaro, javax.swing.GroupLayout.PREFERRED_SIZE, 379, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 247, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtChat, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtSendChat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel1)
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblName1, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lblScore1, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel3))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblScore2, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lblName2, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(179, 179, 179)
                .addComponent(jLabel4)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(panelCaro, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(lblName1)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblScore1)))
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(lblName2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(lblScore2, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtChat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtSendChat))
                                .addGap(19, 19, 19)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnExitRoom, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(17, 17, 17))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnExitRoomMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnExitRoomMouseClicked
        // TODO add your handling code here:

        try {
            listenServer.SendMessage(39, null);
            java.awt.EventQueue.invokeLater(new Runnable() {
                public void run() {

                    new RoomFrame(listenServer).setVisible(true);

                }
            });
            this.dispose();
        } catch (Exception e) {
        }

    }//GEN-LAST:event_btnExitRoomMouseClicked

    private void panelCaroMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelCaroMouseClicked
        // TODO add your handling code here:
        //lấy tọa độ của điểm đánh
        int x = evt.getX();
        int y = evt.getY();
        
        if (GameState == MY_TURN) {
            Position pos = new Position();
            //lấy về vị trí offetx, offety của bàn cờ
            int offetX = board.getX();
            int offetY = board.getY();
            
            if (!board.GetPos(x - offetX, y - offetY, pos)) {
                return;
            }
            //Nếu vị trí đánh còn trống
            if (board.Pieces[pos.x][pos.y].State == CPiece.EMPTY) {
                try {
                    //đổi lượt
                    GameState = WAIT;
                    //hiển thị lên nhãn thông báo đợi
                    putStatus("Doi...");
                    //lấy đến server để cập nhật bàn cờ
                    listenServer.SendMessage(30, pos);
                } catch (IOException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                //nếu ô đã được đánh. Hiển thị ra thông báo
                putStatus("Ô này đã được đánh, vui lòng chọn ô khác để đi!");
            }
        }

    }//GEN-LAST:event_panelCaroMouseClicked

    private void txtSendChatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtSendChatMouseClicked
        // TODO add your handling code here:
        //
        String strMess = listenServer.user.getUsername() + ": " + txtChat.getText();
        //Nếu khung chat rỗng
        if (chatArea.getText().isEmpty()) {
            //thêm nội dung vừa chát vào
            chatArea.setText(strMess);
        } else {
            //Nếu khung chat không rỗng, lấy về nội dung trước đó và gắn kèm nội dung mới được chat
            chatArea.setText(chatArea.getText() + '\n' + strMess);
        }
        //sau khi gửi thì set ô nhập chat về rỗng
        txtChat.setText("");
        try {
            //gửi cho server để xử lý
            listenServer.SendMessage(40, strMess);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_txtSendChatMouseClicked

    private void btnExitRoomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExitRoomActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnExitRoomActionPerformed

    private void txtSendChatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSendChatActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSendChatActionPerformed

    private void txtChatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtChatActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtChatActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                System.out.println("CLIENT");
//                new Main(null).setVisible(true);
//            }
//        });
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LoginForm().setVisible(true);

            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnExitRoom;
    private javax.swing.JTextArea chatArea;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblName1;
    private javax.swing.JLabel lblName2;
    private javax.swing.JLabel lblScore1;
    private javax.swing.JLabel lblScore2;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JScrollPane panelCaro;
    private javax.swing.JTextField txtChat;
    private javax.swing.JButton txtSendChat;
    // End of variables declaration//GEN-END:variables

    @Override
    public void ReceiveMessage(Message msg) throws IOException {
        switch (msg.getType()) {
            case 30: // get ban co
            {

                board.Pieces = (CPiece[][]) msg.getContent();
                board.Draw();
                break;
            }
            
            case 31: {
                putStatus("Tới lượt bạn");
                GameState = MY_TURN;
                break;
            }
            case 34: // thông tin 2 người chơi
            {
                User[] arrUser = (User[]) msg.getContent();
                if (arrUser != null && arrUser.length >= 1) {
                    lblName1.setText(arrUser[0].getUsername());
                    lblScore1.setText("" + arrUser[0].getScore());
                    lblName2.setText(arrUser[1].getUsername());
                    lblScore2.setText("" + arrUser[1].getScore());
                }

                break;
            }
            //nếu có người chơi thắng
            case 35: {
                if ("win".equalsIgnoreCase(msg.getContent().toString())) {
                    GameState = YOU_WIN;
                    putStatus("Bạn đã thắng rồi!");

                } else if ("lose".equalsIgnoreCase(msg.getContent().toString())) {
                    GameState = YOU_LOSE;
                    putStatus("Bạn đã thua rồi!");

                }
                //System.out.println(msg.getObject());
                break;
            }
            case 36: {
                putStatus("Đợt đối thủ đánh!");

                break;
            }
            case 40: // nhận tin nhắn chat
            {
                String strMess = msg.getContent().toString();
                if (chatArea.getText().isEmpty()) {
                    chatArea.setText(strMess);
                } else {
                    chatArea.setText(chatArea.getText() + '\n' + strMess);
                }
                break;
            }
            case 45: // get status
            {

                chatArea.setText("");
                lblStatus.setText("Bạn đã thắng, Đợi người chơi thứ hai");
                break;
            }
            case 100: // xem muốn chơi nữa ko?
            {
                if (JOptionPane.showConfirmDialog(this, "Bạn cớ muốn chơi tiếp không?", "Thông báo", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.NO_OPTION) {
                    try {
                        listenServer.SendMessage(43, null);
                        java.awt.EventQueue.invokeLater(new Runnable() {
                            public void run() {
                                new RoomFrame(listenServer).setVisible(true);

                            }
                        });
                        this.dispose();
                    } catch (Exception e) {
                    }
                }
       
                break;
            }

        }
    }
}
