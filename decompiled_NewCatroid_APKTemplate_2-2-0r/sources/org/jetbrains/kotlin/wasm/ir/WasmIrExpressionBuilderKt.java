package org.jetbrains.kotlin.wasm.ir;

import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: WasmIrExpressionBuilder.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000*\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a+\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u00012\u0017\u0010\u0003\u001a\u0013\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004¢\u0006\u0002\b\u0007H\u0086\bø\u0001\u0000\u001a\f\u0010\b\u001a\u00020\t*\u00020\nH\u0002\u001a\f\u0010\u000b\u001a\u00020\t*\u00020\nH\u0002\u001a\f\u0010\f\u001a\u00020\t*\u00020\nH\u0002\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\r"}, d2 = {"buildWasmExpression", "", "Lorg/jetbrains/kotlin/wasm/ir/WasmInstr;", "body", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/wasm/ir/WasmExpressionBuilder;", "", "Lkotlin/ExtensionFunctionType;", "isInCfgNode", "", "Lorg/jetbrains/kotlin/wasm/ir/WasmOp;", "isOutCfgNode", "pureStacklessInstruction", "wasm.ir"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class WasmIrExpressionBuilderKt {

    /* JADX INFO: compiled from: WasmIrExpressionBuilder.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[WasmOp.values().length];
            try {
                iArr[WasmOp.UNREACHABLE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[WasmOp.RETURN.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[WasmOp.THROW.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[WasmOp.RETHROW.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[WasmOp.BR.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr[WasmOp.BR_TABLE.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                iArr[WasmOp.ELSE.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                iArr[WasmOp.CATCH.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                iArr[WasmOp.CATCH_ALL.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                iArr[WasmOp.REF_NULL.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                iArr[WasmOp.I32_CONST.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                iArr[WasmOp.I64_CONST.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                iArr[WasmOp.F32_CONST.ordinal()] = 13;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                iArr[WasmOp.F64_CONST.ordinal()] = 14;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                iArr[WasmOp.LOCAL_GET.ordinal()] = 15;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                iArr[WasmOp.GLOBAL_GET.ordinal()] = 16;
            } catch (NoSuchFieldError unused16) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean isOutCfgNode(WasmOp wasmOp) {
        switch (WhenMappings.$EnumSwitchMapping$0[wasmOp.ordinal()]) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
                return true;
            default:
                return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean isInCfgNode(WasmOp wasmOp) {
        int i = WhenMappings.$EnumSwitchMapping$0[wasmOp.ordinal()];
        return i == 7 || i == 8 || i == 9;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean pureStacklessInstruction(WasmOp wasmOp) {
        switch (WhenMappings.$EnumSwitchMapping$0[wasmOp.ordinal()]) {
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
                return true;
            default:
                return false;
        }
    }

    public static final List<WasmInstr> buildWasmExpression(Function1<? super WasmExpressionBuilder, Unit> body) {
        Intrinsics.checkNotNullParameter(body, "body");
        ArrayList arrayList = new ArrayList();
        body.invoke(new WasmIrExpressionBuilder(arrayList));
        return arrayList;
    }
}
