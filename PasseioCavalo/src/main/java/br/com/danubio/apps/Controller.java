
package br.com.danubio.apps;

import java.awt.Graphics2D;
import java.awt.Point;

public interface Controller {

    public void mouseMoved(Point point);
    public void mouseClicked(Point point);
    public void drawHorseRide(Graphics2D g2d);
    
}