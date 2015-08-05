import java.util.*;
class Student
{
	private String doj;
	private String id;
	private ArrayList<Mark> marks;
	private String name;
	private String std;
	public Student(String doj,String id,String name,String std)
	{
		marks=new ArrayList<Mark>();
		this.doj=doj;
		this.id=id;
		this.name=name;
		this.std=std;
	}
	private class Mark
	{
		String subject;
		int mark;
		public Mark(String subject,int mark)
		{
			this.subject=subject;
			this.mark=mark;
		}
	}
	public void addSubject(String subject,int mark)
	{
		Mark m=new Mark(subject,mark);
		marks.add(m);
	}
	public String toString()
	{
		StringBuilder br=new StringBuilder();
		br.append("Id : "+this.id+"\n");
		br.append("Name : "+this.name+"\n");
		br.append("Std : "+this.std+"\n");
		br.append("DOJ : "+this.doj+"\n");
		for(Mark m:marks)
		{
			br.append(m.subject+" : "+m.mark);
		}
		return br.toString();
	}
}
