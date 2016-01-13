package sceneSetting;

import actors.Enemies.Boss;
import actors.Enemies.TestEnemy;

public class BossRoom extends Room {

	public BossRoom(int xCoord, int yCoord, RoomSize roomSize,
			FloorType floorType) {
		super(xCoord, yCoord, roomSize, floorType);
		// TODO Auto-generated constructor stub
	}
	
	public void generateTerrain(){
		TerrainFormationGenerator tfGenerator = new TerrainFormationGenerator(dimensionX, dimensionY);
		
		overLayTiles(tfGenerator.getBossTerrain(floorType));
	}
	
	public void generateEnemies(){
		enemies.add(new Boss(400,300, 5));
	}
}
