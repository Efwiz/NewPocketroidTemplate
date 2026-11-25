package org.jetbrains.kotlin.descriptors;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.builtins.UnsignedTypes;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeUtils;

/* JADX INFO: compiled from: ConstUtil.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\u001a\n\u0010\u0000\u001a\u00020\u0001*\u00020\u0002¨\u0006\u0003"}, d2 = {"canBeUsedForConstVal", "", "Lorg/jetbrains/kotlin/types/KotlinType;", "descriptors"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ConstUtilKt {
    public static final boolean canBeUsedForConstVal(KotlinType kotlinType) {
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        return ((KotlinBuiltIns.isPrimitiveType(kotlinType) || UnsignedTypes.isUnsignedType(kotlinType)) && !TypeUtils.isNullableType(kotlinType)) || KotlinBuiltIns.isString(kotlinType);
    }
}
