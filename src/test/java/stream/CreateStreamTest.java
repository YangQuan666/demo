package stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.example.User;

public class CreateStreamTest {

    @Test
    void streamOfTest() {

        // int
        IntStream intStream = IntStream.of(1, 2, 3, 4, 5);
        Assertions.assertEquals(5, intStream.count());

        // double
        DoubleStream doubleStream = DoubleStream.of(3.14159, 2.718, 1.618);
        Assertions.assertEquals(3, doubleStream.count());

        // String
        Stream<String> stringStream = Stream.of("hello", "world");
        Assertions.assertEquals(2, stringStream.count());

        // 对象
        Stream<User> userStream = Stream.of(new User("yang"), new User("quan"));
        Assertions.assertEquals(2, userStream.count());

    }

    @Test
    void streamGenerateTest() {

        Supplier<String> supplier = () -> LocalDateTime.now().toString();

        // 将supplier传给generate方法
        Stream<String> stream = Stream.generate(supplier);
        stream.limit(5).forEach(System.out::println);
    }

    @Test
    void streamIterateTest() {

        // 如下的逻辑相当于 for(int i = 0; ; i++)
        Stream<Integer> stream1 = Stream.iterate(1, i -> i + 1);
        stream1.limit(10).forEach(System.out::println);

        // 如下的逻辑相当于 for(int i = 0; i < 10; i +=2 )
        Stream<Integer> stream2 = Stream.iterate(1, i -> i < 10, i -> i + 2);
        stream2.forEach(System.out::println);
    }

    // 集合类转流
    @Test
    void collectionToStreamTest() {

        // List 转 Stream
        List<User> list = List.of(new User("yang"), new User("quan"));
        Stream<User> listStream = list.stream();

        // Set 转 Stream
        Set<String> set = Set.of("hello", "world");
        Stream<String> setStream = set.stream();

        // Map 转 Stream
        Map<String, Double> map = Map.of("pi", 3.14159, "e", 2.718);
        Stream<Map.Entry<String, Double>> mapStream = map.entrySet().stream();
    }
}
