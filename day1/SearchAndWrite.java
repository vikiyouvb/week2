import java.util.*;
import java.io.*;

//Class that encapsulates the line number and the position information
class LinePos
{
	int lineno;
	LinkedList<Integer> poslist;
	//LinePos is initialized with the lineno that contains the pattern
	public LinePos(int lineno)
	{
		this.lineno=lineno;
		poslist=new LinkedList<Integer>();
	}
	//Adds the new position to the list of positions in the line that contains the pattern
	public void addPos(int pos)
	{
		poslist.add(pos);
	}
	//Displays the lineno and the positions in the format <lineno>:<pos1>,<pos2>
	public String toString()
	{
		StringBuilder br=new StringBuilder();
		br.append("<"+this.lineno+">:");
		for(Integer pos:poslist)
		{
			br.append("<"+pos+">,");
		}
		br.deleteCharAt(br.length()-1);
		return br.toString();
	}
}	

public class SearchAndWrite
{
	private LinkedList<LinePos> patPos;
	private PatternSearch ps;
	private String pattern;
	public SearchAndWrite(String file,String pattern) throws IOException
	{
		this.patPos=new LinkedList<LinePos>();
		this.ps=new PatternSearch(pattern);
		this.pattern=pattern;
		File f=new File(file);
		readFile(f);
		writeFile();
	}
	public void readFile(File f) throws IOException
	{
		int linecount=1;
		try(DataInputStream in=new DataInputStream(new BufferedInputStream(new FileInputStream(f))))
		{
			String line;
			while((line=in.readLine())!=null)
			{
				findPos(line,linecount++);
			}
		}
	}
	public void writeFile() throws IOException
	{
		try(DataOutputStream out=new DataOutputStream(new BufferedOutputStream(new FileOutputStream(pattern+".locations"))))
		{
			for(LinePos lp:patPos)
			{
				out.writeChars(lp.toString()+"\n");
			}
		}
	}
	//Creates a new LinePos object
	public void findPos(String line,int lineno)
	{
		int pos=this.ps.findPos(line,0);
		int N=line.length();
		LinePos lp=new LinePos(lineno);
		while(pos!=N)
		{
			lp.addPos(pos);
			pos=this.ps.findPos(line,pos+1);
		}
		patPos.add(lp);
	}
	public static void main(String args[]) throws Exception
	{
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Enter the filename");
		String fname=br.readLine();
		System.out.println("Enter the pattern");
		String pattern=br.readLine();
		SearchAndWrite s=new SearchAndWrite(fname,pattern);
	}
}
