package ch.robin.oester.agario.game;

import java.awt.Insets;

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
	
	public static boolean isShowing() {
		return frame.isShowing();
	}
	
	public static double getPosX() {
		return frame.getLocationOnScreen().getX();
	}
	
	public static double getPosY() {
		return frame.getLocationOnScreen().getY();
	}

	public static Insets getInsets() {
		return frame.getInsets();
	}
}
