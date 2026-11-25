package org.jetbrains.kotlin.js.config;

import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.js.JavaScript;

/* JADX INFO: compiled from: WasmTarget.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0005\b\u0086\u0081\u0002\u0018\u0000 \u00052\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001\u0005B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004¨\u0006\u0006"}, d2 = {"Lorg/jetbrains/kotlin/js/config/WasmTarget;", "", "(Ljava/lang/String;I)V", JavaScript.NAME, "WASI", "Companion", "js.frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public enum WasmTarget {
    JS,
    WASI;

    private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);

    public static EnumEntries<WasmTarget> getEntries() {
        return $ENTRIES;
    }

    /* JADX INFO: compiled from: WasmTarget.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/js/config/WasmTarget$Companion;", "", "()V", "fromName", "Lorg/jetbrains/kotlin/js/config/WasmTarget;", "name", "", "js.frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final WasmTarget fromName(String name2) {
            Intrinsics.checkNotNullParameter(name2, "name");
            if (Intrinsics.areEqual(name2, "wasm-js")) {
                return WasmTarget.JS;
            }
            if (Intrinsics.areEqual(name2, "wasm-wasi")) {
                return WasmTarget.WASI;
            }
            return null;
        }
    }
}
