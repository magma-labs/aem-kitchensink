package io.cone.aem.core.custom;

import org.apache.sling.api.resource.Resource;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Servlet;
import java.util.List;

/**
 * @author Carlos Gutierrez on 2/26/18
 */
@Component(immediate = true)
public class ServiceComsumerExample {
  private final Logger logger = LoggerFactory.getLogger(getClass());

  @Reference(cardinality = ReferenceCardinality.OPTIONAL)
  private Resource resource;

  @Reference(cardinality = ReferenceCardinality.MULTIPLE)
  private List<Servlet> servlets;

  @Reference(cardinality = ReferenceCardinality.MULTIPLE, target = "(sling.servlet.methods=POST)")
  private List<Servlet> postServlets;

  @Activate
  protected void activate() {
    logger.info("Optional Reference: {}", resource);
    logger.info("Servlets Registered: {}", servlets);
    logger.info("POST Servlets Registered: {}", postServlets);
  }
}
