package org.jetbrains.kotlin.com.intellij.util;

import java.util.Collection;
import org.jetbrains.kotlin.com.intellij.openapi.application.ApplicationManager;

/* JADX INFO: loaded from: classes3.dex */
abstract class Queries {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "org/jetbrains/kotlin/com/intellij/util/Queries", "getInstance"));
    }

    protected abstract <I, O> Query<O> transforming(Query<? extends I> query, java.util.function.Function<? super I, ? extends Collection<? extends O>> function);

    Queries() {
    }

    static Queries getInstance() {
        Queries queries = (Queries) ApplicationManager.getApplication().getService(Queries.class);
        if (queries == null) {
            $$$reportNull$$$0(0);
        }
        return queries;
    }
}
