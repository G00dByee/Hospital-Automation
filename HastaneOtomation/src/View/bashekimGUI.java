package View;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import Helper.Helper;
import Helper.Item;
import Model.Bashekim;
import Model.Clinic;

import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Point;
import java.sql.SQLException;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.ActionEvent;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;

public class bashekimGUI extends JFrame {

	static Bashekim bashekim = new Bashekim();
	private JPanel w_pane;
	private JTable table_doctor;
	DefaultTableModel doctorModel = null;
	Object[] doctorData = null;
	private JTable table_clinic;
	private JTextField fld_addClinicName;
	private JTextField fld_addDoctorName;
	private JTextField fld_addDoctorTC;
	private JTextField fld_addDoctorpass;
	private JTextField fld_DoctorID;
	DefaultTableModel clinicModel = null;
	Object[] clinicData = null;
	Clinic clinic = new Clinic();
	private JPopupMenu clinicMenu= null;
	private JTable table_worker;
	private JTable table_allAppointments;
	private DefaultTableModel allAppointModel;
	private Object[] allAppointData;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					bashekimGUI frame = new bashekimGUI(bashekim);
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
	public bashekimGUI(Bashekim bashekim) throws SQLException {
		setResizable(false);
		setTitle("Hastane Yönetim Sistemi");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 777, 427);

		allAppointModel = new DefaultTableModel();
		Object[] colName = new Object[4];
		colName[0] = "İD";
		colName[1] = "Doktor";
		colName[2] = "Hasta";
		colName[3] = "Tarih";
		allAppointModel.setColumnIdentifiers(colName);
		allAppointData= new Object[4];
		for(int i = 0; i<bashekim.getAppointmentLİst().size();i++) {
			allAppointData[0] = bashekim.getAppointmentLİst().get(i).getId();
			allAppointData[1] = bashekim.getAppointmentLİst().get(i).getDoctor_name();
			allAppointData[2] = bashekim.getAppointmentLİst().get(i).getHasta_name();
			allAppointData[3] = bashekim.getAppointmentLİst().get(i).getApp_date();
			allAppointModel.addRow(allAppointData);
		}
		
		
		doctorModel = new DefaultTableModel();
		Object[] colDoctorName = new Object[4];
		colDoctorName[0] = "İD";
		colDoctorName[1] = "TC";
		colDoctorName[2] = "Şifre";
		colDoctorName[3] = "Ad Soyad";
		doctorModel.setColumnIdentifiers(colDoctorName);
		doctorData = new Object[4];
		for (int i = 0; i < bashekim.getDoctorList().size(); i++) {
			doctorData[0] = bashekim.getDoctorList().get(i).getId();
			doctorData[1] = bashekim.getDoctorList().get(i).getTcno();
			doctorData[2] = bashekim.getDoctorList().get(i).getPassword();
			doctorData[3] = bashekim.getDoctorList().get(i).getName();
			doctorModel.addRow(doctorData);
		}

		clinicModel = new DefaultTableModel();
		Object[] colClinicName = new Object[2];
		colClinicName[0] = "İD";
		colClinicName[1] = "Poliklinik Adı";
		clinicModel.setColumnIdentifiers(colClinicName);
		clinicData = new Object[2];
		for (int i = 0; i < clinic.getClinicList().size(); i++) {
			clinicData[0] = clinic.getClinicList().get(i).getId();
			clinicData[1] = clinic.getClinicList().get(i).getName();
			clinicModel.addRow(clinicData);
		}

		
		// Worker Model
		DefaultTableModel workerModel= new DefaultTableModel();
		Object[] workerData = new Object[2];
		Object[] colWorkerName= new Object[2];
		colWorkerName[0]= "ID";
		colWorkerName[1]="Doktor İsmi";
		workerModel.setColumnIdentifiers(colWorkerName);

		
		
		
		w_pane = new JPanel();
		w_pane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(w_pane);
		w_pane.setLayout(null);

		JLabel lblNewLabel_4 = new JLabel("Hoş Geldiniz Sayın, " + bashekim.getName());
		lblNewLabel_4.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_4.setBounds(21, 22, 193, 20);
		w_pane.add(lblNewLabel_4);

