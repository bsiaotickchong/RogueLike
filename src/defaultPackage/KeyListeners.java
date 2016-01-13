package defaultPackage;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import actors.*;
@SuppressWarnings("unused")
public class KeyListeners {
	
	public void updatePlayerLocation(Player1 quad) {
		//int delta = getDelta();
		// rotate quad
		//rotation += 0.15f * delta;
		
		//when directional keys are pressed:
		if (Keyboard.isKeyDown(Keyboard.KEY_A)) { // move LEFT
			if (quad.vx > -quad.maxVx) { // if leftwards velocity is not at max yet
				quad.vx -= quad.accel; // accelerate in the left direction
				if (quad.vx <= -quad.maxVx) { // if over max velocity
					quad.vx = -quad.maxVx; // set velocity to max velocity
				}
			} else if (quad.vx <= -quad.maxVx) { // if over max velocity
				quad.vx = -quad.maxVx; // set velocity to max velocity
			}
			// physEngine.moveActor(quad, delta);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_D)) { // move RIGHT
			if (quad.vx < quad.maxVx) { // if rightwards velocity is not at max yet
				quad.vx += quad.accel; // accelerate in the right direction
				if (quad.vx >= quad.maxVx) { // if over max velocity
					quad.vx = quad.maxVx; // set velocity to max velocity
				}
			} else if (quad.vx >= quad.maxVx) { // if over max velocity
				quad.vx = quad.maxVx; // set velocity to max velocity
			}
			// physEngine.moveActor(quad, delta);
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_W)) { // move UP
			if (quad.vy < quad.maxVy) { // if upwards velocity is not at max yet
				quad.vy += quad.accel; // accelerate in the upwards direction
				if (quad.vy >= quad.maxVy) { // if over max velocity
					quad.vy = quad.maxVy; // set velocity to max velocity
				}
			} else if (quad.vy >= quad.maxVy) { // if over max velocity
				quad.vy = quad.maxVy; // set velocity to max velocity
			}
			// physEngine.moveActor(quad, delta);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_S)) { // move DOWN
			if (quad.vy > -quad.maxVy) { // if downwards velocity is not at max yet
				quad.vy -= quad.accel; // accelerate downwards
				if (quad.vy <= -quad.maxVy) { // if over max velocity
					quad.vy = -quad.maxVy; // move at max velocity
				}
			} else if (quad.vy <= -quad.maxVy) { // if over max velocity
				quad.vy = -quad.maxVy; // move at max velocity
			}
			// physEngine.moveActor(quad, delta);
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
			quad.vx = 0;
			quad.vy = 0;
		}
		
		// --------------------------------------------------------
        // once directional keys are released, apply friction: (should
        // eventually replace decelerateActor()

                if (!Keyboard.isKeyDown(Keyboard.KEY_A)) {
                        if (quad.vx < 0) {
                                quad.vx += quad.frictionCoef;
                                if (quad.vx >= 0) {
                                        quad.vx = 0;
                                }
                        }
                        // physEngine.moveActor(quad, delta);
                }
                if (!Keyboard.isKeyDown(Keyboard.KEY_D)) {
                        if (quad.vx > 0) {
                                quad.vx -= quad.frictionCoef;
                                if (quad.vx <= 0) {
                                        quad.vx = 0;
                                }
                        }
                        // physEngine.moveActor(quad, delta);
                }
                if (!Keyboard.isKeyDown(Keyboard.KEY_W)) {
                        if (quad.vy > 0) {
                                quad.vy -= quad.frictionCoef;
                                if (quad.vy <= 0) {
                                        quad.vy = 0;
                                }
                        }
                        // physEngine.moveActor(quad, delta);
                }
                if (!Keyboard.isKeyDown(Keyboard.KEY_S)) {
                        if (quad.vy < 0) {
                                quad.vy += quad.frictionCoef;
                                if (quad.vy >= 0) {
                                        quad.vy = 0;
                                }
                        }
                        // physEngine.moveActor(quad, delta);
                }

		
	}
	
	public void printMouseInfo(){
		printMouseLocation();
		printMouseButtonStatus();
		
		System.out.println();
	}
	
	public void printKeyboardInfo(){
		while (Keyboard.next()){
			if(Keyboard.getEventKey() == Keyboard.KEY_A){
				if (Keyboard.getEventKeyState()){
					System.out.print("[A Key Pressed]");
				}
				else {
					System.out.print("[A Key Released]");
				}
			}
			if(Keyboard.getEventKey() == Keyboard.KEY_W){
				if (Keyboard.getEventKeyState()){
					System.out.print("[W Key Pressed]");
				}
				else {
					System.out.print("[W Key Released]");
				}
			}
			if(Keyboard.getEventKey() == Keyboard.KEY_D){
				if (Keyboard.getEventKeyState()){
					System.out.print("[D Key Pressed]");
				}
				else {
					System.out.print("[D Key Released]");
				}
			}
			if(Keyboard.getEventKey() == Keyboard.KEY_S){
				if (Keyboard.getEventKeyState()){
					System.out.print("[S Key Pressed]");
				}
				else {
					System.out.print("[S Key Released]");
				}
			}
			System.out.println("");
		}
	}
	
	public void printMouseLocation(){
		int x = Mouse.getX();
		int y = Mouse.getY();
		
		System.out.print("[MouseX: " + x + ", MouseY: " + y + "]");
	}
	
	public void printMouseButtonStatus(){
		if (Mouse.isButtonDown(0)) {
			System.out.print("[LeftButtonDown!]");
		}
		else if (Mouse.isButtonDown(1)) {
			System.out.print("[RightButtonDown!]");
		}
		else if (Mouse.isButtonDown(2)) {
			System.out.print("[MiddleButtonDown!]");
		}
	}
}
