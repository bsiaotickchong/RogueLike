package actors.Doors;

import actors.ShapeType;
import sceneSetting.Room;
import sceneSetting.TestingRoom;

public class DoorWest extends Door{
	
	public DoorWest(int x, int y, Room exitRoom) {
		super(20, 50, ShapeType.QUAD, exitRoom, exitRoom.getWidth()-30, exitRoom.getHeight()/2);
		
		placeDoor(x,y);
	}
}
