import java.util.*;
import java.io.*;

public class MaxAndMin
{
	public static int[] maxMin(int arr[])
	{
		int min=Integer.MAX_VALUE; 
		int max=Integer.MIN_VALUE; 
		int res[]=new int[2]; // Array to store the min and max value
		for(int i=0;i<arr.length;i++)
		{
			//Iterate through the array and update min and max accordingly
			if(arr[i]>max)
			{
				max=arr[i];
			}
			if(arr[i]<min)
			{
				min=arr[i];
			}
		}
		//Store the min value at 0th index and max value at 1st index
		res[0]=min;
		res[1]=max;
		return res;
	}
	public static void main(String args[]) throws Exception
	{
		//Reader object to read data from the command line
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		//Read the string and split the string using spaces Ex Inp->1 2 3 Out->["1","2","3"]
		String inps[]=br.readLine().split(" ");
		// Convert the string array to integer array
		int arr[]=new int[inps.length];
		for(int i=0;i<inps.length;i++)
		{
			arr[i]=Integer.parseInt(inps[i]);
		}
		int res[]=maxMin(arr);
		System.out.printf("Min value=%d, Max value=%d\n",res[0],res[1]);
	}
}
