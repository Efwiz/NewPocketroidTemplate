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
public class QualifiedThisReference extends ThisReference {
    ReferenceBinding currentCompatibleType;
    public TypeReference qualification;

    @Override // org.eclipse.jdt.internal.compiler.ast.Reference, org.eclipse.jdt.internal.compiler.ast.Expression, org.eclipse.jdt.internal.compiler.ast.Statement
    public FlowInfo analyseCode(BlockScope blockScope, FlowContext flowContext, FlowInfo flowInfo) {
        return flowInfo;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression
    public FlowInfo analyseCode(BlockScope blockScope, FlowContext flowContext, FlowInfo flowInfo, boolean z) {
        return flowInfo;
    }

    public QualifiedThisReference(TypeReference typeReference, int i, int i2) {
        super(i, i2);
        this.qualification = typeReference;
        typeReference.bits |= 1073741824;
        this.sourceStart = typeReference.sourceStart;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.ThisReference, org.eclipse.jdt.internal.compiler.ast.Expression
    public void generateCode(BlockScope blockScope, CodeStream codeStream, boolean z) {
        int i = codeStream.position;
        if (z) {
            if ((this.bits & ASTNode.DepthMASK) != 0) {
                codeStream.generateOuterAccess(blockScope.getEmulationPath(this.currentCompatibleType, true, false), this, this.currentCompatibleType, blockScope);
            } else {
                codeStream.aload_0();
            }
        }
        codeStream.recordPositionsFrom(i, this.sourceStart);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.ThisReference, org.eclipse.jdt.internal.compiler.ast.Expression
    public TypeBinding resolveType(BlockScope blockScope) {
        MethodBinding methodBindingReferenceMethodBinding;
        this.constant = Constant.NotAConstant;
        TypeBinding typeBindingResolveType = this.qualification.resolveType(blockScope, true);
        if (typeBindingResolveType == null || !typeBindingResolveType.isValidBinding()) {
            return null;
        }
        TypeBinding typeBindingErasure = typeBindingResolveType.erasure();
        if (typeBindingErasure instanceof ReferenceBinding) {
            this.resolvedType = blockScope.environment().convertToParameterizedType((ReferenceBinding) typeBindingErasure);
        } else {
            this.resolvedType = typeBindingErasure;
        }
        int iFindCompatibleEnclosing = findCompatibleEnclosing(blockScope.referenceType().binding, typeBindingErasure, blockScope);
        this.bits &= -8161;
        this.bits |= (iFindCompatibleEnclosing & 255) << 5;
        ReferenceBinding referenceBinding = this.currentCompatibleType;
        if (referenceBinding == null) {
            if (this.resolvedType.isValidBinding()) {
                blockScope.problemReporter().noSuchEnclosingInstance(typeBindingErasure, this, false);
            }
            return this.resolvedType;
        }
        blockScope.tagAsAccessingEnclosingInstanceStateOf(referenceBinding, false);
        if (iFindCompatibleEnclosing == 0) {
            checkAccess(blockScope, null);
        }
        MethodScope methodScopeNamedMethodScope = blockScope.namedMethodScope();
        if (methodScopeNamedMethodScope != null && (methodBindingReferenceMethodBinding = methodScopeNamedMethodScope.referenceMethodBinding()) != null) {
            for (TypeBinding typeBindingEnclosingType = methodBindingReferenceMethodBinding.receiver; typeBindingEnclosingType != null; typeBindingEnclosingType = typeBindingEnclosingType.enclosingType()) {
                if (TypeBinding.equalsEquals(typeBindingEnclosingType, this.resolvedType)) {
                    this.resolvedType = typeBindingEnclosingType;
                    return typeBindingEnclosingType;
                }
            }
        }
        return this.resolvedType;
    }

    int findCompatibleEnclosing(ReferenceBinding referenceBinding, TypeBinding typeBinding, BlockScope blockScope) {
        this.currentCompatibleType = referenceBinding;
        int i = 0;
        while (true) {
            ReferenceBinding referenceBinding2 = this.currentCompatibleType;
            if (referenceBinding2 == null || !TypeBinding.notEquals(referenceBinding2, typeBinding)) {
                break;
            }
            i++;
            this.currentCompatibleType = this.currentCompatibleType.isStatic() ? null : this.currentCompatibleType.enclosingType();
        }
        return i;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.ThisReference, org.eclipse.jdt.internal.compiler.ast.Expression
    public StringBuffer printExpression(int i, StringBuffer stringBuffer) {
        return this.qualification.print(0, stringBuffer).append(".this");
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.ThisReference, org.eclipse.jdt.internal.compiler.ast.Expression, org.eclipse.jdt.internal.compiler.ast.ASTNode
    public void traverse(ASTVisitor aSTVisitor, BlockScope blockScope) {
        if (aSTVisitor.visit(this, blockScope)) {
            this.qualification.traverse(aSTVisitor, blockScope);
        }
        aSTVisitor.endVisit(this, blockScope);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.ThisReference, org.eclipse.jdt.internal.compiler.ast.Expression
    public void traverse(ASTVisitor aSTVisitor, ClassScope classScope) {
        if (aSTVisitor.visit(this, classScope)) {
            this.qualification.traverse(aSTVisitor, classScope);
        }
        aSTVisitor.endVisit(this, classScope);
    }
}
