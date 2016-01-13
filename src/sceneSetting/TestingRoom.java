package sceneSetting;

import actors.Rock;
import actors.ShapeType;
import actors.Enemies.*;
public class TestingRoom extends Room {
	
	public TestingRoom(int xCoord, int yCoord, RoomSize roomSize, FloorType floorType) {
		super(xCoord, yCoord, roomSize, floorType);
		roomName = "testing_room";
	}
	
	//these child's methods override the parent room method
	public void generateAbyss(){
		//System.out.println("generated abyss...");
	}
	
	public void generateRocks(){
		//System.out.println("generated rocks...");
		enemies.add(new Rock(400,150,50,50, ShapeType.CIRCLE));
		enemies.add(new Rock(600,300,50,300, ShapeType.QUAD));
		//enemies.add(new TestEnemy(400,300, .000002f));
		isWorking = true;
	}
	
	public void generateEnemies(){
		//System.out.println("generated enemies...");
		for (int y = 0; y < roomTiles.getDimensionY(); y++){
			for (int x = 0; x < roomTiles.getDimensionX(); x++){
				TileType tileType = roomTiles.getTile(x,y).getTileType();
				if (tileType == TileType.ENEMY){
					enemies.add(new TestEnemy(roomTiles.getTile(x, y).getX(),roomTiles.getTile(x, y).getY(), 1, roomTiles));
				}
			}
		}

		
	}
}
