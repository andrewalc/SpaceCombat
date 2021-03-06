package model;

import view.SpaceField;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

/**
 * Class that represents an SpaceShooter Enemy Space Craft.
 */
public class EnemyCraft extends ACraft {

  // Constants
  public static final int ENEMY_HITBOX_RADIUS = 20;
  private static final int ENEMY_MAX_HP = 5;
  private static final double ENEMY_ACCELERATION = 6;
  private static final double ENEMY_DECCELERATION = 1;
  private static final double ENEMY_ZERO_VELOCITY_THRESHOLD = .1;
  private static final int ENEMY_MAXIMUM_VELOCITY = 20;

  // Values
  private int tickCreated;
  int moveState = 0;

  /**
   * Constructor for an EnemyCraft. Requires a point to spawn the craft at.
   * Sets the crafts initial hp, velocity, hitboxRadius, and bullets list.
   *
   * @param spawnPoint Point where the craft should spawn.
   */
  public EnemyCraft(Point spawnPoint, int tickCreated) {
    // Spawn point
    this.position = spawnPoint;
    // Set variables
    this.velocity = new Point(0, 0);
    this.hp = ENEMY_MAX_HP;
    this.hitboxRadius = ENEMY_HITBOX_RADIUS;
    this.bullets = new ArrayList<AProjectile>();
    this.tickCreated = tickCreated;
  }

  /**
   * Returns the tick at which this EnemyCraft was created.
   *
   * @return Int tick this enemy craft was created at.
   */
  public int getTickCreated() {
    return tickCreated;
  }

  // Temp way to switch enemys from moving up or down.
  public void moveTrigger(){
    if(moveState == 0){
      moveState = 1;
    }
    else if(moveState == 1){
      moveState = 0;
    }
  }

  /**
   * Creates and fires a Bullet in front of the EnemyCraft's current position on call.
   */
  protected void fireBullet() {
    this.bullets.add(new EnemyBullet(new Point(this.position.x - Bullet.BULLET_WIDTH, this
            .position.y)));
  }

  /**
   * Damages this EnemyCraft by DAMAGE_PLAYER_BULLET amount.
   */
  public void damagedByBullet() {
    this.damage(GameState.DAMAGE_PLAYER_BULLET);
  }

  @Override
  public void move() {
    if(moveState == 0){
      this.setVelocity(new Point(0, (new Random().nextInt(10)) + (int) ENEMY_ACCELERATION));
    }
    else if(moveState == 1){
      this.setVelocity(new Point(0, ((new Random().nextInt(10)) + (int) ENEMY_ACCELERATION) *
              -1));
    }
    this.position.x += this.velocity.x;
    this.position.y += this.velocity.y;
    // slowing down in the x+ direction
    if (!(Math.abs(this.velocity.x) < ENEMY_ZERO_VELOCITY_THRESHOLD) && this.velocity.x > 0) {
      this.velocity.x -= ENEMY_DECCELERATION;
    }
    // slowing down in the x- direction
    else if (!(Math.abs(this.velocity.x) < ENEMY_ZERO_VELOCITY_THRESHOLD) && this.velocity.x < 0) {
      this.velocity.x += ENEMY_DECCELERATION;
    }

    // slowing down in the y+ direction
    if (!(Math.abs(this.velocity.y) < ENEMY_ZERO_VELOCITY_THRESHOLD) && this.velocity.y > 0) {
      this.velocity.y -= ENEMY_DECCELERATION;
    }
    // slowing down in the y- direction
    else if (!(Math.abs(this.velocity.y) < ENEMY_ZERO_VELOCITY_THRESHOLD) && this.velocity.y < 0) {
      this.velocity.y += ENEMY_DECCELERATION;
    }

    //
    if (Math.abs(this.velocity.x) > ENEMY_MAXIMUM_VELOCITY && this.velocity.x > 0) {
      this.velocity.x = ENEMY_MAXIMUM_VELOCITY;
    } else if (Math.abs(this.velocity.x) > ENEMY_MAXIMUM_VELOCITY && this.velocity.x < 0) {
      this.velocity.x = -ENEMY_MAXIMUM_VELOCITY;
    }

    if (Math.abs(this.velocity.y) > ENEMY_MAXIMUM_VELOCITY && this.velocity.y > 0) {
      this.velocity.y = ENEMY_MAXIMUM_VELOCITY;
    } else if (Math.abs(this.velocity.y) > ENEMY_MAXIMUM_VELOCITY && this.velocity.y < 0) {
      this.velocity.y = -ENEMY_MAXIMUM_VELOCITY;
    }

    // blocking out of bounds area and resetting velocity
    if (this.position.x > SpaceField.FIELD_DIM.getWidth() - this.hitboxRadius / 2) {
      this.position.x = (int) SpaceField.FIELD_DIM.getWidth() - this.hitboxRadius / 2;
    }
    if (this.position.x < 0 + this.hitboxRadius / 2) {
      this.position.x = 0 + this.hitboxRadius / 2;

    }
    if (this.position.y > (int) SpaceField.FIELD_DIM.getHeight() - this.hitboxRadius / 2) {
      this.position.y = (int) SpaceField.FIELD_DIM.getHeight() - this.hitboxRadius / 2;

    }
    if (this.position.y < 0 + this.hitboxRadius / 2) {
      this.position.y = 0 + this.hitboxRadius / 2;

    }
  }


}
