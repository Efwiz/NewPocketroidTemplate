package org.jetbrains.kotlin.fir.contracts.builder;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.firebase.analytics.FirebaseAnalytics;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.fir.builder.FirBuilderDsl;
import org.jetbrains.kotlin.fir.contracts.FirLegacyRawContractDescription;
import org.jetbrains.kotlin.fir.contracts.impl.FirLegacyRawContractDescriptionImpl;
import org.jetbrains.kotlin.fir.expressions.FirFunctionCall;

/* JADX INFO: compiled from: FirLegacyRawContractDescriptionBuilder.kt */
/* JADX INFO: loaded from: classes4.dex */
@FirBuilderDsl
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0006\u0010\u000f\u001a\u00020\u0010R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000e¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/fir/contracts/builder/FirLegacyRawContractDescriptionBuilder;", "", "()V", "contractCall", "Lorg/jetbrains/kotlin/fir/expressions/FirFunctionCall;", "getContractCall", "()Lorg/jetbrains/kotlin/fir/expressions/FirFunctionCall;", "setContractCall", "(Lorg/jetbrains/kotlin/fir/expressions/FirFunctionCall;)V", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", "setSource", "(Lorg/jetbrains/kotlin/KtSourceElement;)V", JsonPOJOBuilder.DEFAULT_BUILD_METHOD, "Lorg/jetbrains/kotlin/fir/contracts/FirLegacyRawContractDescription;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirLegacyRawContractDescriptionBuilder {
    public FirFunctionCall contractCall;
    private KtSourceElement source;

    public final KtSourceElement getSource() {
        return this.source;
    }

    public final void setSource(KtSourceElement ktSourceElement) {
        this.source = ktSourceElement;
    }

    public final FirFunctionCall getContractCall() {
        FirFunctionCall firFunctionCall = this.contractCall;
        if (firFunctionCall != null) {
            return firFunctionCall;
        }
        Intrinsics.throwUninitializedPropertyAccessException("contractCall");
        return null;
    }

    public final void setContractCall(FirFunctionCall firFunctionCall) {
        Intrinsics.checkNotNullParameter(firFunctionCall, "<set-?>");
        this.contractCall = firFunctionCall;
    }

    public final FirLegacyRawContractDescription build() {
        return new FirLegacyRawContractDescriptionImpl(this.source, getContractCall());
    }
}
