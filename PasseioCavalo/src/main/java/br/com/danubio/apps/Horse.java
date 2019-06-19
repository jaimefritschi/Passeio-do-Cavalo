
package br.com.danubio.apps;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.Iterator;

public class Horse extends Piece {
    private static final int MOVEMENTS = 8;
    
    private Point point;
    private Movement[] movements;
    
    public Horse()
    {
        movements = new Movement[MOVEMENTS];
        
        movements[0] = new Movement(2, -1);
        movements[1] = new Movement(1, -2);
        movements[2] = new Movement(-1, -2);
        movements[3] = new Movement(-2, -1);
        movements[4] = new Movement(-2, 1);
        movements[5] = new Movement(-1, 2);
        movements[6] = new Movement(1, 2);
        movements[7] = new Movement(2, 1);
    }
    
    public void setPoint(Point point, ChessBoard board)
    {
        this.point = point;
        updateObserver();
        
        for (int i=0; i < MOVEMENTS; i++) {
            point = board.getPoint(movements[i].moveX() + this.point.x, 
              movements[i].moveY() + this.point.y);
            movements[i].setPoint(point);
            movements[i].setSelected(false);
        }
    }
    
    public Point getPoint()
    {
        return point;
    }
    
    public void move(Movement movement, ChessBoard board)
    {
        setPoint(movement.getPoint(), board);
    }
    
    public void drawHorse(Graphics2D g2d)
    {
        g2d.drawImage(Settings.getSettings().getHorseImage(), 
                (int) getX(), (int) getY(), null);
        for (Movement move : getMovements()) {
            move.drawMovement(g2d);
        }
    }
    
    @Override
    public void updateObserver()
    {
        if (getPoint() != null) {
            int amount = Settings.getSettings().getAmountPixels();
            setRect(getPoint().x * amount, getPoint().y * amount, amount, amount);
        }
    }

    @Override
    public Movement[] getMovements() {
        return movements;
    }

}
