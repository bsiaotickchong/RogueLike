package defaultPackage;
import java.util.ArrayList;

import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;

import actors.*;
import actors.Enemies.*;
import actors.Weapons.Sword;
import actors.Weapons.Weapon;
import actors.Doors.Door;

@SuppressWarnings("unused")
public class PhysicsEngine {
	
	LogicEngine logic = new LogicEngine();
	
	public void moveActor(Actor actor){
		
		if (actor instanceof Enemy){
        	if (actor.getMovePattern().hasFocus()){
        		if (actor.getMovePattern().getActorFocus() instanceof GenericFocus){
        			moveActor(actor.getMovePattern().getActorFocus());
        		}
        	}
        }
		
		// TRY CATCH FOR ACTOR'S MOVEMENT TYPE?
		if (true) {
			try {
				if (actor.getMovePattern().getType() == MovementType.GRAVITATE) {
					gravitate(actor);
				}
				else if (actor.getMovePattern().getType() == MovementType.ATTRACT){
					attract(actor);
				}
			} catch (NullPointerException e) {
			}
		}		
		
		if (actor instanceof Camera) {
			/*
			if (logic.isColliding(actor, actor.getMovePattern().getActorFocus())) {
				actor.vx = 0;
				actor.vy = 0;
			}
			*/
		}
		
		//int delta = getDelta();
		
		//set limits for actor's velocity
		if (actor.vx > actor.maxVx){
			actor.vx = actor.maxVx;
		}
		if (actor.vx < -actor.maxVx){
			actor.vx = -actor.maxVx;
		}
		if (actor.vy > actor.maxVy){
			actor.vy = actor.maxVy;
		}
		if (actor.vy < -actor.maxVy){
			actor.vy = -actor.maxVy;
		}
		
		if (Math.abs(actor.vx) < actor.maxVx) 
			actor.x += actor.vx; //when under speed limit, move based on vx
		else if (logic.isPositive(actor.vx) )//&& Math.abs(actor.vx) >= actor.maxVx) //if vx is positive and over max vx
			actor.x += actor.maxVx; //move based on max velocity
		else if (!logic.isPositive(actor.vx) )//&& Math.abs(actor.vx) >= actor.maxVx ) //if vx is negative and over max vx (-maxVx)
			actor.x -= actor.maxVx; //move based on -max velocity
		
		if (Math.abs(actor.vy) < actor.maxVy) 
			actor.y -= actor.vy; //when under speed limit, move
		else if (logic.isPositive(actor.vy) && Math.abs(actor.vy) >= actor.maxVy) //if vy is positive and over max vy
			actor.y -= actor.maxVy; //move based on max velocity
		else if (!logic.isPositive(actor.vy) && Math.abs(actor.vy) >= actor.maxVy) //if vy is negative and over max vy
			actor.y += actor.maxVy; //move based on -max velocity
		
		// if (!logic.isMoving(actor))
		// decelerateActor(actor, delta);

		// timeKeeper.updateFPS(); // update FPS Counter

		
	}
	
	public void decelerateActor(Actor actor){
		//DECELERATE if statements
		if (logic.isPositive(actor.vx) && Math.abs(actor.vx) > .01){ //if x velocity is positive, subtract
			actor.vx -= actor.decel;
		}
		else if (!logic.isPositive(actor.vx) && Math.abs(actor.vx) > .01){ //if x velocity is negative, add
			actor.vx += actor.decel;
		}
		else{ 
			actor.vx = 0; //x velocity = 0 once the velocity is close enough to 0
		}
		if (logic.isPositive(actor.vy) && Math.abs(actor.vy) > .01){ //if vy is positive, subtract
			actor.vy -= actor.decel;
		}
		else if (!logic.isPositive(actor.vy) && Math.abs(actor.vy) > .01){ //if vy is negative, subtract
			actor.vy += actor.decel;
		}
		else{
			actor.vy = 0;
		}
	}
	
