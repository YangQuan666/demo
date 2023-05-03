package lambda;

import org.junit.jupiter.api.Test;

public class CustomFunctionalTest {

    @Test
    void test() {
        // 定义
        TriFunction<Integer, Boolean, Double, String> triFunction = (i, b, d) -> String.format("i=%d,b=%b,d=%.2f", i, b, d);

        // 调用
        String str = triFunction.apply(1, true, 3.14);
        System.out.println(str);
    }
}