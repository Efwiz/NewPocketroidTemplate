package org.jetbrains.kotlin.idea.references;

import java.util.Collection;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.KtConstructorDelegationReferenceExpression;

/* JADX INFO: compiled from: KtConstructorDelegationReference.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\b&\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0002¢\u0006\u0002\u0010\u0004J\b\u0010\n\u001a\u00020\u000bH\u0016J\u0012\u0010\f\u001a\u0004\u0018\u00010\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016R\u001a\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\t¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/idea/references/KtConstructorDelegationReference;", "Lorg/jetbrains/kotlin/idea/references/KtSimpleReference;", "Lorg/jetbrains/kotlin/psi/KtConstructorDelegationReferenceExpression;", "expression", "(Lorg/jetbrains/kotlin/psi/KtConstructorDelegationReferenceExpression;)V", "resolvesByNames", "", "Lorg/jetbrains/kotlin/name/Name;", "getResolvesByNames", "()Ljava/util/Collection;", "getRangeInElement", "Lorg/jetbrains/kotlin/com/intellij/openapi/util/TextRange;", "handleElementRename", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "newElementName", "", "kt-references"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class KtConstructorDelegationReference extends KtSimpleReference<KtConstructorDelegationReferenceExpression> {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtConstructorDelegationReference(KtConstructorDelegationReferenceExpression expression) {
        super(expression);
        Intrinsics.checkNotNullParameter(expression, "expression");
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReferenceBase, org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public TextRange getRangeInElement() {
        return new TextRange(0, ((KtConstructorDelegationReferenceExpression) getPsiElement()).getTextLength());
    }

    @Override // org.jetbrains.kotlin.idea.references.KtReference
    public Collection<Name> getResolvesByNames() {
        return CollectionsKt.emptyList();
    }

    @Override // org.jetbrains.kotlin.idea.references.AbstractKtReference, org.jetbrains.kotlin.com.intellij.psi.PsiReferenceBase, org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public PsiElement handleElementRename(String newElementName) {
        Intrinsics.checkNotNullParameter(newElementName, "newElementName");
        return getExpression();
    }
}
