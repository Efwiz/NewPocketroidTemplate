package org.jetbrains.kotlin.resolve.calls.inference.model;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.resolve.descriptorUtil.AnnotationsForResolveUtilsKt;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;

/* JADX INFO: compiled from: TypeVariable.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u0007\u001a\u00020\bH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/inference/model/TypeVariableFromCallableDescriptor;", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/NewTypeVariable;", "originalTypeParameter", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "(Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;)V", "getOriginalTypeParameter", "()Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "hasOnlyInputTypesAnnotation", "", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class TypeVariableFromCallableDescriptor extends NewTypeVariable {
    private final TypeParameterDescriptor originalTypeParameter;

    public final TypeParameterDescriptor getOriginalTypeParameter() {
        return this.originalTypeParameter;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public TypeVariableFromCallableDescriptor(TypeParameterDescriptor originalTypeParameter) {
        Intrinsics.checkNotNullParameter(originalTypeParameter, "originalTypeParameter");
        KotlinBuiltIns builtIns = DescriptorUtilsKt.getBuiltIns(originalTypeParameter);
        String identifier = originalTypeParameter.getShortName().getIdentifier();
        Intrinsics.checkNotNullExpressionValue(identifier, "getIdentifier(...)");
        super(builtIns, identifier, originalTypeParameter, null);
        this.originalTypeParameter = originalTypeParameter;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.inference.model.NewTypeVariable
    public boolean hasOnlyInputTypesAnnotation() {
        return AnnotationsForResolveUtilsKt.hasOnlyInputTypesAnnotation(this.originalTypeParameter);
    }
}
