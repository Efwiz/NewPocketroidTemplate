package org.jetbrains.kotlin.resolve.calls.tower;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.progress.ProgressIndicatorAndCompilationCanceledStatus;
import org.jetbrains.kotlin.resolve.calls.components.candidate.ResolutionCandidate;
import org.jetbrains.kotlin.resolve.calls.inference.model.ConstraintSystemError;
import org.jetbrains.kotlin.resolve.calls.inference.model.LowerPriorityToPreserveCompatibility;
import org.jetbrains.kotlin.resolve.calls.model.KotlinCallDiagnostic;
import org.jetbrains.kotlin.resolve.calls.model.KotlinCallDiagnosticsKt;
import org.jetbrains.kotlin.resolve.descriptorUtil.AnnotationsForResolveKt;
import org.jetbrains.kotlin.resolve.scopes.HierarchicalScope;
import org.jetbrains.kotlin.resolve.scopes.ImportingScope;
import org.jetbrains.kotlin.resolve.scopes.LexicalScope;
import org.jetbrains.kotlin.resolve.scopes.ResolutionScope;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValueWithSmartCastInfo;
import org.jetbrains.kotlin.resolve.scopes.utils.ScopeUtilsKt;
import org.jetbrains.kotlin.types.DynamicTypesKt;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.util.OperatorNameConventions;

