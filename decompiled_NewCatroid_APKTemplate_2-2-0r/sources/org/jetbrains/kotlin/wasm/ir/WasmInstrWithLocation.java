package org.jetbrains.kotlin.wasm.ir;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.wasm.ir.source.location.SourceLocation;

/* JADX INFO: compiled from: Declarations.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0017\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006B#\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\nR\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/wasm/ir/WasmInstrWithLocation;", "Lorg/jetbrains/kotlin/wasm/ir/WasmInstr;", "operator", "Lorg/jetbrains/kotlin/wasm/ir/WasmOp;", FirebaseAnalytics.Param.LOCATION, "Lorg/jetbrains/kotlin/wasm/ir/source/location/SourceLocation;", "(Lorg/jetbrains/kotlin/wasm/ir/WasmOp;Lorg/jetbrains/kotlin/wasm/ir/source/location/SourceLocation;)V", "immediates", "", "Lorg/jetbrains/kotlin/wasm/ir/WasmImmediate;", "(Lorg/jetbrains/kotlin/wasm/ir/WasmOp;Ljava/util/List;Lorg/jetbrains/kotlin/wasm/ir/source/location/SourceLocation;)V", "getLocation", "()Lorg/jetbrains/kotlin/wasm/ir/source/location/SourceLocation;", "wasm.ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class WasmInstrWithLocation extends WasmInstr {
    private final SourceLocation location;

    @Override // org.jetbrains.kotlin.wasm.ir.WasmInstr
    public SourceLocation getLocation() {
        return this.location;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public WasmInstrWithLocation(WasmOp operator, List<? extends WasmImmediate> immediates, SourceLocation location) {
        super(operator, immediates, null);
        Intrinsics.checkNotNullParameter(operator, "operator");
        Intrinsics.checkNotNullParameter(immediates, "immediates");
        Intrinsics.checkNotNullParameter(location, "location");
        this.location = location;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public WasmInstrWithLocation(WasmOp operator, SourceLocation location) {
        this(operator, CollectionsKt.emptyList(), location);
        Intrinsics.checkNotNullParameter(operator, "operator");
        Intrinsics.checkNotNullParameter(location, "location");
    }
}
