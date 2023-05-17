package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Helper.DBConnection;

public class Hasta extends User {

	DBConnection conn = new DBConnection();
	Connection con = null;
	PreparedStatement preparedstatement = null;
	Statement st = null;
	ResultSet rs = null;

	public Hasta() {
		super();

	}

	public Hasta(int id, String tcno, String name, String type, String password) {
		super(id, tcno, name, type, password);

	}

	public boolean addHasta(String name, String tcno, String password) {
		String query = "INSERT INTO user " + "(name,tcno,password)VALUES" + "(?,?,?,?)";
		int key = 0;
		boolean duplicate = false;
		try {
			con = conn.connDb();
			st = con.createStatement();
			rs = st.executeQuery("SELECT * FROM user WHERE tcno = '" +tcno+"'");
			while (rs.next()) {
				duplicate = true;	
				break;
			}
			if(!duplicate) {
			preparedstatement = con.prepareStatement(query);
			preparedstatement.setString(1, name);
			preparedstatement.setString(2, tcno);
			preparedstatement.setString(3, password);
			preparedstatement.executeUpdate();
			key = 1;
		} }catch (SQLException e) {
			e.printStackTrace();
		}
		if (key == 1)
			return true;
		else
			return false;
	}
	public boolean updateWhourStatus(int doctor_id,String wdate) {
		String query = "UPDATE whour SET status = ? WHERE doctor_id = ? AND wdate =?";
		int key = 0;
		try {
			con = conn.connDb();
			st = con.createStatement();
			preparedstatement = con.prepareStatement(query);
			preparedstatement.setString(1, "c");
			preparedstatement.setInt(2, doctor_id);
			preparedstatement.setString(3, wdate);
			preparedstatement.executeUpdate();
			key = 1; 
			{
		} }catch (SQLException e) {
			e.printStackTrace();
		}
		if (key == 1)
			return true;
		else
			return false;
	}
	public boolean updateWhourStatus2(String wdate) {
		String query = "UPDATE whour SET status = ? WHERE wdate = ?";
		int key = 0;
		try {
			con = conn.connDb();
			st = con.createStatement();
			preparedstatement = con.prepareStatement(query);
			preparedstatement.setString(1, "o");
			preparedstatement.setString(2, wdate);
			preparedstatement.executeUpdate();
			key = 1; 
			{
		} }catch (SQLException e) {
			e.printStackTrace();
		}
		if (key == 1)
			return true;
		else
			return false;
	}
}
