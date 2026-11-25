package org.jetbrains.kotlin.codegen;

import com.android.dx.rop.code.RegisterSpec;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.intrinsics.JavaClassProperty;
import org.jetbrains.kotlin.codegen.state.GenerationState;
import org.jetbrains.kotlin.codegen.state.KotlinTypeMapper;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.resolve.InlineClassesUtilsKt;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.commons.InstructionAdapter;

/* JADX INFO: loaded from: classes3.dex */
public class CallReceiver extends StackValue {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final StackValue dispatchReceiver;
    private final StackValue extensionReceiver;
    private final KotlinType secondReceiverKotlinType;
    private final Type secondReceiverType;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 15 || i == 16) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 15 || i == 16) ? 2 : 3];
        switch (i) {
            case 1:
            case 6:
                objArr[0] = "extensionReceiver";
                break;
            case 2:
            case 12:
                objArr[0] = ModuleXmlParser.TYPE;
                break;
            case 3:
            case 7:
            case 9:
                objArr[0] = "resolvedCall";
                break;
            case 4:
                objArr[0] = "codegen";
                break;
            case 5:
            default:
                objArr[0] = "dispatchReceiver";
                break;
            case 8:
            case 10:
                objArr[0] = "typeMapper";
                break;
            case 11:
                objArr[0] = "state";
                break;
            case 13:
            case 14:
                objArr[0] = RegisterSpec.PREFIX;
                break;
            case 15:
            case 16:
                objArr[0] = "org/jetbrains/kotlin/codegen/CallReceiver";
                break;
        }
        if (i == 15) {
            objArr[1] = "getDispatchReceiver";
        } else if (i != 16) {
            objArr[1] = "org/jetbrains/kotlin/codegen/CallReceiver";
        } else {
            objArr[1] = "getExtensionReceiver";
        }
        switch (i) {
            case 3:
            case 4:
            case 5:
            case 6:
                objArr[2] = "generateCallReceiver";
                break;
            case 7:
            case 8:
                objArr[2] = "calcDispatchReceiverType";
                break;
            case 9:
            case 10:
            case 11:
                objArr[2] = "calcExtensionReceiverType";
                break;
            case 12:
            case 13:
                objArr[2] = "putSelector";
                break;
            case 14:
                objArr[2] = "dup";
                break;
            case 15:
            case 16:
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 15 && i != 16) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    private static <T> T calcDispatchReceiver(T t, boolean z, T t2, T t3) {
        if (t != null) {
            return t;
        }
        return z ? t2 : t3;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    private CallReceiver(StackValue stackValue, StackValue stackValue2, Type type, KotlinType kotlinType, Type type2, KotlinType kotlinType2) {
        super(type, kotlinType, stackValue.canHaveSideEffects() || stackValue2.canHaveSideEffects());
        if (stackValue == null) {
            $$$reportNull$$$0(0);
        }
        if (stackValue2 == null) {
            $$$reportNull$$$0(1);
        }
        if (type == null) {
            $$$reportNull$$$0(2);
        }
        this.dispatchReceiver = stackValue;
        this.extensionReceiver = stackValue2;
        this.secondReceiverType = type2;
        this.secondReceiverKotlinType = kotlinType2;
    }

    public StackValue withoutReceiverArgument() {
        return new CallReceiver(this.dispatchReceiver, none(), this.type, this.kotlinType, this.secondReceiverType, this.secondReceiverKotlinType);
    }

    public static StackValue generateCallReceiver(ResolvedCall<?> resolvedCall, ExpressionCodegen expressionCodegen, Callable callable, ReceiverParameterDescriptor receiverParameterDescriptor, StackValue stackValue, ReceiverParameterDescriptor receiverParameterDescriptor2, StackValue stackValue2) {
        JvmKotlinType jvmKotlinType;
        Type type;
        KotlinType kotlinType;
        if (resolvedCall == null) {
            $$$reportNull$$$0(3);
        }
        if (expressionCodegen == null) {
            $$$reportNull$$$0(4);
        }
        if (stackValue == null) {
            $$$reportNull$$$0(5);
        }
        if (stackValue2 == null) {
            $$$reportNull$$$0(6);
        }
        KotlinTypeMapper kotlinTypeMapper = expressionCodegen.typeMapper;
        GenerationState state = expressionCodegen.getState();
        if (receiverParameterDescriptor2 != null) {
            jvmKotlinType = calcExtensionReceiverType(resolvedCall, receiverParameterDescriptor2, kotlinTypeMapper, callable, state);
            if (receiverParameterDescriptor != null) {
                JvmKotlinType jvmKotlinTypeCalcDispatchReceiverType = calcDispatchReceiverType(resolvedCall, receiverParameterDescriptor, kotlinTypeMapper, callable);
                Type type2 = jvmKotlinTypeCalcDispatchReceiverType.getType();
                kotlinType = jvmKotlinTypeCalcDispatchReceiverType.getKotlinType();
                type = type2;
            }
            return new CallReceiver(stackValue, stackValue2, jvmKotlinType.getType(), jvmKotlinType.getKotlinType(), type, kotlinType);
        }
        if (receiverParameterDescriptor != null) {
            jvmKotlinType = calcDispatchReceiverType(resolvedCall, receiverParameterDescriptor, kotlinTypeMapper, callable);
        } else if (isLocalFunCall(callable)) {
            jvmKotlinType = new JvmKotlinType(callable.getGenerateCalleeType(), null);
        } else {
            jvmKotlinType = new JvmKotlinType(Type.VOID_TYPE, null);
        }
        type = null;
        kotlinType = null;
        return new CallReceiver(stackValue, stackValue2, jvmKotlinType.getType(), jvmKotlinType.getKotlinType(), type, kotlinType);
    }

    private static JvmKotlinType calcDispatchReceiverType(ResolvedCall<?> resolvedCall, ReceiverParameterDescriptor receiverParameterDescriptor, KotlinTypeMapper kotlinTypeMapper, Callable callable) {
        if (resolvedCall == null) {
            $$$reportNull$$$0(7);
        }
        if (kotlinTypeMapper == null) {
            $$$reportNull$$$0(8);
        }
        if (receiverParameterDescriptor == null) {
            return null;
        }
        CallableDescriptor resultingDescriptor = resolvedCall.getResultingDescriptor();
        if (CodegenUtilKt.isJvmStaticInObjectOrClassOrInterface(resultingDescriptor)) {
            return new JvmKotlinType(Type.VOID_TYPE, null);
        }
        DeclarationDescriptor containingDeclaration = resultingDescriptor.getContainingModule();
        if (callable != null) {
            if (InlineClassesUtilsKt.isInlineClass(containingDeclaration)) {
                ClassDescriptor classDescriptor = (ClassDescriptor) containingDeclaration;
                return new JvmKotlinType(kotlinTypeMapper.mapType(classDescriptor), classDescriptor.getDefaultType());
            }
            return new JvmKotlinType(callable.getDispatchReceiverType(), callable.getDispatchReceiverKotlinType());
        }
        if (containingDeclaration instanceof ClassDescriptor) {
            ClassDescriptor classDescriptor2 = (ClassDescriptor) containingDeclaration;
            return new JvmKotlinType(kotlinTypeMapper.mapType(classDescriptor2), classDescriptor2.getDefaultType());
        }
        KotlinType returnType = receiverParameterDescriptor.getReturnType();
        return new JvmKotlinType(kotlinTypeMapper.mapType(returnType), returnType);
    }

    private static JvmKotlinType calcExtensionReceiverType(ResolvedCall<?> resolvedCall, ReceiverParameterDescriptor receiverParameterDescriptor, KotlinTypeMapper kotlinTypeMapper, Callable callable, GenerationState generationState) {
        if (resolvedCall == null) {
            $$$reportNull$$$0(9);
        }
        if (kotlinTypeMapper == null) {
            $$$reportNull$$$0(10);
        }
        if (generationState == null) {
            $$$reportNull$$$0(11);
        }
        if (receiverParameterDescriptor == null) {
            return null;
        }
        CallableDescriptor callableDescriptor = resolvedCall.get$suspendPropertyDescriptor();
        if ((callableDescriptor instanceof PropertyDescriptor) && generationState.getIntrinsics().getIntrinsic((PropertyDescriptor) callableDescriptor) != JavaClassProperty.INSTANCE) {
            ReceiverParameterDescriptor extensionReceiverParameter = callableDescriptor.getExtensionReceiverParameter();
            return new JvmKotlinType(kotlinTypeMapper.mapType(extensionReceiverParameter.getType()), extensionReceiverParameter.getType());
        }
        if (callable != null) {
            return new JvmKotlinType(callable.getExtensionReceiverType(), callable.getExtensionReceiverKotlinType());
        }
        return new JvmKotlinType(kotlinTypeMapper.mapType(receiverParameterDescriptor.getType()), receiverParameterDescriptor.getType());
    }

    @Override // org.jetbrains.kotlin.codegen.StackValue
    public void putSelector(Type type, KotlinType kotlinType, InstructionAdapter instructionAdapter) {
        if (type == null) {
            $$$reportNull$$$0(12);
        }
        if (instructionAdapter == null) {
            $$$reportNull$$$0(13);
        }
        StackValue stackValueOnStack = this.extensionReceiver;
        boolean z = stackValueOnStack != none();
        if (this.extensionReceiver instanceof StackValue.SafeCall) {
            stackValueOnStack.put(stackValueOnStack.type, stackValueOnStack.kotlinType, instructionAdapter);
            stackValueOnStack = StackValue.onStack(stackValueOnStack.type, stackValueOnStack.kotlinType);
        }
        Type type2 = (Type) calcDispatchReceiver(this.secondReceiverType, z, this.dispatchReceiver.type, type);
        this.dispatchReceiver.put(type2, (KotlinType) calcDispatchReceiver(this.secondReceiverKotlinType, z, this.dispatchReceiver.kotlinType, kotlinType), instructionAdapter);
        if (!z) {
            type = stackValueOnStack.type;
        }
        if (!z) {
            kotlinType = stackValueOnStack.kotlinType;
        }
        stackValueOnStack.moveToTopOfStack(type, kotlinType, instructionAdapter, type2.getSize());
    }

    @Override // org.jetbrains.kotlin.codegen.StackValue
    public void dup(InstructionAdapter instructionAdapter, boolean z) {
        if (instructionAdapter == null) {
            $$$reportNull$$$0(14);
        }
        AsmUtil.dup(instructionAdapter, this.extensionReceiver.type, this.dispatchReceiver.type);
    }

    public StackValue getDispatchReceiver() {
        StackValue stackValue = this.dispatchReceiver;
        if (stackValue == null) {
            $$$reportNull$$$0(15);
        }
        return stackValue;
    }

    public StackValue getExtensionReceiver() {
        StackValue stackValue = this.extensionReceiver;
        if (stackValue == null) {
            $$$reportNull$$$0(16);
        }
        return stackValue;
    }
}
