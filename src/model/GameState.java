package model;

import view.SCView;
import view.SpaceField;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Class containing all objects necessary to have a functioning game of SC.
 */
public class GameState {
    // Objects
    private Craft craft;

    private ArrayList<Asteroid> asteroids = new ArrayList<Asteroid>();

    private ArrayList<EnemyCraft> enemies = new ArrayList<EnemyCraft>();


    private boolean gameOver = false;

    public static final int RELOAD_TIME = 80;
    public static final int DAMAGE_ASTEROID_COLLISION = 5;
    public static final int SCORE_ASTEROID = 10;
    public static final int INVINCIBILITY_FRAMES = 25;
    public static final int MAX_ENEMY_CRAFT = 9;



    private int ammoFutureTick = -1;
    private int invincibilityTick = -1;

    public GameState(Point spawnPosition){
        this.craft = new Craft(spawnPosition);
    }

    public Craft getPlayer(){
        return this.craft;
    }

    public ArrayList<Asteroid> getAsteroids() {
        return asteroids;
    }

    public ArrayList<EnemyCraft> getEnemies() {
        return enemies;
    }


    public void updateIsGameOver(){
        if (this.craft.getHp() <= 0){
            this.gameOver = true;
        }
    }

    public boolean isGameOver(){
        return gameOver;
    }

    public void onTick(int tickCount) {
        updateIsGameOver();
        spawnAsteroids(tickCount);
        spawnEnemyCrafts(tickCount);
        System.out.println(this.enemies.size());
        craft.move();
        invincibilityFrames(tickCount);
        playerCollisions(tickCount);
        bulletAsteroidCollisions();
        manageBullets();
        manageAmmo(tickCount);
        manageAsteroids();

    }

    private void spawnEnemyCrafts(int tickCount) {
        if (tickCount % 90 == 0 && this.enemies.size() < MAX_ENEMY_CRAFT){
            this.enemies.add(new EnemyCraft(new Point(SCView.WINDOW_WIDTH - EnemyCraft.ENEMY_HITBOX
                    - (int) ( Math.random()*100), this.generateYVal()), tickCount));
        }
    }

    public void bulletAsteroidCollisions() {
        for (ABullet b: this.craft.getBullets()){
            for (Asteroid a: this.asteroids){
                if  (Math.abs(a.getPosition().getX() - b.getPosition().getX())
                        < Asteroid.ASTEROID_RADIUS_HITBOX && Math.abs(a.getPosition().getY() - b.getPosition().getY()) < Asteroid.ASTEROID_RADIUS_HITBOX){
                    System.out.println("COLLISION!!!!!");
                    a.collisionWithBullet();
                    b.collisionWithObject();
                    this.craft.addToScore(SCORE_ASTEROID);
                }
            }
        }
    }


    public void playerCollisions(int tickCount) {
        for (Asteroid a : this.asteroids) {
            if (Math.abs(this.craft.getPosition().getX() - a.getPosition().getX()) < Asteroid.ASTEROID_RADIUS_HITBOX
                    && Math.abs(this.craft.getPosition().getY() - a.getPosition().getY()) < Asteroid.ASTEROID_RADIUS_HITBOX) {
                if (!this.craft.isInvincible()) {
                    if (this.craft.getHp() - DAMAGE_ASTEROID_COLLISION < 0) {
                        this.craft.kill();
                    } else {
                        this.craft.damage(DAMAGE_ASTEROID_COLLISION);
                    }
                }
                this.damageInvincibilityTrigger(tickCount);
            }
        }
    }

    public void damageInvincibilityTrigger(int tickCount) {
            if (this.invincibilityTick == -1) {
                int currentTick = tickCount;
                this.invincibilityTick = currentTick + INVINCIBILITY_FRAMES;
                this.craft.enableInvincibility();
            }

    }

    private void invincibilityFrames(int tickCount) {
        // Produces the flashing effect to display invincibility.
        if (this.craft.isInvincible() && tickCount % 2 == 0 ){
            this.craft.disableVisibility();
        }
        else{
            this.craft.enableVisibility();
        }

        // Disable invincibility when tick is reached.
        if (this.craft.isInvincible() && tickCount == this.invincibilityTick) {
            this.invincibilityTick = -1;
            this.craft.enableVisibility();
            this.craft.disableInvincibility();
        }
    }

    private void spawnAsteroids(int tickCount) {
        if (tickCount % 7 == 0) {
            this.asteroids.add(new Asteroid(new Point(SCView.WINDOW_WIDTH + Asteroid.ASTEROID_RADIUS * 2, this.generateYVal())));
        }
    }

    private void manageAsteroids() {
        // Move all astroids
        for (Asteroid a : this.asteroids){
            a.move();
        }
        // If any of them are offscreen, clean up.
        this.cleanUpAsteroids();
    }

    public void cleanUpAsteroids() {
        for (int i = 0; i < this.asteroids.size(); i++){
            if (!this.asteroids.get(i).isVisible()){
                this.asteroids.remove(i);
            }
        }
    }

    public int generateYVal() {
        int min = Craft.PLAYER_HITBOX/2;
        int max = (int) SpaceField.FIELD_DIM.getHeight() - Craft.PLAYER_HITBOX/2;
        return new Random().nextInt(max + 1 - min) + min;
    }

    public void manageBullets() {
        // move all bullets
        for (ABullet b : craft.getBullets()) {
            b.move();
        }

        // bullet garbage collection
        craft.cleanUpBullets();
    }

    public void manageAmmo(int tickCount) {

        if (this.craft.isReloadingAmmo() && ammoFutureTick == -1){
            ammoFutureTick = tickCount + RELOAD_TIME;
            //hud.promptReloading();
            System.out.print("Currently at Tick: " + tickCount+ " \nLooking to reload at tick: " + ammoFutureTick + "\n");
        }


        if (tickCount == ammoFutureTick){
            craft.reloadAmmo();
            ammoFutureTick = -1;
            //hud.promptReloaded();
        }

    }


}
