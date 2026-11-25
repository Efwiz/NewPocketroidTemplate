package org.jetbrains.kotlin.resolve;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.types.DefinitelyNotNullType;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.KotlinTypeKt;
import org.jetbrains.kotlin.types.UnwrappedType;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;

/* JADX INFO: compiled from: DefinitelyNonNullableTypes.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\u001a\n\u0010\u0000\u001a\u00020\u0001*\u00020\u0002¨\u0006\u0003"}, d2 = {"containsIncorrectExplicitDefinitelyNonNullableType", "", "Lorg/jetbrains/kotlin/types/KotlinType;", "frontend"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class DefinitelyNonNullableTypesKt {
    public static final boolean containsIncorrectExplicitDefinitelyNonNullableType(KotlinType kotlinType) {
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        return TypeUtilsKt.contains(kotlinType, new Function1<UnwrappedType, Boolean>() { // from class: org.jetbrains.kotlin.resolve.DefinitelyNonNullableTypesKt.containsIncorrectExplicitDefinitelyNonNullableType.1
            /* JADX WARN: Code duplicated, block: B:9:0x0025  */
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(UnwrappedType it) {
                boolean z;
                Intrinsics.checkNotNullParameter(it, "it");
                if (it instanceof DefinitelyNotNullType) {
                    DefinitelyNotNullType definitelyNotNullType = (DefinitelyNotNullType) it;
                    if (!TypeUtilsKt.isTypeParameter(definitelyNotNullType.getOriginal()) || KotlinTypeKt.isNullable(definitelyNotNullType.getOriginal())) {
                        z = false;
                    } else {
                        z = true;
                    }
                } else {
                    z = false;
                }
                return Boolean.valueOf(z);
            }
        });
    }
}
