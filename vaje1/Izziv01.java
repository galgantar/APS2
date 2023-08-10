import java.util.Random;


public class Izziv01 {

    public static int[] generateTable(int n) {
        int t[] = new int[n];
        for (int i = 1; i <= n; i++) {
            t[i - 1] = i;
        }
        return t;
    }

    public static int findLinear(int[] a, int v) {
        for (int i = 0; i < a.length; i++) {
            if (a[i] == v) {
                return i;
            }
        }
        return -1;
    }

    public static int findBinary(int[] a, int l, int r, int v) {
        if (r < l) {
            System.out.println("NOT FOUND " + v);
            return -1;
        }
        
        int m = (l + r) / 2;
        if (a[m] == v) {
            return m;
        }
        else if (a[m] < v) {
            return findBinary(a, m + 1, r, v);
        }
        return findBinary(a, l, m - 1, v);
    }

    public static long timeLinear(int n) {
        int t[] = generateTable(n);
        Random rand = new Random(); 

        long startTime = System.nanoTime();
        for (int i = 0; i < 1000; i++) {
            int x = rand.nextInt(n) + 1;
            int xi = findLinear(t, x);
        }
        long executionTime = System.nanoTime() - startTime;
        return executionTime / 1000;
    }

    public static long timeBinary(int n) {
        int t[] = generateTable(n);
        Random rand = new Random(); 

        long startTime = System.nanoTime();
        for (int i = 0; i < 1000; i++) {
            int x = rand.nextInt(n) + 1;
            int xi = findBinary(t, 0, n - 1, x);
        }
        long executionTime = System.nanoTime() - startTime;
        return executionTime / 1000;
    }

    public static void main(String[] args) {
        System.out.println("n          | linearno   | dvojisko");
        for (int n = 1000; n <= 100000; n += 1000) {
            System.out.printf("%-10s | %-10s | %-10s \n", n, timeLinear(n), timeBinary(n));
        }
    }
}
