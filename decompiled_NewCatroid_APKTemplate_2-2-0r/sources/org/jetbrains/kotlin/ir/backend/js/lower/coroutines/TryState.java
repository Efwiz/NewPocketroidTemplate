package org.jetbrains.kotlin.ir.backend.js.lower.coroutines;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: StateMachineBuilder.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005J\t\u0010\t\u001a\u00020\u0003HÆ\u0003J\t\u0010\n\u001a\u00020\u0003HÆ\u0003J\u001d\u0010\u000b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u000f\u001a\u00020\u0010HÖ\u0001J\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/coroutines/TryState;", "", "tryState", "Lorg/jetbrains/kotlin/ir/backend/js/lower/coroutines/SuspendState;", "catchState", "(Lorg/jetbrains/kotlin/ir/backend/js/lower/coroutines/SuspendState;Lorg/jetbrains/kotlin/ir/backend/js/lower/coroutines/SuspendState;)V", "getCatchState", "()Lorg/jetbrains/kotlin/ir/backend/js/lower/coroutines/SuspendState;", "getTryState", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class TryState {
    private final SuspendState catchState;
    private final SuspendState tryState;

    public static /* synthetic */ TryState copy$default(TryState tryState, SuspendState suspendState, SuspendState suspendState2, int i, Object obj) {
        if ((i & 1) != 0) {
            suspendState = tryState.tryState;
        }
        if ((i & 2) != 0) {
            suspendState2 = tryState.catchState;
        }
        return tryState.copy(suspendState, suspendState2);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final SuspendState getTryState() {
        return this.tryState;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final SuspendState getCatchState() {
        return this.catchState;
    }

    public final TryState copy(SuspendState tryState, SuspendState catchState) {
        Intrinsics.checkNotNullParameter(tryState, "tryState");
        Intrinsics.checkNotNullParameter(catchState, "catchState");
        return new TryState(tryState, catchState);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof TryState)) {
            return false;
        }
        TryState tryState = (TryState) other;
        return Intrinsics.areEqual(this.tryState, tryState.tryState) && Intrinsics.areEqual(this.catchState, tryState.catchState);
    }

    public int hashCode() {
        return (this.tryState.hashCode() * 31) + this.catchState.hashCode();
    }

    public String toString() {
        return "TryState(tryState=" + this.tryState + ", catchState=" + this.catchState + ')';
    }

    public TryState(SuspendState tryState, SuspendState catchState) {
        Intrinsics.checkNotNullParameter(tryState, "tryState");
        Intrinsics.checkNotNullParameter(catchState, "catchState");
        this.tryState = tryState;
        this.catchState = catchState;
    }

    public final SuspendState getCatchState() {
        return this.catchState;
    }

    public final SuspendState getTryState() {
        return this.tryState;
    }
}
