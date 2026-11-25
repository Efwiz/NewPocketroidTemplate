package org.eclipse.jdt.internal.compiler.flow;

import org.eclipse.jdt.internal.compiler.ast.ASTNode;
import org.eclipse.jdt.internal.compiler.codegen.BranchLabel;

/* JADX INFO: loaded from: classes14.dex */
public class SwitchFlowContext extends FlowContext {
    public BranchLabel breakLabel;
    public UnconditionalFlowInfo initsOnBreak;

    @Override // org.eclipse.jdt.internal.compiler.flow.FlowContext
    public boolean isBreakable() {
        return true;
    }

    public SwitchFlowContext(FlowContext flowContext, ASTNode aSTNode, BranchLabel branchLabel, boolean z) {
        super(flowContext, aSTNode);
        this.initsOnBreak = FlowInfo.DEAD_END;
        this.breakLabel = branchLabel;
        if (!z || flowContext.conditionalLevel <= -1) {
            return;
        }
        this.conditionalLevel++;
    }

    @Override // org.eclipse.jdt.internal.compiler.flow.FlowContext
    public BranchLabel breakLabel() {
        return this.breakLabel;
    }

    @Override // org.eclipse.jdt.internal.compiler.flow.FlowContext
    public String individualToString() {
        StringBuffer stringBuffer = new StringBuffer("Switch flow context[initsOnBreak -");
        stringBuffer.append(this.initsOnBreak.toString()).append(']');
        return stringBuffer.toString();
    }

    @Override // org.eclipse.jdt.internal.compiler.flow.FlowContext
    public void recordBreakFrom(FlowInfo flowInfo) {
        if ((this.initsOnBreak.tagBits & 1) == 0) {
            this.initsOnBreak = this.initsOnBreak.mergedWith(flowInfo.unconditionalInits());
        } else {
            this.initsOnBreak = flowInfo.unconditionalCopy();
        }
    }
}
