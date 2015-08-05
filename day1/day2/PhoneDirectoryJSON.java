import java.util.*;
import java.io.*;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

class Contact
{
	String name;
	String address;
	String mobile;
	String home;
	String work;
	public Contact(String name,String address,String mobile,String home,String work)
	{
		this.name=name;
		this.address=address;
		this.mobile=mobile;
		this.home=home;
		this.work=work;
	}
	public String toString()
	{
		StringBuilder br=new StringBuilder();
		br.append("Name : "+this.name);
		br.append(" | Address : "+this.address);
		br.append(" | Mobile : "+this.mobile);
		br.append(" | Home : "+this.home);
		br.append(" | Work : "+this.work);
		return br.toString();
	}
}	

public class PhoneDirectoryJSON
{
	Trie<ArrayList<Contact>> namedict;
	Trie<Contact> numdict;
	public PhoneDirectoryJSON()
	{
		namedict=new Trie<ArrayList<Contact>>();
		numdict=new Trie<Contact>();
	}
	public void addToDict(Contact c)
	{
		if(!namedict.contains(c.name))
		{
			ArrayList<Contact> list=new ArrayList<Contact>();
			list.add(c);
			namedict.put(c.name,list);	
		}
		else
		{
			ArrayList<Contact> list=namedict.get(c.name);
			list.add(c);
		}
		numdict.put(c.mobile,c);
	}
	public void displayContactsByName(String name)
	{
		LinkedList<String> keys=namedict.getKeysByPrefix(name);
		for(String key:keys)
		{
			for(Contact c:namedict.get(key))
			{
				System.out.println(c);
			}
		}
	}
	public void displayContactByNumber(String number)
	{
		System.out.println(numdict.get(number));
	}
	public void processData(String[][] data)
	{
		for(int i=0;i<data.length;i++)
		{
			Contact c=new Contact(data[i][0],data[i][1],data[i][2],data[i][3],data[i][4]);
			addToDict(c);
		}
	}
	public void processJSON(String fname) throws IOException,ParseException
	{
		JSONParser parser=new JSONParser();
		JSONArray contacts=(JSONArray)parser.parse(new FileReader(fname));
		for(int i=0;i<contacts.size();i++)
		{
			JSONObject contact=(JSONObject)contacts.get(i);
			String cname=(String)contact.get("name");
			String address=(String)contact.get("address");
			String mobile=(String)contact.get("mobile");
			String home=(String)contact.get("home");
			String work=(String)contact.get("work");
			Contact c=new Contact(cname,address,mobile,home,work);
			addToDict(c);
		}
	}
	public static void main(String args[]) throws Exception
	{
		PhoneDirectoryJSON p=new PhoneDirectoryJSON();
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Enter json file name");
		String fname=br.readLine();
		try
		{
			p.processJSON(fname);
			p.displayContactsByName("Vignesh");
		}
		catch(IOException ex)
		{
			System.out.println(ex.getMessage());
		}
		catch(ParseException ex)
		{
			System.out.println(ex.getMessage());
		}
	}
}
