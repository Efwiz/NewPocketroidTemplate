package org.jetbrains.kotlin.fir.resolve.calls.tower;

import com.gaurav.avnc.vnc.XKeySym;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineConstantsKt;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression;
import org.jetbrains.kotlin.fir.expressions.FirResolvedQualifier;
import org.jetbrains.kotlin.fir.expressions.builder.FirExpressionStubBuilder;
import org.jetbrains.kotlin.fir.resolve.BodyResolveComponents;
import org.jetbrains.kotlin.fir.resolve.DoubleColonLHS;
import org.jetbrains.kotlin.fir.resolve.calls.CallInfo;
import org.jetbrains.kotlin.fir.resolve.calls.CallKind;
import org.jetbrains.kotlin.fir.resolve.calls.CandidateCollector;
import org.jetbrains.kotlin.fir.resolve.calls.CandidateFactory;
import org.jetbrains.kotlin.fir.resolve.calls.ClassQualifierReceiver;
import org.jetbrains.kotlin.fir.resolve.calls.ExpressionReceiverValue;
import org.jetbrains.kotlin.fir.resolve.calls.QualifierReceiver;
import org.jetbrains.kotlin.fir.resolve.calls.QualifierReceiverKt;
import org.jetbrains.kotlin.fir.resolve.calls.ReceiverValue;
import org.jetbrains.kotlin.fir.scopes.FirScope;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.builder.FirResolvedTypeRefBuilder;
import org.jetbrains.kotlin.fir.types.impl.FirImplicitBuiltinTypeRef;
import org.jetbrains.kotlin.resolve.calls.tasks.ExplicitReceiverKind;
import org.jetbrains.kotlin.resolve.descriptorUtil.AnnotationsForResolveKt;

