package org.openrewrite.okhttp;

import org.junit.jupiter.api.Test;
import org.openrewrite.InMemoryExecutionContext;
import org.openrewrite.java.JavaParser;
import org.openrewrite.java.testing.okhttp.ReorderRequestBodyCreateArguments;
import org.openrewrite.test.RecipeSpec;
import org.openrewrite.test.RewriteTest;

import static org.openrewrite.java.Assertions.java;

public class ReorderRequestBodyCreateArgumentsTest implements RewriteTest {
    @Override
    public void defaults(RecipeSpec spec) {
        spec
          .parser(JavaParser.fromJavaVersion().classpathFromResources(new InMemoryExecutionContext(),
            "squareup-okhttp"))
          .recipe(new ReorderRequestBodyCreateArguments());
    }

    @Test
    void reorderArguments() {
        //language=java
        rewriteRun(
          java(
            """
              import com.squareup.okhttp.MediaType;
              import com.squareup.okhttp.RequestBody;
              
              class MyTest {
                  void testMethod() {
                      MediaType mediaType = MediaType.parse("application/json");
                      RequestBody body = RequestBody.create(mediaType,"some request");
                  }
              }
              """,
            """
              import com.squareup.okhttp.MediaType;
              import com.squareup.okhttp.RequestBody;
              
              class MyTest {
                  void testMethod() {
                      MediaType mediaType = MediaType.parse("application/json");
                      RequestBody body = RequestBody.create("some request", mediaType);
                  }
              }
              """
          )
        );
    }
}
