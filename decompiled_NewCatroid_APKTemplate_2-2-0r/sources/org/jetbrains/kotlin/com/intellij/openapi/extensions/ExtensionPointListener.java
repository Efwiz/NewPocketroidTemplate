package org.jetbrains.kotlin.com.intellij.openapi.extensions;

import org.apache.xerces.impl.xs.SchemaSymbols;

/* JADX INFO: loaded from: classes3.dex */
public interface ExtensionPointListener<T> {
    public static final ExtensionPointListener<?>[] EMPTY_ARRAY = new ExtensionPointListener[0];

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 4 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 4 ? 3 : 2];
        if (i == 1 || i == 3) {
            objArr[0] = "pluginDescriptor";
        } else if (i != 4) {
            objArr[0] = SchemaSymbols.ATTVAL_EXTENSION;
        } else {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/extensions/ExtensionPointListener";
        }
        if (i != 4) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/extensions/ExtensionPointListener";
        } else {
            objArr[1] = "emptyArray";
        }
        if (i == 2 || i == 3) {
            objArr[2] = "extensionRemoved";
        } else if (i != 4) {
            objArr[2] = "extensionAdded";
        }
        String str2 = String.format(str, objArr);
        if (i == 4) {
            throw new IllegalStateException(str2);
        }
    }

    default void extensionAdded(T t, PluginDescriptor pluginDescriptor) {
        if (t == null) {
            $$$reportNull$$$0(0);
        }
        if (pluginDescriptor == null) {
            $$$reportNull$$$0(1);
        }
    }

    default void extensionRemoved(T t, PluginDescriptor pluginDescriptor) {
        if (t == null) {
            $$$reportNull$$$0(2);
        }
        if (pluginDescriptor == null) {
            $$$reportNull$$$0(3);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    static <T> ExtensionPointListener<T>[] emptyArray() {
        ExtensionPointListener<T>[] extensionPointListenerArr = (ExtensionPointListener<T>[]) EMPTY_ARRAY;
        if (extensionPointListenerArr == null) {
            $$$reportNull$$$0(4);
        }
        return extensionPointListenerArr;
    }
}
