package org.jetbrains.kotlin.com.intellij.psi;

import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;

/* JADX INFO: loaded from: classes3.dex */
public abstract class AbstractElementManipulator<T extends PsiElement> implements ElementManipulator<T> {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/AbstractElementManipulator";
        if (i != 1) {
            objArr[2] = "handleContentChange";
        } else {
            objArr[2] = "getRangeInElement";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.ElementManipulator
    public T handleContentChange(T t, String str) throws IncorrectOperationException {
        if (t == null) {
            $$$reportNull$$$0(0);
        }
        return handleContentChange(t, getRangeInElement(t), str);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.ElementManipulator
    public TextRange getRangeInElement(T t) {
        if (t == null) {
            $$$reportNull$$$0(1);
        }
        return new TextRange(0, t.getTextLength());
    }
}
