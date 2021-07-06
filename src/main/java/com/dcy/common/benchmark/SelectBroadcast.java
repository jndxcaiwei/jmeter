package com.dcy.common.benchmark;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

/**
 * @author dcy
 * Create Date: 2021-06-08
 */
public class SelectBroadcast extends AbstractJavaSamplerClient {
    private static final Logger LOGGER = LogManager.getLogger(SelectBroadcast.class);
    Connection connection;
    Statement statement;
    public SelectBroadcast() {
        LOGGER.warn("{}",this.getClass().getName());
    }

    @Override
    public void setupTest(JavaSamplerContext context) {
        //create a connection here
        final String ADDRESS = "10.186.17.107";
        final String PORT = "8066";
        final String USER = "action";
        final String PASSWORD = "action";
        final String DB = "mytest";

        try {
            connection = DriverManager.getConnection("jdbc:mariadb://"
                    + ADDRESS + ":" + PORT + "/" + DB + "?user=" + USER + "&password="
                    + PASSWORD + "&useBatchMultiSend=true"+"&allowMultiQueries=true");
            statement = connection.createStatement();
            statement.execute("select * from sbtest1 where id=1");
        } catch (SQLException troubles) {
            LOGGER.error("",troubles);
        }
    }

    @Override
    public void teardownTest(JavaSamplerContext context) {
        //destroy connection here
        try {
            connection.close();
        } catch (SQLException troubles) {
            LOGGER.error("",troubles);
        }
    }


    @Override
    public SampleResult runTest(JavaSamplerContext context) {

        SampleResult result = new SampleResult();

        boolean success = true;

        result.sampleStart();
        // run your test code here.
        try {
            for (int i = 1; i <= 12; i++) {
                for (int j = (context.getJMeterContext().getThreadNum() * 50) + 1; j <= (context.getJMeterContext().getThreadNum() + 1) * 50; j++) {
                    statement.execute(String.format("select * from sbtest1 where id between %d and %d",j,j+2));
                    statement.execute(String.format("select * from sbtest1 where id between %d and %d",j+6,j+8));
                }
//                statement.executeBatch();
            }
        } catch (Exception e1) {
            LOGGER.error("",e1);
            success=false;
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