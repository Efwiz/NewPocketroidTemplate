package org.jetbrains.kotlin.wasm.ir;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.kotlin.wasm.ir.source.location.SourceLocation;

/* JADX INFO: compiled from: Declarations.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001B\u001f\b\u0004\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\u0002\u0010\u0007R\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0014\u0010\n\u001a\u0004\u0018\u00010\u000bX¦\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f\u0082\u0001\u0002\u0010\u0011¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/wasm/ir/WasmInstr;", "", "operator", "Lorg/jetbrains/kotlin/wasm/ir/WasmOp;", "immediates", "", "Lorg/jetbrains/kotlin/wasm/ir/WasmImmediate;", "(Lorg/jetbrains/kotlin/wasm/ir/WasmOp;Ljava/util/List;)V", "getImmediates", "()Ljava/util/List;", FirebaseAnalytics.Param.LOCATION, "Lorg/jetbrains/kotlin/wasm/ir/source/location/SourceLocation;", "getLocation", "()Lorg/jetbrains/kotlin/wasm/ir/source/location/SourceLocation;", "getOperator", "()Lorg/jetbrains/kotlin/wasm/ir/WasmOp;", "Lorg/jetbrains/kotlin/wasm/ir/WasmInstrWithLocation;", "Lorg/jetbrains/kotlin/wasm/ir/WasmInstrWithoutLocation;", "wasm.ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class WasmInstr {
    private final List<WasmImmediate> immediates;
    private final WasmOp operator;

    public /* synthetic */ WasmInstr(WasmOp wasmOp, List list, DefaultConstructorMarker defaultConstructorMarker) {
        this(wasmOp, list);
    }

    public abstract SourceLocation getLocation();

    /* JADX WARN: Multi-variable type inference failed */
    private WasmInstr(WasmOp wasmOp, List<? extends WasmImmediate> list) {
        this.operator = wasmOp;
        this.immediates = list;
    }

    public final WasmOp getOperator() {
        return this.operator;
    }

    public /* synthetic */ WasmInstr(WasmOp wasmOp, List list, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(wasmOp, (i & 2) != 0 ? CollectionsKt.emptyList() : list, null);
    }

    public final List<WasmImmediate> getImmediates() {
        return this.immediates;
    }
}
