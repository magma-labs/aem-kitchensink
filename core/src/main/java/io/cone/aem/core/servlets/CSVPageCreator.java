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
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Session;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.*;

/**
 * @author Carlos Gutierrez on 3/6/18
 * Access the page as http://localhost:4502/etc/aem-kitchensink/pagecreator.csv.html
 */
@Component(service=Servlet.class,
        property={
                "sling.servlet.resourceTypes="+ "aem-kitchensink/tools/pagecreator",
                "sling.servlet.methods=" + HttpConstants.METHOD_POST,
                "sling.servlet.selectors=csv"
        })
public class CSVPageCreator extends SlingAllMethodsServlet {
  private static final long serialVersionUID = 1L;
  Logger logger = LoggerFactory.getLogger(getClass());

  @Reference
  private Replicator replicator;

  private Resource resource;

  public void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response)throws ServletException, IOException {
    response.setHeader("Content-Type", "application/json");
    JSONObject resultObject = new JSONObject();
    resource = request.getResource();

    String param = request.getParameter("importer");
    byte[] input = param.getBytes();
    InputStream stream = new ByteArrayInputStream(input);
    try {
      resultObject = readCSV(stream);
    } catch (JSONException e) {
      logger.error("Failure to Read CSV: " + e);
    }

    if(resultObject != null){
      //Write the result to the page
      response.getWriter().print(resultObject.toString());
      response.getWriter().close();
    }
  }

  private JSONObject readCSV(InputStream stream) throws IOException, JSONException {
    JSONObject out = new JSONObject();
    BufferedReader br = new BufferedReader(new InputStreamReader(stream));

    if(br != null){
      String line;
      String[] newPage;
      JSONObject createdPageObject = null;
      //Read each line of the CSV
      while ((line = br.readLine()) != null){
        newPage = line.split(",");
        String aemTag = null;
        String publishFlag = null;
        String aemTemplatePath = null;

        //If the line has a template, tag, publish flag, set those variables
        if(newPage.length == 5){
          aemTemplatePath = newPage[2];
          aemTag = newPage[3];
          publishFlag = newPage[4];
        }else if(newPage.length == 4){
          aemTemplatePath = newPage[2];
          aemTag = newPage[3];
        }else if(newPage.length == 3){
          publishFlag = newPage[2];
        }

        //As long as there is a path and title, the page can be created
        if((newPage.length  > 1)
                && !newPage[0].isEmpty()
                && !newPage[1].isEmpty()){
          String path = newPage[0];
          String title = newPage[1];
          try {
            createdPageObject = createTrainingPage(path, title, aemTemplatePath, aemTag, publishFlag);
          } catch (Exception e) {
            logger.error(path +" not created successfully: " + e);
          }

          //add the status of the row into the json array
          if(createdPageObject != null){
            out.put(path, createdPageObject); //Print Title of Page
            createdPageObject = null;
          }else{
            out.put(path, new JSONObject().put("Status","Could not create a page"));
          }
        }
        else {
          out.put(line, new JSONObject().put("Status","Could not properly parce"));
        }
      }
    }
    br.close();
    return out;
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
      tm.setTags(p.getContentResource(),
              new Tag[]{tm.resolve(tag)},
              true);
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
