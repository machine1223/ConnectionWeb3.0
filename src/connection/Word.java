package connection;

public class Word {
	public String orignName;
	public String targetName;
	public String content;
	public String date;
	
	public Word()
	{
		this.orignName = "";
		this.targetName = "";
		this.content = "";
		this.date = "";
	}
	
	public Word(String orign,String target,String content,String date)
	{
		this.orignName = orign;
		this.targetName = target;
		this.content = content;
		this.date = date;
	}
	
	public boolean isIncluded(String name)
	{
		if(this.orignName.equals(name) || this.targetName.equals(name))
			return true;
		return false;
	}
}
