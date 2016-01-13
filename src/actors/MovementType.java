package actors;

public enum MovementType {
	PLAYER, DEAD, LOCKED, CIRCLE, GRAVITATE, ATTRACT
}

//PLAYER: for player
//DEAD: can move but does not move on its own
//LOCKED: can't move
//CIRCLE: moves in a circular pattern
//GRAVITATE: gravitates towards the actor's focus using the actor's gravity constant
//ATTRACT: gravitates but is stronger when farther away from the actor focus