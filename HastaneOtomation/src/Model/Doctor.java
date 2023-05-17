package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Helper.DBConnection;

public class Doctor extends User {
	Statement st = null;
	Connection con = null;
	ResultSet rs = null;
	PreparedStatement preparedstatement = null;

	public Doctor() {
		super();
	}

	public Doctor(int id, String tcno, String name, String type, String password) {
		super(id, tcno, name, type, password);
	}

	public boolean addWhour(int doctor_id, String doctor_name, String wdate) throws SQLException {
		int key = 0;
		int count = 0;
		String query = "INSERT INTO `hospital`.`whour` ( `doctor_id`, `doctor_name`, `wdate`) VALUES ( ?, ?, ?)";
		try {
			Connection con = conn.connDb();
			st = con.createStatement();
			rs = st.executeQuery("SELECT * FROM  `hospital`.`whour` WHERE `status`='o' AND `doctor_id` =" + doctor_id
					+ " AND `wdate`='" + wdate + "'");
			while (rs.next()) {
				count++;
				break;
			}
			if (count == 0) {
				preparedstatement = con.prepareStatement(query);
				preparedstatement.setInt(1, doctor_id);
				preparedstatement.setString(2, doctor_name);
				preparedstatement.setString(3, wdate);
				preparedstatement.executeUpdate();
			}
			key = 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (key == 1)
			return true;
		else
			return false;
	}

	public ArrayList<Appointment> getAppointmentList(int doctor_id) {
		ArrayList<Appointment> list = new ArrayList<Appointment>();
		Appointment obj;

		try {
			con = conn.connDb();
			st = con.createStatement();
			rs = st.executeQuery("SELECT * FROM appointment WHERE doctor_id = " + doctor_id);
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
		String query = "DELETE FROM appointment WHERE id =?";
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
}
