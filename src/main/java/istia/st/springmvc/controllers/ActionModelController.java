package istia.st.springmvc.controllers;

import java.io.IOException;
import java.io.Writer;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import istia.st.springmvc.models.ApplicationModel;
import istia.st.springmvc.models.Container;
import istia.st.springmvc.models.Personne;
import istia.st.springmvc.models.SessionModel;

/**
 * [@SessionAttributes] désigne la clé [container] comme faisant partie des
 * attributs de la session.
 * 
 * @author Dominique Mas
 *
 */
@RestController
@SessionAttributes({ "container" })
public class ActionModelController {

	/**
	 * Le composant Spring [SessionModel] est injecté [@Autowired] dans le
	 * contrôleur. On rappelle ici qu'un contrôleur Spring est un singleton. Il est
	 * alors paradoxal d'y injecter un composant de portée moindre, ici de portée
	 * [Session]. C'est là qu'intervient l'annotation [@Scope(value = "session",
	 * proxyMode = ScopedProxyMode.TARGET_CLASS)] du composant [SessionModel]. A
	 * chaque fois que le code du contrôleur accède au champ [session], une méthode
	 * proxy est exécutée pour rendre la session de la requête actuellement traitée
	 * par le contrôleur.
	 */
	@Autowired
	private SessionModel session;
	@Autowired
	private ApplicationModel application;

