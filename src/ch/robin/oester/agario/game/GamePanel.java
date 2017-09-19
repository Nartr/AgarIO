package ch.robin.oester.agario.game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import ch.robin.oester.agario.game.objects.Player;

public class GamePanel extends JPanel implements Runnable {

	public static final int WIDTH = 800, HEIGHT = 600;
	
	private static final long serialVersionUID = 1L;
	
	private JFrame frame;
	private Image screen;
	private Graphics2D canvas;
	private Thread gameThread;
	private List<Player> players;

	public GamePanel(JFrame frame) {
		this.frame = frame;
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		this.setFocusable(true);
		this.requestFocus();
		this.addKeyListener(new KeyBoard());
		
		this.screen = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		this.canvas = (Graphics2D) screen.getGraphics();
		
		this.gameThread = new Thread(this);
		
		this.players = new ArrayList<>();
		players.add(new Player());
		
		gameThread.start();
	}

	@Override
	public void run() {
		while(true) {
			long lastFrame = System.currentTimeMillis();
			while(true) {
				long thisFrame = System.currentTimeMillis();
				float timeSinceLastFrame = (float) (thisFrame - lastFrame) / 1000;
				lastFrame = thisFrame;
				
				update(timeSinceLastFrame);
				draw();
				
				try {
					Thread.sleep(15);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private void update(float timeSinceLastFrame) {
		Point mouse = MouseInfo.getPointerInfo().getLocation();
		if(frame.isShowing()) {
			double x = mouse.getX() - frame.getLocationOnScreen().getX() - frame.getInsets().left;
			double y = mouse.getY() - frame.getLocationOnScreen().getY() - frame.getInsets().top;
			
			for(Player p : players) {
				p.update(timeSinceLastFrame, x, y);
			}
		}
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		g.drawImage(screen, 0, 0, WIDTH, HEIGHT, null);
		g.dispose();
	}
	
	private void draw() {
		canvas.setColor(Color.BLACK);
		canvas.fillRect(0, 0, WIDTH, HEIGHT);
		for(Player p : players) {
			p.draw(canvas);
		}
		repaint();
	}
}
