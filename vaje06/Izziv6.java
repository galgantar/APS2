import java.util.Scanner;

class Matrix {


	private int[][] m;

	public int n; //only square matrices

	public Matrix(int n){

		this.n = n;

		m = new int[n][n];

	}


    //set value at i,j
	public void setV(int i, int j, int val){

		m[i][j] = val;

	}


    // get value at index i,j
	public int v(int i, int j){

		return m[i][j];

	}

	public int elementSum() {
		int s = 0;
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
				s += v(i, j);
		return s;
	}


    // return a square submatrix from this
	public Matrix getSubmatrix(int startRow, int startCol, int dim){

		Matrix subM = new Matrix(dim);

		for (int i = 0; i<dim ; i++ )

			for (int j=0;j<dim ; j++ )

				subM.setV(i,j, m[startRow+i][startCol+j]);



		return subM;

	}


    // write this matrix as a submatrix from b (useful for the result of multiplication)
	public void putSubmatrix(int startRow, int startCol, Matrix b){

		for (int i = 0; i<b.n ; i++ )

			for (int j=0;j<b.n ; j++ )

				setV(startRow+i,startCol+j, b.v(i,j));

	}


    // matrix addition
	public Matrix sum(Matrix b){

		Matrix c = new Matrix(n);

		for(int i = 0; i<n;i++){

			for(int j = 0; j<n;j++){

				c.setV(i, j, m[i][j]+b.v(i, j));

			}

		}

		return c;

	}





    // matrix subtraction
	public Matrix sub(Matrix b){

		Matrix c = new Matrix(n);

		for(int i = 0; i< n;i++){

			for(int j = 0; j<n;j++){

				c.setV(i, j, m[i][j]-b.v(i, j));

			}

		}

		return c;

	}



	//simple multiplication
	public Matrix mult(Matrix b){
        Matrix res = new Matrix(n);
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                for (int k = 0; k < n; k++)
                    res.setV(i, j, res.v(i, j) + this.v(i, k) * b.v(k, j));
        return res;
	}


    // Strassen multiplication
	public Matrix multStrassen(Matrix b, int cutoff){
        if (this.n <= cutoff) {
			return this.mult(b);
		}
		Matrix a11 = this.getSubmatrix(0, 0, n / 2);
		Matrix a12 = this.getSubmatrix(0, n / 2, n / 2);
		Matrix a21 = this.getSubmatrix(n / 2, 0, n / 2);
		Matrix a22 = this.getSubmatrix(n / 2, n / 2, n / 2);

		Matrix b11 = b.getSubmatrix(0, 0, n / 2);
		Matrix b12 = b.getSubmatrix(0, n / 2, n / 2);
		Matrix b21 = b.getSubmatrix(n / 2, 0, n / 2);
		Matrix b22 = b.getSubmatrix(n / 2, n / 2, n / 2);

		Matrix m1 = (a11.sum(a22)).multStrassen(b11.sum(b22), cutoff);
		Matrix m2 = (a21.sum(a22)).multStrassen(b11, cutoff);
		Matrix m3 = a11.multStrassen(b12.sub(b22), cutoff);
		Matrix m4 = a22.multStrassen(b21.sub(b11), cutoff);
		Matrix m5 =(a11.sum(a12)).multStrassen(b22, cutoff);
		Matrix m6 = (a21.sub(a11)).multStrassen(b11.sum(b12), cutoff);
		Matrix m7 = (a12.sub(a22)).multStrassen(b21.sum(b22), cutoff);
		
		System.out.println("m1: " + m1.elementSum());
		System.out.println("m2: " + m2.elementSum());
		System.out.println("m3: " + m3.elementSum());
		System.out.println("m4: " + m4.elementSum());
		System.out.println("m5: " + m5.elementSum());
		System.out.println("m6: " + m6.elementSum());
		System.out.println("m7: " + m7.elementSum());

		Matrix c11 = m1.sum(m4).sub(m5).sum(m7);
		Matrix c12 = m3.sum(m5);
		Matrix c21 = m2.sum(m4);
		Matrix c22 = m1.sub(m2).sum(m3).sum(m6);

		Matrix res = new Matrix(n);
		res.putSubmatrix(0, 0, c11);
		res.putSubmatrix(0, n / 2, c12);
		res.putSubmatrix(n / 2, 0, c21);
		res.putSubmatrix(n / 2, n / 2, c22);

		return res;
	}

	public void print() {
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++)
				System.out.print(v(i,j) + " ");
			System.out.println();
		}
	}
}




public class Izziv6 {

	public static Matrix readMatrix(Scanner sc, int dim) {
		Matrix res = new Matrix(dim);
		for (int i = 0; i < dim; i++)
			for (int j = 0; j < dim; j++)
				res.setV(i, j, sc.nextInt()) ;
		return res;
	}
	
	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		int dim = sc.nextInt();
		int cutoff = sc.nextInt();

		Matrix a = readMatrix(sc, dim);
		Matrix b = readMatrix(sc, dim);

		a.multStrassen(b, cutoff).print();
		sc.close();
	}
}
