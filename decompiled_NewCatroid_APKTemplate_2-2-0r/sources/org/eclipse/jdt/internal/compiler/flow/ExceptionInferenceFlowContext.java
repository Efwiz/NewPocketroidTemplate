package org.eclipse.jdt.internal.compiler.flow;

import org.eclipse.jdt.internal.compiler.ast.ASTNode;
import org.eclipse.jdt.internal.compiler.lookup.BlockScope;
import org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding;

/* JADX INFO: loaded from: classes14.dex */
public class ExceptionInferenceFlowContext extends ExceptionHandlingFlowContext {
    public ExceptionInferenceFlowContext(FlowContext flowContext, ASTNode aSTNode, ReferenceBinding[] referenceBindingArr, FlowContext flowContext2, BlockScope blockScope, UnconditionalFlowInfo unconditionalFlowInfo) {
        super(flowContext, aSTNode, referenceBindingArr, flowContext2, blockScope, unconditionalFlowInfo);
    }
}
