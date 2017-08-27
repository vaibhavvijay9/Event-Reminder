import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import javax.swing.JTextArea;

public class Welcome extends JFrame implements MouseListener
{
	private JPanel contentPane;
	JMenu mnAdd,mnEdit,mnRemove,mnViewAll;
	JTextArea textArea;
	
	public Welcome() 
	{
		setTitle("Reminder");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setLocationRelativeTo(this);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 192, 203));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 444, 21);
		contentPane.add(menuBar);
		
		mnAdd = new JMenu("Add");
		menuBar.add(mnAdd);
		
		mnEdit = new JMenu("Edit");
		menuBar.add(mnEdit);
		
		mnRemove = new JMenu("Remove");
		menuBar.add(mnRemove);
		
		mnViewAll = new JMenu("View All");
		menuBar.add(mnViewAll);
		
		JLabel label = new JLabel("Event Reminder");
		label.setFont(new Font("Monotype Corsiva", Font.PLAIN, 36));
		label.setBounds(109, 32, 242, 35);
		contentPane.add(label);
		
		textArea = new JTextArea();
		textArea.setOpaque(false);
		textArea.setEditable(false);
		textArea.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 26));
		textArea.setForeground(new Color(255, 0, 51));
		textArea.setBounds(10, 75, 424, 193);
		contentPane.add(textArea);
		
		check(); 			//user defined function
		
		mnAdd.addMouseListener(this);
		mnEdit.addMouseListener(this);
		mnRemove.addMouseListener(this);
		mnViewAll.addMouseListener(this);	
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) {	}
	@Override
	public void mouseEntered(MouseEvent arg0) {}
	@Override
	public void mouseExited(MouseEvent arg0) {}
	@Override
	public void mousePressed(MouseEvent e) 
	{
		if(e.getSource()==mnAdd)
		{
			Add_Event ae=new Add_Event();
			ae.setVisible(true);
			dispose();
		}
		if(e.getSource()==mnEdit)
		{
			Edit_Event ee=new Edit_Event();
			ee.setVisible(true);
			dispose();
		}
		if(e.getSource()==mnRemove)
		{
			Remove_Event re=new Remove_Event();
			re.setVisible(true);
			dispose();
		}
		if(e.getSource()==mnViewAll)
		{
			ViewAll_Event ve=new ViewAll_Event();
			ve.setVisible(true);
			dispose();
		}
	}
	@Override
	public void mouseReleased(MouseEvent arg0) { }
	
	// checking for event today
	public void check()
	{
		Date d=new Date();
		SimpleDateFormat f=new SimpleDateFormat("dd/MM");
		String date=f.format(d);
		
		int flag=0;
		Vector<String> v=new Vector<>();
		
		String query="select * from events where date=?";
		try
		{
			PreparedStatement ps=DBInfo.con.prepareStatement(query);
			ps.setString(1, date);
			ResultSet res=ps.executeQuery();
			while(res.next())
			{
				flag=1;
				v.add(res.getString(2));
			}
			if(flag==1)
			{
				for(int i=0;i<v.size();i++)
				{
					textArea.append(v.elementAt(i)+"\n");
				}
			}
			else
			{
				textArea.setText("No Events Today.");
			}	
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
	public static void main(String[] args) 
	{
		Welcome w=new Welcome();
		w.setVisible(true);
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
}
