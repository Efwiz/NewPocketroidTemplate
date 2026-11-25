package org.jetbrains.kotlin.backend.wasm.lower;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.wasm.ir2wasm.ClassInfoKt;
import org.jetbrains.kotlin.backend.wasm.ir2wasm.WasmSignature;
import org.jetbrains.kotlin.ir.backend.js.JsCommonBackendContext;
import org.jetbrains.kotlin.ir.backend.js.lower.BridgesConstruction;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;

/* JADX INFO: compiled from: WasmBridgesConstruction.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0002¢\u0006\u0002\u0010\u0004J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0016J\u0010\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\fH\u0016R\u0014\u0010\u0005\u001a\u00020\u0006X\u0096D¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/backend/wasm/lower/WasmBridgesConstruction;", "Lorg/jetbrains/kotlin/ir/backend/js/lower/BridgesConstruction;", "Lorg/jetbrains/kotlin/ir/backend/js/JsCommonBackendContext;", "context", "(Lorg/jetbrains/kotlin/ir/backend/js/JsCommonBackendContext;)V", "shouldCastDispatchReceiver", "", "getShouldCastDispatchReceiver", "()Z", "getBridgeOrigin", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;", "bridge", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "getFunctionSignature", "Lorg/jetbrains/kotlin/backend/wasm/ir2wasm/WasmSignature;", "function", "backend.wasm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class WasmBridgesConstruction extends BridgesConstruction<JsCommonBackendContext> {
    private final boolean shouldCastDispatchReceiver;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public WasmBridgesConstruction(JsCommonBackendContext context) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
        this.shouldCastDispatchReceiver = true;
    }

    @Override // org.jetbrains.kotlin.ir.backend.js.lower.BridgesConstruction
    public WasmSignature getFunctionSignature(IrSimpleFunction function) {
        Intrinsics.checkNotNullParameter(function, "function");
        return ClassInfoKt.wasmSignature(function, getContext().getIrBuiltIns());
    }

    @Override // org.jetbrains.kotlin.ir.backend.js.lower.BridgesConstruction
    public boolean getShouldCastDispatchReceiver() {
        return this.shouldCastDispatchReceiver;
    }

    @Override // org.jetbrains.kotlin.ir.backend.js.lower.BridgesConstruction
    public IrDeclarationOrigin getBridgeOrigin(IrSimpleFunction bridge) {
        Intrinsics.checkNotNullParameter(bridge, "bridge");
        return IrDeclarationOrigin.BRIDGE.INSTANCE;
    }
}
