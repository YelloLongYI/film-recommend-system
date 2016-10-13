package recommend;

public class FindBestTopK {
	
	private int size;
	private double[] rates,tops;
	private int[] index;
	
	public FindBestTopK(double[] rates,int k){
		this.size=k;
		this.tops=new double[k];
		this.index=new int[k];
		this.rates=rates;
	}
	
	public void findTopK(){
		for(int i=1;i<=size;i++){
			tops[i-1]=rates[i];
			index[i-1]=i;
		}
		adjustHeap();
		for(int i=size+1;i<rates.length;i++){
			if(rates[i]>tops[0]){
				tops[0]=rates[i];
				index[0]=i;
				adjustHeap(0);
			}
		}
		sort();
	}
	
	private void adjustHeap(int idx){
		double temp1=tops[idx];
		int temp2=index[idx];
		int i=idx,j=idx*2+1;
		while(j<size){
			if(j+1<size&&tops[j]>tops[j+1])
				j++;
			if(temp1>tops[j]){
				tops[i]=tops[j];
				index[i]=index[j];
				i=j;
				j=2*i+1;
			}else
				break;
		}
		tops[i]=temp1;
		index[i]=temp2;
	}
	
	private void adjustHeap(){
		for(int i=(size-2)/2;i>=0;i--)
			adjustHeap(i);
	}
	
	private void swap1(int i,int j){
		int tmp=index[i];
		index[i]=index[j];
		index[j]=tmp;
	}
	
	private void swap2(int i,int j){
		double tmp=tops[i];
		tops[i]=tops[j];
		tops[j]=tmp;
	}
	
	private void sort(){
		for(int i=size-1;i>=1;i--)
			for(int j=0;j<i;j++)
				if(tops[j]<tops[j+1]){
					swap1(j,j+1);
					swap2(j,j+1);
				}
	}
	
	public double[] getResRates(){
		return this.tops;
	}
	
	public int[] getResIndex(){
		return this.index;
	}	
	
	
	
	
//	public void print(){
//		for(int i=0;i<size;i++)
//			System.out.print(tops[i]+" ");
//		System.out.println();
//		for(int i=0;i<size;i++)
//			System.out.print(index[i]+" ");
//		System.out.println();
//	}
//	
//	public static void main(String[] args){
//		double[] arr=new double[]{0,-3,-5,1,-2,-4,21,4,59,12,43,19};
//		FindBestTopK test=new FindBestTopK(arr, 5);
//		test.findTopK();
//		test.print();
//	}
}
