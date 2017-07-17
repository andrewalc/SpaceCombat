package model;


import java.awt.Point;

/**
 * Class representing an Enemy's Bullets.
 */
public class EnemyBullet extends AProjectile {
    public static final int ENEMY_BULLET_HEIGHT = 4;
    public static final int ENEMY_BULLET_WIDTH = 10;
    private static final int ENEMY_BULLET_SPEED = 17;
    /**
     * Constructor for an enemy bullet. Requires a point for the bullet to spawn at.
     * @param spawnPoint The point where this bullet should spawn.
     */
    public EnemyBullet(Point spawnPoint){
        this.position = spawnPoint;
        setVelocity(new Point(-ENEMY_BULLET_SPEED, 0));
    }
    /**
     * Method call that moves the bullet.
     */
    public void move() {
        this.position.x += this.getVelocity().getX();
        this.checkVisibility();
    }
}
