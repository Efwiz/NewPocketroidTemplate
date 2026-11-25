package org.jetbrains.kotlin.fir.resolve.calls.tower;

import java.util.PriorityQueue;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineConstantsKt;
import org.jetbrains.kotlin.fir.resolve.calls.CandidateCollector;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: TowerResolveManager.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0001\n\u0002\b\u0004\u0018\u00002\u00020\u0001:\u0001\u001dB\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004B\u001b\b\u0002\u0012\u0012\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u0006¢\u0006\u0002\u0010\tJ3\u0010\r\u001a\u00020\u000e2\b\b\u0002\u0010\u000f\u001a\u00020\u00072\u001c\u0010\u0010\u001a\u0018\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\u0011\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0006¢\u0006\u0002\u0010\u0012J\u0016\u0010\u0013\u001a\u00020\u000e2\u0006\u0010\u0014\u001a\u00020\u0007H\u0086@¢\u0006\u0002\u0010\u0015J\u0006\u0010\u0016\u001a\u00020\u000eJ\u0010\u0010\u0017\u001a\u00020\u000e2\u0006\u0010\u0010\u001a\u00020\fH\u0002J\u0006\u0010\u0018\u001a\u00020\u000eJ\u000e\u0010\u0019\u001a\u00020\u001aH\u0082@¢\u0006\u0002\u0010\u001bJ\u0016\u0010\u001c\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0007H\u0082@¢\u0006\u0002\u0010\u0015R\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001e"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/calls/tower/TowerResolveManager;", "", "collector", "Lorg/jetbrains/kotlin/fir/resolve/calls/CandidateCollector;", "(Lorg/jetbrains/kotlin/fir/resolve/calls/CandidateCollector;)V", "shouldStopAtTheLevel", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/fir/resolve/calls/tower/TowerGroup;", "", "(Lkotlin/jvm/functions/Function1;)V", "queue", "Ljava/util/PriorityQueue;", "Lorg/jetbrains/kotlin/fir/resolve/calls/tower/TowerResolveManager$SuspendedResolverTask;", "enqueueResolverTask", "", "group", "task", "Lkotlin/coroutines/Continuation;", "(Lorg/jetbrains/kotlin/fir/resolve/calls/tower/TowerGroup;Lkotlin/jvm/functions/Function1;)V", "requestGroup", "requested", "(Lorg/jetbrains/kotlin/fir/resolve/calls/tower/TowerGroup;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "reset", "resumeTask", "runTasks", "stopResolverTask", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "suspendResolverTask", "SuspendedResolverTask", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class TowerResolveManager {
    private final PriorityQueue<SuspendedResolverTask> queue;
    private final Function1<TowerGroup, Boolean> shouldStopAtTheLevel;

    /* JADX INFO: renamed from: org.jetbrains.kotlin.fir.resolve.calls.tower.TowerResolveManager$requestGroup$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: TowerResolveManager.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    @DebugMetadata(c = "org.jetbrains.kotlin.fir.resolve.calls.tower.TowerResolveManager", f = "TowerResolveManager.kt", i = {}, l = {36, 43}, m = "requestGroup", n = {}, s = {})
    static final class C45481 extends ContinuationImpl {
        int label;
        /* synthetic */ Object result;

        C45481(Continuation<? super C45481> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return TowerResolveManager.this.requestGroup(null, this);
        }
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.fir.resolve.calls.tower.TowerResolveManager$stopResolverTask$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: TowerResolveManager.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    @DebugMetadata(c = "org.jetbrains.kotlin.fir.resolve.calls.tower.TowerResolveManager", f = "TowerResolveManager.kt", i = {}, l = {47}, m = "stopResolverTask", n = {}, s = {})
    static final class C45491 extends ContinuationImpl {
        int label;
        /* synthetic */ Object result;

        C45491(Continuation<? super C45491> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return TowerResolveManager.this.stopResolverTask(this);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private TowerResolveManager(Function1<? super TowerGroup, Boolean> function1) {
        this.shouldStopAtTheLevel = function1;
        this.queue = new PriorityQueue<>();
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.fir.resolve.calls.tower.TowerResolveManager$1, reason: invalid class name */
    /* JADX INFO: compiled from: TowerResolveManager.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    /* synthetic */ class AnonymousClass1 extends FunctionReference implements Function1<TowerGroup, Boolean> {
        AnonymousClass1(Object obj) {
            super(1, obj);
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final String getSignature() {
            return "shouldStopAtTheGroup(Lorg/jetbrains/kotlin/fir/resolve/calls/tower/TowerGroup;)Z";
        }

        @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
        public final String getName() {
            return "shouldStopAtTheGroup";
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final KDeclarationContainer getOwner() {
            return Reflection.getOrCreateKotlinClass(CandidateCollector.class);
        }

        @Override // kotlin.jvm.functions.Function1
        public final Boolean invoke(TowerGroup p0) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            return Boolean.valueOf(((CandidateCollector) this.receiver).shouldStopAtTheGroup(p0));
        }
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public TowerResolveManager(CandidateCollector collector) {
        this(new AnonymousClass1(collector));
        Intrinsics.checkNotNullParameter(collector, "collector");
    }

    public final void reset() {
        this.queue.clear();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Object suspendResolverTask(TowerGroup towerGroup, Continuation<? super Unit> continuation) {
        this.queue.add(new SuspendedResolverTask(continuation, towerGroup));
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (coroutine_suspended == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            DebugProbesKt.probeCoroutineSuspended(continuation);
        }
        return coroutine_suspended == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? coroutine_suspended : Unit.INSTANCE;
    }

    /* JADX WARN: Code duplicated, block: B:7:0x0014  */
    public final Object requestGroup(TowerGroup towerGroup, Continuation<? super Unit> continuation) throws Throwable {
        C45481 c45481;
        if (continuation instanceof C45481) {
            c45481 = (C45481) continuation;
            if ((c45481.label & Integer.MIN_VALUE) != 0) {
                c45481.label -= Integer.MIN_VALUE;
            } else {
                c45481 = new C45481(continuation);
            }
        } else {
            c45481 = new C45481(continuation);
        }
        Object obj = c45481.result;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = c45481.label;
        if (i != 0) {
            if (i == 1) {
                ResultKt.throwOnFailure(obj);
                throw null;
            }
            if (i != 2) {
                throw new IllegalStateException(CoroutineConstantsKt.ILLEGAL_STATE_ERROR_MESSAGE);
            }
            ResultKt.throwOnFailure(obj);
            return Unit.INSTANCE;
        }
        ResultKt.throwOnFailure(obj);
        if (this.shouldStopAtTheLevel.invoke(towerGroup).booleanValue()) {
            c45481.label = 1;
            if (stopResolverTask(c45481) == coroutine_suspended) {
                return coroutine_suspended;
            }
            throw null;
        }
        SuspendedResolverTask suspendedResolverTaskPeek = this.queue.peek();
        if (suspendedResolverTaskPeek == null || suspendedResolverTaskPeek.getGroup().compareTo(towerGroup) > 0) {
            return Unit.INSTANCE;
        }
        c45481.label = 2;
        if (suspendResolverTask(towerGroup, c45481) == coroutine_suspended) {
            return coroutine_suspended;
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:7:0x0014  */
    public final Object stopResolverTask(Continuation<?> continuation) throws Throwable {
        C45491 c45491;
        if (continuation instanceof C45491) {
            c45491 = (C45491) continuation;
            if ((c45491.label & Integer.MIN_VALUE) != 0) {
                c45491.label -= Integer.MIN_VALUE;
            } else {
                c45491 = new C45491(continuation);
            }
        } else {
            c45491 = new C45491(continuation);
        }
        Object obj = c45491.result;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = c45491.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            c45491.label = 1;
            C45491 c45492 = c45491;
            Object coroutine_suspended2 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (coroutine_suspended2 == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
                DebugProbesKt.probeCoroutineSuspended(c45492);
            }
            if (coroutine_suspended2 == coroutine_suspended) {
                return coroutine_suspended;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException(CoroutineConstantsKt.ILLEGAL_STATE_ERROR_MESSAGE);
            }
            ResultKt.throwOnFailure(obj);
        }
        throw null;
    }

    /* JADX INFO: compiled from: TowerResolveManager.kt */
    @Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u000f\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0082\b\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u001b\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u0011\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u0000H\u0096\u0002J\u000f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003HÆ\u0003J\t\u0010\u0010\u001a\u00020\u0006HÆ\u0003J#\u0010\u0011\u001a\u00020\u00002\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u0006HÆ\u0001J\u0013\u0010\u0012\u001a\u00020\u00132\b\u0010\u000e\u001a\u0004\u0018\u00010\u0014HÖ\u0003J\t\u0010\u0015\u001a\u00020\rHÖ\u0001J\t\u0010\u0016\u001a\u00020\u0017HÖ\u0001R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\u0018"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/calls/tower/TowerResolveManager$SuspendedResolverTask;", "", "continuation", "Lkotlin/coroutines/Continuation;", "", "group", "Lorg/jetbrains/kotlin/fir/resolve/calls/tower/TowerGroup;", "(Lkotlin/coroutines/Continuation;Lorg/jetbrains/kotlin/fir/resolve/calls/tower/TowerGroup;)V", "getContinuation", "()Lkotlin/coroutines/Continuation;", "getGroup", "()Lorg/jetbrains/kotlin/fir/resolve/calls/tower/TowerGroup;", "compareTo", "", "other", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "", "hashCode", "toString", "", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final /* data */ class SuspendedResolverTask implements Comparable<SuspendedResolverTask> {
        private final Continuation<Unit> continuation;
        private final TowerGroup group;

        /* JADX WARN: Multi-variable type inference failed */
        public static /* synthetic */ SuspendedResolverTask copy$default(SuspendedResolverTask suspendedResolverTask, Continuation continuation, TowerGroup towerGroup, int i, Object obj) {
            if ((i & 1) != 0) {
                continuation = suspendedResolverTask.continuation;
            }
            if ((i & 2) != 0) {
                towerGroup = suspendedResolverTask.group;
            }
            return suspendedResolverTask.copy(continuation, towerGroup);
        }

        public final Continuation<Unit> component1() {
            return this.continuation;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final TowerGroup getGroup() {
            return this.group;
        }

        public final SuspendedResolverTask copy(Continuation<? super Unit> continuation, TowerGroup group) {
            Intrinsics.checkNotNullParameter(continuation, "continuation");
            Intrinsics.checkNotNullParameter(group, "group");
            return new SuspendedResolverTask(continuation, group);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof SuspendedResolverTask)) {
                return false;
            }
            SuspendedResolverTask suspendedResolverTask = (SuspendedResolverTask) other;
            return Intrinsics.areEqual(this.continuation, suspendedResolverTask.continuation) && Intrinsics.areEqual(this.group, suspendedResolverTask.group);
        }

        public int hashCode() {
            return (this.continuation.hashCode() * 31) + this.group.hashCode();
        }

        public String toString() {
            return "SuspendedResolverTask(continuation=" + this.continuation + ", group=" + this.group + ')';
        }

        /* JADX WARN: Multi-variable type inference failed */
        public SuspendedResolverTask(Continuation<? super Unit> continuation, TowerGroup group) {
            Intrinsics.checkNotNullParameter(continuation, "continuation");
            Intrinsics.checkNotNullParameter(group, "group");
            this.continuation = continuation;
            this.group = group;
        }

        public final Continuation<Unit> getContinuation() {
            return this.continuation;
        }

        public final TowerGroup getGroup() {
            return this.group;
        }

        @Override // java.lang.Comparable
        public int compareTo(SuspendedResolverTask other) {
            Intrinsics.checkNotNullParameter(other, "other");
            return this.group.compareTo(other.group);
        }
    }

    public static /* synthetic */ void enqueueResolverTask$default(TowerResolveManager towerResolveManager, TowerGroup towerGroup, Function1 function1, int i, Object obj) {
        if ((i & 1) != 0) {
            towerGroup = TowerGroup.INSTANCE.getStart();
        }
        towerResolveManager.enqueueResolverTask(towerGroup, function1);
    }

    public final void enqueueResolverTask(TowerGroup group, Function1<? super Continuation<? super Unit>, ? extends Object> task) {
        Intrinsics.checkNotNullParameter(group, "group");
        Intrinsics.checkNotNullParameter(task, "task");
        this.queue.add(new SuspendedResolverTask(IntrinsicsKt.createCoroutineUnintercepted(task, new Continuation<Unit>() { // from class: org.jetbrains.kotlin.fir.resolve.calls.tower.TowerResolveManager$enqueueResolverTask$continuation$1
            @Override // kotlin.coroutines.Continuation
            /* JADX INFO: renamed from: getContext */
            public CoroutineContext get$context() {
                return EmptyCoroutineContext.INSTANCE;
            }

            @Override // kotlin.coroutines.Continuation
            public void resumeWith(Object result) throws Throwable {
                ResultKt.throwOnFailure(result);
            }
        }), group));
    }

    private final void resumeTask(SuspendedResolverTask task) {
        if (this.shouldStopAtTheLevel.invoke(task.getGroup()).booleanValue()) {
            return;
        }
        Continuation<Unit> continuation = task.getContinuation();
        Result.Companion companion = Result.INSTANCE;
        continuation.resumeWith(Result.m2898constructorimpl(Unit.INSTANCE));
    }

    public final void runTasks() {
        while (!this.queue.isEmpty()) {
            SuspendedResolverTask suspendedResolverTaskPoll = this.queue.poll();
            Intrinsics.checkNotNull(suspendedResolverTaskPoll);
            resumeTask(suspendedResolverTaskPoll);
        }
    }
}
