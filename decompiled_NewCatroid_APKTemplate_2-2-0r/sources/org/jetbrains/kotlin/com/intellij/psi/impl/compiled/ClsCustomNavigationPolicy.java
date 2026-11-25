package org.jetbrains.kotlin.com.intellij.psi.impl.compiled;

import org.jetbrains.kotlin.com.intellij.openapi.extensions.ExtensionPointName;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;

/* JADX INFO: loaded from: classes3.dex */
public interface ClsCustomNavigationPolicy {
    public static final ExtensionPointName<ClsCustomNavigationPolicy> EP_NAME = ExtensionPointName.create("org.jetbrains.kotlin.com.intellij.psi.clsCustomNavigationPolicy");

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i == 1) {
            objArr[0] = "clsClass";
        } else if (i == 2) {
            objArr[0] = "clsMethod";
        } else if (i != 3) {
            objArr[0] = "clsFile";
        } else {
            objArr[0] = "clsField";
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/compiled/ClsCustomNavigationPolicy";
        objArr[2] = "getNavigationElement";
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    default PsiElement getNavigationElement(ClsClassImpl clsClassImpl) {
        if (clsClassImpl != null) {
            return null;
        }
        $$$reportNull$$$0(1);
        return null;
    }

    default PsiElement getNavigationElement(ClsFieldImpl clsFieldImpl) {
        if (clsFieldImpl != null) {
            return null;
        }
        $$$reportNull$$$0(3);
        return null;
    }

    default PsiElement getNavigationElement(ClsFileImpl clsFileImpl) {
        if (clsFileImpl != null) {
            return null;
        }
        $$$reportNull$$$0(0);
        return null;
    }

    default PsiElement getNavigationElement(ClsMethodImpl clsMethodImpl) {
        if (clsMethodImpl != null) {
            return null;
        }
        $$$reportNull$$$0(2);
        return null;
    }
}
