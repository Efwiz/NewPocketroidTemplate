package org.eclipse.jdt.internal.compiler.ast;

import org.eclipse.jdt.internal.compiler.ASTVisitor;
import org.eclipse.jdt.internal.compiler.codegen.BranchLabel;
import org.eclipse.jdt.internal.compiler.codegen.CodeStream;
import org.eclipse.jdt.internal.compiler.flow.FlowContext;
import org.eclipse.jdt.internal.compiler.flow.FlowInfo;
import org.eclipse.jdt.internal.compiler.flow.LoopingFlowContext;
import org.eclipse.jdt.internal.compiler.flow.UnconditionalFlowInfo;
import org.eclipse.jdt.internal.compiler.impl.Constant;
import org.eclipse.jdt.internal.compiler.lookup.BlockScope;
import org.eclipse.jdt.internal.compiler.lookup.TypeBinding;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;

/* JADX INFO: loaded from: classes14.dex */
public class DoStatement extends Statement {
    public Statement action;
    private BranchLabel breakLabel;
    public Expression condition;
    private BranchLabel continueLabel;
    int mergedInitStateIndex = -1;
    int preConditionInitStateIndex = -1;

    public DoStatement(Expression expression, Statement statement, int i, int i2) {
        this.sourceStart = i;
        this.sourceEnd = i2;
        this.condition = expression;
        this.action = statement;
        if (statement instanceof EmptyStatement) {
            statement.bits |= 1;
        }
    }

