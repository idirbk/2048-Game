package code_source;

/**
 * @author idir
 *
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;

public class Grille_de_jeu {
	private int taille;
	private Cellule[][] tab_2d;
	private boolean gagne = false;
	private int score = 0;
	private int max = 0;
	private Long secondes;

	// constructeur avec un tableau existant
	public Grille_de_jeu(int taille, Cellule[][] tab_2d) {

		this.taille = taille;
		this.tab_2d = tab_2d;
	}

	// constructeur avec la largeur de la grille qui alloue dinamiquement le tableau
	// de cellule et initialise le contenu à 0
	public Grille_de_jeu(int taille) {

		this.secondes = (long) 0;
		this.taille = taille;
		this.tab_2d = new Cellule[taille][taille];
		for (int i = 0; i < taille; ++i)
			for (int j = 0; j < taille; ++j) {
				this.tab_2d[i][j] = new Cellule();
				this.tab_2d[i][j].setContenu(0);
				this.tab_2d[i][j].setPosition_vertical(taille - j - 1);
				this.tab_2d[i][j].setPosition_horisontal(i);
			}
	}

	// méthode qui retourne un vecteur contenant les indices des cellue contenat 0
	private Vector<int[]> celulles_Vides() {
		Vector<int[]> v = new Vector<int[]>();

		for (int i = 0; i < this.taille; ++i)

			for (int j = 0; j < this.taille; ++j) {
				if (this.tab_2d[i][j].getContenu() == 0) {
					int[] var = new int[2];
					var[0] = this.tab_2d[i][j].getPosition_horisontal();
					var[1] = this.tab_2d[i][j].getPosition_vertical();

					v.add(var);
				}
			}

		return v;
	}

	// méthode qui ajoute un cellule à la grille avec 20% de chance d'ajouter un 4
	// et 80% de chance d'ajouter un deux
	public void ajouter_Celulles() {
		Vector<int[]> v = this.celulles_Vides();
		int taille = v.size();

		if (taille == 0)
			return;

		int index = (int) (Math.random() * taille);
		int proba = (int) (Math.random() * 4);
		int contenu;

		if (proba == 0) {
			contenu = 4;
		} else {
			contenu = 2;
		}

		this.tab_2d[v.get(index)[0]][this.taille - v.get(index)[1] - 1].setContenu(contenu);

	}

	//méthode qui fais une rotation du tableau de cellules de 90° vers la gauche
	public void rotationGauche() {
		Cellule tmp[][];
		tmp = new Cellule[this.taille][this.taille];
		for (int i = 0; i < this.taille; i++)
			for (int j = 0; j < this.taille; ++j)
				tmp[i][j] = this.tab_2d[j][taille - i - 1];

		this.tab_2d = tmp;

	}
	
	//méthode qui fais une rotation du tableau de cellude de 90° vers la droite
	public void rotationDroite() {
		Cellule tmp[][];
		tmp = new Cellule[this.taille][this.taille];
		for (int i = 0; i < this.taille; i++)
			for (int j = 0; j < this.taille; ++j)
				tmp[i][j] = this.tab_2d[this.taille - j - 1][i];

		this.tab_2d = tmp;

	}

	// méthode qui gére le deplacement des cellue vers la gauche
	public boolean clic_gauche() {
		boolean retour = false;
		for (int j = 0; j < this.taille; ++j) {
			boolean modif = true;
			int debut = 1;
			while (modif) {
				modif = false;

				for (int i = debut; i < taille; ++i) {
					int v1 = this.tab_2d[i][j].getContenu();
					int v2 = this.tab_2d[i - 1][j].getContenu();
					if (v1 != 0) {
						if (v2 == 0) {
							retour = true;
							this.tab_2d[i - 1][j].setContenu(v1);
							this.tab_2d[i][j].setContenu(0);
							modif = true;
						} else {
							if (v1 == v2) {
								retour = true;
								this.score += v1 * 2;
								this.tab_2d[i - 1][j].setContenu(v1 * 2);
								this.tab_2d[i][j].setContenu(0);
								modif = true;
								debut = i + 2;
								if (v1 * 2 > this.max)
									this.max = v1 * 2;
							}
						}
					}
				}
			}
		}

		return retour;
	}
	
	// méthode qui gére le deplacement des cellue vers la droite
	public boolean clic_droit() {
		boolean retour;
		this.rotationDroite();
		this.rotationDroite();
		retour = this.clic_gauche();
		this.rotationGauche();
		this.rotationGauche();
		return retour;
	}
	
	// méthode qui gére le deplacement des cellue vers le haut
	public boolean clic_haut() {
		boolean retour;
		this.rotationGauche();
		retour = this.clic_gauche();
		this.rotationDroite();

		return retour;
	}
	
	// méthode qui gére le deplacement des cellue vers le bas
	public boolean clic_bas() {
		boolean retour;
		this.rotationDroite();
		retour = this.clic_gauche();
		this.rotationGauche();
		return retour;

	}
	
	// méthode qui retourne vrai si le joueur a atteint 2048 pour la première fois
	public boolean gagnePremiereFois() {
		if (this.gagne) {
			return false;
		}

		for (int i = 0; i < this.taille; ++i)
			for (int j = 0; j < this.taille; ++j)
				if (this.tab_2d[i][j].getContenu() == 2048) {
					this.gagne = true;
					return true;
				}

		return false;
	}

	// méthode qui retourne vrai si aucun mouvment n'ai possible et  faux sinon
	public boolean perdu() {

		Vector<int[]> v = this.celulles_Vides();

		if (v.size() != 0)
			return false;
		for (int i = 0; i < this.taille - 1; ++i)
			for (int j = 0; j < this.taille - 1; ++j) {
				if (this.tab_2d[i][j].getContenu() == this.tab_2d[i + 1][j].getContenu()
						|| this.tab_2d[i][j].getContenu() == this.tab_2d[i][j + 1].getContenu())
					return false;
			}

		for (int i = 0; i < this.taille - 1; ++i)
			if (tab_2d[i][this.taille - 1].getContenu() == tab_2d[i + 1][this.taille - 1].getContenu()
					|| tab_2d[this.taille - 1][i].getContenu() == tab_2d[this.taille - 1][i + 1].getContenu())
				return false;

		
		return true;
	}

	// méthode qui sanvgarde le meilleur score dans un fichier score.txt dans le repertoire ressources
	public void sauvgarderScore() {
		try {

			FileReader fichier = new FileReader("ressources/score.txt");
			BufferedReader br = new BufferedReader(fichier);
			String max = br.readLine();
			fichier.close();
			if (Integer.valueOf(max) < this.score) {
				FileWriter fichierw = new FileWriter("ressources/score.txt");
				BufferedWriter buf = new BufferedWriter(fichierw);
				buf.write(Integer.toString(this.score));
				buf.close();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// méthode qui retourne le meilleur score du jeu qui est dans le fichier score.txt d'où le static
	public static int getBestScore() {
		FileReader fichier;
		try {
			fichier = new FileReader("ressources/score.txt");
			BufferedReader br = new BufferedReader(fichier);
			String max = br.readLine();
			fichier.close();

			return Integer.valueOf(max);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
			return -1;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}

	}

	// méthode qui sauvgarde le jeu courant avec le score le timer la taille de la grille et le contenu de la grille dans le fichier sauvgarde.txt
	public void saveGame(Long secondes) {
		FileWriter fichier;
		try {
			fichier = new FileWriter("ressources/sauvgarde.txt");
			BufferedWriter buf = new BufferedWriter(fichier);
			buf.write(Integer.toString(this.taille));
			buf.newLine();
			buf.write(Integer.toString(this.score));
			buf.newLine();
			buf.write(Long.toString(secondes));
			buf.newLine();

			for (int i = 0; i < this.taille; ++i)
				for (int j = 0; j < this.taille; ++j) {
					buf.write(Integer.toString(this.tab_2d[i][j].getContenu()));
					buf.newLine();
				}
			buf.close();
			fichier.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//méthode qui charge la partille stocker dans le fichier sauvgarde.txt
	public static Grille_de_jeu chargegame() {
		Grille_de_jeu grille = new Grille_de_jeu(4);
		FileReader fichier;

		try {
			fichier = new FileReader("ressources/sauvgarde.txt");
			BufferedReader br = new BufferedReader(fichier);
			String taille = br.readLine();
			int taillint = Integer.valueOf(taille.trim());
			;
			grille = new Grille_de_jeu(taillint);
			String scor = br.readLine();
			grille.score = Integer.valueOf(scor.trim());
			String seconde = br.readLine();
			grille.secondes = Long.valueOf(seconde.trim());

			for (int i = 0; i < taillint; ++i)
				for (int j = 0; j < taillint; ++j) {
					String contenu = br.readLine();
					grille.tab_2d[i][j].setContenu(Integer.valueOf(contenu.trim()));
					if (grille.tab_2d[i][j].getContenu() >= 2048)
						grille.gagne = true;
				}
			String s = br.readLine();
			while (s != null) {
				System.out.println(s);
				s = br.readLine();
			}
			br.close();
			fichier.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return grille;

	}

	/* accesseur et modificateur */
	public int getTaille() {
		return taille;
	}

	public void setTaille(int taille) {
		this.taille = taille;
	}

	public Cellule[][] getTab_2d() {
		return tab_2d;
	}

	public void setTab_2d(Cellule[][] tab_2d) {
		this.tab_2d = tab_2d;
	}

	public boolean isGagne() {
		return gagne;
	}

	public void setGagne(boolean gagne) {
		this.gagne = gagne;
	}

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}

	public int getcontenu(int i, int j) {
		return tab_2d[i][j].getContenu();
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public Long getSecondes() {
		return secondes;
	}

	public void setSecondes(Long secondes) {
		this.secondes = secondes;
	}

}
