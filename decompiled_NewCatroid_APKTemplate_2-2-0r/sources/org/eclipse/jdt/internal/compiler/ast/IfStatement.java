package org.eclipse.jdt.internal.compiler.ast;

import org.eclipse.jdt.internal.compiler.ASTVisitor;
import org.eclipse.jdt.internal.compiler.codegen.BranchLabel;
import org.eclipse.jdt.internal.compiler.codegen.CodeStream;
import org.eclipse.jdt.internal.compiler.flow.FlowContext;
import org.eclipse.jdt.internal.compiler.flow.FlowInfo;
import org.eclipse.jdt.internal.compiler.flow.UnconditionalFlowInfo;
import org.eclipse.jdt.internal.compiler.impl.Constant;
import org.eclipse.jdt.internal.compiler.lookup.BlockScope;
import org.eclipse.jdt.internal.compiler.lookup.TypeBinding;

/* JADX INFO: loaded from: classes14.dex */
public class IfStatement extends Statement {
    public Expression condition;
    public Statement elseStatement;
    public Statement thenStatement;
    int thenInitStateIndex = -1;
    int elseInitStateIndex = -1;
    int mergedInitStateIndex = -1;

    public IfStatement(Expression expression, Statement statement, int i, int i2) {
        this.condition = expression;
        this.thenStatement = statement;
        if (statement instanceof EmptyStatement) {
            statement.bits |= 1;
        }
        this.sourceStart = i;
        this.sourceEnd = i2;
    }

