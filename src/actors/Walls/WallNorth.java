package actors.Walls;

import actors.Quad;
import actors.ShapeType;

public class WallNorth extends Wall{

		//FIX ALL OF THESE BECAUSE QUADS MUST HAVE X AND Y COORDS AT THEIR CENTERS NOT TOP LEFT CORNERS
	
	//x and y will be where the top left corner of the entire wall is (pretending you are facing the wall)
	//so the x and y of the bottom wall would be at the bottom right corner of the sum of the quads making up the wall
	public WallNorth(float x, float y, int width, int height, boolean hasDoor) {
		super(x, y, width, height, hasDoor);

		wallRightOfDoor = new Quad(x+DOOR_WIDTH+3*(width-DOOR_WIDTH)/4, y+WALL_THICKNESS/2, (width-DOOR_WIDTH)/2, WALL_THICKNESS, ShapeType.QUAD);
		wallLeftOfDoor = new Quad(x+(width-DOOR_WIDTH)/4, y+WALL_THICKNESS/2, (width-DOOR_WIDTH)/2, WALL_THICKNESS, ShapeType.QUAD);
		wallWhenNoDoor = new Quad(x+(width)/2, y+WALL_THICKNESS/2, DOOR_WIDTH, WALL_THICKNESS, ShapeType.QUAD);
		
		setHardnessToZero();
	}

}
