package org.jetbrains.kotlin.codegen.context;

import kotlin.text.StringsKt;
import org.jetbrains.kotlin.codegen.AsmUtil;
import org.jetbrains.kotlin.codegen.ExpressionCodegen;
import org.jetbrains.kotlin.codegen.JvmCodegenUtil;
import org.jetbrains.kotlin.codegen.StackValue;
import org.jetbrains.kotlin.codegen.binding.CodegenBinding;
import org.jetbrains.kotlin.codegen.binding.MutableClosure;
import org.jetbrains.kotlin.codegen.state.GenerationState;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptorWithAccessors;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.expressions.ExpressionTypingUtils;
import org.jetbrains.org.objectweb.asm.Type;

/* JADX INFO: loaded from: classes3.dex */
public interface LocalLookup {
    boolean isLocal(DeclarationDescriptor declarationDescriptor);

    public enum LocalLookupCase {
        VAR { // from class: org.jetbrains.kotlin.codegen.context.LocalLookup.LocalLookupCase.1
            @Override // org.jetbrains.kotlin.codegen.context.LocalLookup.LocalLookupCase
            public boolean isCase(DeclarationDescriptor declarationDescriptor) {
                return (declarationDescriptor instanceof VariableDescriptor) && !(declarationDescriptor instanceof PropertyDescriptor);
            }

            @Override // org.jetbrains.kotlin.codegen.context.LocalLookup.LocalLookupCase
            public StackValue.StackValueWithSimpleReceiver innerValue(DeclarationDescriptor declarationDescriptor, LocalLookup localLookup, GenerationState generationState, MutableClosure mutableClosure, Type type) {
                StackValue.StackValueWithSimpleReceiver stackValueWithSimpleReceiverField;
                EnclosedValueDescriptor enclosedValueDescriptor;
                VariableDescriptor variableDescriptor = (VariableDescriptor) declarationDescriptor;
                if (localLookup == null || !localLookup.isLocal(variableDescriptor)) {
                    return null;
                }
                KotlinType propertyDelegateType = variableDescriptor instanceof VariableDescriptorWithAccessors ? JvmCodegenUtil.getPropertyDelegateType((VariableDescriptorWithAccessors) variableDescriptor, generationState.getBindingContext()) : null;
                Type sharedVarType = generationState.getTypeMapper().getSharedVarType(variableDescriptor);
                if (propertyDelegateType == null) {
                    propertyDelegateType = variableDescriptor.getType();
                }
                Type typeMapType = generationState.getTypeMapper().mapType(propertyDelegateType);
                Type type2 = sharedVarType != null ? sharedVarType : typeMapType;
                KotlinType kotlinType = sharedVarType != null ? null : propertyDelegateType;
                String capturedFieldName = AsmUtil.getCapturedFieldName(variableDescriptor.getShortName().asString());
                StackValue.Local local = StackValue.LOCAL_0;
                if (sharedVarType != null) {
                    StackValue.Field fieldReceiverWithRefWrapper = StackValue.receiverWithRefWrapper(typeMapType, type, capturedFieldName, local, variableDescriptor);
                    stackValueWithSimpleReceiverField = StackValue.fieldForSharedVar(typeMapType, type, capturedFieldName, fieldReceiverWithRefWrapper, variableDescriptor);
                    enclosedValueDescriptor = new EnclosedValueDescriptor(capturedFieldName, declarationDescriptor, stackValueWithSimpleReceiverField, fieldReceiverWithRefWrapper, type2, kotlinType);
                } else {
                    stackValueWithSimpleReceiverField = StackValue.field(type2, kotlinType, type, capturedFieldName, false, local, variableDescriptor);
                    enclosedValueDescriptor = new EnclosedValueDescriptor(capturedFieldName, declarationDescriptor, stackValueWithSimpleReceiverField, type2, kotlinType);
                }
                mutableClosure.captureVariable(enclosedValueDescriptor);
                return stackValueWithSimpleReceiverField;
            }
        },
        LOCAL_NAMED_FUNCTION { // from class: org.jetbrains.kotlin.codegen.context.LocalLookup.LocalLookupCase.2
            static final /* synthetic */ boolean $assertionsDisabled = false;

            @Override // org.jetbrains.kotlin.codegen.context.LocalLookup.LocalLookupCase
            public boolean isCase(DeclarationDescriptor declarationDescriptor) {
                return ExpressionTypingUtils.isLocalFunction(declarationDescriptor);
            }

            @Override // org.jetbrains.kotlin.codegen.context.LocalLookup.LocalLookupCase
            public StackValue.StackValueWithSimpleReceiver innerValue(DeclarationDescriptor declarationDescriptor, LocalLookup localLookup, GenerationState generationState, MutableClosure mutableClosure, Type type) {
                FunctionDescriptor functionDescriptor = (FunctionDescriptor) declarationDescriptor;
                if (localLookup == null || !localLookup.isLocal(functionDescriptor)) {
                    return null;
                }
                BindingContext bindingContext = generationState.getBindingContext();
                Type typeAsmTypeForAnonymousClass = CodegenBinding.asmTypeForAnonymousClass(bindingContext, functionDescriptor);
                MutableClosure mutableClosure2 = (MutableClosure) bindingContext.get(CodegenBinding.CLOSURE, (ClassDescriptor) bindingContext.get(CodegenBinding.CLASS_FOR_CALLABLE, functionDescriptor));
                if (mutableClosure2 != null && JvmCodegenUtil.isConst(mutableClosure2)) {
                    return StackValue.field(typeAsmTypeForAnonymousClass, null, typeAsmTypeForAnonymousClass, JvmAbi.INSTANCE_FIELD, true, StackValue.LOCAL_0, functionDescriptor);
                }
                String internalName = typeAsmTypeForAnonymousClass.getInternalName();
                String strSubstringAfterLast = StringsKt.substringAfterLast(internalName, "/", internalName);
                int iLastIndexOf = strSubstringAfterLast.lastIndexOf(36);
                String str = AsmUtil.getCapturedFieldName(functionDescriptor.getShortName().asString()) + (iLastIndexOf >= 0 ? strSubstringAfterLast.substring(iLastIndexOf) : "");
                StackValue.Field field = StackValue.field(typeAsmTypeForAnonymousClass, null, type, str, false, StackValue.LOCAL_0, functionDescriptor);
                mutableClosure.captureVariable(new EnclosedValueDescriptor(str, declarationDescriptor, field, typeAsmTypeForAnonymousClass, null));
                return field;
            }
        },
        RECEIVER { // from class: org.jetbrains.kotlin.codegen.context.LocalLookup.LocalLookupCase.3
            static final /* synthetic */ boolean $assertionsDisabled = false;

            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                String str = i != 2 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
                Object[] objArr = new Object[i != 2 ? 3 : 2];
                if (i == 1) {
                    objArr[0] = "codegen";
                } else if (i != 2) {
                    objArr[0] = "d";
                } else {
                    objArr[0] = "org/jetbrains/kotlin/codegen/context/LocalLookup$LocalLookupCase$3";
                }
                if (i != 2) {
                    objArr[1] = "org/jetbrains/kotlin/codegen/context/LocalLookup$LocalLookupCase$3";
                } else {
                    objArr[1] = "outerValue";
                }
                if (i != 2) {
                    objArr[2] = "outerValue";
                }
                String str2 = String.format(str, objArr);
                if (i == 2) {
                    throw new IllegalStateException(str2);
                }
            }

