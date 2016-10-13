package recommend;
import tools.*;

import java.util.*;

import dataStructure.*;

public class ReadDatas {
	
	public static int[] getRatings(String dataName,List<Ratings> ratings){
		IOReader ir=new IOReader(dataName,"UTF-8");
		Ratings temp;
		int rowSize=0,colSize=0;
		String str;
		while((str=ir.getLine())!=null){
			temp=new Ratings(str);
			rowSize=Math.max(rowSize, temp.uid);
			colSize=Math.max(colSize, temp.mid);
			ratings.add(temp);
		}
		return new int[]{rowSize,colSize};
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
