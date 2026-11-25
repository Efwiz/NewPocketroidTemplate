package org.jetbrains.kotlin.fir.resolve.calls;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;

/* JADX INFO: compiled from: CheckerSink.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0014\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u001a\u001a\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004H\u0086H¢\u0006\u0002\u0010\u0005\u001a\u0012\u0010\u0006\u001a\u00020\u0001*\u00020\u0002H\u0086H¢\u0006\u0002\u0010\u0007¨\u0006\b"}, d2 = {"yieldDiagnostic", "", "Lorg/jetbrains/kotlin/fir/resolve/calls/CheckerSink;", "diagnostic", "Lorg/jetbrains/kotlin/fir/resolve/calls/ResolutionDiagnostic;", "(Lorg/jetbrains/kotlin/fir/resolve/calls/CheckerSink;Lorg/jetbrains/kotlin/fir/resolve/calls/ResolutionDiagnostic;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "yieldIfNeed", "(Lorg/jetbrains/kotlin/fir/resolve/calls/CheckerSink;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "resolve"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class CheckerSinkKt {
    public static final Object yieldIfNeed(CheckerSink checkerSink, Continuation<? super Unit> continuation) {
        if (checkerSink.getNeedYielding()) {
            Object objYield = checkerSink.yield(continuation);
            return objYield == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objYield : Unit.INSTANCE;
        }
        return Unit.INSTANCE;
    }

    public static final Object yieldDiagnostic(CheckerSink checkerSink, ResolutionDiagnostic resolutionDiagnostic, Continuation<? super Unit> continuation) {
        Object objYield;
        checkerSink.reportDiagnostic(resolutionDiagnostic);
        return (checkerSink.getNeedYielding() && (objYield = checkerSink.yield(continuation)) == IntrinsicsKt.getCOROUTINE_SUSPENDED()) ? objYield : Unit.INSTANCE;
    }
}
