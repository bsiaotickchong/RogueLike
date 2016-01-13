package actors.Weapons;

import java.util.ArrayList;

import defaultPackage.GameEngine;
import sceneSetting.Direction;
import actors.Actor;
import actors.Player1;
import actors.Quad;
import actors.ShapeType;
import actors.Enemies.Enemy;

public class Sword extends Weapon{

	protected float rangeRadius;
	protected Actor hitbox;
	
	protected int damageGiven = 1;
	
	Direction facingDirection;
	
	public Sword(float x, float y, int size, Direction facingDirection, int frameDuration) {
		super(x, y, size, frameDuration);
		
		this.facingDirection = facingDirection;
		this.rangeRadius = rangeRadius;
		
		this.hardness = 0;
		
		hitbox = new Quad(x,y, size, size, ShapeType.CIRCLE);
	}
	
	public void act(ArrayList<Actor> actorsOnScreen){
		
		Actor player1 = this;
		
		for (int i = 0; i<actorsOnScreen.size(); i++){
			if (actorsOnScreen.get(i) instanceof Player1){
				player1 = actorsOnScreen.get(i);
				break;
			}
		}
		
		this.x = player1.x;
		this.y = player1.y;
	}
	
	public Direction getFacingDirection(){
		return facingDirection;
	}
	
	public Actor getHitbox(){
		return hitbox;
	}
	
	public void damage(Actor actor){
		if (actor instanceof Enemy){
			((Enemy) actor).damageHealth(damageGiven);
		}
	}
	
	public void destroy(){
		GameEngine.actorsOnScreen.remove(this);
	}

}
