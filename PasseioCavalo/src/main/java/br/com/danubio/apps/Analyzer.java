
package br.com.danubio.apps;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;

public abstract class Analyzer {

    public abstract void selectMovement(Point2D point);
    public abstract void movePiece(Point2D point);
    public abstract void drawAnalyzer(Graphics2D g2d);
}
