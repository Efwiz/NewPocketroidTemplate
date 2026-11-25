package org.jetbrains.kotlin.asJava.finder;

import org.jetbrains.kotlin.asJava.KotlinAsJavaSupport;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiManager;
import org.jetbrains.kotlin.com.intellij.psi.impl.file.PsiPackageImpl;
import org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.kotlin.name.FqName;

/* JADX INFO: loaded from: classes2.dex */
public class KtLightPackage extends PsiPackageImpl {
    private final FqName fqName;
    private final GlobalSearchScope scope;

    public KtLightPackage(PsiManager psiManager, FqName fqName, GlobalSearchScope globalSearchScope) {
        super(psiManager, fqName.asString());
        this.fqName = fqName;
        this.scope = globalSearchScope;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.file.PsiPackageBase, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement copy() {
        return new KtLightPackage(getManager(), this.fqName, this.scope);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.file.PsiPackageImpl, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement, org.jetbrains.kotlin.com.intellij.pom.PomTarget
    public boolean isValid() {
        return KotlinAsJavaSupport.getInstance(getProject()).packageExists(this.fqName, this.scope);
    }
}
