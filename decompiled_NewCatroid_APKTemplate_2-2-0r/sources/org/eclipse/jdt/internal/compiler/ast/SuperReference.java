package org.eclipse.jdt.internal.compiler.ast;

import org.eclipse.jdt.internal.compiler.ASTVisitor;
import org.eclipse.jdt.internal.compiler.impl.Constant;
import org.eclipse.jdt.internal.compiler.lookup.BlockScope;
import org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding;
import org.eclipse.jdt.internal.compiler.lookup.TypeBinding;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;

/* JADX INFO: loaded from: classes14.dex */
public class SuperReference extends ThisReference {
    @Override // org.eclipse.jdt.internal.compiler.ast.ThisReference, org.eclipse.jdt.internal.compiler.ast.ASTNode
    public boolean isImplicitThis() {
        return false;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.ASTNode
    public boolean isSuper() {
        return true;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.ThisReference, org.eclipse.jdt.internal.compiler.ast.ASTNode
    public boolean isThis() {
        return false;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.ASTNode
    public boolean isUnqualifiedSuper() {
        return true;
    }

    public SuperReference(int i, int i2) {
        super(i, i2);
    }

    public static ExplicitConstructorCall implicitSuperConstructorCall() {
        return new ExplicitConstructorCall(1);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.ThisReference, org.eclipse.jdt.internal.compiler.ast.Expression
    public StringBuffer printExpression(int i, StringBuffer stringBuffer) {
        return stringBuffer.append(PsiKeyword.SUPER);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.ThisReference, org.eclipse.jdt.internal.compiler.ast.Expression
    public TypeBinding resolveType(BlockScope blockScope) {
        this.constant = Constant.NotAConstant;
        ReferenceBinding referenceBindingEnclosingReceiverType = blockScope.enclosingReceiverType();
        if (!checkAccess(blockScope, referenceBindingEnclosingReceiverType)) {
            return null;
        }
        if (referenceBindingEnclosingReceiverType.f626id == 1) {
            blockScope.problemReporter().cannotUseSuperInJavaLangObject(this);
            return null;
        }
        ReferenceBinding referenceBindingSuperclass = referenceBindingEnclosingReceiverType.superclass();
        this.resolvedType = referenceBindingSuperclass;
        return referenceBindingSuperclass;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.ThisReference, org.eclipse.jdt.internal.compiler.ast.Expression, org.eclipse.jdt.internal.compiler.ast.ASTNode
    public void traverse(ASTVisitor aSTVisitor, BlockScope blockScope) {
        aSTVisitor.visit(this, blockScope);
        aSTVisitor.endVisit(this, blockScope);
    }
}
