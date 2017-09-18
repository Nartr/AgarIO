package ch.robin.oester.agario.game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyBoard implements KeyListener {

	private static boolean[] keys;
	
	static {
		KeyBoard.keys = new boolean[1024];
	}

	@Override
	public void keyPressed(KeyEvent e) {
		KeyBoard.keys[e.getKeyCode()] = true;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		KeyBoard.keys[e.getKeyCode()] = false;
	}

	@Override
	public void keyTyped(KeyEvent e) {}
	
	public static boolean isKeyPressed(int keyCode) {
		return KeyBoard.keys[keyCode];
	}
}
