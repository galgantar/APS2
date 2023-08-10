import java.util.Scanner;


public class Izziv04 {
    
    public static int countOnes(int k) {
        int c = 0;
        for (int i = 0; i < 32; i++)
            if ((k & (1 << i)) != 0)
                c++;
        return c;
    }
    
    public static void printArray(int[] t) {
        for (int i = 0; i < t.length; i++)
            System.out.print(t[i] + " ");
        System.out.println();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int t[] = new int[n];
        int r[] = new int[n];
        for (int i = 0; i < n; i++)
            t[i] = sc.nextInt();

        int c1[] = new int[33];
        int c2[] = new int[33];

        for (int i = 0; i < n; i++)
            c1[countOnes(t[i])]++;
        
        for (int i = 1; i < 33; i++)
            c2[i] = c2[i - 1] + c1[i - 1];

        // printArray(c1);
        // printArray(c2);
        
        int r1[] = new int[n];
        int r2[] = new int[n];

        for (int i = 0; i < n; i++) {
            int index = c2[countOnes(t[i])]++;
            //System.out.println(t[i] + " -> " + index);
            r[index] = t[i];
            r1[i] = t[i];
            r2[i] = c2[countOnes(t[i])] - 1;
        }
        

        // v testih so (ne vem zakaj) rezultati obrnjeni
        for (int i = 0; i < n; i++)
            System.out.printf("(%d,%d)\n", r1[n - i - 1], r2[n - i - 1]);


        printArray(r);        
        sc.close();
    }
}