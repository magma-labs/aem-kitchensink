package io.cone.aem.core.servlets;

import com.day.cq.commons.jcr.JcrUtil;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.query.Query;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;

/**
 * @author Carlos Gutierrez on 3/7/18
 * Access like http://localhost:4502/etc/aem-kitchensink/importer.html
 * We created a node with the sling:resourceType property as 'aem-kitchensink/tools/importer'
 * The servlet is able to be triggered using that node even when the 'aem-kitchensink/tools/importer'
 * node doesn't really exist
 */
@Component(service=Servlet.class,
        property={
                "sling.servlet.resourceTypes="+ "aem-kitchensink/tools/importer"
        })
public class ImporterServlet extends SlingSafeMethodsServlet {
  Logger logger = LoggerFactory.getLogger(this.getClass());

  private static final long serialVersionUID = 1L;

  @Override
  public final void doGet(final SlingHttpServletRequest request, final SlingHttpServletResponse response)
          throws ServletException, IOException {
    response.setHeader("Content-Type", "text/html");

    Session session = request.getResourceResolver().adaptTo(Session.class);

    try{
      String q = "/jcr:root/content/aem-kitchensink/en//*" +
              "[@sling:resourceType='wcm/foundation/components/parsys']";
      Query query = session.getWorkspace().getQueryManager()
              .createQuery(q, "xpath");
      NodeIterator result = query.execute().getNodes();
      while (result.hasNext()) {
        Node n = result.nextNode();
        Node newTextNode = JcrUtil.createUniqueNode(n, "newtext",
                "nt:unstructured", session);
        newTextNode.setProperty("sling:resourceType",
                "foundation/components/text");
        newTextNode.setProperty("text", "<h3>A Text Component from a Servlet using the JCR API</h3>");
        newTextNode.setProperty("textIsRich", "true");
        response.getWriter().print("Added node: " + n.getPath() + "<br />");
      }
      session.save();
    }
    catch (RepositoryException e){
      response.getWriter().print("Could not create nodes");
      logger.error(e.getMessage() + e);
    }
    response.getWriter().close();
  }
}
