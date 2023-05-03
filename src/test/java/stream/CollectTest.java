package stream;

import com.example.User;
import org.junit.jupiter.api.Test;


import java.util.*;
import java.util.stream.Collectors;


public class CollectTest {

    @Test
    void streamToList() {
        List<User> users = List.of(new User("yang"), new User("quan"), new User("frank"));

        // 收集所有用户名，结果为ArrayList
        List<String> list = users.stream()
                .map(User::getName)
                .collect(Collectors.toList());

        System.out.println(list);
    }

    @Test
    void streamToSet() {
        List<User> users = List.of(new User("yang"), new User("quan"), new User("yang"));

// 收集所有用户名，结果为TreeSet
        Set<String> set = users.stream()
                .map(User::getName)
                .collect(Collectors.toCollection(TreeSet::new));

        System.out.println(set);
    }

    @Test
    void streamToString() {
        List<User> users = List.of(new User("yang"), new User("quan"), new User("frank"));

// 将用户名收集为String
        String name = users.stream()
                .map(User::getName)
                .collect(Collectors.joining(", "));

        System.out.println(name);
    }

    @Test
    void streamToInt() {
        List<User> users = List.of(new User("yang", 20), new User("quan", 25), new User("frank", 30));

// 计算所有user的年龄之和
        int total = users.stream()
                .collect(Collectors.summingInt(User::getAge));

        System.out.println(total);
    }

    @Test
    void streamToMap1() {
        List<User> users = List.of(new User("yang"), new User("quan"), new User("frank"));

// 根据用户名分组
        Map<String, List<User>> map = users.stream()
                .collect(Collectors.groupingBy(User::getName));

        System.out.println(map);
    }

    @Test
    void steamToMap2() {
        List<User> users = List.of(new User("yang", 10), new User("quan", 11), new User("yang", 13));

        // 根据用户名统计年龄之和
        Map<String, Integer> map = users.stream()
                .collect(Collectors.groupingBy(User::getName, Collectors.summingInt(User::getAge)));

        System.out.println(map);
    }

    @Test
    void steamToMap3() {
        List<User> users = List.of(new User("yang", 20), new User("quan", 22), new User("yang", 33));

// 根据年龄阈值分组用户
        Map<Boolean, List<User>> map = users.stream()
                .collect(Collectors.partitioningBy(u -> u.getAge() >= 30));

        System.out.println(map);
    }
}
