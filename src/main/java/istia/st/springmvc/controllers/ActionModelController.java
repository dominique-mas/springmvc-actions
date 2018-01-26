package istia.st.springmvc.controllers;

import java.io.IOException;
import java.io.Writer;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import istia.st.springmvc.model.Personne;
import istia.st.springmvc.model.SessionModel;

@RestController
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
}