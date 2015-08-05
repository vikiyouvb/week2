import java.util.*;
import java.io.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JSONDemo
{
	public static void processJSON(String fname) throws IOException,ParseException
	{
		JSONParser parser=new JSONParser();
		JSONObject object=(JSONObject) parser.parse(new FileReader(fname));
		JSONObject student=(JSONObject)object.get("Student");
		String s_doj=(String)student.get("Date Of Joining");
		String s_id=(String)student.get("ID");
		String s_name=(String)student.get("Name");
		String s_std=(String)student.get("Std");
		Student s=new Student(s_doj,s_id,s_name,s_std);
		JSONArray marks=(JSONArray)student.get("Marks");
		for(int i=0;i<marks.size();i++)
		{
			JSONObject markn=(JSONObject)marks.get(i);
			Integer mark=((Long)markn.get("Mark")).intValue();
			String subject=(String)markn.get("Subject");
			s.addSubject(subject,mark);
		}
		JSONObject teacher=(JSONObject)object.get("Teacher");
		String t_doj=(String)teacher.get("Date Of Joining");
		String t_id=(String)teacher.get("ID");
		String t_name=(String)teacher.get("Name");
		Double t_salary=(Double)teacher.get("Salary");
		Teacher t=new Teacher(t_name,t_id,t_doj,t_salary);
		JSONArray classes=(JSONArray)teacher.get("Classes Taking Care Of");
		for(int i=0;i<classes.size();i++)
		{
			t.addClass((String)teacher.get(i));
		}		
		System.out.println(student);
		System.out.println(teacher);
	}
	public static void main(String args[]) throws Exception
	{
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Enter the .json file name");
		String fname=br.readLine();
		try
		{
			JSONDemo.processJSON(fname);
		}
		catch(IOException ex)
		{
			System.out.println(ex.getMessage());
		}
		catch(ParseException ex)
		{
			System.out.println(ex.getMessage());
		}
		catch(NullPointerException ex)
		{
			System.out.println(ex.getMessage());
		}
	}
}
