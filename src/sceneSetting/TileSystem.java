package sceneSetting;

import java.util.ArrayList;

public class TileSystem {
	
	private int dimensionX; //how many tiles across
	private int dimensionY; //how many tiles down
	
	private Tile[][] tiles; 
	private int tileSize = 64;
	
	private int xCoordOfTileSystem = 0; //top left corner of entire tile system
	private int yCoordOfTileSystem = 0; //top left corner of entire tile system
	
	//should be 15 (width) by 9 (height)
	public TileSystem (int dimensionX, int dimensionY){
		this.dimensionX = dimensionX;
		this.dimensionY = dimensionY;
		
		tiles = new Tile[dimensionX][dimensionY];
		
		for (int i = 0; i < dimensionX; i++){
			for (int j = 0; j < dimensionY; j++){
				tiles[i][j] = new Tile(xCoordOfTileSystem + tileSize/2+j*tileSize, yCoordOfTileSystem + tileSize/2+j*tileSize, TileType.LAND); //generates tiles at the center of their tile locations
			}
		}
	}
	
	public void generateTiles(){
		
	}
	
	public void writeToCoordinate(int x, int y, TileType tileType){
		tiles[x][y].setTileType(tileType);
	}
	
	public int getDimensionX(){
		return dimensionX;
	}
	public int getDimensionY(){
		return dimensionY;
	}
	
	public Tile getTile(int x, int y){
		return tiles[x][y];
	}
}
