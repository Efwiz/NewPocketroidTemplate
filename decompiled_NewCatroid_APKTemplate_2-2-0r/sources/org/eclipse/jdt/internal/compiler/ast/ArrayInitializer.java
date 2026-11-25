package org.eclipse.jdt.internal.compiler.ast;

import org.eclipse.jdt.internal.compiler.ASTVisitor;
import org.eclipse.jdt.internal.compiler.codegen.CodeStream;
import org.eclipse.jdt.internal.compiler.flow.FlowContext;
import org.eclipse.jdt.internal.compiler.flow.FlowInfo;
import org.eclipse.jdt.internal.compiler.impl.Constant;
import org.eclipse.jdt.internal.compiler.lookup.ArrayBinding;
import org.eclipse.jdt.internal.compiler.lookup.BlockScope;
import org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding;
import org.eclipse.jdt.internal.compiler.lookup.TypeBinding;

/* JADX INFO: loaded from: classes14.dex */
public class ArrayInitializer extends Expression {
    public ArrayBinding binding;
    public Expression[] expressions;

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression, org.eclipse.jdt.internal.compiler.ast.Statement
    public FlowInfo analyseCode(BlockScope blockScope, FlowContext flowContext, FlowInfo flowInfo) {
        if (this.expressions != null) {
            boolean z = blockScope.compilerOptions().analyseResourceLeaks;
            boolean zUsesNullTypeAnnotations = blockScope.environment().usesNullTypeAnnotations();
            int length = this.expressions.length;
            for (int i = 0; i < length; i++) {
                flowInfo = this.expressions[i].analyseCode(blockScope, flowContext, flowInfo).unconditionalInits();
                if (z && FakedTrackingVariable.isAnyCloseable(this.expressions[i].resolvedType)) {
                    flowInfo = FakedTrackingVariable.markPassedToOutside(blockScope, this.expressions[i], flowInfo, flowContext, false);
                }
                if (zUsesNullTypeAnnotations) {
                    checkAgainstNullTypeAnnotation(blockScope, this.binding.elementsType(), this.expressions[i], flowContext, flowInfo);
                }
            }
        }
        return flowInfo;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression
    public void generateCode(BlockScope blockScope, CodeStream codeStream, boolean z) {
        generateCode(null, null, blockScope, codeStream, z);
    }

    public void generateCode(TypeReference typeReference, ArrayAllocationExpression arrayAllocationExpression, BlockScope blockScope, CodeStream codeStream, boolean z) {
        int i = codeStream.position;
        Expression[] expressionArr = this.expressions;
        int length = expressionArr == null ? 0 : expressionArr.length;
        codeStream.generateInlinedValue(length);
        codeStream.newArray(typeReference, arrayAllocationExpression, this.binding);
        if (this.expressions != null) {
            int i2 = this.binding.dimensions > 1 ? -1 : this.binding.leafComponentType.f626id;
            for (int i3 = 0; i3 < length; i3++) {
                Expression expression = this.expressions[i3];
                if (expression.constant != Constant.NotAConstant) {
                    switch (i2) {
                        case 2:
                        case 3:
                        case 4:
                        case 7:
                        case 10:
                            if (expression.constant.longValue() != 0) {
                                codeStream.dup();
                                codeStream.generateInlinedValue(i3);
                                expression.generateCode(blockScope, codeStream, true);
                                codeStream.arrayAtPut(i2, false);
                            }
                            break;
                        case 5:
                            if (expression.constant.booleanValue()) {
                                codeStream.dup();
                                codeStream.generateInlinedValue(i3);
                                expression.generateCode(blockScope, codeStream, true);
                                codeStream.arrayAtPut(i2, false);
                            }
                            break;
                        case 6:
                        default:
                            if (!(expression instanceof NullLiteral)) {
                                codeStream.dup();
                                codeStream.generateInlinedValue(i3);
                                expression.generateCode(blockScope, codeStream, true);
                                codeStream.arrayAtPut(i2, false);
                            }
                            break;
                        case 8:
                        case 9:
                            double dDoubleValue = expression.constant.doubleValue();
                            if (dDoubleValue == -0.0d || dDoubleValue != 0.0d) {
                                codeStream.dup();
                                codeStream.generateInlinedValue(i3);
                                expression.generateCode(blockScope, codeStream, true);
                                codeStream.arrayAtPut(i2, false);
                            }
                            break;
                    }
                } else if (!(expression instanceof NullLiteral)) {
                    codeStream.dup();
                    codeStream.generateInlinedValue(i3);
                    expression.generateCode(blockScope, codeStream, true);
                    codeStream.arrayAtPut(i2, false);
                }
            }
        }
        if (z) {
            codeStream.generateImplicitConversion(this.implicitConversion);
        } else {
            codeStream.pop();
        }
        codeStream.recordPositionsFrom(i, this.sourceStart);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression
    public StringBuffer printExpression(int i, StringBuffer stringBuffer) {
        stringBuffer.append('{');
        if (this.expressions != null) {
            int i2 = 20;
            for (int i3 = 0; i3 < this.expressions.length; i3++) {
                if (i3 > 0) {
                    stringBuffer.append(", ");
                }
                this.expressions[i3].printExpression(0, stringBuffer);
                i2--;
                if (i2 == 0) {
                    stringBuffer.append('\n');
                    printIndent(i + 1, stringBuffer);
                    i2 = 20;
                }
            }
        }
        return stringBuffer.append('}');
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression
    public TypeBinding resolveTypeExpecting(BlockScope blockScope, TypeBinding typeBinding) {
        ReferenceBinding javaLangObject;
        TypeBinding typeBindingResolveType;
        TypeBinding typeBindingResolveType2;
        this.constant = Constant.NotAConstant;
        int i = 1;
        if (typeBinding instanceof ArrayBinding) {
            if ((this.bits & 1) == 0) {
                TypeBinding typeBindingLeafComponentType = typeBinding.leafComponentType();
                if (!typeBindingLeafComponentType.isReifiable()) {
                    blockScope.problemReporter().illegalGenericArray(typeBindingLeafComponentType, this);
                }
            }
            ArrayBinding arrayBinding = (ArrayBinding) typeBinding;
            this.binding = arrayBinding;
            this.resolvedType = arrayBinding;
            if (this.expressions == null) {
                return this.binding;
            }
            TypeBinding typeBindingElementsType = this.binding.elementsType();
            int length = this.expressions.length;
            for (int i2 = 0; i2 < length; i2++) {
                Expression expression = this.expressions[i2];
                expression.setExpressionContext(ExpressionContext.ASSIGNMENT_CONTEXT);
                expression.setExpectedType(typeBindingElementsType);
                if (expression instanceof ArrayInitializer) {
                    typeBindingResolveType2 = expression.resolveTypeExpecting(blockScope, typeBindingElementsType);
                } else {
                    typeBindingResolveType2 = expression.resolveType(blockScope);
                }
                if (typeBindingResolveType2 != null) {
                    if (TypeBinding.notEquals(typeBindingElementsType, typeBindingResolveType2)) {
                        blockScope.compilationUnitScope().recordTypeConversion(typeBindingElementsType, typeBindingResolveType2);
                    }
                    if (expression.isConstantValueOfTypeAssignableToType(typeBindingResolveType2, typeBindingElementsType) || typeBindingResolveType2.isCompatibleWith(typeBindingElementsType)) {
                        expression.computeConversion(blockScope, typeBindingElementsType, typeBindingResolveType2);
                    } else if (isBoxingCompatible(typeBindingResolveType2, typeBindingElementsType, expression, blockScope)) {
                        expression.computeConversion(blockScope, typeBindingElementsType, typeBindingResolveType2);
                    } else {
                        blockScope.problemReporter().typeMismatchError(typeBindingResolveType2, typeBindingElementsType, expression, (ASTNode) null);
                    }
                }
            }
            return this.binding;
        }
        Expression[] expressionArr = this.expressions;
        if (expressionArr == null) {
            typeBindingResolveType = blockScope.getJavaLangObject();
        } else {
            Expression expression2 = expressionArr[0];
            int i3 = 1;
            while (true) {
                if (expression2 == null || !(expression2 instanceof ArrayInitializer)) {
                    javaLangObject = null;
                    break;
                }
                i3++;
                Expression[] expressionArr2 = ((ArrayInitializer) expression2).expressions;
                if (expressionArr2 == null) {
                    javaLangObject = blockScope.getJavaLangObject();
                    expression2 = null;
                    break;
                }
                expression2 = expressionArr2[0];
            }
            typeBindingResolveType = expression2 != null ? expression2.resolveType(blockScope) : javaLangObject;
            int length2 = this.expressions.length;
            while (i < length2) {
                Expression expression3 = this.expressions[i];
                if (expression3 != null) {
                    expression3.resolveType(blockScope);
                }
                i++;
            }
            i = i3;
        }
        if (typeBindingResolveType != null) {
            this.resolvedType = blockScope.createArrayType(typeBindingResolveType, i);
            if (typeBinding != null) {
                blockScope.problemReporter().typeMismatchError(this.resolvedType, typeBinding, this, (ASTNode) null);
            }
        }
        return null;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression, org.eclipse.jdt.internal.compiler.ast.ASTNode
    public void traverse(ASTVisitor aSTVisitor, BlockScope blockScope) {
        Expression[] expressionArr;
        if (aSTVisitor.visit(this, blockScope) && (expressionArr = this.expressions) != null) {
            int length = expressionArr.length;
            for (int i = 0; i < length; i++) {
                this.expressions[i].traverse(aSTVisitor, blockScope);
            }
        }
        aSTVisitor.endVisit(this, blockScope);
    }
}
