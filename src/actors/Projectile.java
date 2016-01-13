package actors;

import java.util.ArrayList;

public class Projectile extends Quad{

	//MovementPattern movePattern;
	
	protected int damageGiven = 1;
	protected int lifeTime;
	protected int lifeTimeCounter;
	
	public Projectile(float x, float y, int width, int height, float vx, float vy, ShapeType hitboxShape, MovementPattern movePattern, int lifeTime) {
		super(x, y, width, height, hitboxShape);
		
		this.vx = vx;
		this.vy = vy;
		this.movePattern = movePattern;
		this.hardness = 0;
		
		if (movePattern.movementType == MovementType.GRAVITATE){
			gravityConstant = 50f; 
		}
		
		this.lifeTime = lifeTime;
		lifeTimeCounter = 0;
	}
	
	public void destroy(ArrayList<Actor> actorsOnScreen){
		actorsOnScreen.remove(this);
		
		//add particles from destruction
	}
	
	public void damage(Actor actor){
		if (actor instanceof Player1){
			((Player1) actor).damageHealth(damageGiven);
		}
	}
	
	public void act(ArrayList<Actor> actorsOnScreen){
		//doesn't necessarily increase framecount by default
		//if frozen framecount will not be touched for the object
		
		lifeTimeCounter++;
		
		if (lifeTimeCounter == lifeTime){
			destroy(actorsOnScreen);
		}
	}
}
