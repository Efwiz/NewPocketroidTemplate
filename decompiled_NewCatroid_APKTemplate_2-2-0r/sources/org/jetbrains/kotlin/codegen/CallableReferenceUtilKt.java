package org.jetbrains.kotlin.codegen;

import com.reandroid.dex.sections.Marker;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.binding.CalculatedClosure;
import org.jetbrains.kotlin.codegen.binding.CodegenBinding;
import org.jetbrains.kotlin.codegen.state.GenerationState;
import org.jetbrains.kotlin.codegen.state.KotlinTypeMapper;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.ConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.descriptors.VariableAccessorDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptorWithAccessors;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.descriptors.impl.LocalVariableDescriptor;
import org.jetbrains.kotlin.descriptors.impl.PropertyGetterDescriptorImpl;
import org.jetbrains.kotlin.psi.KtCallableReferenceExpression;
import org.jetbrains.kotlin.resolve.DescriptorFactory;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.InlineClassesUtilsKt;
import org.jetbrains.kotlin.resolve.jvm.AsmTypes;
import org.jetbrains.kotlin.resolve.jvm.InlineClassManglingRulesKt;
import org.jetbrains.kotlin.resolve.source.KotlinSourceElement;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.commons.InstructionAdapter;
import org.jetbrains.org.objectweb.asm.commons.Method;

