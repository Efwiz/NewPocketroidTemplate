package org.jetbrains.kotlin.backend.wasm.ir2wasm;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.IrFileEntry;
import org.jetbrains.kotlin.wasm.ir.WasmExpressionBuilder;
import org.jetbrains.kotlin.wasm.ir.source.location.SourceLocation;

/* JADX INFO: compiled from: locationUtils.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\n\u0010\u0000\u001a\u00020\u0001*\u00020\u0002\u001a\n\u0010\u0003\u001a\u00020\u0001*\u00020\u0002\u001a\u0014\u0010\u0004\u001a\u00020\u0005*\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\b¨\u0006\t"}, d2 = {"buildUnreachableAfterNothingType", "", "Lorg/jetbrains/kotlin/wasm/ir/WasmExpressionBuilder;", "buildUnreachableForVerifier", "getSourceLocation", "Lorg/jetbrains/kotlin/wasm/ir/source/location/SourceLocation;", "Lorg/jetbrains/kotlin/ir/IrElement;", "fileEntry", "Lorg/jetbrains/kotlin/ir/IrFileEntry;", "backend.wasm"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class LocationUtilsKt {
    public static final SourceLocation getSourceLocation(IrElement irElement, IrFileEntry irFileEntry) {
        Intrinsics.checkNotNullParameter(irElement, "<this>");
        if (irFileEntry == null) {
            return SourceLocation.INSTANCE.NoLocation("fileEntry is null");
        }
        String name2 = irFileEntry.getName();
        int lineNumber = irFileEntry.getLineNumber(irElement.getStartOffset());
        int columnNumber = irFileEntry.getColumnNumber(irElement.getStartOffset());
        if (lineNumber < 0 || columnNumber < 0) {
            return SourceLocation.INSTANCE.NoLocation("startLine or startColumn < 0");
        }
        return new SourceLocation.Location(name2, lineNumber, columnNumber);
    }

    public static final void buildUnreachableForVerifier(WasmExpressionBuilder wasmExpressionBuilder) {
        Intrinsics.checkNotNullParameter(wasmExpressionBuilder, "<this>");
        wasmExpressionBuilder.buildUnreachable(SourceLocation.INSTANCE.NoLocation("This instruction should never be reached, but required for wasm verifier"));
    }

    public static final void buildUnreachableAfterNothingType(WasmExpressionBuilder wasmExpressionBuilder) {
        Intrinsics.checkNotNullParameter(wasmExpressionBuilder, "<this>");
        wasmExpressionBuilder.buildUnreachable(SourceLocation.INSTANCE.NoLocation("The unreachable instruction after an expression with Nothing type to make sure that execution doesn't come here (or it fails fast if so). It also might be required for wasm verifier."));
    }
}