/* JADX INFO: compiled from: FirTowerResolveTask.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000¤\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010#\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0010\u0018\u00002\u00020\u0001B-\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b¢\u0006\u0002\u0010\fJ4\u0010\r\u001a\u00020\u000e2\u0014\u0010\u000f\u001a\u0010\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00110\u0010j\u0002`\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0016H\u0082@¢\u0006\u0002\u0010\u0017JR\u0010\u0018\u001a\u00020\u000e2\n\u0010\u0019\u001a\u0006\u0012\u0002\b\u00030\u001a2\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00162\u0010\u0010\u001b\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u001a0\u001c2\f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u001e0\u001c2\u0006\u0010\u001f\u001a\u00020 H\u0082@¢\u0006\u0002\u0010!J \u0010\"\u001a\u00020\u000e2\u0006\u0010\u0013\u001a\u00020\u00142\b\u0010#\u001a\u0004\u0018\u00010$H\u0082@¢\u0006\u0002\u0010%J.\u0010&\u001a\u00020\u000e2\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010'\u001a\u00020(2\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010)\u001a\u00020*H\u0082@¢\u0006\u0002\u0010+J>\u0010,\u001a\u00020\u000e2\u0006\u0010'\u001a\u00020(2\u0006\u0010-\u001a\u00020\u001e2\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010.\u001a\u00020/2\u0006\u0010)\u001a\u00020*2\u0006\u0010\u0015\u001a\u00020\u0016H\u0082@¢\u0006\u0002\u00100J \u00101\u001a\u00020\u000e2\u0006\u0010\u0013\u001a\u00020\u00142\b\u0010#\u001a\u0004\u0018\u00010$H\u0082@¢\u0006\u0002\u0010%J.\u00102\u001a\u00020\u000e2\u0006\u00103\u001a\u00020\u001e2\u0006\u00104\u001a\u0002052\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u00106\u001a\u00020\u0016H\u0082@¢\u0006\u0002\u00107J(\u00108\u001a\u00020\u000e2\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0019\u001a\u0002092\b\b\u0002\u0010\u0015\u001a\u00020\u0016H\u0086@¢\u0006\u0002\u0010:J \u0010;\u001a\u00020\u000e2\u0006\u0010\u0013\u001a\u00020\u00142\b\b\u0002\u0010\u001f\u001a\u00020 H\u0086@¢\u0006\u0002\u0010<J\u001e\u0010=\u001a\u00020\u000e2\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010>\u001a\u00020?H\u0086@¢\u0006\u0002\u0010@J\u001e\u0010A\u001a\u00020\u000e2\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010B\u001a\u00020CH\u0086@¢\u0006\u0002\u0010D¨\u0006E"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/calls/tower/FirTowerResolveTask;", "Lorg/jetbrains/kotlin/fir/resolve/calls/tower/FirBaseTowerResolveTask;", "components", "Lorg/jetbrains/kotlin/fir/resolve/BodyResolveComponents;", "manager", "Lorg/jetbrains/kotlin/fir/resolve/calls/tower/TowerResolveManager;", "towerDataElementsForName", "Lorg/jetbrains/kotlin/fir/resolve/calls/tower/TowerDataElementsForName;", "collector", "Lorg/jetbrains/kotlin/fir/resolve/calls/CandidateCollector;", "candidateFactory", "Lorg/jetbrains/kotlin/fir/resolve/calls/CandidateFactory;", "(Lorg/jetbrains/kotlin/fir/resolve/BodyResolveComponents;Lorg/jetbrains/kotlin/fir/resolve/calls/tower/TowerResolveManager;Lorg/jetbrains/kotlin/fir/resolve/calls/tower/TowerDataElementsForName;Lorg/jetbrains/kotlin/fir/resolve/calls/CandidateCollector;Lorg/jetbrains/kotlin/fir/resolve/calls/CandidateFactory;)V", "processCandidatesWithGivenContextReceiverGroup", "", "contextReceiverGroup", "", "Lorg/jetbrains/kotlin/fir/resolve/calls/ContextReceiverValue;", "Lorg/jetbrains/kotlin/fir/declarations/ContextReceiverGroup;", CompilerOptions.INFO, "Lorg/jetbrains/kotlin/fir/resolve/calls/CallInfo;", "parentGroup", "Lorg/jetbrains/kotlin/fir/resolve/calls/tower/TowerGroup;", "(Ljava/util/List;Lorg/jetbrains/kotlin/fir/resolve/calls/CallInfo;Lorg/jetbrains/kotlin/fir/resolve/calls/tower/TowerGroup;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "processCandidatesWithGivenImplicitReceiverAsValue", "receiver", "Lorg/jetbrains/kotlin/fir/resolve/calls/ImplicitReceiverValue;", "implicitReceiverValuesWithEmptyScopes", "", "emptyScopes", "Lorg/jetbrains/kotlin/fir/scopes/FirScope;", "skipSynthetics", "", "(Lorg/jetbrains/kotlin/fir/resolve/calls/ImplicitReceiverValue;Lorg/jetbrains/kotlin/fir/resolve/calls/CallInfo;Lorg/jetbrains/kotlin/fir/resolve/calls/tower/TowerGroup;Ljava/util/Set;Ljava/util/Set;ZLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "processClassifierScope", "qualifierReceiver", "Lorg/jetbrains/kotlin/fir/resolve/calls/QualifierReceiver;", "(Lorg/jetbrains/kotlin/fir/resolve/calls/CallInfo;Lorg/jetbrains/kotlin/fir/resolve/calls/QualifierReceiver;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "processExtensionsThatHideMembers", "receiverValue", "Lorg/jetbrains/kotlin/fir/resolve/calls/ReceiverValue;", "explicitReceiverKind", "Lorg/jetbrains/kotlin/resolve/calls/tasks/ExplicitReceiverKind;", "(Lorg/jetbrains/kotlin/fir/resolve/calls/CallInfo;Lorg/jetbrains/kotlin/fir/resolve/calls/ReceiverValue;Lorg/jetbrains/kotlin/fir/resolve/calls/tower/TowerGroup;Lorg/jetbrains/kotlin/resolve/calls/tasks/ExplicitReceiverKind;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "processHideMembersLevel", "topLevelScope", "index", "", "(Lorg/jetbrains/kotlin/fir/resolve/calls/ReceiverValue;Lorg/jetbrains/kotlin/fir/scopes/FirScope;Lorg/jetbrains/kotlin/fir/resolve/calls/CallInfo;ILorg/jetbrains/kotlin/resolve/calls/tasks/ExplicitReceiverKind;Lorg/jetbrains/kotlin/fir/resolve/calls/tower/TowerGroup;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "processQualifierScopes", "processScopeForExplicitReceiver", "scope", "explicitReceiverValue", "Lorg/jetbrains/kotlin/fir/resolve/calls/ExpressionReceiverValue;", "towerGroup", "(Lorg/jetbrains/kotlin/fir/scopes/FirScope;Lorg/jetbrains/kotlin/fir/resolve/calls/ExpressionReceiverValue;Lorg/jetbrains/kotlin/fir/resolve/calls/CallInfo;Lorg/jetbrains/kotlin/fir/resolve/calls/tower/TowerGroup;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "runResolverForExpressionReceiver", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "(Lorg/jetbrains/kotlin/fir/resolve/calls/CallInfo;Lorg/jetbrains/kotlin/fir/expressions/FirExpression;Lorg/jetbrains/kotlin/fir/resolve/calls/tower/TowerGroup;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "runResolverForNoReceiver", "(Lorg/jetbrains/kotlin/fir/resolve/calls/CallInfo;ZLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "runResolverForQualifierReceiver", "resolvedQualifier", "Lorg/jetbrains/kotlin/fir/expressions/FirResolvedQualifier;", "(Lorg/jetbrains/kotlin/fir/resolve/calls/CallInfo;Lorg/jetbrains/kotlin/fir/expressions/FirResolvedQualifier;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "runResolverForSuperReceiver", "superCall", "Lorg/jetbrains/kotlin/fir/expressions/FirQualifiedAccessExpression;", "(Lorg/jetbrains/kotlin/fir/resolve/calls/CallInfo;Lorg/jetbrains/kotlin/fir/expressions/FirQualifiedAccessExpression;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class FirTowerResolveTask extends FirBaseTowerResolveTask {

    /* JADX INFO: renamed from: org.jetbrains.kotlin.fir.resolve.calls.tower.FirTowerResolveTask$processCandidatesWithGivenContextReceiverGroup$1, reason: invalid class name */
    /* JADX INFO: compiled from: FirTowerResolveTask.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    @DebugMetadata(c = "org.jetbrains.kotlin.fir.resolve.calls.tower.FirTowerResolveTask", f = "FirTowerResolveTask.kt", i = {0, 0, 0, 0, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 4, 4, 4, 4}, l = {523, 536, 555, 570, 583}, m = "processCandidatesWithGivenContextReceiverGroup", n = {"this", "contextReceiverGroup", CompilerOptions.INFO, "parentGroup", "this", "contextReceiverGroup", CompilerOptions.INFO, "parentGroup", "this_$iv", "this", "contextReceiverGroup", CompilerOptions.INFO, "parentGroup", "this_$iv", "lexical$iv", "depth$iv", "this", "contextReceiverGroup", CompilerOptions.INFO, "parentGroup", "this_$iv", "this", "contextReceiverGroup", CompilerOptions.INFO, "parentGroup"}, s = {"L$0", "L$1", "L$2", "L$3", "L$0", "L$1", "L$2", "L$3", "L$4", "L$0", "L$1", "L$2", "L$3", "L$4", "L$6", "I$1", "L$0", "L$1", "L$2", "L$3", "L$4", "L$0", "L$1", "L$2", "L$3"})
    static final class AnonymousClass1 extends ContinuationImpl {
        int I$0;
        int I$1;
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        Object L$4;
        Object L$5;
        Object L$6;
        int label;
        /* synthetic */ Object result;

        AnonymousClass1(Continuation<? super AnonymousClass1> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return FirTowerResolveTask.this.processCandidatesWithGivenContextReceiverGroup(null, null, null, this);
        }
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.fir.resolve.calls.tower.FirTowerResolveTask$processCandidatesWithGivenImplicitReceiverAsValue$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: FirTowerResolveTask.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    @DebugMetadata(c = "org.jetbrains.kotlin.fir.resolve.calls.tower.FirTowerResolveTask", f = "FirTowerResolveTask.kt", i = {0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4, 4, 4, 4, 4, 4, 4, 5, 5, 5, 5}, l = {423, 523, 536, 555, 570, 583}, m = "processCandidatesWithGivenImplicitReceiverAsValue", n = {"this", "receiver", CompilerOptions.INFO, "parentGroup", "implicitReceiverValuesWithEmptyScopes", "emptyScopes", "skipSynthetics", "this", "receiver", CompilerOptions.INFO, "parentGroup", "implicitReceiverValuesWithEmptyScopes", "emptyScopes", "this", "receiver", CompilerOptions.INFO, "parentGroup", "implicitReceiverValuesWithEmptyScopes", "emptyScopes", "this_$iv", "scope", "this", "receiver", CompilerOptions.INFO, "parentGroup", "implicitReceiverValuesWithEmptyScopes", "emptyScopes", "this_$iv", "lexical$iv", "scope", "depth$iv", "this", "receiver", CompilerOptions.INFO, "parentGroup", "implicitReceiverValuesWithEmptyScopes", "emptyScopes", "this_$iv", "this", "receiver", CompilerOptions.INFO, "parentGroup"}, s = {"L$0", "L$1", "L$2", "L$3", "L$4", "L$5", "Z$0", "L$0", "L$1", "L$2", "L$3", "L$4", "L$5", "L$0", "L$1", "L$2", "L$3", "L$4", "L$5", "L$6", "L$8", "L$0", "L$1", "L$2", "L$3", "L$4", "L$5", "L$6", "L$8", "L$9", "I$1", "L$0", "L$1", "L$2", "L$3", "L$4", "L$5", "L$6", "L$0", "L$1", "L$2", "L$3"})
    static final class C45301 extends ContinuationImpl {
        int I$0;
        int I$1;
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        Object L$4;
        Object L$5;
        Object L$6;
        Object L$7;
        Object L$8;
        Object L$9;
        boolean Z$0;
        int label;
        /* synthetic */ Object result;

        C45301(Continuation<? super C45301> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return FirTowerResolveTask.this.processCandidatesWithGivenImplicitReceiverAsValue(null, null, null, null, null, false, this);
        }
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.fir.resolve.calls.tower.FirTowerResolveTask$processClassifierScope$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: FirTowerResolveTask.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    @DebugMetadata(c = "org.jetbrains.kotlin.fir.resolve.calls.tower.FirTowerResolveTask", f = "FirTowerResolveTask.kt", i = {}, l = {523}, m = "processClassifierScope", n = {}, s = {})
    static final class C45311 extends ContinuationImpl {
        int label;
        /* synthetic */ Object result;

        C45311(Continuation<? super C45311> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return FirTowerResolveTask.this.processClassifierScope(null, null, this);
        }
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.fir.resolve.calls.tower.FirTowerResolveTask$processExtensionsThatHideMembers$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: FirTowerResolveTask.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    @DebugMetadata(c = "org.jetbrains.kotlin.fir.resolve.calls.tower.FirTowerResolveTask", f = "FirTowerResolveTask.kt", i = {0, 0, 0, 0, 0}, l = {395}, m = "processExtensionsThatHideMembers", n = {"this", CompilerOptions.INFO, "receiverValue", "parentGroup", "explicitReceiverKind"}, s = {"L$0", "L$1", "L$2", "L$3", "L$4"})
    static final class C45321 extends ContinuationImpl {
        int I$0;
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        Object L$4;
        Object L$5;
        int label;
        /* synthetic */ Object result;

        C45321(Continuation<? super C45321> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return FirTowerResolveTask.this.processExtensionsThatHideMembers(null, null, null, null, this);
        }
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.fir.resolve.calls.tower.FirTowerResolveTask$processHideMembersLevel$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: FirTowerResolveTask.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    @DebugMetadata(c = "org.jetbrains.kotlin.fir.resolve.calls.tower.FirTowerResolveTask", f = "FirTowerResolveTask.kt", i = {}, l = {523}, m = "processHideMembersLevel", n = {}, s = {})
    static final class C45331 extends ContinuationImpl {
        int label;
        /* synthetic */ Object result;

        C45331(Continuation<? super C45331> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return FirTowerResolveTask.this.processHideMembersLevel(null, null, null, 0, null, null, this);
        }
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.fir.resolve.calls.tower.FirTowerResolveTask$processQualifierScopes$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: FirTowerResolveTask.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    @DebugMetadata(c = "org.jetbrains.kotlin.fir.resolve.calls.tower.FirTowerResolveTask", f = "FirTowerResolveTask.kt", i = {}, l = {523}, m = "processQualifierScopes", n = {}, s = {})
    static final class C45341 extends ContinuationImpl {
        int label;
        /* synthetic */ Object result;

        C45341(Continuation<? super C45341> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return FirTowerResolveTask.this.processQualifierScopes(null, null, this);
        }
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.fir.resolve.calls.tower.FirTowerResolveTask$processScopeForExplicitReceiver$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: FirTowerResolveTask.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    @DebugMetadata(c = "org.jetbrains.kotlin.fir.resolve.calls.tower.FirTowerResolveTask", f = "FirTowerResolveTask.kt", i = {}, l = {523}, m = "processScopeForExplicitReceiver", n = {}, s = {})
    static final class C45351 extends ContinuationImpl {
        int label;
        /* synthetic */ Object result;

        C45351(Continuation<? super C45351> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return FirTowerResolveTask.this.processScopeForExplicitReceiver(null, null, null, null, this);
        }
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.fir.resolve.calls.tower.FirTowerResolveTask$runResolverForExpressionReceiver$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: FirTowerResolveTask.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    @DebugMetadata(c = "org.jetbrains.kotlin.fir.resolve.calls.tower.FirTowerResolveTask", f = "FirTowerResolveTask.kt", i = {0, 0, 0, 0, 1, 1, 1, 1, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 3, 3, 4, 4, 4, 4, 4, 5, 5, 5, 5}, l = {295, 523, 308, 308, 553, 566}, m = "runResolverForExpressionReceiver", n = {"this", CompilerOptions.INFO, "parentGroup", "explicitReceiverValue", "this", CompilerOptions.INFO, "parentGroup", "explicitReceiverValue", "this", CompilerOptions.INFO, "parentGroup", "explicitReceiverValue", "this_$iv", "this", CompilerOptions.INFO, "parentGroup", "explicitReceiverValue", "this_$iv", "lexical$iv", "depth$iv", "this", CompilerOptions.INFO, "parentGroup", "explicitReceiverValue", "this_$iv", "this", CompilerOptions.INFO, "parentGroup", "explicitReceiverValue"}, s = {"L$0", "L$1", "L$2", "L$3", "L$0", "L$1", "L$2", "L$3", "L$0", "L$1", "L$2", "L$3", "L$4", "L$0", "L$1", "L$2", "L$3", "L$4", "L$6", "I$1", "L$0", "L$1", "L$2", "L$3", "L$4", "L$0", "L$1", "L$2", "L$3"})
    static final class C45361 extends ContinuationImpl {
        int I$0;
        int I$1;
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        Object L$4;
        Object L$5;
        Object L$6;
        int label;
        /* synthetic */ Object result;

        C45361(Continuation<? super C45361> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return FirTowerResolveTask.this.runResolverForExpressionReceiver(null, null, null, this);
        }
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.fir.resolve.calls.tower.FirTowerResolveTask$runResolverForNoReceiver$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: FirTowerResolveTask.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    @DebugMetadata(c = "org.jetbrains.kotlin.fir.resolve.calls.tower.FirTowerResolveTask", f = "FirTowerResolveTask.kt", i = {0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 3, 3, 3}, l = {531, 550, 355, 365}, m = "runResolverForNoReceiver", n = {"this", CompilerOptions.INFO, "emptyScopes", "implicitReceiverValuesWithEmptyScopes", "parentGroup$iv", "scope", "skipSynthetics", "this", CompilerOptions.INFO, "emptyScopes", "implicitReceiverValuesWithEmptyScopes", "parentGroup$iv", "lexical$iv", "scope", "skipSynthetics", "depth$iv", "this", CompilerOptions.INFO, "emptyScopes", "implicitReceiverValuesWithEmptyScopes", "parentGroup$iv", "skipSynthetics", "this", CompilerOptions.INFO, "parentGroup$iv"}, s = {"L$0", "L$1", "L$2", "L$3", "L$4", "L$6", "Z$0", "L$0", "L$1", "L$2", "L$3", "L$4", "L$6", "L$7", "Z$0", "I$1", "L$0", "L$1", "L$2", "L$3", "L$4", "Z$0", "L$0", "L$1", "L$2"})
    static final class C45371 extends ContinuationImpl {
        int I$0;
        int I$1;
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        Object L$4;
        Object L$5;
        Object L$6;
        Object L$7;
        boolean Z$0;
        int label;
        /* synthetic */ Object result;

        C45371(Continuation<? super C45371> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return FirTowerResolveTask.this.runResolverForNoReceiver(null, false, this);
        }
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.fir.resolve.calls.tower.FirTowerResolveTask$runResolverForQualifierReceiver$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: FirTowerResolveTask.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    @DebugMetadata(c = "org.jetbrains.kotlin.fir.resolve.calls.tower.FirTowerResolveTask", f = "FirTowerResolveTask.kt", i = {0, 0, 0, 0, 1, 1, 1, 2, 2, 2, 2}, l = {XKeySym.XK_ae, XKeySym.XK_ccedilla, 245, 250}, m = "runResolverForQualifierReceiver", n = {"this", CompilerOptions.INFO, "resolvedQualifier", "qualifierReceiver", "this", CompilerOptions.INFO, "resolvedQualifier", "this", CompilerOptions.INFO, "resolvedQualifier", "typeRef"}, s = {"L$0", "L$1", "L$2", "L$3", "L$0", "L$1", "L$2", "L$0", "L$1", "L$2", "L$3"})
    static final class C45381 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        int label;
        /* synthetic */ Object result;

        C45381(Continuation<? super C45381> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return FirTowerResolveTask.this.runResolverForQualifierReceiver(null, null, this);
        }
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.fir.resolve.calls.tower.FirTowerResolveTask$runResolverForSuperReceiver$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: FirTowerResolveTask.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    @DebugMetadata(c = "org.jetbrains.kotlin.fir.resolve.calls.tower.FirTowerResolveTask", f = "FirTowerResolveTask.kt", i = {}, l = {523}, m = "runResolverForSuperReceiver", n = {}, s = {})
    static final class C45391 extends ContinuationImpl {
        int label;
        /* synthetic */ Object result;

        C45391(Continuation<? super C45391> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return FirTowerResolveTask.this.runResolverForSuperReceiver(null, null, this);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FirTowerResolveTask(BodyResolveComponents components, TowerResolveManager manager, TowerDataElementsForName towerDataElementsForName, CandidateCollector collector, CandidateFactory candidateFactory) {
        super(components, manager, towerDataElementsForName, collector, candidateFactory);
        Intrinsics.checkNotNullParameter(components, "components");
        Intrinsics.checkNotNullParameter(manager, "manager");
        Intrinsics.checkNotNullParameter(towerDataElementsForName, "towerDataElementsForName");
        Intrinsics.checkNotNullParameter(collector, "collector");
        Intrinsics.checkNotNullParameter(candidateFactory, "candidateFactory");
    }

    /* JADX WARN: Code duplicated, block: B:30:0x00b8  */
    /* JADX WARN: Code duplicated, block: B:36:0x00db  */
    /* JADX WARN: Code duplicated, block: B:37:0x00e0  */
    /* JADX WARN: Code duplicated, block: B:40:0x011b A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:41:0x011c  */
    /* JADX WARN: Code duplicated, block: B:45:0x0122  */
    /* JADX WARN: Code duplicated, block: B:47:0x013a A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:7:0x0014  */
    public final Object runResolverForQualifierReceiver(CallInfo callInfo, FirResolvedQualifier firResolvedQualifier, Continuation<? super Unit> continuation) throws Throwable {
        C45381 c45381;
        QualifierReceiver qualifierReceiverCreateQualifierReceiver;
        FirTowerResolveTask firTowerResolveTask;
        CallInfo callInfo2;
        FirTypeRef typeRef;
        FirExpression explicitReceiver;
        KtSourceElement source;
        FirExpression firExpressionMo6810build;
        CallInfo callInfoReplaceExplicitReceiver;
        TowerGroup qualifierValue;
        FirTowerResolveTask firTowerResolveTask2;
        TowerGroup qualifierValue2;
        if (continuation instanceof C45381) {
            c45381 = (C45381) continuation;
            if ((c45381.label & Integer.MIN_VALUE) != 0) {
                c45381.label -= Integer.MIN_VALUE;
            } else {
                c45381 = new C45381(continuation);
            }
        } else {
            c45381 = new C45381(continuation);
        }
        Object obj = c45381.result;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = c45381.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            qualifierReceiverCreateQualifierReceiver = QualifierReceiverKt.createQualifierReceiver(firResolvedQualifier, getSession(), getComponents().getScopeSession());
            c45381.L$0 = this;
            c45381.L$1 = callInfo;
            c45381.L$2 = firResolvedQualifier;
            c45381.L$3 = qualifierReceiverCreateQualifierReceiver;
            c45381.label = 1;
            if (processQualifierScopes(callInfo, qualifierReceiverCreateQualifierReceiver, c45381) == coroutine_suspended) {
                return coroutine_suspended;
            }
            firTowerResolveTask = this;
        } else {
            if (i == 1) {
                QualifierReceiver qualifierReceiver = (QualifierReceiver) c45381.L$3;
                firResolvedQualifier = (FirResolvedQualifier) c45381.L$2;
                CallInfo callInfo3 = (CallInfo) c45381.L$1;
                firTowerResolveTask = (FirTowerResolveTask) c45381.L$0;
                ResultKt.throwOnFailure(obj);
                qualifierReceiverCreateQualifierReceiver = qualifierReceiver;
                callInfo = callInfo3;
            } else {
                if (i == 2) {
                    FirResolvedQualifier firResolvedQualifier2 = (FirResolvedQualifier) c45381.L$2;
                    CallInfo callInfo4 = (CallInfo) c45381.L$1;
                    FirTowerResolveTask firTowerResolveTask3 = (FirTowerResolveTask) c45381.L$0;
                    ResultKt.throwOnFailure(obj);
                    firTowerResolveTask = firTowerResolveTask3;
                    callInfo2 = callInfo4;
                    firResolvedQualifier = firResolvedQualifier2;
                    if (firResolvedQualifier.getSymbol() != null) {
                        typeRef = firResolvedQualifier.getTypeRef();
                        if (Intrinsics.areEqual(callInfo2.getCallKind(), CallKind.CallableReference.INSTANCE) && (callInfo2.getLhs() instanceof DoubleColonLHS.Type)) {
                            FirExpressionStubBuilder firExpressionStubBuilder = new FirExpressionStubBuilder();
                            explicitReceiver = callInfo2.getExplicitReceiver();
                            if (explicitReceiver != null) {
                                source = explicitReceiver.getSource();
                            } else {
                                source = null;
                            }
                            firExpressionStubBuilder.setSource(source);
                            FirResolvedTypeRefBuilder firResolvedTypeRefBuilder = new FirResolvedTypeRefBuilder();
                            firResolvedTypeRefBuilder.setType(callInfo2.getLhs().getType());
                            firExpressionStubBuilder.setTypeRef(firResolvedTypeRefBuilder.mo6810build());
                            firExpressionMo6810build = firExpressionStubBuilder.mo6810build();
                            callInfoReplaceExplicitReceiver = callInfo2.replaceExplicitReceiver(firExpressionMo6810build);
                            qualifierValue = TowerGroup.INSTANCE.getQualifierValue();
                            c45381.L$0 = firTowerResolveTask;
                            c45381.L$1 = callInfo2;
                            c45381.L$2 = firResolvedQualifier;
                            c45381.L$3 = typeRef;
                            c45381.label = 3;
                            if (firTowerResolveTask.runResolverForExpressionReceiver(callInfoReplaceExplicitReceiver, firExpressionMo6810build, qualifierValue, c45381) == coroutine_suspended) {
                                return coroutine_suspended;
                            }
                            firTowerResolveTask2 = firTowerResolveTask;
                            firTowerResolveTask = firTowerResolveTask2;
                        }
                        if (!(typeRef instanceof FirImplicitBuiltinTypeRef)) {
                            qualifierValue2 = TowerGroup.INSTANCE.getQualifierValue();
                            c45381.L$0 = null;
                            c45381.L$1 = null;
                            c45381.L$2 = null;
                            c45381.L$3 = null;
                            c45381.label = 4;
                            if (firTowerResolveTask.runResolverForExpressionReceiver(callInfo2, firResolvedQualifier, qualifierValue2, c45381) == coroutine_suspended) {
                                return coroutine_suspended;
                            }
                        }
                    }
                    return Unit.INSTANCE;
                }
                if (i == 3) {
                    typeRef = (FirTypeRef) c45381.L$3;
                    firResolvedQualifier = (FirResolvedQualifier) c45381.L$2;
                    callInfo2 = (CallInfo) c45381.L$1;
                    firTowerResolveTask2 = (FirTowerResolveTask) c45381.L$0;
                    ResultKt.throwOnFailure(obj);
                    firTowerResolveTask = firTowerResolveTask2;
                    if (!(typeRef instanceof FirImplicitBuiltinTypeRef)) {
                        qualifierValue2 = TowerGroup.INSTANCE.getQualifierValue();
                        c45381.L$0 = null;
                        c45381.L$1 = null;
                        c45381.L$2 = null;
                        c45381.L$3 = null;
                        c45381.label = 4;
                        if (firTowerResolveTask.runResolverForExpressionReceiver(callInfo2, firResolvedQualifier, qualifierValue2, c45381) == coroutine_suspended) {
                            return coroutine_suspended;
                        }
                    }
                    return Unit.INSTANCE;
                }
                if (i != 4) {
                    throw new IllegalStateException(CoroutineConstantsKt.ILLEGAL_STATE_ERROR_MESSAGE);
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }
        c45381.L$0 = firTowerResolveTask;
        c45381.L$1 = callInfo;
        c45381.L$2 = firResolvedQualifier;
        c45381.L$3 = null;
        c45381.label = 2;
        if (firTowerResolveTask.processClassifierScope(callInfo, qualifierReceiverCreateQualifierReceiver, c45381) == coroutine_suspended) {
            return coroutine_suspended;
        }
        callInfo2 = callInfo;
        if (firResolvedQualifier.getSymbol() != null) {
            typeRef = firResolvedQualifier.getTypeRef();
            if (Intrinsics.areEqual(callInfo2.getCallKind(), CallKind.CallableReference.INSTANCE)) {
                FirExpressionStubBuilder firExpressionStubBuilder2 = new FirExpressionStubBuilder();
                explicitReceiver = callInfo2.getExplicitReceiver();
                if (explicitReceiver != null) {
                    source = explicitReceiver.getSource();
                } else {
                    source = null;
                }
                firExpressionStubBuilder2.setSource(source);
                FirResolvedTypeRefBuilder firResolvedTypeRefBuilder2 = new FirResolvedTypeRefBuilder();
                firResolvedTypeRefBuilder2.setType(callInfo2.getLhs().getType());
                firExpressionStubBuilder2.setTypeRef(firResolvedTypeRefBuilder2.mo6810build());
                firExpressionMo6810build = firExpressionStubBuilder2.mo6810build();
                callInfoReplaceExplicitReceiver = callInfo2.replaceExplicitReceiver(firExpressionMo6810build);
                qualifierValue = TowerGroup.INSTANCE.getQualifierValue();
                c45381.L$0 = firTowerResolveTask;
                c45381.L$1 = callInfo2;
                c45381.L$2 = firResolvedQualifier;
                c45381.L$3 = typeRef;
                c45381.label = 3;
                if (firTowerResolveTask.runResolverForExpressionReceiver(callInfoReplaceExplicitReceiver, firExpressionMo6810build, qualifierValue, c45381) == coroutine_suspended) {
                    return coroutine_suspended;
                }
                firTowerResolveTask2 = firTowerResolveTask;
                firTowerResolveTask = firTowerResolveTask2;
            }
            if (!(typeRef instanceof FirImplicitBuiltinTypeRef)) {
                qualifierValue2 = TowerGroup.INSTANCE.getQualifierValue();
                c45381.L$0 = null;
                c45381.L$1 = null;
                c45381.L$2 = null;
                c45381.L$3 = null;
                c45381.label = 4;
                if (firTowerResolveTask.runResolverForExpressionReceiver(callInfo2, firResolvedQualifier, qualifierValue2, c45381) == coroutine_suspended) {
                    return coroutine_suspended;
                }
                return Unit.INSTANCE;
            }
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:7:0x001a  */
    public final Object processQualifierScopes(CallInfo callInfo, QualifierReceiver qualifierReceiver, Continuation<? super Unit> continuation) throws Throwable {
        C45341 c45341;
        if (continuation instanceof C45341) {
            c45341 = (C45341) continuation;
            if ((c45341.label & Integer.MIN_VALUE) != 0) {
                c45341.label -= Integer.MIN_VALUE;
            } else {
                c45341 = new C45341(continuation);
            }
        } else {
            c45341 = new C45341(continuation);
        }
        C45341 c45342 = c45341;
        Object objProcessLevel = c45342.result;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = c45342.label;
        if (i == 0) {
            ResultKt.throwOnFailure(objProcessLevel);
            if (qualifierReceiver == null) {
                return Unit.INSTANCE;
            }
            FirScope firScopeCallableScope = qualifierReceiver.callableScope();
            if (firScopeCallableScope == null) {
                return Unit.INSTANCE;
            }
            FirTowerResolveTask firTowerResolveTask = this;
            ScopeTowerLevel scopeTowerLevel$default = FirBaseTowerResolveTask.toScopeTowerLevel$default(firTowerResolveTask, firScopeCallableScope, null, false, false, null, qualifierReceiver instanceof ClassQualifierReceiver ? new ExpressionReceiverValue(((ClassQualifierReceiver) qualifierReceiver).getExplicitReceiver()) : null, 11, null);
            TowerGroup qualifier = TowerGroup.INSTANCE.getQualifier();
            ExplicitReceiverKind explicitReceiverKind = ExplicitReceiverKind.NO_EXPLICIT_RECEIVER;
            c45342.label = 1;
            objProcessLevel = firTowerResolveTask.processLevel(scopeTowerLevel$default, callInfo, qualifier, explicitReceiverKind, c45342);
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

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:7:0x001a  */
    public final Object processClassifierScope(CallInfo callInfo, QualifierReceiver qualifierReceiver, Continuation<? super Unit> continuation) throws Throwable {
        C45311 c45311;
        if (continuation instanceof C45311) {
            c45311 = (C45311) continuation;
            if ((c45311.label & Integer.MIN_VALUE) != 0) {
                c45311.label -= Integer.MIN_VALUE;
            } else {
                c45311 = new C45311(continuation);
            }
        } else {
            c45311 = new C45311(continuation);
        }
        C45311 c45312 = c45311;
        Object objProcessLevel = c45312.result;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = c45312.label;
        if (i == 0) {
            ResultKt.throwOnFailure(objProcessLevel);
            if (qualifierReceiver == null) {
                return Unit.INSTANCE;
            }
            if (!Intrinsics.areEqual(callInfo.getCallKind(), CallKind.CallableReference.INSTANCE) && (qualifierReceiver instanceof ClassQualifierReceiver)) {
                ClassQualifierReceiver classQualifierReceiver = (ClassQualifierReceiver) qualifierReceiver;
                if (!Intrinsics.areEqual(classQualifierReceiver.getClassSymbol(), classQualifierReceiver.getOriginalSymbol())) {
                    return Unit.INSTANCE;
                }
            }
            FirScope firScopeClassifierScope = qualifierReceiver.classifierScope();
            if (firScopeClassifierScope == null) {
                return Unit.INSTANCE;
            }
            FirTowerResolveTask firTowerResolveTask = this;
            ScopeTowerLevel scopeTowerLevel$default = FirBaseTowerResolveTask.toScopeTowerLevel$default(firTowerResolveTask, firScopeClassifierScope, null, false, false, null, null, 27, null);
            TowerGroup classifier = TowerGroup.INSTANCE.getClassifier();
            ExplicitReceiverKind explicitReceiverKind = ExplicitReceiverKind.NO_EXPLICIT_RECEIVER;
            c45312.label = 1;
            objProcessLevel = firTowerResolveTask.processLevel(scopeTowerLevel$default, callInfo, classifier, explicitReceiverKind, c45312);
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

    /* JADX WARN: Code duplicated, block: B:34:0x01b5  */
    /* JADX WARN: Code duplicated, block: B:36:0x01c8 A[ADDED_TO_REGION] */
    /* JADX WARN: Code duplicated, block: B:49:0x022f  */
    /* JADX WARN: Code duplicated, block: B:51:0x0275 A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:52:0x0276  */
    /* JADX WARN: Code duplicated, block: B:54:0x0284  */
    /* JADX WARN: Code duplicated, block: B:7:0x0018  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:52:0x0276 -> B:53:0x027d). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:54:0x0284 -> B:55:0x028b). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:60:0x02ef -> B:62:0x02f2). Please report as a decompilation issue!!! */
    /*  JADX ERROR: JadxOverflowException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxOverflowException: Regions stack size limit reached
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
        	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
        	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
        */
    public final java.lang.Object runResolverForExpressionReceiver(org.jetbrains.kotlin.fir.resolve.calls.CallInfo r26, org.jetbrains.kotlin.fir.expressions.FirExpression r27, org.jetbrains.kotlin.fir.resolve.calls.tower.TowerGroup r28, kotlin.coroutines.Continuation<? super kotlin.Unit> r29) {
        /*
            Method dump skipped, instruction units count: 782
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.jetbrains.kotlin.fir.resolve.calls.tower.FirTowerResolveTask.runResolverForExpressionReceiver(org.jetbrains.kotlin.fir.resolve.calls.CallInfo, org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.resolve.calls.tower.TowerGroup, kotlin.coroutines.Continuation):java.lang.Object");
    }

    public static /* synthetic */ Object runResolverForExpressionReceiver$default(FirTowerResolveTask firTowerResolveTask, CallInfo callInfo, FirExpression firExpression, TowerGroup towerGroup, Continuation continuation, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: runResolverForExpressionReceiver");
        }
        if ((i & 4) != 0) {
            towerGroup = TowerGroup.INSTANCE.getEmptyRoot();
        }
        return firTowerResolveTask.runResolverForExpressionReceiver(callInfo, firExpression, towerGroup, continuation);
    }

    public static /* synthetic */ Object runResolverForNoReceiver$default(FirTowerResolveTask firTowerResolveTask, CallInfo callInfo, boolean z, Continuation continuation, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: runResolverForNoReceiver");
        }
        if ((i & 2) != 0) {
            z = false;
        }
        return firTowerResolveTask.runResolverForNoReceiver(callInfo, z, continuation);
    }

    /* JADX WARN: Code duplicated, block: B:38:0x01b7  */
    /* JADX WARN: Code duplicated, block: B:40:0x01ca A[ADDED_TO_REGION] */
    /* JADX WARN: Code duplicated, block: B:59:0x0263  */
    /* JADX WARN: Code duplicated, block: B:63:0x027a  */
    /* JADX WARN: Code duplicated, block: B:65:0x02a8 A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:7:0x0018  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:30:0x0167 -> B:31:0x0175). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:62:0x0278 -> B:66:0x02a9). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:64:0x02a6 -> B:66:0x02a9). Please report as a decompilation issue!!! */
    /*  JADX ERROR: JadxOverflowException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxOverflowException: Regions stack size limit reached
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
        	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
        	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
        */
    public final java.lang.Object runResolverForNoReceiver(org.jetbrains.kotlin.fir.resolve.calls.CallInfo r30, boolean r31, kotlin.coroutines.Continuation<? super kotlin.Unit> r32) {
        /*
            Method dump skipped, instruction units count: 765
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.jetbrains.kotlin.fir.resolve.calls.tower.FirTowerResolveTask.runResolverForNoReceiver(org.jetbrains.kotlin.fir.resolve.calls.CallInfo, boolean, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARN: Code duplicated, block: B:7:0x0018  */
    public final Object runResolverForSuperReceiver(CallInfo callInfo, FirQualifiedAccessExpression firQualifiedAccessExpression, Continuation<? super Unit> continuation) throws Throwable {
        C45391 c45391;
        if (continuation instanceof C45391) {
            c45391 = (C45391) continuation;
            if ((c45391.label & Integer.MIN_VALUE) != 0) {
                c45391.label -= Integer.MIN_VALUE;
            } else {
                c45391 = new C45391(continuation);
            }
        } else {
            c45391 = new C45391(continuation);
        }
        C45391 c45392 = c45391;
        Object objProcessLevel = c45392.result;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = c45392.label;
        if (i == 0) {
            ResultKt.throwOnFailure(objProcessLevel);
            FirTowerResolveTask firTowerResolveTask = this;
            MemberScopeTowerLevel memberScopeTowerLevel$default = FirBaseTowerResolveTask.toMemberScopeTowerLevel$default(firTowerResolveTask, new ExpressionReceiverValue(firQualifiedAccessExpression), null, null, false, 7, null);
            TowerGroup member = TowerGroup.INSTANCE.getMember();
            ExplicitReceiverKind explicitReceiverKind = ExplicitReceiverKind.DISPATCH_RECEIVER;
            c45392.label = 1;
            objProcessLevel = firTowerResolveTask.processLevel(memberScopeTowerLevel$default, callInfo, member, explicitReceiverKind, c45392);
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

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:7:0x0018  */
    public final Object processExtensionsThatHideMembers(CallInfo callInfo, ReceiverValue receiverValue, TowerGroup towerGroup, ExplicitReceiverKind explicitReceiverKind, Continuation<? super Unit> continuation) throws Throwable {
        C45321 c45321;
        FirTowerResolveTask firTowerResolveTask;
        ExplicitReceiverKind explicitReceiverKind2;
        Iterator it;
        FirTowerResolveTask firTowerResolveTask2;
        int i;
        CallInfo callInfo2;
        ReceiverValue receiverValue2;
        TowerGroup towerGroup2;
        if (continuation instanceof C45321) {
            c45321 = (C45321) continuation;
            if ((c45321.label & Integer.MIN_VALUE) != 0) {
                c45321.label -= Integer.MIN_VALUE;
                firTowerResolveTask = this;
            } else {
                firTowerResolveTask = this;
                c45321 = firTowerResolveTask.new C45321(continuation);
            }
        } else {
            firTowerResolveTask = this;
            c45321 = firTowerResolveTask.new C45321(continuation);
        }
        Object obj = c45321.result;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i2 = c45321.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            if (!Intrinsics.areEqual(callInfo.getCallKind(), CallKind.Function.INSTANCE) || !AnnotationsForResolveKt.getHIDES_MEMBERS_NAME_LIST().contains(callInfo.getName())) {
                return Unit.INSTANCE;
            }
            explicitReceiverKind2 = explicitReceiverKind;
            it = CollectionsKt.asReversed(getComponents().getFileImportsScope()).iterator();
            firTowerResolveTask2 = firTowerResolveTask;
            i = 0;
            callInfo2 = callInfo;
            receiverValue2 = receiverValue;
            towerGroup2 = towerGroup;
        } else {
            if (i2 != 1) {
                throw new IllegalStateException(CoroutineConstantsKt.ILLEGAL_STATE_ERROR_MESSAGE);
            }
            int i3 = c45321.I$0;
            Iterator it2 = (Iterator) c45321.L$5;
            ExplicitReceiverKind explicitReceiverKind3 = (ExplicitReceiverKind) c45321.L$4;
            TowerGroup towerGroup3 = (TowerGroup) c45321.L$3;
            ReceiverValue receiverValue3 = (ReceiverValue) c45321.L$2;
            CallInfo callInfo3 = (CallInfo) c45321.L$1;
            FirTowerResolveTask firTowerResolveTask3 = (FirTowerResolveTask) c45321.L$0;
            ResultKt.throwOnFailure(obj);
            it = it2;
            explicitReceiverKind2 = explicitReceiverKind3;
            receiverValue2 = receiverValue3;
            callInfo2 = callInfo3;
            firTowerResolveTask2 = firTowerResolveTask3;
            i = i3;
            towerGroup2 = towerGroup3;
        }
        while (it.hasNext()) {
            int i4 = i + 1;
            FirScope firScope = (FirScope) it.next();
            c45321.L$0 = firTowerResolveTask2;
            c45321.L$1 = callInfo2;
            c45321.L$2 = receiverValue2;
            c45321.L$3 = towerGroup2;
            c45321.L$4 = explicitReceiverKind2;
            c45321.L$5 = it;
            c45321.I$0 = i4;
            c45321.label = 1;
            FirTowerResolveTask firTowerResolveTask4 = firTowerResolveTask2;
            Iterator it3 = it;
            if (firTowerResolveTask2.processHideMembersLevel(receiverValue2, firScope, callInfo2, i, explicitReceiverKind2, towerGroup2, c45321) == coroutine_suspended) {
                return coroutine_suspended;
            }
            i = i4;
            firTowerResolveTask2 = firTowerResolveTask4;
            it = it3;
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:7:0x0018  */
    public final Object processScopeForExplicitReceiver(FirScope firScope, ExpressionReceiverValue expressionReceiverValue, CallInfo callInfo, TowerGroup towerGroup, Continuation<? super Unit> continuation) throws Throwable {
        C45351 c45351;
        if (continuation instanceof C45351) {
            c45351 = (C45351) continuation;
            if ((c45351.label & Integer.MIN_VALUE) != 0) {
                c45351.label -= Integer.MIN_VALUE;
            } else {
                c45351 = new C45351(continuation);
            }
        } else {
            c45351 = new C45351(continuation);
        }
        C45351 c45352 = c45351;
        Object objProcessLevel = c45352.result;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = c45352.label;
        if (i == 0) {
            ResultKt.throwOnFailure(objProcessLevel);
            FirTowerResolveTask firTowerResolveTask = this;
            ScopeTowerLevel scopeTowerLevel$default = FirBaseTowerResolveTask.toScopeTowerLevel$default(firTowerResolveTask, firScope, expressionReceiverValue, false, false, null, null, 30, null);
            ExplicitReceiverKind explicitReceiverKind = ExplicitReceiverKind.EXTENSION_RECEIVER;
            c45352.label = 1;
            objProcessLevel = firTowerResolveTask.processLevel(scopeTowerLevel$default, callInfo, towerGroup, explicitReceiverKind, c45352);
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

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:32:0x01b3  */
    /* JADX WARN: Code duplicated, block: B:36:0x0214 A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:37:0x0215  */
    /* JADX WARN: Code duplicated, block: B:40:0x0222  */
    /* JADX WARN: Code duplicated, block: B:7:0x001a  */
    /* JADX WARN: Code duplicated, block: B:81:0x01cf A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:84:? A[LOOP:0: B:30:0x01ad->B:84:?, LOOP_END, SYNTHETIC] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:37:0x0215 -> B:38:0x021a). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:68:0x0332 -> B:69:0x033c). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:75:0x03be -> B:77:0x03c1). Please report as a decompilation issue!!! */
    /*  JADX ERROR: JadxOverflowException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxOverflowException: Regions stack size limit reached
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
        	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
        	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
        */
    public final java.lang.Object processCandidatesWithGivenImplicitReceiverAsValue(org.jetbrains.kotlin.fir.resolve.calls.ImplicitReceiverValue<?> r28, org.jetbrains.kotlin.fir.resolve.calls.CallInfo r29, org.jetbrains.kotlin.fir.resolve.calls.tower.TowerGroup r30, java.util.Set<org.jetbrains.kotlin.fir.resolve.calls.ImplicitReceiverValue<?>> r31, java.util.Set<org.jetbrains.kotlin.fir.scopes.FirScope> r32, boolean r33, kotlin.coroutines.Continuation<? super kotlin.Unit> r34) {
        /*
            Method dump skipped, instruction units count: 988
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.jetbrains.kotlin.fir.resolve.calls.tower.FirTowerResolveTask.processCandidatesWithGivenImplicitReceiverAsValue(org.jetbrains.kotlin.fir.resolve.calls.ImplicitReceiverValue, org.jetbrains.kotlin.fir.resolve.calls.CallInfo, org.jetbrains.kotlin.fir.resolve.calls.tower.TowerGroup, java.util.Set, java.util.Set, boolean, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:36:0x01a1  */
    /* JADX WARN: Code duplicated, block: B:38:0x01b3 A[ADDED_TO_REGION] */
    /* JADX WARN: Code duplicated, block: B:52:0x0227  */
    /* JADX WARN: Code duplicated, block: B:54:0x026c A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:55:0x026d  */
    /* JADX WARN: Code duplicated, block: B:57:0x0285  */
    /* JADX WARN: Code duplicated, block: B:7:0x0018  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:30:0x017f -> B:32:0x0182). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:55:0x026d -> B:56:0x027a). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:57:0x0285 -> B:34:0x019b). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:62:0x02f2 -> B:64:0x02f5). Please report as a decompilation issue!!! */
    /*  JADX ERROR: JadxOverflowException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxOverflowException: Regions stack size limit reached
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
        	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
        	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
        */
    public final java.lang.Object processCandidatesWithGivenContextReceiverGroup(java.util.List<? extends org.jetbrains.kotlin.fir.resolve.calls.ContextReceiverValue<?>> r27, org.jetbrains.kotlin.fir.resolve.calls.CallInfo r28, org.jetbrains.kotlin.fir.resolve.calls.tower.TowerGroup r29, kotlin.coroutines.Continuation<? super kotlin.Unit> r30) {
        /*
            Method dump skipped, instruction units count: 766
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.jetbrains.kotlin.fir.resolve.calls.tower.FirTowerResolveTask.processCandidatesWithGivenContextReceiverGroup(java.util.List, org.jetbrains.kotlin.fir.resolve.calls.CallInfo, org.jetbrains.kotlin.fir.resolve.calls.tower.TowerGroup, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:7:0x0018  */
    public final Object processHideMembersLevel(ReceiverValue receiverValue, FirScope firScope, CallInfo callInfo, int i, ExplicitReceiverKind explicitReceiverKind, TowerGroup towerGroup, Continuation<? super Unit> continuation) throws Throwable {
        C45331 c45331;
        if (continuation instanceof C45331) {
            c45331 = (C45331) continuation;
            if ((c45331.label & Integer.MIN_VALUE) != 0) {
                c45331.label -= Integer.MIN_VALUE;
            } else {
                c45331 = new C45331(continuation);
            }
        } else {
            c45331 = new C45331(continuation);
        }
        C45331 c45332 = c45331;
        Object objProcessLevel = c45332.result;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i2 = c45332.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(objProcessLevel);
            FirTowerResolveTask firTowerResolveTask = this;
            ScopeTowerLevel scopeTowerLevel$default = FirBaseTowerResolveTask.toScopeTowerLevel$default(firTowerResolveTask, firScope, receiverValue, true, false, null, null, 28, null);
            TowerGroup towerGroupTopPrioritized = towerGroup.TopPrioritized(i);
            c45332.label = 1;
            objProcessLevel = firTowerResolveTask.processLevel(scopeTowerLevel$default, callInfo, towerGroupTopPrioritized, explicitReceiverKind, c45332);
            if (objProcessLevel == coroutine_suspended) {
                return coroutine_suspended;
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException(CoroutineConstantsKt.ILLEGAL_STATE_ERROR_MESSAGE);
            }
            ResultKt.throwOnFailure(objProcessLevel);
        }
        ((Boolean) objProcessLevel).booleanValue();
        return Unit.INSTANCE;
    }
}
