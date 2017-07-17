package view;

import model.Craft;
import model.GameState;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyListener;

import javax.swing.*;

public class SCView extends JFrame {
	public static final int WINDOW_WIDTH = 1000;
	public static final int WINDOW_HEIGHT = 600;
	public static final Dimension WINDOW_DIM = new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT);
	public static final Color COLOR_BACKGROUND = Color.BLACK;
	private Hud hudPanel;
	private SpaceField fieldPanel;

	public SCView() {
		super("Space Combat");
		// JFrame Settings
		setSize(WINDOW_DIM);
		setPreferredSize(WINDOW_DIM);
		setResizable(false);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setFocusable(true);
		requestFocus();

		// Put all things in here
		JPanel contentPane = new JPanel(new BorderLayout());
		contentPane.setBackground(COLOR_BACKGROUND);

		// Add Hud
		hudPanel = new Hud();
		contentPane.add(hudPanel, BorderLayout.NORTH);


		fieldPanel = new SpaceField();
		contentPane.add(fieldPanel, BorderLayout.CENTER);
		// Set and pack
		setContentPane(contentPane);
		pack();
	}

	public void initialize() {
		setVisible(true);
	}

	public void updateView(GameState state, int tickCount) {
		fieldPanel.updateState(state);
		hudPanel.updateHud(state.getPlayer(), tickCount);
	}
}