            @Override // org.jetbrains.kotlin.codegen.context.LocalLookup.LocalLookupCase
            public boolean isCase(DeclarationDescriptor declarationDescriptor) {
                return declarationDescriptor instanceof ReceiverParameterDescriptor;
            }

            @Override // org.jetbrains.kotlin.codegen.context.LocalLookup.LocalLookupCase
            public StackValue.StackValueWithSimpleReceiver innerValue(DeclarationDescriptor declarationDescriptor, LocalLookup localLookup, GenerationState generationState, MutableClosure mutableClosure, Type type) {
                CallableDescriptor enclosingCallableDescriptorWithReceiver = mutableClosure.getEnclosingCallableDescriptorWithReceiver();
                ReceiverParameterDescriptor extensionReceiverParameter = enclosingCallableDescriptorWithReceiver != null ? enclosingCallableDescriptorWithReceiver.getExtensionReceiverParameter() : null;
                if (extensionReceiverParameter != declarationDescriptor) {
                    return null;
                }
                KotlinType type2 = extensionReceiverParameter.getType();
                StackValue.Field field = StackValue.field(generationState.getTypeMapper().mapType(type2), type2, type, mutableClosure.getCapturedReceiverFieldName(generationState.getBindingContext(), generationState.getLanguageVersionSettings()), false, StackValue.LOCAL_0, declarationDescriptor);
                mutableClosure.setNeedsCaptureReceiverFromOuterContext();
                return field;
            }

