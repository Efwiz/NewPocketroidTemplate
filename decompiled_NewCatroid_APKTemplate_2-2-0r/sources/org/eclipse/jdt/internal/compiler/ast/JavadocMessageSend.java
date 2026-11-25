package org.eclipse.jdt.internal.compiler.ast;

import org.eclipse.jdt.core.compiler.CharOperation;
import org.eclipse.jdt.internal.compiler.ASTVisitor;
import org.eclipse.jdt.internal.compiler.impl.Constant;
import org.eclipse.jdt.internal.compiler.lookup.BlockScope;
import org.eclipse.jdt.internal.compiler.lookup.ClassScope;
import org.eclipse.jdt.internal.compiler.lookup.MethodBinding;
import org.eclipse.jdt.internal.compiler.lookup.ProblemMethodBinding;
import org.eclipse.jdt.internal.compiler.lookup.ProblemReferenceBinding;
import org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding;
import org.eclipse.jdt.internal.compiler.lookup.Scope;
import org.eclipse.jdt.internal.compiler.lookup.TypeBinding;

/* JADX INFO: loaded from: classes14.dex */
public class JavadocMessageSend extends MessageSend {
    public int tagSourceEnd;
    public int tagSourceStart;
    public int tagValue;

    public JavadocMessageSend(char[] cArr, long j) {
        this.selector = cArr;
        this.nameSourcePosition = j;
        this.sourceStart = (int) (this.nameSourcePosition >>> 32);
        this.sourceEnd = (int) this.nameSourcePosition;
        this.bits |= 32768;
    }

