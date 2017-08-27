import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;

public class Remove_Event extends JFrame 
{

	private JPanel contentPane;
	private JTextField textField;

	public Remove_Event() 
	{
		setTitle("Remove Event");
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
		
		JLabel label_2 = new JLabel("");
		label_2.setBounds(10, 246, 358, 14);
		contentPane.add(label_2);
		
		JLabel label = new JLabel("Event: ");
		label.setBounds(88, 76, 46, 14);
		contentPane.add(label);
		
		JComboBox comboBox = new JComboBox(DBInfo.getEvents());
		comboBox.setFocusable(false);
		comboBox.addItemListener(new ItemListener() 
		{
			public void itemStateChanged(ItemEvent arg0) 
			{
				String s=(String) comboBox.getSelectedItem();
				if(!(s.equalsIgnoreCase("select")))
				{
					label_2.setText(null);
				}
				String query="select date from events where title=?";
				try
				{
					PreparedStatement ps=DBInfo.con.prepareStatement(query);
					ps.setString(1, s);
					ResultSet res=ps.executeQuery();
					while(res.next())
					{
						textField.setText(DBInfo.format(res.getString(1)));
					}
				}
				catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});
		comboBox.setBounds(172, 73, 196, 20);
		contentPane.add(comboBox);
		
		JLabel label_1 = new JLabel("Date:");
		label_1.setBounds(88, 134, 46, 14);
		contentPane.add(label_1);
		
		textField = new JTextField();
		textField.setEditable(false);
		textField.setDisabledTextColor(new Color(245, 245, 245));
		textField.setColumns(10);
		textField.setBounds(172, 131, 196, 20);
		contentPane.add(textField);
		
		JButton button = new JButton("Remove Event");
		button.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				String title=(String) comboBox.getSelectedItem();
				if(title.equalsIgnoreCase("select"))
				{
					label_2.setText("*Please select an event!!!");
					label_2.setForeground(Color.RED);
				}
				else
				{
					label_2.setText(null);
					label_2.setForeground(Color.BLACK);
					String query="delete from events where title=?";
					try 
					{
						PreparedStatement ps=DBInfo.con.prepareStatement(query);
						ps.setString(1, title);
						ps.executeUpdate();
						dispose();
						new Remove_Event().setVisible(true);
					}
					catch (SQLException e) 
					{
						e.printStackTrace();
					}
				}
			}
		});
		button.setFocusPainted(false);
		button.setBounds(172, 179, 116, 23);
		contentPane.add(button);
		
		
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