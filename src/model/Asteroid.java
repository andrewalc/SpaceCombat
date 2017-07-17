package model;

import java.awt.*;

/**
 * Class representing an asteroid that should fly across the screen.
 */
public class Asteroid extends AProjectile {
    public static final int ASTEROID_RADIUS = 8;
    public static final int ASTEROID_SPEED = 8;
    public static final int ASTEROID_RADIUS_HITBOX = ASTEROID_RADIUS + 3;

    public Asteroid(Point position){
        this.position = position;
        setVelocity(new Point(-ASTEROID_SPEED, 0));
    }

    public void move(){
        this.position.x += this.getVelocity().getX();
        this.checkVisibility();
    }
}
