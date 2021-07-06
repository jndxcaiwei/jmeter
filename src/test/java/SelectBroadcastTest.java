import com.dcy.common.benchmark.SelectBroadcast;
import com.dcy.common.benchmark.SelectSingle;
import com.dcy.common.benchmark.jmeter.JmeterBootstrap;
import com.dcy.common.benchmark.jmeter.JmeterTestProperties;
import org.junit.jupiter.api.Test;

public class SelectBroadcastTest {
    @Test
    public void test1(){
        final JmeterTestProperties properties = JmeterTestProperties.newBuilder().withLoopCount(3).withThreadCount(8).withTestClass(SelectBroadcast.class).build();
        JmeterBootstrap.run(properties);
    }
}
