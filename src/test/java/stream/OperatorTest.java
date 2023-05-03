package stream;

import com.example.User;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.stream.Stream;

public class OperatorTest {

    private final Stream<User> stream = Stream.of(new User("yang"), new User("mario"), new User("jack"));

    @Test
    void mapTest1() {
        // 传入lambda表达式
        stream.map(user -> user.getName()).forEach(System.out::println);
    }

    @Test
    void mapTest2() {
        // 传入方法引用
        stream.map(User::getName).forEach(System.out::println);
    }

    @Test
    void filterTest() {
        stream.filter(user -> user.getName().length() > 4).forEach(System.out::println);
    }

    @Test
    void sortTest() {
        stream.sorted((u1, u2) -> u1.getName().compareTo(u2.getName())).distinct().forEach(System.out::println);
    }


    private final Stream<String> strStream = Stream.of("my", "name", "is", "yang", "quan");

    @Test
    void reduceTest1() {
        // 将流中的字符串拼接
        // 因为没有初始值，所以结果可能为空
        Optional<String> value = strStream.reduce((res, element) -> res + element);
        value.ifPresent(System.out::println);
    }

    @Test
    void reduceTest2() {
        // 第一个参数sum是上次调用的结果，而第二个参数str是从流传递过来的值
        // 看方法注释
        String value = strStream.reduce("whatever", (result, element) -> result + element);
        System.out.println(value);
    }

    @Test
    void reduceTest3() {
        // 求流中单词长度之和
        Integer value = strStream.reduce(0, // 初始值
                (result, element) -> result + element.length(), // 累加器
                (integer, integer2) -> integer + integer2); // 如果在并行流的情况下，多个部分如何进行合并
        System.out.println(value);
    }
}
