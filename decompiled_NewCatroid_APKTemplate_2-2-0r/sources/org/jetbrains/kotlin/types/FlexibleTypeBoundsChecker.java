package org.jetbrains.kotlin.types;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;

/* JADX INFO: compiled from: flexibleTypes.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0006J\u0010\u0010\b\u001a\u0004\u0018\u00010\t2\u0006\u0010\n\u001a\u00020\tJ\u0010\u0010\b\u001a\u0004\u0018\u00010\t2\u0006\u0010\u000b\u001a\u00020\u0006¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/types/FlexibleTypeBoundsChecker;", "", "()V", "areTypesMayBeLowerAndUpperBoundsOfSameFlexibleTypeByMutability", "", "a", "Lorg/jetbrains/kotlin/types/KotlinType;", JvmAnnotationNames.SERIALIZED_IR_BYTES_FIELD_NAME, "getBaseBoundFqNameByMutability", "Lorg/jetbrains/kotlin/name/FqName;", "fqName", ModuleXmlParser.TYPE, "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FlexibleTypeBoundsChecker {
    public static final FlexibleTypeBoundsChecker INSTANCE = new FlexibleTypeBoundsChecker();

    private FlexibleTypeBoundsChecker() {
    }

    public final boolean areTypesMayBeLowerAndUpperBoundsOfSameFlexibleTypeByMutability(KotlinType a, KotlinType b) {
        FqName fqNameSafe;
        Intrinsics.checkNotNullParameter(a, "a");
        Intrinsics.checkNotNullParameter(b, "b");
        ClassifierDescriptor this$0 = a.getConstructor().getThis$0();
        if (this$0 == null || (fqNameSafe = DescriptorUtilsKt.getFqNameSafe(this$0)) == null) {
            return false;
        }
        FqName fqName = CommonFlexibleTypeBoundsChecker.INSTANCE.getBaseTypesToMutableEquivalent().get(fqNameSafe);
        if (fqName == null && (fqName = CommonFlexibleTypeBoundsChecker.INSTANCE.getMutableToBaseMap().get(fqNameSafe)) == null) {
            return false;
        }
        ClassifierDescriptor this$1 = b.getConstructor().getThis$0();
        return Intrinsics.areEqual(fqName, this$1 != null ? DescriptorUtilsKt.getFqNameSafe(this$1) : null);
    }

    public final FqName getBaseBoundFqNameByMutability(KotlinType type) {
        FqName fqNameSafe;
        Intrinsics.checkNotNullParameter(type, "type");
        ClassifierDescriptor this$0 = type.getConstructor().getThis$0();
        if (this$0 == null || (fqNameSafe = DescriptorUtilsKt.getFqNameSafe(this$0)) == null) {
            return null;
        }
        return getBaseBoundFqNameByMutability(fqNameSafe);
    }

    public final FqName getBaseBoundFqNameByMutability(FqName fqName) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        return CommonFlexibleTypeBoundsChecker.INSTANCE.getBaseBoundFqNameByMutability(fqName);
    }
}
