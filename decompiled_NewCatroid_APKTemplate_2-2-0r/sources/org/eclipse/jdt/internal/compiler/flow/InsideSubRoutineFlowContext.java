package org.eclipse.jdt.internal.compiler.flow;

import org.eclipse.jdt.internal.compiler.ast.ASTNode;
import org.eclipse.jdt.internal.compiler.ast.SubRoutineStatement;

/* JADX INFO: loaded from: classes14.dex */
public class InsideSubRoutineFlowContext extends TryFlowContext {
    public UnconditionalFlowInfo initsOnReturn;

    public InsideSubRoutineFlowContext(FlowContext flowContext, ASTNode aSTNode) {
        super(flowContext, aSTNode);
        this.initsOnReturn = FlowInfo.DEAD_END;
    }

    @Override // org.eclipse.jdt.internal.compiler.flow.FlowContext
    public String individualToString() {
        StringBuffer stringBuffer = new StringBuffer("Inside SubRoutine flow context[initsOnReturn -");
        stringBuffer.append(this.initsOnReturn.toString()).append(']');
        return stringBuffer.toString();
    }

    @Override // org.eclipse.jdt.internal.compiler.flow.FlowContext
    public UnconditionalFlowInfo initsOnReturn() {
        return this.initsOnReturn;
    }

    @Override // org.eclipse.jdt.internal.compiler.flow.FlowContext
    public boolean isNonReturningContext() {
        return ((SubRoutineStatement) this.associatedNode).isSubRoutineEscaping();
    }

    @Override // org.eclipse.jdt.internal.compiler.flow.FlowContext
    public void recordReturnFrom(UnconditionalFlowInfo unconditionalFlowInfo) {
        if ((unconditionalFlowInfo.tagBits & 1) == 0) {
            if (this.initsOnReturn == FlowInfo.DEAD_END) {
                this.initsOnReturn = (UnconditionalFlowInfo) unconditionalFlowInfo.copy();
            } else {
                this.initsOnReturn = this.initsOnReturn.mergedWith(unconditionalFlowInfo);
            }
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.flow.FlowContext
    public SubRoutineStatement subroutine() {
        return (SubRoutineStatement) this.associatedNode;
    }
}
