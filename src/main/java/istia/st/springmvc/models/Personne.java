package istia.st.springmvc.models;

public class Personne {

	// identifiant
	private Integer id;
	// nom
	private String nom;
	// âge
	private int age;
	
	public Personne() {}
	
	public Personne(int id, String nom, int age) {
		this.id = id;
		this.nom = nom;
		this.age = age;
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the nom
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * @param nom
	 *            the nom to set
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}

	/**
	 * @return the age
	 */
	public int getAge() {
		return age;
	}

	/**
	 * @param age
	 *            the age to set
	 */
	public void setAge(int age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "Je suis la personne avec l'id " + getId() + ". Je m'apelle " + getNom() + " et j'ai " + getAge()
				+ " an(s).";
	}
}
