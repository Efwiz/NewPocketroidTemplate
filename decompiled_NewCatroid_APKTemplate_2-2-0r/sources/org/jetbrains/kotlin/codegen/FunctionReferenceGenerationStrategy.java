package org.jetbrains.kotlin.codegen;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.collections.CollectionsKt;
import org.jetbrains.kotlin.backend.common.CodegenUtil;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.codegen.state.GenerationState;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.psi.KtCallExpression;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtPsiFactory;
import org.jetbrains.kotlin.psi.KtValueArgument;
import org.jetbrains.kotlin.psi.ValueArgument;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.calls.components.ArgumentsUtilsKt;
import org.jetbrains.kotlin.resolve.calls.model.DefaultValueArgument;
import org.jetbrains.kotlin.resolve.calls.model.DelegatingResolvedCall;
import org.jetbrains.kotlin.resolve.calls.model.ExpressionValueArgument;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedValueArgument;
import org.jetbrains.kotlin.resolve.calls.model.VarargValueArgument;
import org.jetbrains.kotlin.resolve.calls.util.CallMaker;
import org.jetbrains.kotlin.resolve.jvm.jvmSignature.JvmMethodSignature;
import org.jetbrains.kotlin.resolve.scopes.receivers.ExpressionReceiver;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.commons.InstructionAdapter;

