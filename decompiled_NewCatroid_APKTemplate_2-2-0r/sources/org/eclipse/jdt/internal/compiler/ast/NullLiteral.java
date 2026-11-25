package org.eclipse.jdt.internal.compiler.ast;

import org.eclipse.jdt.internal.compiler.ASTVisitor;
import org.eclipse.jdt.internal.compiler.codegen.CodeStream;
import org.eclipse.jdt.internal.compiler.flow.FlowContext;
import org.eclipse.jdt.internal.compiler.flow.FlowInfo;
import org.eclipse.jdt.internal.compiler.impl.Constant;
import org.eclipse.jdt.internal.compiler.lookup.BlockScope;
import org.eclipse.jdt.internal.compiler.lookup.TypeBinding;

/* JADX INFO: loaded from: classes14.dex */
public class NullLiteral extends MagicLiteral {
    static final char[] source = {'n', 'u', 'l', 'l'};

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression
    public int nullStatus(FlowInfo flowInfo, FlowContext flowContext) {
        return 2;
    }

    public NullLiteral(int i, int i2) {
        super(i, i2);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Literal
    public void computeConstant() {
        this.constant = Constant.NotAConstant;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression
    public void generateCode(BlockScope blockScope, CodeStream codeStream, boolean z) {
        int i = codeStream.position;
        if (z) {
            codeStream.aconst_null();
            codeStream.generateImplicitConversion(this.implicitConversion);
        }
        codeStream.recordPositionsFrom(i, this.sourceStart);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Literal
    public TypeBinding literalType(BlockScope blockScope) {
        return TypeBinding.NULL;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression
    public Object reusableJSRTarget() {
        return TypeBinding.NULL;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.MagicLiteral, org.eclipse.jdt.internal.compiler.ast.Literal
    public char[] source() {
        return source;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression, org.eclipse.jdt.internal.compiler.ast.ASTNode
    public void traverse(ASTVisitor aSTVisitor, BlockScope blockScope) {
        aSTVisitor.visit(this, blockScope);
        aSTVisitor.endVisit(this, blockScope);
    }
}
