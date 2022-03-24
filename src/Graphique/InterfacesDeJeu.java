package Graphique;

/*
* @author idir
*
*/

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import code_source.Grille_de_jeu;

public class InterfacesDeJeu extends JFrame {
												/**
	 * 
	 */
	private static final long serialVersionUID = -3423355781117203517L;

	/* Fenêtre de jeu */
	
	// constructeur qui initialise les labels representant les cellule et les autre composant ansi que la grille de jeu*/
	public InterfacesDeJeu(int taille, boolean music, boolean sound, boolean continu)
			throws UnsupportedLookAndFeelException {
		UIManager.setLookAndFeel(new NimbusLookAndFeel());
		this.vect = new Vector<JLabel>();
		this.btn = new JButton();
		this.canvas = new JPanel();
		if (!continu) {
			this.jeu = new Grille_de_jeu(taille);
			jeu.ajouter_Celulles();
		} else {
			this.jeu = Grille_de_jeu.chargegame();
			this.secondes = jeu.getSecondes();
		}
		this.gl = new GridLayout(jeu.getTaille(), jeu.getTaille(), 10, 10);
		this.panel = new JPanel();
		this.f = new BorderLayout();
		this.score = new JLabel();
		this.time = new JLabel();
		this.valide = true;
		this.gagne = false;

		this.initialize(music, sound);

	}

	// mèthde appeler dans le constructeur de la fenêtre
	private void initialize(boolean music, boolean sound) {

		this.music = music;
		this.sound = sound;
		this.setFocusable(true);
		this.addKeyListener(new keyDemoKeyListener(this));
		this.setSize(new Dimension(jeu.getTaille() * 120, jeu.getTaille() * 110));
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		// this.setLayout(new FlowLayout());

		panel.setLayout(new GridLayout(1, 3));
		panel.setPreferredSize(new Dimension(500, 80));

		this.add(panel, BorderLayout.NORTH);

		panel.add(score);

		score.setBackground(Color.lightGray);
		score.setOpaque(true);
		score.setSize(60, 40);
		score.setHorizontalAlignment(JLabel.CENTER);
		score.setVerticalAlignment(JLabel.CENTER);
		score.setBorder(BorderFactory.createEtchedBorder(Color.black, Color.black));
		score.setFont(new java.awt.Font(Font.MONOSPACED, Font.BOLD, 20));
		score.setText("Score : " + Integer.toString(jeu.getScore()));
		panel.add(btn);
		btn.setText("Leave the game");
		btn.setBackground(Color.lightGray);
		btn.setOpaque(true);
		btn.setFont(new java.awt.Font(Font.MONOSPACED, Font.BOLD, 15));
		btn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				jeu.sauvgarderScore();
				jeu.saveGame(secondes);
				dispose();
				Menu frame;
				try {
					frame = new Menu();
					frame.setVisible(true);
					frame.music = music;
					frame.sound = sound;
					if (music)
						audioClip.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		});

		panel.add(time);
		time.setBackground(Color.lightGray);
		time.setOpaque(true);
		time.setSize(60, 40);
		time.setHorizontalAlignment(JLabel.CENTER);
		time.setVerticalAlignment(JLabel.CENTER);
		time.setBorder(BorderFactory.createEtchedBorder(Color.black, Color.black));
		time.setFont(new java.awt.Font(Font.MONOSPACED, Font.BOLD, 20));

		canvas.setLayout(gl);
		canvas.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		canvas.setBackground(Color.darkGray);
		this.add(canvas, BorderLayout.CENTER);

		for (int i = 0; i < Math.pow(jeu.getTaille(), 2); ++i) {
			JLabel label = new JLabel();
			if (jeu.getcontenu(i % jeu.getTaille(), i / jeu.getTaille()) != 0) {
				label.setHorizontalAlignment(JLabel.CENTER);
				label.setVerticalAlignment(JLabel.CENTER);
				label.setText(Integer.toString(jeu.getcontenu(i % jeu.getTaille(), i / jeu.getTaille())));
			}

			label.setOpaque(true);
			label.setBackground(getColor(jeu.getcontenu(i % jeu.getTaille(), i / jeu.getTaille())));

			label.setFont(new java.awt.Font(Font.SERIF, Font.BOLD, 30));
			vect.addElement(label);
			canvas.add(label);
		}

