import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

public class Bsp2 {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        System.out.print("n>");
        int n = Integer.parseInt(s.nextLine());

        ThreadPoolExecutor pool = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);

        List<Future<Integer>> list = new ArrayList<Future<Integer>>();
        for (int i = 1; i <= (n - n % 100)/100; i++) {
            int min = (i - 1) * 100;
            int max = i * 100 - 1;
            int finalMax = max;
            list.add(pool.submit(() -> {
                int x = 0;
                for (int j = min; j <= finalMax; j++) {
                    x += j;
                }
                return x;
            }));
        }
        list.add(pool.submit(() -> {
            int x = 0;
            for (int j = (n - n % 100); j <= n; j++) {
                x += j;
            }
            return x;
        }));


        int sum = list.stream().mapToInt(future -> {
            try {
                return future.get();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            }
        }).sum();
        System.out.println(sum);
        System.out.println("Check: " + (int)(Math.pow(n, 2) + n)/2);
        pool.shutdown();
    }
}
