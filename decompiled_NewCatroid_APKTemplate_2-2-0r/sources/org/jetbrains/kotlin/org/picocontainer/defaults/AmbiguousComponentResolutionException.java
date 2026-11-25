package org.jetbrains.kotlin.org.picocontainer.defaults;

import java.util.Arrays;
import org.jetbrains.kotlin.org.picocontainer.PicoIntrospectionException;

/* JADX INFO: loaded from: classes4.dex */
public final class AmbiguousComponentResolutionException extends PicoIntrospectionException {
    private final Object[] ambiguousComponentKeys;
    private final Class<?> ambiguousDependency;
    private Class<?> component;

    public AmbiguousComponentResolutionException(Class cls, Object[] objArr) {
        super("");
        this.ambiguousDependency = cls;
        this.ambiguousComponentKeys = (Object[]) objArr.clone();
    }

    @Override // java.lang.Throwable
    public String getMessage() {
        return this.component + " has ambiguous dependency on " + this.ambiguousDependency + ", resolves to multiple classes: " + Arrays.asList(getAmbiguousComponentKeys());
    }

    public Object[] getAmbiguousComponentKeys() {
        return this.ambiguousComponentKeys;
    }

    public void setComponent(Class cls) {
        this.component = cls;
    }
}
