package org.jetbrains.kotlin.fir.contracts.impl;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.contracts.FirContractElementDeclaration;
import org.jetbrains.kotlin.fir.contracts.FirEffectDeclaration;
import org.jetbrains.kotlin.fir.contracts.FirResolvedContractDescription;
import org.jetbrains.kotlin.fir.visitors.FirTransformer;
import org.jetbrains.kotlin.fir.visitors.FirTransformerUtilKt;
import org.jetbrains.kotlin.fir.visitors.FirVisitor;

/* JADX INFO: compiled from: FirResolvedContractDescriptionImpl.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0000\u0018\u00002\u00020\u0001B+\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u0005¢\u0006\u0002\u0010\tJ5\u0010\u000f\u001a\u00020\u0010\"\u0004\b\u0000\u0010\u0011\"\u0004\b\u0001\u0010\u00122\u0012\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u0002H\u0011\u0012\u0004\u0012\u0002H\u00120\u00142\u0006\u0010\u0015\u001a\u0002H\u0012H\u0016¢\u0006\u0002\u0010\u0016J)\u0010\u0017\u001a\u00020\u0000\"\u0004\b\u0000\u0010\u00122\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u0002H\u00120\u00192\u0006\u0010\u0015\u001a\u0002H\u0012H\u0016¢\u0006\u0002\u0010\u001aR\u001a\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0016\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u001a\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000b¨\u0006\u001b"}, d2 = {"Lorg/jetbrains/kotlin/fir/contracts/impl/FirResolvedContractDescriptionImpl;", "Lorg/jetbrains/kotlin/fir/contracts/FirResolvedContractDescription;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "effects", "", "Lorg/jetbrains/kotlin/fir/contracts/FirEffectDeclaration;", "unresolvedEffects", "Lorg/jetbrains/kotlin/fir/contracts/FirContractElementDeclaration;", "(Lorg/jetbrains/kotlin/KtSourceElement;Ljava/util/List;Ljava/util/List;)V", "getEffects", "()Ljava/util/List;", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", "getUnresolvedEffects", "acceptChildren", "", "R", "D", "visitor", "Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;", "data", "(Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;Ljava/lang/Object;)V", "transformChildren", "transformer", "Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;", "(Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/contracts/impl/FirResolvedContractDescriptionImpl;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirResolvedContractDescriptionImpl extends FirResolvedContractDescription {
    private final List<FirEffectDeclaration> effects;
    private final KtSourceElement source;
    private final List<FirContractElementDeclaration> unresolvedEffects;

    @Override // org.jetbrains.kotlin.fir.FirPureAbstractElement, org.jetbrains.kotlin.fir.FirElement
    public /* bridge */ /* synthetic */ FirElement transformChildren(FirTransformer firTransformer, Object obj) {
        return transformChildren((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.contracts.FirResolvedContractDescription, org.jetbrains.kotlin.fir.contracts.FirContractDescription, org.jetbrains.kotlin.fir.FirElement
    public KtSourceElement getSource() {
        return this.source;
    }

    @Override // org.jetbrains.kotlin.fir.contracts.FirResolvedContractDescription
    public List<FirEffectDeclaration> getEffects() {
        return this.effects;
    }

    @Override // org.jetbrains.kotlin.fir.contracts.FirResolvedContractDescription
    public List<FirContractElementDeclaration> getUnresolvedEffects() {
        return this.unresolvedEffects;
    }

    public FirResolvedContractDescriptionImpl(KtSourceElement ktSourceElement, List<FirEffectDeclaration> effects, List<FirContractElementDeclaration> unresolvedEffects) {
        Intrinsics.checkNotNullParameter(effects, "effects");
        Intrinsics.checkNotNullParameter(unresolvedEffects, "unresolvedEffects");
        this.source = ktSourceElement;
        this.effects = effects;
        this.unresolvedEffects = unresolvedEffects;
    }

    @Override // org.jetbrains.kotlin.fir.FirPureAbstractElement, org.jetbrains.kotlin.fir.FirElement
    public <R, D> void acceptChildren(FirVisitor<? extends R, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        Iterator<T> it = getEffects().iterator();
        while (it.hasNext()) {
            ((FirEffectDeclaration) it.next()).accept(visitor, data);
        }
        Iterator<T> it2 = getUnresolvedEffects().iterator();
        while (it2.hasNext()) {
            ((FirContractElementDeclaration) it2.next()).accept(visitor, data);
        }
    }

    @Override // org.jetbrains.kotlin.fir.FirPureAbstractElement, org.jetbrains.kotlin.fir.FirElement
    public <D> FirResolvedContractDescriptionImpl transformChildren(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        FirTransformerUtilKt.transformInplace(getEffects(), transformer, data);
        FirTransformerUtilKt.transformInplace(getUnresolvedEffects(), transformer, data);
        return this;
    }
}
