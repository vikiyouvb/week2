
import java.util.*;
import java.io.*;
import java.text.*;
import org.apache.commons.csv.*;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class CSVProcess
{
	private HashMap<String,String> types;
	private HashMap<String,ArrayList<String>> groups;
	private HashMap<String,String> rename;
	private HashMap<String,String> format;
	private HashMap<String,String> ops;
	private LinkedHashMap<String,String> temp;
	private boolean printHeader=true;
	public CSVProcess(String jsoninpfile,String csvinpfile,String csvoutfile) throws IOException,Exception
	{
		types=new HashMap<String,String>();
		groups=new HashMap<String,ArrayList<String>>();
		rename=new HashMap<String,String>();
		format=new HashMap<String,String>();
		ops=new HashMap<String,String>();
		temp=new LinkedHashMap<String,String>();
		buildDS(jsoninpfile);
		processCSV(csvinpfile,csvoutfile);
	}
	//Helper method
	private void addToMap(JSONObject obj,HashMap<String,String> map,String key)
	{
		JSONObject objs=(JSONObject)obj.get(key);
		for(String k:(Set<String>)objs.keySet())
		{
			map.put(k,(String)objs.get(k));
		}
	}
	//Helper method
	private void addToList(JSONObject obj,ArrayList<String> list,String key)
	{
		JSONArray objs=(JSONArray)obj.get(key);
		for(int i=0;i<objs.size();i++)
		{
			list.add((String)objs.get(i));
		}
	}
	//Builds internal data structures from the json config file
	private void buildDS(String file) throws IOException,ParseException
	{
		JSONParser parser=new JSONParser();
		JSONObject config=(JSONObject)parser.parse(new FileReader(file));
		addToMap(config,this.types,"types");
		JSONObject groups=(JSONObject)config.get("combine");
		for(String key:(Set<String>)groups.keySet())
		{
			ArrayList<String> temp=new ArrayList<String>();
			addToList(groups,temp,key);
			this.groups.put(key,temp);
		}
		addToMap(config,this.rename,"rename");
		addToMap(config,this.format,"format");
		addToMap(config,this.ops,"operation");
	}
	private void processCSV(String csvinpfile,String csvoutfile) throws IOException,Exception
	{	
		CSVFormat format=CSVFormat.RFC4180.withHeader().withDelimiter(',');
		try(CSVPrinter printer=new CSVPrinter(new FileWriter(csvoutfile),format))
		{
			try(CSVParser parser=new CSVParser(new FileReader(csvinpfile),format))
			{
				Map<String,Integer> headerMap=parser.getHeaderMap();
				Set<String> headers=headerMap.keySet();
				for(CSVRecord record:parser)
				{
					for(String header:headers)
					{
						temp.put(header,record.get(header));
					}
					this.operation();//Performs operation given in the config file
					this.format();//Formats the data according to the specification in the config file
					this.combine();//Groups one or more header fields into a single JSON object
					this.rename();
					if(this.printHeader)//Prints the new header for the output file
					{
						printer.printRecord(this.temp.keySet());
						this.printHeader=false;
					}
					printer.printRecord(this.temp.values());
					temp.clear();
				}
			}
		}
	}
	private void operation()
	{
		for(String field:this.ops.keySet())
		{	
				String rval=temp.get(field);
				String dtype=this.types.get(field);
				String res=rval;
				switch(dtype)
				{
					case "Integer":
					case "Double":res=String.valueOf(CSVProcess.ops(Double.parseDouble(rval),this.ops.get(field)));break;
					case "String":res=String.valueOf(CSVProcess.ops(rval,this.ops.get(field)));break;
				}
				temp.put(field,res);
		}
	}
	private void format() throws Exception
	{
		for(String field:this.format.keySet())
		{
				String rval=temp.get(field);
				String dtype=this.types.get(field);
				if(dtype.equals("Timestamp"))
				{
					temp.put(field,CSVProcess.dateFormat(rval,this.format.get(field)));
				}
		}
	}
	private void rename()
	{
		for(String field:this.rename.keySet())
		{
			if(temp.containsKey(field))
			{
				temp.put(this.rename.get(field),temp.remove(field));
			}
		}
	}
	private void combine()
	{
		for(String field:this.groups.keySet())
		{
			temp.put(field,buildJSON(field));
		}
	}
	private String buildJSON(String header)
	{
		JSONObject obj=new JSONObject();
		for(String field:this.groups.get(header))
		{
			if(this.rename.containsKey(field))
			{
				obj.put(this.rename.get(field),this.temp.get(field));	
			}
			else
			{
				obj.put(field,this.temp.get(field));
			}
			temp.remove(field);
		}
		return obj.toString();
	}
	private static double ops(double num,String ops)
	{
		String opr=ops.substring(0,3);
		double opd=Double.parseDouble(ops.substring(4,ops.length()));
		switch(opr)
		{
			case "add": return num+opd;
			case "sub": return num-opd;
			case "mul": return num*opd;
			case "div": return num/opd;
		}
		return num;
	}
	private static String ops(String s,String ops)
	{
		String opr=ops.substring(0,3);
		String opd=ops.substring(4,ops.length());
		if(opr.equals("add"))
		{
			return s+opd;
		}
		return s;
	}
	private static String dateFormat(String s,String f) throws Exception
	{
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		Date d=sf.parse(s);
		s=new SimpleDateFormat(f).format(d);
		return s;
	}
	
	public static void main(String args[])throws Exception
	{
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Enter the json file");
		String jname=br.readLine();
		System.out.println("Enter the input csv file");
		String csvinp=br.readLine();
		System.out.println("Enter the output csv file");
		String csvout=br.readLine();
		try
		{
			CSVProcess csv=new CSVProcess(jname,csvinp,csvout);
		}
		catch(IOException ex)
		{
			System.out.println(ex.getMessage());
		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
		}
	}
}
