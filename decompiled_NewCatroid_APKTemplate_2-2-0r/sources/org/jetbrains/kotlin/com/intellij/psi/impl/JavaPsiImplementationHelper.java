package org.jetbrains.kotlin.com.intellij.psi.impl;

import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.pom.java.LanguageLevel;
import org.jetbrains.kotlin.com.intellij.psi.PsiCatchSection;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiImportList;
import org.jetbrains.kotlin.com.intellij.psi.PsiImportStatementBase;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaModule;
import org.jetbrains.kotlin.com.intellij.psi.PsiMember;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;

/* JADX INFO: loaded from: classes3.dex */
public abstract class JavaPsiImplementationHelper {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "project", "org/jetbrains/kotlin/com/intellij/psi/impl/JavaPsiImplementationHelper", "getInstance"));
    }

    public abstract PsiElement getClsFileNavigationElement(PsiJavaFile psiJavaFile);

    public abstract ASTNode getDefaultImportAnchor(PsiImportList psiImportList, PsiImportStatementBase psiImportStatementBase);

    public abstract PsiElement getDefaultMemberAnchor(PsiClass psiClass, PsiMember psiMember);

    public abstract LanguageLevel getEffectiveLanguageLevel(VirtualFile virtualFile);

    public abstract PsiClass getOriginalClass(PsiClass psiClass);

    public abstract PsiJavaModule getOriginalModule(PsiJavaModule psiJavaModule);

    public abstract void setupCatchBlock(String str, PsiType psiType, PsiElement psiElement, PsiCatchSection psiCatchSection);

    public static JavaPsiImplementationHelper getInstance(Project project) {
        if (project == null) {
            $$$reportNull$$$0(0);
        }
        return (JavaPsiImplementationHelper) project.getService(JavaPsiImplementationHelper.class);
    }
}
