package PMsystemDesign;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTabbedPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
import java.awt.Color;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;
import javax.swing.border.TitledBorder;
import javax.swing.UIManager;

public class archivesM extends JFrame implements ActionListener{
	JLabel label = null;
	JLabel label_same = null;
	JLabel label_name=null;
	JLabel label_sex=null;
	JLabel label_birthday=null;
	//JLabel label_birthday_1=null;
	JLabel label_department=null;
	JLabel label_job=null;
	JLabel label_education=null;
	//JLabel label_education_1= null;
	JLabel label_specialty=null;
	JLabel label_address=null;
	JLabel label_tel=null;
	JLabel label_email=null;
	JLabel label_status=null;
	//JLabel label_status_1=null;
	JLabel label_remark=null;
	
	JTextField fillinField = null;
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
	
	JButton button_alter=null;
	JButton button_query = null;
	JButton button_queryall = null;
	JButton button_delete = null;
	JButton button_deleteall = null;
	JButton back = null;
	
	JPanel jP1, jP2,jP3,jP4,jP5,jP6= null;
	JPanel jPTop, jPBottom = null;
	DefaultTableModel employeeTableModel = null;
	JTable employeeJTable = null;
	JScrollPane employeeJScrollPane = null;
	Vector employeeVector = null;
	Vector titleVector = null;

	JComboBox selectBox = null;
	
	private static DbProcess dbProcess;
	String SQFieldStr = "员工号";
	
