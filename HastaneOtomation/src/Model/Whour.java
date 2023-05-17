package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Helper.DBConnection;
import Helper.Item;

public class Whour {

	private int id, doctor_id;
	private String wdate, doctor_name, status;

	DBConnection conn = new DBConnection();
	ResultSet rs = null;
	Statement st = null;
	Connection con = null;
	PreparedStatement preparedstatement = null;

	public Whour(int id, int doctor_id, String wdate, String doctor_name, String status) {
		super();
		this.id = id;
		this.doctor_id = doctor_id;
		this.wdate = wdate;
		this.doctor_name = doctor_name;
		this.status = status;
	}


	public Whour() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getDoctor_id() {
		return doctor_id;
	}

	public void setDoctor_id(int doctor_id) {
		this.doctor_id = doctor_id;
	}

	public String getWdate() {
		return wdate;
	}

	public void setWdate(String wdate) {
		this.wdate = wdate;
	}

	public String getDoctor_name() {
		return doctor_name;
	}

	public void setDoctor_name(String doctor_name) {
		this.doctor_name = doctor_name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public ArrayList<Whour> getWhourList(int doctor_id) {
		ArrayList<Whour> list = new ArrayList<Whour>();
		Whour obj;
		try {
			con = conn.connDb();
			st = con.createStatement();
			rs = st.executeQuery("SELECT * FROM whour WHERE status = 'o' AND doctor_id = " + doctor_id );
			while (rs.next()) {
				obj = new Whour(rs.getInt("id"), rs.getInt("doctor_id"), rs.getString("wdate"),
						rs.getString("doctor_name"), rs.getString("status"));
				list.add(obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	public boolean deleteWhour(int id) {
		String query = "DELETE FROM whour WHERE id =?";
		boolean key = false;
		try {
			con = conn.connDb();
			st = con.createStatement();
			preparedstatement= con.prepareStatement(query);
			preparedstatement.setInt(1,id);
			preparedstatement.executeUpdate();
			key = true ;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(key) 
			return true;
		else 
			return false;
		
		
	}
}
