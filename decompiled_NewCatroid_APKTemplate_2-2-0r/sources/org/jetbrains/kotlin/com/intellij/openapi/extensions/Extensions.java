package org.jetbrains.kotlin.com.intellij.openapi.extensions;

import org.jetbrains.kotlin.com.intellij.openapi.Disposable;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.impl.ExtensionsAreaImpl;
import org.jetbrains.kotlin.com.intellij.openapi.util.Disposer;

/* JADX INFO: loaded from: classes3.dex */
public final class Extensions {
    private static ExtensionsAreaImpl ourRootArea;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 3 || i == 5 || i == 7 || i == 9 || i == 12) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 3 || i == 5 || i == 7 || i == 9 || i == 12) ? 2 : 3];
        switch (i) {
            case 2:
                objArr[0] = "parentDisposable";
                break;
            case 3:
            case 5:
            case 7:
            case 9:
            case 12:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/extensions/Extensions";
                break;
            case 4:
            case 6:
            case 8:
            case 10:
                objArr[0] = "extensionPointName";
                break;
            case 11:
                objArr[0] = "extClass";
                break;
            default:
                objArr[0] = "area";
                break;
        }
        if (i == 3) {
            objArr[1] = "getArea";
        } else if (i == 5 || i == 7 || i == 9) {
            objArr[1] = "getExtensions";
        } else if (i != 12) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/extensions/Extensions";
        } else {
            objArr[1] = "findExtension";
        }
        switch (i) {
            case 3:
            case 5:
            case 7:
            case 9:
            case 12:
                break;
            case 4:
            case 6:
            case 8:
                objArr[2] = "getExtensions";
                break;
            case 10:
            case 11:
                objArr[2] = "findExtension";
                break;
            default:
                objArr[2] = "setRootArea";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 3 && i != 5 && i != 7 && i != 9 && i != 12) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    public static void setRootArea(ExtensionsAreaImpl extensionsAreaImpl, Disposable disposable) {
        if (extensionsAreaImpl == null) {
            $$$reportNull$$$0(1);
        }
        if (disposable == null) {
            $$$reportNull$$$0(2);
        }
        final ExtensionsAreaImpl extensionsAreaImpl2 = ourRootArea;
        ourRootArea = extensionsAreaImpl;
        Disposer.register(disposable, new Disposable() { // from class: org.jetbrains.kotlin.com.intellij.openapi.extensions.Extensions$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.com.intellij.openapi.Disposable
            public final void dispose() {
                Extensions.lambda$setRootArea$0(extensionsAreaImpl2);
            }
        });
    }

    static /* synthetic */ void lambda$setRootArea$0(ExtensionsAreaImpl extensionsAreaImpl) {
        ourRootArea.notifyAreaReplaced(extensionsAreaImpl);
        ourRootArea = extensionsAreaImpl;
    }

    @Deprecated
    public static ExtensionsArea getRootArea() {
        return ourRootArea;
    }
}
