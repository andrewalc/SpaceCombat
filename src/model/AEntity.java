package model;

import java.awt.Point;

/**
 * @author Ethan McCue
 *
 * Class to represent any entity that can be placed in the game
 */
public abstract class AEntity {

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
   * Enables this entity's visibility.
   */
  public void enableVisibility() {
    this.visible = true;
  }

  /**
   * Disables this entity's visibility.
   */
  public void disableVisibility() {
    this.visible = false;
  }


}
