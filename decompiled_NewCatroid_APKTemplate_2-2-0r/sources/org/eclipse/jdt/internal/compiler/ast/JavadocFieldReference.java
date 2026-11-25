package org.eclipse.jdt.internal.compiler.ast;

import org.eclipse.jdt.core.compiler.CharOperation;
import org.eclipse.jdt.internal.compiler.ASTVisitor;
import org.eclipse.jdt.internal.compiler.impl.Constant;
import org.eclipse.jdt.internal.compiler.lookup.Binding;
import org.eclipse.jdt.internal.compiler.lookup.BlockScope;
import org.eclipse.jdt.internal.compiler.lookup.ClassScope;
import org.eclipse.jdt.internal.compiler.lookup.FieldBinding;
import org.eclipse.jdt.internal.compiler.lookup.MethodBinding;
import org.eclipse.jdt.internal.compiler.lookup.ProblemFieldBinding;
import org.eclipse.jdt.internal.compiler.lookup.ProblemMethodBinding;
import org.eclipse.jdt.internal.compiler.lookup.ProblemReferenceBinding;
import org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding;
import org.eclipse.jdt.internal.compiler.lookup.Scope;
import org.eclipse.jdt.internal.compiler.lookup.TypeBinding;

/* JADX INFO: loaded from: classes14.dex */
public class JavadocFieldReference extends FieldReference {
    public MethodBinding methodBinding;
    public int tagSourceEnd;
    public int tagSourceStart;
    public int tagValue;

    public JavadocFieldReference(char[] cArr, long j) {
        super(cArr, j);
        this.bits |= 32768;
    }

    protected TypeBinding internalResolveType(Scope scope) {
        Binding field;
        MethodBinding method;
        int iProblemId;
        Binding binding;
        this.constant = Constant.NotAConstant;
        if (this.receiver == null) {
            this.actualReceiverType = scope.enclosingReceiverType();
        } else if (scope.kind == 3) {
            this.actualReceiverType = this.receiver.resolveType((ClassScope) scope);
        } else {
            this.actualReceiverType = this.receiver.resolveType((BlockScope) scope);
        }
        if (this.actualReceiverType == null) {
            return null;
        }
        if (this.receiver != null && this.receiver.isThis()) {
            field = scope.classScope().getBinding(this.token, this.bits & 7, this, true);
        } else {
            field = scope.getField(this.actualReceiverType, this.token, this);
        }
        if (!field.isValidBinding() && (((iProblemId = field.problemId()) == 5 || iProblemId == 6 || iProblemId == 7) && (binding = ((ProblemFieldBinding) field).closestMatch) != null)) {
            field = binding;
        }
        if (!field.isValidBinding() || !(field instanceof FieldBinding)) {
            if (!(this.receiver.resolvedType instanceof ProblemReferenceBinding) && (this.actualReceiverType instanceof ReferenceBinding)) {
                ReferenceBinding referenceBinding = (ReferenceBinding) this.actualReceiverType;
                char[] cArr = this.token;
                if (CharOperation.equals(this.actualReceiverType.sourceName(), cArr)) {
                    method = scope.getConstructor(referenceBinding, Binding.NO_TYPES, this);
                } else if (this.receiver.isThis()) {
                    method = scope.getImplicitMethod(cArr, Binding.NO_TYPES, this);
                } else {
                    method = scope.getMethod(referenceBinding, cArr, Binding.NO_TYPES, this);
                }
                if (method.isValidBinding()) {
                    this.methodBinding = method;
                } else {
                    ProblemMethodBinding problemMethodBinding = (ProblemMethodBinding) method;
                    if (problemMethodBinding.closestMatch == null) {
                        if (field.isValidBinding()) {
                            field = new ProblemFieldBinding(referenceBinding, field.readableName(), 1);
                        }
                        scope.problemReporter().javadocInvalidField(this, field, this.actualReceiverType, scope.getDeclarationModifiers());
                    } else {
                        this.methodBinding = problemMethodBinding.closestMatch;
                    }
                }
            }
            return null;
        }
        this.binding = (FieldBinding) field;
        if (isFieldUseDeprecated(this.binding, scope, this.bits)) {
            scope.problemReporter().javadocDeprecatedField(this.binding, this, scope.getDeclarationModifiers());
        }
        TypeBinding typeBinding = this.binding.type;
        this.resolvedType = typeBinding;
        return typeBinding;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.FieldReference, org.eclipse.jdt.internal.compiler.lookup.InvocationSite
    public boolean isSuperAccess() {
        return (this.bits & 16384) != 0;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.FieldReference, org.eclipse.jdt.internal.compiler.ast.Expression
    public StringBuffer printExpression(int i, StringBuffer stringBuffer) {
        if (this.receiver != null) {
            this.receiver.printExpression(0, stringBuffer);
        }
        stringBuffer.append('#').append(this.token);
        return stringBuffer;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.FieldReference, org.eclipse.jdt.internal.compiler.ast.Expression
    public TypeBinding resolveType(BlockScope blockScope) {
        return internalResolveType(blockScope);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression
    public TypeBinding resolveType(ClassScope classScope) {
        return internalResolveType(classScope);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.FieldReference, org.eclipse.jdt.internal.compiler.ast.Expression, org.eclipse.jdt.internal.compiler.ast.ASTNode
    public void traverse(ASTVisitor aSTVisitor, BlockScope blockScope) {
        if (aSTVisitor.visit(this, blockScope) && this.receiver != null) {
            this.receiver.traverse(aSTVisitor, blockScope);
        }
        aSTVisitor.endVisit(this, blockScope);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression
    public void traverse(ASTVisitor aSTVisitor, ClassScope classScope) {
        if (aSTVisitor.visit(this, classScope) && this.receiver != null) {
            this.receiver.traverse(aSTVisitor, classScope);
        }
        aSTVisitor.endVisit(this, classScope);
    }
}
