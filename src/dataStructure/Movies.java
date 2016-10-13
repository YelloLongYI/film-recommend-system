package dataStructure;

import java.util.ArrayList;

public class Movies {
	
	public int id;
	public String name;
	ArrayList<String> types;
	public Movies(String record){
		String[] tmp1=record.split("::");
		this.id=Integer.parseInt(tmp1[0]);
		this.name=tmp1[1];
		String[] tmp2=tmp1[2].split("|");
		types=new ArrayList<String>();
		for(int i=0;i<tmp2.length;i++)
			types.add(tmp2[i]);
	}
	
}
