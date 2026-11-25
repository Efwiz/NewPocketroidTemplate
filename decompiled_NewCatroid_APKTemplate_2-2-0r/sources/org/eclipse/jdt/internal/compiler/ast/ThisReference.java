package org.eclipse.jdt.internal.compiler.ast;

import org.eclipse.jdt.internal.compiler.ASTVisitor;
import org.eclipse.jdt.internal.compiler.codegen.CodeStream;
import org.eclipse.jdt.internal.compiler.flow.FlowContext;
import org.eclipse.jdt.internal.compiler.flow.FlowInfo;
import org.eclipse.jdt.internal.compiler.impl.Constant;
import org.eclipse.jdt.internal.compiler.lookup.BlockScope;
import org.eclipse.jdt.internal.compiler.lookup.ClassScope;
import org.eclipse.jdt.internal.compiler.lookup.MethodBinding;
import org.eclipse.jdt.internal.compiler.lookup.MethodScope;
import org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding;
import org.eclipse.jdt.internal.compiler.lookup.TypeBinding;

/* JADX INFO: loaded from: classes14.dex */
public class ThisReference extends Reference {
    @Override // org.eclipse.jdt.internal.compiler.ast.Reference
    public FlowInfo analyseAssignment(BlockScope blockScope, FlowContext flowContext, FlowInfo flowInfo, Assignment assignment, boolean z) {
        return flowInfo;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Reference, org.eclipse.jdt.internal.compiler.ast.Expression
    public boolean checkNPE(BlockScope blockScope, FlowContext flowContext, FlowInfo flowInfo, int i) {
        return true;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Reference
    public void generateAssignment(BlockScope blockScope, CodeStream codeStream, Assignment assignment, boolean z) {
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Reference
    public void generateCompoundAssignment(BlockScope blockScope, CodeStream codeStream, Expression expression, int i, int i2, boolean z) {
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Reference
    public void generatePostIncrement(BlockScope blockScope, CodeStream codeStream, CompoundAssignment compoundAssignment, boolean z) {
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.ASTNode
    public boolean isThis() {
        return true;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Reference, org.eclipse.jdt.internal.compiler.ast.Expression
    public int nullStatus(FlowInfo flowInfo, FlowContext flowContext) {
        return 4;
    }

    public static ThisReference implicitThis() {
        ThisReference thisReference = new ThisReference(0, 0);
        thisReference.bits |= 4;
        return thisReference;
    }

    public ThisReference(int i, int i2) {
        this.sourceStart = i;
        this.sourceEnd = i2;
    }

    public boolean checkAccess(BlockScope blockScope, ReferenceBinding referenceBinding) {
        TypeDeclaration typeDeclarationReferenceType;
        MethodScope methodScope = blockScope.methodScope();
        if (methodScope.isConstructorCall) {
            methodScope.problemReporter().fieldsOrThisBeforeConstructorInvocation(this);
            return false;
        }
        if (methodScope.isStatic) {
            methodScope.problemReporter().errorThisSuperInStatic(this);
            return false;
        }
        if (isUnqualifiedSuper() && (typeDeclarationReferenceType = methodScope.referenceType()) != null && TypeDeclaration.kind(typeDeclarationReferenceType.modifiers) == 2) {
            methodScope.problemReporter().errorNoSuperInInterface(this);
            return false;
        }
        if (referenceBinding == null) {
            return true;
        }
        blockScope.tagAsAccessingEnclosingInstanceStateOf(referenceBinding, false);
        return true;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression
    public void generateCode(BlockScope blockScope, CodeStream codeStream, boolean z) {
        int i = codeStream.position;
        if (z) {
            codeStream.aload_0();
        }
        if ((this.bits & 4) == 0) {
            codeStream.recordPositionsFrom(i, this.sourceStart);
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.ASTNode
    public boolean isImplicitThis() {
        return (this.bits & 4) != 0;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression
    public StringBuffer printExpression(int i, StringBuffer stringBuffer) {
        return isImplicitThis() ? stringBuffer : stringBuffer.append("this");
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression
    public TypeBinding resolveType(BlockScope blockScope) {
        MethodBinding methodBindingReferenceMethodBinding;
        this.constant = Constant.NotAConstant;
        ReferenceBinding referenceBindingEnclosingReceiverType = blockScope.enclosingReceiverType();
        if (!isImplicitThis() && !checkAccess(blockScope, referenceBindingEnclosingReceiverType)) {
            return null;
        }
        this.resolvedType = referenceBindingEnclosingReceiverType;
        MethodScope methodScopeNamedMethodScope = blockScope.namedMethodScope();
        if (methodScopeNamedMethodScope != null && (methodBindingReferenceMethodBinding = methodScopeNamedMethodScope.referenceMethodBinding()) != null && methodBindingReferenceMethodBinding.receiver != null && TypeBinding.equalsEquals(methodBindingReferenceMethodBinding.receiver, this.resolvedType)) {
            this.resolvedType = methodBindingReferenceMethodBinding.receiver;
        }
        return this.resolvedType;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression, org.eclipse.jdt.internal.compiler.ast.ASTNode
    public void traverse(ASTVisitor aSTVisitor, BlockScope blockScope) {
        aSTVisitor.visit(this, blockScope);
        aSTVisitor.endVisit(this, blockScope);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression
    public void traverse(ASTVisitor aSTVisitor, ClassScope classScope) {
        aSTVisitor.visit(this, classScope);
        aSTVisitor.endVisit(this, classScope);
    }
}
