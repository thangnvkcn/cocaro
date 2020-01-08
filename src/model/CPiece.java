package model;

import java.io.Serializable;
//ô trên bàn cờ có 3 trạng thái
public class CPiece implements Serializable{

    public int State;
    static public final int EMPTY = 0;
    static public final int WHITE = 1;       //Quan O
    static public final int BLACK = 2;      //Quan X

}  