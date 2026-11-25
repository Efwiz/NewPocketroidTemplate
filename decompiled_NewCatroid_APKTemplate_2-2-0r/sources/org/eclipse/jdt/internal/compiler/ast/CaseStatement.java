package org.eclipse.jdt.internal.compiler.ast;

import org.eclipse.jdt.internal.compiler.ASTVisitor;
import org.eclipse.jdt.internal.compiler.codegen.BranchLabel;
import org.eclipse.jdt.internal.compiler.codegen.CodeStream;
import org.eclipse.jdt.internal.compiler.flow.FlowContext;
import org.eclipse.jdt.internal.compiler.flow.FlowInfo;
import org.eclipse.jdt.internal.compiler.impl.Constant;
import org.eclipse.jdt.internal.compiler.impl.IntConstant;
import org.eclipse.jdt.internal.compiler.lookup.BlockScope;
import org.eclipse.jdt.internal.compiler.lookup.FieldBinding;
import org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding;
import org.eclipse.jdt.internal.compiler.lookup.TypeBinding;

/* JADX INFO: loaded from: classes14.dex */
public class CaseStatement extends Statement {
    public Expression constantExpression;
    public BranchLabel targetLabel;

    @Override // org.eclipse.jdt.internal.compiler.ast.Statement
    public void resolve(BlockScope blockScope) {
    }

    public CaseStatement(Expression expression, int i, int i2) {
        this.constantExpression = expression;
        this.sourceEnd = i;
        this.sourceStart = i2;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Statement
    public FlowInfo analyseCode(BlockScope blockScope, FlowContext flowContext, FlowInfo flowInfo) {
        Expression expression = this.constantExpression;
        if (expression != null) {
            if (expression.constant == Constant.NotAConstant && !this.constantExpression.resolvedType.isEnum()) {
                blockScope.problemReporter().caseExpressionMustBeConstant(this.constantExpression);
            }
            this.constantExpression.analyseCode(blockScope, flowContext, flowInfo);
        }
        return flowInfo;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Statement
    public StringBuffer printStatement(int i, StringBuffer stringBuffer) {
        printIndent(i, stringBuffer);
        if (this.constantExpression == null) {
            stringBuffer.append("default :");
        } else {
            stringBuffer.append("case ");
            this.constantExpression.printExpression(0, stringBuffer).append(" :");
        }
        return stringBuffer;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Statement
    public void generateCode(BlockScope blockScope, CodeStream codeStream) {
        if ((this.bits & Integer.MIN_VALUE) == 0) {
            return;
        }
        int i = codeStream.position;
        this.targetLabel.place();
        codeStream.recordPositionsFrom(i, this.sourceStart);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Statement
    public Constant resolveCase(BlockScope blockScope, TypeBinding typeBinding, SwitchStatement switchStatement) {
        blockScope.enclosingCase = this;
        if (this.constantExpression == null) {
            if (switchStatement.defaultCase != null) {
                blockScope.problemReporter().duplicateDefaultCase(this);
            }
            switchStatement.defaultCase = this;
            return Constant.NotAConstant;
        }
        CaseStatement[] caseStatementArr = switchStatement.cases;
        int i = switchStatement.caseCount;
        switchStatement.caseCount = i + 1;
        caseStatementArr[i] = this;
        if (typeBinding != null && typeBinding.isEnum()) {
            Expression expression = this.constantExpression;
            if (expression instanceof SingleNameReference) {
                ((SingleNameReference) expression).setActualReceiverType((ReferenceBinding) typeBinding);
            }
        }
        TypeBinding typeBindingResolveType = this.constantExpression.resolveType(blockScope);
        if (typeBindingResolveType == null || typeBinding == null) {
            return Constant.NotAConstant;
        }
        if (this.constantExpression.isConstantValueOfTypeAssignableToType(typeBindingResolveType, typeBinding) || typeBindingResolveType.isCompatibleWith(typeBinding)) {
            if (typeBindingResolveType.isEnum()) {
                if (((this.constantExpression.bits & ASTNode.ParenthesizedMASK) >> 21) != 0) {
                    blockScope.problemReporter().enumConstantsCannotBeSurroundedByParenthesis(this.constantExpression);
                }
                Expression expression2 = this.constantExpression;
                if ((expression2 instanceof NameReference) && (expression2.bits & 7) == 1) {
                    NameReference nameReference = (NameReference) this.constantExpression;
                    FieldBinding fieldBinding = nameReference.fieldBinding();
                    if ((fieldBinding.modifiers & 16384) == 0) {
                        blockScope.problemReporter().enumSwitchCannotTargetField(nameReference, fieldBinding);
                    } else if (nameReference instanceof QualifiedNameReference) {
                        blockScope.problemReporter().cannotUseQualifiedEnumConstantInCaseLabel(nameReference, fieldBinding);
                    }
                    return IntConstant.fromValue(fieldBinding.original().f627id + 1);
                }
            } else {
                return this.constantExpression.constant;
            }
        } else if (isBoxingCompatible(typeBindingResolveType, typeBinding, this.constantExpression, blockScope)) {
            return this.constantExpression.constant;
        }
        blockScope.problemReporter().typeMismatchError(typeBindingResolveType, typeBinding, this.constantExpression, switchStatement.expression);
        return Constant.NotAConstant;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.ASTNode
    public void traverse(ASTVisitor aSTVisitor, BlockScope blockScope) {
        Expression expression;
        if (aSTVisitor.visit(this, blockScope) && (expression = this.constantExpression) != null) {
            expression.traverse(aSTVisitor, blockScope);
        }
        aSTVisitor.endVisit(this, blockScope);
    }
}
