package org.jetbrains.kotlin.psi;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.psi.stubs.elements.KtStubElementTypes;

/* JADX INFO: compiled from: KtContractEffectList.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a\u0010\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u0003¨\u0006\u0004"}, d2 = {"getContractEffects", "", "Lorg/jetbrains/kotlin/psi/KtContractEffect;", "Lorg/jetbrains/kotlin/psi/KtContractEffectList;", "psi"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class KtContractEffectListKt {
    public static final List<KtContractEffect> getContractEffects(KtContractEffectList ktContractEffectList) {
        Intrinsics.checkNotNullParameter(ktContractEffectList, "<this>");
        List stubOrPsiChildrenAsList = ktContractEffectList.getStubOrPsiChildrenAsList(KtStubElementTypes.CONTRACT_EFFECT);
        Intrinsics.checkNotNullExpressionValue(stubOrPsiChildrenAsList, "getStubOrPsiChildrenAsList(...)");
        return stubOrPsiChildrenAsList;
    }
}
