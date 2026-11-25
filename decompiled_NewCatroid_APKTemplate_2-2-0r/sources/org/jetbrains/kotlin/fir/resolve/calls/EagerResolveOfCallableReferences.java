package org.jetbrains.kotlin.fir.resolve.calls;

import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import org.jetbrains.kotlin.resolve.calls.tower.CandidateApplicability;

/* JADX INFO: compiled from: ResolutionStages.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÀ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J.\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0096@¢\u0006\u0002\u0010\r¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/calls/EagerResolveOfCallableReferences;", "Lorg/jetbrains/kotlin/fir/resolve/calls/CheckerStage;", "()V", "check", "", "candidate", "Lorg/jetbrains/kotlin/fir/resolve/calls/Candidate;", "callInfo", "Lorg/jetbrains/kotlin/fir/resolve/calls/CallInfo;", "sink", "Lorg/jetbrains/kotlin/fir/resolve/calls/CheckerSink;", "context", "Lorg/jetbrains/kotlin/fir/resolve/calls/ResolutionContext;", "(Lorg/jetbrains/kotlin/fir/resolve/calls/Candidate;Lorg/jetbrains/kotlin/fir/resolve/calls/CallInfo;Lorg/jetbrains/kotlin/fir/resolve/calls/CheckerSink;Lorg/jetbrains/kotlin/fir/resolve/calls/ResolutionContext;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class EagerResolveOfCallableReferences extends CheckerStage {
    public static final EagerResolveOfCallableReferences INSTANCE = new EagerResolveOfCallableReferences();

    /* JADX INFO: compiled from: ResolutionStages.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[CandidateApplicability.values().length];
            try {
                iArr[CandidateApplicability.RESOLVED_NEED_PRESERVE_COMPATIBILITY.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.fir.resolve.calls.EagerResolveOfCallableReferences$check$1, reason: invalid class name */
    /* JADX INFO: compiled from: ResolutionStages.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    @DebugMetadata(c = "org.jetbrains.kotlin.fir.resolve.calls.EagerResolveOfCallableReferences", f = "ResolutionStages.kt", i = {0, 0, 0}, l = {750}, m = "check", n = {"candidate", "sink", "context"}, s = {"L$0", "L$1", "L$2"})
    static final class AnonymousClass1 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        int label;
        /* synthetic */ Object result;

        AnonymousClass1(Continuation<? super AnonymousClass1> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return EagerResolveOfCallableReferences.this.check(null, null, null, null, this);
        }
    }

    private EagerResolveOfCallableReferences() {
    }

    /* JADX WARN: Code duplicated, block: B:21:0x0067  */
    /* JADX WARN: Code duplicated, block: B:27:0x00a2  */
    /* JADX WARN: Code duplicated, block: B:29:0x00a6  */
    /* JADX WARN: Code duplicated, block: B:38:0x00d8 A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:39:0x00d9  */
    /* JADX WARN: Code duplicated, block: B:49:0x0071 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:50:0x00c8 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:52:0x00e0 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:54:0x0098 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:7:0x0014  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:39:0x00d9 -> B:40:0x00dc). Please report as a decompilation issue!!! */
    /*  JADX ERROR: JadxOverflowException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxOverflowException: Regions stack size limit reached
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
        	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
        	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
        */
    @Override // org.jetbrains.kotlin.fir.resolve.calls.ResolutionStage
    public java.lang.Object check(org.jetbrains.kotlin.fir.resolve.calls.Candidate r8, org.jetbrains.kotlin.fir.resolve.calls.CallInfo r9, org.jetbrains.kotlin.fir.resolve.calls.CheckerSink r10, org.jetbrains.kotlin.fir.resolve.calls.ResolutionContext r11, kotlin.coroutines.Continuation<? super kotlin.Unit> r12) {
        /*
            Method dump skipped, instruction units count: 256
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.jetbrains.kotlin.fir.resolve.calls.EagerResolveOfCallableReferences.check(org.jetbrains.kotlin.fir.resolve.calls.Candidate, org.jetbrains.kotlin.fir.resolve.calls.CallInfo, org.jetbrains.kotlin.fir.resolve.calls.CheckerSink, org.jetbrains.kotlin.fir.resolve.calls.ResolutionContext, kotlin.coroutines.Continuation):java.lang.Object");
    }
}
