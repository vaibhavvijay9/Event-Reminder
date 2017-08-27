import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JPanel;

import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Add_Event extends JFrame
{
	Connection con=DBInfo.con;
	
	private JPanel contentPane;
	private JTextField textField;
	
	public Add_Event() 
	{
		setTitle("Add Event");
		setResizable(false);
		setBounds(100, 100, 450, 300);
		setLocationRelativeTo(this);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 192, 203));
		contentPane.setBorder(null);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (Exception e1)
		{
			e1.printStackTrace();
		}
		
		addWindowListener(new WindowAdapter() 
		{
			@Override
			public void windowClosing(WindowEvent arg0) 
			{
				Welcome w=new Welcome();
				w.setVisible(true);
				dispose();
			}
		});
		
		JLabel label = new JLabel("");
		label.setFont(new Font("Tahoma", Font.PLAIN, 11));
		label.setBounds(10, 246, 363, 14);
		contentPane.add(label);
		
		JLabel lblDate = new JLabel("Date:");
		lblDate.setBounds(68, 82, 75, 14);
		contentPane.add(lblDate);
		
		UtilDateModel model=new UtilDateModel();
		JDatePanelImpl datepanel=new JDatePanelImpl(model);
		JDatePickerImpl datepicker=new JDatePickerImpl(datepanel);
		datepicker.setBounds(153, 75, 220, 27);
		contentPane.add(datepicker);
		
		JLabel lblEvent = new JLabel("Event:");
		lblEvent.setBounds(68, 134, 46, 14);
		contentPane.add(lblEvent);
		
		textField = new JTextField();
		textField.addFocusListener(new FocusAdapter() 
		{
			@Override
			public void focusGained(FocusEvent arg0) 
			{
				label.setText(null);
			}
		});
		textField.setBounds(153, 131, 220, 25);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JButton btnAddEvent = new JButton("Add Event");
		btnAddEvent.setFocusPainted(false);
		btnAddEvent.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				Date d=(Date)datepicker.getModel().getValue();	
				String title=textField.getText();
				
				if(title.length()==0 || d.equals(null))
				{
					label.setText("*All fields are mandatory!!!");
					label.setForeground(Color.RED);
				}
				else
				{
					SimpleDateFormat f=new SimpleDateFormat("dd/MM");
					String date=f.format(d);
					int flag=0;
					String query="select * from events where date=? and title=?";
					try 
					{
						PreparedStatement ps = con.prepareStatement(query);
						ps.setString(1, date);
						ps.setString(2, title);
						ResultSet res=ps.executeQuery();
						while(res.next())
						{
							flag=1;
						}
					} 
					catch (SQLException e1) 
					{
						e1.printStackTrace();
					}
					
					if(flag==1)
					{
						label.setText("Duplicate Entry....record already exists!!!");
						label.setForeground(Color.RED);
					}
					
					else
					{
						String query1="insert into events values(?,?)";
						try
						{
							PreparedStatement ps1=con.prepareStatement(query1);
							ps1.setString(1, date);
							ps1.setString(2, title);
							ps1.executeUpdate();
							label.setText("Event added...");
							label.setForeground(Color.BLACK);
							model.setValue(null);
							textField.setText(null);
						}
						catch (SQLException e) 
						{
							e.printStackTrace();
						}
					}
				}
			}
		});
		btnAddEvent.setBounds(153, 189, 118, 23);
		contentPane.add(btnAddEvent);
		
		JLabel lblBack = new JLabel("Back");
		lblBack.addMouseListener(new MouseAdapter() 
		{
			@Override
			public void mouseClicked(MouseEvent arg0) 
			{
				Welcome w=new Welcome();
				w.setVisible(true);
				dispose();
			}
		});
		lblBack.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblBack.setForeground(Color.RED);
		lblBack.setBounds(10, 11, 46, 14);
		contentPane.add(lblBack);
		}
}
