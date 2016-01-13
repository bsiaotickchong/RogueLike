package sceneSetting;

import actors.Actor;
import actors.Rock;
import actors.ShapeType;

public class Tile extends Actor{

	private Actor actorSpawnedOnTile;
	private TileType tileType = TileType.LAND;
	
	private int tileSize = 64;
	
	private int xCoord; //xcoord in terms of the tile system
	private int yCoord; //ycoord in terms of the tile system
	
	public Tile(float x, float y, Actor actor){
		super(x, y);
		
		actorSpawnedOnTile = actor;
		
		xCoord = ((int)x - (int)x%tileSize)/tileSize;
		yCoord = ((int)y - (int)y%tileSize)/tileSize;
	}
	
	public Tile(float x, float y, TileType tileType){
		super(x,y);
		
		this.tileType = tileType;
		
		xCoord = ((int)x - (int)x%tileSize)/tileSize;
		yCoord = ((int)y - (int)y%tileSize)/tileSize;
	}
	
	public void setTileType (TileType tileType){
		this.tileType = tileType;
		
		actorSpawnedOnTile = new Rock(x, y, tileSize, tileSize, ShapeType.QUAD);
	}
	
	public TileType getTileType(){
		return tileType;
	}
	
	public float getX(){
		return x;
	}
	
	public float getY(){
		return y;
	}
	
	public int getXCoord(){
		return xCoord; 
	}
	
	public int getYCoord(){
		return yCoord;
	}

}
