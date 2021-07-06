import com.dcy.common.benchmark.Insert;
import com.dcy.common.benchmark.jmeter.JmeterBootstrap;
import com.dcy.common.benchmark.jmeter.JmeterTestProperties;
import org.junit.jupiter.api.Test;

public class InsertTest {
    @Test
    public void test1(){
        final JmeterTestProperties properties = JmeterTestProperties.newBuilder().withLoopCount(50).withThreadCount(8).withTestClass(Insert.class).build();
        JmeterBootstrap.run(properties);
    }
}
