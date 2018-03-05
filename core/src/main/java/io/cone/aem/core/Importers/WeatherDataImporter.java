package io.cone.aem.core.Importers;

import com.day.cq.commons.jcr.JcrUtil;
import com.day.cq.polling.importer.ImportException;
import com.day.cq.polling.importer.Importer;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.commons.json.JSONArray;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;
import org.apache.sling.jcr.api.SlingRepository;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author Carlos Gutierrez on 3/5/18
 */
@Component(service=Importer.class,
        property={
                "importer.scheme=weather"
        })
public class WeatherDataImporter implements Importer {
  private final Logger logger = LoggerFactory.getLogger(getClass());

  private final String SOURCE_URL = "http://samples.openweathermap.org/data/2.5/weather?q=London,uk&appid=b6907d289e10d714a6e88b30761fae22";


  private static final String ID = "id";
  private static final String MAIN = "main";
  private static final String DESCRIPTION = "description";
  private static final String ICON = "icon";

  @Reference
  private SlingRepository repo;

  @Override
  public void importData(final String scheme, final String dataSource, final Resource resource)
          throws ImportException {
    try {
      URL sourceUrl	 = new URL(SOURCE_URL + dataSource);
      BufferedReader in = new BufferedReader(new InputStreamReader(sourceUrl.openStream()));
      String readLine = in.readLine(); // expecting only one line
      JSONObject jsonObject = new JSONObject(readLine);
      JSONArray weatherArray = jsonObject.getJSONArray("weather");
      JSONObject weatherObj = weatherArray.getJSONObject(0);
      logger.info("Last weather object for datasource {} was {}", dataSource, weatherObj);
      in.close();

      //persist
      writeToRepository(dataSource, weatherObj, resource);
    }
    catch (MalformedURLException e) {
      logger.error("MalformedURLException", e);
    }
    catch (IOException e) {
      logger.error("IOException", e);
    }
    catch (RepositoryException e) {
      logger.error("RepositoryException", e);
    }
    catch (JSONException e) {
      logger.error("JSONException", e);
    }

  }

  private void writeToRepository(final String dataSource, final JSONObject weather, final Resource resource) throws RepositoryException, JSONException {
    Session session= repo.loginService("training",null);
    Node parent = resource.adaptTo(Node.class);
    Node sourcePageNode = JcrUtil.createPath(parent.getPath() + "/" + dataSource, "cq:Page",
            session);
    Node weatherNode = JcrUtil.createPath(sourcePageNode.getPath() + "/weather", "nt:unstructured",
            session);
    if(weather != null){
      weatherNode.setProperty(ID, weather.getString(ID));
      weatherNode.setProperty(MAIN, weather.getString(MAIN));
      weatherNode.setProperty(DESCRIPTION, weather.getString(DESCRIPTION));
      weatherNode.setProperty(ICON, weather.getString(ICON));
    }
    session.save();
    session.logout();
  }

  @Override
  public void importData(String scheme, String dataSource, Resource target,
                         String login, String password) throws ImportException {
    importData(scheme, dataSource, target);

  }
}
