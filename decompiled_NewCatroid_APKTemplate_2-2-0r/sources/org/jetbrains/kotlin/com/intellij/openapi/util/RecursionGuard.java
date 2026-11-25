package org.jetbrains.kotlin.com.intellij.openapi.util;

import java.util.List;
import java.util.Objects;
import org.antlr.stringtemplate.language.ASTExpr;

/* JADX INFO: loaded from: classes3.dex */
public abstract class RecursionGuard<Key> {

    public interface StackStamp {
        boolean mayCacheNow();
    }

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 2 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 2 ? 3 : 2];
        if (i == 1) {
            objArr[0] = "computation";
        } else if (i != 2) {
            objArr[0] = ASTExpr.DEFAULT_MAP_KEY_NAME;
        } else {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/util/RecursionGuard";
        }
        if (i != 2) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/util/RecursionGuard";
        } else {
            objArr[1] = "markStack";
        }
        if (i != 2) {
            objArr[2] = "doPreventingRecursion";
        }
        String str2 = String.format(str, objArr);
        if (i == 2) {
            throw new IllegalStateException(str2);
        }
    }

    public abstract <T, E extends Throwable> T computePreventingRecursion(Key key, boolean z, ThrowableComputable<T, E> throwableComputable) throws Throwable;

    public abstract List<? extends Key> currentStack();

    public abstract void prohibitResultCaching(Key key);

    public <T> T doPreventingRecursion(Key key, boolean z, final Computable<T> computable) {
        if (key == null) {
            $$$reportNull$$$0(0);
        }
        if (computable == null) {
            $$$reportNull$$$0(1);
        }
        Objects.requireNonNull(computable);
        return (T) computePreventingRecursion(key, z, new ThrowableComputable() { // from class: org.jetbrains.kotlin.com.intellij.openapi.util.RecursionGuard$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.com.intellij.openapi.util.ThrowableComputable
            public final Object compute() {
                return computable.compute();
            }
        });
    }
}