	//applies collision force to actorAffected by the otherActor
	public void applyCollisionForce(Actor actorAffected, Actor otherActor){
		
		//when two circles collide
		if (actorAffected.getHitboxShape() == ShapeType.CIRCLE && otherActor.getHitboxShape() == ShapeType.CIRCLE){
			float originalVx = actorAffected.vx;
			float originalVy = actorAffected.vy;

			//rock hardness
			double angleBetweenCenters = logic.angleBetweenCenters(actorAffected, otherActor);
			//System.out.println(Math.cos(angleBetweenCenters));
			//System.out.println(angleBetweenCenters);
			//System.out.println(actorAffected.vx + "|" + actorAffected.vy);

			//distBetweenCenters
			float distBetweenCenters = logic.distBetweenCenters(actorAffected, otherActor);

			float vHypotenuse = (float) Math.sqrt(actorAffected.vx*actorAffected.vx + actorAffected.vy*actorAffected.vy);
			float angleOfVelocity = 0;
			if (actorAffected.vx != 0) //makes sure you don't divide by 0
				angleOfVelocity = (float) Math.atan(actorAffected.vy/actorAffected.vx);
			else{
				if (logic.isPositive(actorAffected.vy)) //when vy is down, angle is -pi/2
					angleOfVelocity = (float) -(Math.PI/2);
				else //when vy is up, angle is pi/2
					angleOfVelocity = (float) (Math.PI/2);
			}
			
			float normalForceVector = (float) (vHypotenuse*Math.cos(angleBetweenCenters - angleOfVelocity));
			if (normalForceVector != normalForceVector){
				normalForceVector = (float) (Math.PI/2);
				System.out.println("HIT NAN");
			}

			if (!logic.isPositive(normalForceVector))
				normalForceVector = -normalForceVector;
			
			float xOfNFV;
			float yOfNFV;
			
			if (angleBetweenCenters >= Math.PI/2 && angleBetweenCenters < 3*Math.PI/2){
			//if (!logic.isPositive(normalForceVector))
			//	normalForceVector = -normalForceVector;
			
				xOfNFV = (float) (normalForceVector * Math.cos(angleBetweenCenters));
				yOfNFV = (float) -(normalForceVector * Math.sin(angleBetweenCenters));
			}
			else{
				xOfNFV = (float) (normalForceVector * Math.cos(angleBetweenCenters));
				yOfNFV = (float) -(normalForceVector * Math.sin(angleBetweenCenters));
				
			}
			
			//unneeded now
			if (Math.sin(angleBetweenCenters) == 1)
				yOfNFV = yOfNFV;
			else if (Math.sin(angleBetweenCenters) == -1)
				yOfNFV = yOfNFV;
			else if (Math.cos(angleBetweenCenters) == 1)
				xOfNFV = xOfNFV;
			else if (Math.cos(angleBetweenCenters) == -1)
				xOfNFV = xOfNFV;
				
			
			//
			//System.out.print(xOfNFV + " " + originalVx);
			//System.out.println(" | " + yOfNFV + "  " + originalVy);
			//System.out.println(yOfNFV);
			if (otherActor.hardness == 0) {
				actorAffected.vx += xOfNFV*1;
				actorAffected.vy += yOfNFV*1;
				if (otherActor instanceof Sword) {
					actorAffected.vx += xOfNFV*50;
					actorAffected.vy += yOfNFV*50;
				}
			}
			
			//System.out.print(xOfNFV + " " + originalVx);
			//System.out.println(" | " + yOfNFV + "  " + originalVy);
			
			/*
			// rock hardness
			double angleBetweenCenters = logic.angleBetweenCenters(
					actorAffected, otherActor);
			// System.out.println(Math.cos(angleBetweenCenters));
			// System.out.println(angleBetweenCenters);
			// System.out.println(actorAffected.vx + "|" + actorAffected.vy);

			float vxToAdd = (float) (actorAffected.vx
					* Math.cos(angleBetweenCenters - Math.PI) * Math
					.cos((Math.PI) / 2 - (angleBetweenCenters - Math.PI)));
			float vyToAdd = (float) (actorAffected.vy
					* Math.sin(angleBetweenCenters - Math.PI) * Math
					.sin((Math.PI) / 2 - (angleBetweenCenters - Math.PI)));

			if (otherActor.hardness == 0) {
				// actorAffected.vx += -2*actorAffected.vx;
				// actorAffected.vy += -2*actorAffected.vy;
				actorAffected.vx -= vxToAdd * 1;
				actorAffected.vy -= vyToAdd * 1;
			}
			*/
		
		}
		else if (actorAffected.getHitboxShape() == ShapeType.CIRCLE && otherActor.getHitboxShape() == ShapeType.QUAD){
			
			float xOverlap = 0;
			float yOverlap = 0;
			
			float xPossibleOverlap = 0;
			float yPossibleOverlap = 0;
			
			if (actorAffected.x <= otherActor.x){
				xPossibleOverlap = (((Quad)actorAffected).getWidth()/2 + actorAffected.x) - (-((Quad)otherActor).getWidth()/2 + otherActor.x);
			} else if (actorAffected.x > otherActor.x){
				xPossibleOverlap = (((Quad)otherActor).getWidth()/2 + otherActor.x) - (-((Quad)actorAffected).getWidth()/2 + actorAffected.x);
			}
			
			if (actorAffected.y <= otherActor.y){
				yPossibleOverlap = (((Quad)actorAffected).getHeight()/2 + actorAffected.y) - (-((Quad)otherActor).getHeight()/2 + otherActor.y);
			} else if (actorAffected.y > otherActor.y){
				yPossibleOverlap = (((Quad)otherActor).getHeight()/2 + otherActor.y) - (-((Quad)actorAffected).getHeight()/2 + actorAffected.y);
			}
			
			if (Math.abs(xPossibleOverlap) < Math.abs(yPossibleOverlap))
				xOverlap = xPossibleOverlap*1.0f; //multiplier prevents continual collision
			else if (Math.abs(yPossibleOverlap) < Math.abs(xPossibleOverlap))
				yOverlap = yPossibleOverlap*1.0f; //multiplier prevents continual collision; if less than one, pushes away slowly
			else {
				xOverlap = xPossibleOverlap;
				yOverlap = yPossibleOverlap;
			}
			
			//SOMETHING HAPPENS WHERE TOP COLLISIONS BECOME STICKY
			//ALL SIDES BECOME STICKY IF THERE ARE ONLY TWO IF ELSE STATEMENTS
			
			//collision on right side of actorAffected
			if (actorAffected.x <= otherActor.x
					&& Math.abs(xPossibleOverlap) < Math.abs(yPossibleOverlap)
					&& ((actorAffected.y + ((Quad)actorAffected).height/2 > otherActor.y - ((Quad)otherActor).height/2) || actorAffected.y - ((Quad)actorAffected).height/2 < otherActor.y + ((Quad)otherActor).height/2)){
				
				if (otherActor.hardness == 0){
					actorAffected.vx += -actorAffected.vx;
				}else if (otherActor.hardness < 0){
					actorAffected.vx += -actorAffected.vx*(Math.abs(otherActor.hardness)*.1 + 1);
				}
			} 
			//collision on left side of actorAffected
			else if (actorAffected.x > otherActor.x
					&& Math.abs(xPossibleOverlap) < Math.abs(yPossibleOverlap)
					&& ((actorAffected.y + ((Quad)actorAffected).height/2 > otherActor.y - ((Quad)otherActor).height/2) || actorAffected.y - ((Quad)actorAffected).height/2 < otherActor.y + ((Quad)otherActor).height/2)){
				
				if (otherActor.hardness == 0){
					actorAffected.vx += -actorAffected.vx;
				} else if (otherActor.hardness < 0){
					actorAffected.vx += -actorAffected.vx*(Math.abs(otherActor.hardness)*.1 + 1);
				}
			}
			//collision on bottom side of actorAffected
			else if (actorAffected.y < otherActor.y
					&& Math.abs(yPossibleOverlap) < Math.abs(xPossibleOverlap)
					&& ((actorAffected.x + ((Quad)actorAffected).width/2 > otherActor.x - ((Quad)otherActor).width/2) || actorAffected.x - ((Quad)actorAffected).width/2 < otherActor.x + ((Quad)otherActor).width/2)){
				
				if (otherActor.hardness == 0){
					actorAffected.vy += -actorAffected.vy;
				}else if (otherActor.hardness < 0){
					actorAffected.vy += -actorAffected.vy*(Math.abs(otherActor.hardness)*.1 + 1);
				}
			}
			//collision on top side of actorAffected
			else if (actorAffected.y >= otherActor.y
					&& Math.abs(yPossibleOverlap) < Math.abs(xPossibleOverlap)
					&& ((actorAffected.x + ((Quad)actorAffected).width/2 > otherActor.x - ((Quad)otherActor).width/2) || actorAffected.x - ((Quad)actorAffected).width/2 < otherActor.x + ((Quad)otherActor).width/2)){
				
				if (otherActor.hardness == 0){
					actorAffected.vy += -actorAffected.vy;
				}else if (otherActor.hardness < 0){
					actorAffected.vy += -actorAffected.vy*(Math.abs(otherActor.hardness)*.1 + 1);
				}
			}
			
		}
		/*
		else if (actorAffected.getHitboxShape() == ShapeType.QUAD && otherActor.getHitboxShape() == ShapeType.QUAD){
			
			float xOverlap = 0;
			float yOverlap = 0;
			
			float xPossibleOverlap = 0;
			float yPossibleOverlap = 0;
			
			if (actorAffected.x <= otherActor.x){
				xPossibleOverlap = (((Quad)actorAffected).getWidth()/2 + actorAffected.x) - (-((Quad)otherActor).getWidth()/2 + otherActor.x);
			} else if (actorAffected.x > otherActor.x){
				xPossibleOverlap = (((Quad)otherActor).getWidth()/2 + otherActor.x) - (-((Quad)actorAffected).getWidth()/2 + actorAffected.x);
			}
			
			if (actorAffected.y <= otherActor.y){
				yPossibleOverlap = (((Quad)actorAffected).getHeight()/2 + actorAffected.y) - (-((Quad)otherActor).getHeight()/2 + otherActor.y);
			} else if (actorAffected.y > otherActor.y){
				yPossibleOverlap = (((Quad)otherActor).getHeight()/2 + otherActor.y) - (-((Quad)actorAffected).getHeight()/2 + actorAffected.y);
			}
			
			if (Math.abs(xPossibleOverlap) < Math.abs(yPossibleOverlap))
				xOverlap = xPossibleOverlap*1.0f; //multiplier prevents continual collision
			else if (Math.abs(yPossibleOverlap) < Math.abs(xPossibleOverlap))
				yOverlap = yPossibleOverlap*1.0f; //multiplier prevents continual collision; if less than one, pushes away slowly
			else {
				xOverlap = xPossibleOverlap;
				yOverlap = yPossibleOverlap;
			}
			
			//SOMETHING HAPPENS WHERE TOP COLLISIONS BECOME STICKY
			//ALL SIDES BECOME STICKY IF THERE ARE ONLY TWO IF ELSE STATEMENTS
			
			//collision on right side of actorAffected
			if (actorAffected.x <= otherActor.x
					&& Math.abs(xPossibleOverlap) < Math.abs(yPossibleOverlap)
					&& ((actorAffected.y + ((Quad)actorAffected).height/2 > otherActor.y - ((Quad)otherActor).height/2) || actorAffected.y - ((Quad)actorAffected).height/2 < otherActor.y + ((Quad)otherActor).height/2)){
				
				if (otherActor.hardness == 0){
					actorAffected.vx += -actorAffected.vx;
				}else if (otherActor.hardness < 0){
					actorAffected.vx += -actorAffected.vx*(Math.abs(otherActor.hardness)*.1 + 1);
				}
			} 
			//collision on left side of actorAffected
			else if (actorAffected.x > otherActor.x
					&& Math.abs(xPossibleOverlap) < Math.abs(yPossibleOverlap)
					&& ((actorAffected.y + ((Quad)actorAffected).height/2 > otherActor.y - ((Quad)otherActor).height/2) || actorAffected.y - ((Quad)actorAffected).height/2 < otherActor.y + ((Quad)otherActor).height/2)){
				
				if (otherActor.hardness == 0){
					actorAffected.vx += -actorAffected.vx;
				} else if (otherActor.hardness < 0){
					actorAffected.vx += -actorAffected.vx*(Math.abs(otherActor.hardness)*.1 + 1);
				}
			}
			//collision on bottom side of actorAffected
			else if (actorAffected.y < otherActor.y
					&& Math.abs(yPossibleOverlap) < Math.abs(xPossibleOverlap)
					&& ((actorAffected.x + ((Quad)actorAffected).width/2 > otherActor.x - ((Quad)otherActor).width/2) || actorAffected.x - ((Quad)actorAffected).width/2 < otherActor.x + ((Quad)otherActor).width/2)){
				
				if (otherActor.hardness == 0){
					actorAffected.vy += -actorAffected.vy;
				}else if (otherActor.hardness < 0){
					actorAffected.vy += -actorAffected.vy*(Math.abs(otherActor.hardness)*.1 + 1);
				}
			}
			//collision on top side of actorAffected
			else if (actorAffected.y >= otherActor.y
					&& Math.abs(yPossibleOverlap) < Math.abs(xPossibleOverlap)
					&& ((actorAffected.x + ((Quad)actorAffected).width/2 > otherActor.x - ((Quad)otherActor).width/2) || actorAffected.x - ((Quad)actorAffected).width/2 < otherActor.x + ((Quad)otherActor).width/2)){
				
				if (otherActor.hardness == 0){
					actorAffected.vy += -actorAffected.vy;
				}else if (otherActor.hardness < 0){
					actorAffected.vy += -actorAffected.vy*(Math.abs(otherActor.hardness)*.1 + 1);
				}
			}
			
		}*/
	}
	
