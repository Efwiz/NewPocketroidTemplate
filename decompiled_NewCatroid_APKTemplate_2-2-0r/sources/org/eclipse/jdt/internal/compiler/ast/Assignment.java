package org.eclipse.jdt.internal.compiler.ast;

import org.eclipse.jdt.internal.compiler.ASTVisitor;
import org.eclipse.jdt.internal.compiler.classfmt.ClassFileConstants;
import org.eclipse.jdt.internal.compiler.codegen.CodeStream;
import org.eclipse.jdt.internal.compiler.flow.FlowContext;
import org.eclipse.jdt.internal.compiler.flow.FlowInfo;
import org.eclipse.jdt.internal.compiler.flow.UnconditionalFlowInfo;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.eclipse.jdt.internal.compiler.impl.Constant;
import org.eclipse.jdt.internal.compiler.lookup.Binding;
import org.eclipse.jdt.internal.compiler.lookup.BlockScope;
import org.eclipse.jdt.internal.compiler.lookup.FieldBinding;
import org.eclipse.jdt.internal.compiler.lookup.LocalVariableBinding;
import org.eclipse.jdt.internal.compiler.lookup.TypeBinding;
import org.eclipse.jdt.internal.compiler.lookup.VariableBinding;
import org.eclipse.jdt.internal.compiler.lookup.WildcardBinding;

/* JADX INFO: loaded from: classes14.dex */
public class Assignment extends Expression {
    public Expression expression;
    public Expression lhs;

