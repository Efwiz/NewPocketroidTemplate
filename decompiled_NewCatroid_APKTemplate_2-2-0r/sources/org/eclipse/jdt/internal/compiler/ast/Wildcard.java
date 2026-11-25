package org.eclipse.jdt.internal.compiler.ast;

import io.netty.util.internal.StringUtil;
import org.eclipse.jdt.core.compiler.CharOperation;
import org.eclipse.jdt.internal.compiler.ASTVisitor;
import org.eclipse.jdt.internal.compiler.lookup.BlockScope;
import org.eclipse.jdt.internal.compiler.lookup.ClassScope;
import org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding;
import org.eclipse.jdt.internal.compiler.lookup.Scope;
import org.eclipse.jdt.internal.compiler.lookup.TypeBinding;
import org.eclipse.jdt.internal.compiler.lookup.WildcardBinding;

/* JADX INFO: loaded from: classes14.dex */
public class Wildcard extends SingleTypeReference {
    public static final int EXTENDS = 1;
    public static final int SUPER = 2;
    public static final int UNBOUND = 0;
    public TypeReference bound;
    public int kind;

    @Override // org.eclipse.jdt.internal.compiler.ast.TypeReference
    public boolean isWildcard() {
        return true;
    }

    public Wildcard(int i) {
        super(WILDCARD_NAME, 0L);
        this.kind = i;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.TypeReference
    public char[][] getParameterizedTypeName() {
        int i = this.kind;
        if (i == 0) {
            return new char[][]{WILDCARD_NAME};
        }
        if (i == 1) {
            return new char[][]{CharOperation.concat(WILDCARD_NAME, WILDCARD_EXTENDS, CharOperation.concatWith(this.bound.getParameterizedTypeName(), '.'))};
        }
        return new char[][]{CharOperation.concat(WILDCARD_NAME, WILDCARD_SUPER, CharOperation.concatWith(this.bound.getParameterizedTypeName(), '.'))};
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.SingleTypeReference, org.eclipse.jdt.internal.compiler.ast.TypeReference
    public char[][] getTypeName() {
        int i = this.kind;
        if (i == 0) {
            return new char[][]{WILDCARD_NAME};
        }
        if (i == 1) {
            return new char[][]{CharOperation.concat(WILDCARD_NAME, WILDCARD_EXTENDS, CharOperation.concatWith(this.bound.getTypeName(), '.'))};
        }
        return new char[][]{CharOperation.concat(WILDCARD_NAME, WILDCARD_SUPER, CharOperation.concatWith(this.bound.getTypeName(), '.'))};
    }

    private TypeBinding internalResolveType(Scope scope, ReferenceBinding referenceBinding, int i) {
        TypeBinding typeBinding;
        TypeBinding typeBindingResolveType;
        if (this.bound != null) {
            if (scope.kind == 3) {
                typeBindingResolveType = this.bound.resolveType((ClassScope) scope, 256);
            } else {
                typeBindingResolveType = this.bound.resolveType((BlockScope) scope, true, 256);
            }
            this.bits |= this.bound.bits & 1048576;
            if (typeBindingResolveType == null) {
                return null;
            }
            typeBinding = typeBindingResolveType;
        } else {
            typeBinding = null;
        }
        this.resolvedType = scope.environment().createWildcard(referenceBinding, i, typeBinding, null, this.kind);
        resolveAnnotations(scope, 0);
        if (scope.environment().usesNullTypeAnnotations()) {
            ((WildcardBinding) this.resolvedType).evaluateNullAnnotations(scope, this);
        }
        return this.resolvedType;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.SingleTypeReference, org.eclipse.jdt.internal.compiler.ast.Expression
    public StringBuffer printExpression(int i, StringBuffer stringBuffer) {
        if (this.annotations != null && this.annotations[0] != null) {
            printAnnotations(this.annotations[0], stringBuffer);
            stringBuffer.append(StringUtil.SPACE);
        }
        int i2 = this.kind;
        if (i2 == 0) {
            stringBuffer.append(WILDCARD_NAME);
        } else if (i2 == 1) {
            stringBuffer.append(WILDCARD_NAME).append(WILDCARD_EXTENDS);
            this.bound.printExpression(0, stringBuffer);
        } else {
            stringBuffer.append(WILDCARD_NAME).append(WILDCARD_SUPER);
            this.bound.printExpression(0, stringBuffer);
        }
        return stringBuffer;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.TypeReference
    public TypeBinding resolveType(BlockScope blockScope, boolean z, int i) {
        TypeReference typeReference = this.bound;
        if (typeReference == null) {
            return null;
        }
        typeReference.resolveType(blockScope, z, 256);
        this.bits |= this.bound.bits & 1048576;
        return null;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.TypeReference
    public TypeBinding resolveType(ClassScope classScope, int i) {
        TypeReference typeReference = this.bound;
        if (typeReference == null) {
            return null;
        }
        typeReference.resolveType(classScope, 256);
        this.bits |= this.bound.bits & 1048576;
        return null;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.TypeReference
    public TypeBinding resolveTypeArgument(BlockScope blockScope, ReferenceBinding referenceBinding, int i) {
        return internalResolveType(blockScope, referenceBinding, i);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.TypeReference
    public TypeBinding resolveTypeArgument(ClassScope classScope, ReferenceBinding referenceBinding, int i) {
        return internalResolveType(classScope, referenceBinding, i);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.SingleTypeReference, org.eclipse.jdt.internal.compiler.ast.TypeReference, org.eclipse.jdt.internal.compiler.ast.Expression, org.eclipse.jdt.internal.compiler.ast.ASTNode
    public void traverse(ASTVisitor aSTVisitor, BlockScope blockScope) {
        if (aSTVisitor.visit(this, blockScope)) {
            if (this.annotations != null) {
                Annotation[] annotationArr = this.annotations[0];
                int length = annotationArr == null ? 0 : annotationArr.length;
                for (int i = 0; i < length; i++) {
                    annotationArr[i].traverse(aSTVisitor, blockScope);
                }
            }
            TypeReference typeReference = this.bound;
            if (typeReference != null) {
                typeReference.traverse(aSTVisitor, blockScope);
            }
        }
        aSTVisitor.endVisit(this, blockScope);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.SingleTypeReference, org.eclipse.jdt.internal.compiler.ast.TypeReference, org.eclipse.jdt.internal.compiler.ast.Expression
    public void traverse(ASTVisitor aSTVisitor, ClassScope classScope) {
        if (aSTVisitor.visit(this, classScope)) {
            if (this.annotations != null) {
                Annotation[] annotationArr = this.annotations[0];
                int length = annotationArr == null ? 0 : annotationArr.length;
                for (int i = 0; i < length; i++) {
                    annotationArr[i].traverse(aSTVisitor, classScope);
                }
            }
            TypeReference typeReference = this.bound;
            if (typeReference != null) {
                typeReference.traverse(aSTVisitor, classScope);
            }
        }
        aSTVisitor.endVisit(this, classScope);
    }
}
