package model;

import view.SCView;


/**
 * Abstract class representing a projectile (ex: Bullets, Asteroids).
 */
public abstract class AProjectile extends AEntity implements IMoveable {


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
