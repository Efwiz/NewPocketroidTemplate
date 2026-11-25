package org.jetbrains.kotlin.platform.konan;

import java.util.Collection;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.platform.SimplePlatform;
import org.jetbrains.kotlin.platform.TargetPlatform;

/* JADX INFO: compiled from: NativePlatform.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\f\u0010\u0002\u001a\u00020\u0003*\u0004\u0018\u00010\u0004\u001a\n\u0010\u0005\u001a\u00020\u0001*\u00020\u0006\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0007"}, d2 = {"legacyNativePlatformUnspecifiedTargetSerializedRepresentation", "", "isNative", "", "Lorg/jetbrains/kotlin/platform/TargetPlatform;", "legacySerializeToString", "Lorg/jetbrains/kotlin/platform/konan/NativePlatformUnspecifiedTarget;", "cli-common"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class NativePlatformKt {
    private static final String legacyNativePlatformUnspecifiedTargetSerializedRepresentation = NativePlatformUnspecifiedTarget.INSTANCE.getPlatformName() + " []";

    public static final boolean isNative(TargetPlatform targetPlatform) {
        if (targetPlatform != null && (!targetPlatform.isEmpty())) {
            TargetPlatform targetPlatform2 = targetPlatform;
            if ((targetPlatform2 instanceof Collection) && targetPlatform2.isEmpty()) {
                return true;
            }
            Iterator<SimplePlatform> it = targetPlatform2.iterator();
            while (it.hasNext()) {
                if (!(it.next() instanceof NativePlatform)) {
                }
            }
            return true;
        }
        return false;
    }

    public static final String legacySerializeToString(NativePlatformUnspecifiedTarget nativePlatformUnspecifiedTarget) {
        Intrinsics.checkNotNullParameter(nativePlatformUnspecifiedTarget, "<this>");
        return legacyNativePlatformUnspecifiedTargetSerializedRepresentation;
    }
}
