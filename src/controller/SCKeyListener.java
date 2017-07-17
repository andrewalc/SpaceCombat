package controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Map;

/**
 * A Keylistener for a view to control the SC game.
 */
public class SCKeyListener implements KeyListener{
    private Map<Integer, Runnable> keyPressedMap;

    public SCKeyListener(){

    }

    public void setKeyPressedMap(Map<Integer, Runnable> map){
        this.keyPressedMap = map;
    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(keyPressedMap.containsKey(e.getKeyCode())){
            keyPressedMap.get(e.getKeyCode()).run();
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
