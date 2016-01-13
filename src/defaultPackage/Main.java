package defaultPackage;

public class Main {

	public static void main(String[] argv) {
		GameEngine gameEngine = new GameEngine();
		gameEngine.initialize();
		gameEngine.gameLoop();
	}
}
