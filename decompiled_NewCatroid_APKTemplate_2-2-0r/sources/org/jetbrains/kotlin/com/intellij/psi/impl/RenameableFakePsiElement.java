package org.jetbrains.kotlin.com.intellij.psi.impl;

import javax.swing.Icon;
import org.jetbrains.kotlin.com.intellij.lang.Language;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiManager;
import org.jetbrains.kotlin.com.intellij.psi.meta.PsiMetaData;
import org.jetbrains.kotlin.com.intellij.psi.meta.PsiMetaOwner;
import org.jetbrains.kotlin.com.intellij.psi.meta.PsiPresentableMetaData;

/* JADX INFO: loaded from: classes3.dex */
public abstract class RenameableFakePsiElement extends FakePsiElement implements PsiMetaOwner, PsiPresentableMetaData {
    private final PsiElement myParent;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[2];
        objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/RenameableFakePsiElement";
        if (i != 1) {
            objArr[1] = "getLanguage";
        } else {
            objArr[1] = "getProject";
        }
        throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", objArr));
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.meta.PsiMetaData
    public PsiElement getDeclaration() {
        return this;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.meta.PsiMetaOwner
    public PsiMetaData getMetaData() {
        return this;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.navigation.NavigationItem, org.jetbrains.kotlin.com.intellij.pom.PomNamedTarget, org.jetbrains.kotlin.com.intellij.psi.PsiNamedElement, org.jetbrains.kotlin.com.intellij.psi.PsiQualifiedNamedElement
    public abstract String getName();

    @Override // org.jetbrains.kotlin.com.intellij.psi.meta.PsiMetaData
    public void init(PsiElement psiElement) {
    }

    protected RenameableFakePsiElement(PsiElement psiElement) {
        this.myParent = psiElement;
    }

    /* JADX INFO: renamed from: getParent */
    public PsiElement getLightParent() {
        return this.myParent;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiFile getContainingFile() {
        return this.myParent.getContainingFile();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.FakePsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public Language getLanguage() {
        Language language = getContainingFile().getLanguage();
        if (language == null) {
            $$$reportNull$$$0(0);
        }
        return language;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public Project getProject() {
        Project project = this.myParent.getProject();
        if (project == null) {
            $$$reportNull$$$0(1);
        }
        return project;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.FakePsiElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiManager getManager() {
        return PsiManager.getInstance(getProject());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.meta.PsiMetaData
    public String getName(PsiElement psiElement) {
        return getName();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.FakePsiElement, org.jetbrains.kotlin.com.intellij.navigation.ItemPresentation
    public final Icon getIcon(boolean z) {
        return getIcon();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.FakePsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public TextRange getTextRange() {
        return TextRange.from(0, 0);
    }
}
