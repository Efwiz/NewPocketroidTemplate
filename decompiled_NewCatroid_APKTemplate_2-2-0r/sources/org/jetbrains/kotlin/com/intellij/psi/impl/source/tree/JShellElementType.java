package org.jetbrains.kotlin.com.intellij.psi.impl.source.tree;

import java.util.concurrent.atomic.AtomicInteger;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.lang.java.JShellLanguage;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiJShellHolderMethodImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiJShellImportHolderImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiJShellRootClassImpl;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.psi.tree.IFileElementType;

/* JADX INFO: loaded from: classes3.dex */
public interface JShellElementType {
    public static final IFileElementType FILE = new IFileElementType("JSHELL_FILE", JShellLanguage.INSTANCE);
    public static final IElementType ROOT_CLASS = new IJShellElementType("JSHELL_ROOT_CLASS") { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JShellElementType.1
        private final AtomicInteger ourClassCounter = new AtomicInteger();

        @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.IJShellElementType
        public PsiElement createPsi(ASTNode aSTNode) {
            return new PsiJShellRootClassImpl(aSTNode, this.ourClassCounter.getAndIncrement());
        }
    };
    public static final IElementType STATEMENTS_HOLDER = new IJShellElementType("JSHELL_STATEMENTS_HOLDER") { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JShellElementType.2
        private final AtomicInteger ourMethodCounter = new AtomicInteger();

        @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.IJShellElementType
        public PsiElement createPsi(ASTNode aSTNode) {
            return new PsiJShellHolderMethodImpl(aSTNode, this.ourMethodCounter.getAndIncrement());
        }
    };
    public static final IElementType IMPORT_HOLDER = new IJShellElementType("JSHELL_IMPORT_HOLDER") { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JShellElementType.3
        @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.IJShellElementType
        public PsiElement createPsi(ASTNode aSTNode) {
            return new PsiJShellImportHolderImpl(aSTNode);
        }
    };
}
