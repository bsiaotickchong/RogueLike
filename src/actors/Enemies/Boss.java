package actors.Enemies;

import java.util.ArrayList;
import java.util.Random;

import org.lwjgl.input.Keyboard;

import sceneSetting.Direction;
import sceneSetting.Tile;
import sceneSetting.TileSystem;
import sceneSetting.TileType;
import defaultPackage.LogicEngine;
import actors.Actor;
import actors.MovementPattern;
import actors.MovementType;
import actors.Player1;
import actors.Projectile;
import actors.Quad;
import actors.ShapeType;

public class Boss extends Enemy{

	Direction facingDirection = Direction.NORTH;
	int directionCounter = 0;
	
	static Random rand = new Random();
	
	Quad detectionCircle = new Quad(this.x, this.y, size*4, size*4, ShapeType.CIRCLE);
	
	static int size = rand.nextInt(30) + 30;

	//GenericFocus focus = new GenericFocus(0,0,this.movePattern);  //will be replaced with either player or tile
	
	int bounceCounterX = 1;
	int bounceCounterY = 1;
	int lastVx = 0;
	int lastVy = 0;
	float semiPrevX = 0;
	float semiPrevY = 0;
	float previousX = 0;
	float previousY = 0;
	
	boolean canShoot = true;
	
	public Boss(float x, float y, float walkingPace) {
		super(x, y, 100, 100, ShapeType.CIRCLE, new MovementPattern(MovementType.DEAD, 0, 0)); 
		
		this.movePattern.setActorFocus(focus);
		this.movePattern.setFocus(x, y);
		//focus.setMovePattern(movePattern); //sets the generic focus' move pattern to its own move pattern (always true)
		this.gravityConstant = .001f;
		
		this.walkingPace = walkingPace;
		this.maxVx = walkingPace;
		this.maxVy = walkingPace;
		
		this.iFrames = 24;
		this.normalHealth = 10;
		this.damageGiven = 1;
		
		this.vx = walkingPace;
		this.vy = walkingPace;
		
		//setTileSystem(tileSystem); //refreshes tile system to current room tiles
	}

	public void act(ArrayList<Actor> actorsOnScreen){
		
		super.act(actorsOnScreen); //checks health and iframes
		
		//if detection circle needed
		detectionCircle.x = this.x;
		detectionCircle.y = this.y;
		
		Actor player1 = this;
		
		for (int i = 0; i<actorsOnScreen.size(); i++){
			if (actorsOnScreen.get(i) instanceof Player1){
				player1 = actorsOnScreen.get(i);
				break;
			}
		}
		
		//walking pattern
		wallBounceWalk();

		//always focusing at player
		focus.x = player1.x;
		focus.y = player1.y;
		
		if (framecount%5 == 0 && canShoot)
			shoot(actorsOnScreen);
		
		framecount++;		
		
	}
	
	public void setFocus(Actor actor){
		this.movePattern.setActorFocus(actor);
	}
	
	public void resetFocusAtSelf(){
		movePattern.setFocus(this.x,this.y);
	}
	
	public void wallBounceWalk(){
		//this.vx = walkingPace;
		//this.vy = walkingPace;
		
		if (Math.abs(x - previousX) < walkingPace){
			this.vx = (float) (Math.pow(-1, bounceCounterX)*walkingPace);
			bounceCounterX++;
		}
		if (Math.abs(y - previousY) < walkingPace){
			this.vy = (float) (Math.pow(-1, bounceCounterY)*walkingPace);
			bounceCounterY++;
		}
		
		//System.out.println(Math.pow(-1, bounceCounterX)*walkingPace);
		//System.out.println(((-1)^bounceCounterY)*walkingPace);
		//System.out.println(walkingPace);
		
		previousX = x;
		previousY = y;
		
	}
	
	public void shoot(ArrayList<Actor> actorsOnScreen){
		//change this so that it adds a set type of projectile (variable) and the placement (focus) is updated before the if statements to match the player location
		if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)){
			actorsOnScreen.add(new Projectile(this.x-this.radius-2.5f, this.y, 15, 15, -2f, 0f, ShapeType.CIRCLE, new MovementPattern(MovementType.DEAD, this), 144));
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)){
			actorsOnScreen.add(new Projectile(this.x+this.radius+2.5f, this.y, 15, 15, 2f, 0f, ShapeType.CIRCLE, new MovementPattern(MovementType.DEAD, this), 144));
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)){
			actorsOnScreen.add(new Projectile(this.x, this.y-this.radius-2.5f, 15, 15, 0f, 2f, ShapeType.CIRCLE, new MovementPattern(MovementType.DEAD, this), 144));
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_UP)){
			actorsOnScreen.add(new Projectile(this.x, this.y+this.radius+2.5f, 15, 15, 0f, -2f, ShapeType.CIRCLE, new MovementPattern(MovementType.DEAD, this), 144));
		}		
	}
	
	public void damageHealth(float damage){
		super.damageHealth(damage);
		
		walkingPace+=1;
		maxVx = walkingPace;
		maxVy = walkingPace;
	}
	
	public void walk(Direction dir){
		((GenericFocus)this.movePattern.getActorFocus()).move(dir, walkingPace, roomTiles);
		
	}
	
	public Tile determineCurrentTile(){
		//System.out.println(((int)this.x-(int)this.x%64)/64 + " : " + (((int)this.y-(int)this.y%64))/64);
		return roomTiles.getTile(((int)this.x-(int)this.x%64)/64, (((int)this.y-(int)this.y%64))/64);
	}
	
	//every frame refresh tile system to current room tilesystem
	public void setTileSystem(TileSystem tileSystem){
		this.roomTiles = tileSystem;
	}
	
	public void destroy(ArrayList<Actor> actorsOnScreen){
		
		//last commands until removed from room's array of enemies
		//drops random pickup
		
		actorsOnScreen.remove(this);
	}

}
