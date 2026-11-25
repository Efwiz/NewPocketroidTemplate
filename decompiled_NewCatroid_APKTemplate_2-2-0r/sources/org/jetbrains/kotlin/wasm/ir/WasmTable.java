package org.jetbrains.kotlin.wasm.ir;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: Declarations.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000b\u0018\u00002\u00020\u0001:\u0001\u0011B#\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\u0002\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/wasm/ir/WasmTable;", "Lorg/jetbrains/kotlin/wasm/ir/WasmNamedModuleField;", "limits", "Lorg/jetbrains/kotlin/wasm/ir/WasmLimits;", "elementType", "Lorg/jetbrains/kotlin/wasm/ir/WasmType;", "importPair", "Lorg/jetbrains/kotlin/wasm/ir/WasmImportDescriptor;", "(Lorg/jetbrains/kotlin/wasm/ir/WasmLimits;Lorg/jetbrains/kotlin/wasm/ir/WasmType;Lorg/jetbrains/kotlin/wasm/ir/WasmImportDescriptor;)V", "getElementType", "()Lorg/jetbrains/kotlin/wasm/ir/WasmType;", "getImportPair", "()Lorg/jetbrains/kotlin/wasm/ir/WasmImportDescriptor;", "getLimits", "()Lorg/jetbrains/kotlin/wasm/ir/WasmLimits;", "setLimits", "(Lorg/jetbrains/kotlin/wasm/ir/WasmLimits;)V", "Value", "wasm.ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class WasmTable extends WasmNamedModuleField {
    private final WasmType elementType;
    private final WasmImportDescriptor importPair;
    private WasmLimits limits;

    public /* synthetic */ WasmTable(WasmLimits wasmLimits, WasmType wasmType, WasmImportDescriptor wasmImportDescriptor, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? new WasmLimits(1, null, null) : wasmLimits, wasmType, (i & 4) != 0 ? null : wasmImportDescriptor);
    }

    public final WasmLimits getLimits() {
        return this.limits;
    }

    public final void setLimits(WasmLimits wasmLimits) {
        Intrinsics.checkNotNullParameter(wasmLimits, "<set-?>");
        this.limits = wasmLimits;
    }

    public final WasmType getElementType() {
        return this.elementType;
    }

    public final WasmImportDescriptor getImportPair() {
        return this.importPair;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public WasmTable(WasmLimits limits, WasmType elementType, WasmImportDescriptor wasmImportDescriptor) {
        super(null);
        Intrinsics.checkNotNullParameter(limits, "limits");
        Intrinsics.checkNotNullParameter(elementType, "elementType");
        this.limits = limits;
        this.elementType = elementType;
        this.importPair = wasmImportDescriptor;
    }

    /* JADX INFO: compiled from: Declarations.kt */
    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0002\u0003\u0004B\u0007\b\u0004¢\u0006\u0002\u0010\u0002\u0082\u0001\u0002\u0005\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/wasm/ir/WasmTable$Value;", "", "()V", "Expression", "Function", "Lorg/jetbrains/kotlin/wasm/ir/WasmTable$Value$Expression;", "Lorg/jetbrains/kotlin/wasm/ir/WasmTable$Value$Function;", "wasm.ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static abstract class Value {
        public /* synthetic */ Value(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX INFO: compiled from: Declarations.kt */
        @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004B\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005¢\u0006\u0002\u0010\u0006R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/wasm/ir/WasmTable$Value$Function;", "Lorg/jetbrains/kotlin/wasm/ir/WasmTable$Value;", "function", "Lorg/jetbrains/kotlin/wasm/ir/WasmFunction;", "(Lorg/jetbrains/kotlin/wasm/ir/WasmFunction;)V", "Lorg/jetbrains/kotlin/wasm/ir/WasmSymbol;", "(Lorg/jetbrains/kotlin/wasm/ir/WasmSymbol;)V", "getFunction", "()Lorg/jetbrains/kotlin/wasm/ir/WasmSymbol;", "wasm.ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class Function extends Value {
            private final WasmSymbol<WasmFunction> function;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            public Function(WasmSymbol<? extends WasmFunction> function) {
                super(null);
                Intrinsics.checkNotNullParameter(function, "function");
                this.function = function;
            }

            public final WasmSymbol<WasmFunction> getFunction() {
                return this.function;
            }

            /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
            public Function(WasmFunction function) {
                this((WasmSymbol<? extends WasmFunction>) new WasmSymbol(function));
                Intrinsics.checkNotNullParameter(function, "function");
            }
        }

        private Value() {
        }

        /* JADX INFO: compiled from: Declarations.kt */
        @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0005R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/wasm/ir/WasmTable$Value$Expression;", "Lorg/jetbrains/kotlin/wasm/ir/WasmTable$Value;", "expr", "", "Lorg/jetbrains/kotlin/wasm/ir/WasmInstr;", "(Ljava/util/List;)V", "getExpr", "()Ljava/util/List;", "wasm.ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class Expression extends Value {
            private final List<WasmInstr> expr;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            public Expression(List<? extends WasmInstr> expr) {
                super(null);
                Intrinsics.checkNotNullParameter(expr, "expr");
                this.expr = expr;
            }

            public final List<WasmInstr> getExpr() {
                return this.expr;
            }
        }
    }
}
