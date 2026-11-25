package org.eclipse.jdt.internal.compiler.ast;

import org.eclipse.jdt.internal.compiler.ASTVisitor;
import org.eclipse.jdt.internal.compiler.impl.Constant;
import org.eclipse.jdt.internal.compiler.lookup.ArrayBinding;
import org.eclipse.jdt.internal.compiler.lookup.BlockScope;
import org.eclipse.jdt.internal.compiler.lookup.ClassScope;
import org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding;
import org.eclipse.jdt.internal.compiler.lookup.Scope;
import org.eclipse.jdt.internal.compiler.lookup.TypeBinding;

/* JADX INFO: loaded from: classes14.dex */
public class JavadocImplicitTypeReference extends TypeReference {
    public char[] token;

    @Override // org.eclipse.jdt.internal.compiler.ast.TypeReference
    public TypeReference augmentTypeWithAdditionalDimensions(int i, Annotation[][] annotationArr, boolean z) {
        return null;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.ASTNode
    public boolean isThis() {
        return true;
    }

    public JavadocImplicitTypeReference(char[] cArr, int i) {
        this.token = cArr;
        this.sourceStart = i;
        this.sourceEnd = i;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.TypeReference
    protected TypeBinding getTypeBinding(Scope scope) {
        this.constant = Constant.NotAConstant;
        ReferenceBinding referenceBindingEnclosingReceiverType = scope.enclosingReceiverType();
        this.resolvedType = referenceBindingEnclosingReceiverType;
        return referenceBindingEnclosingReceiverType;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.TypeReference
    public char[] getLastToken() {
        return this.token;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.TypeReference
    public char[][] getTypeName() {
        char[] cArr = this.token;
        if (cArr != null) {
            return new char[][]{cArr};
        }
        return null;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.TypeReference
    protected TypeBinding internalResolveType(Scope scope, int i) {
        this.constant = Constant.NotAConstant;
        if (this.resolvedType != null) {
            if (this.resolvedType.isValidBinding()) {
                return this.resolvedType;
            }
            int iProblemId = this.resolvedType.problemId();
            if (iProblemId == 1 || iProblemId == 2) {
                return this.resolvedType.closestMatch();
            }
            return null;
        }
        TypeBinding typeBinding = getTypeBinding(scope);
        this.resolvedType = typeBinding;
        if (typeBinding == null) {
            return null;
        }
        boolean zIsValidBinding = typeBinding.isValidBinding();
        if (!zIsValidBinding) {
            reportInvalidType(scope);
            int iProblemId2 = typeBinding.problemId();
            if ((iProblemId2 != 1 && iProblemId2 != 2) || (typeBinding = typeBinding.closestMatch()) == null) {
                return null;
            }
        }
        if (typeBinding.isArrayType() && ((ArrayBinding) typeBinding).leafComponentType == TypeBinding.VOID) {
            scope.problemReporter().cannotAllocateVoidArray(this);
            return null;
        }
        if (isTypeUseDeprecated(typeBinding, scope)) {
            reportDeprecatedType(typeBinding, scope);
        }
        if (typeBinding.isGenericType() || typeBinding.isParameterizedType()) {
            typeBinding = scope.environment().convertToRawType(typeBinding, true);
        }
        if (!zIsValidBinding) {
            return typeBinding;
        }
        this.resolvedType = typeBinding;
        return typeBinding;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.TypeReference
    protected void reportInvalidType(Scope scope) {
        scope.problemReporter().javadocInvalidType(this, this.resolvedType, scope.getDeclarationModifiers());
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.TypeReference
    protected void reportDeprecatedType(TypeBinding typeBinding, Scope scope) {
        scope.problemReporter().javadocDeprecatedType(typeBinding, this, scope.getDeclarationModifiers());
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.TypeReference, org.eclipse.jdt.internal.compiler.ast.Expression, org.eclipse.jdt.internal.compiler.ast.ASTNode
    public void traverse(ASTVisitor aSTVisitor, BlockScope blockScope) {
        aSTVisitor.visit(this, blockScope);
        aSTVisitor.endVisit(this, blockScope);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.TypeReference, org.eclipse.jdt.internal.compiler.ast.Expression
    public void traverse(ASTVisitor aSTVisitor, ClassScope classScope) {
        aSTVisitor.visit(this, classScope);
        aSTVisitor.endVisit(this, classScope);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression
    public StringBuffer printExpression(int i, StringBuffer stringBuffer) {
        return new StringBuffer();
    }
}
