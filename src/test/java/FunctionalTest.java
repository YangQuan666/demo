import org.junit.jupiter.api.Test;


public class FunctionalTest {


    /**
     * 使用匿名类实现Runnable接口来创建一个线程
     */
    @Test
    public void createThread() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("thread running...");
            }
        }).start();
    }


    /**
     * 传入lambda表达式来代替匿名类
     */
    @Test
    public void createThreadLambda() {
        new Thread(() -> System.out.println("thread running...")).start();
    }


    /**
     * 使用方法引用代替匿名内部类
     */
    @Test
    public void createThreadMethodReference() {
        new Thread(FunctionalTest::go).start();
    }

    /**
     * lambda.LambdaTest#go()和Runnable#run()具有同样的入参和返回值
     */
    static void go() {
        System.out.println("Go::go()");
    }
}
