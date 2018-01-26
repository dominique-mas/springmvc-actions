package istia.st.springmvc.model;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

/**
 * L'annotation [@Component] est une annotation Spring qui fait de la classe
 * [SessionModel] un composant dont le cycle de vie est géré par Spring.
 * 
 * L'annotation [@Scope(value = "session", proxyMode =
 * ScopedProxyMode.TARGET_CLASS)] est également une annotation Spring. Lorsque
 * Spring MVC la rencontre, la classe correspondante est créée et mise dans la
 * session de l'utilisateur. L'attribut [proxyMode =
 * ScopedProxyMode.TARGET_CLASS] est important. C'est grâce à lui que Spring MVC
 * crée une instance par utilisateur et non une unique instance pour tous les
 * utilisateurs (singleton).
 * 
 * 
 * 
 * @author Dominique Mas
 *
 */
@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SessionModel {

	private int compteur;
	
	public int getCompteur() {
		return compteur;
	}
	
	public void setCompteur(int compteur) {
		this.compteur = compteur;
	}
}
