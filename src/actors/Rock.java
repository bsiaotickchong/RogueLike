package actors;

public class Rock extends Quad{

	boolean destructable = false;
	public float maxVx = 0f;
	public float maxVy = 0f;
	public float accel = 0f;
	public float frictionCoef = 9999f;
	boolean movable = false;
	
	public Rock(float x, float y, int width, int height, ShapeType hitboxShape) {
		super(x, y, width, height, hitboxShape);
		// TODO Auto-generated constructor stub
		this.hardness = 0; //sets rock to solid
	}
	
}