    public IfStatement(Expression expression, Statement statement, Statement statement2, int i, int i2) {
        this.condition = expression;
        this.thenStatement = statement;
        if (statement instanceof EmptyStatement) {
            statement.bits |= 1;
        }
        this.elseStatement = statement2;
        if (statement2 instanceof IfStatement) {
            statement2.bits |= 536870912;
        }
        if (statement2 instanceof EmptyStatement) {
            statement2.bits |= 1;
        }
        this.sourceStart = i;
        this.sourceEnd = i2;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Statement
    public FlowInfo analyseCode(BlockScope blockScope, FlowContext flowContext, FlowInfo flowInfo) {
        FlowInfo flowInfoAnalyseCode = this.condition.analyseCode(blockScope, flowContext, flowInfo);
        int i = (flowInfo.reachMode() & 3) != 0 ? 1 : 0;
        Constant constantOptimizedBooleanConstant = this.condition.optimizedBooleanConstant();
        this.condition.checkNPEbyUnboxing(blockScope, flowContext, flowInfo);
        boolean z = constantOptimizedBooleanConstant != Constant.NotAConstant && constantOptimizedBooleanConstant.booleanValue();
        boolean z2 = (constantOptimizedBooleanConstant == Constant.NotAConstant || constantOptimizedBooleanConstant.booleanValue()) ? false : true;
        flowContext.conditionalLevel++;
        FlowInfo flowInfoSafeInitsWhenTrue = flowInfoAnalyseCode.safeInitsWhenTrue();
        if (z2) {
            flowInfoSafeInitsWhenTrue.setReachMode(1);
        }
        FlowInfo flowInfoCopy = flowInfoAnalyseCode.initsWhenFalse().copy();
        if (z) {
            flowInfoCopy.setReachMode(1);
        }
        if ((flowInfo.tagBits & 3) == 0 && (flowInfoSafeInitsWhenTrue.tagBits & 3) != 0) {
            this.bits |= 256;
        } else if ((flowInfo.tagBits & 3) == 0 && (flowInfoCopy.tagBits & 3) != 0) {
            this.bits |= 128;
        }
        boolean z3 = !isKnowDeadCodePattern(this.condition) || blockScope.compilerOptions().reportDeadCodeInTrivialIfStatement;
        if (this.thenStatement != null) {
            this.thenInitStateIndex = blockScope.methodScope().recordInitializationStates(flowInfoSafeInitsWhenTrue);
            if (z2 || (this.bits & 256) != 0) {
                if (z3) {
                    this.thenStatement.complainIfUnreachable(flowInfoSafeInitsWhenTrue, blockScope, i, false);
                } else {
                    this.bits &= -257;
                }
            }
            flowInfoSafeInitsWhenTrue = this.thenStatement.analyseCode(blockScope, flowContext, flowInfoSafeInitsWhenTrue);
            if (!(this.thenStatement instanceof Block)) {
                flowContext.expireNullCheckedFieldInfo();
            }
        }
        flowContext.expireNullCheckedFieldInfo();
        if ((flowInfoSafeInitsWhenTrue.tagBits & 1) != 0) {
            this.bits |= 1073741824;
        }
        if (this.elseStatement != null) {
            if (flowInfoSafeInitsWhenTrue == FlowInfo.DEAD_END && (this.bits & 536870912) == 0 && !(this.elseStatement instanceof IfStatement)) {
                blockScope.problemReporter().unnecessaryElse(this.elseStatement);
            }
            this.elseInitStateIndex = blockScope.methodScope().recordInitializationStates(flowInfoCopy);
            if (z || (this.bits & 128) != 0) {
                if (z3) {
                    this.elseStatement.complainIfUnreachable(flowInfoCopy, blockScope, i, false);
                } else {
                    this.bits &= -129;
                }
            }
            flowInfoCopy = this.elseStatement.analyseCode(blockScope, flowContext, flowInfoCopy);
            if (!(this.elseStatement instanceof Block)) {
                flowContext.expireNullCheckedFieldInfo();
            }
        }
        FlowInfo flowInfo2 = flowInfoCopy;
        blockScope.correlateTrackingVarsIfElse(flowInfoSafeInitsWhenTrue, flowInfo2);
        UnconditionalFlowInfo unconditionalFlowInfoMergedOptimizedBranchesIfElse = FlowInfo.mergedOptimizedBranchesIfElse(flowInfoSafeInitsWhenTrue, z, flowInfo2, z2, true, flowInfo, this, z3);
        this.mergedInitStateIndex = blockScope.methodScope().recordInitializationStates(unconditionalFlowInfoMergedOptimizedBranchesIfElse);
        flowContext.conditionalLevel--;
        return unconditionalFlowInfoMergedOptimizedBranchesIfElse;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Statement
    public void generateCode(BlockScope blockScope, CodeStream codeStream) {
        Statement statement;
        Statement statement2;
        BranchLabel branchLabel;
        BranchLabel branchLabel2;
        if ((this.bits & Integer.MIN_VALUE) == 0) {
            return;
        }
        int i = codeStream.position;
        BranchLabel branchLabel3 = new BranchLabel(codeStream);
        Constant constantOptimizedBooleanConstant = this.condition.optimizedBooleanConstant();
        boolean z = ((constantOptimizedBooleanConstant != Constant.NotAConstant && !constantOptimizedBooleanConstant.booleanValue()) || (statement = this.thenStatement) == null || statement.isEmptyBlock()) ? false : true;
        boolean z2 = ((constantOptimizedBooleanConstant != Constant.NotAConstant && constantOptimizedBooleanConstant.booleanValue()) || (statement2 = this.elseStatement) == null || statement2.isEmptyBlock()) ? false : true;
        if (z) {
            BranchLabel branchLabel4 = null;
            if (constantOptimizedBooleanConstant != Constant.NotAConstant && constantOptimizedBooleanConstant.booleanValue()) {
                this.condition.generateCode(blockScope, codeStream, false);
            } else {
                Expression expression = this.condition;
                if (z2) {
                    branchLabel2 = new BranchLabel(codeStream);
                    branchLabel = branchLabel2;
                } else {
                    branchLabel = null;
                    branchLabel2 = branchLabel3;
                }
                expression.generateOptimizedBoolean(blockScope, codeStream, null, branchLabel2, true);
                branchLabel4 = branchLabel;
            }
            int i2 = this.thenInitStateIndex;
            if (i2 != -1) {
                codeStream.removeNotDefinitelyAssignedVariables(blockScope, i2);
                codeStream.addDefinitelyAssignedVariables(blockScope, this.thenInitStateIndex);
            }
            this.thenStatement.generateCode(blockScope, codeStream);
            if (z2) {
                if ((this.bits & 1073741824) == 0) {
                    this.thenStatement.branchChainTo(branchLabel3);
                    int i3 = codeStream.position;
                    codeStream.goto_(branchLabel3);
                    codeStream.recordPositionsFrom(i3, this.thenStatement.sourceEnd);
                }
                int i4 = this.elseInitStateIndex;
                if (i4 != -1) {
                    codeStream.removeNotDefinitelyAssignedVariables(blockScope, i4);
                    codeStream.addDefinitelyAssignedVariables(blockScope, this.elseInitStateIndex);
                }
                if (branchLabel4 != null) {
                    branchLabel4.place();
                }
                this.elseStatement.generateCode(blockScope, codeStream);
            }
        } else if (z2) {
            if (constantOptimizedBooleanConstant != Constant.NotAConstant && !constantOptimizedBooleanConstant.booleanValue()) {
                this.condition.generateCode(blockScope, codeStream, false);
            } else {
                this.condition.generateOptimizedBoolean(blockScope, codeStream, branchLabel3, null, true);
            }
            int i5 = this.elseInitStateIndex;
            if (i5 != -1) {
                codeStream.removeNotDefinitelyAssignedVariables(blockScope, i5);
                codeStream.addDefinitelyAssignedVariables(blockScope, this.elseInitStateIndex);
            }
            this.elseStatement.generateCode(blockScope, codeStream);
        } else {
            this.condition.generateCode(blockScope, codeStream, false);
            codeStream.recordPositionsFrom(i, this.sourceStart);
        }
        int i6 = this.mergedInitStateIndex;
        if (i6 != -1) {
            codeStream.removeNotDefinitelyAssignedVariables(blockScope, i6);
            codeStream.addDefinitelyAssignedVariables(blockScope, this.mergedInitStateIndex);
        }
        branchLabel3.place();
        codeStream.recordPositionsFrom(i, this.sourceStart);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Statement
    public StringBuffer printStatement(int i, StringBuffer stringBuffer) {
        printIndent(i, stringBuffer).append("if (");
        this.condition.printExpression(0, stringBuffer).append(")\n");
        int i2 = i + 2;
        this.thenStatement.printStatement(i2, stringBuffer);
        if (this.elseStatement != null) {
            stringBuffer.append('\n');
            printIndent(i, stringBuffer);
            stringBuffer.append("else\n");
            this.elseStatement.printStatement(i2, stringBuffer);
        }
        return stringBuffer;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Statement
    public void resolve(BlockScope blockScope) {
        TypeBinding typeBindingResolveTypeExpecting = this.condition.resolveTypeExpecting(blockScope, TypeBinding.BOOLEAN);
        this.condition.computeConversion(blockScope, typeBindingResolveTypeExpecting, typeBindingResolveTypeExpecting);
        Statement statement = this.thenStatement;
        if (statement != null) {
            statement.resolve(blockScope);
        }
        Statement statement2 = this.elseStatement;
        if (statement2 != null) {
            statement2.resolve(blockScope);
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.ASTNode
    public void traverse(ASTVisitor aSTVisitor, BlockScope blockScope) {
        if (aSTVisitor.visit(this, blockScope)) {
            this.condition.traverse(aSTVisitor, blockScope);
            Statement statement = this.thenStatement;
            if (statement != null) {
                statement.traverse(aSTVisitor, blockScope);
            }
            Statement statement2 = this.elseStatement;
            if (statement2 != null) {
                statement2.traverse(aSTVisitor, blockScope);
            }
        }
        aSTVisitor.endVisit(this, blockScope);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Statement
    public boolean doesNotCompleteNormally() {
        Statement statement;
        Statement statement2 = this.thenStatement;
        return statement2 != null && statement2.doesNotCompleteNormally() && (statement = this.elseStatement) != null && statement.doesNotCompleteNormally();
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Statement
    public boolean completesByContinue() {
        Statement statement = this.thenStatement;
        if (statement != null && statement.completesByContinue()) {
            return true;
        }
        Statement statement2 = this.elseStatement;
        return statement2 != null && statement2.completesByContinue();
    }
}
