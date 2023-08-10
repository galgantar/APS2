import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;


class Resitev {
    public int volumen;
    public int cena;

    public Resitev(int volumen, int cena) {
        this.volumen = volumen;
        this.cena = cena;
    }

    @Override
    public String toString() {
        return String.format("(%d, %d)", volumen, cena);
    }
}


public class prog {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        int W = sc.nextInt();
        int n = sc.nextInt();

        int[] volumni = new int[n];
        int[] cene = new int[n];

        for (int i = 0; i < n; i++){
            volumni[i] = sc.nextInt();
            cene[i] = sc.nextInt();
        }

        ArrayList<Resitev> resitve = new ArrayList<>();
        resitve.add(new Resitev(0, 0));
        
        System.out.println("0: " + resitve.get(0));

        for (int i = 0; i < n; i++) {
            int velikost = resitve.size();
            for (int j = 0; j < velikost; j++) {
                Resitev dodana = new Resitev(volumni[i], cene[i]);
                resitve.add(new Resitev(resitve.get(j).volumen + dodana.volumen, resitve.get(j).cena + dodana.cena));
            }
            
            Collections.sort(resitve, (r1, r2) -> r2.cena - r1.cena); // najprej po mn pomembni
            Collections.sort(resitve, (r1, r2) -> r1.volumen - r2.volumen);
            
            int k = 1;
            while (k < resitve.size()) {
                if (resitve.get(k).cena <= resitve.get(k - 1).cena || resitve.get(k).volumen > W) {
                    resitve.remove(k);
                }
                else {
                    k++;
                }
            }
        
            System.out.print(i+1 + ": ");
            for (Resitev r : resitve)
                System.out.print(r + " ");
            
            System.out.println();
        }
    }
}
