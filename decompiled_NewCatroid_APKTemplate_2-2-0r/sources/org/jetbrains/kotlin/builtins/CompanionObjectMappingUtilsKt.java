package org.jetbrains.kotlin.builtins;

import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;

/* JADX INFO: compiled from: CompanionObjectMappingUtils.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u001a\u0012\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004\u001a\u0012\u0010\u0005\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u0007¨\u0006\b"}, d2 = {"isMappedIntrinsicCompanionObject", "", "Lorg/jetbrains/kotlin/builtins/CompanionObjectMapping;", "classDescriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "isMappedIntrinsicCompanionObjectClassId", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "descriptors"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class CompanionObjectMappingUtilsKt {
    public static final boolean isMappedIntrinsicCompanionObject(CompanionObjectMapping companionObjectMapping, ClassDescriptor classDescriptor) {
        Intrinsics.checkNotNullParameter(companionObjectMapping, "<this>");
        Intrinsics.checkNotNullParameter(classDescriptor, "classDescriptor");
        if (DescriptorUtils.isCompanionObject(classDescriptor)) {
            Set<ClassId> classIds = companionObjectMapping.getClassIds();
            ClassId classId = DescriptorUtilsKt.getClassId((ClassifierDescriptor) classDescriptor);
            if (CollectionsKt.contains(classIds, classId != null ? classId.getOuterClassId() : null)) {
                return true;
            }
        }
        return false;
    }

    public static final boolean isMappedIntrinsicCompanionObjectClassId(CompanionObjectMapping companionObjectMapping, ClassId classId) {
        Intrinsics.checkNotNullParameter(companionObjectMapping, "<this>");
        Intrinsics.checkNotNullParameter(classId, "classId");
        return CollectionsKt.contains(companionObjectMapping.getClassIds(), classId.getOuterClassId());
    }
}
