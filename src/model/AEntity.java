package model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrew on 7/15/17.
 */
public abstract class AEntity {
    // Make sure these are set properly in implementations
    protected Point position;
    protected Point velocity;
    protected int hitbox;
    protected int hp;

    protected boolean visible = true;
    protected boolean invincible = false;
    protected List<ABullet> bullets;

    /**
     * Turns invincibility on.
     */
    public void enableInvincibility(){
        this.invincible = true;
    }

    /**
     * Turns invincibility off.
     */
    public void disableInvincibility(){
        this.invincible = false;
    }

    public int getHp() {
        return hp;
    }

    public Point getPosition() {
        return position;
    }
    public Point getVelocity() {
        return velocity;
    }

    public void kill(){
        this.hp = 0;
    }

    public void damage(int damageTaken){
        this.hp -= damageTaken;
    }

    public void enableVisibility(){
        this.visible = true;
    }

    public void disableVisibility(){
        this.visible = false;
    }

    public boolean isInvincible() {
        return invincible;
    }

    public boolean isVisible() {
        return visible;
    }

    public abstract void move();

    public void cleanUpBullets() {
        for (int i = 0; i < this.bullets.size() ; i++){
            if (!this.bullets.get(i).visible){
                this.bullets.remove(i);
            }
        }
    }

    public List<ABullet> getBullets() {
        return bullets;
    }

}
