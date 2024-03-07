package main;


import java.awt.*;

import java.awt.event.*;
import pieces.*;

public class Input extends MouseAdapter implements MouseListener, MouseMotionListener {

	Board board;
	
	public Input(Board board) {
		this.board = board;
	}
	
	
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	int col = e.getX() / board.tilesize;
	int row = e.getY() / board.tilesize;
	
	Piece pieceXY = board.getPiece(col, row);
	
	if(pieceXY != null) {
		board.selectedPiece = pieceXY;
	}
	
	
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
		if(board.selectedPiece != null) {
			
			board.selectedPiece.xPos = e.getX() - board.tilesize / 2;
			board.selectedPiece.yPos = e.getY() - board.tilesize / 2;
			
			board.repaint();
		}
	}


	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
		int col = e.getX() / board.tilesize;
		int row = e.getY() / board.tilesize;
		
		if(board.selectedPiece != null) {
			Move move = new Move(board, board.selectedPiece, col, row);
			
			if(board.isValidMove(move)) {
				board.makeMove(move);
				
			} else {
				board.selectedPiece.xPos = board.selectedPiece.col * board.tilesize;
				board.selectedPiece.yPos = board.selectedPiece.row * board.tilesize;
			}
		}
		
		board.selectedPiece = null;
		board.repaint();
	}

}
