package com.dcy.common.benchmark.jmeter;

import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;

/**
 * write description here
 *
 * @author dcy
 * @version 2019-05-06 13:29
 * Create Date: 2019-05-06 13:29
 */
public class JmeterTestProperties {
    int loopCount;
    /**
     * 这个参数不是很了解,线程的启动速度???
     */
    int rampUp;
    int threadCount;
    Class<? extends AbstractJavaSamplerClient> testClass;

    private JmeterTestProperties(int loopCount, int rampUp, int threadCount, Class<? extends AbstractJavaSamplerClient> testClass) {
        this.loopCount = loopCount;
        this.rampUp = rampUp;
        this.threadCount = threadCount;
        this.testClass = testClass;
    }


    public static JmeterTestPropertiesBuilder newBuilder() {
        return new JmeterTestPropertiesBuilder();
    }

    public static final class JmeterTestPropertiesBuilder {
        int loopCount=1;
        int rampUp = 1;//seconds
        int threadCount=1;
        Class<? extends AbstractJavaSamplerClient> testClass;

        private JmeterTestPropertiesBuilder() {
        }


        public JmeterTestPropertiesBuilder withLoopCount(int loopCount) {
            this.loopCount = loopCount;
            return this;
        }

        public JmeterTestPropertiesBuilder withRampUp(int rampUp) {
            this.rampUp = rampUp;
            return this;
        }

        public JmeterTestPropertiesBuilder withThreadCount(int threadCount) {
            this.threadCount = threadCount;
            return this;
        }

        public JmeterTestPropertiesBuilder withTestClass(Class<? extends AbstractJavaSamplerClient> testClass) {
            this.testClass = testClass;
            return this;
        }

        public JmeterTestProperties build() {
            return new JmeterTestProperties(loopCount, rampUp, threadCount, testClass);
        }
    }
}