/* JADX INFO: compiled from: callableReferenceUtil.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000l\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\u001a(\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00032\b\u0010\u0005\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0007\u001a\u00020\b\u001a\u0016\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\b\u001a \u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0000\u001a \u0010\u0016\u001a\u00020\b2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0000\u001a \u0010\u0017\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0018\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0000\u001a \u0010\u0019\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0018\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0000\u001a\u0010\u0010\u001a\u001a\u00020\n2\u0006\u0010\u0012\u001a\u00020\u0013H\u0000\u001a\"\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u0018\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00152\b\b\u0002\u0010\u001d\u001a\u00020\bH\u0002\u001a\u0010\u0010\u001e\u001a\u00020\b2\u0006\u0010\u0012\u001a\u00020\u0013H\u0002\u001a.\u0010\u001f\u001a\u0010\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020!\u0018\u00010 *\u00020\u00112\u0006\u0010\"\u001a\u00020#2\f\u0010$\u001a\b\u0012\u0004\u0012\u00020!0%\u001a\u0012\u0010&\u001a\u00020\b*\u00020#2\u0006\u0010'\u001a\u00020!\u001a\n\u0010(\u001a\u00020\b*\u00020#\u001a\n\u0010)\u001a\u00020\b*\u00020#\u001a\n\u0010*\u001a\u00020\b*\u00020+\u001a$\u0010,\u001a\u00020\u000f*\u00020\u00112\u0006\u0010-\u001a\u00020\n2\u0006\u0010.\u001a\u00020\u00032\b\u0010/\u001a\u0004\u0018\u00010\u0006¨\u00060"}, d2 = {"capturedBoundReferenceReceiver", "Lorg/jetbrains/kotlin/codegen/StackValue;", "ownerType", "Lorg/jetbrains/org/objectweb/asm/Type;", "expectedReceiverType", "expectedReceiverKotlinType", "Lorg/jetbrains/kotlin/types/KotlinType;", "isInliningStrategy", "", "computeExpectedNumberOfReceivers", "", "referencedFunction", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "isBound", "generateCallableReferenceDeclarationContainer", "", "iv", "Lorg/jetbrains/org/objectweb/asm/commons/InstructionAdapter;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "state", "Lorg/jetbrains/kotlin/codegen/state/GenerationState;", "generateCallableReferenceDeclarationContainerClass", "generateFunctionReferenceSignature", "callable", "generatePropertyReferenceSignature", "getCallableReferenceTopLevelFlag", "getSignatureString", "", "isPropertySignature", "isTopLevelCallableReference", "generateClosureFieldsInitializationFromParameters", "Lkotlin/Pair;", "Lorg/jetbrains/kotlin/codegen/FieldInfo;", "closure", "Lorg/jetbrains/kotlin/codegen/binding/CalculatedClosure;", "args", "", "isBoundReferenceReceiverField", "fieldInfo", "isForBoundCallableReference", "isForCallableReference", "isSyntheticClassForCallableReference", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "loadBoundReferenceReceiverParameter", "index", ModuleXmlParser.TYPE, "kotlinType", Marker.BACKEND}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class CallableReferenceUtilKt {
    public static final StackValue capturedBoundReferenceReceiver(final Type ownerType, final Type expectedReceiverType, final KotlinType kotlinType, final boolean z) {
        Intrinsics.checkNotNullParameter(ownerType, "ownerType");
        Intrinsics.checkNotNullParameter(expectedReceiverType, "expectedReceiverType");
        StackValue stackValueOperation = StackValue.operation(expectedReceiverType, new Function1<InstructionAdapter, Unit>() { // from class: org.jetbrains.kotlin.codegen.CallableReferenceUtilKt.capturedBoundReferenceReceiver.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(InstructionAdapter instructionAdapter) {
                invoke2(instructionAdapter);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(InstructionAdapter instructionAdapter) {
                instructionAdapter.load(0, ownerType);
                instructionAdapter.getfield(ownerType.getInternalName(), z ? AsmUtil.CAPTURED_RECEIVER_FIELD : "receiver", AsmTypes.OBJECT_TYPE.getDescriptor());
                KotlinType kotlinType2 = kotlinType;
                StackValue.coerce(AsmTypes.OBJECT_TYPE, kotlinType2 != null ? TypeUtilsKt.getBuiltIns(kotlinType2).getNullableAnyType() : null, expectedReceiverType, kotlinType, instructionAdapter);
            }
        });
        Intrinsics.checkNotNullExpressionValue(stackValueOperation, "operation(...)");
        return stackValueOperation;
    }

    public static final boolean isSyntheticClassForCallableReference(ClassDescriptor classDescriptor) {
        Intrinsics.checkNotNullParameter(classDescriptor, "<this>");
        if (classDescriptor instanceof SyntheticClassDescriptorForLambda) {
            SourceElement source = ((SyntheticClassDescriptorForLambda) classDescriptor).getSourceElement();
            KotlinSourceElement kotlinSourceElement = source instanceof KotlinSourceElement ? (KotlinSourceElement) source : null;
            if ((kotlinSourceElement != null ? kotlinSourceElement.getPsi() : null) instanceof KtCallableReferenceExpression) {
                return true;
            }
        }
        return false;
    }

    public static final boolean isForCallableReference(CalculatedClosure calculatedClosure) {
        Intrinsics.checkNotNullParameter(calculatedClosure, "<this>");
        ClassDescriptor closureClass = calculatedClosure.getClosureClass();
        Intrinsics.checkNotNullExpressionValue(closureClass, "getClosureClass(...)");
        return isSyntheticClassForCallableReference(closureClass);
    }

    public static final boolean isForBoundCallableReference(CalculatedClosure calculatedClosure) {
        Intrinsics.checkNotNullParameter(calculatedClosure, "<this>");
        return isForCallableReference(calculatedClosure) && calculatedClosure.getCapturedReceiverFromOuterContext() != null;
    }

    public static final void loadBoundReferenceReceiverParameter(InstructionAdapter instructionAdapter, int i, Type type, KotlinType kotlinType) {
        Intrinsics.checkNotNullParameter(instructionAdapter, "<this>");
        Intrinsics.checkNotNullParameter(type, "type");
        instructionAdapter.load(i, type);
        StackValue.coerce(type, kotlinType, AsmTypes.OBJECT_TYPE, kotlinType != null ? TypeUtilsKt.getBuiltIns(kotlinType).getNullableAnyType() : null, instructionAdapter);
    }

    public static final boolean isBoundReferenceReceiverField(CalculatedClosure calculatedClosure, FieldInfo fieldInfo) {
        Intrinsics.checkNotNullParameter(calculatedClosure, "<this>");
        Intrinsics.checkNotNullParameter(fieldInfo, "fieldInfo");
        return isForBoundCallableReference(calculatedClosure) && Intrinsics.areEqual(fieldInfo.getFieldName(), AsmUtil.CAPTURED_RECEIVER_FIELD);
    }

    public static final Pair<Integer, FieldInfo> generateClosureFieldsInitializationFromParameters(InstructionAdapter instructionAdapter, CalculatedClosure closure, List<? extends FieldInfo> args) {
        Intrinsics.checkNotNullParameter(instructionAdapter, "<this>");
        Intrinsics.checkNotNullParameter(closure, "closure");
        Intrinsics.checkNotNullParameter(args, "args");
        int size = 1;
        int i = -1;
        FieldInfo fieldInfo = null;
        for (FieldInfo fieldInfo2 : args) {
            if (isBoundReferenceReceiverField(closure, fieldInfo2)) {
                fieldInfo = fieldInfo2;
                i = size;
                size = fieldInfo2.getFieldType().getSize() + size;
            } else {
                size = DescriptorAsmUtil.genAssignInstanceFieldFromParam(fieldInfo2, size, instructionAdapter);
            }
        }
        if (fieldInfo != null) {
            return new Pair<>(Integer.valueOf(i), fieldInfo);
        }
        return null;
    }

    public static final int computeExpectedNumberOfReceivers(FunctionDescriptor referencedFunction, boolean z) {
        Intrinsics.checkNotNullParameter(referencedFunction, "referencedFunction");
        int size = ((referencedFunction.getContextReceiverParameters().size() + (referencedFunction.mo6753getDispatchReceiverParameter() != null ? 1 : 0)) + (referencedFunction.getExtensionReceiverParameter() == null ? 0 : 1)) - (z ? 1 : 0);
        if (size < 0 && (referencedFunction instanceof ConstructorDescriptor) && DescriptorUtils.isObject(((ConstructorDescriptor) referencedFunction).getContainingModule().getContainingModule())) {
            return 0;
        }
        return size;
    }

    public static final boolean generateCallableReferenceDeclarationContainerClass(InstructionAdapter iv, CallableDescriptor descriptor, GenerationState state) {
        Intrinsics.checkNotNullParameter(iv, "iv");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(state, "state");
        KotlinTypeMapper typeMapper = state.getTypeMapper();
        DeclarationDescriptor containingDeclaration = descriptor.getContainingModule();
        Intrinsics.checkNotNullExpressionValue(containingDeclaration, "getContainingDeclaration(...)");
        if (containingDeclaration instanceof ClassDescriptor) {
            SimpleType defaultType = ((ClassDescriptor) containingDeclaration).getDefaultType();
            Intrinsics.checkNotNullExpressionValue(defaultType, "getDefaultType(...)");
            DescriptorAsmUtil.putJavaLangClassInstance(iv, typeMapper.mapClass((ClassifierDescriptor) containingDeclaration), defaultType, typeMapper);
            return true;
        }
        if (containingDeclaration instanceof PackageFragmentDescriptor) {
            iv.aconst(typeMapper.mapOwner(descriptor));
            return true;
        }
        if (descriptor instanceof VariableDescriptorWithAccessors) {
            iv.aconst(state.getBindingContext().get(CodegenBinding.DELEGATED_PROPERTY_METADATA_OWNER, descriptor));
            return true;
        }
        iv.aconst(null);
        return false;
    }

    public static final void generateCallableReferenceDeclarationContainer(InstructionAdapter iv, CallableDescriptor descriptor, GenerationState state) {
        Intrinsics.checkNotNullParameter(iv, "iv");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(state, "state");
        if (generateCallableReferenceDeclarationContainerClass(iv, descriptor, state)) {
            if (isTopLevelCallableReference(descriptor)) {
                iv.aconst(state.getModuleName());
                iv.invokestatic(AsmTypes.REFLECTION, "getOrCreateKotlinPackage", Type.getMethodDescriptor(AsmTypes.K_DECLARATION_CONTAINER_TYPE, AsmTypes.getType(Class.class), AsmTypes.getType(String.class)), false);
            } else {
                AsmUtil.wrapJavaClassIntoKClass(iv);
            }
        }
    }

    private static final boolean isTopLevelCallableReference(CallableDescriptor callableDescriptor) {
        if (callableDescriptor instanceof LocalVariableDescriptor) {
            return DescriptorUtils.getParentOfType(callableDescriptor, ClassDescriptor.class) == null;
        }
        return callableDescriptor.getContainingModule() instanceof PackageFragmentDescriptor;
    }

    public static final int getCallableReferenceTopLevelFlag(CallableDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        return isTopLevelCallableReference(descriptor) ? 1 : 0;
    }

    public static final void generateFunctionReferenceSignature(InstructionAdapter iv, CallableDescriptor callable, GenerationState state) {
        Intrinsics.checkNotNullParameter(iv, "iv");
        Intrinsics.checkNotNullParameter(callable, "callable");
        Intrinsics.checkNotNullParameter(state, "state");
        iv.aconst(getSignatureString$default(callable, state, false, 4, null));
    }

    public static final void generatePropertyReferenceSignature(InstructionAdapter iv, CallableDescriptor callable, GenerationState state) {
        Intrinsics.checkNotNullParameter(iv, "iv");
        Intrinsics.checkNotNullParameter(callable, "callable");
        Intrinsics.checkNotNullParameter(state, "state");
        iv.aconst(getSignatureString(callable, state, true));
    }

    static /* synthetic */ String getSignatureString$default(CallableDescriptor callableDescriptor, GenerationState generationState, boolean z, int i, Object obj) {
        if ((i & 4) != 0) {
            z = false;
        }
        return getSignatureString(callableDescriptor, generationState, z);
    }

    private static final String getSignatureString(CallableDescriptor callableDescriptor, GenerationState generationState, boolean z) {
        VariableAccessorDescriptor variableAccessorDescriptor;
        Method methodMapAsmMethod;
        AccessorForConstructorDescriptor accessorForConstructorDescriptor;
        if (callableDescriptor instanceof LocalVariableDescriptor) {
            Type type = (Type) generationState.getBindingContext().get(CodegenBinding.DELEGATED_PROPERTY_METADATA_OWNER, callableDescriptor);
            if (type == null) {
                throw new AssertionError("No delegated property metadata owner for " + callableDescriptor);
            }
            List<LocalVariableDescriptor> localDelegatedProperties = CodegenBinding.getLocalDelegatedProperties(generationState.getBindingContext(), type);
            int iIndexOf = localDelegatedProperties != null ? localDelegatedProperties.indexOf(callableDescriptor) : -1;
            if (iIndexOf < 0) {
                throw new AssertionError("Local delegated property is not found in " + type + ": " + callableDescriptor);
            }
            return "<v#" + iIndexOf + '>';
        }
        if (callableDescriptor instanceof ClassConstructorDescriptor) {
            if (InlineClassManglingRulesKt.shouldHideConstructorDueToValueClassTypeValueParameters((CallableMemberDescriptor) callableDescriptor)) {
                ClassConstructorDescriptor classConstructorDescriptor = (ClassConstructorDescriptor) callableDescriptor;
                accessorForConstructorDescriptor = new AccessorForConstructorDescriptor(classConstructorDescriptor, classConstructorDescriptor.getContainingModule(), null, AccessorKind.NORMAL);
            } else {
                accessorForConstructorDescriptor = (ClassConstructorDescriptor) callableDescriptor;
            }
            variableAccessorDescriptor = accessorForConstructorDescriptor;
        } else if (callableDescriptor instanceof FunctionDescriptor) {
            variableAccessorDescriptor = (FunctionDescriptor) callableDescriptor;
        } else if (callableDescriptor instanceof VariableDescriptorWithAccessors) {
            PropertyGetterDescriptorImpl getter = ((VariableDescriptorWithAccessors) callableDescriptor).getGetter();
            if (getter == null) {
                Intrinsics.checkNotNull(callableDescriptor, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.PropertyDescriptor");
                PropertyDescriptor propertyDescriptor = (PropertyDescriptor) callableDescriptor;
                PropertyGetterDescriptorImpl propertyGetterDescriptorImplCreateDefaultGetter = DescriptorFactory.createDefaultGetter(propertyDescriptor, Annotations.INSTANCE.getEMPTY());
                propertyGetterDescriptorImplCreateDefaultGetter.initialize(propertyDescriptor.getType());
                Intrinsics.checkNotNullExpressionValue(propertyGetterDescriptorImplCreateDefaultGetter, "apply(...)");
                getter = propertyGetterDescriptorImplCreateDefaultGetter;
            }
            variableAccessorDescriptor = getter;
        } else {
            throw new IllegalStateException(("Unsupported callable reference: " + callableDescriptor).toString());
        }
        FunctionDescriptor original = ((FunctionDescriptor) DescriptorUtils.unwrapFakeOverride(variableAccessorDescriptor)).getOriginal();
        Intrinsics.checkNotNullExpressionValue(original, "getOriginal(...)");
        DeclarationDescriptor containingDeclaration = callableDescriptor.getContainingModule();
        Intrinsics.checkNotNullExpressionValue(containingDeclaration, "getContainingDeclaration(...)");
        if (InlineClassesUtilsKt.isInlineClass(containingDeclaration) && !InlineClassesUtilsKt.isGetterOfUnderlyingPropertyOfInlineClass(original)) {
            methodMapAsmMethod = generationState.getTypeMapper().mapSignatureForInlineErasedClassSkipGeneric(original).getAsmMethod();
        } else if (z) {
            methodMapAsmMethod = generationState.getTypeMapper().mapPropertyReferenceSignature(original);
        } else {
            methodMapAsmMethod = generationState.getTypeMapper().mapAsmMethod(original);
        }
        Intrinsics.checkNotNull(methodMapAsmMethod);
        return methodMapAsmMethod.getName() + methodMapAsmMethod.getDescriptor();
    }
}