	public archivesM(){
		this.setTitle("档案管理");
		this.setBounds(200, 30, 850,650);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setLayout(new GridLayout(2, 1));
		this.setVisible(true);
		
		label = new JLabel("员工信息表");
		label_same = new JLabel(" =");
		label_name=new JLabel("*姓名：");
		label_sex=new JLabel("*性别：");
		label_birthday=new JLabel("*生日：");
		//label_birthday_1=new JLabel("格式：年-月-日");
		label_department=new JLabel("*部门：");
		label_job=new JLabel("*职务：");
		label_education=new JLabel("*教育程度：");
		//label_education_1= new JLabel("注：小学, 初中, 高中, 职高, 大本, 大专, 硕士, 博士, 博士后");
		label_specialty=new JLabel("专业技能：");
		label_address=new JLabel("家庭住址：");
		label_tel=new JLabel("*联系电话：");
		label_email=new JLabel("电子邮件：");
		label_status=new JLabel("*当前状态：");
		//label_status_1=new JLabel("注：“T”为员工，“F”为非员工，带“*”为必填项");
		label_remark=new JLabel("备注：");
		
		fillinField = new JTextField(16);
		name=new JTextField(20);
		sex=new JTextField(2);
		birthday=new JTextField(10);
		department=new JTextField(20);
		job=new JTextField(10);
		education=new JTextField(8);
		specialty=new JTextField(35);
		address=new JTextField(40);
		tel=new JTextField(15);
		email=new JTextField(20);
		status=new JTextField(2);
		remark=new JTextField(30);
		
		button_query = new JButton("查询");
		button_queryall = new JButton("查询所有记录");
		button_alter = new JButton("修改信息");
		button_delete = new JButton("删除当前记录");
		button_deleteall = new JButton("删除所有记录");
		back = new JButton("返回主界面");
		
		button_query.addActionListener(this);
		button_queryall.addActionListener(this);
		button_alter.addActionListener(this);
		button_delete.addActionListener(this);
		button_deleteall.addActionListener(this);
		back.addActionListener(this);
		fillinField.addKeyListener(new java.awt.event.KeyAdapter(){
			public void keyTyped(java.awt.event.KeyEvent e){
				if(e.getKeyChar()=='\n'){
					button_query.doClick();
				}
			}
		});
		
		selectBox = new JComboBox();
		selectBox.setModel(new DefaultComboBoxModel(new String[] {"员工号","姓名", "性别", "生日", "部门", "职务", "教育程度", "专业技能", "家庭住址", "联系电话", "电子邮件", "当前状态", "备注"}));
		selectBox.addItemListener(new ItemListener() {//下拉框事件监听  
            public void itemStateChanged(ItemEvent event) {  
                switch (event.getStateChange()) {  
                case ItemEvent.SELECTED:  
                	SQFieldStr = (String) event.getItem();  
                    System.out.println("选中：" + SQFieldStr);  
                    break;  
                case ItemEvent.DESELECTED:  
                    System.out.println("取消选中：" + event.getItem());  
                    break;  
                }  
            }  
        });
		
		employeeVector = new Vector();
		titleVector = new Vector();
		titleVector.add("员工号");
		titleVector.add("姓名");
		titleVector.add("性别");
		titleVector.add("生日");
		titleVector.add("部门");
		titleVector.add("职务");
		titleVector.add("教育程度");
		titleVector.add("专业技能");
		titleVector.add("家庭住址");
		titleVector.add("联系电话");
		titleVector.add("电子邮件");
		titleVector.add("当前状态");
		titleVector.add("备注");
		employeeJTable = new JTable(employeeVector, titleVector);
		employeeJTable.setPreferredScrollableViewportSize(new Dimension(800,230));
	    employeeJScrollPane = new JScrollPane(employeeJTable);
		//分别设置水平和垂直滚动条自动出现
		employeeJScrollPane.setHorizontalScrollBarPolicy(                
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		employeeJScrollPane.setVerticalScrollBarPolicy(                
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		employeeJTable.addMouseListener(new MouseAdapter()
		{ 
			public void mouseClicked(MouseEvent e) 
			{ 
				int row = ((JTable) e.getSource()).rowAtPoint(e.getPoint()); // 获得行位置
				System.out.println("mouseClicked(). row = " + row);
				Vector v = new Vector();
				v = (Vector)employeeVector.get(row);
				name.setText((String) v.get(1));
				sex.setText((String) v.get(2));
				birthday.setText((String) v.get(3));
				department.setText((String) v.get(4));
				job.setText((String) v.get(5));
				education.setText((String) v.get(6));
				specialty.setText((String) v.get(7));
				address.setText((String) v.get(8));
				tel.setText((String) v.get(9));
				email.setText((String) v.get(10));
				status.setText((String) v.get(11));
				remark.setText((String) v.get(12));
			}
		});
		jP1 = new JPanel();
		jP2 = new JPanel();
		jP3 = new JPanel();
		jP4 = new JPanel();
		jP5 = new JPanel();
		jP6 = new JPanel();
		jPTop = new JPanel();
		jPBottom = new JPanel();
		
		jP1.add(label,BorderLayout.SOUTH);
		jP2.add(employeeJScrollPane);
		
		jP3.add(selectBox);
		jP3.add(label_same);
		jP3.add(fillinField);
		jP3.add(button_query);
		jP3.add(button_queryall);
		jP3.setLayout(new FlowLayout(FlowLayout.LEFT));
		jP3.setPreferredSize(new Dimension(10,10));
		
		jP4.add(label_name);
		jP4.add(name);
		jP4.add(label_sex);
		jP4.add(sex);
		jP4.add(label_birthday);
		jP4.add(birthday);
		jP4.add(label_department);
		jP4.add(department);
		jP4.add(label_job);
		jP4.add(job);
		jP4.add(label_education);
		jP4.add(education);
		jP4.add(label_specialty);
		jP4.add(specialty);
		jP4.setLayout(new FlowLayout(FlowLayout.LEFT));
		jP4.setPreferredSize(new Dimension(20,20));
		
		jP5.add(label_address);
		jP5.add(address);
		jP5.add(label_tel);
		jP5.add(tel);
		jP5.add(label_email);
		jP5.add(email);
		jP5.add(label_status);
		jP5.add(status);
		jP5.add(label_remark);
		jP5.add(remark);
		jP5.setLayout(new FlowLayout(FlowLayout.LEFT));
		jP5.setPreferredSize(new Dimension(20,20));
		
		jP6.add(button_alter);
		jP6.add(button_delete);
		jP6.add(button_deleteall);
		jP6.add(back);
		jP6.setLayout(new FlowLayout(FlowLayout.LEFT));
		jP6.setPreferredSize(new Dimension(20,20));
		
		jPTop.add(jP1);
		jPTop.add(jP2);
		
		jPBottom.setLayout(new GridLayout(4, 1));
		jPBottom.add(jP3);
		jPBottom.add(jP4);
		jPBottom.add(jP5);
		jPBottom.add(jP6);
		
		this.add("North", jPTop);
		this.add("South", jPBottom);
		
		dbProcess = new DbProcess();
	}
	
	public static void main(String[] args){
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					archivesM archivesM= new archivesM();
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
		}else if(e.getActionCommand().equals("查询")
				&&!fillinField.getText().isEmpty()){
			System.out.println("actionPerformed(). 查询");
			String sQueryField = fillinField.getText();
			System.out.println(sQueryField);
			queryProcess(sQueryField);
			fillinField.setText("");
		}else if(e.getActionCommand().equals("查询所有记录")) {
			System.out.println("actionPerformed(). 查询所有记录");
			queryAllProcess();
		}else if(e.getActionCommand().equals("修改信息")
				&& !education.getText().isEmpty()
				&& !birthday.getText().isEmpty()
				&& !name.getText().isEmpty()
				&& !sex.getText().isEmpty()
				&& !department.getText().isEmpty()
				&& !job.getText().isEmpty()
				&& !status.getText().isEmpty()
				&& !tel.getText().isEmpty()){
			System.out.println("actionPerformed(). 信息修改");
			updateProcess();
		}else if(e.getActionCommand().equals("删除当前记录")){
			System.out.println("actionPerformed(). 删除当前记录");
			deleteCurrentRecordProcess();
		}else if(e.getActionCommand().equals("删除所有记录")){
			tip tip=new tip();
		}
	}
	public void queryProcess(String sQueryField)
	{
		try{
			// 建立查询条件
			String sql = "select *  from person where ";
			String  queryFieldStr= jCBSelectQueryFieldTransfer(SQFieldStr);
			sql = sql + queryFieldStr;
			sql = sql + " = ";
			sql = sql + "'" + sQueryField + "';";
			System.out.println("queryProcess(). sql = " + sql);
			dbProcess.connect();
			ResultSet rs = dbProcess.executeQuery(sql);
			// 将查询获得的记录数据，转换成适合生成JTable的数据形式
			employeeVector.clear();
			while(rs.next()){
				Vector v = new Vector();
				v.add(rs.getString("PID"));
				v.add(rs.getString("Pname"));
				v.add(rs.getString("Psex"));
				v.add(rs.getString("Pbirthday"));
				v.add(rs.getString("Pdepartment"));
				v.add(rs.getString("Pjob"));
				v.add(rs.getString("Pedu_level"));
				v.add(rs.getString("Pspecialty"));
				v.add(rs.getString("Paddress"));
				v.add(rs.getString("Ptel"));
				v.add(rs.getString("Pemail"));
				v.add(rs.getString("Pstatus"));
				v.add(rs.getString("Premark"));
				employeeVector.add(v);
			}
			
			employeeJTable.updateUI();

			dbProcess.disconnect();
		}catch(SQLException sqle){
			System.out.println("sqle = " + sqle);
			JOptionPane.showMessageDialog(null,
				"数据操作错误","错误",JOptionPane.ERROR_MESSAGE);
		}catch(Exception e){
			System.out.println("e = " + e);
			JOptionPane.showMessageDialog(null,
				"数据操作错误","错误",JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void queryAllProcess()
	{
		try{
			// 建立查询条件
			String sql = "select * from person;";
			System.out.println("queryAllProcess(). sql = " + sql);
			dbProcess.connect();
			ResultSet rs = dbProcess.executeQuery(sql);

			// 将查询获得的记录数据，转换成适合生成JTable的数据形式
			employeeVector.clear();
			while(rs.next()){
				Vector v = new Vector();
				v.add(rs.getString("PID"));
				v.add(rs.getString("Pname"));
				v.add(rs.getString("Psex"));
				v.add(rs.getString("Pbirthday"));
				v.add(rs.getString("Pdepartment"));
				v.add(rs.getString("Pjob"));
				v.add(rs.getString("Pedu_level"));
				v.add(rs.getString("Pspecialty"));
				v.add(rs.getString("Paddress"));
				v.add(rs.getString("Ptel"));
				v.add(rs.getString("Pemail"));
				v.add(rs.getString("Pstatus"));
				v.add(rs.getString("Premark"));
				employeeVector.add(v);
			}
			
			employeeJTable.updateUI();

			dbProcess.disconnect();
		}catch(SQLException sqle){
			System.out.println("sqle = " + sqle);
			JOptionPane.showMessageDialog(null,
				"数据操作错误","错误",JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void updateProcess()
	{
		int count=employeeJTable.getSelectedRow();//获取你选中的行号（记录）  
		String PID= employeeJTable.getValueAt(count, 0).toString();
		System.out.println(PID);
		String Pname=name.getText().trim();
		String Psex=sex.getText().trim();
		String Pbirthday=birthday.getText().trim();
		String Pdepartment=department.getText().trim();
		String Pjob=job.getText().trim();
		String Pedu_level=education.getText().trim();
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
		String Pstatus=status.getText().trim();
		String Premark=remark.getText().trim();
		if(remark.getText().trim()==null){
			Premark=null;
		}
		
		// 建立更新条件
		String sql = "update person set Pname = '";
		sql = sql + Pname + "',Psex='";
		sql = sql + Psex + "',Pbirthday='";
		sql = sql + Pbirthday + "',Pdepartment='";
		sql = sql + Pdepartment + "',Pjob='";
		sql = sql + Pjob + "',Pedu_level='";
		sql = sql + Pedu_level+ "',Pspecialty='";
		sql = sql + Pspecialty + "',Paddress='";
		sql = sql + Paddress + "',Ptel='";
		sql = sql + Ptel + "',Pemail='";
		sql = sql + Pemail + "',Pstatus='";
		sql = sql + Pstatus+ "',Premark='";
		sql = sql + Premark + "' ";
		sql = sql + " where PID = '" + PID + "';";
		System.out.println("updateProcess(). sql = " + sql);
		try{
			if (dbProcess.executeUpdate(sql) < 1) {
				System.out.println("updateProcess(). update database failed.");
			}
		}catch(Exception e){
			System.out.println("e = " + e);
			JOptionPane.showMessageDialog(null,
				"数据操作错误","错误",JOptionPane.ERROR_MESSAGE);
		}
		queryAllProcess();
	}

	public void deleteCurrentRecordProcess()
	{
		int count=employeeJTable.getSelectedRow();//获取你选中的行号（记录）  
		String PID= employeeJTable.getValueAt(count, 0).toString();
		System.out.println(PID);
		String sql = "delete from person where PID = '" + PID+ "';";
		System.out.println("deleteCurrentRecordProcess(). sql = " + sql);
		try{
			if (dbProcess.executeUpdate(sql) < 1) {
				System.out.println("deleteCurrentRecordProcess(). delete database failed.");
			}
		}catch(Exception e){
			System.out.println("e = " + e);
			JOptionPane.showMessageDialog(null,
				"数据操作错误","错误",JOptionPane.ERROR_MESSAGE);
		}
		queryAllProcess();
	}
	
	public void deleteAllRecordsProcess()
	{
		// 建立删除条件
		String sql = "delete from person ;";
		System.out.println("deleteAllRecordsProcess(). sql = " + sql);
		try{
			if (dbProcess.executeUpdate(sql) < 1) {
				System.out.println("deleteAllRecordsProcess(). delete database failed.");
			}
		}catch(Exception e){
			System.out.println("e = " + e);
			JOptionPane.showMessageDialog(null,
				"数据操作错误","错误",JOptionPane.ERROR_MESSAGE);
		}
		queryAllProcess();
	}
	
	public String jCBSelectQueryFieldTransfer(String InputStr)
	{
		String outputStr = "";
		System.out.println("jCBSelectQueryFieldTransfer(). InputStr = " + InputStr);
		
		if(InputStr.equals("员工号")){
			outputStr = "PID";
		}else if(InputStr.equals("姓名")){
			outputStr = "Pname";
		}else if(InputStr.equals("性别")){
			outputStr = "Psex";
		}else if(InputStr.equals("生日")){
			outputStr = "Pbirthday";
		}else if(InputStr.equals("部门")){
			outputStr = "Pdepartment";
		}else if(InputStr.equals("职务")){
			outputStr = "Pjob";
		}else if(InputStr.equals("教育程度")){
			outputStr = "Pedu_level";
		}else if(InputStr.equals("专业技能")){
			outputStr = "Pspecialty";
		}
		else if(InputStr.equals("家庭住址")){
			outputStr = "Paddress";
		}
		else if(InputStr.equals("联系电话")){
			outputStr = "Ptel";
		}
		else if(InputStr.equals("电子邮件")){
			outputStr = "Pemail";
		}
		else if(InputStr.equals("当前状态")){
			outputStr = "Pstatus";
		}
		else if(InputStr.equals("备注")){
			outputStr = "Premark";
		}
		System.out.println("jCBSelectQueryFieldTransfer(). outputStr = " + outputStr);
		return outputStr;
	}
}
