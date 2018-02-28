package io.cone.aem.core.custom;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Carlos Gutierrez on 2/28/18
 */
@Component
public class SimpleComponent {
  private final Logger logger = LoggerFactory.getLogger(getClass());

  @Activate
  protected void activate() {
    logger.info(">>>>>>>>>>>>>>>>>> Custom component Activated!!!");
  }

  @Deactivate
  protected void deactivate() {
    logger.info(">>>>>>>>>>>>>>>>>> Custom component Deactivated!!!");
  }
}
