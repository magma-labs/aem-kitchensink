package io.cone.aem.core.servlets;

import com.day.cq.replication.ReplicationActionType;
import com.day.cq.replication.Replicator;
import com.day.cq.tagging.Tag;
import com.day.cq.tagging.TagManager;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.commons.json.JSONObject;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Session;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;

/**
 * @author Carlos Gutierrez on 3/6/18
 * Access the srvlet like http://localhost:4502/etc/aem-kitchensink/pagecreator.html
 */
@Component(service=Servlet.class,
        property={
                "sling.servlet.resourceTypes="+ "aem-kitchensink/tools/pagecreator",
                "sling.servlet.methods=" + HttpConstants.METHOD_POST
        })
public class PageCreatorServlet extends SlingAllMethodsServlet {
  Logger logger = LoggerFactory.getLogger(this.getClass());

  private static final long serialVersionUID = 1L;

  @Reference
  private Replicator replicator;

  private Resource resource;

  public void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response)throws ServletException, IOException {
    response.setHeader("Content-Type", "application/json");
    JSONObject resultObject = new JSONObject();
    resource = request.getResource();

    String param = request.getParameter("importer");
    String[] newPage = param.split(",");
    try {
      resultObject = createTrainingPage(newPage[0], newPage[1], newPage[2], newPage[3], newPage[4]);
    } catch (Exception e) {
      logger.error("Failure to create page: " + e, e);
    }

    if(resultObject != null){
      //Write the result to the page
      response.getWriter().print(resultObject.toString());
      response.getWriter().close();
    }
  }

  private JSONObject createTrainingPage(String path, String title, String template, String tag, String publish) throws Exception{
    JSONObject pageInfo = new JSONObject();

    if(path==null || title==null) return null;

    //Parse the path to get the pageNodeName and parentPath
    int lastSlash = path.lastIndexOf("/");
    String pageNodeName = path.substring(lastSlash+1);
    String parentPath = path.substring(0, lastSlash);

    if(pageNodeName==null || parentPath==null) return null;

    //Set a default template if none is given
    if(template == null || template.isEmpty()){
      template = "/apps/aem-kitchensink/templates/page-content";
    }

    //Create page
    PageManager pageManager = resource.getResourceResolver().adaptTo(PageManager.class);
    Page p = pageManager.create(parentPath,
            pageNodeName,
            template,
            title);
    //Add a tag to the page
    if(tag != null && !tag.isEmpty()) {
      //TagManager can be retrieved via adaptTo
      TagManager tm = resource.getResourceResolver().adaptTo(TagManager.class);
      Resource resource = p.getContentResource();
      Tag pageTag = tm.resolve(tag);
      Tag[] tags = new Tag[]{pageTag};
      tm.setTags(resource, tags,true);
    }

    //Publish page if requested
    boolean publishPage = Boolean.parseBoolean(publish);
    if(publishPage){
      //Replicator is exposed as a service
      replicator.replicate(resource.getResourceResolver().adaptTo(Session.class),
              ReplicationActionType.ACTIVATE,
              p.getPath());
    }

    pageInfo.put("Status", "Successful");
    pageInfo.put("Location", p.getPath());
    pageInfo.put("Title", p.getTitle());
    pageInfo.put("Template Used", p.getTemplate().getPath());
    pageInfo.put("Tagged with", p.getTags()[0].getTitle());
    pageInfo.put("Was Published", publishPage);
    return pageInfo;
  }
}
