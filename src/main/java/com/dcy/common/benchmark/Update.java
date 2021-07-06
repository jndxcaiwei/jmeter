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
public class Update extends AbstractJavaSamplerClient {
    private static final Logger LOGGER = LogManager.getLogger(Update.class);
    Connection connection;
    Statement statement;
    public Update() {
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
         //   System.out.println("当前线程id：" + context.getJMeterContext().getThreadNum());
            for (int i = 1; i <= 12; i++) {
                for (int j = (context.getJMeterContext().getThreadNum() * 50) + 1; j <= (context.getJMeterContext().getThreadNum() + 1) * 50; j++) {
                    statement.execute(String.format("update sbtest1 set c='###########-###########-###########-###########-###########-###########-###########-###########-###########-###########' where id=%d",j));
                    statement.execute(String.format("update sbtest1 set c='33973744704-80540844748-72700647445-87330233173-87249600839-07301471459-22846777364-58808996678-64607045326-48799346817' where id=%d",j));
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
