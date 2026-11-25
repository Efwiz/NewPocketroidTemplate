package org.jetbrains.kotlin.wasm.ir;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;

/* JADX INFO: compiled from: Operators.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000n\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0019\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0018\u0003\u0004\u0005\u0006\u0007\b\t\n\u000b\f\r\u000e\u000f\u0010\u0011\u0012\u0013\u0014\u0015\u0016\u0017\u0018\u0019\u001aB\u0007\b\u0004¢\u0006\u0002\u0010\u0002\u0082\u0001\u0018\u001b\u001c\u001d\u001e\u001f !\"#$%&'()*+,-./012¨\u00063"}, d2 = {"Lorg/jetbrains/kotlin/wasm/ir/WasmImmediate;", "", "()V", "BlockType", "ConstF32", "ConstF64", "ConstI32", "ConstI64", "ConstString", "ConstU8", "DataIdx", "ElemIdx", "FuncIdx", "GcType", "GlobalIdx", "HeapType", "LabelIdx", "LabelIdxVector", "LocalIdx", "MemArg", "MemoryIdx", "StructFieldIdx", "SymbolI32", "TableIdx", "TagIdx", "TypeIdx", "ValTypeVector", "Lorg/jetbrains/kotlin/wasm/ir/WasmImmediate$BlockType;", "Lorg/jetbrains/kotlin/wasm/ir/WasmImmediate$ConstF32;", "Lorg/jetbrains/kotlin/wasm/ir/WasmImmediate$ConstF64;", "Lorg/jetbrains/kotlin/wasm/ir/WasmImmediate$ConstI32;", "Lorg/jetbrains/kotlin/wasm/ir/WasmImmediate$ConstI64;", "Lorg/jetbrains/kotlin/wasm/ir/WasmImmediate$ConstString;", "Lorg/jetbrains/kotlin/wasm/ir/WasmImmediate$ConstU8;", "Lorg/jetbrains/kotlin/wasm/ir/WasmImmediate$DataIdx;", "Lorg/jetbrains/kotlin/wasm/ir/WasmImmediate$ElemIdx;", "Lorg/jetbrains/kotlin/wasm/ir/WasmImmediate$FuncIdx;", "Lorg/jetbrains/kotlin/wasm/ir/WasmImmediate$GcType;", "Lorg/jetbrains/kotlin/wasm/ir/WasmImmediate$GlobalIdx;", "Lorg/jetbrains/kotlin/wasm/ir/WasmImmediate$HeapType;", "Lorg/jetbrains/kotlin/wasm/ir/WasmImmediate$LabelIdx;", "Lorg/jetbrains/kotlin/wasm/ir/WasmImmediate$LabelIdxVector;", "Lorg/jetbrains/kotlin/wasm/ir/WasmImmediate$LocalIdx;", "Lorg/jetbrains/kotlin/wasm/ir/WasmImmediate$MemArg;", "Lorg/jetbrains/kotlin/wasm/ir/WasmImmediate$MemoryIdx;", "Lorg/jetbrains/kotlin/wasm/ir/WasmImmediate$StructFieldIdx;", "Lorg/jetbrains/kotlin/wasm/ir/WasmImmediate$SymbolI32;", "Lorg/jetbrains/kotlin/wasm/ir/WasmImmediate$TableIdx;", "Lorg/jetbrains/kotlin/wasm/ir/WasmImmediate$TagIdx;", "Lorg/jetbrains/kotlin/wasm/ir/WasmImmediate$TypeIdx;", "Lorg/jetbrains/kotlin/wasm/ir/WasmImmediate$ValTypeVector;", "wasm.ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class WasmImmediate {
    public /* synthetic */ WasmImmediate(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    /* JADX INFO: compiled from: Operators.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0019\u0010\u0002\u001a\u00020\u0003ø\u0001\u0000ø\u0001\u0001¢\u0006\n\n\u0002\u0010\u0007\u001a\u0004\b\u0005\u0010\u0006\u0082\u0002\u000b\n\u0005\b¡\u001e0\u0001\n\u0002\b!¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/wasm/ir/WasmImmediate$ConstU8;", "Lorg/jetbrains/kotlin/wasm/ir/WasmImmediate;", "value", "Lkotlin/UByte;", "(BLkotlin/jvm/internal/DefaultConstructorMarker;)V", "getValue-w2LRezQ", "()B", "B", "wasm.ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class ConstU8 extends WasmImmediate {
        private final byte value;

        public /* synthetic */ ConstU8(byte b, DefaultConstructorMarker defaultConstructorMarker) {
            this(b);
        }

        private ConstU8(byte b) {
            super(null);
            this.value = b;
        }

        /* JADX INFO: renamed from: getValue-w2LRezQ, reason: not valid java name and from getter */
        public final byte getValue() {
            return this.value;
        }
    }

    private WasmImmediate() {
    }

    /* JADX INFO: compiled from: Operators.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/wasm/ir/WasmImmediate$ConstI32;", "Lorg/jetbrains/kotlin/wasm/ir/WasmImmediate;", "value", "", "(I)V", "getValue", "()I", "wasm.ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class ConstI32 extends WasmImmediate {
        private final int value;

        public ConstI32(int i) {
            super(null);
            this.value = i;
        }

        public final int getValue() {
            return this.value;
        }
    }

    /* JADX INFO: compiled from: Operators.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/wasm/ir/WasmImmediate$ConstI64;", "Lorg/jetbrains/kotlin/wasm/ir/WasmImmediate;", "value", "", "(J)V", "getValue", "()J", "wasm.ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class ConstI64 extends WasmImmediate {
        private final long value;

        public ConstI64(long j) {
            super(null);
            this.value = j;
        }

        public final long getValue() {
            return this.value;
        }
    }

    /* JADX INFO: compiled from: Operators.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0019\u0010\u0002\u001a\u00020\u0003ø\u0001\u0000ø\u0001\u0001¢\u0006\n\n\u0002\u0010\u0007\u001a\u0004\b\u0005\u0010\u0006\u0082\u0002\u000b\n\u0005\b¡\u001e0\u0001\n\u0002\b!¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/wasm/ir/WasmImmediate$ConstF32;", "Lorg/jetbrains/kotlin/wasm/ir/WasmImmediate;", "rawBits", "Lkotlin/UInt;", "(ILkotlin/jvm/internal/DefaultConstructorMarker;)V", "getRawBits-pVg5ArA", "()I", "I", "wasm.ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class ConstF32 extends WasmImmediate {
        private final int rawBits;

        public /* synthetic */ ConstF32(int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(i);
        }

        private ConstF32(int i) {
            super(null);
            this.rawBits = i;
        }

        /* JADX INFO: renamed from: getRawBits-pVg5ArA, reason: not valid java name and from getter */
        public final int getRawBits() {
            return this.rawBits;
        }
    }

    /* JADX INFO: compiled from: Operators.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0019\u0010\u0002\u001a\u00020\u0003ø\u0001\u0000ø\u0001\u0001¢\u0006\n\n\u0002\u0010\u0007\u001a\u0004\b\u0005\u0010\u0006\u0082\u0002\u000b\n\u0005\b¡\u001e0\u0001\n\u0002\b!¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/wasm/ir/WasmImmediate$ConstF64;", "Lorg/jetbrains/kotlin/wasm/ir/WasmImmediate;", "rawBits", "Lkotlin/ULong;", "(JLkotlin/jvm/internal/DefaultConstructorMarker;)V", "getRawBits-s-VKNKU", "()J", "J", "wasm.ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class ConstF64 extends WasmImmediate {
        private final long rawBits;

        public /* synthetic */ ConstF64(long j, DefaultConstructorMarker defaultConstructorMarker) {
            this(j);
        }

        private ConstF64(long j) {
            super(null);
            this.rawBits = j;
        }

        /* JADX INFO: renamed from: getRawBits-s-VKNKU, reason: not valid java name and from getter */
        public final long getRawBits() {
            return this.rawBits;
        }
    }

    /* JADX INFO: compiled from: Operators.kt */
    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0005R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/wasm/ir/WasmImmediate$SymbolI32;", "Lorg/jetbrains/kotlin/wasm/ir/WasmImmediate;", "value", "Lorg/jetbrains/kotlin/wasm/ir/WasmSymbol;", "", "(Lorg/jetbrains/kotlin/wasm/ir/WasmSymbol;)V", "getValue", "()Lorg/jetbrains/kotlin/wasm/ir/WasmSymbol;", "wasm.ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class SymbolI32 extends WasmImmediate {
        private final WasmSymbol<Integer> value;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public SymbolI32(WasmSymbol<Integer> value) {
            super(null);
            Intrinsics.checkNotNullParameter(value, "value");
            this.value = value;
        }

        public final WasmSymbol<Integer> getValue() {
            return this.value;
        }
    }

    /* JADX INFO: compiled from: Operators.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005R\u0019\u0010\u0002\u001a\u00020\u0003ø\u0001\u0000ø\u0001\u0001¢\u0006\n\n\u0002\u0010\b\u001a\u0004\b\u0006\u0010\u0007R\u0019\u0010\u0004\u001a\u00020\u0003ø\u0001\u0000ø\u0001\u0001¢\u0006\n\n\u0002\u0010\b\u001a\u0004\b\t\u0010\u0007\u0082\u0002\u000b\n\u0005\b¡\u001e0\u0001\n\u0002\b!¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/wasm/ir/WasmImmediate$MemArg;", "Lorg/jetbrains/kotlin/wasm/ir/WasmImmediate;", "align", "Lkotlin/UInt;", TypedValues.CycleType.S_WAVE_OFFSET, "(IILkotlin/jvm/internal/DefaultConstructorMarker;)V", "getAlign-pVg5ArA", "()I", "I", "getOffset-pVg5ArA", "wasm.ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class MemArg extends WasmImmediate {
        private final int align;
        private final int offset;

        public /* synthetic */ MemArg(int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
            this(i, i2);
        }

        private MemArg(int i, int i2) {
            super(null);
            this.align = i;
            this.offset = i2;
        }

        /* JADX INFO: renamed from: getAlign-pVg5ArA, reason: not valid java name and from getter */
        public final int getAlign() {
            return this.align;
        }

        /* JADX INFO: renamed from: getOffset-pVg5ArA, reason: not valid java name and from getter */
        public final int getOffset() {
            return this.offset;
        }
    }

    /* JADX INFO: compiled from: Operators.kt */
    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0002\u0003\u0004B\u0007\b\u0004¢\u0006\u0002\u0010\u0002\u0082\u0001\u0002\u0005\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/wasm/ir/WasmImmediate$BlockType;", "Lorg/jetbrains/kotlin/wasm/ir/WasmImmediate;", "()V", "Function", "Value", "Lorg/jetbrains/kotlin/wasm/ir/WasmImmediate$BlockType$Function;", "Lorg/jetbrains/kotlin/wasm/ir/WasmImmediate$BlockType$Value;", "wasm.ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static abstract class BlockType extends WasmImmediate {
        public /* synthetic */ BlockType(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX INFO: compiled from: Operators.kt */
        @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/wasm/ir/WasmImmediate$BlockType$Function;", "Lorg/jetbrains/kotlin/wasm/ir/WasmImmediate$BlockType;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/wasm/ir/WasmFunctionType;", "(Lorg/jetbrains/kotlin/wasm/ir/WasmFunctionType;)V", "getType", "()Lorg/jetbrains/kotlin/wasm/ir/WasmFunctionType;", "wasm.ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class Function extends BlockType {
            private final WasmFunctionType type;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public Function(WasmFunctionType type) {
                super(null);
                Intrinsics.checkNotNullParameter(type, "type");
                this.type = type;
            }

            public final WasmFunctionType getType() {
                return this.type;
            }
        }

        private BlockType() {
            super(null);
        }

        /* JADX INFO: compiled from: Operators.kt */
        @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u000f\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0004R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/wasm/ir/WasmImmediate$BlockType$Value;", "Lorg/jetbrains/kotlin/wasm/ir/WasmImmediate$BlockType;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/wasm/ir/WasmType;", "(Lorg/jetbrains/kotlin/wasm/ir/WasmType;)V", "getType", "()Lorg/jetbrains/kotlin/wasm/ir/WasmType;", "wasm.ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class Value extends BlockType {
            private final WasmType type;

            public Value(WasmType wasmType) {
                super(null);
                this.type = wasmType;
            }

            public final WasmType getType() {
                return this.type;
            }
        }
    }

    /* JADX INFO: compiled from: Operators.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004B\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005¢\u0006\u0002\u0010\u0006R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/wasm/ir/WasmImmediate$FuncIdx;", "Lorg/jetbrains/kotlin/wasm/ir/WasmImmediate;", "value", "Lorg/jetbrains/kotlin/wasm/ir/WasmFunction;", "(Lorg/jetbrains/kotlin/wasm/ir/WasmFunction;)V", "Lorg/jetbrains/kotlin/wasm/ir/WasmSymbol;", "(Lorg/jetbrains/kotlin/wasm/ir/WasmSymbol;)V", "getValue", "()Lorg/jetbrains/kotlin/wasm/ir/WasmSymbol;", "wasm.ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class FuncIdx extends WasmImmediate {
        private final WasmSymbol<WasmFunction> value;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        public FuncIdx(WasmSymbol<? extends WasmFunction> value) {
            super(null);
            Intrinsics.checkNotNullParameter(value, "value");
            this.value = value;
        }

        public final WasmSymbol<WasmFunction> getValue() {
            return this.value;
        }

        /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
        public FuncIdx(WasmFunction value) {
            this((WasmSymbol<? extends WasmFunction>) new WasmSymbol(value));
            Intrinsics.checkNotNullParameter(value, "value");
        }
    }

    /* JADX INFO: compiled from: Operators.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004B\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005¢\u0006\u0002\u0010\u0006R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/wasm/ir/WasmImmediate$LocalIdx;", "Lorg/jetbrains/kotlin/wasm/ir/WasmImmediate;", "value", "Lorg/jetbrains/kotlin/wasm/ir/WasmLocal;", "(Lorg/jetbrains/kotlin/wasm/ir/WasmLocal;)V", "Lorg/jetbrains/kotlin/wasm/ir/WasmSymbol;", "(Lorg/jetbrains/kotlin/wasm/ir/WasmSymbol;)V", "getValue", "()Lorg/jetbrains/kotlin/wasm/ir/WasmSymbol;", "wasm.ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class LocalIdx extends WasmImmediate {
        private final WasmSymbol<WasmLocal> value;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public LocalIdx(WasmSymbol<WasmLocal> value) {
            super(null);
            Intrinsics.checkNotNullParameter(value, "value");
            this.value = value;
        }

        public final WasmSymbol<WasmLocal> getValue() {
            return this.value;
        }

        /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
        public LocalIdx(WasmLocal value) {
            this((WasmSymbol<WasmLocal>) new WasmSymbol(value));
            Intrinsics.checkNotNullParameter(value, "value");
        }
    }

    /* JADX INFO: compiled from: Operators.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004B\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005¢\u0006\u0002\u0010\u0006R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/wasm/ir/WasmImmediate$GlobalIdx;", "Lorg/jetbrains/kotlin/wasm/ir/WasmImmediate;", "value", "Lorg/jetbrains/kotlin/wasm/ir/WasmGlobal;", "(Lorg/jetbrains/kotlin/wasm/ir/WasmGlobal;)V", "Lorg/jetbrains/kotlin/wasm/ir/WasmSymbol;", "(Lorg/jetbrains/kotlin/wasm/ir/WasmSymbol;)V", "getValue", "()Lorg/jetbrains/kotlin/wasm/ir/WasmSymbol;", "wasm.ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class GlobalIdx extends WasmImmediate {
        private final WasmSymbol<WasmGlobal> value;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public GlobalIdx(WasmSymbol<WasmGlobal> value) {
            super(null);
            Intrinsics.checkNotNullParameter(value, "value");
            this.value = value;
        }

        public final WasmSymbol<WasmGlobal> getValue() {
            return this.value;
        }

        /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
        public GlobalIdx(WasmGlobal value) {
            this((WasmSymbol<WasmGlobal>) new WasmSymbol(value));
            Intrinsics.checkNotNullParameter(value, "value");
        }
    }

    /* JADX INFO: compiled from: Operators.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004B\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005¢\u0006\u0002\u0010\u0006R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/wasm/ir/WasmImmediate$TypeIdx;", "Lorg/jetbrains/kotlin/wasm/ir/WasmImmediate;", "value", "Lorg/jetbrains/kotlin/wasm/ir/WasmTypeDeclaration;", "(Lorg/jetbrains/kotlin/wasm/ir/WasmTypeDeclaration;)V", "Lorg/jetbrains/kotlin/wasm/ir/WasmSymbolReadOnly;", "(Lorg/jetbrains/kotlin/wasm/ir/WasmSymbolReadOnly;)V", "getValue", "()Lorg/jetbrains/kotlin/wasm/ir/WasmSymbolReadOnly;", "wasm.ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class TypeIdx extends WasmImmediate {
        private final WasmSymbolReadOnly<WasmTypeDeclaration> value;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        public TypeIdx(WasmSymbolReadOnly<? extends WasmTypeDeclaration> value) {
            super(null);
            Intrinsics.checkNotNullParameter(value, "value");
            this.value = value;
        }

        public final WasmSymbolReadOnly<WasmTypeDeclaration> getValue() {
            return this.value;
        }

        /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
        public TypeIdx(WasmTypeDeclaration value) {
            this(new WasmSymbol(value));
            Intrinsics.checkNotNullParameter(value, "value");
        }
    }

    /* JADX INFO: compiled from: Operators.kt */
    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0005R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/wasm/ir/WasmImmediate$ValTypeVector;", "Lorg/jetbrains/kotlin/wasm/ir/WasmImmediate;", "value", "", "Lorg/jetbrains/kotlin/wasm/ir/WasmType;", "(Ljava/util/List;)V", "getValue", "()Ljava/util/List;", "wasm.ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class ValTypeVector extends WasmImmediate {
        private final List<WasmType> value;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        public ValTypeVector(List<? extends WasmType> value) {
            super(null);
            Intrinsics.checkNotNullParameter(value, "value");
            this.value = value;
        }

        public final List<WasmType> getValue() {
            return this.value;
        }
    }

    /* JADX INFO: compiled from: Operators.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/wasm/ir/WasmImmediate$MemoryIdx;", "Lorg/jetbrains/kotlin/wasm/ir/WasmImmediate;", "value", "", "(I)V", "getValue", "()I", "wasm.ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class MemoryIdx extends WasmImmediate {
        private final int value;

        public MemoryIdx(int i) {
            super(null);
            this.value = i;
        }

        public final int getValue() {
            return this.value;
        }
    }

    /* JADX INFO: compiled from: Operators.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004B\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005¢\u0006\u0002\u0010\u0006R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/wasm/ir/WasmImmediate$DataIdx;", "Lorg/jetbrains/kotlin/wasm/ir/WasmImmediate;", "value", "", "(I)V", "Lorg/jetbrains/kotlin/wasm/ir/WasmSymbol;", "(Lorg/jetbrains/kotlin/wasm/ir/WasmSymbol;)V", "getValue", "()Lorg/jetbrains/kotlin/wasm/ir/WasmSymbol;", "wasm.ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class DataIdx extends WasmImmediate {
        private final WasmSymbol<Integer> value;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public DataIdx(WasmSymbol<Integer> value) {
            super(null);
            Intrinsics.checkNotNullParameter(value, "value");
            this.value = value;
        }

        public final WasmSymbol<Integer> getValue() {
            return this.value;
        }

        public DataIdx(int i) {
            this((WasmSymbol<Integer>) new WasmSymbol(Integer.valueOf(i)));
        }
    }

    /* JADX INFO: compiled from: Operators.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004B\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005¢\u0006\u0002\u0010\u0006R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/wasm/ir/WasmImmediate$TableIdx;", "Lorg/jetbrains/kotlin/wasm/ir/WasmImmediate;", "value", "", "(I)V", "Lorg/jetbrains/kotlin/wasm/ir/WasmSymbolReadOnly;", "(Lorg/jetbrains/kotlin/wasm/ir/WasmSymbolReadOnly;)V", "getValue", "()Lorg/jetbrains/kotlin/wasm/ir/WasmSymbolReadOnly;", "wasm.ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class TableIdx extends WasmImmediate {
        private final WasmSymbolReadOnly<Integer> value;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public TableIdx(WasmSymbolReadOnly<Integer> value) {
            super(null);
            Intrinsics.checkNotNullParameter(value, "value");
            this.value = value;
        }

        public final WasmSymbolReadOnly<Integer> getValue() {
            return this.value;
        }

        public TableIdx(int i) {
            this(new WasmSymbol(Integer.valueOf(i)));
        }
    }

    /* JADX INFO: compiled from: Operators.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/wasm/ir/WasmImmediate$LabelIdx;", "Lorg/jetbrains/kotlin/wasm/ir/WasmImmediate;", "value", "", "(I)V", "getValue", "()I", "wasm.ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class LabelIdx extends WasmImmediate {
        private final int value;

        public LabelIdx(int i) {
            super(null);
            this.value = i;
        }

        public final int getValue() {
            return this.value;
        }
    }

    /* JADX INFO: compiled from: Operators.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/wasm/ir/WasmImmediate$TagIdx;", "Lorg/jetbrains/kotlin/wasm/ir/WasmImmediate;", "value", "", "(I)V", "getValue", "()I", "wasm.ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class TagIdx extends WasmImmediate {
        private final int value;

        public TagIdx(int i) {
            super(null);
            this.value = i;
        }

        public final int getValue() {
            return this.value;
        }
    }

    /* JADX INFO: compiled from: Operators.kt */
    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\b\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0005R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/wasm/ir/WasmImmediate$LabelIdxVector;", "Lorg/jetbrains/kotlin/wasm/ir/WasmImmediate;", "value", "", "", "(Ljava/util/List;)V", "getValue", "()Ljava/util/List;", "wasm.ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class LabelIdxVector extends WasmImmediate {
        private final List<Integer> value;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public LabelIdxVector(List<Integer> value) {
            super(null);
            Intrinsics.checkNotNullParameter(value, "value");
            this.value = value;
        }

        public final List<Integer> getValue() {
            return this.value;
        }
    }

    /* JADX INFO: compiled from: Operators.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/wasm/ir/WasmImmediate$ElemIdx;", "Lorg/jetbrains/kotlin/wasm/ir/WasmImmediate;", "value", "Lorg/jetbrains/kotlin/wasm/ir/WasmElement;", "(Lorg/jetbrains/kotlin/wasm/ir/WasmElement;)V", "getValue", "()Lorg/jetbrains/kotlin/wasm/ir/WasmElement;", "wasm.ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class ElemIdx extends WasmImmediate {
        private final WasmElement value;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public ElemIdx(WasmElement value) {
            super(null);
            Intrinsics.checkNotNullParameter(value, "value");
            this.value = value;
        }

        public final WasmElement getValue() {
            return this.value;
        }
    }

    /* JADX INFO: compiled from: Operators.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004B\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005¢\u0006\u0002\u0010\u0006R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/wasm/ir/WasmImmediate$GcType;", "Lorg/jetbrains/kotlin/wasm/ir/WasmImmediate;", "value", "Lorg/jetbrains/kotlin/wasm/ir/WasmTypeDeclaration;", "(Lorg/jetbrains/kotlin/wasm/ir/WasmTypeDeclaration;)V", "Lorg/jetbrains/kotlin/wasm/ir/WasmSymbol;", "(Lorg/jetbrains/kotlin/wasm/ir/WasmSymbol;)V", "getValue", "()Lorg/jetbrains/kotlin/wasm/ir/WasmSymbol;", "wasm.ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class GcType extends WasmImmediate {
        private final WasmSymbol<WasmTypeDeclaration> value;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        public GcType(WasmSymbol<? extends WasmTypeDeclaration> value) {
            super(null);
            Intrinsics.checkNotNullParameter(value, "value");
            this.value = value;
        }

        public final WasmSymbol<WasmTypeDeclaration> getValue() {
            return this.value;
        }

        /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
        public GcType(WasmTypeDeclaration value) {
            this((WasmSymbol<? extends WasmTypeDeclaration>) new WasmSymbol(value));
            Intrinsics.checkNotNullParameter(value, "value");
        }
    }

    /* JADX INFO: compiled from: Operators.kt */
    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0005R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/wasm/ir/WasmImmediate$StructFieldIdx;", "Lorg/jetbrains/kotlin/wasm/ir/WasmImmediate;", "value", "Lorg/jetbrains/kotlin/wasm/ir/WasmSymbol;", "", "(Lorg/jetbrains/kotlin/wasm/ir/WasmSymbol;)V", "getValue", "()Lorg/jetbrains/kotlin/wasm/ir/WasmSymbol;", "wasm.ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class StructFieldIdx extends WasmImmediate {
        private final WasmSymbol<Integer> value;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public StructFieldIdx(WasmSymbol<Integer> value) {
            super(null);
            Intrinsics.checkNotNullParameter(value, "value");
            this.value = value;
        }

        public final WasmSymbol<Integer> getValue() {
            return this.value;
        }
    }

    /* JADX INFO: compiled from: Operators.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004B\r\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/wasm/ir/WasmImmediate$HeapType;", "Lorg/jetbrains/kotlin/wasm/ir/WasmImmediate;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/wasm/ir/WasmType;", "(Lorg/jetbrains/kotlin/wasm/ir/WasmType;)V", "value", "Lorg/jetbrains/kotlin/wasm/ir/WasmHeapType;", "(Lorg/jetbrains/kotlin/wasm/ir/WasmHeapType;)V", "getValue", "()Lorg/jetbrains/kotlin/wasm/ir/WasmHeapType;", "wasm.ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class HeapType extends WasmImmediate {
        private final WasmHeapType value;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public HeapType(WasmHeapType value) {
            super(null);
            Intrinsics.checkNotNullParameter(value, "value");
            this.value = value;
        }

        public final WasmHeapType getValue() {
            return this.value;
        }

        /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
        public HeapType(WasmType type) {
            this(TypesKt.getHeapType(type));
            Intrinsics.checkNotNullParameter(type, "type");
        }
    }

    /* JADX INFO: compiled from: Operators.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/wasm/ir/WasmImmediate$ConstString;", "Lorg/jetbrains/kotlin/wasm/ir/WasmImmediate;", "value", "", "(Ljava/lang/String;)V", "getValue", "()Ljava/lang/String;", "wasm.ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class ConstString extends WasmImmediate {
        private final String value;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public ConstString(String value) {
            super(null);
            Intrinsics.checkNotNullParameter(value, "value");
            this.value = value;
        }

        public final String getValue() {
            return this.value;
        }
    }
}
