package org.jetbrains.kotlin.backend.wasm.utils;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.wasm.WasmSymbols;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.ir.backend.js.JsCommonInlineClassesUtils;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.types.IrSimpleType;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypeUtilsKt;

/* JADX INFO: compiled from: WasmInlineClassesUtils.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0012\u0010\u000b\u001a\u0004\u0018\u00010\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0010\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\fH\u0016R\u0014\u0010\u0005\u001a\u00020\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\t\u001a\u00020\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\bR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/backend/wasm/utils/WasmInlineClassesUtils;", "Lorg/jetbrains/kotlin/ir/backend/js/JsCommonInlineClassesUtils;", "wasmSymbols", "Lorg/jetbrains/kotlin/backend/wasm/WasmSymbols;", "(Lorg/jetbrains/kotlin/backend/wasm/WasmSymbols;)V", "boxIntrinsic", "Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "getBoxIntrinsic", "()Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "unboxIntrinsic", "getUnboxIntrinsic", "getInlinedClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/ir/types/IrType;", "isClassInlineLike", "", "klass", "backend.wasm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class WasmInlineClassesUtils implements JsCommonInlineClassesUtils {
    private final WasmSymbols wasmSymbols;

    public WasmInlineClassesUtils(WasmSymbols wasmSymbols) {
        Intrinsics.checkNotNullParameter(wasmSymbols, "wasmSymbols");
        this.wasmSymbols = wasmSymbols;
    }

    @Override // org.jetbrains.kotlin.ir.backend.js.JsCommonInlineClassesUtils
    public IrClass getInlinedClass(IrType type) {
        IrClass irClassErase;
        Intrinsics.checkNotNullParameter(type, "type");
        if (!(type instanceof IrSimpleType) || IrTypeUtilsKt.isNullable(type) || (irClassErase = org.jetbrains.kotlin.ir.backend.js.utils.IrTypeUtilsKt.erase(type)) == null || !isClassInlineLike(irClassErase)) {
            return null;
        }
        return irClassErase;
    }

    @Override // org.jetbrains.kotlin.backend.common.InlineClassesUtils
    public boolean isClassInlineLike(IrClass klass) {
        Intrinsics.checkNotNullParameter(klass, "klass");
        return super.isClassInlineLike(klass) || AnnotationsKt.hasWasmAutoboxedAnnotation(klass);
    }

    @Override // org.jetbrains.kotlin.ir.backend.js.JsCommonInlineClassesUtils
    public IrSimpleFunctionSymbol getBoxIntrinsic() {
        return this.wasmSymbols.getBoxIntrinsic();
    }

    @Override // org.jetbrains.kotlin.ir.backend.js.JsCommonInlineClassesUtils
    public IrSimpleFunctionSymbol getUnboxIntrinsic() {
        return this.wasmSymbols.getUnboxIntrinsic();
    }
}
