package actors;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import defaultPackage.GameEngine;
import sceneSetting.Direction;
import actors.Weapons.Stick;
import actors.Weapons.Sword;
import actors.Weapons.Weapon;


public class Player1 extends Quad{

	public float mass = 1;
	public float gravity = 9.8655f/20;
	public boolean isMovable = true;
	
	public float maxVx = 4f;
	public float maxVy = 4f;
	public float accel = 1f;
	public float decel = 1f;
	
	public boolean isDetectionBoxOn = true; //hit box for detecting things may be turned on and off
	public boolean isHitBoxOn = true; //hit box for collisions may be turned on and off (for example, cut scenes)
	
	public boolean canShoot = true;
	public boolean canUseWeapon = true;
	
	private Weapon weapon;
	private boolean weaponIsOut = false;
	private int weaponSize = 200;
	private int weaponFrameCount = 0;
	private int fireRate = 1; //1 swing per second
	private int framesSinceLastSwing = 0;
	
	private float normalHealth = 3;
	private float metalHealth = 0;
	private float magicHealth = 0;
	
	private int iFrames = 72; //1.5 sec of invinc
	private int iFrameCounter = 0;
	private boolean isInvincible = false;
	
	public Player1(float x, float y, int width, int height, ShapeType hitboxShape) {
		super(x, y, width, height, hitboxShape);
		// TODO Auto-generated constructor stub
		//this.hardness = 0;
		movePattern = new MovementPattern(MovementType.PLAYER);
	}
	
	public void act(ArrayList<Actor> actorsOnScreen){
		if (canUseWeapon)
			swingWeapon(actorsOnScreen);
		
		checkIFrames();
		checkHealth(actorsOnScreen);
		
		framecount++;
	}
	
	public void shoot(ArrayList<Actor> actorsOnScreen){
		//change this so that it adds a set type of projectile (variable) and the placement (focus) is updated before the if statements to match the player location
		if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)){
			actorsOnScreen.add(new Projectile(this.x-this.radius-2.5f, this.y, 5, 5, -2f, 0f, ShapeType.CIRCLE, new MovementPattern(MovementType.DEAD, this), 144));
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)){
			actorsOnScreen.add(new Projectile(this.x+this.radius+2.5f, this.y, 5, 5, 2f, 0f, ShapeType.CIRCLE, new MovementPattern(MovementType.DEAD, this), 144));
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_UP)){
			actorsOnScreen.add(new Projectile(this.x, this.y-this.radius-2.5f, 5, 5, 0f, 2f, ShapeType.CIRCLE, new MovementPattern(MovementType.DEAD, this), 144));
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)){
			actorsOnScreen.add(new Projectile(this.x, this.y+this.radius+2.5f, 5, 5, 0f, -2f, ShapeType.CIRCLE, new MovementPattern(MovementType.DEAD, this), 144));
		}		
	}
	
	public void swingWeapon(ArrayList<Actor> actorsOnScreen){
		//create temporary new actor (new sword class), parameters: (length, attribute)
		
		if (!weaponIsOut && framesSinceLastSwing % (48*fireRate) == 0) {
			
			
			if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
				weapon = new Stick(this.x, this.y, weaponSize, Direction.WEST, 12);
				weaponIsOut = true;

				actorsOnScreen.add(weapon);
			}
			else if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
				weapon = new Stick(this.x, this.y, weaponSize, Direction.EAST, 12);
				weaponIsOut = true;

				actorsOnScreen.add(weapon);
			}
			else if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
				weapon = new Stick(this.x, this.y, weaponSize, Direction.NORTH, 12);
				weaponIsOut = true;

				actorsOnScreen.add(weapon);
			}
			else if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
				weapon = new Stick(this.x, this.y, weaponSize, Direction.SOUTH, 12);
				weaponIsOut = true;

				actorsOnScreen.add(weapon);
			}

		}
		
		//destroyed after certain num of frames
		if (weaponIsOut && weaponFrameCount < weapon.getFrameDuration())
			weaponFrameCount++;
		else if (weaponIsOut && weaponFrameCount >= weapon.getFrameDuration()){
			weaponFrameCount = 0;
			weapon.destroy(actorsOnScreen);
			weaponIsOut = false;
		}
		
		if (weaponIsOut){
			framesSinceLastSwing++;
		} else if (framesSinceLastSwing > 0){
			framesSinceLastSwing++;
		} 
		if (framesSinceLastSwing % (48*fireRate) == 0){
			framesSinceLastSwing = 0;
		}
		
		//variables needed for player1's sword: length, attribute, fire speed, length held out, durability
	}
	
	public void checkHealth(ArrayList<Actor> actorsOnScreen){
		if (normalHealth + metalHealth + magicHealth == 0){
			GameEngine.gameOver = true;

			destroy(actorsOnScreen);
		}
		
	}
	
	public void damageHealth(float damage){
		
		if (!isInvincible) {
			if (magicHealth > 0) {
				damageMagicHealth(damage);
			} else if (metalHealth > 0) {
				damageMetalHealth(damage);
			} else {
				damageNormalHealth(damage);
			}
			
			iFrameCounter = 1;
			isInvincible = true;
		}
	}
	
	public float getNormalHealth(){
		return normalHealth;
	}
	
	public void damageNormalHealth(float damage){
		this.normalHealth -= damage;
	}
	
	public float getMetalHealth(){
		return metalHealth;
	}
	
	public void damageMetalHealth(float damage){
		this.metalHealth -= damage;
	}
	
	public float getMagicHealth(){
		return metalHealth;
	}
	
	public void damageMagicHealth(float damage){
		this.metalHealth -= damage;
	}
	
	public void checkIFrames(){
		if (iFrameCounter < iFrames && iFrameCounter != 0){
			iFrameCounter++;
			isInvincible = true;
		} else if (iFrameCounter == iFrames){
			isInvincible = false;
			iFrameCounter = 0;
		}
	}
	
	public boolean isInvincible(){
		return isInvincible;
	}
	
	public void destroy(ArrayList<Actor> actorsOnScreen){
		actorsOnScreen.remove(this);
		
		//add particles from destruction
	}
}
