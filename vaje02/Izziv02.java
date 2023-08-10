import java.util.Scanner;


public class Izziv02 {
    
    public static void printHeap(int t[], int dolzKopica) {
        int w = 1;
        int s = 0;
        while (s < dolzKopica) {
            for (int i = s; i < s + w && i < dolzKopica; i++) {
                System.out.print(t[i] + " ");
            }
            s += w;
            w *= 2;
            if (s < dolzKopica) 
                System.out.print("| ");
        }
        System.out.println();
    }
    
    public static void pogrezni(int t[], int i, int dolzKopica) {
        
        if (2 * i + 2 < dolzKopica) {
            int l = t[2 * i + 1];
            int d = t[2 * i + 2];

            if (t[i] < Math.max(l, d)) {
                if (l >= d) {
                    t[2 * i + 1] = t[i];
                    t[i] = l;
                    pogrezni(t, 2 * i + 1, dolzKopica);
                }
                else {
                    t[2 * i + 2] = t[i];
                    t[i] = d;
                    pogrezni(t, 2 * i + 2, dolzKopica);
                }
            }
        }
        else if (2 * i + 1 < dolzKopica) {
            int l = t[2 * i + 1];
            if (t[i] < l) {
                t[2 * i + 1] = t[i];
                t[i] = l;
                pogrezni(t, 2 * i + 1, dolzKopica);
            }
        }
    }
    
    public static void heapify(int t[]) {
        for (int i = t.length / 2 - 1; i >= 0; i--) {
            //System.out.println("Pogrezam: " + t[i]);
            pogrezni(t, i, t.length);
        }
    }

    public static void heapsort(int t[]) {
        heapify(t);
        printHeap(t, t.length);
        for (int k = t.length - 1; k >= 0; k--) {
            int max = t[0];
            t[0] = t[k];
            t[k] = max;
            
            pogrezni(t, 0, k);
            printHeap(t, k);
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int t[] = new int[n];

        for (int i = 0; i < n; i++)
            t[i] = sc.nextInt();

        //printHeap(t, n);
        heapsort(t);

        // for (int i = 0; i < t.length; i++)
        //     System.out.println(t[i] + " ");
        sc.close();
    }
}
