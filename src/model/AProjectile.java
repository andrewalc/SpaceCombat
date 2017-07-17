package model;

import view.SCView;

import java.awt.*;

/**
 * Created by Andrew on 7/15/17.
 */
public abstract class AProjectile {
    // Values
    protected Point position;
    protected Point velocity;

    // Boolean states
    boolean visible = true;

    public Point getPosition() {
        return position;
    }

    /**
     * Called when this bullet collides with object. Its visibility is now false.
     */
    public void collisionWithObject() {
        this.visible = false;
    }

    public abstract void move();

    public boolean isVisible() {
        return visible;
    }
    /**
     * Checks whether this bullet is still on screen and should be rendered.
     */
    public void checkVisibility() {
        if (this.position.x > SCView.WINDOW_WIDTH && this.velocity.getX() > 0  ||
                this.position.x < 0 && this.velocity.getX() < 0) {
            this.visible = false;
        }
    }

    public Point getVelocity() {
        return velocity;
    }

    public void setVelocity(Point velocity) {
        this.velocity = velocity;
    }

}
