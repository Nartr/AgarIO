package ch.robin.oester.agario.game.objects;

public abstract class WorldObject {

	protected World w;
	protected double posX;
	protected double posY;
	
	public WorldObject(World w, double posX, double posY) {
		this.w = w;
		this.posX = posX;
		this.posY = posY;
	}
	
	public double getPosX() {
		return posX;
	}
	
	public double getPosY() {
		return posY;
	}
	
	public void setPosX(double posX) {
		this.posX = posX;
	}
	
	public void setPosY(double posY) {
		this.posY = posY;
	}
}
