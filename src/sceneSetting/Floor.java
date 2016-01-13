package sceneSetting;

import java.util.ArrayList;
import java.util.Random;

import actors.Actor;
import actors.Doors.DoorEast;
import actors.Doors.DoorNorth;
import actors.Doors.DoorSouth;
import actors.Doors.DoorWest;
import actors.Walls.WallEast;
import actors.Walls.WallNorth;
import actors.Walls.WallSouth;
import actors.Walls.WallWest;

public class Floor {
	
	protected FloorType size;
	protected int numOfRooms;
	
	protected ArrayList<Room> rooms = new ArrayList<Room>();
	
	protected final int DOOR_WIDTH = 50;
	protected final int DOOR_THICKNESS = 20;
	protected final int WALL_THICKNESS = 100;
	
	protected FloorType floorType; //fields, caves, etc.
	
	protected boolean bossRoomPlaced = false;
	
	public Floor (FloorType size, FloorType floorType){
		this.size = size;
		this.floorType = floorType;
		if (size == FloorType.small) {
			numOfRooms = 7;
		}
		else if (size == FloorType.medium) {
			numOfRooms = 12;
		}
		else if (size == FloorType.large) {
			numOfRooms = 19;
		}
		else{
			numOfRooms = 0;
		}
		
		generateRooms();
		generateDoorsForRooms();
		generateWallsForRooms();
	}
	
	
	
	public void generateRooms(){

		
		Random rand = new Random();
		
		int xCoord = 0; //xCoord of room detector/placer
		int yCoord = 0; //yCoord of room detector/placer
		Direction randDirection; 
		boolean isEmpty = false;
		
		
		rooms.add(new SpawnRoom(xCoord, yCoord, RoomSize.small, floorType)); //initial spawn room?
		
		while (numOfRooms>0 || !bossRoomPlaced){
			
			RoomSize roomSize = RoomSize.small; //this size might be randomized later on, either per floor or per room
			
			int randNum = rand.nextInt(4);
			//this switch case sets the direction that the room will go in, prevents num confusion
			/*switch (randNum) {
			case 0: {
				randDirection = Direction.NORTH;
				break;
			}
			case 1: {
				randDirection = Direction.WEST;
				break;
			}
			case 2: {
				randDirection = Direction.EAST;
				break;
			}
			case 3: {
				randDirection = Direction.SOUTH;
				break;
			}
			}*/
			randDirection = Direction.values()[rand.nextInt(4)];
			//calls checkDirection
			//then moves "xCoord" and "yCoord" of detector REGARDLESS of finding an available direction
			switch (randDirection) {
			case NORTH: 
				isEmpty = directionIsEmpty(Direction.NORTH, xCoord, yCoord);
				yCoord += 1;
				break;

			case WEST: 
				isEmpty = directionIsEmpty(Direction.WEST, xCoord, yCoord);
				xCoord -= 1;
				break;
			
			case EAST: 
				isEmpty = directionIsEmpty(Direction.EAST, xCoord, yCoord);
				xCoord += 1;
				break;
			
			case SOUTH: 
				isEmpty = directionIsEmpty(Direction.SOUTH, xCoord, yCoord);
				yCoord -= 1;
				break;
			}
			
			if (!bossRoomPlaced && isEmpty && numOfRooms == 0){
				rooms.add(new BossRoom(xCoord, yCoord, RoomSize.small, floorType));
				bossRoomPlaced = true;
			}
			else if (isEmpty){
				rooms.add(new TestingRoom(xCoord, yCoord, roomSize, floorType));
				numOfRooms--;
				
				//System.out.println(xCoord + " | " + yCoord);
				//System.out.println("room number: "+numOfRooms);
			} 
			
		}
		
	}
	
	
	
