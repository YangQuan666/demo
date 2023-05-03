package stream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

public class ForLoopTest {

    private static List<String> list;
    private static final int size = 30000;

    @BeforeAll
    static void setList() {

        list = new LinkedList<>();

        for (int i = 0; i < size; i++) {
            list.add("str" + i);
        }
    }

    @Test
    void forTest() {
        for (int i = 0; i < list.size(); i++) {
            String str = list.get(i);
            System.out.println(str);
        }
    }

    @Test
    void forInTest() {
        for (String str : list) {
            System.out.println(str);
        }
    }

    @Test
    void forEachTest() {
        list.forEach(System.out::println);
    }
}
