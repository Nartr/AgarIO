package ch.robin.oester.agario.game;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import ch.robin.oester.agario.game.objects.World;

public class GamePanel extends JPanel implements Runnable {

	public static final int WIDTH = 800, HEIGHT = 600;
	
	private static final long serialVersionUID = 1L;
	
	private Image screen;
	private Graphics2D canvas;
	
	private Thread gameThread;
	private World world;

	public GamePanel() {
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setFocusable(true);
		requestFocus();
		addKeyListener(new KeyBoard());
		
		this.screen = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		this.canvas = (Graphics2D) screen.getGraphics();
		
		this.gameThread = new Thread(this);
		
		this.world = new World(2000, 2000);
		
		gameThread.start();
	}

	@Override
	public void run() {
		long lastFrame = System.currentTimeMillis();
		while(true) {
			long thisFrame = System.currentTimeMillis();
			float timeSinceLastFrame = (float) (thisFrame - lastFrame) / 1000;
			lastFrame = thisFrame;
			
			world.update(timeSinceLastFrame);
			world.draw(canvas);
			repaint();
			
			try {
				Thread.sleep(15);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		g.drawImage(screen, 0, 0, WIDTH, HEIGHT, null);
		g.dispose();
	}
}
