package it.polito.tdp.rivers.model;

import java.time.LocalDate;

public class Event implements Comparable<Event>{
	
	public enum EventType{ 
		INGRESSO,
		USCITA, 
		TRACIMAZIONE,
		IRRIGAZIONE;
	}
	
	private LocalDate date;
	private EventType type; 
	private double flow;

	@Override
	public int compareTo(Event other) {
		return this.date.compareTo(other.date);
	}
	
	public Event(LocalDate date, EventType type, double flow) {
		this.date = date;
		this.type = type;
		this.flow = flow;
	}

	public double getFlow() {
		return flow;
	}

	public void setFlow(double flow) {
		this.flow = flow;
	}

	public EventType getType() {
		return type;
	}

	public void setType(EventType type) {
		this.type = type;
	}

	public LocalDate getDate() {
		return date;
	}
	
	public void setDate(LocalDate date) {
		this.date = date;
	}

}

	






