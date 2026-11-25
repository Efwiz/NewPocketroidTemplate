package org.jetbrains.kotlin.com.intellij.psi.meta;

import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.util.ArrayUtilRt;

/* JADX INFO: loaded from: classes3.dex */
public interface PsiMetaData {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[2];
        objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/meta/PsiMetaData";
        if (i != 1) {
            objArr[1] = "getDependencies";
        } else {
            objArr[1] = "getDependences";
        }
        throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", objArr));
    }

    PsiElement getDeclaration();

    String getName();

    String getName(PsiElement psiElement);

    void init(PsiElement psiElement);

    default Object[] getDependencies() {
        Object[] dependences = getDependences();
        if (dependences == null) {
            $$$reportNull$$$0(0);
        }
        return dependences;
    }

    @Deprecated
    default Object[] getDependences() {
        Object[] objArr = ArrayUtilRt.EMPTY_OBJECT_ARRAY;
        if (objArr == null) {
            $$$reportNull$$$0(1);
        }
        return objArr;
    }
}