	public void checkCollisions(ArrayList<Actor> actorsOnScreen) {
		
		
		for (int i = 0; i<actorsOnScreen.size(); i++){ //goes through every actor
			for (int j = 0; j<actorsOnScreen.size(); j++){ //compares each actor to every other actor on screen
				
				//When i and j are not equal, you are not comparing the same object to itself
				if (j != i) {
					try {
						if (logic.isColliding(actorsOnScreen.get(i), actorsOnScreen.get(j))) {
							if (!(actorsOnScreen.get(i) instanceof Rock) //if not a rock
								//&& !(actorsOnScreen.get(i) instanceof Wall) && !(actorsOnScreen.get(j) instanceof Wall) //if not a wall 
								&& !(actorsOnScreen.get(i) instanceof Door) && !(actorsOnScreen.get(j) instanceof Door) //if not a door
								&& !(actorsOnScreen.get(i).getMovePattern().getType() == MovementType.LOCKED)
								&& !(actorsOnScreen.get(i) instanceof Projectile && actorsOnScreen.get(j) instanceof Projectile)
								){ 
								
								if (!(actorsOnScreen.get(i) instanceof Projectile || actorsOnScreen.get(j) instanceof Projectile)){ //if neither actors are projectiles
									if (!(actorsOnScreen.get(j) instanceof PickUp)) { //if the other actor is a pickup, don't move the actor moving on it
										if (!(actorsOnScreen.get(i) instanceof Weapon && actorsOnScreen.get(j) instanceof Player1)
												&& !(actorsOnScreen.get(j) instanceof Weapon && actorsOnScreen.get(i) instanceof Player1)){
											displaceOutOfCollision(actorsOnScreen.get(i), actorsOnScreen.get(j));
										}
									}
								}
								
								
								if (!(actorsOnScreen.get(i) instanceof Weapon && actorsOnScreen.get(j) instanceof Player1)
										&& !(actorsOnScreen.get(j) instanceof Weapon && actorsOnScreen.get(i) instanceof Player1)){
									if (!(actorsOnScreen.get(i) instanceof Projectile && actorsOnScreen.get(j) instanceof Enemy)
											&& !(actorsOnScreen.get(j) instanceof Projectile && actorsOnScreen.get(i) instanceof Enemy)){
										applyCollisionForce(actorsOnScreen.get(i), actorsOnScreen.get(j)); // applies collision force to the actor being checked
									}	
								}
								if (actorsOnScreen.get(j) instanceof Projectile && (actorsOnScreen.get(i) instanceof Player1)){
									
									((Projectile)actorsOnScreen.get(j)).damage(actorsOnScreen.get(i));
									
									((Projectile)actorsOnScreen.get(j)).destroy(actorsOnScreen);
									
								}
								if (actorsOnScreen.get(j) instanceof Sword && !(actorsOnScreen.get(i) instanceof Player1)){
									if (actorsOnScreen.get(i) instanceof Enemy){
										((Sword)actorsOnScreen.get(j)).damage((Enemy)(actorsOnScreen.get(i)));
										//System.out.println(((Enemy)actorsOnScreen.get(i)).getNormalHealth());
									}
								}
								if (actorsOnScreen.get(j) instanceof Enemy && (actorsOnScreen.get(i) instanceof Player1)){
									((Enemy)actorsOnScreen.get(j)).damage((Player1)(actorsOnScreen.get(i)));
								}
								
							}	
							if (actorsOnScreen.get(i) instanceof Player1 && actorsOnScreen.get(j) instanceof Door){
								((Door)actorsOnScreen.get(j)).setIsBeingTraversed(true);
							}
						}
					}
					catch (Exception NullPointerException){

					}
				}
				else {}
			}
		}
	}
	
