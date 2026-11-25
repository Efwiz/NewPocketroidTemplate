package org.jetbrains.kotlin.fir.types;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.load.java.JvmAbi;

/* JADX INFO: compiled from: ConeTypeVariableAndStubTypes.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0016\u0018\u00002\u00020\u0001B\u0017\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006B\u0015\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\t¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/fir/types/ConeStubTypeForChainInference;", "Lorg/jetbrains/kotlin/fir/types/ConeStubType;", "variable", "Lorg/jetbrains/kotlin/fir/types/ConeTypeVariable;", "nullability", "Lorg/jetbrains/kotlin/fir/types/ConeNullability;", "(Lorg/jetbrains/kotlin/fir/types/ConeTypeVariable;Lorg/jetbrains/kotlin/fir/types/ConeNullability;)V", JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME, "Lorg/jetbrains/kotlin/fir/types/ConeStubTypeConstructor;", "(Lorg/jetbrains/kotlin/fir/types/ConeStubTypeConstructor;Lorg/jetbrains/kotlin/fir/types/ConeNullability;)V", "cones"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class ConeStubTypeForChainInference extends ConeStubType {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ConeStubTypeForChainInference(ConeStubTypeConstructor constructor, ConeNullability nullability) {
        super(constructor, nullability, null);
        Intrinsics.checkNotNullParameter(constructor, "constructor");
        Intrinsics.checkNotNullParameter(nullability, "nullability");
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public ConeStubTypeForChainInference(ConeTypeVariable variable, ConeNullability nullability) {
        this(new ConeStubTypeConstructor(variable, false, false, 4, null), nullability);
        Intrinsics.checkNotNullParameter(variable, "variable");
        Intrinsics.checkNotNullParameter(nullability, "nullability");
    }
}
