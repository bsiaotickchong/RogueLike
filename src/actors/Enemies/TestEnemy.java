package actors.Enemies;

import java.util.ArrayList;
import java.util.Random;

import defaultPackage.LogicEngine;
import actors.Actor;
import actors.MovementPattern;
import actors.Player1;
import actors.Quad;
import actors.ShapeType;
import actors.MovementType;
import sceneSetting.Direction;
import sceneSetting.Tile;
import sceneSetting.TileSystem;
import sceneSetting.TileType;

public class TestEnemy extends Enemy{
	
	Direction facingDirection = Direction.NORTH;
	int directionCounter = 0;
	
	static Random rand = new Random();
	
	Quad detectionCircle = new Quad(this.x, this.y, size*4, size*4, ShapeType.CIRCLE);
	
	static int size = rand.nextInt(30) + 30;

	//GenericFocus focus = new GenericFocus(0,0,this.movePattern);  //will be replaced with either player or tile
	
	public TestEnemy(float x, float y, float walkingPace, TileSystem tileSystem) {
		super(x, y, size, size, ShapeType.CIRCLE, new MovementPattern(MovementType.ATTRACT, 0, 0)); 
		
		this.movePattern.setActorFocus(focus);
		this.movePattern.setFocus(x, y);
		//focus.setMovePattern(movePattern); //sets the generic focus' move pattern to its own move pattern (always true)
		this.gravityConstant = .001f;
		
		this.walkingPace = walkingPace;
		this.maxVx = walkingPace;
		this.maxVy = walkingPace;
		
		this.iFrames = 24;
		this.normalHealth = 5;
		this.damageGiven = 1;
		
		setTileSystem(tileSystem); //refreshes tile system to current room tiles
	}

	public void act(ArrayList<Actor> actorsOnScreen){
		
		super.act(actorsOnScreen);
		
		detectionCircle.x = this.x;
		detectionCircle.y = this.y;
		
		Actor player1 = this;
		
		for (int i = 0; i<actorsOnScreen.size(); i++){
			if (actorsOnScreen.get(i) instanceof Player1){
				player1 = actorsOnScreen.get(i);
				break;
			}
		}
		
		if (framecount % 60 == 0 && !LogicEngine.isColliding(player1, detectionCircle)){
			randomWalk();
			//facingDirection = Direction.values()[directionCounter%4];
			//directionCounter++;
		} else if (LogicEngine.isColliding(player1, detectionCircle)){
			focus.x = player1.x;
			focus.y = player1.y;
		}
		framecount++;
		
		
	}
	
	public void setFocus(Actor actor){
		this.movePattern.setActorFocus(actor);
	}
	
	public void resetFocusAtSelf(){
		movePattern.setFocus(this.x,this.y);
	}
	
