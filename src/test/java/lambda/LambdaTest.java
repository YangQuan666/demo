package lambda;

import org.junit.jupiter.api.Test;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class LambdaTest {

    @Test
    void runnableTest() {
        Runnable runnable = () -> System.out.println("Runnable#run");

        runnable.run();
    }

    @Test
    void supplierTest() {
        Supplier<String> supplier = () -> "Supplier-get";

        String str = supplier.get();
    }


    @Test
    void consumerTest() {
        Consumer<String> consumer = (str) -> System.out.println("Consumer#accept: " + str);
        // 如果只有一个参数则可以省略括号，这样写也是可以的: str -> System.out.println("Consumer#accept: " + str);

        consumer.accept("hello");
    }

    @Test
    void functionalTest() {
        Function<String, String> function = str -> "Function#apply: " + str;
        // 等价于 (str) -> { return "Function#apply: " + str; }

        String res = function.apply("hello");
    }

    Function<String, String> function1 = s -> s + "无括号";

    Function<String, String> function2 = (s) -> s + "有括号";

    BiConsumer<String, Integer> biConsumer1 = (s, integer) -> s = s + integer;

    BiConsumer<String, Integer> biConsumer2 = (s, integer) -> {
        System.out.println(s);
        System.out.println(integer);
    };
}