	//checks for circle collision based off of the centers and radii of the two actors
	
	//displace circle when colliding based on angle
	public void displaceOutOfCollision(Actor actorAffected, Actor otherActor){
		
		//when two circles collide
		if (actorAffected.getHitboxShape() == ShapeType.CIRCLE && otherActor.getHitboxShape() == ShapeType.CIRCLE){
			float overlap = ((Quad)actorAffected).radius + ((Quad)otherActor).radius - logic.distBetweenCenters(actorAffected, otherActor);
	        double angleBetweenCenters = logic.angleBetweenCenters(actorAffected, otherActor);
	        float xToDisplace = (float) (overlap*Math.cos(angleBetweenCenters));
	        float yToDisplace = (float) (overlap*Math.sin(angleBetweenCenters));
	        
	        //corrects faulty sin and cos values at 0, pi/2, pi, 3pi/2
	        //unneeded now
			if (Math.sin(angleBetweenCenters) == 1)
				yToDisplace = yToDisplace;
			else if (Math.sin(angleBetweenCenters) == -1)
				yToDisplace = yToDisplace;
			else if (Math.cos(angleBetweenCenters) == 1)
				xToDisplace = xToDisplace;
			else if (Math.cos(angleBetweenCenters) == -1)
				xToDisplace = xToDisplace;
	        
			
	        actorAffected.x += xToDisplace;
	        actorAffected.y += yToDisplace;
	        
	        
	        
	        //System.out.println("angle" + angleBetweenCenters);
	        //System.out.println(xToDisplace + " | " + yToDisplace);
		}
		else if (actorAffected.getHitboxShape() == ShapeType.QUAD && otherActor.getHitboxShape() == ShapeType.QUAD){
			float xOverlap = 0;
			float yOverlap = 0;
			
			float xPossibleOverlap = 0;
			float yPossibleOverlap = 0;
			
			if (actorAffected.x <= otherActor.x){
				xPossibleOverlap = (((Quad)actorAffected).getWidth()/2 + actorAffected.x) - (-((Quad)otherActor).getWidth()/2 + otherActor.x);
			} else if (actorAffected.x > otherActor.x){
				xPossibleOverlap = (((Quad)otherActor).getWidth()/2 + otherActor.x) - (-((Quad)actorAffected).getWidth()/2 + actorAffected.x);
			}
			
			if (actorAffected.y <= otherActor.y){
				yPossibleOverlap = (((Quad)actorAffected).getHeight()/2 + actorAffected.y) - (-((Quad)otherActor).getHeight()/2 + otherActor.y);
			} else if (actorAffected.y > otherActor.y){
				yPossibleOverlap = (((Quad)otherActor).getHeight()/2 + otherActor.y) - (-((Quad)actorAffected).getHeight()/2 + actorAffected.y);
			}
			
			if (Math.abs(xPossibleOverlap) < Math.abs(yPossibleOverlap))
				xOverlap = xPossibleOverlap*1.0f; //multiplier prevents continual collision
			else if (Math.abs(yPossibleOverlap) < Math.abs(xPossibleOverlap))
				yOverlap = yPossibleOverlap*1.0f; //multiplier prevents continual collision; if less than one, pushes away slowly
			else {
				xOverlap = xPossibleOverlap;
				yOverlap = yPossibleOverlap;
			}
			
			//System.out.println(xOverlap + " | " + yOverlap);
			
			if (actorAffected.x < otherActor.x)
				actorAffected.x -= xOverlap;
			if (actorAffected.x >= otherActor.x)
				actorAffected.x += xOverlap;
			if (actorAffected.y < otherActor.y)
				actorAffected.y -= yOverlap;
			if (actorAffected.y >= otherActor.y)
				actorAffected.y += yOverlap;
		}
		
		//use quad on quad collision displacement handling for circle on quad
		//problems when actorAffected collides with right or bottom side of otherActor
		else if (actorAffected.getHitboxShape() == ShapeType.CIRCLE && otherActor.getHitboxShape() == ShapeType.QUAD){
			float xOverlap = 0;
			float yOverlap = 0;
			
			float xPossibleOverlap = 0;
			float yPossibleOverlap = 0;
			
			if (actorAffected.x <= otherActor.x){
				xPossibleOverlap = (((Quad)actorAffected).getWidth()/2 + actorAffected.x) - (-((Quad)otherActor).getWidth()/2 + otherActor.x);
			} else if (actorAffected.x > otherActor.x){
				xPossibleOverlap = (((Quad)otherActor).getWidth()/2 + otherActor.x) - (-((Quad)actorAffected).getWidth()/2 + actorAffected.x);
			}
			
			if (actorAffected.y <= otherActor.y){
				yPossibleOverlap = (((Quad)actorAffected).getHeight()/2 + actorAffected.y) - (-((Quad)otherActor).getHeight()/2 + otherActor.y);
			} else if (actorAffected.y > otherActor.y){
				yPossibleOverlap = (((Quad)otherActor).getHeight()/2 + otherActor.y) - (-((Quad)actorAffected).getHeight()/2 + actorAffected.y);
			}
			
			if (Math.abs(xPossibleOverlap) < Math.abs(yPossibleOverlap))
				xOverlap = xPossibleOverlap*1.0f; //multiplier prevents continual collision
			else if (Math.abs(yPossibleOverlap) < Math.abs(xPossibleOverlap))
				yOverlap = yPossibleOverlap*1.0f; //multiplier prevents continual collision; if less than one, pushes away slowly
			else {
				xOverlap = xPossibleOverlap;
				yOverlap = yPossibleOverlap;
			}
			
			//System.out.println(xOverlap + " | " + yOverlap);
			
			if (actorAffected.x < otherActor.x)
				actorAffected.x -= xOverlap;
			if (actorAffected.x >= otherActor.x)
				actorAffected.x += xOverlap;
			if (actorAffected.y < otherActor.y)
				actorAffected.y -= yOverlap;
			if (actorAffected.y >= otherActor.y)
				actorAffected.y += yOverlap;
		} 
	}
	
