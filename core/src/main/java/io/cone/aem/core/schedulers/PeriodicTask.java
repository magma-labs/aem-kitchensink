package io.cone.aem.core.schedulers;

import io.cone.aem.core.listeners.CustomEvents;
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

  @Reference
  private EventAdmin eventAdmin;

  @Override
  public void run() {
    // Uncomment the code below and check the logs at <crx-folder-location>/logs/project-aem-kitchensink.log>

    /*
    logger.info("PeriodicTask running every 30 seconds");
    fireCompletionEvent();
    */
  }

  private void fireCompletionEvent() {
    // Shows functionality of io.cone.aem.core.listeners.CustomEventListener
    Event event = new Event(CustomEvents.CUSTOM_EVENT_COMPLETE, new HashMap<String, Object>() {
      private static final long serialVersionUID = 1L;
      {
        put("message", "Hello World");
      }
    });
    eventAdmin.postEvent(event);
  }
}
