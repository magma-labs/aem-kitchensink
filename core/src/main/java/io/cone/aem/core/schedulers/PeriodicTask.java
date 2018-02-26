package io.cone.aem.core.schedulers;

import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

  @Override
  public void run() {
    // Uncomment and check the logs at <crx-folder-location>/logs/project-aem-kitchensink.log>
    // logger.info("PeriodicTask running every 30 seconds");
  }
}
