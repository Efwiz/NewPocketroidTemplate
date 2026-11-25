package org.jetbrains.kotlin.codegen;

import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.binding.CodegenBinding;
import org.jetbrains.kotlin.codegen.inline.SourceCompilerForInlineKt;
import org.jetbrains.kotlin.codegen.optimization.nullCheck.RedundantNullCheckMethodTransformerKt;
import org.jetbrains.kotlin.codegen.state.GenerationState;
import org.jetbrains.kotlin.codegen.state.KotlinTypeMapper;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.VariableAccessorDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptorWithAccessors;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.FunctionImportedFromObject;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.jvm.InlineClassManglingRulesKt;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DescriptorWithContainerSource;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: DelegatedPropertiesCodegenHelper.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0002J\u000e\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012J\u0016\u0010\u000f\u001a\u00020\u00102\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u000e0\u0014H\u0002J\u0010\u0010\u0015\u001a\u00020\u00102\u0006\u0010\r\u001a\u00020\u000eH\u0002J\f\u0010\u0016\u001a\u00020\u000e*\u00020\u000eH\u0002R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/codegen/DelegatedPropertiesCodegenHelper;", "", "state", "Lorg/jetbrains/kotlin/codegen/state/GenerationState;", "(Lorg/jetbrains/kotlin/codegen/state/GenerationState;)V", "bindingContext", "Lorg/jetbrains/kotlin/resolve/BindingContext;", "bindingTrace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "typeMapper", "Lorg/jetbrains/kotlin/codegen/state/KotlinTypeMapper;", "getMetadataParameterIndex", "", "calleeDescriptor", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "isDelegatedPropertyMetadataRequired", "", "descriptor", "Lorg/jetbrains/kotlin/descriptors/VariableDescriptorWithAccessors;", "operatorCall", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "isDelegatedPropertyMetadataRequiredForFunctionFromBinaries", "getActualCallee", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DelegatedPropertiesCodegenHelper {
    private final BindingContext bindingContext;
    private final BindingTrace bindingTrace;
    private final GenerationState state;
    private final KotlinTypeMapper typeMapper;

    public DelegatedPropertiesCodegenHelper(GenerationState state) {
        Intrinsics.checkNotNullParameter(state, "state");
        this.state = state;
        this.bindingContext = state.getBindingContext();
        this.bindingTrace = state.getBindingTrace();
        this.typeMapper = state.getTypeMapper();
    }

    public final boolean isDelegatedPropertyMetadataRequired(VariableDescriptorWithAccessors descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        ResolvedCall<FunctionDescriptor> resolvedCall = (ResolvedCall) this.bindingContext.get(BindingContext.PROVIDE_DELEGATE_RESOLVED_CALL, descriptor);
        VariableAccessorDescriptor getter = descriptor.getGetter();
        ResolvedCall<FunctionDescriptor> resolvedCall2 = getter != null ? (ResolvedCall) this.bindingContext.get(BindingContext.DELEGATED_PROPERTY_RESOLVED_CALL, getter) : null;
        VariableAccessorDescriptor setter = descriptor.getSetter();
        ResolvedCall<FunctionDescriptor> resolvedCall3 = setter != null ? (ResolvedCall) this.bindingContext.get(BindingContext.DELEGATED_PROPERTY_RESOLVED_CALL, setter) : null;
        return (resolvedCall != null && isDelegatedPropertyMetadataRequired(resolvedCall)) || (resolvedCall2 != null && isDelegatedPropertyMetadataRequired(resolvedCall2)) || (resolvedCall3 != null && isDelegatedPropertyMetadataRequired(resolvedCall3));
    }

    private final boolean isDelegatedPropertyMetadataRequired(ResolvedCall<FunctionDescriptor> operatorCall) {
        CallableDescriptor resultingDescriptor = operatorCall.getResultingDescriptor();
        Intrinsics.checkNotNullExpressionValue(resultingDescriptor, "getResultingDescriptor(...)");
        FunctionDescriptor original = getActualCallee((FunctionDescriptor) resultingDescriptor).getOriginal();
        Intrinsics.checkNotNullExpressionValue(original, "getOriginal(...)");
        if (!original.isInline()) {
            return true;
        }
        if (Intrinsics.areEqual((Object) true, this.bindingContext.get(BindingContext.UNUSED_DELEGATED_PROPERTY_OPERATOR_PARAMETER, original.getValueParameters().get(1)))) {
            return false;
        }
        if (!(original instanceof DescriptorWithContainerSource)) {
            return true;
        }
        Boolean bool = (Boolean) this.bindingTrace.get(CodegenBinding.PROPERTY_METADATA_REQUIRED_FOR_OPERATOR_CALL, original);
        if (bool != null) {
            return bool.booleanValue();
        }
        boolean zIsDelegatedPropertyMetadataRequiredForFunctionFromBinaries = isDelegatedPropertyMetadataRequiredForFunctionFromBinaries(original);
        this.bindingTrace.record(CodegenBinding.PROPERTY_METADATA_REQUIRED_FOR_OPERATOR_CALL, original, Boolean.valueOf(zIsDelegatedPropertyMetadataRequiredForFunctionFromBinaries));
        return zIsDelegatedPropertyMetadataRequiredForFunctionFromBinaries;
    }

    private final FunctionDescriptor getActualCallee(FunctionDescriptor functionDescriptor) {
        return functionDescriptor instanceof FunctionImportedFromObject ? ((FunctionImportedFromObject) functionDescriptor).getCallableFromObject() : functionDescriptor;
    }

    private final boolean isDelegatedPropertyMetadataRequiredForFunctionFromBinaries(FunctionDescriptor calleeDescriptor) {
        if (!(calleeDescriptor instanceof DescriptorWithContainerSource)) {
            throw new IllegalArgumentException(("Function descriptor from binaries expected: " + calleeDescriptor).toString());
        }
        return RedundantNullCheckMethodTransformerKt.usesLocalExceptParameterNullCheck(SourceCompilerForInlineKt.loadCompiledInlineFunction(KotlinTypeMapper.INSTANCE.getContainingClassesForDeserializedCallable((DescriptorWithContainerSource) calleeDescriptor).getImplClassId(), this.state.getTypeMapper().mapAsmMethod(calleeDescriptor), calleeDescriptor.isSuspend(), InlineClassManglingRulesKt.requiresFunctionNameManglingForReturnType(calleeDescriptor), this.state).getNode(), getMetadataParameterIndex(calleeDescriptor));
    }

    private final int getMetadataParameterIndex(FunctionDescriptor calleeDescriptor) {
        int size;
        calleeDescriptor.getValueParameters().size();
        ReceiverParameterDescriptor dispatchReceiverParameter = calleeDescriptor.mo6753getDispatchReceiverParameter();
        if (dispatchReceiverParameter != null) {
            KotlinTypeMapper kotlinTypeMapper = this.typeMapper;
            KotlinType type = dispatchReceiverParameter.getType();
            Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
            size = KotlinTypeMapper.mapType$default(kotlinTypeMapper, type, null, null, 6, null).getSize();
        } else {
            size = 0;
        }
        ReceiverParameterDescriptor extensionReceiverParameter = calleeDescriptor.getExtensionReceiverParameter();
        if (extensionReceiverParameter != null) {
            KotlinTypeMapper kotlinTypeMapper2 = this.typeMapper;
            KotlinType type2 = extensionReceiverParameter.getType();
            Intrinsics.checkNotNullExpressionValue(type2, "getType(...)");
            size += KotlinTypeMapper.mapType$default(kotlinTypeMapper2, type2, null, null, 6, null).getSize();
        }
        KotlinTypeMapper kotlinTypeMapper3 = this.typeMapper;
        KotlinType type3 = calleeDescriptor.getValueParameters().get(0).getType();
        Intrinsics.checkNotNullExpressionValue(type3, "getType(...)");
        return size + KotlinTypeMapper.mapType$default(kotlinTypeMapper3, type3, null, null, 6, null).getSize();
    }
}
