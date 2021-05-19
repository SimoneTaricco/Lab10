package it.polito.tdp.rivers.model;

import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.rivers.db.RiversDAO;

public class Model {
	
	private RiversDAO dao;
	private List<Flow> flows;

	public Model() {
		super();
		dao = new RiversDAO();
	}
	
	public List<River> getAllRivers(){
		return dao.getAllRivers();
	}
	
	public List<Flow> getAllFlows(River river) {
		
		this.flows = new ArrayList<>(this.dao.getAllFlows(river));
				
		return this.dao.getAllFlows(river);
	}
		
	
	public double getAvgFlows(List<Flow> list) {
		
		double res = 0;
		
		for(Flow f:list)
			res += f.getFlow();
		
		return res/list.size();
	}


}
