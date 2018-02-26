package io.cone.aem.core.schedulers;

import io.cone.aem.core.listeners.CustomEvents;
import org.apache.sling.event.jobs.JobManager;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

/**
 * @author Carlos Gutierrez on 2/26/18
 * Fixed period scheduled task
 */
@Component(
        service=Runnable.class,
        property={"scheduler.period:Long=30"} // Can be changed to a more flexible schedule e.g. "scheduler.expression=*/30 * * * * MON-FRI"
)
public class PeriodicTask implements Runnable {
  private final Logger logger = LoggerFactory.getLogger(getClass());

  // For event handlers
  @Reference
  private EventAdmin eventAdmin;

  // For Jobs
  @Reference
  private JobManager jobManager;

  @Override
  public void run() {
    // Uncomment the code below and check the logs at <crx-folder-location>/logs/project-aem-kitchensink.log>

    /*
    logger.info("PeriodicTask running every 30 seconds");
    fireCompletionEvent();
    startJob();
    */
  }

  private void fireCompletionEvent() {
    // Shows functionality of io.cone.aem.core.listeners.CustomEventListener
    Event event = new Event(CustomEvents.CUSTOM_EVENT_COMPLETE, new HashMap<String, Object>() {
      private static final long serialVersionUID = 1L;
      {
        put("message", "Hello World from an event handler");
      }
    });
    eventAdmin.postEvent(event);
  }

  private void startJob() {
    // Shows functionality of io.cone.aem.core.listeners.SimpleJob
    jobManager.addJob(CustomEvents.CUSTOM_JOB_COMPLETE, new HashMap<String, Object>() {
      private static final long serialVersionUID = 1L;
      {
        put("message", "Hello World from a job");
      }
    });
  }
}
