package org.jetbrains.kotlin.com.intellij.openapi.util;

/* JADX INFO: loaded from: classes3.dex */
public abstract class AtomicClearableLazyValue<T> extends ClearableLazyValue<T> {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "org/jetbrains/kotlin/com/intellij/openapi/util/AtomicClearableLazyValue", "getValue"));
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.util.ClearableLazyValue
    public final synchronized T getValue() {
        T t;
        t = (T) super.getValue();
        if (t == null) {
            $$$reportNull$$$0(0);
        }
        return t;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.util.ClearableLazyValue
    public final synchronized void drop() {
        super.drop();
    }
}
