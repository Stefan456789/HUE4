import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

public class Bsp1 {
    public static void main(String[] args) {

        List<Integer> list = new ArrayList<>();
        try {
            List<Integer[]> ints = Files.readAllLines(Paths
                    .get("numbers.csv"))
                    .stream()
                    .map(x -> Arrays.stream(x.split(":"))
                            .map(z -> {
                                int zahl = Integer.MIN_VALUE;
                                try {
                                    zahl = Integer.parseInt(z);
                                } catch (NumberFormatException ex){
                                }
                                return zahl;
                            })
                            .filter(o -> o != Integer.MAX_VALUE)
                            .toArray(Integer[]::new))
                    .collect(Collectors.toList());

            ints.forEach(x -> Collections.addAll(list, x));



        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        Scanner s = new Scanner(System.in);
        System.out.print("Chunks:");
        int chunks = Integer.parseInt(s.nextLine());
        System.out.print("Divider:");
        int div = Integer.parseInt(s.nextLine());

        ThreadPoolExecutor pool = (ThreadPoolExecutor) Executors.newCachedThreadPool();
        for (int i = 1; i <= chunks; i++) {
            int min = list.size() / chunks * (i-1);
            int max = list.size() / chunks * i;

            pool.execute(new Chunk(list.subList(min,max), div));
        }

        pool.shutdown();
    }
}
