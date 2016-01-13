package actors.Walls;

import actors.MovementPattern;
import actors.MovementType;
import actors.Quad;
import actors.ShapeType;

public class Wall extends Quad{

	//wall right of door
	protected Quad wallRightOfDoor;
	
	//wall when no door
	protected Quad wallWhenNoDoor;
	
	//wall left of door
	protected Quad wallLeftOfDoor;
	
	protected final int DOOR_WIDTH = 50;
	protected final int DOOR_THICKNESS = 20;
	protected final int WALL_THICKNESS = 100;
	
	protected boolean hasDoor = false;
	
	public Wall(float x, float y, int width, int height, boolean hasDoor) {
		super(x, y, width, height, ShapeType.QUAD);
		
		this.hasDoor = hasDoor;
		
		this.movePattern = new MovementPattern(MovementType.LOCKED);
	}
	
	public Quad getWallRightOfDoor(){
		return wallRightOfDoor;
	}
	public Quad getWallLeftOfDoor(){
		return wallLeftOfDoor;
	}
	public Quad getWallWhenNoDoor(){
		return wallWhenNoDoor;
	}
	
	public boolean wallHasDoor(){
		return hasDoor;
	}
	
	public void setHasDoor (boolean hasDoor){
		this.hasDoor = hasDoor;
	}
	
	public void setHardnessToZero(){
		wallRightOfDoor.hardness = 0;
		wallWhenNoDoor.hardness = 0;
		wallLeftOfDoor.hardness = 0;
	}
}
