package com.apse.model;

import java.util.List;

public class AppsCreationDto {
	
	private List<App> apps;
	 
    // default and parameterized constructor
	
	
 
    public void addApp(App app) {
        this.apps.add(app);
    }

	public AppsCreationDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AppsCreationDto(List<App> apps) {
		super();
		this.apps = apps;
	}

	public List<App> getApps() {
		return apps;
	}

	public void setApps(List<App> apps) {
		this.apps = apps;
	}
    
    

}
