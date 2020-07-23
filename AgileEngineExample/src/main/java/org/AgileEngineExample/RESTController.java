package org.AgileEngineExample;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class RESTController {	
	
	@RequestMapping("/search/{searchTerm}")
	public String index(@PathVariable("searchTerm") String searchTerm) {
		return "Search Term: " + searchTerm;
	}

}
