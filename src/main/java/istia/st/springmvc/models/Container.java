package istia.st.springmvc.models;

public class Container {
	// le compteur
	private int compteur = 10;

	// les getters et setters
	public int getCompteur() {
		return compteur;
	}

	public void setCompteur(int compteur) {
		this.compteur = compteur;
	}

	@Override
	public String toString() {
		return String.valueOf(compteur);
	}
	
	
}
