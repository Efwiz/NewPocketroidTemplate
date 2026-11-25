package org.jetbrains.kotlin.com.intellij.openapi.extensions;

import org.jetbrains.kotlin.com.intellij.openapi.extensions.impl.ExtensionPointImpl;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.impl.ExtensionsAreaImpl;

/* JADX INFO: loaded from: classes3.dex */
public abstract class BaseExtensionPointName<T> {
    private final String myName;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 2) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 1 || i == 2) ? 2 : 3];
        if (i == 1 || i == 2) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/extensions/BaseExtensionPointName";
        } else {
            objArr[0] = "name";
        }
        if (i == 1) {
            objArr[1] = "getName";
        } else if (i != 2) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/extensions/BaseExtensionPointName";
        } else {
            objArr[1] = "getPointImpl";
        }
        if (i != 1 && i != 2) {
            objArr[2] = "<init>";
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 2) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    public BaseExtensionPointName(String str) {
        if (str == null) {
            $$$reportNull$$$0(0);
        }
        this.myName = str;
    }

    public final String getName() {
        String str = this.myName;
        if (str == null) {
            $$$reportNull$$$0(1);
        }
        return str;
    }

    public final String toString() {
        return this.myName;
    }

    protected final ExtensionPointImpl<T> getPointImpl(AreaInstance areaInstance) {
        ExtensionPointImpl<T> extensionPoint = ((ExtensionsAreaImpl) (areaInstance == null ? Extensions.getRootArea() : areaInstance.getExtensionArea())).getExtensionPoint(getName());
        if (extensionPoint == null) {
            $$$reportNull$$$0(2);
        }
        return extensionPoint;
    }
}
