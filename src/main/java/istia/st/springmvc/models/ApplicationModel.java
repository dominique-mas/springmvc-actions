package istia.st.springmvc.models;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Component;

/**
 * L'annotation [@Component] fait que la classe [ApplicationModel] sera un
 * composant géré par Spring. La nature par défaut des composants Spring est le
 * type [singleton] : le composant est créé en un unique exemplaire lorsque le
 * conteneur Spring est instancié ç-à-d en général au démarrage de
 * l'application.
 * 
 * @author Dominique Mas
 *
 */
@Component
public class ApplicationModel {

	/**
	 * compteur
	 * 
	 * Le type [AtomicLong] a une méthode [incrementAndGet] dite atomique. Cela signifie qu'un
	 * thread qui exécute cette méthode est assuré qu'un autre thread ne lira pas la
	 * valeur du compteur (Get) entre sa lecture (Get) et son incrément (increment)
	 * par le 1er thread, ce qui provoquerait des erreurs puisque deux threads
	 * liraient la même valeur du compteur, et celui-ci au lieu d'être incrémenté de
	 * deux le serait de un.
	 */
	private AtomicLong compteur = new AtomicLong(0);

	// getters et setters
	public AtomicLong getCompteur() {
		return compteur;
	}

	public void setCompteur(AtomicLong compteur) {
		this.compteur = compteur;
	}
}
