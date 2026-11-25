package org.jetbrains.kotlin.com.intellij.openapi.extensions;

import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: loaded from: classes3.dex */
public interface ExtensionsArea {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i == 1) {
            objArr[0] = "extensionPointBeanClass";
        } else if (i != 2) {
            objArr[0] = "extensionPointName";
        } else {
            objArr[0] = Namer.METADATA_CLASS_KIND;
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/extensions/ExtensionsArea";
        objArr[2] = "registerExtensionPoint";
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    <T> ExtensionPoint<T> getExtensionPoint(String str);

    <T> ExtensionPoint<T> getExtensionPoint(ExtensionPointName<T> extensionPointName);

    <T> ExtensionPoint<T> getExtensionPointIfRegistered(String str);

    boolean hasExtensionPoint(String str);

    boolean hasExtensionPoint(ExtensionPointName<?> extensionPointName);

    void registerExtensionPoint(String str, String str2, ExtensionPoint.Kind kind, boolean z);

    @Deprecated
    default void registerExtensionPoint(String str, String str2, ExtensionPoint.Kind kind) {
        if (str == null) {
            $$$reportNull$$$0(0);
        }
        if (str2 == null) {
            $$$reportNull$$$0(1);
        }
        if (kind == null) {
            $$$reportNull$$$0(2);
        }
        registerExtensionPoint(str, str2, kind, false);
    }
}
