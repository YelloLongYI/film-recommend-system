package tools;

import java.io.*;

public class IOWriter {
	OutputStreamWriter osw=null;
	BufferedWriter bw=null;
	
	public IOWriter(String fileName,String code){
		try{
			osw=new OutputStreamWriter(new FileOutputStream(fileName),code);
			bw=new BufferedWriter(osw);
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public String writeLine(String str){
		try{
			bw.write(str+"\n");
		}catch(IOException e){
			e.printStackTrace();
		}finally{
			return str;
		}
	}
	
	public void close(){
		try{
			if(bw!=null)
				bw.close();
			if(osw!=null)
				osw.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}
