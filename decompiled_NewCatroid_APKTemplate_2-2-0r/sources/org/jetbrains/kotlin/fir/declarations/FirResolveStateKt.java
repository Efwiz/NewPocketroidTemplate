package org.jetbrains.kotlin.fir.declarations;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.fir.FirElementWithResolveState;
import org.jetbrains.kotlin.fir.declarations.synthetic.FirSyntheticPropertyAccessor;

/* JADX INFO: compiled from: FirResolveState.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0014\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\u001a\n\u0010\u0007\u001a\u00020\b*\u00020\u0001\"\u001b\u0010\u0000\u001a\u00020\u0001*\u00020\u00028F¢\u0006\f\u0012\u0004\b\u0003\u0010\u0004\u001a\u0004\b\u0005\u0010\u0006¨\u0006\t"}, d2 = {"resolvePhase", "Lorg/jetbrains/kotlin/fir/declarations/FirResolvePhase;", "Lorg/jetbrains/kotlin/fir/FirElementWithResolveState;", "getResolvePhase$annotations", "(Lorg/jetbrains/kotlin/fir/FirElementWithResolveState;)V", "getResolvePhase", "(Lorg/jetbrains/kotlin/fir/FirElementWithResolveState;)Lorg/jetbrains/kotlin/fir/declarations/FirResolvePhase;", "asResolveState", "Lorg/jetbrains/kotlin/fir/declarations/FirResolvedToPhaseState;", Constants.TYPE_TREE}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FirResolveStateKt {
    public static /* synthetic */ void getResolvePhase$annotations(FirElementWithResolveState firElementWithResolveState) {
    }

    public static final FirResolvedToPhaseState asResolveState(FirResolvePhase firResolvePhase) {
        Intrinsics.checkNotNullParameter(firResolvePhase, "<this>");
        return FirResolvedToPhaseState.INSTANCE.invoke(firResolvePhase);
    }

    public static final FirResolvePhase getResolvePhase(FirElementWithResolveState firElementWithResolveState) {
        Intrinsics.checkNotNullParameter(firElementWithResolveState, "<this>");
        if (firElementWithResolveState instanceof FirSyntheticPropertyAccessor) {
            return getResolvePhase(((FirSyntheticPropertyAccessor) firElementWithResolveState).getDelegate());
        }
        return firElementWithResolveState.getResolveState().getResolvePhase();
    }
}
