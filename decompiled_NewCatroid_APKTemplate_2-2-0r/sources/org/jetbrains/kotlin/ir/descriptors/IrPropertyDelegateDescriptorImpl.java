package org.jetbrains.kotlin.ir.descriptors;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.FieldDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.name.NameUtils;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: IrDelegateDescriptor.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u00012\u00020\u0002B\u001d\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\u0006¢\u0006\u0002\u0010\bR\u0014\u0010\u0003\u001a\u00020\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0014\u0010\u0007\u001a\u00020\u0006X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/ir/descriptors/IrPropertyDelegateDescriptorImpl;", "Lorg/jetbrains/kotlin/ir/descriptors/IrDelegateDescriptorBase;", "Lorg/jetbrains/kotlin/ir/descriptors/IrPropertyDelegateDescriptor;", "correspondingProperty", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "delegateType", "Lorg/jetbrains/kotlin/types/KotlinType;", "kPropertyType", "(Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;Lorg/jetbrains/kotlin/types/KotlinType;Lorg/jetbrains/kotlin/types/KotlinType;)V", "getCorrespondingProperty", "()Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "getKPropertyType", "()Lorg/jetbrains/kotlin/types/KotlinType;", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IrPropertyDelegateDescriptorImpl extends IrDelegateDescriptorBase implements IrPropertyDelegateDescriptor {
    private final PropertyDescriptor correspondingProperty;
    private final KotlinType kPropertyType;

    @Override // org.jetbrains.kotlin.ir.descriptors.IrPropertyDelegateDescriptor
    public PropertyDescriptor getCorrespondingProperty() {
        return this.correspondingProperty;
    }

    @Override // org.jetbrains.kotlin.ir.descriptors.IrPropertyDelegateDescriptor
    public KotlinType getKPropertyType() {
        return this.kPropertyType;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public IrPropertyDelegateDescriptorImpl(PropertyDescriptor correspondingProperty, KotlinType delegateType, KotlinType kPropertyType) {
        Annotations annotations;
        Intrinsics.checkNotNullParameter(correspondingProperty, "correspondingProperty");
        Intrinsics.checkNotNullParameter(delegateType, "delegateType");
        Intrinsics.checkNotNullParameter(kPropertyType, "kPropertyType");
        DeclarationDescriptor containingDeclaration = correspondingProperty.getPackageFragment();
        Intrinsics.checkNotNullExpressionValue(containingDeclaration, "getContainingDeclaration(...)");
        Name name2 = correspondingProperty.getShortName();
        Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
        Name namePropertyDelegateName = NameUtils.propertyDelegateName(name2);
        FieldDescriptor delegateField = correspondingProperty.getDelegateField();
        super(containingDeclaration, namePropertyDelegateName, delegateType, (delegateField == null || (annotations = delegateField.getAnnotations()) == null) ? Annotations.INSTANCE.getEMPTY() : annotations);
        this.correspondingProperty = correspondingProperty;
        this.kPropertyType = kPropertyType;
    }
}
