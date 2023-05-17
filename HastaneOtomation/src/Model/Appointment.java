package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Helper.DBConnection;

public class Appointment {

	private int hasta_id, doctor_id, id;
	private String hasta_name, doctor_name, app_date;
	private DBConnection conn = new DBConnection();
	Connection con = null;
	Statement st = null;
	ResultSet rs = null;
	PreparedStatement preparedstatement = null;

	public Appointment(int hasta_id, int doctor_id, int id, String hasta_name, String doctor_name, String app_date) {
		super();
		this.hasta_id = hasta_id;
		this.doctor_id = doctor_id;
		this.id = id;
		this.hasta_name = hasta_name;
		this.doctor_name = doctor_name;
		this.app_date = app_date;
	}

	public Appointment() {
	}

	public int getHasta_id() {
		return hasta_id;
	}

	public void setHasta_id(int hasta_id) {
		this.hasta_id = hasta_id;
	}

	public int getDoctor_id() {
		return doctor_id;
	}

	public void setDoctor_id(int doctor_id) {
		this.doctor_id = doctor_id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getHasta_name() {
		return hasta_name;
	}

	public void setHasta_name(String hasta_name) {
		this.hasta_name = hasta_name;
	}

	public String getDoctor_name() {
		return doctor_name;
	}

	public void setDoctor_name(String doctor_name) {
		this.doctor_name = doctor_name;
	}

	public String getApp_date() {
		return app_date;
	}

	public void setApp_date(String app_date) {
		this.app_date = app_date;
	}

	public boolean addAppointment(int doctor_id, int hasta_id, String hasta_name, String doctor_name, String app_date) {
		boolean key = false;
		String query = "INSERT INTO appointment" + "(doctor_id,hasta_id,hasta_name,doctor_name,app_date)VALUES"
				+ "(?,?,?,?,?)";

		try {
			con = conn.connDb();
			preparedstatement = con.prepareStatement(query);
			preparedstatement.setInt(1, doctor_id);
			preparedstatement.setInt(2, hasta_id);
			preparedstatement.setString(3, hasta_name);
			preparedstatement.setString(4, doctor_name);
			preparedstatement.setString(5, app_date);
			preparedstatement.executeUpdate();
			key = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		if (key)
			return true;
		else
			return false;
	}

	public ArrayList<Appointment> getAppointmentList(int hasta_id) {
		ArrayList<Appointment> list = new ArrayList<Appointment>();
		Appointment obj;

		try {
			con = conn.connDb();
			st = con.createStatement();
			rs = st.executeQuery("SELECT * FROM appointment WHERE hasta_id = " + hasta_id);
			while (rs.next()) {
				obj = new Appointment();
				obj.setId(rs.getInt("id"));
				obj.setDoctor_id(rs.getInt("doctor_id"));
				obj.setDoctor_name(rs.getString("doctor_name"));
				obj.setHasta_id(rs.getInt("hasta_id"));
				obj.setHasta_name(rs.getString("hasta_name"));
				obj.setApp_date(rs.getString("app_date"));
				list.add(obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;

	}

	public boolean deleteAppointment(int id) {
		boolean key = false;
		String query = "DELETE FROM appointment WHERE id = ? ";
		try {
			con = conn.connDb();
			st = con.createStatement();
			preparedstatement = con.prepareStatement(query);
			preparedstatement.setInt(1, id);
			preparedstatement.executeUpdate();
			key = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (key)
			return true;
		else
			return false;
	}

}
