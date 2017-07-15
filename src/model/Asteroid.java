package model;

import java.awt.*;

/**
 * Class representing an asteroid that should fly across the screen.
 */
public class Asteroid {
    public static final int ASTEROID_RADIUS = 8;
    public static final int ASTEROID_SPEED = 8;
    public static final int ASTEROID_RADIUS_HITBOX = ASTEROID_RADIUS + 3;

    private Point position;
    private Point velocity = new Point(-ASTEROID_SPEED, 0);

    private boolean visible = true;

    public Asteroid(Point position){
        this.position = position;
    }

    public void move(){
        this.position.x += this.velocity.x;
        this.checkVisibility();
    }

    public void checkVisibility() {
        if (this.position.x < 0) {
            this.visible = false;
        }
    }

    public void collisionWithBullet() {
        this.visible = false;
    }

    public boolean isVisible() {
        return visible;
    }

    public Point getPosition() {
        return position;
    }
}
