package org.jetbrains.kotlin.fir.declarations;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;

/* JADX INFO: compiled from: FirResolveState.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u0000 \t2\u00020\u0001:\u0001\tB\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u0007\u001a\u00020\bH\u0016R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/fir/declarations/FirInProcessOfResolvingToPhaseStateWithoutBarrier;", "Lorg/jetbrains/kotlin/fir/declarations/FirInProcessOfResolvingToPhaseState;", "resolvingTo", "Lorg/jetbrains/kotlin/fir/declarations/FirResolvePhase;", "(Lorg/jetbrains/kotlin/fir/declarations/FirResolvePhase;)V", "getResolvingTo", "()Lorg/jetbrains/kotlin/fir/declarations/FirResolvePhase;", "toString", "", "Companion", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirInProcessOfResolvingToPhaseStateWithoutBarrier extends FirInProcessOfResolvingToPhaseState {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final List<FirInProcessOfResolvingToPhaseState> phases;
    private final FirResolvePhase resolvingTo;

    @Override // org.jetbrains.kotlin.fir.declarations.FirInProcessOfResolvingToPhaseState
    public FirResolvePhase getResolvingTo() {
        return this.resolvingTo;
    }

    /* JADX INFO: compiled from: FirResolveState.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0011\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\bH\u0086\u0002R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/fir/declarations/FirInProcessOfResolvingToPhaseStateWithoutBarrier$Companion;", "", "()V", "phases", "", "Lorg/jetbrains/kotlin/fir/declarations/FirInProcessOfResolvingToPhaseState;", "invoke", TypedValues.CycleType.S_WAVE_PHASE, "Lorg/jetbrains/kotlin/fir/declarations/FirResolvePhase;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final FirInProcessOfResolvingToPhaseState invoke(FirResolvePhase phase) {
            Intrinsics.checkNotNullParameter(phase, "phase");
            if (phase != FirResolvePhase.RAW_FIR) {
                return (FirInProcessOfResolvingToPhaseState) FirInProcessOfResolvingToPhaseStateWithoutBarrier.phases.get(phase.ordinal() - 1);
            }
            throw new IllegalArgumentException(("Cannot resolve to " + FirResolvePhase.RAW_FIR + " as it's a first phase").toString());
        }
    }

    private FirInProcessOfResolvingToPhaseStateWithoutBarrier(FirResolvePhase firResolvePhase) {
        super(null);
        this.resolvingTo = firResolvePhase;
    }

    static {
        List listDrop = ArraysKt.drop(FirResolvePhase.values(), 1);
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listDrop, 10));
        Iterator it = listDrop.iterator();
        while (it.hasNext()) {
            arrayList.add(new FirInProcessOfResolvingToPhaseStateWithoutBarrier((FirResolvePhase) it.next()));
        }
        phases = arrayList;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirResolveState
    public String toString() {
        return "ResolvingTo(" + getResolvingTo() + ')';
    }
}
