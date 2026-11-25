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
public class JavadocSingleTypeReference extends SingleTypeReference {
    public PackageBinding packageBinding;
    public int tagSourceEnd;
    public int tagSourceStart;

    public JavadocSingleTypeReference(char[] cArr, long j, int i, int i2) {
        super(cArr, j);
        this.tagSourceStart = i;
        this.tagSourceEnd = i2;
        this.bits |= 32768;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.TypeReference
    protected TypeBinding internalResolveType(Scope scope, int i) {
        TypeBinding typeBindingClosestMatch;
        this.constant = Constant.NotAConstant;
        if (this.resolvedType != null) {
            if (this.resolvedType.isValidBinding()) {
                return this.resolvedType;
            }
            int iProblemId = this.resolvedType.problemId();
            if (iProblemId == 1 || iProblemId == 2 || iProblemId == 5) {
                return this.resolvedType.closestMatch();
            }
            return null;
        }
        this.resolvedType = getTypeBinding(scope);
        if (this.resolvedType == null) {
            return null;
        }
        if (!this.resolvedType.isValidBinding()) {
            Binding typeOrPackage = scope.getTypeOrPackage(new char[][]{this.token});
            if (typeOrPackage instanceof PackageBinding) {
                this.packageBinding = (PackageBinding) typeOrPackage;
            } else {
                if (this.resolvedType.problemId() == 7 && (typeBindingClosestMatch = this.resolvedType.closestMatch()) != null && typeBindingClosestMatch.isTypeVariable()) {
                    this.resolvedType = typeBindingClosestMatch;
                    return this.resolvedType;
                }
                reportInvalidType(scope);
            }
            return null;
        }
        if (isTypeUseDeprecated(this.resolvedType, scope)) {
            reportDeprecatedType(this.resolvedType, scope);
        }
        if (this.resolvedType.isGenericType() || this.resolvedType.isParameterizedType()) {
            this.resolvedType = scope.environment().convertToRawType(this.resolvedType, true);
        }
        return this.resolvedType;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.TypeReference
    protected void reportDeprecatedType(TypeBinding typeBinding, Scope scope) {
        scope.problemReporter().javadocDeprecatedType(typeBinding, this, scope.getDeclarationModifiers());
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.TypeReference
    protected void reportInvalidType(Scope scope) {
        scope.problemReporter().javadocInvalidType(this, this.resolvedType, scope.getDeclarationModifiers());
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.SingleTypeReference, org.eclipse.jdt.internal.compiler.ast.TypeReference, org.eclipse.jdt.internal.compiler.ast.Expression, org.eclipse.jdt.internal.compiler.ast.ASTNode
    public void traverse(ASTVisitor aSTVisitor, BlockScope blockScope) {
        aSTVisitor.visit(this, blockScope);
        aSTVisitor.endVisit(this, blockScope);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.SingleTypeReference, org.eclipse.jdt.internal.compiler.ast.TypeReference, org.eclipse.jdt.internal.compiler.ast.Expression
    public void traverse(ASTVisitor aSTVisitor, ClassScope classScope) {
        aSTVisitor.visit(this, classScope);
        aSTVisitor.endVisit(this, classScope);
    }
}
