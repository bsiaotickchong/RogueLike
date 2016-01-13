package defaultPackage;
import org.lwjgl.*;
import org.lwjgl.input.Mouse;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.*;

import actors.*;

public class Window{
	
	boolean vsync; // is VSync enabled
	
	int screenWidth = 800;
	int screenHeight = 600;
	
	public Window (int screenWidth, int screenHeight){
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
	}
	
	public void start(){
		try {
			Display.setDisplayMode(new DisplayMode(screenWidth,screenHeight));
			Display.create();
			Display.setVSyncEnabled(vsync);
		} catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(0);
		}
		
		//GL11.glViewport(120,120,800,600);
		
		
	}
	
	
}
