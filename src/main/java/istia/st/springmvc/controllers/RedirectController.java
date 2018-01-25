package istia.st.springmvc.controllers;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class RedirectController {

	@RequestMapping(value = "/a10", method = RequestMethod.GET)
	public String a10() {
		// on rend comme résultat 'a01' qui est le nom d'une action. Ce sera alors elle
		// qui va envoyer la réponse au client
		return "a01";
	}
	
	@RequestMapping(value = "/a11", method = RequestMethod.GET)
	public String a11() {
		return "redirect:/a01";
	}
	
	@RequestMapping(value = "/a12", method = RequestMethod.GET)
	public void a12(HttpServletResponse response) {
		response.setStatus(301);
		response.setHeader("Location", "/a01");
	}
}
