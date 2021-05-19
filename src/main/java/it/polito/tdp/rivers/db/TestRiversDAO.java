package it.polito.tdp.rivers.db;

import it.polito.tdp.rivers.model.River;

public class TestRiversDAO {

	public static void main(String[] args) {
		RiversDAO dao = new RiversDAO();
		System.out.println(dao.getAllRivers());
		
		for (River r:dao.getAllRivers())
			System.out.println(r.getId());
	
	}

}
