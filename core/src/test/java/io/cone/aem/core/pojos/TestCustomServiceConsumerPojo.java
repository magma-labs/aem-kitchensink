package io.cone.aem.core.pojos;

import io.cone.aem.core.custom.CustomService;
import io.cone.aem.core.models.HelloWorldModel;
import junitx.util.PrivateAccessor;
import org.apache.sling.settings.SlingSettingsService;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Simple JUnit test verifying the HelloWorldModel
 */
public class TestCustomServiceConsumerPojo {

    private CustomServiceConsumerPojo custom;

    private static final String expectedMessage = "Hello world from a service!";
    
    @Before
    public void setup() throws Exception {
        CustomService customService = mock(CustomService.class);
        when(customService.getMessage()).thenReturn(expectedMessage);

        custom = new CustomServiceConsumerPojo();
        PrivateAccessor.setField(custom, "customService", customService);
    }
    
    @Test
    public void testGetMessage() throws Exception {
        String msg = custom.getMessage();
        assertNotNull(msg);
        assertTrue(msg.length() > 0);
    }

}
