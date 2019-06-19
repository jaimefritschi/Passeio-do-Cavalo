
package br.com.danubio.apps;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.util.Iterator;

public abstract class Piece extends Rectangle.Float implements SettingsObserver {

    public abstract Point getPoint();
    public abstract void setPoint(Point point, ChessBoard board);
    public abstract Movement[] getMovements();
    public abstract void move(Movement movement, ChessBoard board);
}
