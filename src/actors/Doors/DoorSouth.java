package actors.Doors;

import actors.ShapeType;
import sceneSetting.Room;
import sceneSetting.TestingRoom;

public class DoorSouth extends Door{

	
	public DoorSouth(int x, int y, Room exitRoom) {
		super(50, 20, ShapeType.QUAD, exitRoom, exitRoom.getWidth()/2, 30);
		
		placeDoor(x,y);
	}

}
