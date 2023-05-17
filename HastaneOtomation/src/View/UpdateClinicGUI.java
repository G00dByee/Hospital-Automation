package View;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.View;

import Helper.Helper;
import Model.Clinic;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class UpdateClinicGUI extends JFrame {

	private JPanel contentPane;
	private JTextField fld_addClinicName;
	private static Clinic clinic = new Clinic();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UpdateClinicGUI frame = new UpdateClinicGUI(clinic);
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
	public UpdateClinicGUI(Clinic clinic) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 300, 284);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		fld_addClinicName = new JTextField();
		fld_addClinicName.setFont(new Font("Tahoma", Font.PLAIN, 14));
		fld_addClinicName.setBounds(33, 75, 219, 36);
		fld_addClinicName.setColumns(10);
		fld_addClinicName.setText(clinic.getName());
		contentPane.add(fld_addClinicName);
		
		JLabel Poliklinik = new JLabel("Poliklinik Adı");
		Poliklinik.setFont(new Font("Tahoma", Font.PLAIN, 15));
		Poliklinik.setBounds(70, 28, 145, 36);
		Poliklinik.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(Poliklinik);
		
		JButton btn_addClinic = new JButton("Düzenle");
		btn_addClinic.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(Helper.confirm("sure")) {
				 clinic.updateClinic(clinic.getId(),fld_addClinicName.getText());
				Helper.showMsg("success");
				dispose();
				
				}
			}
		});
		btn_addClinic.setBounds(75, 136, 140, 41);
		contentPane.add(btn_addClinic);
	}

}
