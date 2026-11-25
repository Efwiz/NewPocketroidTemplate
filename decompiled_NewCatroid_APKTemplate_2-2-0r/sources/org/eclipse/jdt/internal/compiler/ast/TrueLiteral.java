package org.eclipse.jdt.internal.compiler.ast;

import net.bytebuddy.asm.Advice;
import org.eclipse.jdt.internal.compiler.ASTVisitor;
import org.eclipse.jdt.internal.compiler.codegen.BranchLabel;
import org.eclipse.jdt.internal.compiler.codegen.CodeStream;
import org.eclipse.jdt.internal.compiler.impl.BooleanConstant;
import org.eclipse.jdt.internal.compiler.lookup.BlockScope;
import org.eclipse.jdt.internal.compiler.lookup.TypeBinding;

/* JADX INFO: loaded from: classes14.dex */
public class TrueLiteral extends MagicLiteral {
    static final char[] source = {Advice.OffsetMapping.ForOrigin.Renderer.ForTypeName.SYMBOL, Advice.OffsetMapping.ForOrigin.Renderer.ForReturnTypeName.SYMBOL, 'u', 'e'};

    public TrueLiteral(int i, int i2) {
        super(i, i2);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Literal
    public void computeConstant() {
        this.constant = BooleanConstant.fromValue(true);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression
    public void generateCode(BlockScope blockScope, CodeStream codeStream, boolean z) {
        int i = codeStream.position;
        if (z) {
            codeStream.generateConstant(this.constant, this.implicitConversion);
        }
        codeStream.recordPositionsFrom(i, this.sourceStart);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression
    public void generateOptimizedBoolean(BlockScope blockScope, CodeStream codeStream, BranchLabel branchLabel, BranchLabel branchLabel2, boolean z) {
        int i = codeStream.position;
        if (z && branchLabel2 == null && branchLabel != null) {
            codeStream.goto_(branchLabel);
        }
        codeStream.recordPositionsFrom(i, this.sourceStart);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Literal
    public TypeBinding literalType(BlockScope blockScope) {
        return TypeBinding.BOOLEAN;
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
