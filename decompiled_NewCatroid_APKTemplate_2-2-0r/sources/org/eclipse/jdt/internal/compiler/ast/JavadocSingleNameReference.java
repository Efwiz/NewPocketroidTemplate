package org.eclipse.jdt.internal.compiler.ast;

import org.eclipse.jdt.internal.compiler.ASTVisitor;
import org.eclipse.jdt.internal.compiler.lookup.BlockScope;
import org.eclipse.jdt.internal.compiler.lookup.ClassScope;
import org.eclipse.jdt.internal.compiler.lookup.LocalVariableBinding;
import org.eclipse.jdt.internal.compiler.lookup.MethodScope;

/* JADX INFO: loaded from: classes14.dex */
public class JavadocSingleNameReference extends SingleNameReference {
    public int tagSourceEnd;
    public int tagSourceStart;

    public JavadocSingleNameReference(char[] cArr, long j, int i, int i2) {
        super(cArr, j);
        this.tagSourceStart = i;
        this.tagSourceEnd = i2;
        this.bits |= 32768;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression, org.eclipse.jdt.internal.compiler.ast.Statement
    public void resolve(BlockScope blockScope) {
        resolve(blockScope, true, blockScope.compilerOptions().reportUnusedParameterIncludeDocCommentReference);
    }

    public void resolve(BlockScope blockScope, boolean z, boolean z2) {
        LocalVariableBinding localVariableBindingFindVariable = blockScope.findVariable(this.token);
        if (localVariableBindingFindVariable != null && localVariableBindingFindVariable.isValidBinding() && (localVariableBindingFindVariable.tagBits & 1024) != 0) {
            this.binding = localVariableBindingFindVariable;
            if (z2) {
                localVariableBindingFindVariable.useFlag = 1;
                return;
            }
            return;
        }
        if (z) {
            try {
                blockScope.problemReporter().javadocUndeclaredParamTagName(this.token, this.sourceStart, this.sourceEnd, ((MethodScope) blockScope).referenceMethod().modifiers);
            } catch (Exception unused) {
                blockScope.problemReporter().javadocUndeclaredParamTagName(this.token, this.sourceStart, this.sourceEnd, -1);
            }
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.SingleNameReference, org.eclipse.jdt.internal.compiler.ast.Expression, org.eclipse.jdt.internal.compiler.ast.ASTNode
    public void traverse(ASTVisitor aSTVisitor, BlockScope blockScope) {
        aSTVisitor.visit(this, blockScope);
        aSTVisitor.endVisit(this, blockScope);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.SingleNameReference, org.eclipse.jdt.internal.compiler.ast.Expression
    public void traverse(ASTVisitor aSTVisitor, ClassScope classScope) {
        aSTVisitor.visit(this, classScope);
        aSTVisitor.endVisit(this, classScope);
    }
}
