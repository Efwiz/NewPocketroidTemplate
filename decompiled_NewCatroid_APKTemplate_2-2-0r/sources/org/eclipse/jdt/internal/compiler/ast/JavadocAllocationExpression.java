package org.eclipse.jdt.internal.compiler.ast;

import org.eclipse.jdt.core.compiler.CharOperation;
import org.eclipse.jdt.internal.compiler.ASTVisitor;
import org.eclipse.jdt.internal.compiler.impl.Constant;
import org.eclipse.jdt.internal.compiler.lookup.Binding;
import org.eclipse.jdt.internal.compiler.lookup.BlockScope;
import org.eclipse.jdt.internal.compiler.lookup.ClassScope;
import org.eclipse.jdt.internal.compiler.lookup.MethodBinding;
import org.eclipse.jdt.internal.compiler.lookup.ParameterizedMethodBinding;
import org.eclipse.jdt.internal.compiler.lookup.ProblemMethodBinding;
import org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding;
import org.eclipse.jdt.internal.compiler.lookup.Scope;
import org.eclipse.jdt.internal.compiler.lookup.SourceTypeBinding;
import org.eclipse.jdt.internal.compiler.lookup.TypeBinding;

/* JADX INFO: loaded from: classes14.dex */
public class JavadocAllocationExpression extends AllocationExpression {
    public int memberStart;
    public char[][] qualification;
    public int tagSourceEnd;
    public int tagSourceStart;
    public int tagValue;

    public JavadocAllocationExpression(int i, int i2) {
        this.sourceStart = i;
        this.sourceEnd = i2;
        this.bits |= 32768;
    }

    public JavadocAllocationExpression(long j) {
        this((int) (j >>> 32), (int) j);
    }

