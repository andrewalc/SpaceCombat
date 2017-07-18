package model;


import java.awt.Point;

/**
 * Class representing an asteroid that should fly across the screen.
 */
public class Asteroid extends AProjectile {

  // Constants
  public static final int ASTEROID_RADIUS = 8;
  public static final int ASTEROID_SPEED = 8;
  public static final int ASTEROID_RADIUS_HITBOX = ASTEROID_RADIUS + 3;

  /**
   * Constructor for an Asteroid. Requires a Point representing the asteroid's spawn point.
   *
   * @param spawnPoint Point where this Asteroid should spawn.
   */
  public Asteroid(Point spawnPoint) {
    this.position = spawnPoint;
    setVelocity(new Point(-ASTEROID_SPEED, 0));
  }

  @Override
  public void move() {
    this.position.x += this.getVelocity().getX();
    this.checkVisibility();
  }
}
