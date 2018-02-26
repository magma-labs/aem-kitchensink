package io.cone.aem.core.listeners;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Carlos Gutierrez on 2/26/18
 * Event fired by io.cone.aem.core.schedulers.PeriodicTask
 */
@Component(service = EventHandler.class,
        property = {
                EventConstants.EVENT_TOPIC + "=" + CustomEvents.CUSTOM_EVENT_COMPLETE
        })
public class CustomEventListener implements EventHandler {
  private final Logger logger = LoggerFactory.getLogger(getClass());


  @Override
  public void handleEvent(Event event) {
    String message = (String) event.getProperty("message");
    logger.info("Event {} fired!, and it contained the following message: {}", CustomEvents.CUSTOM_EVENT_COMPLETE, message);
  }
}
