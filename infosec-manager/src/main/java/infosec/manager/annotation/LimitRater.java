package infosec.manager.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * 限流注解
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface LimitRater {

    // 唯一id
    String key() default "";

    // 限流速率
    int rate() default -1;

    // 令牌桶容量
    int capacity() default 0;

    // 当前需要的token数量
    int acquire() default 1;

    // 时间单位
    TimeUnit timeUnit() default TimeUnit.SECONDS;
}
