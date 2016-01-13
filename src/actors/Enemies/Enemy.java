package actors.Enemies;

import java.util.ArrayList;

import defaultPackage.GameEngine;
import sceneSetting.TileSystem;
import actors.Actor;
import actors.MovementPattern;
import actors.MovementType;
import actors.Player1;
import actors.Quad;
import actors.ShapeType;

public class Enemy extends Quad{
	
	public float mass = 1;
	public float velocity = 0;
	public float velocityDirection = 0;
	
	protected float walkingPace = 100f; //defaults at 100
	
	public boolean isDetectionBoxOn = false; //hit box for detecting things may be turned on and off
	public boolean isHitBoxOn = true; //hit box for collisions may be turned on and off (for example, cut scenes)
	public boolean isMovable = true;
	
	protected GenericFocus focus = null;
	protected TileSystem roomTiles = null;
	

	protected float normalHealth = 1;
	protected float metalHealth = 0;
	protected float magicHealth = 0;
	
	protected int iFrames = 72; //1.5 sec of invinc
	protected int iFrameCounter = 0;
	protected boolean isInvincible = false;
	
	protected int damageGiven = 0;
	
	public Enemy(float x, float y, int width, int height, ShapeType hitboxShape, MovementPattern movePattern) {
		super(x, y, width, height, hitboxShape);
		this.movePattern = movePattern;
		
		focus = new GenericFocus(0,0);
		// TODO Auto-generated constructor stub
		this.hardness = 0;
		
		this.maxVx = 5f;
		this.maxVy = 5f;
		
		if (movePattern.movementType == MovementType.GRAVITATE){ //constant can be changed for enemy children or through setGravityConstant() method
			gravityConstant = 50f; 
		}
	}
	
	public void act(ArrayList<Actor> actorsOnScreen){
		checkHealth(actorsOnScreen);
		checkIFrames();
	}
	
	public Actor getFocus(){
		return focus;
	}
	
	public void checkHealth(ArrayList<Actor> actorsOnScreen){
		if (normalHealth + metalHealth + magicHealth == 0){
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
			
			//if (magicHealth + metalHealth + normalHealth == 0)
			//	destroy(GameEngine.actorsOnScreen);
			
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
	
	public void damage(Actor actor){
		if (actor instanceof Player1){
			((Player1) actor).damageHealth(damageGiven);
		}
	}
	
	public void destroy(ArrayList<Actor> actorsOnScreen){
		actorsOnScreen.remove(this);
		
		//add particles from destruction
	}
}
