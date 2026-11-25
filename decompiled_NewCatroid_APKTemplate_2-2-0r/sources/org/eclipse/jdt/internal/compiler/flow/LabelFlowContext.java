package org.eclipse.jdt.internal.compiler.flow;

import org.eclipse.jdt.core.compiler.CharOperation;
import org.eclipse.jdt.internal.compiler.ast.ASTNode;
import org.eclipse.jdt.internal.compiler.codegen.BranchLabel;
import org.eclipse.jdt.internal.compiler.lookup.BlockScope;

/* JADX INFO: loaded from: classes14.dex */
public class LabelFlowContext extends SwitchFlowContext {
    public char[] labelName;

    public LabelFlowContext(FlowContext flowContext, ASTNode aSTNode, char[] cArr, BranchLabel branchLabel, BlockScope blockScope) {
        super(flowContext, aSTNode, branchLabel, false);
        this.labelName = cArr;
        checkLabelValidity(blockScope);
    }

    void checkLabelValidity(BlockScope blockScope) {
        for (FlowContext localParent = getLocalParent(); localParent != null; localParent = localParent.getLocalParent()) {
            char[] cArrLabelName = localParent.labelName();
            if (cArrLabelName != null && CharOperation.equals(cArrLabelName, this.labelName)) {
                blockScope.problemReporter().alreadyDefinedLabel(this.labelName, this.associatedNode);
            }
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.flow.SwitchFlowContext, org.eclipse.jdt.internal.compiler.flow.FlowContext
    public String individualToString() {
        return "Label flow context [label:" + String.valueOf(this.labelName) + "]";
    }

    @Override // org.eclipse.jdt.internal.compiler.flow.FlowContext
    public char[] labelName() {
        return this.labelName;
    }
}
