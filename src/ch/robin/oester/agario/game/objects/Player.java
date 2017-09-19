package ch.robin.oester.agario.game.objects;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public class Player {

	private List<Blub> blubs;
	
	public Player() {
		this.blubs = new ArrayList<>();
		this.blubs.add(new Blub(100, 100));
	}

	public void update(float timeSinceLastFrame, double mouseX, double mouseY) {
		for(Blub b : blubs) {
			b.update(timeSinceLastFrame, mouseX, mouseY);
		}
	}
	
	public void draw(Graphics canvas) {
		for(Blub b : blubs) {
			b.draw(canvas);
		}
	}
}
