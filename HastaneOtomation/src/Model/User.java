package Model;

import Helper.DBConnection;

public class User {
	private int id;
	private String tcno,name,type,password;
	DBConnection conn = new DBConnection();
	
	public User(int id, String tcno, String name, String type, String password) {
		super();
		this.id = id;
		this.tcno = tcno;
		this.name = name;
		this.type = type;
		this.password = password;
	}
	
	public User() {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTcno() {
		return tcno;
	}

	public void setTcno(String tcno) {
		this.tcno = tcno;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
