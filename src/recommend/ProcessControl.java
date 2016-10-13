package recommend;
import dataStructure.*;
import tools.*;
import java.util.*;

public class ProcessControl {
	
	private static String trainPath="data\\ratings_sample_train1.dat";
	private static String developPath="data\\ratings_sample_develop.dat";
	private static String testPath="data\\ratings_sample_test.dat";
	
	private static int k=5;
	
	public static void setK(int kval){
		k=kval;
	}
	
	private static void getExists(List<Ratings> ratings,HashMap<Integer,HashSet<Integer>> exists){
		for(Ratings rating:ratings){
			if(!exists.containsKey(rating.uid))
				exists.put(rating.uid, new HashSet<Integer>());
			exists.get(rating.uid).add(rating.mid);
		}
	}
	
	public static void process1(){
		List<Ratings> trainList=new ArrayList<Ratings>(),developList=new ArrayList<Ratings>(),testList=new ArrayList<Ratings>();
		int[] size=ReadDatas.getRatings(trainPath,trainList);
		ReadDatas.getRatings(developPath,developList);
		ReadDatas.getRatings(testPath,testList);
		
		
		
		Matrix user_matrix=new Matrix(size[0]+1,k,false),item_matrix=new Matrix(size[1]+1,k,true);
		user_matrix.initMatrix();
		item_matrix.initMatrix();
		System.out.println(size[0]+" "+size[1]);
		
		
		
		HashMap<Integer,HashSet<Integer>> exists1=new HashMap<Integer,HashSet<Integer>>();
		HashMap<Integer,HashSet<Integer>> exists2=new HashMap<Integer,HashSet<Integer>>();
		getExists(trainList,exists1);
		getExists(developList,exists1);
		getExists(testList,exists2);
		
		IteratorOperate.startGradientDescentTrain(user_matrix, item_matrix, trainList, developList);
		IteratorOperate.predict(user_matrix, item_matrix, testList, "data\\predict.dat");
		//IteratorOperate.recommend(user_matrix, item_matrix, exists1, 10, 5, "data\\recommend.dat",exists2);
		
	}

	public static void main(String[] args) {
		
		ProcessControl.setK(18);
		IteratorOperate.setIterNum(200);
		IteratorOperate.setLamda(0.1, 0.1);
		IteratorOperate.setStep(0.01);
		
		process1();

	}

}
