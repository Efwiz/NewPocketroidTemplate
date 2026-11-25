package org.jetbrains.kotlin.types;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;

/* JADX INFO: compiled from: dynamicTypes.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\u001a\u000e\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003\u001a\n\u0010\u0004\u001a\u00020\u0005*\u00020\u0006¨\u0006\u0007"}, d2 = {"createDynamicType", "Lorg/jetbrains/kotlin/types/DynamicType;", "builtIns", "Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;", "isDynamic", "", "Lorg/jetbrains/kotlin/types/KotlinType;", "descriptors"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class DynamicTypesKt {
    public static final boolean isDynamic(KotlinType kotlinType) {
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        return kotlinType.unwrap() instanceof DynamicType;
    }

    public static final DynamicType createDynamicType(KotlinBuiltIns builtIns) {
        Intrinsics.checkNotNullParameter(builtIns, "builtIns");
        return new DynamicType(builtIns, TypeAttributes.INSTANCE.getEmpty());
    }
}
