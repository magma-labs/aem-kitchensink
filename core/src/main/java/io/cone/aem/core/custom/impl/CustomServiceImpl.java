package io.cone.aem.core.custom.impl;

import io.cone.aem.core.custom.CustomService;
import org.osgi.service.component.annotations.Component;

/**
 * @author Carlos Gutierrez on 2/23/18
 */
@Component(service=CustomService.class)
public class CustomServiceImpl implements CustomService {
  @Override
  public String getMessage() {
    return "Hello world from a service!";
  }
}
