package org.jetbrains.kotlin.codegen;

import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: AccessorForPropertyBackingField.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001BC\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\b\u0010\b\u001a\u0004\u0018\u00010\t\u0012\b\u0010\n\u001a\u0004\u0018\u00010\t\u0012\u0006\u0010\u000b\u001a\u00020\f\u0012\u0006\u0010\r\u001a\u00020\u000e¢\u0006\u0002\u0010\u000f¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/codegen/AccessorForPropertyBackingField;", "Lorg/jetbrains/kotlin/codegen/AccessorForPropertyDescriptor;", "property", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "containingDeclaration", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "delegateType", "Lorg/jetbrains/kotlin/types/KotlinType;", "extensionReceiverParameter", "Lorg/jetbrains/kotlin/descriptors/ReceiverParameterDescriptor;", "dispatchReceiverParameter", "nameSuffix", "", "fieldAccessorKind", "Lorg/jetbrains/kotlin/codegen/AccessorKind;", "(Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;Lorg/jetbrains/kotlin/types/KotlinType;Lorg/jetbrains/kotlin/descriptors/ReceiverParameterDescriptor;Lorg/jetbrains/kotlin/descriptors/ReceiverParameterDescriptor;Ljava/lang/String;Lorg/jetbrains/kotlin/codegen/AccessorKind;)V", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class AccessorForPropertyBackingField extends AccessorForPropertyDescriptor {
    /* JADX WARN: Illegal instructions before constructor call */
    public AccessorForPropertyBackingField(PropertyDescriptor property, DeclarationDescriptor containingDeclaration, KotlinType kotlinType, ReceiverParameterDescriptor receiverParameterDescriptor, ReceiverParameterDescriptor receiverParameterDescriptor2, String nameSuffix, AccessorKind fieldAccessorKind) {
        KotlinType kotlinType2;
        Intrinsics.checkNotNullParameter(property, "property");
        Intrinsics.checkNotNullParameter(containingDeclaration, "containingDeclaration");
        Intrinsics.checkNotNullParameter(nameSuffix, "nameSuffix");
        Intrinsics.checkNotNullParameter(fieldAccessorKind, "fieldAccessorKind");
        if (kotlinType == null) {
            KotlinType type = property.getType();
            Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
            kotlinType2 = type;
        } else {
            kotlinType2 = kotlinType;
        }
        super(property, kotlinType2, receiverParameterDescriptor != null ? receiverParameterDescriptor.getType() : null, receiverParameterDescriptor2, containingDeclaration, null, nameSuffix, fieldAccessorKind);
    }
}
