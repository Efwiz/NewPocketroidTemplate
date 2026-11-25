package org.jetbrains.kotlin.fir.resolve.calls.tower;

import com.gaurav.avnc.vnc.XKeySym;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineConstantsKt;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression;
import org.jetbrains.kotlin.fir.expressions.FirResolvedQualifier;
import org.jetbrains.kotlin.fir.resolve.BodyResolveComponents;
import org.jetbrains.kotlin.fir.resolve.TypeExpansionUtilsKt;
import org.jetbrains.kotlin.fir.resolve.calls.CallInfo;
import org.jetbrains.kotlin.fir.resolve.calls.CallKind;
import org.jetbrains.kotlin.fir.resolve.calls.Candidate;
import org.jetbrains.kotlin.fir.resolve.calls.CandidateCollector;
import org.jetbrains.kotlin.fir.resolve.calls.CandidateFactory;
import org.jetbrains.kotlin.fir.resolve.calls.ExpressionReceiverValue;
import org.jetbrains.kotlin.fir.resolve.calls.ResolutionContext;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.types.CompilerConeAttributesKt;
import org.jetbrains.kotlin.fir.types.TypeUtilsKt;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.name.SpecialNames;
import org.jetbrains.kotlin.resolve.calls.tasks.ExplicitReceiverKind;
import org.jetbrains.kotlin.util.OperatorNameConventions;

