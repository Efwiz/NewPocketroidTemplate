package org.jetbrains.kotlin.backend.wasm.ir2wasm;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.wasm.WasmSymbols;
import org.jetbrains.kotlin.ir.IrBuiltIns;
import org.jetbrains.kotlin.ir.backend.js.utils.TypeTranformerKt;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.wasm.ir.WasmAnyRef;
import org.jetbrains.kotlin.wasm.ir.WasmEqRef;
import org.jetbrains.kotlin.wasm.ir.WasmExternRef;
import org.jetbrains.kotlin.wasm.ir.WasmF32;
import org.jetbrains.kotlin.wasm.ir.WasmF64;
import org.jetbrains.kotlin.wasm.ir.WasmHeapType;
import org.jetbrains.kotlin.wasm.ir.WasmI16;
import org.jetbrains.kotlin.wasm.ir.WasmI31Ref;
import org.jetbrains.kotlin.wasm.ir.WasmI32;
import org.jetbrains.kotlin.wasm.ir.WasmI64;
import org.jetbrains.kotlin.wasm.ir.WasmI8;
import org.jetbrains.kotlin.wasm.ir.WasmRefNullType;
import org.jetbrains.kotlin.wasm.ir.WasmRefNullrefType;
import org.jetbrains.kotlin.wasm.ir.WasmType;
import org.jetbrains.kotlin.wasm.ir.WasmUnreachableType;

