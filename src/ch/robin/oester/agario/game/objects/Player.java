package ch.robin.oester.agario.game.objects;

import java.awt.Graphics;
import java.awt.Point;

public class Player {
	
	private static final int START_SIZE = 10;
	private static final int START_SECURITY = 36;
	
	private World w;
	private Blub blub;
	
	public Player(World w) {
		this.w = w;
		this.blub = new Blub(w, w.getRandom().nextInt(w.getWidth() - 2 * START_SECURITY) + START_SECURITY, 
				w.getRandom().nextInt(w.getHeight() - 2 * START_SECURITY) + START_SECURITY, START_SIZE);
	}

	public void update(float timeSinceLastFrame, double mouseX, double mouseY) {
		blub.update(timeSinceLastFrame, mouseX, mouseY);
		if(blub.getPosX() > w.getWidth() - blub.getRadius()) {
			blub.setPosX(w.getWidth() - blub.getRadius());
		}
		if(blub.getPosX() < blub.getRadius()) {
			blub.setPosX(blub.getRadius());
		}
		
		if(blub.getPosY() > w.getHeight() - blub.getRadius()) {
			blub.setPosY(w.getHeight() - blub.getRadius());
		}
		if(blub.getPosY() < blub.getRadius()) {
			blub.setPosY(blub.getRadius());
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
