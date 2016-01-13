package actors;

public class MovementPattern {
	
	float radius = 0;
	float focusX; //where the actor either starts or bases its movement off of
	float focusY;
	Actor actorFocus;
	public MovementType movementType;
	
	public MovementPattern (MovementType movementType){
		this.movementType = movementType;
	}
	
	public MovementPattern (MovementType movementType, Actor actorFocus){
		this.actorFocus = actorFocus;
		this.movementType = movementType;
	}
	
	public MovementPattern (MovementType movementType, int focusX, int focusY){
		this.focusX = focusX;
		this.focusY = focusY;
		this.movementType = movementType;
	}
	
	public MovementPattern (MovementType movementType, float focusX, float focusY, float radius){
		this.radius = radius;
		this.focusX = focusX;
		this.focusY = focusY;
		this.movementType = movementType;
	}
	
	public void setActorFocus(Actor actorFocus){
		this.actorFocus = actorFocus;
	}
	
	public Actor getActorFocus(){
		return actorFocus;
	}
	
	public void setRadius(float radius){
		this.radius = radius;
	}
	
	public float getRadius(){
		return radius;
	}
	
	public void setFocus(float focusX, float focusY){
		try {
			actorFocus.x = focusX;
			actorFocus.y = focusY;
		} catch (NullPointerException e){
			this.focusX = focusX;
			this.focusY = focusY;
		}
	}
	
	public float getFocusX(){
		return actorFocus.x;
	}
	
	public float getFocusY(){
		return actorFocus.y;
	}
	
	public MovementType getType(){
		return movementType;
	}
	
	public boolean hasFocus(){
		return actorFocus != null; //true if focus exists, false if it equals null/doesn't exist
	}
}
