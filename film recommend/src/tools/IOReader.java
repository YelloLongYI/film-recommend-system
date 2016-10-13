package tools;
import java.io.*;

public class IOReader {
	InputStreamReader isr=null;
	BufferedReader br=null;
	
	public IOReader(String fileName,String code){
		try{
			isr=new InputStreamReader(new FileInputStream(fileName),code);
			br=new BufferedReader(isr);
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public String getLine(){
		String str=null;
		try{
			str=br.readLine();
		}catch(IOException e){
			e.printStackTrace();
		}finally{
			return str;
		}
	}
	
	public void close(){
		try{
			if(br!=null)
				br.close();
			if(isr!=null)
				isr.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}
