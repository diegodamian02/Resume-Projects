package main;

import pieces.*;


public class CheckScanner {
	
	Board board;
	
	public CheckScanner(Board board) {
		
		this.board = board;
	}
	
	public boolean isKingChecked(int col, int row, boolean isWhite) {
		
		Piece king = board.findKing(isWhite);
		
		assert king != null;
		
		int kingCol = king.col;
		int kingRow = king.row;
		
		
		if(board.selectedPiece != null && board.selectedPiece.name.equals("King")) {
			kingCol = col;
			kingRow = row;
		}
		
		return  hitByRook(col, row, king, kingCol, kingRow, 0, 1) || //up
				hitByRook(col, row, king, kingCol, kingRow, 1, 0) || //right
				hitByRook(col, row, king, kingCol, kingRow, 0, -1) || //down
				hitByRook(col, row, king, kingCol, kingRow, -1, 0) ||  //left
				
				hitByBishop(col, row, king, kingCol, kingRow, -1, -1) || //up left
				hitByBishop(col, row, king, kingCol, kingRow, 1, -1) || //up right
				hitByBishop(col, row, king, kingCol, kingRow, 1, 1) || //down right
				hitByBishop(col, row, king, kingCol, kingRow, -1, 1) || //down left
				
				hitByKnight(col, row,king, kingCol, kingRow) ||
				hitByPawn(col, row, king, kingCol, kingRow) ||
				hitByKing(king, kingCol, kingRow);
	
	}

	
	
	private boolean hitByRook(int col, int row, Piece king, int kingCol, int kingRow, int colVal, int rowVal) {
		
		for(int i = 1; i < 8; i++) {
			if(kingCol + (i * colVal) == col && kingRow + (i * rowVal) == row ) {
				break;
			}
			
			Piece piece = board.getPiece(kingCol + (i *  colVal), kingRow + (i * rowVal));
			
			if(piece != null && piece != board.selectedPiece ) {
				
				if(!board.sameTeam(piece, king) && (piece.name.equals("Rook") || piece.name.equals("Queen"))) {
					return true;
				}
				
				break;
			}
			
		}
		
		return false;
	}
	
	
private boolean hitByBishop(int col, int row, Piece king, int kingCol, int kingRow, int colVal, int rowVal) {
		
		for(int i = 1; i < 8; i++) {
			if(kingCol - (i * colVal) == col && kingRow - (i * rowVal) == row ) {
				break;
			}
			
			Piece piece = board.getPiece(kingCol - (i *  colVal), kingRow - (i * rowVal));
			
			if(piece != null && piece != board.selectedPiece ) {
				
				if(!board.sameTeam(piece, king) && (piece.name.equals("Bishop") || piece.name.equals("Queen"))) {
					return true;
				}
				
				break;
			}
			
		}
		
		return false;
	}
	
	private boolean hitByKnight(int col, int row, Piece king, int kingCol, int kingRow) {
		return  checkKnight(board.getPiece(kingCol - 1,  kingRow -2), king, col, row ) ||
				checkKnight(board.getPiece(kingCol + 1,  kingRow -2), king, col, row ) ||
				checkKnight(board.getPiece(kingCol + 2,  kingRow -1), king, col, row ) ||
				checkKnight(board.getPiece(kingCol + 2,  kingRow +1), king, col, row ) ||
				checkKnight(board.getPiece(kingCol + 1,  kingRow +2), king, col, row ) ||
				checkKnight(board.getPiece(kingCol - 1,  kingRow +2), king, col, row ) ||
				checkKnight(board.getPiece(kingCol - 2,  kingRow +1), king, col, row ) ||
				checkKnight(board.getPiece(kingCol - 2,  kingRow -1), king, col, row );
		
	}
	
	private boolean checkKnight(Piece p, Piece k, int col, int row) {
		return p != null && !board.sameTeam(p, k) && p.name.equals("Knight") && !(p.col == col && p.row == row);
	}
	
	private boolean hitByKing(Piece king, int kingCol, int kingRow) {
		
		return  checKing(board.getPiece(kingCol - 1, kingRow - 1), king) ||
				checKing(board.getPiece(kingCol + 1, kingRow - 1), king) ||
				checKing(board.getPiece(kingCol, kingRow - 1), king) ||
				checKing(board.getPiece(kingCol - 1, kingRow), king) ||
				checKing(board.getPiece(kingCol + 1, kingRow), king) ||
				checKing(board.getPiece(kingCol - 1, kingRow + 1), king) ||
				checKing(board.getPiece(kingCol + 1, kingRow + 1), king) ||
				checKing(board.getPiece(kingCol, kingRow + 1), king);
		
	}
	
	private boolean checKing(Piece p, Piece k) {
		
		return p != null && !board.sameTeam(p, k) && p.name.equals("King");
	}
	
	private boolean hitByPawn(int col, int row, Piece king, int kingCol, int kingRow) {
		
	int colorVal = king.isWhite ?  -1 : 1;
	return checkPawn(board.getPiece(kingCol + 1, kingRow + colorVal), king, col, row) ||
			checkPawn(board.getPiece(kingCol -1, kingRow + colorVal), king, col, row);
	}
	
	private boolean checkPawn(Piece p, Piece k, int col, int row) {
		
		return p != null && !board.sameTeam(p, k) && p.name.equals("Pawn");
	}
	
	public boolean noValidMoves(boolean isWhite) {
		Piece king = board.findKing(isWhite);
		
		if(king != null) {
			for(Piece piece : board.pieceList) {
				board.selectedPiece = piece == king ? king : null;
				
				if(board.sameTeam(piece, king)) {
					for(int r = 0; r < board.rows; r ++) {
						for(int c = 0; c < board.cols; c++) {
							Piece pieceXY = board.getPiece(c, r);
							if(piece.canMakeMove(pieceXY, c, r)) {
								return false;
							}
						}
					}
				}
			}
		}
		
		return true;
		
	}
}
