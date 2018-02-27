package io.cone.aem.core.custom.dynamicConfig;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

/**
 * @author Carlos Gutierrez on 2/27/18
 */
@ObjectClassDefinition(name="Custom Config",
        description = "Simple demo for dynamic config")
public @interface CustomConfig {
  @AttributeDefinition(name = "Message",
          description = "Configurable message")
  String myMessage() default "";
}
