package org.jetbrains.kotlin.com.intellij.openapi.extensions;

/* JADX INFO: loaded from: classes3.dex */
public interface ExtensionPointAndAreaListener<T> extends ExtensionPointListener<T> {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "oldArea", "org/jetbrains/kotlin/com/intellij/openapi/extensions/ExtensionPointAndAreaListener", "areaReplaced"));
    }

    default void areaReplaced(ExtensionsArea extensionsArea) {
        if (extensionsArea == null) {
            $$$reportNull$$$0(0);
        }
    }
}
