package org.jetbrains.kotlin.fir.resolve.calls;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.jetbrains.kotlin.util.PrivateForInline;

/* JADX INFO: compiled from: CheckerSink.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B)\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0010\b\u0002\u0010\u0004\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\u0010\u0010\u0012\u001a\u00020\u00062\u0006\u0010\u0013\u001a\u00020\u0014H\u0016J\u000e\u0010\u0015\u001a\u00020\u0006H\u0097@¢\u0006\u0002\u0010\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\"\u0010\u0004\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR\u0014\u0010\u000e\u001a\u00020\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0010¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/calls/CheckerSinkImpl;", "Lorg/jetbrains/kotlin/fir/resolve/calls/CheckerSink;", "candidate", "Lorg/jetbrains/kotlin/fir/resolve/calls/Candidate;", "continuation", "Lkotlin/coroutines/Continuation;", "", "stopOnFirstError", "", "(Lorg/jetbrains/kotlin/fir/resolve/calls/Candidate;Lkotlin/coroutines/Continuation;Z)V", "getContinuation", "()Lkotlin/coroutines/Continuation;", "setContinuation", "(Lkotlin/coroutines/Continuation;)V", "needYielding", "getNeedYielding", "()Z", "getStopOnFirstError", "reportDiagnostic", "diagnostic", "Lorg/jetbrains/kotlin/fir/resolve/calls/ResolutionDiagnostic;", PsiKeyword.YIELD, "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CheckerSinkImpl extends CheckerSink {
    private final Candidate candidate;
    private Continuation<? super Unit> continuation;
    private final boolean stopOnFirstError;

    public /* synthetic */ CheckerSinkImpl(Candidate candidate, Continuation continuation, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(candidate, (i & 2) != 0 ? null : continuation, (i & 4) != 0 ? true : z);
    }

    public final Continuation<Unit> getContinuation() {
        return this.continuation;
    }

    public final void setContinuation(Continuation<? super Unit> continuation) {
        this.continuation = continuation;
    }

    public final boolean getStopOnFirstError() {
        return this.stopOnFirstError;
    }

    public CheckerSinkImpl(Candidate candidate, Continuation<? super Unit> continuation, boolean z) {
        Intrinsics.checkNotNullParameter(candidate, "candidate");
        this.candidate = candidate;
        this.continuation = continuation;
        this.stopOnFirstError = z;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.calls.CheckerSink
    public void reportDiagnostic(ResolutionDiagnostic diagnostic) {
        Intrinsics.checkNotNullParameter(diagnostic, "diagnostic");
        this.candidate.addDiagnostic(diagnostic);
    }

    @Override // org.jetbrains.kotlin.fir.resolve.calls.CheckerSink
    @PrivateForInline
    public Object yield(Continuation<? super Unit> continuation) {
        this.continuation = continuation;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (coroutine_suspended == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            DebugProbesKt.probeCoroutineSuspended(continuation);
        }
        return coroutine_suspended == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? coroutine_suspended : Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.calls.CheckerSink
    public boolean getNeedYielding() {
        return this.stopOnFirstError && !this.candidate.isSuccessful();
    }
}
