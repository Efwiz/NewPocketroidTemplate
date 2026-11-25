package org.jetbrains.kotlin.descriptors;

import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.descriptors.impl.SyntheticFieldDescriptor;
import org.jetbrains.kotlin.psi.KtDeclarationWithBody;
import org.jetbrains.kotlin.resolve.DescriptorToSourceUtils;

/* JADX INFO: compiled from: descriptorPsiUtils.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000$\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0010\u0010\u0006\u001a\u00020\u00072\b\u0010\b\u001a\u0004\u0018\u00010\t\u001a\n\u0010\n\u001a\u00020\u0007*\u00020\u000b\"\u001b\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005¨\u0006\f"}, d2 = {"explicitParameters", "", "Lorg/jetbrains/kotlin/descriptors/ParameterDescriptor;", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "getExplicitParameters", "(Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;)Ljava/util/List;", "isBackingFieldReference", "", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "hasBody", "Lorg/jetbrains/kotlin/descriptors/PropertyAccessorDescriptor;", "frontend"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class DescriptorPsiUtilsKt {
    public static final boolean hasBody(PropertyAccessorDescriptor propertyAccessorDescriptor) {
        Intrinsics.checkNotNullParameter(propertyAccessorDescriptor, "<this>");
        PsiElement sourceFromDescriptor = DescriptorToSourceUtils.getSourceFromDescriptor(propertyAccessorDescriptor);
        KtDeclarationWithBody ktDeclarationWithBody = sourceFromDescriptor instanceof KtDeclarationWithBody ? (KtDeclarationWithBody) sourceFromDescriptor : null;
        return ktDeclarationWithBody != null && ktDeclarationWithBody.hasBody();
    }

    public static final boolean isBackingFieldReference(DeclarationDescriptor declarationDescriptor) {
        return declarationDescriptor instanceof SyntheticFieldDescriptor;
    }

    public static final List<ParameterDescriptor> getExplicitParameters(CallableDescriptor callableDescriptor) {
        Intrinsics.checkNotNullParameter(callableDescriptor, "<this>");
        ArrayList arrayList = new ArrayList(callableDescriptor.getValueParameters().size() + 2);
        ReceiverParameterDescriptor receiverParameterDescriptorMo6753getDispatchReceiverParameter = callableDescriptor.mo6753getDispatchReceiverParameter();
        if (receiverParameterDescriptorMo6753getDispatchReceiverParameter != null) {
            arrayList.add(receiverParameterDescriptorMo6753getDispatchReceiverParameter);
        }
        ReceiverParameterDescriptor extensionReceiverParameter = callableDescriptor.getExtensionReceiverParameter();
        if (extensionReceiverParameter != null) {
            arrayList.add(extensionReceiverParameter);
        }
        arrayList.addAll(callableDescriptor.getValueParameters());
        return arrayList;
    }
}
