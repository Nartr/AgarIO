package ch.robin.oester.agario.game.objects;

import java.awt.Color;
import java.awt.Graphics;

public class Blub {
	
	private static double MAX_SPEED = 10000;

	private double posX, posY;
	private double mass;
	
	public Blub(double posX, double posY) {
		this.posX = posX;
		this.posY = posY;
		
		this.mass = 100;
	}
	
	public void draw(Graphics canvas) {
		canvas.setColor(Color.WHITE);
		canvas.fillOval((int) (posX - mass / 2), (int) (posY - mass / 2), (int) mass, (int) mass);
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
}
