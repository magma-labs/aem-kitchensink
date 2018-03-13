package io.cone.aem.core.custom.impl;

import io.cone.aem.core.custom.ConfigurationFactoryService;
import io.cone.aem.core.custom.dynamicConfig.CustomConfig;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.metatype.annotations.Designate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * @author Carlos Gutierrez on 3/12/18
 * Will be found on http://localhost:4502/system/console/configMgr
 * with the name "Custom Config" with a plus sign on the right
 */
@Designate(ocd=CustomConfig.class, factory=true)
@Component(service=ConfigurationFactoryService.class)
public class ConfigurationFactoryServiceImpl implements ConfigurationFactoryService {
  private final Logger logger = LoggerFactory.getLogger(getClass());
  private String message;

  @Activate
  protected void activate(final CustomConfig config) {
    message = config.myMessage();
  }

  public String sayHello() {
    logger.info(">>>>>>>>>>>>>>>>>>>>>> SAYING: " + message);
    return message;
  }
}
