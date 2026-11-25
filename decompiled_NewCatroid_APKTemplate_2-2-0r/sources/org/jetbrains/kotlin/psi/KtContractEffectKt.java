package org.jetbrains.kotlin.psi;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;

/* JADX INFO: compiled from: KtContractEffect.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a\n\u0010\u0000\u001a\u00020\u0001*\u00020\u0002¨\u0006\u0003"}, d2 = {"getExpression", "Lorg/jetbrains/kotlin/psi/KtExpression;", "Lorg/jetbrains/kotlin/psi/KtContractEffect;", "psi"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class KtContractEffectKt {
    public static final KtExpression getExpression(KtContractEffect ktContractEffect) {
        Intrinsics.checkNotNullParameter(ktContractEffect, "<this>");
        PsiElement childOfType = PsiTreeUtil.getChildOfType(ktContractEffect, KtExpression.class);
        Intrinsics.checkNotNull(childOfType);
        return (KtExpression) childOfType;
    }
}
