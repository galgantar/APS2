import java.util.Scanner;

class prog {

    public static void izpisiTablelo(int[][] r) {
        System.out.print("    ");
        for (int k = 1; k < r[0].length; k++)
            System.out.printf("%4d", k);
        System.out.println();

        for (int n = 0; n < r.length; n++) {
            System.out.printf("%4d", n);
            for (int k = 1; k < r[0].length; k++)
                System.out.printf("%4d", r[n][k]);
            System.out.println();
        }
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int K = sc.nextInt();

        int r[][] = new int[N + 1][K + 1];

        for (int k = 0; k <= K; k++)
            r[0][k] = 0;
        for (int n = 0; n <= N; n++)
            r[n][1] = n;

        // solve
        for (int k = 2; k <= K; k++) {
            for (int n = 1; n <= N; n++) {
                int min = Integer.MAX_VALUE;
                for (int x = 1; x <= n; x++) {
                    int parc = Math.max(r[x - 1][k - 1], r[n - x][k]);
                    min = Math.min(min, parc);
                }
                r[n][k] = 1 + min;
            }
        }
        
        izpisiTablelo(r);
    }
}