
package br.com.danubio.apps;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class HorseRideAnalyzer extends Analyzer {
    
    private HorseRideController controller;
    private ChessBoard board;
    private Horse horse;
    
    private Movement activated;
    
    private List<Point> avaiableList;
    private List<Point> rideList;
    
    public HorseRideAnalyzer(HorseRideController controller)
    {
        this.controller = controller;
        this.board = this.controller.getChessBoard();
        this.horse = this.controller.getHorse();
        
        rideList = new ArrayList<Point>();
        avaiableList = new ArrayList<Point>();
        refresh();
    }
    
    private void refresh()
    {
        rideList.clear();
        avaiableList.clear();
        for(Square[] squares : this.board.getSquares()) {
            for (Square square : squares) {
                avaiableList.add(square.getPoint());
            }
        }
    }
    
    public void setMovement(Movement movement)
    {
        this.activated = movement;
    }
    
    @Override
    public void selectMovement(Point2D point)
    {
        for(Movement movement : horse.getMovements()) {
            if (movement.getPoint() != null && movement.contains(point)) {
                if (activated != null) {
                    activated.setSelected(false);
                }
                
                activated = movement;
                activated.setSelected(true);
                break;
            }
        }
    }
    
    @Override
    public void movePiece(Point2D point)
    {
        if (activated.contains(point)) {
            horse.move(activated, board);
            board.setSelected(horse.getPoint());
            
            rideList.add(horse.getPoint());
            avaiableList.remove(horse.getPoint());
            
            if (avaiableList.isEmpty()) {
                controller.saveHorseRide(rideList);
                refresh();
                board.refresh();
                controller.setAnalyzer(controller.getChoiceSquareAnalyzer());
            }
            
        }
    }
    
    private void activeMovement()
    {
        activated = null;
        for (Movement movement : horse.getMovements()) {
            if (movement.getPoint() != null && movement.isSelected()) {
                activated = movement;
                break;
            }
        }
    }
    
    @Override
    public void drawAnalyzer(Graphics2D g2d)
    {
        board.drawChessBoard(g2d);
        horse.drawHorse(g2d);
    }
}