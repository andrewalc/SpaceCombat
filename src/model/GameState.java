package model;

import view.SpaceField;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

/**
 * Class containing all objects necessary to have a functioning game of SC.
 */
public class GameState {
    // Objects
    private PlayerCraft craft;

    private ArrayList<Asteroid> asteroids = new ArrayList<Asteroid>();

    private ArrayList<EnemyCraft> enemies = new ArrayList<EnemyCraft>();


    private boolean gameOver = false;

    public static final int RELOAD_TIME = 80;
    public static final int DAMAGE_ASTEROID_COLLISION = 5;
    public static final int DAMAGE_ENEMY_BULLET = 10;
    public static final int DAMAGE_ENEMY_CRAFT_COLLISION = 15;
    public static final int DAMAGE_PLAYER_BULLET = 1;


    public static final int SCORE_ASTEROID = 10;
    public static final int SCORE_ENEMY_CRAFT = 100;

    public static final int INVINCIBILITY_FRAMES = 25;
    public static final int MAX_ENEMY_CRAFT = 4;



    private int ammoFutureTick = -1;
    private int invincibilityTick = -1;

    public GameState(Point spawnPosition){
        this.craft = new PlayerCraft(spawnPosition);
    }

    public PlayerCraft getPlayer(){
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
        craft.move();
        manageEnemyCrafts(tickCount);
        invincibilityFrames(tickCount);
        playerCollisions(tickCount);
        bulletAsteroidCollisions();
        bulletEnemyCraftCollisions();
        manageBullets();
        manageAmmo(tickCount);
        manageAsteroids();

    }

    private void spawnEnemyCrafts(int tickCount) {
        if (tickCount % 90 == 0 && this.enemies.size() < MAX_ENEMY_CRAFT){
            this.enemies.add(new EnemyCraft(new Point((int) SpaceField.FIELD_DIM.getWidth() - EnemyCraft.ENEMY_HITBOX_RADIUS
                    - (int) ( Math.random()*100), this.generateYVal()), tickCount));
        }
    }


    private void manageEnemyCrafts(int tickCount) {

        for (EnemyCraft enemy: this.enemies){
            if (enemy.hp < 1){
                enemy.disableVisibility();
            }
            enemy.move();
            if ((tickCount - enemy.getTickCreated()) % 33 == 0){
                enemy.fireBullet();
            }
            // on tick, switch the direction the enemy is going.
            if((tickCount - enemy.getTickCreated()) % enemy.getVelocity().getY() == 0){
                enemy.moveTrigger();
            }
            for (AProjectile eb : enemy.getBullets()){
                eb.move();
            }
        }

        this.cleanUpEnemyCrafts();
    }

    public void cleanUpEnemyCrafts() {
        for (int i = 0; i < this.enemies.size(); i++){
            if (!this.enemies.get(i).isVisible()){
                this.enemies.remove(i);
                this.craft.addToScore(SCORE_ENEMY_CRAFT);
            }
        }
    }
    // TODO: Sometimes get a concurrent modification error with methods using for each on ABullets.
    // May have to do with the cleanup methods removing bullets when not visible. Hard to determine since the error isn't
    // thrown too often. Try getting rid of the removing functionality.
    private void bulletEnemyCraftCollisions() {
        for (AProjectile b: this.craft.getBullets()){
            for (EnemyCraft enemy: this.enemies){
                if  (Math.abs(enemy.getPosition().getX() - b.getPosition().getX()) < Bullet.BULLET_WIDTH/2 + EnemyCraft.ENEMY_HITBOX_RADIUS
                        && Math.abs(enemy.getPosition().getY() - b.getPosition().getY()) < Bullet.BULLET_HEIGHT/2 + EnemyCraft.ENEMY_HITBOX_RADIUS){
                    System.out.println("ENEMY COLLISION!!!!!");
                    enemy.damagedByBullet();
                    b.collisionWithObject();
                }
            }
        }
    }

    public void bulletAsteroidCollisions() {
        for (AProjectile b: this.craft.getBullets()){
            for (Asteroid a: this.asteroids){
                if  (Math.abs(a.getPosition().getX() - b.getPosition().getX())
                        < Asteroid.ASTEROID_RADIUS_HITBOX && Math.abs(a.getPosition().getY() - b.getPosition().getY()) < Asteroid.ASTEROID_RADIUS_HITBOX){
                    System.out.println("COLLISION!!!!!");
                    a.collisionWithObject();
                    b.collisionWithObject();
                    this.craft.addToScore(SCORE_ASTEROID);
                }
            }
        }
    }


    public void playerCollisions(int tickCount) {
        for (Asteroid a : this.asteroids) {
            // Collision dectection is treated by checking x and y axis only, so just imagine squares around both.
            if (Math.abs(this.craft.getPosition().getX() - a.getPosition().getX()) < PlayerCraft.PLAYER_HITBOX_RADIUS + Asteroid.ASTEROID_RADIUS_HITBOX
                    && Math.abs(this.craft.getPosition().getY() - a.getPosition().getY()) < PlayerCraft.PLAYER_HITBOX_RADIUS + Asteroid.ASTEROID_RADIUS_HITBOX) {
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

        for (EnemyCraft enemy : this.enemies) {
            // Collision dectection is treated by checking x and y axis only, so just imagine squares around both.
            if (Math.abs(this.craft.position.getX() - enemy.position.getX()) < PlayerCraft.PLAYER_HITBOX_RADIUS + EnemyCraft.ENEMY_HITBOX_RADIUS
                    && Math.abs(this.craft.position.getY() - enemy.position.getY()) < PlayerCraft.PLAYER_HITBOX_RADIUS + EnemyCraft.ENEMY_HITBOX_RADIUS) {
                if (!this.craft.isInvincible()) {
                    if (this.craft.getHp() - DAMAGE_ENEMY_CRAFT_COLLISION < 0) {
                        craft.kill();
                    }
                    else {
                        craft.damage(DAMAGE_ENEMY_CRAFT_COLLISION);
                    }
                }
                this.damageInvincibilityTrigger(tickCount);
            }

            for (AProjectile eb : enemy.getBullets()) {
                if (Math.abs(this.craft.getPosition().getX() - eb.getPosition().getX()) < PlayerCraft.PLAYER_HITBOX_RADIUS + EnemyBullet.ENEMY_BULLET_WIDTH/2
                        && Math.abs(this.craft.getPosition().getY() - eb.getPosition().getY()) < PlayerCraft.PLAYER_HITBOX_RADIUS + EnemyBullet.ENEMY_BULLET_HEIGHT/2) {
                    if (!this.craft.invincible) {
                        if (this.craft.getHp() - DAMAGE_ENEMY_BULLET < 0) {
                            craft.kill();
                        }
                        else {
                            craft.damage(DAMAGE_ENEMY_BULLET);
                            eb.collisionWithObject();
                        }
                    }
                    this.damageInvincibilityTrigger(tickCount);
                }
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
            this.asteroids.add(new Asteroid(
                    new Point((int) SpaceField.FIELD_DIM.getWidth() + Asteroid.ASTEROID_RADIUS * 2, this.generateYVal())));
        }
    }

    private void manageAsteroids() {
        // Move all asteroids
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
        int min = PlayerCraft.PLAYER_HITBOX_RADIUS;
        int max = (int) SpaceField.FIELD_DIM.getHeight() - PlayerCraft.PLAYER_HITBOX_RADIUS;
        return new Random().nextInt(max + 1 - min) + min;
    }

    public void manageBullets() {
        // move all bullets
        for (AProjectile b : craft.getBullets()) {
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
