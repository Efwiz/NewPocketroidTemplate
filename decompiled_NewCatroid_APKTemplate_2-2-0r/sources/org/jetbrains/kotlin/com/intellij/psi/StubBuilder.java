package org.jetbrains.kotlin.com.intellij.psi;

import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;

/* JADX INFO: loaded from: classes3.dex */
public interface StubBuilder {
    StubElement buildStubTree(PsiFile psiFile);

    boolean skipChildProcessingWhenBuildingStubs(ASTNode aSTNode, ASTNode aSTNode2);
}
