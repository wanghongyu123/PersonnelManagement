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

public class remove_details extends JFrame implements ActionListener {
	// 定义组件
	JLabel label_details = null;
	JLabel equal = null;
	
	JTextField jTFQueryField = null;
	
	JButton query = null;
	JButton queryAll = null;
	JButton deleteCurrentRecord = null;
	JButton deleteAllRecords = null;
	JButton back = null;
	
	//JComboBox jCBSelectQueryField = null;
	JComboBox<String> jCBSelectQueryField = null;//查询字段
	JPanel jP1, jP2,jP3,jP4 = null;
	JPanel jPTop, jPBottom = null;
	DefaultTableModel removeTableModel = null;
	JTable removeJTable = null;
	JScrollPane removeJScrollPane = null;
	Vector removeVector = null;
	Vector titleVector = null;
	
	private static DbProcess dbProcess;
	String SelectQueryFieldStr = "记录编号";
	String PID_E;
	String PID_J;
	String PID_L;
	// 构造函数
	public remove_details() {
		// 创建组件	
		label_details = new JLabel("人事调动明细表");
		equal = new JLabel(" = ");
		
		jTFQueryField = new JTextField(10);
		
		query = new JButton("查询");
		queryAll = new JButton("查询所有记录");
		deleteCurrentRecord = new JButton("删除当前记录");
		deleteAllRecords = new JButton("删除所有记录");
		back = new JButton("返回主界面");
		// 设置监听
		query.addActionListener(this);
		queryAll.addActionListener(this);
		deleteCurrentRecord.addActionListener(this);
		deleteAllRecords.addActionListener(this);
		back.addActionListener(this);
		
		jCBSelectQueryField = new JComboBox<String>();//查询字段
		jCBSelectQueryField.addItem("记录编号");  
		jCBSelectQueryField.addItem("员工号");  
		jCBSelectQueryField.addItem("变更代码");
		jCBSelectQueryField.addItem("描述");
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
	
		removeVector = new Vector();
		titleVector = new Vector();
		
		// 定义表头
		titleVector.add("记录编号");
		titleVector.add("员工号");
		titleVector.add("变更代码");
		titleVector.add("描述");
		removeJTable = new JTable(removeVector, titleVector);
		removeJTable.setPreferredScrollableViewportSize(new Dimension(450,160));
		removeJScrollPane = new JScrollPane(removeJTable);
		//分别设置水平和垂直滚动条自动出现
		removeJScrollPane.setHorizontalScrollBarPolicy(                
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		removeJScrollPane.setVerticalScrollBarPolicy(                
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

		jP1 = new JPanel();
		jP2 = new JPanel();
		jP3 = new JPanel();
		jP4 = new JPanel();
		jPTop = new JPanel();
		jPBottom = new JPanel();
		
		jP1.add(label_details,BorderLayout.SOUTH);
		jP2.add(removeJScrollPane);
		
		jP3.add(jCBSelectQueryField);
		jP3.add(equal);
		jP3.add(jTFQueryField);
		jP3.add(query);
		jP3.add(queryAll);
		jP3.setLayout(new FlowLayout(FlowLayout.LEFT));
		jP3.setPreferredSize(new Dimension(20,20));
		
		jP4.add(deleteCurrentRecord);
		jP4.add(deleteAllRecords);
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
		this.setTitle("人事调动明细");
		this.setSize(500, 500);
		this.setLocation(750, 100);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setResizable(false);
		
		dbProcess = new DbProcess();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("查询")  
			&& !jTFQueryField.getText().isEmpty()){
			System.out.println("actionPerformed(). 查询");
			String sQueryField = jTFQueryField.getText().trim();
			queryProcess(sQueryField);
			jTFQueryField.setText("");
		}else if(e.getActionCommand().equals("查询所有记录")) {
			System.out.println("actionPerformed(). 查询所有记录");
			queryAllProcess();
		}else if(e.getActionCommand().equals("删除当前记录")){
			System.out.println("actionPerformed(). 删除当前记录");
			deleteCurrentRecordProcess();
		}else if(e.getActionCommand().equals("删除所有记录")){
			tip_1 tip_1 = new tip_1();
		}else if(e.getActionCommand().equals("返回主界面")){
			PM_design window = new PM_design();
			this.setVisible(false);
		}
	}
	
	public static void main(String[] args) {
        // TODO Auto-generated method stub
		remove_details detailsR = new  remove_details();
    }
	
	public void queryProcess(String sQueryField)
	{
		try{
			// 建立查询条件
			String sql = "select * from personnel where ";
			String queryFieldStr = jCBSelectQueryFieldTransfer(SelectQueryFieldStr);
			sql = sql + queryFieldStr;
			sql = sql + " = ";
			sql = sql + "'" + sQueryField + "';";
			
			System.out.println("queryProcess(). sql = " + sql);
	
			dbProcess.connect();
			ResultSet rs = dbProcess.executeQuery(sql);
	
			// 将查询获得的记录数据，转换成适合生成JTable的数据形式
			removeVector.clear();
			while(rs.next()){
				Vector v = new Vector();
				v.add(rs.getString("RID"));
				v.add(rs.getString("PID"));
				v.add(rs.getString("Rcode"));
				v.add(rs.getString("Rdescription"));
				removeVector.add(v);
			}
			
			removeJTable.updateUI();

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
			String sql = "select * from personnel;";
			System.out.println("queryAllProcess(). sql = " + sql);
	
			dbProcess.connect();
			ResultSet rs = dbProcess.executeQuery(sql);

			// 将查询获得的记录数据，转换成适合生成JTable的数据形式
			removeVector.clear();
			while(rs.next()){
				Vector v = new Vector();
				v.add(rs.getString("RID"));
				v.add(rs.getString("PID"));
				v.add(rs.getString("Rcode"));
				v.add(rs.getString("Rdescription"));
				removeVector.add(v);
			}
			
			removeJTable.updateUI();

			dbProcess.disconnect();
		}catch(SQLException sqle){
			System.out.println("sqle = " + sqle);
			JOptionPane.showMessageDialog(null,
				"数据操作错误","错误",JOptionPane.ERROR_MESSAGE);
		}
	}

	public void deleteCurrentRecordProcess()
	{
		int count=removeJTable.getSelectedRow();  
		String RID=removeJTable.getValueAt(count, 0).toString();
		System.out.println(RID);
		String sql = "delete from personnel where RID = '" + RID + "';";
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
		String sql = "delete from personnel;";
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
		
		if(InputStr.equals("记录编号")){
			outputStr = "RID";
		}else if(InputStr.equals("员工号")){
			outputStr = "PID";
		}else if(InputStr.equals("变更代码")){
			outputStr = "Rcode";
		}else if(InputStr.equals("描述")){
			outputStr = "Rdescription";
		}
		System.out.println("jCBSelectQueryFieldTransfer(). outputStr = " + outputStr);
		return outputStr;
	}
	
	public void archivesEntryProcess(String str1) {
		int count=removeJTable.getRowCount();
		System.out.println(count);
		String RID=String.valueOf(++count);
		String Rcode=str1;
		String Rdescription=null;
		try{
			// 建立查询条件
			String sql = "select description from personnel_change where Rcode='"+Rcode+"';";
			dbProcess.connect();
			ResultSet rs = dbProcess.executeQuery(sql);
			while(rs.next()){
				Rdescription=rs.getString("description");
			}
			
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
	    System.out.println(PID_E);
		String sql1 = "insert into personnel values('";
		sql1= sql1 + RID + "','";
		sql1 = sql1 + PID_E +  "','";
		sql1 = sql1 + Rcode + "','";
		sql1 = sql1 + Rdescription + "');";
		try{
			if (dbProcess.executeUpdate(sql1) < 1) {
				System.out.println("insertProcess(). insert database failed.");
			}
		}catch(Exception e){
			System.out.println("e = " + e);
			JOptionPane.showMessageDialog(null,
				"数据操作错误","错误",JOptionPane.ERROR_MESSAGE);
		}
		removeVector.clear();
		Vector v = new Vector();
		v.add(RID);
		v.add(PID_E);
		v.add(Rcode);
		v.add(Rdescription);
		removeVector.add(v);
		removeJTable.updateUI();
		queryAllProcess();
	}
	
	public void removeJobProcess(String str2) {
		int count=removeJTable.getRowCount();
		System.out.println(count);
		String RID=String.valueOf(++count);
		String Rcode=str2;
		String Rdescription=null;
		try{
			// 建立查询条件
			String sql = "select description from personnel_change where Rcode='"+Rcode+"';";
			dbProcess.connect();
			ResultSet rs = dbProcess.executeQuery(sql);
			while(rs.next()){
				Rdescription=rs.getString("description");
			}
			
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
	    System.out.println(PID_J);
		String sql1 = "insert into personnel values('";
		sql1= sql1 + RID + "','";
		sql1 = sql1 + PID_J +  "','";
		sql1 = sql1 + Rcode + "','";
		sql1 = sql1 + Rdescription + "');";
		try{
			if (dbProcess.executeUpdate(sql1) < 1) {
				System.out.println("insertProcess(). insert database failed.");
			}
		}catch(Exception e){
			System.out.println("e = " + e);
			JOptionPane.showMessageDialog(null,
				"数据操作错误","错误",JOptionPane.ERROR_MESSAGE);
		}
		removeVector.clear();
		Vector v = new Vector();
		v.add(RID);
		v.add(PID_J);
		v.add(Rcode);
		v.add(Rdescription);
		removeVector.add(v);
		removeJTable.updateUI();
		queryAllProcess();
	}
	
	public void removeEmployeeProcess(String str3) {
		int count=removeJTable.getRowCount();
		System.out.println(count);
		String RID=String.valueOf(++count);
		String Rcode=str3;
		String Rdescription=null;
		try{
			// 建立查询条件
			String sql = "select description from personnel_change where Rcode='"+Rcode+"';";
			dbProcess.connect();
			ResultSet rs = dbProcess.executeQuery(sql);
			while(rs.next()){
				Rdescription=rs.getString("description");
			}
			
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
	    System.out.println(PID_L);
		String sql1 = "insert into personnel values('";
		sql1= sql1 + RID + "','";
		sql1 = sql1 + PID_L +  "','";
		sql1 = sql1 + Rcode + "','";
		sql1 = sql1 + Rdescription + "');";
		try{
			if (dbProcess.executeUpdate(sql1) < 1) {
				System.out.println("insertProcess(). insert database failed.");
			}
		}catch(Exception e){
			System.out.println("e = " + e);
			JOptionPane.showMessageDialog(null,
				"数据操作错误","错误",JOptionPane.ERROR_MESSAGE);
		}
		removeVector.clear();
		Vector v = new Vector();
		v.add(RID);
		v.add(PID_L);
		v.add(Rcode);
		v.add(Rdescription);
		removeVector.add(v);
		removeJTable.updateUI();
		queryAllProcess();
	}
}
