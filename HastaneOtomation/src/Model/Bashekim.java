package Model;

import Helper.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Bashekim extends User {
	Connection con = null;
	Statement st = null;
	ResultSet rs = null;
	PreparedStatement preparedstatement = null;

	public Bashekim() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Bashekim(int id, String tcno, String name, String type, String password) {
		super(id, tcno, name, type, password);
		// TODO Auto-generated constructor stub
	}

	public ArrayList<User> getDoctorList() throws SQLException {
		ArrayList<User> list = new ArrayList<User>();
		User obj;
		try {
			con = conn.connDb();
			st = con.createStatement();
			rs = st.executeQuery("SELECT * FROM user WHERE type = 'doktor'");
			while (rs.next()) {
				obj = new User(rs.getInt("id"), rs.getString("tcno"), rs.getString("name"), rs.getString("type"),
						rs.getString("password"));
				list.add(obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public ArrayList<User> getClinicDoctorList(int clinic_id) throws SQLException {
		ArrayList<User> list = new ArrayList<User>();
		User obj;
		try {
			con = conn.connDb();
			st = con.createStatement();
			rs = st.executeQuery(
					"SELECT u.id,u.tcno,u.type,u.name,u.password FROM worker e LEFT JOIN user u ON e.user_id = u.id WHERE clinic_id="
							+ +clinic_id);
			while (rs.next()) {
				obj = new User(rs.getInt("u.id"), rs.getString("u.tcno"), rs.getString("u.name"),
						rs.getString("u.type"), rs.getString("u.password"));
				list.add(obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public boolean addDoctor(String name, String tcno, String password) {
		String query = "INSERT INTO user" + "(tcno,password,name,type) VALUES" + "(?,?,?,?)";
		boolean key = false;
		try {
			con = conn.connDb();
			st = con.createStatement();
			preparedstatement = con.prepareStatement(query);
			preparedstatement.setString(1, tcno);
			preparedstatement.setString(2, password);
			preparedstatement.setString(3, name);
			preparedstatement.setString(4, "doktor");
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

	public boolean deleteDoctor(int id) {
		String query = "DELETE FROM user WHERE id = ?";
		boolean key = false;
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

	public boolean updateDoctor(int id, String tcno, String password, String name) {
		String query = "UPDATE user SET name = ?,tcno =?,password=? WHERE id = ?";
		boolean key = false;
		try {
			con = conn.connDb();
			st = con.createStatement();
			preparedstatement = con.prepareStatement(query);
			preparedstatement.setString(1, name);
			preparedstatement.setString(2, tcno);
			preparedstatement.setString(3, password);
			preparedstatement.setInt(4, id);
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

	public boolean addWorker(int user_id, int clinic_id) {
		String query = "INSERT INTO worker" + "(user_id,clinic_id) VALUES" + "(?,?)";
		boolean key = false;
		int count = 0;
		try {
			con = conn.connDb();
			rs = st.executeQuery("SELECT * FROM worker WHERE clinic_id=" + clinic_id + " AND user_id=" + user_id);
			while (rs.next()) {
				count++;
			}
			if (count == 0) {
				st = con.createStatement();
				preparedstatement = con.prepareStatement(query);
				preparedstatement.setInt(1, user_id);
				preparedstatement.setInt(2, clinic_id);
				preparedstatement.executeUpdate();
				key = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (key)
			return true;
		else
			return false;
	}

	public ArrayList<Appointment> getAppointmentLİst() {
		ArrayList<Appointment> list = new ArrayList<Appointment>();
		Appointment obj;

		try {
			con = conn.connDb();
			st = con.createStatement();
			rs = st.executeQuery("SELECT * FROM appointment ");
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
