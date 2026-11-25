package org.jetbrains.kotlin.fir.types;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ConeIntegerLiteralType.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b&\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/fir/types/ConeIntegerConstantOperatorType;", "Lorg/jetbrains/kotlin/fir/types/ConeIntegerLiteralType;", "isUnsigned", "", "nullability", "Lorg/jetbrains/kotlin/fir/types/ConeNullability;", "(ZLorg/jetbrains/kotlin/fir/types/ConeNullability;)V", "cones"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class ConeIntegerConstantOperatorType extends ConeIntegerLiteralType {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ConeIntegerConstantOperatorType(boolean z, ConeNullability nullability) {
        super(z, nullability, null);
        Intrinsics.checkNotNullParameter(nullability, "nullability");
    }
}
