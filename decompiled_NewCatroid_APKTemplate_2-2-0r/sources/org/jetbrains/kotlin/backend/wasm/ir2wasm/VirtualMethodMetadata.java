package org.jetbrains.kotlin.backend.wasm.ir2wasm;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;

/* JADX INFO: compiled from: ClassInfo.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/backend/wasm/ir2wasm/VirtualMethodMetadata;", "", "function", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "signature", "Lorg/jetbrains/kotlin/backend/wasm/ir2wasm/WasmSignature;", "(Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;Lorg/jetbrains/kotlin/backend/wasm/ir2wasm/WasmSignature;)V", "getFunction", "()Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "getSignature", "()Lorg/jetbrains/kotlin/backend/wasm/ir2wasm/WasmSignature;", "backend.wasm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class VirtualMethodMetadata {
    private final IrSimpleFunction function;
    private final WasmSignature signature;

    public VirtualMethodMetadata(IrSimpleFunction function, WasmSignature signature) {
        Intrinsics.checkNotNullParameter(function, "function");
        Intrinsics.checkNotNullParameter(signature, "signature");
        this.function = function;
        this.signature = signature;
    }

    public final IrSimpleFunction getFunction() {
        return this.function;
    }

    public final WasmSignature getSignature() {
        return this.signature;
    }
}
