package org.jetbrains.kotlin.platform.jvm;

import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import org.jetbrains.kotlin.platform.SimplePlatform;
import org.jetbrains.kotlin.platform.TargetPlatform;

/* JADX INFO: compiled from: JvmPlatform.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\u001a\f\u0010\u0000\u001a\u00020\u0001*\u0004\u0018\u00010\u0002¨\u0006\u0003"}, d2 = {"isJvm", "", "Lorg/jetbrains/kotlin/platform/TargetPlatform;", "config.jvm"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class JvmPlatformKt {
    public static final boolean isJvm(TargetPlatform targetPlatform) {
        return (targetPlatform != null ? (SimplePlatform) CollectionsKt.singleOrNull(targetPlatform) : null) instanceof JvmPlatform;
    }
}
