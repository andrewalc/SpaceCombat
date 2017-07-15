package model;

import view.SCView;

import java.awt.*;

/**
 * Class that represents a standard bullet.
 */
public class Bullet extends ABullet{
    // Constants
    public static final int BULLET_SPEED = 15;
    public static final int BULLET_HEIGHT = 4;
    public static final int BULLET_WIDTH = 6;

    /**
     * Constructor for a bullet. Requires a point for the bullet to spawn at.
     * @param spawnPoint The point where this bullet should spawn.
     */
    public Bullet(Point spawnPoint){
        this.position = spawnPoint;
    }

    /**
     * Method call that moves the bullet.
     */
    public void move() {
        this.position.x += BULLET_SPEED;
        this.checkVisibility();
    }
}
