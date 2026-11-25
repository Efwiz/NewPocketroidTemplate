package org.jetbrains.kotlin.platform.wasm;

import kotlin.Metadata;
import kotlin.collections.SetsKt;
import org.jetbrains.kotlin.platform.TargetPlatform;
import org.jetbrains.kotlin.platform.WasmPlatform;

/* JADX INFO: compiled from: WasmPlatform.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001:\u0001\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0004"}, d2 = {"Lorg/jetbrains/kotlin/platform/wasm/WasmPlatforms;", "", "()V", "Default", "wasm.config"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class WasmPlatforms {
    public static final WasmPlatforms INSTANCE = new WasmPlatforms();

    /* JADX INFO: compiled from: WasmPlatform.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/platform/wasm/WasmPlatforms$Default;", "Lorg/jetbrains/kotlin/platform/TargetPlatform;", "()V", "wasm.config"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Default extends TargetPlatform {
        public static final Default INSTANCE = new Default();

        private Default() {
            super(SetsKt.setOf(WasmPlatform.INSTANCE));
        }
    }

    private WasmPlatforms() {
    }
}
