package org.jetbrains.kotlin.fir.resolve.calls.tower;

import com.gaurav.avnc.vnc.XKeySym;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineConstantsKt;
import org.jetbrains.kotlin.fir.resolve.BodyResolveComponents;
import org.jetbrains.kotlin.fir.resolve.calls.CallInfo;
import org.jetbrains.kotlin.fir.resolve.calls.CandidateCollector;
import org.jetbrains.kotlin.fir.resolve.calls.CandidateFactory;
import org.jetbrains.kotlin.fir.resolve.calls.ExpressionReceiverValue;
import org.jetbrains.kotlin.resolve.calls.tasks.ExplicitReceiverKind;

/* JADX INFO: compiled from: FirInvokeResolveTowerExtension.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u0001B5\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r¢\u0006\u0002\u0010\u000eJ.\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\t2\u0006\u0010\u0016\u001a\u00020\u0017H\u0082@¢\u0006\u0002\u0010\u0018J\u001e\u0010\u0019\u001a\u00020\u00102\u0006\u0010\u001a\u001a\u00020\u00142\u0006\u0010\u001b\u001a\u00020\u001cH\u0086@¢\u0006\u0002\u0010\u001dJ\u001e\u0010\u001e\u001a\u00020\u00102\u0006\u0010\u001a\u001a\u00020\u00142\u0006\u0010\u001b\u001a\u00020\u001cH\u0086@¢\u0006\u0002\u0010\u001dJ\u001e\u0010\u001f\u001a\u00020\u00102\u0006\u0010\u001a\u001a\u00020\u00142\u0006\u0010\u001b\u001a\u00020\u001cH\u0086@¢\u0006\u0002\u0010\u001dJ\u0014\u0010 \u001a\u00020\t*\u00020\t2\u0006\u0010!\u001a\u00020\"H\u0002R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006#"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/calls/tower/InvokeFunctionResolveTask;", "Lorg/jetbrains/kotlin/fir/resolve/calls/tower/FirBaseTowerResolveTask;", "components", "Lorg/jetbrains/kotlin/fir/resolve/BodyResolveComponents;", "manager", "Lorg/jetbrains/kotlin/fir/resolve/calls/tower/TowerResolveManager;", "towerDataElementsForName", "Lorg/jetbrains/kotlin/fir/resolve/calls/tower/TowerDataElementsForName;", "receiverGroup", "Lorg/jetbrains/kotlin/fir/resolve/calls/tower/TowerGroup;", "collector", "Lorg/jetbrains/kotlin/fir/resolve/calls/CandidateCollector;", "candidateFactory", "Lorg/jetbrains/kotlin/fir/resolve/calls/CandidateFactory;", "(Lorg/jetbrains/kotlin/fir/resolve/BodyResolveComponents;Lorg/jetbrains/kotlin/fir/resolve/calls/tower/TowerResolveManager;Lorg/jetbrains/kotlin/fir/resolve/calls/tower/TowerDataElementsForName;Lorg/jetbrains/kotlin/fir/resolve/calls/tower/TowerGroup;Lorg/jetbrains/kotlin/fir/resolve/calls/CandidateCollector;Lorg/jetbrains/kotlin/fir/resolve/calls/CandidateFactory;)V", "processLevelForRegularInvoke", "", "towerLevel", "Lorg/jetbrains/kotlin/fir/resolve/calls/tower/TowerScopeLevel;", "callInfo", "Lorg/jetbrains/kotlin/fir/resolve/calls/CallInfo;", "group", "explicitReceiverKind", "Lorg/jetbrains/kotlin/resolve/calls/tasks/ExplicitReceiverKind;", "(Lorg/jetbrains/kotlin/fir/resolve/calls/tower/TowerScopeLevel;Lorg/jetbrains/kotlin/fir/resolve/calls/CallInfo;Lorg/jetbrains/kotlin/fir/resolve/calls/tower/TowerGroup;Lorg/jetbrains/kotlin/resolve/calls/tasks/ExplicitReceiverKind;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "runResolverForBuiltinInvokeExtensionWithExplicitArgument", CompilerOptions.INFO, "invokeReceiverValue", "Lorg/jetbrains/kotlin/fir/resolve/calls/ExpressionReceiverValue;", "(Lorg/jetbrains/kotlin/fir/resolve/calls/CallInfo;Lorg/jetbrains/kotlin/fir/resolve/calls/ExpressionReceiverValue;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "runResolverForBuiltinInvokeExtensionWithImplicitArgument", "runResolverForInvoke", "withGivenInvokeReceiverGroup", "invokeResolvePriority", "Lorg/jetbrains/kotlin/fir/resolve/calls/tower/InvokeResolvePriority;", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class InvokeFunctionResolveTask extends FirBaseTowerResolveTask {
    private final TowerGroup receiverGroup;

    /* JADX INFO: renamed from: org.jetbrains.kotlin.fir.resolve.calls.tower.InvokeFunctionResolveTask$processLevelForRegularInvoke$1, reason: invalid class name */
    /* JADX INFO: compiled from: FirInvokeResolveTowerExtension.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    @DebugMetadata(c = "org.jetbrains.kotlin.fir.resolve.calls.tower.InvokeFunctionResolveTask", f = "FirInvokeResolveTowerExtension.kt", i = {}, l = {XKeySym.XK_Ncaron}, m = "processLevelForRegularInvoke", n = {}, s = {})
    static final class AnonymousClass1 extends ContinuationImpl {
        int label;
        /* synthetic */ Object result;

        AnonymousClass1(Continuation<? super AnonymousClass1> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return InvokeFunctionResolveTask.this.processLevelForRegularInvoke(null, null, null, null, this);
        }
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.fir.resolve.calls.tower.InvokeFunctionResolveTask$runResolverForBuiltinInvokeExtensionWithExplicitArgument$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: FirInvokeResolveTowerExtension.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    @DebugMetadata(c = "org.jetbrains.kotlin.fir.resolve.calls.tower.InvokeFunctionResolveTask", f = "FirInvokeResolveTowerExtension.kt", i = {}, l = {XKeySym.XK_Ncaron}, m = "runResolverForBuiltinInvokeExtensionWithExplicitArgument", n = {}, s = {})
    static final class C45411 extends ContinuationImpl {
        int label;
        /* synthetic */ Object result;

        C45411(Continuation<? super C45411> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return InvokeFunctionResolveTask.this.runResolverForBuiltinInvokeExtensionWithExplicitArgument(null, null, this);
        }
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.fir.resolve.calls.tower.InvokeFunctionResolveTask$runResolverForBuiltinInvokeExtensionWithImplicitArgument$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: FirInvokeResolveTowerExtension.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    @DebugMetadata(c = "org.jetbrains.kotlin.fir.resolve.calls.tower.InvokeFunctionResolveTask", f = "FirInvokeResolveTowerExtension.kt", i = {0, 0, 0}, l = {XKeySym.XK_Ncaron}, m = "runResolverForBuiltinInvokeExtensionWithImplicitArgument", n = {"this", CompilerOptions.INFO, "invokeReceiverValue"}, s = {"L$0", "L$1", "L$2"})
    static final class C45421 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        int label;
        /* synthetic */ Object result;

        C45421(Continuation<? super C45421> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return InvokeFunctionResolveTask.this.runResolverForBuiltinInvokeExtensionWithImplicitArgument(null, null, this);
        }
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.fir.resolve.calls.tower.InvokeFunctionResolveTask$runResolverForInvoke$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: FirInvokeResolveTowerExtension.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    @DebugMetadata(c = "org.jetbrains.kotlin.fir.resolve.calls.tower.InvokeFunctionResolveTask", f = "FirInvokeResolveTowerExtension.kt", i = {0, 0, 0, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 3, 3, 3, 3, 4, 4, 4, 4}, l = {379, 387, 387, 395, 402}, m = "runResolverForInvoke", n = {"this", CompilerOptions.INFO, "invokeReceiverValue", "this", CompilerOptions.INFO, "invokeReceiverValue", "parentGroup$iv", "this", CompilerOptions.INFO, "invokeReceiverValue", "parentGroup$iv", "lexical$iv", "depth$iv", "this", CompilerOptions.INFO, "invokeReceiverValue", "parentGroup$iv", "this", CompilerOptions.INFO, "invokeReceiverValue", "parentGroup$iv"}, s = {"L$0", "L$1", "L$2", "L$0", "L$1", "L$2", "L$3", "L$0", "L$1", "L$2", "L$3", "L$5", "I$1", "L$0", "L$1", "L$2", "L$3", "L$0", "L$1", "L$2", "L$3"})
    static final class C45431 extends ContinuationImpl {
        int I$0;
        int I$1;
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        Object L$4;
        Object L$5;
        int label;
        /* synthetic */ Object result;

        C45431(Continuation<? super C45431> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return InvokeFunctionResolveTask.this.runResolverForInvoke(null, null, this);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public InvokeFunctionResolveTask(BodyResolveComponents components, TowerResolveManager manager, TowerDataElementsForName towerDataElementsForName, TowerGroup receiverGroup, CandidateCollector collector, CandidateFactory candidateFactory) {
        super(components, manager, towerDataElementsForName, collector, candidateFactory);
        Intrinsics.checkNotNullParameter(components, "components");
        Intrinsics.checkNotNullParameter(manager, "manager");
        Intrinsics.checkNotNullParameter(towerDataElementsForName, "towerDataElementsForName");
        Intrinsics.checkNotNullParameter(receiverGroup, "receiverGroup");
        Intrinsics.checkNotNullParameter(collector, "collector");
        Intrinsics.checkNotNullParameter(candidateFactory, "candidateFactory");
        this.receiverGroup = receiverGroup;
    }

    private final TowerGroup withGivenInvokeReceiverGroup(TowerGroup towerGroup, InvokeResolvePriority invokeResolvePriority) {
        return towerGroup.InvokeReceiver(this.receiverGroup, invokeResolvePriority);
    }

    /* JADX WARN: Code duplicated, block: B:35:0x0194  */
    /* JADX WARN: Code duplicated, block: B:37:0x01a7 A[ADDED_TO_REGION] */
    /* JADX WARN: Code duplicated, block: B:50:0x0212  */
    /* JADX WARN: Code duplicated, block: B:52:0x0254 A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:54:0x0258  */
    /* JADX WARN: Code duplicated, block: B:7:0x001a  */
    /*  JADX ERROR: JadxOverflowException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxOverflowException: Regions stack size limit reached
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
        	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
        	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
        */
    public final java.lang.Object runResolverForInvoke(org.jetbrains.kotlin.fir.resolve.calls.CallInfo r31, org.jetbrains.kotlin.fir.resolve.calls.ExpressionReceiverValue r32, kotlin.coroutines.Continuation<? super kotlin.Unit> r33) {
        /*
            Method dump skipped, instruction units count: 704
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.jetbrains.kotlin.fir.resolve.calls.tower.InvokeFunctionResolveTask.runResolverForInvoke(org.jetbrains.kotlin.fir.resolve.calls.CallInfo, org.jetbrains.kotlin.fir.resolve.calls.ExpressionReceiverValue, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARN: Code duplicated, block: B:7:0x0018  */
    public final Object runResolverForBuiltinInvokeExtensionWithExplicitArgument(CallInfo callInfo, ExpressionReceiverValue expressionReceiverValue, Continuation<? super Unit> continuation) throws Throwable {
        C45411 c45411;
        if (continuation instanceof C45411) {
            c45411 = (C45411) continuation;
            if ((c45411.label & Integer.MIN_VALUE) != 0) {
                c45411.label -= Integer.MIN_VALUE;
            } else {
                c45411 = new C45411(continuation);
            }
        } else {
            c45411 = new C45411(continuation);
        }
        C45411 c45412 = c45411;
        Object objProcessLevel = c45412.result;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = c45412.label;
        if (i == 0) {
            ResultKt.throwOnFailure(objProcessLevel);
            InvokeFunctionResolveTask invokeFunctionResolveTask = this;
            MemberScopeTowerLevel memberScopeTowerLevel$default = FirBaseTowerResolveTask.toMemberScopeTowerLevel$default(invokeFunctionResolveTask, expressionReceiverValue, null, null, false, 7, null);
            TowerGroup towerGroupWithGivenInvokeReceiverGroup = withGivenInvokeReceiverGroup(TowerGroup.INSTANCE.getMember(), InvokeResolvePriority.INVOKE_EXTENSION);
            ExplicitReceiverKind explicitReceiverKind = ExplicitReceiverKind.DISPATCH_RECEIVER;
            c45412.label = 1;
            objProcessLevel = invokeFunctionResolveTask.processLevel(memberScopeTowerLevel$default, callInfo, towerGroupWithGivenInvokeReceiverGroup, explicitReceiverKind, c45412);
            if (objProcessLevel == coroutine_suspended) {
                return coroutine_suspended;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException(CoroutineConstantsKt.ILLEGAL_STATE_ERROR_MESSAGE);
            }
            ResultKt.throwOnFailure(objProcessLevel);
        }
        ((Boolean) objProcessLevel).booleanValue();
        return Unit.INSTANCE;
    }

    /* JADX WARN: Code duplicated, block: B:17:0x0069  */
    /* JADX WARN: Code duplicated, block: B:19:0x00bf A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:20:0x00c0  */
    /* JADX WARN: Code duplicated, block: B:7:0x0018  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:20:0x00c0 -> B:21:0x00c2). Please report as a decompilation issue!!! */
    /*  JADX ERROR: JadxOverflowException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxOverflowException: Regions stack size limit reached
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
        	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
        	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
        */
    public final java.lang.Object runResolverForBuiltinInvokeExtensionWithImplicitArgument(org.jetbrains.kotlin.fir.resolve.calls.CallInfo r22, org.jetbrains.kotlin.fir.resolve.calls.ExpressionReceiverValue r23, kotlin.coroutines.Continuation<? super kotlin.Unit> r24) {
        /*
            Method dump skipped, instruction units count: 204
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.jetbrains.kotlin.fir.resolve.calls.tower.InvokeFunctionResolveTask.runResolverForBuiltinInvokeExtensionWithImplicitArgument(org.jetbrains.kotlin.fir.resolve.calls.CallInfo, org.jetbrains.kotlin.fir.resolve.calls.ExpressionReceiverValue, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:7:0x0014  */
    public final Object processLevelForRegularInvoke(TowerScopeLevel towerScopeLevel, CallInfo callInfo, TowerGroup towerGroup, ExplicitReceiverKind explicitReceiverKind, Continuation<? super Unit> continuation) throws Throwable {
        AnonymousClass1 anonymousClass1;
        if (continuation instanceof AnonymousClass1) {
            anonymousClass1 = (AnonymousClass1) continuation;
            if ((anonymousClass1.label & Integer.MIN_VALUE) != 0) {
                anonymousClass1.label -= Integer.MIN_VALUE;
            } else {
                anonymousClass1 = new AnonymousClass1(continuation);
            }
        } else {
            anonymousClass1 = new AnonymousClass1(continuation);
        }
        AnonymousClass1 anonymousClass2 = anonymousClass1;
        Object objProcessLevel = anonymousClass2.result;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = anonymousClass2.label;
        if (i == 0) {
            ResultKt.throwOnFailure(objProcessLevel);
            TowerGroup towerGroupWithGivenInvokeReceiverGroup = withGivenInvokeReceiverGroup(towerGroup, InvokeResolvePriority.COMMON_INVOKE);
            anonymousClass2.label = 1;
            objProcessLevel = processLevel(towerScopeLevel, callInfo, towerGroupWithGivenInvokeReceiverGroup, explicitReceiverKind, anonymousClass2);
            if (objProcessLevel == coroutine_suspended) {
                return coroutine_suspended;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException(CoroutineConstantsKt.ILLEGAL_STATE_ERROR_MESSAGE);
            }
            ResultKt.throwOnFailure(objProcessLevel);
        }
        ((Boolean) objProcessLevel).booleanValue();
        return Unit.INSTANCE;
    }
}