    public Assignment(Expression expression, Expression expression2, int i) {
        this.lhs = expression;
        expression.bits |= 8192;
        this.expression = expression2;
        this.sourceStart = expression.sourceStart;
        this.sourceEnd = i;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression, org.eclipse.jdt.internal.compiler.ast.Statement
    public FlowInfo analyseCode(BlockScope blockScope, FlowContext flowContext, FlowInfo flowInfo) {
        UnconditionalFlowInfo unconditionalFlowInfoUnconditionalCopy;
        LocalVariableBinding localVariableBinding = this.lhs.localVariableBinding();
        this.expression.checkNPEbyUnboxing(blockScope, flowContext, flowInfo);
        CompilerOptions compilerOptions = blockScope.compilerOptions();
        boolean z = localVariableBinding != null && flowInfo.reachMode() == 0 && compilerOptions.analyseResourceLeaks && (FakedTrackingVariable.isAnyCloseable(this.expression.resolvedType) || this.expression.resolvedType == TypeBinding.NULL);
        if (z) {
            unconditionalFlowInfoUnconditionalCopy = flowInfo.unconditionalCopy();
            FakedTrackingVariable.preConnectTrackerAcrossAssignment(this, localVariableBinding, this.expression, flowInfo);
        } else {
            unconditionalFlowInfoUnconditionalCopy = null;
        }
        UnconditionalFlowInfo unconditionalFlowInfo = unconditionalFlowInfoUnconditionalCopy;
        UnconditionalFlowInfo unconditionalFlowInfoUnconditionalInits = ((Reference) this.lhs).analyseAssignment(blockScope, flowContext, flowInfo, this, false).unconditionalInits();
        if (z) {
            FakedTrackingVariable.handleResourceAssignment(blockScope, unconditionalFlowInfo, unconditionalFlowInfoUnconditionalInits, flowContext, this, this.expression, localVariableBinding);
        } else {
            FakedTrackingVariable.cleanUpAfterAssignment(blockScope, this.lhs.bits, this.expression);
        }
        int iNullStatus = this.expression.nullStatus(unconditionalFlowInfoUnconditionalInits, flowContext);
        if (localVariableBinding != null && (localVariableBinding.type.tagBits & 2) == 0 && iNullStatus == 2) {
            flowContext.recordUsingNullReference(blockScope, localVariableBinding, this.lhs, 769, unconditionalFlowInfoUnconditionalInits);
        }
        if (compilerOptions.isAnnotationBasedNullAnalysisEnabled) {
            VariableBinding variableBindingNullAnnotatedVariableBinding = this.lhs.nullAnnotatedVariableBinding(compilerOptions.sourceLevel >= ClassFileConstants.JDK1_8);
            if (variableBindingNullAnnotatedVariableBinding != null) {
                Expression expression = this.expression;
                iNullStatus = NullAnnotationMatching.checkAssignment(blockScope, flowContext, variableBindingNullAnnotatedVariableBinding, unconditionalFlowInfoUnconditionalInits, iNullStatus, expression, expression.resolvedType);
                if (iNullStatus == 4 && (variableBindingNullAnnotatedVariableBinding instanceof FieldBinding) && (this.lhs instanceof Reference) && compilerOptions.enableSyntacticNullAnalysisForFields) {
                    flowContext.recordNullCheckedFieldReference((Reference) this.lhs, (this.bits & 16) != 0 ? 2 : 1);
                }
            }
        }
        if (localVariableBinding != null && (localVariableBinding.type.tagBits & 2) == 0) {
            unconditionalFlowInfoUnconditionalInits.markNullStatus(localVariableBinding, iNullStatus);
            flowContext.markFinallyNullStatus(localVariableBinding, iNullStatus);
        }
        return unconditionalFlowInfoUnconditionalInits;
    }

    void checkAssignment(BlockScope blockScope, TypeBinding typeBinding, TypeBinding typeBinding2) {
        FieldBinding lastField = getLastField(this.lhs);
        if (lastField != null && typeBinding2 != TypeBinding.NULL && typeBinding.kind() == 516 && ((WildcardBinding) typeBinding).boundKind != 2) {
            blockScope.problemReporter().wildcardAssignment(typeBinding, typeBinding2, this.expression);
            return;
        }
        if (lastField != null && !lastField.isStatic() && lastField.declaringClass != null && lastField.declaringClass.isRawType()) {
            blockScope.problemReporter().unsafeRawFieldAssignment(lastField, typeBinding2, this.lhs);
        } else if (typeBinding2.needsUncheckedConversion(typeBinding)) {
            blockScope.problemReporter().unsafeTypeConversion(this.expression, typeBinding2, typeBinding);
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression
    public void generateCode(BlockScope blockScope, CodeStream codeStream, boolean z) {
        int i = codeStream.position;
        ((Reference) this.lhs).generateAssignment(blockScope, codeStream, this, z);
        codeStream.recordPositionsFrom(i, this.sourceStart);
    }

    FieldBinding getLastField(Expression expression) {
        if (expression instanceof SingleNameReference) {
            if ((expression.bits & 7) == 1) {
                return (FieldBinding) ((SingleNameReference) expression).binding;
            }
            return null;
        }
        if (expression instanceof FieldReference) {
            return ((FieldReference) expression).binding;
        }
        if (!(expression instanceof QualifiedNameReference)) {
            return null;
        }
        QualifiedNameReference qualifiedNameReference = (QualifiedNameReference) expression;
        if (qualifiedNameReference.otherBindings == null) {
            if ((expression.bits & 7) == 1) {
                return (FieldBinding) qualifiedNameReference.binding;
            }
            return null;
        }
        return qualifiedNameReference.otherBindings[qualifiedNameReference.otherBindings.length - 1];
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression
    public int nullStatus(FlowInfo flowInfo, FlowContext flowContext) {
        if ((this.implicitConversion & 512) != 0) {
            return 4;
        }
        return this.expression.nullStatus(flowInfo, flowContext);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression, org.eclipse.jdt.internal.compiler.ast.Statement, org.eclipse.jdt.internal.compiler.ast.ASTNode
    public StringBuffer print(int i, StringBuffer stringBuffer) {
        printIndent(i, stringBuffer);
        return printExpressionNoParenthesis(i, stringBuffer);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression
    public StringBuffer printExpression(int i, StringBuffer stringBuffer) {
        stringBuffer.append('(');
        return printExpressionNoParenthesis(0, stringBuffer).append(')');
    }

    public StringBuffer printExpressionNoParenthesis(int i, StringBuffer stringBuffer) {
        this.lhs.printExpression(i, stringBuffer).append(" = ");
        return this.expression.printExpression(0, stringBuffer);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression, org.eclipse.jdt.internal.compiler.ast.Statement
    public StringBuffer printStatement(int i, StringBuffer stringBuffer) {
        return print(i, stringBuffer).append(';');
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression
    public TypeBinding resolveType(BlockScope blockScope) {
        this.constant = Constant.NotAConstant;
        Expression expression = this.lhs;
        if (!(expression instanceof Reference) || expression.isThis()) {
            blockScope.problemReporter().expressionShouldBeAVariable(this.lhs);
            return null;
        }
        TypeBinding typeBindingResolveType = this.lhs.resolveType(blockScope);
        this.expression.setExpressionContext(ExpressionContext.ASSIGNMENT_CONTEXT);
        this.expression.setExpectedType(typeBindingResolveType);
        if (typeBindingResolveType != null) {
            this.resolvedType = typeBindingResolveType.capture(blockScope, this.lhs.sourceStart, this.lhs.sourceEnd);
        }
        LocalVariableBinding localVariableBinding = this.lhs.localVariableBinding();
        if (localVariableBinding != null && (localVariableBinding.isCatchParameter() || localVariableBinding.isParameter())) {
            localVariableBinding.tagBits &= -2049;
        }
        TypeBinding typeBindingResolveType2 = this.expression.resolveType(blockScope);
        if (typeBindingResolveType == null || typeBindingResolveType2 == null) {
            return null;
        }
        Binding directBinding = getDirectBinding(this.lhs);
        if (directBinding != null && !directBinding.isVolatile() && directBinding == getDirectBinding(this.expression)) {
            blockScope.problemReporter().assignmentHasNoEffect(this, directBinding.shortReadableName());
        }
        if (TypeBinding.notEquals(typeBindingResolveType, typeBindingResolveType2)) {
            blockScope.compilationUnitScope().recordTypeConversion(typeBindingResolveType, typeBindingResolveType2);
        }
        if (this.expression.isConstantValueOfTypeAssignableToType(typeBindingResolveType2, typeBindingResolveType) || typeBindingResolveType2.isCompatibleWith(typeBindingResolveType, blockScope)) {
            this.expression.computeConversion(blockScope, typeBindingResolveType, typeBindingResolveType2);
            checkAssignment(blockScope, typeBindingResolveType, typeBindingResolveType2);
            Expression expression2 = this.expression;
            if ((expression2 instanceof CastExpression) && (expression2.bits & 16384) == 0) {
                CastExpression.checkNeedForAssignedCast(blockScope, typeBindingResolveType, (CastExpression) this.expression);
            }
            return this.resolvedType;
        }
        if (isBoxingCompatible(typeBindingResolveType2, typeBindingResolveType, this.expression, blockScope)) {
            this.expression.computeConversion(blockScope, typeBindingResolveType, typeBindingResolveType2);
            Expression expression3 = this.expression;
            if ((expression3 instanceof CastExpression) && (expression3.bits & 16384) == 0) {
                CastExpression.checkNeedForAssignedCast(blockScope, typeBindingResolveType, (CastExpression) this.expression);
            }
            return this.resolvedType;
        }
        blockScope.problemReporter().typeMismatchError(typeBindingResolveType2, typeBindingResolveType, this.expression, this.lhs);
        return typeBindingResolveType;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression
    public TypeBinding resolveTypeExpecting(BlockScope blockScope, TypeBinding typeBinding) {
        TypeBinding typeBindingResolveTypeExpecting = super.resolveTypeExpecting(blockScope, typeBinding);
        if (typeBindingResolveTypeExpecting == null) {
            return null;
        }
        TypeBinding typeBinding2 = this.resolvedType;
        TypeBinding typeBinding3 = this.expression.resolvedType;
        if (TypeBinding.equalsEquals(typeBinding, TypeBinding.BOOLEAN) && TypeBinding.equalsEquals(typeBinding2, TypeBinding.BOOLEAN) && (this.lhs.bits & 8192) != 0) {
            blockScope.problemReporter().possibleAccidentalBooleanAssignment(this);
        }
        checkAssignment(blockScope, typeBinding2, typeBinding3);
        return typeBindingResolveTypeExpecting;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression, org.eclipse.jdt.internal.compiler.ast.ASTNode
    public void traverse(ASTVisitor aSTVisitor, BlockScope blockScope) {
        if (aSTVisitor.visit(this, blockScope)) {
            this.lhs.traverse(aSTVisitor, blockScope);
            this.expression.traverse(aSTVisitor, blockScope);
        }
        aSTVisitor.endVisit(this, blockScope);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression
    public LocalVariableBinding localVariableBinding() {
        return this.lhs.localVariableBinding();
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression
    public boolean statementExpression() {
        return (this.bits & ASTNode.ParenthesizedMASK) == 0;
    }
}
