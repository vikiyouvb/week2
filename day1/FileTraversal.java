import java.util.*;
import java.io.*;

public class FileTraversal
{
	private Trie<Integer> extCount;
	private LinkedList<File> queue;
	public FileTraversal(String path)
	{
		extCount=new Trie<Integer>();
		queue=new LinkedList<File>();
		File f=new File(path);
		if(!f.exists())
		{
			throw new IllegalArgumentException("The specified path does not exist");
		}
		if(!f.isDirectory())
		{
			throw new IllegalArgumentException("Please provide directory path");
		}
		visitDirectory(f);
		while(queue.size()>0)
		{
			visitDirectory(queue.removeFirst());
		}	
	}
	public void visitDirectory(File f)
	{
		for(File ff:f.listFiles())
		{
			if(!ff.isDirectory())
			{
				String ext=getExt(ff.getName());
				if(ext!=null)
				{
					if(!extCount.contains(ext))
					{
						extCount.put(ext,1);
					}
					else
					{
						int count=extCount.get(ext);
						extCount.put(ext,count+1);
					}
				}
			}
			else
			{
				queue.add(ff);
			}	
		}
	}
	private  String getExt(String fname)
	{
		String exts[]=fname.split("\\.");
		if(exts.length>1)
		{
			return exts[exts.length-1];
		}
		return null;
	}
	public void printMap()
	{
		for(String s:extCount.getAllKeys())
		{
			System.out.println(s+" : "+extCount.get(s));
		}
	}
	public static void main(String args[]) throws Exception
	{
		System.out.println("Enter the absolute path of the directory");
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		String dirname=br.readLine();
		try
		{
			FileTraversal f=new FileTraversal(dirname);
			f.printMap();
		}
		catch(InputMismatchException ex)
		{
			System.out.println(ex.getMessage());
		}
	}
}
