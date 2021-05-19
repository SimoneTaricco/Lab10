package it.polito.tdp.rivers.model;

import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.rivers.db.RiversDAO;

public class Model {
	
	private RiversDAO dao;
	private Double Q;
	private Double C;
	private Double f_out_min;
	private Double fout;
	private List<Flow> flows;
	private List<Double> occupazione;
	
	private double giorniNonMinima;
	
	
	private double fin;

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

	public void calcola(Double k, Double fmedio) {
		
		this.Q = k*fmedio*30*86400;
		this.C = Q/2;
		this.f_out_min = 0.8*fmedio*86400;
		this.giorniNonMinima = 0;
		this.occupazione = new ArrayList<Double>();		

		
		this.fin = 0.0;
		this.fout = 0.0;
		
		for (Flow f:flows) {
			
			System.out.println("Livello:" + C + " flusso:" + f.getFlow()*86400);
			
			/*if (C + f.getFlow()*86400 < Q) {
				this.occupazione.add(C+= f.getFlow()*86400);
				//System.out.println(C);
			}*/		
						
			if (C + f.getFlow()*86400 > f_out_min) {
				
				if (C + f.getFlow()*86400 > Q) {
					C = Q;
				}
				
				System.out.println("Rimane dell'acqua");
				C += (C-f_out_min);

				
			} else {
				this.giorniNonMinima++;	
				this.C = 0.0;
			}

		}
		
				
		
	}

}
