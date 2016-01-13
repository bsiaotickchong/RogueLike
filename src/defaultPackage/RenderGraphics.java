package defaultPackage;
import java.awt.Font;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;
import org.lwjgl.LWJGLException;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import sceneSetting.Direction;
import sceneSetting.SpawnRoom;
import actors.*;
import actors.Doors.Door;
import actors.Enemies.Enemy;
import actors.Walls.Wall;
import actors.Weapons.Stick;
import actors.Weapons.Sword;
import actors.Weapons.Weapon;

public class RenderGraphics {
	
	//Quad quad = new Quad(0,0,0,0);
	//Quad quad2 = new Quad(0,0,0,0);
	ArrayList <Actor> actorsOnScreen; //will eventually be replaced
	ArrayList <Actor> bottomLevelActors; //rendered on bottom (first)
	ArrayList <Actor> midLevelActors; //rendered in middle (second)
	ArrayList <Actor> topLevelActors; //rendered on top (last)
	Actor player1; //player is separate so that he persists through rooms
	
	Texture defaultTexture;
	Texture backgroundTexture;
	Texture player1_backTexture;
	Texture wall_default;
	Texture firebullet;
	Texture fieldrock1;
	Texture normalFullHeart;
	Texture halfHeart;
	
	Texture invisible;
	
	Texture fields1;
	Texture greenmist;
	
	Texture weapon_stick;
	Texture sword;
	
	Color lightRed = new Color(255,60,50,200);
	Color lightBrown = new Color(80,80,20,250);
	Color lightGray = new Color(80,0,0,150);
	
	boolean motionBlur = true;
	int framesToAccum = 5;
	int i = 0;
	long frameCount = 0;
	
	
	public void renderGL() {
		// Clear The Screen And The Depth Buffer
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		
		frameCount++;
		
		Color.white.bind();
		fields1.bind();
		
		/*GL11.glPushMatrix();
			GL11.glBegin(GL11.GL_QUADS);
			GL11.glTexCoord2f(0,0); //bottom left
			GL11.glVertex2f(0,backgroundTexture.getTextureHeight());
			GL11.glTexCoord2f(1,0); //bottom right
			GL11.glVertex2f(backgroundTexture.getTextureWidth(),backgroundTexture.getTextureHeight());
			GL11.glTexCoord2f(1,1); //top right
			GL11.glVertex2f(backgroundTexture.getTextureWidth(),0);
			GL11.glTexCoord2f(0,1); //top left
			GL11.glVertex2f(0,0);
			GL11.glEnd();
		GL11.glPopMatrix();*/
		
		/*
		GL11.glPushMatrix();
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2f(0,0); //bottom left
		GL11.glVertex2f(0,600);
		GL11.glTexCoord2f(1,0); //bottom right
		GL11.glVertex2f(1000,600);
		GL11.glTexCoord2f(1,1); //top right
		GL11.glVertex2f(1000,0);
		GL11.glTexCoord2f(0,1); //top left
		GL11.glVertex2f(0,0);
		GL11.glEnd();
		GL11.glPopMatrix();
		*/
		
		//WHY MUST IT BE 1024?!?!??! THE MADNESS
		/*
		GL11.glPushMatrix();
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2f(0,0); //bottom left
		GL11.glVertex2f(0,0);
		GL11.glTexCoord2f(1,0); //bottom right
		GL11.glVertex2f(1024,0);
		GL11.glTexCoord2f(1,1); //top right
		GL11.glVertex2f(1024,1024);
		GL11.glTexCoord2f(0,1); //top left
		GL11.glVertex2f(0,1024);
		GL11.glEnd();
		GL11.glPopMatrix();
		*/
		
		GL11.glPushMatrix();
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2f(0,0); //bottom left
		GL11.glVertex2f(-100,-100);
		GL11.glTexCoord2f(1,0); //bottom right
		GL11.glVertex2f(1948, -100);
		GL11.glTexCoord2f(1,1); //top right
		GL11.glVertex2f(1948,1948);
		GL11.glTexCoord2f(0,1); //top left
		GL11.glVertex2f(-100,1948);
		GL11.glEnd();
		GL11.glPopMatrix();
		
		
		try {
		for (int i = 0; i<actorsOnScreen.size(); i++){
			if (actorsOnScreen.get(i) instanceof Quad)
				drawActor((Quad)actorsOnScreen.get(i));
		}
		
		for (int i = 0; i<bottomLevelActors.size(); i++){
			if (bottomLevelActors.get(i) instanceof Quad)
				drawActor((Quad)bottomLevelActors.get(i));
		}
		for (int i = 0; i < midLevelActors.size(); i++) {
			if (midLevelActors.get(i) instanceof Quad)
				drawActor((Quad)midLevelActors.get(i));
		}
		for (int i = 0; i < topLevelActors.size(); i++) {
			if (topLevelActors.get(i) instanceof Quad)
				drawActor((Quad)topLevelActors.get(i));
		}
		}
		catch (NullPointerException e){
			
		}
		
		//drawActor(quad2);
		if (GameEngine.currentRoom instanceof SpawnRoom)
			font.drawString(190, 100, "Use WASD to move and Arrow Keys to use your weapon", Color.orange);
		
		if (GameEngine.gameOver == true){
			font2.drawString(300, 200, "GAME OVER", Color.black);
			font2.drawString(310, 210, "GAME OVER", Color.white);
		}
		
		if (GameEngine.GUIOn){
			for (int i = 0; i<actorsOnScreen.size(); i++){
				if (actorsOnScreen.get(i) instanceof Player1){
					drawGUI((Player1)actorsOnScreen.get(i));
					break;
				}
			}
		}
		
		lightGray.bind();
		greenmist.bind();
		GL11.glPushMatrix();
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2f(0,0); //bottom left
		GL11.glVertex2f(-100,-100);
		GL11.glTexCoord2f(1,0); //bottom right
		GL11.glVertex2f(1948, -100);
		GL11.glTexCoord2f(1,1); //top right
		GL11.glVertex2f(1948,1948);
		GL11.glTexCoord2f(0,1); //top left
		GL11.glVertex2f(-100,1948);
		GL11.glEnd();
		GL11.glPopMatrix();
		
	}
	
