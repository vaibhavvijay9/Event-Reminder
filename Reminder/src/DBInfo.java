import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;

public class DBInfo 
{
	static Connection con;
	
	static Vector<String> header;
	static Vector<Vector<String>> data;
	
	static
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/reminder","root","rat");
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	public static Vector<String> getEvents()
	  {
		  Vector<String> v=new Vector<>();
		  String query="select title from events order by title";
		  try
		  {
			PreparedStatement ps=con.prepareStatement(query);
			ResultSet res=ps.executeQuery();
			while(res.next())
			{
				v.add(res.getString(1));
			}
		  }
		  catch (Exception e)
		  {
			e.printStackTrace();
		  }
		  v.add(0, "Select");
		  return v;
	  }
	
	public static void getAllEvents()
	{
		header=new Vector<>();
		data=new Vector<>();
		
		String query="select * from events order by substr(date,4,2),substr(date,1,2)";
		try 
		{
			PreparedStatement ps=con.prepareStatement(query);
			ResultSet res=ps.executeQuery();
			ResultSetMetaData rsmd=res.getMetaData();
			int colcount=rsmd.getColumnCount();
			
			header.add("Date");
			header.add("Title");
			
			while(res.next())
			{
				Vector<String> records=new Vector<>();
				for(int i=1;i<=colcount;i++)
				{
					String date=res.getString(1);
					String title=res.getString(2);
					
					date=format(date);		// 09/05------> 09 May
					
					records.add(date);
					records.add(title);
				}
				data.add(records);
			}
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
	  
	//format date view 
	  public static String format(String date)
		{
			String mon=date.substring(3,5);
			switch (mon) 
			{
				case "01": date=date.substring(0,2)+" January";
				break;
				case "02": date=date.substring(0,2)+" February";
				break;
				case "03": date=date.substring(0,2)+" March";
				break;
				case "04": date=date.substring(0,2)+" April";
				break;
				case "05": date=date.substring(0,2)+" May";
				break;
				case "06": date=date.substring(0,2)+" June";
				break;
				case "07": date=date.substring(0,2)+" July";
				break;
				case "08": date=date.substring(0,2)+" August";
				break;
				case "09": date=date.substring(0,2)+" September";
				break;
				case "10": date=date.substring(0,2)+" October";
				break;
				case "11": date=date.substring(0,2)+" November";
				break;
				case "12": date=date.substring(0,2)+" December";
				break;
				default:
				break;
			}
			return date;
		}
}
