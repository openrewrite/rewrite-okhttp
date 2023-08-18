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

import org.openrewrite.Recipe;
import org.openrewrite.java.ReorderMethodArguments;

import java.util.Collections;
import java.util.List;

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
    public List<Recipe> getRecipeList() {
        return Collections.singletonList(
                new ReorderMethodArguments(
                        "okhttp3.RequestBody create(okhttp3.MediaType, java.lang.String)",
                        new String[]{"contentType", "content"},
                        new String[]{"content", "contentType"},
                        null, null
                )
        );
    }
}
