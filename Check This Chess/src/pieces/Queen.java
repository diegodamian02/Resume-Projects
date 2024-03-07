package pieces;

import java.awt.image.BufferedImage;

import main.Board;
public class Queen extends Piece{

	public Queen(Board board, int col, int row, boolean isWhite) {
		super(board);
		this.col = col;
		this.row = row;
		this.xPos = col * board.tilesize;
		this.yPos = row * board.tilesize;
		
		this.isWhite = isWhite;
		this.name = "Queen";
		
		this.sprite = sheet.getSubimage(1 * sheetScale, isWhite ? 0 : sheetScale, sheetScale, sheetScale).getScaledInstance(board.tilesize, board.tilesize, BufferedImage.SCALE_SMOOTH);
		
		
	}
	
	public boolean isValidMovement(int col, int row) {
		
		return Math.abs(this.col - col) == Math.abs(this.row - row) || this.col == col || this.row == row;
		
	}
	
	
public boolean moveCollidesWithPiece(int col, int row) {
		
	if(Math.abs(this.col -col) == Math.abs(this.row - row)){
		//up left
		if(this.col > col && this.row > row) {
			for(int i = 1; i < Math.abs(this.col - col); i++) {
				if(board.getPiece(this.col - i, this.row - i) != null) {
					return true;
				}
			}
		}
		
		//up right
				if(this.col < col && this.row > row) {
					for(int i = 1; i < Math.abs(this.col - col); i++) {
						if(board.getPiece(this.col + i, this.row - i) != null) {
							return true;
						}
					}
				}
				
		//down left
				if(this.col > col && this.row < row) {
					for(int i = 1; i < Math.abs(this.col - col); i++) {
						if(board.getPiece(this.col - i, this.row + i) != null) {
							return true;
						}
					}
				}	
				
		//up right
				if(this.col < col && this.row < row) {
					for(int i = 1; i < Math.abs(this.col - col); i++) {
						if(board.getPiece(this.col + i, this.row + i) != null) {
							return true;
						}
					}
				}

	}
	else {
		//left
		if(this.col > col) {
			for (int i = this.col -1; i > col; i--) {
				if(board.getPiece(i, this.row) != null)
					return true;
			}
		}
		
		//right
		if(this.row  < col) {
			for(int i = this.col  + 1; i <  col; i++) {
				if(board.getPiece(this.col, i) != null)
					return true;
			}
		}
		
		//up
		if(this.row > row) {
			for(int i = this.row - 1; i > row; i--) {
				if(board.getPiece(this.col, i) != null)
					return true;
			}
		}
		
		if(this.row < row) {
			for(int i = this.row + 1; i < row; i++) {
				if(board.getPiece(this.row, i) != null)
					return true;
			}
		}
	}
	
		return false;
	}
}