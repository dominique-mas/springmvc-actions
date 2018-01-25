package istia.st.springmvc.controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResponsesControllers {

	@RequestMapping(value="/a01", method=RequestMethod.GET)
	public String a01() {
		return "Greetings from Spring Boot!";
	}
	
	@RequestMapping(value="/a02", method=RequestMethod.GET, produces="text/plain;charset=UTF-8")
	public String a02() {
		return "caractères accentués : éèàôûî";
	}
	
	@RequestMapping(value="/a03", method=RequestMethod.GET, produces="text/xml;charset=UTF-8")
	public String a03() {
		String greeting = "<greetings><greeting>Greetings from Spring Boot!</greeting></greetings>";
		return greeting;
	}
	
	@RequestMapping(value="/a04", method=RequestMethod.GET)
	public Map<String, Object> a04() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("1", "un");
		map.put("2", new int[] {4, 5});
		return map;
	}
	
	//TODO a05
	
	@RequestMapping(value="/a06")
	public void a06() {
		
	}
	
	@RequestMapping(value="/a07", method=RequestMethod.GET, produces = "text/html;charset=UTF-8")
	public String a07() {
		String greeting = "<h1>Greetings from Spring Boot!</h1>";
		return greeting;
	}
	
	@RequestMapping(value="/a08", method=RequestMethod.GET, produces = "text/plain;charset=UTF-8")
	public String a08() {
		String greeting = "<h1>Greetings from Spring Boot!</h1>";
		return greeting;
	}
	
	@RequestMapping("/a13")
	public void a13(HttpServletResponse response) throws IOException {
		response.addHeader("Content-Type", "text/html;charset=UTF-8");
		response.getWriter().write("<h3>lorem ipsum dolor</h3>");
	}
}
