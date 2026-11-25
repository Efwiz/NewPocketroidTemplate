package org.jetbrains.kotlin.fir.resolve.calls.tower;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IndexedValue;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtFakeSourceElementKind;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.KtSourceElementKt;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineConstantsKt;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.declarations.FirTowerDataElement;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirResolvedQualifier;
import org.jetbrains.kotlin.fir.expressions.builder.FirResolvedQualifierBuilder;
import org.jetbrains.kotlin.fir.resolve.BodyResolveComponents;
import org.jetbrains.kotlin.fir.resolve.ResolveUtilsKt;
import org.jetbrains.kotlin.fir.resolve.calls.CallInfo;
import org.jetbrains.kotlin.fir.resolve.calls.CandidateCollector;
import org.jetbrains.kotlin.fir.resolve.calls.CandidateFactory;
import org.jetbrains.kotlin.fir.resolve.calls.ContextReceiverValue;
import org.jetbrains.kotlin.fir.resolve.calls.ExpressionReceiverValue;
import org.jetbrains.kotlin.fir.resolve.calls.ImplicitReceiverValue;
import org.jetbrains.kotlin.fir.resolve.calls.ReceiverValue;
import org.jetbrains.kotlin.fir.scopes.FirScope;
import org.jetbrains.kotlin.fir.scopes.impl.FirLocalScope;
import org.jetbrains.kotlin.fir.scopes.impl.FirWhenSubjectImportingScope;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.resolve.calls.tasks.ExplicitReceiverKind;

