package org.eclipse.jdt.internal.compiler.flow;

import org.eclipse.jdt.internal.compiler.ast.ASTNode;
import org.eclipse.jdt.internal.compiler.lookup.Binding;
import org.eclipse.jdt.internal.compiler.lookup.BlockScope;
import org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding;
import org.eclipse.jdt.internal.compiler.lookup.TypeBinding;

/* JADX INFO: loaded from: classes14.dex */
public class InitializationFlowContext extends ExceptionHandlingFlowContext {
    public int exceptionCount;
    public FlowInfo[] exceptionThrowerFlowInfos;
    public ASTNode[] exceptionThrowers;
    public FlowInfo initsBeforeContext;
    public TypeBinding[] thrownExceptions;

    @Override // org.eclipse.jdt.internal.compiler.flow.ExceptionHandlingFlowContext, org.eclipse.jdt.internal.compiler.flow.FlowContext
    public FlowContext getInitializationContext() {
        return this;
    }

    public InitializationFlowContext(FlowContext flowContext, ASTNode aSTNode, FlowInfo flowInfo, FlowContext flowContext2, BlockScope blockScope) {
        super(flowContext, aSTNode, Binding.NO_EXCEPTIONS, flowContext2, blockScope, FlowInfo.DEAD_END);
        this.thrownExceptions = new TypeBinding[5];
        this.exceptionThrowers = new ASTNode[5];
        this.exceptionThrowerFlowInfos = new FlowInfo[5];
        this.initsBeforeContext = flowInfo;
    }

    public void checkInitializerExceptions(BlockScope blockScope, FlowContext flowContext, FlowInfo flowInfo) {
        for (int i = 0; i < this.exceptionCount; i++) {
            flowContext.checkExceptionHandlers(this.thrownExceptions[i], this.exceptionThrowers[i], this.exceptionThrowerFlowInfos[i], blockScope);
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.flow.ExceptionHandlingFlowContext, org.eclipse.jdt.internal.compiler.flow.FlowContext
    public String individualToString() {
        StringBuffer stringBuffer = new StringBuffer("Initialization flow context");
        for (int i = 0; i < this.exceptionCount; i++) {
            stringBuffer.append('[').append(this.thrownExceptions[i].readableName());
            stringBuffer.append('-').append(this.exceptionThrowerFlowInfos[i].toString()).append(']');
        }
        return stringBuffer.toString();
    }

    @Override // org.eclipse.jdt.internal.compiler.flow.ExceptionHandlingFlowContext
    public void recordHandlingException(ReferenceBinding referenceBinding, UnconditionalFlowInfo unconditionalFlowInfo, TypeBinding typeBinding, TypeBinding typeBinding2, ASTNode aSTNode, boolean z) {
        TypeBinding[] typeBindingArr = this.thrownExceptions;
        int length = typeBindingArr.length;
        if (this.exceptionCount == length) {
            int i = length * 2;
            TypeBinding[] typeBindingArr2 = new TypeBinding[i];
            this.thrownExceptions = typeBindingArr2;
            System.arraycopy(typeBindingArr, 0, typeBindingArr2, 0, length);
            ASTNode[] aSTNodeArr = this.exceptionThrowers;
            ASTNode[] aSTNodeArr2 = new ASTNode[i];
            this.exceptionThrowers = aSTNodeArr2;
            System.arraycopy(aSTNodeArr, 0, aSTNodeArr2, 0, length);
            FlowInfo[] flowInfoArr = this.exceptionThrowerFlowInfos;
            FlowInfo[] flowInfoArr2 = new FlowInfo[i];
            this.exceptionThrowerFlowInfos = flowInfoArr2;
            System.arraycopy(flowInfoArr, 0, flowInfoArr2, 0, length);
        }
        TypeBinding[] typeBindingArr3 = this.thrownExceptions;
        int i2 = this.exceptionCount;
        typeBindingArr3[i2] = typeBinding;
        this.exceptionThrowers[i2] = aSTNode;
        FlowInfo[] flowInfoArr3 = this.exceptionThrowerFlowInfos;
        this.exceptionCount = i2 + 1;
        flowInfoArr3[i2] = unconditionalFlowInfo.copy();
    }
}
