package model;

import java.awt.*;

/**
 * Class representing an Enemy's Bullets.
 */
public class EnemyBullet extends ABullet {
    public static final int ENEMY_BULLET_HEIGHT = 4;
    public static final int ENEMY_BULLET_WIDTH = 10;
    private static final int ENEMY_BULLET_SPEED = 35;
    /**
     * Constructor for an enemy bullet. Requires a point for the bullet to spawn at.
     * @param spawnPoint The point where this bullet should spawn.
     */
    public EnemyBullet(Point spawnPoint){
        this.position = spawnPoint;
    }
    /**
     * Method call that moves the bullet.
     */
    public void move() {
        this.position.x += ENEMY_BULLET_SPEED;
        this.checkVisibility();
    }
}
