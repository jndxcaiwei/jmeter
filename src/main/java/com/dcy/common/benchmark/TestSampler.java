package com.dcy.common.benchmark;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author dcy
 * Create Date: 2021-06-08
 */
public class TestSampler extends AbstractJavaSamplerClient {
    private static final Logger LOGGER = LogManager.getLogger(TestSampler.class);

    public TestSampler() {

    }

    @Override
    public void setupTest(JavaSamplerContext context) {
        //create a connection here
    }

    @Override
    public void teardownTest(JavaSamplerContext context) {
        //destroy connection here
    }


    @Override
    public SampleResult runTest(JavaSamplerContext context) {

        SampleResult result = new SampleResult();

        boolean success = true;


        result.sampleStart();
        // run your test code here.
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e1) {
            LOGGER.info(e1);
        }

        result.sampleEnd();

        result.setSuccessful(success);

        return result;
    }

    @Override
    public Arguments getDefaultParameters() {
        return super.getDefaultParameters();
    }
}
