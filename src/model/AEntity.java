package model;

import java.awt.Point;
import java.util.List;

/**
 * Abstract class for an entity, particularly space crafts.
 */
public abstract class AEntity {
  // Make sure these are set properly in implementations
  protected Point position;
  protected Point velocity;
  protected int hitboxRadius;
  protected int hp;

  // Boolean states
  protected boolean visible = true;
  protected boolean invincible = false;

  // TODO: Consider making enemy bullets a field in GameState instead of being tied to a enemy.
  // When an enemy is removed from the game all their bullets get deleted too. They should
  // probably persist. Workaround?
  protected List<AProjectile> bullets;

  /**
   * Turns invincibility on.
   */
  public void enableInvincibility() {
    this.invincible = true;
  }

  /**
   * Turns invincibility off.
   */
  public void disableInvincibility() {
    this.invincible = false;
  }

  /**
   * Returns the entity's current hp.
   *
   * @return Current HP.
   */
  public int getHp() {
    return hp;
  }

  /**
   * Returns the current position of the entity as a point.
   *
   * @return Point representing the current position.
   */
  public Point getPosition() {
    return position;
  }

  /**
   * Returns the current velocity of the entity as a point.
   *
   * @return Point representing the current velocity.
   */
  public Point getVelocity() {
    return velocity;
  }

  /**
   * Kills this entity by setting its HP to 0.
   */
  public void kill() {
    this.hp = 0;
  }

  /**
   * Method that causes this entity to take a given amount of damage. The entity's HP is
   * reduced by the given amount.
   *
   * @param damageTaken Integer representing how much damage to take.
   */
  public void damage(int damageTaken) {
    if (!invincible) {
      this.hp -= damageTaken;
    }
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

  /**
   * Returns whether this entity is currently invincible.
   *
   * @return Boolean representing the entity's current invincibility state.
   */
  public boolean isInvincible() {
    return invincible;
  }

  /**
   * Returns whether this entity is currently visible.
   *
   * @return Boolean representing the entity's current visibility state.
   */
  public boolean isVisible() {
    return visible;
  }

  /**
   * Causes the entity to move accordingly by referring to its velocity and using it to update
   * it's position. This method should be called on every tick.
   */
  public abstract void move();


  /**
   * Returns the List of bullets (AProjectiles) that belong to this entity.
   *
   * @return A List of (AProjectile) Bullets belonging to this entity.
   */
  public List<AProjectile> getBullets() {
    return bullets;
  }

  /**
   * Checks if any bullets belonging to this entity are no longing visible. If so, it
   * removes them from the list of bullets.
   */
  public void cleanUpBullets() {
    for (int i = 0; i < this.bullets.size(); i++) {
      if (!this.bullets.get(i).visible) {
        this.bullets.remove(i);
      }
    }
  }


}
