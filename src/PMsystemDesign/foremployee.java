package PMsystemDesign;

import java.awt.EventQueue;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;

import java.awt.BorderLayout;
import java.util.Random;
import java.util.Vector;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;


public class foremployee extends JFrame implements ActionListener{
	JButton query=null;
	
	private static DbProcess dbProcess;
	String PID_R;
	public foremployee() {
		this.setTitle("个人信息自助查询");
		this.setBounds(100, 150, 500,400);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setLayout(null);
		this.setVisible(true);
		
		query = new JButton("信息查询");
		query.setBounds(36, 54, 93, 31);
		this.getContentPane().add(query);
		
		query.addActionListener(this);
		
		dbProcess = new DbProcess();
	}
	
	public void actionPerformed(ActionEvent e){
		query.addMouseListener(new MouseAdapter()
		{ 
			public void mouseClicked(MouseEvent e) { 
				try {
					 	getInformation();
					} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					}
			}
		});
	}
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					foremployee foremployee = new foremployee();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public void getInformation() throws SQLException{
		String sql = "select * from person where PID='"+PID_R+"'";
		System.out.println(sql);
		dbProcess.connect();
		ResultSet rs = dbProcess.executeQuery(sql);
		foremployee_1 details=new foremployee_1();
		while(rs.next()){
			details.ID.setText(rs.getString("PID"));
			details.name.setText(rs.getString("Pname"));
			details.sex.setText(rs.getString("Psex"));
			details.birthday.setText(rs.getString("Pbirthday"));
			details.department.setText(rs.getString("Pdepartment"));
			details.job.setText(rs.getString("Pjob"));
			details.education.setText(rs.getString("Pedu_level"));
			details.specialty.setText(rs.getString("Pspecialty"));
			details.address.setText(rs.getString("Paddress"));
			details.tel.setText(rs.getString("Ptel"));
			details.email.setText(rs.getString("Pemail"));
			details.status.setText(rs.getString("Pstatus"));
			details.remark.setText(rs.getString("Premark"));
		}
	
	}
}
