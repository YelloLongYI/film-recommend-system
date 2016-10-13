package dataStructure;

public class Users {
	
	public int id;
	public int gender;
	public int age;
	public int occupation;
	public Users(String record){
		String[] tmp=record.split("::");
		this.id=Integer.parseInt(tmp[0]);
		this.gender=tmp[1].equals("F")?1:0;
		this.occupation=Integer.parseInt(tmp[2]);
	}
	
}
