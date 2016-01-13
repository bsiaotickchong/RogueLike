package sceneSetting;

import java.util.Random;

public class TerrainFormationGenerator {

	private TileSystem terrainTiles;
	private int numOfFormationsPerFloor = 5;
	
	private int startOfFieldsFloor = 0;
	private int startOfCaveFloor = 5; //
	private int startOfMinesFloor = 10;
	private int startOfHellFloor = 15;
	private int startOfAbyssFloor = 20;
	private int startOfHeavenFloor = 25;
	
	
	
	//declare Random here to prevent duplicate suedorandom nums
	private Random rand = new Random();
	
	public TerrainFormationGenerator(int dimensionX, int dimensionY){
		terrainTiles = new TileSystem(dimensionX, dimensionY);
		
		interpretMaps(); //interpretmaps is not working
	}
	
	public TileSystem getSpawnTerrain(FloorType floorType){
		return spawnTerrain;
	}
	
	public TileSystem getRandTerrain(FloorType floorType){
		return getTerrainFormation(rand.nextInt(numOfFormationsPerFloor), floorType);
	}
	
	public TileSystem getBossTerrain(FloorType floorType){
		return bossTerrain;
	}
	
	public TileSystem getTerrainFormation(int randNum, FloorType floorType){
		TerrainFormationName randFormation = TerrainFormationName.values()[randNum];
		
		switch (floorType){
		case fields1:
			randFormation = TerrainFormationName.values()[randNum+startOfFieldsFloor];
			break;
		case fields2:
			randFormation = TerrainFormationName.values()[randNum+startOfFieldsFloor];
			break;
		case cave1:
			randFormation = TerrainFormationName.values()[randNum+startOfCaveFloor];
			break;
		case cave2:
			randFormation = TerrainFormationName.values()[randNum+startOfCaveFloor];
			break;
		case mines1:
			randFormation = TerrainFormationName.values()[randNum+startOfMinesFloor];
			break;
		case mines2:
			randFormation = TerrainFormationName.values()[randNum+startOfMinesFloor];
			break;
		case hell1:
			randFormation = TerrainFormationName.values()[randNum+startOfHellFloor];
			break;
		case hell2:
			randFormation = TerrainFormationName.values()[randNum+startOfHellFloor];
			break;
		case abyss1:
			randFormation = TerrainFormationName.values()[randNum+startOfAbyssFloor];
			break;
		case heaven1:
			randFormation = TerrainFormationName.values()[randNum+startOfHeavenFloor];
			break;
		}
		
		switch (randFormation) {
		case FIELDS_01:
			return fields_01Terrain;
		case FIELDS_02:
			return fields_02Terrain;
		case FIELDS_03:
			return fields_03Terrain;
		case FIELDS_04:
			return fields_04Terrain;
		case FIELDS_05:
			return fields_05Terrain;
		}
		
		//error
		return terrainTiles;
	}
		
	private TileSystem bossTerrain;	
	private TileSystem spawnTerrain;
	private TileSystem fields_01Terrain;
	private TileSystem fields_02Terrain;
	private TileSystem fields_03Terrain;
	private TileSystem fields_04Terrain;
	private TileSystem fields_05Terrain;
	
	private void interpretMaps(){
		TileMapInterpreter tileMapInterpreter = new TileMapInterpreter();
		
		bossTerrain = tileMapInterpreter.convertTileMap(bossTerrainMap, terrainTiles.getDimensionX(), terrainTiles.getDimensionY());
		spawnTerrain = tileMapInterpreter.convertTileMap(spawnTerrainMap, terrainTiles.getDimensionX(), terrainTiles.getDimensionY());
		fields_01Terrain = tileMapInterpreter.convertTileMap(fields_01TerrainMap, terrainTiles.getDimensionX(), terrainTiles.getDimensionY());
		fields_02Terrain = tileMapInterpreter.convertTileMap(fields_02TerrainMap, terrainTiles.getDimensionX(), terrainTiles.getDimensionY());
		fields_03Terrain = tileMapInterpreter.convertTileMap(fields_03TerrainMap, terrainTiles.getDimensionX(), terrainTiles.getDimensionY());
		fields_04Terrain = tileMapInterpreter.convertTileMap(fields_04TerrainMap, terrainTiles.getDimensionX(), terrainTiles.getDimensionY());
		fields_05Terrain = tileMapInterpreter.convertTileMap(fields_05TerrainMap, terrainTiles.getDimensionX(), terrainTiles.getDimensionY());
	}
	
	private char[] bossTerrainMap = 
		{'r','-','-','-','-','-','-','-','-','-','-','-','-','-','r',
		 '-','-','-','-','-','-','-','-','-','-','-','-','-','-','-',
		 '-','-','-','-','-','-','-','-','-','-','-','-','-','-','-',
		 '-','-','-','-','-','-','-','-','-','-','-','-','-','-','-',
		 '-','-','-','-','-','-','-','-','-','-','-','-','-','-','-',
		 '-','-','-','-','-','-','-','-','-','-','-','-','-','-','-',
		 '-','-','-','-','-','-','-','-','-','-','-','-','-','-','-',
		 '-','-','-','-','-','-','-','-','-','-','-','-','-','-','-',
		 'r','-','-','-','-','-','-','-','-','-','-','-','-','-','r'};
	
