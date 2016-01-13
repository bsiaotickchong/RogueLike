package defaultPackage;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import actors.*;

@SuppressWarnings("unused")
public class LogicEngine {
	
	public boolean isPositive(float num){
		return Math.abs(num) == num;
	}
	public boolean isMoving(Actor actor){
		if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
			return true;
		}
		else if (actor.vy != 0)
			return true;
		else 
			return false;
	}
	
	//returns true if colliding via circle collision detection for now
	public static boolean isColliding(Actor actor1, Actor actor2){
		
		//if one actor is Quad and other is Line...
		//System.out.print(distBetweenCenters(actor1, actor2)+ " | " + ((Quad)actor1).radius + ((Quad)actor2).radius);
		
		//if both actors are Quads... necessary if statement?
		
		if (actor1 instanceof Quad && actor2 instanceof Quad){
			if (actor1.getHitboxShape() == ShapeType.CIRCLE && actor2.getHitboxShape() == ShapeType.CIRCLE)
				return distBetweenCenters(actor1, actor2) <= ((Quad)actor1).radius + ((Quad)actor2).radius;
			else if (actor1.getHitboxShape() == ShapeType.QUAD && actor2.getHitboxShape() == ShapeType.QUAD){
				/*Quad quad1 = (Quad)actor1;
				Quad quad2 = (Quad)actor2;
				if ((quad1.x + quad1.width/2) < (quad2.x - quad2.width/2) //if right of 1 is less than left of 2
						|| quad2.x + quad2.width/2 < (quad1.x - quad1.width/2) //if right of 2 is less than left of 1
						|| quad1.y + quad1.height/2 < (quad2.y - quad2.height/2) //if bottom of 1 is higher than 2
						|| quad2.y + quad2.height/2 < (quad1.y - quad1.height/2)){ //if bottom of 2 is higher than top of 1
					return false;
				}
				else{
					System.out.println("colliding");
					return true;
				}*/
				return !(((Quad)actor1).x + ((Quad)actor1).width/2 < ((Quad)actor2).x - ((Quad)actor2).width/2 //if right is less than left
				      || ((Quad)actor2).x + ((Quad)actor2).width/2 < ((Quad)actor1).x - ((Quad)actor1).width/2 //if right is less than left
				      || ((Quad)actor1).y + ((Quad)actor1).height/2 < ((Quad)actor2).y - ((Quad)actor2).height/2 //if top is less than bottom
				      || ((Quad)actor2).y + ((Quad)actor2).height/2 < ((Quad)actor1).y - ((Quad)actor1).height/2); //if top is less than bottom
			}
			else if (actor1.getHitboxShape() == ShapeType.CIRCLE && actor2.getHitboxShape() == ShapeType.POLYGON){
				//use polygon collision detection, but handle collision as if there are 2 circles colliding at the point of collision
				
				return false;
			}
			else if (actor1.getHitboxShape() == ShapeType.CIRCLE && actor2.getHitboxShape() == ShapeType.QUAD){
				return !(((Quad)actor1).x + ((Quad)actor1).radius < ((Quad)actor2).x - ((Quad)actor2).width/2 //if right is less than left
					      || ((Quad)actor2).x + ((Quad)actor2).width/2 < ((Quad)actor1).x - ((Quad)actor1).radius //if right is less than left
					      || ((Quad)actor1).y + ((Quad)actor1).radius < ((Quad)actor2).y - ((Quad)actor2).height/2 //if top is less than bottom
					      || ((Quad)actor2).y + ((Quad)actor2).height/2 < ((Quad)actor1).y - ((Quad)actor1).radius); //if top is less than bottom
			}
				
			else
				return false;
		}
		else if (actor1 instanceof Camera && actor2 instanceof Quad){
			return distBetweenCenters(actor1, actor2) <= ((Quad)actor2).radius*5;
		}
		else 
			return false;
		
		
	}
	
	//right side of actor 1 colliding with left side of actor2 collision
	public boolean isCollidingWithLeftSide(Actor actor1, Actor actor2){
		return (((Quad)actor1).x + ((Quad)actor1).width/2 > ((Quad)actor2).x - ((Quad)actor2).width/2
				&& actor1.x < actor2.x
				&& (((Quad)actor1).y - ((Quad)actor1).height/2 > ((Quad)actor2).y - ((Quad)actor2).height/2 && ((Quad)actor1).y - ((Quad)actor1).height/2 < ((Quad)actor2).y + ((Quad)actor2).height/2)
				|| (((Quad)actor1).y + ((Quad)actor1).height/2 > ((Quad)actor2).y - ((Quad)actor2).height/2 && ((Quad)actor1).y + ((Quad)actor1).height/2 < ((Quad)actor2).y + ((Quad)actor2).height/2));
	}
	
	//gets distance between the centers of the two actors
	public static float distBetweenCenters(Actor actor1, Actor actor2){
		return (float) Math.sqrt((actor1.x - actor2.x)*(actor1.x - actor2.x) + (actor1.y - actor2.y)*(actor1.y - actor2.y));
	}
	
	//gets angle between two points where actor1's center is on the angle projection and actor2 at the origin with respect to a horizontal line to the right
	public double angleBetweenCenters(Actor actor1, Actor actor2){ //FAILS WHEN AT TOP AND BOTTOM, WHY? NAN?
        if (actor1.x >= actor2.x){ //if on right side
        	if (actor2.x - actor1.x != 0){ //CATCHES NAN since you'd be dividing by 0
                if (Math.atan((actor2.y - actor1.y)/(actor2.x - actor1.x)) < 0){ //if angle is negative
                        return Math.atan((actor2.y - actor1.y)/(actor2.x - actor1.x)) + Math.PI*2;
                        }
                else{
                        //return (Math.PI/2.+Math.atan((actor2.y - actor1.y)/(actor2.x - actor1.x)));}
                	
                	return Math.atan((actor2.y - actor1.y)/(actor2.x - actor1.x));
                        
                }
        	}
        	else{
        		//System.out.println("asdf");    
        		if (actor1.y > actor2.y)
        			return Math.PI/2;
        		else if (actor1.y < actor2.y)
        			return -Math.PI/2; //in case of same x position
        		else //ITJ'S ALWASDF;ASLDKFA HERE 
        			return 0;
        	}
        }
        else if (actor1.x < actor2.x){
        	if (actor1.y == actor2.y)
        		return Math.PI;
        	
            return Math.atan((actor2.y - actor1.y)/(actor2.x - actor1.x)) + Math.PI; //
        }
        else
        	return Math.PI;
	}
	
	
	private boolean isEnteringNewScene = false;
	
	//true only when a new scene/room is being entered
	public boolean isEnteringNewScene(){
		if (isEnteringNewScene){
			isEnteringNewScene = false;
			return true;
		}
		else {
				return false;
		}
	}
	
	//call this whenever a new room/scene is loaded
	public void setEnteringNewSceneBool(boolean isEnteringNewScene){
		this.isEnteringNewScene = isEnteringNewScene;
	}
}
