import java.util.*;

public class PrefixMap
{
	private TreeMap<String,ArrayList<String>> map;
	public PrefixMap()
	{
		map=new TreeMap<String,ArrayList<String>>();
	}
	public void processKeys(String keys[])
	{
		for(String s:keys)
		{
			addToMap(s);
		}
	}
	public void addToMap(String s)
	{
		int strlen=s.length();
		if(strlen<3)
		{
			throw new IllegalArgumentException("Key must be of length more than 3");
		}
		String key=s.substring(0,3);
		if(!map.containsKey(key))
		{
			map.put(key,new ArrayList<String>());
		}
		map.get(key).add(s);
	}
	public void displayMap()
	{
		for(String prefix:map.keySet())
		{
			System.out.print(prefix+" --> ");
			ArrayList<String> vals=map.get(prefix);
			Collections.sort(vals);
			System.out.println(vals);
		}
	}
	public static void main(String args[])
	{
		String keys[]={"one","two","three","four","five","six","oneone","onefiv"};
		PrefixMap pm=new PrefixMap();
		pm.processKeys(keys);
		pm.displayMap();		
	}
}
