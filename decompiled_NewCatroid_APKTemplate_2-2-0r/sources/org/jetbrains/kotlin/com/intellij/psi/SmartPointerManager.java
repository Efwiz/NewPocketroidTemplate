package org.jetbrains.kotlin.com.intellij.psi;

import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;

/* JADX INFO: loaded from: classes3.dex */
public abstract class SmartPointerManager {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 3) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 1 || i == 3) ? 2 : 3];
        if (i == 1 || i == 3) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/SmartPointerManager";
        } else {
            objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
        }
        if (i == 1) {
            objArr[1] = "createPointer";
        } else if (i != 3) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/SmartPointerManager";
        } else {
            objArr[1] = "createLazyPointer";
        }
        if (i != 1) {
            if (i == 2) {
                objArr[2] = "createLazyPointer";
            } else if (i != 3) {
                objArr[2] = "createPointer";
            }
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 3) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    public abstract <E extends PsiElement> SmartPsiElementPointer<E> createSmartPsiElementPointer(E e);

    public abstract <E extends PsiElement> SmartPsiElementPointer<E> createSmartPsiElementPointer(E e, PsiFile psiFile);

    public abstract SmartPsiFileRange createSmartPsiFileRangePointer(PsiFile psiFile, TextRange textRange);

    public abstract boolean pointToTheSameElement(SmartPsiElementPointer<?> smartPsiElementPointer, SmartPsiElementPointer<?> smartPsiElementPointer2);

    public abstract void removePointer(SmartPsiElementPointer<?> smartPsiElementPointer);

    public static SmartPointerManager getInstance(Project project) {
        return (SmartPointerManager) project.getService(SmartPointerManager.class);
    }

    public static <E extends PsiElement> SmartPsiElementPointer<E> createPointer(E e) {
        if (e == null) {
            $$$reportNull$$$0(0);
        }
        SmartPsiElementPointer<E> smartPsiElementPointerCreateSmartPsiElementPointer = getInstance(e.getProject()).createSmartPsiElementPointer(e);
        if (smartPsiElementPointerCreateSmartPsiElementPointer == null) {
            $$$reportNull$$$0(1);
        }
        return smartPsiElementPointerCreateSmartPsiElementPointer;
    }

    @Deprecated
    public <E extends PsiElement> SmartPsiElementPointer<E> createLazyPointer(E e) {
        if (e == null) {
            $$$reportNull$$$0(2);
        }
        SmartPsiElementPointer<E> smartPsiElementPointerCreateSmartPsiElementPointer = createSmartPsiElementPointer(e);
        if (smartPsiElementPointerCreateSmartPsiElementPointer == null) {
            $$$reportNull$$$0(3);
        }
        return smartPsiElementPointerCreateSmartPsiElementPointer;
    }
}
