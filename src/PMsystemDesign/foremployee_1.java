package PMsystemDesign;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;

import java.awt.BorderLayout;
import java.util.Random;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class foremployee_1 extends JFrame implements ActionListener{
	JLabel label_ID=null;
	JLabel label_name=null;
	JLabel label_sex=null;
	JLabel label_birthday=null;
	JLabel label_department=null;
	JLabel label_job=null;
	JLabel label_education=null;
	JLabel label_specialty=null;
	JLabel label_address=null;
	JLabel label_tel=null;
	JLabel label_email=null;
	JLabel label_status=null;
	JLabel label_remark=null;
	
	JTextField ID=null;
	JTextField name=null;
	JTextField sex=null;
	JTextField birthday=null;
	JTextField department=null;
	JTextField job=null;
	JTextField education=null;
	JTextField specialty=null;
	JTextField address=null;
	JTextField tel=null;
	JTextField email=null;
	JTextField status=null;
	JTextField remark=null;
	
	JButton backem=null;
	JButton quit=null;
	
	private static DbProcess dbProcess;
	
	public foremployee_1(){
		this.setTitle("详情");
		this.setBounds(100, 150, 500,400);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setLayout(null);
		this.setVisible(true);
		
		ID = new JTextField();
		ID.setBounds(59, 25, 66, 21);
		this.getContentPane().add(ID);
		ID.setColumns(10);
		
		label_ID = new JLabel("*员工号：");
		label_ID.setBounds(10, 28, 54, 15);
		this.getContentPane().add(label_ID);
		
		name = new JTextField();
		name.setBounds(206, 25, 66, 21);
		this.getContentPane().add(name);
		name.setColumns(10);
		
		label_name = new JLabel("*姓名：");
		label_name.setBounds(164, 28, 54, 15);
		this.getContentPane().add(label_name);
		
		birthday = new JTextField();
		birthday.setBounds(59, 66, 66, 21);
		this.getContentPane().add(birthday);
		birthday.setColumns(10);
		
		label_birthday = new JLabel("*生日：");
		label_birthday.setBounds(23, 69, 54, 15);
		this.getContentPane().add(label_birthday);
		
		department = new JTextField();
		department.setBounds(206, 66, 66, 21);
		this.getContentPane().add(department);
		department.setColumns(20);
		
		label_department = new JLabel("*部门：");
		label_department.setBounds(164, 69, 54, 15);
		this.getContentPane().add(label_department);
		
		sex = new JTextField();
		sex.setBounds(354, 25, 66, 21);
		this.getContentPane().add(sex);
		sex.setColumns(10);
		
		label_sex = new JLabel("*性别");
		label_sex.setBounds(309, 28, 54, 15);
		this.getContentPane().add(label_sex);
		
		job = new JTextField();
		job.setBounds(354, 66, 66, 21);
		this.getContentPane().add(job);
		job.setColumns(10);
		
		label_job = new JLabel("*职务");
		label_job.setBounds(309, 69, 54, 15);
		this.getContentPane().add(label_job);
		
		education = new JTextField();
		education.setBounds(71, 120, 66, 21);
		this.getContentPane().add(education);
		education.setColumns(10);
		
		label_education = new JLabel("*教育程度：");
		label_education.setBounds(10, 123, 67, 15);
		this.getContentPane().add(label_education);
		
		specialty = new JTextField();
		specialty.setBounds(217, 120, 203, 21);
		this.getContentPane().add(specialty);
		specialty.setColumns(100);
		
		label_specialty = new JLabel("专业技能：");
		label_specialty.setBounds(147, 123, 71, 15);
		this.getContentPane().add(label_specialty);
		
		address = new JTextField();
		address.setBounds(71, 162, 292, 21);
		this.getContentPane().add(address);
		address.setColumns(50);
		
		label_address = new JLabel("家庭住址：");
		label_address.setBounds(10, 165, 66, 15);
		this.getContentPane().add(label_address);
		
		tel = new JTextField();
		tel.setBounds(71, 207, 115, 21);
		this.getContentPane().add(tel);
		tel.setColumns(20);
		
		label_tel = new JLabel("*联系电话：");
		label_tel.setBounds(10, 210, 67, 15);
		this.getContentPane().add(label_tel);
		
		email = new JTextField();
		email.setBounds(263, 207, 123, 21);
		this.getContentPane().add(email);
		email.setColumns(20);
		
		label_email = new JLabel("电子邮件：");
		label_email.setBounds(196, 210, 76, 15);
		this.getContentPane().add(label_email);
		
		status = new JTextField();
		status.setBounds(71, 260, 54, 21);
		this.getContentPane().add(status);
		status.setColumns(4);
		
		label_status = new JLabel("*当前状态：");
		label_status.setBounds(7, 263, 70, 15);
		this.getContentPane().add(label_status);
		
		remark = new JTextField();
		remark.setBounds(196, 260, 190, 21);
		this.getContentPane().add(remark);
		remark.setColumns(50);
		
		label_remark = new JLabel("备注：");
		label_remark.setBounds(147, 263, 54, 15);
		this.getContentPane().add(label_remark);
		
		backem = new JButton("返回");
		backem.setBounds(60, 317, 115, 23);
		this.getContentPane().add(backem);
		
		quit= new JButton("退出");
		quit.setBounds(190, 317, 115, 23);
		this.getContentPane().add(quit);
		
		backem.addActionListener(this);
		quit.addActionListener(this);
		
		dbProcess = new DbProcess();
	}
	
	public void actionPerformed(ActionEvent e){
		if(e.getActionCommand().equals("退出")){
			System.out.println("系统将退出");
			System.exit(0);
		}else{
			foremployee foremployee = new foremployee();
			this.setVisible(false);
		}
	}
	
	public static void main(String[] args){
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					foremployee_1 details=new foremployee_1();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
}