    /* JADX WARN: Code duplicated, block: B:30:0x0097 A[PHI: r1
      0x0097: PHI (r1v5 org.eclipse.jdt.internal.compiler.flow.UnconditionalFlowInfo) = 
      (r1v4 org.eclipse.jdt.internal.compiler.flow.UnconditionalFlowInfo)
      (r1v4 org.eclipse.jdt.internal.compiler.flow.UnconditionalFlowInfo)
      (r1v35 org.eclipse.jdt.internal.compiler.flow.UnconditionalFlowInfo)
     binds: [B:21:0x0061, B:23:0x0067, B:28:0x0086] A[DONT_GENERATE, DONT_INLINE]] */
    @Override // org.eclipse.jdt.internal.compiler.ast.Statement
    public FlowInfo analyseCode(BlockScope blockScope, FlowContext flowContext, FlowInfo flowInfo) {
        FlowInfo flowInfoAddInitializationsFrom;
        FlowInfo flowInfoAddInitializationsFrom2;
        this.breakLabel = new BranchLabel();
        this.continueLabel = new BranchLabel();
        LoopingFlowContext loopingFlowContext = new LoopingFlowContext(flowContext, flowInfo, this, this.breakLabel, this.continueLabel, blockScope, false);
        Constant constant = this.condition.constant;
        boolean z = constant != Constant.NotAConstant && constant.booleanValue();
        Constant constantOptimizedBooleanConstant = this.condition.optimizedBooleanConstant();
        boolean z2 = constantOptimizedBooleanConstant != Constant.NotAConstant && constantOptimizedBooleanConstant.booleanValue();
        boolean z3 = (constantOptimizedBooleanConstant == Constant.NotAConstant || constantOptimizedBooleanConstant.booleanValue()) ? false : true;
        int iReachMode = flowInfo.reachMode();
        UnconditionalFlowInfo unconditionalFlowInfoNullInfoLessUnconditionalCopy = flowInfo.nullInfoLessUnconditionalCopy();
        Statement statement = this.action;
        if (statement == null || statement.isEmptyBlock()) {
            flowInfoAddInitializationsFrom = flowInfo;
        } else {
            unconditionalFlowInfoNullInfoLessUnconditionalCopy = this.action.analyseCode(blockScope, loopingFlowContext, unconditionalFlowInfoNullInfoLessUnconditionalCopy).unconditionalInits();
            if ((unconditionalFlowInfoNullInfoLessUnconditionalCopy.tagBits & loopingFlowContext.initsOnContinue.tagBits & 1) != 0) {
                this.continueLabel = null;
            }
            if ((this.condition.implicitConversion & 1024) != 0) {
                flowInfoAddInitializationsFrom = flowInfo.unconditionalInits().addInitializationsFrom(unconditionalFlowInfoNullInfoLessUnconditionalCopy.mergedWith(loopingFlowContext.initsOnContinue));
            } else {
                flowInfoAddInitializationsFrom = flowInfo;
            }
        }
        UnconditionalFlowInfo unconditionalFlowInfo = unconditionalFlowInfoNullInfoLessUnconditionalCopy;
        this.condition.checkNPEbyUnboxing(blockScope, flowContext, flowInfoAddInitializationsFrom);
        unconditionalFlowInfo.setReachMode(iReachMode);
        Expression expression = this.condition;
        boolean z4 = z2;
        LoopingFlowContext loopingFlowContext2 = new LoopingFlowContext(flowContext, flowInfo, this, null, null, blockScope, true);
        FlowInfo flowInfoAnalyseCode = expression.analyseCode(blockScope, loopingFlowContext2, (this.action == null ? unconditionalFlowInfo : unconditionalFlowInfo.mergedWith(loopingFlowContext.initsOnContinue)).copy());
        this.preConditionInitStateIndex = blockScope.methodScope().recordInitializationStates(unconditionalFlowInfo.mergedWith(loopingFlowContext.initsOnContinue));
        if (!z3 && this.continueLabel != null) {
            loopingFlowContext.complainOnDeferredFinalChecks(blockScope, flowInfoAnalyseCode);
            loopingFlowContext2.complainOnDeferredFinalChecks(blockScope, flowInfoAnalyseCode);
            loopingFlowContext.complainOnDeferredNullChecks(blockScope, flowInfo.unconditionalCopy().addPotentialNullInfoFrom(flowInfoAnalyseCode.initsWhenTrue().unconditionalInits()));
            loopingFlowContext2.complainOnDeferredNullChecks(blockScope, unconditionalFlowInfo.addPotentialNullInfoFrom(flowInfoAnalyseCode.initsWhenTrue().unconditionalInits()));
        } else {
            loopingFlowContext.complainOnDeferredNullChecks(blockScope, flowInfo.unconditionalCopy().addPotentialNullInfoFrom(flowInfoAnalyseCode.initsWhenTrue().unconditionalInits()), false);
            loopingFlowContext2.complainOnDeferredNullChecks(blockScope, unconditionalFlowInfo.addPotentialNullInfoFrom(flowInfoAnalyseCode.initsWhenTrue().unconditionalInits()), false);
        }
        if (loopingFlowContext.hasEscapingExceptions()) {
            FlowInfo flowInfoCopy = flowInfo.copy();
            loopingFlowContext.simulateThrowAfterLoopBack(flowInfoCopy.mergedWith(flowInfoCopy.unconditionalCopy().addNullInfoFrom(flowInfoAnalyseCode.initsWhenTrue()).unconditionalInits()));
        }
        if ((loopingFlowContext.initsOnBreak.tagBits & 3) != 0) {
            flowInfoAddInitializationsFrom2 = loopingFlowContext.initsOnBreak;
        } else {
            flowInfoAddInitializationsFrom2 = flowInfo.unconditionalCopy().addInitializationsFrom(loopingFlowContext.initsOnBreak);
        }
        if ((flowInfoAnalyseCode.tagBits & 3) == 0) {
            flowInfoAnalyseCode = flowInfo.copy().addInitializationsFrom(flowInfoAnalyseCode.initsWhenFalse());
        }
        UnconditionalFlowInfo unconditionalFlowInfoMergedOptimizedBranches = FlowInfo.mergedOptimizedBranches(flowInfoAddInitializationsFrom2, z4, flowInfoAnalyseCode, false, true ^ z);
        this.mergedInitStateIndex = blockScope.methodScope().recordInitializationStates(unconditionalFlowInfoMergedOptimizedBranches);
        return unconditionalFlowInfoMergedOptimizedBranches;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Statement
    public void generateCode(BlockScope blockScope, CodeStream codeStream) {
        if ((this.bits & Integer.MIN_VALUE) == 0) {
            return;
        }
        int i = codeStream.position;
        BranchLabel branchLabel = new BranchLabel(codeStream);
        if (this.action != null) {
            branchLabel.tagBits |= 2;
        }
        branchLabel.place();
        this.breakLabel.initialize(codeStream);
        BranchLabel branchLabel2 = this.continueLabel;
        boolean z = branchLabel2 != null;
        if (z) {
            branchLabel2.initialize(codeStream);
        }
        Statement statement = this.action;
        if (statement != null) {
            statement.generateCode(blockScope, codeStream);
        }
        if (z) {
            this.continueLabel.place();
            int i2 = this.preConditionInitStateIndex;
            if (i2 != -1) {
                codeStream.removeNotDefinitelyAssignedVariables(blockScope, i2);
                codeStream.addDefinitelyAssignedVariables(blockScope, this.preConditionInitStateIndex);
            }
            Constant constantOptimizedBooleanConstant = this.condition.optimizedBooleanConstant();
            if (constantOptimizedBooleanConstant != Constant.NotAConstant && !constantOptimizedBooleanConstant.booleanValue()) {
                this.condition.generateCode(blockScope, codeStream, false);
            } else {
                this.condition.generateOptimizedBoolean(blockScope, codeStream, branchLabel, null, true);
            }
        }
        int i3 = this.mergedInitStateIndex;
        if (i3 != -1) {
            codeStream.removeNotDefinitelyAssignedVariables(blockScope, i3);
            codeStream.addDefinitelyAssignedVariables(blockScope, this.mergedInitStateIndex);
        }
        if (this.breakLabel.forwardReferenceCount() > 0) {
            this.breakLabel.place();
        }
        codeStream.recordPositionsFrom(i, this.sourceStart);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Statement
    public StringBuffer printStatement(int i, StringBuffer stringBuffer) {
        printIndent(i, stringBuffer).append(PsiKeyword.DO);
        if (this.action == null) {
            stringBuffer.append(" ;\n");
        } else {
            stringBuffer.append('\n');
            this.action.printStatement(i + 1, stringBuffer).append('\n');
        }
        stringBuffer.append("while (");
        return this.condition.printExpression(0, stringBuffer).append(");");
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

    @Override // org.eclipse.jdt.internal.compiler.ast.ASTNode
    public void traverse(ASTVisitor aSTVisitor, BlockScope blockScope) {
        if (aSTVisitor.visit(this, blockScope)) {
            Statement statement = this.action;
            if (statement != null) {
                statement.traverse(aSTVisitor, blockScope);
            }
            this.condition.traverse(aSTVisitor, blockScope);
        }
        aSTVisitor.endVisit(this, blockScope);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Statement
    public boolean doesNotCompleteNormally() {
        Constant constant = this.condition.constant;
        boolean z = constant == null || (constant != Constant.NotAConstant && constant.booleanValue());
        Constant constantOptimizedBooleanConstant = this.condition.optimizedBooleanConstant();
        boolean z2 = constantOptimizedBooleanConstant == null || (constantOptimizedBooleanConstant != Constant.NotAConstant && constantOptimizedBooleanConstant.booleanValue());
        if (z || z2) {
            Statement statement = this.action;
            return statement == null || !statement.breaksOut(null);
        }
        Statement statement2 = this.action;
        return (statement2 == null || statement2.breaksOut(null) || !this.action.doesNotCompleteNormally() || this.action.completesByContinue()) ? false : true;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Statement
    public boolean completesByContinue() {
        return this.action.continuesAtOuterLabel();
    }
}
