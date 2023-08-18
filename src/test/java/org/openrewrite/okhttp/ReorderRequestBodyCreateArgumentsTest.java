/*
 * Copyright 2023 the original author or authors.
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

import org.junit.jupiter.api.Test;
import org.openrewrite.java.JavaParser;
import org.openrewrite.java.testing.okhttp.ReorderRequestBodyCreateArguments;
import org.openrewrite.test.RecipeSpec;
import org.openrewrite.test.RewriteTest;

import static org.openrewrite.java.Assertions.java;

class ReorderRequestBodyCreateArgumentsTest implements RewriteTest {
    @Override
    public void defaults(RecipeSpec spec) {
        spec
          .recipe(new ReorderRequestBodyCreateArguments())
          .parser(JavaParser.fromJavaVersion().classpath("okhttp", "okio"));
    }

    @Test
    void reorderArguments() {
        //language=java
        rewriteRun(
          java("""
            import okhttp3.MediaType;
            import okhttp3.RequestBody;

            class MyTest {
                void testMethod() {
                    MediaType mediaType = MediaType.parse("application/json");
                    RequestBody body = RequestBody.create(mediaType, "some request");
                }
            }
            """, """
            import okhttp3.MediaType;
            import okhttp3.RequestBody;
                          
            class MyTest {
                void testMethod() {
                    MediaType mediaType = MediaType.parse("application/json");
                    RequestBody body = RequestBody.create("some request", mediaType);
                }
            }
            """)
        );
    }
}
