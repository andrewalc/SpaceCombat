import controller.SCController;
import model.Craft;
import model.GameState;
import view.SCView;

import java.awt.*;

public class SpaceCombat {

	public static void main(String[] args) {
		Point initialPosition = new Point(50, SCView.WINDOW_HEIGHT/2);
		GameState model = new GameState(initialPosition);
		SCView view = new SCView();

		SCController controller = new SCController(model, view);
		controller.startSpaceCombat();
	}
}
