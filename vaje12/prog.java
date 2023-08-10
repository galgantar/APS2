import java.util.*;



class Vozlisce {
    public int id;
    public ArrayList<Povezava> vhodne;
    public ArrayList<Povezava> izhodne;

    public Vozlisce(int id) {
        this.id = id;
        this.vhodne = new ArrayList<>();
        this.izhodne = new ArrayList<>();
    }

    @Override
    public String toString() {
        return String.format("(Vozl: %d) ", id) + izhodne + " " + vhodne;
    }
}

class Povezava {
    public int u;
    public int v;
    public int c;

    public Povezava(int u, int v, int c) {
        this.u = u;
        this.v = v;
        this.c = c;
    }

    @Override
    public String toString() {
        return String.format("(u: %d, v:%d, c:%d)", u, v, c);
    }
}

class prog {

    public static void izpisiResitev(ArrayList<Integer> res, int h) {
        System.out.printf("h%d: ", h);

        for (Integer a : res)
            if (a == Integer.MAX_VALUE)
                System.out.print("Inf ");
            else
                System.out.print(a + " ");
        
        System.out.println();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();

        ArrayList<Vozlisce> G = new ArrayList<>();
        for (int i = 0; i < n; i++)
            G.add(new Vozlisce(i));
        
        while (sc.hasNextInt()) {
            int u = sc.nextInt();
            int v = sc.nextInt();
            int c = sc.nextInt();
            G.get(u).izhodne.add(new Povezava(u, v, c));
            G.get(v).vhodne.add(new Povezava(u, v, c));
        }

        ArrayList<Integer> next = new ArrayList<>();
        next.add(0);
        for (int i = 1; i < n; i++) {
            next.add(Integer.MAX_VALUE);
        }

        ArrayList<Integer> prev = new ArrayList<>();
        int h = 0;

        do {
            izpisiResitev(next, h);
            prev = next;
            next = new ArrayList<>();
            next.add(0);
            h++;

            for (int i = 1; i < n; i++) {
                int minDolz = prev.get(i);

                for (Povezava p : G.get(i).vhodne) {
                    if (prev.get(p.u) != Integer.MAX_VALUE) {
                        minDolz = Math.min(minDolz, prev.get(p.u) + p.c);
                    }
                }
                next.add(minDolz);
            }
        } while(!prev.equals(next));
    }
}