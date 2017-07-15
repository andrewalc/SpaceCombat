package view;

import model.Craft;
import model.GameState;

import java.awt.*;

import javax.swing.JPanel;

/**
 * Class that represents the Hud for SpaceCombat. Should display score, hp, ammo, reload status and more.
 */
public class Hud extends JPanel {

	public static final Dimension HUD_DIM = new Dimension(
			(int) SCView.WINDOW_DIM.getWidth(),
			(int) SCView.WINDOW_DIM.getHeight() / 6);

	// Craft values
	private int hp;
	private int ammo;
	private int score;
	// Spacing
	public static final int HUD_BORDER_OFFSET = 25;
	public static final int HUD_NEW_LINE_OFFSET = (int) SCView.WINDOW_HEIGHT / 28;
	private static final int firstLineInHudY = HUD_BORDER_OFFSET  + HUD_NEW_LINE_OFFSET;
	// Font Values
	private int fontSize = 17;
	private Font hudFont = new Font("Ariel", Font.BOLD, fontSize);
	// Strings
	private String scoreStr;
	private String hpStr;
	private String ammoStr;
	private String ammoReloadingStr = "RELOADING";
	private String ammoPressRStr = "PRESS R TO RELOAD!";
	// Colors
	private Color hudOutlineColor;
	private Color scoreColor;
	private Color hpColor;
	private Color ammoColor;

	// Visual Tick Candy
	private int ammoGreenFlickerTick = -1;


	/**
	 * Constructor for the Hud JPanel.
	 */
	public Hud() {
		// Default values
		this.hp = -1;
		this.ammo = -1;
		this.score  = -1;

		this.scoreStr = "Score: " + this.score;
		this.hpStr = "HP: " + this.hp;
		this.ammoStr = "AMMO: " + this.ammo;

		hudOutlineColor = Color.WHITE;
		scoreColor = Color.CYAN;
		hpColor = Color.GREEN;
		ammoColor = Color.WHITE;
		setVisible(true);
		setPreferredSize(HUD_DIM);
		setBackground(Color.WHITE);
		setOpaque(false);

	}


	public void paintComponent(Graphics g) {
		drawHudBox(g);
		drawScore(g);
		drawHp(g);
		drawAmmoStatus(g);
	}

	/**
	 * Draws the craft's ammo status to the Hud. Will change color and text depending on the state of the ammo.
	 * @param g Graphics g.
	 */
	private void drawAmmoStatus(Graphics g) {
		g.setColor(ammoColor);
		g.setFont(hudFont);
		g.drawString(ammoStr, (int) (HUD_DIM.getWidth()/2) - 50, (int) (HUD_DIM.getHeight() / 2) + fontSize/5);
	}

	/**
	 * Draws the craft's hp on the Hud.
	 * @param g Graphics g.
	 */
	private void drawHp(Graphics g) {
		g.setColor(hpColor);
		g.setFont(hudFont);
		g.drawString(hpStr, HUD_BORDER_OFFSET + 30, firstLineInHudY + HUD_NEW_LINE_OFFSET);
	}

	/**
	 * Draws the player score on the Hud.
	 * @param g Graphics g.
	 */
	private void drawScore(Graphics g) {
		g.setColor(scoreColor);
		g.setFont(hudFont);
		g.drawString(scoreStr, HUD_BORDER_OFFSET + 30, firstLineInHudY);
	}

	/**
	 * Draws the box that outlines the Hud.
	 * @param g Graphics g.
	 */
	private void drawHudBox(Graphics g) {
		g.setColor(hudOutlineColor);
		g.drawRect(HUD_BORDER_OFFSET, HUD_BORDER_OFFSET,
				(int) HUD_DIM.getWidth() - (2 * HUD_BORDER_OFFSET),
				(int) HUD_DIM.getHeight() - (2 * HUD_BORDER_OFFSET));
	}

	public void updateHud(Craft craft, int tickCount){
		this.hp = craft.getHp();
		this.ammo = craft.getAmmo();
		this.score = craft.getScore();
		this.scoreStr = "Score: " + this.score;
		if(this.hp < 50){
			hpColor = Color.YELLOW;
		}
		if(this.hp < 25){
			hpColor = Color.RED;
		}
		this.hpStr = "HP: " + this.hp;

		this.ammoColor = Color.WHITE;
		// TODO: this tick stuff isn't really holding the green frame duration (its just one frame i think).
		// Should find out why.
		if(tickCount > ammoGreenFlickerTick - 5 && tickCount <= ammoGreenFlickerTick){
			ammoColor = Color.GREEN;
		}
		if(tickCount == ammoGreenFlickerTick){
			ammoGreenFlickerTick = -1;
		}

		this.ammoStr = "AMMO: " + this.ammo;

		if (craft.isReloadingAmmo()){
			if(ammoGreenFlickerTick == -1){
				ammoGreenFlickerTick = tickCount + GameState.RELOAD_TIME;
			}
			ammoColor = Color.yellow;
			ammoStr = ammoReloadingStr;
		}

		if (!craft.isReloadingAmmo() && !(craft.getAmmo() > 0 )){
			ammoColor = Color.red;
			if (tickCount % 8 == 0) {
				ammoStr = "";
			}
			else {
				ammoStr = ammoPressRStr;
			}
		}




		repaint();
	}
}
