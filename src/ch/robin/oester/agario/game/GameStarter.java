package ch.robin.oester.agario.game;

import javax.swing.JFrame;

public class GameStarter {
	
	private static JFrame frame;
	
	public static void main(String[] args) {
		GameStarter.frame = new JFrame("AgarIO");
		frame.setContentPane(new GamePanel());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	public static JFrame getFrame() {
		return frame;
	}
}
