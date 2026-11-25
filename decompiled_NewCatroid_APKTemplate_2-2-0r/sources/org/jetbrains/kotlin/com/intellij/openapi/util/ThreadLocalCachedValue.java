package org.jetbrains.kotlin.com.intellij.openapi.util;

import java.lang.ref.SoftReference;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX INFO: loaded from: classes3.dex */
public abstract class ThreadLocalCachedValue<T> {
    private final ThreadLocal<SoftReference<T>> myThreadLocal = new ThreadLocal<>();

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "value", "org/jetbrains/kotlin/com/intellij/openapi/util/ThreadLocalCachedValue", ConfigConstants.CONFIG_INIT_SECTION));
    }

    protected abstract T create();

    protected void init(T t) {
        if (t == null) {
            $$$reportNull$$$0(0);
        }
    }

    public T getValue() {
        T t = (T) org.jetbrains.kotlin.com.intellij.reference.SoftReference.dereference(this.myThreadLocal.get());
        if (t == null) {
            T tCreate = create();
            this.myThreadLocal.set(new SoftReference<>(tCreate));
            return tCreate;
        }
        init(t);
        return t;
    }
}
