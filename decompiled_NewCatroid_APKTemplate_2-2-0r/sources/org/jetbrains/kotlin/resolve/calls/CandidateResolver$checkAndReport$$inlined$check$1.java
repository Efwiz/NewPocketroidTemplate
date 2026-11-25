package org.jetbrains.kotlin.resolve.calls;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.kotlin.name.SpecialNames;
import org.jetbrains.kotlin.resolve.calls.context.CallCandidateResolutionContext;
import org.jetbrains.kotlin.resolve.calls.results.ResolutionStatus;

/* JADX INFO: compiled from: CandidateResolver.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u0001\"\b\b\u0000\u0010\u0002*\u00020\u0003H\n¢\u0006\u0002\b\u0004¨\u0006\u0005"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "D", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "invoke", "org/jetbrains/kotlin/resolve/calls/CandidateResolver$check$1"}, k = 3, mv = {1, 9, 0}, xi = 48)
public final class CandidateResolver$checkAndReport$$inlined$check$1 extends Lambda implements Function0<Unit> {
    final /* synthetic */ Function1 $checker$inlined;
    final /* synthetic */ CallCandidateResolutionContext $this_check;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CandidateResolver$checkAndReport$$inlined$check$1(CallCandidateResolutionContext callCandidateResolutionContext, Function1 function1) {
        super(0);
        this.$this_check = callCandidateResolutionContext;
        this.$checker$inlined = function1;
    }

    @Override // kotlin.jvm.functions.Function0
    public /* bridge */ /* synthetic */ Unit invoke() {
        invoke2();
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
    public final void invoke2() {
        CallCandidateResolutionContext callCandidateResolutionContext = this.$this_check;
        callCandidateResolutionContext.candidateCall.addStatus((ResolutionStatus) this.$checker$inlined.invoke(callCandidateResolutionContext));
    }
}
