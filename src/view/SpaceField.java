package view;

import model.*;

import javax.swing.*;
import java.awt.*;

/**
 * Class that represents the space playing field.
 */
public class SpaceField extends JPanel {

    public GameState state;
    public static final Dimension FIELD_DIM = new Dimension(SCView.WINDOW_WIDTH,
            SCView.WINDOW_HEIGHT - (int) Hud.HUD_DIM.getHeight()-23);

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
        for(ABullet b: state.getPlayer().getBullets()){
            if(b.isVisible()){
                g.drawRect((int) b.getPosition().getX(), (int) b.getPosition().getY(),
                        Bullet.BULLET_WIDTH, Bullet.BULLET_HEIGHT);
            }
        }
    }

    private void drawEnemyBullets(Graphics g) {
        g.setColor(Color.GREEN);
        for(EnemyCraft enemy : state.getEnemies()){
            for(ABullet b: enemy.getBullets()){
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
            g.setColor(Color.CYAN);
            //draw origin
            //g.drawOval((int)state.getPlayer().getPosition().getX(), (int)state.getPlayer().getPosition().getY(), 1,1);
            g.drawRect((int)state.getPlayer().getPosition().getX() - Craft.PLAYER_HITBOX_RADIUS,
                    (int)state.getPlayer().getPosition().getY() - Craft.PLAYER_HITBOX_RADIUS,
                    Craft.PLAYER_HITBOX_RADIUS*2, Craft.PLAYER_HITBOX_RADIUS*2);
        }
    }

    private void drawEnemies(Graphics g) {
        for(EnemyCraft enemy : this.state.getEnemies()){
            if(enemy.isVisible()){
                g.setColor(Color.RED);
                //draw origin
                //g.drawOval((int)enemy.getPosition().getX(), (int)enemy.getPosition().getY(), 1,1);
                g.drawRect((int) enemy.getPosition().getX() - EnemyCraft.ENEMY_HITBOX_RADIUS,
                        (int) enemy.getPosition().getY() - EnemyCraft.ENEMY_HITBOX_RADIUS,
                        EnemyCraft.ENEMY_HITBOX_RADIUS*2, EnemyCraft.ENEMY_HITBOX_RADIUS*2);
            }
        }

    }
}
