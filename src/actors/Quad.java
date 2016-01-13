package actors;


public class Quad extends Actor{
	
	public int width = 0;
	public int height = 0;
	public float radius = 0;
	
	//create texture width
	//create texture height
	
	public Quad(float x, float y, int width, int height, ShapeType hitboxShape) {
		super(x, y, hitboxShape); //gives x and y to Actor constructor
		// TODO Auto-generated constructor stub
		this.width = width;
		this.height = height;
		radius = calculateRadiusOfCircleHitBox();
		
		this.hitboxShape = hitboxShape;
	}
	
	//calculates radius of circle to be hitbox, just equal to width for now (good for perfect squares)
	public float calculateRadiusOfCircleHitBox (){
		return width/2;
	}
	
	public float getWidth(){
		return width;
	}
	
	public float getHeight(){
		return height;
	}
}
