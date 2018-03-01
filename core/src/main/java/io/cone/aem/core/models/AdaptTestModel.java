package io.cone.aem.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;

import javax.inject.Inject;
import javax.jcr.Node;

/**
 * @author Carlos Gutierrez on 3/1/18
 */
@Model(adaptables=Resource.class)
public class AdaptTestModel {
  @Inject @Self
  private Node thisNode;

  @Inject
  private Node childNode;

  public String getParentMessage() throws Exception{
    return thisNode.getProperty("message").getString();
  }

  public String getChildMessage() throws Exception{
    return childNode.getProperty("message").getString();
  }
}
