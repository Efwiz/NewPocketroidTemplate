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
public class ForStatement extends Statement {
    public Statement action;
    private BranchLabel breakLabel;
    public Expression condition;
    private BranchLabel continueLabel;
    public Statement[] increments;
    public Statement[] initializations;
    public BlockScope scope;
    int preCondInitStateIndex = -1;
    int preIncrementsInitStateIndex = -1;
    int condIfTrueInitStateIndex = -1;
    int mergedInitStateIndex = -1;

    public ForStatement(Statement[] statementArr, Expression expression, Statement[] statementArr2, Statement statement, boolean z, int i, int i2) {
        this.sourceStart = i;
        this.sourceEnd = i2;
        this.initializations = statementArr;
        this.condition = expression;
        this.increments = statementArr2;
        this.action = statement;
        if (statement instanceof EmptyStatement) {
            statement.bits |= 1;
        }
        if (z) {
            this.bits |= 536870912;
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Statement
    public FlowInfo analyseCode(BlockScope blockScope, FlowContext flowContext, FlowInfo flowInfo) {
        FlowInfo flowInfo2;
        FlowInfo flowInfo3;
        LoopingFlowContext loopingFlowContext;
        boolean z;
        UnconditionalFlowInfo unconditionalFlowInfoUnconditionalCopy;
        LoopingFlowContext loopingFlowContext2;
        FlowInfo flowInfo4;
        int i;
        LoopingFlowContext loopingFlowContext3;
        FlowInfo flowInfoAddInitializationsFrom;
        UnconditionalFlowInfo unconditionalFlowInfoUnconditionalCopy2;
        boolean z2;
        boolean z3;
        this.breakLabel = new BranchLabel();
        this.continueLabel = new BranchLabel();
        int i2 = (flowInfo.reachMode() & 3) != 0 ? 1 : 0;
        Statement[] statementArr = this.initializations;
        if (statementArr != null) {
            int length = statementArr.length;
            FlowInfo flowInfoAnalyseCode = flowInfo;
            for (int i3 = 0; i3 < length; i3++) {
                flowInfoAnalyseCode = this.initializations[i3].analyseCode(this.scope, flowContext, flowInfoAnalyseCode);
            }
            flowInfo2 = flowInfoAnalyseCode;
        } else {
            flowInfo2 = flowInfo;
        }
        this.preCondInitStateIndex = blockScope.methodScope().recordInitializationStates(flowInfo2);
        Expression expression = this.condition;
        Constant constant = expression == null ? null : expression.constant;
        boolean z4 = constant == null || (constant != Constant.NotAConstant && constant.booleanValue());
        boolean z5 = (constant == null || constant == Constant.NotAConstant || constant.booleanValue()) ? false : true;
        Expression expression2 = this.condition;
        Constant constantOptimizedBooleanConstant = expression2 == null ? null : expression2.optimizedBooleanConstant();
        boolean z6 = constantOptimizedBooleanConstant == null || (constantOptimizedBooleanConstant != Constant.NotAConstant && constantOptimizedBooleanConstant.booleanValue());
        boolean z7 = (constantOptimizedBooleanConstant == null || constantOptimizedBooleanConstant == Constant.NotAConstant || constantOptimizedBooleanConstant.booleanValue()) ? false : true;
        UnconditionalFlowInfo unconditionalFlowInfoNullInfoLessUnconditionalCopy = flowInfo2.nullInfoLessUnconditionalCopy();
        Expression expression3 = this.condition;
        if (expression3 == null || z4) {
            flowInfo3 = unconditionalFlowInfoNullInfoLessUnconditionalCopy;
            loopingFlowContext = null;
        } else {
            BlockScope blockScope2 = this.scope;
            loopingFlowContext = loopingFlowContext;
            LoopingFlowContext loopingFlowContext4 = new LoopingFlowContext(flowContext, flowInfo2, this, null, null, this.scope, true);
            FlowInfo flowInfoAnalyseCode2 = expression3.analyseCode(blockScope2, loopingFlowContext, unconditionalFlowInfoNullInfoLessUnconditionalCopy);
            this.condition.checkNPEbyUnboxing(blockScope, flowContext, flowInfo2);
            flowInfo3 = flowInfoAnalyseCode2;
        }
        Statement statement = this.action;
        if (statement == null || (statement.isEmptyBlock() && blockScope.compilerOptions().complianceLevel <= ClassFileConstants.JDK1_3)) {
            boolean z8 = z7;
            if (loopingFlowContext != null) {
                loopingFlowContext.complainOnDeferredFinalChecks(this.scope, flowInfo3);
            }
            if (z4) {
                if (loopingFlowContext != null) {
                    loopingFlowContext.complainOnDeferredNullChecks(blockScope, flowInfo3);
                }
                return FlowInfo.DEAD_END;
            }
            if (z5) {
                this.continueLabel = null;
            }
            z = z8;
            unconditionalFlowInfoUnconditionalCopy = flowInfo3.initsWhenTrue().unconditionalCopy();
            loopingFlowContext2 = new LoopingFlowContext(flowContext, flowInfo2, this, this.breakLabel, this.continueLabel, this.scope, false);
        } else {
            LoopingFlowContext loopingFlowContext5 = new LoopingFlowContext(flowContext, flowInfo2, this, this.breakLabel, this.continueLabel, this.scope, true);
            FlowInfo flowInfoInitsWhenTrue = flowInfo3.initsWhenTrue();
            this.condIfTrueInitStateIndex = blockScope.methodScope().recordInitializationStates(flowInfoInitsWhenTrue);
            if (z5) {
                unconditionalFlowInfoUnconditionalCopy2 = FlowInfo.DEAD_END;
                z2 = z7;
                z3 = true;
            } else {
                unconditionalFlowInfoUnconditionalCopy2 = flowInfoInitsWhenTrue.unconditionalCopy();
                z2 = z7;
                z3 = true;
                if (z2) {
                    unconditionalFlowInfoUnconditionalCopy2.setReachMode(1);
                }
            }
            if (this.action.complainIfUnreachable(unconditionalFlowInfoUnconditionalCopy2, this.scope, i2, z3) < 2) {
                unconditionalFlowInfoUnconditionalCopy2 = this.action.analyseCode(this.scope, loopingFlowContext5, unconditionalFlowInfoUnconditionalCopy2).unconditionalInits();
            }
            if ((unconditionalFlowInfoUnconditionalCopy2.tagBits & loopingFlowContext5.initsOnContinue.tagBits & 1) != 0) {
                this.continueLabel = null;
            } else {
                if (loopingFlowContext != null) {
                    loopingFlowContext.complainOnDeferredFinalChecks(this.scope, flowInfo3);
                }
                unconditionalFlowInfoUnconditionalCopy2 = unconditionalFlowInfoUnconditionalCopy2.mergedWith(loopingFlowContext5.initsOnContinue);
                loopingFlowContext5.complainOnDeferredFinalChecks(this.scope, unconditionalFlowInfoUnconditionalCopy2);
            }
            loopingFlowContext2 = loopingFlowContext5;
            z = z2;
            unconditionalFlowInfoUnconditionalCopy = unconditionalFlowInfoUnconditionalCopy2;
        }
        FlowInfo flowInfoCopy = flowInfo2.copy();
        if (this.continueLabel != null) {
            if (this.increments != null) {
                UnconditionalFlowInfo unconditionalFlowInfo = unconditionalFlowInfoUnconditionalCopy;
                loopingFlowContext3 = new LoopingFlowContext(flowContext, flowInfo2, this, null, null, this.scope, true);
                this.preIncrementsInitStateIndex = blockScope.methodScope().recordInitializationStates(unconditionalFlowInfo);
                int length2 = this.increments.length;
                FlowInfo flowInfoAnalyseCode3 = unconditionalFlowInfo;
                for (int i4 = 0; i4 < length2; i4++) {
                    flowInfoAnalyseCode3 = this.increments[i4].analyseCode(this.scope, loopingFlowContext3, flowInfoAnalyseCode3);
                }
                BlockScope blockScope3 = this.scope;
                UnconditionalFlowInfo unconditionalFlowInfoUnconditionalInits = flowInfoAnalyseCode3.unconditionalInits();
                loopingFlowContext3.complainOnDeferredFinalChecks(blockScope3, unconditionalFlowInfoUnconditionalInits);
                unconditionalFlowInfoUnconditionalCopy = unconditionalFlowInfoUnconditionalInits;
                flowInfo4 = flowInfoCopy;
            } else {
                flowInfo4 = flowInfoCopy;
                loopingFlowContext3 = null;
            }
            flowInfo4.addPotentialInitializationsFrom(unconditionalFlowInfoUnconditionalCopy).addInitializationsFrom(flowInfo3.initsWhenFalse());
            i = 0;
        } else {
            flowInfo4 = flowInfoCopy;
            loopingFlowContext2 = loopingFlowContext2;
            UnconditionalFlowInfo unconditionalFlowInfo2 = unconditionalFlowInfoUnconditionalCopy;
            flowInfo4.addInitializationsFrom(flowInfo3.initsWhenFalse());
            if (this.increments == null || i2 != 0) {
                i = 0;
            } else {
                i = 0;
                blockScope.problemReporter().fakeReachable(this.increments[0]);
            }
            unconditionalFlowInfoUnconditionalCopy = unconditionalFlowInfo2;
            loopingFlowContext3 = null;
        }
        if (loopingFlowContext != null) {
            loopingFlowContext.complainOnDeferredNullChecks(blockScope, unconditionalFlowInfoUnconditionalCopy);
        }
        LoopingFlowContext loopingFlowContext6 = loopingFlowContext2;
        loopingFlowContext6.complainOnDeferredNullChecks(blockScope, unconditionalFlowInfoUnconditionalCopy);
        if (loopingFlowContext3 != null) {
            loopingFlowContext3.complainOnDeferredNullChecks(blockScope, unconditionalFlowInfoUnconditionalCopy);
        }
        if (loopingFlowContext6.hasEscapingExceptions()) {
            FlowInfo flowInfoCopy2 = flowInfo2.copy();
            if (this.continueLabel != null) {
                flowInfoCopy2 = flowInfoCopy2.mergedWith(flowInfoCopy2.unconditionalCopy().addNullInfoFrom(unconditionalFlowInfoUnconditionalCopy).unconditionalInits());
            }
            loopingFlowContext6.simulateThrowAfterLoopBack(flowInfoCopy2);
        }
        if ((loopingFlowContext6.initsOnBreak.tagBits & 3) != 0) {
            flowInfoAddInitializationsFrom = loopingFlowContext6.initsOnBreak;
        } else {
            flowInfoAddInitializationsFrom = flowInfo2.addInitializationsFrom(loopingFlowContext6.initsOnBreak);
        }
        UnconditionalFlowInfo unconditionalFlowInfoMergedOptimizedBranches = FlowInfo.mergedOptimizedBranches(flowInfoAddInitializationsFrom, z6, flowInfo4, z, !z4);
        if (this.initializations != null) {
            int i5 = i;
            while (true) {
                Statement[] statementArr2 = this.initializations;
                if (i5 >= statementArr2.length) {
                    break;
                }
                Statement statement2 = statementArr2[i5];
                if (statement2 instanceof LocalDeclaration) {
                    unconditionalFlowInfoMergedOptimizedBranches.resetAssignmentInfo(((LocalDeclaration) statement2).binding);
                }
                i5++;
            }
        }
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
        Statement[] statementArr = this.initializations;
        if (statementArr != null) {
            int length = statementArr.length;
            for (int i2 = 0; i2 < length; i2++) {
                this.initializations[i2].generateCode(this.scope, codeStream);
            }
        }
        Expression expression = this.condition;
        Constant constantOptimizedBooleanConstant = expression == null ? null : expression.optimizedBooleanConstant();
        if (constantOptimizedBooleanConstant != null && constantOptimizedBooleanConstant != Constant.NotAConstant && !constantOptimizedBooleanConstant.booleanValue()) {
            this.condition.generateCode(this.scope, codeStream, false);
            if ((this.bits & 536870912) != 0) {
                codeStream.exitUserScope(this.scope);
            }
            int i3 = this.mergedInitStateIndex;
            if (i3 != -1) {
                codeStream.removeNotDefinitelyAssignedVariables(blockScope, i3);
                codeStream.addDefinitelyAssignedVariables(blockScope, this.mergedInitStateIndex);
            }
            codeStream.recordPositionsFrom(i, this.sourceStart);
            return;
        }
        BranchLabel branchLabel = new BranchLabel(codeStream);
        branchLabel.tagBits |= 2;
        BranchLabel branchLabel2 = new BranchLabel(codeStream);
        this.breakLabel.initialize(codeStream);
        BranchLabel branchLabel3 = this.continueLabel;
        if (branchLabel3 == null) {
            branchLabel2.place();
            Expression expression2 = this.condition;
            if (expression2 != null && expression2.constant == Constant.NotAConstant) {
                this.condition.generateOptimizedBoolean(this.scope, codeStream, null, this.breakLabel, true);
            }
        } else {
            branchLabel3.initialize(codeStream);
            Expression expression3 = this.condition;
            if (expression3 != null && expression3.constant == Constant.NotAConstant && (((statement = this.action) != null && !statement.isEmptyBlock()) || this.increments != null)) {
                branchLabel2.tagBits |= 2;
                int i4 = codeStream.position;
                codeStream.goto_(branchLabel2);
                codeStream.recordPositionsFrom(i4, this.condition.sourceStart);
            }
        }
        if (this.action != null) {
            int i5 = this.condIfTrueInitStateIndex;
            if (i5 != -1) {
                codeStream.addDefinitelyAssignedVariables(blockScope, i5);
            }
            branchLabel.place();
            this.action.generateCode(this.scope, codeStream);
        } else {
            branchLabel.place();
        }
        int i6 = this.preIncrementsInitStateIndex;
        if (i6 != -1) {
            codeStream.removeNotDefinitelyAssignedVariables(blockScope, i6);
            codeStream.addDefinitelyAssignedVariables(blockScope, this.preIncrementsInitStateIndex);
        }
        BranchLabel branchLabel4 = this.continueLabel;
        if (branchLabel4 != null) {
            branchLabel4.place();
            Statement[] statementArr2 = this.increments;
            if (statementArr2 != null) {
                int length2 = statementArr2.length;
                for (int i7 = 0; i7 < length2; i7++) {
                    this.increments[i7].generateCode(this.scope, codeStream);
                }
            }
            int i8 = this.preCondInitStateIndex;
            if (i8 != -1) {
                codeStream.removeNotDefinitelyAssignedVariables(blockScope, i8);
            }
            branchLabel2.place();
            Expression expression4 = this.condition;
            if (expression4 != null && expression4.constant == Constant.NotAConstant) {
                this.condition.generateOptimizedBoolean(this.scope, codeStream, branchLabel, null, true);
            } else {
                codeStream.goto_(branchLabel);
            }
        } else {
            int i9 = this.preCondInitStateIndex;
            if (i9 != -1) {
                codeStream.removeNotDefinitelyAssignedVariables(blockScope, i9);
            }
        }
        if ((this.bits & 536870912) != 0) {
            codeStream.exitUserScope(this.scope);
        }
        int i10 = this.mergedInitStateIndex;
        if (i10 != -1) {
            codeStream.removeNotDefinitelyAssignedVariables(blockScope, i10);
            codeStream.addDefinitelyAssignedVariables(blockScope, this.mergedInitStateIndex);
        }
        this.breakLabel.place();
        codeStream.recordPositionsFrom(i, this.sourceStart);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Statement
    public StringBuffer printStatement(int i, StringBuffer stringBuffer) {
        printIndent(i, stringBuffer).append("for (");
        if (this.initializations != null) {
            for (int i2 = 0; i2 < this.initializations.length; i2++) {
                if (i2 > 0) {
                    stringBuffer.append(", ");
                }
                this.initializations[i2].print(0, stringBuffer);
            }
        }
        stringBuffer.append("; ");
        Expression expression = this.condition;
        if (expression != null) {
            expression.printExpression(0, stringBuffer);
        }
        stringBuffer.append("; ");
        if (this.increments != null) {
            for (int i3 = 0; i3 < this.increments.length; i3++) {
                if (i3 > 0) {
                    stringBuffer.append(", ");
                }
                this.increments[i3].print(0, stringBuffer);
            }
        }
        stringBuffer.append(") ");
        if (this.action == null) {
            stringBuffer.append(';');
        } else {
            stringBuffer.append('\n');
            this.action.printStatement(i + 1, stringBuffer);
        }
        return stringBuffer;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Statement
    public void resolve(BlockScope blockScope) {
        if ((this.bits & 536870912) != 0) {
            blockScope = new BlockScope(blockScope);
        }
        this.scope = blockScope;
        Statement[] statementArr = this.initializations;
        if (statementArr != null) {
            int length = statementArr.length;
            for (int i = 0; i < length; i++) {
                this.initializations[i].resolve(this.scope);
            }
        }
        Expression expression = this.condition;
        if (expression != null) {
            TypeBinding typeBindingResolveTypeExpecting = expression.resolveTypeExpecting(this.scope, TypeBinding.BOOLEAN);
            this.condition.computeConversion(this.scope, typeBindingResolveTypeExpecting, typeBindingResolveTypeExpecting);
        }
        Statement[] statementArr2 = this.increments;
        if (statementArr2 != null) {
            int length2 = statementArr2.length;
            for (int i2 = 0; i2 < length2; i2++) {
                this.increments[i2].resolve(this.scope);
            }
        }
        Statement statement = this.action;
        if (statement != null) {
            statement.resolve(this.scope);
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.ASTNode
    public void traverse(ASTVisitor aSTVisitor, BlockScope blockScope) {
        if (aSTVisitor.visit(this, blockScope)) {
            Statement[] statementArr = this.initializations;
            if (statementArr != null) {
                int length = statementArr.length;
                for (int i = 0; i < length; i++) {
                    this.initializations[i].traverse(aSTVisitor, this.scope);
                }
            }
            Expression expression = this.condition;
            if (expression != null) {
                expression.traverse(aSTVisitor, this.scope);
            }
            Statement[] statementArr2 = this.increments;
            if (statementArr2 != null) {
                int length2 = statementArr2.length;
                for (int i2 = 0; i2 < length2; i2++) {
                    this.increments[i2].traverse(aSTVisitor, this.scope);
                }
            }
            Statement statement = this.action;
            if (statement != null) {
                statement.traverse(aSTVisitor, this.scope);
            }
        }
        aSTVisitor.endVisit(this, blockScope);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Statement
    public boolean doesNotCompleteNormally() {
        Statement statement;
        Expression expression = this.condition;
        Constant constant = expression == null ? null : expression.constant;
        boolean z = constant == null || (constant != Constant.NotAConstant && constant.booleanValue());
        Expression expression2 = this.condition;
        Constant constantOptimizedBooleanConstant = expression2 == null ? null : expression2.optimizedBooleanConstant();
        return (z || (constantOptimizedBooleanConstant == null || (constantOptimizedBooleanConstant != Constant.NotAConstant && constantOptimizedBooleanConstant.booleanValue()))) && ((statement = this.action) == null || !statement.breaksOut(null));
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Statement
    public boolean completesByContinue() {
        return this.action.continuesAtOuterLabel();
    }
}
