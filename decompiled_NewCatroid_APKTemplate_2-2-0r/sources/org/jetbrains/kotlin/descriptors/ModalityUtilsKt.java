package org.jetbrains.kotlin.descriptors;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.resolve.DescriptorUtils;

/* JADX INFO: compiled from: ModalityUtils.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\"\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0000\u0010\u0003\"\u0015\u0010\u0004\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0003\"\u0015\u0010\u0005\u001a\u00020\u0001*\u00020\u00068F¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0007\"\u0015\u0010\b\u001a\u00020\u0001*\u00020\u00068F¢\u0006\u0006\u001a\u0004\b\b\u0010\u0007¨\u0006\t"}, d2 = {"isFinalClass", "", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "(Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;)Z", "isFinalOrEnum", "isOverridable", "Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;", "(Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;)Z", "isOverridableOrOverrides", "descriptors"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ModalityUtilsKt {
    public static final boolean isOverridable(CallableMemberDescriptor callableMemberDescriptor) {
        Intrinsics.checkNotNullParameter(callableMemberDescriptor, "<this>");
        if (!Intrinsics.areEqual(callableMemberDescriptor.getVisibility(), DescriptorVisibilities.PRIVATE) && callableMemberDescriptor.getModality() != Modality.FINAL) {
            DeclarationDescriptor containingDeclaration = callableMemberDescriptor.getContainingDeclaration();
            ClassDescriptor classDescriptor = containingDeclaration instanceof ClassDescriptor ? (ClassDescriptor) containingDeclaration : null;
            if (classDescriptor == null || !isFinalClass(classDescriptor)) {
                return true;
            }
        }
        return false;
    }

    public static final boolean isOverridableOrOverrides(CallableMemberDescriptor callableMemberDescriptor) {
        Intrinsics.checkNotNullParameter(callableMemberDescriptor, "<this>");
        return isOverridable(callableMemberDescriptor) || DescriptorUtils.isOverride(callableMemberDescriptor);
    }

    public static final boolean isFinalClass(ClassDescriptor classDescriptor) {
        Intrinsics.checkNotNullParameter(classDescriptor, "<this>");
        return classDescriptor.getModality() == Modality.FINAL && classDescriptor.getKind() != ClassKind.ENUM_CLASS;
    }

    public static final boolean isFinalOrEnum(ClassDescriptor classDescriptor) {
        Intrinsics.checkNotNullParameter(classDescriptor, "<this>");
        return classDescriptor.getModality() == Modality.FINAL;
    }
}
