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
package org.openrewrite.java.testing.okhttp;

import org.openrewrite.ExecutionContext;
import org.openrewrite.Recipe;
import org.openrewrite.TreeVisitor;
import org.openrewrite.java.JavaIsoVisitor;
import org.openrewrite.java.JavaTemplate;
import org.openrewrite.java.MethodMatcher;
import org.openrewrite.java.tree.Expression;
import org.openrewrite.java.tree.J;

public class ReorderRequestBodyCreateArguments extends Recipe {
    @Override
    public String getDisplayName() {
        return "Reorder the arguments of `RequestBody.create()`";
    }

    @Override
    public String getDescription() {
        return "Reorder the arguments of `RequestBody.create()`. The `MediaType` argument should come before the `String`.";
    }

    @Override
    public TreeVisitor<?, ExecutionContext> getVisitor() {
        return new ReorderArgumentsVisitor();
    }

    private static class ReorderArgumentsVisitor extends JavaIsoVisitor<ExecutionContext> {
        private static MethodMatcher requestBodyCreateMatcher = new MethodMatcher("com.squareup.okhttp.RequestBody create(com.square.okhttp.RequestBody, java.lang.String)");
        @Override
        public J.MethodInvocation visitMethodInvocation(J.MethodInvocation mi, ExecutionContext ctx) {
            J.MethodInvocation m = super.visitMethodInvocation(mi, ctx);

            if (!requestBodyCreateMatcher.matches(m)) {
                return m;
            }

            Expression firstArg = m.getArguments().get(0);
            Expression secondArg = m.getArguments().get(1);

            return JavaTemplate.builder("RequestBody.create(#{any()}, #{any()})")
                    .build()
                    .apply(getCursor(), m.getCoordinates().replace(), secondArg, firstArg);
        }
    }
}
