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

public class archives_entry extends JFrame implements ActionListener{
	JLabel label_name=null;
	JLabel label_sex=null;
	JLabel label_authority=null;
	JLabel label_birthday=null;
	JLabel label_birthday_1=null;
	JLabel label_department=null;
	JLabel label_job=null;
	JLabel label_education=null;
	JLabel label_specialty=null;
	JLabel label_address=null;
	JLabel label_tel=null;
	JLabel label_email=null;
	JLabel label_status=null;
	JLabel label_status_1=null;
	JLabel label_remark=null;
	
	JTextField name=null;
	JTextField sex=null;
	JTextField authority=null;
	JTextField birthday=null;
	JTextField department=null;
	JTextField job=null;
	JTextField specialty=null;
	JTextField address=null;
	JTextField tel=null;
	JTextField email=null;
	JTextField remark=null;
	
	JButton done=null;
	JButton back=null;
	JButton con=null;
	
	JComboBox education = null;
	JComboBox status = null;
	
	private static DbProcess dbProcess;
	String SelectQueryFieldStr = "大本";
	String SelectQueryFieldStr_1 = "T";
	String PID_R;
	
	public archives_entry(){
		this.setTitle("新员工信息录入");
		this.setBounds(100, 100, 500,400);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setLayout(null);
		this.setVisible(true);
		
		name = new JTextField();
		name.setBounds(59, 25, 66, 21);
		this.getContentPane().add(name);
		name.setColumns(20);
		
		label_name = new JLabel("*姓名：");
		label_name.setBounds(23, 28, 54, 15);
		this.getContentPane().add(label_name);
		
		sex = new JTextField();
		sex.setBounds(206, 25, 66, 21);
		this.getContentPane().add(sex);
		sex.setColumns(5);
		
		label_sex = new JLabel("*性别：");
		label_sex.setBounds(164, 28, 54, 15);
		this.getContentPane().add(label_sex);
		
		authority= new JTextField();
		authority.setBounds(354, 25, 66, 21);
		this.getContentPane().add(authority);
		authority.setColumns(10);
		
		label_authority = new JLabel("*权限：");
		label_authority.setBounds(309, 28, 54, 15);
		this.getContentPane().add(label_authority);
		
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
		
		job = new JTextField();
		job.setBounds(354, 66, 66, 21);
		this.getContentPane().add(job);
		job.setColumns(12);
		
		label_job = new JLabel("*职务");
		label_job.setBounds(309, 69, 54, 15);
		this.getContentPane().add(label_job);
		
		education = new JComboBox();
		education.setModel(new DefaultComboBoxModel(new String[] {"小学", "初中", "高中", "职高", "大本", "大专", "硕士", "博士", "博士后"}));
		education.setBounds(71, 120, 66, 21);
		this.getContentPane().add(education);
		education.addItemListener(new ItemListener() {//下拉框事件监听  
            public void itemStateChanged(ItemEvent event) {  
                switch (event.getStateChange()) {  
                case ItemEvent.SELECTED:  
                	SelectQueryFieldStr = (String) event.getItem();  
                    System.out.println("选中：" + SelectQueryFieldStr);  
                    break;  
                case ItemEvent.DESELECTED:  
                    System.out.println("取消选中：" + event.getItem());  
                    break;  
                }  
            }  
        });
		
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
		
		status = new JComboBox();
		status.setModel(new DefaultComboBoxModel(new String[] {"T", "F"}));
		status.setBounds(71, 260, 54, 21);
		this.getContentPane().add(status);
		status.addItemListener(new ItemListener() {//下拉框事件监听  
            public void itemStateChanged(ItemEvent event) {  
                switch (event.getStateChange()) {  
                case ItemEvent.SELECTED:  
                	SelectQueryFieldStr_1 = (String) event.getItem();  
                    System.out.println("选中：" + SelectQueryFieldStr_1);  
                    break;  
                case ItemEvent.DESELECTED:  
                    System.out.println("取消选中：" + event.getItem());  
                    break;  
                }  
            }  
        });
		
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
		
		done = new JButton("确认");
		done.setBounds(60, 317, 115, 23);
		this.getContentPane().add(done);
		
		con = new JButton("继续录入");
		con.setBounds(190, 317, 115, 23);
		this.getContentPane().add(con);
		
		label_status_1 = new JLabel("注：“T”为员工，“F”为非员工，带“*”为必填项");
		label_status_1.setForeground(Color.RED);
		label_status_1.setBounds(10, 288, 300, 15);
		this.getContentPane().add(label_status_1);
		
		back = new JButton("返回主界面");
		back.setBounds(327, 317, 115, 23);
		this.getContentPane().add(back);
		
		label_birthday_1 = new JLabel("格式：年-月-日");
		label_birthday_1.setForeground(Color.RED);
		label_birthday_1.setBackground(Color.RED);
		label_birthday_1.setBounds(23, 95, 114, 15);
		this.getContentPane().add(label_birthday_1);
		
		done.addActionListener(this);
		back.addActionListener(this);
		con.addActionListener(this);
		
		dbProcess = new DbProcess();
	}
	
