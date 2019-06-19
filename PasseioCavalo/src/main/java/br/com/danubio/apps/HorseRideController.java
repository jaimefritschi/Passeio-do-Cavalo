
package br.com.danubio.apps;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.List;

public class HorseRideController implements Controller {

    private HorseRideAnalyzer horseAnalyzer;
    private ChoiceSquareAnalyzer choiceAnalyzer;
    private Analyzer analyzer;
    
    private HorseRideView view;
    
    private Horse horse;
    private ChessBoard board;
    
    public HorseRideController(HorseRideView view) 
    {
        this.view = view;
        
        board = new ChessBoard();
        horse = new Horse();
        
        horseAnalyzer = new HorseRideAnalyzer(this);
        choiceAnalyzer = new ChoiceSquareAnalyzer(this);
        analyzer = choiceAnalyzer;
    }
    
    public Horse getHorse()
    {
        return horse;
    }
    
    public ChessBoard getChessBoard()
    {
        return board;
    }
    
    public void setAnalyzer(Analyzer analyzer)
    {
        this.analyzer = analyzer;
    }
    
    public HorseRideAnalyzer getHorseRideAnalyzer()
    {
        return horseAnalyzer;
    }
    
    public ChoiceSquareAnalyzer getChoiceSquareAnalyzer()
    {
        return choiceAnalyzer;
    }
    
    public void mouseMoved(Point point)
    {
        analyzer.selectMovement(new Point2D.Float(point.x, point.y));
        view.repaint();
    }
    
    public void mouseClicked(Point point)
    {
        analyzer.movePiece(new Point2D.Float(point.x, point.y));
        view.repaint();
    }
    
    public void drawHorseRide(Graphics2D g2d)
    {
        analyzer.drawAnalyzer(g2d);
    }
    
    public void saveHorseRide(List<Point> points)
    {
        view.saveHorseRide(points);
    }
}
