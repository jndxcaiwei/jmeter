import com.dcy.common.benchmark.SelectSingle;
import com.dcy.common.benchmark.jmeter.JmeterBootstrap;
import com.dcy.common.benchmark.jmeter.JmeterTestProperties;
import org.junit.jupiter.api.Test;

public class SelectSingle {
    @Test
    public void test1(){
        final JmeterTestProperties properties = JmeterTestProperties.newBuilder().withLoopCount(3).withThreadCount(8).withTestClass(SelectSingle.class).build();
        JmeterBootstrap.run(properties);
    }
}

