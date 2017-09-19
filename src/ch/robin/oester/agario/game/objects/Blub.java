package ch.robin.oester.agario.game.objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class Blub extends WorldObject {

	private static double MAX_SPEED = 10000;

	private double mass = 100;
	
	public Blub(World w, double posX, double posY) {
		super(w, posX, posY);
		this.mass = 50;
	}
	
	public Blub(World w, double posX, double posY, int mass) {
		super(w, posX, posY);
		this.mass = mass;
	}
	
	public void draw(Graphics canvas) {
		canvas.setColor(new Color(255, 195, 43));
		canvas.fillOval((int) (w.getCam().getXOnScreen(this) - mass / 2), (int) (w.getCam().getYOnScreen(this) - mass / 2), (int) mass, (int) mass);
	}

	public void update(float timeSinceLastFrame, double mouseX, double mouseY) {
		double dx = mouseX - posX;
		double dy = mouseY - posY;
		double length = Math.sqrt(dx * dx + dy * dy);
		
		if(length > 2) {
			posX += dx / length * timeSinceLastFrame * (MAX_SPEED / mass);
			posY += dy / length * timeSinceLastFrame * (MAX_SPEED / mass);
		}
	}
	
	public boolean isInside(Point p) {
		double dx = p.getX() - posX;
		double dy = p.getY() - posY;
		
		double length = Math.sqrt(dx * dx + dy * dy);
		
		if(length < mass / 2) {
			return true;
		}
		return false;
	}
	
	public double getMass() {
		return mass;
	}

	public void addMass(double amount) {
		mass += amount;
	}
}
