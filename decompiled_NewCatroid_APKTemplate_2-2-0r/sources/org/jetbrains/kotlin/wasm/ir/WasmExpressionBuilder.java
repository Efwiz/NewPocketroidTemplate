package org.jetbrains.kotlin.wasm.ir;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.Arrays;
import kotlin.Metadata;
import kotlin.UByte;
import kotlin.UInt;
import kotlin.ULong;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.wasm.ir.source.location.SourceLocation;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: WasmExpressionBuilder.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000¨\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0011\n\u0002\u0018\u0002\n\u0002\b\u0002\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J6\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\f2\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u000e2\u0012\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\n0\u0010H\u0086\bø\u0001\u0000J\u0012\u0010\t\u001a\u00020\u00042\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u000eJ\u0016\u0010\u0011\u001a\u00020\n2\u0006\u0010\u0012\u001a\u00020\u00042\u0006\u0010\u0013\u001a\u00020\u0014J\u0016\u0010\u0015\u001a\u00020\n2\u0006\u0010\u0012\u001a\u00020\u00042\u0006\u0010\u0013\u001a\u00020\u0014J\u001e\u0010\u0016\u001a\u00020\n2\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0012\u001a\u00020\u00042\u0006\u0010\u0013\u001a\u00020\u0014J>\u0010\u0019\u001a\u00020\n2\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0012\u001a\u00020\u00042\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001b2\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020\u001e2\u0006\u0010\u0013\u001a\u00020\u0014J\u001c\u0010 \u001a\u00020\n2\f\u0010!\u001a\b\u0012\u0004\u0012\u00020#0\"2\u0006\u0010\u0013\u001a\u00020\u0014J,\u0010$\u001a\u00020\n2\f\u0010!\u001a\b\u0012\u0004\u0012\u00020%0\"2\u000e\b\u0002\u0010&\u001a\b\u0012\u0004\u0012\u00020\u00040'2\u0006\u0010\u0013\u001a\u00020\u0014J\u000e\u0010(\u001a\u00020\n2\u0006\u0010)\u001a\u00020\u0004J\u0016\u0010*\u001a\u00020\n2\u0006\u0010+\u001a\u00020,2\u0006\u0010\u0013\u001a\u00020\u0014J\u0016\u0010-\u001a\u00020\n2\u0006\u0010+\u001a\u00020.2\u0006\u0010\u0013\u001a\u00020\u0014J\u0016\u0010/\u001a\u00020\n2\u0006\u0010+\u001a\u00020\u00042\u0006\u0010\u0013\u001a\u00020\u0014J\u001c\u00100\u001a\u00020\n2\f\u0010+\u001a\b\u0012\u0004\u0012\u00020\u00040\"2\u0006\u0010\u0013\u001a\u00020\u0014J\u0016\u00101\u001a\u00020\n2\u0006\u0010+\u001a\u0002022\u0006\u0010\u0013\u001a\u00020\u0014J\u000e\u00103\u001a\u00020\n2\u0006\u0010\u0013\u001a\u00020\u0014J\u0006\u00104\u001a\u00020\nJ\u0006\u00105\u001a\u00020\nJ\u001c\u00106\u001a\u00020\n2\f\u00107\u001a\b\u0012\u0004\u0012\u0002080\"2\u0006\u0010\u0013\u001a\u00020\u0014J\u0016\u00109\u001a\u00020\n2\u0006\u0010:\u001a\u00020;2\u0006\u0010\u0013\u001a\u00020\u0014J\u001c\u0010<\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\f2\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u000eJ1\u0010=\u001a\u00020\n2\u0006\u0010>\u001a\u00020\u00182\u0006\u0010\u0013\u001a\u00020\u00142\u0012\u0010?\u001a\n\u0012\u0006\b\u0001\u0012\u00020A0@\"\u00020AH&¢\u0006\u0002\u0010BJ)\u0010C\u001a\u00020\n2\u0006\u0010>\u001a\u00020\u00182\u0012\u0010?\u001a\n\u0012\u0006\b\u0001\u0012\u00020A0@\"\u00020AH\u0002¢\u0006\u0002\u0010DJ6\u0010E\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\f2\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u000e2\u0012\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\n0\u0010H\u0086\bø\u0001\u0000J\u001c\u0010F\u001a\u00020\n2\f\u0010G\u001a\b\u0012\u0004\u0012\u00020H0'2\u0006\u0010\u0013\u001a\u00020\u0014J\u001c\u0010I\u001a\u00020\n2\f\u0010G\u001a\b\u0012\u0004\u0012\u00020H0'2\u0006\u0010\u0013\u001a\u00020\u0014J\u0016\u0010J\u001a\u00020\n2\u0006\u0010K\u001a\u00020\u001e2\u0006\u0010\u0013\u001a\u00020\u0014J\u001c\u0010L\u001a\u00020\n2\f\u0010G\u001a\b\u0012\u0004\u0012\u00020H0'2\u0006\u0010\u0013\u001a\u00020\u0014J\u001c\u0010M\u001a\u00020\n2\f\u00107\u001a\b\u0012\u0004\u0012\u0002080\"2\u0006\u0010\u0013\u001a\u00020\u0014J\u0016\u0010N\u001a\u00020\n2\u0006\u0010:\u001a\u00020;2\u0006\u0010\u0013\u001a\u00020\u0014J*\u0010O\u001a\u00020\n2\f\u0010P\u001a\b\u0012\u0004\u0012\u00020H0\"2\f\u0010Q\u001a\b\u0012\u0004\u0012\u00020\u00040\"2\u0006\u0010\u0013\u001a\u00020\u0014J\u001c\u0010R\u001a\u00020\n2\f\u0010P\u001a\b\u0012\u0004\u0012\u00020H0\"2\u0006\u0010\u0013\u001a\u00020\u0014J*\u0010S\u001a\u00020\n2\f\u0010P\u001a\b\u0012\u0004\u0012\u00020H0\"2\f\u0010Q\u001a\b\u0012\u0004\u0012\u00020\u00040\"2\u0006\u0010\u0013\u001a\u00020\u0014J\u0016\u0010T\u001a\u00020\n2\u0006\u0010)\u001a\u00020\u00042\u0006\u0010\u0013\u001a\u00020\u0014J\u001c\u0010U\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\f2\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u000eJ\u000e\u0010V\u001a\u00020\n2\u0006\u0010\u0013\u001a\u00020\u0014J\u0006\u0010W\u001a\u00020\nJ\u001a\u0010X\u001a\u00020\n2\f\u0010Y\u001a\b\u0012\u0004\u0012\u00020\f0ZH\u0086\bø\u0001\u0000J\u001a\u0010[\u001a\u00020\n2\f\u0010Y\u001a\b\u0012\u0004\u0012\u00020\f0ZH\u0086\bø\u0001\u0000R\u0018\u0010\u0003\u001a\u00020\u0004X¦\u000e¢\u0006\f\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\b\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\\"}, d2 = {"Lorg/jetbrains/kotlin/wasm/ir/WasmExpressionBuilder;", "", "()V", "numberOfNestedBlocks", "", "getNumberOfNestedBlocks", "()I", "setNumberOfNestedBlocks", "(I)V", "buildBlock", "", CoroutineCodegenUtilKt.COROUTINE_LABEL_FIELD_NAME, "", "resultType", "Lorg/jetbrains/kotlin/wasm/ir/WasmType;", "body", "Lkotlin/Function1;", "buildBr", "absoluteBlockLevel", FirebaseAnalytics.Param.LOCATION, "Lorg/jetbrains/kotlin/wasm/ir/source/location/SourceLocation;", "buildBrIf", "buildBrInstr", "brOp", "Lorg/jetbrains/kotlin/wasm/ir/WasmOp;", "buildBrOnCastInstr", "fromIsNullable", "", "toIsNullable", TypedValues.TransitionType.S_FROM, "Lorg/jetbrains/kotlin/wasm/ir/WasmHeapType;", "to", "buildCall", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/wasm/ir/WasmSymbol;", "Lorg/jetbrains/kotlin/wasm/ir/WasmFunction;", "buildCallIndirect", "Lorg/jetbrains/kotlin/wasm/ir/WasmFunctionType;", "tableIdx", "Lorg/jetbrains/kotlin/wasm/ir/WasmSymbolReadOnly;", "buildCatch", "tagIdx", "buildConstF32", "value", "", "buildConstF64", "", "buildConstI32", "buildConstI32Symbol", "buildConstI64", "", "buildDrop", "buildElse", "buildEnd", "buildGetGlobal", "global", "Lorg/jetbrains/kotlin/wasm/ir/WasmGlobal;", "buildGetLocal", "local", "Lorg/jetbrains/kotlin/wasm/ir/WasmLocal;", "buildIf", "buildInstr", "op", "immediates", "", "Lorg/jetbrains/kotlin/wasm/ir/WasmImmediate;", "(Lorg/jetbrains/kotlin/wasm/ir/WasmOp;Lorg/jetbrains/kotlin/wasm/ir/source/location/SourceLocation;[Lorg/jetbrains/kotlin/wasm/ir/WasmImmediate;)V", "buildInstrWithNoLocation", "(Lorg/jetbrains/kotlin/wasm/ir/WasmOp;[Lorg/jetbrains/kotlin/wasm/ir/WasmImmediate;)V", "buildLoop", "buildRefCastNullStatic", "toType", "Lorg/jetbrains/kotlin/wasm/ir/WasmTypeDeclaration;", "buildRefCastStatic", "buildRefNull", ModuleXmlParser.TYPE, "buildRefTestStatic", "buildSetGlobal", "buildSetLocal", "buildStructGet", "struct", "fieldId", "buildStructNew", "buildStructSet", "buildThrow", "buildTry", "buildUnreachable", "commentGroupEnd", "commentGroupStart", "text", "Lkotlin/Function0;", "commentPreviousInstr", "wasm.ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class WasmExpressionBuilder {
    public abstract void buildInstr(WasmOp op, SourceLocation location, WasmImmediate... immediates);

    public abstract int getNumberOfNestedBlocks();

    public abstract void setNumberOfNestedBlocks(int i);

    public final void buildConstI32(int value, SourceLocation location) {
        Intrinsics.checkNotNullParameter(location, "location");
        buildInstr(WasmOp.I32_CONST, location, new WasmImmediate.ConstI32(value));
    }

    public final void buildConstI64(long value, SourceLocation location) {
        Intrinsics.checkNotNullParameter(location, "location");
        buildInstr(WasmOp.I64_CONST, location, new WasmImmediate.ConstI64(value));
    }

    public final void buildConstF32(float value, SourceLocation location) {
        Intrinsics.checkNotNullParameter(location, "location");
        buildInstr(WasmOp.F32_CONST, location, new WasmImmediate.ConstF32(UInt.m2993constructorimpl(Float.floatToRawIntBits(value)), null));
    }

    public final void buildConstF64(double value, SourceLocation location) {
        Intrinsics.checkNotNullParameter(location, "location");
        buildInstr(WasmOp.F64_CONST, location, new WasmImmediate.ConstF64(ULong.m3072constructorimpl(Double.doubleToRawLongBits(value)), null));
    }

    public final void buildConstI32Symbol(WasmSymbol<Integer> value, SourceLocation location) {
        Intrinsics.checkNotNullParameter(value, "value");
        Intrinsics.checkNotNullParameter(location, "location");
        buildInstr(WasmOp.I32_CONST, location, new WasmImmediate.SymbolI32(value));
    }

    public final void buildUnreachable(SourceLocation location) {
        Intrinsics.checkNotNullParameter(location, "location");
        buildInstr(WasmOp.UNREACHABLE, location, new WasmImmediate[0]);
    }

    public static /* synthetic */ void buildBlock$default(WasmExpressionBuilder wasmExpressionBuilder, String str, WasmType wasmType, Function1 body, int i, Object obj) {
        if (obj == null) {
            if ((i & 2) != 0) {
                wasmType = null;
            }
            Intrinsics.checkNotNullParameter(body, "body");
            wasmExpressionBuilder.setNumberOfNestedBlocks(wasmExpressionBuilder.getNumberOfNestedBlocks() + 1);
            wasmExpressionBuilder.buildInstr(WasmOp.BLOCK, SourceLocation.INSTANCE.NoLocation("BLOCK"), new WasmImmediate.BlockType.Value(wasmType));
            body.invoke(Integer.valueOf(wasmExpressionBuilder.getNumberOfNestedBlocks()));
            wasmExpressionBuilder.buildEnd();
            return;
        }
        throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: buildBlock");
    }

    public final void buildBlock(String label, WasmType resultType, Function1<? super Integer, Unit> body) {
        Intrinsics.checkNotNullParameter(body, "body");
        setNumberOfNestedBlocks(getNumberOfNestedBlocks() + 1);
        buildInstr(WasmOp.BLOCK, SourceLocation.INSTANCE.NoLocation("BLOCK"), new WasmImmediate.BlockType.Value(resultType));
        body.invoke(Integer.valueOf(getNumberOfNestedBlocks()));
        buildEnd();
    }

    public static /* synthetic */ void buildLoop$default(WasmExpressionBuilder wasmExpressionBuilder, String str, WasmType wasmType, Function1 body, int i, Object obj) {
        if (obj == null) {
            if ((i & 2) != 0) {
                wasmType = null;
            }
            Intrinsics.checkNotNullParameter(body, "body");
            wasmExpressionBuilder.setNumberOfNestedBlocks(wasmExpressionBuilder.getNumberOfNestedBlocks() + 1);
            wasmExpressionBuilder.buildInstr(WasmOp.LOOP, SourceLocation.INSTANCE.NoLocation("LOOP"), new WasmImmediate.BlockType.Value(wasmType));
            body.invoke(Integer.valueOf(wasmExpressionBuilder.getNumberOfNestedBlocks()));
            wasmExpressionBuilder.buildEnd();
            return;
        }
        throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: buildLoop");
    }

    public final void buildLoop(String label, WasmType resultType, Function1<? super Integer, Unit> body) {
        Intrinsics.checkNotNullParameter(body, "body");
        setNumberOfNestedBlocks(getNumberOfNestedBlocks() + 1);
        buildInstr(WasmOp.LOOP, SourceLocation.INSTANCE.NoLocation("LOOP"), new WasmImmediate.BlockType.Value(resultType));
        body.invoke(Integer.valueOf(getNumberOfNestedBlocks()));
        buildEnd();
    }

    private final void buildInstrWithNoLocation(WasmOp op, WasmImmediate... immediates) {
        buildInstr(op, SourceLocation.INSTANCE.NoLocation(op.getMnemonic()), (WasmImmediate[]) Arrays.copyOf(immediates, immediates.length));
    }

    public static /* synthetic */ void buildIf$default(WasmExpressionBuilder wasmExpressionBuilder, String str, WasmType wasmType, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: buildIf");
        }
        if ((i & 2) != 0) {
            wasmType = null;
        }
        wasmExpressionBuilder.buildIf(str, wasmType);
    }

    public final void buildIf(String label, WasmType resultType) {
        setNumberOfNestedBlocks(getNumberOfNestedBlocks() + 1);
        buildInstrWithNoLocation(WasmOp.IF, new WasmImmediate.BlockType.Value(resultType));
    }

    public final void buildElse() {
        buildInstrWithNoLocation(WasmOp.ELSE, new WasmImmediate[0]);
    }

    public static /* synthetic */ int buildBlock$default(WasmExpressionBuilder wasmExpressionBuilder, WasmType wasmType, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: buildBlock");
        }
        if ((i & 1) != 0) {
            wasmType = null;
        }
        return wasmExpressionBuilder.buildBlock(wasmType);
    }

    public final int buildBlock(WasmType resultType) {
        setNumberOfNestedBlocks(getNumberOfNestedBlocks() + 1);
        buildInstrWithNoLocation(WasmOp.BLOCK, new WasmImmediate.BlockType.Value(resultType));
        return getNumberOfNestedBlocks();
    }

    public final void buildEnd() {
        setNumberOfNestedBlocks(getNumberOfNestedBlocks() - 1);
        buildInstrWithNoLocation(WasmOp.END, new WasmImmediate[0]);
    }

    public final void buildBrInstr(WasmOp brOp, int absoluteBlockLevel, SourceLocation location) {
        Intrinsics.checkNotNullParameter(brOp, "brOp");
        Intrinsics.checkNotNullParameter(location, "location");
        buildInstr(brOp, location, new WasmImmediate.LabelIdx(getNumberOfNestedBlocks() - absoluteBlockLevel));
    }

    public final void buildBrOnCastInstr(WasmOp brOp, int absoluteBlockLevel, boolean fromIsNullable, boolean toIsNullable, WasmHeapType from, WasmHeapType to, SourceLocation location) {
        Intrinsics.checkNotNullParameter(brOp, "brOp");
        Intrinsics.checkNotNullParameter(from, "from");
        Intrinsics.checkNotNullParameter(to, "to");
        Intrinsics.checkNotNullParameter(location, "location");
        buildInstr(brOp, location, new WasmImmediate.ConstU8(UByte.m2916constructorimpl((byte) ((fromIsNullable ? 1 : 0) | (toIsNullable ? 2 : 0))), null), new WasmImmediate.LabelIdx(getNumberOfNestedBlocks() - absoluteBlockLevel), new WasmImmediate.HeapType(from), new WasmImmediate.HeapType(to));
    }

    public final void buildBr(int absoluteBlockLevel, SourceLocation location) {
        Intrinsics.checkNotNullParameter(location, "location");
        buildBrInstr(WasmOp.BR, absoluteBlockLevel, location);
    }

    public final void buildThrow(int tagIdx, SourceLocation location) {
        Intrinsics.checkNotNullParameter(location, "location");
        buildInstr(WasmOp.THROW, location, new WasmImmediate.TagIdx(tagIdx));
    }

    public static /* synthetic */ void buildTry$default(WasmExpressionBuilder wasmExpressionBuilder, String str, WasmType wasmType, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: buildTry");
        }
        if ((i & 2) != 0) {
            wasmType = null;
        }
        wasmExpressionBuilder.buildTry(str, wasmType);
    }

    public final void buildTry(String label, WasmType resultType) {
        setNumberOfNestedBlocks(getNumberOfNestedBlocks() + 1);
        buildInstrWithNoLocation(WasmOp.TRY, new WasmImmediate.BlockType.Value(resultType));
    }

    public final void buildCatch(int tagIdx) {
        buildInstrWithNoLocation(WasmOp.CATCH, new WasmImmediate.TagIdx(tagIdx));
    }

    public final void buildBrIf(int absoluteBlockLevel, SourceLocation location) {
        Intrinsics.checkNotNullParameter(location, "location");
        buildBrInstr(WasmOp.BR_IF, absoluteBlockLevel, location);
    }

    public final void buildCall(WasmSymbol<? extends WasmFunction> symbol, SourceLocation location) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        Intrinsics.checkNotNullParameter(location, "location");
        buildInstr(WasmOp.CALL, location, new WasmImmediate.FuncIdx(symbol));
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ void buildCallIndirect$default(WasmExpressionBuilder wasmExpressionBuilder, WasmSymbol wasmSymbol, WasmSymbolReadOnly wasmSymbolReadOnly, SourceLocation sourceLocation, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: buildCallIndirect");
        }
        if ((i & 2) != 0) {
            wasmSymbolReadOnly = new WasmSymbol(0);
        }
        wasmExpressionBuilder.buildCallIndirect(wasmSymbol, wasmSymbolReadOnly, sourceLocation);
    }

    public final void buildCallIndirect(WasmSymbol<WasmFunctionType> symbol, WasmSymbolReadOnly<Integer> tableIdx, SourceLocation location) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        Intrinsics.checkNotNullParameter(tableIdx, "tableIdx");
        Intrinsics.checkNotNullParameter(location, "location");
        buildInstr(WasmOp.CALL_INDIRECT, location, new WasmImmediate.TypeIdx(symbol), new WasmImmediate.TableIdx(tableIdx));
    }

    public final void buildGetLocal(WasmLocal local, SourceLocation location) {
        Intrinsics.checkNotNullParameter(local, "local");
        Intrinsics.checkNotNullParameter(location, "location");
        buildInstr(WasmOp.LOCAL_GET, location, new WasmImmediate.LocalIdx(local));
    }

    public final void buildSetLocal(WasmLocal local, SourceLocation location) {
        Intrinsics.checkNotNullParameter(local, "local");
        Intrinsics.checkNotNullParameter(location, "location");
        buildInstr(WasmOp.LOCAL_SET, location, new WasmImmediate.LocalIdx(local));
    }

    public final void buildGetGlobal(WasmSymbol<WasmGlobal> global, SourceLocation location) {
        Intrinsics.checkNotNullParameter(global, "global");
        Intrinsics.checkNotNullParameter(location, "location");
        buildInstr(WasmOp.GLOBAL_GET, location, new WasmImmediate.GlobalIdx(global));
    }

    public final void buildSetGlobal(WasmSymbol<WasmGlobal> global, SourceLocation location) {
        Intrinsics.checkNotNullParameter(global, "global");
        Intrinsics.checkNotNullParameter(location, "location");
        buildInstr(WasmOp.GLOBAL_SET, location, new WasmImmediate.GlobalIdx(global));
    }

    public final void buildStructGet(WasmSymbol<? extends WasmTypeDeclaration> struct, WasmSymbol<Integer> fieldId, SourceLocation location) {
        Intrinsics.checkNotNullParameter(struct, "struct");
        Intrinsics.checkNotNullParameter(fieldId, "fieldId");
        Intrinsics.checkNotNullParameter(location, "location");
        buildInstr(WasmOp.STRUCT_GET, location, new WasmImmediate.GcType(struct), new WasmImmediate.StructFieldIdx(fieldId));
    }

    public final void buildStructNew(WasmSymbol<? extends WasmTypeDeclaration> struct, SourceLocation location) {
        Intrinsics.checkNotNullParameter(struct, "struct");
        Intrinsics.checkNotNullParameter(location, "location");
        buildInstr(WasmOp.STRUCT_NEW, location, new WasmImmediate.GcType(struct));
    }

    public final void buildStructSet(WasmSymbol<? extends WasmTypeDeclaration> struct, WasmSymbol<Integer> fieldId, SourceLocation location) {
        Intrinsics.checkNotNullParameter(struct, "struct");
        Intrinsics.checkNotNullParameter(fieldId, "fieldId");
        Intrinsics.checkNotNullParameter(location, "location");
        buildInstr(WasmOp.STRUCT_SET, location, new WasmImmediate.GcType(struct), new WasmImmediate.StructFieldIdx(fieldId));
    }

    public final void buildRefCastNullStatic(WasmSymbolReadOnly<? extends WasmTypeDeclaration> toType, SourceLocation location) {
        Intrinsics.checkNotNullParameter(toType, "toType");
        Intrinsics.checkNotNullParameter(location, "location");
        buildInstr(WasmOp.REF_CAST_NULL, location, new WasmImmediate.HeapType(new WasmHeapType.Type(toType)));
    }

    public final void buildRefCastStatic(WasmSymbolReadOnly<? extends WasmTypeDeclaration> toType, SourceLocation location) {
        Intrinsics.checkNotNullParameter(toType, "toType");
        Intrinsics.checkNotNullParameter(location, "location");
        buildInstr(WasmOp.REF_CAST, location, new WasmImmediate.HeapType(new WasmHeapType.Type(toType)));
    }

    public final void buildRefTestStatic(WasmSymbolReadOnly<? extends WasmTypeDeclaration> toType, SourceLocation location) {
        Intrinsics.checkNotNullParameter(toType, "toType");
        Intrinsics.checkNotNullParameter(location, "location");
        buildInstr(WasmOp.REF_TEST, location, new WasmImmediate.HeapType(new WasmHeapType.Type(toType)));
    }

    public final void buildRefNull(WasmHeapType type, SourceLocation location) {
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(location, "location");
        buildInstr(WasmOp.REF_NULL, location, new WasmImmediate.HeapType(new WasmRefType(type)));
    }

    public final void buildDrop(SourceLocation location) {
        Intrinsics.checkNotNullParameter(location, "location");
        buildInstr(WasmOp.DROP, location, new WasmImmediate[0]);
    }

    public final void commentPreviousInstr(Function0<String> text) {
        Intrinsics.checkNotNullParameter(text, "text");
        buildInstr(WasmOp.PSEUDO_COMMENT_PREVIOUS_INSTR, SourceLocation.INSTANCE.NoLocation("Pseudo-instruction"), new WasmImmediate.ConstString(text.invoke()));
    }

    public final void commentGroupStart(Function0<String> text) {
        Intrinsics.checkNotNullParameter(text, "text");
        buildInstr(WasmOp.PSEUDO_COMMENT_GROUP_START, SourceLocation.INSTANCE.NoLocation("Pseudo-instruction"), new WasmImmediate.ConstString(text.invoke()));
    }

    public final void commentGroupEnd() {
        buildInstr(WasmOp.PSEUDO_COMMENT_GROUP_END, SourceLocation.INSTANCE.NoLocation("Pseudo-instruction"), new WasmImmediate[0]);
    }
}