	public void gravitate (Actor actor){
		actor.getMovePattern().setFocus(actor.getMovePattern().getActorFocus().x,actor.getMovePattern().getActorFocus().y);
		
		double angleBetweenCenters = logic.angleBetweenCenters(actor, actor.getMovePattern().getActorFocus());
		
		float rSquared = (logic.distBetweenCenters(actor, actor.getMovePattern().getActorFocus()) * logic.distBetweenCenters(actor, actor.getMovePattern().getActorFocus()));
		
		float vxToAdd = (float) ((Math.cos(angleBetweenCenters - Math.PI) * Math.cos((Math.PI) / 2 - (angleBetweenCenters - Math.PI))) / rSquared); // divide by R^2
		float vyToAdd = (float) ((Math.sin(angleBetweenCenters - Math.PI) * Math.sin((Math.PI) / 2 - (angleBetweenCenters - Math.PI))) / rSquared);
		
		//float x = (float) (Math.cos(angleBetweenCenters) * logic.distBetweenCenters(actor.getMovePattern().getActorFocus(), actor));
		//float y = (float) (Math.sin(angleBetweenCenters) * logic.distBetweenCenters(actor.getMovePattern().getActorFocus(), actor));
		/*
		if (angleBetweenCenters <= Math.PI/2 && angleBetweenCenters >= 0){ //top right
			vxToAdd = -1 / (x);
			vyToAdd = -1 / (y);
		} else if (angleBetweenCenters > Math.PI/2 && angleBetweenCenters <= Math.PI){ //top left
			vxToAdd = -1 / (x);
			vyToAdd = -1 / (y);
		} else if (angleBetweenCenters > Math.PI && angleBetweenCenters <= 3*Math.PI / 2){ //bottom left
			vxToAdd = -1 / (x);
			vyToAdd = -1 / (y);
		} else if (angleBetweenCenters < 2*Math.PI && angleBetweenCenters >= 3*Math.PI / 2){ //bottom right
			vxToAdd = -1 / (x);
			vyToAdd = -1 / (y);
		}*/
		
		/*
		try {
			float gravityConstant = actor.gravityConstant;
		} catch (NullPointerException e){
			
		}*/
		float vToAdd = 0;
		if (logic.distBetweenCenters(actor, actor.getMovePattern().getActorFocus()) != 0){ //check if actor is directly on top of its focus
			vToAdd = (float) (actor.getGravityConstant() / Math.pow(logic.distBetweenCenters(actor, actor.getMovePattern().getActorFocus()),2f));
		}
		else {
			vToAdd = actor.maxVx; 
		}
			
		if (vToAdd != vToAdd){
			vToAdd = (float) (Math.PI/2);
			System.out.println("HIT NAN");
		}
				
		vxToAdd = (float) (Math.cos(angleBetweenCenters) * vToAdd);
		vyToAdd = (float) (Math.sin(angleBetweenCenters) * vToAdd);
		
		//unneeded now
		if (Math.sin(angleBetweenCenters) == 1)
			vyToAdd = vyToAdd;
		else if (Math.sin(angleBetweenCenters) == -1)
			vyToAdd = vyToAdd;
		else if (Math.cos(angleBetweenCenters) == 1)
			vxToAdd = vxToAdd;
		else if (Math.cos(angleBetweenCenters) == -1)
			vxToAdd = vxToAdd;
		
		if (vxToAdd > actor.maxVx){
			vxToAdd  = actor.maxVx;
		}
		if (vxToAdd  < -actor.maxVx){
			vxToAdd  = -actor.maxVx;
		}
		if (vyToAdd  > actor.maxVy){
			vyToAdd= actor.maxVy;
		}
		if (vyToAdd < -actor.maxVy){
			vyToAdd = -actor.maxVy;
		}
		
		actor.vx -= vxToAdd;
		actor.vy += vyToAdd;
		
		
		//System.out.println("vx: " + actor.getMovePattern().getFocusX() + " vy: " + actor.getMovePattern().getFocusY());
		//System.out.println("vx: " + vxToAdd + " vy: " + vyToAdd + " angle: " + angleBetweenCenters);
		//System.out.println(angleBetweenCenters);
	}
	