/* JADX INFO: compiled from: TypeTransformer.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\n\u0010\u000f\u001a\u00020\u0010*\u00020\u0011J\f\u0010\u0012\u001a\u0004\u0018\u00010\u0010*\u00020\u0011J\n\u0010\u0013\u001a\u00020\u0010*\u00020\u0011J\f\u0010\u0014\u001a\u00020\u0010*\u00020\u0011H\u0002J\f\u0010\u0015\u001a\u0004\u0018\u00010\u0010*\u00020\u0011J\n\u0010\u0016\u001a\u00020\u0010*\u00020\u0011R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u000b\u001a\u00020\f¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/backend/wasm/ir2wasm/WasmTypeTransformer;", "", "context", "Lorg/jetbrains/kotlin/backend/wasm/ir2wasm/WasmModuleCodegenContext;", "builtIns", "Lorg/jetbrains/kotlin/ir/IrBuiltIns;", "(Lorg/jetbrains/kotlin/backend/wasm/ir2wasm/WasmModuleCodegenContext;Lorg/jetbrains/kotlin/ir/IrBuiltIns;)V", "getBuiltIns", "()Lorg/jetbrains/kotlin/ir/IrBuiltIns;", "getContext", "()Lorg/jetbrains/kotlin/backend/wasm/ir2wasm/WasmModuleCodegenContext;", "symbols", "Lorg/jetbrains/kotlin/backend/wasm/WasmSymbols;", "getSymbols", "()Lorg/jetbrains/kotlin/backend/wasm/WasmSymbols;", "toBoxedInlineClassType", "Lorg/jetbrains/kotlin/wasm/ir/WasmType;", "Lorg/jetbrains/kotlin/ir/types/IrType;", "toWasmBlockResultType", "toWasmFieldType", "toWasmGcRefType", "toWasmResultType", "toWasmValueType", "backend.wasm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class WasmTypeTransformer {
    private final IrBuiltIns builtIns;
    private final WasmModuleCodegenContext context;
    private final WasmSymbols symbols;

    public WasmTypeTransformer(WasmModuleCodegenContext context, IrBuiltIns builtIns) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(builtIns, "builtIns");
        this.context = context;
        this.builtIns = builtIns;
        this.symbols = context.getBackendContext().getWasmSymbols();
    }

    public final WasmModuleCodegenContext getContext() {
        return this.context;
    }

    public final IrBuiltIns getBuiltIns() {
        return this.builtIns;
    }

    public final WasmSymbols getSymbols() {
        return this.symbols;
    }

    public final WasmType toWasmResultType(IrType irType) {
        Intrinsics.checkNotNullParameter(irType, "<this>");
        if (Intrinsics.areEqual(irType, this.builtIns.getUnitType()) ? true : Intrinsics.areEqual(irType, this.builtIns.getNothingType())) {
            return null;
        }
        return toWasmValueType(irType);
    }

    public final WasmType toWasmBlockResultType(IrType irType) {
        Intrinsics.checkNotNullParameter(irType, "<this>");
        if (Intrinsics.areEqual(irType, this.builtIns.getNothingType())) {
            return WasmUnreachableType.INSTANCE;
        }
        if (Intrinsics.areEqual(irType, this.symbols.getVoidType())) {
            return null;
        }
        return toWasmValueType(irType);
    }

    private final WasmType toWasmGcRefType(IrType irType) {
        WasmModuleCodegenContext wasmModuleCodegenContext = this.context;
        return new WasmRefNullType(new WasmHeapType.Type(wasmModuleCodegenContext.referenceGcType(TypeTransformerKt.getRuntimeClass(irType, wasmModuleCodegenContext.getBackendContext().getIrBuiltIns()).getSymbol())));
    }

    public final WasmType toBoxedInlineClassType(IrType irType) {
        Intrinsics.checkNotNullParameter(irType, "<this>");
        return toWasmGcRefType(irType);
    }

    public final WasmType toWasmFieldType(IrType irType) {
        Intrinsics.checkNotNullParameter(irType, "<this>");
        if (Intrinsics.areEqual(irType, this.builtIns.getBooleanType()) ? true : Intrinsics.areEqual(irType, this.builtIns.getByteType())) {
            return WasmI8.INSTANCE;
        }
        if (Intrinsics.areEqual(irType, this.builtIns.getShortType()) ? true : Intrinsics.areEqual(irType, this.builtIns.getCharType())) {
            return WasmI16.INSTANCE;
        }
        return toWasmValueType(irType);
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    public final WasmType toWasmValueType(IrType irType) {
        Intrinsics.checkNotNullParameter(irType, "<this>");
        if (Intrinsics.areEqual(irType, this.builtIns.getBooleanType()) ? true : Intrinsics.areEqual(irType, this.builtIns.getByteType()) ? true : Intrinsics.areEqual(irType, this.builtIns.getShortType()) ? true : Intrinsics.areEqual(irType, this.builtIns.getIntType()) ? true : Intrinsics.areEqual(irType, this.builtIns.getCharType())) {
            return WasmI32.INSTANCE;
        }
        if (Intrinsics.areEqual(irType, this.builtIns.getLongType())) {
            return WasmI64.INSTANCE;
        }
        if (Intrinsics.areEqual(irType, this.builtIns.getFloatType())) {
            return WasmF32.INSTANCE;
        }
        if (Intrinsics.areEqual(irType, this.builtIns.getDoubleType())) {
            return WasmF64.INSTANCE;
        }
        if (Intrinsics.areEqual(irType, this.builtIns.getNothingNType())) {
            return WasmRefNullrefType.INSTANCE;
        }
        if (Intrinsics.areEqual(irType, this.builtIns.getNothingType())) {
            return WasmAnyRef.INSTANCE;
        }
        if (Intrinsics.areEqual(irType, this.symbols.getVoidType())) {
            throw new IllegalStateException("Void type can't be used as a value".toString());
        }
        IrClass erasedUpperBound = TypeTranformerKt.getErasedUpperBound(irType);
        if (erasedUpperBound == null) {
            erasedUpperBound = this.builtIns.getAnyClass().getOwner();
        }
        IrClass inlinedClass = this.context.getBackendContext().getInlineClassesUtils().getInlinedClass(irType);
        if (erasedUpperBound.getIsExternal()) {
            return WasmExternRef.INSTANCE;
        }
        if (!TypeTransformerKt.isBuiltInWasmRefType(irType)) {
            return inlinedClass != null ? toWasmValueType(this.context.getBackendContext().getInlineClassesUtils().getInlineClassUnderlyingType(inlinedClass)) : toWasmGcRefType(irType);
        }
        String identifier = erasedUpperBound.getName().getIdentifier();
        Intrinsics.checkNotNullExpressionValue(identifier, "getIdentifier(...)");
        switch (identifier.hashCode()) {
            case -1412634841:
                if (identifier.equals("anyref")) {
                    return WasmAnyRef.INSTANCE;
                }
                break;
            case -1240234324:
                if (identifier.equals("i31ref")) {
                    return WasmI31Ref.INSTANCE;
                }
                break;
            case -509644977:
                if (identifier.equals("funcref")) {
                    return new WasmRefNullType(WasmHeapType.Simple.Func.INSTANCE);
                }
                break;
            case 96754791:
                if (identifier.equals("eqref")) {
                    return WasmEqRef.INSTANCE;
                }
                break;
            case 144515230:
                if (identifier.equals("structref")) {
                    return new WasmRefNullType(WasmHeapType.Simple.Struct.INSTANCE);
                }
                break;
        }
        throw new IllegalStateException(("Unknown reference type " + identifier).toString());
    }
}
