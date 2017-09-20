package ch.robin.oester.agario.game.objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ch.robin.oester.agario.game.GamePanel;
import ch.robin.oester.agario.game.GameStarter;

public class World {
	
	private static final int LINE_SIZE = 64;
	private static final int POINT_SIZE = 15;
	private static final int FRAMES_PER_POINT = 5;
	
	private int width, height;
	
	private int linesX, linesY;
	
	private Random rmd;
	
	private Player player;
	private List<Point> points;
	
	private Camera cam;
	
	public World(int width, int height) {
		this.width = width;
		this.height = height;
		
		this.linesX = GamePanel.WIDTH / LINE_SIZE + 2;
		this.linesY = GamePanel.HEIGHT / LINE_SIZE + 2;
		
		this.rmd = new Random();
		
		this.player = new Player(this);
		this.points = new ArrayList<>();
		
		this.cam = new Camera(player, this);
	}
	
	public void update(float timeSinceLastFrame) {
		Point mouse = MouseInfo.getPointerInfo().getLocation();
		if(GameStarter.getFrame().isShowing()) {
			double x = mouse.getX() + cam.getPosX() - GameStarter.getFrame().getLocationOnScreen().getX() - GameStarter.getFrame().getInsets().left;
			double y = mouse.getY() + cam.getPosY() - GameStarter.getFrame().getLocationOnScreen().getY() - GameStarter.getFrame().getInsets().top;
			
			if(rmd.nextInt(FRAMES_PER_POINT) == 0) {
				points.add(new Point(rmd.nextInt(width - 2 * POINT_SIZE) + POINT_SIZE, rmd.nextInt(height - 2 * POINT_SIZE) + POINT_SIZE));
			}
			
			player.update(timeSinceLastFrame, x, y);
			cam.update();
		}
	}
	
	public void draw(Graphics canvas) {
		canvas.setColor(new Color(62, 62, 62));
		canvas.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
		
		int amountX = (int) cam.getPosX() / LINE_SIZE;
		int firstX = amountX * LINE_SIZE;
		int amountY = (int) cam.getPosY() / LINE_SIZE;
		int firstY = amountY * LINE_SIZE;
		
		canvas.setColor(new Color(235, 235, 235));
		for(int i = 0; i < linesX; i++) {
			canvas.drawLine((int) cam.getXOnScreen(firstX) + i * LINE_SIZE, (int) cam.getYOnScreen(firstY), 
					(int) cam.getXOnScreen(firstX) + i * LINE_SIZE, (int) cam.getYOnScreen(firstY) + linesY * LINE_SIZE);
		}
		for(int i = 0; i < linesY; i++) {
			canvas.drawLine((int) cam.getXOnScreen(firstX), (int) cam.getYOnScreen(firstY) + i * LINE_SIZE, 
					(int) cam.getXOnScreen(firstX) + linesX * LINE_SIZE, (int) cam.getYOnScreen(firstY) + i * LINE_SIZE);
		}
		
		canvas.setColor(new Color(43, 255, 251));
		for(Point point : points) {
			if(cam.isOnScreen(point.getX(), point.getY())) {
				canvas.fillOval((int) cam.getXOnScreen(point.getX() - POINT_SIZE / 2), (int) cam.getYOnScreen(point.getY() - POINT_SIZE / 2), POINT_SIZE, POINT_SIZE);
			}
		}
		
		player.draw(canvas);
	}
	
	public Camera getCam() {
		return cam;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public List<Point> getPoints() {
		return points;
	}
}
