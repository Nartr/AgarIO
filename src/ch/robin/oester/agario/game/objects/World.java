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
	
	private static final int LINE_SECURITY = 3;
	private static final int LINE_SIZE = 3;
	
	private static final int POINT_SIZE = 2;
	private static final int FRAMES_PER_POINT = 5;
	
	private final int width, height;
	
	private double zoom;
	private double maxZoom;
	
	private int linesX, linesY;
	
	private Random rmd;
	
	private Player player;
	private List<WorldPoint> points;
	
	private Camera cam;
	
	private Color[] colors = {new Color(85, 255, 50), new Color(255, 122, 45), new Color(45, 84, 255),
			new Color(34, 246, 249), new Color(249, 29, 235), new Color(255, 165, 63), new Color(247, 255, 38),
			new Color(38, 255, 211), new Color(200, 35, 255)};
	
	public World(int width, int height, double zoom) {
		this.width = width;
		this.height = height;
		
		this.zoom = zoom;
		this.maxZoom = Math.min((double) width / GamePanel.WIDTH, (double) height / GamePanel.HEIGHT);
		
		this.rmd = new Random();
		
		this.player = new Player(this);
		this.points = new ArrayList<>();
		
		this.cam = new Camera(player, this);
		
		updateLines();
	}
	
	private void updateLines() {
		linesX = (int) (GamePanel.WIDTH * zoom / (LINE_SIZE * PIXEL_PER_UNIT)) + LINE_SECURITY;
		linesY = (int) (GamePanel.HEIGHT * zoom / (LINE_SIZE * PIXEL_PER_UNIT)) + LINE_SECURITY;
	}
	
	public void update(float timeSinceLastFrame) {
		Point mouse = MouseInfo.getPointerInfo().getLocation();
		if(GameStarter.isShowing()) {
			double x = (mouse.getX() - GameStarter.getPosX() - GameStarter.getInsets().left) * zoom
					+ cam.getPosX();
			double y = (mouse.getY() - GameStarter.getPosY() - GameStarter.getInsets().top) * zoom
					+ cam.getPosY();
			
			if(rmd.nextInt(FRAMES_PER_POINT) == 0) {
				points.add(new WorldPoint(this, rmd.nextInt(width - 2 * getDrawRadius()) + getDrawRadius(), 
						rmd.nextInt(height - 2 * getDrawRadius()) + getDrawRadius(), rmd.nextInt(colors.length)));
			}
			
			player.update(timeSinceLastFrame, x, y);
			updateLines();
			cam.update();
		}
	}
	
	public void draw(Graphics canvas) {
		canvas.setColor(new Color(45, 45, 45));
		canvas.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
		
		int amountX = (int) (cam.getPosX() / (LINE_SIZE * PIXEL_PER_UNIT));
		int firstX = (int) (amountX * LINE_SIZE * PIXEL_PER_UNIT);
		int amountY = (int) (cam.getPosY() / (LINE_SIZE * PIXEL_PER_UNIT));
		int firstY = (int) (amountY * LINE_SIZE * PIXEL_PER_UNIT);
		
		canvas.setColor(new Color(100, 100, 100));
		for(int i = 0; i < linesX; i++) {
			canvas.drawLine((int) (cam.getXOnScreen(firstX) + i * LINE_SIZE * PIXEL_PER_UNIT / zoom), (int) cam.getYOnScreen(firstY), 
					(int) (cam.getXOnScreen(firstX) + i * LINE_SIZE * PIXEL_PER_UNIT / zoom), 
					(int) (cam.getYOnScreen(firstY) + linesY * LINE_SIZE * PIXEL_PER_UNIT / zoom));
		}
		for(int i = 0; i < linesY; i++) {
			canvas.drawLine((int) cam.getXOnScreen(firstX), (int) (cam.getYOnScreen(firstY) + i * LINE_SIZE * PIXEL_PER_UNIT / zoom), 
					(int) (cam.getXOnScreen(firstX) + linesX * LINE_SIZE * PIXEL_PER_UNIT / zoom), 
					(int) (cam.getYOnScreen(firstY) + i * LINE_SIZE * PIXEL_PER_UNIT / zoom));
		}
		
		for(WorldPoint point : points) {
			if(cam.isOnScreen(point.getPosX(), point.getPosY())) {
				canvas.setColor(colors[point.getColor()]);
				canvas.fillOval((int) cam.getXOnScreen(point.getPosX()) - getDrawRadius(), (int) cam.getYOnScreen(point.getPosY()) - getDrawRadius(), 
						2 * getDrawRadius(), 2 * getDrawRadius());
			}
		}
		
		player.draw(canvas);
	}
	
	private int getDrawRadius() {
		return (int) (Math.sqrt(POINT_SIZE / Math.PI) * World.PIXEL_PER_UNIT / zoom);
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
	
	public List<WorldPoint> getPoints() {
		return points;
	}
	
	public Random getRandom() {
		return rmd;
	}
	
	public double getZoom() {
		return zoom;
	}
	
	public void setZoom(double zoom) {
		this.zoom = zoom;
	}
	
	public double getMaxZoom() {
		return maxZoom;
	}
}
