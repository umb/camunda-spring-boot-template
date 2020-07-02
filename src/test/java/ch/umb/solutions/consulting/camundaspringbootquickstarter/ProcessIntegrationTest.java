package ch.umb.solutions.consulting.camundaspringbootquickstarter;

import ch.umb.solutions.consulting.camundaspringbootquickstarter.mock.LoggerDelegateMock;
import org.apache.ibatis.logging.LogFactory;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import static ch.umb.solutions.consulting.camundaspringbootquickstarter.mock.MockHelper.registerMock;
import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.*;



/**
 * Test case starting an in-memory database-backed Process Engine using SpringBoot
 * without test coverage report
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ProcessApp.class})
public class ProcessIntegrationTest {

  private static final String PROCESS_DEFINITION_KEY = "job-executor-test";

  static {
    LogFactory.useSlf4jLogging(); // MyBatis
  }

  @Autowired
  RuntimeService runtimeService;


  @Autowired
  ApplicationContext context;

  @Before
  public void registerMocks() {
    registerMock(context, "loggerDelegate", LoggerDelegateMock.class);
  }

  @Test
  public void testHappyPath() {

    // Either: Drive the process by API and assert correct behavior by camunda-bpm-assert, e.g.:
    ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(PROCESS_DEFINITION_KEY);
    execute(job(processInstance));
    execute(job(processInstance));
    execute(job(processInstance));
    assertThat(processInstance).isEnded();
    // Now: Drive the process by API and assert correct behavior by camunda-bpm-assert
  }

}
