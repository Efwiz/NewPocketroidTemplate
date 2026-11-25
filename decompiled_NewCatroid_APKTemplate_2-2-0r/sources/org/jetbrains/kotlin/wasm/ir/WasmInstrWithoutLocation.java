package org.jetbrains.kotlin.wasm.ir;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.wasm.ir.source.location.SourceLocation;

/* JADX INFO: compiled from: Declarations.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\u0002\u0010\u0007R\u0016\u0010\b\u001a\u0004\u0018\u00010\t8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000b¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/wasm/ir/WasmInstrWithoutLocation;", "Lorg/jetbrains/kotlin/wasm/ir/WasmInstr;", "operator", "Lorg/jetbrains/kotlin/wasm/ir/WasmOp;", "immediates", "", "Lorg/jetbrains/kotlin/wasm/ir/WasmImmediate;", "(Lorg/jetbrains/kotlin/wasm/ir/WasmOp;Ljava/util/List;)V", FirebaseAnalytics.Param.LOCATION, "Lorg/jetbrains/kotlin/wasm/ir/source/location/SourceLocation;", "getLocation", "()Lorg/jetbrains/kotlin/wasm/ir/source/location/SourceLocation;", "wasm.ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class WasmInstrWithoutLocation extends WasmInstr {
    @Override // org.jetbrains.kotlin.wasm.ir.WasmInstr
    public SourceLocation getLocation() {
        return null;
    }

    public /* synthetic */ WasmInstrWithoutLocation(WasmOp wasmOp, List list, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(wasmOp, (i & 2) != 0 ? CollectionsKt.emptyList() : list);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public WasmInstrWithoutLocation(WasmOp operator, List<? extends WasmImmediate> immediates) {
        super(operator, immediates, null);
        Intrinsics.checkNotNullParameter(operator, "operator");
        Intrinsics.checkNotNullParameter(immediates, "immediates");
    }
}
