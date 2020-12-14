package com.apse.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.apse.model.Serv;
import com.apse.model.ServDao;
import com.apse.model.ServsCreationDto;

@RestController
@RequestMapping("/servs")
public class ServController {

	private ServDao servDao;

	// @Autowired
	public ServController(ServDao servDao) {
		this.servDao = servDao;
	}

	@GetMapping("/{id}")
	public Map<String, Object> getServById(@PathVariable String id) {
		return servDao.getServById(id);
	}

	@GetMapping(value = "/title/{title}", produces = "application/json; charset=utf-8")
	public ModelAndView getTitle(@PathVariable String title, Model model) throws IOException {
		System.err.println("----------------------Title-----------------------" + title);
		
		List<Serv> servsFound = servDao.getServByTitle(title);
		model.addAttribute("servs", servsFound);
		ModelAndView mav = new ModelAndView("servsPage");

		return mav;
	}

	@GetMapping(value = "/findAllThyme")
	public ModelAndView getTitleThyme(Model model) throws Exception {

		List<Serv> allServs = servDao.findAll();

		model.addAttribute("servs", allServs);
		ModelAndView mav = new ModelAndView("servsPage");

		return mav;
	}

	@GetMapping(value = "/findAll", produces = "application/json; charset=utf-8")
	public List<Serv> findAll() throws Exception {

		System.err.println("----------------------findall-----------------------");

		return servDao.findAll();
	}

	@PostMapping
	public Serv insertServ(@RequestBody Serv serv) throws Exception {
		return servDao.insertServ(serv);
	}

	@PutMapping("/{id}")
	public Map<String, Object> updateServById(@RequestBody Serv serv, @PathVariable String id) {
		return servDao.updateServById(id, serv);
	}

	@DeleteMapping("/{id}")
	public void deleteServById(@PathVariable String id) {
		servDao.deleteServById(id);
	}

	@GetMapping("/search")
	public List<Serv> search(@RequestParam("query") String query) {
		return servDao.wildcardQuery(query);
	}

	
	@GetMapping("/create") public String showCreateForm(Model model) {
	ServsCreationDto servsForm = new ServsCreationDto();
	 
	// for (int i = 1; i <= 3; i++) { servsForm.addServ(new Serv()); }
	// servsForm.addServ(new Serv(null, null, null, null, null, null, null, null));  
	model.addAttribute("form", servsForm); return "servs/createServsForm"; }
	 

}
