package defaultPackage;
import java.util.ArrayList;

import org.lwjgl.*;
import org.lwjgl.input.Mouse;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.*;
import org.newdawn.slick.*;

import sceneSetting.Floor;
import sceneSetting.FloorType;
import sceneSetting.Room;
import sceneSetting.RoomSize;
import sceneSetting.TestingRoom;
import actors.*;
import actors.Doors.Door;
import actors.Enemies.Enemy;
import actors.Walls.Wall;
import actors.Weapons.Weapon;
import actors.Enemies.*;

@SuppressWarnings("unused")
public class GameEngine {
	KeyListeners keyListener = new KeyListeners(); // instantiate the universal key listener
	RenderGraphics gRenderer = new RenderGraphics();
	PhysicsEngine physEngine = new PhysicsEngine();
	LogicEngine logicEngine = new LogicEngine();
	TimeKeeper timeKeeper = new TimeKeeper(); 
	
	
	Player1 player1 = new Player1(480,288,30,30, ShapeType.CIRCLE);
    Rock rock1 = new Rock(400,300,100,100, ShapeType.CIRCLE);
    Enemy enemy1 = new Enemy(500,500,30,30, ShapeType.CIRCLE, new MovementPattern(MovementType.GRAVITATE, player1));
    Enemy enemy2 = new Enemy(200,170,30,30, ShapeType.CIRCLE, new MovementPattern(MovementType.GRAVITATE, player1.x, player1.y, 100f));
    Rock rock2 = new Rock(300,300,100,100, ShapeType.QUAD);
    
    int screenHeight = 800;
	int screenWidth = 880;
    
    Camera camera = new Camera(400,300,screenWidth,screenHeight, new MovementPattern(MovementType.ATTRACT, player1));
    
	public static ArrayList<Actor> actorsOnScreen = new ArrayList<Actor>();
	
	boolean titleIsRunning = false;
	boolean gameIsRunning = true;
	public static boolean gameOver = false;
	static boolean GUIOn = true;
	
	static Room currentRoom;
	
	
	public void initialize() {
		Window display = new Window(screenWidth,screenHeight); //will eventually read from file to get window dimensions
		display.start();
		
		gRenderer.initResources(); //initialize resources for objects in room
		gRenderer.initGL(screenWidth, screenHeight);
		timeKeeper.start();
	}
		
	// game loop
	public void gameLoop() {
		
		logicEngine.setEnteringNewSceneBool(true); //initial scene
		
		Floor floor1 = new Floor(FloorType.small, FloorType.fields1);
		
		while (gameIsRunning) {
			
			timeKeeper.setLoops(0);
			
			while (timeKeeper.getTime() > timeKeeper.next_game_tick && timeKeeper.loops < timeKeeper.MAX_FRAMESKIP){
				//update game
				updateGame(floor1);
				
				timeKeeper.next_game_tick += timeKeeper.SKIP_TICKS;
				
				timeKeeper.incrementLoops();
				
				
			}
			
			displayGame(timeKeeper.getInterpolation());
			
			
			if (gameOver == true){
				try {
				    Thread.sleep(2000);
				} catch(InterruptedException ex) {
				    Thread.currentThread().interrupt();
				}
				
				player1 = new Player1(480,288,30,30, ShapeType.CIRCLE);
				
				gameOver = false;
				floor1 = new Floor(FloorType.small, FloorType.fields1);
				
				logicEngine.setEnteringNewSceneBool(true);
			}
			
			if (Display.isCloseRequested()){
				gameIsRunning = false;
				Display.destroy();
				System.exit(0);
			}
		}
	}
	
	
	
	public void updateGame(Floor currentFloor){
		//int deltaTime = timeKeeper.getDelta(); // gets time since last frame
		
		player1.act(actorsOnScreen);
		
		
		if (logicEngine.isEnteringNewScene()) { //first room loaded will be moved, set to spawn room of floor
			loadActorsToScreen(currentFloor.getRooms().get(0));
			logicEngine.setEnteringNewSceneBool(false);
			currentRoom = currentFloor.getRooms().get(0);
		}

		for (int i = 0; i<actorsOnScreen.size(); i++){
			
			try {
			if (actorsOnScreen.get(i) instanceof Enemy){
				actorsOnScreen.get(i).act(actorsOnScreen);
			}
			
			if (actorsOnScreen.get(i) instanceof Projectile){
				actorsOnScreen.get(i).act(actorsOnScreen);
			}
			
			if (actorsOnScreen.get(i) instanceof Weapon){
				actorsOnScreen.get(i).act(actorsOnScreen);
			}
			
			Actor actor = actorsOnScreen.get(i);
			if (actor instanceof Door){ //check if door is being traversed
				if (((Door) actor).isBeingTraversed()) {
					((Door) actor).setIsBeingTraversed(false); 
					// Fade screen out

					// load room
					loadActorsToScreen(((Door) actor).getExitRoom());
					currentRoom = ((Door) actor).getExitRoom();

					// place actor in new position
					((Door) actor).placePlayerAtSpawn(player1);
					// place camera on actor
					camera.x = player1.x;
					camera.y = player1.y;

					// Fade screen in
				}
			}
			
			} catch(IndexOutOfBoundsException e){
				//an actor was removed and caused error for other if statements
				i--; //makes sure an actor is not skipped because this one was removed
			}
		}
		
		updateActorsOnScreen();
	}
	
