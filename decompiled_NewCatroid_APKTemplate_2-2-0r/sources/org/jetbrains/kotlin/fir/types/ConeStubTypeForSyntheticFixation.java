package org.jetbrains.kotlin.fir.types;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.load.java.JvmAbi;

/* JADX INFO: compiled from: ConeTypeVariableAndStubTypes.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/fir/types/ConeStubTypeForSyntheticFixation;", "Lorg/jetbrains/kotlin/fir/types/ConeStubTypeForChainInference;", JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME, "Lorg/jetbrains/kotlin/fir/types/ConeStubTypeConstructor;", "nullability", "Lorg/jetbrains/kotlin/fir/types/ConeNullability;", "(Lorg/jetbrains/kotlin/fir/types/ConeStubTypeConstructor;Lorg/jetbrains/kotlin/fir/types/ConeNullability;)V", "cones"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ConeStubTypeForSyntheticFixation extends ConeStubTypeForChainInference {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ConeStubTypeForSyntheticFixation(ConeStubTypeConstructor constructor, ConeNullability nullability) {
        super(constructor, nullability);
        Intrinsics.checkNotNullParameter(constructor, "constructor");
        Intrinsics.checkNotNullParameter(nullability, "nullability");
    }
}
