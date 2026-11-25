package org.jetbrains.kotlin.resolve.calls.model;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.resolve.calls.tower.TowerUtilsKt;

/* JADX INFO: compiled from: SimpleCandidateFactory.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\u001a\n\u0010\u0000\u001a\u00020\u0001*\u00020\u0002¨\u0006\u0003"}, d2 = {"isSyntheticEnumEntries", "", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "resolution"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class SimpleCandidateFactoryKt {
    public static final boolean isSyntheticEnumEntries(PropertyDescriptor propertyDescriptor) {
        Intrinsics.checkNotNullParameter(propertyDescriptor, "<this>");
        if (TowerUtilsKt.isSynthesized(propertyDescriptor) && propertyDescriptor.mo6753getDispatchReceiverParameter() == null && propertyDescriptor.getExtensionReceiverParameter() == null) {
            DeclarationDescriptor containingDeclaration = propertyDescriptor.getModuleDescriptor();
            ClassDescriptor classDescriptor = containingDeclaration instanceof ClassDescriptor ? (ClassDescriptor) containingDeclaration : null;
            if ((classDescriptor != null ? classDescriptor.getKind() : null) == ClassKind.ENUM_CLASS) {
                return true;
            }
        }
        return false;
    }
}
