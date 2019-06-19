
package br.com.danubio.apps;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;

public class ChoiceSquareAnalyzer extends Analyzer {
    private HorseRideController controller;
    
    private ChessBoard board;
    private Movement activated;
    
    public ChoiceSquareAnalyzer(HorseRideController controller)
    {
        this.controller = controller;
        this.board = this.controller.getChessBoard();
        this.activated = new Movement(0, 0);
        this.activated.setSelected(true);
    }

    @Override
    public void selectMovement(Point2D point) 
    {
        for (Square[] squares : board.getSquares()) {
            for (Square square : squares) {
                if (!activated.contains(point)) {
                    if (square.contains(point)) {
                        changeSquare(square);
                        break;
                    }
                } else {
                    break;
                }
            }
        }
    }
    
    private void changeSquare(Square square) 
    {
        activated.setPoint(square.getPoint());
    }

    @Override
    public void movePiece(Point2D point) 
    {
        if (activated.contains(point)) {
            HorseRideAnalyzer horseAnalyzer = controller.getHorseRideAnalyzer();
            controller.setAnalyzer(horseAnalyzer);
            horseAnalyzer.setMovement(activated);
            horseAnalyzer.movePiece(point);
            activated.setSelected(true);
        }
    }

    @Override
    public void drawAnalyzer(Graphics2D g2d)
    {
        board.drawChessBoard(g2d);
        activated.drawMovement(g2d);
    }
}
