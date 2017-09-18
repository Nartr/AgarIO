package ch.robin.oester.agario.game;

import javax.swing.JFrame;

public class GameStarter {
	
	public static void main(String[] args) {
		JFrame frame = new JFrame("AgarIO");
		frame.setContentPane(new GamePanel(frame));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}