/* JADX INFO: compiled from: FirInvokeResolveTowerExtension.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u008e\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\b\u0000\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\"\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\b\b\u0002\u0010\u0013\u001a\u00020\u0014H\u0002JV\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0017\u001a\u00020\u00102\b\b\u0002\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001b2$\b\u0004\u0010\u001c\u001a\u001e\b\u0001\u0012\u0004\u0012\u00020\u001e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00160\u001f\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u001dH\u0082\b¢\u0006\u0002\u0010 JB\u0010!\u001a\u00020\u00162\u0006\u0010\"\u001a\u00020\u00102*\b\u0004\u0010#\u001a$\b\u0001\u0012\u0004\u0012\u00020\u001e\u0012\u0004\u0012\u00020\u0010\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00160\u001f\u0012\u0006\u0012\u0004\u0018\u00010\u00010$H\u0082\b¢\u0006\u0002\u0010%J\u0016\u0010&\u001a\u00020\u00162\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010'\u001a\u00020(J\u0016\u0010)\u001a\u00020\u00162\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010*\u001a\u00020(J\u000e\u0010+\u001a\u00020\u00162\u0006\u0010\u000f\u001a\u00020\u0010J\u0016\u0010,\u001a\u00020\u00162\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010'\u001a\u00020-J\u0016\u0010.\u001a\u00020\u00162\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010'\u001a\u00020/J0\u00100\u001a\u00020\u00162\u0006\u00101\u001a\u00020\u00102\u0006\u00102\u001a\u0002032\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u00104\u001a\u00020\u001b2\u0006\u0010\u0011\u001a\u00020\u0012H\u0002J(\u00105\u001a\u00020\u00162\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u00106\u001a\u000207H\u0002R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\u00020\n8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u00068"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/calls/tower/FirInvokeResolveTowerExtension;", "", "context", "Lorg/jetbrains/kotlin/fir/resolve/calls/ResolutionContext;", "manager", "Lorg/jetbrains/kotlin/fir/resolve/calls/tower/TowerResolveManager;", "candidateFactoriesAndCollectors", "Lorg/jetbrains/kotlin/fir/resolve/calls/tower/CandidateFactoriesAndCollectors;", "(Lorg/jetbrains/kotlin/fir/resolve/calls/ResolutionContext;Lorg/jetbrains/kotlin/fir/resolve/calls/tower/TowerResolveManager;Lorg/jetbrains/kotlin/fir/resolve/calls/tower/CandidateFactoriesAndCollectors;)V", "components", "Lorg/jetbrains/kotlin/fir/resolve/BodyResolveComponents;", "getComponents", "()Lorg/jetbrains/kotlin/fir/resolve/BodyResolveComponents;", "createInvokeFunctionResolveTask", "Lorg/jetbrains/kotlin/fir/resolve/calls/tower/InvokeFunctionResolveTask;", CompilerOptions.INFO, "Lorg/jetbrains/kotlin/fir/resolve/calls/CallInfo;", "receiverGroup", "Lorg/jetbrains/kotlin/fir/resolve/calls/tower/TowerGroup;", "candidateFactory", "Lorg/jetbrains/kotlin/fir/resolve/calls/CandidateFactory;", "enqueueInvokeReceiverTask", "", "invokeReceiverInfo", "towerDataElementsForName", "Lorg/jetbrains/kotlin/fir/resolve/calls/tower/TowerDataElementsForName;", "invokeBuiltinExtensionMode", "", "runResolutionForInvokeReceiverVariable", "Lkotlin/Function2;", "Lorg/jetbrains/kotlin/fir/resolve/calls/tower/FirTowerResolveTask;", "Lkotlin/coroutines/Continuation;", "(Lorg/jetbrains/kotlin/fir/resolve/calls/CallInfo;Lorg/jetbrains/kotlin/fir/resolve/calls/CallInfo;Lorg/jetbrains/kotlin/fir/resolve/calls/tower/TowerDataElementsForName;ZLkotlin/jvm/functions/Function2;)V", "enqueueResolveForExplicitReceiver", "originalCallInfo", "invokeAction", "Lkotlin/Function3;", "(Lorg/jetbrains/kotlin/fir/resolve/calls/CallInfo;Lkotlin/jvm/functions/Function3;)V", "enqueueResolveTasksForExpressionReceiver", "receiver", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "enqueueResolveTasksForImplicitInvokeCall", "receiverExpression", "enqueueResolveTasksForNoReceiver", "enqueueResolveTasksForQualifier", "Lorg/jetbrains/kotlin/fir/expressions/FirResolvedQualifier;", "enqueueResolveTasksForSuperReceiver", "Lorg/jetbrains/kotlin/fir/expressions/FirQualifiedAccessExpression;", "enqueueResolverTasksForInvoke", "invokeFunctionInfo", "explicitReceiver", "Lorg/jetbrains/kotlin/fir/resolve/calls/ExpressionReceiverValue;", "useImplicitReceiverAsBuiltinInvokeArgument", "enqueueResolverTasksForInvokeReceiverCandidates", "collector", "Lorg/jetbrains/kotlin/fir/resolve/calls/CandidateCollector;", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirInvokeResolveTowerExtension {
    private final CandidateFactoriesAndCollectors candidateFactoriesAndCollectors;
    private final ResolutionContext context;
    private final TowerResolveManager manager;

    public FirInvokeResolveTowerExtension(ResolutionContext context, TowerResolveManager manager, CandidateFactoriesAndCollectors candidateFactoriesAndCollectors) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(manager, "manager");
        Intrinsics.checkNotNullParameter(candidateFactoriesAndCollectors, "candidateFactoriesAndCollectors");
        this.context = context;
        this.manager = manager;
        this.candidateFactoriesAndCollectors = candidateFactoriesAndCollectors;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final BodyResolveComponents getComponents() {
        return this.context.getBodyResolveComponents();
    }

    public final void enqueueResolveTasksForQualifier(CallInfo info, FirResolvedQualifier receiver) {
        Intrinsics.checkNotNullParameter(info, "info");
        Intrinsics.checkNotNullParameter(receiver, "receiver");
        if (Intrinsics.areEqual(info.getCallKind(), CallKind.Function.INSTANCE)) {
            CallInfo callInfoReplaceWithVariableAccess = info.replaceWithVariableAccess();
            TowerDataElementsForName towerDataElementsForName = new TowerDataElementsForName(callInfoReplaceWithVariableAccess.getName(), getComponents().getTowerDataContext());
            CandidateCollector candidateCollector = new CandidateCollector(getComponents(), getComponents().getResolutionStageRunner());
            TowerResolveManager.enqueueResolverTask$default(this.manager, null, new FirInvokeResolveTowerExtension$enqueueResolveTasksForQualifier$$inlined$enqueueResolveForExplicitReceiver$1(new InvokeReceiverResolveTask(getComponents(), this.manager, towerDataElementsForName, candidateCollector, new CandidateFactory(this.context, callInfoReplaceWithVariableAccess), new FirInvokeResolveTowerExtension$enqueueInvokeReceiverTask$invokeReceiverProcessor$1(this, false, info, candidateCollector)), null, callInfoReplaceWithVariableAccess, receiver), 1, null);
            CallInfo callInfoReplaceExplicitReceiver = callInfoReplaceWithVariableAccess.replaceExplicitReceiver(null);
            CandidateCollector candidateCollector2 = new CandidateCollector(getComponents(), getComponents().getResolutionStageRunner());
            TowerResolveManager.enqueueResolverTask$default(this.manager, null, new FirInvokeResolveTowerExtension$enqueueResolveForExplicitReceiver$$inlined$enqueueInvokeReceiverTask$2(new InvokeReceiverResolveTask(getComponents(), this.manager, towerDataElementsForName, candidateCollector2, new CandidateFactory(this.context, callInfoReplaceExplicitReceiver), new FirInvokeResolveTowerExtension$enqueueInvokeReceiverTask$invokeReceiverProcessor$1(this, true, info, candidateCollector2)), null, callInfoReplaceExplicitReceiver), 1, null);
        }
    }

    public final void enqueueResolveTasksForNoReceiver(CallInfo info) {
        Intrinsics.checkNotNullParameter(info, "info");
        if (Intrinsics.areEqual(info.getCallKind(), CallKind.Function.INSTANCE)) {
            CallInfo callInfoReplaceWithVariableAccess = info.replaceWithVariableAccess();
            TowerDataElementsForName towerDataElementsForName = new TowerDataElementsForName(callInfoReplaceWithVariableAccess.getName(), getComponents().getTowerDataContext());
            CandidateCollector candidateCollector = new CandidateCollector(getComponents(), getComponents().getResolutionStageRunner());
            TowerResolveManager.enqueueResolverTask$default(this.manager, null, new FirInvokeResolveTowerExtension$enqueueResolveTasksForNoReceiver$$inlined$enqueueInvokeReceiverTask$default$1(new InvokeReceiverResolveTask(getComponents(), this.manager, towerDataElementsForName, candidateCollector, new CandidateFactory(this.context, callInfoReplaceWithVariableAccess), new FirInvokeResolveTowerExtension$enqueueInvokeReceiverTask$invokeReceiverProcessor$1(this, false, info, candidateCollector)), null, callInfoReplaceWithVariableAccess), 1, null);
        }
    }

    public final void enqueueResolveTasksForSuperReceiver(CallInfo info, FirQualifiedAccessExpression receiver) {
        Intrinsics.checkNotNullParameter(info, "info");
        Intrinsics.checkNotNullParameter(receiver, "receiver");
        if (Intrinsics.areEqual(info.getCallKind(), CallKind.Function.INSTANCE)) {
            CallInfo callInfoReplaceWithVariableAccess = info.replaceWithVariableAccess();
            TowerDataElementsForName towerDataElementsForName = new TowerDataElementsForName(callInfoReplaceWithVariableAccess.getName(), getComponents().getTowerDataContext());
            CandidateCollector candidateCollector = new CandidateCollector(getComponents(), getComponents().getResolutionStageRunner());
            TowerResolveManager.enqueueResolverTask$default(this.manager, null, new FirInvokeResolveTowerExtension$enqueueResolveTasksForSuperReceiver$$inlined$enqueueInvokeReceiverTask$default$1(new InvokeReceiverResolveTask(getComponents(), this.manager, towerDataElementsForName, candidateCollector, new CandidateFactory(this.context, callInfoReplaceWithVariableAccess), new FirInvokeResolveTowerExtension$enqueueInvokeReceiverTask$invokeReceiverProcessor$1(this, false, info, candidateCollector)), null, callInfoReplaceWithVariableAccess, receiver), 1, null);
        }
    }

    public final void enqueueResolveTasksForExpressionReceiver(CallInfo info, FirExpression receiver) {
        Intrinsics.checkNotNullParameter(info, "info");
        Intrinsics.checkNotNullParameter(receiver, "receiver");
        if (Intrinsics.areEqual(info.getCallKind(), CallKind.Function.INSTANCE)) {
            CallInfo callInfoReplaceWithVariableAccess = info.replaceWithVariableAccess();
            TowerDataElementsForName towerDataElementsForName = new TowerDataElementsForName(callInfoReplaceWithVariableAccess.getName(), getComponents().getTowerDataContext());
            CandidateCollector candidateCollector = new CandidateCollector(getComponents(), getComponents().getResolutionStageRunner());
            TowerResolveManager.enqueueResolverTask$default(this.manager, null, new FirInvokeResolveTowerExtension$enqueueResolveTasksForExpressionReceiver$$inlined$enqueueResolveForExplicitReceiver$1(new InvokeReceiverResolveTask(getComponents(), this.manager, towerDataElementsForName, candidateCollector, new CandidateFactory(this.context, callInfoReplaceWithVariableAccess), new FirInvokeResolveTowerExtension$enqueueInvokeReceiverTask$invokeReceiverProcessor$1(this, false, info, candidateCollector)), null, callInfoReplaceWithVariableAccess, receiver), 1, null);
            CallInfo callInfoReplaceExplicitReceiver = callInfoReplaceWithVariableAccess.replaceExplicitReceiver(null);
            CandidateCollector candidateCollector2 = new CandidateCollector(getComponents(), getComponents().getResolutionStageRunner());
            TowerResolveManager.enqueueResolverTask$default(this.manager, null, new FirInvokeResolveTowerExtension$enqueueResolveForExplicitReceiver$$inlined$enqueueInvokeReceiverTask$2(new InvokeReceiverResolveTask(getComponents(), this.manager, towerDataElementsForName, candidateCollector2, new CandidateFactory(this.context, callInfoReplaceExplicitReceiver), new FirInvokeResolveTowerExtension$enqueueInvokeReceiverTask$invokeReceiverProcessor$1(this, true, info, candidateCollector2)), null, callInfoReplaceExplicitReceiver), 1, null);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void enqueueResolverTasksForInvokeReceiverCandidates(boolean invokeBuiltinExtensionMode, CallInfo info, TowerGroup receiverGroup, CandidateCollector collector) {
        for (Candidate candidate : collector.bestCandidates()) {
            FirBasedSymbol<?> symbol = candidate.getSymbol();
            boolean z = symbol instanceof FirCallableSymbol;
            if (z || (symbol instanceof FirClassLikeSymbol)) {
                boolean z2 = false;
                boolean z3 = z && TypeUtilsKt.isExtensionFunctionType(getComponents().getReturnTypeCalculator().tryCalculateReturnType((FirCallableSymbol<?>) symbol), getComponents().getSession());
                if (!invokeBuiltinExtensionMode || z3) {
                    FirExpression firExpressionChosenExtensionReceiverExpression = candidate.chosenExtensionReceiverExpression();
                    if (!invokeBuiltinExtensionMode && z3 && candidate.getExplicitReceiverKind() == ExplicitReceiverKind.NO_EXPLICIT_RECEIVER) {
                        z2 = true;
                    }
                    FirExpression firExpressionCreateExplicitReceiverForInvoke = FirInvokeResolveTowerExtensionKt.createExplicitReceiverForInvoke(getComponents(), candidate, info, invokeBuiltinExtensionMode, firExpressionChosenExtensionReceiverExpression);
                    if (firExpressionCreateExplicitReceiverForInvoke != null) {
                        Name name2 = OperatorNameConventions.INVOKE;
                        if (invokeBuiltinExtensionMode) {
                            candidate = null;
                        }
                        CallInfo callInfoCopy$default = CallInfo.copy$default(info, null, null, name2, firExpressionCreateExplicitReceiverForInvoke, null, true, null, null, null, null, candidate, null, null, null, null, 31699, null);
                        if (invokeBuiltinExtensionMode) {
                            FirExpression explicitReceiver = info.getExplicitReceiver();
                            Intrinsics.checkNotNull(explicitReceiver);
                            callInfoCopy$default = callInfoCopy$default.withReceiverAsArgument(explicitReceiver);
                        }
                        enqueueResolverTasksForInvoke(callInfoCopy$default, new ExpressionReceiverValue(firExpressionCreateExplicitReceiverForInvoke), invokeBuiltinExtensionMode, z2, receiverGroup);
                    }
                }
            }
        }
    }

    private final void enqueueResolverTasksForInvoke(CallInfo invokeFunctionInfo, ExpressionReceiverValue explicitReceiver, boolean invokeBuiltinExtensionMode, boolean useImplicitReceiverAsBuiltinInvokeArgument, TowerGroup receiverGroup) {
        InvokeFunctionResolveTask invokeFunctionResolveTaskCreateInvokeFunctionResolveTask = createInvokeFunctionResolveTask(invokeFunctionInfo, receiverGroup, new CandidateFactory(this.context, invokeFunctionInfo));
        if (invokeBuiltinExtensionMode) {
            TowerResolveManager.enqueueResolverTask$default(this.manager, null, new C45271(invokeFunctionResolveTaskCreateInvokeFunctionResolveTask, invokeFunctionInfo, explicitReceiver, null), 1, null);
            return;
        }
        if (useImplicitReceiverAsBuiltinInvokeArgument) {
            if (!CompilerConeAttributesKt.isExtensionFunctionType(TypeExpansionUtilsKt.fullyExpandedType(explicitReceiver.getType(), this.context.getSession()))) {
                throw new IllegalArgumentException("Failed requirement.".toString());
            }
            TowerResolveManager.enqueueResolverTask$default(this.manager, null, new C45282(invokeFunctionResolveTaskCreateInvokeFunctionResolveTask, invokeFunctionInfo, explicitReceiver, null), 1, null);
        }
        TowerResolveManager.enqueueResolverTask$default(this.manager, null, new C45293(invokeFunctionResolveTaskCreateInvokeFunctionResolveTask, invokeFunctionInfo, explicitReceiver, null), 1, null);
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.fir.resolve.calls.tower.FirInvokeResolveTowerExtension$enqueueResolverTasksForInvoke$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: FirInvokeResolveTowerExtension.kt */
    @Metadata(d1 = {"\u0000\u0006\n\u0000\n\u0002\u0010\u0002\u0010\u0000\u001a\u00020\u0001H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, ""}, k = 3, mv = {1, 9, 0}, xi = 48)
    @DebugMetadata(c = "org.jetbrains.kotlin.fir.resolve.calls.tower.FirInvokeResolveTowerExtension$enqueueResolverTasksForInvoke$1", f = "FirInvokeResolveTowerExtension.kt", i = {}, l = {218}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {}, s = {})
    static final class C45271 extends SuspendLambda implements Function1<Continuation<? super Unit>, Object> {
        final /* synthetic */ ExpressionReceiverValue $explicitReceiver;
        final /* synthetic */ CallInfo $invokeFunctionInfo;
        final /* synthetic */ InvokeFunctionResolveTask $task;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C45271(InvokeFunctionResolveTask invokeFunctionResolveTask, CallInfo callInfo, ExpressionReceiverValue expressionReceiverValue, Continuation<? super C45271> continuation) {
            super(1, continuation);
            this.$task = invokeFunctionResolveTask;
            this.$invokeFunctionInfo = callInfo;
            this.$explicitReceiver = expressionReceiverValue;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Continuation<?> continuation) {
            return new C45271(this.$task, this.$invokeFunctionInfo, this.$explicitReceiver, continuation);
        }

        @Override // kotlin.jvm.functions.Function1
        public final Object invoke(Continuation<? super Unit> continuation) {
            return ((C45271) create(continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                this.label = 1;
                if (this.$task.runResolverForBuiltinInvokeExtensionWithExplicitArgument(this.$invokeFunctionInfo, this.$explicitReceiver, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException(CoroutineConstantsKt.ILLEGAL_STATE_ERROR_MESSAGE);
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.fir.resolve.calls.tower.FirInvokeResolveTowerExtension$enqueueResolverTasksForInvoke$2, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: FirInvokeResolveTowerExtension.kt */
    @Metadata(d1 = {"\u0000\u0006\n\u0000\n\u0002\u0010\u0002\u0010\u0000\u001a\u00020\u0001H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, ""}, k = 3, mv = {1, 9, 0}, xi = 48)
    @DebugMetadata(c = "org.jetbrains.kotlin.fir.resolve.calls.tower.FirInvokeResolveTowerExtension$enqueueResolverTasksForInvoke$2", f = "FirInvokeResolveTowerExtension.kt", i = {}, l = {226}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {}, s = {})
    static final class C45282 extends SuspendLambda implements Function1<Continuation<? super Unit>, Object> {
        final /* synthetic */ ExpressionReceiverValue $explicitReceiver;
        final /* synthetic */ CallInfo $invokeFunctionInfo;
        final /* synthetic */ InvokeFunctionResolveTask $task;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C45282(InvokeFunctionResolveTask invokeFunctionResolveTask, CallInfo callInfo, ExpressionReceiverValue expressionReceiverValue, Continuation<? super C45282> continuation) {
            super(1, continuation);
            this.$task = invokeFunctionResolveTask;
            this.$invokeFunctionInfo = callInfo;
            this.$explicitReceiver = expressionReceiverValue;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Continuation<?> continuation) {
            return new C45282(this.$task, this.$invokeFunctionInfo, this.$explicitReceiver, continuation);
        }

        @Override // kotlin.jvm.functions.Function1
        public final Object invoke(Continuation<? super Unit> continuation) {
            return ((C45282) create(continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                this.label = 1;
                if (this.$task.runResolverForBuiltinInvokeExtensionWithImplicitArgument(this.$invokeFunctionInfo, this.$explicitReceiver, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException(CoroutineConstantsKt.ILLEGAL_STATE_ERROR_MESSAGE);
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.fir.resolve.calls.tower.FirInvokeResolveTowerExtension$enqueueResolverTasksForInvoke$3, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: FirInvokeResolveTowerExtension.kt */
    @Metadata(d1 = {"\u0000\u0006\n\u0000\n\u0002\u0010\u0002\u0010\u0000\u001a\u00020\u0001H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, ""}, k = 3, mv = {1, 9, 0}, xi = 48)
    @DebugMetadata(c = "org.jetbrains.kotlin.fir.resolve.calls.tower.FirInvokeResolveTowerExtension$enqueueResolverTasksForInvoke$3", f = "FirInvokeResolveTowerExtension.kt", i = {}, l = {XKeySym.XK_eacute}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {}, s = {})
    static final class C45293 extends SuspendLambda implements Function1<Continuation<? super Unit>, Object> {
        final /* synthetic */ ExpressionReceiverValue $explicitReceiver;
        final /* synthetic */ CallInfo $invokeFunctionInfo;
        final /* synthetic */ InvokeFunctionResolveTask $task;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C45293(InvokeFunctionResolveTask invokeFunctionResolveTask, CallInfo callInfo, ExpressionReceiverValue expressionReceiverValue, Continuation<? super C45293> continuation) {
            super(1, continuation);
            this.$task = invokeFunctionResolveTask;
            this.$invokeFunctionInfo = callInfo;
            this.$explicitReceiver = expressionReceiverValue;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Continuation<?> continuation) {
            return new C45293(this.$task, this.$invokeFunctionInfo, this.$explicitReceiver, continuation);
        }

        @Override // kotlin.jvm.functions.Function1
        public final Object invoke(Continuation<? super Unit> continuation) {
            return ((C45293) create(continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                this.label = 1;
                if (this.$task.runResolverForInvoke(this.$invokeFunctionInfo, this.$explicitReceiver, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException(CoroutineConstantsKt.ILLEGAL_STATE_ERROR_MESSAGE);
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }
    }

    public final void enqueueResolveTasksForImplicitInvokeCall(CallInfo info, FirExpression receiverExpression) {
        Intrinsics.checkNotNullParameter(info, "info");
        Intrinsics.checkNotNullParameter(receiverExpression, "receiverExpression");
        ExpressionReceiverValue expressionReceiverValue = new ExpressionReceiverValue(receiverExpression);
        InvokeFunctionResolveTask invokeFunctionResolveTaskCreateInvokeFunctionResolveTask$default = createInvokeFunctionResolveTask$default(this, info, TowerGroup.INSTANCE.getEmptyRootForInvokeReceiver(), null, 4, null);
        TowerResolveManager.enqueueResolverTask$default(this.manager, null, new AnonymousClass1(invokeFunctionResolveTaskCreateInvokeFunctionResolveTask$default, info, expressionReceiverValue, null), 1, null);
        TowerResolveManager.enqueueResolverTask$default(this.manager, null, new AnonymousClass2(invokeFunctionResolveTaskCreateInvokeFunctionResolveTask$default, info, expressionReceiverValue, null), 1, null);
        TowerResolveManager.enqueueResolverTask$default(this.manager, null, new AnonymousClass3(invokeFunctionResolveTaskCreateInvokeFunctionResolveTask$default, info, expressionReceiverValue, null), 1, null);
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.fir.resolve.calls.tower.FirInvokeResolveTowerExtension$enqueueResolveTasksForImplicitInvokeCall$1, reason: invalid class name */
    /* JADX INFO: compiled from: FirInvokeResolveTowerExtension.kt */
    @Metadata(d1 = {"\u0000\u0006\n\u0000\n\u0002\u0010\u0002\u0010\u0000\u001a\u00020\u0001H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, ""}, k = 3, mv = {1, 9, 0}, xi = 48)
    @DebugMetadata(c = "org.jetbrains.kotlin.fir.resolve.calls.tower.FirInvokeResolveTowerExtension$enqueueResolveTasksForImplicitInvokeCall$1", f = "FirInvokeResolveTowerExtension.kt", i = {}, l = {245}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {}, s = {})
    static final class AnonymousClass1 extends SuspendLambda implements Function1<Continuation<? super Unit>, Object> {
        final /* synthetic */ ExpressionReceiverValue $explicitReceiverValue;
        final /* synthetic */ CallInfo $info;
        final /* synthetic */ InvokeFunctionResolveTask $task;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(InvokeFunctionResolveTask invokeFunctionResolveTask, CallInfo callInfo, ExpressionReceiverValue expressionReceiverValue, Continuation<? super AnonymousClass1> continuation) {
            super(1, continuation);
            this.$task = invokeFunctionResolveTask;
            this.$info = callInfo;
            this.$explicitReceiverValue = expressionReceiverValue;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Continuation<?> continuation) {
            return new AnonymousClass1(this.$task, this.$info, this.$explicitReceiverValue, continuation);
        }

        @Override // kotlin.jvm.functions.Function1
        public final Object invoke(Continuation<? super Unit> continuation) {
            return ((AnonymousClass1) create(continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                this.label = 1;
                if (this.$task.runResolverForInvoke(this.$info, this.$explicitReceiverValue, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException(CoroutineConstantsKt.ILLEGAL_STATE_ERROR_MESSAGE);
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.fir.resolve.calls.tower.FirInvokeResolveTowerExtension$enqueueResolveTasksForImplicitInvokeCall$2, reason: invalid class name */
    /* JADX INFO: compiled from: FirInvokeResolveTowerExtension.kt */
    @Metadata(d1 = {"\u0000\u0006\n\u0000\n\u0002\u0010\u0002\u0010\u0000\u001a\u00020\u0001H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, ""}, k = 3, mv = {1, 9, 0}, xi = 48)
    @DebugMetadata(c = "org.jetbrains.kotlin.fir.resolve.calls.tower.FirInvokeResolveTowerExtension$enqueueResolveTasksForImplicitInvokeCall$2", f = "FirInvokeResolveTowerExtension.kt", i = {}, l = {250}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {}, s = {})
    static final class AnonymousClass2 extends SuspendLambda implements Function1<Continuation<? super Unit>, Object> {
        final /* synthetic */ ExpressionReceiverValue $explicitReceiverValue;
        final /* synthetic */ CallInfo $info;
        final /* synthetic */ InvokeFunctionResolveTask $task;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass2(InvokeFunctionResolveTask invokeFunctionResolveTask, CallInfo callInfo, ExpressionReceiverValue expressionReceiverValue, Continuation<? super AnonymousClass2> continuation) {
            super(1, continuation);
            this.$task = invokeFunctionResolveTask;
            this.$info = callInfo;
            this.$explicitReceiverValue = expressionReceiverValue;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Continuation<?> continuation) {
            return new AnonymousClass2(this.$task, this.$info, this.$explicitReceiverValue, continuation);
        }

        @Override // kotlin.jvm.functions.Function1
        public final Object invoke(Continuation<? super Unit> continuation) {
            return ((AnonymousClass2) create(continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                this.label = 1;
                if (this.$task.runResolverForBuiltinInvokeExtensionWithExplicitArgument(this.$info, this.$explicitReceiverValue, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException(CoroutineConstantsKt.ILLEGAL_STATE_ERROR_MESSAGE);
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.fir.resolve.calls.tower.FirInvokeResolveTowerExtension$enqueueResolveTasksForImplicitInvokeCall$3, reason: invalid class name */
    /* JADX INFO: compiled from: FirInvokeResolveTowerExtension.kt */
    @Metadata(d1 = {"\u0000\u0006\n\u0000\n\u0002\u0010\u0002\u0010\u0000\u001a\u00020\u0001H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, ""}, k = 3, mv = {1, 9, 0}, xi = 48)
    @DebugMetadata(c = "org.jetbrains.kotlin.fir.resolve.calls.tower.FirInvokeResolveTowerExtension$enqueueResolveTasksForImplicitInvokeCall$3", f = "FirInvokeResolveTowerExtension.kt", i = {}, l = {255}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {}, s = {})
    static final class AnonymousClass3 extends SuspendLambda implements Function1<Continuation<? super Unit>, Object> {
        final /* synthetic */ ExpressionReceiverValue $explicitReceiverValue;
        final /* synthetic */ CallInfo $info;
        final /* synthetic */ InvokeFunctionResolveTask $task;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass3(InvokeFunctionResolveTask invokeFunctionResolveTask, CallInfo callInfo, ExpressionReceiverValue expressionReceiverValue, Continuation<? super AnonymousClass3> continuation) {
            super(1, continuation);
            this.$task = invokeFunctionResolveTask;
            this.$info = callInfo;
            this.$explicitReceiverValue = expressionReceiverValue;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Continuation<?> continuation) {
            return new AnonymousClass3(this.$task, this.$info, this.$explicitReceiverValue, continuation);
        }

        @Override // kotlin.jvm.functions.Function1
        public final Object invoke(Continuation<? super Unit> continuation) {
            return ((AnonymousClass3) create(continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                this.label = 1;
                if (this.$task.runResolverForBuiltinInvokeExtensionWithImplicitArgument(this.$info, this.$explicitReceiverValue, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException(CoroutineConstantsKt.ILLEGAL_STATE_ERROR_MESSAGE);
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }
    }

    static /* synthetic */ InvokeFunctionResolveTask createInvokeFunctionResolveTask$default(FirInvokeResolveTowerExtension firInvokeResolveTowerExtension, CallInfo callInfo, TowerGroup towerGroup, CandidateFactory candidateFactory, int i, Object obj) {
        if ((i & 4) != 0) {
            candidateFactory = firInvokeResolveTowerExtension.candidateFactoriesAndCollectors.getCandidateFactory();
        }
        return firInvokeResolveTowerExtension.createInvokeFunctionResolveTask(callInfo, towerGroup, candidateFactory);
    }

    private final InvokeFunctionResolveTask createInvokeFunctionResolveTask(CallInfo info, TowerGroup receiverGroup, CandidateFactory candidateFactory) {
        return new InvokeFunctionResolveTask(getComponents(), this.manager, new TowerDataElementsForName(info.getName(), getComponents().getTowerDataContext()), receiverGroup, this.candidateFactoriesAndCollectors.getResultCollector(), candidateFactory);
    }
}
