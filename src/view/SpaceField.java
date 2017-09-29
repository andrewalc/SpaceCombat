package view;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import javax.swing.JPanel;

import model.AProjectile;
import model.Asteroid;
import model.Bullet;
import model.PlayerCraft;
import model.EnemyBullet;
import model.EnemyCraft;
import model.GameState;

/**
 * Class that represents the space playing field.
 */
public class SpaceField extends JPanel {

    private GameState state;
    public static final Dimension FIELD_DIM = new Dimension(SCView.WINDOW_WIDTH,
            SCView.WINDOW_HEIGHT - (int) Hud.HUD_DIM.getHeight()-23);
    private static final double ENEMY_ROTATION_DEGREES = 15;

    public SpaceField(){
        setVisible(true);
        setPreferredSize(FIELD_DIM);
        setOpaque(false);
    }

    public void updateState(GameState state){
        this.state = state;
        repaint();
    }

    @Override
    public void paintComponent(Graphics g){
        drawPlayer(g);
        drawPlayerBullets(g);
        drawEnemyBullets(g);
        drawEnemies(g);
        drawAsteroids(g);
        g.drawRect(0,0, this.getPreferredSize().width-1, this.getPreferredSize().height);
    }

    private void drawAsteroids(Graphics g) {
        g.setColor(Color.ORANGE);
        for(Asteroid asteroid : this.state.getAsteroids()){
            g.drawOval((int) asteroid.getPosition().getX(), (int) asteroid.getPosition().getY(),
                    Asteroid.ASTEROID_RADIUS,Asteroid.ASTEROID_RADIUS);
        }
    }

    private void drawPlayerBullets(Graphics g) {
        g.setColor(Color.RED);
        for(AProjectile b: state.getPlayer().getBullets()){
            if(b.isVisible()){
                g.drawRect((int) b.getPosition().getX(), (int) b.getPosition().getY(),
                        Bullet.BULLET_WIDTH, Bullet.BULLET_HEIGHT);
            }
        }
    }

    private void drawEnemyBullets(Graphics g) {
        g.setColor(Color.GREEN);
        for(EnemyCraft enemy : state.getEnemies()){
            for(AProjectile b: enemy.getBullets()){
                //draw origin
                //g.drawRect((int)b.getPosition().getX(), (int)b.getPosition().getY(), 1,1);
                if(b.isVisible()){
                    g.drawRect((int) b.getPosition().getX() - EnemyBullet.ENEMY_BULLET_WIDTH/2,
                            (int) b.getPosition().getY() - EnemyBullet.ENEMY_BULLET_HEIGHT/2,
                            EnemyBullet.ENEMY_BULLET_WIDTH, EnemyBullet.ENEMY_BULLET_HEIGHT);
                }
            }
        }

    }


    private void drawPlayer(Graphics g) {
        if(this.state.getPlayer().isVisible()){
            Graphics2D g2d = (Graphics2D)g;
            g2d.setColor(Color.CYAN);
            //draw origin
            //g.drawOval((int)state.getPlayer().getPosition().getX(), (int)state.getPlayer().getPosition().getY(), 1,1);
            // Rotates the Graphics, draws the player, and rotates the graphics back. This result
            // is a rotating player in accordance with velocity, but the hitbox is still a static
            // square.
            AffineTransform oldTransform = g2d.getTransform();
            g2d.rotate(Math.toRadians(state.getPlayer().getVelocity().getY() + (state
                    .getPlayer().getVelocity().getX())
            ) * 1.5, state
                    .getPlayer()
                    .getPosition().getX(), state.getPlayer().getPosition().getY());
            g2d.drawRect((int)state.getPlayer().getPosition().getX() - PlayerCraft
                            .PLAYER_HITBOX_RADIUS,
                    (int)state.getPlayer().getPosition().getY() - PlayerCraft.PLAYER_HITBOX_RADIUS,
                    PlayerCraft.PLAYER_HITBOX_RADIUS*2, PlayerCraft.PLAYER_HITBOX_RADIUS*2);
            g2d.setTransform(oldTransform);

        }
    }

    private void drawEnemies(Graphics g) {
        for(EnemyCraft enemy : this.state.getEnemies()){
            if(enemy.isVisible()){
                Graphics2D g2d = (Graphics2D)g;

                //draw origin
                //g.drawOval((int)enemy.getPosition().getX(), (int)enemy.getPosition().getY(), 1,1);
                // Rotates the Graphics, draws the enemy, and rotates the graphics back. This result
                // is a rotating player in accordance with velocity, but the hitbox is still a static
                // square.
                AffineTransform oldTransform = g2d.getTransform();
                if(enemy.getVelocity().getY() > 0){
                    g2d.rotate(Math.toRadians(ENEMY_ROTATION_DEGREES), enemy
                            .getPosition().getX(), enemy.getPosition().getY());
                }
                if(enemy.getVelocity().getY() < 0){
                    g2d.rotate(Math.toRadians(-ENEMY_ROTATION_DEGREES), enemy
                            .getPosition().getX(), enemy.getPosition().getY());
                }
                g.setColor(Color.RED);
                g.drawRect((int) enemy.getPosition().getX() - EnemyCraft.ENEMY_HITBOX_RADIUS,
                        (int) enemy.getPosition().getY() - EnemyCraft.ENEMY_HITBOX_RADIUS,
                        EnemyCraft.ENEMY_HITBOX_RADIUS*2, EnemyCraft.ENEMY_HITBOX_RADIUS*2);
                g2d.setTransform(oldTransform);
            }
        }

    }
}
