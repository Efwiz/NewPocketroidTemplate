package org.eclipse.jdt.internal.compiler.ast;

import org.eclipse.jdt.internal.compiler.ASTVisitor;
import org.eclipse.jdt.internal.compiler.classfmt.ClassFileConstants;
import org.eclipse.jdt.internal.compiler.codegen.BranchLabel;
import org.eclipse.jdt.internal.compiler.codegen.CodeStream;
import org.eclipse.jdt.internal.compiler.codegen.Opcodes;
import org.eclipse.jdt.internal.compiler.flow.FlowContext;
import org.eclipse.jdt.internal.compiler.flow.FlowInfo;
import org.eclipse.jdt.internal.compiler.flow.UnconditionalFlowInfo;
import org.eclipse.jdt.internal.compiler.impl.Constant;
import org.eclipse.jdt.internal.compiler.lookup.BlockScope;
import org.eclipse.jdt.internal.compiler.lookup.FieldBinding;
import org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding;
import org.eclipse.jdt.internal.compiler.lookup.SourceTypeBinding;
import org.eclipse.jdt.internal.compiler.lookup.TypeBinding;

/* JADX INFO: loaded from: classes14.dex */
public class AssertStatement extends Statement {
    public Expression assertExpression;
    private FieldBinding assertionSyntheticFieldBinding;
    public Expression exceptionArgument;
    int preAssertInitStateIndex = -1;

    public AssertStatement(Expression expression, Expression expression2, int i) {
        this.assertExpression = expression2;
        this.exceptionArgument = expression;
        this.sourceStart = i;
        this.sourceEnd = expression.sourceEnd;
    }

