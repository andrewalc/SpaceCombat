package model;

import view.SpaceField;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 * Class that represents an SpaceShooter Space Craft.
 */
public class PlayerCraft extends ACraft {

  // Constants
  public static final int PLAYER_HITBOX_RADIUS = 10;
  private static final int MAX_HP = 100;
  private static final int MAX_AMMO = 25;
  private static final double ACCELERATION = 6;
  private static final double DECCELERATION = 1;
  private static final double ZERO_VELOCITY_THRESHOLD = .1;
  private static final int MAXIMUM_VELOCITY = 20;

  // Values
  private int ammo;
  private int score;


  // Boolean States
  private boolean reloadingAmmo = false;

  /**
   * Constructor for a craft. Requires a point to spawn the craft at.
   * Sets the crafts initial hp, ammo, velocity, score, hitboxRadius, and bullets list.
   *
   * @param spawnPoint Point where the craft should spawn.
   */
  public PlayerCraft(Point spawnPoint) {
    // Spawn point
    this.position = spawnPoint;
    // Set variables
    this.velocity = new Point(0, 0);
    this.hp = MAX_HP;
    this.ammo = MAX_AMMO;
    this.score = 0;
    this.hitboxRadius = PLAYER_HITBOX_RADIUS;
    this.bullets = new ArrayList<AProjectile>();

  }

  /**
   * Returns the Craft's current remaining ammo.
   *
   * @return Craft's remaining ammo.
   */
  public int getAmmo() {
    return ammo;
  }

  /**
   * Returns the Craft's current score.
   *
   * @return Craft's current score.
   */
  public int getScore() {
    return score;
  }

  /**
   * Adds the given value to the Craft's score.
   *
   * @param points Integer to add to the Craft's score.
   */
  public void addToScore(int points) {
    this.score += points;
  }

  /**
   * Returns whether the craft is currently reloading its ammo.
   *
   * @return Craft's reloading state.
   */
  public boolean isReloadingAmmo() {
    return reloadingAmmo;
  }

  /**
   * Begins the reloading ammo process for this craft.
   */
  public void startReloading() {
    System.out.println("Starting Reload");
    this.reloadingAmmo = true;
  }

  /**
   * Reloads this craft's ammo.
   */
  public void reloadAmmo() {
    this.ammo = MAX_AMMO;
    this.reloadingAmmo = false;
    System.out.println("RELOADED!");
  }

  /**
   * Creates and fires a Bullet in front of the Crafts current position on call.
   */
  private void fireBullet() {
    this.bullets.add(new Bullet(new Point(this.position.x + Bullet.BULLET_WIDTH, this.position.y)));
  }


  /**
   * Method that handles KeyEvents for the Craft. Includes directional movement, reloading, and
   * shooting bullets.
   *
   * @param keyEvent Int Keyevent to interpret.
   */
  public void keyMove(int keyEvent) {
    switch (keyEvent) {
      case KeyEvent.VK_UP:
        this.velocity.y += -ACCELERATION;
        break;
      case KeyEvent.VK_DOWN:
        this.velocity.y += ACCELERATION;
        break;
      case KeyEvent.VK_LEFT:
        this.velocity.x += -ACCELERATION;
        break;
      case KeyEvent.VK_RIGHT:
        this.velocity.x += ACCELERATION;
        break;
      case KeyEvent.VK_R:
        this.startReloading();
        break;
      case KeyEvent.VK_SPACE:
        if (!this.reloadingAmmo && this.ammo > 0) {
          this.fireBullet();
          this.ammo--;
        }
        break;

      default:
        break;
    }
  }

  @Override
  public void move() {

    // Set a new position given the current X and Y velocity.
    this.position.x += this.velocity.x;
    this.position.y += this.velocity.y;

    // Manages slowing down in the x+ direction
    if (!(Math.abs(this.velocity.x) < ZERO_VELOCITY_THRESHOLD) && this.velocity.x > 0) {
      this.velocity.x -= DECCELERATION;
    }
    // Manages slowing down in the x- direction
    else if (!(Math.abs(this.velocity.x) < ZERO_VELOCITY_THRESHOLD) && this.velocity.x < 0) {
      this.velocity.x += DECCELERATION;
    }

    // Manages slowing down in the y+ direction
    if (!(Math.abs(this.velocity.y) < ZERO_VELOCITY_THRESHOLD) && this.velocity.y > 0) {
      this.velocity.y -= DECCELERATION;
    }
    // Manages slowing down in the y- direction
    else if (!(Math.abs(this.velocity.y) < ZERO_VELOCITY_THRESHOLD) && this.velocity.y < 0) {
      this.velocity.y += DECCELERATION;
    }

    // Caps the velocity in the +X
    if (Math.abs(this.velocity.x) > MAXIMUM_VELOCITY && this.velocity.x > 0) {
      this.velocity.x = MAXIMUM_VELOCITY;
    }
    // Caps the velocity in the -X
    else if (Math.abs(this.velocity.x) > MAXIMUM_VELOCITY && this.velocity.x < 0) {
      this.velocity.x = -MAXIMUM_VELOCITY;
    }

    // Caps the velocity in the +Y
    if (Math.abs(this.velocity.y) > MAXIMUM_VELOCITY && this.velocity.y > 0) {
      this.velocity.y = MAXIMUM_VELOCITY;
    }
    // Caps the velocity in the -Y
    else if (Math.abs(this.velocity.y) > MAXIMUM_VELOCITY && this.velocity.y < 0) {
      this.velocity.y = -MAXIMUM_VELOCITY;
    }

    // Restricts out of bounds area, resets velocity if the edge is hit.
    if (this.position.x > SpaceField.FIELD_DIM.getWidth() - this.hitboxRadius) {
      this.position.x = (int) SpaceField.FIELD_DIM.getWidth() - this.hitboxRadius;
      this.velocity.x = 0;
    }
    if (this.position.x < 0 + this.hitboxRadius) {
      this.position.x = 0 + this.hitboxRadius;
      this.velocity.x = 0;

    }
    if (this.position.y > (int) SpaceField.FIELD_DIM.getHeight() - this.hitboxRadius) {
      this.position.y = (int) SpaceField.FIELD_DIM.getHeight() - this.hitboxRadius;
      this.velocity.y = 0;

    }
    if (this.position.y < 0 + this.hitboxRadius) {
      this.position.y = 0 + this.hitboxRadius;
      this.velocity.y = 0;

    }
  }
}
