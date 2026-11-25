package org.jetbrains.kotlin.ir.backend.js.lower.coroutines;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: StateMachineBuilder.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005J\t\u0010\t\u001a\u00020\u0003HÆ\u0003J\t\u0010\n\u001a\u00020\u0003HÆ\u0003J\u001d\u0010\u000b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u000f\u001a\u00020\u0010HÖ\u0001J\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/coroutines/LoopBounds;", "", "headState", "Lorg/jetbrains/kotlin/ir/backend/js/lower/coroutines/SuspendState;", "exitState", "(Lorg/jetbrains/kotlin/ir/backend/js/lower/coroutines/SuspendState;Lorg/jetbrains/kotlin/ir/backend/js/lower/coroutines/SuspendState;)V", "getExitState", "()Lorg/jetbrains/kotlin/ir/backend/js/lower/coroutines/SuspendState;", "getHeadState", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class LoopBounds {
    private final SuspendState exitState;
    private final SuspendState headState;

    public static /* synthetic */ LoopBounds copy$default(LoopBounds loopBounds, SuspendState suspendState, SuspendState suspendState2, int i, Object obj) {
        if ((i & 1) != 0) {
            suspendState = loopBounds.headState;
        }
        if ((i & 2) != 0) {
            suspendState2 = loopBounds.exitState;
        }
        return loopBounds.copy(suspendState, suspendState2);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final SuspendState getHeadState() {
        return this.headState;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final SuspendState getExitState() {
        return this.exitState;
    }

    public final LoopBounds copy(SuspendState headState, SuspendState exitState) {
        Intrinsics.checkNotNullParameter(headState, "headState");
        Intrinsics.checkNotNullParameter(exitState, "exitState");
        return new LoopBounds(headState, exitState);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof LoopBounds)) {
            return false;
        }
        LoopBounds loopBounds = (LoopBounds) other;
        return Intrinsics.areEqual(this.headState, loopBounds.headState) && Intrinsics.areEqual(this.exitState, loopBounds.exitState);
    }

    public int hashCode() {
        return (this.headState.hashCode() * 31) + this.exitState.hashCode();
    }

    public String toString() {
        return "LoopBounds(headState=" + this.headState + ", exitState=" + this.exitState + ')';
    }

    public LoopBounds(SuspendState headState, SuspendState exitState) {
        Intrinsics.checkNotNullParameter(headState, "headState");
        Intrinsics.checkNotNullParameter(exitState, "exitState");
        this.headState = headState;
        this.exitState = exitState;
    }

    public final SuspendState getExitState() {
        return this.exitState;
    }

    public final SuspendState getHeadState() {
        return this.headState;
    }
}
