package istia.st.springmvc.controllers;

import java.io.IOException;
import java.io.Writer;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import istia.st.springmvc.model.Personne;

@RestController
public class ActionModelController {

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
		
		//return buffer.toString();
		
		// Ça fonctionne !
		return (String) request.getParameter("name");
	}
	
	@RequestMapping(value = "/m08", method = RequestMethod.GET)
	public void m08(Writer writer) throws IOException {
		writer.write("Hello world!");
	}
}
