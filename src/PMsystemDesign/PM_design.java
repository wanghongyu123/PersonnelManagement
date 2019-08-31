package PMsystemDesign;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Label;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class PM_design extends JFrame implements ActionListener{
	Label label=null;
	
	JMenuBar menuBar = null;
	
	JMenu employee = null;
	JMenuItem entry = null;
	JMenuItem archivesM = null;
	
	JMenu remove= null;
	JMenuItem remove_details = null;
	JMenuItem remove_job = null;//岗位调整
	JMenuItem remove_employee = null;//员工辞退
	
	JMenu safety = null;
	JMenuItem safety_Pass = null;
	JMenuItem safety_Right = null;
	
	JMenu systemS = null;
	JMenuItem systemS_L = null;
	JMenuItem systemS_Q = null;
	
	JMenu help = null;
	JMenuItem help_A = null;
	
	private static DbProcess dbProcess;
	
	public PM_design(){
		label = new Label("企业人事管理系统");
		label.setBackground(SystemColor.activeCaption);
		label.setFont(new Font("Harrington", Font.BOLD | Font.ITALIC, 50));
		label.setForeground(Color.BLUE);
		this.setTitle("您好！管理员");
		this.getContentPane().setForeground(Color.LIGHT_GRAY);
		this.getContentPane().add(label, BorderLayout.CENTER);
		this.setBounds(100, 100, 450, 450);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		
		menuBar = new JMenuBar();
		this.setJMenuBar(menuBar);
		
		employee = new JMenu("员工档案");
		menuBar.add(employee);
		entry = new JMenuItem("信息录入");
		employee.add(entry);
		archivesM = new JMenuItem("档案管理");
		employee.add(archivesM);
		
		remove= new JMenu("调动管理");
		menuBar.add(remove);
		remove_details = new JMenuItem("调动明细");
		remove.add(remove_details);
		remove_job = new JMenuItem("岗位调整");
		remove.add(remove_job);
		remove_employee = new JMenuItem("员工辞退");
		remove.add(remove_employee);
	
		safety = new JMenu("安全功能");
		menuBar.add(safety);
		safety_Pass = new JMenuItem("密码认证");
		safety.add(safety_Pass);
		safety_Right = new JMenuItem("权限授予");
		safety.add(safety_Right);
		
		systemS = new JMenu("系统设置");
		menuBar.add(systemS);
		systemS_L = new JMenuItem("返回登录界面");
		systemS.add(systemS_L);
		systemS_Q = new JMenuItem("退出系统");
		systemS.add(systemS_Q);
		
		help = new JMenu("帮助");
		menuBar.add(help);
		help_A = new JMenuItem("关于");
		help.add(help_A);
		
		entry.addActionListener(this);
		archivesM.addActionListener(this);
		remove_details.addActionListener(this);
		remove_job.addActionListener(this);
		remove_employee.addActionListener(this);
		safety_Pass.addActionListener(this);
		safety_Right.addActionListener(this);
		systemS_L.addActionListener(this);
		systemS_Q.addActionListener(this);
		help_A.addActionListener(this);
		
		dbProcess = new DbProcess();	
	}
	public static void main(String[] args){
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PM_design window = new PM_design();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public void actionPerformed(ActionEvent e){
		if(e.getActionCommand().equals("信息录入")){
			archives_entry en= new archives_entry();
			this.setVisible(false);
		}else if(e.getActionCommand().equals("档案管理")) {
			archivesM archivesM= new archivesM();
			this.setVisible(false);
		}else if(e.getActionCommand().equals("返回登录界面")){
			System.out.println("退出登录！");
			PM_login log=new PM_login();
			this.setVisible(false);
		}else if(e.getActionCommand().equals("退出系统")){
			System.out.println("退出系统！");
			System.exit(0);
		}else if(e.getActionCommand().equals("调动明细")){
			remove_details detailsR = new  remove_details();
			this.setVisible(false);
		}else if(e.getActionCommand().equals("岗位调整")){
			remove_job remove_job = new  remove_job();
			this.setVisible(false);
		}else if(e.getActionCommand().equals("员工辞退")){
			remove_employee remove_employee = new  remove_employee();
			this.setVisible(false);
		}else if(e.getActionCommand().equals("密码认证")){
			passwordM passwordM = new  passwordM();
			this.setVisible(false);
		}else if(e.getActionCommand().equals("权限授予")){
			authorityM authorityM = new  authorityM();
			this.setVisible(false);
		}else if(e.getActionCommand().equals("关于")){
			
		}
	}
}
