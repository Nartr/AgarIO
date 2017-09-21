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
	
	public static final int PIXEL_PER_UNIT = 20;
	public static final double ZOOM = 0.75;
	
	private static final int LINE_SECURITY = 3;
	private static final int LINE_SIZE = 3;
	
	private static final int POINT_SIZE = 1;
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
		
		this.rmd = new Random();
		
		this.player = new Player(this);
		this.points = new ArrayList<>();
		
		this.cam = new Camera(player, this);
		
		updateLines();
	}
	
	private void updateLines() {
		linesX = (int) (GamePanel.WIDTH * ZOOM / (LINE_SIZE * PIXEL_PER_UNIT)) + LINE_SECURITY;
		linesY = (int) (GamePanel.HEIGHT * ZOOM / (LINE_SIZE * PIXEL_PER_UNIT)) + LINE_SECURITY;
	}
	
	public void update(float timeSinceLastFrame) {
		Point mouse = MouseInfo.getPointerInfo().getLocation();
		if(GameStarter.getFrame().isShowing()) {
			double x = (mouse.getX() - GameStarter.getFrame().getLocationOnScreen().getX() - GameStarter.getFrame().getInsets().left) * ZOOM
					+ cam.getPosX();
			double y = (mouse.getY() - GameStarter.getFrame().getLocationOnScreen().getY() - GameStarter.getFrame().getInsets().top) * ZOOM
					+ cam.getPosY();
			
			if(rmd.nextInt(FRAMES_PER_POINT) == 0) {
				points.add(new Point(rmd.nextInt(width - 2 * getDrawRadius()) + getDrawRadius(), 
						rmd.nextInt(height - 2 * getDrawRadius()) + getDrawRadius()));
			}
			
			player.update(timeSinceLastFrame, x, y);
			updateLines();
			cam.update();
		}
	}
	
	public void draw(Graphics canvas) {
		canvas.setColor(new Color(62, 62, 62));
		canvas.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
		
		int amountX = (int) (cam.getPosX() / (LINE_SIZE * PIXEL_PER_UNIT));
		int firstX = (int) (amountX * LINE_SIZE * PIXEL_PER_UNIT);
		int amountY = (int) (cam.getPosY() / (LINE_SIZE * PIXEL_PER_UNIT));
		int firstY = (int) (amountY * LINE_SIZE * PIXEL_PER_UNIT);
		
		canvas.setColor(new Color(235, 235, 235));
		for(int i = 0; i < linesX; i++) {
			canvas.drawLine((int) (cam.getXOnScreen(firstX) + i * LINE_SIZE * PIXEL_PER_UNIT / ZOOM), (int) cam.getYOnScreen(firstY), 
					(int) (cam.getXOnScreen(firstX) + i * LINE_SIZE * PIXEL_PER_UNIT / ZOOM), 
					(int) (cam.getYOnScreen(firstY) + linesY * LINE_SIZE * PIXEL_PER_UNIT / ZOOM));
		}
		for(int i = 0; i < linesY; i++) {
			canvas.drawLine((int) cam.getXOnScreen(firstX), (int) (cam.getYOnScreen(firstY) + i * LINE_SIZE * PIXEL_PER_UNIT / ZOOM), 
					(int) (cam.getXOnScreen(firstX) + linesX * LINE_SIZE * PIXEL_PER_UNIT / ZOOM), 
					(int) (cam.getYOnScreen(firstY) + i * LINE_SIZE * PIXEL_PER_UNIT / ZOOM));
		}
		
		canvas.setColor(new Color(43, 255, 251));
		for(Point point : points) {
			if(cam.isOnScreen(point.getX(), point.getY())) {
				canvas.fillOval((int) cam.getXOnScreen(point.getX()) - getDrawRadius(), (int) cam.getYOnScreen(point.getY()) - getDrawRadius(), 
						2 * getDrawRadius(), 2 * getDrawRadius());
			}
		}
		
		player.draw(canvas);
	}
	
	public int getDrawRadius() {
		return (int) (Math.sqrt(POINT_SIZE / Math.PI) * PIXEL_PER_UNIT / ZOOM);
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
	
	public Random getRandom() {
		return rmd;
	}
}
