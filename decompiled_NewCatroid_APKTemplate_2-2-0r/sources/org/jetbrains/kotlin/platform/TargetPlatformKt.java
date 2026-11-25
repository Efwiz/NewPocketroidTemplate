package org.jetbrains.kotlin.platform;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: TargetPlatform.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\f\u0010\u0000\u001a\u00020\u0001*\u0004\u0018\u00010\u0002\u001a\f\u0010\u0003\u001a\u00020\u0001*\u0004\u0018\u00010\u0002\u001a\n\u0010\u0004\u001a\u00020\u0005*\u00020\u0006\u001a\n\u0010\u0007\u001a\u00020\u0002*\u00020\u0006¨\u0006\b"}, d2 = {"isCommon", "", "Lorg/jetbrains/kotlin/platform/TargetPlatform;", "isMultiPlatform", "serializeToString", "", "Lorg/jetbrains/kotlin/platform/SimplePlatform;", "toTargetPlatform", "compiler.common"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class TargetPlatformKt {
    public static final boolean isMultiPlatform(TargetPlatform targetPlatform) {
        return targetPlatform != null && targetPlatform.size() > 1;
    }

    public static final boolean isCommon(TargetPlatform targetPlatform) {
        if (isMultiPlatform(targetPlatform)) {
            Intrinsics.checkNotNull(targetPlatform);
            Iterator<SimplePlatform> it = targetPlatform.iterator();
            String platformName = it.next().getPlatformName();
            while (it.hasNext()) {
                if (!Intrinsics.areEqual(it.next().getPlatformName(), platformName)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static final TargetPlatform toTargetPlatform(SimplePlatform simplePlatform) {
        Intrinsics.checkNotNullParameter(simplePlatform, "<this>");
        return new TargetPlatform(SetsKt.setOf(simplePlatform));
    }

    public static final String serializeToString(SimplePlatform simplePlatform) {
        Intrinsics.checkNotNullParameter(simplePlatform, "<this>");
        return simplePlatform.getPlatformName() + " [" + simplePlatform.getTargetName() + ']';
    }
}
