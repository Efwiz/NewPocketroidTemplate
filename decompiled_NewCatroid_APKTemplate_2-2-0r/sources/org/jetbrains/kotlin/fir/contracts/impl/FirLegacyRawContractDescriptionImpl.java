package org.jetbrains.kotlin.fir.contracts.impl;

import com.google.firebase.analytics.FirebaseAnalytics;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.contracts.FirLegacyRawContractDescription;
import org.jetbrains.kotlin.fir.expressions.FirFunctionCall;
import org.jetbrains.kotlin.fir.visitors.FirTransformer;
import org.jetbrains.kotlin.fir.visitors.FirVisitor;

/* JADX INFO: compiled from: FirLegacyRawContractDescriptionImpl.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0000\u0018\u00002\u00020\u0001B\u0017\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J5\u0010\r\u001a\u00020\u000e\"\u0004\b\u0000\u0010\u000f\"\u0004\b\u0001\u0010\u00102\u0012\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u0002H\u000f\u0012\u0004\u0012\u0002H\u00100\u00122\u0006\u0010\u0013\u001a\u0002H\u0010H\u0016¢\u0006\u0002\u0010\u0014J)\u0010\u0015\u001a\u00020\u0000\"\u0004\b\u0000\u0010\u00102\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u0002H\u00100\u00172\u0006\u0010\u0013\u001a\u0002H\u0010H\u0016¢\u0006\u0002\u0010\u0018R\u001a\u0010\u0004\u001a\u00020\u0005X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u0016\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/fir/contracts/impl/FirLegacyRawContractDescriptionImpl;", "Lorg/jetbrains/kotlin/fir/contracts/FirLegacyRawContractDescription;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "contractCall", "Lorg/jetbrains/kotlin/fir/expressions/FirFunctionCall;", "(Lorg/jetbrains/kotlin/KtSourceElement;Lorg/jetbrains/kotlin/fir/expressions/FirFunctionCall;)V", "getContractCall", "()Lorg/jetbrains/kotlin/fir/expressions/FirFunctionCall;", "setContractCall", "(Lorg/jetbrains/kotlin/fir/expressions/FirFunctionCall;)V", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", "acceptChildren", "", "R", "D", "visitor", "Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;", "data", "(Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;Ljava/lang/Object;)V", "transformChildren", "transformer", "Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;", "(Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/contracts/impl/FirLegacyRawContractDescriptionImpl;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirLegacyRawContractDescriptionImpl extends FirLegacyRawContractDescription {
    private FirFunctionCall contractCall;
    private final KtSourceElement source;

    @Override // org.jetbrains.kotlin.fir.FirPureAbstractElement, org.jetbrains.kotlin.fir.FirElement
    public /* bridge */ /* synthetic */ FirElement transformChildren(FirTransformer firTransformer, Object obj) {
        return transformChildren((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.contracts.FirLegacyRawContractDescription, org.jetbrains.kotlin.fir.contracts.FirContractDescription, org.jetbrains.kotlin.fir.FirElement
    public KtSourceElement getSource() {
        return this.source;
    }

    @Override // org.jetbrains.kotlin.fir.contracts.FirLegacyRawContractDescription
    public FirFunctionCall getContractCall() {
        return this.contractCall;
    }

    public void setContractCall(FirFunctionCall firFunctionCall) {
        Intrinsics.checkNotNullParameter(firFunctionCall, "<set-?>");
        this.contractCall = firFunctionCall;
    }

    public FirLegacyRawContractDescriptionImpl(KtSourceElement ktSourceElement, FirFunctionCall contractCall) {
        Intrinsics.checkNotNullParameter(contractCall, "contractCall");
        this.source = ktSourceElement;
        this.contractCall = contractCall;
    }

    @Override // org.jetbrains.kotlin.fir.FirPureAbstractElement, org.jetbrains.kotlin.fir.FirElement
    public <R, D> void acceptChildren(FirVisitor<? extends R, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        getContractCall().accept(visitor, data);
    }

    @Override // org.jetbrains.kotlin.fir.FirPureAbstractElement, org.jetbrains.kotlin.fir.FirElement
    public <D> FirLegacyRawContractDescriptionImpl transformChildren(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        setContractCall((FirFunctionCall) getContractCall().transform(transformer, data));
        return this;
    }
}
