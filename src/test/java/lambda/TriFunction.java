package lambda;

/**
 * 接收三个参数并带有返回值
 */
@FunctionalInterface
interface TriFunction<T, U, V, R> {
    R apply(T t, U u, V v);
}