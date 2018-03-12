package io.cone.aem.core.models;

import io.cone.aem.core.pojos.simple.TouchMultiPojo;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.commons.json.JSONObject;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Model(adaptables=Resource.class)
public class TouchMultiWidgetModel {
  Logger logger = LoggerFactory.getLogger(this.getClass());

  @Inject @Optional
  private List<String> items;

  private List<TouchMultiPojo> resultItems;

  public List<TouchMultiPojo> getItems() {
    fillList();
    return resultItems;
  }

  private void fillList() {
    if (items == null) {
      return;
    }

    resultItems = new ArrayList<>();

    try {
      for (String itemString : items) {
        JSONObject resultObject = new JSONObject(itemString);
        String page = resultObject.getString("page");
        String path = resultObject.getString("path");
        resultItems.add(new TouchMultiPojo(page , path));
      }
    } catch(Exception e) {
      logger.error(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> Exception parsing the JSON string", e);
    }
  }
}
