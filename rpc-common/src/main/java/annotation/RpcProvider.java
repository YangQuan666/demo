package annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * RPC服务提供方注解
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface RpcProvider {

    // 服务类型（被暴露的实现类的接口类型）
    Class<?> interfaceName();

    // 服务版本（默认为空）
    String version() default "";
}
