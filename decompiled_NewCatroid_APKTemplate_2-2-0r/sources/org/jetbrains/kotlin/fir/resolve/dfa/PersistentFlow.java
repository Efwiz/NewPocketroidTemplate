package org.jetbrains.kotlin.fir.resolve.dfa;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.kotlinx.collections.immutable.PersistentList;
import org.jetbrains.kotlin.kotlinx.collections.immutable.PersistentMap;
import org.jetbrains.kotlin.kotlinx.collections.immutable.PersistentSet;

/* JADX INFO: compiled from: Flow.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\"\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u0081\u0001\b\u0000\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0000\u0012\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004\u0012\u0018\u0010\u0007\u001a\u0014\u0012\u0004\u0012\u00020\b\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t0\u0004\u0012\u0012\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\f0\u0004\u0012\u0012\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\u0004\u0012\u0018\u0010\u000e\u001a\u0014\u0012\u0004\u0012\u00020\u0005\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u000f0\u0004¢\u0006\u0002\u0010\u0010J\u0006\u0010\u001a\u001a\u00020\u001bJ\u0012\u0010\u001c\u001a\u0004\u0018\u00010\u001d2\u0006\u0010\u001e\u001a\u00020\u0005H\u0016J\u0010\u0010\u001f\u001a\u0004\u0018\u00010\u00002\u0006\u0010 \u001a\u00020\u0000J\u0010\u0010!\u001a\u00020\u00052\u0006\u0010\u001e\u001a\u00020\u0005H\u0016R\u001a\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R \u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\f0\u0004X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R \u0010\u000e\u001a\u0014\u0012\u0004\u0012\u00020\u0005\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u000f0\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R \u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\u0004X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0012R\u001a\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00050\u00158VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0017R\u000e\u0010\u0018\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R&\u0010\u0007\u001a\u0014\u0012\u0004\u0012\u00020\b\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t0\u0004X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0012R\u0010\u0010\u0002\u001a\u0004\u0018\u00010\u0000X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\""}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/dfa/PersistentFlow;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/Flow;", "previousFlow", "approvedTypeStatements", "Lorg/jetbrains/kotlin/kotlinx/collections/immutable/PersistentMap;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/RealVariable;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/PersistentTypeStatement;", "logicStatements", "Lorg/jetbrains/kotlin/fir/resolve/dfa/DataFlowVariable;", "Lorg/jetbrains/kotlin/kotlinx/collections/immutable/PersistentList;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/Implication;", "assignmentIndex", "", "directAliasMap", "backwardsAliasMap", "Lorg/jetbrains/kotlin/kotlinx/collections/immutable/PersistentSet;", "(Lorg/jetbrains/kotlin/fir/resolve/dfa/PersistentFlow;Lkotlinx/collections/immutable/PersistentMap;Lkotlinx/collections/immutable/PersistentMap;Lkotlinx/collections/immutable/PersistentMap;Lkotlinx/collections/immutable/PersistentMap;Lkotlinx/collections/immutable/PersistentMap;)V", "getAssignmentIndex$semantics", "()Lkotlinx/collections/immutable/PersistentMap;", "getDirectAliasMap$semantics", "knownVariables", "", "getKnownVariables", "()Ljava/util/Set;", FirebaseAnalytics.Param.LEVEL, "getLogicStatements$semantics", "fork", "Lorg/jetbrains/kotlin/fir/resolve/dfa/MutableFlow;", "getTypeStatement", "Lorg/jetbrains/kotlin/fir/resolve/dfa/TypeStatement;", "variable", "lowestCommonAncestor", "other", "unwrapVariable", "semantics"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PersistentFlow extends Flow {
    private final PersistentMap<RealVariable, PersistentTypeStatement> approvedTypeStatements;
    private final PersistentMap<RealVariable, Integer> assignmentIndex;
    private final PersistentMap<RealVariable, PersistentSet<RealVariable>> backwardsAliasMap;
    private final PersistentMap<RealVariable, RealVariable> directAliasMap;
    private final int level;
    private final PersistentMap<DataFlowVariable, PersistentList<Implication>> logicStatements;
    private final PersistentFlow previousFlow;

    public final PersistentMap<DataFlowVariable, PersistentList<Implication>> getLogicStatements$semantics() {
        return this.logicStatements;
    }

    public final PersistentMap<RealVariable, Integer> getAssignmentIndex$semantics() {
        return this.assignmentIndex;
    }

    public final PersistentMap<RealVariable, RealVariable> getDirectAliasMap$semantics() {
        return this.directAliasMap;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public PersistentFlow(PersistentFlow persistentFlow, PersistentMap<RealVariable, PersistentTypeStatement> approvedTypeStatements, PersistentMap<DataFlowVariable, ? extends PersistentList<Implication>> logicStatements, PersistentMap<RealVariable, Integer> assignmentIndex, PersistentMap<RealVariable, RealVariable> directAliasMap, PersistentMap<RealVariable, ? extends PersistentSet<RealVariable>> backwardsAliasMap) {
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
        this.level = persistentFlow != null ? persistentFlow.level + 1 : 0;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.dfa.Flow
    public Set<RealVariable> getKnownVariables() {
        return SetsKt.plus(this.approvedTypeStatements.keySet(), (Iterable) this.directAliasMap.keySet());
    }

    @Override // org.jetbrains.kotlin.fir.resolve.dfa.Flow
    public RealVariable unwrapVariable(RealVariable variable) {
        Intrinsics.checkNotNullParameter(variable, "variable");
        RealVariable realVariable = (RealVariable) this.directAliasMap.get(variable);
        return realVariable == null ? variable : realVariable;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.dfa.Flow
    public TypeStatement getTypeStatement(RealVariable variable) {
        Intrinsics.checkNotNullParameter(variable, "variable");
        PersistentTypeStatement persistentTypeStatement = (PersistentTypeStatement) this.approvedTypeStatements.get(unwrapVariable(variable));
        return persistentTypeStatement != null ? PersistentTypeStatement.copy$default(persistentTypeStatement, variable, null, 2, null) : null;
    }

    public final PersistentFlow lowestCommonAncestor(PersistentFlow other) {
        Intrinsics.checkNotNullParameter(other, "other");
        PersistentFlow persistentFlow = this;
        while (persistentFlow.level > other.level) {
            persistentFlow = persistentFlow.previousFlow;
            if (persistentFlow == null) {
                return null;
            }
        }
        while (other.level > persistentFlow.level) {
            other = other.previousFlow;
            if (other == null) {
                return null;
            }
        }
        while (!Intrinsics.areEqual(persistentFlow, other)) {
            persistentFlow = persistentFlow.previousFlow;
            if (persistentFlow == null || (other = other.previousFlow) == null) {
                return null;
            }
        }
        return persistentFlow;
    }

    public final MutableFlow fork() {
        return new MutableFlow(this, this.approvedTypeStatements.builder(), this.logicStatements.builder(), this.assignmentIndex.builder(), this.directAliasMap.builder(), this.backwardsAliasMap.builder());
    }
}
