package ch.robin.oester.agario.game.objects;

import java.awt.Color;
import java.awt.Graphics;

public class Blub extends WorldObject {

	private static double MAX_SPEED = 1000.0;
	private static int LENGTH_SECURITY = 5;

	private double mass;
	
	public Blub(World w, double posX, double posY, double mass) {
		super(w, posX, posY);
		this.mass = mass;
	}

	public void update(float timeSinceLastFrame, double mouseX, double mouseY) {
		double dx = mouseX - posX;
		double dy = mouseY - posY;
		double length = Math.sqrt(dx * dx + dy * dy);
		
		if(length > LENGTH_SECURITY) {
			posX += dx / length * timeSinceLastFrame * (MAX_SPEED / (0.1 * getDrawRadius()));
			posY += dy / length * timeSinceLastFrame * (MAX_SPEED / (0.1 * getDrawRadius()));
		}
	}
	
	public void draw(Graphics canvas) {
		canvas.setColor(new Color(255, 195, 43));
		canvas.fillOval((int) (w.getCam().getXOnScreen(this) - getDrawRadius()), (int) (w.getCam().getYOnScreen(this) - getDrawRadius()), 
				(int) (2 * getDrawRadius()), (int) (2 * getDrawRadius()));
	}
	
	public boolean isInside(WorldObject p) {
		double dx = p.getPosX() - posX;
		double dy = p.getPosY() - posY;
		
		double length = Math.sqrt(dx * dx + dy * dy);
		
		if(length < getRadius()) {
			return true;
		}
		return false;
	}
	
	public double getRadius() {
		return Math.sqrt(mass / Math.PI) * World.PIXEL_PER_UNIT;
	}
	
	public double getDrawRadius() {
		return getRadius() / w.getZoom();
	}

	public void addMass(double amount) {
		mass += amount;
	}
	
	public double getMass() {
		return mass;
	}
}
