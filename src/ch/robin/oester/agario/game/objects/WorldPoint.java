package ch.robin.oester.agario.game.objects;

public class WorldPoint extends WorldObject {
	
	private int c;

	public WorldPoint(World w, double posX, double posY, int colorid) {
		super(w, posX, posY);
		this.c = colorid;
	}
	
	public int getColor() {
		return c;
	}
}
