package io.cone.aem.core.pojos;

import com.adobe.cq.sightly.WCMUsePojo;
import io.cone.aem.core.custom.CustomService;

/**
 * @author Carlos Gutierrez on 2/23/18
 */
public class CustomServiceConsumerPojo extends WCMUsePojo {
  private CustomService customService;

  @Override
  public void activate() throws Exception {
    /*
     * Neither @Reference nor @Inject annotations work for
     * getting a service instance in
     * classes extending WCMUsePojo. @Inject works for Sling Models
     */
    customService = getSlingScriptHelper().getService(CustomService.class);
  }

  public String getMessage() {
    return customService.getMessage();
  }
}
