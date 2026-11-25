package org.jetbrains.kotlin.psi;

import java.util.Collections;
import java.util.List;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;

/* JADX INFO: loaded from: classes6.dex */
public interface KtCallableDeclaration extends KtNamedDeclaration, KtTypeParameterListOwner {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "org/jetbrains/kotlin/psi/KtCallableDeclaration", "getContextReceivers"));
    }

    PsiElement getColon();

    /* JADX INFO: renamed from: getReceiverTypeReference */
    KtTypeReference mo7393getReceiverTypeReference();

    /* JADX INFO: renamed from: getTypeReference */
    KtTypeReference mo7396getTypeReference();

    KtParameterList getValueParameterList();

    List<KtParameter> getValueParameters();

    /* JADX INFO: renamed from: setTypeReference */
    KtTypeReference mo7397setTypeReference(KtTypeReference ktTypeReference);

    default List<KtContextReceiver> getContextReceivers() {
        List<KtContextReceiver> listEmptyList = Collections.emptyList();
        if (listEmptyList == null) {
            $$$reportNull$$$0(0);
        }
        return listEmptyList;
    }
}
