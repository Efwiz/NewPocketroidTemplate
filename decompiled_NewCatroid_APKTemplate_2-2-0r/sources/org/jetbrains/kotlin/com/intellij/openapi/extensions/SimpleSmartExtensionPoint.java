package org.jetbrains.kotlin.com.intellij.openapi.extensions;

import org.jetbrains.kotlin.com.intellij.util.SmartList;

/* JADX INFO: loaded from: classes3.dex */
public abstract class SimpleSmartExtensionPoint<T> extends SmartExtensionPoint<T, T> {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 1 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 1 ? 3 : 2];
        if (i != 1) {
            objArr[0] = "t";
        } else {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/extensions/SimpleSmartExtensionPoint";
        }
        if (i != 1) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/extensions/SimpleSmartExtensionPoint";
        } else {
            objArr[1] = "getExtension";
        }
        if (i != 1) {
            objArr[2] = "getExtension";
        }
        String str2 = String.format(str, objArr);
        if (i == 1) {
            throw new IllegalStateException(str2);
        }
    }

    public SimpleSmartExtensionPoint() {
        super(new SmartList());
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.extensions.SmartExtensionPoint
    protected final T getExtension(T t) {
        if (t == null) {
            $$$reportNull$$$0(0);
        }
        if (t == null) {
            $$$reportNull$$$0(1);
        }
        return t;
    }

    public static <T> SimpleSmartExtensionPoint<T> create(final ExtensionPointName<T> extensionPointName) {
        return new SimpleSmartExtensionPoint<T>() { // from class: org.jetbrains.kotlin.com.intellij.openapi.extensions.SimpleSmartExtensionPoint.1
            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "org/jetbrains/kotlin/com/intellij/openapi/extensions/SimpleSmartExtensionPoint$1", "getExtensionPoint"));
            }

            @Override // org.jetbrains.kotlin.com.intellij.openapi.extensions.SmartExtensionPoint
            protected ExtensionPoint<T> getExtensionPoint() {
                ExtensionPoint<T> extensionPoint = Extensions.getRootArea().getExtensionPoint(extensionPointName);
                if (extensionPoint == null) {
                    $$$reportNull$$$0(0);
                }
                return extensionPoint;
            }
        };
    }

    public static <T> SimpleSmartExtensionPoint<T> create(final ExtensionsArea extensionsArea, final ExtensionPointName<T> extensionPointName) {
        return new SimpleSmartExtensionPoint<T>() { // from class: org.jetbrains.kotlin.com.intellij.openapi.extensions.SimpleSmartExtensionPoint.2
            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "org/jetbrains/kotlin/com/intellij/openapi/extensions/SimpleSmartExtensionPoint$2", "getExtensionPoint"));
            }

            @Override // org.jetbrains.kotlin.com.intellij.openapi.extensions.SmartExtensionPoint
            protected ExtensionPoint<T> getExtensionPoint() {
                ExtensionPoint<T> extensionPoint = extensionsArea.getExtensionPoint(extensionPointName);
                if (extensionPoint == null) {
                    $$$reportNull$$$0(0);
                }
                return extensionPoint;
            }
        };
    }
}
