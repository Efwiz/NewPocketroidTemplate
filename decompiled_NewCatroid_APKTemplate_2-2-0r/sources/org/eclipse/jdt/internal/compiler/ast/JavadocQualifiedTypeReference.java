package org.eclipse.jdt.internal.compiler.ast;

import org.eclipse.jdt.internal.compiler.ASTVisitor;
import org.eclipse.jdt.internal.compiler.impl.Constant;
import org.eclipse.jdt.internal.compiler.lookup.Binding;
import org.eclipse.jdt.internal.compiler.lookup.BlockScope;
import org.eclipse.jdt.internal.compiler.lookup.ClassScope;
import org.eclipse.jdt.internal.compiler.lookup.PackageBinding;
import org.eclipse.jdt.internal.compiler.lookup.Scope;
import org.eclipse.jdt.internal.compiler.lookup.TypeBinding;

/* JADX INFO: loaded from: classes14.dex */
public class JavadocQualifiedTypeReference extends QualifiedTypeReference {
    public PackageBinding packageBinding;
    public int tagSourceEnd;
    public int tagSourceStart;

    public JavadocQualifiedTypeReference(char[][] cArr, long[] jArr, int i, int i2) {
        super(cArr, jArr);
        this.tagSourceStart = i;
        this.tagSourceEnd = i2;
        this.bits |= 32768;
    }

    private TypeBinding internalResolveType(Scope scope, boolean z) {
        this.constant = Constant.NotAConstant;
        if (this.resolvedType != null) {
            return this.resolvedType.isValidBinding() ? this.resolvedType : this.resolvedType.closestMatch();
        }
        TypeBinding typeBinding = getTypeBinding(scope);
        this.resolvedType = typeBinding;
        if (typeBinding == null) {
            return null;
        }
        if (!typeBinding.isValidBinding()) {
            Binding typeOrPackage = scope.getTypeOrPackage(this.tokens);
            if (typeOrPackage instanceof PackageBinding) {
                this.packageBinding = (PackageBinding) typeOrPackage;
            } else {
                reportInvalidType(scope);
            }
            return null;
        }
        if (typeBinding.isGenericType() || typeBinding.isParameterizedType()) {
            this.resolvedType = scope.environment().convertToRawType(typeBinding, true);
        }
        return this.resolvedType;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.TypeReference
    protected void reportDeprecatedType(TypeBinding typeBinding, Scope scope) {
        scope.problemReporter().javadocDeprecatedType(typeBinding, this, scope.getDeclarationModifiers());
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.TypeReference
    protected void reportDeprecatedType(TypeBinding typeBinding, Scope scope, int i) {
        scope.problemReporter().javadocDeprecatedType(typeBinding, this, scope.getDeclarationModifiers(), i);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.TypeReference
    protected void reportInvalidType(Scope scope) {
        scope.problemReporter().javadocInvalidType(this, this.resolvedType, scope.getDeclarationModifiers());
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.TypeReference
    public TypeBinding resolveType(BlockScope blockScope, boolean z, int i) {
        return internalResolveType(blockScope, z);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.TypeReference
    public TypeBinding resolveType(ClassScope classScope, int i) {
        return internalResolveType((Scope) classScope, false);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.QualifiedTypeReference, org.eclipse.jdt.internal.compiler.ast.TypeReference, org.eclipse.jdt.internal.compiler.ast.Expression, org.eclipse.jdt.internal.compiler.ast.ASTNode
    public void traverse(ASTVisitor aSTVisitor, BlockScope blockScope) {
        aSTVisitor.visit(this, blockScope);
        aSTVisitor.endVisit(this, blockScope);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.QualifiedTypeReference, org.eclipse.jdt.internal.compiler.ast.TypeReference, org.eclipse.jdt.internal.compiler.ast.Expression
    public void traverse(ASTVisitor aSTVisitor, ClassScope classScope) {
        aSTVisitor.visit(this, classScope);
        aSTVisitor.endVisit(this, classScope);
    }
}
