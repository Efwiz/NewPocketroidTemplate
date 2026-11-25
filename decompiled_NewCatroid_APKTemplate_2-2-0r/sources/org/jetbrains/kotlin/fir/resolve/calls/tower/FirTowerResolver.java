package org.jetbrains.kotlin.fir.resolve.calls.tower;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineConstantsKt;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression;
import org.jetbrains.kotlin.fir.expressions.FirResolvedQualifier;
import org.jetbrains.kotlin.fir.references.FirSuperReference;
import org.jetbrains.kotlin.fir.resolve.BodyResolveComponents;
import org.jetbrains.kotlin.fir.resolve.ScopeUtilsKt;
import org.jetbrains.kotlin.fir.resolve.calls.CallInfo;
import org.jetbrains.kotlin.fir.resolve.calls.CandidateCollector;
import org.jetbrains.kotlin.fir.resolve.calls.CandidateFactory;
import org.jetbrains.kotlin.fir.resolve.calls.ImplicitReceiverValue;
import org.jetbrains.kotlin.fir.resolve.calls.ResolutionContext;
import org.jetbrains.kotlin.fir.resolve.calls.ResolutionStageRunner;
import org.jetbrains.kotlin.fir.scopes.FirTypeScope;
import org.jetbrains.kotlin.fir.symbols.ConeClassLikeLookupTag;
import org.jetbrains.kotlin.fir.symbols.impl.FirConstructorSymbol;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.TypeComponentsKt;
import org.jetbrains.kotlin.name.SpecialNames;
import org.jetbrains.kotlin.resolve.calls.tasks.ExplicitReceiverKind;
import org.jetbrains.kotlin.types.AbstractTypeChecker;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.jetbrains.kotlin.types.model.TypeCheckerProviderContext;

