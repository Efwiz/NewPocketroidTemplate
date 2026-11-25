package org.eclipse.jdt.internal.compiler.ast;

import org.eclipse.jdt.internal.compiler.ASTVisitor;
import org.eclipse.jdt.internal.compiler.impl.Constant;
import org.eclipse.jdt.internal.compiler.lookup.Binding;
import org.eclipse.jdt.internal.compiler.lookup.BlockScope;
import org.eclipse.jdt.internal.compiler.lookup.ClassScope;
import org.eclipse.jdt.internal.compiler.lookup.ElementValuePair;
import org.eclipse.jdt.internal.compiler.lookup.FieldBinding;
import org.eclipse.jdt.internal.compiler.lookup.LocalVariableBinding;
import org.eclipse.jdt.internal.compiler.lookup.MethodBinding;
import org.eclipse.jdt.internal.compiler.lookup.TypeBinding;

/* JADX INFO: loaded from: classes14.dex */
public class MemberValuePair extends ASTNode {
    public MethodBinding binding;
    public ElementValuePair compilerElementPair = null;

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    public char[] f618name;
    public Expression value;

    public MemberValuePair(char[] cArr, int i, int i2, Expression expression) {
        this.f618name = cArr;
        this.sourceStart = i;
        this.sourceEnd = i2;
        this.value = expression;
        if (expression instanceof ArrayInitializer) {
            expression.bits |= 1;
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.ASTNode
    public StringBuffer print(int i, StringBuffer stringBuffer) {
        stringBuffer.append(this.f618name).append(" = ");
        this.value.print(0, stringBuffer);
        return stringBuffer;
    }

    public void resolveTypeExpecting(final BlockScope blockScope, final TypeBinding typeBinding) {
        Expression expression = this.value;
        if (expression == null) {
            this.compilerElementPair = new ElementValuePair(this.f618name, this.value, this.binding);
            return;
        }
        TypeBinding typeBindingResolveType = null;
        if (typeBinding == null) {
            if (expression instanceof ArrayInitializer) {
                expression.resolveTypeExpecting(blockScope, null);
            } else {
                expression.resolveType(blockScope);
            }
            this.compilerElementPair = new ElementValuePair(this.f618name, this.value, this.binding);
            return;
        }
        expression.setExpectedType(typeBinding);
        Expression expression2 = this.value;
        if (expression2 instanceof ArrayInitializer) {
            typeBindingResolveType = ((ArrayInitializer) expression2).resolveTypeExpecting(blockScope, this.binding.returnType);
        } else if (expression2 instanceof ArrayAllocationExpression) {
            blockScope.problemReporter().annotationValueMustBeArrayInitializer(this.binding.declaringClass, this.f618name, this.value);
            this.value.resolveType(blockScope);
        } else {
            typeBindingResolveType = expression2.resolveType(blockScope);
            this.value.traverse(new ASTVisitor() { // from class: org.eclipse.jdt.internal.compiler.ast.MemberValuePair.1
                @Override // org.eclipse.jdt.internal.compiler.ASTVisitor
                public boolean visit(SingleNameReference singleNameReference, BlockScope blockScope2) {
                    if (singleNameReference.binding instanceof LocalVariableBinding) {
                        ((LocalVariableBinding) singleNameReference.binding).useFlag = 1;
                    }
                    return true;
                }
            }, blockScope);
        }
        this.compilerElementPair = new ElementValuePair(this.f618name, this.value, this.binding);
        if (typeBindingResolveType == null) {
            return;
        }
        final TypeBinding typeBindingLeafComponentType = typeBinding.leafComponentType();
        final boolean[] zArr = new boolean[1];
        final TypeBinding typeBinding2 = typeBindingResolveType;
        Runnable runnable = new Runnable() { // from class: org.eclipse.jdt.internal.compiler.ast.MemberValuePair.2
            @Override // java.lang.Runnable
            public void run() {
                if (!MemberValuePair.this.value.isConstantValueOfTypeAssignableToType(typeBinding2, typeBinding) && !typeBinding2.isCompatibleWith(typeBinding)) {
                    if (typeBinding.isArrayType() && typeBinding.dimensions() == 1 && (MemberValuePair.this.value.isConstantValueOfTypeAssignableToType(typeBinding2, typeBindingLeafComponentType) || typeBinding2.isCompatibleWith(typeBindingLeafComponentType))) {
                        return;
                    }
                    if (typeBindingLeafComponentType.isAnnotationType() && !typeBinding2.isAnnotationType()) {
                        blockScope.problemReporter().annotationValueMustBeAnnotation(MemberValuePair.this.binding.declaringClass, MemberValuePair.this.f618name, MemberValuePair.this.value, typeBindingLeafComponentType);
                    } else {
                        blockScope.problemReporter().typeMismatchError(typeBinding2, typeBinding, MemberValuePair.this.value, (ASTNode) null);
                    }
                    zArr[0] = true;
                    return;
                }
                blockScope.compilationUnitScope().recordTypeConversion(typeBinding.leafComponentType(), typeBinding2.leafComponentType());
                MemberValuePair.this.value.computeConversion(blockScope, typeBinding, typeBinding2);
            }
        };
        int i = 0;
        if (!blockScope.deferCheck(runnable)) {
            runnable.run();
            if (zArr[0]) {
                return;
            }
        }
        int i2 = typeBindingLeafComponentType.erasure().f626id;
        if (i2 != 2 && i2 != 3 && i2 != 4 && i2 != 5) {
            if (i2 == 16) {
                Expression expression3 = this.value;
                if (expression3 instanceof ArrayInitializer) {
                    Expression[] expressionArr = ((ArrayInitializer) expression3).expressions;
                    if (expressionArr != null) {
                        int length = expressionArr.length;
                        while (i < length) {
                            Expression expression4 = expressionArr[i];
                            if (!(expression4 instanceof ClassLiteralAccess)) {
                                blockScope.problemReporter().annotationValueMustBeClassLiteral(this.binding.declaringClass, this.f618name, expression4);
                            }
                            i++;
                        }
                        return;
                    }
                    return;
                }
                if (expression3 instanceof ClassLiteralAccess) {
                    return;
                }
                blockScope.problemReporter().annotationValueMustBeClassLiteral(this.binding.declaringClass, this.f618name, this.value);
                return;
            }
            switch (i2) {
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                    break;
                default:
                    if (typeBindingLeafComponentType.isEnum()) {
                        Expression expression5 = this.value;
                        if (expression5 instanceof NullLiteral) {
                            blockScope.problemReporter().annotationValueMustBeConstant(this.binding.declaringClass, this.f618name, this.value, true);
                        } else if (expression5 instanceof ArrayInitializer) {
                            Expression[] expressionArr2 = ((ArrayInitializer) expression5).expressions;
                            if (expressionArr2 != null) {
                                int length2 = expressionArr2.length;
                                while (i < length2) {
                                    Expression expression6 = expressionArr2[i];
                                    if (expression6 instanceof NullLiteral) {
                                        blockScope.problemReporter().annotationValueMustBeConstant(this.binding.declaringClass, this.f618name, expression6, true);
                                    } else if (expression6 instanceof NameReference) {
                                        Binding binding = ((NameReference) expression6).binding;
                                        if (binding.kind() == 1 && !((FieldBinding) binding).declaringClass.isEnum()) {
                                            blockScope.problemReporter().annotationValueMustBeConstant(this.binding.declaringClass, this.f618name, expression6, true);
                                        }
                                    }
                                    i++;
                                }
                            }
                        } else if (expression5 instanceof NameReference) {
                            Binding binding2 = ((NameReference) expression5).binding;
                            if (binding2.kind() == 1) {
                                FieldBinding fieldBinding = (FieldBinding) binding2;
                                if (!fieldBinding.declaringClass.isEnum()) {
                                    if (!fieldBinding.type.isArrayType()) {
                                        blockScope.problemReporter().annotationValueMustBeConstant(this.binding.declaringClass, this.f618name, this.value, true);
                                    } else {
                                        blockScope.problemReporter().annotationValueMustBeArrayInitializer(this.binding.declaringClass, this.f618name, this.value);
                                    }
                                }
                            }
                        } else {
                            blockScope.problemReporter().annotationValueMustBeConstant(this.binding.declaringClass, this.f618name, this.value, true);
                        }
                    } else if (typeBindingLeafComponentType.isAnnotationType()) {
                        if (!typeBindingResolveType.leafComponentType().isAnnotationType()) {
                            blockScope.problemReporter().annotationValueMustBeAnnotation(this.binding.declaringClass, this.f618name, this.value, typeBindingLeafComponentType);
                        } else {
                            Expression expression7 = this.value;
                            if (expression7 instanceof ArrayInitializer) {
                                Expression[] expressionArr3 = ((ArrayInitializer) expression7).expressions;
                                if (expressionArr3 != null) {
                                    int length3 = expressionArr3.length;
                                    while (i < length3) {
                                        Expression expression8 = expressionArr3[i];
                                        if ((expression8 instanceof NullLiteral) || !(expression8 instanceof Annotation)) {
                                            blockScope.problemReporter().annotationValueMustBeAnnotation(this.binding.declaringClass, this.f618name, expression8, typeBindingLeafComponentType);
                                        }
                                        i++;
                                    }
                                }
                            } else if (!(expression7 instanceof Annotation)) {
                                blockScope.problemReporter().annotationValueMustBeAnnotation(this.binding.declaringClass, this.f618name, this.value, typeBindingLeafComponentType);
                            }
                        }
                    }
                    break;
            }
            return;
        }
        Expression expression9 = this.value;
        if (expression9 instanceof ArrayInitializer) {
            Expression[] expressionArr4 = ((ArrayInitializer) expression9).expressions;
            if (expressionArr4 != null) {
                int length4 = expressionArr4.length;
                for (int i3 = 0; i3 < length4; i3++) {
                    Expression expression10 = expressionArr4[i3];
                    if (expression10.resolvedType != null && expression10.constant == Constant.NotAConstant) {
                        blockScope.problemReporter().annotationValueMustBeConstant(this.binding.declaringClass, this.f618name, expressionArr4[i3], false);
                    }
                }
                return;
            }
            return;
        }
        if (expression9.constant == Constant.NotAConstant) {
            if (typeBindingResolveType.isArrayType()) {
                blockScope.problemReporter().annotationValueMustBeArrayInitializer(this.binding.declaringClass, this.f618name, this.value);
            } else {
                blockScope.problemReporter().annotationValueMustBeConstant(this.binding.declaringClass, this.f618name, this.value, false);
            }
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.ASTNode
    public void traverse(ASTVisitor aSTVisitor, BlockScope blockScope) {
        Expression expression;
        if (aSTVisitor.visit(this, blockScope) && (expression = this.value) != null) {
            expression.traverse(aSTVisitor, blockScope);
        }
        aSTVisitor.endVisit(this, blockScope);
    }

    public void traverse(ASTVisitor aSTVisitor, ClassScope classScope) {
        Expression expression;
        if (aSTVisitor.visit(this, classScope) && (expression = this.value) != null) {
            expression.traverse(aSTVisitor, classScope);
        }
        aSTVisitor.endVisit(this, classScope);
    }
}
