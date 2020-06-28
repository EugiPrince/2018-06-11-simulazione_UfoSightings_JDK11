package it.polito.tdp.ufo.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.ufo.model.AnnoAvvistamenti;
import it.polito.tdp.ufo.model.Sighting;

public class SightingsDAO {
	
	public List<Sighting> getSightings() {
		String sql = "SELECT * FROM sighting" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Sighting> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				list.add(new Sighting(res.getInt("id"),
						res.getTimestamp("datetime").toLocalDateTime(),
						res.getString("city"), 
						res.getString("state"), 
						res.getString("country"),
						res.getString("shape"),
						res.getInt("duration"),
						res.getString("duration_hm"),
						res.getString("comments"),
						res.getDate("date_posted").toLocalDate(),
						res.getDouble("latitude"), 
						res.getDouble("longitude"))) ;
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}

	public List<AnnoAvvistamenti> getAnni() {
		String sql = "SELECT YEAR(datetime) AS anno, COUNT(id) AS avvistamenti " + 
				"FROM sighting " + 
				"WHERE country = \"us\" " + 
				"GROUP BY YEAR(datetime)";
		List<AnnoAvvistamenti> result = new ArrayList<>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			ResultSet res = st.executeQuery();
			
			while(res.next()) {
				result.add(new AnnoAvvistamenti(Year.of(res.getInt("anno")), res.getInt("avvistamenti")));
			}
			
			conn.close();
			return result;
		} catch(SQLException e) {
			e.printStackTrace();
			return null ;
		}
	}
	
	public List<String> getVertici(Year anno) {
		String sql = "SELECT DISTINCT state " + 
				"FROM sighting " + 
				"WHERE country = \"us\" "
				+ "AND YEAR(datetime) = ?";
		List<String> stati = new ArrayList<>();
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			st.setInt(1, anno.getValue());
			ResultSet res = st.executeQuery();
			
			while(res.next()) {
				stati.add(res.getString("state"));
			}
			
			conn.close();
			return stati;
		} catch(SQLException e) {
			e.printStackTrace();
			return null ;
		}
	}

	public boolean arco(String s1, String s2, Year anno) {
		String sql = "SELECT COUNT(*) AS cnt " + 
				"FROM sighting s1, sighting s2 " + 
				"WHERE s1.country = \"us\" AND s2.country = \"us\" AND YEAR(s1.datetime) = YEAR(s2.datetime) AND YEAR(s1.datetime) = ? AND s1.state = ? AND s2.state = ? AND s2.datetime > s1.datetime";
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			st.setInt(1, anno.getValue());
			st.setString(2, s1);
			st.setString(3, s2);
			ResultSet res = st.executeQuery();
			
			if(res.next()) {
				if(res.getInt("cnt") > 0) {
					conn.close();
					return true;
				}
				else {
					conn.close();
					return false;
				}
			}
			else {
				conn.close();
				return false;
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
		
	}
}
