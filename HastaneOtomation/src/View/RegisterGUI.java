package View;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Helper.Helper;
import Model.Hasta;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class RegisterGUI extends JFrame {

	private JPanel w_pane;
	private JTextField fld_registerHastaName;
	private JTextField fld_registerHastaTC;
	private JPasswordField fld_registerHastaPass;
	Hasta hasta = new Hasta();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegisterGUI frame = new RegisterGUI();
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
	public RegisterGUI() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 372, 313);
		w_pane = new JPanel();
		w_pane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(w_pane);
		w_pane.setLayout(null);
		
		JButton btn_registerHasta = new JButton("Kayıt Ol");
		btn_registerHasta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(fld_registerHastaName.getText().length()==0||fld_registerHastaPass.getText().length()==0||fld_registerHastaTC.getText().length()==0) {
					Helper.showMsg("fill");
				}else {
					boolean control = hasta.addHasta(fld_registerHastaName.getText(), fld_registerHastaTC.getText(), fld_registerHastaPass.getText());
					if(control) {
						Helper.showMsg("success");
						dispose();
						LoginGUI lGUI = new LoginGUI();
						lGUI.setVisible(true);
						
					}else {
						Helper.showMsg("error");
					}
				}
			}
		});
		btn_registerHasta.setBounds(96, 203, 152, 29);
		w_pane.add(btn_registerHasta);
		
		JLabel lblNewLabel = new JLabel("Ad Soyad");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(131, 31, 83, 29);
		w_pane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("TC No");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(113, 93, 101, 19);
		w_pane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Parola");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setBounds(125, 147, 89, 14);
		w_pane.add(lblNewLabel_2);
		
		fld_registerHastaName = new JTextField();
		fld_registerHastaName.setBounds(96, 63, 152, 19);
		w_pane.add(fld_registerHastaName);
		fld_registerHastaName.setColumns(10);
		
		fld_registerHastaTC = new JTextField();
		fld_registerHastaTC.setBounds(96, 116, 152, 20);
		w_pane.add(fld_registerHastaTC);
		fld_registerHastaTC.setColumns(10);
		
		fld_registerHastaPass = new JPasswordField();
		fld_registerHastaPass.setBounds(96, 168, 152, 20);
		w_pane.add(fld_registerHastaPass);
		
		JButton btnNewButton = new JButton("Giriş Yap");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginGUI LGUI = new LoginGUI();
				LGUI.setVisible(true);
				dispose();
			}
		});
		btnNewButton.setBounds(96, 233, 152, 29);
		w_pane.add(btnNewButton);
	}
}