	private int orthoHeight=2000;
	private int orthoWidth=2000;
	private int orthoFact = 600;
	
	private int viewPFact = (int)(orthoFact*1.17f);
	
	public void initGL(int screenWidth, int screenHeight) {
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		
		GL11.glShadeModel(GL11.GL_SMOOTH);        
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		//GL11.glDisable(GL11.GL_LIGHTING); 
		GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);       
		GL11.glClearDepth(1);
        
    	// enable alpha blending
    	GL11.glEnable(GL11.GL_BLEND);
    	GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
    	
    	float aspectRatio = screenWidth/screenHeight;
    	float xRatio = (float)screenHeight/screenWidth;
    	float yRatio = (float)screenWidth/screenHeight;
    	
    	GL11.glViewport(0-381, 0-369, screenWidth+viewPFact, screenHeight+(int)(viewPFact*yRatio/2));
    	//GL11.glViewport(0-screenWidth/8,0+screenHeight/8,(int)(screenWidth)-screenWidth/8,(int)(screenHeight)+screenHeight/8);
    	
    	GL11.glMatrixMode(GL11.GL_MODELVIEW);
 
    	
    	//GL11.glPushMatrix();
    	GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		//GL11.glOrtho(0-screenWidth, screenWidth*2, screenHeight*2, 0-screenHeight, 1, -1); //ALLOWS YOU TO RENDER THINGS BASED ON THESE BOUNDARIES
		GL11.glOrtho(0-orthoFact, screenWidth+orthoFact, screenHeight+orthoFact*yRatio/2, 0-orthoFact*yRatio/2, 1, -1); //ALLOWS YOU TO RENDER THINGS BASED ON THESE BOUNDARIES
		//GL11.glTranslatef(400, 300, 0);
		
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		//GL11.glPopMatrix();
		
		
		//disable bilinear filtering, no blending
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
	}
	
	//this method takes the Actors (will be in an array
	//the internal list of Actors of this class will then be updated
	//eventually add bot, mid, top level actors on screen as parameters so it's easier to render things over each other
	public void sendActors(ArrayList <Actor> actorsOnScreen) {
		this.actorsOnScreen = actorsOnScreen;
	}
	
	public void drawActor(Quad actorToDraw){
		float rotation = 0;
		
		// R,G,B,A Set The Color 
		//GL11.glColor4f(actorToDraw.colorOfActor[0], actorToDraw.colorOfActor[1], actorToDraw.colorOfActor[2], actorToDraw.colorOfActor[3]); //red, green, blue, alpha
		Color.white.bind();
		 // or GL11.glBind(texture.getTextureID());
		
		// draw quad
		/*
		GL11.glPushMatrix();
			GL11.glTranslatef(actorToDraw.x, actorToDraw.y, 0);
			//GL11.glRotatef(rotation, 0f, 0f, 1f);
			GL11.glTranslatef(-actorToDraw.x, -actorToDraw.y, 0);
			
			GL11.glBegin(GL11.GL_QUADS);
				GL11.glVertex2f(actorToDraw.x - actorToDraw.width/2, actorToDraw.y - actorToDraw.height/2);
				GL11.glVertex2f(actorToDraw.x + actorToDraw.width/2, actorToDraw.y - actorToDraw.height/2);
				GL11.glVertex2f(actorToDraw.x + actorToDraw.width/2, actorToDraw.y + actorToDraw.height/2);
				GL11.glVertex2f(actorToDraw.x - actorToDraw.width/2, actorToDraw.y + actorToDraw.height/2);
			GL11.glEnd();
		GL11.glPopMatrix();
		*/
		
		//Color.white.bind();
		
		GL11.glRenderMode(GL11.GL_LUMINANCE);
		//GL11.glRenderMode(GL11.GL_AND_INVERTED);
		defaultTexture.bind(); //eventually put texture inside of actor
		//apply texture stretch to fit quad
		/*
		GL11.glPushMatrix();
		
		GL11.glBegin(GL11.GL_QUADS);
			GL11.glTexCoord2f(0,0); //bottom left
			GL11.glVertex2f(actorToDraw.x - defaultTexture.getTextureWidth()/2, actorToDraw.y - defaultTexture.getTextureHeight()/2);
			GL11.glTexCoord2f(1,0); //bottom right
			GL11.glVertex2f(actorToDraw.x + defaultTexture.getTextureWidth()/2, actorToDraw.y - defaultTexture.getTextureHeight()/2);
			GL11.glTexCoord2f(1,1); //top right
			GL11.glVertex2f(actorToDraw.x + defaultTexture.getTextureWidth()/2, actorToDraw.y + defaultTexture.getTextureHeight()/2);
			GL11.glTexCoord2f(0,1); //top left
			GL11.glVertex2f(actorToDraw.x - defaultTexture.getTextureWidth()/2, actorToDraw.y + defaultTexture.getTextureHeight()/2);
		GL11.glEnd();
		*/
		//GL11.glPopMatrix();
		//apply texture stretch to fit quad 
		
		//if (actorToDraw instanceof Player1)
		//	player1_backTexture.bind();
		
		
		if (actorToDraw instanceof Projectile){
			firebullet.bind();
		} else if (actorToDraw instanceof Player1){
			if (((Player1)actorToDraw).isInvincible()){ //turn red when iFrames are up
				lightRed.bind();
			}
			firebullet.bind(); //bind player texture
		} else if (actorToDraw instanceof Enemy){
			if (((Enemy)actorToDraw).isInvincible()){ //turn red when iFrames are up
				lightRed.bind();
			}
			//bind enemy texture
		} else if (actorToDraw instanceof Rock){
			fieldrock1.bind();
		} else if (actorToDraw instanceof Weapon){
			if (actorToDraw instanceof Sword) {
				sword.bind();
				if (((Sword)actorToDraw).getFacingDirection() == Direction.NORTH)
					rotation = 0;
				else if (((Sword)actorToDraw).getFacingDirection() == Direction.EAST)
					rotation = 90;
				else if (((Sword)actorToDraw).getFacingDirection() == Direction.SOUTH)
					rotation = 180;
				else if (((Sword)actorToDraw).getFacingDirection() == Direction.WEST)
					rotation = 270;
				if (actorToDraw instanceof Stick) {
					// weapon_stick.bind();
					lightBrown.bind();
				}
			}
			
			//rotation = 90f;
		} else if (actorToDraw instanceof Door){
			//wall_default.bind();
		} else if (actorToDraw instanceof Quad){
			//wall_default.bind();
			invisible.bind();
		}
		
		for (int i = 0; i < GameEngine.currentRoom.getWalls().size(); i++){
			if (!((Wall)GameEngine.currentRoom.getWalls().get(i)).wallHasDoor())
				if (actorToDraw == ((Wall) GameEngine.currentRoom.getWalls().get(i)).getWallWhenNoDoor());
					//wall_default.bind();
		}
		
		float x = 10;
		float y = 10;
		 GL11.glPushMatrix();
		 	GL11.glTranslatef(actorToDraw.x, actorToDraw.y, 0);
		 	GL11.glRotatef(rotation, 0f, 0f, 1f);
		 	GL11.glTranslatef(-actorToDraw.x, -actorToDraw.y, 0);
		 
				GL11.glBegin(GL11.GL_QUADS);
				GL11.glTexCoord2f(0,0); //bottom left
				GL11.glVertex2f(actorToDraw.x - actorToDraw.width/2,actorToDraw.y - actorToDraw.height/2);
				GL11.glTexCoord2f(1,0); //bottom right
				GL11.glVertex2f(actorToDraw.x + actorToDraw.width/2, actorToDraw.y - actorToDraw.height/2);
				GL11.glTexCoord2f(1,1); //top right
				GL11.glVertex2f(actorToDraw.x + actorToDraw.width/2,actorToDraw.y + actorToDraw.height/2);
				GL11.glTexCoord2f(0,1); //top left
				GL11.glVertex2f(actorToDraw.x - actorToDraw.width/2,actorToDraw.y + actorToDraw.height/2);
				GL11.glEnd();
		GL11.glPopMatrix();
		
		//font.drawString(100, 50, "THE LIGHTWEIGHT JAVA GAMES LIBRARY", Color.orange);
		//font2.drawString(150, 50, "WELCOME TO DIE!!!", Color.orange);
		
		//debugging strings
		//fontLocation.drawString(actorToDraw.x + 20, actorToDraw.y - 30, "x: "+String.valueOf(actorToDraw.x), Color.yellow);
		//fontLocation.drawString(actorToDraw.x + 20, actorToDraw.y - 20, "y: "+String.valueOf(actorToDraw.y), Color.yellow);
		//defaultTexture.release();
		//GL11.glBindTexture(GL_TEXTURE_2D, 0);
		
		//example texture application
		/*
		 * GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2f(0,0);
		GL11.glVertex2f(actorToDraw.x - actorToDraw.width/2,actorToDraw.y + actorToDraw.height/2);
		GL11.glTexCoord2f(1,0);
		GL11.glVertex2f(100+defaultTexture.getTextureWidth(),100);
		GL11.glTexCoord2f(1,1);
		GL11.glVertex2f(100+defaultTexture.getTextureWidth(),100+defaultTexture.getTextureHeight());
		GL11.glTexCoord2f(0,1);
		GL11.glVertex2f(100,100+defaultTexture.getTextureHeight());
		GL11.glEnd();
		 */
		
		//draw circle
		/*
		GL11.glColor4f(actorToDraw.colorOfActor[0]+.1f, actorToDraw.colorOfActor[1]+.1f, actorToDraw.colorOfActor[2]+.1f, actorToDraw.colorOfActor[3]+.1f); //red
		
		GL11.glPushMatrix();
		GL11.glTranslatef(actorToDraw.x, actorToDraw.y, 0);
		GL11.glScalef(actorToDraw.radius, actorToDraw.radius, 1);
		
		
		GL11.glBegin(GL11.GL_TRIANGLE_FAN);
		GL11.glVertex2f(0, 0);
		for(int i = 0; i <= 20; i++){ //NUM_PIZZA_SLICES decides how round the circle looks.
		    double angle = Math.PI * 2 * i / 20;
		    GL11.glVertex2f((float)Math.cos(angle), (float)Math.sin(angle));
		}
		GL11.glEnd();

		GL11.glPopMatrix();
		*/
		
		if (!(actorToDraw instanceof Projectile)){
			//fontLocation.drawString(actorToDraw.x, actorToDraw.y, actorToDraw.toString(), Color.cyan);
		}
		
	}
	
	private TrueTypeFont font;
	private TrueTypeFont font2;
	private TrueTypeFont fontLocation;
	
	private boolean antiAlias = true;
	
	//initialize resources
	//eventually make each room initialize its own resources?
	//each floor?
	//how do you destroy resources/take them out of ram?
	public void initResources(){
		try {
			// load texture from PNG file
			defaultTexture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/blackcircle256.png"));
			/*
			System.out.println("Texture loaded: "+defaultTexture);
			System.out.println(">> Image width: "+defaultTexture.getImageWidth());
			System.out.println(">> Image height: "+defaultTexture.getImageHeight());
			System.out.println(">> Texture width: "+defaultTexture.getTextureWidth());
			System.out.println(">> Texture height: "+defaultTexture.getTextureHeight());
			System.out.println(">> Texture ID: "+defaultTexture.getTextureID());
			*/
			
			backgroundTexture = TextureLoader.getTexture("JPG", ResourceLoader.getResourceAsStream("res/background.jpg"));
			
			/*
			System.out.println("Texture loaded: "+backgroundTexture);
			System.out.println(">> Image width: "+backgroundTexture.getImageWidth());
			System.out.println(">> Image height: "+backgroundTexture.getImageHeight());
			System.out.println(">> Texture width: "+backgroundTexture.getTextureWidth());
			System.out.println(">> Texture height: "+backgroundTexture.getTextureHeight());
			System.out.println(">> Texture ID: "+backgroundTexture.getTextureID());
			*/
			
			player1_backTexture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/player1_back.png"));
			
			wall_default = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/wall_default.png"));
			
			firebullet = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/firebullet.png"));
			
			invisible = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/invisible.png"));
			
			//load rocks
			fieldrock1 = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/rocks/fieldrock1.png"));
			
			//load hearts
			normalFullHeart = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/hearts/fullheart1.png"));
			halfHeart = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/hearts/halfheart1.png"));
			
			//load background images
			fields1 = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/backgrounds/fields1_1.png"));
			greenmist = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/backgrounds/greenmist1.png"));
			
			//load weapon images
			weapon_stick = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/weapons/stick_swing.png"));
			
			sword = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/weapons/sword_swing.png"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
		//load text
		Font awtFont = new Font("Times New Roman", Font.BOLD, 24);
		font = new TrueTypeFont(awtFont, antiAlias);
		
		Font awtFont3 = new Font("Times New Roman", Font.BOLD, 12);
		fontLocation = new TrueTypeFont(awtFont3, antiAlias);
		
		try {
			InputStream inputStream = ResourceLoader.getResourceAsStream("res/BAD GRUNGE.ttf");
			
			Font awtFont2 = Font.createFont(Font.TRUETYPE_FONT, inputStream);
			awtFont2 = awtFont2.deriveFont(110f); //set font size
			font2 = new TrueTypeFont(awtFont2, antiAlias);
			
			/*
			System.out.println("Font loaded: "+font2);
			System.out.println(">> Image string: "+font2.toString());
			*/
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void drawGUI(Player1 player){
		drawHearts(player);
		drawMap();
		drawItems();
	}
	
	public void drawHearts(Player1 player){
		int heartStartXCoord = -50;
		int heartStartYCoord = -165;
		int heartSize = 32;
		Color.white.bind();
		normalFullHeart.bind();
		for (int i = 0; i < player.getNormalHealth() - player.getNormalHealth()%1; i++) {
			if (i < 10){
				GL11.glPushMatrix();
				GL11.glBegin(GL11.GL_QUADS);
				GL11.glTexCoord2f(0,0); //bottom left
				GL11.glVertex2f(heartStartXCoord + heartSize*i - heartSize/2, heartStartYCoord - heartSize/2);
				GL11.glTexCoord2f(1,0); //bottom right
				GL11.glVertex2f(heartStartXCoord + heartSize*i + heartSize/2, heartStartYCoord - heartSize/2);
				GL11.glTexCoord2f(1,1); //top right
				GL11.glVertex2f(heartStartXCoord + heartSize*i + heartSize/2, heartStartYCoord + heartSize/2);
				GL11.glTexCoord2f(0,1); //top left
				GL11.glVertex2f(heartStartXCoord + heartSize*i - heartSize/2, heartStartYCoord + heartSize/2);
				GL11.glEnd();
				GL11.glPopMatrix();
			} else if (i < 20){
				GL11.glPushMatrix();
				GL11.glBegin(GL11.GL_QUADS);
				GL11.glTexCoord2f(0,0); //bottom left
				GL11.glVertex2f(heartStartXCoord + heartSize*(i-10) - heartSize/2, heartStartYCoord - heartSize/2 + 32);
				GL11.glTexCoord2f(1,0); //bottom right
				GL11.glVertex2f(heartStartXCoord + heartSize*(i-10) + heartSize/2, heartStartYCoord - heartSize/2 + 32);
				GL11.glTexCoord2f(1,1); //top right
				GL11.glVertex2f(heartStartXCoord + heartSize*(i-10) + heartSize/2, heartStartYCoord + heartSize/2 + 32);
				GL11.glTexCoord2f(0,1); //top left
				GL11.glVertex2f(heartStartXCoord + heartSize*(i-10) - heartSize/2, heartStartYCoord + heartSize/2 + 32);
				GL11.glEnd();
				GL11.glPopMatrix();
			}
		}

		if (player.getNormalHealth() % 1 > 0f) {

		}
		
	}
	
	public void drawMap(){
		
	}
	
	public void drawItems(){
		
	}
}
