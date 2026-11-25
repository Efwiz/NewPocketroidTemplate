package org.jetbrains.kotlin.contracts.model.structure;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: Types.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\f\u0010\u0000\u001a\u00020\u0001*\u0004\u0018\u00010\u0002\u001a\n\u0010\u0003\u001a\u00020\u0002*\u00020\u0004¨\u0006\u0005"}, d2 = {"isBoolean", "", "Lorg/jetbrains/kotlin/contracts/model/structure/ESType;", "toESType", "Lorg/jetbrains/kotlin/types/KotlinType;", "resolution"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class TypesKt {
    public static final ESType toESType(KotlinType kotlinType) {
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        if (KotlinBuiltIns.isBoolean(kotlinType)) {
            return ESBooleanType.INSTANCE;
        }
        if (KotlinBuiltIns.isAny(kotlinType)) {
            return ESAnyType.INSTANCE;
        }
        if (KotlinBuiltIns.isNullableAny(kotlinType)) {
            return ESNullableAnyType.INSTANCE;
        }
        if (KotlinBuiltIns.isNothing(kotlinType)) {
            return ESNothingType.INSTANCE;
        }
        return KotlinBuiltIns.isNullableNothing(kotlinType) ? ESNullableNothingType.INSTANCE : new ESKotlinType(kotlinType);
    }

    public static final boolean isBoolean(ESType eSType) {
        return eSType instanceof ESBooleanType;
    }
}
