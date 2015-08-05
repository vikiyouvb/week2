import java.util.*;

public class Trie<Value>
{
	private Node root;
	private class Node
	{
		char key;
		Value v;
		Node left;
		Node middle;
		Node right;
		public Node(char key,Value v)
		{
			this.key=key;
			this.v=v;
			this.left=null;
			this.middle=null;
			this.right=null;
		}
	}
	private Node put(Node root,String key,Value v,int pos)
	{
		char c=key.charAt(pos);
		if(root==null)
		{
			root=new Node(c,null);	
		}
		if(pos==key.length()-1 && c==root.key)
		{
			root.v=v;
			return root;
		}
		if(c<root.key)
		{
			root.left=put(root.left,key,v,pos);
		}
		else if(c>root.key)
		{
			root.right=put(root.right,key,v,pos);
		}
		else
		{
			root.middle=put(root.middle,key,v,pos+1);
		}
		return root;
	}
	public void put(String key,Value v)
	{
		root=put(root,key,v,0);
	}
	private Node get(Node root,String key,int pos)
	{
		char c=key.charAt(pos);
		if(root==null)
		{
			return null;
		}
		if(pos==key.length()-1 && c==root.key)
		{
			return root;
		}
		if(c<root.key)
		{
			return get(root.left,key,pos);
		}
		else if(c>root.key)
		{
			return get(root.right,key,pos);
		}
		else
		{
			return get(root.middle,key,pos+1);
		}
	}
	public Value get(String key)
	{
		Node res=get(root,key,0);
		if(res==null || res.v==null)
		{
			return null;
		}
		return res.v;
	}
	public boolean contains(String key)
	{
		return get(key)!=null;
	}
	private void get(Node root,StringBuilder br,LinkedList<String> list)
	{
		if(root==null)
		{
			return;
		}
		get(root.left,br,list);
		if(root.v!=null)
		{
			list.add(br.toString()+root.key);
		}
		get(root.middle,br.append(root.key),list);
		br.deleteCharAt(br.length()-1);	
		get(root.right,br,list);
	}
	public LinkedList<String> getKeysByPrefix(String prefix)
	{
		LinkedList<String> list=new LinkedList<String>();
		StringBuilder br=new StringBuilder();
		br.append(prefix);
		Node res=get(root,prefix,0);
		if(res!=null)
		{
			if(res.v!=null)
			{
				list.add(prefix);
			}
			get(res.middle,br,list);
		}
		return list;	
	}
	public LinkedList<String> getAllKeys()
	{
		LinkedList<String> list=new LinkedList<String>();
		StringBuilder br=new StringBuilder();
		get(root,br,list);
		return list;
	}
	public static void main(String args[])
	{
		Trie<Integer> t=new Trie<Integer>();
		t.put("abc",1);
		t.put("aba",2);
		t.put("aca",3);
		LinkedList<String> val=t.getAllKeys();
		for(String s:val)
		{
			System.out.println(s);
		}		
	}
}
