package org.eclipse.jdt.internal.compiler.ast;

import org.eclipse.jdt.internal.compiler.ASTVisitor;
import org.eclipse.jdt.internal.compiler.classfmt.ClassFileConstants;
import org.eclipse.jdt.internal.compiler.codegen.BranchLabel;
import org.eclipse.jdt.internal.compiler.codegen.CodeStream;
import org.eclipse.jdt.internal.compiler.flow.FlowContext;
import org.eclipse.jdt.internal.compiler.flow.FlowInfo;
import org.eclipse.jdt.internal.compiler.flow.LoopingFlowContext;
import org.eclipse.jdt.internal.compiler.flow.UnconditionalFlowInfo;
import org.eclipse.jdt.internal.compiler.impl.Constant;
import org.eclipse.jdt.internal.compiler.lookup.BlockScope;
import org.eclipse.jdt.internal.compiler.lookup.TypeBinding;

/* JADX INFO: loaded from: classes14.dex */
public class WhileStatement extends Statement {
    public Statement action;
    private BranchLabel breakLabel;
    public Expression condition;
    private BranchLabel continueLabel;
    int preCondInitStateIndex = -1;
    int condIfTrueInitStateIndex = -1;
    int mergedInitStateIndex = -1;

    public WhileStatement(Expression expression, Statement statement, int i, int i2) {
        this.condition = expression;
        this.action = statement;
        if (statement instanceof EmptyStatement) {
            statement.bits |= 1;
        }
        this.sourceStart = i;
        this.sourceEnd = i2;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Statement
    public FlowInfo analyseCode(BlockScope blockScope, FlowContext flowContext, FlowInfo flowInfo) {
        FlowInfo flowInfoCopy;
        boolean z;
        boolean z2;
        UnconditionalFlowInfo unconditionalFlowInfoMergedWith;
        FlowInfo flowInfoAddInitializationsFrom;
        this.breakLabel = new BranchLabel();
        this.continueLabel = new BranchLabel();
        int i = (flowInfo.reachMode() & 3) != 0 ? 1 : 0;
        Constant constant = this.condition.constant;
        boolean z3 = constant != Constant.NotAConstant && constant.booleanValue();
        boolean z4 = (constant == Constant.NotAConstant || constant.booleanValue()) ? false : true;
        Constant constantOptimizedBooleanConstant = this.condition.optimizedBooleanConstant();
        boolean z5 = constantOptimizedBooleanConstant != Constant.NotAConstant && constantOptimizedBooleanConstant.booleanValue();
        boolean z6 = (constantOptimizedBooleanConstant == Constant.NotAConstant || constantOptimizedBooleanConstant.booleanValue()) ? false : true;
        this.preCondInitStateIndex = blockScope.methodScope().recordInitializationStates(flowInfo);
        UnconditionalFlowInfo unconditionalFlowInfoNullInfoLessUnconditionalCopy = flowInfo.nullInfoLessUnconditionalCopy();
        Expression expression = this.condition;
        boolean z7 = z6;
        boolean z8 = z5;
        LoopingFlowContext loopingFlowContext = new LoopingFlowContext(flowContext, flowInfo, this, null, null, blockScope, true);
        FlowInfo flowInfoAnalyseCode = expression.analyseCode(blockScope, loopingFlowContext, unconditionalFlowInfoNullInfoLessUnconditionalCopy);
        this.condition.checkNPEbyUnboxing(blockScope, flowContext, flowInfo);
        Statement statement = this.action;
        if (statement == null || (statement.isEmptyBlock() && blockScope.compilerOptions().complianceLevel <= ClassFileConstants.JDK1_3)) {
            boolean z9 = z3;
            loopingFlowContext.complainOnDeferredFinalChecks(blockScope, flowInfoAnalyseCode);
            loopingFlowContext.complainOnDeferredNullChecks(blockScope, flowInfoAnalyseCode.unconditionalInits());
            if (z9) {
                return FlowInfo.DEAD_END;
            }
            FlowInfo flowInfoAddInitializationsFrom2 = flowInfo.copy().addInitializationsFrom(flowInfoAnalyseCode.initsWhenFalse());
            if (z8) {
                flowInfoAddInitializationsFrom2.setReachMode(1);
            }
            this.mergedInitStateIndex = blockScope.methodScope().recordInitializationStates(flowInfoAddInitializationsFrom2);
            return flowInfoAddInitializationsFrom2;
        }
        boolean z10 = z3;
        LoopingFlowContext loopingFlowContext2 = new LoopingFlowContext(flowContext, flowInfo, this, this.breakLabel, this.continueLabel, blockScope, true);
        if (z4) {
            flowInfoCopy = FlowInfo.DEAD_END;
            z = z7;
            z2 = true;
        } else {
            flowInfoCopy = flowInfoAnalyseCode.initsWhenTrue().copy();
            z = z7;
            z2 = true;
            if (z) {
                flowInfoCopy.setReachMode(1);
            }
        }
        this.condIfTrueInitStateIndex = blockScope.methodScope().recordInitializationStates(flowInfoAnalyseCode.initsWhenTrue());
        if (this.action.complainIfUnreachable(flowInfoCopy, blockScope, i, z2) < 2) {
            flowInfoCopy = this.action.analyseCode(blockScope, loopingFlowContext2, flowInfoCopy);
        }
        FlowInfo flowInfoCopy2 = flowInfo.copy();
        int i2 = flowInfoCopy.tagBits & loopingFlowContext2.initsOnContinue.tagBits;
        if ((i2 & 3) != 0) {
            if ((i2 & 1) != 0) {
                this.continueLabel = null;
            }
            flowInfoCopy2.addInitializationsFrom(flowInfoAnalyseCode.initsWhenFalse());
            unconditionalFlowInfoMergedWith = flowInfoCopy.mergedWith(loopingFlowContext2.initsOnContinue.unconditionalInits());
            loopingFlowContext.complainOnDeferredNullChecks(blockScope, unconditionalFlowInfoMergedWith, false);
            loopingFlowContext2.complainOnDeferredNullChecks(blockScope, unconditionalFlowInfoMergedWith, false);
        } else {
            loopingFlowContext.complainOnDeferredFinalChecks(blockScope, flowInfoAnalyseCode);
            unconditionalFlowInfoMergedWith = flowInfoCopy.mergedWith(loopingFlowContext2.initsOnContinue.unconditionalInits());
            loopingFlowContext.complainOnDeferredNullChecks(blockScope, unconditionalFlowInfoMergedWith);
            loopingFlowContext2.complainOnDeferredFinalChecks(blockScope, unconditionalFlowInfoMergedWith);
            loopingFlowContext2.complainOnDeferredNullChecks(blockScope, unconditionalFlowInfoMergedWith);
            flowInfoCopy2.addPotentialInitializationsFrom(unconditionalFlowInfoMergedWith.unconditionalInits()).addInitializationsFrom(flowInfoAnalyseCode.initsWhenFalse());
        }
        if (loopingFlowContext2.hasEscapingExceptions()) {
            FlowInfo flowInfoCopy3 = flowInfo.copy();
            if (this.continueLabel != null) {
                flowInfoCopy3 = flowInfoCopy3.mergedWith(flowInfoCopy3.unconditionalCopy().addNullInfoFrom(unconditionalFlowInfoMergedWith).unconditionalInits());
            }
            loopingFlowContext2.simulateThrowAfterLoopBack(flowInfoCopy3);
        }
        if ((loopingFlowContext2.initsOnBreak.tagBits & 3) != 0) {
            flowInfoAddInitializationsFrom = loopingFlowContext2.initsOnBreak;
        } else {
            flowInfoAddInitializationsFrom = flowInfo.addInitializationsFrom(loopingFlowContext2.initsOnBreak);
        }
        UnconditionalFlowInfo unconditionalFlowInfoMergedOptimizedBranches = FlowInfo.mergedOptimizedBranches(flowInfoAddInitializationsFrom, z8, flowInfoCopy2, z, !z10);
        this.mergedInitStateIndex = blockScope.methodScope().recordInitializationStates(unconditionalFlowInfoMergedOptimizedBranches);
        return unconditionalFlowInfoMergedOptimizedBranches;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Statement
    public void generateCode(BlockScope blockScope, CodeStream codeStream) {
        Statement statement;
        if ((this.bits & Integer.MIN_VALUE) == 0) {
            return;
        }
        int i = codeStream.position;
        Constant constantOptimizedBooleanConstant = this.condition.optimizedBooleanConstant();
        if (constantOptimizedBooleanConstant != Constant.NotAConstant && !constantOptimizedBooleanConstant.booleanValue()) {
            this.condition.generateCode(blockScope, codeStream, false);
            int i2 = this.mergedInitStateIndex;
            if (i2 != -1) {
                codeStream.removeNotDefinitelyAssignedVariables(blockScope, i2);
                codeStream.addDefinitelyAssignedVariables(blockScope, this.mergedInitStateIndex);
            }
            codeStream.recordPositionsFrom(i, this.sourceStart);
            return;
        }
        this.breakLabel.initialize(codeStream);
        BranchLabel branchLabel = this.continueLabel;
        if (branchLabel == null) {
            if (this.condition.constant == Constant.NotAConstant) {
                this.condition.generateOptimizedBoolean(blockScope, codeStream, null, this.breakLabel, true);
            }
        } else {
            branchLabel.initialize(codeStream);
            if ((this.condition.constant == Constant.NotAConstant || !this.condition.constant.booleanValue()) && (statement = this.action) != null && !statement.isEmptyBlock()) {
                int i3 = codeStream.position;
                codeStream.goto_(this.continueLabel);
                codeStream.recordPositionsFrom(i3, this.condition.sourceStart);
            }
        }
        BranchLabel branchLabel2 = new BranchLabel(codeStream);
        if (this.action != null) {
            branchLabel2.tagBits |= 2;
            int i4 = this.condIfTrueInitStateIndex;
            if (i4 != -1) {
                codeStream.addDefinitelyAssignedVariables(blockScope, i4);
            }
            branchLabel2.place();
            this.action.generateCode(blockScope, codeStream);
            int i5 = this.preCondInitStateIndex;
            if (i5 != -1) {
                codeStream.removeNotDefinitelyAssignedVariables(blockScope, i5);
            }
        } else {
            branchLabel2.place();
        }
        BranchLabel branchLabel3 = this.continueLabel;
        if (branchLabel3 != null) {
            branchLabel3.place();
            this.condition.generateOptimizedBoolean(blockScope, codeStream, branchLabel2, null, true);
        }
        int i6 = this.mergedInitStateIndex;
        if (i6 != -1) {
            codeStream.removeNotDefinitelyAssignedVariables(blockScope, i6);
            codeStream.addDefinitelyAssignedVariables(blockScope, this.mergedInitStateIndex);
        }
        this.breakLabel.place();
        codeStream.recordPositionsFrom(i, this.sourceStart);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Statement
    public void resolve(BlockScope blockScope) {
        TypeBinding typeBindingResolveTypeExpecting = this.condition.resolveTypeExpecting(blockScope, TypeBinding.BOOLEAN);
        this.condition.computeConversion(blockScope, typeBindingResolveTypeExpecting, typeBindingResolveTypeExpecting);
        Statement statement = this.action;
        if (statement != null) {
            statement.resolve(blockScope);
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Statement
    public StringBuffer printStatement(int i, StringBuffer stringBuffer) {
        printIndent(i, stringBuffer).append("while (");
        this.condition.printExpression(0, stringBuffer).append(')');
        Statement statement = this.action;
        if (statement == null) {
            stringBuffer.append(';');
        } else {
            statement.printStatement(i + 1, stringBuffer);
        }
        return stringBuffer;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.ASTNode
    public void traverse(ASTVisitor aSTVisitor, BlockScope blockScope) {
        if (aSTVisitor.visit(this, blockScope)) {
            this.condition.traverse(aSTVisitor, blockScope);
            Statement statement = this.action;
            if (statement != null) {
                statement.traverse(aSTVisitor, blockScope);
            }
        }
        aSTVisitor.endVisit(this, blockScope);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Statement
    public boolean doesNotCompleteNormally() {
        Statement statement;
        Constant constant = this.condition.constant;
        boolean z = constant == null || (constant != Constant.NotAConstant && constant.booleanValue());
        Constant constantOptimizedBooleanConstant = this.condition.optimizedBooleanConstant();
        return (z || (constantOptimizedBooleanConstant == null || (constantOptimizedBooleanConstant != Constant.NotAConstant && constantOptimizedBooleanConstant.booleanValue()))) && ((statement = this.action) == null || !statement.breaksOut(null));
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Statement
    public boolean completesByContinue() {
        return this.action.continuesAtOuterLabel();
    }
}
