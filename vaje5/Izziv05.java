import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;



public class Izziv05 {
    public static Integer najvecjaVsota(List<Integer> t) {
        if (t.size() == 1) {
            System.out.println(t + ": " + t.get(0));
            return t.get(0);
        }
        
        int delim = t.size() % 2 == 0 ? t.size() / 2 : t.size() / 2 + 1;
        int maxLeft = najvecjaVsota(t.subList(0, delim));
        int maxRigth = najvecjaVsota(t.subList(delim, t.size()));

        int l = delim - 1;
        int r = delim;
        int maxBoth = t.get(l--) + t.get(r++);
        int currentSum = maxBoth;

        while (l >= 0) {
            currentSum += t.get(l--);
            maxBoth = Math.max(maxBoth, currentSum);
        }
        currentSum = maxBoth;
        while (r < t.size()) {
            currentSum += t.get(r++);
            maxBoth = Math.max(maxBoth, currentSum);
        }

        int result = Math.max(maxLeft, Math.max(maxRigth, maxBoth));
        System.out.println(t + ": " + result);
        return result;
    }
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        ArrayList<Integer> t = new ArrayList<>();
        while (sc.hasNextInt()) {
            t.add(sc.nextInt());
        }

        najvecjaVsota(t);
    }
}