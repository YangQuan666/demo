package stream;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;
import java.util.SortedSet;
import java.util.TreeSet;

public class StreamTest {


    // 不写注释，尝试理解代码含义
    @Test
    void randomNumber() {
        SortedSet<Integer> sortedSet = new TreeSet<>();
        Random rand = new Random(47);
        while (sortedSet.size() < 7) {
            int r = rand.nextInt(20);
            if (r < 5) {
                continue;
            }
            sortedSet.add(r);
        }
        System.out.println(sortedSet);
    }

    // 流式编程
    @Test
    void randomNumberStream() {
        int[] array = new Random(47)
                .ints(5, 20)
                .distinct()
                .limit(7)
                .sorted()
                .toArray();

        System.out.println(Arrays.toString(array));
    }
}