	private char[] spawnTerrainMap = 
		{'-','-','-','-','-','-','-','-','-','-','-','-','-','-','-',
		 '-','-','-','-','-','-','-','-','-','-','-','-','-','-','-',
		 '-','-','-','-','-','-','-','-','-','-','-','-','-','-','-',
		 '-','-','-','-','-','-','-','-','-','-','-','-','-','-','-',
		 '-','-','-','-','-','-','-','-','-','-','-','-','-','-','-',
		 '-','-','-','-','-','-','-','-','-','-','-','-','-','-','-',
		 '-','-','-','-','-','-','-','-','-','-','-','-','-','-','-',
		 '-','-','-','-','-','-','-','-','-','-','-','-','-','-','-',
		 '-','-','-','-','-','-','-','-','-','-','-','-','-','-','-'};
	
	private char[] fields_01TerrainMap = 
		{'r','r','r','-','-','-','-','-','-','-','r','r','r','r','-',
		 'r','-','-','-','-','r','r','-','-','-','-','-','-','-','-',
		 '-','-','-','-','-','r','-','-','-','-','-','e','-','-','-',
		 '-','-','-','-','r','-','-','-','-','-','-','-','-','-','-',
		 '-','-','-','-','-','-','-','-','-','r','-','-','-','-','-',
		 '-','-','-','-','-','-','-','-','-','r','-','-','-','-','-',
		 '-','-','-','-','e','-','-','-','r','-','-','r','r','-','-',
		 'r','-','r','-','-','-','-','r','-','-','-','-','-','-','-',
		 'r','r','-','-','-','-','-','-','-','-','-','-','-','-','-'};
	
	private char[] fields_02TerrainMap = 
		{'r','-','-','-','-','-','-','-','-','-','-','-','-','-','r',
		 '-','-','-','-','-','-','r','-','-','-','-','-','-','-','-',
		 '-','-','-','-','e','-','r','-','-','-','e','-','-','-','-',
		 '-','-','-','-','-','-','-','r','-','-','-','-','-','-','-',
		 '-','-','r','r','r','r','-','r','-','r','r','r','r','-','-',
		 '-','-','-','-','-','-','-','r','-','-','-','-','-','-','-',
		 '-','-','-','-','e','-','-','-','r','-','e','-','-','-','-',
		 '-','-','-','-','-','-','-','-','r','-','-','-','-','-','-',
		 'r','-','-','-','-','-','-','-','-','-','-','-','-','-','r'};
	
	private char[] fields_03TerrainMap = 
		{'-','-','-','-','-','-','-','-','-','-','-','-','-','-','-',
		 '-','-','-','-','-','-','-','-','-','-','-','-','-','-','-',
		 '-','-','r','r','r','r','r','-','-','r','r','r','r','-','-',
		 '-','-','r','-','e','-','-','-','-','-','e','-','r','-','-',
		 '-','-','-','-','-','-','-','-','-','-','-','-','-','-','-',
		 '-','-','r','-','e','-','-','-','-','-','e','-','r','-','-',
		 '-','-','r','r','r','r','-','-','r','r','r','r','r','-','-',
		 '-','-','-','-','-','-','-','-','-','-','-','-','-','-','-',
		 '-','-','-','-','-','-','-','-','-','-','-','-','-','-','-'};
	
	private char[] fields_04TerrainMap = 
		{'-','-','-','-','-','-','-','-','-','-','-','-','-','-','-',
		 '-','-','-','-','-','r','-','-','-','-','-','-','-','-','-',
		 '-','-','e','-','-','r','-','-','r','-','-','-','-','-','-',
		 '-','-','-','-','-','-','-','e','-','-','-','r','-','-','-',
		 '-','-','e','-','r','-','-','-','-','-','r','-','e','-','-',
		 '-','-','-','r','-','-','r','-','-','-','-','r','-','-','-',
		 '-','-','r','-','-','-','-','-','-','-','-','-','r','-','-',
		 '-','-','-','-','-','-','-','-','-','r','-','e','-','-','-',
		 '-','-','-','-','-','-','-','-','-','-','-','-','-','-','-'};
	
	private char[] fields_05TerrainMap = 
		{'-','-','-','-','-','-','-','-','-','-','-','-','-','-','-',
		 '-','-','-','-','-','-','r','-','r','-','-','-','-','-','-',
		 '-','-','-','-','r','r','-','-','-','r','r','-','-','-','-',
		 '-','-','-','r','r','-','-','e','-','-','r','r','-','-','-',
		 '-','-','-','r','-','e','-','-','-','e','-','r','-','-','-',
		 '-','-','-','r','r','-','-','e','-','-','r','r','-','-','-',
		 '-','-','-','-','r','r','-','-','-','r','r','-','-','-','-',
		 '-','-','-','-','-','-','r','-','r','-','-','-','-','-','-',
		 '-','-','-','-','-','-','-','-','-','-','-','-','-','-','-'};
}
