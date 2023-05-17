package View;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import Model.Hasta;
import Model.Whour;
import Model.Appointment;
import Model.Bashekim;
import Model.Clinic;
import Model.Doctor;
import Helper.Helper;
import Helper.Item;

import javax.swing.JButton;
import javax.swing.JTabbedPane;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.JTable;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class HastaGUI extends JFrame {

	private JPanel w_pane;
	private static Hasta hasta = new Hasta();
	private JTable table_Doktor;
	private DefaultTableModel doctorModel;
	private Object[] doctorData = null;
	private Bashekim bashekim = new Bashekim();
	private Clinic clinic = new Clinic();
	private JTable table_appointments;
	private DefaultTableModel whourModel;
	private Object[] whourData = null;
	private Whour whour = new Whour();
	private Appointment appoint = new Appointment();
	private int selectDoctorID = 0;
	private String selectDoctorName = null;
	private JTable table_hastaAppointments;
	private DefaultTableModel appointModel;
	private Object[] appointData;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HastaGUI frame = new HastaGUI(hasta);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * 
	 * @throws SQLException
	 */
	public HastaGUI(Hasta hasta) throws SQLException {
		setResizable(false);
		setTitle("Hastane Yönetim Sistemi");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 777, 427);

		// Randevularım tablosu
		appointModel = new DefaultTableModel();
		Object[] colAppoint = new Object[3];
		colAppoint[0] = "İD";
		colAppoint[1] = "Doktor";
		colAppoint[2] = "Randevu Tarihi";
		appointModel.setColumnIdentifiers(colAppoint);
		appointData = new Object[3];
		for (int i = 0; i < appoint.getAppointmentList(hasta.getId()).size(); i++) {
			appointData[0] = appoint.getAppointmentList(hasta.getId()).get(i).getId();
			appointData[1] = appoint.getAppointmentList(hasta.getId()).get(i).getDoctor_name();
			appointData[2] = appoint.getAppointmentList(hasta.getId()).get(i).getApp_date();
			appointModel.addRow(appointData);
		}

		doctorModel = new DefaultTableModel();
		Object[] colDoctorName = new Object[2];
		colDoctorName[0] = "İD";
		colDoctorName[1] = "Ad Soyad";
		doctorModel.setColumnIdentifiers(colDoctorName);
		doctorData = new Object[2];

		whourModel = new DefaultTableModel();
		Object[] colWhourName = new Object[2];
		colWhourName[0] = "İD";
		colWhourName[1] = "Tarih";
		whourModel.setColumnIdentifiers(colWhourName);
		whourData = new Object[2];

		w_pane = new JPanel();
		w_pane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(w_pane);
		w_pane.setLayout(null);

		JLabel lblNewLabel = new JLabel("Hoş Geldiniz Sayın , " + hasta.getName());
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel.setBounds(10, 22, 212, 26);
		w_pane.add(lblNewLabel);

		JButton btn_exit = new JButton("Çıkış Yap");
		btn_exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginGUI LGUI = new LoginGUI();
				LGUI.setVisible(true);
				dispose();
			}
		});
		btn_exit.setBounds(662, 11, 89, 23);
		w_pane.add(btn_exit);

		JComboBox select_clinic = new JComboBox();
		select_clinic.setModel(new DefaultComboBoxModel(new String[] { "        --Poliklinik Seç--" }));
		select_clinic.setBounds(304, 53, 129, 31);

		JTabbedPane hasta_tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		JPanel pane_hasta = new JPanel();
		hasta_tabbedPane.addTab("Randevu Al", null, pane_hasta, null);
		pane_hasta.setLayout(null);
		hasta_tabbedPane.setBounds(10, 61, 741, 297);

		JScrollPane Scroll_clinic = new JScrollPane();
		Scroll_clinic.setBounds(10, 29, 261, 246);
		pane_hasta.add(Scroll_clinic);
		w_pane.add(hasta_tabbedPane);
		for (int i = 0; i < clinic.getClinicList().size(); i++) {
			select_clinic
					.addItem(new Item(clinic.getClinicList().get(i).getId(), clinic.getClinicList().get(i).getName()));
		}
		select_clinic.addItem("--Poliklinik Seç--");
		select_clinic.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (select_clinic.getSelectedIndex() != 0) {
					JComboBox c = (JComboBox) e.getSource();
					Item item = (Item) c.getSelectedItem();
					DefaultTableModel clearModel = (DefaultTableModel) table_Doktor.getModel();
					clearModel.setRowCount(0);

					try {
						for (int i = 0; i < bashekim.getClinicDoctorList(item.getKey()).size(); i++) {
							doctorData[0] = bashekim.getClinicDoctorList(item.getKey()).get(i).getId();
							doctorData[1] = bashekim.getClinicDoctorList(item.getKey()).get(i).getName();
							doctorModel.addRow(doctorData);
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				} else {
					DefaultTableModel clearModel = (DefaultTableModel) table_Doktor.getModel();
					clearModel.setRowCount(0);
				}

			}
		});
		pane_hasta.add(select_clinic);

		table_Doktor = new JTable(doctorModel);
		Scroll_clinic.setViewportView(table_Doktor);

		JLabel lbl_klinikler = new JLabel("Doktor Listesi");
		lbl_klinikler.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lbl_klinikler.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_klinikler.setBounds(61, 11, 129, 14);
		pane_hasta.add(lbl_klinikler);

		JLabel lblNewLabel_1 = new JLabel("Klinik Listesi");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_1.setBounds(327, 28, 68, 14);
		pane_hasta.add(lblNewLabel_1);

		JButton btnNewButton = new JButton("Seç");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selDoctorRow = table_Doktor.getModel().getValueAt(table_Doktor.getSelectedRow(), 0).toString();
				int selDoctorID = Integer.parseInt(selDoctorRow);
				Item clinicItem = (Item) select_clinic.getModel().getSelectedItem();
				int clinicID = clinicItem.getKey();
				DefaultTableModel clearModel = (DefaultTableModel) table_appointments.getModel();
				clearModel.setRowCount(0);

				for (int i = 0; i < whour.getWhourList(selDoctorID).size(); i++) {
					whourData[0] = whour.getWhourList(selDoctorID).get(i).getId();
					whourData[1] = whour.getWhourList(selDoctorID).get(i).getWdate();
					whourModel.addRow(whourData);
				}
				selectDoctorID = selDoctorID;
				selectDoctorName = table_Doktor.getModel().getValueAt(table_Doktor.getSelectedRow(), 1).toString();
			}
		});
		btnNewButton.setBounds(327, 143, 99, 31);
		pane_hasta.add(btnNewButton);

		JLabel lblNewLabel_2 = new JLabel("Doktor Seç");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setBounds(327, 118, 89, 14);
		pane_hasta.add(lblNewLabel_2);

		JScrollPane Scroll_Appointments = new JScrollPane();
		Scroll_Appointments.setBounds(491, 29, 245, 246);
		pane_hasta.add(Scroll_Appointments);

		table_appointments = new JTable(whourModel);
		Scroll_Appointments.setViewportView(table_appointments);
		table_appointments.getColumnModel().getColumn(0).setPreferredWidth(5);

		
		JLabel lblNewLabel_3 = new JLabel("Randevu Zamanı");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_3.setBounds(551, 11, 113, 14);
		pane_hasta.add(lblNewLabel_3);

		JButton btn_takeAppointment = new JButton("Randevu Al");
		btn_takeAppointment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selrow = table_Doktor.getSelectedRow();
				int selrow2 = table_appointments.getSelectedRow();
				if (selrow >= 0 && selrow2 >= 0) {
					int hastaID = hasta.getId();
					String hastaName = hasta.getName();
					String app_date = table_appointments.getModel().getValueAt(selrow2, 1).toString();
					boolean control = appoint.addAppointment(selectDoctorID, hastaID, hastaName, selectDoctorName,
							app_date);
					if (control) {
						Helper.showMsg("success");
						hasta.updateWhourStatus(selectDoctorID, app_date);
						int selDoctorID = Integer.parseInt(
								table_Doktor.getModel().getValueAt(table_Doktor.getSelectedRow(), 0).toString());
						updateWhourModel(selectDoctorID);
						updateAppointmentModel(hasta.getId());
					} else {
						Helper.showMsg("error");
					}
				} else {
					Helper.showMsg("error");

				}
			}
		});
		btn_takeAppointment.setBounds(317, 210, 116, 30);
		pane_hasta.add(btn_takeAppointment);

		JPanel w_myAppointments = new JPanel();
		hasta_tabbedPane.addTab("Randevularım", null, w_myAppointments, null);
		w_myAppointments.setLayout(null);

		JScrollPane scroll_randevu = new JScrollPane();
		scroll_randevu.setBounds(10, 11, 726, 264);
		w_myAppointments.add(scroll_randevu);

		table_hastaAppointments = new JTable(appointModel);
		scroll_randevu.setViewportView(table_hastaAppointments);

		JButton btnNewButton_1 = new JButton("Sil");
		btnNewButton_1.setBounds(662, 37, 89, 23);
		w_pane.add(btnNewButton_1);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (Helper.confirm("sure")) {
					int selrow = table_hastaAppointments.getSelectedRow();
					if (selrow >= 0) {
						int selectID = Integer
								.parseInt(table_hastaAppointments.getModel().getValueAt(selrow, 0).toString());
						boolean control = appoint.deleteAppointment(selectID);
						if (control) {
							Helper.showMsg("success");
							String selectDate = table_hastaAppointments.getModel()
									.getValueAt(table_hastaAppointments.getSelectedRow(), 2).toString();
							hasta.updateWhourStatus2(selectDate);
							updateAppointmentModel(hasta.getId());
						} else {
							Helper.showMsg("error");
						}
					} else {
						Helper.showMsg("error");
					}
				}
			}
		});

	}

	public void updateAppointmentModel(int hasta_id) {
		DefaultTableModel clearModel = (DefaultTableModel) table_hastaAppointments.getModel();
		clearModel.setRowCount(0);
		for (int i = 0; i < appoint.getAppointmentList(hasta_id).size(); i++) {
			appointData[0] = appoint.getAppointmentList(hasta_id).get(i).getId();
			appointData[1] = appoint.getAppointmentList(hasta_id).get(i).getDoctor_name();
			appointData[2] = appoint.getAppointmentList(hasta_id).get(i).getApp_date();
			appointModel.addRow(appointData);
		}
	}

	public void updateWhourModel(int doctor_id) {
		DefaultTableModel clearModel = (DefaultTableModel) table_appointments.getModel();
		clearModel.setRowCount(0);
		for (int i = 0; i < whour.getWhourList(doctor_id).size(); i++) {
			whourData[0] = whour.getWhourList(doctor_id).get(i).getId();
			whourData[1] = whour.getWhourList(doctor_id).get(i).getWdate();
			whourModel.addRow(whourData);
		}
	}
}
