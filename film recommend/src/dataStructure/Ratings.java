package dataStructure;

public class Ratings {
	
	public int uid;
	public int mid;
	public int rate;
	public Ratings(String record){
		String[] tmp=record.split("::");
		this.uid=Integer.parseInt(tmp[0]);
		this.mid=Integer.parseInt(tmp[1]);
		this.rate=Integer.parseInt(tmp[2]);
	}
	
}
