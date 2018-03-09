package io.cone.aem.core.pojos.simple;

/**
 * @author Carlos Gutierrez on 3/8/18
 */
public class TouchMultiPojo {
  private String page;
  private String path;

  public TouchMultiPojo(String page, String path) {
    this.page = page;
    this.path = path;
  }

  public String getPage() {
    return page;
  }

  public void setPage(String page) {
    this.page = page;
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }
}
