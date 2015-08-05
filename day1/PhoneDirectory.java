import java.util.*;

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

public class PhoneDirectory
{
	Trie<ArrayList<Contact>> namedict;
	Trie<Contact> numdict;
	public PhoneDirectory()
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
	public static void main(String args[])
	{
		PhoneDirectory p=new PhoneDirectory();
		String data[][]={{"sarath","chennai","7299863073",null,null},{"vignesh","chennai","7299863073",null,null},{"vignesh","trichy","8939136614",null,null}};
		p.processData(data);
		p.displayContactByNumber("7299863073");
	}
}
