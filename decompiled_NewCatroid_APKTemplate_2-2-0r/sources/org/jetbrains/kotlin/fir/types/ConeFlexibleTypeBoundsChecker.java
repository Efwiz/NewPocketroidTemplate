package org.jetbrains.kotlin.fir.types;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.StandardClassIds;

/* JADX INFO: compiled from: ConeFlexibleTypeBoundsChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0006J\u0010\u0010\b\u001a\u0004\u0018\u00010\t2\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/fir/types/ConeFlexibleTypeBoundsChecker;", "", "()V", "areTypesMayBeLowerAndUpperBoundsOfSameFlexibleTypeByMutability", "", "a", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", JvmAnnotationNames.SERIALIZED_IR_BYTES_FIELD_NAME, "getBaseBoundFqNameByMutability", "Lorg/jetbrains/kotlin/name/ClassId;", "providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ConeFlexibleTypeBoundsChecker {
    public static final ConeFlexibleTypeBoundsChecker INSTANCE = new ConeFlexibleTypeBoundsChecker();

    private ConeFlexibleTypeBoundsChecker() {
    }

    public final boolean areTypesMayBeLowerAndUpperBoundsOfSameFlexibleTypeByMutability(ConeKotlinType a, ConeKotlinType b) {
        Intrinsics.checkNotNullParameter(a, "a");
        Intrinsics.checkNotNullParameter(b, "b");
        ClassId classId = ConeTypeUtilsKt.getClassId(a);
        if (classId == null) {
            return false;
        }
        ClassId classId2 = StandardClassIds.Collections.INSTANCE.getBaseCollectionToMutableEquivalent().get(classId);
        if (classId2 == null && (classId2 = StandardClassIds.Collections.INSTANCE.getMutableCollectionToBaseCollection().get(classId)) == null) {
            return false;
        }
        return Intrinsics.areEqual(classId2, ConeTypeUtilsKt.getClassId(b));
    }

    public final ClassId getBaseBoundFqNameByMutability(ConeKotlinType a) {
        Intrinsics.checkNotNullParameter(a, "a");
        ClassId classId = ConeTypeUtilsKt.getClassId(a);
        if (classId == null) {
            return null;
        }
        return StandardClassIds.Collections.INSTANCE.getBaseCollectionToMutableEquivalent().containsKey(classId) ? classId : StandardClassIds.Collections.INSTANCE.getMutableCollectionToBaseCollection().get(classId);
    }
}
