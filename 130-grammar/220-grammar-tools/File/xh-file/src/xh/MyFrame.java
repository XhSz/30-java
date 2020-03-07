package xh;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;

public class MyFrame {	
	public static final int WIDTH=520; 		//界面宽				
	public static final int HEIGHT=320;		//界面高
	
	//设置为全局变量
	JFrame jf=new JFrame();
	final JPanel jp=new JPanel();
	JTextField jtf=new JTextField();
	JButton jbSelect =new JButton("浏览");
	JButton jbOK=new JButton("确定");
	JTextArea jta=new JTextArea();
	JScrollPane jsp=new JScrollPane(jta);
	
	public MyFrame(){
		init();
	}
	
	public void init(){
		
		//布局
		jp.setLayout(null);
		jtf.setBounds(10,10,350,30);
		jtf.setEditable(false);
		jbSelect.setBounds(370,10, 65, 30);
		jbOK.setBounds(445,10,65,30);
		jsp.setBounds(10,60,500,200);
		
		jsp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED); 
		jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		jp.add(jtf);
		jp.add(jbSelect);
		jp.add(jbOK);		
		jp.add(jsp);
		
		jf.add(jp);
		jf.setTitle("目录及文件的长度和个数统计");
		Dimension   screenSize=Toolkit.getDefaultToolkit().getScreenSize(); 	//获取屏幕size
		jf.setBounds((screenSize.width-WIDTH)/2,(screenSize.height-HEIGHT)/2,WIDTH,HEIGHT);
		jf.setResizable(false);													//设置不可调整大小   		
		jf.setVisible(true);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	//关闭窗口时退出程序
		
		
		//事件
		jbSelect.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser=new JFileChooser();   
			    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);//设置只能选择目录
			    chooser.removeChoosableFileFilter(chooser.getAcceptAllFileFilter());  //移去所有文件过滤器
			    chooser.addChoosableFileFilter(new FileFilter() {					
					@Override
					public String getDescription() {
						return "目录文件";
					}
					@Override
					public boolean accept(File f) {
						return true;
					}
				});   //增加文件过滤器,接受目录文件
			    int state=chooser.showDialog(jp,"选择");
			    File file=chooser.getSelectedFile();
			    if(file!=null&&state==JFileChooser.APPROVE_OPTION){
			    	jtf.setText(file.toString());
			    }
			}
		});
		jbOK.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				new CountFile(jta,jtf.getText()).start();				
			}
		});		
	}
}
