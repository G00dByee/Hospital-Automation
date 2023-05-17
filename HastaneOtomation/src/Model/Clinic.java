package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import Helper.DBConnection;

public class Clinic {
	private int id;
	private String name;

	Connection con = null;
	Statement st = null;
	ResultSet rs = null;
	PreparedStatement preparedstatement = null;
	DBConnection conn = new DBConnection();

	public Clinic(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public Clinic() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<Clinic> getClinicList() throws SQLException {
		ArrayList<Clinic> list = new ArrayList<Clinic>();
		Clinic obj;
		try {
			con = conn.connDb();
			st = con.createStatement();
			rs = st.executeQuery("SELECT * FROM clinic");
			while (rs.next()) {
				obj = new Clinic();
				obj.setId(rs.getInt("id"));
				obj.setName(rs.getString("name"));
				list.add(obj);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			con.close();
		}
		return list;
	}

	public boolean addClinic(String name) {
		String query = "INSERT INTO clinic" + "(name) VALUES" + "(?)";
		boolean key = false;
		try {
			con = conn.connDb();
			st = con.createStatement();
			preparedstatement = con.prepareStatement(query);
			preparedstatement.setString(1, name);
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

	public boolean updateClinic(int id, String name) {
		String query = "UPDATE clinic SET name = ? WHERE id = ?";
		boolean key = false;
		try {
			con = conn.connDb();
			st = con.createStatement();
			preparedstatement = con.prepareStatement(query);
			preparedstatement.setString(1, name);
			preparedstatement.setInt(2, id);
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

	public Clinic getFetch(int id) {
		con = conn.connDb();
		Clinic c = new Clinic();;
		try {
			st = con.createStatement();
			rs = st.executeQuery("SELECT * FROM clinic WHERE id =" + id);
			while (rs.next()) {
				c.setId(rs.getInt("id"));
				c.setName(rs.getString("name"));
				break;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return c;
	}
	public boolean deleteClinic(int id) {
		String query= "DELETE FROM clinic WHERE id = ?";
		boolean key = false;
		try {
			con = conn.connDb();
			st = con.createStatement();
			preparedstatement = con.prepareStatement(query);
			preparedstatement.setInt(1,id);
			preparedstatement.executeUpdate();
			key = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(key) 
			return true;
		else 
			return false;
	}
	
	
}
