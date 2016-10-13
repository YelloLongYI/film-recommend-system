package dataStructure;
import java.util.*;

import recommend.FindBestTopK;
import tools.IOWriter;

public class IteratorOperate {
	
	private static double lamda1=0,lamda2=0,step=0.1;
	private static int iterNum=5;
	private static int recomNum=10;
	
	public static void setLamda(double l1,double l2){
		lamda1=l1;
		lamda2=l2;
	}
	
	public static void setStep(double st){
		step=st;
	}
	
	public static void setIterNum(int num){
		iterNum=num;
	}
	
	private static double matrixMultiplyElement(Matrix matrix1,Matrix matrix2,int row,int col){
//		System.out.println(matrix1.getRowSize()+" "+matrix1.getColSize());
//		System.out.println(matrix2.getRowSize()+" "+matrix2.getColSize());
//		System.out.println(row+" "+col);
		double[] rowElements=matrix1.getRow(row),colElements=matrix2.getCol(col);
		if(rowElements.length!=colElements.length)
			throw new RuntimeException("dimension error");
		else{
			if(row>matrix1.getRowSize()||col>matrix2.getColSize())
				return 0;	
			double res=0;
			for(int i=0;i<rowElements.length;i++)
				res+=rowElements[i]*colElements[i];
			return res;
		}
	}
	
	/**
	 * 单个样本损失
	 */
	private static double getError(Matrix user_matrix,Matrix item_matrix,Ratings rating){
		double error=1.0*rating.rate-matrixMultiplyElement(user_matrix, item_matrix, rating.uid, rating.mid);
		return error;
	}
	
	/**
	 * 模型在测试集上总损失
	 */
	private static double RMSE(Matrix user_matrix,Matrix item_matrix,List<Ratings> testRates){
		double loss=0,error=0;
		Ratings temp;
		Iterator<Ratings> iter=testRates.iterator();
		while(iter.hasNext()){
			temp=iter.next();		
			error=getError(user_matrix, item_matrix, temp);
			loss+=error*error;
		}
		return Math.sqrt(loss/testRates.size());
	}
	
	private static double evaluate(Matrix user_matrix,Matrix item_matrix,List<Ratings> testRates){
		return RMSE(user_matrix, item_matrix, testRates);
	}
	
	
	
	/**
	 * 更新参数
	 */
	private static void update(Matrix user_matrix,Matrix item_matrix,double error,int u,int i){
		if(u>user_matrix.getRowUpper()||i>item_matrix.getColUpper())
			return;
		double puk,qki;
		double[] pu=user_matrix.getRow(u),qi=item_matrix.getCol(i);
		int k=user_matrix.getColSize();
		for(int idx=0;idx<k;idx++){
			puk=pu[idx];
			qki=qi[idx];
			pu[idx]=puk+step*(error*qki-lamda1*puk);
			qi[idx]=qki+step*(error*puk-lamda2*qki);
		}
		user_matrix.setRow(u, pu);
		item_matrix.setCol(i, qi);
	}
	
	public static void startGradientDescentTrain(Matrix user_matrix,Matrix item_matrix,List<Ratings> trainRates,
			List<Ratings> testRates){
		int iterCount=0;
		Ratings temp;
		while(iterCount<iterNum){
			Iterator<Ratings> iter=trainRates.iterator();
			while(iter.hasNext()){
				temp=iter.next();
				update(user_matrix, item_matrix, getError(user_matrix,item_matrix,temp), temp.uid, temp.mid);
			}
			iterCount++;
			System.out.format("iterCount = %4d ,loss = %4f\n", iterCount,evaluate(user_matrix,item_matrix,testRates));
		}
	}
	
	public static void predict(Matrix user_matrix,Matrix item_matrix,List<Ratings> testRates,String savePath){
		
		Iterator<Ratings> iter=testRates.iterator();
		Ratings temp;
		double score;
		IOWriter iw=new IOWriter(savePath, "UTF-8");
		while(iter.hasNext()){
			temp=iter.next();
			score=matrixMultiplyElement(user_matrix, item_matrix, temp.uid, temp.mid);
			iw.writeLine(temp.uid+" "+temp.mid+" "+score);
		}
		iw.close();	
	}
	
	private static double[] getPreference(Matrix user_matrix,Matrix item_matrix,int u){
		int size=item_matrix.getColSize();
		double[] scores=new double[size-1];
		for(int i=1;i<size;i++){
			scores[i-1]=matrixMultiplyElement(user_matrix, item_matrix, u, i);
		}
		return scores;
	}
	
	public static void recommend(Matrix user_matrix,Matrix item_matrix,HashMap<Integer,HashSet<Integer>> exists1,
			int k,double threshold,String savePath,HashMap<Integer,HashSet<Integer>> exists2){
		
		int usrNum=user_matrix.getRowSize();
		double[] scores,topk;
		int[] index;
		FindBestTopK bests;
		IOWriter iw=new IOWriter(savePath, "UTF-8");
		int right=0,sum=0;
		for(int u=1;u<usrNum;u++){
			scores=getPreference(user_matrix, item_matrix, u);
			bests=new FindBestTopK(scores, k);
			bests.findTopK();
			topk=bests.getResRates();
			index=bests.getResIndex();
			for(int i=0;i<k;i++){
				if(topk[i]<threshold)
					break;
				if(!exists1.get(u).contains(index[i])){
					iw.writeLine(u+" "+index[i]+" "+topk[i]);
					sum++;
					if(exists2.containsKey(u)&&exists2.get(u).contains(index[i]))
						right++;
				}
			}
		}
		System.out.println(1.0*right/sum);
		iw.close();
		
	}
		
}
