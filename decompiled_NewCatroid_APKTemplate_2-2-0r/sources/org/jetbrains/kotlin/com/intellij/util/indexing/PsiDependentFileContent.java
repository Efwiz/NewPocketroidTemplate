package org.jetbrains.kotlin.com.intellij.util.indexing;

import org.jetbrains.kotlin.com.intellij.lang.LighterAST;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;

/* JADX INFO: loaded from: classes3.dex */
public interface PsiDependentFileContent extends FileContent {
    LighterAST getLighterAST();

    PsiFile getPsiFile();
}
