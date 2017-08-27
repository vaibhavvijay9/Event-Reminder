import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.JButton;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Edit_Event extends JFrame implements ItemListener
{
	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	UtilDateModel model;
	JDatePickerImpl datepicker;
	JDatePanelImpl panel;
	JComboBox comboBox;
	String old_date,title,old_title;
	JLabel label;
	
	public Edit_Event() 
	{
		setTitle("Edit Event");
		setResizable(false);
		setBounds(100, 100, 450, 300);
		setLocationRelativeTo(this);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 192, 203));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
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
		
		label = new JLabel("");
		label.setBounds(10, 246, 366, 14);
		contentPane.add(label);
		
		JLabel lblDate_1 = new JLabel("Date:");
		lblDate_1.setBounds(61, 73, 81, 14);
		contentPane.add(lblDate_1);
		
		textField_1 = new JTextField();
		textField_1.setEditable(false);
		textField_1.setBounds(152, 70, 224, 20);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblDate = new JLabel("Date:(New)");
		lblDate.setBounds(61, 116, 81, 14);
		contentPane.add(lblDate);
		
		model=new UtilDateModel();
		panel=new JDatePanelImpl(model);
		datepicker=new JDatePickerImpl(panel);
		datepicker.setBounds(152, 110, 220, 27);
		contentPane.add(datepicker);
		
		JLabel lblTitle = new JLabel("Title:(New)");
		lblTitle.setBounds(61, 167, 81, 14);
		contentPane.add(lblTitle);
		
		textField = new JTextField();
		textField.setBounds(152, 164, 224, 25);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblEvent = new JLabel("Event:");
		lblEvent.setBounds(61, 33, 81, 14);
		contentPane.add(lblEvent);
		
		comboBox = new JComboBox(DBInfo.getEvents());
		comboBox.setFocusable(false);
		comboBox.setBounds(152, 30, 224, 20);
		contentPane.add(comboBox);
		
		comboBox.addItemListener(this);
		
		JButton btnUpdateEvent = new JButton("Update Event");
		btnUpdateEvent.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				Date new_sel_date=(Date) datepicker.getModel().getValue();
							
				title=textField.getText();				
				
				if(title.length()==0 || new_sel_date.equals(null))
				{
					label.setText("*All fields are mandatory!!!");
					label.setForeground(Color.RED);
				}
				else
				{
					SimpleDateFormat f=new SimpleDateFormat("dd/MM");
					String new_date=f.format(new_sel_date);
					int flag=0;
					String query="select * from events where date=? and title=?";
					try 
					{
						PreparedStatement ps = DBInfo.con.prepareStatement(query);
						ps.setString(1, new_date);
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
						String query1="update events set date=?,title=? where title=?";
						try
						{
							PreparedStatement ps1=DBInfo.con.prepareStatement(query1);
							ps1.setString(1, new_date);
							ps1.setString(2, title);
							ps1.setString(3, old_title);			
							ps1.executeUpdate();
							dispose();
							new Edit_Event().setVisible(true);
						}
						catch (SQLException e) 
						{
							e.printStackTrace();
						}
					}
				}
			}
		});
		btnUpdateEvent.setBounds(152, 208, 124, 23);
		contentPane.add(btnUpdateEvent);
		
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

	@Override
	public void itemStateChanged(ItemEvent arg0) 
	{
		old_title=(String) comboBox.getSelectedItem();
		
		String query="select date,title from events where title=?";
		try
		{
			PreparedStatement ps=DBInfo.con.prepareStatement(query);
			ps.setString(1, old_title);
			
			ResultSet res=ps.executeQuery();
			while(res.next())
			{
				old_date=res.getString(1);
				title=res.getString(2);
				
				textField_1.setText(old_date);
				textField.setText(title);
				
				int dd=Integer.parseInt(old_date.substring(0, 2));
				int mm=Integer.parseInt(old_date.substring(3, 5))-1;
				
				model.setSelected(true);
				model.setDate(2017, mm, dd);
			}
			
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}		
	}
}
