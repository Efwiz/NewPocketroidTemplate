package org.jetbrains.kotlin.psi;

import kotlin.Metadata;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: KtSimpleNameExpression.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\n\u0010\u0002\u001a\u0004\u0018\u00010\u0003H&J\b\u0010\u0004\u001a\u00020\u0005H&J\b\u0010\u0006\u001a\u00020\u0007H&J\b\u0010\b\u001a\u00020\u0003H&J\b\u0010\t\u001a\u00020\nH&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u000bÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/psi/KtSimpleNameExpression;", "Lorg/jetbrains/kotlin/psi/KtReferenceExpression;", "getIdentifier", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "getReferencedName", "", "getReferencedNameAsName", "Lorg/jetbrains/kotlin/name/Name;", "getReferencedNameElement", "getReferencedNameElementType", "Lorg/jetbrains/kotlin/com/intellij/psi/tree/IElementType;", "psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface KtSimpleNameExpression extends KtReferenceExpression {
    PsiElement getIdentifier();

    String getReferencedName();

    Name getReferencedNameAsName();

    PsiElement getReferencedNameElement();

    IElementType getReferencedNameElementType();
}
