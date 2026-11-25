package org.eclipse.jdt.internal.compiler.ast;

import org.eclipse.jdt.internal.compiler.ASTVisitor;
import org.eclipse.jdt.internal.compiler.classfmt.ClassFileConstants;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.eclipse.jdt.internal.compiler.lookup.BlockScope;
import org.eclipse.jdt.internal.compiler.lookup.ClassScope;
import org.eclipse.jdt.internal.compiler.lookup.ProblemReferenceBinding;
import org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding;
import org.eclipse.jdt.internal.compiler.lookup.TypeBinding;

/* JADX INFO: loaded from: classes14.dex */
public class QualifiedSuperReference extends QualifiedThisReference {
    @Override // org.eclipse.jdt.internal.compiler.ast.ASTNode, org.eclipse.jdt.internal.compiler.lookup.InvocationSite
    public boolean isQualifiedSuper() {
        return true;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.ASTNode
    public boolean isSuper() {
        return true;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.ThisReference, org.eclipse.jdt.internal.compiler.ast.ASTNode
    public boolean isThis() {
        return false;
    }

    public QualifiedSuperReference(TypeReference typeReference, int i, int i2) {
        super(typeReference, i, i2);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.QualifiedThisReference, org.eclipse.jdt.internal.compiler.ast.ThisReference, org.eclipse.jdt.internal.compiler.ast.Expression
    public StringBuffer printExpression(int i, StringBuffer stringBuffer) {
        return this.qualification.print(0, stringBuffer).append(".super");
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.QualifiedThisReference, org.eclipse.jdt.internal.compiler.ast.ThisReference, org.eclipse.jdt.internal.compiler.ast.Expression
    public TypeBinding resolveType(BlockScope blockScope) {
        ReferenceBinding referenceBindingSuperclass;
        if ((this.bits & ASTNode.ParenthesizedMASK) != 0) {
            blockScope.problemReporter().invalidParenthesizedExpression(this);
            return null;
        }
        super.resolveType(blockScope);
        if (this.resolvedType != null && !this.resolvedType.isValidBinding()) {
            blockScope.problemReporter().illegalSuperAccess(this.qualification.resolvedType, this.resolvedType, this);
            return null;
        }
        if (this.currentCompatibleType == null) {
            return null;
        }
        if (this.currentCompatibleType.f626id == 1) {
            blockScope.problemReporter().cannotUseSuperInJavaLangObject(this);
            return null;
        }
        if (this.currentCompatibleType.isInterface()) {
            referenceBindingSuperclass = this.currentCompatibleType;
        } else {
            referenceBindingSuperclass = this.currentCompatibleType.superclass();
        }
        this.resolvedType = referenceBindingSuperclass;
        return referenceBindingSuperclass;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.QualifiedThisReference
    int findCompatibleEnclosing(ReferenceBinding referenceBinding, TypeBinding typeBinding, BlockScope blockScope) {
        if (typeBinding.isInterface()) {
            CompilerOptions compilerOptions = blockScope.compilerOptions();
            ReferenceBinding[] referenceBindingArrSuperInterfaces = referenceBinding.superInterfaces();
            int length = referenceBindingArrSuperInterfaces.length;
            long j = compilerOptions.complianceLevel;
            boolean z = true;
            boolean z2 = j >= ClassFileConstants.JDK1_8;
            ReferenceBinding referenceBinding2 = null;
            char[][] cArr = null;
            for (int i = 0; i < length; i++) {
                if (TypeBinding.equalsEquals(referenceBindingArrSuperInterfaces[i].erasure(), typeBinding)) {
                    referenceBinding2 = referenceBindingArrSuperInterfaces[i];
                    this.currentCompatibleType = referenceBinding2;
                } else if (referenceBindingArrSuperInterfaces[i].erasure().isCompatibleWith(typeBinding)) {
                    cArr = referenceBindingArrSuperInterfaces[i].compoundName;
                    if (referenceBinding2 == null) {
                        referenceBinding2 = referenceBindingArrSuperInterfaces[i];
                    }
                    z = false;
                }
            }
            if (!z || !z2) {
                this.currentCompatibleType = null;
                this.resolvedType = new ProblemReferenceBinding(cArr, referenceBinding2, z2 ? 21 : 29);
            }
            return 0;
        }
        return super.findCompatibleEnclosing(referenceBinding, typeBinding, blockScope);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.QualifiedThisReference, org.eclipse.jdt.internal.compiler.ast.ThisReference, org.eclipse.jdt.internal.compiler.ast.Expression, org.eclipse.jdt.internal.compiler.ast.ASTNode
    public void traverse(ASTVisitor aSTVisitor, BlockScope blockScope) {
        if (aSTVisitor.visit(this, blockScope)) {
            this.qualification.traverse(aSTVisitor, blockScope);
        }
        aSTVisitor.endVisit(this, blockScope);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.QualifiedThisReference, org.eclipse.jdt.internal.compiler.ast.ThisReference, org.eclipse.jdt.internal.compiler.ast.Expression
    public void traverse(ASTVisitor aSTVisitor, ClassScope classScope) {
        if (aSTVisitor.visit(this, classScope)) {
            this.qualification.traverse(aSTVisitor, classScope);
        }
        aSTVisitor.endVisit(this, classScope);
    }
}
