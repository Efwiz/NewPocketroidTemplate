package org.jetbrains.kotlin.com.intellij.psi.controlFlow;

import java.util.List;
import org.jetbrains.kotlin.com.intellij.psi.PsiReturnStatement;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;

/* JADX INFO: loaded from: classes3.dex */
public interface ReturnStatementsVisitor {
    void visit(List<PsiReturnStatement> list) throws IncorrectOperationException;
}