/* JADX INFO: compiled from: FirTowerResolveTask.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000Â\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\b \u0018\u00002\u00020\u0001B-\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b¢\u0006\u0002\u0010\fJ2\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00190\u00182\u0018\u0010\u001a\u001a\u0014\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u001b\u0018\u00010\u0018j\u0004\u0018\u0001`\u001c2\b\u0010\u001d\u001a\u0004\u0018\u00010\u001eH\u0002J~\u0010\u001f\u001a\u00020 2\b\b\u0002\u0010!\u001a\u00020\"2 \u0010#\u001a\u001c\u0012\u0004\u0012\u00020%\u0012\u0006\u0012\u0004\u0018\u00010&\u0012\u0004\u0012\u00020\"\u0012\u0004\u0012\u00020 0$2\u001c\u0010'\u001a\u0018\u0012\b\u0012\u0006\u0012\u0002\b\u00030)\u0012\u0004\u0012\u00020\"\u0012\u0004\u0012\u00020 0(2&\u0010*\u001a\"\u0012\u0012\u0012\u0010\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u001b0\u0018j\u0002`\u001c\u0012\u0004\u0012\u00020\"\u0012\u0004\u0012\u00020 0(H\u0084\bø\u0001\u0000J\u0010\u0010+\u001a\u00020\"2\u0006\u0010,\u001a\u00020\"H\u0016J\u0010\u0010-\u001a\u00020 2\u0006\u0010,\u001a\u00020\"H\u0016J.\u0010.\u001a\u00020/2\u0006\u00100\u001a\u0002012\u0006\u00102\u001a\u0002032\u0006\u00104\u001a\u00020\"2\u0006\u00105\u001a\u000206H\u0082@¢\u0006\u0002\u00107J@\u0010.\u001a\u00020 2\u0006\u00100\u001a\u0002012\u0006\u00102\u001a\u0002032\u0006\u00104\u001a\u00020\"2\b\b\u0002\u00105\u001a\u0002062\u000e\b\u0002\u00108\u001a\b\u0012\u0004\u0012\u00020 09H\u0084H¢\u0006\u0002\u0010:JB\u0010;\u001a\u00020<*\u0010\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u001b0\u0018j\u0002`\u001c2\n\b\u0002\u0010=\u001a\u0004\u0018\u00010\u001e2\u001a\b\u0002\u0010>\u001a\u0014\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u001b\u0018\u00010\u0018j\u0004\u0018\u0001`\u001cH\u0004J>\u0010;\u001a\u00020?*\u00020\u001e2\n\b\u0002\u0010=\u001a\u0004\u0018\u00010\u001e2\u001a\b\u0002\u0010\u001a\u001a\u0014\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u001b\u0018\u00010\u0018j\u0004\u0018\u0001`\u001c2\b\b\u0002\u0010@\u001a\u00020/H\u0004JT\u0010A\u001a\u00020B*\u00020%2\n\b\u0002\u0010=\u001a\u0004\u0018\u00010\u001e2\b\b\u0002\u0010C\u001a\u00020/2\b\b\u0002\u0010D\u001a\u00020/2\u001a\b\u0002\u0010\u001a\u001a\u0014\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u001b\u0018\u00010\u0018j\u0004\u0018\u0001`\u001c2\n\b\u0002\u0010E\u001a\u0004\u0018\u00010FH\u0004J$\u0010G\u001a\u00020B*\u00020%2\n\b\u0002\u0010H\u001a\u0004\u0018\u00010&2\n\b\u0002\u0010I\u001a\u0004\u0018\u00010JH\u0004R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0002\u001a\u00020\u0003X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0011\u001a\u00020\u00128DX\u0084\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014R\u0014\u0010\u0006\u001a\u00020\u0007X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006K"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/calls/tower/FirBaseTowerResolveTask;", "", "components", "Lorg/jetbrains/kotlin/fir/resolve/BodyResolveComponents;", "manager", "Lorg/jetbrains/kotlin/fir/resolve/calls/tower/TowerResolveManager;", "towerDataElementsForName", "Lorg/jetbrains/kotlin/fir/resolve/calls/tower/TowerDataElementsForName;", "collector", "Lorg/jetbrains/kotlin/fir/resolve/calls/CandidateCollector;", "candidateFactory", "Lorg/jetbrains/kotlin/fir/resolve/calls/CandidateFactory;", "(Lorg/jetbrains/kotlin/fir/resolve/BodyResolveComponents;Lorg/jetbrains/kotlin/fir/resolve/calls/tower/TowerResolveManager;Lorg/jetbrains/kotlin/fir/resolve/calls/tower/TowerDataElementsForName;Lorg/jetbrains/kotlin/fir/resolve/calls/CandidateCollector;Lorg/jetbrains/kotlin/fir/resolve/calls/CandidateFactory;)V", "getComponents", "()Lorg/jetbrains/kotlin/fir/resolve/BodyResolveComponents;", "handler", "Lorg/jetbrains/kotlin/fir/resolve/calls/tower/TowerLevelHandler;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "getSession", "()Lorg/jetbrains/kotlin/fir/FirSession;", "getTowerDataElementsForName", "()Lorg/jetbrains/kotlin/fir/resolve/calls/tower/TowerDataElementsForName;", "createExtensionReceiverOptions", "", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "contextReceiverGroup", "Lorg/jetbrains/kotlin/fir/resolve/calls/ContextReceiverValue;", "Lorg/jetbrains/kotlin/fir/declarations/ContextReceiverGroup;", "extensionReceiverValue", "Lorg/jetbrains/kotlin/fir/resolve/calls/ReceiverValue;", "enumerateTowerLevels", "", "parentGroup", "Lorg/jetbrains/kotlin/fir/resolve/calls/tower/TowerGroup;", "onScope", "Lkotlin/Function3;", "Lorg/jetbrains/kotlin/fir/scopes/FirScope;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirRegularClassSymbol;", "onImplicitReceiver", "Lkotlin/Function2;", "Lorg/jetbrains/kotlin/fir/resolve/calls/ImplicitReceiverValue;", "onContextReceiverGroup", "interceptTowerGroup", "towerGroup", "onSuccessfulLevel", "processLevel", "", "towerLevel", "Lorg/jetbrains/kotlin/fir/resolve/calls/tower/TowerScopeLevel;", "callInfo", "Lorg/jetbrains/kotlin/fir/resolve/calls/CallInfo;", "group", "explicitReceiverKind", "Lorg/jetbrains/kotlin/resolve/calls/tasks/ExplicitReceiverKind;", "(Lorg/jetbrains/kotlin/fir/resolve/calls/tower/TowerScopeLevel;Lorg/jetbrains/kotlin/fir/resolve/calls/CallInfo;Lorg/jetbrains/kotlin/fir/resolve/calls/tower/TowerGroup;Lorg/jetbrains/kotlin/resolve/calls/tasks/ExplicitReceiverKind;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "onEmptyLevel", "Lkotlin/Function0;", "(Lorg/jetbrains/kotlin/fir/resolve/calls/tower/TowerScopeLevel;Lorg/jetbrains/kotlin/fir/resolve/calls/CallInfo;Lorg/jetbrains/kotlin/fir/resolve/calls/tower/TowerGroup;Lorg/jetbrains/kotlin/resolve/calls/tasks/ExplicitReceiverKind;Lkotlin/jvm/functions/Function0;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "toMemberScopeTowerLevel", "Lorg/jetbrains/kotlin/fir/resolve/calls/tower/ContextReceiverGroupMemberScopeTowerLevel;", "extensionReceiver", "otherContextReceiverGroup", "Lorg/jetbrains/kotlin/fir/resolve/calls/tower/MemberScopeTowerLevel;", "skipSynthetics", "toScopeTowerLevel", "Lorg/jetbrains/kotlin/fir/resolve/calls/tower/ScopeTowerLevel;", "withHideMembersOnly", "includeInnerConstructors", "dispatchReceiverForStatics", "Lorg/jetbrains/kotlin/fir/resolve/calls/ExpressionReceiverValue;", "toScopeTowerLevelForStaticWithImplicitDispatchReceiver", "staticOwnerOwnerSymbol", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FirBaseTowerResolveTask {
    private final CandidateFactory candidateFactory;
    private final CandidateCollector collector;
    private final BodyResolveComponents components;
    private final TowerLevelHandler handler;
    private final TowerResolveManager manager;
    private final TowerDataElementsForName towerDataElementsForName;

    /* JADX INFO: renamed from: org.jetbrains.kotlin.fir.resolve.calls.tower.FirBaseTowerResolveTask$processLevel$1, reason: invalid class name */
    /* JADX INFO: compiled from: FirTowerResolveTask.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 176)
    @DebugMetadata(c = "org.jetbrains.kotlin.fir.resolve.calls.tower.FirBaseTowerResolveTask", f = "FirTowerResolveTask.kt", i = {0}, l = {82}, m = "processLevel", n = {"onEmptyLevel"}, s = {"L$0"})
    static final class AnonymousClass1 extends ContinuationImpl {
        Object L$0;
        int label;
        /* synthetic */ Object result;

        AnonymousClass1(Continuation<? super AnonymousClass1> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return FirBaseTowerResolveTask.this.processLevel(null, null, null, null, null, this);
        }
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.fir.resolve.calls.tower.FirBaseTowerResolveTask$processLevel$3, reason: invalid class name */
    /* JADX INFO: compiled from: FirTowerResolveTask.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    @DebugMetadata(c = "org.jetbrains.kotlin.fir.resolve.calls.tower.FirBaseTowerResolveTask", f = "FirTowerResolveTask.kt", i = {0, 0, 0, 0, 0}, l = {194}, m = "processLevel", n = {"this", "towerLevel", "callInfo", "explicitReceiverKind", "finalGroup"}, s = {"L$0", "L$1", "L$2", "L$3", "L$4"})
    static final class AnonymousClass3 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        Object L$4;
        int label;
        /* synthetic */ Object result;

        AnonymousClass3(Continuation<? super AnonymousClass3> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return FirBaseTowerResolveTask.this.processLevel(null, null, null, null, this);
        }
    }

    public TowerGroup interceptTowerGroup(TowerGroup towerGroup) {
        Intrinsics.checkNotNullParameter(towerGroup, "towerGroup");
        return towerGroup;
    }

    public void onSuccessfulLevel(TowerGroup towerGroup) {
        Intrinsics.checkNotNullParameter(towerGroup, "towerGroup");
    }

    public FirBaseTowerResolveTask(BodyResolveComponents components, TowerResolveManager manager, TowerDataElementsForName towerDataElementsForName, CandidateCollector collector, CandidateFactory candidateFactory) {
        Intrinsics.checkNotNullParameter(components, "components");
        Intrinsics.checkNotNullParameter(manager, "manager");
        Intrinsics.checkNotNullParameter(towerDataElementsForName, "towerDataElementsForName");
        Intrinsics.checkNotNullParameter(collector, "collector");
        Intrinsics.checkNotNullParameter(candidateFactory, "candidateFactory");
        this.components = components;
        this.manager = manager;
        this.towerDataElementsForName = towerDataElementsForName;
        this.collector = collector;
        this.candidateFactory = candidateFactory;
        this.handler = new TowerLevelHandler();
    }

    protected final BodyResolveComponents getComponents() {
        return this.components;
    }

    protected final TowerDataElementsForName getTowerDataElementsForName() {
        return this.towerDataElementsForName;
    }

    protected final FirSession getSession() {
        return this.components.getSession();
    }

    /* JADX WARN: Code duplicated, block: B:7:0x0014  */
    protected final Object processLevel(TowerScopeLevel towerScopeLevel, CallInfo callInfo, TowerGroup towerGroup, ExplicitReceiverKind explicitReceiverKind, Function0<Unit> function0, Continuation<? super Unit> continuation) throws Throwable {
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
            anonymousClass2.L$0 = function0;
            anonymousClass2.label = 1;
            objProcessLevel = processLevel(towerScopeLevel, callInfo, towerGroup, explicitReceiverKind, anonymousClass2);
            if (objProcessLevel == coroutine_suspended) {
                return coroutine_suspended;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException(CoroutineConstantsKt.ILLEGAL_STATE_ERROR_MESSAGE);
            }
            function0 = (Function0) anonymousClass2.L$0;
            ResultKt.throwOnFailure(objProcessLevel);
        }
        if (((Boolean) objProcessLevel).booleanValue()) {
            function0.invoke();
        }
        return Unit.INSTANCE;
    }

    public static /* synthetic */ Object processLevel$default(FirBaseTowerResolveTask firBaseTowerResolveTask, TowerScopeLevel towerScopeLevel, CallInfo callInfo, TowerGroup towerGroup, ExplicitReceiverKind explicitReceiverKind, Function0 function0, Continuation continuation, int i, Object obj) throws Throwable {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: processLevel");
        }
        if ((i & 8) != 0) {
            explicitReceiverKind = ExplicitReceiverKind.NO_EXPLICIT_RECEIVER;
        }
        ExplicitReceiverKind explicitReceiverKind2 = explicitReceiverKind;
        if ((i & 16) != 0) {
            function0 = new Function0<Unit>() { // from class: org.jetbrains.kotlin.fir.resolve.calls.tower.FirBaseTowerResolveTask.processLevel.2
                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2() {
                }

                @Override // kotlin.jvm.functions.Function0
                public /* bridge */ /* synthetic */ Unit invoke() {
                    invoke2();
                    return Unit.INSTANCE;
                }
            };
        }
        InlineMarker.mark(0);
        Object objProcessLevel = firBaseTowerResolveTask.processLevel(towerScopeLevel, callInfo, towerGroup, explicitReceiverKind2, continuation);
        InlineMarker.mark(1);
        if (((Boolean) objProcessLevel).booleanValue()) {
            function0.invoke();
        }
        return Unit.INSTANCE;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ ScopeTowerLevel toScopeTowerLevel$default(FirBaseTowerResolveTask firBaseTowerResolveTask, FirScope firScope, ReceiverValue receiverValue, boolean z, boolean z2, List list, ExpressionReceiverValue expressionReceiverValue, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: toScopeTowerLevel");
        }
        ReceiverValue receiverValue2 = (i & 1) != 0 ? null : receiverValue;
        boolean z3 = false;
        boolean z4 = (i & 2) != 0 ? false : z;
        if ((i & 4) == 0) {
            z3 = z2;
        } else if (receiverValue2 != null) {
            z3 = true;
        }
        return firBaseTowerResolveTask.toScopeTowerLevel(firScope, receiverValue2, z4, z3, (i & 8) != 0 ? null : list, (i & 16) == 0 ? expressionReceiverValue : null);
    }

    protected final ScopeTowerLevel toScopeTowerLevel(FirScope firScope, ReceiverValue receiverValue, boolean z, boolean z2, List<? extends ContextReceiverValue<?>> list, ExpressionReceiverValue expressionReceiverValue) {
        Intrinsics.checkNotNullParameter(firScope, "<this>");
        return new ScopeTowerLevel(this.components, firScope, createExtensionReceiverOptions(list, receiverValue), z, z2, expressionReceiverValue);
    }

    public static /* synthetic */ ScopeTowerLevel toScopeTowerLevelForStaticWithImplicitDispatchReceiver$default(FirBaseTowerResolveTask firBaseTowerResolveTask, FirScope firScope, FirRegularClassSymbol firRegularClassSymbol, KtSourceElement ktSourceElement, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: toScopeTowerLevelForStaticWithImplicitDispatchReceiver");
        }
        if ((i & 1) != 0) {
            firRegularClassSymbol = null;
        }
        if ((i & 2) != 0) {
            ktSourceElement = null;
        }
        return firBaseTowerResolveTask.toScopeTowerLevelForStaticWithImplicitDispatchReceiver(firScope, firRegularClassSymbol, ktSourceElement);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ MemberScopeTowerLevel toMemberScopeTowerLevel$default(FirBaseTowerResolveTask firBaseTowerResolveTask, ReceiverValue receiverValue, ReceiverValue receiverValue2, List list, boolean z, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: toMemberScopeTowerLevel");
        }
        if ((i & 1) != 0) {
            receiverValue2 = null;
        }
        if ((i & 2) != 0) {
            list = null;
        }
        if ((i & 4) != 0) {
            z = false;
        }
        return firBaseTowerResolveTask.toMemberScopeTowerLevel(receiverValue, receiverValue2, list, z);
    }

    protected final MemberScopeTowerLevel toMemberScopeTowerLevel(ReceiverValue receiverValue, ReceiverValue receiverValue2, List<? extends ContextReceiverValue<?>> list, boolean z) {
        Intrinsics.checkNotNullParameter(receiverValue, "<this>");
        return new MemberScopeTowerLevel(this.components, receiverValue, createExtensionReceiverOptions(list, receiverValue2), z);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ ContextReceiverGroupMemberScopeTowerLevel toMemberScopeTowerLevel$default(FirBaseTowerResolveTask firBaseTowerResolveTask, List list, ReceiverValue receiverValue, List list2, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: toMemberScopeTowerLevel");
        }
        if ((i & 1) != 0) {
            receiverValue = null;
        }
        if ((i & 2) != 0) {
            list2 = null;
        }
        return firBaseTowerResolveTask.toMemberScopeTowerLevel(list, receiverValue, list2);
    }

    protected final ContextReceiverGroupMemberScopeTowerLevel toMemberScopeTowerLevel(List<? extends ContextReceiverValue<?>> list, ReceiverValue receiverValue, List<? extends ContextReceiverValue<?>> list2) {
        Intrinsics.checkNotNullParameter(list, "<this>");
        return new ContextReceiverGroupMemberScopeTowerLevel(this.components, list, createExtensionReceiverOptions(list2, receiverValue));
    }

    private final List<FirExpression> createExtensionReceiverOptions(List<? extends ContextReceiverValue<?>> contextReceiverGroup, ReceiverValue extensionReceiverValue) {
        if (contextReceiverGroup == null) {
            if (extensionReceiverValue != null) {
                return CollectionsKt.listOf(extensionReceiverValue.getReceiverExpression());
            }
            return CollectionsKt.emptyList();
        }
        List<? extends ContextReceiverValue<?>> list = contextReceiverGroup;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(((ContextReceiverValue) it.next()).getReceiverExpression());
        }
        return arrayList;
    }

    public static /* synthetic */ void enumerateTowerLevels$default(FirBaseTowerResolveTask firBaseTowerResolveTask, TowerGroup parentGroup, Function3 onScope, Function2 onImplicitReceiver, Function2 onContextReceiverGroup, int i, Object obj) {
        if (obj == null) {
            if ((i & 1) != 0) {
                parentGroup = TowerGroup.INSTANCE.getEmptyRoot();
            }
            Intrinsics.checkNotNullParameter(parentGroup, "parentGroup");
            Intrinsics.checkNotNullParameter(onScope, "onScope");
            Intrinsics.checkNotNullParameter(onImplicitReceiver, "onImplicitReceiver");
            Intrinsics.checkNotNullParameter(onContextReceiverGroup, "onContextReceiverGroup");
            for (IndexedValue<FirLocalScope> indexedValue : firBaseTowerResolveTask.towerDataElementsForName.getReversedFilteredLocalScopes()) {
                onScope.invoke(indexedValue.component2(), null, parentGroup.Local(indexedValue.getIndex()));
            }
            int i2 = 0;
            for (FirTowerDataElement firTowerDataElement : firBaseTowerResolveTask.towerDataElementsForName.getNonLocalTowerDataElements()) {
                int i3 = i2 + 1;
                FirScope scope = firTowerDataElement.getScope();
                if (!firTowerDataElement.getIsLocal() && scope != null) {
                    onScope.invoke(scope, firTowerDataElement.getStaticScopeOwnerSymbol(), scope instanceof FirWhenSubjectImportingScope ? TowerGroup.INSTANCE.UnqualifiedEnum(i2) : parentGroup.NonLocal(i2));
                }
                ImplicitReceiverValue<?> implicitReceiver = firTowerDataElement.getImplicitReceiver();
                if (implicitReceiver != null) {
                    onImplicitReceiver.invoke(implicitReceiver, parentGroup.Implicit(i2));
                }
                i2 = i3;
            }
            for (IndexedValue<List<ContextReceiverValue<?>>> indexedValue2 : firBaseTowerResolveTask.towerDataElementsForName.getContextReceiverGroups()) {
                onContextReceiverGroup.invoke(indexedValue2.component2(), parentGroup.ContextReceiverGroup(indexedValue2.getIndex()));
            }
            return;
        }
        throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: enumerateTowerLevels");
    }

    protected final void enumerateTowerLevels(TowerGroup parentGroup, Function3<? super FirScope, ? super FirRegularClassSymbol, ? super TowerGroup, Unit> onScope, Function2<? super ImplicitReceiverValue<?>, ? super TowerGroup, Unit> onImplicitReceiver, Function2<? super List<? extends ContextReceiverValue<?>>, ? super TowerGroup, Unit> onContextReceiverGroup) {
        Intrinsics.checkNotNullParameter(parentGroup, "parentGroup");
        Intrinsics.checkNotNullParameter(onScope, "onScope");
        Intrinsics.checkNotNullParameter(onImplicitReceiver, "onImplicitReceiver");
        Intrinsics.checkNotNullParameter(onContextReceiverGroup, "onContextReceiverGroup");
        for (IndexedValue<FirLocalScope> indexedValue : this.towerDataElementsForName.getReversedFilteredLocalScopes()) {
            onScope.invoke(indexedValue.component2(), null, parentGroup.Local(indexedValue.getIndex()));
        }
        int i = 0;
        for (FirTowerDataElement firTowerDataElement : this.towerDataElementsForName.getNonLocalTowerDataElements()) {
            int i2 = i + 1;
            FirScope scope = firTowerDataElement.getScope();
            if (!firTowerDataElement.getIsLocal() && scope != null) {
                onScope.invoke(scope, firTowerDataElement.getStaticScopeOwnerSymbol(), scope instanceof FirWhenSubjectImportingScope ? TowerGroup.INSTANCE.UnqualifiedEnum(i) : parentGroup.NonLocal(i));
            }
            ImplicitReceiverValue<?> implicitReceiver = firTowerDataElement.getImplicitReceiver();
            if (implicitReceiver != null) {
                onImplicitReceiver.invoke(implicitReceiver, parentGroup.Implicit(i));
            }
            i = i2;
        }
        for (IndexedValue<List<ContextReceiverValue<?>>> indexedValue2 : this.towerDataElementsForName.getContextReceiverGroups()) {
            onContextReceiverGroup.invoke(indexedValue2.component2(), parentGroup.ContextReceiverGroup(indexedValue2.getIndex()));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:7:0x0017  */
    public final Object processLevel(TowerScopeLevel towerScopeLevel, CallInfo callInfo, TowerGroup towerGroup, ExplicitReceiverKind explicitReceiverKind, Continuation<? super Boolean> continuation) throws Throwable {
        AnonymousClass3 anonymousClass3;
        FirBaseTowerResolveTask firBaseTowerResolveTask;
        TowerGroup towerGroup2;
        TowerScopeLevel towerScopeLevel2;
        CallInfo callInfo2;
        ExplicitReceiverKind explicitReceiverKind2;
        if (continuation instanceof AnonymousClass3) {
            anonymousClass3 = (AnonymousClass3) continuation;
            if ((anonymousClass3.label & Integer.MIN_VALUE) != 0) {
                anonymousClass3.label -= Integer.MIN_VALUE;
            } else {
                anonymousClass3 = new AnonymousClass3(continuation);
            }
        } else {
            anonymousClass3 = new AnonymousClass3(continuation);
        }
        Object obj = anonymousClass3.result;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = anonymousClass3.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            TowerGroup towerGroupInterceptTowerGroup = interceptTowerGroup(towerGroup);
            TowerResolveManager towerResolveManager = this.manager;
            anonymousClass3.L$0 = this;
            anonymousClass3.L$1 = towerScopeLevel;
            anonymousClass3.L$2 = callInfo;
            anonymousClass3.L$3 = explicitReceiverKind;
            anonymousClass3.L$4 = towerGroupInterceptTowerGroup;
            anonymousClass3.label = 1;
            if (towerResolveManager.requestGroup(towerGroupInterceptTowerGroup, anonymousClass3) == coroutine_suspended) {
                return coroutine_suspended;
            }
            firBaseTowerResolveTask = this;
            towerGroup2 = towerGroupInterceptTowerGroup;
            towerScopeLevel2 = towerScopeLevel;
            callInfo2 = callInfo;
            explicitReceiverKind2 = explicitReceiverKind;
        } else {
            if (i != 1) {
                throw new IllegalStateException(CoroutineConstantsKt.ILLEGAL_STATE_ERROR_MESSAGE);
            }
            towerGroup2 = (TowerGroup) anonymousClass3.L$4;
            ExplicitReceiverKind explicitReceiverKind3 = (ExplicitReceiverKind) anonymousClass3.L$3;
            CallInfo callInfo3 = (CallInfo) anonymousClass3.L$2;
            TowerScopeLevel towerScopeLevel3 = (TowerScopeLevel) anonymousClass3.L$1;
            firBaseTowerResolveTask = (FirBaseTowerResolveTask) anonymousClass3.L$0;
            ResultKt.throwOnFailure(obj);
            explicitReceiverKind2 = explicitReceiverKind3;
            callInfo2 = callInfo3;
            towerScopeLevel2 = towerScopeLevel3;
        }
        ProcessResult processResultHandleLevel = firBaseTowerResolveTask.handler.handleLevel(firBaseTowerResolveTask.collector, firBaseTowerResolveTask.candidateFactory, callInfo2, explicitReceiverKind2, towerGroup2, towerScopeLevel2);
        if (firBaseTowerResolveTask.collector.isSuccess()) {
            firBaseTowerResolveTask.onSuccessfulLevel(towerGroup2);
        }
        return Boxing.boxBoolean(processResultHandleLevel == ProcessResult.SCOPE_EMPTY);
    }

    protected final ScopeTowerLevel toScopeTowerLevelForStaticWithImplicitDispatchReceiver(FirScope firScope, FirRegularClassSymbol firRegularClassSymbol, KtSourceElement ktSourceElement) {
        Intrinsics.checkNotNullParameter(firScope, "<this>");
        ExpressionReceiverValue expressionReceiverValue = null;
        if (firRegularClassSymbol != null) {
            FirResolvedQualifierBuilder firResolvedQualifierBuilder = new FirResolvedQualifierBuilder();
            FqName packageFqName = firRegularClassSymbol.getClassId().getPackageFqName();
            Intrinsics.checkNotNullExpressionValue(packageFqName, "getPackageFqName(...)");
            firResolvedQualifierBuilder.setPackageFqName(packageFqName);
            firResolvedQualifierBuilder.setRelativeClassFqName(firRegularClassSymbol.getClassId().getRelativeClassName());
            firResolvedQualifierBuilder.setSymbol(firRegularClassSymbol);
            firResolvedQualifierBuilder.setSource(ktSourceElement != null ? KtSourceElementKt.fakeElement$default(ktSourceElement, KtFakeSourceElementKind.ImplicitReceiver.INSTANCE, 0, 0, 6, null) : null);
            FirResolvedQualifier firResolvedQualifierMo6810build = firResolvedQualifierBuilder.mo6809build();
            FirResolvedQualifier firResolvedQualifier = firResolvedQualifierMo6810build;
            firResolvedQualifier.replaceTypeRef(ResolveUtilsKt.typeForQualifier(this.components, firResolvedQualifierMo6810build));
            expressionReceiverValue = new ExpressionReceiverValue(firResolvedQualifier);
        }
        return toScopeTowerLevel(firScope, null, false, false, null, expressionReceiverValue);
    }
}