/* JADX INFO: loaded from: classes3.dex */
public class FunctionReferenceGenerationStrategy extends FunctionGenerationStrategy.CodegenBased {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final FunctionDescriptor functionDescriptor;
    private final boolean isInliningStrategy;
    private final KotlinType receiverKotlinType;
    private final Type receiverType;
    private final StackValue receiverValue;
    private final FunctionDescriptor referencedFunction;
    private final ResolvedCall<?> resolvedCall;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        switch (i) {
            case 11:
            case 12:
            case 13:
                str = "@NotNull method %s.%s must not return null";
                break;
            default:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
        }
        switch (i) {
            case 11:
            case 12:
            case 13:
                i2 = 2;
                break;
            default:
                i2 = 3;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
                objArr[0] = "functionDescriptor";
                break;
            case 2:
                objArr[0] = "resolvedCall";
                break;
            case 3:
            case 6:
            case 8:
            case 10:
                objArr[0] = "codegen";
                break;
            case 4:
            case 7:
            case 9:
                objArr[0] = "signature";
                break;
            case 5:
                objArr[0] = "fakeArguments";
                break;
            case 11:
            case 12:
            case 13:
                objArr[0] = "org/jetbrains/kotlin/codegen/FunctionReferenceGenerationStrategy";
                break;
            default:
                objArr[0] = "state";
                break;
        }
        switch (i) {
            case 11:
            case 12:
            case 13:
                objArr[1] = "receiverParameterStackValue";
                break;
            default:
                objArr[1] = "org/jetbrains/kotlin/codegen/FunctionReferenceGenerationStrategy";
                break;
        }
        switch (i) {
            case 3:
            case 4:
                objArr[2] = "doGenerateBody";
                break;
            case 5:
            case 6:
                objArr[2] = "computeAndSaveArguments";
                break;
            case 7:
            case 8:
                objArr[2] = "computeAndSaveReceiver";
                break;
            case 9:
            case 10:
                objArr[2] = "receiverParameterStackValue";
                break;
            case 11:
            case 12:
            case 13:
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        switch (i) {
            case 11:
            case 12:
            case 13:
                throw new IllegalStateException(str2);
            default:
                throw new IllegalArgumentException(str2);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FunctionReferenceGenerationStrategy(GenerationState generationState, FunctionDescriptor functionDescriptor, ResolvedCall<?> resolvedCall, JvmKotlinType jvmKotlinType, StackValue stackValue, boolean z) {
        super(generationState);
        if (generationState == null) {
            $$$reportNull$$$0(0);
        }
        if (functionDescriptor == null) {
            $$$reportNull$$$0(1);
        }
        if (resolvedCall == null) {
            $$$reportNull$$$0(2);
        }
        this.resolvedCall = resolvedCall;
        FunctionDescriptor functionDescriptor2 = (FunctionDescriptor) resolvedCall.getResultingDescriptor();
        if (functionDescriptor2.isSuspend()) {
            this.referencedFunction = CoroutineCodegenUtilKt.getOrCreateJvmSuspendFunctionView(functionDescriptor2, generationState);
            this.functionDescriptor = CoroutineCodegenUtilKt.getOrCreateJvmSuspendFunctionView(functionDescriptor, generationState);
        } else {
            this.referencedFunction = functionDescriptor2;
            this.functionDescriptor = functionDescriptor;
        }
        this.receiverType = jvmKotlinType != null ? jvmKotlinType.getType() : null;
        this.receiverKotlinType = jvmKotlinType != null ? jvmKotlinType.getKotlinType() : null;
        this.receiverValue = stackValue;
        this.isInliningStrategy = z;
    }

    @Override // org.jetbrains.kotlin.codegen.FunctionGenerationStrategy.CodegenBased
    public void doGenerateBody(ExpressionCodegen expressionCodegen, JvmMethodSignature jvmMethodSignature) {
        StackValue stackValueInvokeFunction;
        if (expressionCodegen == null) {
            $$$reportNull$$$0(3);
        }
        if (jvmMethodSignature == null) {
            $$$reportNull$$$0(4);
        }
        final int iComputeExpectedNumberOfReceivers = CallableReferenceUtilKt.computeExpectedNumberOfReceivers(this.referencedFunction, this.receiverType != null);
        final int size = this.functionDescriptor.getValueParameters().size() - iComputeExpectedNumberOfReceivers;
        KtCallExpression ktCallExpressionConstructFakeFunctionCall = CodegenUtil.constructFakeFunctionCall(this.state.getProject(), size);
        final List<KtValueArgument> valueArguments = ktCallExpressionConstructFakeFunctionCall.getValueArguments();
        final ReceiverValue receiverValueComputeAndSaveReceiver = computeAndSaveReceiver(jvmMethodSignature, expressionCodegen, this.referencedFunction.mo6753getDispatchReceiverParameter());
        final ReceiverValue receiverValueComputeAndSaveReceiver2 = computeAndSaveReceiver(jvmMethodSignature, expressionCodegen, this.referencedFunction.getExtensionReceiverParameter());
        computeAndSaveArguments(valueArguments, expressionCodegen, iComputeExpectedNumberOfReceivers);
        DelegatingResolvedCall<CallableDescriptor> delegatingResolvedCall = new DelegatingResolvedCall<CallableDescriptor>(this.resolvedCall) { // from class: org.jetbrains.kotlin.codegen.FunctionReferenceGenerationStrategy.1
            static final /* synthetic */ boolean $assertionsDisabled = false;
            private final Map<ValueParameterDescriptor, ResolvedValueArgument> argumentMap = new LinkedHashMap();

            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                Object[] objArr = new Object[2];
                objArr[0] = "org/jetbrains/kotlin/codegen/FunctionReferenceGenerationStrategy$1";
                if (i == 1) {
                    objArr[1] = "getCandidateDescriptor";
                } else if (i != 2) {
                    objArr[1] = "getValueArguments";
                } else {
                    objArr[1] = "getResultingDescriptor";
                }
                throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", objArr));
            }

            {
                int i = 0;
                for (ValueParameterDescriptor valueParameterDescriptor : FunctionReferenceGenerationStrategy.this.referencedFunction.getValueParameters()) {
                    if (valueParameterDescriptor.getVarargElementType() != null) {
                        if (i != size) {
                            if (FunctionReferenceGenerationStrategy.this.functionDescriptor.getValueParameters().get(iComputeExpectedNumberOfReceivers + i).getType().equals(valueParameterDescriptor.getVarargElementType())) {
                                this.argumentMap.put(valueParameterDescriptor, new VarargValueArgument(valueArguments.subList(i, size)));
                                i = size;
                            }
                        } else {
                            this.argumentMap.put(valueParameterDescriptor, ArgumentsUtilsKt.hasDefaultValue(valueParameterDescriptor) ? DefaultValueArgument.DEFAULT : new VarargValueArgument());
                        }
                    }
                    if (i < size) {
                        this.argumentMap.put(valueParameterDescriptor, new ExpressionValueArgument((ValueArgument) valueArguments.get(i)));
                        i++;
                    } else {
                        this.argumentMap.put(valueParameterDescriptor, DefaultValueArgument.DEFAULT);
                    }
                }
            }

            @Override // org.jetbrains.kotlin.resolve.calls.model.DelegatingResolvedCall, org.jetbrains.kotlin.resolve.calls.model.ResolvedCall
            public ReceiverValue getExtensionReceiver() {
                return receiverValueComputeAndSaveReceiver2;
            }

            @Override // org.jetbrains.kotlin.resolve.calls.model.DelegatingResolvedCall, org.jetbrains.kotlin.resolve.calls.model.ResolvedCall
            public ReceiverValue getDispatchReceiver() {
                return receiverValueComputeAndSaveReceiver;
            }

            @Override // org.jetbrains.kotlin.resolve.calls.model.DelegatingResolvedCall, org.jetbrains.kotlin.resolve.calls.model.ResolvedCall
            public List<ResolvedValueArgument> getValueArgumentsByIndex() {
                return new ArrayList(this.argumentMap.values());
            }

            @Override // org.jetbrains.kotlin.resolve.calls.model.DelegatingResolvedCall, org.jetbrains.kotlin.resolve.calls.model.ResolvedCall
            public Map<ValueParameterDescriptor, ResolvedValueArgument> getValueArguments() {
                Map<ValueParameterDescriptor, ResolvedValueArgument> map = this.argumentMap;
                if (map == null) {
                    $$$reportNull$$$0(0);
                }
                return map;
            }

            @Override // org.jetbrains.kotlin.resolve.calls.model.DelegatingResolvedCall, org.jetbrains.kotlin.resolve.calls.model.ResolvedCall
            /* JADX INFO: renamed from: getCandidateDescriptor */
            public CallableDescriptor get$suspendPropertyDescriptor() {
                FunctionDescriptor functionDescriptor = FunctionReferenceGenerationStrategy.this.referencedFunction;
                if (functionDescriptor == null) {
                    $$$reportNull$$$0(1);
                }
                return functionDescriptor;
            }

            @Override // org.jetbrains.kotlin.resolve.calls.model.DelegatingResolvedCall, org.jetbrains.kotlin.resolve.calls.model.ResolvedCall
            public CallableDescriptor getResultingDescriptor() {
                FunctionDescriptor functionDescriptor = FunctionReferenceGenerationStrategy.this.referencedFunction;
                if (functionDescriptor == null) {
                    $$$reportNull$$$0(2);
                }
                return functionDescriptor;
            }
        };
        Type returnType = expressionCodegen.getReturnType();
        if (this.referencedFunction instanceof ConstructorDescriptor) {
            if (returnType.getSort() == 9) {
                stackValueInvokeFunction = expressionCodegen.generateNewArray(ktCallExpressionConstructFakeFunctionCall, this.referencedFunction.getReturnType(), delegatingResolvedCall);
            } else {
                stackValueInvokeFunction = expressionCodegen.generateConstructorCall(delegatingResolvedCall, expressionCodegen.typeMapper.mapType(this.referencedFunction.getReturnType()));
            }
        } else {
            stackValueInvokeFunction = expressionCodegen.invokeFunction(CallMaker.makeCall(ktCallExpressionConstructFakeFunctionCall, null, null, ktCallExpressionConstructFakeFunctionCall, valueArguments), delegatingResolvedCall, StackValue.none());
        }
        InstructionAdapter instructionAdapter = expressionCodegen.v;
        stackValueInvokeFunction.put(returnType, this.functionDescriptor.getReturnType(), instructionAdapter);
        instructionAdapter.areturn(returnType);
    }

    private void computeAndSaveArguments(List<? extends ValueArgument> list, ExpressionCodegen expressionCodegen, int i) {
        if (list == null) {
            $$$reportNull$$$0(5);
        }
        if (expressionCodegen == null) {
            $$$reportNull$$$0(6);
        }
        List listDrop = CollectionsKt.drop(this.functionDescriptor.getValueParameters(), i);
        for (int i2 = 0; i2 < listDrop.size(); i2++) {
            ValueParameterDescriptor valueParameterDescriptor = (ValueParameterDescriptor) listDrop.get(i2);
            ValueArgument valueArgument = list.get(i2);
            Type typeMapType = this.state.getTypeMapper().mapType(valueParameterDescriptor);
            int index = expressionCodegen.myFrameMap.getIndex(valueParameterDescriptor);
            if (index > 0) {
                expressionCodegen.tempVariables.put(valueArgument.getArgumentExpression(), StackValue.local(index, typeMapType));
            } else {
                expressionCodegen.tempVariables.put(valueArgument.getArgumentExpression(), StackValue.local(valueParameterDescriptor.getIndex() + 1 + i, typeMapType));
            }
        }
    }

    private ReceiverValue computeAndSaveReceiver(JvmMethodSignature jvmMethodSignature, ExpressionCodegen expressionCodegen, ReceiverParameterDescriptor receiverParameterDescriptor) {
        if (jvmMethodSignature == null) {
            $$$reportNull$$$0(7);
        }
        if (expressionCodegen == null) {
            $$$reportNull$$$0(8);
        }
        if (receiverParameterDescriptor == null) {
            return null;
        }
        KtExpression ktExpressionCreateExpression = new KtPsiFactory(this.state.getProject(), false).createExpression("callableReferenceFakeReceiver");
        expressionCodegen.tempVariables.put(ktExpressionCreateExpression, receiverParameterStackValue(jvmMethodSignature, expressionCodegen));
        return ExpressionReceiver.INSTANCE.create(ktExpressionCreateExpression, receiverParameterDescriptor.getType(), BindingContext.EMPTY);
    }

    private StackValue receiverParameterStackValue(JvmMethodSignature jvmMethodSignature, ExpressionCodegen expressionCodegen) {
        if (jvmMethodSignature == null) {
            $$$reportNull$$$0(9);
        }
        if (expressionCodegen == null) {
            $$$reportNull$$$0(10);
        }
        StackValue stackValue = this.receiverValue;
        if (stackValue != null) {
            if (stackValue == null) {
                $$$reportNull$$$0(11);
            }
            return stackValue;
        }
        if (this.receiverType != null) {
            StackValue stackValueCapturedBoundReferenceReceiver = CallableReferenceUtilKt.capturedBoundReferenceReceiver(expressionCodegen.getState().getTypeMapper().mapClass((ClassDescriptor) expressionCodegen.getContext().getParentContext().getContextDescriptor()), this.receiverType, this.receiverKotlinType, this.isInliningStrategy);
            if (stackValueCapturedBoundReferenceReceiver == null) {
                $$$reportNull$$$0(12);
            }
            return stackValueCapturedBoundReferenceReceiver;
        }
        StackValue.Local local = StackValue.local(1, jvmMethodSignature.getAsmMethod().getArgumentTypes()[0]);
        if (local == null) {
            $$$reportNull$$$0(13);
        }
        return local;
    }
}
