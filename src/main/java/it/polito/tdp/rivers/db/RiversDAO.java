package it.polito.tdp.rivers.db;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import it.polito.tdp.rivers.model.Flow;
import it.polito.tdp.rivers.model.River;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class RiversDAO {

	public List<River> getAllRivers() {
		
		final String sql = "SELECT id, name FROM river";

		List<River> rivers = new LinkedList<River>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				rivers.add(new River(res.getInt("id"), res.getString("name")));
			}

			conn.close();
			
		} catch (SQLException e) {
			//e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}

		return rivers;
	}
	
	public List<Flow> getAllFlows(River river) {
		
		final String sql = "SELECT *"
				+ "FROM flow "
				+ "WHERE river = ? "
				+ "ORDER BY day";
		
		List<Flow> list = new ArrayList<Flow>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, river.getId());
			ResultSet res = st.executeQuery();

			while (res.next()) {
				Date date = res.getDate("day");
				LocalDate data2 =  new java.sql.Date(date.getTime()).toLocalDate();
				list.add(new Flow(data2, res.getDouble("flow"),river));
			};
			
			conn.close();
			
			return list;
			
		} catch (SQLException e) {
			//e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}
	}
	
	
	
	
	
	
}
