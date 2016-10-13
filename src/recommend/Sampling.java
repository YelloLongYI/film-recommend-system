package recommend;

import java.util.Random;
import java.io.*;
import tools.*;

public class Sampling {
	
	private static double sampleRate=0.2;
	
	public static void setRate(double rate){
		sampleRate=rate;
	}
	
	public static void startSampling(String dataName,String saveName1,String saveName2){
		Random random=new Random();
		IOReader ir=new IOReader(dataName,"UTF-8");
		IOWriter iw1=new IOWriter(saveName1,"UTF-8");
		IOWriter iw2=new IOWriter(saveName2,"UTF-8");
		String str;
		double prob;
		while((str=ir.getLine())!=null){
			prob=random.nextDouble();
			if(prob>sampleRate)
				iw1.writeLine(str);
			else
				iw2.writeLine(str);
		}
		ir.close();
		iw1.close();
		iw2.close();
		
		
	}

	public static void main(String[] args) {
		startSampling("data\\ratings.dat","data\\ratings_sample_train.dat","data\\ratings_sample_test.dat");
		startSampling("data\\ratings_sample_train.dat","data\\ratings_sample_train1.dat","data\\ratings"+"_sample_develop.dat");
		
		System.out.println("ok");

	}

}
