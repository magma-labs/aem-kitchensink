package io.cone.aem.core.pojos;

import java.util.HashMap;
import java.util.Map;
import java.util.LinkedHashMap;

import org.apache.sling.api.resource.ResourceMetadata;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.wrappers.ValueMapDecorator;
import org.apache.commons.collections.iterators.TransformIterator;

import com.adobe.cq.sightly.WCMUsePojo;
import com.adobe.granite.ui.components.ds.DataSource;
import com.adobe.granite.ui.components.ds.SimpleDataSource;
import com.adobe.granite.ui.components.ds.ValueMapResource;

/**
 * @author Carlos Gutierrez on 3/13/18
 */
public class HtlDataSourceExample extends WCMUsePojo {
  @Override
  public void activate() throws Exception {
    final ResourceResolver resolver = getResource().getResourceResolver();
    final Map<String, String> countries = new LinkedHashMap<String, String>();

    countries.put("foo", "Foo");
    countries.put("bar", "Bar");
    countries.put("baz", "Baz");
    countries.put("lorem", "Lorem");
    countries.put("ipsum", "Ipsum");

    @SuppressWarnings("unchecked")
    DataSource ds = new SimpleDataSource(new TransformIterator(countries.keySet().iterator(), transformer -> {
      String country = (String) transformer;
      ValueMap vm = new ValueMapDecorator(new HashMap<String, Object>());

      vm.put("value", country);
      vm.put("text", countries.get(country));

      return new ValueMapResource(resolver, new ResourceMetadata(), "nt:unstructured", vm);
    }));

    this.getRequest().setAttribute(DataSource.class.getName(), ds);

  }
}
