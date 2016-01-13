package actors.Weapons;

import actors.*;

public class Weapon extends Quad{

	protected int frameDuration;
	protected int size;
	
	public Weapon(float x, float y, int size, int frameDuration) {
		super(x, y, size, size, ShapeType.CIRCLE);
		this.size = size;
		this.frameDuration = frameDuration;
		// TODO Auto-generated constructor stub
	}
	
	public int getFrameDuration(){
		return frameDuration;
	}
	
	public void setFrameDuration(int frameDuration){
		this.frameDuration = frameDuration;
	}
}
