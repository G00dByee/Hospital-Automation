package View;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Model.Doctor;
import Model.Hasta;
import Model.Whour;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JTabbedPane;
import com.toedter.calendar.JDateChooser;

import Helper.Helper;

import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class DoctorGUI extends JFrame {

	private JPanel w_pane;
	private static Doctor doctor = new Doctor();
	private JTable table_whour;
	private DefaultTableModel whourModel = null;
	private Object[] whourData = null;
	private Whour whour = new Whour();
	private JTable table_doctorAppoint;
	private DefaultTableModel doctorappModel;
	Object[] doctorappData;
	private Hasta hasta = new Hasta();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DoctorGUI frame = new DoctorGUI(doctor);
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
	public DoctorGUI(Doctor doctor) {
		setResizable(false);
		setTitle("Hastane Yönetim Sistemi");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 777, 427);

		// Doktor Randevuları
		doctorappModel = new DefaultTableModel();
		Object[] doctorcolname = new Object[3];
		doctorcolname[0] = "İD";
		doctorcolname[1] = "Hasta";
		doctorcolname[2] = "Tarih";
		doctorappModel.setColumnIdentifiers(doctorcolname);
		doctorappData = new Object[3];
		for (int i = 0; i < doctor.getAppointmentList(doctor.getId()).size(); i++) {
			doctorappData[0] = doctor.getAppointmentList(doctor.getId()).get(i).getId();
			doctorappData[1] = doctor.getAppointmentList(doctor.getId()).get(i).getHasta_name();
			doctorappData[2] = doctor.getAppointmentList(doctor.getId()).get(i).getApp_date();
			doctorappModel.addRow(doctorappData);
		}

		// whour Model
		whourModel = new DefaultTableModel();
		Object[] colwhourName = new Object[2];
		colwhourName[0] = "İD";
		colwhourName[1] = "Tarih";
		whourModel.setColumnIdentifiers(colwhourName);
		whourData = new Object[2];
		for (int i = 0; i < whour.getWhourList(doctor.getId()).size(); i++) {
			whourData[0] = whour.getWhourList(doctor.getId()).get(i).getId();
			whourData[1] = whour.getWhourList(doctor.getId()).get(i).getWdate();
			whourModel.addRow(whourData);
		}

		w_pane = new JPanel();
		w_pane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(w_pane);
		w_pane.setLayout(null);

		JLabel lbl_Hello = new JLabel("Hoş Geldiniz Sayın, " + doctor.getName());
		lbl_Hello.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lbl_Hello.setBounds(10, 11, 203, 30);
		w_pane.add(lbl_Hello);

		JButton btnNewButton = new JButton("Çıkış Yap");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginGUI LGUI = new LoginGUI();
				LGUI.setVisible(true);
				dispose();
			}
		});
		btnNewButton.setBounds(631, 17, 109, 30);
		w_pane.add(btnNewButton);

		JTabbedPane w_DoctorTabbedPane = new JTabbedPane(JTabbedPane.TOP);
		w_DoctorTabbedPane.setFont(new Font("Tahoma", Font.PLAIN, 13));
		w_DoctorTabbedPane.setBounds(10, 53, 751, 324);
		w_pane.add(w_DoctorTabbedPane);

		JPanel w_workHoursPane = new JPanel();
		w_DoctorTabbedPane.addTab("Çalışma Saatleri", null, w_workHoursPane, null);
		w_workHoursPane.setLayout(null);

		JDateChooser select_date = new JDateChooser();
		select_date.getCalendarButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		select_date.setBounds(31, 11, 130, 20);
		w_workHoursPane.add(select_date);

		JComboBox select_time = new JComboBox();
		select_time.setModel(new DefaultComboBoxModel(new String[] { "8:00", "8:30", "9:00", "9:30", "10:00", "10:30",
				"11:00", "11:30", "12:00", "12:30", "13:00", "13:30", "14:00", "14:30", "15:00", "15:30" }));
		select_time.setBounds(171, 11, 91, 22);
		w_workHoursPane.add(select_time);

		JButton btn_addWhour = new JButton("Ekle");
		btn_addWhour.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String date = "";
				try {
					date = sdf.format(select_date.getDate());
				} catch (Exception e2) {
				}
				if (date.length() == 0) {
					Helper.showMsg("Geçerli Tarih Seçimi Yapınız");
				} else {
					String time = " " + select_time.getSelectedItem().toString() + ":00";
					String selectDate = date + time;
					try {
						boolean control = doctor.addWhour(doctor.getId(), doctor.getName(), selectDate);
						if (control) {
							Helper.showMsg("success");
							updateWhourModel(doctor);
						} else {
							Helper.showMsg("error");
						}
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				}
			}
		});
		btn_addWhour.setBounds(291, 11, 89, 20);
		w_workHoursPane.add(btn_addWhour);

		JScrollPane scrollWhour = new JScrollPane();
		scrollWhour.setBounds(10, 36, 726, 247);
		w_workHoursPane.add(scrollWhour);

		table_whour = new JTable(whourModel);
		scrollWhour.setViewportView(table_whour);

		JButton btn_deleteWdate = new JButton("Sil");
		btn_deleteWdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectID = Integer
						.parseInt(table_whour.getModel().getValueAt(table_whour.getSelectedRow(), 0).toString());
				boolean control = whour.deleteWhour(selectID);
				if (control) {
					Helper.showMsg("success");
					updateWhourModel(doctor);
				} else {
					Helper.showMsg("error");
				}
			}
		});
		btn_deleteWdate.setBounds(390, 11, 89, 23);
		w_workHoursPane.add(btn_deleteWdate);

		JPanel w_randevuPane = new JPanel();
		w_DoctorTabbedPane.addTab("Randevularım", null, w_randevuPane, null);
		w_randevuPane.setLayout(null);

		JScrollPane scroll_doctorAppointments = new JScrollPane();
		scroll_doctorAppointments.setBounds(10, 11, 726, 255);
		w_randevuPane.add(scroll_doctorAppointments);

		table_doctorAppoint = new JTable(doctorappModel);
		scroll_doctorAppointments.setViewportView(table_doctorAppoint);

		JButton btn_deleteAppointment = new JButton("Sil");
		btn_deleteAppointment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (Helper.confirm("sure")) {
					int selID = Integer.parseInt(table_doctorAppoint.getModel()
							.getValueAt(table_doctorAppoint.getSelectedRow(), 0).toString());
					String date = table_doctorAppoint.getModel().getValueAt(table_doctorAppoint.getSelectedRow(), 2).toString();
					boolean control = doctor.deleteAppointment(selID);
					if (control) {
						Helper.showMsg("success");
						hasta.updateWhourStatus2(date);
						updateDoctorAppointModel(doctor.getId());
					}
				}
			}
		});
		btn_deleteAppointment.setBounds(314, 271, 89, 23);
		w_randevuPane.add(btn_deleteAppointment);
	}

	public void updateWhourModel(Doctor doctor) {
		DefaultTableModel clearModel = (DefaultTableModel) table_whour.getModel();
		clearModel.setRowCount(0);
		for (int i = 0; i < whour.getWhourList(doctor.getId()).size(); i++) {
			whourData[0] = whour.getWhourList(doctor.getId()).get(i).getId();
			whourData[1] = whour.getWhourList(doctor.getId()).get(i).getWdate();
			whourModel.addRow(whourData);
		}
	}
	public void updateDoctorAppointModel(int doctor_id) {
		DefaultTableModel clearModel = (DefaultTableModel) table_doctorAppoint.getModel();
		clearModel.setRowCount(0);
		for(int i = 0 ; i<doctor.getAppointmentList(doctor_id).size();i++) {
			doctorappData[0] = doctor.getAppointmentList(doctor_id).get(i).getId();
			doctorappData[1] = doctor.getAppointmentList(doctor_id).get(i).getHasta_name();
			doctorappData[2] = doctor.getAppointmentList(doctor_id).get(i).getApp_date();
			doctorappModel.addRow(doctorappData);
		}
	}
}
