package lambda.compose;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.function.Function;
import java.util.function.Predicate;

public class FunctionalComposeTest {

    @Test
    void andThenTest() {
        // 定义原函数
        Function<String, String> function = str -> str + " apply";

        // 传入andThen函数并执行原函数
        String res = function.andThen(s -> s + " andThen").apply("hello");

        Assertions.assertEquals(res, "hello apply andThen");
    }

    @Test
    void composeTest() {
        // 定义原函数
        Function<String, String> function = str -> str + " apply";

        // 传入compose函数并执行原函数
        String res = function.compose(s -> "compose " + s).apply("hello");

        Assertions.assertEquals(res, "compose hello apply");
    }

    @Test
    void andTest() {
        // 定义原函数
        Predicate<Integer> predicate = i -> i > 0;

        // 传入and函数并执行原函数
        boolean res = predicate.and(i -> i % 2 == 0).test(4);

        Assertions.assertTrue(res);
    }

    @Test
    void orTest() {
        // 定义原函数
        Predicate<Integer> predicate = i -> i > 0;

        // 传入or函数并执行原函数
        boolean res = predicate.or(i -> i % 2 == 0).test(-4);

        Assertions.assertTrue(res);
    }

    @Test
    void negateTest() {
        // 定义原函数
        Predicate<Integer> predicate = i -> i > 0;

        // 调用negate方法并执行原函数
        boolean res = predicate.negate().test(-4);

        Assertions.assertTrue(res);
    }
}
