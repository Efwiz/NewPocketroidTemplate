package org.jetbrains.kotlin.fir.resolve.dfa;

import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.kotlinx.collections.immutable.PersistentList;
import org.jetbrains.kotlin.kotlinx.collections.immutable.PersistentMap;
import org.jetbrains.kotlin.kotlinx.collections.immutable.PersistentSet;

/* JADX INFO: compiled from: Flow.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\"\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0016¢\u0006\u0002\u0010\u0002B\u0081\u0001\b\u0000\u0012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0004\u0012\u0012\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u0006\u0012\u0018\u0010\t\u001a\u0014\u0012\u0004\u0012\u00020\n\u0012\n\u0012\b\u0012\u0004\u0012\u00020\f0\u000b0\u0006\u0012\u0012\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u000e0\u0006\u0012\u0012\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00070\u0006\u0012\u0018\u0010\u0010\u001a\u0014\u0012\u0004\u0012\u00020\u0007\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u00110\u0006¢\u0006\u0002\u0010\u0012J\u0006\u0010\u001d\u001a\u00020\u0004J\u0012\u0010\u001e\u001a\u0004\u0018\u00010\u001f2\u0006\u0010 \u001a\u00020\u0007H\u0016J\u0010\u0010!\u001a\u00020\u00072\u0006\u0010 \u001a\u00020\u0007H\u0016R \u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u0006X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R \u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u000e0\u0006X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0014R&\u0010\u0010\u001a\u0014\u0012\u0004\u0012\u00020\u0007\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u00110\u0006X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0014R \u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00070\u0006X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0014R\u001a\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00070\u00198VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001a\u0010\u001bR&\u0010\t\u001a\u0014\u0012\u0004\u0012\u00020\n\u0012\n\u0012\b\u0012\u0004\u0012\u00020\f0\u000b0\u0006X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0014R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\""}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/dfa/MutableFlow;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/Flow;", "()V", "previousFlow", "Lorg/jetbrains/kotlin/fir/resolve/dfa/PersistentFlow;", "approvedTypeStatements", "Lorg/jetbrains/kotlin/kotlinx/collections/immutable/PersistentMap$Builder;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/RealVariable;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/PersistentTypeStatement;", "logicStatements", "Lorg/jetbrains/kotlin/fir/resolve/dfa/DataFlowVariable;", "Lorg/jetbrains/kotlin/kotlinx/collections/immutable/PersistentList;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/Implication;", "assignmentIndex", "", "directAliasMap", "backwardsAliasMap", "Lorg/jetbrains/kotlin/kotlinx/collections/immutable/PersistentSet;", "(Lorg/jetbrains/kotlin/fir/resolve/dfa/PersistentFlow;Lkotlinx/collections/immutable/PersistentMap$Builder;Lkotlinx/collections/immutable/PersistentMap$Builder;Lkotlinx/collections/immutable/PersistentMap$Builder;Lkotlinx/collections/immutable/PersistentMap$Builder;Lkotlinx/collections/immutable/PersistentMap$Builder;)V", "getApprovedTypeStatements$semantics", "()Lkotlinx/collections/immutable/PersistentMap$Builder;", "getAssignmentIndex$semantics", "getBackwardsAliasMap$semantics", "getDirectAliasMap$semantics", "knownVariables", "", "getKnownVariables", "()Ljava/util/Set;", "getLogicStatements$semantics", "freeze", "getTypeStatement", "Lorg/jetbrains/kotlin/fir/resolve/dfa/TypeStatement;", "variable", "unwrapVariable", "semantics"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class MutableFlow extends Flow {
    private final PersistentMap.Builder<RealVariable, PersistentTypeStatement> approvedTypeStatements;
    private final PersistentMap.Builder<RealVariable, Integer> assignmentIndex;
    private final PersistentMap.Builder<RealVariable, PersistentSet<RealVariable>> backwardsAliasMap;
    private final PersistentMap.Builder<RealVariable, RealVariable> directAliasMap;
    private final PersistentMap.Builder<DataFlowVariable, PersistentList<Implication>> logicStatements;
    private final PersistentFlow previousFlow;

    public final PersistentMap.Builder<RealVariable, PersistentTypeStatement> getApprovedTypeStatements$semantics() {
        return this.approvedTypeStatements;
    }

    public final PersistentMap.Builder<DataFlowVariable, PersistentList<Implication>> getLogicStatements$semantics() {
        return this.logicStatements;
    }

    public final PersistentMap.Builder<RealVariable, Integer> getAssignmentIndex$semantics() {
        return this.assignmentIndex;
    }

    public final PersistentMap.Builder<RealVariable, RealVariable> getDirectAliasMap$semantics() {
        return this.directAliasMap;
    }

    public final PersistentMap.Builder<RealVariable, PersistentSet<RealVariable>> getBackwardsAliasMap$semantics() {
        return this.backwardsAliasMap;
    }

    public MutableFlow(PersistentFlow persistentFlow, PersistentMap.Builder<RealVariable, PersistentTypeStatement> approvedTypeStatements, PersistentMap.Builder<DataFlowVariable, PersistentList<Implication>> logicStatements, PersistentMap.Builder<RealVariable, Integer> assignmentIndex, PersistentMap.Builder<RealVariable, RealVariable> directAliasMap, PersistentMap.Builder<RealVariable, PersistentSet<RealVariable>> backwardsAliasMap) {
        Intrinsics.checkNotNullParameter(approvedTypeStatements, "approvedTypeStatements");
        Intrinsics.checkNotNullParameter(logicStatements, "logicStatements");
        Intrinsics.checkNotNullParameter(assignmentIndex, "assignmentIndex");
        Intrinsics.checkNotNullParameter(directAliasMap, "directAliasMap");
        Intrinsics.checkNotNullParameter(backwardsAliasMap, "backwardsAliasMap");
        this.previousFlow = persistentFlow;
        this.approvedTypeStatements = approvedTypeStatements;
        this.logicStatements = logicStatements;
        this.assignmentIndex = assignmentIndex;
        this.directAliasMap = directAliasMap;
        this.backwardsAliasMap = backwardsAliasMap;
    }

    public MutableFlow() {
        this(null, FlowKt.emptyPersistentHashMapBuilder(), FlowKt.emptyPersistentHashMapBuilder(), FlowKt.emptyPersistentHashMapBuilder(), FlowKt.emptyPersistentHashMapBuilder(), FlowKt.emptyPersistentHashMapBuilder());
    }

    @Override // org.jetbrains.kotlin.fir.resolve.dfa.Flow
    public Set<RealVariable> getKnownVariables() {
        return SetsKt.plus((Set) this.approvedTypeStatements.keySet(), (Iterable) this.directAliasMap.keySet());
    }

    @Override // org.jetbrains.kotlin.fir.resolve.dfa.Flow
    public RealVariable unwrapVariable(RealVariable variable) {
        Intrinsics.checkNotNullParameter(variable, "variable");
        RealVariable realVariable = this.directAliasMap.get(variable);
        return realVariable == null ? variable : realVariable;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.dfa.Flow
    public TypeStatement getTypeStatement(RealVariable variable) {
        Intrinsics.checkNotNullParameter(variable, "variable");
        PersistentTypeStatement persistentTypeStatement = this.approvedTypeStatements.get(unwrapVariable(variable));
        return persistentTypeStatement != null ? PersistentTypeStatement.copy$default(persistentTypeStatement, variable, null, 2, null) : null;
    }

    public final PersistentFlow freeze() {
        return new PersistentFlow(this.previousFlow, this.approvedTypeStatements.build(), this.logicStatements.build(), this.assignmentIndex.build(), this.directAliasMap.build(), this.backwardsAliasMap.build());
    }
}
