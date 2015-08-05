import java.util.*;
import java.io.*;
import org.apache.commons.csv.*;

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

public class PhoneDirectoryCSV
{
	Trie<ArrayList<Contact>> namedict;
	Trie<Contact> numdict;
	public PhoneDirectoryCSV()
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
	public void processCSV(String fname) throws IOException
	{	
		CSVFormat format=CSVFormat.DEFAULT.withHeader().withDelimiter(',');
		try(CSVParser parser=new CSVParser(new FileReader(fname),format))
		{
			for(CSVRecord record:parser)
			{
				Contact c=new Contact(record.get("Name"),record.get("Address"),record.get("Mobile"),record.get("Home"),record.get("Work"));
				addToDict(c);
			}
		}		
	}
	public static void main(String args[]) throws Exception
	{
		PhoneDirectoryCSV p=new PhoneDirectoryCSV();
		System.out.println("Enter the csv file path");
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		String fpath=br.readLine();
		try
		{
			p.processCSV(fpath);
			p.displayContactByNumber("7299863073");
		}
		catch(IOException ex)
		{
			System.out.println(ex.getMessage());
		}
	}
}
