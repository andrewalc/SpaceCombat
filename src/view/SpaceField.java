package view;

import model.Asteroid;
import model.Bullet;
import model.GameState;

import javax.swing.*;
import java.awt.*;

/**
 * Class that represents the space playing field.
 */
public class SpaceField extends JPanel {

    public GameState state;
    public static final int CRAFT_HITBOX_RADIUS = 10;
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
        g.setColor(Color.CYAN);
        drawPlayer(g);
        drawPlayerBullets(g);
        g.setColor(Color.ORANGE);
        for(Asteroid asteroid : this.state.getAsteroids()){
            g.drawOval((int) asteroid.getPosition().getX(), (int) asteroid.getPosition().getY(),
                    Asteroid.ASTEROID_RADIUS,Asteroid.ASTEROID_RADIUS);
        }
        g.drawRect(0,0, this.getPreferredSize().width-1, this.getPreferredSize().height);
    }

    private void drawPlayerBullets(Graphics g) {
        g.setColor(Color.RED);
        for(Bullet b: state.getPlayer().getBullets()){
            g.drawRect((int) b.getPosition().getX(), (int) b.getPosition().getY(),
                    Bullet.BULLET_WIDTH, Bullet.BULLET_HEIGHT);
        }
    }


    private void drawPlayer(Graphics g) {
        if(this.state.getPlayer().isVisible()){
            g.drawRect(state.getPlayer().getPosition().x - CRAFT_HITBOX_RADIUS,
                    state.getPlayer().getPosition().y - CRAFT_HITBOX_RADIUS,
                    CRAFT_HITBOX_RADIUS*2, CRAFT_HITBOX_RADIUS*2);
        }
    }
}
