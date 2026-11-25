package org.eclipse.jdt.internal.compiler.ast;

import org.eclipse.jdt.internal.compiler.ASTVisitor;
import org.eclipse.jdt.internal.compiler.lookup.BlockScope;
import org.eclipse.jdt.internal.compiler.lookup.ClassScope;

/* JADX INFO: loaded from: classes14.dex */
public class MarkerAnnotation extends Annotation {
    public MarkerAnnotation(TypeReference typeReference, int i) {
        this.type = typeReference;
        this.sourceStart = i;
        this.sourceEnd = typeReference.sourceEnd;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Annotation
    public MemberValuePair[] memberValuePairs() {
        return NoValuePairs;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Annotation, org.eclipse.jdt.internal.compiler.ast.Expression, org.eclipse.jdt.internal.compiler.ast.ASTNode
    public void traverse(ASTVisitor aSTVisitor, BlockScope blockScope) {
        if (aSTVisitor.visit(this, blockScope) && this.type != null) {
            this.type.traverse(aSTVisitor, blockScope);
        }
        aSTVisitor.endVisit(this, blockScope);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Annotation, org.eclipse.jdt.internal.compiler.ast.Expression
    public void traverse(ASTVisitor aSTVisitor, ClassScope classScope) {
        if (aSTVisitor.visit(this, classScope) && this.type != null) {
            this.type.traverse(aSTVisitor, classScope);
        }
        aSTVisitor.endVisit(this, classScope);
    }
}
