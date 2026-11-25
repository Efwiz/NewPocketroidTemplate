package org.jetbrains.kotlin.fir.resolve.calls;

import kotlin.Metadata;
import kotlin.Result;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.name.SpecialNames;
import org.jetbrains.kotlin.resolve.calls.tower.CandidateApplicability;

/* JADX INFO: compiled from: ResolutionStageRunner.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J \u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/calls/ResolutionStageRunner;", "", "()V", "processCandidate", "Lorg/jetbrains/kotlin/resolve/calls/tower/CandidateApplicability;", "candidate", "Lorg/jetbrains/kotlin/fir/resolve/calls/Candidate;", "context", "Lorg/jetbrains/kotlin/fir/resolve/calls/ResolutionContext;", "stopOnFirstError", "", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ResolutionStageRunner {
    public static /* synthetic */ CandidateApplicability processCandidate$default(ResolutionStageRunner resolutionStageRunner, Candidate candidate, ResolutionContext resolutionContext, boolean z, int i, Object obj) {
        if ((i & 4) != 0) {
            z = true;
        }
        return resolutionStageRunner.processCandidate(candidate, resolutionContext, z);
    }

    public final CandidateApplicability processCandidate(Candidate candidate, ResolutionContext context, boolean stopOnFirstError) {
        Intrinsics.checkNotNullParameter(candidate, "candidate");
        Intrinsics.checkNotNullParameter(context, "context");
        CheckerSinkImpl checkerSinkImpl = new CheckerSinkImpl(candidate, null, stopOnFirstError, 2, null);
        final Ref.BooleanRef booleanRef = new Ref.BooleanRef();
        checkerSinkImpl.setContinuation(IntrinsicsKt.createCoroutineUnintercepted(new AnonymousClass1(candidate, checkerSinkImpl, context, null), new Continuation<Unit>() { // from class: org.jetbrains.kotlin.fir.resolve.calls.ResolutionStageRunner.processCandidate.2
            @Override // kotlin.coroutines.Continuation
            /* JADX INFO: renamed from: getContext */
            public CoroutineContext get$context() {
                return EmptyCoroutineContext.INSTANCE;
            }

            @Override // kotlin.coroutines.Continuation
            public void resumeWith(Object result) throws Throwable {
                Throwable thM2901exceptionOrNullimpl = Result.m2901exceptionOrNullimpl(result);
                if (thM2901exceptionOrNullimpl == null) {
                    booleanRef.element = true;
                    return;
                }
                throw thM2901exceptionOrNullimpl;
            }
        }));
        while (!booleanRef.element) {
            Continuation<Unit> continuation = checkerSinkImpl.getContinuation();
            Intrinsics.checkNotNull(continuation);
            Result.Companion companion = Result.INSTANCE;
            continuation.resumeWith(Result.m2898constructorimpl(Unit.INSTANCE));
            if (!candidate.isSuccessful()) {
                break;
            }
        }
        return candidate.getCurrentApplicability();
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.fir.resolve.calls.ResolutionStageRunner$processCandidate$1, reason: invalid class name */
    /* JADX INFO: compiled from: ResolutionStageRunner.kt */
    @Metadata(d1 = {"\u0000\u0006\n\u0000\n\u0002\u0010\u0002\u0010\u0000\u001a\u00020\u0001H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, ""}, k = 3, mv = {1, 9, 0}, xi = 48)
    @DebugMetadata(c = "org.jetbrains.kotlin.fir.resolve.calls.ResolutionStageRunner$processCandidate$1", f = "ResolutionStageRunner.kt", i = {0}, l = {24}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {"index$iv"}, s = {"I$0"})
    static final class AnonymousClass1 extends SuspendLambda implements Function1<Continuation<? super Unit>, Object> {
        final /* synthetic */ Candidate $candidate;
        final /* synthetic */ ResolutionContext $context;
        final /* synthetic */ CheckerSinkImpl $sink;
        int I$0;
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(Candidate candidate, CheckerSinkImpl checkerSinkImpl, ResolutionContext resolutionContext, Continuation<? super AnonymousClass1> continuation) {
            super(1, continuation);
            this.$candidate = candidate;
            this.$sink = checkerSinkImpl;
            this.$context = resolutionContext;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Continuation<?> continuation) {
            return new AnonymousClass1(this.$candidate, this.$sink, this.$context, continuation);
        }

        @Override // kotlin.jvm.functions.Function1
        public final Object invoke(Continuation<? super Unit> continuation) {
            return ((AnonymousClass1) create(continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code duplicated, block: B:11:0x0052  */
        /* JADX WARN: Code duplicated, block: B:13:0x005a  */
        /* JADX WARN: Code duplicated, block: B:18:0x0089 A[RETURN] */
        /* JADX WARN: Code duplicated, block: B:19:0x008a  */
        /* JADX WARN: Code duplicated, block: B:21:0x0094 A[LOOP:0: B:9:0x004c->B:21:0x0094, LOOP_END] */
        /* JADX WARN: Code duplicated, block: B:25:0x0065 A[SYNTHETIC] */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:19:0x008a -> B:20:0x008e). Please report as a decompilation issue!!! */
        /*  JADX ERROR: JadxOverflowException in pass: RegionMakerVisitor
            jadx.core.utils.exceptions.JadxOverflowException: Regions count limit reached at block B:11:0x0052
            	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
            	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
            	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
            */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final java.lang.Object invokeSuspend(java.lang.Object r14) {
            /*
                r13 = this;
                java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                int r1 = r13.label
                r2 = 1
                if (r1 == 0) goto L2a
                if (r1 != r2) goto L22
                int r1 = r13.I$0
                java.lang.Object r3 = r13.L$3
                java.util.Iterator r3 = (java.util.Iterator) r3
                java.lang.Object r4 = r13.L$2
                org.jetbrains.kotlin.fir.resolve.calls.ResolutionContext r4 = (org.jetbrains.kotlin.fir.resolve.calls.ResolutionContext) r4
                java.lang.Object r5 = r13.L$1
                org.jetbrains.kotlin.fir.resolve.calls.CheckerSinkImpl r5 = (org.jetbrains.kotlin.fir.resolve.calls.CheckerSinkImpl) r5
                java.lang.Object r6 = r13.L$0
                org.jetbrains.kotlin.fir.resolve.calls.Candidate r6 = (org.jetbrains.kotlin.fir.resolve.calls.Candidate) r6
                kotlin.ResultKt.throwOnFailure(r14)
                goto L8e
            L22:
                java.lang.IllegalStateException r14 = new java.lang.IllegalStateException
                java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
                r14.<init>(r0)
                throw r14
            L2a:
                kotlin.ResultKt.throwOnFailure(r14)
                org.jetbrains.kotlin.fir.resolve.calls.Candidate r14 = r13.$candidate
                org.jetbrains.kotlin.fir.resolve.calls.CallInfo r14 = r14.getCallInfo()
                org.jetbrains.kotlin.fir.resolve.calls.CallKind r14 = r14.getCallKind()
                java.util.List r14 = r14.getResolutionSequence()
                java.lang.Iterable r14 = (java.lang.Iterable) r14
                org.jetbrains.kotlin.fir.resolve.calls.Candidate r1 = r13.$candidate
                org.jetbrains.kotlin.fir.resolve.calls.CheckerSinkImpl r3 = r13.$sink
                org.jetbrains.kotlin.fir.resolve.calls.ResolutionContext r4 = r13.$context
                java.util.Iterator r14 = r14.iterator()
                r5 = 0
                r10 = r1
                r1 = r3
                r3 = r14
                r14 = r4
            L4c:
                boolean r4 = r3.hasNext()
                if (r4 == 0) goto L96
                java.lang.Object r4 = r3.next()
                int r11 = r5 + 1
                if (r5 >= 0) goto L5d
                kotlin.collections.CollectionsKt.throwIndexOverflow()
            L5d:
                org.jetbrains.kotlin.fir.resolve.calls.ResolutionStage r4 = (org.jetbrains.kotlin.fir.resolve.calls.ResolutionStage) r4
                int r6 = r10.getPassedStages()
                if (r5 < r6) goto L94
                int r5 = r10.getPassedStages()
                int r5 = r5 + r2
                r10.setPassedStages(r5)
                org.jetbrains.kotlin.fir.resolve.calls.CallInfo r6 = r10.getCallInfo()
                r7 = r1
                org.jetbrains.kotlin.fir.resolve.calls.CheckerSink r7 = (org.jetbrains.kotlin.fir.resolve.calls.CheckerSink) r7
                r13.L$0 = r10
                r13.L$1 = r1
                r13.L$2 = r14
                r13.L$3 = r3
                r13.I$0 = r11
                r13.label = r2
                r5 = r10
                r8 = r14
                r9 = r13
                java.lang.Object r4 = r4.check(r5, r6, r7, r8, r9)
                if (r4 != r0) goto L8a
                return r0
            L8a:
                r4 = r14
                r5 = r1
                r6 = r10
                r1 = r11
            L8e:
                r14 = r4
                r10 = r6
                r12 = r5
                r5 = r1
                r1 = r12
                goto L4c
            L94:
                r5 = r11
                goto L4c
            L96:
                kotlin.Unit r14 = kotlin.Unit.INSTANCE
                return r14
            */
            throw new UnsupportedOperationException("Method not decompiled: org.jetbrains.kotlin.fir.resolve.calls.ResolutionStageRunner.AnonymousClass1.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }
}
