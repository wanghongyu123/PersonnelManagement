package PMsystemDesign;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class passwordM extends JFrame implements ActionListener {
	// 定义组件
	JLabel label_table = null;
	JLabel equal = null;
	JLabel label_password = null;
	
	JTextField queryField = null;
	JTextField password = null;
	
	JButton query = null;
	JButton queryAll = null;
	JButton update = null;
	JButton back = null;
	
	JComboBox<String> jCBSelectQueryField = null;
	
	JPanel jP1, jP2,jP3,jP4 = null;
	JPanel jPTop, jPBottom = null;
	DefaultTableModel passwordTableModel = null;
	JTable passwordJTable = null;
	JScrollPane passwordJScrollPane = null;
	Vector passwordVector = null;
	Vector titleVector = null;
	
	private static DbProcess dbProcess;
	String SelectQueryFieldStr = "员工号";
	

	public passwordM() {
		label_table = new JLabel("员工密码表");
		equal = new JLabel(" = ");
		label_password=new JLabel(" 密码： ");
		
		queryField = new JTextField(10);
		password = new JTextField(10);
		
		query = new JButton("查询");
		queryAll = new JButton("查询所有记录");
		update = new JButton("更新");
		back=new JButton("返回主界面");
		
		query.addActionListener(this);
		queryAll.addActionListener(this);
		update.addActionListener(this);
		back.addActionListener(this);
		
		jCBSelectQueryField = new JComboBox<String>();
		jCBSelectQueryField.addItem("员工号");  
		jCBSelectQueryField.addItem("密码");  
		jCBSelectQueryField.addItemListener(new ItemListener() {
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
	
		passwordVector = new Vector();
		titleVector = new Vector();
		
		titleVector.add("员工号");
		titleVector.add("密码");
		passwordJTable = new JTable(passwordVector, titleVector);
		passwordJTable.setPreferredScrollableViewportSize(new Dimension(450,160));
		passwordJScrollPane = new JScrollPane(passwordJTable);
		//分别设置水平和垂直滚动条自动出现
		passwordJScrollPane.setHorizontalScrollBarPolicy(                
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		passwordJScrollPane.setVerticalScrollBarPolicy(                
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		//为表格添加监听器 
		passwordJTable.addMouseListener(new MouseAdapter()
		{ 
			public void mouseClicked(MouseEvent e) 
			{ 
				int row = ((JTable) e.getSource()).rowAtPoint(e.getPoint()); // 获得行位置
				System.out.println("mouseClicked(). row = " + row);
				Vector v = new Vector();
				v = (Vector) passwordVector.get(row);
				password.setText((String) v.get(1));
			}
		});


		jP1 = new JPanel();
		jP2 = new JPanel();
		jP3 = new JPanel();
		jP4 = new JPanel();
		jPTop = new JPanel();
		jPBottom = new JPanel();
		
		jP1.add(label_table,BorderLayout.SOUTH);
		jP2.add(passwordJScrollPane);
		
		jP3.add(jCBSelectQueryField);
		jP3.add(equal);
		jP3.add(queryField);
		jP3.add(query);
		jP3.add(queryAll);
		jP3.setLayout(new FlowLayout(FlowLayout.LEFT));
		jP3.setPreferredSize(new Dimension(20,20));
		
		jP4.add(label_password);
		jP4.add(password);
		jP4.add(update);
		jP4.add(back);
		jP4.setLayout(new FlowLayout(FlowLayout.LEFT));
		jP4.setPreferredSize(new Dimension(20,20));
		
		jPTop.add(jP1);
		jPTop.add(jP2);
		
		jPBottom.setLayout(new GridLayout(4, 1));
		jPBottom.add(jP3);
		jPBottom.add(jP4);
		
		this.add("North", jPTop);
		this.add("South", jPBottom);
	
		this.setLayout(new GridLayout(2, 1));
		this.setTitle("员工密码管理系统");
		this.setSize(500, 500);
		this.setLocation(150, 150);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setResizable(false);
		
		dbProcess = new DbProcess();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("查询")  
			&& !queryField.getText().isEmpty()){
			System.out.println("actionPerformed(). 查询");
			String sQueryField = queryField.getText().trim();
			queryProcess(sQueryField);
			queryField.setText("");
		}else if(e.getActionCommand().equals("查询所有记录")) {
			System.out.println("actionPerformed(). 查询所有记录");
			queryAllProcess();
		}else if(e.getActionCommand().equals("更新")
				&& !password.getText().isEmpty()){
			System.out.println("actionPerformed(). 更新");
			updateProcess();
		}else if(e.getActionCommand().equals("返回主界面")){
			PM_design window = new PM_design();
			this.setVisible(false);
		}
	}
	
	public static void main(String[] args) {
        // TODO Auto-generated method stub
		passwordM passwordM = new  passwordM();
    }
	
	public void queryProcess(String sQueryField)
	{
		try{
			String queryFieldStr = jCBSelectQueryFieldTransfer(SelectQueryFieldStr);
		    String sql = "select * from person where ";
			sql = sql + queryFieldStr;
			sql = sql + " = ";
			sql = sql + "'" + sQueryField + "';";
			
			System.out.println("queryProcess(). sql = " + sql);
	
			dbProcess.connect();
			ResultSet rs = dbProcess.executeQuery(sql);
	
			// 将查询获得的记录数据，转换成适合生成JTable的数据形式
			passwordVector.clear();
			while(rs.next()){
				Vector v = new Vector();
				v.add(rs.getString("PID"));
				v.add(rs.getString("Ppasswd"));
				passwordVector.add(v);
			}
			
			passwordJTable.updateUI();

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
			passwordVector.clear();
			while(rs.next()){
				Vector v = new Vector();
				v.add(rs.getString("PID"));
				v.add(rs.getString("Ppasswd"));
				passwordVector.add(v);
			}
			
			passwordJTable.updateUI();

			dbProcess.disconnect();
		}catch(SQLException sqle){
			System.out.println("sqle = " + sqle);
			JOptionPane.showMessageDialog(null,
				"数据操作错误","错误",JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void updateProcess()
	{
		int count=passwordJTable.getSelectedRow();  
		String PID=passwordJTable.getValueAt(count, 0).toString();
		System.out.println(PID);
		String Ppasswd =password.getText().trim();
		// 建立更新条件
		String sql = "update person set Ppasswd = '";
		sql = sql + Ppasswd + "'";
		sql = sql + " WHERE PID = '" + PID + "';";
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

	public String jCBSelectQueryFieldTransfer(String InputStr)
	{
		String outputStr = "";
		System.out.println("jCBSelectQueryFieldTransfer(). InputStr = " + InputStr);
		
		if(InputStr.equals("员工号")){
			outputStr = "PID";
		}else{
			outputStr = "Ppasswd";
		}
		System.out.println("jCBSelectQueryFieldTransfer(). outputStr = " + outputStr);
		return outputStr;
	}
}

