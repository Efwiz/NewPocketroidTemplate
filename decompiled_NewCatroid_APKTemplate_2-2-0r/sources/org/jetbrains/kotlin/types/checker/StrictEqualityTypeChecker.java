package org.jetbrains.kotlin.types.checker;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;
import org.jetbrains.kotlin.types.AbstractStrictEqualityTypeChecker;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.UnwrappedType;

/* JADX INFO: compiled from: NewKotlinTypeChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0006J\u0016\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\b2\u0006\u0010\u0007\u001a\u00020\b¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/types/checker/StrictEqualityTypeChecker;", "", "()V", "strictEqualTypes", "", "a", "Lorg/jetbrains/kotlin/types/SimpleType;", JvmAnnotationNames.SERIALIZED_IR_BYTES_FIELD_NAME, "Lorg/jetbrains/kotlin/types/UnwrappedType;", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class StrictEqualityTypeChecker {
    public static final StrictEqualityTypeChecker INSTANCE = new StrictEqualityTypeChecker();

    private StrictEqualityTypeChecker() {
    }

    public final boolean strictEqualTypes(UnwrappedType a, UnwrappedType b) {
        Intrinsics.checkNotNullParameter(a, "a");
        Intrinsics.checkNotNullParameter(b, "b");
        return AbstractStrictEqualityTypeChecker.INSTANCE.strictEqualTypes(SimpleClassicTypeSystemContext.INSTANCE, a, b);
    }

    public final boolean strictEqualTypes(SimpleType a, SimpleType b) {
        Intrinsics.checkNotNullParameter(a, "a");
        Intrinsics.checkNotNullParameter(b, "b");
        return AbstractStrictEqualityTypeChecker.INSTANCE.strictEqualTypes(SimpleClassicTypeSystemContext.INSTANCE, a, b);
    }
}
