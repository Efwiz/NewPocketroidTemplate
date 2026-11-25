package org.jetbrains.kotlin.resolve.calls.tasks;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.types.DynamicTypesKt;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: dynamicCalls.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\u001a\n\u0010\u0000\u001a\u00020\u0001*\u00020\u0002¨\u0006\u0003"}, d2 = {"isDynamic", "", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "frontend"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class DynamicCallsKt {
    public static final boolean isDynamic(DeclarationDescriptor declarationDescriptor) {
        ReceiverParameterDescriptor receiverParameterDescriptorMo6753getDispatchReceiverParameter;
        Intrinsics.checkNotNullParameter(declarationDescriptor, "<this>");
        if (!(declarationDescriptor instanceof CallableDescriptor) || (receiverParameterDescriptorMo6753getDispatchReceiverParameter = ((CallableDescriptor) declarationDescriptor).mo6753getDispatchReceiverParameter()) == null) {
            return false;
        }
        KotlinType type = receiverParameterDescriptorMo6753getDispatchReceiverParameter.getType();
        Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
        return DynamicTypesKt.isDynamic(type);
    }
}
