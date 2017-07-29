package model;


import java.awt.Point;

/**
 * Class that represents a bullet that EnemyCrafts shoot.
 */
public class EnemyBullet extends AProjectile {

  // Constants
  public static final int ENEMY_BULLET_HEIGHT = 4;
  public static final int ENEMY_BULLET_WIDTH = 10;
  private static final int ENEMY_BULLET_SPEED = 17;

  /**
   * Constructor for an enemy bullet. Requires a Point representing the bullet's spawn point.
   *
   * @param spawnPoint Point where this bullet should spawn.
   */
  public EnemyBullet(Point spawnPoint) {
    this.position = spawnPoint;
    setVelocity(new Point(-ENEMY_BULLET_SPEED, 0));
  }

  @Override
  public void move() {
    this.position.x += this.getVelocity().getX();
    this.checkVisibility();
  }
}
