package actors;

public class Camera extends Actor{

	//SET BOUNDS SO THAT PLAYER IS ALWAYS ON SCREEN
	
	private int width;
	private int height;
	
	public Camera(float x, float y, int width, int height, MovementPattern movePattern) {
		super(x, y);
		this.width = width;
		this.height = height;
		this.movePattern = movePattern;
		// TODO Auto-generated constructor stub
		
		maxVx = 3f;
		maxVy = 3f;
		accel = .5f;
		
		if (movePattern.movementType == MovementType.GRAVITATE || movePattern.movementType == MovementType.ATTRACT){
			gravityConstant = .00005f;
		}
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}
}
