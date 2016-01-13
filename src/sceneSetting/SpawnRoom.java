package sceneSetting;

import actors.Enemies.Boss;

public class SpawnRoom extends Room {

	public SpawnRoom(int xCoord, int yCoord, RoomSize roomSize, FloorType floorType) {
		super(xCoord, yCoord, roomSize, floorType);
		// TODO Auto-generated constructor stub
	}
	
	public void generateTerrain(){
		TerrainFormationGenerator tfGenerator = new TerrainFormationGenerator(dimensionX, dimensionY);
		
		overLayTiles(tfGenerator.getSpawnTerrain(floorType));
	}

	public void generateEnemies(){
		//enemies.add(new Boss(400,300, 5)); //for testing purposes
	}
}
