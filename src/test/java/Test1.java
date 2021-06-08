import com.dcy.common.benchmark.TestSampler;
import com.dcy.common.benchmark.jmeter.JmeterBootstrap;
import com.dcy.common.benchmark.jmeter.JmeterTestProperties;
import org.junit.jupiter.api.Test;

/**
 * @author dcy
 * Create Date: 2021-06-08
 */
public class Test1 {
    @Test
    public  void test1(){
        final JmeterTestProperties properties = JmeterTestProperties.newBuilder().withLoopCount(1).withThreadCount(1).withTestClass(TestSampler.class).build();
        JmeterBootstrap.run(properties);

    }

}
