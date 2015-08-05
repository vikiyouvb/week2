import java.util.*;

class Teacher
{
	private String name;
	private String id;
	private String doj;
	private double salary;
	private ArrayList<String> classes;
	public Teacher(String name,String id,String doj,double salary)
	{
		this.name=name;
		this.id=id;
		this.doj=doj;
		this.salary=salary;
		classes=new ArrayList<String>();
	}
	public void addClass(String cname)
	{
		classes.add(cname);
	}
	public String toString()
	{
		StringBuilder br=new StringBuilder();
		br.append("Id : "+this.id+"\n");
		br.append("Name : "+this.name+"\n");
		br.append("DOJ : "+this.doj+"\n");
		br.append("Salary : "+this.salary+"\n");
		br.append("Classes : "+this.classes);
		return br.toString();
	}
}
