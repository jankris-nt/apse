package com.apse.model;

import java.util.List;

public class ServsCreationDto {
	
	private List<Serv> servs;
	 
    // default and parameterized constructor
	
	
 
    public void addServ(Serv serv) {
        this.servs.add(serv);
    }

	public ServsCreationDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ServsCreationDto(List<Serv> servs) {
		super();
		this.servs = servs;
	}

	public List<Serv> getServs() {
		return servs;
	}

	public void setServs(List<Serv> servs) {
		this.servs = servs;
	}
    
    

}
