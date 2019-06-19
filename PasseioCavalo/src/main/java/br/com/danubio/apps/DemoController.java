
package br.com.danubio.apps;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.Timer;

public class DemoController implements Controller, ActionListener {

    private List<Point> points;
    private Timer timer;
    private int index;
    
    
    private ChessBoard board;
    private Horse horse;
    private HorseRideView view;
    
    public DemoController(HorseRideView view)
    {
        timer = new Timer(1000, this);
        board = new ChessBoard();
        horse = new Horse();
        this.view = view;
    }
    
    public void setList(List<Point> list)
    {
        timer.stop();
        
        if (list != null) {
            board.refresh();
            points = list;
            index = 0;
            timer.start();
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent event)
    {
        if (index < points.size()) {
            Point point = points.get(index++);
            horse.setPoint(point, board);
            board.setSelected(point);
            view.repaint();
        } else {
            timer.stop();
            view.setController(view.getHorseRideController());
            view.repaint();
        }
    }
    
    @Override
    public void mouseMoved(Point point) 
    {
        
    }

    @Override
    public void mouseClicked(Point point) 
    {
        
    }

    @Override
    public void drawHorseRide(Graphics2D g2d) 
    {
        board.drawChessBoard(g2d);
        horse.drawHorse(g2d);
    }

}