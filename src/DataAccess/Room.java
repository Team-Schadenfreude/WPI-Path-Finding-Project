package DataAccess;

public class Room {
	private String name;
	public Room(String name)
	{
		this.name = name;
	}
	public String getName()
	{
		return this.name;
	}
	
	public String toString(){
        return this.name;
	}
}
