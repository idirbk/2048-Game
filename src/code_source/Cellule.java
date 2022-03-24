package code_source;

/**
 * @author idir
 *
 */

/* Cet classe represente une seul case du 2048 contenant un numéro */
public class Cellule {

	private int position_vertical;
	private int position_horisontal;
	private int contenu;
	
	//constructeur avec paramètre
	public Cellule(int position_vertical, int position_horisontal, int contenu) {
		this.position_vertical = position_vertical;
		this.position_horisontal = position_horisontal;
		this.contenu = contenu;
	}

	//concrtucteur sans paramètre 
	public Cellule() {
		super();
		this.position_vertical = -1;
		this.position_horisontal = -1;
		this.contenu = -1;
	}

	/*les accesseur  et les modificateur */
	public int getPosition_vertical() {
		return position_vertical;
	}

	public void setPosition_vertical(int position_vertical) {
		this.position_vertical = position_vertical;
	}

	public int getPosition_horisontal() {
		return position_horisontal;
	}

	public void setPosition_horisontal(int position_horisontal) {
		this.position_horisontal = position_horisontal;
	}

	public int getContenu() {
		return contenu;
	}

	public void setContenu(int contenu) {
		this.contenu = contenu;
	}
}
