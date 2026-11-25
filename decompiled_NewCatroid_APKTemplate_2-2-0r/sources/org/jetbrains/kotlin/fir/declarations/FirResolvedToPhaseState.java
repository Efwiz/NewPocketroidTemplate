package org.jetbrains.kotlin.fir.declarations;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;

/* JADX INFO: compiled from: FirResolveState.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u0000 \t2\u00020\u0001:\u0001\tB\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u0007\u001a\u00020\bH\u0016R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/fir/declarations/FirResolvedToPhaseState;", "Lorg/jetbrains/kotlin/fir/declarations/FirResolveState;", "resolvePhase", "Lorg/jetbrains/kotlin/fir/declarations/FirResolvePhase;", "(Lorg/jetbrains/kotlin/fir/declarations/FirResolvePhase;)V", "getResolvePhase", "()Lorg/jetbrains/kotlin/fir/declarations/FirResolvePhase;", "toString", "", "Companion", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirResolvedToPhaseState extends FirResolveState {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final List<FirResolvedToPhaseState> phases;
    private final FirResolvePhase resolvePhase;

    @Override // org.jetbrains.kotlin.fir.declarations.FirResolveState
    public FirResolvePhase getResolvePhase() {
        return this.resolvePhase;
    }

    /* JADX INFO: compiled from: FirResolveState.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0011\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\bH\u0086\u0002R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/fir/declarations/FirResolvedToPhaseState$Companion;", "", "()V", "phases", "", "Lorg/jetbrains/kotlin/fir/declarations/FirResolvedToPhaseState;", "invoke", TypedValues.CycleType.S_WAVE_PHASE, "Lorg/jetbrains/kotlin/fir/declarations/FirResolvePhase;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final FirResolvedToPhaseState invoke(FirResolvePhase phase) {
            Intrinsics.checkNotNullParameter(phase, "phase");
            return (FirResolvedToPhaseState) FirResolvedToPhaseState.phases.get(phase.ordinal());
        }
    }

    private FirResolvedToPhaseState(FirResolvePhase firResolvePhase) {
        super(null);
        this.resolvePhase = firResolvePhase;
    }

    static {
        FirResolvePhase[] firResolvePhaseArrValues = FirResolvePhase.values();
        ArrayList arrayList = new ArrayList(firResolvePhaseArrValues.length);
        for (FirResolvePhase firResolvePhase : firResolvePhaseArrValues) {
            arrayList.add(new FirResolvedToPhaseState(firResolvePhase));
        }
        phases = arrayList;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirResolveState
    public String toString() {
        return "ResolvedTo(" + getResolvePhase() + ')';
    }
}
