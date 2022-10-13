import java.util.List;

public class Chunk implements Runnable{

    private final List<Integer> list;
    private final int div;


    public Chunk(List<Integer> list, int div) {
        this.list = list;
        this.div = div;
    }

    @Override
    public void run() {
        list.stream().filter(x -> x % div == 0).forEach(System.out::println);
    }
}
