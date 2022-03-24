package Graphique;

/**
 * @author idir
 *
 */

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import code_source.Grille_de_jeu;
											/* Menu de demarage*/ 
public class Menu extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
	//notre fonction main qui determine l'entre du programme
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(new NimbusLookAndFeel());
					Menu frame = new Menu();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	//construteur de la JFrame qui initialiser les boutons et les labels  
	public Menu() throws IOException {
		setResizable(false);
		this.setTitle("Welcome to 2048 game");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 800);
		setLocationRelativeTo(null);
		this.setFocusable(true);
		this.addKeyListener(new KeyMenuListner(this));
		contentPane = new JPanel();

		this.add(contentPane);
		contentPane.setLayout(null);

		Icon icon = new ImageIcon("ressources/_plus.png");
		plus = new JButton(icon);
		contentPane.add(plus);
		plus.setBounds(400, 250, 100, 100);

		Icon icon2 = new ImageIcon("ressources/moins.png");
		moins = new JButton(icon2);
		moins.setBounds(100, 250, 100, 100);
		contentPane.add(moins);

		taille = new JLabel();
		taille.setBounds(200, 250, 200, 100);
		taille.setText("4");
		taille.setHorizontalAlignment(JLabel.CENTER);
		taille.setVerticalAlignment(JLabel.CENTER);
		taille.setFont(new java.awt.Font(Font.SERIF, Font.BOLD, 50));
		taille.setBackground(Color.RED);
		contentPane.add(taille);

		play = new JButton(" PLAY ");
		play.setBounds(100, 400, 400, 75);
		play.setFont(new java.awt.Font(Font.SERIF, Font.BOLD, 35));
		play.setBackground(new Color(30, 127, 203));
		contentPane.add(play);

		option = new JButton("OPTIONS");
		option.setBounds(100, 600, 400, 75);
		option.setFont(new java.awt.Font(Font.SERIF, Font.BOLD, 35));
		option.setBackground(new Color(30, 127, 203));
		contentPane.add(option);
		option.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				try {
					Options frame = new Options(Menu.this);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});

		tailleLabel.setBounds(100, 200, 400, 50);
		tailleLabel.setFont(new java.awt.Font(Font.SERIF, Font.BOLD, 35));
		tailleLabel.setHorizontalAlignment(JLabel.CENTER);
		tailleLabel.setVerticalAlignment(JLabel.CENTER);
		contentPane.add(tailleLabel);

		bestScore = new JLabel("Best score : " + Integer.toString(Grille_de_jeu.getBestScore()));
		bestScore.setBounds(50, 150, 500, 50);
		bestScore.setFont(new java.awt.Font(Font.SERIF, Font.BOLD, 25));
		bestScore.setHorizontalAlignment(JLabel.CENTER);
		bestScore.setVerticalAlignment(JLabel.CENTER);
		bestScore.setOpaque(true);
		bestScore.setBackground(new Color(248, 91, 26));
		contentPane.add(bestScore);

		play.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				InterfacesDeJeu window;
				try {
					boolean continu = false;
					window = new InterfacesDeJeu(Integer.valueOf(taille.getText()), music, sound, continu);
					window.setVisible(true);
				} catch (NumberFormatException | UnsupportedLookAndFeelException e) {
					e.printStackTrace();
				}

			}
		});

		plus.addActionListener(new ActionListener() {

			@SuppressWarnings("deprecation")
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (sound) {
					if (close) {
						clip.stop();
					} else {
						close = true;
					}
					playsound();
				}

				int var = Integer.valueOf(taille.getText());
				if (var < 10) {
					var++;
					taille.setText(Integer.toString(var));
				}

			}
		});

		moins.addActionListener(new ActionListener() {

			@SuppressWarnings("deprecation")
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (sound) {
					if (close) {
						clip.stop();
					} else {
						close = true;
					}
					playsound();
				}

				int var = Integer.valueOf(taille.getText());
				if (var > 4) {
					var--;
					taille.setText(Integer.toString(var));
				}
			}
		});

		continu = new JButton("CONTINUE");
		continu.setBounds(100, 500, 400, 75);
		continu.setFont(new java.awt.Font(Font.SERIF, Font.BOLD, 35));
		continu.setBackground(new Color(30, 127, 203));
		contentPane.add(continu);
		continu.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				InterfacesDeJeu window;
				try {
					boolean continu = true;
					window = new InterfacesDeJeu(Integer.valueOf(taille.getText()), music, sound, continu);
					window.setVisible(true);
				} catch (NumberFormatException | UnsupportedLookAndFeelException e) {
					e.printStackTrace();
				}

			}
		});

		logo.setBounds(50, 50, 500, 100);
		contentPane.add(logo);

		fon2.setBounds(50, 50, 500, 670);
		contentPane.add(fon2);

		fond.setBounds(0, 0, 600, 800);
		contentPane.add(fond);
	}

	JLabel fon2 = new JLabel(new ImageIcon("ressources/gris.png"));
	JLabel fond = new JLabel(new ImageIcon("ressources/blue.png"));
	JLabel logo = new JLabel(new ImageIcon("ressources/logo.png"));
	JLabel tailleLabel = new JLabel("Grid size");
	JButton plus;
	JButton moins;
	JButton play;
	JButton continu;
	JButton option;
	JLabel taille;
	JLabel bestScore;
	boolean music = false;
	boolean sound = true;
	boolean close = false;
	@SuppressWarnings("deprecation")
	java.applet.AudioClip clip;

	// méthode qui incrémmante le grid size
	public void add() {
		int var = Integer.valueOf(this.taille.getText());
		if (var < 10) {
			var++;
			this.taille.setText(Integer.toString(var));
		}
	}

	// méthode qui décremente  le grid size
	public void sub() {
		int var = Integer.valueOf(this.taille.getText());
		if (var > 4) {
			var--;
			this.taille.setText(Integer.toString(var));
		}
	}

	// méthode qui instancie une interface de jeu pour commancer a joué
	public void enter() {
		dispose();
		InterfacesDeJeu window;
		try {
			boolean continu = false;
			window = new InterfacesDeJeu(Integer.valueOf(taille.getText()), music, sound, continu);
			window.setVisible(true);
		} catch (NumberFormatException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
	}

	// méthode qui joue un son de click
	@SuppressWarnings("deprecation")
	void playsound() {
		URL soundbyte;
		try {
			soundbyte = new File("ressources/Click.wav").toURI().toURL();
			clip = java.applet.Applet.newAudioClip(soundbyte);

			clip.play();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

	}
}
