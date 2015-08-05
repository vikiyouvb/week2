import java.util.*;
import java.io.*;

public class DirMove
{
	private Trie<Integer> filemap;
	private LinkedList<File> queue;
	public DirMove(String srcDir,String destDir)
	{
		filemap=new Trie<Integer>();
		queue=new LinkedList<File>();
		File src=new File(srcDir);
		File dest=new File(destDir);
		if(!src.exists() || !dest.exists())
		{
			throw new IllegalArgumentException("Either the source or destination path does not exists");
		}
		else if(!src.isDirectory() || !dest.isDirectory())
		{
			throw new IllegalArgumentException("The given path does not resolve to directory");
		}
		moveDirectory(src,destDir);
		while(queue.size()>0)
		{
			moveDirectory(queue.removeFirst(),destDir);
		}
	}
	public void moveDirectory(File srcdir,String dest)
	{
		for(File ff:srcdir.listFiles())
		{
			if(!ff.isDirectory())
			{
				String fname=ff.getName();
				System.out.println(fname);
				if(!filemap.contains(fname))
				{
					ff.renameTo(new File(dest+fname));
					filemap.put(fname,1);
				}
				else
				{
					Integer nextIndex=filemap.get(fname);
					String fnewname=getNewFileName(fname,nextIndex);
					filemap.put(fname,nextIndex+1);
					filemap.put(fnewname,1);
					ff.renameTo(new File(dest+fnewname));
				}				
			}
			else
			{
				queue.add(ff);
			}
		}
	}
	public String getNewFileName(String name,int index)
	{
		String fname[]=name.split("\\.");
		if(fname.length>0)
		{
			return fname[0]+"-"+index+"."+fname[1];
		}
		else
		{
			return fname[0]+"-"+index;
		}
	}
	public static void main(String args[]) throws Exception
	{
		 BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		 System.out.println("Enter the source directory");
		 String srcdir=br.readLine();
		 System.out.println("Enter the destination directory");
		 String destdir=br.readLine();
		 DirMove dm=new DirMove(srcdir,destdir);
	}
}