            @Override // org.jetbrains.kotlin.codegen.context.LocalLookup.LocalLookupCase
            public StackValue outerValue(EnclosedValueDescriptor enclosedValueDescriptor, ExpressionCodegen expressionCodegen) {
                if (enclosedValueDescriptor == null) {
                    $$$reportNull$$$0(0);
                }
                if (expressionCodegen == null) {
                    $$$reportNull$$$0(1);
                }
                StackValue.Local local = StackValue.local(((CallableDescriptor) enclosedValueDescriptor.getDescriptor()).mo6753getDispatchReceiverParameter() != null ? 1 : 0, enclosedValueDescriptor.getType());
                if (local == null) {
                    $$$reportNull$$$0(2);
                }
                return local;
            }
        };

        static final /* synthetic */ boolean $assertionsDisabled = false;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            String str = (i == 2 || i == 3) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
            Object[] objArr = new Object[(i == 2 || i == 3) ? 2 : 3];
            if (i == 1) {
                objArr[0] = "codegen";
            } else if (i == 2 || i == 3) {
                objArr[0] = "org/jetbrains/kotlin/codegen/context/LocalLookup$LocalLookupCase";
            } else {
                objArr[0] = "d";
            }
            if (i == 2 || i == 3) {
                objArr[1] = "outerValue";
            } else {
                objArr[1] = "org/jetbrains/kotlin/codegen/context/LocalLookup$LocalLookupCase";
            }
            if (i != 2 && i != 3) {
                objArr[2] = "outerValue";
            }
            String str2 = String.format(str, objArr);
            if (i != 2 && i != 3) {
                throw new IllegalArgumentException(str2);
            }
            throw new IllegalStateException(str2);
        }

        public abstract StackValue.StackValueWithSimpleReceiver innerValue(DeclarationDescriptor declarationDescriptor, LocalLookup localLookup, GenerationState generationState, MutableClosure mutableClosure, Type type);

        public abstract boolean isCase(DeclarationDescriptor declarationDescriptor);

        public StackValue outerValue(EnclosedValueDescriptor enclosedValueDescriptor, ExpressionCodegen expressionCodegen) {
            if (enclosedValueDescriptor == null) {
                $$$reportNull$$$0(0);
            }
            if (expressionCodegen == null) {
                $$$reportNull$$$0(1);
            }
            DeclarationDescriptor descriptor = enclosedValueDescriptor.getDescriptor();
            int iLookupLocalIndex = expressionCodegen.lookupLocalIndex(descriptor);
            if (iLookupLocalIndex >= 0) {
                StackValue.Local local = StackValue.local(iLookupLocalIndex, enclosedValueDescriptor.getType());
                if (local == null) {
                    $$$reportNull$$$0(2);
                }
                return local;
            }
            StackValue stackValueFindCapturedValue = expressionCodegen.findCapturedValue(descriptor);
            if (stackValueFindCapturedValue == null) {
                $$$reportNull$$$0(3);
            }
            return stackValueFindCapturedValue;
        }
    }
}
