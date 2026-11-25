package org.eclipse.jdt.internal.compiler.flow;

import org.eclipse.jdt.internal.compiler.ast.ASTNode;
import org.eclipse.jdt.internal.compiler.lookup.LocalVariableBinding;

/* JADX INFO: loaded from: classes14.dex */
public abstract class TryFlowContext extends FlowContext {
    public FlowContext outerTryContext;

    public TryFlowContext(FlowContext flowContext, ASTNode aSTNode) {
        super(flowContext, aSTNode);
    }

    @Override // org.eclipse.jdt.internal.compiler.flow.FlowContext
    public void markFinallyNullStatus(LocalVariableBinding localVariableBinding, int i) {
        FlowContext flowContext = this.outerTryContext;
        if (flowContext != null) {
            flowContext.markFinallyNullStatus(localVariableBinding, i);
        }
        super.markFinallyNullStatus(localVariableBinding, i);
    }

    @Override // org.eclipse.jdt.internal.compiler.flow.FlowContext
    public void mergeFinallyNullInfo(FlowInfo flowInfo) {
        FlowContext flowContext = this.outerTryContext;
        if (flowContext != null) {
            flowContext.mergeFinallyNullInfo(flowInfo);
        }
        super.mergeFinallyNullInfo(flowInfo);
    }
}
