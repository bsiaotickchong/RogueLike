package actors.Doors;

import actors.ShapeType;
import sceneSetting.Room;
import sceneSetting.TestingRoom;

public class DoorEast extends Door{

	
	public DoorEast(int x, int y, Room exitRoom) {
		super(20, 50, ShapeType.QUAD, exitRoom, 30, exitRoom.getHeight()/2);
		
		placeDoor(x,y);
	}

}
