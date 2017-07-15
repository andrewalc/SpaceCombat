package model;

import view.SpaceField;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 * Class that represents an SpaceShooter AirCraft.
 */
public class Craft extends AEntity {

    // Constants
    public static final int PLAYER_HITBOX = 20;
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
         * Sets the crafts initial hp, ammo, velocity, and score.
         * @param spawnPoint Point where the craft should spawn.
         */
    public Craft(Point spawnPoint){
            // Spawn point
        this.position = spawnPoint;
        // Set variables
        this.velocity = new Point(0,0);
        this.hp = MAX_HP;
        this.ammo = MAX_AMMO;
        this.score = 0;
        this.hitbox = PLAYER_HITBOX;
    }

    /**
     * Begins the reloading process for this craft.
     */
    public void startReloading() {
        System.out.println("Starting Reload");
        this.reloadingAmmo = true;
    }

    /**
     * Reloads this crafts ammo.
     */
    public void reloadAmmo(){
        this.ammo = MAX_AMMO;
        this.reloadingAmmo = false;
        System.out.println("RELOADED!");
    }


    public void move() {
        this.position.x += this.velocity.x;
        this.position.y += this.velocity.y;
        // slowing down in the x+ direction
        if (!(Math.abs(this.velocity.x) < ZERO_VELOCITY_THRESHOLD) && this.velocity.x > 0) {
            this.velocity.x -= DECCELERATION;
        }
        // slowing down in the x- direction
        else if (!(Math.abs(this.velocity.x) < ZERO_VELOCITY_THRESHOLD) && this.velocity.x < 0) {
            this.velocity.x += DECCELERATION;
        }

        // slowing down in the y+ direction
        if (!(Math.abs(this.velocity.y) < ZERO_VELOCITY_THRESHOLD) && this.velocity.y > 0) {
            this.velocity.y -= DECCELERATION;
        }
        // slowing down in the y- direction
        else if (!(Math.abs(this.velocity.y) < ZERO_VELOCITY_THRESHOLD) && this.velocity.y < 0) {
            this.velocity.y += DECCELERATION;
        }

        //
        if (Math.abs(this.velocity.x) > MAXIMUM_VELOCITY && this.velocity.x > 0) {
            this.velocity.x = MAXIMUM_VELOCITY;
        }
        else if (Math.abs(this.velocity.x) > MAXIMUM_VELOCITY && this.velocity.x < 0) {
            this.velocity.x = -MAXIMUM_VELOCITY;
        }

        if (Math.abs(this.velocity.y) > MAXIMUM_VELOCITY && this.velocity.y > 0) {
            this.velocity.y = MAXIMUM_VELOCITY;
        }
        else if (Math.abs(this.velocity.y) > MAXIMUM_VELOCITY && this.velocity.y < 0) {
            this.velocity.y = -MAXIMUM_VELOCITY;
        }

        // blocking out of bounds area and resetting velocity
        if (this.position.x > SpaceField.FIELD_DIM.getWidth() - this.hitbox/2) {
            this.position.x = (int) SpaceField.FIELD_DIM.getWidth() - this.hitbox/2;
            this.velocity.x = 0;
        }
        if (this.position.x < 0 + this.hitbox/2) {
            this.position.x = 0 + this.hitbox/2;
            this.velocity.x = 0;

        }
        if (this.position.y > (int) SpaceField.FIELD_DIM.getHeight() - this.hitbox/2) {
            this.position.y = (int) SpaceField.FIELD_DIM.getHeight() - this.hitbox/2;
            this.velocity.y = 0;

        }
        if (this.position.y < 0 + this.hitbox/2) {
            this.position.y = 0 + this.hitbox/2;
            this.velocity.y = 0;

        }
    }

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
                if (!this.reloadingAmmo && this.ammo > 0){
                    this.fireBullet();
                    this.ammo--;
                }
                break;

            default:
                break;
        }
    }

    private void fireBullet() {
        this.bullets.add(new Bullet(new Point(this.position.x + Bullet.BULLET_WIDTH, this.position.y)));
    }

    // Getters

    public int getAmmo() {
        return ammo;
    }

    public int getScore() {
        return score;
    }

    public boolean isReloadingAmmo() {
        return reloadingAmmo;
    }

    public void addToScore(int points){
        this.score += points;
    }
}
