package model;

import view.SCView;

import java.awt.*;

/**
 * Class that represents a standard bullet.
 */
public class Bullet {

    // Values
    private Point position;
    private Point velocity;

    // Boolean states
    boolean visible = true;

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

    /**
     * Checks whether this bullet is still on screen and should be rendered.
     */
    public void checkVisibility() {
        if (this.position.x > SCView.WINDOW_WIDTH) {
            this.visible = false;
        }
    }

    /**
     * Called when this bullet collides with object. Its visibility is now false.
     */
    public void collisionWithObject() {
        this.visible = false;
    }

    public Point getPosition() {
        return position;
    }

}
