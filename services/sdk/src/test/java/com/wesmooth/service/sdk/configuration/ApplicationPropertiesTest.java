/* WeSmooth! 2024 */
package com.wesmooth.service.sdk.configuration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.env.Environment;

@ExtendWith(MockitoExtension.class)
public class ApplicationPropertiesTest {
  private static final String KEY_TEST_PROPERTY_1 = "KEY123";
  private static final String VALUE_TEST_PROPERTY_1 = "true";
  private static final String KEY_TEST_PROPERTY_2 = "KEY.MY.Key";
  private static final String VALUE_TEST_PROPERTY_2 = "Value 123";
  @Mock Environment environmentMock;

  @BeforeEach
  public void setup() {
    when(environmentMock.getProperty(eq(KEY_TEST_PROPERTY_1))).thenReturn(VALUE_TEST_PROPERTY_1);
    when(environmentMock.getProperty(eq(KEY_TEST_PROPERTY_2))).thenReturn(VALUE_TEST_PROPERTY_2);
  }

  @Test
  public void testApplicationProperties() {
    ApplicationProperties applicationProperties = new ApplicationProperties(environmentMock);
    assertEquals(VALUE_TEST_PROPERTY_1, applicationProperties.getProperty(KEY_TEST_PROPERTY_1));
    assertEquals(VALUE_TEST_PROPERTY_2, applicationProperties.getProperty(KEY_TEST_PROPERTY_2));
    assertNull(applicationProperties.getProperty("DUMMY"));
  }
}
