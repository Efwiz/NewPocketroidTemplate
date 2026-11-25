package org.jetbrains.kotlin.wasm.ir;

import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;

/* JADX INFO: compiled from: Declarations.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0002\f\rB\u001d\b\u0004\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\u0002\u0010\u0007R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b\u0082\u0001\u0002\u000e\u000f¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/wasm/ir/WasmFunction;", "Lorg/jetbrains/kotlin/wasm/ir/WasmNamedModuleField;", "name", "", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/wasm/ir/WasmSymbolReadOnly;", "Lorg/jetbrains/kotlin/wasm/ir/WasmFunctionType;", "(Ljava/lang/String;Lorg/jetbrains/kotlin/wasm/ir/WasmSymbolReadOnly;)V", "getName", "()Ljava/lang/String;", "getType", "()Lorg/jetbrains/kotlin/wasm/ir/WasmSymbolReadOnly;", "Defined", "Imported", "Lorg/jetbrains/kotlin/wasm/ir/WasmFunction$Defined;", "Lorg/jetbrains/kotlin/wasm/ir/WasmFunction$Imported;", "wasm.ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class WasmFunction extends WasmNamedModuleField {
    private final String name;
    private final WasmSymbolReadOnly<WasmFunctionType> type;

    public /* synthetic */ WasmFunction(String str, WasmSymbolReadOnly wasmSymbolReadOnly, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, wasmSymbolReadOnly);
    }

    @Override // org.jetbrains.kotlin.wasm.ir.WasmNamedModuleField
    public String getName() {
        return this.name;
    }

    public final WasmSymbolReadOnly<WasmFunctionType> getType() {
        return this.type;
    }

    private WasmFunction(String str, WasmSymbolReadOnly<WasmFunctionType> wasmSymbolReadOnly) {
        super(null);
        this.name = str;
        this.type = wasmSymbolReadOnly;
    }

    /* JADX INFO: compiled from: Declarations.kt */
    @Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B;\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\u000e\b\u0002\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b\u0012\u000e\b\u0002\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u000b0\b¢\u0006\u0002\u0010\fR\u0017\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u000b0\b¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0017\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u000e¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/wasm/ir/WasmFunction$Defined;", "Lorg/jetbrains/kotlin/wasm/ir/WasmFunction;", "name", "", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/wasm/ir/WasmSymbolReadOnly;", "Lorg/jetbrains/kotlin/wasm/ir/WasmFunctionType;", "locals", "", "Lorg/jetbrains/kotlin/wasm/ir/WasmLocal;", "instructions", "Lorg/jetbrains/kotlin/wasm/ir/WasmInstr;", "(Ljava/lang/String;Lorg/jetbrains/kotlin/wasm/ir/WasmSymbolReadOnly;Ljava/util/List;Ljava/util/List;)V", "getInstructions", "()Ljava/util/List;", "getLocals", "wasm.ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Defined extends WasmFunction {
        private final List<WasmInstr> instructions;
        private final List<WasmLocal> locals;

        public /* synthetic */ Defined(String str, WasmSymbolReadOnly wasmSymbolReadOnly, ArrayList arrayList, ArrayList arrayList2, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(str, wasmSymbolReadOnly, (i & 4) != 0 ? new ArrayList() : arrayList, (i & 8) != 0 ? new ArrayList() : arrayList2);
        }

        public final List<WasmLocal> getLocals() {
            return this.locals;
        }

        public final List<WasmInstr> getInstructions() {
            return this.instructions;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Defined(String name2, WasmSymbolReadOnly<WasmFunctionType> type, List<WasmLocal> locals, List<WasmInstr> instructions) {
            super(name2, type, null);
            Intrinsics.checkNotNullParameter(name2, "name");
            Intrinsics.checkNotNullParameter(type, "type");
            Intrinsics.checkNotNullParameter(locals, "locals");
            Intrinsics.checkNotNullParameter(instructions, "instructions");
            this.locals = locals;
            this.instructions = instructions;
        }
    }

    /* JADX INFO: compiled from: Declarations.kt */
    @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B#\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tR\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/wasm/ir/WasmFunction$Imported;", "Lorg/jetbrains/kotlin/wasm/ir/WasmFunction;", "name", "", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/wasm/ir/WasmSymbolReadOnly;", "Lorg/jetbrains/kotlin/wasm/ir/WasmFunctionType;", "importPair", "Lorg/jetbrains/kotlin/wasm/ir/WasmImportDescriptor;", "(Ljava/lang/String;Lorg/jetbrains/kotlin/wasm/ir/WasmSymbolReadOnly;Lorg/jetbrains/kotlin/wasm/ir/WasmImportDescriptor;)V", "getImportPair", "()Lorg/jetbrains/kotlin/wasm/ir/WasmImportDescriptor;", "wasm.ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Imported extends WasmFunction {
        private final WasmImportDescriptor importPair;

        public final WasmImportDescriptor getImportPair() {
            return this.importPair;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Imported(String name2, WasmSymbolReadOnly<WasmFunctionType> type, WasmImportDescriptor importPair) {
            super(name2, type, null);
            Intrinsics.checkNotNullParameter(name2, "name");
            Intrinsics.checkNotNullParameter(type, "type");
            Intrinsics.checkNotNullParameter(importPair, "importPair");
            this.importPair = importPair;
        }
    }
}
