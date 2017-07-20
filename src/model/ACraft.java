package model;

import java.util.List;

/**
 * Abstract class for a space craft.
 */
public abstract class ACraft extends AEntity implements IMoveable {

  // Make sure these are set properly in implementations
  protected int hitboxRadius;
  protected int hp;

  // TODO: Consider making enemy bullets a field in GameState instead of being tied to a enemy.
  // When an enemy is removed from the game all their bullets get deleted too. They should
  // probably persist. Workaround?
  protected List<AProjectile> bullets;

  // Boolean states
  protected boolean invincible = false;

  /**
   * Returns the entity's current hp.
   *
   * @return Current HP.
   */
  public int getHp() {
    return hp;
  }

  /**
   * Returns the List of bullets (AProjectiles) that belong to this entity.
   *
   * @return A List of (AProjectile) Bullets belonging to this entity.
   */
  public List<AProjectile> getBullets() {
    return bullets;
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
   * Causes the entity to move accordingly by referring to its velocity and using it to update
   * it's position. This method should be called on every tick.
   */
  public abstract void move();

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
    //TODO: Create specific damage methods instead of damage calls everywhere(see damagedByBullet())
    if (!invincible) {
      this.hp -= damageTaken;
    }
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
