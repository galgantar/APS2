import java.util.Scanner;
import java.util.ArrayList;


public class Izziv07 {    
    public static boolean isPrime(int n) {
        if (n == 2)
            return true;
        
        for (int i = 3; i * i <= n; i += 2)
            if (n % i == 0)
                return false;
        return true;
    }

    public static int nextPrime(int n) {
        while (!isPrime(++n)) {}
        return n;
    }

    public static int modularExponantiation(int a, int b, int p) {
        int res = 1;
        for (int i = 0; i < b; i++)
            res = (res * a) % p;
        return res;
    }

    public static boolean isNthPrimitiveRoot(int p, int n, int omega) {
        for (int k = 1; k <= n; k++)
            if (modularExponantiation(omega, k, p) == 1)
                return k == n;
        return false;
    }

    public static ArrayList<Integer> getNthPrimitiveRoots(int p, int n) {
        ArrayList<Integer> primitiveRoots = new ArrayList<>();

        for (int omega = 2; omega < p; omega++) {
            if (isNthPrimitiveRoot(p, n, omega))
                primitiveRoots.add(omega);
        }
        return primitiveRoots;
    }

    public static int[][] getVandermond(int p, int n, int omega) {
        int[][] vandermond = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++) 
                vandermond[i][j] = modularExponantiation(omega, i * j, p);
        return vandermond;
    }
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();

        int p = n;
        ArrayList<Integer> NthPrimitiveRoots = new ArrayList<>();

        while (NthPrimitiveRoots.size() == 0) {
            p = nextPrime(p);
            NthPrimitiveRoots = getNthPrimitiveRoots(p, n);
        }

        int[][] vandermond = getVandermond(p, n, NthPrimitiveRoots.get(0));

        System.out.print(p + ": ");
        for (int root : NthPrimitiveRoots) {
            System.out.print(root + " ");
        }
        System.out.println();
        

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++)
                System.out.printf("%2d ", vandermond[i][j]);
            System.out.println();
        }

        sc.close();
    }
}
