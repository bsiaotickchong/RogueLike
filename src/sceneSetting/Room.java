package sceneSetting;
import java.util.ArrayList;

import actors.*;
import actors.Doors.Door;
import actors.Enemies.TestEnemy;
import actors.Walls.Wall;

public class Room {
	
	protected String roomName = "blank"; //will be diff for rooms that inherit from this class object
	
	protected ArrayList<Actor> enemies = new ArrayList<Actor>();
	protected ArrayList<Actor> projectiles = new ArrayList<Actor>();
	protected ArrayList<Actor> pickups = new ArrayList<Actor>();
	protected ArrayList<Actor> items = new ArrayList<Actor>();
	protected ArrayList<Actor> terrain = new ArrayList<Actor>();
	protected ArrayList<Actor> doors = new ArrayList<Actor>();
	protected ArrayList<Actor> walls = new ArrayList<Actor>();
	
	protected int xCoord; //x placement on arbitrary Floor plane
	protected int yCoord; //y placement on arbitrary Floor plane
	
	protected int width; //width of room in pixels
	protected int height; //height of room in pixels
	protected int dimensionX; //how many tiles columns
	protected int dimensionY; //how many tile rows
	protected int tileSize = 64;
	
	protected FloorType floorType;
	
	public int numOfDoors = 0;
	
	protected TileSystem roomTiles = new TileSystem(15, 9);
	
	public boolean isWorking = false;
	
	public Room (int xCoord, int yCoord, RoomSize roomSize, FloorType floorType){
		this.xCoord = xCoord;
		this.yCoord = yCoord;
		this.floorType = floorType;
		if (roomSize == RoomSize.small){ //64px square tiles
			dimensionX = 15;
			dimensionY = 9;
			
			width = dimensionX*tileSize; //15 tiles 960px
			height = dimensionY*tileSize; //9 tiles 576px
		}
		else if (roomSize == RoomSize.medium){ //not happening with problematic ortho and viewport
			width = 1000;
			height = 800;
		}
		else if (roomSize == RoomSize.large){ //not happening with problematic ortho and viewport
			width = 1200;
			height = 1000;
		}
		//add other room sizes later
		
		generateRoom(); //when room is constructed, the room is generated automatically
		//System.out.println(this);
	}
	
	public void generateRoom(){
		generateTerrain();
		generateEnemies();
		
		//places actors on tiles based on what Tile type each tile is
		for (int y = 0; y < roomTiles.getDimensionY(); y++){
			for (int x = 0; x < roomTiles.getDimensionX(); x++){
				TileType tileType = roomTiles.getTile(x,y).getTileType();
				switch (tileType){
				case ROCK:
					terrain.add(new Rock(x*tileSize + tileSize/2, y*tileSize + tileSize/2, tileSize, tileSize, ShapeType.QUAD));
					break;
				case ABYSS:
					//add abyss (like a rock except indestructable)
					break;
				case SPIKE:
					//add spike, no collision handling but hurts player
					break;
				case FIRE:
					//add fire, collision handling + damage
					break;
				case MUD:
					//changes max velocity of player, sets friction high 
					break;
				case ICE:
					//sets friction low
					break;
				case LAND:
					//nothing
					break;
				}
			}
		}
	}
	
	public void generateTerrain(){
		TerrainFormationGenerator tfGenerator = new TerrainFormationGenerator(dimensionX, dimensionY);
		
		overLayTiles(tfGenerator.getRandTerrain(floorType));
	}
	
	public void generateRocks(){
		enemies.add(new Rock(400,300,100,100, ShapeType.CIRCLE));
		System.out.println("ERROR: LOADED ROOM INSTEAD OF TESTINGROOM");
	}
	
	public void generateEnemies(){
		//to be filled in child classes
	}
	
	//floor will add doors via this method
	public void addDoor(Door door){
		doors.add(door);
		numOfDoors++;
	}
	
	//floor will add walls via this method
	public void addWall(Wall wall){
		walls.add(wall);
	}
	
	public int getHeight(){
		return height;
	}
	
	public int getWidth(){
		return width;
	}
	
	public ArrayList<Actor> getEnemies(){
		return enemies;
	}
	public ArrayList<Actor> getProjectiles(){
		return projectiles;
	}
	public ArrayList<Actor> getPickups(){
		return pickups;
	}
	public ArrayList<Actor> getItems(){
		return items;
	}
	public ArrayList<Actor> getTerrain(){
		return terrain;
	}
	public ArrayList<Actor> getDoors(){
		return doors;
	}
	public ArrayList<Actor> getWalls(){
		return walls;
	}
	
	public int getXCoord(){
		return xCoord;
	}
	
	public int getYCoord(){
		return yCoord;
	}
	
	//takes new tilesystem and overlays it on the roomtiles tilesystem
	protected void overLayTiles(TileSystem newTiles){
		for (int y = 0; y < roomTiles.getDimensionY(); y++){
			for (int x = 0; x < roomTiles.getDimensionX(); x++){
				TileType tileType =  newTiles.getTile(x,y).getTileType();
				switch (tileType){
				case ROCK:
					roomTiles.writeToCoordinate(x, y, tileType);
					break;
				case ABYSS:
					roomTiles.writeToCoordinate(x, y, tileType);
					break;
				case SPIKE:
					roomTiles.writeToCoordinate(x, y, tileType);
					break;
				case FIRE:
					roomTiles.writeToCoordinate(x, y, tileType);
					break;
				case MUD:
					roomTiles.writeToCoordinate(x, y, tileType);
					break;
				case ICE:
					roomTiles.writeToCoordinate(x, y, tileType);
					break;
				case LAND:
					roomTiles.writeToCoordinate(x, y, tileType);
					break;
				case ENEMY:
					roomTiles.writeToCoordinate(x, y, tileType);
					break;
				}
			}
		}
	}
}
