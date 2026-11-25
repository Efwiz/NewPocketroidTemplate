package org.jetbrains.kotlin.analysis.project.structure;

import java.util.List;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.RestrictedSuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequenceScope;
import kotlin.sequences.SequencesKt;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineConstantsKt;
import org.jetbrains.kotlin.name.SpecialNames;
import org.jetbrains.kotlin.utils.SortUtilsKt;

/* JADX INFO: compiled from: ktModuleUtils.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\u001a\u001a\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u00012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001\u001a\u0010\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00020\u0005*\u00020\u0002\u001a\u001f\u0010\u0006\u001a\b\u0012\u0004\u0012\u0002H\u00070\u0005\"\n\b\u0000\u0010\u0007\u0018\u0001*\u00020\u0002*\u00020\u0002H\u0086\b\u001a\u001f\u0010\b\u001a\b\u0012\u0004\u0012\u0002H\u00070\u0005\"\n\b\u0000\u0010\u0007\u0018\u0001*\u00020\u0002*\u00020\u0002H\u0086\b\u001a\u001f\u0010\t\u001a\b\u0012\u0004\u0012\u0002H\u00070\u0005\"\n\b\u0000\u0010\u0007\u0018\u0001*\u00020\u0002*\u00020\u0002H\u0086\b\u001a\u001f\u0010\n\u001a\b\u0012\u0004\u0012\u0002H\u00070\u0005\"\n\b\u0000\u0010\u0007\u0018\u0001*\u00020\u0002*\u00020\u0002H\u0086\b¨\u0006\u000b"}, d2 = {"computeTransitiveDependsOnDependencies", "", "Lorg/jetbrains/kotlin/analysis/project/structure/KtModule;", "directDependsOnDependencies", "allDirectDependencies", "Lkotlin/sequences/Sequence;", "allDirectDependenciesOfType", "M", "directDependsOnDependenciesOfType", "directFriendDependenciesOfType", "directRegularDependenciesOfType", "project-structure"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class KtModuleUtilsKt {
    public static final /* synthetic */ <M extends KtModule> Sequence<M> directRegularDependenciesOfType(KtModule ktModule) {
        Intrinsics.checkNotNullParameter(ktModule, "<this>");
        Sequence sequenceAsSequence = CollectionsKt.asSequence(ktModule.getDirectRegularDependencies());
        Intrinsics.needClassReification();
        Sequence<M> sequenceFilter = SequencesKt.filter(sequenceAsSequence, new Function1<Object, Boolean>() { // from class: org.jetbrains.kotlin.analysis.project.structure.KtModuleUtilsKt$directRegularDependenciesOfType$$inlined$filterIsInstance$1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(Object obj) {
                Intrinsics.reifiedOperationMarker(3, "M");
                return Boolean.valueOf(obj instanceof Object);
            }
        });
        Intrinsics.checkNotNull(sequenceFilter, "null cannot be cast to non-null type kotlin.sequences.Sequence<R of kotlin.sequences.SequencesKt___SequencesKt.filterIsInstance>");
        return sequenceFilter;
    }

    public static final /* synthetic */ <M extends KtModule> Sequence<M> directFriendDependenciesOfType(KtModule ktModule) {
        Intrinsics.checkNotNullParameter(ktModule, "<this>");
        Sequence sequenceAsSequence = CollectionsKt.asSequence(ktModule.getDirectFriendDependencies());
        Intrinsics.needClassReification();
        Sequence<M> sequenceFilter = SequencesKt.filter(sequenceAsSequence, new Function1<Object, Boolean>() { // from class: org.jetbrains.kotlin.analysis.project.structure.KtModuleUtilsKt$directFriendDependenciesOfType$$inlined$filterIsInstance$1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(Object obj) {
                Intrinsics.reifiedOperationMarker(3, "M");
                return Boolean.valueOf(obj instanceof Object);
            }
        });
        Intrinsics.checkNotNull(sequenceFilter, "null cannot be cast to non-null type kotlin.sequences.Sequence<R of kotlin.sequences.SequencesKt___SequencesKt.filterIsInstance>");
        return sequenceFilter;
    }

    public static final /* synthetic */ <M extends KtModule> Sequence<M> directDependsOnDependenciesOfType(KtModule ktModule) {
        Intrinsics.checkNotNullParameter(ktModule, "<this>");
        Sequence sequenceAsSequence = CollectionsKt.asSequence(ktModule.getDirectDependsOnDependencies());
        Intrinsics.needClassReification();
        Sequence<M> sequenceFilter = SequencesKt.filter(sequenceAsSequence, new Function1<Object, Boolean>() { // from class: org.jetbrains.kotlin.analysis.project.structure.KtModuleUtilsKt$directDependsOnDependenciesOfType$$inlined$filterIsInstance$1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(Object obj) {
                Intrinsics.reifiedOperationMarker(3, "M");
                return Boolean.valueOf(obj instanceof Object);
            }
        });
        Intrinsics.checkNotNull(sequenceFilter, "null cannot be cast to non-null type kotlin.sequences.Sequence<R of kotlin.sequences.SequencesKt___SequencesKt.filterIsInstance>");
        return sequenceFilter;
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.analysis.project.structure.KtModuleUtilsKt$allDirectDependencies$1, reason: invalid class name */
    /* JADX INFO: compiled from: ktModuleUtils.kt */
    @Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\u00030\u0002H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "Lkotlin/sequences/SequenceScope;", "Lorg/jetbrains/kotlin/analysis/project/structure/KtModule;"}, k = 3, mv = {1, 9, 0}, xi = 48)
    @DebugMetadata(c = "org.jetbrains.kotlin.analysis.project.structure.KtModuleUtilsKt$allDirectDependencies$1", f = "ktModuleUtils.kt", i = {0, 1}, l = {43, 44, 45}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {"$this$sequence", "$this$sequence"}, s = {"L$0", "L$0"})
    static final class AnonymousClass1 extends RestrictedSuspendLambda implements Function2<SequenceScope<? super KtModule>, Continuation<? super Unit>, Object> {
        final /* synthetic */ KtModule $this_allDirectDependencies;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(KtModule ktModule, Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
            this.$this_allDirectDependencies = ktModule;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$this_allDirectDependencies, continuation);
            anonymousClass1.L$0 = obj;
            return anonymousClass1;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(SequenceScope<? super KtModule> sequenceScope, Continuation<? super Unit> continuation) {
            return ((AnonymousClass1) create(sequenceScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code duplicated, block: B:21:0x0077 A[RETURN] */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            SequenceScope sequenceScope;
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                SequenceScope sequenceScope2 = (SequenceScope) this.L$0;
                this.L$0 = sequenceScope2;
                this.label = 1;
                if (sequenceScope2.yieldAll(this.$this_allDirectDependencies.getDirectRegularDependencies(), this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
                sequenceScope = sequenceScope2;
            } else {
                if (i == 1) {
                    sequenceScope = (SequenceScope) this.L$0;
                    ResultKt.throwOnFailure(obj);
                } else if (i == 2) {
                    sequenceScope = (SequenceScope) this.L$0;
                    ResultKt.throwOnFailure(obj);
                    this.L$0 = null;
                    this.label = 3;
                    if (sequenceScope.yieldAll(this.$this_allDirectDependencies.getDirectFriendDependencies(), this) == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                } else {
                    if (i != 3) {
                        throw new IllegalStateException(CoroutineConstantsKt.ILLEGAL_STATE_ERROR_MESSAGE);
                    }
                    ResultKt.throwOnFailure(obj);
                }
                return Unit.INSTANCE;
            }
            this.L$0 = sequenceScope;
            this.label = 2;
            if (sequenceScope.yieldAll(this.$this_allDirectDependencies.getDirectDependsOnDependencies(), this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            this.L$0 = null;
            this.label = 3;
            if (sequenceScope.yieldAll(this.$this_allDirectDependencies.getDirectFriendDependencies(), this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            return Unit.INSTANCE;
        }
    }

    public static final Sequence<KtModule> allDirectDependencies(KtModule ktModule) {
        Intrinsics.checkNotNullParameter(ktModule, "<this>");
        return SequencesKt.sequence(new AnonymousClass1(ktModule, null));
    }

    public static final /* synthetic */ <M extends KtModule> Sequence<M> allDirectDependenciesOfType(KtModule ktModule) {
        Intrinsics.checkNotNullParameter(ktModule, "<this>");
        Sequence<KtModule> sequenceAllDirectDependencies = allDirectDependencies(ktModule);
        Intrinsics.needClassReification();
        Sequence<M> sequenceFilter = SequencesKt.filter(sequenceAllDirectDependencies, new Function1<Object, Boolean>() { // from class: org.jetbrains.kotlin.analysis.project.structure.KtModuleUtilsKt$allDirectDependenciesOfType$$inlined$filterIsInstance$1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(Object obj) {
                Intrinsics.reifiedOperationMarker(3, "M");
                return Boolean.valueOf(obj instanceof Object);
            }
        });
        Intrinsics.checkNotNull(sequenceFilter, "null cannot be cast to non-null type kotlin.sequences.Sequence<R of kotlin.sequences.SequencesKt___SequencesKt.filterIsInstance>");
        return sequenceFilter;
    }

    public static final List<KtModule> computeTransitiveDependsOnDependencies(List<? extends KtModule> directDependsOnDependencies) {
        Intrinsics.checkNotNullParameter(directDependsOnDependencies, "directDependsOnDependencies");
        return SortUtilsKt.topologicalSort$default(directDependsOnDependencies, null, new Function1<KtModule, Iterable<? extends KtModule>>() { // from class: org.jetbrains.kotlin.analysis.project.structure.KtModuleUtilsKt.computeTransitiveDependsOnDependencies.1
            @Override // kotlin.jvm.functions.Function1
            public final Iterable<KtModule> invoke(KtModule topologicalSort) {
                Intrinsics.checkNotNullParameter(topologicalSort, "$this$topologicalSort");
                return topologicalSort.getDirectDependsOnDependencies();
            }
        }, 2, null);
    }
}
