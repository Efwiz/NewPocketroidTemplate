package org.jetbrains.kotlin.contracts.model.structure;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.SimpleType;

/* JADX INFO: compiled from: Types.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/contracts/model/structure/ESAnyType;", "Lorg/jetbrains/kotlin/contracts/model/structure/ESBuiltInType;", "()V", "toKotlinType", "Lorg/jetbrains/kotlin/types/KotlinType;", "builtIns", "Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ESAnyType extends ESBuiltInType {
    public static final ESAnyType INSTANCE = new ESAnyType();

    private ESAnyType() {
    }

    @Override // org.jetbrains.kotlin.contracts.model.structure.ESType
    public KotlinType toKotlinType(KotlinBuiltIns builtIns) {
        Intrinsics.checkNotNullParameter(builtIns, "builtIns");
        SimpleType anyType = builtIns.getAnyType();
        Intrinsics.checkNotNullExpressionValue(anyType, "getAnyType(...)");
        return anyType;
    }
}
