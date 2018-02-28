package io.cone.aem.core;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Carlos Gutierrez on 2/28/18
 * Added as activator in core/pom.xml on build/plugins/plugin(org.apache.felix)/configuration/instructions
 */
public class Activator implements BundleActivator {
  private final Logger logger = LoggerFactory.getLogger(getClass());

  public void start(BundleContext context) throws Exception {
    logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> Bundle Started!");
  }

  public void stop(BundleContext context) throws Exception {
    logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> Bundle Stopped!");
  }
}
