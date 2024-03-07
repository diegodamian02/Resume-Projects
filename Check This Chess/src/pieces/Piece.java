package pieces;

import main.Board;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.awt.*;

public class Piece {
	
	Image sprite;
	Board board; 
	
	public int col, row;
	public int  xPos, yPos;
	
	public boolean isWhite;
	public String name;
	public int value;
	
	public boolean isFirstMove = true;
	
	
	BufferedImage sheet;
	
	
	{
		try {
			sheet = ImageIO.read(ClassLoader.getSystemResourceAsStream("res/pieces.png"));
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	protected int sheetScale = sheet.getWidth()/6;
	
	
	public Piece (Board board) {
		this.board = board;
	}
	 
	
	public boolean isValidMovement(int col, int row) {
		return true;
	}
	
	public boolean moveCollidesWithPiece(int col, int row) {
		
		return false;
		
	}
	
	public boolean inCheck(int col, int row) {
		return board.checkScanner.isKingChecked(col, row, this.isWhite);
	}
	
	public boolean canMakeMove(Piece pieceXY, int col, int row) {
		
		return isValidMovement(col, row) &&  (pieceXY == null || !board.sameTeam(pieceXY, this) && !moveCollidesWithPiece(col, row) && !inCheck(col, row));
	}
	public void paint(Graphics2D g2d) {
		
		g2d.drawImage(sprite, xPos, yPos, null);
	}

}
