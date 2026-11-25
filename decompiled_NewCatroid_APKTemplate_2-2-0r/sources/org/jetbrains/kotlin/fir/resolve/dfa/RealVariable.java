package org.jetbrains.kotlin.fir.resolve.dfa;

import java.util.LinkedHashSet;
import java.util.Set;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: DfaVariables.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010#\n\u0002\b\u000b\n\u0002\u0010\u0000\n\u0002\b\u0002\u0018\u00002\u00020\u0001B/\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0001\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n¢\u0006\u0002\u0010\u000bJ\u0013\u0010\u001c\u001a\u00020\u00052\b\u0010\u001d\u001a\u0004\u0018\u00010\u001eH\u0096\u0002J\b\u0010\u001f\u001a\u00020\bH\u0016R\u001b\u0010\f\u001a\u00020\b8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u000f\u0010\u0010\u001a\u0004\b\r\u0010\u000eR\u0017\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00000\u0012¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\u0019R\u0011\u0010\t\u001a\u00020\n¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001b¨\u0006 "}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/dfa/RealVariable;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/DataFlowVariable;", "identifier", "Lorg/jetbrains/kotlin/fir/resolve/dfa/Identifier;", "isThisReference", "", "explicitReceiverVariable", "variableIndexForDebug", "", "stability", "Lorg/jetbrains/kotlin/fir/resolve/dfa/PropertyStability;", "(Lorg/jetbrains/kotlin/fir/resolve/dfa/Identifier;ZLorg/jetbrains/kotlin/fir/resolve/dfa/DataFlowVariable;ILorg/jetbrains/kotlin/fir/resolve/dfa/PropertyStability;)V", "_hashCode", "get_hashCode", "()I", "_hashCode$delegate", "Lkotlin/Lazy;", "dependentVariables", "", "getDependentVariables", "()Ljava/util/Set;", "getExplicitReceiverVariable", "()Lorg/jetbrains/kotlin/fir/resolve/dfa/DataFlowVariable;", "getIdentifier", "()Lorg/jetbrains/kotlin/fir/resolve/dfa/Identifier;", "()Z", "getStability", "()Lorg/jetbrains/kotlin/fir/resolve/dfa/PropertyStability;", Namer.EQUALS_METHOD_NAME, "other", "", "hashCode", "semantics"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class RealVariable extends DataFlowVariable {

    /* JADX INFO: renamed from: _hashCode$delegate, reason: from kotlin metadata */
    private final Lazy _hashCode;
    private final Set<RealVariable> dependentVariables;
    private final DataFlowVariable explicitReceiverVariable;
    private final Identifier identifier;
    private final boolean isThisReference;
    private final PropertyStability stability;

    public boolean equals(Object other) {
        return this == other;
    }

    public final Identifier getIdentifier() {
        return this.identifier;
    }

    /* JADX INFO: renamed from: isThisReference, reason: from getter */
    public final boolean getIsThisReference() {
        return this.isThisReference;
    }

    public final DataFlowVariable getExplicitReceiverVariable() {
        return this.explicitReceiverVariable;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public RealVariable(Identifier identifier, boolean z, DataFlowVariable dataFlowVariable, int i, PropertyStability stability) {
        super(i, null);
        Intrinsics.checkNotNullParameter(identifier, "identifier");
        Intrinsics.checkNotNullParameter(stability, "stability");
        this.identifier = identifier;
        this.isThisReference = z;
        this.explicitReceiverVariable = dataFlowVariable;
        this.dependentVariables = new LinkedHashSet();
        RealVariable realVariable = dataFlowVariable instanceof RealVariable ? (RealVariable) dataFlowVariable : null;
        this.stability = stability.combineWithReceiverStability(realVariable != null ? realVariable.stability : null);
        this._hashCode = LazyKt.lazy(new Function0<Integer>() { // from class: org.jetbrains.kotlin.fir.resolve.dfa.RealVariable$_hashCode$2
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final Integer invoke() {
                int iHashCode = this.this$0.getIdentifier().hashCode() * 31;
                DataFlowVariable explicitReceiverVariable = this.this$0.getExplicitReceiverVariable();
                return Integer.valueOf(iHashCode + (explicitReceiverVariable != null ? explicitReceiverVariable.hashCode() : 0));
            }
        });
        if (dataFlowVariable instanceof RealVariable) {
            ((RealVariable) dataFlowVariable).dependentVariables.add(this);
        }
    }

    public final Set<RealVariable> getDependentVariables() {
        return this.dependentVariables;
    }

    public final PropertyStability getStability() {
        return this.stability;
    }

    private final int get_hashCode() {
        return ((Number) this._hashCode.getValue()).intValue();
    }

    public int hashCode() {
        return get_hashCode();
    }
}