	@RequestMapping(value = "/m01", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
	public String m01(String nom, String age) {
		return String.format("Hello [%s-%s]!, Greetings from Spring Boot!", nom, age);
	}

	@RequestMapping(value = "/m02", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	public String m02(String nom, String age) {
		return String.format("Hello [%s-%s]!, Greetings from Spring Boot!", nom, age);
	}

	/**
	 * Mapper les paramètres de l'action dans un objet Java
	 * 
	 * @param personne
	 * @return
	 */
	@RequestMapping(value = "/m04", method = RequestMethod.POST)
	public Personne m04(Personne personne) {
		return personne;
	}

	/**
	 * Récupérer les éléments d'une URL
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	@RequestMapping(value = "/m05/{a}/x/{b}", method = RequestMethod.GET)
	public Map<String, String> m05(@PathVariable("a") String a, @PathVariable("b") String b) {
		Map<String, String> map = new HashMap<String, String>();

		map.put("a", a);
		map.put("b", b);

		return map;
	}

	/**
	 * Récupérer des éléments d'URL et des paramètres
	 * 
	 * @param a
	 * @param b
	 * @param c
	 * @return
	 */
	@RequestMapping(value = "/m06/{a}/x/{b}", method = RequestMethod.GET)
	public Map<String, Object> m06(@PathVariable("a") Integer a, @PathVariable("b") Double b, Double c) {
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("a", a);
		map.put("b", b);
		map.put("c", c);

		return map;
	}

	/**
	 * Accéder à la totalité de la requête
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/m07", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
	public String m07(HttpServletRequest request) {
		Enumeration<String> headerNames = request.getHeaderNames();
		StringBuffer buffer = new StringBuffer();

		while (headerNames.hasMoreElements()) {
			String name = headerNames.nextElement();
			buffer.append(String.format("%s : %s\n", name, request.getHeader(name)));
		}

		// return buffer.toString();

		// Ça fonctionne !
		return (String) request.getParameter("name");
	}

	/**
	 * Accéder à l'objet [Writer]
	 * 
	 * @param writer
	 * @throws IOException
	 */
	@RequestMapping(value = "/m08", method = RequestMethod.GET)
	public void m08(Writer writer) throws IOException {
		writer.write("Hello world!");
	}

	/**
	 * Accéder à un entête HTTP
	 * 
	 * @param userAgent
	 * @return
	 */
	@RequestMapping(value = "/m09", method = RequestMethod.GET)
	public String m09(@RequestHeader("User-Agent") String userAgent) {
		return userAgent;
	}

	/**
	 * Accéder à un cookie (ici on crée le cookie)
	 * 
	 * @param response
	 */
	@RequestMapping(value = "/m10", method = RequestMethod.GET)
	public void m10(HttpServletResponse response) {
		response.addCookie(new Cookie("cookie1", "rememberme"));
	}

	/**
	 * Accéder à un cookie (ici on récupère la valeur du cookie)
	 * 
	 * @param cookie1
	 * @return
	 */
	@RequestMapping(value = "/m11", method = RequestMethod.GET)
	public String m11(@CookieValue("cookie1") String cookie1) {
		return cookie1;
	}

	/**
	 * Accéder au corps d'un POST
	 * 
	 * @param requestBody
	 * @return
	 */
	@RequestMapping(value = "/m12", method = RequestMethod.POST)
	public String m12(@RequestBody String requestBody) {
		return requestBody;
	}

	/**
	 * Récupérer des valeurs postées en JSON. L'annotation [@RequestBody] a été
	 * associée à un objet de type [Personne]. Le corps JSON sera automatiquement
	 * désérialisé dans cet objet.
	 * 
	 * @param personne
	 * @return
	 */
	@RequestMapping(value = "/m13", method = RequestMethod.POST, consumes = "application/json")
	public String m13(@RequestBody Personne personne) {
		return personne.toString();
	}

	@RequestMapping(value = "/m14", method = RequestMethod.POST, consumes = "text/plain")
	public String m13(@RequestBody String requestBody) throws JsonParseException, JsonMappingException, IOException {
		Personne personne = new ObjectMapper().readValue(requestBody, Personne.class);
		return personne.toString();
	}

	/**
	 * Récupérer la session
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/m15", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
	public String m15(HttpSession session) {
		Object objCompteur = session.getAttribute("compteur");
		int iCompteur = (objCompteur == null) ? 0 : (Integer) objCompteur;

		iCompteur++;
		session.setAttribute("compteur", iCompteur);
		return String.valueOf(iCompteur);
	}

	/**
	 * Gérer un objet de portée (scope) session [Autowired]
	 * 
	 * @return
	 */
	@RequestMapping(value = "/m16", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
	public String m16() {
		session.setCompteur(session.getCompteur() + 1);
		return String.valueOf(session.getCompteur());
	}

	/**
	 * Gérer un objet de portée application [Autowired]
	 * 
	 * @return
	 */
	@RequestMapping(value = "/m17", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
	public String m17() {
		return String.valueOf(application.getCompteur().incrementAndGet());
	}

	/**
	 * Utilisation de [@SessionAttributes]. La clé [container] est mise en session.
	 * Etape indispensable avant d'appeler m19.
	 * 
	 * @param session
	 */
	@RequestMapping(value = "/m18", method = RequestMethod.GET)
	public void m18(HttpSession session) {
		// ici on met la clé [container] dans la session
		session.setAttribute("container", new Container());
	}

	/**
	 * On utilise l'annotation [@ModelAttribute]. Le comportement de cette
	 * annotation est assez complexe. Le paramètre [container] de cette annotation
	 * peut désigner diverses choses et en particulier un objet de la session. Il
	 * faut pour cela que celui-ci ait été déclaré avec une annotation
	 * [@SessionAttributes] sur la classe elle-même (voir plus haut).
	 * 
	 * L'annotation [@SessionAttributes({"container"})] fait que cette clé peut être
	 * injectée dans un paramètre annoté avec [@ModelAttribute("container")].
	 * 
	 * Pas visible dans l'exemple d'exécution qui va suivre, mais une information
	 * annotée avec [@ModelAttribute] fait automatiquement partie du modèle M
	 * transmis à la vue V.
	 * 
	 * @param container
	 * @return
	 */
	@RequestMapping(value = "/m19", method = RequestMethod.GET)
	public String m19(@ModelAttribute("container") Container container) {
		container.setCompteur(1 + container.getCompteur());

		return String.valueOf(container.getCompteur());
	}

	/**
	 * Définit un attribut de modèle nommé [p]. Il s'agit du modèle M d'une vue V,
	 * modèle représenté par un type [Model] dans Spring MVC. Un modèle se comporte
	 * comme un dictionnaire de couples [clé, valeur]. Ici, la clé [p] est associée
	 * à l'objet [Personne] construit par la méthode [getPersonne]. Le nom de la
	 * méthode peut être quelconque.
	 * 
	 * @return
	 */
	@ModelAttribute("p")
	public Personne getPersonne() {
		return new Personne(7, "abcd", 14);
	}

	// ---------------instanciation de @ModelAttribute --------------------------
	// sera injecté s'il est dans la session
	// sera injecté si le contrôleur a défini une méthode pour cet attribut
	// peut provenir des champs de l'URL s'il existe un convertisseur String -->
	// type de l'attribut
	// sinon est construit avec le constructeur par défaut
	// ensuite les attributs du modèle sont initialisés avec les paramètres du GET
	// ou du POST
	// le résultat final fera partie du modèle produit par l'action

	// l'attribut p est injecté dans les arguments------------------------
	/**
	 * On rend l'objet [personne] pour vérification. Celui-ci sera sérialisé en jSON
	 * avant d'être envoyé au client.
	 * 
	 * @param personne
	 * @return
	 */
	@RequestMapping(value = "/m20", method = RequestMethod.GET)
	public Personne m20(@ModelAttribute("p") Personne personne) {
		return personne;
	}

	/**
	 * Une action qui veut faire afficher une vue V doit construire le modèle M de
	 * celle-ci. Spring MVC gère celui-ci avec un type [Model] qui peut être injecté
	 * dans les paramètres de l'action. Au départ ce modèle est vide ou contient les
	 * informations taguées avec l'annotation [@ModelAttribute]. L'action enrichit
	 * ou non ce modèle avant de le transmettre à une vue.
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/m21", method = RequestMethod.GET)
	public String m21(Model model) {
		return model.toString();
	}

	/**
	 * L'attribut de modèle de clé [param1] n'existe pas. Dans ce cas, le type
	 * associé doit avoir un constructeur par défaut. C'est le cas ici du type
	 * [String] mais on ne peut écrire [@ModelAttribute("param1") Integer p1] car la
	 * classe [Integer] n'a pas de constructeur par défaut.
	 * 
	 * L'attribut de modèle [param1] est bien présent dans le modèle mais la méthode
	 * [toString] de la valeur associée ne donne pas d'indication sur cette valeur.
	 * 
	 * L'application est appelée de la façon suivante :
	 * http://localhost:8080/m22?p1=x
	 * 
	 * Pourquoi est-ce que la page n'affiche pas la valeur x pour p1 ???
	 * 
	 * @param p1
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/m22", method = RequestMethod.GET)
	public String m22(@ModelAttribute("param1") String p1, Model model) {

		return model.toString();
	}

	/**
	 * L'attribut de modèle [param2] est mis explicitement dans le modèle.
	 * 
	 * L'application est appelée de la façon suivante :
	 * http://localhost:8080/m23?p2=y
	 * 
	 * La page affiche la valeur y de p2...
	 * 
	 * @param p2
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/m23", method = RequestMethod.GET)
	public String m23(String p2, Model model) {
		model.addAttribute("param2", p2);
		return model.toString();
	}

	// ------ l'attribut de modèle [unePersonne] est automatiquement mis dans le
	// modèle
	/**
	 * On constate que l'annotation [@ModelAttribute("unePersonne") Personne p1] a
	 * mis la personne [p1] dans le modèle, associée à la clé [unePersonne].
	 * 
	 * Et là ça marche...
	 * 
	 * @param p1
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/m23b", method = RequestMethod.GET)
	public String m23b(@ModelAttribute("unePersonne") Personne p1, Model model) {
		return model.toString();
	}

	// On n'a pas mis l'annotation [@ModelAttribute].
	// --------- la personne p1 est automatiquement mise dans le modèle
	// -------- avec pour clé le nom de sa classe avec le 1er caractère en minuscule
	@RequestMapping(value = "/m23c", method = RequestMethod.GET)
	public String m23c(Personne p1, Model model) {
		return model.toString();
	}
}