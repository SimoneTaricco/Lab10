package it.polito.tdp.rivers.model;


import java.util.PriorityQueue;

import it.polito.tdp.rivers.model.Event.EventType;

public class Simulator {
	
	private Model model;
	private final static Integer CONV_TAX = 86400; // per la conversione
	
	public Simulator() {
		this.model = new Model();
	}
	
	// coda
	private PriorityQueue<Event> queue;	
	
	// parametri in input
	private Double Q;
	private River river;
	
	// stato del sistema
	private Double C; 			// capienza attuale	
	private Double f_out_min;
	private Double f_out_provv; 		// provvisoria, per irrigazione
	
	// valori da simulazione
	private double occupMedia;
	private int giorniNonMinima;
		
	public void setParam(Double k, double fmedio, River river) {
		
		this.Q = k*fmedio*30*CONV_TAX;			// capacità massima
		this.C = Q/2;              			// capacità attuale           
		this.f_out_min = 0.8*fmedio*CONV_TAX;  // se C + quello che entra è maggiore di questo, rimane la differenza, altrimenti si svuota tutto
		this.river = river;
	}
	
	
	public void run() {
		
		this.queue = new PriorityQueue<Event>();
		
		// stato iniziale
		this.occupMedia = 0;
		this.giorniNonMinima = 0;
		
		// popolazione della coda
		for(Flow f:model.getAllFlows(river)) {
			this.queue.add(new Event(f.getDay(), EventType.INGRESSO, f.getFlow()*CONV_TAX)); 
		}
		
		// ciclo di simulazione
		while(!this.queue.isEmpty()) {
			Event e = this.queue.poll();
			processEvent(e);
		}
	}
		
	private void processEvent(Event e) {
		
		switch(e.getType()) {
			
		case INGRESSO: 
			
			this.C += e.getFlow(); //capienza corrente con flusso del giorno
			
			if(this.C > this.Q) //tracimazione
				this.queue.add(new Event(e.getDate(), EventType.TRACIMAZIONE, e.getFlow()));
			
			//probabilità aggiuntiva
			int p = (int) (Math.random()*100);
			if(p < 5) //irrigazione
				this.queue.add(new Event(e.getDate(), EventType.IRRIGAZIONE, e.getFlow()));
			else //uscita classica
				this.queue.add(new Event(e.getDate(), EventType.USCITA, e.getFlow()));
			
			break;
			
		case USCITA:
			
			if(C<f_out_min) {
				C = 0.0;
				this.giorniNonMinima++;
			} else {
				this.C -= this.f_out_min;
			}
			this.occupMedia += C;
			
			break;
			
		case TRACIMAZIONE:

			this.C = this.Q;
			
			break;
			
		case IRRIGAZIONE:
			
			this.f_out_provv = 10*this.f_out_min;
			
			if(f_out_provv > C) { 
				//irrigazione completa non possibile
				this.giorniNonMinima++;
				this.f_out_provv = this.C;
				this.C = 0.0;
				this.occupMedia += this.C;
			}
			else {
				//irrigazione possibile
				this.C -= this.f_out_provv;
			}
			this.occupMedia += this.C;
			
			break;		
		}
	
	}


	public int getGiorniDisservizio() {
		return this.giorniNonMinima;
	}
	
	public double getOccupMedia() {
		return this.occupMedia;
	}

}