    TypeBinding internalResolveType(Scope scope) {
        boolean zIsTypeVariable;
        int length;
        this.constant = Constant.NotAConstant;
        if (this.type == null) {
            this.resolvedType = scope.enclosingSourceType();
        } else if (scope.kind == 3) {
            this.resolvedType = this.type.resolveType((ClassScope) scope);
        } else {
            this.resolvedType = this.type.resolveType((BlockScope) scope, true);
        }
        this.argumentTypes = Binding.NO_PARAMETERS;
        if (this.arguments != null) {
            this.argumentsHaveErrors = false;
            int length2 = this.arguments.length;
            this.argumentTypes = new TypeBinding[length2];
            zIsTypeVariable = false;
            for (int i = 0; i < length2; i++) {
                Expression expression = this.arguments[i];
                if (scope.kind == 3) {
                    this.argumentTypes[i] = expression.resolveType((ClassScope) scope);
                } else {
                    this.argumentTypes[i] = expression.resolveType((BlockScope) scope);
                }
                if (this.argumentTypes[i] == null) {
                    this.argumentsHaveErrors = true;
                } else if (!zIsTypeVariable) {
                    zIsTypeVariable = this.argumentTypes[i].isTypeVariable();
                }
            }
            if (this.argumentsHaveErrors) {
                return null;
            }
        } else {
            zIsTypeVariable = false;
        }
        if (this.resolvedType == null) {
            return null;
        }
        this.resolvedType = scope.environment().convertToRawType(this.type.resolvedType, true);
        SourceTypeBinding sourceTypeBindingEnclosingSourceType = scope.enclosingSourceType();
        if (sourceTypeBindingEnclosingSourceType != null && sourceTypeBindingEnclosingSourceType.isCompatibleWith(this.resolvedType)) {
            this.bits |= 16384;
        }
        ReferenceBinding referenceBinding = (ReferenceBinding) this.resolvedType;
        this.binding = scope.getConstructor(referenceBinding, this.argumentTypes, this);
        if (!this.binding.isValidBinding()) {
            MethodBinding constructor = this.binding;
            ReferenceBinding referenceBindingEnclosingType = referenceBinding;
            while (!constructor.isValidBinding() && (referenceBindingEnclosingType.isMemberType() || referenceBindingEnclosingType.isLocalType())) {
                referenceBindingEnclosingType = referenceBindingEnclosingType.enclosingType();
                constructor = scope.getConstructor(referenceBindingEnclosingType, this.argumentTypes, this);
            }
            if (constructor.isValidBinding()) {
                this.binding = constructor;
            }
        }
        if (!this.binding.isValidBinding()) {
            MethodBinding method = scope.getMethod(this.resolvedType, this.resolvedType.sourceName(), this.argumentTypes, this);
            if (method.isValidBinding()) {
                this.binding = method;
            } else {
                if (this.binding.declaringClass == null) {
                    this.binding.declaringClass = referenceBinding;
                }
                scope.problemReporter().javadocInvalidConstructor(this, this.binding, scope.getDeclarationModifiers());
            }
            return this.resolvedType;
        }
        if (this.binding.isVarargs()) {
            int length3 = this.argumentTypes.length;
            if (this.binding.parameters.length != length3 || !this.argumentTypes[length3 - 1].isArrayType()) {
                scope.problemReporter().javadocInvalidConstructor(this, new ProblemMethodBinding(this.binding, this.binding.selector, this.argumentTypes, 1), scope.getDeclarationModifiers());
            }
        } else if (zIsTypeVariable) {
            scope.problemReporter().javadocInvalidConstructor(this, new ProblemMethodBinding(this.binding, this.binding.selector, this.argumentTypes, 1), scope.getDeclarationModifiers());
        } else if (this.binding instanceof ParameterizedMethodBinding) {
            ParameterizedMethodBinding parameterizedMethodBinding = (ParameterizedMethodBinding) this.binding;
            if (parameterizedMethodBinding.hasSubstitutedParameters()) {
                int length4 = this.argumentTypes.length;
                for (int i2 = 0; i2 < length4; i2++) {
                    if (TypeBinding.notEquals(parameterizedMethodBinding.parameters[i2], this.argumentTypes[i2]) && TypeBinding.notEquals(parameterizedMethodBinding.parameters[i2].erasure(), this.argumentTypes[i2].erasure())) {
                        scope.problemReporter().javadocInvalidConstructor(this, new ProblemMethodBinding(this.binding, this.binding.selector, this.argumentTypes, 1), scope.getDeclarationModifiers());
                        break;
                    }
                }
            }
        } else if (this.resolvedType.isMemberType() && (length = this.qualification.length) > 1) {
            if (!(this.type instanceof JavadocQualifiedTypeReference) || ((JavadocQualifiedTypeReference) this.type).tokens.length == length) {
                ReferenceBinding referenceBindingEnclosingType2 = referenceBinding;
                while (length > 0) {
                    length--;
                    if (!CharOperation.equals(this.qualification[length], referenceBindingEnclosingType2.sourceName) || (referenceBindingEnclosingType2 = referenceBindingEnclosingType2.enclosingType()) == null) {
                        break;
                    }
                }
                if (length > 0 || referenceBindingEnclosingType2 != null) {
                    scope.problemReporter().javadocInvalidMemberTypeQualification(this.memberStart + 1, this.sourceEnd, scope.getDeclarationModifiers());
                }
            } else {
                scope.problemReporter().javadocInvalidMemberTypeQualification(this.memberStart + 1, this.sourceEnd, scope.getDeclarationModifiers());
            }
        }
        if (isMethodUseDeprecated(this.binding, scope, true)) {
            scope.problemReporter().javadocDeprecatedMethod(this.binding, this, scope.getDeclarationModifiers());
        }
        return referenceBinding;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.AllocationExpression, org.eclipse.jdt.internal.compiler.lookup.InvocationSite
    public boolean isSuperAccess() {
        return (this.bits & 16384) != 0;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.AllocationExpression, org.eclipse.jdt.internal.compiler.ast.Expression
    public TypeBinding resolveType(BlockScope blockScope) {
        return internalResolveType(blockScope);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression
    public TypeBinding resolveType(ClassScope classScope) {
        return internalResolveType(classScope);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.AllocationExpression, org.eclipse.jdt.internal.compiler.ast.Expression, org.eclipse.jdt.internal.compiler.ast.ASTNode
    public void traverse(ASTVisitor aSTVisitor, BlockScope blockScope) {
        if (aSTVisitor.visit(this, blockScope)) {
            if (this.typeArguments != null) {
                int length = this.typeArguments.length;
                for (int i = 0; i < length; i++) {
                    this.typeArguments[i].traverse(aSTVisitor, blockScope);
                }
            }
            if (this.type != null) {
                this.type.traverse(aSTVisitor, blockScope);
            }
            if (this.arguments != null) {
                int length2 = this.arguments.length;
                for (int i2 = 0; i2 < length2; i2++) {
                    this.arguments[i2].traverse(aSTVisitor, blockScope);
                }
            }
        }
        aSTVisitor.endVisit(this, blockScope);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression
    public void traverse(ASTVisitor aSTVisitor, ClassScope classScope) {
        if (aSTVisitor.visit(this, classScope)) {
            if (this.typeArguments != null) {
                int length = this.typeArguments.length;
                for (int i = 0; i < length; i++) {
                    this.typeArguments[i].traverse(aSTVisitor, classScope);
                }
            }
            if (this.type != null) {
                this.type.traverse(aSTVisitor, classScope);
            }
            if (this.arguments != null) {
                int length2 = this.arguments.length;
                for (int i2 = 0; i2 < length2; i2++) {
                    this.arguments[i2].traverse(aSTVisitor, classScope);
                }
            }
        }
        aSTVisitor.endVisit(this, classScope);
    }
}
