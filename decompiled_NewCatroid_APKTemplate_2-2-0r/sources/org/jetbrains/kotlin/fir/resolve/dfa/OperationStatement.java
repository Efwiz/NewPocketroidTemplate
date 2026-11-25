package org.jetbrains.kotlin.fir.resolve.dfa;

import io.netty.util.internal.StringUtil;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: statements.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J\t\u0010\f\u001a\u00020\u0005HÆ\u0003J\u001d\u0010\r\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011HÖ\u0003J\t\u0010\u0012\u001a\u00020\u0013HÖ\u0001J\b\u0010\u0014\u001a\u00020\u0015H\u0016R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/dfa/OperationStatement;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/Statement;", "variable", "Lorg/jetbrains/kotlin/fir/resolve/dfa/DataFlowVariable;", "operation", "Lorg/jetbrains/kotlin/fir/resolve/dfa/Operation;", "(Lorg/jetbrains/kotlin/fir/resolve/dfa/DataFlowVariable;Lorg/jetbrains/kotlin/fir/resolve/dfa/Operation;)V", "getOperation", "()Lorg/jetbrains/kotlin/fir/resolve/dfa/Operation;", "getVariable", "()Lorg/jetbrains/kotlin/fir/resolve/dfa/DataFlowVariable;", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "toString", "", "semantics"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class OperationStatement extends Statement {
    private final Operation operation;
    private final DataFlowVariable variable;

    public static /* synthetic */ OperationStatement copy$default(OperationStatement operationStatement, DataFlowVariable dataFlowVariable, Operation operation, int i, Object obj) {
        if ((i & 1) != 0) {
            dataFlowVariable = operationStatement.variable;
        }
        if ((i & 2) != 0) {
            operation = operationStatement.operation;
        }
        return operationStatement.copy(dataFlowVariable, operation);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final DataFlowVariable getVariable() {
        return this.variable;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final Operation getOperation() {
        return this.operation;
    }

    public final OperationStatement copy(DataFlowVariable variable, Operation operation) {
        Intrinsics.checkNotNullParameter(variable, "variable");
        Intrinsics.checkNotNullParameter(operation, "operation");
        return new OperationStatement(variable, operation);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof OperationStatement)) {
            return false;
        }
        OperationStatement operationStatement = (OperationStatement) other;
        return Intrinsics.areEqual(this.variable, operationStatement.variable) && this.operation == operationStatement.operation;
    }

    public int hashCode() {
        return (this.variable.hashCode() * 31) + this.operation.hashCode();
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public OperationStatement(DataFlowVariable variable, Operation operation) {
        super(null);
        Intrinsics.checkNotNullParameter(variable, "variable");
        Intrinsics.checkNotNullParameter(operation, "operation");
        this.variable = variable;
        this.operation = operation;
    }

    public final Operation getOperation() {
        return this.operation;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.dfa.Statement
    public DataFlowVariable getVariable() {
        return this.variable;
    }

    public String toString() {
        return new StringBuilder().append(getVariable()).append(StringUtil.SPACE).append(this.operation).toString();
    }
}
