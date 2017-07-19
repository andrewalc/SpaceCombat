package model;


import java.awt.Point;

/**
 * Class that represents a standard bullet that a player shoots.
 */
public class Bullet extends AProjectile {

  // Constants
  public static final int BULLET_SPEED = 15;
  public static final int BULLET_HEIGHT = 4;
  public static final int BULLET_WIDTH = 6;

  /**
   * Constructor for a bullet. Requires a Point representing the bullet's spawn point.
   *
   * @param spawnPoint Point where this bullet should spawn.
   */
  public Bullet(Point spawnPoint) {
    this.position = spawnPoint;
    setVelocity(new Point(BULLET_SPEED, 0));
  }

  @Override
  public void move() {
    this.position.x += this.getVelocity().getX();
    this.checkVisibility();
  }
}
