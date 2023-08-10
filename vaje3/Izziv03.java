import java.util.Scanner;


public class Izziv03 {
    
    public static int[] preberiStevila(Scanner sc, int n) {
        int t[] = new int[n];
        for (int i = 0; i < n; i++)
            t[i] = sc.nextInt();
        return t;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        int n = sc.nextInt();
        int m = sc.nextInt();
        int a[] = preberiStevila(sc, n);
        int b[] = preberiStevila(sc, m);

        int r[] = new int[n + m];
        String moves = new String();

        int i = 0;
        int j = 0;
        int k = 0;
        while (i < n || j < m) {
            if (i == n) {
                r[k++] = b[j++];
                moves += "b";
            }
            else if (j == m) {
                r[k++] = a[i++];
                moves += "a";
            }
            else if (a[i] <= b[j]) {
                r[k++] = a[i++];
                moves += "a";
            }
            else {
                r[k++] = b[j++];
                moves += "b";
            }
        }

        System.out.println(moves);
        for (int t = 0; t < r.length; t++)
            System.out.print(r[t] + " ");
        System.out.println();
        
        sc.close();
    }
}