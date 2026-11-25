package org.jetbrains.kotlin.resolve.calls.smartcasts;

import kotlin.Metadata;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeUtils;

/* JADX INFO: compiled from: DataFlowValue.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\"\u0018\u0010\u0000\u001a\u00020\u0001*\u00020\u00028BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004¨\u0006\u0005"}, d2 = {"immanentNullability", "Lorg/jetbrains/kotlin/resolve/calls/smartcasts/Nullability;", "Lorg/jetbrains/kotlin/types/KotlinType;", "getImmanentNullability", "(Lorg/jetbrains/kotlin/types/KotlinType;)Lorg/jetbrains/kotlin/resolve/calls/smartcasts/Nullability;", "frontend"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class DataFlowValueKt {
    /* JADX INFO: Access modifiers changed from: private */
    public static final Nullability getImmanentNullability(KotlinType kotlinType) {
        return TypeUtils.isNullableType(kotlinType) ? Nullability.UNKNOWN : Nullability.NOT_NULL;
    }
}
