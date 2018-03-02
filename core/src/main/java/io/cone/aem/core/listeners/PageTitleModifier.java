package io.cone.aem.core.listeners;

import org.apache.sling.jcr.api.SlingRepository;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Property;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

/**
 * @author Carlos Gutierrez on 3/2/18
 */
@Component(service = EventHandler.class,
        property = {
                EventConstants.EVENT_TOPIC + "=org/apache/sling/api/resource/Resource/CHANGED",
                EventConstants.EVENT_TOPIC + "=org/apache/sling/api/resource/Resource/ADDED",
                EventConstants.EVENT_FILTER + "=(path=/content/aem-kitchensink/en/jcr:content)"
        })
public class PageTitleModifier implements EventHandler {
  private final Logger logger = LoggerFactory.getLogger(getClass());

  @Reference
  private SlingRepository repository;

  private Session session;

  @Activate
  protected void activate() throws Exception {
    // Uses sub-service created in
    // apps/aem-kitchensink/config/org.apache.sling.serviceusermapping.impl.ServiceUserMapperImpl.amended-training.xml
    session = repository.loginService("training",null);
  }

  @Override
  public void handleEvent(Event event) {
    try {
      Property titleProperty = getTitleProperty(event);
      addExclamationSign(titleProperty);
    } catch(Exception e) {
      logger.error(">>>>>>>>>>>>>>>> Error handling the title modification", e);
    }
  }

  private Property getTitleProperty(Event event) throws RepositoryException {
    String modifiedPropertyPath = getPropertyPath(event);
    return session.getProperty(modifiedPropertyPath);
  }

  private String getPropertyPath(Event event) throws RepositoryException {
    String path = (String) event.getProperty("path");
    String[] ModifiedAttributes = (String[]) event.getProperty("resourceChangedAttributes");
    String propertyPath = null;

    for (String attribute : ModifiedAttributes) {
      if (attribute.equalsIgnoreCase("jcr:title")) {
        propertyPath = path + "/" + ModifiedAttributes[0];
        break;
      }
    }

    return propertyPath;
  }

  private void addExclamationSign(Property titleProperty) throws RepositoryException {
    if (!titleProperty.getString().endsWith("!")) {
      titleProperty.setValue(titleProperty.getString() + "!");
      logger.info(">>>>>>>>>>>>>>>>> Page title modified!!!!!!");
      session.save();
    }
  }
}