/* JADX INFO: compiled from: TowerResolver.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u00002\u00020\u0001:\u0004\u0017\u0018\u0019\u001aB\u0005¢\u0006\u0002\u0010\u0002J4\u0010\u0003\u001a\b\u0012\u0004\u0012\u0002H\u00050\u0004\"\b\b\u0000\u0010\u0005*\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\f\u0010\t\u001a\b\u0012\u0004\u0012\u0002H\u00050\n2\u0006\u0010\u000b\u001a\u00020\fJF\u0010\r\u001a\n\u0012\u0004\u0012\u0002H\u0005\u0018\u00010\u0004\"\b\b\u0000\u0010\u0005*\u00020\u00062\f\u0010\t\u001a\b\u0012\u0004\u0012\u0002H\u00050\n2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u0002H\u00050\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0002J<\u0010\u0014\u001a\b\u0012\u0004\u0012\u0002H\u00050\u0004\"\b\b\u0000\u0010\u0005*\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\f\u0010\t\u001a\b\u0012\u0004\u0012\u0002H\u00050\n2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u000b\u001a\u00020\fJ:\u0010\u0015\u001a\b\u0012\u0004\u0012\u0002H\u00050\u0004\"\b\b\u0000\u0010\u0005*\u00020\u00062\f\u0010\t\u001a\b\u0012\u0004\u0012\u0002H\u00050\n2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u0002H\u00050\u000f2\u0006\u0010\u0010\u001a\u00020\u0011JH\u0010\u0016\u001a\b\u0012\u0004\u0012\u0002H\u00050\u0004\"\b\b\u0000\u0010\u0005*\u00020\u0006*\u00020\b2\f\u0010\t\u001a\b\u0012\u0004\u0012\u0002H\u00050\n2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u0002H\u00050\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u000b\u001a\u00020\fH\u0002¨\u0006\u001b"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/tower/TowerResolver;", "", "()V", "collectAllCandidates", "", "C", "Lorg/jetbrains/kotlin/resolve/calls/tower/Candidate;", "scopeTower", "Lorg/jetbrains/kotlin/resolve/calls/tower/ImplicitScopeTower;", "processor", "Lorg/jetbrains/kotlin/resolve/calls/tower/ScopeTowerProcessor;", "name", "Lorg/jetbrains/kotlin/name/Name;", "processTowerData", "resultCollector", "Lorg/jetbrains/kotlin/resolve/calls/tower/TowerResolver$ResultCollector;", "useOrder", "", "towerData", "Lorg/jetbrains/kotlin/resolve/calls/tower/TowerData;", "runResolve", "runWithEmptyTowerData", "run", "AllCandidatesCollector", "ResultCollector", "SuccessfulResultCollector", "Task", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class TowerResolver {

    /* JADX INFO: compiled from: TowerResolver.kt */
    @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\b&\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\u00020\u0003B\u0005¢\u0006\u0002\u0010\u0004J\u000e\u0010\u0005\u001a\b\u0012\u0004\u0012\u00028\u00000\u0006H&J\u0010\u0010\u0007\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u0006H&J\u0016\u0010\b\u001a\u00020\t2\f\u0010\n\u001a\b\u0012\u0004\u0012\u00028\u00000\u0006H&¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/tower/TowerResolver$ResultCollector;", "C", "Lorg/jetbrains/kotlin/resolve/calls/tower/Candidate;", "", "()V", "getFinalCandidates", "", "getSuccessfulCandidates", "pushCandidates", "", "candidates", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static abstract class ResultCollector<C extends Candidate> {
        public abstract Collection<C> getFinalCandidates();

        public abstract Collection<C> getSuccessfulCandidates();

        public abstract void pushCandidates(Collection<? extends C> candidates);
    }

    public final <C extends Candidate> Collection<C> runResolve(ImplicitScopeTower scopeTower, ScopeTowerProcessor<? extends C> processor, boolean useOrder, Name name2) {
        Intrinsics.checkNotNullParameter(scopeTower, "scopeTower");
        Intrinsics.checkNotNullParameter(processor, "processor");
        Intrinsics.checkNotNullParameter(name2, "name");
        return run(scopeTower, processor, new SuccessfulResultCollector(), useOrder, name2);
    }

    public final <C extends Candidate> Collection<C> collectAllCandidates(ImplicitScopeTower scopeTower, ScopeTowerProcessor<? extends C> processor, Name name2) {
        Intrinsics.checkNotNullParameter(scopeTower, "scopeTower");
        Intrinsics.checkNotNullParameter(processor, "processor");
        Intrinsics.checkNotNullParameter(name2, "name");
        return run(scopeTower, processor, new AllCandidatesCollector(), false, name2);
    }

    private final <C extends Candidate> Collection<C> run(ImplicitScopeTower implicitScopeTower, ScopeTowerProcessor<? extends C> scopeTowerProcessor, ResultCollector<C> resultCollector, boolean z, Name name2) {
        return new Task(this, implicitScopeTower, scopeTowerProcessor, resultCollector, z, name2).run();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: compiled from: TowerResolver.kt */
    @Metadata(d1 = {"\u0000r\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0082\u0004\u0018\u0000*\n\b\u0000\u0010\u0001 \u0001*\u00020\u00022\u00020\u0003B9\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00028\u00000\u0007\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00028\u00000\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r¢\u0006\u0002\u0010\u000eJ \u0010%\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u00152\u0006\u0010&\u001a\u00020'2\u0006\u0010(\u001a\u00020\u000bH\u0002J\b\u0010)\u001a\u00020*H\u0002J\f\u0010+\u001a\b\u0012\u0004\u0012\u00028\u00000\u0015J\u0012\u0010,\u001a\b\u0012\u0004\u0012\u00020\u00160\u0015*\u00020\u0005H\u0002J\u0014\u0010-\u001a\u00020\u000b*\u00020.2\u0006\u0010\f\u001a\u00020\rH\u0002J\u0014\u0010-\u001a\u00020\u000b*\u00020'2\u0006\u0010\f\u001a\u00020\rH\u0002J\u0014\u0010-\u001a\u00020\u000b*\u00020/2\u0006\u0010\f\u001a\u00020\rH\u0002J\u0014\u00100\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u0015*\u00020 H\u0002J\u001c\u00100\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u0015*\u00020 2\u0006\u0010-\u001a\u00020\u000bH\u0002R\u0011\u0010\u000f\u001a\u00020\u0010¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R!\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00160\u00158BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0019\u0010\u001a\u001a\u0004\b\u0017\u0010\u0018R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R!\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00160\u00158BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u001d\u0010\u001a\u001a\u0004\b\u001c\u0010\u0018R\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00028\u00000\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00028\u00000\tX\u0088\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020 0\u001fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010!\u001a\u00020\"¢\u0006\b\n\u0000\u001a\u0004\b#\u0010$R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u00061"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/tower/TowerResolver$Task;", "C", "Lorg/jetbrains/kotlin/resolve/calls/tower/Candidate;", "", "implicitScopeTower", "Lorg/jetbrains/kotlin/resolve/calls/tower/ImplicitScopeTower;", "processor", "Lorg/jetbrains/kotlin/resolve/calls/tower/ScopeTowerProcessor;", "resultCollector", "Lorg/jetbrains/kotlin/resolve/calls/tower/TowerResolver$ResultCollector;", "useOrder", "", "name", "Lorg/jetbrains/kotlin/name/Name;", "(Lorg/jetbrains/kotlin/resolve/calls/tower/TowerResolver;Lorg/jetbrains/kotlin/resolve/calls/tower/ImplicitScopeTower;Lorg/jetbrains/kotlin/resolve/calls/tower/ScopeTowerProcessor;Lorg/jetbrains/kotlin/resolve/calls/tower/TowerResolver$ResultCollector;ZLorg/jetbrains/kotlin/name/Name;)V", "hidesMembersLevel", "Lorg/jetbrains/kotlin/resolve/calls/tower/HidesMembersTowerLevel;", "getHidesMembersLevel", "()Lorg/jetbrains/kotlin/resolve/calls/tower/HidesMembersTowerLevel;", "isNameForHidesMember", "localLevels", "", "Lorg/jetbrains/kotlin/resolve/calls/tower/ScopeTowerLevel;", "getLocalLevels", "()Ljava/util/Collection;", "localLevels$delegate", "Lkotlin/Lazy;", "nonLocalLevels", "getNonLocalLevels", "nonLocalLevels$delegate", "skippedDataForLookup", "", "Lorg/jetbrains/kotlin/resolve/calls/tower/TowerData;", "syntheticLevel", "Lorg/jetbrains/kotlin/resolve/calls/tower/SyntheticScopeBasedTowerLevel;", "getSyntheticLevel", "()Lorg/jetbrains/kotlin/resolve/calls/tower/SyntheticScopeBasedTowerLevel;", "processImplicitReceiver", "implicitReceiver", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValueWithSmartCastInfo;", "resolveExtensions", "recordLookups", "", "run", "createNonLocalLevels", "mayFitForName", "Lorg/jetbrains/kotlin/resolve/scopes/ResolutionScope;", "Lorg/jetbrains/kotlin/types/KotlinType;", "process", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
    final class Task<C extends Candidate> {
        private final HidesMembersTowerLevel hidesMembersLevel;
        private final ImplicitScopeTower implicitScopeTower;
        private final boolean isNameForHidesMember;

        /* JADX INFO: renamed from: localLevels$delegate, reason: from kotlin metadata */
        private final Lazy localLevels;
        private final Name name;

        /* JADX INFO: renamed from: nonLocalLevels$delegate, reason: from kotlin metadata */
        private final Lazy nonLocalLevels;
        private final ScopeTowerProcessor<C> processor;
        private final ResultCollector<C> resultCollector;
        private final List<TowerData> skippedDataForLookup;
        private final SyntheticScopeBasedTowerLevel syntheticLevel;
        final /* synthetic */ TowerResolver this$0;
        private final boolean useOrder;

        /* JADX WARN: Multi-variable type inference failed */
        public Task(TowerResolver towerResolver, ImplicitScopeTower implicitScopeTower, ScopeTowerProcessor<? extends C> processor, ResultCollector<C> resultCollector, boolean z, Name name2) {
            Intrinsics.checkNotNullParameter(implicitScopeTower, "implicitScopeTower");
            Intrinsics.checkNotNullParameter(processor, "processor");
            Intrinsics.checkNotNullParameter(resultCollector, "resultCollector");
            Intrinsics.checkNotNullParameter(name2, "name");
            this.this$0 = towerResolver;
            this.implicitScopeTower = implicitScopeTower;
            this.processor = processor;
            this.resultCollector = resultCollector;
            this.useOrder = z;
            this.name = name2;
            this.isNameForHidesMember = AnnotationsForResolveKt.getHIDES_MEMBERS_NAME_LIST().contains(name2) || CollectionsKt.contains(AnnotationsForResolveKt.getHIDES_MEMBERS_NAME_LIST(), implicitScopeTower.getNameForGivenImportAlias(name2));
            this.skippedDataForLookup = new ArrayList();
            this.localLevels = LazyKt.lazy(LazyThreadSafetyMode.NONE, (Function0) new Function0<List<? extends ScopeBasedTowerLevel>>(this) { // from class: org.jetbrains.kotlin.resolve.calls.tower.TowerResolver$Task$localLevels$2
                final /* synthetic */ TowerResolver.Task<C> this$0;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                /* JADX WARN: Multi-variable type inference failed */
                {
                    super(0);
                    this.this$0 = this;
                }

                @Override // kotlin.jvm.functions.Function0
                public final List<? extends ScopeBasedTowerLevel> invoke() {
                    Sequence sequenceFilter = SequencesKt.filter(ScopeUtilsKt.getParentsWithSelf(((TowerResolver.Task) this.this$0).implicitScopeTower.getLexicalScope()), new Function1<Object, Boolean>() { // from class: org.jetbrains.kotlin.resolve.calls.tower.TowerResolver$Task$localLevels$2$invoke$$inlined$filterIsInstance$1
                        /* JADX WARN: Can't rename method to resolve collision */
                        @Override // kotlin.jvm.functions.Function1
                        public final Boolean invoke(Object obj) {
                            return Boolean.valueOf(obj instanceof LexicalScope);
                        }
                    });
                    Intrinsics.checkNotNull(sequenceFilter, "null cannot be cast to non-null type kotlin.sequences.Sequence<R of kotlin.sequences.SequencesKt___SequencesKt.filterIsInstance>");
                    final TowerResolver.Task<C> task = this.this$0;
                    Sequence sequenceFilter2 = SequencesKt.filter(sequenceFilter, new Function1<LexicalScope, Boolean>() { // from class: org.jetbrains.kotlin.resolve.calls.tower.TowerResolver$Task$localLevels$2.1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        /* JADX WARN: Multi-variable type inference failed */
                        {
                            super(1);
                        }

                        /* JADX WARN: Code duplicated, block: B:7:0x001f  */
                        @Override // kotlin.jvm.functions.Function1
                        public final Boolean invoke(LexicalScope it) {
                            boolean z2;
                            Intrinsics.checkNotNullParameter(it, "it");
                            if (it.getKind().getWithLocalDescriptors()) {
                                TowerResolver.Task<C> task2 = task;
                                if (task2.mayFitForName(it, ((TowerResolver.Task) task2).name)) {
                                    z2 = true;
                                } else {
                                    z2 = false;
                                }
                            } else {
                                z2 = false;
                            }
                            return Boolean.valueOf(z2);
                        }
                    });
                    final TowerResolver.Task<C> task2 = this.this$0;
                    return SequencesKt.toList(SequencesKt.map(sequenceFilter2, new Function1<LexicalScope, ScopeBasedTowerLevel>() { // from class: org.jetbrains.kotlin.resolve.calls.tower.TowerResolver$Task$localLevels$2.2
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        /* JADX WARN: Multi-variable type inference failed */
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public final ScopeBasedTowerLevel invoke(LexicalScope it) {
                            Intrinsics.checkNotNullParameter(it, "it");
                            return new ScopeBasedTowerLevel(((TowerResolver.Task) task2).implicitScopeTower, it);
                        }
                    }));
                }
            });
            this.nonLocalLevels = LazyKt.lazy(LazyThreadSafetyMode.NONE, (Function0) new Function0<Collection<? extends ScopeTowerLevel>>(this) { // from class: org.jetbrains.kotlin.resolve.calls.tower.TowerResolver$Task$nonLocalLevels$2
                final /* synthetic */ TowerResolver.Task<C> this$0;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                /* JADX WARN: Multi-variable type inference failed */
                {
                    super(0);
                    this.this$0 = this;
                }

                @Override // kotlin.jvm.functions.Function0
                public final Collection<? extends ScopeTowerLevel> invoke() {
                    TowerResolver.Task<C> task = this.this$0;
                    return task.createNonLocalLevels(((TowerResolver.Task) task).implicitScopeTower);
                }
            });
            this.hidesMembersLevel = new HidesMembersTowerLevel(implicitScopeTower);
            this.syntheticLevel = new SyntheticScopeBasedTowerLevel(implicitScopeTower, implicitScopeTower.getSyntheticScopes());
        }

        private final Collection<ScopeTowerLevel> getLocalLevels() {
            return (Collection) this.localLevels.getValue();
        }

        private final Collection<ScopeTowerLevel> getNonLocalLevels() {
            return (Collection) this.nonLocalLevels.getValue();
        }

        public final HidesMembersTowerLevel getHidesMembersLevel() {
            return this.hidesMembersLevel;
        }

        public final SyntheticScopeBasedTowerLevel getSyntheticLevel() {
            return this.syntheticLevel;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final Collection<ScopeTowerLevel> createNonLocalLevels(ImplicitScopeTower implicitScopeTower) {
            ArrayList arrayList = new ArrayList();
            if (implicitScopeTower.getAreContextReceiversEnabled()) {
                List<HierarchicalScope> list = SequencesKt.toList(ScopeUtilsKt.getParentsWithSelf(implicitScopeTower.getLexicalScope()));
                ArrayList arrayList2 = new ArrayList();
                int i = 0;
                int i2 = 0;
                for (HierarchicalScope hierarchicalScope : list) {
                    int i3 = i2 + 1;
                    if (!(hierarchicalScope instanceof LexicalScope)) {
                        i = i2;
                        break;
                    }
                    LexicalScope lexicalScope = (LexicalScope) hierarchicalScope;
                    createNonLocalLevels$addLevelForLexicalScope(implicitScopeTower, this, arrayList, lexicalScope);
                    List<ReceiverValueWithSmartCastInfo> contextReceivers = implicitScopeTower.getContextReceivers(lexicalScope);
                    if (!contextReceivers.isEmpty()) {
                        arrayList2.add(contextReceivers);
                    }
                    i2 = i3;
                }
                Iterator it = arrayList2.iterator();
                while (it.hasNext()) {
                    createNonLocalLevels$addLevelForContextReceiverGroup(implicitScopeTower, this, arrayList, (List) it.next());
                }
                Iterator it2 = list.subList(i, list.size()).iterator();
                while (it2.hasNext()) {
                    createNonLocalLevels$addLevelForImportingScope(implicitScopeTower, this, arrayList, (HierarchicalScope) it2.next());
                }
                return arrayList;
            }
            for (HierarchicalScope hierarchicalScope2 : ScopeUtilsKt.getParentsWithSelf(implicitScopeTower.getLexicalScope())) {
                if (hierarchicalScope2 instanceof LexicalScope) {
                    createNonLocalLevels$addLevelForLexicalScope(implicitScopeTower, this, arrayList, (LexicalScope) hierarchicalScope2);
                } else {
                    createNonLocalLevels$addLevelForImportingScope(implicitScopeTower, this, arrayList, hierarchicalScope2);
                }
            }
            return arrayList;
        }

        private static final <C extends Candidate> void createNonLocalLevels$addLevel(List<ScopeTowerLevel> list, Task<? extends C> task, ScopeTowerLevel scopeTowerLevel, boolean z) {
            if (z) {
                list.add(scopeTowerLevel);
            } else {
                ((Task) task).skippedDataForLookup.add(new TowerData.ForLookupForNoExplicitReceiver(scopeTowerLevel));
            }
        }

        private static final <C extends Candidate> void createNonLocalLevels$addLevelForLexicalScope(ImplicitScopeTower implicitScopeTower, Task<? extends C> task, List<ScopeTowerLevel> list, LexicalScope lexicalScope) {
            if (!lexicalScope.getKind().getWithLocalDescriptors()) {
                createNonLocalLevels$addLevel(list, task, new ScopeBasedTowerLevel(implicitScopeTower, lexicalScope), task.mayFitForName(lexicalScope, ((Task) task).name));
            }
            ReceiverValueWithSmartCastInfo implicitReceiver = implicitScopeTower.getImplicitReceiver(lexicalScope);
            if (implicitReceiver != null) {
                createNonLocalLevels$addLevel(list, task, new MemberScopeTowerLevel(implicitScopeTower, implicitReceiver), task.mayFitForName(implicitReceiver, ((Task) task).name));
            }
        }

        private static final <C extends Candidate> void createNonLocalLevels$addLevelForContextReceiverGroup(ImplicitScopeTower implicitScopeTower, Task<? extends C> task, List<ScopeTowerLevel> list, List<ReceiverValueWithSmartCastInfo> list2) {
            ContextReceiversGroupScopeTowerLevel contextReceiversGroupScopeTowerLevel = new ContextReceiversGroupScopeTowerLevel(implicitScopeTower, list2);
            List<ReceiverValueWithSmartCastInfo> list3 = list2;
            boolean z = false;
            if (!(list3 instanceof Collection) || !list3.isEmpty()) {
                Iterator<T> it = list3.iterator();
                while (it.hasNext()) {
                    if (task.mayFitForName((ReceiverValueWithSmartCastInfo) it.next(), ((Task) task).name)) {
                        z = true;
                        break;
                    }
                }
            }
            createNonLocalLevels$addLevel(list, task, contextReceiversGroupScopeTowerLevel, z);
        }

        private static final <C extends Candidate> void createNonLocalLevels$addLevelForImportingScope(ImplicitScopeTower implicitScopeTower, Task<? extends C> task, List<ScopeTowerLevel> list, HierarchicalScope hierarchicalScope) {
            Intrinsics.checkNotNull(hierarchicalScope, "null cannot be cast to non-null type org.jetbrains.kotlin.resolve.scopes.ImportingScope");
            createNonLocalLevels$addLevel(list, task, new ImportingScopeBasedTowerLevel(implicitScopeTower, (ImportingScope) hierarchicalScope), task.mayFitForName(hierarchicalScope, ((Task) task).name));
        }

        private final Collection<C> process(TowerData towerData) {
            Collection<C> collectionProcessTowerData = this.this$0.processTowerData(this.processor, this.resultCollector, this.useOrder, towerData);
            if (collectionProcessTowerData == null) {
                return null;
            }
            recordLookups();
            return collectionProcessTowerData;
        }

        private final Collection<C> process(TowerData towerData, boolean z) {
            if (!z) {
                this.skippedDataForLookup.add(towerData);
                return null;
            }
            return process(towerData);
        }

        public final Collection<C> run() {
            Collection<C> collectionProcess;
            if (this.isNameForHidesMember && (collectionProcess = process(new TowerData.TowerLevel(this.hidesMembersLevel))) != null) {
                return collectionProcess;
            }
            Collection<C> collectionProcess2 = process(TowerData.Empty.INSTANCE);
            if (collectionProcess2 != null) {
                return collectionProcess2;
            }
            Collection<C> collectionProcess3 = process(new TowerData.TowerLevel(this.syntheticLevel));
            if (collectionProcess3 != null) {
                return collectionProcess3;
            }
            Iterator<ScopeTowerLevel> it = getLocalLevels().iterator();
            while (it.hasNext()) {
                Collection<C> collectionProcess4 = process(new TowerData.TowerLevel(it.next()));
                if (collectionProcess4 != null) {
                    return collectionProcess4;
                }
            }
            ArrayList arrayList = new ArrayList();
            if (this.implicitScopeTower.getImplicitsResolutionFilter() == ImplicitsExtensionsResolutionFilter.Default.INSTANCE) {
                run$processScopes(this, arrayList, ScopeUtilsKt.getParentsWithSelf(this.implicitScopeTower.getLexicalScope()), new Function1<HierarchicalScope, Boolean>() { // from class: org.jetbrains.kotlin.resolve.calls.tower.TowerResolver$Task$run$5
                    @Override // kotlin.jvm.functions.Function1
                    public final Boolean invoke(HierarchicalScope it2) {
                        Intrinsics.checkNotNullParameter(it2, "it");
                        return true;
                    }
                });
            } else {
                Sequence<ScopeWithImplicitsExtensionsResolutionInfo> sequenceAllScopesWithImplicitsResolutionInfo = this.implicitScopeTower.allScopesWithImplicitsResolutionInfo();
                final Map map = MapsKt.toMap(SequencesKt.map(sequenceAllScopesWithImplicitsResolutionInfo, new Function1<ScopeWithImplicitsExtensionsResolutionInfo, Pair<? extends HierarchicalScope, ? extends Boolean>>() { // from class: org.jetbrains.kotlin.resolve.calls.tower.TowerResolver$Task$run$scopeToResolveExtensionsForImplicitReceiverMap$1
                    @Override // kotlin.jvm.functions.Function1
                    public final Pair<HierarchicalScope, Boolean> invoke(ScopeWithImplicitsExtensionsResolutionInfo it2) {
                        Intrinsics.checkNotNullParameter(it2, "it");
                        return TuplesKt.to(it2.getScope(), Boolean.valueOf(it2.getResolveExtensionsForImplicitReceiver()));
                    }
                }));
                run$processScopes(this, arrayList, SequencesKt.map(sequenceAllScopesWithImplicitsResolutionInfo, new Function1<ScopeWithImplicitsExtensionsResolutionInfo, HierarchicalScope>() { // from class: org.jetbrains.kotlin.resolve.calls.tower.TowerResolver$Task$run$6
                    @Override // kotlin.jvm.functions.Function1
                    public final HierarchicalScope invoke(ScopeWithImplicitsExtensionsResolutionInfo it2) {
                        Intrinsics.checkNotNullParameter(it2, "it");
                        return it2.getScope();
                    }
                }), new Function1<HierarchicalScope, Boolean>() { // from class: org.jetbrains.kotlin.resolve.calls.tower.TowerResolver$Task$run$7
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final Boolean invoke(HierarchicalScope it2) {
                        Intrinsics.checkNotNullParameter(it2, "it");
                        Boolean bool = map.get(it2);
                        return Boolean.valueOf(bool != null ? bool.booleanValue() : false);
                    }
                });
            }
            recordLookups();
            return this.resultCollector.getFinalCandidates();
        }

        private static final <C extends Candidate> Collection<C> run$processLexicalScope(Task<? extends C> task, List<List<ReceiverValueWithSmartCastInfo>> list, LexicalScope lexicalScope, boolean z) {
            Collection<C> collectionProcessImplicitReceiver;
            Collection<C> collectionProcess;
            if (((Task) task).implicitScopeTower.getAreContextReceiversEnabled()) {
                List<ReceiverValueWithSmartCastInfo> contextReceivers = ((Task) task).implicitScopeTower.getContextReceivers(lexicalScope);
                if (!contextReceivers.isEmpty()) {
                    list.add(contextReceivers);
                }
            }
            if (!lexicalScope.getKind().getWithLocalDescriptors() && (collectionProcess = task.process(new TowerData.TowerLevel(new ScopeBasedTowerLevel(((Task) task).implicitScopeTower, lexicalScope)), task.mayFitForName(lexicalScope, ((Task) task).name))) != null) {
                return collectionProcess;
            }
            ReceiverValueWithSmartCastInfo implicitReceiver = ((Task) task).implicitScopeTower.getImplicitReceiver(lexicalScope);
            if (implicitReceiver == null || (collectionProcessImplicitReceiver = task.processImplicitReceiver(implicitReceiver, z)) == null) {
                return null;
            }
            return collectionProcessImplicitReceiver;
        }

        private static final <C extends Candidate> Collection<C> run$processContextReceiverGroup(Task<? extends C> task, List<ReceiverValueWithSmartCastInfo> list) {
            Collection<C> collectionProcess = task.process(new TowerData.TowerLevel(new ContextReceiversGroupScopeTowerLevel(((Task) task).implicitScopeTower, list)));
            if (collectionProcess != null) {
                return collectionProcess;
            }
            Collection<C> collectionProcess2 = task.process(new TowerData.BothTowerLevelAndContextReceiversGroup(((Task) task).syntheticLevel, list));
            if (collectionProcess2 != null) {
                return collectionProcess2;
            }
            Iterator<ScopeTowerLevel> it = task.getNonLocalLevels().iterator();
            while (it.hasNext()) {
                Collection<C> collectionProcess3 = task.process(new TowerData.BothTowerLevelAndContextReceiversGroup(it.next(), list));
                if (collectionProcess3 != null) {
                    return collectionProcess3;
                }
            }
            return null;
        }

        private static final <C extends Candidate> Collection<C> run$processImportingScope(Task<? extends C> task, ImportingScope importingScope) {
            Collection<C> collectionProcess = task.process(new TowerData.TowerLevel(new ImportingScopeBasedTowerLevel(((Task) task).implicitScopeTower, importingScope)), task.mayFitForName(importingScope, ((Task) task).name));
            if (collectionProcess != null) {
                return collectionProcess;
            }
            return null;
        }

        private static final <C extends Candidate> Collection<C> run$processScopes(Task<? extends C> task, List<List<ReceiverValueWithSmartCastInfo>> list, Sequence<? extends HierarchicalScope> sequence, Function1<? super HierarchicalScope, Boolean> function1) {
            if (((Task) task).implicitScopeTower.getAreContextReceiversEnabled()) {
                boolean z = false;
                for (HierarchicalScope hierarchicalScope : sequence) {
                    if (hierarchicalScope instanceof LexicalScope) {
                        Collection<C> collectionRun$processLexicalScope = run$processLexicalScope(task, list, (LexicalScope) hierarchicalScope, function1.invoke(hierarchicalScope).booleanValue());
                        if (collectionRun$processLexicalScope != null) {
                            return collectionRun$processLexicalScope;
                        }
                    } else {
                        if (!z) {
                            Iterator<T> it = list.iterator();
                            while (it.hasNext()) {
                                Collection<C> collectionRun$processContextReceiverGroup = run$processContextReceiverGroup(task, (List) it.next());
                                if (collectionRun$processContextReceiverGroup != null) {
                                    return collectionRun$processContextReceiverGroup;
                                }
                            }
                            z = true;
                        }
                        Intrinsics.checkNotNull(hierarchicalScope, "null cannot be cast to non-null type org.jetbrains.kotlin.resolve.scopes.ImportingScope");
                        Collection<C> collectionRun$processImportingScope = run$processImportingScope(task, (ImportingScope) hierarchicalScope);
                        if (collectionRun$processImportingScope != null) {
                            return collectionRun$processImportingScope;
                        }
                    }
                }
                return null;
            }
            for (HierarchicalScope hierarchicalScope2 : sequence) {
                if (hierarchicalScope2 instanceof LexicalScope) {
                    Collection<C> collectionRun$processLexicalScope2 = run$processLexicalScope(task, list, (LexicalScope) hierarchicalScope2, function1.invoke(hierarchicalScope2).booleanValue());
                    if (collectionRun$processLexicalScope2 != null) {
                        return collectionRun$processLexicalScope2;
                    }
                } else {
                    Intrinsics.checkNotNull(hierarchicalScope2, "null cannot be cast to non-null type org.jetbrains.kotlin.resolve.scopes.ImportingScope");
                    Collection<C> collectionRun$processImportingScope2 = run$processImportingScope(task, (ImportingScope) hierarchicalScope2);
                    if (collectionRun$processImportingScope2 != null) {
                        return collectionRun$processImportingScope2;
                    }
                }
            }
            return null;
        }

        private final Collection<C> processImplicitReceiver(ReceiverValueWithSmartCastInfo implicitReceiver, boolean resolveExtensions) {
            Collection<C> collectionProcess;
            if (this.isNameForHidesMember && (collectionProcess = process(new TowerData.BothTowerLevelAndImplicitReceiver(this.hidesMembersLevel, implicitReceiver))) != null) {
                return collectionProcess;
            }
            Collection<C> collectionProcess2 = process(new TowerData.TowerLevel(new MemberScopeTowerLevel(this.implicitScopeTower, implicitReceiver)), mayFitForName(implicitReceiver, this.name));
            if (collectionProcess2 != null) {
                return collectionProcess2;
            }
            Collection<C> collectionProcess3 = process(new TowerData.BothTowerLevelAndImplicitReceiver(this.syntheticLevel, implicitReceiver));
            if (collectionProcess3 != null) {
                return collectionProcess3;
            }
            if (!resolveExtensions) {
                return null;
            }
            Collection<C> collectionProcess4 = process(new TowerData.OnlyImplicitReceiver(implicitReceiver));
            if (collectionProcess4 != null) {
                return collectionProcess4;
            }
            Iterator<ScopeTowerLevel> it = getLocalLevels().iterator();
            while (it.hasNext()) {
                Collection<C> collectionProcess5 = process(new TowerData.BothTowerLevelAndImplicitReceiver(it.next(), implicitReceiver));
                if (collectionProcess5 != null) {
                    return collectionProcess5;
                }
            }
            Iterator<ScopeTowerLevel> it2 = getNonLocalLevels().iterator();
            while (it2.hasNext()) {
                Collection<C> collectionProcess6 = process(new TowerData.BothTowerLevelAndImplicitReceiver(it2.next(), implicitReceiver));
                if (collectionProcess6 != null) {
                    return collectionProcess6;
                }
            }
            return null;
        }

        private final void recordLookups() {
            this.processor.recordLookups(this.skippedDataForLookup, this.name);
        }

        private final boolean mayFitForName(ReceiverValueWithSmartCastInfo receiverValueWithSmartCastInfo, Name name2) {
            KotlinType type = receiverValueWithSmartCastInfo.getReceiverValue().getType();
            Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
            if (mayFitForName(type, name2)) {
                return true;
            }
            if (!receiverValueWithSmartCastInfo.hasTypesFromSmartCasts()) {
                return false;
            }
            Set<KotlinType> typesFromSmartCasts = receiverValueWithSmartCastInfo.getTypesFromSmartCasts();
            if (!(typesFromSmartCasts instanceof Collection) || !typesFromSmartCasts.isEmpty()) {
                Iterator<T> it = typesFromSmartCasts.iterator();
                while (it.hasNext()) {
                    if (mayFitForName((KotlinType) it.next(), name2)) {
                        return true;
                    }
                }
            }
            return false;
        }

        private final boolean mayFitForName(KotlinType kotlinType, Name name2) {
            return (!DynamicTypesKt.isDynamic(kotlinType) && kotlinType.getMemberScope().definitelyDoesNotContainName(name2) && kotlinType.getMemberScope().definitelyDoesNotContainName(OperatorNameConventions.INVOKE)) ? false : true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final boolean mayFitForName(ResolutionScope resolutionScope, Name name2) {
            return (resolutionScope.definitelyDoesNotContainName(name2) && resolutionScope.definitelyDoesNotContainName(OperatorNameConventions.INVOKE)) ? false : true;
        }
    }

    public final <C extends Candidate> Collection<C> runWithEmptyTowerData(ScopeTowerProcessor<? extends C> processor, ResultCollector<C> resultCollector, boolean useOrder) {
        Intrinsics.checkNotNullParameter(processor, "processor");
        Intrinsics.checkNotNullParameter(resultCollector, "resultCollector");
        Collection<C> collectionProcessTowerData = processTowerData(processor, resultCollector, useOrder, TowerData.Empty.INSTANCE);
        return collectionProcessTowerData == null ? resultCollector.getFinalCandidates() : collectionProcessTowerData;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final <C extends Candidate> Collection<C> processTowerData(ScopeTowerProcessor<? extends C> processor, ResultCollector<C> resultCollector, boolean useOrder, TowerData towerData) {
        List<Collection<? extends C>> listListOf;
        ProgressIndicatorAndCompilationCanceledStatus.checkCanceled();
        if (useOrder) {
            listListOf = processor.process(towerData);
        } else {
            listListOf = CollectionsKt.listOf(CollectionsKt.flatten(processor.process(towerData)));
        }
        Iterator<Collection<? extends C>> it = listListOf.iterator();
        while (it.hasNext()) {
            resultCollector.pushCandidates(it.next());
            Collection<C> successfulCandidates = resultCollector.getSuccessfulCandidates();
            if (successfulCandidates != null) {
                return successfulCandidates;
            }
        }
        return null;
    }

    /* JADX INFO: compiled from: TowerResolver.kt */
    @Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003B\u0005¢\u0006\u0002\u0010\u0004J\u000e\u0010\u0007\u001a\b\u0012\u0004\u0012\u00028\u00000\bH\u0016J\u0010\u0010\t\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\bH\u0016J\u0016\u0010\n\u001a\u00020\u000b2\f\u0010\f\u001a\b\u0012\u0004\u0012\u00028\u00000\bH\u0016R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00028\u00000\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/tower/TowerResolver$AllCandidatesCollector;", "C", "Lorg/jetbrains/kotlin/resolve/calls/tower/Candidate;", "Lorg/jetbrains/kotlin/resolve/calls/tower/TowerResolver$ResultCollector;", "()V", "allCandidates", "Ljava/util/ArrayList;", "getFinalCandidates", "", "getSuccessfulCandidates", "pushCandidates", "", "candidates", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class AllCandidatesCollector<C extends Candidate> extends ResultCollector<C> {
        private final ArrayList<C> allCandidates = new ArrayList<>();

        @Override // org.jetbrains.kotlin.resolve.calls.tower.TowerResolver.ResultCollector
        public Collection<C> getSuccessfulCandidates() {
            return null;
        }

        @Override // org.jetbrains.kotlin.resolve.calls.tower.TowerResolver.ResultCollector
        public Collection<C> getFinalCandidates() {
            return this.allCandidates;
        }

        @Override // org.jetbrains.kotlin.resolve.calls.tower.TowerResolver.ResultCollector
        public void pushCandidates(Collection<? extends C> candidates) {
            Intrinsics.checkNotNullParameter(candidates, "candidates");
            ArrayList<C> arrayList = this.allCandidates;
            for (Object obj : candidates) {
                if (((Candidate) obj).getResultingApplicability() != CandidateApplicability.HIDDEN) {
                    arrayList.add(obj);
                }
            }
        }
    }

    /* JADX INFO: compiled from: TowerResolver.kt */
    @Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u0002\n\u0002\b\u0003\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003B\u0005¢\u0006\u0002\u0010\u0004J\u000e\u0010\u000f\u001a\b\u0012\u0004\u0012\u00028\u00000\u0007H\u0016J\u0010\u0010\u0010\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u0007H\u0016J\u0015\u0010\u0011\u001a\u00020\n2\u0006\u0010\u0012\u001a\u00028\u0000H\u0002¢\u0006\u0002\u0010\u0013J\u0015\u0010\u0014\u001a\u00020\n2\u0006\u0010\u0012\u001a\u00028\u0000H\u0002¢\u0006\u0002\u0010\u0013J\u0016\u0010\u0015\u001a\u00020\u00162\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00028\u00000\u0007H\u0016J\u0015\u0010\u0018\u001a\u00020\n2\u0006\u0010\u0012\u001a\u00028\u0000H\u0002¢\u0006\u0002\u0010\u0013R*\u0010\u0005\u001a\u001e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u00070\u0006j\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u0007`\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u001e\u0010\u000b\u001a\u00020\f*\b\u0012\u0004\u0012\u00028\u00000\u00078BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000e¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/tower/TowerResolver$SuccessfulResultCollector;", "C", "Lorg/jetbrains/kotlin/resolve/calls/tower/Candidate;", "Lorg/jetbrains/kotlin/resolve/calls/tower/TowerResolver$ResultCollector;", "()V", "candidateGroups", "Ljava/util/ArrayList;", "", "Lkotlin/collections/ArrayList;", "isSuccessful", "", "groupApplicability", "Lorg/jetbrains/kotlin/resolve/calls/tower/CandidateApplicability;", "getGroupApplicability", "(Ljava/util/Collection;)Lorg/jetbrains/kotlin/resolve/calls/tower/CandidateApplicability;", "getFinalCandidates", "getSuccessfulCandidates", "isPreserveCompatibilityCandidate", "candidate", "(Lorg/jetbrains/kotlin/resolve/calls/tower/Candidate;)Z", "needToReportCompatibilityWarning", "pushCandidates", "", "candidates", "shouldStopResolveOnCandidate", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class SuccessfulResultCollector<C extends Candidate> extends ResultCollector<C> {
        private ArrayList<Collection<C>> candidateGroups = new ArrayList<>();
        private boolean isSuccessful;

        @Override // org.jetbrains.kotlin.resolve.calls.tower.TowerResolver.ResultCollector
        public Collection<C> getSuccessfulCandidates() {
            Collection<C> next;
            if (!this.isSuccessful) {
                return null;
            }
            Iterator<Collection<C>> it = this.candidateGroups.iterator();
            C c = null;
            Collection<C> collection = null;
            loop0: while (true) {
                if (!it.hasNext()) {
                    next = null;
                    break;
                }
                next = it.next();
                for (C c2 : next) {
                    if (shouldStopResolveOnCandidate(c2)) {
                        break loop0;
                    }
                    if (c == null && isPreserveCompatibilityCandidate(c2)) {
                        collection = next;
                        c = c2;
                    }
                }
            }
            if (next == null) {
                return null;
            }
            if (c != null && collection != next && needToReportCompatibilityWarning(c)) {
                Iterator<T> it2 = next.iterator();
                while (it2.hasNext()) {
                    ((Candidate) it2.next()).addCompatibilityWarning(c);
                }
            }
            ArrayList arrayList = new ArrayList();
            for (Object obj : next) {
                if (shouldStopResolveOnCandidate((Candidate) obj)) {
                    arrayList.add(obj);
                }
            }
            return arrayList;
        }

        private final boolean needToReportCompatibilityWarning(C candidate) {
            if (candidate instanceof ResolutionCandidate) {
                List<KotlinCallDiagnostic> diagnostics = ((ResolutionCandidate) candidate).getDiagnostics();
                if (!(diagnostics instanceof Collection) || !diagnostics.isEmpty()) {
                    Iterator<T> it = diagnostics.iterator();
                    while (it.hasNext()) {
                        ConstraintSystemError constraintSystemError = KotlinCallDiagnosticsKt.getConstraintSystemError((KotlinCallDiagnostic) it.next());
                        LowerPriorityToPreserveCompatibility lowerPriorityToPreserveCompatibility = constraintSystemError instanceof LowerPriorityToPreserveCompatibility ? (LowerPriorityToPreserveCompatibility) constraintSystemError : null;
                        if (lowerPriorityToPreserveCompatibility != null && lowerPriorityToPreserveCompatibility.getNeedToReportWarning()) {
                            return true;
                        }
                    }
                }
            }
            return false;
        }

        private final boolean shouldStopResolveOnCandidate(C candidate) {
            return CandidateApplicabilityKt.getShouldStopResolve(candidate.getResultingApplicability());
        }

        private final boolean isPreserveCompatibilityCandidate(C candidate) {
            return candidate.getResultingApplicability() == CandidateApplicability.RESOLVED_NEED_PRESERVE_COMPATIBILITY;
        }

        @Override // org.jetbrains.kotlin.resolve.calls.tower.TowerResolver.ResultCollector
        public void pushCandidates(Collection<? extends C> candidates) {
            Intrinsics.checkNotNullParameter(candidates, "candidates");
            Collection<? extends C> collection = candidates;
            boolean z = false;
            if (!collection.isEmpty()) {
                Iterator<T> it = collection.iterator();
                while (it.hasNext()) {
                    if (((Candidate) it.next()).isSuccessful()) {
                        z = true;
                        break;
                    }
                }
            }
            boolean z2 = this.isSuccessful;
            if (!z2 && !z) {
                this.candidateGroups.add(candidates);
                return;
            }
            if (!z2) {
                this.candidateGroups.clear();
                this.isSuccessful = true;
            }
            if (z) {
                ArrayList<Collection<C>> arrayList = this.candidateGroups;
                ArrayList arrayList2 = new ArrayList();
                for (Object obj : collection) {
                    if (((Candidate) obj).isSuccessful()) {
                        arrayList2.add(obj);
                    }
                }
                arrayList.add(arrayList2);
            }
        }

        @Override // org.jetbrains.kotlin.resolve.calls.tower.TowerResolver.ResultCollector
        public Collection<C> getFinalCandidates() {
            Object obj;
            CandidateApplicability groupApplicability;
            Iterator<T> it = this.candidateGroups.iterator();
            if (it.hasNext()) {
                Object next = it.next();
                if (it.hasNext()) {
                    CandidateApplicability groupApplicability2 = getGroupApplicability((Collection) next);
                    do {
                        Object next2 = it.next();
                        CandidateApplicability groupApplicability3 = getGroupApplicability((Collection) next2);
                        if (groupApplicability2.compareTo(groupApplicability3) < 0) {
                            next = next2;
                            groupApplicability2 = groupApplicability3;
                        }
                    } while (it.hasNext());
                }
                obj = next;
            } else {
                obj = null;
            }
            Collection<? extends C> collection = (Collection) obj;
            if (collection != null && (groupApplicability = getGroupApplicability(collection)) != CandidateApplicability.HIDDEN) {
                ArrayList arrayList = new ArrayList();
                for (Object obj2 : collection) {
                    if (((Candidate) obj2).getResultingApplicability() == groupApplicability) {
                        arrayList.add(obj2);
                    }
                }
                return arrayList;
            }
            return CollectionsKt.emptyList();
        }

        private final CandidateApplicability getGroupApplicability(Collection<? extends C> collection) {
            CandidateApplicability candidateApplicability;
            Iterator<T> it = collection.iterator();
            if (it.hasNext()) {
                CandidateApplicability resultingApplicability = ((Candidate) it.next()).getResultingApplicability();
                while (it.hasNext()) {
                    CandidateApplicability resultingApplicability2 = ((Candidate) it.next()).getResultingApplicability();
                    if (resultingApplicability.compareTo(resultingApplicability2) < 0) {
                        resultingApplicability = resultingApplicability2;
                    }
                }
                candidateApplicability = resultingApplicability;
            } else {
                candidateApplicability = null;
            }
            CandidateApplicability candidateApplicability2 = candidateApplicability;
            return candidateApplicability2 == null ? CandidateApplicability.HIDDEN : candidateApplicability2;
        }
    }
}