	public void updateActorsOnScreen() {
		keyListener.updatePlayerLocation(player1);
		
		

		for (int i = 0; i<actorsOnScreen.size(); i++){
            physEngine.moveActor(actorsOnScreen.get(i));
		}
		
		physEngine.checkCollisions(actorsOnScreen);
		
		// keep quad on the screen (will be removed later when solid quads are placed on screen)
		/*
		if (player1.x < 0)
			player1.x = 0;
		if (player1.x > currentRoom.getWidth())
			player1.x = currentRoom.getWidth();
		if (player1.y < 0)
			player1.y = 0;
		if (player1.y > currentRoom.getHeight())
			player1.y = currentRoom.getHeight();
		*/
		//System.out.println(logicEngine.angleBetweenCenters(player1, rock1));
		
		
	}
	
	int i = 0; //for accumulation buffer
	
	public void displayGame(float interpolation){
		
		gRenderer.sendActors(actorsOnScreen); //will eventually accept 3 arraylists for diff rendering levels
		
		
		
		/*///////// motion blur?
		
		if (i==0)
			GL11.glAccum(GL11.GL_LOAD, 1.0f / timeKeeper.FRAMES_TO_ACCUMULATE);
		else
			GL11.glAccum(GL11.GL_ACCUM, 1.0f / timeKeeper.FRAMES_TO_ACCUMULATE);
		
		i++;
		
		//i++;
		if (i >= timeKeeper.FRAMES_TO_ACCUMULATE){
			i = 0;
			//System.out.println(timeKeeper.next_game_tick);
			GL11.glAccum(GL11.GL_RETURN, 1.0f);
			
			try {
				Display.swapBuffers();
			} catch (LWJGLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		*////////////
		
		//translate screen to player position, must be before renderGL()
		
		//GL11.glViewport(-(int)camera.x+camera.getWidth()/2, (int)camera.y-camera.getHeight()/2, camera.getWidth()*2, camera.getHeight()*2); 
		//GL11.glViewport(-(int)camera.x, (int)camera.y-camera.getHeight(), camera.getWidth()*2, camera.getHeight()*2); 
		//GL11.glPushMatrix();
		//GL11.glMatrixMode(GL11.GL_MODELVIEW);
		//GL11.glLoadIdentity();
		
		//GL11.glOrtho(camera.x-camera.getWidth()/2, camera.x+camera.getWidth()/2, camera.y+camera.getHeight()/2, camera.y-camera.getHeight()/2, 1, -1); //ALLOWS YOU TO RENDER THINGS BASED ON THESE BOUNDARIES
		
		//GL11.glTranslatef(camera.vx, camera.vy, 0);
		
		//GL11.glPopMatrix();
		
		//GL11.glMatrixMode(GL11.GL_MODELVIEW);
		
		
		//render all actors to screen
		gRenderer.renderGL();
		
		timeKeeper.updateFPS();
		
		Display.update();
		Display.sync(60); // cap fps to 60fps
	}
	
	// load specified actors to screen
	public void loadActorsToScreen(Room room) {		//WHEN LOADING NEW ROOM, PLAYER MUST PERSIST BY BEING PART OF DIFFERENT ARRAYLIST for rendering as well
		
		actorsOnScreen.removeAll(actorsOnScreen);
		//actorsOnScreen.add(rock1);
		
		//actorsOnScreen = new ArrayList<Actor>();
		
		actorsOnScreen.add(player1);
		actorsOnScreen.add(camera);
		//actorsOnScreen.add(enemy1);
		//actorsOnScreen.add(enemy2);
		//actorsOnScreen.add(rock2);
		
		//ONCE NEW ROOM IS LOADED, ALL THINGS SET IN THE TESTINGROOM OBJECT ARE GONE/NEVER HAPPEN WHEN THEY ALREADY HAPPENED
		//WHERE THEY ERASED?
		//IS A NEW ROOM BEING CREATED WITHOUT THE STUFF?
		
		actorsOnScreen.addAll(room.getEnemies());
		actorsOnScreen.addAll(room.getProjectiles());
		actorsOnScreen.addAll(room.getItems());
		actorsOnScreen.addAll(room.getPickups());
		actorsOnScreen.addAll(room.getTerrain());
		actorsOnScreen.addAll(room.getDoors());
		//actorsOnScreen.addAll(room.getWalls());
		
		for (int i = 0; i < room.getWalls().size(); i++){
			actorsOnScreen.add(((Wall)room.getWalls().get(i)).getWallLeftOfDoor());
			
			if (!((Wall)room.getWalls().get(i)).wallHasDoor())
				actorsOnScreen.add(((Wall)room.getWalls().get(i)).getWallWhenNoDoor());
			
			actorsOnScreen.add(((Wall)room.getWalls().get(i)).getWallRightOfDoor());
		}
		
		/*
		for(Actor wall : room.getWalls()){
			actorsOnScreen.add(((Wall)wall).getWallLeftOfDoor());
			
			if (!((Wall)wall).wallHasDoor())
				actorsOnScreen.add(((Wall)wall).getWallWhenNoDoor());
			
			actorsOnScreen.add(((Wall)wall).getWallRightOfDoor());
		}*/
		
		//System.out.println(room.numOfDoors);
		
		//System.out.println("Room Coords: " + "X: " + room.getXCoord() + " Y: " + room.getYCoord());
		
		
		
		//for debugging
		for(Actor a : actorsOnScreen) {
			//System.out.print(a + ": ");
			//System.out.println("X: " + a.x + " Y: " + a.y);
			if (a instanceof Wall){
				System.out.println(a + ": " + a.x + " | " + a.y);
			}
		}
		
		//background images will load based on the room type/floor
        
        //enemy1.getMovePattern().setActorFocus(player1);
        //enemy2.getMovePattern().setActorFocus(enemy1);

	}
	
	public void loadFloor(){
		//load textures
		//generate floor?
	}
	
	
}
