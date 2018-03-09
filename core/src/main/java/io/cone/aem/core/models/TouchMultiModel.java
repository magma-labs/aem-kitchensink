package io.cone.aem.core.models;

import javax.inject.Inject;

import io.cone.aem.core.pojos.simple.TouchMultiPojo;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;

import java.util.ArrayList;
import java.util.List;

@Model(adaptables=Resource.class)
public class TouchMultiModel {
  @Inject @Optional
  private List<String> page;

  @Inject @Optional
  private List<String> path;

  private List<TouchMultiPojo> items;

  public List<TouchMultiPojo> getItems() {
    fillList();
    return items;
  }

  private void fillList() {
    if (page == null || path == null) {
      return;
    }

    items = new ArrayList<>();

    for(int i = 0; i < page.size(); i++) {
      items.add(new TouchMultiPojo(page.get(i), path.get(i)));
    }
  }
}
