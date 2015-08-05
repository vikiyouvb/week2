import java.util.*;
import java.io.*;

public class ListInAlphaOrder
{
	private Trie<Integer> wordmap;
	public ListInAlphaOrder(String srcfile,String destfile) throws IOException
	{
		wordmap=new Trie<Integer>();
		File src=new File(srcfile);
		File dest=new File(destfile);
		processFile(src,dest);
	}
	private void processFile(File src,File dest) throws IOException
	{
		try(DataInputStream in=new DataInputStream(new BufferedInputStream(new FileInputStream(src))))
		{
			try
			{
				String words[]=in.readLine().split(" "); //Reads a line from the file and converts into an array of strings
				for(String word:words)
				{
					//If the map does not contain the word, init the word count to 1
					if(!wordmap.contains(word))
					{
						wordmap.put(word,1);
					}
					//If the word is already present, update the count
					else
					{
						int count=wordmap.get(word);
						wordmap.put(word,count+1);
					}
				}
			}
			catch(EOFException ex)
			{

			}
		}
		try(DataOutputStream out=new DataOutputStream(new BufferedOutputStream(new FileOutputStream(dest))))
		{
			//Get all the keys in the map and write the result in the format "[Word] : [Word count]"
			LinkedList<String> keys=wordmap.getAllKeys();
			for(String word:keys)
			{
				out.writeChars(String.format("[%s] : [%d]\n",word,wordmap.get(word)));
			}
		}
				
	}
	public static void main(String args[]) throws Exception
	{
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Enter the src file name");
		String src=br.readLine();
		System.out.println("Enter the dest file name");
		String dest=br.readLine();
		ListInAlphaOrder la=new ListInAlphaOrder(src,dest);
	}
}
