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
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 *
 * @author Admin
 */
public class Board extends JPanel {

    static final int TOP = 10;
    static final int LEFT = 10;
    static final int BOARDSIZE = 25;
    static final int PIECESIZE = 20;
    
    public CPiece Pieces[][];
    public int Area[][];
    public int numPiece;
    Dimension dim;
    Graphics bufferGraphics;
    Image offscreen;
    Image blackImage;
    Image whiteImage;
    
    public Board() {

        resize(310, 310);

        Pieces = new CPiece[BOARDSIZE][BOARDSIZE];

        for (int i = 0; i < BOARDSIZE; i++) {
            for (int j = 0; j < BOARDSIZE; j++) {
                Pieces[i][j] = new CPiece();
            }
        }
        Area = new int[BOARDSIZE][BOARDSIZE];


        try
        {
            
            URL url = this.getClass().getResource("/image/cross.png");
            
            blackImage = ImageIO.read(url); //quan X
            url = this.getClass().getResource("/image/circle.png"); //quan O
            whiteImage = ImageIO.read(url);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
  

    public void Initialize() {
      
        for (int x = 0; x < BOARDSIZE; x++) {
            for (int y = 0; y < BOARDSIZE; y++) {
                Pieces[x][y].State = CPiece.EMPTY;
                Area[x][y] = 0;
            }
        }
        numPiece = 0;
    }
    //tạo khung hình chữ nhật
    public void init(int width, int height) {
        //dim = getSize();
        offscreen = createImage(width, height);
        bufferGraphics = offscreen.getGraphics();
    }

    public boolean GetPos(int x, int y, Position pos) {

        if (x < LEFT - (PIECESIZE / 2) || x > LEFT + (PIECESIZE * (BOARDSIZE - 1)) + (PIECESIZE / 2)) {
            return false;
        }
        if (y < TOP - (PIECESIZE / 2) || y > TOP + (PIECESIZE * (BOARDSIZE - 1)) + (PIECESIZE / 2)) {
            return false;
        }
        pos.x = (x - (LEFT - (PIECESIZE / 2))) / PIECESIZE;
        pos.y = (y - (TOP - (PIECESIZE / 2))) / PIECESIZE;
        return true;
    }

    public void Draw() {
        
        this.repaint();
        
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int x, y;
        bufferGraphics.clearRect(0, 0, offscreen.getWidth(this), offscreen.getHeight(this));
        bufferGraphics.setColor(Color.red);
        for (x = 0; x < BOARDSIZE; x++) {
            bufferGraphics.drawLine(x * PIECESIZE + LEFT-PIECESIZE/2, TOP-PIECESIZE/2, x * PIECESIZE + LEFT-PIECESIZE/2, TOP + (BOARDSIZE - 1) * PIECESIZE-PIECESIZE/2);
        }
        for (y = 0; y < BOARDSIZE; y++) {
            bufferGraphics.drawLine(LEFT-PIECESIZE/2, y * PIECESIZE + TOP-PIECESIZE/2, LEFT + (BOARDSIZE - 1) * PIECESIZE-PIECESIZE/2, y * PIECESIZE + TOP-PIECESIZE/2);
        }
        


       // XO Drawing
        if (blackImage == null || whiteImage==null)
        {
            for(x = 0; x < BOARDSIZE; x++) {
                for(y = 0; y < BOARDSIZE; y++) {
                    switch(Pieces[x][y].State) {
                        case CPiece.BLACK:
                            bufferGraphics.setColor(Color.red);

                            bufferGraphics.drawLine(x*PIECESIZE+2, y*PIECESIZE+2, (x+1)*PIECESIZE-2, (y+1)*PIECESIZE-2);
                            bufferGraphics.drawLine(x*PIECESIZE+2, (y+1)*PIECESIZE-2, (x+1)*PIECESIZE-2, y*PIECESIZE+2);

                            break;
                        case CPiece.WHITE:
                            bufferGraphics.setColor(Color.blue);
                            bufferGraphics.drawOval(x*PIECESIZE,y*PIECESIZE,PIECESIZE-2,PIECESIZE-2);
                            break;
                    }
                }
            }
        }
        else
        {
            for(x = 0; x < BOARDSIZE; x++) {
                for(y = 0; y < BOARDSIZE; y++) {
                    switch(Pieces[x][y].State) {
                        case CPiece.WHITE:
                            bufferGraphics.drawImage(whiteImage, x*PIECESIZE, y*PIECESIZE, this);
 
                            break;
                        case CPiece.BLACK:
                            bufferGraphics.drawImage(blackImage, x*PIECESIZE, y*PIECESIZE, this);
                          
                            break;
                    }
                }
            }
        
        }
        
        g.drawImage(offscreen, 0, 0, this); // vẽ ra bàn cờ
    }
}