/* JADX INFO: compiled from: FirTowerResolver.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ \u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\u000f\u001a\u00020\u0010H\u0002J(\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u0013\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0002J\u0006\u0010\u0014\u001a\u00020\u0012J&\u0010\u0015\u001a\u00020\u00072\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\nJ\"\u0010\u0015\u001a\u00020\u00072\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\n\b\u0002\u0010\u0016\u001a\u0004\u0018\u00010\u0007J&\u0010\u0017\u001a\u00020\u00072\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u000f\u001a\u00020\u0010R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001c"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/calls/tower/FirTowerResolver;", "", "components", "Lorg/jetbrains/kotlin/fir/resolve/BodyResolveComponents;", "resolutionStageRunner", "Lorg/jetbrains/kotlin/fir/resolve/calls/ResolutionStageRunner;", "collector", "Lorg/jetbrains/kotlin/fir/resolve/calls/CandidateCollector;", "(Lorg/jetbrains/kotlin/fir/resolve/BodyResolveComponents;Lorg/jetbrains/kotlin/fir/resolve/calls/ResolutionStageRunner;Lorg/jetbrains/kotlin/fir/resolve/calls/CandidateCollector;)V", "manager", "Lorg/jetbrains/kotlin/fir/resolve/calls/tower/TowerResolveManager;", "buildCandidateFactoriesAndCollectors", "Lorg/jetbrains/kotlin/fir/resolve/calls/tower/CandidateFactoriesAndCollectors;", CompilerOptions.INFO, "Lorg/jetbrains/kotlin/fir/resolve/calls/CallInfo;", "context", "Lorg/jetbrains/kotlin/fir/resolve/calls/ResolutionContext;", "enqueueResolutionTasks", "", "candidateFactoriesAndCollectors", "reset", "runResolver", "externalCollector", "runResolverForDelegatingConstructor", "constructedType", "Lorg/jetbrains/kotlin/fir/types/ConeClassLikeType;", "derivedClassLookupTag", "Lorg/jetbrains/kotlin/fir/symbols/ConeClassLikeLookupTag;", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirTowerResolver {
    private final CandidateCollector collector;
    private final BodyResolveComponents components;
    private final TowerResolveManager manager;

    public FirTowerResolver(BodyResolveComponents components, ResolutionStageRunner resolutionStageRunner, CandidateCollector collector) {
        Intrinsics.checkNotNullParameter(components, "components");
        Intrinsics.checkNotNullParameter(resolutionStageRunner, "resolutionStageRunner");
        Intrinsics.checkNotNullParameter(collector, "collector");
        this.components = components;
        this.collector = collector;
        this.manager = new TowerResolveManager(collector);
    }

    public /* synthetic */ FirTowerResolver(BodyResolveComponents bodyResolveComponents, ResolutionStageRunner resolutionStageRunner, CandidateCollector candidateCollector, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(bodyResolveComponents, resolutionStageRunner, (i & 4) != 0 ? new CandidateCollector(bodyResolveComponents, resolutionStageRunner) : candidateCollector);
    }

    public static /* synthetic */ CandidateCollector runResolver$default(FirTowerResolver firTowerResolver, CallInfo callInfo, ResolutionContext resolutionContext, CandidateCollector candidateCollector, int i, Object obj) {
        if ((i & 4) != 0) {
            candidateCollector = null;
        }
        return firTowerResolver.runResolver(callInfo, resolutionContext, candidateCollector);
    }

    public final CandidateCollector runResolver(CallInfo info, ResolutionContext context, CandidateCollector externalCollector) {
        Intrinsics.checkNotNullParameter(info, "info");
        Intrinsics.checkNotNullParameter(context, "context");
        if (externalCollector == null) {
            externalCollector = this.collector;
        }
        return runResolver(info, context, externalCollector, this.manager);
    }

    public final CandidateCollector runResolver(CallInfo info, ResolutionContext context, CandidateCollector collector, TowerResolveManager manager) {
        Intrinsics.checkNotNullParameter(info, "info");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(collector, "collector");
        Intrinsics.checkNotNullParameter(manager, "manager");
        enqueueResolutionTasks(context, manager, buildCandidateFactoriesAndCollectors(info, collector, context), info);
        manager.runTasks();
        return collector;
    }

    private final void enqueueResolutionTasks(ResolutionContext context, TowerResolveManager manager, CandidateFactoriesAndCollectors candidateFactoriesAndCollectors, CallInfo info) {
        FirInvokeResolveTowerExtension firInvokeResolveTowerExtension = new FirInvokeResolveTowerExtension(context, manager, candidateFactoriesAndCollectors);
        FirTowerResolveTask firTowerResolveTask = new FirTowerResolveTask(this.components, manager, new TowerDataElementsForName(info.getName(), this.components.getTowerDataContext()), candidateFactoriesAndCollectors.getResultCollector(), candidateFactoriesAndCollectors.getCandidateFactory());
        FirExpression explicitReceiver = info.getExplicitReceiver();
        if (explicitReceiver instanceof FirResolvedQualifier) {
            TowerResolveManager.enqueueResolverTask$default(manager, null, new AnonymousClass1(firTowerResolveTask, info, explicitReceiver, null), 1, null);
            firInvokeResolveTowerExtension.enqueueResolveTasksForQualifier(info, (FirResolvedQualifier) explicitReceiver);
            return;
        }
        if (explicitReceiver == null) {
            TowerResolveManager.enqueueResolverTask$default(manager, null, new AnonymousClass2(firTowerResolveTask, info, null), 1, null);
            firInvokeResolveTowerExtension.enqueueResolveTasksForNoReceiver(info);
            return;
        }
        if (explicitReceiver instanceof FirQualifiedAccessExpression) {
            FirQualifiedAccessExpression firQualifiedAccessExpression = (FirQualifiedAccessExpression) explicitReceiver;
            if (firQualifiedAccessExpression.getCalleeReference() instanceof FirSuperReference) {
                TowerResolveManager.enqueueResolverTask$default(manager, null, new AnonymousClass3(firTowerResolveTask, info, explicitReceiver, null), 1, null);
                firInvokeResolveTowerExtension.enqueueResolveTasksForSuperReceiver(info, firQualifiedAccessExpression);
                return;
            }
        }
        if (info.isImplicitInvoke()) {
            firInvokeResolveTowerExtension.enqueueResolveTasksForImplicitInvokeCall(info, explicitReceiver);
        } else {
            TowerResolveManager.enqueueResolverTask$default(manager, null, new AnonymousClass4(firTowerResolveTask, info, explicitReceiver, null), 1, null);
            firInvokeResolveTowerExtension.enqueueResolveTasksForExpressionReceiver(info, explicitReceiver);
        }
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.fir.resolve.calls.tower.FirTowerResolver$enqueueResolutionTasks$1, reason: invalid class name */
    /* JADX INFO: compiled from: FirTowerResolver.kt */
    @Metadata(d1 = {"\u0000\u0006\n\u0000\n\u0002\u0010\u0002\u0010\u0000\u001a\u00020\u0001H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, ""}, k = 3, mv = {1, 9, 0}, xi = 48)
    @DebugMetadata(c = "org.jetbrains.kotlin.fir.resolve.calls.tower.FirTowerResolver$enqueueResolutionTasks$1", f = "FirTowerResolver.kt", i = {}, l = {66}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {}, s = {})
    static final class AnonymousClass1 extends SuspendLambda implements Function1<Continuation<? super Unit>, Object> {
        final /* synthetic */ CallInfo $info;
        final /* synthetic */ FirTowerResolveTask $mainTask;
        final /* synthetic */ FirExpression $receiver;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(FirTowerResolveTask firTowerResolveTask, CallInfo callInfo, FirExpression firExpression, Continuation<? super AnonymousClass1> continuation) {
            super(1, continuation);
            this.$mainTask = firTowerResolveTask;
            this.$info = callInfo;
            this.$receiver = firExpression;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Continuation<?> continuation) {
            return new AnonymousClass1(this.$mainTask, this.$info, this.$receiver, continuation);
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
                if (this.$mainTask.runResolverForQualifierReceiver(this.$info, (FirResolvedQualifier) this.$receiver, this) == coroutine_suspended) {
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

    /* JADX INFO: renamed from: org.jetbrains.kotlin.fir.resolve.calls.tower.FirTowerResolver$enqueueResolutionTasks$2, reason: invalid class name */
    /* JADX INFO: compiled from: FirTowerResolver.kt */
    @Metadata(d1 = {"\u0000\u0006\n\u0000\n\u0002\u0010\u0002\u0010\u0000\u001a\u00020\u0001H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, ""}, k = 3, mv = {1, 9, 0}, xi = 48)
    @DebugMetadata(c = "org.jetbrains.kotlin.fir.resolve.calls.tower.FirTowerResolver$enqueueResolutionTasks$2", f = "FirTowerResolver.kt", i = {}, l = {70}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {}, s = {})
    static final class AnonymousClass2 extends SuspendLambda implements Function1<Continuation<? super Unit>, Object> {
        final /* synthetic */ CallInfo $info;
        final /* synthetic */ FirTowerResolveTask $mainTask;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass2(FirTowerResolveTask firTowerResolveTask, CallInfo callInfo, Continuation<? super AnonymousClass2> continuation) {
            super(1, continuation);
            this.$mainTask = firTowerResolveTask;
            this.$info = callInfo;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Continuation<?> continuation) {
            return new AnonymousClass2(this.$mainTask, this.$info, continuation);
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
                if (FirTowerResolveTask.runResolverForNoReceiver$default(this.$mainTask, this.$info, false, this, 2, null) == coroutine_suspended) {
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

    /* JADX INFO: renamed from: org.jetbrains.kotlin.fir.resolve.calls.tower.FirTowerResolver$enqueueResolutionTasks$3, reason: invalid class name */
    /* JADX INFO: compiled from: FirTowerResolver.kt */
    @Metadata(d1 = {"\u0000\u0006\n\u0000\n\u0002\u0010\u0002\u0010\u0000\u001a\u00020\u0001H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, ""}, k = 3, mv = {1, 9, 0}, xi = 48)
    @DebugMetadata(c = "org.jetbrains.kotlin.fir.resolve.calls.tower.FirTowerResolver$enqueueResolutionTasks$3", f = "FirTowerResolver.kt", i = {}, l = {76}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {}, s = {})
    static final class AnonymousClass3 extends SuspendLambda implements Function1<Continuation<? super Unit>, Object> {
        final /* synthetic */ CallInfo $info;
        final /* synthetic */ FirTowerResolveTask $mainTask;
        final /* synthetic */ FirExpression $receiver;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass3(FirTowerResolveTask firTowerResolveTask, CallInfo callInfo, FirExpression firExpression, Continuation<? super AnonymousClass3> continuation) {
            super(1, continuation);
            this.$mainTask = firTowerResolveTask;
            this.$info = callInfo;
            this.$receiver = firExpression;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Continuation<?> continuation) {
            return new AnonymousClass3(this.$mainTask, this.$info, this.$receiver, continuation);
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
                if (this.$mainTask.runResolverForSuperReceiver(this.$info, (FirQualifiedAccessExpression) this.$receiver, this) == coroutine_suspended) {
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

    /* JADX INFO: renamed from: org.jetbrains.kotlin.fir.resolve.calls.tower.FirTowerResolver$enqueueResolutionTasks$4, reason: invalid class name */
    /* JADX INFO: compiled from: FirTowerResolver.kt */
    @Metadata(d1 = {"\u0000\u0006\n\u0000\n\u0002\u0010\u0002\u0010\u0000\u001a\u00020\u0001H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, ""}, k = 3, mv = {1, 9, 0}, xi = 48)
    @DebugMetadata(c = "org.jetbrains.kotlin.fir.resolve.calls.tower.FirTowerResolver$enqueueResolutionTasks$4", f = "FirTowerResolver.kt", i = {}, l = {85}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {}, s = {})
    static final class AnonymousClass4 extends SuspendLambda implements Function1<Continuation<? super Unit>, Object> {
        final /* synthetic */ CallInfo $info;
        final /* synthetic */ FirTowerResolveTask $mainTask;
        final /* synthetic */ FirExpression $receiver;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass4(FirTowerResolveTask firTowerResolveTask, CallInfo callInfo, FirExpression firExpression, Continuation<? super AnonymousClass4> continuation) {
            super(1, continuation);
            this.$mainTask = firTowerResolveTask;
            this.$info = callInfo;
            this.$receiver = firExpression;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Continuation<?> continuation) {
            return new AnonymousClass4(this.$mainTask, this.$info, this.$receiver, continuation);
        }

        @Override // kotlin.jvm.functions.Function1
        public final Object invoke(Continuation<? super Unit> continuation) {
            return ((AnonymousClass4) create(continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                this.label = 1;
                if (FirTowerResolveTask.runResolverForExpressionReceiver$default(this.$mainTask, this.$info, this.$receiver, null, this, 4, null) == coroutine_suspended) {
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

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r8v0, types: [org.jetbrains.kotlin.fir.resolve.calls.ImplicitReceiverValue] */
    public final CandidateCollector runResolverForDelegatingConstructor(final CallInfo info, ConeClassLikeType constructedType, ConeClassLikeLookupTag derivedClassLookupTag, final ResolutionContext context) {
        Intrinsics.checkNotNullParameter(info, "info");
        Intrinsics.checkNotNullParameter(constructedType, "constructedType");
        Intrinsics.checkNotNullParameter(derivedClassLookupTag, "derivedClassLookupTag");
        Intrinsics.checkNotNullParameter(context, "context");
        ConeClassLikeType coneClassLikeTypeOuterType = this.components.getOuterClassManager().outerType(constructedType);
        final FirTypeScope firTypeScopeDelegatingConstructorScope = ScopeUtilsKt.delegatingConstructorScope(constructedType, this.components.getSession(), this.components.getScopeSession(), derivedClassLookupTag);
        if (firTypeScopeDelegatingConstructorScope == null) {
            return this.collector;
        }
        Object obj = null;
        if (coneClassLikeTypeOuterType != null) {
            for (Object obj2 : CollectionsKt.drop(this.components.getImplicitReceiverStack().receiversAsReversed(), 1)) {
                if (AbstractTypeChecker.isSubtypeOf$default(AbstractTypeChecker.INSTANCE, (TypeCheckerProviderContext) TypeComponentsKt.getTypeContext(this.components.getSession()), (KotlinTypeMarker) ((ImplicitReceiverValue) obj2).getType(), (KotlinTypeMarker) coneClassLikeTypeOuterType, false, 8, (Object) null)) {
                    obj = obj2;
                    break;
                }
            }
            obj = (ImplicitReceiverValue) obj;
            if (obj == null) {
                return this.collector;
            }
        }
        final ?? r8 = obj;
        final CandidateFactory candidateFactory = new CandidateFactory(context, info);
        final CandidateCollector candidateCollector = this.collector;
        firTypeScopeDelegatingConstructorScope.processDeclaredConstructors(new Function1<FirConstructorSymbol, Unit>() { // from class: org.jetbrains.kotlin.fir.resolve.calls.tower.FirTowerResolver.runResolverForDelegatingConstructor.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(FirConstructorSymbol firConstructorSymbol) {
                invoke2(firConstructorSymbol);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(FirConstructorSymbol it) {
                Intrinsics.checkNotNullParameter(it, "it");
                CandidateCollector candidateCollector2 = candidateCollector;
                TowerGroup member = TowerGroup.INSTANCE.getMember();
                CandidateFactory candidateFactory2 = candidateFactory;
                CallInfo callInfo = info;
                FirConstructorSymbol firConstructorSymbol = it;
                ExplicitReceiverKind explicitReceiverKind = ExplicitReceiverKind.NO_EXPLICIT_RECEIVER;
                FirTypeScope firTypeScope = firTypeScopeDelegatingConstructorScope;
                ImplicitReceiverValue<?> implicitReceiverValue = r8;
                candidateCollector2.consumeCandidate(member, candidateFactory2.createCandidate(callInfo, firConstructorSymbol, explicitReceiverKind, firTypeScope, (240 & 16) != 0 ? null : implicitReceiverValue != null ? implicitReceiverValue.getReceiverExpression() : null, (240 & 32) != 0 ? CollectionsKt.emptyList() : CollectionsKt.emptyList(), (240 & 64) != 0 ? false : false, (240 & 128) != 0 ? false : false), context);
            }
        });
        return this.collector;
    }

    private final CandidateFactoriesAndCollectors buildCandidateFactoriesAndCollectors(CallInfo info, CandidateCollector collector, ResolutionContext context) {
        return new CandidateFactoriesAndCollectors(new CandidateFactory(context, info), collector);
    }

    public final void reset() {
        this.collector.newDataSet();
        this.manager.reset();
    }
}
