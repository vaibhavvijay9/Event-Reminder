import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableColumn;

import java.awt.Color;
import javax.swing.JTable;
import javax.swing.UIManager;

public class ViewAll_Event extends JFrame 
{

	private JPanel contentPane;

	public ViewAll_Event() 
	{
		setTitle("All Events");
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
		
		DBInfo.getAllEvents();
		
		JTable t=new JTable(DBInfo.data, DBInfo.header);
		t.setRowHeight(20);
		JScrollPane pane=new JScrollPane(t);
		pane.setSize(424, 230);
		pane.setLocation(10, 30);
		contentPane.add(pane); 
		
		//  Modifying column width  vv
		TableColumn column = null;
		column = t.getColumnModel().getColumn(1);		// column 2nd
	    column.setPreferredWidth(250);					//column 2nd increased i.e. 1st decreased

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
		lblBack.setBounds(10, 5, 46, 14);
		contentPane.add(lblBack);
	}
}
