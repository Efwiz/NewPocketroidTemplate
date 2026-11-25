package org.jetbrains.kotlin.psi;

import org.jetbrains.kotlin.com.intellij.psi.PsiNameIdentifierOwner;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: loaded from: classes6.dex */
public interface KtNamedDeclaration extends PsiNameIdentifierOwner, KtDeclaration, KtNamed, KtStatementExpression {
    /* JADX INFO: renamed from: getFqName */
    FqName mo7391getFqName();

    Name getNameAsSafeName();
}
