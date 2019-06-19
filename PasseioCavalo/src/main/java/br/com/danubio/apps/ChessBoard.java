
package br.com.danubio.apps;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

public class ChessBoard {
    private static final Color LIGHT_COLOR = new Color(255, 206, 158);
    private static final Color DARK_COLOR = new Color(209, 139, 71);
    private static final Color BUSY_COLOR = new Color(222, 55, 11);
    
    private static final int SQUARE = 8;
    
    private Square[][] squares;
    
    public ChessBoard()
    {
        squares = new Square[SQUARE][SQUARE];
        
        for (int y=0; y < SQUARE; y++) {
            for (int x=0; x < SQUARE; x++) {
                squares[x][y] = new Square(new Point(x, y));
                boolean choice = ((x + y) % 2) == 0;
                squares[x][y].setColor((choice) ? LIGHT_COLOR : DARK_COLOR);
            }
        }
    }
    
    public Square[][] getSquares()
    {
        return squares;
    }
    
    public void setSelected(Point point)
    {
        Square square = squares[point.x][point.y];
        square.setSelected(true);
    }
    
    public void refresh()
    {
        for (Square[] squares : this.squares) {
            for (Square square : squares) {
                square.setSelected(false);
            }
        }
    }
    
    public Point getPoint(int x, int y)
    {
        if (x < 0 || x >= SQUARE || y < 0 || y >= SQUARE) {
            return null;
        }
        
        return squares[x][y].getPoint();
    }

    public void drawChessBoard(Graphics2D g2d)
    {
        // não confere a posição dos Rectangle2D 
        //g2d.translate(30, 30);
        
        Square square;
        for (int y=0; y < SQUARE; y++) {
            for (int x=0; x < SQUARE; x++) {
                square = squares[x][y];
                g2d.setColor(square.getColor());
                g2d.fill(square);
                g2d.setColor(Color.BLACK);
                g2d.draw(square);
            }
        }
    }
}
