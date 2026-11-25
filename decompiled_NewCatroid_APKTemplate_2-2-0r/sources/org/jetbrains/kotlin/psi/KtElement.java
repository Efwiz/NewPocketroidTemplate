package org.jetbrains.kotlin.psi;

import kotlin.Deprecated;
import kotlin.Metadata;
import org.jetbrains.kotlin.com.intellij.psi.NavigatablePsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiReference;

/* JADX INFO: compiled from: KtElement.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u00012\u00020\u0002J5\u0010\u0003\u001a\u0002H\u0004\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u00052\u0012\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u00072\u0006\u0010\b\u001a\u0002H\u0005H&¢\u0006\u0002\u0010\tJ/\u0010\n\u001a\u00020\u000b\"\u0004\b\u0000\u0010\u00052\u0012\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u0002H\u00050\u00072\u0006\u0010\b\u001a\u0002H\u0005H&¢\u0006\u0002\u0010\rJ\n\u0010\u000e\u001a\u0004\u0018\u00010\u000fH'ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0010À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/psi/KtElement;", "Lorg/jetbrains/kotlin/com/intellij/psi/NavigatablePsiElement;", "Lorg/jetbrains/kotlin/psi/KtPureElement;", "accept", "R", "D", "visitor", "Lorg/jetbrains/kotlin/psi/KtVisitor;", "data", "(Lorg/jetbrains/kotlin/psi/KtVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "acceptChildren", "", "Ljava/lang/Void;", "(Lorg/jetbrains/kotlin/psi/KtVisitor;Ljava/lang/Object;)V", "getReference", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiReference;", "psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface KtElement extends NavigatablePsiElement, KtPureElement {
    <R, D> R accept(KtVisitor<R, D> visitor, D data);

    <D> void acceptChildren(KtVisitor<Void, D> visitor, D data);

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    @Deprecated(message = "Don't use getReference() on KtElement for the choice is unpredictable")
    PsiReference getReference();
}
