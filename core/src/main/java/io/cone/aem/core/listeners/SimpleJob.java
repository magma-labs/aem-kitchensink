package io.cone.aem.core.listeners;

import org.apache.sling.event.jobs.Job;
import org.apache.sling.event.jobs.consumer.JobConsumer;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Carlos Gutierrez on 2/26/18
 * Job started by io.cone.aem.core.schedulers.PeriodicTask
 */
@Component(
        service=JobConsumer.class,
        property={
                "job.topics=" + CustomEvents.CUSTOM_JOB_COMPLETE
        }
)
public class SimpleJob implements JobConsumer {
  private final Logger logger = LoggerFactory.getLogger(getClass());

  @Override
  public JobResult process(Job job) {
    String message = (String) job.getProperty("message");
    logger.info("Job {} executed!, and it contained the following message: {}", CustomEvents.CUSTOM_JOB_COMPLETE, message);
    return JobResult.OK;
  }
}