	public void randomWalk(){
		//do random walking around room
		Direction randomDirection = Direction.values()[rand.nextInt(4)];
		Tile currTile = determineCurrentTile();
		//int xCoord = ((int)currTile.getX()-32)/64;
		//int yCoord = ((int)currTile.getY()-32)/64;
		int xCoord = currTile.getXCoord();
		int yCoord = currTile.getYCoord();
		
		boolean northOpen = false;
		boolean eastOpen = false;
		boolean southOpen = false;
		boolean westOpen = false;
		
		try {
			northOpen = !(roomTiles.getTile(xCoord, yCoord-1).getTileType() == TileType.ROCK);
		} catch (ArrayIndexOutOfBoundsException a){
			
		}
		try {
			eastOpen = !(roomTiles.getTile(xCoord+1, yCoord).getTileType() == TileType.ROCK);
		} catch (ArrayIndexOutOfBoundsException a){
			
		}
		try {
			southOpen = !(roomTiles.getTile(xCoord, yCoord+1).getTileType() == TileType.ROCK);
		} catch (ArrayIndexOutOfBoundsException a){
			
		}
		try {
			westOpen = !(roomTiles.getTile(xCoord-1, yCoord).getTileType() == TileType.ROCK);
		} catch (ArrayIndexOutOfBoundsException a){
			
		}
		
		int openCounter = 0;
		
		if (northOpen)
			openCounter++;
		if (eastOpen)
			openCounter++;
		if (southOpen)
			openCounter++;
		if (westOpen)
			openCounter++;
		
		if (openCounter == 4)
			randomDirection = Direction.values()[rand.nextInt(4)];
		else if (openCounter == 3){
			if (northOpen && eastOpen && southOpen){
				randomDirection = Direction.values()[rand.nextInt(3)]; //0,1,2
			} else if (northOpen && eastOpen && westOpen){
				switch (rand.nextInt(3)){
				case 0:
					randomDirection = Direction.values()[0]; //north
					break;
				case 1:
					randomDirection = Direction.values()[1]; //east 
					break;
				case 2:
					randomDirection = Direction.values()[3]; //west
					break;
				}
			} else if (northOpen && southOpen && westOpen){
				switch (rand.nextInt(3)){
				case 0:
					randomDirection = Direction.values()[0]; //north
					break;
				case 1:
					randomDirection = Direction.values()[2]; //south 
					break;
				case 2:
					randomDirection = Direction.values()[3]; //west
					break;
				}
			} 
		} else if (openCounter == 2){
			if (northOpen && eastOpen)
				randomDirection = Direction.values()[rand.nextInt(2)];
			else if (northOpen && southOpen){
				switch (rand.nextInt(2)){
				case 0:
					randomDirection = Direction.values()[0]; //north
					break;
				case 1:
					randomDirection = Direction.values()[2]; //south
					break;
				}
			} else if (northOpen && westOpen){
				switch (rand.nextInt(2)){
				case 0:
					randomDirection = Direction.values()[0]; //north
					break;
				case 1:
					randomDirection = Direction.values()[3]; //west
					break;
				}
			} else if (eastOpen && southOpen){
				switch (rand.nextInt(2)){
				case 0:
					randomDirection = Direction.values()[1]; //east
					break;
				case 1:
					randomDirection = Direction.values()[2]; //south
					break;
				}
			} else if (eastOpen && westOpen){
				switch (rand.nextInt(2)){
				case 0:
					randomDirection = Direction.values()[1]; //east
					break;
				case 1:
					randomDirection = Direction.values()[3]; //west
					break;
				}
			} else if (southOpen && westOpen){
				switch (rand.nextInt(2)){
				case 0:
					randomDirection = Direction.values()[2]; //south
					break;
				case 1:
					randomDirection = Direction.values()[3]; //west
					break;
				}
			} 
		} else if (openCounter == 1){
			if (northOpen)
				randomDirection = Direction.values()[0]; //north
			else if (eastOpen)
				randomDirection = Direction.values()[1]; //east
			else if (southOpen)
				randomDirection = Direction.values()[2]; //south
			else if (westOpen)
				randomDirection = Direction.values()[3]; //west
		} else{
		}
		
		if (openCounter != 0){
			/*
			switch (randomDirection) {
			case NORTH:
				//focus.x = roomTiles.getTile(xCoord, yCoord-1).getX();
				focus.y = roomTiles.getTile(xCoord, yCoord-1).getY();
				System.out.println("moved " + randomDirection);
				break;
			case EAST:
				focus.x = roomTiles.getTile(xCoord+1, yCoord).getX();
				//focus.y = roomTiles.getTile(xCoord+1, yCoord).getY();
				System.out.println("moved " + randomDirection);
				break;
			case SOUTH:
				//focus.x = roomTiles.getTile(xCoord, yCoord+1).getX();
				focus.y = roomTiles.getTile(xCoord, yCoord+1).getY();
				System.out.println("moved " + randomDirection);
				break;
			case WEST:
				focus.x = roomTiles.getTile(xCoord-1, yCoord).getX();
				//focus.y = roomTiles.getTile(xCoord-1, yCoord).getY();
				System.out.println("moved " + randomDirection);
				break;
			}
			*/
			
			if (randomDirection == Direction.NORTH)
				focus.y = ((Tile)roomTiles.getTile(xCoord, yCoord-1)).getY();
			else if (randomDirection == Direction.EAST)
				focus.x = ((Tile)roomTiles.getTile(xCoord+1, yCoord)).getY();
			else if (randomDirection == Direction.SOUTH)
				focus.y = ((Tile)roomTiles.getTile(xCoord, yCoord+1)).getY();
			else if (randomDirection == Direction.WEST)
				focus.x = ((Tile)roomTiles.getTile(xCoord-1, yCoord)).getY();
			
			//System.out.println(focus.x + " " + focus.y);
		}
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
