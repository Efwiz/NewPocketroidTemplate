package org.jetbrains.kotlin.platform.konan;

import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.platform.TargetPlatform;

/* JADX INFO: compiled from: deprecated.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u0011\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u0007¢\u0006\u0002\b\u0003¨\u0006\u0004"}, d2 = {"deprecatedIsNative", "", "Lorg/jetbrains/kotlin/platform/TargetPlatform;", "isNative", "cli-common"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class KonanPlatformKt {
    @Deprecated(level = DeprecationLevel.ERROR, message = "This class is deprecated and will be removed soon, use API from 'org.jetbrains.kotlin.platform.*' packages instead", replaceWith = @ReplaceWith(expression = "org.jetbrains.kotlin.platform.konan.NativePlatformKt.isNative", imports = {}))
    public static final boolean isNative(TargetPlatform targetPlatform) {
        Intrinsics.checkNotNullParameter(targetPlatform, "<this>");
        return NativePlatformKt.isNative(targetPlatform);
    }
}
