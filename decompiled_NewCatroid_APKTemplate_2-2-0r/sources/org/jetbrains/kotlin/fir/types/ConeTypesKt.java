package org.jetbrains.kotlin.fir.types;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ConeTypes.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\b\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\n\u0010\u0000\u001a\u00020\u0001*\u00020\u0001¨\u0006\u0002"}, d2 = {"unwrapDefinitelyNotNull", "Lorg/jetbrains/kotlin/fir/types/ConeSimpleKotlinType;", "cones"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ConeTypesKt {
    public static final ConeSimpleKotlinType unwrapDefinitelyNotNull(ConeSimpleKotlinType coneSimpleKotlinType) {
        Intrinsics.checkNotNullParameter(coneSimpleKotlinType, "<this>");
        return coneSimpleKotlinType instanceof ConeDefinitelyNotNullType ? ((ConeDefinitelyNotNullType) coneSimpleKotlinType).getOriginal() : coneSimpleKotlinType;
    }
}
