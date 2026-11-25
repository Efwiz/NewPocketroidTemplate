package org.eclipse.jdt.internal.compiler.ast;

import org.eclipse.jdt.internal.compiler.flow.FlowContext;
import org.eclipse.jdt.internal.compiler.flow.FlowInfo;
import org.eclipse.jdt.internal.compiler.impl.Constant;
import org.eclipse.jdt.internal.compiler.lookup.BlockScope;
import org.eclipse.jdt.internal.compiler.lookup.TypeBinding;

/* JADX INFO: loaded from: classes14.dex */
public abstract class Literal extends Expression {
    @Override // org.eclipse.jdt.internal.compiler.ast.Expression, org.eclipse.jdt.internal.compiler.ast.Statement
    public FlowInfo analyseCode(BlockScope blockScope, FlowContext flowContext, FlowInfo flowInfo) {
        return flowInfo;
    }

    public abstract void computeConstant();

    public abstract TypeBinding literalType(BlockScope blockScope);

    public abstract char[] source();

    public Literal(int i, int i2) {
        this.sourceStart = i;
        this.sourceEnd = i2;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression
    public StringBuffer printExpression(int i, StringBuffer stringBuffer) {
        return stringBuffer.append(source());
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression
    public TypeBinding resolveType(BlockScope blockScope) {
        this.resolvedType = literalType(blockScope);
        computeConstant();
        if (this.constant == null) {
            blockScope.problemReporter().constantOutOfRange(this, this.resolvedType);
            this.constant = Constant.NotAConstant;
        }
        return this.resolvedType;
    }
}
