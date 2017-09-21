package ch.robin.oester.agario.game.objects;

import ch.robin.oester.agario.game.GamePanel;

public class Camera {
	
	private static final int RENDER_SECURITY = 50;
	
	private double posX, posY;
	
	private Player focus;
	private World w;

	public Camera(Player focus, World w) {
		this.focus = focus;
		this.w = w;
		
		update();
	}
	
	public void update() {
		if(focus.getBlub().getPosX() < GamePanel.WIDTH * w.getZoom() / 2) {
			this.posX = 0;
		} else if(focus.getBlub().getPosX() > w.getWidth() - GamePanel.WIDTH * w.getZoom() / 2) {
			this.posX = w.getWidth() - GamePanel.WIDTH * w.getZoom();
		} else {
			this.posX = focus.getBlub().getPosX() - GamePanel.WIDTH * w.getZoom() / 2;
		}
		
		if(focus.getBlub().getPosY() < GamePanel.HEIGHT * w.getZoom() / 2) {
			this.posY = 0;
		} else if(focus.getBlub().getPosY() > w.getHeight() - GamePanel.HEIGHT * w.getZoom() / 2) {
			this.posY = w.getHeight() - GamePanel.HEIGHT * w.getZoom();
		} else {
			this.posY = focus.getBlub().getPosY() - GamePanel.HEIGHT * w.getZoom() / 2;
		}
	}
	
	public boolean isOnScreen(double x, double y) {
		return x > posX - RENDER_SECURITY && x < posX + GamePanel.WIDTH * w.getZoom() + RENDER_SECURITY
				&& y > posY - RENDER_SECURITY && y < posY + GamePanel.HEIGHT * w.getZoom() + RENDER_SECURITY;
	}
	
	public double getXOnScreen(double worldX) {
		return (worldX - posX) / w.getZoom();
	}
	
	public double getYOnScreen(double worldY) {
		return (worldY - posY) / w.getZoom();
	}
	
	public boolean isOnScreen(WorldObject w) {
		return isOnScreen(w.getPosX(), w.getPosY());
	}
	
	public double getXOnScreen(WorldObject w) {
		return getXOnScreen(w.getPosX());
	}
	
	public double getYOnScreen(WorldObject w) {
		return getYOnScreen(w.getPosY());
	}
	
	public double getPosX() {
		return posX;
	}
	
	public double getPosY() {
		return posY;
	}
}
