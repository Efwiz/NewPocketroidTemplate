package org.jetbrains.kotlin.fir.resolve.calls;

import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;

/* JADX INFO: compiled from: ResolutionStages.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÀ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J.\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0096@¢\u0006\u0002\u0010\r¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/calls/CheckIncompatibleTypeVariableUpperBounds;", "Lorg/jetbrains/kotlin/fir/resolve/calls/ResolutionStage;", "()V", "check", "", "candidate", "Lorg/jetbrains/kotlin/fir/resolve/calls/Candidate;", "callInfo", "Lorg/jetbrains/kotlin/fir/resolve/calls/CallInfo;", "sink", "Lorg/jetbrains/kotlin/fir/resolve/calls/CheckerSink;", "context", "Lorg/jetbrains/kotlin/fir/resolve/calls/ResolutionContext;", "(Lorg/jetbrains/kotlin/fir/resolve/calls/Candidate;Lorg/jetbrains/kotlin/fir/resolve/calls/CallInfo;Lorg/jetbrains/kotlin/fir/resolve/calls/CheckerSink;Lorg/jetbrains/kotlin/fir/resolve/calls/ResolutionContext;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CheckIncompatibleTypeVariableUpperBounds extends ResolutionStage {
    public static final CheckIncompatibleTypeVariableUpperBounds INSTANCE = new CheckIncompatibleTypeVariableUpperBounds();

    /* JADX INFO: renamed from: org.jetbrains.kotlin.fir.resolve.calls.CheckIncompatibleTypeVariableUpperBounds$check$1, reason: invalid class name */
    /* JADX INFO: compiled from: ResolutionStages.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    @DebugMetadata(c = "org.jetbrains.kotlin.fir.resolve.calls.CheckIncompatibleTypeVariableUpperBounds", f = "ResolutionStages.kt", i = {0, 0, 0, 0}, l = {756}, m = "check", n = {"candidate", "sink", "context", "$this$check_u24lambda_u242"}, s = {"L$0", "L$1", "L$2", "L$3"})
    static final class AnonymousClass1 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        Object L$4;
        int label;
        /* synthetic */ Object result;

        AnonymousClass1(Continuation<? super AnonymousClass1> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return CheckIncompatibleTypeVariableUpperBounds.this.check(null, null, null, null, this);
        }
    }

    private CheckIncompatibleTypeVariableUpperBounds() {
    }

    /* JADX WARN: Code duplicated, block: B:17:0x0077  */
    /* JADX WARN: Code duplicated, block: B:21:0x0095  */
    /* JADX WARN: Code duplicated, block: B:27:0x00a9  */
    /* JADX WARN: Code duplicated, block: B:35:0x00d2  */
    /* JADX WARN: Code duplicated, block: B:38:0x00dc  */
    /* JADX WARN: Code duplicated, block: B:41:0x00e6  */
    /* JADX WARN: Code duplicated, block: B:43:0x00f4  */
    /* JADX WARN: Code duplicated, block: B:52:0x015e A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:53:0x015f  */
    /* JADX WARN: Code duplicated, block: B:59:0x008b A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:60:0x00c8 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:62:0x014b A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:64:0x00ba A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:67:0x0071 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:69:0x0071 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:72:0x0107 A[ADDED_TO_REGION, REMOVE, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:74:? A[LOOP:2: B:25:0x00a3->B:74:?, LOOP_END, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:7:0x0018  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:53:0x015f -> B:54:0x016b). Please report as a decompilation issue!!! */
    /*  JADX ERROR: JadxOverflowException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxOverflowException: Regions stack size limit reached
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
        	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
        	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
        */
    @Override // org.jetbrains.kotlin.fir.resolve.calls.ResolutionStage
    public java.lang.Object check(org.jetbrains.kotlin.fir.resolve.calls.Candidate r21, org.jetbrains.kotlin.fir.resolve.calls.CallInfo r22, org.jetbrains.kotlin.fir.resolve.calls.CheckerSink r23, org.jetbrains.kotlin.fir.resolve.calls.ResolutionContext r24, kotlin.coroutines.Continuation<? super kotlin.Unit> r25) {
        /*
            Method dump skipped, instruction units count: 383
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.jetbrains.kotlin.fir.resolve.calls.CheckIncompatibleTypeVariableUpperBounds.check(org.jetbrains.kotlin.fir.resolve.calls.Candidate, org.jetbrains.kotlin.fir.resolve.calls.CallInfo, org.jetbrains.kotlin.fir.resolve.calls.CheckerSink, org.jetbrains.kotlin.fir.resolve.calls.ResolutionContext, kotlin.coroutines.Continuation):java.lang.Object");
    }
}
