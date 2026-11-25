package org.jetbrains.kotlin.codegen.context;

import org.jetbrains.kotlin.codegen.DescriptorAsmUtil;
import org.jetbrains.kotlin.codegen.JvmCodegenUtil;
import org.jetbrains.kotlin.codegen.OwnerKind;
import org.jetbrains.kotlin.codegen.StackValue;
import org.jetbrains.kotlin.codegen.binding.MutableClosure;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.codegen.state.GenerationState;
import org.jetbrains.kotlin.codegen.state.KotlinTypeMapper;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.impl.SyntheticFieldDescriptor;
import org.jetbrains.kotlin.resolve.inline.InlineUtil;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.org.objectweb.asm.Label;

/* JADX INFO: loaded from: classes3.dex */
public class MethodContext extends CodegenContext<CallableMemberDescriptor> {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final FunctionDescriptor functionDescriptor;
    private final boolean isDefaultFunctionContext;
    private Label methodEndLabel;
    private Label methodStartLabel;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 3 || i == 8) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 3 || i == 8) ? 2 : 3];
        switch (i) {
            case 1:
                objArr[0] = "contextKind";
                break;
            case 2:
                objArr[0] = "parentContext";
                break;
            case 3:
            case 8:
                objArr[0] = "org/jetbrains/kotlin/codegen/context/MethodContext";
                break;
            case 4:
                objArr[0] = "descriptor";
                break;
            case 5:
                objArr[0] = "state";
                break;
            case 6:
                objArr[0] = "methodStartLabel";
                break;
            case 7:
                objArr[0] = "methodEndLabel";
                break;
            default:
                objArr[0] = "functionDescriptor";
                break;
        }
        if (i == 3) {
            objArr[1] = "getParentContext";
        } else if (i != 8) {
            objArr[1] = "org/jetbrains/kotlin/codegen/context/MethodContext";
        } else {
            objArr[1] = "getFunctionDescriptor";
        }
        switch (i) {
            case 3:
            case 8:
                break;
            case 4:
            case 5:
                objArr[2] = "generateReceiver";
                break;
            case 6:
                objArr[2] = "setMethodStartLabel";
                break;
            case 7:
                objArr[2] = "setMethodEndLabel";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 3 && i != 8) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    protected MethodContext(FunctionDescriptor functionDescriptor, OwnerKind ownerKind, CodegenContext codegenContext, MutableClosure mutableClosure, boolean z) {
        super(JvmCodegenUtil.getDirectMember(functionDescriptor), ownerKind, codegenContext, mutableClosure, codegenContext.hasThisDescriptor() ? codegenContext.getThisDescriptor() : null, null);
        if (functionDescriptor == null) {
            $$$reportNull$$$0(0);
        }
        if (ownerKind == null) {
            $$$reportNull$$$0(1);
        }
        if (codegenContext == null) {
            $$$reportNull$$$0(2);
        }
        this.functionDescriptor = functionDescriptor;
        this.isDefaultFunctionContext = z;
    }

    @Override // org.jetbrains.kotlin.codegen.context.CodegenContext
    public CodegenContext getParentContext() {
        CodegenContext parentContext = super.getParentContext();
        if (parentContext == null) {
            $$$reportNull$$$0(3);
        }
        return parentContext;
    }

    public StackValue getReceiverExpression(KotlinTypeMapper kotlinTypeMapper) {
        KotlinType type = getCallableDescriptorWithReceiver().getExtensionReceiverParameter().getType();
        return StackValue.local(DescriptorAsmUtil.getReceiverIndex(this, getContextDescriptor()), kotlinTypeMapper.mapType(type), type);
    }

    @Override // org.jetbrains.kotlin.codegen.context.CodegenContext
    public StackValue lookupInContext(DeclarationDescriptor declarationDescriptor, StackValue stackValue, GenerationState generationState, boolean z) {
        if (declarationDescriptor instanceof SyntheticFieldDescriptor) {
            declarationDescriptor = ((SyntheticFieldDescriptor) declarationDescriptor).getPropertyDescriptor();
        }
        if (getContextDescriptor() == declarationDescriptor) {
            return stackValue != null ? stackValue : StackValue.LOCAL_0;
        }
        return getParentContext().lookupInContext(declarationDescriptor, stackValue, generationState, z);
    }

    public StackValue generateReceiver(CallableDescriptor callableDescriptor, GenerationState generationState, boolean z) {
        if (callableDescriptor == null) {
            $$$reportNull$$$0(4);
        }
        if (generationState == null) {
            $$$reportNull$$$0(5);
        }
        if (CoroutineCodegenUtilKt.unwrapInitialDescriptorForSuspendFunction(getCallableDescriptorWithReceiver()) == callableDescriptor) {
            return getReceiverExpression(generationState.getTypeMapper());
        }
        return lookupInContext(callableDescriptor.getExtensionReceiverParameter(), StackValue.LOCAL_0, generationState, z);
    }

    @Override // org.jetbrains.kotlin.codegen.context.CodegenContext
    public StackValue getOuterExpression(StackValue stackValue, boolean z) {
        return getParentContext().getOuterExpression(stackValue, false);
    }

    public Label getMethodStartLabel() {
        return this.methodStartLabel;
    }

    public void setMethodStartLabel(Label label) {
        if (label == null) {
            $$$reportNull$$$0(6);
        }
        this.methodStartLabel = label;
    }

    public Label getMethodEndLabel() {
        return this.methodEndLabel;
    }

    public void setMethodEndLabel(Label label) {
        if (label == null) {
            $$$reportNull$$$0(7);
        }
        this.methodEndLabel = label;
    }

    public String toString() {
        return "Method: " + getContextDescriptor();
    }

    @Override // org.jetbrains.kotlin.codegen.context.CodegenContext
    public boolean isInlineMethodContext() {
        return InlineUtil.isInline(getFunctionDescriptor());
    }

    public FunctionDescriptor getFunctionDescriptor() {
        FunctionDescriptor functionDescriptor = this.functionDescriptor;
        if (functionDescriptor == null) {
            $$$reportNull$$$0(8);
        }
        return functionDescriptor;
    }

    public boolean isDefaultFunctionContext() {
        return this.isDefaultFunctionContext;
    }
}
