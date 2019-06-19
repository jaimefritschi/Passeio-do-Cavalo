
package br.com.danubio.apps;

import java.awt.Color;
import java.awt.Point;
import java.awt.geom.Rectangle2D;

public class Square extends Rectangle2D.Float implements SettingsObserver {
    private static final Color SELECTED_COLOR = new Color(222, 55, 11);
    
    private Point point;
    private Color color;
    private boolean isSelected;
    
    public Square(Point point) 
    {
        setPoint(point);
        isSelected = false;
    }
    
    public void setColor(Color color)
    {
        this.color = color;
    }
    
    public void setSelected(boolean is)
    {
        isSelected = is;
    }
    
    public Color getColor()
    {
        if (isSelected) 
            return SELECTED_COLOR;
        return color;
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
    
    public void updateObserver()
    {
        int amount = Settings.getSettings().getAmountPixels();
        setRect(point.x * amount, point.y * amount, amount, amount);
    }
    
    
}
