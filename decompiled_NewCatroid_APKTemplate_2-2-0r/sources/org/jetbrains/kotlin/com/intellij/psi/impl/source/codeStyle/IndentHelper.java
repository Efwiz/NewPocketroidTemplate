package org.jetbrains.kotlin.com.intellij.psi.impl.source.codeStyle;

import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.openapi.application.ApplicationManager;
import org.jetbrains.kotlin.com.intellij.openapi.fileTypes.FileType;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;

/* JADX INFO: loaded from: classes3.dex */
public abstract class IndentHelper {
    public abstract int getIndent(PsiFile psiFile, ASTNode aSTNode);

    public abstract int getIndent(PsiFile psiFile, ASTNode aSTNode, boolean z);

    public static IndentHelper getInstance() {
        return (IndentHelper) ApplicationManager.getApplication().getService(IndentHelper.class);
    }

    @Deprecated
    public final int getIndent(Project project, FileType fileType, ASTNode aSTNode) {
        return getIndent(getFile(aSTNode), aSTNode);
    }

    @Deprecated
    public final int getIndent(Project project, FileType fileType, ASTNode aSTNode, boolean z) {
        return getIndent(getFile(aSTNode), aSTNode, z);
    }

    private static PsiFile getFile(ASTNode aSTNode) {
        return aSTNode.getPsi().getContainingFile();
    }
}
