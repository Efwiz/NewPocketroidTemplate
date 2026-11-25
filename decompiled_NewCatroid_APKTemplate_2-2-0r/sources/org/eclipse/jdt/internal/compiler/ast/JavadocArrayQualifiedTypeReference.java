package org.eclipse.jdt.internal.compiler.ast;

import org.eclipse.jdt.internal.compiler.ASTVisitor;
import org.eclipse.jdt.internal.compiler.lookup.BlockScope;
import org.eclipse.jdt.internal.compiler.lookup.ClassScope;
import org.eclipse.jdt.internal.compiler.lookup.Scope;
import org.eclipse.jdt.internal.compiler.lookup.TypeBinding;

/* JADX INFO: loaded from: classes14.dex */
public class JavadocArrayQualifiedTypeReference extends ArrayQualifiedTypeReference {
    public int tagSourceEnd;
    public int tagSourceStart;

    public JavadocArrayQualifiedTypeReference(JavadocQualifiedTypeReference javadocQualifiedTypeReference, int i) {
        super(javadocQualifiedTypeReference.tokens, i, javadocQualifiedTypeReference.sourcePositions);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.TypeReference
    protected void reportInvalidType(Scope scope) {
        scope.problemReporter().javadocInvalidType(this, this.resolvedType, scope.getDeclarationModifiers());
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.TypeReference
    protected void reportDeprecatedType(TypeBinding typeBinding, Scope scope) {
        scope.problemReporter().javadocDeprecatedType(typeBinding, this, scope.getDeclarationModifiers());
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.ArrayQualifiedTypeReference, org.eclipse.jdt.internal.compiler.ast.QualifiedTypeReference, org.eclipse.jdt.internal.compiler.ast.TypeReference, org.eclipse.jdt.internal.compiler.ast.Expression, org.eclipse.jdt.internal.compiler.ast.ASTNode
    public void traverse(ASTVisitor aSTVisitor, BlockScope blockScope) {
        aSTVisitor.visit(this, blockScope);
        aSTVisitor.endVisit(this, blockScope);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.ArrayQualifiedTypeReference, org.eclipse.jdt.internal.compiler.ast.QualifiedTypeReference, org.eclipse.jdt.internal.compiler.ast.TypeReference, org.eclipse.jdt.internal.compiler.ast.Expression
    public void traverse(ASTVisitor aSTVisitor, ClassScope classScope) {
        aSTVisitor.visit(this, classScope);
        aSTVisitor.endVisit(this, classScope);
    }
}
