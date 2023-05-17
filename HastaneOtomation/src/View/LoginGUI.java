package View;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Helper.DBConnection;
import Helper.Helper;
import Model.Bashekim;
import Model.Doctor;
import Model.Hasta;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JTabbedPane;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;

public class LoginGUI extends JFrame {

	private JPanel w_pane;
	private JTextField fld_hastaTC;
	private JTextField fld_doctorTC;
	private JPasswordField fld_doctorPass;
	private JPasswordField fld_hastaPASS;
	private DBConnection conn = new DBConnection();
	private ResultSet rs = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginGUI frame = new LoginGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public LoginGUI() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 523);
		w_pane = new JPanel();
		w_pane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(w_pane);
		w_pane.setLayout(null);

		JLabel lbl = new JLabel(new ImageIcon(getClass().getResource("hospital.png")));
		lbl.setBounds(141, 61, 143, 109);
		w_pane.add(lbl);

		JLabel lbl_1 = new JLabel("Hastane Yönetim Sistemi Hoşgeldiniz");
		lbl_1.setBounds(46, 181, 333, 14);
		lbl_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lbl_1.setHorizontalAlignment(SwingConstants.CENTER);
		w_pane.add(lbl_1);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 216, 414, 257);
		w_pane.add(tabbedPane);

		JPanel w_hastaLogin = new JPanel();
		tabbedPane.addTab("Hasta Girişi", null, w_hastaLogin, null);
		w_hastaLogin.setLayout(null);

		JLabel lblNewLabel_2 = new JLabel("Parola");
		lblNewLabel_2.setBounds(74, 126, 66, 14);
		w_hastaLogin.add(lblNewLabel_2);
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 13));

		JLabel lblNewLabel_1 = new JLabel("TC Numarası");
		lblNewLabel_1.setBounds(61, 79, 86, 14);
		w_hastaLogin.add(lblNewLabel_1);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 13));

		fld_hastaTC = new JTextField();
		fld_hastaTC.setBounds(150, 77, 143, 20);
		w_hastaLogin.add(fld_hastaTC);
		fld_hastaTC.setColumns(10);

		JButton btn_hastaRegister = new JButton("Kayıt Ol");
		btn_hastaRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Hasta hasta = new Hasta();
				RegisterGUI rGUI = new RegisterGUI();
				rGUI.setVisible(true);
				dispose();

			}
		});
		btn_hastaRegister.setBounds(10, 206, 89, 23);
		w_hastaLogin.add(btn_hastaRegister);

		JButton btn_hastaLogin = new JButton("Giriş");
		btn_hastaLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (fld_hastaTC.getText().length() == 0 || fld_hastaPASS.getText().length() == 0) {
					Helper.showMsg("fill");
				} else {
					try {
						Connection con = conn.connDb();
						Statement st = con.createStatement();
						ResultSet rs = st.executeQuery("SELECT * FROM user");
						while (rs.next()) {
							if (fld_hastaPASS.getText().equals(rs.getString("password"))
									&& fld_hastaTC.getText().equals(rs.getString("tcno"))) {
								Hasta hasta = new Hasta();
								hasta.setId(rs.getInt("id"));
								hasta.setName(rs.getString("name"));
								hasta.setPassword(rs.getString("password"));
								hasta.setTcno(rs.getString("tcno"));
								hasta.setType(rs.getString("type"));
								HastaGUI hGUI = new HastaGUI(hasta);
								hGUI.setVisible(true);
								dispose();
							}
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		btn_hastaLogin.setBounds(310, 206, 89, 23);
		w_hastaLogin.add(btn_hastaLogin);

		fld_hastaPASS = new JPasswordField();
		fld_hastaPASS.setBounds(150, 121, 143, 20);
		w_hastaLogin.add(fld_hastaPASS);

		JPanel w_doctorLogin = new JPanel();
		tabbedPane.addTab("Doktor Girişi", null, w_doctorLogin, null);
		w_doctorLogin.setLayout(null);

		JLabel lblNewLabel_1_1 = new JLabel("TC Numarası");
		lblNewLabel_1_1.setBounds(63, 71, 74, 16);
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		w_doctorLogin.add(lblNewLabel_1_1);

		fld_doctorTC = new JTextField();
		fld_doctorTC.setBounds(147, 67, 130, 23);
		fld_doctorTC.setColumns(10);
		w_doctorLogin.add(fld_doctorTC);

		JLabel lblNewLabel_2_1 = new JLabel("Parola");
		lblNewLabel_2_1.setBounds(80, 116, 52, 16);
		lblNewLabel_2_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		w_doctorLogin.add(lblNewLabel_2_1);

		JButton btn_doctorLogin = new JButton("Giriş");
		btn_doctorLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (fld_doctorTC.getText().length() == 0 || fld_doctorPass.getText().length() == 0) {
					Helper.showMsg("fill");
				} else {
					try {
						Connection con = conn.connDb();
						Statement st = con.createStatement();
						ResultSet rs = st.executeQuery("SELECT * FROM user");
						while (rs.next()) {
							if (fld_doctorTC.getText().equals(rs.getString("tcno"))
									&& fld_doctorPass.getText().equals(rs.getString("password"))) {
								if (rs.getString("type").equals("bashekim")) {
									Bashekim bashekim = new Bashekim();
									bashekim.setName(rs.getString("name"));
									bashekim.setPassword(rs.getString("password"));
									bashekim.setTcno(rs.getString("tcno"));
									bashekim.setId(rs.getInt("id"));
									bashekim.setType(rs.getString("type"));
									bashekimGUI bGUI = new bashekimGUI(bashekim);
									bGUI.setVisible(true);
									dispose();
								} else if (rs.getString("type").equals("doktor")) {
									Doctor doctor = new Doctor();
									doctor.setName(rs.getString("name"));
									doctor.setPassword(rs.getString("password"));
									doctor.setTcno(rs.getString("tcno"));
									doctor.setType(rs.getString("type"));
									doctor.setId(rs.getInt("id"));
									DoctorGUI dGUI = new DoctorGUI(doctor);
									dGUI.setVisible(true);
									dispose();
								}
							}
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		btn_doctorLogin.setBounds(147, 164, 130, 23);
		w_doctorLogin.add(btn_doctorLogin);

		fld_doctorPass = new JPasswordField();
		fld_doctorPass.setBounds(147, 116, 130, 20);
		w_doctorLogin.add(fld_doctorPass);
	}
}
