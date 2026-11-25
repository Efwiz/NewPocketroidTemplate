package org.eclipse.jdt.internal.compiler.ast;

import org.eclipse.jdt.internal.compiler.ASTVisitor;
import org.eclipse.jdt.internal.compiler.lookup.BlockScope;
import org.eclipse.jdt.internal.compiler.lookup.ClassScope;
import org.eclipse.jdt.internal.compiler.lookup.Scope;
import org.eclipse.jdt.internal.compiler.lookup.TypeBinding;

/* JADX INFO: loaded from: classes14.dex */
public class UnionTypeReference extends TypeReference {
    public TypeReference[] typeReferences;

    @Override // org.eclipse.jdt.internal.compiler.ast.TypeReference
    public TypeReference augmentTypeWithAdditionalDimensions(int i, Annotation[][] annotationArr, boolean z) {
        return this;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.TypeReference
    public char[] getLastToken() {
        return null;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.TypeReference
    protected TypeBinding getTypeBinding(Scope scope) {
        return null;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.TypeReference
    public boolean isUnionType() {
        return true;
    }

    public UnionTypeReference(TypeReference[] typeReferenceArr) {
        this.bits |= 536870912;
        this.typeReferences = typeReferenceArr;
        this.sourceStart = typeReferenceArr[0].sourceStart;
        this.sourceEnd = typeReferenceArr[typeReferenceArr.length - 1].sourceEnd;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.TypeReference
    public TypeBinding resolveType(BlockScope blockScope, boolean z, int i) {
        int length = this.typeReferences.length;
        TypeBinding[] typeBindingArr = new TypeBinding[length];
        boolean z2 = false;
        for (int i2 = 0; i2 < length; i2++) {
            TypeBinding typeBindingResolveType = this.typeReferences[i2].resolveType(blockScope, z, i);
            if (typeBindingResolveType == null) {
                return null;
            }
            int iKind = typeBindingResolveType.kind();
            if (iKind != 260) {
                if (iKind == 4100) {
                    blockScope.problemReporter().invalidTypeVariableAsException(typeBindingResolveType, this.typeReferences[i2]);
                    z2 = true;
                }
            } else if (typeBindingResolveType.isBoundParameterizedType()) {
                blockScope.problemReporter().invalidParameterizedExceptionType(typeBindingResolveType, this.typeReferences[i2]);
                z2 = true;
            }
            if (typeBindingResolveType.findSuperTypeOriginatingFrom(21, true) == null && typeBindingResolveType.isValidBinding()) {
                blockScope.problemReporter().cannotThrowType(this.typeReferences[i2], typeBindingResolveType);
                z2 = true;
            }
            typeBindingArr[i2] = typeBindingResolveType;
            for (int i3 = 0; i3 < i2; i3++) {
                if (typeBindingArr[i3].isCompatibleWith(typeBindingResolveType)) {
                    blockScope.problemReporter().wrongSequenceOfExceptionTypes(this.typeReferences[i3], typeBindingArr[i3], typeBindingResolveType);
                } else {
                    if (typeBindingResolveType.isCompatibleWith(typeBindingArr[i3])) {
                        blockScope.problemReporter().wrongSequenceOfExceptionTypes(this.typeReferences[i2], typeBindingResolveType, typeBindingArr[i3]);
                    }
                }
                z2 = true;
            }
        }
        if (z2) {
            return null;
        }
        TypeBinding typeBindingLowerUpperBound = blockScope.lowerUpperBound(typeBindingArr);
        this.resolvedType = typeBindingLowerUpperBound;
        return typeBindingLowerUpperBound;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.TypeReference
    public char[][] getTypeName() {
        return this.typeReferences[0].getTypeName();
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.TypeReference, org.eclipse.jdt.internal.compiler.ast.Expression, org.eclipse.jdt.internal.compiler.ast.ASTNode
    public void traverse(ASTVisitor aSTVisitor, BlockScope blockScope) {
        if (aSTVisitor.visit(this, blockScope)) {
            TypeReference[] typeReferenceArr = this.typeReferences;
            int length = typeReferenceArr == null ? 0 : typeReferenceArr.length;
            for (int i = 0; i < length; i++) {
                this.typeReferences[i].traverse(aSTVisitor, blockScope);
            }
        }
        aSTVisitor.endVisit(this, blockScope);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.TypeReference, org.eclipse.jdt.internal.compiler.ast.Expression
    public void traverse(ASTVisitor aSTVisitor, ClassScope classScope) {
        if (aSTVisitor.visit(this, classScope)) {
            TypeReference[] typeReferenceArr = this.typeReferences;
            int length = typeReferenceArr == null ? 0 : typeReferenceArr.length;
            for (int i = 0; i < length; i++) {
                this.typeReferences[i].traverse(aSTVisitor, classScope);
            }
        }
        aSTVisitor.endVisit(this, classScope);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression
    public StringBuffer printExpression(int i, StringBuffer stringBuffer) {
        TypeReference[] typeReferenceArr = this.typeReferences;
        int length = typeReferenceArr == null ? 0 : typeReferenceArr.length;
        printIndent(i, stringBuffer);
        for (int i2 = 0; i2 < length; i2++) {
            this.typeReferences[i2].printExpression(0, stringBuffer);
            if (i2 != length - 1) {
                stringBuffer.append(" | ");
            }
        }
        return stringBuffer;
    }
}