	public static void main(String[] args){
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					archives_entry en= new archives_entry();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public void actionPerformed(ActionEvent e){
		if(e.getActionCommand().equals("返回主界面")){
			PM_design window = new PM_design();
			this.setVisible(false);
		}else if(e.getActionCommand().equals("继续录入")){
			name.setText("");
			sex.setText("");
			authority.setText("");
			birthday.setText("");
			department.setText("");
			job.setText("");
			specialty.setText("");
			address.setText("");
			tel.setText("");
			email.setText("");
			remark.setText("");
		}else if(e.getActionCommand().equals("确认")
				&& !name.getText().isEmpty()
				&& !sex.getText().isEmpty()
				&& !birthday.getText().isEmpty()
				&& !authority.getText().isEmpty()
				&& !department.getText().isEmpty()
				&& !job.getText().isEmpty()
				&& !tel.getText().isEmpty()){
			System.out.println("actionPerformed(). 信息录入");
			insertProcess();
			//增添记录至调动明细表
			remove_details detailsR = new  remove_details();
			detailsR.queryAllProcess();
			detailsR.PID_E=PID_R;
			detailsR.archivesEntryProcess("0");
		}
	}
	public void insertProcess(){
		String PID=null;
		String Ppasswd=null;
		PID_R=String.valueOf(new Random().nextInt(10000));
		String Ppasswd_R=String.valueOf(new Random().nextInt(10000));
		try {
			if(!checkOut(PID_R,Ppasswd_R)){
			PID=PID_R;
			Ppasswd=Ppasswd_R;
			}
			} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			}
		String Pauthority=authority.getText().trim();
		String Pname=name.getText().trim();
		String Psex=sex.getText().trim();
		String Pbirthday=birthday.getText().trim();
		String Pdepartment=department.getText().trim();
		String Pjob=job.getText().trim();
		String Pedu_level=SelectQueryFieldStr;
		String Pspecialty=specialty.getText().trim();
		if(specialty.getText().trim()==null){
			Pspecialty=null;
		}
		String Paddress=address.getText().trim();
		if(address.getText().trim()==null){
			Paddress=null;
		}
		String Ptel=tel.getText().trim();
		String Pemail=email.getText().trim();
		if(email.getText().trim()==null){
			Pemail=null;
		}
		String Pstatus=SelectQueryFieldStr_1;
		String Premark=remark.getText().trim();
		if(remark.getText().trim()==null){
			Premark=null;
		}
		
		String sql = "insert into person values('";
		sql = sql + PID + "','";
		sql = sql + Ppasswd +  "','";
		sql = sql + Pauthority + "','";
		sql = sql + Pname + "','";
		sql = sql + Psex + "','";
		sql = sql + Pbirthday + "','";
		sql = sql + Pdepartment + "','";
		sql = sql + Pjob + "','";
		sql = sql + Pedu_level+ "','";
		sql = sql + Pspecialty + "','";
		sql = sql + Paddress + "','";
		sql = sql + Ptel + "','";
		sql = sql + Pemail + "','";
		sql = sql + Pstatus+ "','";
		sql = sql + Premark + "');";
		System.out.println(sql);
		try{
			if (dbProcess.executeUpdate(sql) < 1) {
				System.out.println("insertProcess(). insert database failed.");
			}
		}catch(Exception e){
			System.out.println("e = " + e);
			JOptionPane.showMessageDialog(null,
				"数据操作错误","错误",JOptionPane.ERROR_MESSAGE);
		}
	}
	public boolean checkOut(String userStr,String passStr) throws SQLException{
		String sql="select * from person;";
		dbProcess.connect();
		ResultSet rs = dbProcess.executeQuery(sql);
		System.out.println(rs);
		int flag=0;
		while(rs.next()){
			if(userStr.equals(rs.getString("PID"))&&passStr.equals(rs.getString("Ppasswd"))){
				flag=1;
				break;
			}
		}
		if(flag==1)
			  return true;
		return false;
	}
}