		/* Le coeur de la détermination du temps écoulé */
		timer = new Timer();
		timer.schedule(new TimerTask() {

			public void run() {
				if (valide) {
					secondes += 1;
					time.setText(timeToText(secondes));
				}
			}

		}, 0, 1000);

		if (music)
			play("ressources/fond.wav");

		this.secondes = jeu.getSecondes();
	}

	Vector<JLabel> vect;
	JButton btn;
	JPanel canvas;
	Grille_de_jeu jeu;
	GridLayout gl;
	JPanel panel;
	BorderLayout f;
	JLabel score;
	JLabel time;
	Timer timer;
	long secondes;
	boolean gagne;
	boolean valide = true;
	boolean playCompleted;
	Clip audioClip;
	Clip audioClip2;
	boolean sound;
	boolean music;

	// méthode qui retourne une couleur selon le contenu de la cellule
	Color getColor(int val) {
		Color c = new Color(0);
		switch (val) {
		case 0:
			c = new Color(255, 195, 0);
			break;
		case 2:
			c = new Color(255, 87, 51);
			break;
		case 4:
			c = new Color(199, 0, 57);
			break;
		case 8:
			c = new Color(214, 137, 16);
			break;
		case 16:
			c = new Color(218, 247, 166);
			break;
		case 32:
			c = new Color(31, 97, 141);
			break;
		case 64:
			c = new Color(25, 111, 61);
			break;
		case 128:
			c = new Color(236, 112, 99);
			break;
		case 256:
			c = new Color(88, 214, 141);
			break;
		case 512:
			c = new Color(93, 109, 126);
			break;
		case 1024:
			c = new Color(165, 105, 189);
			break;
		case 2048:
			c = new Color(171, 178, 185);
			break;
		default:
			c = Color.black;
		}

		return c;

	}

	// méthode qui met à jour les labels à chaque mouvement
	public void movelabel() {

		score.setText("Score : " + Integer.toString(jeu.getScore()));

		for (int i = 0; i < jeu.getTaille() * jeu.getTaille(); ++i) {
			vect.elementAt(i).setHorizontalAlignment(JLabel.CENTER);
			vect.elementAt(i).setVerticalAlignment(JLabel.CENTER);
			if (jeu.getcontenu(i % jeu.getTaille(), i / jeu.getTaille()) != 0) {
				vect.elementAt(i).setText(Integer.toString(jeu.getcontenu(i % jeu.getTaille(), i / jeu.getTaille())));
			} else {
				vect.elementAt(i).setText("");
			}
			vect.elementAt(i).setBackground(getColor(jeu.getcontenu(i % jeu.getTaille(), i / jeu.getTaille())));
		}

		if (jeu.perdu())
			finPartie();

		if (jeu.getMax() == 2048 && !this.gagne) {
			ImageIcon icon = new ImageIcon("ressources/gagne.png");
			JOptionPane.showMessageDialog(null, "Congratulation you win 2048 , continue", "You are the best !!",
					JOptionPane.OK_OPTION, icon);
			this.gagne = true;

		}

	}

	/* méthodes qui gére l'evenement clavier et qui met a jour la grille*/
	@SuppressWarnings("deprecation")
	public void moveDroite() {
		if (this.jeu.clic_droit()) {
			if (sound) {
				if (val) {
					clip.stop();
				} else {
					val = !val;
				}
				playsound();
			}

			this.jeu.ajouter_Celulles();
			this.movelabel();

		}

	}

	@SuppressWarnings("deprecation")
	public void moveHaut() {
		if (this.jeu.clic_bas()) {

			if (sound) {
				if (val) {
					clip.stop();
				} else {
					val = !val;
				}
				playsound();
			}

			this.jeu.ajouter_Celulles();
			this.movelabel();

		}

	}

	@SuppressWarnings("deprecation")
	public void moveBas() {

		if (this.jeu.clic_haut()) {
			if (sound) {
				if (val) {
					clip.stop();
				} else {
					val = !val;
				}
				playsound();
			}

			this.jeu.ajouter_Celulles();
			this.movelabel();

		}

	}

	@SuppressWarnings("deprecation")
	public void movegauche() {
		if (this.jeu.clic_gauche()) {
			if (sound) {
				if (val) {
					clip.stop();
				} else {
					val = !val;
				}
				playsound();
			}

			this.jeu.ajouter_Celulles();
			this.movelabel();

		}
	}

	boolean val = false;

	// méthode qui gére les fin de partie
	public void finPartie() {
		valide = false;
		jeu.sauvgarderScore();
		int i = JOptionPane.showConfirmDialog(null, "HAHA!! YOU LOSE :)" + "\n" + "	try again", "LOSER",
				JOptionPane.YES_NO_OPTION);

		if (i == 1) {
			this.dispose();
			if (music)
				audioClip.stop();
			Menu frame;
			try {
				frame = new Menu();
				frame.setVisible(true);

				frame.sound = this.sound;
				frame.music = this.music;

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			jeu = new Grille_de_jeu(4);
			jeu.ajouter_Celulles();
			movelabel();
			secondes = 0;
			valide = true;

		}

	}

	
	private String timeToText(long secondes) {
		
		/*
		 * Détermination des 3 composantes int h, mn, s
		 * 
		 * 
		 * Remarque, on aura toujours les nombres qui tiennent
		 * 
		 * sur deux chiffres, grâce aux modulos
		 */
		long heure, min, sec;
		sec = secondes % 60;
		min = (secondes / 60) % 60;
		heure = (secondes / (60 * 60)) % 24;

		/* Détermination des 3 composantes String h, mn, s */
		String sHeure, sMin, sSec;
		sHeure = timeFormat(heure);
		sMin = timeFormat(min);
		sSec = timeFormat(sec);

		/* Retourne la chaîne à afficher */
		return sHeure + ":" + sMin + ":" + sSec;

	}

	@SuppressWarnings("deprecation")
	private String timeFormat(long timeComposant) {
		if (timeComposant < 10)
			return "0" + new Long(timeComposant).toString();
		else {
			return new Long(timeComposant).toString();
		}
	}

	// méthode qui joue de la musique
	void play(String audioFilePath) {
		File audioFile = new File(audioFilePath);

		try {
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);

			AudioFormat format = audioStream.getFormat();

			DataLine.Info info = new DataLine.Info(Clip.class, format);

			audioClip = (Clip) AudioSystem.getLine(info);

			audioClip.addLineListener(null);

			audioClip.open(audioStream);

			audioClip.start();
			audioClip.loop(1000);
			/*
			 * while (!playCompleted) { // wait for the playback completes try {
			 * Thread.sleep(1000); } catch (InterruptedException ex) { ex.printStackTrace();
			 * } }
			 * 
			 * audioClip.close();
			 */

		} catch (UnsupportedAudioFileException ex) {
			System.out.println("The specified audio file is not supported.");
			ex.printStackTrace();
		} catch (LineUnavailableException ex) {
			System.out.println("Audio line for playing back is unavailable.");
			ex.printStackTrace();
		} catch (IOException ex) {
			System.out.println("Error playing the audio file.");
			ex.printStackTrace();
		}

	}

	@SuppressWarnings("deprecation")
	java.applet.AudioClip clip;

	// méthode qui joue du son
	@SuppressWarnings("deprecation")
	void playsound() {
		URL soundbyte;
		try {
			soundbyte = new File("ressources/sound.wav").toURI().toURL();
			clip = java.applet.Applet.newAudioClip(soundbyte);

			clip.play();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
