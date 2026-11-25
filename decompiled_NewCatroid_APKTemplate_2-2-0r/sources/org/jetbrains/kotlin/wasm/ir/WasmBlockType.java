package org.jetbrains.kotlin.wasm.ir;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;

/* JADX INFO: compiled from: Types.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0002\u0003\u0004B\u0007\b\u0004¢\u0006\u0002\u0010\u0002\u0082\u0001\u0002\u0005\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/wasm/ir/WasmBlockType;", "", "()V", "Function", "Value", "Lorg/jetbrains/kotlin/wasm/ir/WasmBlockType$Function;", "Lorg/jetbrains/kotlin/wasm/ir/WasmBlockType$Value;", "wasm.ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class WasmBlockType {
    public /* synthetic */ WasmBlockType(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    /* JADX INFO: compiled from: Types.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/wasm/ir/WasmBlockType$Function;", "Lorg/jetbrains/kotlin/wasm/ir/WasmBlockType;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/wasm/ir/WasmFunctionType;", "(Lorg/jetbrains/kotlin/wasm/ir/WasmFunctionType;)V", "getType", "()Lorg/jetbrains/kotlin/wasm/ir/WasmFunctionType;", "wasm.ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Function extends WasmBlockType {
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

    private WasmBlockType() {
    }

    /* JADX INFO: compiled from: Types.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/wasm/ir/WasmBlockType$Value;", "Lorg/jetbrains/kotlin/wasm/ir/WasmBlockType;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/wasm/ir/WasmType;", "(Lorg/jetbrains/kotlin/wasm/ir/WasmType;)V", "getType", "()Lorg/jetbrains/kotlin/wasm/ir/WasmType;", "wasm.ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Value extends WasmBlockType {
        private final WasmType type;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Value(WasmType type) {
            super(null);
            Intrinsics.checkNotNullParameter(type, "type");
            this.type = type;
        }

        public final WasmType getType() {
            return this.type;
        }
    }
}