    public JavadocMessageSend(char[] cArr, long j, JavadocArgumentExpression[] javadocArgumentExpressionArr) {
        this(cArr, j);
        this.arguments = javadocArgumentExpressionArr;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v55, types: [org.eclipse.jdt.internal.compiler.lookup.TypeBinding] */
    /* JADX WARN: Type inference failed for: r0v56, types: [org.eclipse.jdt.internal.compiler.lookup.TypeBinding] */
    /* JADX WARN: Type inference failed for: r0v58, types: [org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding, org.eclipse.jdt.internal.compiler.lookup.TypeBinding] */
    /* JADX WARN: Type inference failed for: r11v0, types: [org.eclipse.jdt.internal.compiler.lookup.Scope] */
    private TypeBinding internalResolveType(Scope scope) {
        boolean zIsTypeVariable;
        MethodBinding methodBinding;
        int iProblemId;
        MethodBinding methodBinding2;
        this.constant = Constant.NotAConstant;
        if (this.receiver == null) {
            this.actualReceiverType = scope.enclosingReceiverType();
        } else if (scope.kind == 3) {
            this.actualReceiverType = this.receiver.resolveType((ClassScope) scope);
        } else {
            this.actualReceiverType = this.receiver.resolveType((BlockScope) scope);
        }
        if (this.arguments != null) {
            this.argumentsHaveErrors = false;
            int length = this.arguments.length;
            this.argumentTypes = new TypeBinding[length];
            zIsTypeVariable = false;
            for (int i = 0; i < length; i++) {
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
        if (this.actualReceiverType == null) {
            return null;
        }
        this.actualReceiverType = scope.environment().convertToRawType(this.receiver.resolvedType, true);
        ReferenceBinding referenceBindingEnclosingReceiverType = scope.enclosingReceiverType();
        if (referenceBindingEnclosingReceiverType != null && referenceBindingEnclosingReceiverType.isCompatibleWith(this.actualReceiverType)) {
            this.bits |= 16384;
        }
        if (this.actualReceiverType.isBaseType()) {
            scope.problemReporter().javadocErrorNoMethodFor(this, this.actualReceiverType, this.argumentTypes, scope.getDeclarationModifiers());
            return null;
        }
        this.binding = scope.getMethod(this.actualReceiverType, this.selector, this.argumentTypes, this);
        if (!this.binding.isValidBinding()) {
            TypeBinding typeBindingEnclosingType = this.actualReceiverType;
            MethodBinding method = this.binding;
            while (!method.isValidBinding() && (typeBindingEnclosingType.isMemberType() || typeBindingEnclosingType.isLocalType())) {
                typeBindingEnclosingType = typeBindingEnclosingType.enclosingType();
                method = scope.getMethod(typeBindingEnclosingType, this.selector, this.argumentTypes, this);
            }
            if (method.isValidBinding()) {
                this.binding = method;
            } else {
                ?? EnclosingType = this.actualReceiverType;
                MethodBinding constructor = this.binding;
                if (!constructor.isValidBinding() && CharOperation.equals(this.selector, EnclosingType.shortReadableName())) {
                    constructor = scope.getConstructor((ReferenceBinding) EnclosingType, this.argumentTypes, this);
                }
                while (!constructor.isValidBinding() && (EnclosingType.isMemberType() || EnclosingType.isLocalType())) {
                    EnclosingType = EnclosingType.enclosingType();
                    if (CharOperation.equals(this.selector, EnclosingType.shortReadableName())) {
                        constructor = scope.getConstructor(EnclosingType, this.argumentTypes, this);
                    }
                }
                if (constructor.isValidBinding()) {
                    this.binding = constructor;
                }
            }
        }
        if (!this.binding.isValidBinding() && (((iProblemId = this.binding.problemId()) == 3 || iProblemId == 5 || iProblemId == 6 || iProblemId == 7) && (methodBinding2 = ((ProblemMethodBinding) this.binding).closestMatch) != null)) {
            this.binding = methodBinding2;
        }
        if (!this.binding.isValidBinding()) {
            if (this.receiver.resolvedType instanceof ProblemReferenceBinding) {
                return null;
            }
            if (this.binding.declaringClass == null) {
                if (this.actualReceiverType instanceof ReferenceBinding) {
                    this.binding.declaringClass = (ReferenceBinding) this.actualReceiverType;
                } else {
                    scope.problemReporter().javadocErrorNoMethodFor(this, this.actualReceiverType, this.argumentTypes, scope.getDeclarationModifiers());
                    return null;
                }
            }
            scope.problemReporter().javadocInvalidMethod(this, this.binding, scope.getDeclarationModifiers());
            if ((this.binding instanceof ProblemMethodBinding) && (methodBinding = ((ProblemMethodBinding) this.binding).closestMatch) != null) {
                this.binding = methodBinding;
            }
            TypeBinding typeBinding = this.binding != null ? this.binding.returnType : null;
            this.resolvedType = typeBinding;
            return typeBinding;
        }
        if (zIsTypeVariable) {
            scope.problemReporter().javadocInvalidMethod(this, new ProblemMethodBinding(this.binding, this.selector, this.argumentTypes, 1), scope.getDeclarationModifiers());
        } else if (this.binding.isVarargs()) {
            int length2 = this.argumentTypes.length;
            if (this.binding.parameters.length != length2 || !this.argumentTypes[length2 - 1].isArrayType()) {
                scope.problemReporter().javadocInvalidMethod(this, new ProblemMethodBinding(this.binding, this.selector, this.argumentTypes, 1), scope.getDeclarationModifiers());
            }
        } else {
            int length3 = this.argumentTypes.length;
            for (int i2 = 0; i2 < length3; i2++) {
                if (TypeBinding.notEquals(this.binding.parameters[i2].erasure(), this.argumentTypes[i2].erasure())) {
                    scope.problemReporter().javadocInvalidMethod(this, new ProblemMethodBinding(this.binding, this.selector, this.argumentTypes, 1), scope.getDeclarationModifiers());
                    break;
                }
            }
        }
        if (isMethodUseDeprecated(this.binding, scope, true)) {
            scope.problemReporter().javadocDeprecatedMethod(this.binding, this, scope.getDeclarationModifiers());
        }
        TypeBinding typeBinding2 = this.binding.returnType;
        this.resolvedType = typeBinding2;
        return typeBinding2;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.MessageSend, org.eclipse.jdt.internal.compiler.lookup.InvocationSite
    public boolean isSuperAccess() {
        return (this.bits & 16384) != 0;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.MessageSend, org.eclipse.jdt.internal.compiler.ast.Expression
    public StringBuffer printExpression(int i, StringBuffer stringBuffer) {
        if (this.receiver != null) {
            this.receiver.printExpression(0, stringBuffer);
        }
        stringBuffer.append('#').append(this.selector).append('(');
        if (this.arguments != null) {
            for (int i2 = 0; i2 < this.arguments.length; i2++) {
                if (i2 > 0) {
                    stringBuffer.append(", ");
                }
                this.arguments[i2].printExpression(0, stringBuffer);
            }
        }
        return stringBuffer.append(')');
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.MessageSend, org.eclipse.jdt.internal.compiler.ast.Expression
    public TypeBinding resolveType(BlockScope blockScope) {
        return internalResolveType(blockScope);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression
    public TypeBinding resolveType(ClassScope classScope) {
        return internalResolveType(classScope);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.MessageSend, org.eclipse.jdt.internal.compiler.ast.Expression, org.eclipse.jdt.internal.compiler.ast.ASTNode
    public void traverse(ASTVisitor aSTVisitor, BlockScope blockScope) {
        if (aSTVisitor.visit(this, blockScope)) {
            if (this.receiver != null) {
                this.receiver.traverse(aSTVisitor, blockScope);
            }
            if (this.arguments != null) {
                int length = this.arguments.length;
                for (int i = 0; i < length; i++) {
                    this.arguments[i].traverse(aSTVisitor, blockScope);
                }
            }
        }
        aSTVisitor.endVisit(this, blockScope);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression
    public void traverse(ASTVisitor aSTVisitor, ClassScope classScope) {
        if (aSTVisitor.visit(this, classScope)) {
            if (this.receiver != null) {
                this.receiver.traverse(aSTVisitor, classScope);
            }
            if (this.arguments != null) {
                int length = this.arguments.length;
                for (int i = 0; i < length; i++) {
                    this.arguments[i].traverse(aSTVisitor, classScope);
                }
            }
        }
        aSTVisitor.endVisit(this, classScope);
    }
}