		JButton btn_bashekimExit = new JButton("ÇIKIŞ");
		btn_bashekimExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginGUI LGUI = new LoginGUI();
				LGUI.setVisible(true);
				dispose();
			}
		});
		btn_bashekimExit.setBounds(645, 23, 106, 23);
		w_pane.add(btn_bashekimExit);

		JTabbedPane w_ClinicTabPane = new JTabbedPane(JTabbedPane.TOP);
		w_ClinicTabPane.setBounds(10, 53, 751, 324);
		w_pane.add(w_ClinicTabPane);

		JPanel w_DoktorYönetimiPane = new JPanel();
		w_ClinicTabPane.addTab("Doktor Yönetimi", null, w_DoktorYönetimiPane, null);
		w_DoktorYönetimiPane.setLayout(null);

		JScrollPane w_scrollDoctor = new JScrollPane();
		w_scrollDoctor.setBounds(10, 0, 548, 299);
		w_DoktorYönetimiPane.add(w_scrollDoctor);

		table_doctor = new JTable(doctorModel);
		w_scrollDoctor.setViewportView(table_doctor);

		fld_addDoctorName = new JTextField();
		fld_addDoctorName.setColumns(10);
		fld_addDoctorName.setBounds(604, 43, 113, 20);
		w_DoktorYönetimiPane.add(fld_addDoctorName);

		JLabel lblNewLabel = new JLabel("Ad Soyad");
		lblNewLabel.setBounds(632, 18, 84, 14);
		w_DoktorYönetimiPane.add(lblNewLabel);

		fld_addDoctorTC = new JTextField();
		fld_addDoctorTC.setColumns(10);
		fld_addDoctorTC.setBounds(604, 99, 113, 20);
		w_DoktorYönetimiPane.add(fld_addDoctorTC);

		JLabel lblNewLabel_1 = new JLabel("TC No");
		lblNewLabel_1.setBounds(633, 74, 84, 14);
		w_DoktorYönetimiPane.add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("Parola");
		lblNewLabel_2.setBounds(632, 130, 113, 14);
		w_DoktorYönetimiPane.add(lblNewLabel_2);

		fld_addDoctorpass = new JTextField();
		fld_addDoctorpass.setColumns(10);
		fld_addDoctorpass.setBounds(604, 155, 113, 20);
		w_DoktorYönetimiPane.add(fld_addDoctorpass);

		JButton bt_addDoctor = new JButton("Ekle");
		bt_addDoctor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (fld_addDoctorName.getText().length() == 0 || fld_addDoctorpass.getText().length() == 0
						|| fld_addDoctorTC.getText().length() == 0) {
					Helper.showMsg("fill");
				} else {
					boolean control = bashekim.addDoctor(fld_addDoctorName.getText(), fld_addDoctorTC.getText(),
							fld_addDoctorpass.getText());
					if (control) {
						Helper.showMsg("success");
						try {
							updateDoctorModel();
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					}
				}
				
			}
		});
		bt_addDoctor.setBounds(604, 186, 113, 23);
		w_DoktorYönetimiPane.add(bt_addDoctor);

		JLabel lblNewLabel_3 = new JLabel("Kullanıcı İD");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_3.setBounds(614, 230, 109, 14);
		w_DoktorYönetimiPane.add(lblNewLabel_3);

		fld_DoctorID = new JTextField();
		fld_DoctorID.setEditable(false);
		fld_DoctorID.setColumns(10);
		fld_DoctorID.setBounds(604, 245, 113, 20);
		w_DoktorYönetimiPane.add(fld_DoctorID);

		JButton btn_doctorDelete = new JButton("Sil");
		btn_doctorDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (fld_DoctorID.getText().length() == 0) {
					Helper.showMsg("Kullanıcı Seçtiğinizden Emin Olun!");
				} else {
					if (Helper.confirm("sure")) {
						boolean control = bashekim.deleteDoctor(Integer.parseInt(fld_DoctorID.getText()));
						if (control) {
							Helper.showMsg("success");
							try {
								updateDoctorModel();
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
					}
				}
			}
		});
		btn_doctorDelete.setBounds(604, 276, 113, 23);
		w_DoktorYönetimiPane.add(btn_doctorDelete);
		table_doctor.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				try {
					fld_DoctorID.setText(table_doctor.getValueAt(table_doctor.getSelectedRow(), 0).toString());
				} catch (Exception e2) {
				}
			}
		});
		table_doctor.getModel().addTableModelListener(new TableModelListener() {

			@Override
			public void tableChanged(TableModelEvent e) {
				if (e.getType() == TableModelEvent.UPDATE) {
					try {
						int selectID = Integer
								.parseInt(table_doctor.getValueAt(table_doctor.getSelectedRow(), 0).toString());
						String selectTC = table_doctor.getValueAt(table_doctor.getSelectedRow(), 1).toString();
						String selectPass = table_doctor.getValueAt(table_doctor.getSelectedRow(), 2).toString();
						String selectName = table_doctor.getValueAt(table_doctor.getSelectedRow(), 3).toString();
						boolean control = bashekim.updateDoctor(selectID, selectTC, selectPass, selectName);
						if (control) {
							Helper.showMsg("success");
						}
					} catch (Exception e3) {
					}
				}
			}
		});

		JPanel w_ClinicPane = new JPanel();
		w_ClinicTabPane.addTab("Poliklinikler", null, w_ClinicPane, null);
		w_ClinicPane.setLayout(null);

		JScrollPane scroll_clinic = new JScrollPane();
		scroll_clinic.setBounds(10, 11, 252, 263);
		w_ClinicPane.add(scroll_clinic);
		
		clinicMenu = new JPopupMenu();
		JMenuItem updateMenu = new JMenuItem("Düzenle");
		JMenuItem deleteMenu = new JMenuItem("Sil");
		
		clinicMenu.add(updateMenu);
		clinicMenu.add(deleteMenu);
		
		updateMenu.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int selID = Integer.parseInt(table_clinic.getValueAt(table_clinic.getSelectedRow(), 0).toString());
				Clinic selectClinic = clinic.getFetch(selID); 
				UpdateClinicGUI updateGUI = new UpdateClinicGUI(selectClinic);
				updateGUI.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				updateGUI.setVisible(true);
				updateGUI.addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosed(WindowEvent e) {
					try {
						updateClinicModel();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				});
			}
		});

		table_clinic = new JTable(clinicModel);
		table_clinic.setComponentPopupMenu(clinicMenu);
		
		
		table_clinic.addMouseListener(new MouseAdapter() {
		
		@Override
		public void mousePressed(MouseEvent e) {
			Point point = e.getPoint();
			int selectedRow = table_clinic.rowAtPoint(point);
			table_clinic.setRowSelectionInterval(selectedRow, selectedRow);				
		}           
		});
		
		deleteMenu.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(Helper.confirm("sure")) {
				int selID= Integer.parseInt(table_clinic.getValueAt(table_clinic.getSelectedRow(), 0).toString());
				boolean selectClinic = clinic.deleteClinic(selID);
				UpdateClinicGUI updateGUI = new UpdateClinicGUI(clinic);
				updateGUI.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				if(selectClinic) {
					Helper.showMsg("success");
					try {
						updateClinicModel();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
			}
				
		}}});
		scroll_clinic.setViewportView(table_clinic);

		JScrollPane scroll_clinicDoctorS = new JScrollPane();
		scroll_clinicDoctorS.setBounds(475, 11, 261, 263);
		w_ClinicPane.add(scroll_clinicDoctorS);
		
		table_worker = new JTable();
		scroll_clinicDoctorS.setViewportView(table_worker);

		fld_addClinicName = new JTextField();
		fld_addClinicName.setBounds(294, 52, 146, 20);
		fld_addClinicName.setColumns(10);
		w_ClinicPane.add(fld_addClinicName);

		JLabel Poliklinik = new JLabel("Poliklinik Adı");
		Poliklinik.setBounds(312, 34, 106, 14);
		Poliklinik.setHorizontalAlignment(SwingConstants.CENTER);
		w_ClinicPane.add(Poliklinik);

		JButton btn_addClinic = new JButton("Ekle");
		btn_addClinic.setBounds(304, 83, 122, 29);
		btn_addClinic.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (fld_addClinicName.getText().length() == 0) {
					Helper.showMsg("fill");
				} else {
					boolean control = clinic.addClinic(fld_addClinicName.getText());
					if (control) {
						Helper.showMsg("success");
						fld_addClinicName.setText(null);
						try {
							updateClinicModel();
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					}	
				}
			}
		});
		w_ClinicPane.add(btn_addClinic);
		
		JComboBox select_doctor = new JComboBox();
		select_doctor.setBounds(294, 217, 146, 29);
		for(int i = 0; i<bashekim.getDoctorList().size();i++) {
			select_doctor.addItem(new Item(bashekim.getDoctorList().get(i).getId(),bashekim.getDoctorList().get(i).getName()));			
		}
		select_doctor.addActionListener(e -> {
			JComboBox c = (JComboBox) e.getSource();
			Item item = (Item) c.getSelectedItem();
		});
		w_ClinicPane.add(select_doctor);
		
		JButton btn_addWorker = new JButton("Ekle");
		btn_addWorker.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selRow = table_clinic.getSelectedRow();
				if(selRow >=0 ) {
					String selClinic =table_clinic.getModel().getValueAt(selRow, 0).toString();
					int selClinicID =Integer.parseInt(selClinic);
					Item doctorItem = (Item) select_doctor.getSelectedItem();
					boolean control = bashekim.addWorker(doctorItem.getKey(), selClinicID);
					if(control) {
						Helper.showMsg("success");
						DefaultTableModel clearModel = (DefaultTableModel) table_worker.getModel();
						clearModel.setRowCount(0);
						
						try {
							for(int i =0; i< bashekim.getClinicDoctorList(selClinicID).size();i++) {
								workerData[0]=bashekim.getClinicDoctorList(selClinicID).get(i).getId();
								workerData[1]=bashekim.getClinicDoctorList(selClinicID).get(i).getName();
								workerModel.addRow(workerData);
							}
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					}else {
						Helper.showMsg("error");
					}
				}else {
					Helper.showMsg("Lütfen Bir Poliklinik Seçiniz");
				}
				table_worker.setModel(workerModel);
			}
		});
		btn_addWorker.setBounds(312, 258, 106, 27);
		w_ClinicPane.add(btn_addWorker);
		
		JButton btn_WorkerSelect = new JButton("Seç");
		btn_WorkerSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selRow = table_clinic.getSelectedRow();
				if(selRow >=0) {
					String selClinic = table_clinic.getModel().getValueAt(selRow, 0).toString();
					int selClinicID = Integer.parseInt(selClinic);
					DefaultTableModel clearModel = (DefaultTableModel) table_worker.getModel();
					clearModel.setRowCount(0);
					
					try {
						for(int i =0; i< bashekim.getClinicDoctorList(selClinicID).size();i++) {
							workerData[0]=bashekim.getClinicDoctorList(selClinicID).get(i).getId();
							workerData[1]=bashekim.getClinicDoctorList(selClinicID).get(i).getName();
							workerModel.addRow(workerData);
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}else {
					Helper.showMsg("Lütfen Bir Poliklinik Seçiniz");
				}
				table_worker.setModel(workerModel);
			}
		});
		btn_WorkerSelect.setBounds(312, 159, 122, 29);
		w_ClinicPane.add(btn_WorkerSelect);
		
		JPanel w_panelAllAppointments = new JPanel();
		w_ClinicTabPane.addTab("Tüm Randevular", null, w_panelAllAppointments, null);
		w_panelAllAppointments.setLayout(null);
		
		JScrollPane scroll_allAppointments = new JScrollPane();
		scroll_allAppointments.setBounds(10, 11, 726, 248);
		w_panelAllAppointments.add(scroll_allAppointments);
		
		table_allAppointments = new JTable(allAppointModel);
		scroll_allAppointments.setViewportView(table_allAppointments);
		table_allAppointments.getColumnModel().getColumn(0).setPreferredWidth(5);
	
		
		JButton btn_deleteAppoinment = new JButton("Sil");
		btn_deleteAppoinment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table_allAppointments.getSelectedRow();
		if(row >=0) {
			int selID = Integer.parseInt(table_allAppointments.getModel().getValueAt(row, 0).toString());
			String date = table_allAppointments.getModel().getValueAt(table_allAppointments.getSelectedRow(),3).toString();
			boolean control = bashekim.deleteAppointment(selID);
			if(control) {
				Helper.showMsg("success");
				bashekim.updateWhourStatus2(date);
				try {
					updateAllAppointModel();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}else {
				Helper.showMsg("error");
			}
		}else {
			Helper.showMsg("error");
		}
			}
		});
		btn_deleteAppoinment.setBounds(324, 262, 89, 23);
		w_panelAllAppointments.add(btn_deleteAppoinment);
	}

	public void updateDoctorModel() throws SQLException {
		DefaultTableModel clearModel = (DefaultTableModel) table_doctor.getModel();
		clearModel.setRowCount(0);
		for (int i = 0; i < bashekim.getDoctorList().size(); i++) {
			doctorData[0] = bashekim.getDoctorList().get(i).getId();
			doctorData[1] = bashekim.getDoctorList().get(i).getTcno();
			doctorData[2] = bashekim.getDoctorList().get(i).getPassword();
			doctorData[3] = bashekim.getDoctorList().get(i).getName();
			doctorModel.addRow(doctorData);
		}
	}

	public void updateClinicModel() throws SQLException {
		DefaultTableModel clearModel = (DefaultTableModel) table_clinic.getModel();
		clearModel.setRowCount(0);
		for (int i = 0; i < clinic.getClinicList().size(); i++) {
			clinicData[0] = clinic.getClinicList().get(i).getId();
			clinicData[1] = clinic.getClinicList().get(i).getName();
			clinicModel.addRow(clinicData);
		}
	}
	public void updateAllAppointModel() throws SQLException {
		DefaultTableModel clearModel = (DefaultTableModel) table_allAppointments.getModel();
		clearModel.setRowCount(0);
		for(int i = 0; i<bashekim.getAppointmentLİst().size();i++) {
			allAppointData[0] = bashekim.getAppointmentLİst().get(i).getId();
			allAppointData[1] = bashekim.getAppointmentLİst().get(i).getDoctor_name();
			allAppointData[2] = bashekim.getAppointmentLİst().get(i).getHasta_name();
			allAppointData[3] = bashekim.getAppointmentLİst().get(i).getApp_date();
			allAppointModel.addRow(allAppointData);
		}
	}
}
