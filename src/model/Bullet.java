package model;

import java.awt.*;

/**
 * Class that represents a standard bullet.
 */
public class Bullet extends AProjectile {
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
        setVelocity(new Point(BULLET_SPEED, 0));
    }

    /**
     * Method call that moves the bullet.
     */
    public void move() {
        this.position.x += this.getVelocity().getX();
        this.checkVisibility();
    }
}
