/*
 * Copyright 2024 the original author or authors.
 * <p>
 * Licensed under the Moderne Source Available License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * https://docs.moderne.io/licensing/moderne-source-available-license
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.openrewrite.okhttp;

import org.junit.jupiter.api.Test;
import org.openrewrite.DocumentExample;
import org.openrewrite.test.RecipeSpec;
import org.openrewrite.test.RewriteTest;

import java.util.regex.Pattern;

import static org.openrewrite.maven.Assertions.pomXml;

class UpgradeMockWebServer3Test implements RewriteTest {

    @DocumentExample
    @Test
    void upgradeMockWebServer3Core() {
        rewriteRun(
          spec -> spec.recipeFromResources("org.openrewrite.okhttp.UpgradeMockWebServer3"),
          //language=xml
          pomXml(
            """
              <?xml version="1.0" encoding="UTF-8"?>
              <project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
                <modelVersion>4.0.0</modelVersion>
                <groupId>com.example</groupId>
                <artifactId>demo</artifactId>
                <version>0.0.1-SNAPSHOT</version>
                <dependencies>
                  <dependency>
                    <groupId>com.squareup.okhttp3</groupId>
                    <artifactId>mockwebserver</artifactId>
                    <version>4.11.0</version>
                    <scope>test</scope>
                  </dependency>
                </dependencies>
              </project>
              """,
            spec -> spec.after(actual -> """
              <?xml version="1.0" encoding="UTF-8"?>
              <project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
                <modelVersion>4.0.0</modelVersion>
                <groupId>com.example</groupId>
                <artifactId>demo</artifactId>
                <version>0.0.1-SNAPSHOT</version>
                <dependencies>
                  <dependency>
                    <groupId>com.squareup.okhttp3</groupId>
                    <artifactId>mockwebserver3</artifactId>
                    <version>%s</version>
                    <scope>test</scope>
                  </dependency>
                </dependencies>
              </project>
              """.formatted(Pattern.compile("<version>(5\\..*)</version>").matcher(actual).results().findFirst().orElseThrow().group(1))
            )
          )
        );
    }

    @Test
    void upgradeMockWebServer3JUnit4() {
        rewriteRun(
          spec -> spec.recipeFromResources("org.openrewrite.okhttp.UpgradeMockWebServer3JUnit4"),
          //language=xml
          pomXml(
            """
              <?xml version="1.0" encoding="UTF-8"?>
              <project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
                <modelVersion>4.0.0</modelVersion>
                <groupId>com.example</groupId>
                <artifactId>demo</artifactId>
                <version>0.0.1-SNAPSHOT</version>
                <dependencies>
                  <dependency>
                    <groupId>com.squareup.okhttp3</groupId>
                    <artifactId>mockwebserver</artifactId>
                    <version>4.11.0</version>
                    <scope>test</scope>
                  </dependency>
                </dependencies>
              </project>
              """,
            spec -> spec.after(actual -> """
              <?xml version="1.0" encoding="UTF-8"?>
              <project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
                <modelVersion>4.0.0</modelVersion>
                <groupId>com.example</groupId>
                <artifactId>demo</artifactId>
                <version>0.0.1-SNAPSHOT</version>
                <dependencies>
                  <dependency>
                    <groupId>com.squareup.okhttp3</groupId>
                    <artifactId>mockwebserver3-junit4</artifactId>
                    <version>%s</version>
                    <scope>test</scope>
                  </dependency>
                </dependencies>
              </project>
              """.formatted(Pattern.compile("<version>(5\\..*)</version>").matcher(actual).results().findFirst().orElseThrow().group(1))
            )
          )
        );
    }

    @Test
    void upgradeMockWebServer3JUnit5() {
        rewriteRun(
          spec -> spec.recipeFromResources("org.openrewrite.okhttp.UpgradeMockWebServer3JUnit5"),
          //language=xml
          pomXml(
            """
              <?xml version="1.0" encoding="UTF-8"?>
              <project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
                <modelVersion>4.0.0</modelVersion>
                <groupId>com.example</groupId>
                <artifactId>demo</artifactId>
                <version>0.0.1-SNAPSHOT</version>
                <dependencies>
                  <dependency>
                    <groupId>com.squareup.okhttp3</groupId>
                    <artifactId>mockwebserver</artifactId>
                    <version>4.11.0</version>
                    <scope>test</scope>
                  </dependency>
                </dependencies>
              </project>
              """,
            spec -> spec.after(actual -> """
              <?xml version="1.0" encoding="UTF-8"?>
              <project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
                <modelVersion>4.0.0</modelVersion>
                <groupId>com.example</groupId>
                <artifactId>demo</artifactId>
                <version>0.0.1-SNAPSHOT</version>
                <dependencies>
                  <dependency>
                    <groupId>com.squareup.okhttp3</groupId>
                    <artifactId>mockwebserver3-junit5</artifactId>
                    <version>%s</version>
                    <scope>test</scope>
                  </dependency>
                </dependencies>
              </project>
              """.formatted(Pattern.compile("<version>(5\\..*)</version>").matcher(actual).results().findFirst().orElseThrow().group(1))
            )
          )
        );
    }
}
