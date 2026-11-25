package org.jetbrains.kotlin.cli.common.arguments;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: Freezable.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\f\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u001a\u0019\u0010\u0000\u001a\u0002H\u0001\"\b\b\u0000\u0010\u0001*\u00020\u0002*\u0002H\u0001¢\u0006\u0002\u0010\u0003\u001a\u0019\u0010\u0004\u001a\u0002H\u0001\"\b\b\u0000\u0010\u0001*\u00020\u0002*\u0002H\u0001¢\u0006\u0002\u0010\u0003\u001a\u0019\u0010\u0005\u001a\u0002H\u0001\"\b\b\u0000\u0010\u0001*\u00020\u0002*\u0002H\u0001¢\u0006\u0002\u0010\u0003¨\u0006\u0006"}, d2 = {"copyOf", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/cli/common/arguments/Freezable;", "(Lorg/jetbrains/kotlin/cli/common/arguments/Freezable;)Lorg/jetbrains/kotlin/cli/common/arguments/Freezable;", "frozen", "unfrozen", "cli-common"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FreezableKt {
    public static final <T extends Freezable> T copyOf(T t) {
        Intrinsics.checkNotNullParameter(t, "<this>");
        T t2 = (T) t.copyOfInternal$cli_common();
        Intrinsics.checkNotNull(t2, "null cannot be cast to non-null type T of org.jetbrains.kotlin.cli.common.arguments.FreezableKt.copyOf");
        return t2;
    }

    public static final <T extends Freezable> T frozen(T t) {
        Intrinsics.checkNotNullParameter(t, "<this>");
        T t2 = (T) t.getInstanceWithFreezeStatus$cli_common(true);
        Intrinsics.checkNotNull(t2, "null cannot be cast to non-null type T of org.jetbrains.kotlin.cli.common.arguments.FreezableKt.frozen");
        return t2;
    }

    public static final <T extends Freezable> T unfrozen(T t) {
        Intrinsics.checkNotNullParameter(t, "<this>");
        T t2 = (T) t.getInstanceWithFreezeStatus$cli_common(false);
        Intrinsics.checkNotNull(t2, "null cannot be cast to non-null type T of org.jetbrains.kotlin.cli.common.arguments.FreezableKt.unfrozen");
        return t2;
    }
}
