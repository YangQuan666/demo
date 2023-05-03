package method.reference;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.example.User;

import java.util.function.Supplier;
import java.util.function.Function;

public class MethodReferenceTest {


    @Test
    void staticMethodReferenceTest() {
        // 静态方法引用
        Callable callable = User::play;

        // 注意这里是call而不是play
        callable.call("GTA");
    }

    @Test
    void staticMethodReferenceErrorTest() {

        // 方法签名不一致
        // Callable callable = com.example.User::sleep;
        // callable.call("Hamburger");
    }

    @Test
    void instanceMethodReferenceTest() {
        // 实例方法引用
        User user = new User();
        Callable callable = user::eat;

        // 注意这里是call而不是play
        callable.call("Hamburger");
    }

    @Test
    void noArgConstructMethodReferenceTest() {
        Supplier<User> supplier = User::new;
        User user = supplier.get();

        Assertions.assertEquals(user.getName(), "quan");
    }

    @Test
    void allArgConstructMethodReferenceTest() {
        Function<String, User> function = User::new;
        User bob = function.apply("bob");

        Assertions.assertEquals(bob.getName(), "bob");
    }
}