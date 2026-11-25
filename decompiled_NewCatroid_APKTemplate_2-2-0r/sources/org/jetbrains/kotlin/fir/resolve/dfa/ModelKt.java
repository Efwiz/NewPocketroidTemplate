package org.jetbrains.kotlin.fir.resolve.dfa;

import java.util.LinkedHashSet;
import kotlin.Metadata;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.fir.types.ConeErrorType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;

/* JADX INFO: compiled from: model.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000F\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\u0010\u0001\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0000\u001a\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004H\u0086\u0004\u001a\u0017\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\b\u0010\u0003\u001a\u0004\u0018\u00010\u0005H\u0086\u0004\u001a\u0015\u0010\u0006\u001a\u00020\u0007*\u00020\u00012\u0006\u0010\b\u001a\u00020\tH\u0086\u0004\u001a\u001b\u0010\n\u001a\u00020\u0004*\u00020\u0002\u0082\u0002\u000e\n\f\b\u0000\u0012\u0002\u0018\u0000\u001a\u0004\u0010\u0000(\u0000\u001a\u001b\u0010\f\u001a\u00020\u0004*\u00020\u0002\u0082\u0002\u000e\n\f\b\u0000\u0012\u0002\u0018\u0000\u001a\u0004\u0010\u0000(\u0001\u001a\u0017\u0010\u000e\u001a\u00020\u0001*\u00020\u00022\b\u0010\u0003\u001a\u0004\u0018\u00010\u0005H\u0086\u0004\u001a\u0015\u0010\u000f\u001a\u00020\u0010*\u00020\u000b2\u0006\u0010\u0011\u001a\u00020\u0012H\u0086\u0004*\"\u0010\u0013\"\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u00150\u00142\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u00150\u0014ò\u0001\b\n\u00020\u000b\n\u00020\r¨\u0006\u0016"}, d2 = {"eq", "Lorg/jetbrains/kotlin/fir/resolve/dfa/OperationStatement;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/DataFlowVariable;", "constant", "", "", "implies", "Lorg/jetbrains/kotlin/fir/resolve/dfa/Implication;", "effect", "Lorg/jetbrains/kotlin/fir/resolve/dfa/Statement;", "isReal", "Lorg/jetbrains/kotlin/fir/resolve/dfa/RealVariable;", "isSynthetic", "Lorg/jetbrains/kotlin/fir/resolve/dfa/SyntheticVariable;", "notEq", "typeEq", "Lorg/jetbrains/kotlin/fir/resolve/dfa/MutableTypeStatement;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "TypeStatements", "", "Lorg/jetbrains/kotlin/fir/resolve/dfa/TypeStatement;", "semantics"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ModelKt {
    public static final OperationStatement eq(DataFlowVariable dataFlowVariable, boolean z) {
        Intrinsics.checkNotNullParameter(dataFlowVariable, "<this>");
        return new OperationStatement(dataFlowVariable, z ? Operation.EqTrue : Operation.EqFalse);
    }

    public static final OperationStatement eq(DataFlowVariable dataFlowVariable, Void r2) {
        Intrinsics.checkNotNullParameter(dataFlowVariable, "<this>");
        return new OperationStatement(dataFlowVariable, Operation.EqNull);
    }

    public static final OperationStatement notEq(DataFlowVariable dataFlowVariable, Void r2) {
        Intrinsics.checkNotNullParameter(dataFlowVariable, "<this>");
        return new OperationStatement(dataFlowVariable, Operation.NotEqNull);
    }

    public static final Implication implies(OperationStatement operationStatement, Statement effect) {
        Intrinsics.checkNotNullParameter(operationStatement, "<this>");
        Intrinsics.checkNotNullParameter(effect, "effect");
        return new Implication(operationStatement, effect);
    }

    public static final MutableTypeStatement typeEq(RealVariable realVariable, ConeKotlinType type) {
        Intrinsics.checkNotNullParameter(realVariable, "<this>");
        Intrinsics.checkNotNullParameter(type, "type");
        return new MutableTypeStatement(realVariable, type instanceof ConeErrorType ? new LinkedHashSet() : SetsKt.linkedSetOf(type));
    }

    public static final boolean isSynthetic(DataFlowVariable dataFlowVariable) {
        Intrinsics.checkNotNullParameter(dataFlowVariable, "<this>");
        return dataFlowVariable instanceof SyntheticVariable;
    }

    public static final boolean isReal(DataFlowVariable dataFlowVariable) {
        Intrinsics.checkNotNullParameter(dataFlowVariable, "<this>");
        return dataFlowVariable instanceof RealVariable;
    }
}
