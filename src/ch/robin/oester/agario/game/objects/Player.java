package ch.robin.oester.agario.game.objects;

import java.awt.Graphics;

import ch.robin.oester.agario.game.GamePanel;

public class Player {
	
	private static final int START_SIZE = 10;
	private static final int START_SECURITY = 36;
	private static final double MASS_PER_POINT = 0.1;
	private static final double ZOOM_LAMBDA = 2.5;
	
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
		
		double zoom = Math.log10(blub.getMass()) / ZOOM_LAMBDA + GamePanel.INIT_ZOOM;
		w.setZoom(Math.min(w.getMaxZoom(), zoom));
		
		for(int i = 0; i < w.getPoints().size(); i++) {
			WorldPoint p = w.getPoints().get(i);
			if(w.getCam().isOnScreen(p.getPosX(), p.getPosY()) && blub.isInside(p)) {
				blub.addMass(MASS_PER_POINT);
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
