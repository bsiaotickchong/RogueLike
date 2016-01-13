package actors.Enemies;

import defaultPackage.LogicEngine;
import defaultPackage.PhysicsEngine;
import actors.Actor;
import actors.MovementPattern;
import sceneSetting.Direction;
import sceneSetting.Tile;
import sceneSetting.TileSystem;
//will eventually mainly focus on the tiles
public class GenericFocus extends Actor{
	
	Tile focusTile = null;
	LogicEngine logic = new LogicEngine();
	PhysicsEngine physEngine = new PhysicsEngine();
	
	
	public GenericFocus(float x, float y) {
		super(x, y);
		// TODO Auto-generated constructor stub
	}
	
	public GenericFocus(Tile tile){

		super(tile.getX(),tile.getY());
		focusTile = tile;
	}
	
	public void move(Direction dir, float walkingPace, TileSystem tileSystem){
		this.vx = 0;
		this.vy = 0;
		switch (dir) {
		case NORTH:
			this.vy += walkingPace;
			break;
		case SOUTH:
			this.vy -= walkingPace;
			break;
		case EAST:
			this.vx += walkingPace;
			break;
		case WEST:
			this.vx -= walkingPace;
			break;
		}
	}
	
	public void moveTowardsFocus(Actor actorFocus){
		
	}
	
	public void moveTowardsFocusTile(){
		
	}

	public void setFocusTile(Tile tile){
		x = tile.getX();
		y = tile.getY();
		
		focusTile = tile;
	}
	
	public Tile getFocusTile(){
		return focusTile;
	}
}
