package org.jetbrains.kotlin.fir.declarations;

import java.util.concurrent.CountDownLatch;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;

/* JADX INFO: compiled from: FirResolveState.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\b\u0010\u000b\u001a\u00020\fH\u0016R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/fir/declarations/FirInProcessOfResolvingToPhaseStateWithBarrier;", "Lorg/jetbrains/kotlin/fir/declarations/FirInProcessOfResolvingToPhaseState;", "resolvingTo", "Lorg/jetbrains/kotlin/fir/declarations/FirResolvePhase;", "barrier", "Ljava/util/concurrent/CountDownLatch;", "(Lorg/jetbrains/kotlin/fir/declarations/FirResolvePhase;Ljava/util/concurrent/CountDownLatch;)V", "getBarrier", "()Ljava/util/concurrent/CountDownLatch;", "getResolvingTo", "()Lorg/jetbrains/kotlin/fir/declarations/FirResolvePhase;", "toString", "", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirInProcessOfResolvingToPhaseStateWithBarrier extends FirInProcessOfResolvingToPhaseState {
    private final CountDownLatch barrier;
    private final FirResolvePhase resolvingTo;

    @Override // org.jetbrains.kotlin.fir.declarations.FirInProcessOfResolvingToPhaseState
    public FirResolvePhase getResolvingTo() {
        return this.resolvingTo;
    }

    public final CountDownLatch getBarrier() {
        return this.barrier;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FirInProcessOfResolvingToPhaseStateWithBarrier(FirResolvePhase resolvingTo, CountDownLatch barrier) {
        super(null);
        Intrinsics.checkNotNullParameter(resolvingTo, "resolvingTo");
        Intrinsics.checkNotNullParameter(barrier, "barrier");
        this.resolvingTo = resolvingTo;
        this.barrier = barrier;
        if (getResolvingTo() == FirResolvePhase.RAW_FIR) {
            throw new IllegalArgumentException(("Cannot resolve to " + FirResolvePhase.RAW_FIR + " as it's a first phase").toString());
        }
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirResolveState
    public String toString() {
        return "ResolvingToWithBarrier(" + getResolvingTo() + ')';
    }
}
