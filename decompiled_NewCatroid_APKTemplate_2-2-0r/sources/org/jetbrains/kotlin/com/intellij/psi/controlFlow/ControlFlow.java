package org.jetbrains.kotlin.com.intellij.psi.controlFlow;

import java.util.List;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;

/* JADX INFO: loaded from: classes3.dex */
public interface ControlFlow {
    public static final ControlFlow EMPTY = new ControlFlowImpl();

    PsiElement getElement(int i);

    int getEndOffset(PsiElement psiElement);

    List<Instruction> getInstructions();

    int getSize();

    int getStartOffset(PsiElement psiElement);

    boolean isConstantConditionOccurred();
}