    public AssertStatement(Expression expression, int i) {
        this.assertExpression = expression;
        this.sourceStart = i;
        this.sourceEnd = expression.sourceEnd;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Statement
    public FlowInfo analyseCode(BlockScope blockScope, FlowContext flowContext, FlowInfo flowInfo) {
        this.preAssertInitStateIndex = blockScope.methodScope().recordInitializationStates(flowInfo);
        Constant constantOptimizedBooleanConstant = this.assertExpression.optimizedBooleanConstant();
        this.assertExpression.checkNPEbyUnboxing(blockScope, flowContext, flowInfo);
        boolean z = false;
        boolean z2 = constantOptimizedBooleanConstant != Constant.NotAConstant && constantOptimizedBooleanConstant.booleanValue();
        if (constantOptimizedBooleanConstant != Constant.NotAConstant && !constantOptimizedBooleanConstant.booleanValue()) {
            z = true;
        }
        flowContext.tagBits |= 4096;
        FlowInfo flowInfoAnalyseCode = this.assertExpression.analyseCode(blockScope, flowContext, flowInfo.copy());
        flowContext.extendTimeToLiveForNullCheckedField(1);
        flowContext.tagBits &= -4097;
        UnconditionalFlowInfo unconditionalFlowInfoUnconditionalInits = flowInfoAnalyseCode.initsWhenTrue().unconditionalInits();
        FlowInfo flowInfoInitsWhenFalse = flowInfoAnalyseCode.initsWhenFalse();
        if (z2) {
            flowInfoInitsWhenFalse.setReachMode(1);
        }
        Expression expression = this.exceptionArgument;
        if (expression != null) {
            FlowInfo flowInfoAnalyseCode2 = expression.analyseCode(blockScope, flowContext, flowInfoInitsWhenFalse.copy());
            if (z2) {
                blockScope.problemReporter().fakeReachable(this.exceptionArgument);
            } else {
                flowContext.checkExceptionHandlers(blockScope.getJavaLangAssertionError(), this, flowInfoAnalyseCode2, blockScope);
            }
        }
        if (!z2) {
            manageSyntheticAccessIfNecessary(blockScope, flowInfo);
        }
        flowContext.recordAbruptExit();
        if (z) {
            return flowInfo;
        }
        if (!blockScope.compilerOptions().includeNullInfoFromAsserts) {
            return flowInfo.nullInfoLessUnconditionalCopy().mergedWith(flowInfoInitsWhenFalse.nullInfoLessUnconditionalCopy()).addNullInfoFrom(flowInfo);
        }
        return flowInfo.mergedWith(flowInfoInitsWhenFalse.nullInfoLessUnconditionalCopy()).addInitializationsFrom(unconditionalFlowInfoUnconditionalInits.discardInitializationInfo());
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Statement
    public void generateCode(BlockScope blockScope, CodeStream codeStream) {
        if ((this.bits & Integer.MIN_VALUE) == 0) {
            return;
        }
        int i = codeStream.position;
        if (this.assertionSyntheticFieldBinding != null) {
            BranchLabel branchLabel = new BranchLabel(codeStream);
            codeStream.fieldAccess(Opcodes.OPC_getstatic, this.assertionSyntheticFieldBinding, null);
            codeStream.ifne(branchLabel);
            Expression expression = this.assertExpression;
            BranchLabel branchLabel2 = new BranchLabel(codeStream);
            expression.generateOptimizedBoolean(blockScope, codeStream, branchLabel2, null, true);
            codeStream.newJavaLangAssertionError();
            codeStream.dup();
            Expression expression2 = this.exceptionArgument;
            if (expression2 != null) {
                expression2.generateCode(blockScope, codeStream, true);
                codeStream.invokeJavaLangAssertionErrorConstructor(this.exceptionArgument.implicitConversion & 15);
            } else {
                codeStream.invokeJavaLangAssertionErrorDefaultConstructor();
            }
            codeStream.athrow();
            int i2 = this.preAssertInitStateIndex;
            if (i2 != -1) {
                codeStream.removeNotDefinitelyAssignedVariables(blockScope, i2);
            }
            branchLabel2.place();
            branchLabel.place();
        } else {
            int i3 = this.preAssertInitStateIndex;
            if (i3 != -1) {
                codeStream.removeNotDefinitelyAssignedVariables(blockScope, i3);
            }
        }
        codeStream.recordPositionsFrom(i, this.sourceStart);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Statement
    public void resolve(BlockScope blockScope) {
        TypeBinding typeBindingResolveType;
        this.assertExpression.resolveTypeExpecting(blockScope, TypeBinding.BOOLEAN);
        Expression expression = this.exceptionArgument;
        if (expression == null || (typeBindingResolveType = expression.resolveType(blockScope)) == null) {
            return;
        }
        int i = typeBindingResolveType.f626id;
        switch (i) {
            case 2:
            case 3:
            case 4:
            case 5:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
                break;
            case 6:
                blockScope.problemReporter().illegalVoidExpression(this.exceptionArgument);
            default:
                i = 1;
                break;
        }
        this.exceptionArgument.implicitConversion = (i << 4) + i;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.ASTNode
    public void traverse(ASTVisitor aSTVisitor, BlockScope blockScope) {
        if (aSTVisitor.visit(this, blockScope)) {
            this.assertExpression.traverse(aSTVisitor, blockScope);
            Expression expression = this.exceptionArgument;
            if (expression != null) {
                expression.traverse(aSTVisitor, blockScope);
            }
        }
        aSTVisitor.endVisit(this, blockScope);
    }

    public void manageSyntheticAccessIfNecessary(BlockScope blockScope, FlowInfo flowInfo) {
        ReferenceBinding referenceBindingEnclosingType;
        if ((flowInfo.tagBits & 1) == 0) {
            SourceTypeBinding sourceTypeBindingEnclosingSourceType = blockScope.enclosingSourceType();
            while (sourceTypeBindingEnclosingSourceType.isLocalType() && (referenceBindingEnclosingType = sourceTypeBindingEnclosingSourceType.enclosingType()) != null && !referenceBindingEnclosingType.isInterface()) {
                sourceTypeBindingEnclosingSourceType = (SourceTypeBinding) referenceBindingEnclosingType;
            }
            this.assertionSyntheticFieldBinding = sourceTypeBindingEnclosingSourceType.addSyntheticFieldForAssert(blockScope);
            for (AbstractMethodDeclaration abstractMethodDeclaration : sourceTypeBindingEnclosingSourceType.scope.referenceType().methods) {
                if (abstractMethodDeclaration.isClinit()) {
                    ((Clinit) abstractMethodDeclaration).setAssertionSupport(this.assertionSyntheticFieldBinding, blockScope.compilerOptions().sourceLevel < ClassFileConstants.JDK1_5);
                    return;
                }
            }
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Statement
    public StringBuffer printStatement(int i, StringBuffer stringBuffer) {
        printIndent(i, stringBuffer);
        stringBuffer.append("assert ");
        this.assertExpression.printExpression(0, stringBuffer);
        if (this.exceptionArgument != null) {
            stringBuffer.append(": ");
            this.exceptionArgument.printExpression(0, stringBuffer);
        }
        return stringBuffer.append(';');
    }
}
