package io.cone.aem.core.custom.impl;

import io.cone.aem.core.custom.CustomService;
import io.cone.aem.core.custom.dynamicConfig.CustomConfig;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.metatype.annotations.Designate;

/**
 * @author Carlos Gutierrez on 2/23/18
 * The @Designate annotation specifies wich interface is
 * going to be used to declare the configurable fields
 * (Dynamic configuration)
 */
@Designate(ocd=CustomConfig.class)
@Component(service=CustomService.class)
public class CustomServiceImpl implements CustomService {
  private String message;

  @Override
  public String getMessage() {
    if (message != null && !message.trim().equals("")) {
      return message;
    }
    return "Hello world from a service!";
  }

  @Activate
  protected void activate(final CustomConfig config) {
    message = config.myMessage();
  }
}
