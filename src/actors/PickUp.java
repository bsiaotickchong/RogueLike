package actors;

import java.util.ArrayList;

public class PickUp extends Quad{

	public PickUp(float x, float y, ShapeType hitboxShape, MovementPattern movePattern) {
		super(x, y, 50, 50, hitboxShape);
		
		this.vx = 0;
		this.vy = 0;
		this.movePattern = movePattern;
		this.hardness = 0;
		this.frictionCoef = .1f;
		
		if (movePattern.movementType == MovementType.GRAVITATE){
			gravityConstant = 50f; 
		}
	}
	
	//used to destroy from screen and also add effects to the player
	public void consume(ArrayList<Actor> actorsOnScreen){
		actorsOnScreen.remove(this);
		
		//add particles from destruction
		//add effects to player 1
	}
}
