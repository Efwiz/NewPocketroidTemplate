package org.jetbrains.kotlin.platform;

import kotlin.Metadata;
import kotlin.collections.CollectionsKt;

/* JADX INFO: compiled from: WasmPlatform.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\u001a\f\u0010\u0000\u001a\u00020\u0001*\u0004\u0018\u00010\u0002¨\u0006\u0003"}, d2 = {"isWasm", "", "Lorg/jetbrains/kotlin/platform/TargetPlatform;", "compiler.common"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class WasmPlatformKt {
    public static final boolean isWasm(TargetPlatform targetPlatform) {
        return (targetPlatform != null ? (SimplePlatform) CollectionsKt.singleOrNull(targetPlatform) : null) instanceof WasmPlatform;
    }
}
