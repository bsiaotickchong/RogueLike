package defaultPackage;
import java.util.concurrent.atomic.AtomicInteger;

import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;

import variableTypes.MutableFloat;

public class TimeKeeper {
	
	final int TICKS_PER_SECOND = 48;
	final int SKIP_TICKS = 1000 / TICKS_PER_SECOND;
	final int MAX_FRAMESKIP = 5;
	final int FRAMES_TO_ACCUMULATE = 2;
	
	long next_game_tick = getTime();
	int loops;
	float interpolation;
	
	
	
	
	/** time at last frame */
	long lastFrame;
	
	/** frames per second */
	int fps;
	/** last fps time */
	long lastFPS;
	
	public void start(){
		getDelta(); // call once before loop to initialize lastFrame
		lastFPS = getTime(); // call before loop to initialize fps timer
	}
	/** 
	 * Calculate how many milliseconds have passed 
	 * since last frame.
	 * 
	 * @return milliseconds passed since last frame 
	 */
	public int getDelta() {
	    long time = getTime();
	    int delta = (int) (time - lastFrame);
	    lastFrame = time;
	 
	    return delta;
	}
	
	/**
	 * Get the accurate system time
	 * 
	 * @return The system time in milliseconds
	 */
	public long getTime() {
	    return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}
	
	/**
	 * Calculate the FPS and set it in the title bar
	 */
	public void updateFPS() {
		if (getTime() - lastFPS > 1000) {
			Display.setTitle("FPS: " + fps);
			fps = 0;
			lastFPS += 1000;
		}
		fps++;
	}
	
	
	public void setLoops(int loopCount){
		loops = loopCount;
	}
	
	public void incrementLoops(){
		loops++;
	}
	
	public float getInterpolation(){
		interpolation = (float)(getTime() + SKIP_TICKS - next_game_tick / (float) (SKIP_TICKS));
		return interpolation;
	}
	
	//input: number of seconds that have accumulated since first time this method was called (uses AtomicInteger which is a mutable form of the Integer class)
	//		 number of seconds that should pass until this method returns true
	//output: boolean; true if certain amount of seconds have passed, false if not
	public boolean hasXSecondsPassed(MutableFloat timeAccumulated, float secondsForAction){
		if (timeAccumulated.getValue()+getDelta()*1000 >= secondsForAction)
			return true;
		else
			timeAccumulated.add(getDelta()*1000);
		return false;
	}
}
