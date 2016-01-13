package actors.Walls;

import actors.Quad;
import actors.ShapeType;

public class WallEast extends Wall{

	//x and y will be where the top left corner of the entire wall is (pretending you are facing the wall)
	//so the x and y of the bottom wall would be at the bottom right corner of the sum of the quads making up the wall
	public WallEast(float x, float y, int width, int height, boolean hasDoor) {
		super(x, y, width, height, hasDoor);

		wallRightOfDoor = new Quad(x-WALL_THICKNESS/2, y+3*(height-DOOR_WIDTH)/4+DOOR_WIDTH, WALL_THICKNESS, (height-DOOR_WIDTH)/2, ShapeType.QUAD);
		wallLeftOfDoor = new Quad(x-WALL_THICKNESS/2, y+(height-DOOR_WIDTH)/4, WALL_THICKNESS, (height-DOOR_WIDTH)/2, ShapeType.QUAD);
		wallWhenNoDoor = new Quad(x-WALL_THICKNESS/2, y+(height)/2, WALL_THICKNESS, DOOR_WIDTH, ShapeType.QUAD);
		
		setHardnessToZero();
	}
	
}
