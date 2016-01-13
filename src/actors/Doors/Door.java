package actors.Doors;

import actors.Actor;
import actors.Quad;
import actors.ShapeType;
import sceneSetting.Floor;
import sceneSetting.Room;
import sceneSetting.TestingRoom;

public class Door extends Quad{
	
	protected Room exitRoom; //has exitroom variable so you can make other rooms besides the one you're next to the exit location
	protected boolean isBeingTraversed = false;
	protected int xSpawnCoord;
	protected int ySpawnCoord;
	protected boolean isOpen;
	
	public Door(int width, int height, ShapeType hitboxShape, Room exitRoom, int xSpawnCoord, int ySpawnCoord) {
		super(0,0,width, height, hitboxShape);
		this.exitRoom = exitRoom;
		this.xSpawnCoord = xSpawnCoord;
		this.ySpawnCoord = ySpawnCoord;
		//System.out.println(exitRoom);
	}
	
	//x and y values of the door will be automatically set when the room instantiates all its doors
	public void placeDoor(float x, float y){
		this.x = x;
		this.y = y;
	}
	
	public void setExitRoom(Room exitRoom){
		this.exitRoom = exitRoom;
	}
	
	//returns exitRoom so you can get the (x,y) coordinates of the room you are moving to
	public Room getExitRoom(){
		if (exitRoom instanceof TestingRoom){
			//System.out.println("is testing room");
			//System.out.println("Doors in room: " + exitRoom.getDoors().size());
			return exitRoom;
		}
		return exitRoom;
	}
	
	public boolean isBeingTraversed(){
		return isBeingTraversed;
	}
	
	public void setIsBeingTraversed(boolean isBeingTraversed){
		this.isBeingTraversed = isBeingTraversed;
	}
	
	public void placePlayerAtSpawn(Actor player){
		player.x = xSpawnCoord;
		player.y = ySpawnCoord;
		
		player.vx = 0;
		player.vy = 0;
	}
}
