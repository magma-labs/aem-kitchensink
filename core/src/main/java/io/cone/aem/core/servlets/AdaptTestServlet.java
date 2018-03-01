package io.cone.aem.core.servlets;

import io.cone.aem.core.models.AdaptTestModel;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.service.component.annotations.Component;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;

/**
 * @author Carlos Gutierrez on 3/1/18
 *
 * Call like: http://localhost:4502/content/aem-kitchensink/en.model.html/content/parentNode
 * where /content/parentNode is the 'Suffix', and 'model' the selector
 */
@Component(service=Servlet.class,
        property={
                "sling.servlet.resourceTypes=aem-kitchensink/components/structure/page",
                "sling.servlet.methods=" + HttpConstants.METHOD_GET,
                "sling.servlet.selectors=model"
        })
public class AdaptTestServlet extends SlingAllMethodsServlet {
  private static final long serialVersionUID = 1L;

  ResourceResolver resourceResolver;

  public void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
    response.setContentType("text/html");

    try {
      resourceResolver = request.getResourceResolver();

      String nodePath = request.getRequestPathInfo().getSuffix();

      if (nodePath != null) {
        Resource resource = resourceResolver.getResource(nodePath);

        AdaptTestModel adaptTestModel = resource.adaptTo(AdaptTestModel.class);
        response.getOutputStream().println("<h3>Parent message: " + adaptTestModel.getParentMessage() + "</h3>");
        response.getOutputStream().println("<h3>Child message: " + adaptTestModel.getChildMessage() + "</h3>");
      } else {
        response.getWriter().println("<h1>No node found at " + nodePath + "</h1>");
      }
    } catch(Exception e) {
      response.getWriter().println(e.getMessage());
    }

    response.getWriter().close();
  }
}
