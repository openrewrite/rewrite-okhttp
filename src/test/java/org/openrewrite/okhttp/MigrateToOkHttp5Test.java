/*
 * Copyright 2021 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * https://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.openrewrite.okhttp;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.openrewrite.DocumentExample;
import org.openrewrite.config.Environment;
import org.openrewrite.test.RecipeSpec;
import org.openrewrite.test.RewriteTest;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.openrewrite.maven.Assertions.pomXml;

class MigrateToOkHttp5Test implements RewriteTest {

    public void defaults(RecipeSpec spec) {
        spec.recipe(Environment.builder()
          .scanRuntimeClasspath("org.openrewrite.okhttp")
          .build()
          .activateRecipes("org.openrewrite.okhttp.UpgradeOkHttp5"));
    }

    @Nested
    class Dependencies {
        @Test
        @DocumentExample
        void mavenDependency5x() {
            rewriteRun(
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
                        <artifactId>okhttp</artifactId>
                        <version>4.11.0</version>
                      </dependency>
                    </dependencies>
                  </project>
                  """,
                spec -> spec.after(actual -> {
                      Matcher matcher = Pattern.compile("<version>(5\\.0\\.\\d+(-(alpha|beta)\\.\\d+)?)</version>").matcher(actual);
                      assertTrue(matcher.find(), actual);
                      return """
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
                              <artifactId>okhttp</artifactId>
                              <version>%s</version>
                            </dependency>
                          </dependencies>
                        </project>
                        """.formatted(matcher.group(1));
                  }
                )
              )
            );
        }
    }
}