	//checks the direction and adds a room if spot is available
	//if you want a room with only one adjacent room, check in all 4 directions and count the trues
	//returns true if there is no room in the direction you gave as a parameter
	//returns false if the space is empty
	public boolean directionIsEmpty(Direction randDirection, int xCoord, int yCoord){
		
		int xOfCurrRoom = 0;
		int yOfCurrRoom = 0;

		switch (randDirection) {
		case NORTH: 
			yCoord += 1;
			break;

		case WEST: 
			xCoord -= 1;
			break;
		
		case EAST: 
			xCoord += 1;
			break;
		
		case SOUTH: 
			yCoord -= 1;
			break;
		}
		
		//System.out.println(xOfCurrRoom + " | " + yOfCurrRoom);
		
		for (int i = 0; i < rooms.size(); i++) {
			try{
			xOfCurrRoom = rooms.get(i).xCoord;
			yOfCurrRoom = rooms.get(i).yCoord;
			} catch(IndexOutOfBoundsException e){
				xOfCurrRoom = 999;
				yOfCurrRoom = 999;
			}
			
			//if direction does not have same x or y
			if (xOfCurrRoom == xCoord && yOfCurrRoom == yCoord){
				
				//System.out.println(xOfCurrRoom + " | " + yOfCurrRoom);
				//System.out.println(xCoord + " | " + yCoord);
				return false;
			}
			//else
			//	System.out.println("space taken: " + xOfCurrRoom + " | " + yOfCurrRoom);
		}
		
		return true;
	}
	
	//places north south east west doors if there are rooms adjacent
	public void generateDoorsForRooms(){
		
		Direction randDirection;
		
		for (int i = 0; i < rooms.size(); i++) { //go through all rooms in rooms arraylist
			for (int j = 0; j < 4; j++) { //check all 4 directions
				randDirection = Direction.values()[j];

				//check direction
				if (!directionIsEmpty(randDirection, rooms.get(i).xCoord, rooms.get(i).yCoord)){
					switch (randDirection) {
					case NORTH: 
						rooms.get(i).addDoor(new DoorNorth(rooms.get(i).getWidth()/2, 0-25, getDirection(randDirection, rooms.get(i).xCoord, rooms.get(i).yCoord)));
						break;

					case WEST: 
						rooms.get(i).addDoor(new DoorWest(0-25, rooms.get(i).getHeight()/2, getDirection(randDirection, rooms.get(i).xCoord, rooms.get(i).yCoord)));
						break;
					
					case EAST: 
						rooms.get(i).addDoor(new DoorEast(rooms.get(i).getWidth()+25, rooms.get(i).getHeight()/2, getDirection(randDirection, rooms.get(i).xCoord, rooms.get(i).yCoord)));
						break;
					
					case SOUTH: 
						rooms.get(i).addDoor(new DoorSouth(rooms.get(i).getWidth()/2, rooms.get(i).getHeight()+25, getDirection(randDirection, rooms.get(i).xCoord, rooms.get(i).yCoord)));
						break;
					}
				}
			}
		}
	}
	
