import java.util.*;

public class PatternSearch
{
	private String pattern;
	private int M;
	private int dfs[][];
	public PatternSearch(String pattern)
	{
		this.pattern=pattern;
		this.dfs=new int[pattern.length()][256];
		this.M=pattern.length();
		buildDfs();
	}
	public void buildDfs()
	{
		dfs[0][pattern.charAt(0)]=1;
		int X=0;
		for(int i=1;i<M;i++)
		{
			for(int j=0;j<256;j++)
			{
				dfs[i][j]=dfs[X][j];
			}
			dfs[i][pattern.charAt(i)]=i+1;
			X=dfs[X][pattern.charAt(i)];
		}		
	}
	public int findPos(String s,int start)
	{
		int i,j=0,N=s.length();
		for(i=start;i<N;i++)
		{
			j=dfs[j][s.charAt(i)];
			if(j==M)
			{
				break;
			}
		}
		if(i==N)
		{
			return N;
		}
		else
		{
			return i-M+1;
		}
	}
	public static void main(String args[])
	{
		PatternSearch k=new PatternSearch("ABA");
		k.findPos("ABABABAA",0);
	}
}
