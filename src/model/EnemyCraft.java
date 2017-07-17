package model;

import view.SpaceField;

import java.awt.Point;
import java.util.ArrayList;

/**
 * Class representing an Enemy Craft.
 */
public class EnemyCraft extends AEntity{

    private int tickCreated;
    public static final int ENEMY_HITBOX_RADIUS = 20;
    private static final int ENEMY_MAX_HP = 5;

    private static final double ENEMY_ACCELERATION = 6;
    private static final double ENEMY_DECCELERATION = 1;
    private static final double ENEMY_ZERO_VELOCITY_THRESHOLD = .1;
    private static final int ENEMY_MAXIMUM_VELOCITY = 20;

    public EnemyCraft(Point spawnpoint, int tickCreated){
        // Spawn point
        this.position = spawnpoint;
        this.velocity = new Point(0,0);
        this.hp = ENEMY_MAX_HP;
        this.hitboxRadius = ENEMY_HITBOX_RADIUS;
        this.bullets = new ArrayList<AProjectile>();
        this.tickCreated = tickCreated;
    }

    public int getTickCreated() {
        return tickCreated;
    }

    protected void fireBullet() {
        this.bullets.add(new EnemyBullet(new Point(this.position.x - Bullet.BULLET_WIDTH, this.position.y)));
    }

    @Override
    public void move() {
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
        else if (!(Math.abs(this.velocity.y) <ENEMY_ZERO_VELOCITY_THRESHOLD) && this.velocity.y < 0) {
            this.velocity.y += ENEMY_DECCELERATION;
        }

        //
        if (Math.abs(this.velocity.x) > ENEMY_MAXIMUM_VELOCITY && this.velocity.x > 0) {
            this.velocity.x = ENEMY_MAXIMUM_VELOCITY;
        }
        else if (Math.abs(this.velocity.x) > ENEMY_MAXIMUM_VELOCITY && this.velocity.x < 0) {
            this.velocity.x = -ENEMY_MAXIMUM_VELOCITY;
        }

        if (Math.abs(this.velocity.y) > ENEMY_MAXIMUM_VELOCITY && this.velocity.y > 0) {
            this.velocity.y = ENEMY_MAXIMUM_VELOCITY;
        }
        else if (Math.abs(this.velocity.y) > ENEMY_MAXIMUM_VELOCITY && this.velocity.y < 0) {
            this.velocity.y = -ENEMY_MAXIMUM_VELOCITY;
        }

        // blocking out of bounds area and resetting velocity
        if (this.position.x > SpaceField.FIELD_DIM.getWidth() - this.hitboxRadius /2) {
            this.position.x = (int) SpaceField.FIELD_DIM.getWidth() - this.hitboxRadius /2;
            this.velocity.x = 0;
        }
        if (this.position.x < 0 + this.hitboxRadius /2) {
            this.position.x = 0 + this.hitboxRadius /2;
            this.velocity.x = 0;

        }
        if (this.position.y > (int) SpaceField.FIELD_DIM.getHeight() - this.hitboxRadius /2) {
            this.position.y = (int) SpaceField.FIELD_DIM.getHeight() - this.hitboxRadius /2;
            this.velocity.y = 0;

        }
        if (this.position.y < 0 + this.hitboxRadius /2) {
            this.position.y = 0 + this.hitboxRadius /2;
            this.velocity.y = 0;

        }
    }

    public void collisionWithBullet() {
        this.damage(GameState.DAMAGE_PLAYER_BULLET);
    }
}
