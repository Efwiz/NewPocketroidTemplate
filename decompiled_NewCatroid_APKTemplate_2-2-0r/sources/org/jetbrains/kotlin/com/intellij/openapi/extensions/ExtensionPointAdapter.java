package org.jetbrains.kotlin.com.intellij.openapi.extensions;

import org.apache.xerces.impl.xs.SchemaSymbols;

/* JADX INFO: loaded from: classes3.dex */
public abstract class ExtensionPointAdapter<T> implements ExtensionPointAndAreaListener<T> {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i == 1 || i == 3) {
            objArr[0] = "pluginDescriptor";
        } else {
            objArr[0] = SchemaSymbols.ATTVAL_EXTENSION;
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/extensions/ExtensionPointAdapter";
        if (i == 2 || i == 3) {
            objArr[2] = "extensionRemoved";
        } else {
            objArr[2] = "extensionAdded";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    public abstract void extensionListChanged();

    @Override // org.jetbrains.kotlin.com.intellij.openapi.extensions.ExtensionPointListener
    public final void extensionAdded(T t, PluginDescriptor pluginDescriptor) {
        if (t == null) {
            $$$reportNull$$$0(0);
        }
        if (pluginDescriptor == null) {
            $$$reportNull$$$0(1);
        }
        extensionListChanged();
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.extensions.ExtensionPointListener
    public final void extensionRemoved(T t, PluginDescriptor pluginDescriptor) {
        if (t == null) {
            $$$reportNull$$$0(2);
        }
        if (pluginDescriptor == null) {
            $$$reportNull$$$0(3);
        }
        extensionListChanged();
    }
}
