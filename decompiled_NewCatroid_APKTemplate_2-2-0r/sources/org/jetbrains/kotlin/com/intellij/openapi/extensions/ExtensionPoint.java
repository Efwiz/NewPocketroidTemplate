package org.jetbrains.kotlin.com.intellij.openapi.extensions;

import java.util.List;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.jetbrains.kotlin.com.intellij.openapi.Disposable;

/* JADX INFO: loaded from: classes3.dex */
public interface ExtensionPoint<T> {

    public enum Kind {
        INTERFACE,
        BEAN_CLASS
    }

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        objArr[0] = SchemaSymbols.ATTVAL_EXTENSION;
        objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/extensions/ExtensionPoint";
        if (i != 1) {
            objArr[2] = "registerExtension";
        } else {
            objArr[2] = "hasExtension";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    void addExtensionPointListener(ExtensionPointListener<T> extensionPointListener, boolean z, Disposable disposable);

    List<T> getExtensionList();

    T[] getExtensions();

    void registerExtension(T t, Disposable disposable);

    @Deprecated
    void registerExtension(T t, LoadingOrder loadingOrder);

    void registerExtension(T t, LoadingOrder loadingOrder, Disposable disposable);

    void registerExtension(T t, PluginDescriptor pluginDescriptor, Disposable disposable);

    /* JADX INFO: renamed from: removeExtensionPointListener */
    void m6481xfe024b65(ExtensionPointListener<T> extensionPointListener);

    int size();

    void unregisterExtension(Class<? extends T> cls);

    @Deprecated
    default void registerExtension(T t) {
        if (t == null) {
            $$$reportNull$$$0(0);
        }
        registerExtension(t, LoadingOrder.ANY);
    }
}
