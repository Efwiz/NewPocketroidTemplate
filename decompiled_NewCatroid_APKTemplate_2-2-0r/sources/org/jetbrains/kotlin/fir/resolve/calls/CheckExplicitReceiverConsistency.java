package org.jetbrains.kotlin.fir.resolve.calls;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirResolvedQualifier;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.resolve.calls.tasks.ExplicitReceiverKind;

/* JADX INFO: compiled from: ResolutionStages.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÀ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J.\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0096@¢\u0006\u0002\u0010\r¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/calls/CheckExplicitReceiverConsistency;", "Lorg/jetbrains/kotlin/fir/resolve/calls/ResolutionStage;", "()V", "check", "", "candidate", "Lorg/jetbrains/kotlin/fir/resolve/calls/Candidate;", "callInfo", "Lorg/jetbrains/kotlin/fir/resolve/calls/CallInfo;", "sink", "Lorg/jetbrains/kotlin/fir/resolve/calls/CheckerSink;", "context", "Lorg/jetbrains/kotlin/fir/resolve/calls/ResolutionContext;", "(Lorg/jetbrains/kotlin/fir/resolve/calls/Candidate;Lorg/jetbrains/kotlin/fir/resolve/calls/CallInfo;Lorg/jetbrains/kotlin/fir/resolve/calls/CheckerSink;Lorg/jetbrains/kotlin/fir/resolve/calls/ResolutionContext;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CheckExplicitReceiverConsistency extends ResolutionStage {
    public static final CheckExplicitReceiverConsistency INSTANCE = new CheckExplicitReceiverConsistency();

    /* JADX INFO: compiled from: ResolutionStages.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[ExplicitReceiverKind.values().length];
            try {
                iArr[ExplicitReceiverKind.NO_EXPLICIT_RECEIVER.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[ExplicitReceiverKind.EXTENSION_RECEIVER.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[ExplicitReceiverKind.DISPATCH_RECEIVER.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[ExplicitReceiverKind.BOTH_RECEIVERS.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    private CheckExplicitReceiverConsistency() {
    }

    @Override // org.jetbrains.kotlin.fir.resolve.calls.ResolutionStage
    public Object check(Candidate candidate, CallInfo callInfo, CheckerSink checkerSink, ResolutionContext resolutionContext, Continuation<? super Unit> continuation) {
        Object objYield;
        Object objYield2;
        Object objYield3;
        ExplicitReceiverKind explicitReceiverKind = candidate.getExplicitReceiverKind();
        FirExpression explicitReceiver = callInfo.getExplicitReceiver();
        int i = WhenMappings.$EnumSwitchMapping$0[explicitReceiverKind.ordinal()];
        if (i != 1) {
            if (i == 2 || i == 3) {
                if (explicitReceiver == null) {
                    checkerSink.reportDiagnostic(new InapplicableWrongReceiver(null, null, 3, null));
                    return (checkerSink.getNeedYielding() && (objYield2 = checkerSink.yield(continuation)) == IntrinsicsKt.getCOROUTINE_SUSPENDED()) ? objYield2 : Unit.INSTANCE;
                }
            } else if (i == 4 && explicitReceiver == null) {
                checkerSink.reportDiagnostic(new InapplicableWrongReceiver(null, null, 3, null));
                return (checkerSink.getNeedYielding() && (objYield3 = checkerSink.yield(continuation)) == IntrinsicsKt.getCOROUTINE_SUSPENDED()) ? objYield3 : Unit.INSTANCE;
            }
        } else if (explicitReceiver != null && !(explicitReceiver instanceof FirResolvedQualifier) && !ResolutionStagesKt.isSuperReferenceExpression(explicitReceiver)) {
            FirTypeRef typeRef = explicitReceiver.getTypeRef();
            FirResolvedTypeRef firResolvedTypeRef = typeRef instanceof FirResolvedTypeRef ? (FirResolvedTypeRef) typeRef : null;
            ConeKotlinType type = firResolvedTypeRef != null ? firResolvedTypeRef.getType() : null;
            if (!(type instanceof ConeKotlinType)) {
                type = null;
            }
            checkerSink.reportDiagnostic(new InapplicableWrongReceiver(null, type, 1, null));
            return (checkerSink.getNeedYielding() && (objYield = checkerSink.yield(continuation)) == IntrinsicsKt.getCOROUTINE_SUSPENDED()) ? objYield : Unit.INSTANCE;
        }
        return Unit.INSTANCE;
    }
}