	/*	walls placed like:
	 *  N---------E
	 *  |		  |
	 *  |		  |
	 *  W---------S
	 */
	public void generateWallsForRooms(){
		Direction randDirection;
		for (int i = 0; i < rooms.size(); i++){
			for (int j = 0; j < 4; j++){
				randDirection = Direction.values()[j];
				boolean wallPlaced = false;
				//if direction has door, set hasDoor = true;
				switch (randDirection) {
				case NORTH: 
					wallPlaced = false;
					try {
					for (int k = 0; k < rooms.get(i).doors.size(); k++){
						if (rooms.get(i).doors.get(k) instanceof DoorNorth) {
							rooms.get(i).addWall(new WallNorth(0-WALL_THICKNESS, 0-WALL_THICKNESS, rooms.get(i).getWidth()+2*WALL_THICKNESS, WALL_THICKNESS, true));
							wallPlaced = true;
							break; //will this break out of the switch case or just the for loop?
						}
					}
					} catch (NullPointerException e){
						
					}
					if (!wallPlaced)
						rooms.get(i).addWall(new WallNorth(0-WALL_THICKNESS, 0-WALL_THICKNESS, rooms.get(i).getWidth()+2*WALL_THICKNESS, WALL_THICKNESS, false));
					break;
					
				case WEST:
					wallPlaced = false;
					try {
					for (int k = 0; k < rooms.get(i).doors.size(); k++){
						if (rooms.get(i).doors.get(k) instanceof DoorWest) {
							rooms.get(i).addWall(new WallWest(0-WALL_THICKNESS, rooms.get(i).getHeight(), WALL_THICKNESS, rooms.get(i).getHeight(), true));
							wallPlaced = true;
							break;
						}
					}
					} catch (NullPointerException e){
						
					}
					
					if (!wallPlaced)
						rooms.get(i).addWall(new WallWest(0-WALL_THICKNESS, rooms.get(i).getHeight(), WALL_THICKNESS, rooms.get(i).getHeight(), false));
					break;
				
				case EAST: 
					wallPlaced = false;
					try {
					for (int k = 0; k < rooms.get(i).doors.size(); k++){
						if (rooms.get(i).doors.get(k) instanceof DoorEast) {
							rooms.get(i).addWall(new WallEast(rooms.get(i).getWidth()+WALL_THICKNESS, 0, WALL_THICKNESS, rooms.get(i).getHeight(), true));
							wallPlaced = true;
							break;
						}
					}
					} catch (NullPointerException e){
						
					}
					if (!wallPlaced)
						rooms.get(i).addWall(new WallEast(rooms.get(i).getWidth()+WALL_THICKNESS, 0, WALL_THICKNESS, rooms.get(i).getHeight(), false));
					break;
				
				case SOUTH: 
					wallPlaced = false;
					try {
					for (int k = 0; k < rooms.get(i).doors.size(); k++){
						if (rooms.get(i).doors.get(k) instanceof DoorSouth) {
							rooms.get(i).addWall(new WallSouth(rooms.get(i).getWidth()+WALL_THICKNESS, rooms.get(i).getHeight()+WALL_THICKNESS, rooms.get(i).getWidth()+2*WALL_THICKNESS, WALL_THICKNESS, true));
							wallPlaced = true;
							break;
						}
					}
					} catch (NullPointerException e){
						
					}
					if (!wallPlaced)
						rooms.get(i).addWall(new WallSouth(rooms.get(i).getWidth()+WALL_THICKNESS, rooms.get(i).getHeight()+WALL_THICKNESS, rooms.get(i).getWidth()+2*WALL_THICKNESS, WALL_THICKNESS, false));
					break;
				}
			}
		
		}
	}
	
	//returns the room north south east or west of coordinates given
	public Room getDirection(Direction direction, int xCoord, int yCoord) {

		int xOfCurrRoom = 0;
		int yOfCurrRoom = 0;

		switch (direction) {
		case NORTH:
			yCoord += 1;
			break;

		case WEST:
			xCoord -= 1;
			break;

		case EAST:
			xCoord += 1;
			break;

		case SOUTH:
			yCoord -= 1;
			break;
		}

		for (int i = 0; i < rooms.size(); i++) {
			xOfCurrRoom = rooms.get(i).xCoord;
			yOfCurrRoom = rooms.get(i).yCoord;
			// if direction does not have same x or y
			if (xOfCurrRoom == xCoord && yOfCurrRoom == yCoord) {
				return rooms.get(i);
			}
		}
		
		//Room asdf; //will not be reached hopefully
		System.out.println("ERROR-Floor: room not found");
		return new TestingRoom(xOfCurrRoom, yOfCurrRoom, RoomSize.small, floorType); //THIS IS BEING EXECUTED INSTEAD!!!
	}
	
	public ArrayList<Room> getRooms(){
		return rooms;
	}
	
	//instead of having an exit room, have exit room coordinates?
}
