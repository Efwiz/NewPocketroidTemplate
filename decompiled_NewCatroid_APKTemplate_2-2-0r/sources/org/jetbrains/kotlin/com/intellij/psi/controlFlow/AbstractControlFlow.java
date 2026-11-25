package org.jetbrains.kotlin.com.intellij.psi.controlFlow;

import java.util.List;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Object2LongMap;

/* JADX INFO: loaded from: classes3.dex */
abstract class AbstractControlFlow implements ControlFlow {
    final Object2LongMap<PsiElement> myElementToOffsetMap;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i == 1 || i == 2) {
            objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
        } else {
            objArr[0] = "map";
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/controlFlow/AbstractControlFlow";
        if (i == 1) {
            objArr[2] = "getStartOffset";
        } else if (i != 2) {
            objArr[2] = "<init>";
        } else {
            objArr[2] = "getEndOffset";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    AbstractControlFlow(Object2LongMap<PsiElement> object2LongMap) {
        if (object2LongMap == null) {
            $$$reportNull$$$0(0);
        }
        this.myElementToOffsetMap = object2LongMap;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.controlFlow.ControlFlow
    public int getStartOffset(PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(1);
        }
        return (int) (this.myElementToOffsetMap.getOrDefault(psiElement, -1L) & 4294967295L);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.controlFlow.ControlFlow
    public int getEndOffset(PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(2);
        }
        return (int) (this.myElementToOffsetMap.getOrDefault(psiElement, -1L) >>> 32);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        List<Instruction> instructions = getInstructions();
        for (int i = 0; i < instructions.size(); i++) {
            sb.append(i).append(": ").append(instructions.get(i)).append("\n");
        }
        return sb.toString();
    }
}
