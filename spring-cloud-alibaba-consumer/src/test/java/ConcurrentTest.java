import com.pzhu.spring.cloud.alibaba.consumer.Util.ThreadPool.yewu1;
import com.pzhu.spring.cloud.alibaba.consumer.Util.ThreadPool.yewu1.withReturnCallAble;
import java.util.List;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Author Jin haiyang
 * @Date 2023/4/30
 */
@SpringBootTest
public class ConcurrentTest {

    /**
     * 测试带有返回值的情况
     */
    @Test
    public void testReturn(){

        long startTime = System.currentTimeMillis();
        List<String> ts = yewu1.runAllOfConcurrencyWithReturn(new withReturnCallAble("string1"),new withReturnCallAble("string2"));
        long useTime = System.currentTimeMillis() - startTime;
        //因为在执行时睡眠了1S，所以并发执行最后也是一秒左右
        System.out.println("总耗时"+useTime);
        System.out.println(ts);
        //关闭线程池，只是为了让main方法结束，不然执行完main方法之后，并不会进行结束。
        yewu1.executor.shutdown();

    }


    @Test
    public void testnoReturn(){

        long startTime = System.currentTimeMillis();
        yewu1.runAllOfConcurrency(
            ()->{
                System.out.println("task1执行了");
            },
            ()-> {
                System.out.println("task2执行了");
            });
        long useTime = System.currentTimeMillis() - startTime;
        //因为在执行时睡眠了1S，所以并发执行最后也是一秒左右
        System.out.println("总耗时"+useTime);
        //关闭线程池，只是为了让main方法结束，不然执行完main方法之后，并不会进行结束。
        yewu1.executor.shutdown();

    }






}
