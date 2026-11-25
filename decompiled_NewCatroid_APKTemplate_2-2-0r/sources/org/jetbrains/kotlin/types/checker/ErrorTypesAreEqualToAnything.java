package org.jetbrains.kotlin.types.checker;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: NewKotlinTypeChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0006H\u0016J\u0018\u0010\b\u001a\u00020\u00042\u0006\u0010\t\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u0006H\u0016¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/types/checker/ErrorTypesAreEqualToAnything;", "Lorg/jetbrains/kotlin/types/checker/KotlinTypeChecker;", "()V", "equalTypes", "", "a", "Lorg/jetbrains/kotlin/types/KotlinType;", JvmAnnotationNames.SERIALIZED_IR_BYTES_FIELD_NAME, "isSubtypeOf", "subtype", "supertype", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ErrorTypesAreEqualToAnything implements KotlinTypeChecker {
    public static final ErrorTypesAreEqualToAnything INSTANCE = new ErrorTypesAreEqualToAnything();

    private ErrorTypesAreEqualToAnything() {
    }

    @Override // org.jetbrains.kotlin.types.checker.KotlinTypeChecker
    public boolean isSubtypeOf(KotlinType subtype, KotlinType supertype) {
        Intrinsics.checkNotNullParameter(subtype, "subtype");
        Intrinsics.checkNotNullParameter(supertype, "supertype");
        return NewKotlinTypeChecker.INSTANCE.getDefault().isSubtypeOf(ClassicTypeCheckerStateKt.createClassicTypeCheckerState$default(true, false, null, null, null, 30, null), subtype.unwrap(), supertype.unwrap());
    }

    @Override // org.jetbrains.kotlin.types.checker.KotlinTypeChecker
    public boolean equalTypes(KotlinType a, KotlinType b) {
        Intrinsics.checkNotNullParameter(a, "a");
        Intrinsics.checkNotNullParameter(b, "b");
        return NewKotlinTypeChecker.INSTANCE.getDefault().equalTypes(ClassicTypeCheckerStateKt.createClassicTypeCheckerState$default(true, false, null, null, null, 30, null), a.unwrap(), b.unwrap());
    }
}