	public void attract (Actor actor){	
		actor.getMovePattern().setFocus(actor.getMovePattern().getActorFocus().x,actor.getMovePattern().getActorFocus().y);
		
		double angleBetweenCenters = logic.angleBetweenCenters(actor, actor.getMovePattern().getActorFocus());
		
		float vToAdd = 0;
		//if (logic.distBetweenCenters(actor, actor.getMovePattern().getActorFocus()) != 0){ //check if actor is directly on top of its focus
		
		//REMEMBER TO SET GRAVITY CONSTANT TO SOMETHING OTHER THAN 0
		vToAdd = (float) (actor.getGravityConstant() * Math.pow(logic.distBetweenCenters(actor, actor.getMovePattern().getActorFocus()),2.5f));; 
		//vToAdd = (float) (Math.pow(logic.distBetweenCenters(actor, actor.getMovePattern().getActorFocus()),2.5f)/20000);
		
		//}
		//else {
		//	vToAdd = 0;  
		//}
			
		if (vToAdd != vToAdd){
			vToAdd = (float) (Math.PI/2);
			System.out.println("HIT NAN");
		}
				
		float vxToAdd = (float) (Math.cos(angleBetweenCenters) * vToAdd);
		float vyToAdd = (float) (Math.sin(angleBetweenCenters) * vToAdd);
		
		
		if (vxToAdd > actor.maxVx){
			vxToAdd  = actor.maxVx;
		}
		if (vxToAdd  < -actor.maxVx){
			vxToAdd  = -actor.maxVx;
		}
		if (vyToAdd  > actor.maxVy){
			vyToAdd= actor.maxVy;
		}
		if (vyToAdd < -actor.maxVy){
			vyToAdd = -actor.maxVy;
		}
		
		actor.vx = -vxToAdd;
		actor.vy = vyToAdd;
		
		//System.out.println(vToAdd);
		//System.out.println("vx: " + actor.getMovePattern().getFocusX() + " vy: " + actor.getMovePattern().getFocusY());
		//if (actor instanceof TestEnemy)
		//System.out.println("vx: " + vxToAdd + " vy: " + vyToAdd + " angle: " + angleBetweenCenters);
		//System.out.println(vToAdd);
	}
}
