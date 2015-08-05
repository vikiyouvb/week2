import java.util.*;

public class HashList
{
	private HashMap<Integer,ArrayList<String>> map;
	public HashList()
	{
		map=new HashMap<Integer,ArrayList<String>>();
	}
	public void addToMap(String arr[])
	{
		for(String s:arr)
		{
			int strlen=s.length();
			if(!map.containsKey(strlen))
			{
				map.put(strlen,new ArrayList<String>());
			}
			map.get(strlen).add(s);
		}
	}
	public void displayMap()
	{
		for(Integer i:map.keySet())
		{
			System.out.print(i+" --> ");
			ArrayList<String> list=map.get(i);
			System.out.println(list);
		}
	}
	public static void main(String args[])
	{
		HashList hl=new HashList();
		String arr[]={"one","two","three","four","five"};
		hl.addToMap(arr);
		hl.displayMap();	
	}
}
