package model;

import java.awt.Point;

import view.SCView;


/**
 * Abstract class representing a projectile (ex: Bullets, Asteroids).
 */
public abstract class AProjectile {

  // Values
  protected Point position;
  protected Point velocity;

  // Boolean states
  boolean visible = true;

  /**
   * Returns the current position of the projectile as a point.
   *
   * @return Point representing the current position.
   */
  public Point getPosition() {
    return position;
  }

  /**
   * Returns the current velocity of the projectile as a point.
   *
   * @return Point representing the current velocity.
   */
  public Point getVelocity() {
    return velocity;
  }

  /**
   * Sets the current velocity of the projectile to the given Point, representing its X and Y
   * velocity.
   *
   * @param velocity A Point representing the X and Y velocity this projectile should be set to.
   */
  public void setVelocity(Point velocity) {
    this.velocity = velocity;
  }

  /**
   * Returns whether this projectile is currently visible.
   *
   * @return Boolean representing the projectile's current visibility state.
   */
  public boolean isVisible() {
    return visible;
  }

  /**
   * Causes the projectile to move accordingly by referring to its velocity and using it to update
   * it's position. This method should be called on every tick.
   */
  public abstract void move();


  /**
   * Sets the projectile's visibility to false. Called when this bullet collides with object.
   */
  public void collisionWithObject() {
    //TODO: disableVisibility sounds familiar...
    this.visible = false;
  }

  /**
   * Checks whether this bullet is still on screen. If it isn't, then disable its visibility.
   */
  public void checkVisibility() {
    if (this.position.x > SCView.WINDOW_WIDTH && this.velocity.getX() > 0 ||
            this.position.x < 0 && this.velocity.getX() < 0) {
      this.visible = false;
    }
  }


}
