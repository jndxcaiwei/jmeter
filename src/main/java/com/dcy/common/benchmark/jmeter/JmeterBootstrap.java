package com.dcy.common.benchmark.jmeter;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.config.gui.ArgumentsPanel;
import org.apache.jmeter.control.LoopController;
import org.apache.jmeter.engine.StandardJMeterEngine;
import org.apache.jmeter.protocol.java.sampler.JavaSampler;
import org.apache.jmeter.reporters.Summariser;
import org.apache.jmeter.save.SaveService;
import org.apache.jmeter.testelement.TestPlan;
import org.apache.jmeter.threads.SetupThreadGroup;
import org.apache.jmeter.util.JMeterUtils;
import org.apache.jorphan.collections.HashTree;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * write description here
 *
 * @author dcy
 * @version 2019-05-06 13:34 Create Date: 2019-05-06 13:34
 */
public class JmeterBootstrap {
    static String jmeterHome1 = "/Users/dcy/java/apache-jmeter-5.4";
    private static final Logger LOGGER = LogManager.getLogger(JmeterBootstrap.class);

    public static void run(JmeterTestProperties properties) {
        try {

            //LoggerFactory.getLogger(GenericController.class).config(() -> "INFO");

            File jmeterHome = new File(jmeterHome1);
            // JMeterUtils.setJMeterHome(jmeterHome);
            String slash = System.getProperty("file.separator");

            if (jmeterHome.exists()) {
                File jmeterProperties = new File(jmeterHome.getPath() + slash + "bin" + slash + "jmeter.properties");
                if (jmeterProperties.exists()) {
                    // JMeter Engine
                    StandardJMeterEngine jmeter = new StandardJMeterEngine();

                    // JMeter initialization (properties, log levels, locale, etc)
                    JMeterUtils.setJMeterHome(jmeterHome.getPath());
                    JMeterUtils.loadJMeterProperties(jmeterProperties.getPath());
                    JMeterUtils.initLocale();

                    // JMeter Test Plan, basically JOrphan HashTree
                    HashTree testPlanTree = new HashTree();

                    // First HTTP Sampler - open example.com
                    // HTTPSampler sampler = new HTTPSampler();
                    // sampler.setDomain("www.baidu.com");
                    // sampler.setPort(80);
                    // sampler.setPath("/");
                    // sampler.setMethod("GET");
                    // sampler.setName("Open example.com");
                    // sampler.setProperty(TestElement.TEST_CLASS,
                    // HTTPSamplerProxy.class.getName());
                    // sampler.setProperty(TestElement.GUI_CLASS,
                    // HttpTestSampleGui.class.getName());
                    // var sampler = new HandlerTcpRequest();

                    // var sampler=new HadndleRequest();

                    JavaSampler sampler = new JavaSampler();
                    sampler.setName("Java Request");
                    sampler.setClassname(properties.testClass.getName());
                    // sampler.setProperty(TestElement.TEST_CLASS, JavaSampler.class.getName());
                    // sampler.setProperty(TestElement.GUI_CLASS,
                    // JavaTestSamplerGui.class.getName());

                    // Loop Controller
                    LoopController loopController = new LoopController();
                    loopController.setLoops(properties.loopCount);
                    loopController.setFirst(true);
                    // loopController.setProperty(TestElement.TEST_CLASS,
                    // LoopController.class.getName());
                    // loopController.setProperty(TestElement.GUI_CLASS,
                    // LoopControlPanel.class.getName());
                    loopController.initialize();

                    // Thread Group
                    SetupThreadGroup threadGroup = new SetupThreadGroup();
                    threadGroup.setName("Example Thread Group");
                    threadGroup.setNumThreads(properties.threadCount);
                    threadGroup.setRampUp(properties.rampUp);
                    threadGroup.setSamplerController(loopController);
                    // threadGroup.setProperty(TestElement.TEST_CLASS, ThreadGroup.class.getName());
                    // threadGroup.setProperty(TestElement.GUI_CLASS,
                    // ThreadGroupGui.class.getName());

                    // Test Plan
                    TestPlan testPlan = new TestPlan("Create JMeter Script From Java Code");
                    // testPlan.setProperty(TestElement.TEST_CLASS, TestPlan.class.getName());
                    // testPlan.setProperty(TestElement.GUI_CLASS, TestPlanGui.class.getName());
                    testPlan.setUserDefinedVariables((Arguments) new ArgumentsPanel().createTestElement());
                    // Construct Test Plan from previously initialized elements
                    testPlanTree.add(testPlan);
                    HashTree threadGroupHashTree = testPlanTree.add(testPlan, threadGroup);
                    threadGroupHashTree.add(sampler);

                    // save generated test plan to JMeter's .jmx file format
                    SaveService.saveTree(testPlanTree, new FileOutputStream(jmeterHome + slash + "example.jmx"));

                    // add Summarizer output to get test progress in stdout like:
                    // summary = 2 in 1.3s = 1.5/s Avg: 631 Min: 290 Max: 973 Err: 0 (0.00%)
                    Summariser summer = null;
                    String summariserName = JMeterUtils.getPropDefault("summariser.name", "summary");
                    if (summariserName.length() > 0) {
                        summer = new Summariser(summariserName);
                    }

                    // Store execution results into a .jtl file
                    // String logFile = jmeterHome + slash + "example.jtl";
                    // ResultCollector logger = new ResultCollector(summer);
                    // logger.setFilename(logFile);
                    //
                    // testPlanTree.add(testPlanTree.getArray()[0], logger);
                    testPlanTree.add(testPlanTree.getArray()[0], summer);

                    // Run Test Plan
                    jmeter.configure(testPlanTree);
                    jmeter.run();

                    System.out.println("Test completed. See " + jmeterHome + slash + "example.jtl file for results");
                    System.out.println("JMeter .jmx script is available at " + jmeterHome + slash + "example.jmx");
                    return;
                }
            }

            System.err.println("jmeter.home property is not set or pointing to incorrect location");
            System.exit(1);
        } catch (IOException e) {
            LOGGER.error("", e);
        }
    }
}
