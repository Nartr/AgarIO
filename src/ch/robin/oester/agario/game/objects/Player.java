package ch.robin.oester.agario.game.objects;

import java.awt.Graphics;
import java.awt.Point;

public class Player {

	private World w;
	private Blub blub;
	
	public Player(World w) {
		this.w = w;
		this.blub = new Blub(w, 100, 100);
	}

	public void update(float timeSinceLastFrame, double mouseX, double mouseY) {
		blub.update(timeSinceLastFrame, mouseX, mouseY);
		if(blub.getPosX() > w.getWidth() - blub.getMass() / 2) {
			blub.setPosX(w.getWidth() - blub.getMass() / 2);
		}
		if(blub.getPosX() < blub.getMass() / 2) {
			blub.setPosX(blub.getMass() / 2);
		}
		if(blub.getPosY() > w.getHeight() - blub.getMass() / 2) {
			blub.setPosY(w.getHeight() - blub.getMass() / 2);
		}
		if(blub.getPosY() < blub.getMass() / 2) {
			blub.setPosY(blub.getMass() / 2);
		}
		for(int i = 0; i < w.getPoints().size(); i++) {
			Point p = w.getPoints().get(i);
			if(w.getCam().isOnScreen(p.getX(), p.getY()) && blub.isInside(p)) {
				blub.addMass(0.1);
				w.getPoints().remove(i);
			}
		}
	}
	
	public void draw(Graphics canvas) {
		blub.draw(canvas);
	}
	
	public Blub getBlub() {
		return blub;
	}
}
