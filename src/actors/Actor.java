package actors;

import java.util.ArrayList;

public class Actor {
	
	//HAVE "IF ACTIVE" METHOD SOMEWHERE TO SEE IF ENOUGH TIME HAS PASSED FOR AN ACTOR TO BE "WORKING"
	//EACH ACTOR WILL HAVE A "RATE OF FIRE," OR "ACTIVATIONS PER SECOND"
	
	public float x = 0;
	public float y = 0;
	public float vx = 0;
	public float vy = 0;
	public float maxVx = 5f;
	public float maxVy = 5f;
	public float accel = 1f;
	public float decel = 1f;
	public float [] colorOfActor = {0.5f, .5f, 1.0f, 1.0f};
	public int hardness = -10; //where -10 = no resistance
	public boolean isMovable = false;
	public float frictionCoef = .5f;
	protected float gravityConstant;
	protected MovementPattern movePattern;
	public ShapeType hitboxShape;
	
	public int width = 0;
	public int height = 0;
	
	public boolean noclip = false;
	
	protected long framecount;
	
	public Actor (float x, float y){
		this.x = x;
		this.y = y;
		
		framecount = 0;
	}
	
	public Actor (float x, float y, ShapeType hitboxShape){
		this.x = x;
		this.y = y;
		this.hitboxShape = hitboxShape;
		
		framecount = 0;
	}
	
	public void setColor (float r, float g, float b, float a){
		colorOfActor[0] = r;
		colorOfActor[1] = g;
		colorOfActor[2] = b;
		colorOfActor[3] = a;
	}
	
	public MovementPattern getMovePattern() {
		return movePattern;
	}
	
	public ShapeType getHitboxShape() {
		return hitboxShape;
	}

	public void setMovePattern(MovementPattern movePattern) {
		this.movePattern = movePattern;
	}
	
	public float getGravityConstant(){
		return this.gravityConstant;
	}
	
	public void setGravityConstant(float gravityConstant){
		this.gravityConstant = gravityConstant;
	}
	
	public void act(ArrayList<Actor> actorsOnScreen){
		//doesn't necessarily increase framecount by default
		//if frozen framecount will not be touched for the object
	}
	
	public boolean hasFocus(){
		return false;
	}
	
	public void destroy(ArrayList<Actor> actorsOnScreen){
		actorsOnScreen.remove(this);
	}
}
