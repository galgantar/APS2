import java.util.*;


class Complex{
	double re;
	double im;

    public Complex(double real, double imag) {
        re = real;
        im = imag;
    }

    public String toString() {
    	double tRe = (double)Math.round(re * 100000) / 100000;
    	double tIm = (double)Math.round(im * 100000) / 100000;
        if (tIm == 0) return tRe + "";
        if (tRe == 0) return tIm + "i";
        if (tIm <  0) return tRe + "-" + (-tIm) + "i";
        return tRe + "+" + tIm + "i";
    }

	public Complex conj() {
		return new Complex(re, -im);
	}

    // sestevanje
    public Complex plus(Complex b) {
        Complex a = this;
        double real = a.re + b.re;
        double imag = a.im + b.im;
        return new Complex(real, imag);
    }

    // odstevanje
    public Complex minus(Complex b) {
        Complex a = this;
        double real = a.re - b.re;
        double imag = a.im - b.im;
        return new Complex(real, imag);
    }

    // mnozenje z drugim kompleksnim stevilo
    public Complex times(Complex b) {
        Complex a = this;
        double real = a.re * b.re - a.im * b.im;
        double imag = a.re * b.im + a.im * b.re;
        return new Complex(real, imag);
    }

    // mnozenje z realnim stevilom
    public Complex times(double alpha) {
        return new Complex(alpha * re, alpha * im);
    }

    // reciprocna vrednost kompleksnega stevila
    public Complex reciprocal() {
        double scale = re*re + im*im;
        return new Complex(re / scale, -im / scale);
    }

    // deljenje
    public Complex divides(Complex b) {
        Complex a = this;
        return a.times(b.reciprocal());
    }

    // e^this
    public Complex exp() {
        return new Complex(Math.exp(re) * Math.cos(im), Math.exp(re) * Math.sin(im));
    }


    //potenca komplesnega stevila
    public Complex pow(int k) {

    	Complex c = new Complex(1,0);
    	for (int i = 0; i <k ; i++) {
			c = c.times(this);
		}
    	return c;
    }
}

class prog {

    public static Complex[] everyOther(Complex[] a, int start) {
        Complex[] res = new Complex[a.length / 2];
        for (int i = 0; i < a.length / 2; i++)
            res[i] = a[start + 2 * i];
        return res;
    }

    public static Complex[] scanArrayWithZeros(Scanner sc, int n, int size) {
        Complex[] res = new Complex[size];
        
        for (int i = 0; i < n; i++)
            res[i] = new Complex(sc.nextInt(), 0);
        for (int i = n; i < size; i++)   
            res[i] = new Complex(0, 0);
        return res;
    }
    
    public static void printArray(Complex[] a) {
        for (int i = 0; i < a.length; i++) {
            System.out.print(a[i].toString() + " ");
        }
        System.out.println();
    }

    public static Complex[] recursiveFFT(Complex[] a) {
        int n = a.length;
        if (n == 1) {
            return a;
        }
        
        Complex[] ys = recursiveFFT(everyOther(a, 0));
        if (ys.length > 1)
        printArray(ys);

        Complex[] yl = recursiveFFT(everyOther(a, 1));
        if (yl.length > 1)
            printArray(yl);
        
        Complex w = (new Complex(0, 2 * Math.PI / n)).exp();
        Complex wk = new Complex(1, 0);

        Complex[] y = new Complex[n];

        for (int k = 0; k < n / 2; k++) {
            y[k] = ys[k].plus(wk.times(yl[k]));
            y[k + n / 2] = ys[k].minus(wk.times(yl[k]));
            wk = wk.times(w);
        }

        return y;
    }

    public static Complex[] inverseFFT(Complex[] a) {
        int n = a.length;
        if (n == 1) {
            return a;
        }
        
        Complex[] ys = inverseFFT(everyOther(a, 0));
        if (ys.length > 1)
            printArray(ys);
        Complex[] yl = inverseFFT(everyOther(a, 1));
        if (yl.length > 1)
            printArray(yl);
        
        Complex w = (new Complex(0, -2 * Math.PI / n)).exp();
        Complex wk = new Complex(1, 0);

        Complex[] y = new Complex[n];

        for (int k = 0; k < n / 2; k++) {
            y[k] = ys[k].plus(wk.times(yl[k]));
            y[k + n / 2] = ys[k].minus(wk.times(yl[k]));
            wk = wk.times(w);
        }

        return y;
    }

    public static Complex[] componentMul(Complex[] a, Complex[] b) {
        Complex[] res = new Complex[a.length];
        for (int i = 0; i < a.length; i++)
            res[i] = a[i].times(b[i]);
        return res;
    }

    public static int firstLargerPower(int n) {
        int res = 2;
        while (res < n)
            res *= 2;
        return res;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int m = sc.nextInt();
        int n = firstLargerPower(2 * m);


        Complex[] a = scanArrayWithZeros(sc, m, n);
        Complex[] b = scanArrayWithZeros(sc, m, n);

        Complex[] ya = recursiveFFT(a);
        printArray(ya);
        Complex[] yb = recursiveFFT(b);
        printArray(yb);

        Complex[] mul = componentMul(ya, yb);

        Complex[] res = inverseFFT(mul);
        
        printArray(res);

        for (int i = 0; i < res.length; i++)
            res[i] = res[i].times(1.0 / n);
        printArray(res);
    }
}