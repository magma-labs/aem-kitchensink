package io.cone.aem.core.servlets;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;

/**
 * @author Carlos Gutierrez on 3/1/18
 */
@Component(service=Servlet.class,
        property={
                Constants.SERVICE_DESCRIPTION + "=Demo Path Servlet",
                "sling.servlet.methods=" + HttpConstants.METHOD_GET,
                "sling.servlet.paths=/bin/foo",
                "sling.servlet.paths=/bin/bar"
        })
public class PathServlet extends SlingSafeMethodsServlet {
  private static final long serialVersionUid = 1L;

  @Override
  protected void doGet(final SlingHttpServletRequest req,
                       final SlingHttpServletResponse resp) throws ServletException, IOException {
    final Resource resource = req.getResource();
    resp.setContentType("text/html");
    resp.getWriter().write("<h1>It Works!</h1>");
  }
}
