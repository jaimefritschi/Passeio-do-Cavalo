
package br.com.danubio.apps;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

public class Movement extends Rectangle.Float implements SettingsObserver {
    
    private static final Stroke lineStroke = new BasicStroke(10.0f);
    private Point point;
    
    private final int moveX;
    private final int moveY;
    private boolean isSelected;
    private Ellipse2D.Float ellipse;
    
    public Movement(int moveX, int moveY)
    {
        this.moveX = moveX;
        this.moveY = moveY;
        point = null;
        isSelected = false;
        ellipse = new Ellipse2D.Float();
    }
    
    public void setPoint(Point point)
    {
        this.point = point;
        updateObserver();
    }
    
    public Point getPoint()
    {
        return point;
    }
    
    public void setSelected(boolean is)
    {
        this.isSelected = is;
    }
    
    public boolean isSelected()
    {
        return isSelected;
    }
    
    public Image getImage()
    {
        if (isSelected) {
            return Settings.getSettings().getSelectedImage();
        }
        
        return Settings.getSettings().getMovementImage();
    }
    
    public int moveX()
    {
        return moveX;
    }
    
    public int moveY()
    {
        return moveY;
    }
    
    @Override
    public void updateObserver()
    {
        if (point != null) {
            int amount = Settings.getSettings().getAmountPixels();
            setRect(this.point.x * amount, this.point.y * amount, amount, amount);
            ellipse.setFrame((this.point.x*amount) + 10, (this.point.y*amount) + 10, amount-20, amount-20);
        }
    }
    
    public void drawMovement(Graphics2D g2d) 
    {
        if (point != null) {
            g2d.setStroke(lineStroke);
            g2d.setPaint((isSelected) ? Color.WHITE : Color.DARK_GRAY);
            g2d.draw(ellipse);
        }
    }
}
