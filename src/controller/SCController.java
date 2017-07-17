package controller;

import model.GameState;
import view.SCView;

import java.awt.event.KeyEvent;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

/**
 * Controller for SpaceCombat.
 */
public class SCController {
    private GameState state;
    private SCView view;

    private int tickCount = 0;


    public SCController(GameState state, SCView view){
        this.state = state;
        this.view = view;
        view.updateView(state, 0);
        configureSCKeyListener();
    }

    private void configureSCKeyListener(){
        // Map for Keys to commands
        Map<Integer, Runnable> keyPresses = new HashMap<Integer, Runnable>();

        // Move up
        keyPresses.put(KeyEvent.VK_UP, () -> {
           state.getPlayer().keyMove(KeyEvent.VK_UP);
        });

        // Move down
        keyPresses.put(KeyEvent.VK_DOWN, () -> {
            state.getPlayer().keyMove(KeyEvent.VK_DOWN);
        });

        // Move left
        keyPresses.put(KeyEvent.VK_LEFT, () -> {
            state.getPlayer().keyMove(KeyEvent.VK_LEFT);
        });

        // Move right
        keyPresses.put(KeyEvent.VK_RIGHT, () -> {
            state.getPlayer().keyMove(KeyEvent.VK_RIGHT);
        });

        // Fire a bullet.
        keyPresses.put(KeyEvent.VK_SPACE, () -> {
            state.getPlayer().keyMove(KeyEvent.VK_SPACE);
        });

        // Reload your ammo.
        keyPresses.put(KeyEvent.VK_R, () -> {
            state.getPlayer().keyMove(KeyEvent.VK_R);
        });

        SCKeyListener kl = new SCKeyListener();
        kl.setKeyPressedMap(keyPresses);
        view.addKeyListener(kl);
    }

    public void startSpaceCombat(){
        view.initialize();
        while(!state.isGameOver()){
            Instant start = Instant.now();

            state.onTick(tickCount);
            view.updateView(state, tickCount);
            Instant after = Instant.now();
            while(Duration.between(start, after).toMillis() < 1000/30.0){
                after = Instant.now();
            }

            tickCount++;
        }
    }


    public int getTickCount() {
        return tickCount;
    }
}
