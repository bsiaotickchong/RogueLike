package actors.Doors;

import actors.ShapeType;
import sceneSetting.Room;
import sceneSetting.TestingRoom;

public class DoorNorth extends Door{
	
	public DoorNorth(int x, int y, Room exitRoom) {
		super(50, 20, ShapeType.QUAD, exitRoom, exitRoom.getWidth()/2, exitRoom.getHeight()-30);
		// TODO Auto-generated constructor stub
		
		placeDoor(x,y);
	}
}
