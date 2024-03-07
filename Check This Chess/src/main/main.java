package main;

import javax.swing.*;

import java.awt.*;

public class main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		JFrame frame = new JFrame();
		frame.getContentPane().setBackground(new Color(118,150,86));
		frame.setLayout(new GridBagLayout());
		frame.setMinimumSize(new Dimension(1000, 1000));
		frame.setLocationRelativeTo(null);

		Board board = new Board();
		frame.add(board);

		frame.setVisible(true);

	}

}
