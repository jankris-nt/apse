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

import com.apse.model.App;
import com.apse.model.AppDao;
import com.apse.model.AppsCreationDto;

@RestController
@RequestMapping("/apps")
public class AppController {

	private AppDao appDao;

	// @Autowired
	public AppController(AppDao appDao) {
		this.appDao = appDao;
	}

	@GetMapping("/{id}")
	public Map<String, Object> getAppById(@PathVariable String id) {
		return appDao.getAppById(id);
	}

	@GetMapping(value = "/title/{title}", produces = "application/json; charset=utf-8")
	public ModelAndView getTitle(@PathVariable String title, Model model) throws IOException {
		System.err.println("----------------------Title-----------------------" + title);
		
		List<App> appsFound = appDao.getAppByTitle(title);
		model.addAttribute("apps", appsFound);
		ModelAndView mav = new ModelAndView("appsPage");

		return mav;
	}

	@GetMapping(value = "/findAllThyme")
	public ModelAndView getTitleThyme(Model model) throws Exception {

		List<App> allApps = appDao.findAll();

		model.addAttribute("apps", allApps);
		ModelAndView mav = new ModelAndView("appsPage");

		return mav;
	}

	@GetMapping(value = "/findAll", produces = "application/json; charset=utf-8")
	public List<App> findAll() throws Exception {

		System.err.println("----------------------findall-----------------------");

		return appDao.findAll();
	}

	@PostMapping
	public App insertApp(@RequestBody App app) throws Exception {
		return appDao.insertApp(app);
	}

	@PutMapping("/{id}")
	public Map<String, Object> updateAppById(@RequestBody App app, @PathVariable String id) {
		return appDao.updateAppById(id, app);
	}

	@DeleteMapping("/{id}")
	public void deleteAppById(@PathVariable String id) {
		appDao.deleteAppById(id);
	}

	@GetMapping("/search")
	public List<App> search(@RequestParam("query") String query) {
		return appDao.wildcardQuery(query);
	}

	
	@GetMapping("/create") public String showCreateForm(Model model) {
	AppsCreationDto appsForm = new AppsCreationDto();
	 
	// App app = new App(null, null, null, null, null, null, 0, null);
	//	for (int i = 1; i <= 3; i++) { appsForm.addApp(new App()); }
	// appsForm.addApp(app); 
	model.addAttribute("form", appsForm); return "apps/createAppsForm"; }
	 

}
