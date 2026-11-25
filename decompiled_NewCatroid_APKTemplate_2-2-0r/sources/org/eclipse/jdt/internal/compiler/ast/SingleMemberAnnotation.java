package org.eclipse.jdt.internal.compiler.ast;

import org.eclipse.jdt.internal.compiler.ASTVisitor;
import org.eclipse.jdt.internal.compiler.lookup.BlockScope;
import org.eclipse.jdt.internal.compiler.lookup.ClassScope;
import org.eclipse.jdt.internal.compiler.lookup.ElementValuePair;

/* JADX INFO: loaded from: classes14.dex */
public class SingleMemberAnnotation extends Annotation {
    public Expression memberValue;
    private MemberValuePair[] singlePairs;

    public SingleMemberAnnotation(TypeReference typeReference, int i) {
        this.type = typeReference;
        this.sourceStart = i;
        this.sourceEnd = typeReference.sourceEnd;
    }

    public SingleMemberAnnotation() {
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Annotation
    public ElementValuePair[] computeElementValuePairs() {
        return new ElementValuePair[]{memberValuePairs()[0].compilerElementPair};
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Annotation
    public MemberValuePair[] memberValuePairs() {
        if (this.singlePairs == null) {
            this.singlePairs = new MemberValuePair[]{new MemberValuePair(VALUE, this.memberValue.sourceStart, this.memberValue.sourceEnd, this.memberValue)};
        }
        return this.singlePairs;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Annotation, org.eclipse.jdt.internal.compiler.ast.Expression
    public StringBuffer printExpression(int i, StringBuffer stringBuffer) {
        super.printExpression(i, stringBuffer);
        stringBuffer.append('(');
        this.memberValue.printExpression(i, stringBuffer);
        return stringBuffer.append(')');
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Annotation, org.eclipse.jdt.internal.compiler.ast.Expression, org.eclipse.jdt.internal.compiler.ast.ASTNode
    public void traverse(ASTVisitor aSTVisitor, BlockScope blockScope) {
        if (aSTVisitor.visit(this, blockScope)) {
            if (this.type != null) {
                this.type.traverse(aSTVisitor, blockScope);
            }
            Expression expression = this.memberValue;
            if (expression != null) {
                expression.traverse(aSTVisitor, blockScope);
            }
        }
        aSTVisitor.endVisit(this, blockScope);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Annotation, org.eclipse.jdt.internal.compiler.ast.Expression
    public void traverse(ASTVisitor aSTVisitor, ClassScope classScope) {
        if (aSTVisitor.visit(this, classScope)) {
            if (this.type != null) {
                this.type.traverse(aSTVisitor, classScope);
            }
            Expression expression = this.memberValue;
            if (expression != null) {
                expression.traverse(aSTVisitor, classScope);
            }
        }
        aSTVisitor.endVisit(this, classScope);
    }
}
