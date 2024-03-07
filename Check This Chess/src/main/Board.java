package main;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import pieces.*;

public class Board extends JPanel {

	public int tilesize = 85;

	int cols = 8;
	int rows = 8;
	
	ArrayList<Piece> pieceList = new ArrayList<>(); 
	
	public Piece selectedPiece;
	
	Input input = new Input(this);
	
	public CheckScanner checkScanner = new CheckScanner(this);
	
	public int enPassantTile = -1;

	public Board() {
		this.setPreferredSize(new Dimension(cols * tilesize, rows * tilesize));
		
		this.addMouseListener(input);
		this.addMouseMotionListener(input);
		
		addPieces();
	}
	
	
	public Piece getPiece(int col, int row) {
		
		for(Piece piece : pieceList) {
			if(piece.col == col && piece.row == row) {
				return piece;
			}
		}
		return null;
		
	}
	
	public void makeMove(Move move) {
		
		if(move.piece.name.equals("Pawn")) {
			movePawn(move);
		} 
		
		else if(move.piece.name.equals("King")){
			
			moveKing((move));
		}
		
		move.piece.col = move.newCol;
		move.piece.row = move.newRow;
		
		move.piece.xPos = move.newCol * tilesize;
		move.piece.yPos = move.newRow * tilesize;
		
		move.piece.isFirstMove = false;
		
		
		
		capture(move.capture);
		
	}
	
	private void moveKing(Move move) {
	
		if(Math.abs(move.piece.col - move.newCol) == 2) {
			Piece rook;
			
			if(move.piece.col < move.newCol) {
				
				rook = getPiece(7, move.piece.row);
				rook.col = 5;
			} else {
				rook = getPiece(0, move.piece.row);
				rook.col = 3;
			}
			
			rook.xPos = rook.col * tilesize;
			
		}
	}
	
	private void movePawn(Move move) {
		
		// en passant
		int colorIndex = move.piece.isWhite ? 1 : -1;
		
		if(getTileNum(move.newCol, move.newRow) == enPassantTile) {
			
			move.capture = getPiece(move.newCol, move.newRow + colorIndex);
			
		}
		
		if(Math.abs(move.piece.row - move.newRow) == 2) {
			
			enPassantTile = getTileNum(move.newCol, move.newRow + colorIndex);
		} else {
			enPassantTile = -1;
		}
		
		
		//promotions
		colorIndex = move.piece.isWhite ? 0  : 7;
		if(move.newRow == colorIndex) {
			promotePawn(move);
		}
		

	}
	
	
	private void promotePawn(Move move)
	{
		pieceList.add(new Queen(this, move.newCol, move.newRow, move.piece.isWhite));
		capture(move.piece);
	}
	
	
	public void capture(Piece piece) {
		
		pieceList.remove(piece);
	}
	
	
	
	public boolean isValidMove(Move move) {
		
		if(sameTeam(move.piece, move.capture))
		return false;
		
		if(!move.piece.isValidMovement(move.newCol, move.newRow)) {
			return false;
		}
		
		if(move.piece.moveCollidesWithPiece(move.newCol, move.newRow)) {
			return false;
		}
		
		/*
		if (checkScanner.isKingChecked(move.newRow, move.newCol, )) {
			return false;
		}
	
		*/
		
	return true;
}
	
	public boolean sameTeam(Piece w, Piece b) {
		
		if(w == null || b == null) {
			return false;
		}
		return w.isWhite == b.isWhite;
	}
	
	public int getTileNum(int col, int row) {
		
		return row * rows + col;
	}
	
	
	
	Piece findKing (boolean isWhite) {
		for(Piece piece : pieceList) {
			if(isWhite == piece.isWhite && piece.name.equals("King")) {
				return piece;
			}
		}
		
		return null;
	}
	
	
	public void addPieces() {
		
		//Adding the black pieces
		
		pieceList.add(new Knight(this, 1, 0, false));
		pieceList.add(new Knight(this, 6, 0, false));
		pieceList.add(new bishop(this, 2, 0, false));
		pieceList.add(new bishop(this, 5, 0, false));
		pieceList.add(new Rook(this, 0, 0, false));
		pieceList.add(new Rook(this, 7, 0, false));
		pieceList.add(new King(this, 3, 0, false));
		pieceList.add(new Queen(this, 4, 0, false));
		
		pieceList.add(new Pawn(this, 0, 1, false));
		pieceList.add(new Pawn(this, 1, 1, false));
		pieceList.add(new Pawn(this, 2, 1, false));
		pieceList.add(new Pawn(this, 3, 1, false));
		pieceList.add(new Pawn(this, 4, 1, false));
		pieceList.add(new Pawn(this, 5, 1, false));
		pieceList.add(new Pawn(this, 6, 1, false));
		pieceList.add(new Pawn(this, 7, 1, false));
		
		//Adding the white pieces
		
		pieceList.add(new Knight(this, 1, 7, true));
		pieceList.add(new Knight(this, 6, 7, true));
		pieceList.add(new bishop(this, 2, 7, true));
		pieceList.add(new bishop(this, 5, 7, true));
		pieceList.add(new Rook(this, 0, 7, true));
		pieceList.add(new Rook(this, 7, 7, true));
		pieceList.add(new King(this, 3, 7, true));
		pieceList.add(new Queen(this, 4, 7, true));
		
		pieceList.add(new Pawn(this, 0, 6, true));
		pieceList.add(new Pawn(this, 1, 6, true));
		pieceList.add(new Pawn(this, 2, 6, true));
		pieceList.add(new Pawn(this, 3, 6, true));
		pieceList.add(new Pawn(this, 4, 6, true));
		pieceList.add(new Pawn(this, 5, 6, true));
		pieceList.add(new Pawn(this, 6, 6, true));
		pieceList.add(new Pawn(this, 7, 6, true));
	}

	public void paintComponent(Graphics g) {

		Graphics2D g2d = (Graphics2D) g;

		for (int i = 0; i < rows; i++)
			for (int j = 0; j < cols; j++) {
				g2d.setColor((j + i) % 2 == 0 ? new Color(238,238,210) : new Color(167, 181, 61));
				g2d.fillRect(j * tilesize, i * tilesize, tilesize, tilesize);
			}
		
		
		//highlighting available tiles >.<
		
		if(selectedPiece != null) {
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < cols; j++) {
				
				if(isValidMove(new Move(this, selectedPiece, j, i))) {
				g2d.setColor(new Color(207, 226, 243, 180));
				g2d.fillRect(j * tilesize, i * tilesize, tilesize, tilesize);
				
				}
				
			}
		}
		
	}
		
		for(Piece piece: pieceList ) {
			piece.paint(g2d);
		}

	}

}
