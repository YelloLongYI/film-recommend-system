package dataStructure;

import java.util.Random;

public class Matrix {
	
	public double[][] matrix;
	int n,m;
	boolean isTranspose=false;
	
	public Matrix(int n,int m,boolean isTranspose){
		matrix=new double[n][m];
		this.n=n;
		this.m=m;
		this.isTranspose=isTranspose;
	}
	
	public void setTranpose(boolean isTranspose){
		this.isTranspose=isTranspose;
	}
	
	public void initMatrix(){
		Random random=new Random();
		for(int i=0;i<this.n;i++)
			for(int j=0;j<this.m;j++)
				this.matrix[i][j]=random.nextDouble();
	}
	
	public double[] getRow(int row){
		if(isTranspose==false)
			return matrix[row];
		double[] rowElement=new double[n];
		for(int i=0;i<this.n;i++)
			rowElement[i]=this.matrix[i][row];
		return rowElement;
	}
	
	public double[] getCol(int col){
		if(isTranspose==true)
			return matrix[col];
		double[] colElement=new double[n];
		for(int i=0;i<this.m;i++)
			colElement[i]=this.matrix[i][col];
		return colElement;
	}
	
	public void setRow(int row,double[] rowElements){
		if(isTranspose==false)
			this.matrix[row]=rowElements;
		else{
			if(rowElements.length!=this.n)
				throw new RuntimeException("dimension is unequal");
			else{
				for(int i=0;i<this.n;i++)
					this.matrix[i][row]=rowElements[i];
			}
		}
	}
	
	public void setCol(int col,double[] colElements){
		if(isTranspose==true)
			this.matrix[col]=colElements;
		else{
			if(colElements.length!=this.n)
				throw new RuntimeException("dimension is unequal");
			else{
				for(int i=0;i<this.n;i++)
					this.matrix[i][col]=colElements[i];
			}
		}
	}
	
	public int getRowSize(){
		return isTranspose==false?n:m;
	}
	
	public int getColSize(){
		return isTranspose==false?m:n;
	}
	
	public int getRowUpper(){
		return isTranspose==false?n-1:m-1;
	}
	
	public int getColUpper(){
		return isTranspose==false?m-1:n-1;
	}
	
	public void printMatrix(){
		for(int i=0;i<n;i++){
			for(int j=0;j<m;j++)
				System.out.print(matrix[i][j]+" ");
			System.out.println();
		}
	}
}
