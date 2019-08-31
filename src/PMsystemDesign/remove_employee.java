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

public class remove_employee extends JFrame implements ActionListener {
	// 定义组件
	JLabel label=null;
	JTextField textField=null;
	JButton button=null;
	JButton button_1=null;
	
	private static DbProcess dbProcess;
	
	public remove_employee() {
		this.setTitle("员工辞退");
		this.setBounds(200, 200, 350, 300);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setLayout(null);
		this.setVisible(true);
		
		textField = new JTextField();
		textField.setBounds(153, 73, 66, 21);
		this.getContentPane().add(textField);
		textField.setColumns(10);
		
		label = new JLabel("员工号：");
		label.setBounds(89, 76, 54, 15);
		this.getContentPane().add(label);
		
		button = new JButton("辞退");
		button.setBounds(50, 155, 93, 23);
		this.getContentPane().add(button);
		
		button_1 = new JButton("返回主界面");
		button_1.setBounds(173, 155, 101, 23);
		this.getContentPane().add(button_1);
		
		button.addActionListener(this);
		button_1.addActionListener(this);
		
		dbProcess = new DbProcess();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("辞退")
				&&!textField.getText().isEmpty()){
			deleteCurrentRecordProcess();
			//增添记录至调动明细表
			remove_details detailsR = new  remove_details();
			detailsR.queryAllProcess();
			detailsR.PID_L=textField.getText().trim();
			detailsR.removeEmployeeProcess("2");
			textField.setText("");
		}else{
			PM_design window = new PM_design();
			this.setVisible(false);
		}
	}
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					remove_employee remove_employee = new  remove_employee();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public void deleteCurrentRecordProcess()
	{
		String PID=textField.getText().trim();
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
	}
}


