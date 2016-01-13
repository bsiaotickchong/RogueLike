package sceneSetting;

import java.util.ArrayList;

//reads an array of chars and converts it into a tilesystem (2d array of Tiles)
public class TileMapInterpreter {
	
	private char[] mapToRead;
	
	public class TileMapIntepreter{
		
	}
	
	public TileSystem convertTileMap(char[] mapToRead, int dimensionX, int dimensionY){
		this.mapToRead = mapToRead;
		
		TileSystem tileMap = new TileSystem(dimensionX, dimensionY);
		
		int counter = 0; //counts where you are in the char mapToRead array
		
		for (int y = 0; y < dimensionY; y++){
			for (int x = 0; x < dimensionX; x++){
				if (mapToRead[counter] == 'r'){
					tileMap.writeToCoordinate(x, y, TileType.ROCK);
				} else if (mapToRead[counter] == '^'){
					tileMap.writeToCoordinate(x, y, TileType.SPIKE);
				} else if (mapToRead[counter] == '-'){
					tileMap.writeToCoordinate(x, y, TileType.LAND);
				} else if (mapToRead[counter] == 'a'){
					tileMap.writeToCoordinate(x, y, TileType.ABYSS);
				} else if (mapToRead[counter] == 'i'){
					tileMap.writeToCoordinate(x, y, TileType.ICE);
				} else if (mapToRead[counter] == 'f'){
					tileMap.writeToCoordinate(x, y, TileType.FIRE);
				} else if (mapToRead[counter] == 'a'){
					tileMap.writeToCoordinate(x, y, TileType.ABYSS);
				} else if (mapToRead[counter] == 'e'){
					tileMap.writeToCoordinate(x, y, TileType.ENEMY);
				}
					
				
				else 
					tileMap.writeToCoordinate(x, y, TileType.LAND);
				
				counter++;
			}
		}
		
		return tileMap; //AHA!!!!
	}
}
