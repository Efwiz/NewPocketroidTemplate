package org.jetbrains.kotlin.wasm.ir;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.reandroid.arsc.value.CompoundEntry;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;

/* JADX INFO: compiled from: Declarations.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\u0018\u00002\u00020\u0001:\u0001\u0010B#\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tR\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/wasm/ir/WasmElement;", "Lorg/jetbrains/kotlin/wasm/ir/WasmNamedModuleField;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/wasm/ir/WasmType;", CompoundEntry.NAME_values, "", "Lorg/jetbrains/kotlin/wasm/ir/WasmTable$Value;", "mode", "Lorg/jetbrains/kotlin/wasm/ir/WasmElement$Mode;", "(Lorg/jetbrains/kotlin/wasm/ir/WasmType;Ljava/util/List;Lorg/jetbrains/kotlin/wasm/ir/WasmElement$Mode;)V", "getMode", "()Lorg/jetbrains/kotlin/wasm/ir/WasmElement$Mode;", "getType", "()Lorg/jetbrains/kotlin/wasm/ir/WasmType;", "getValues", "()Ljava/util/List;", "Mode", "wasm.ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class WasmElement extends WasmNamedModuleField {
    private final Mode mode;
    private final WasmType type;
    private final List<WasmTable.Value> values;

    public final WasmType getType() {
        return this.type;
    }

    public final List<WasmTable.Value> getValues() {
        return this.values;
    }

    public final Mode getMode() {
        return this.mode;
    }

    /* JADX INFO: compiled from: Declarations.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0003\u0003\u0004\u0005B\u0007\b\u0004¢\u0006\u0002\u0010\u0002\u0082\u0001\u0003\u0006\u0007\b¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/wasm/ir/WasmElement$Mode;", "", "()V", "Active", "Declarative", "Passive", "Lorg/jetbrains/kotlin/wasm/ir/WasmElement$Mode$Active;", "Lorg/jetbrains/kotlin/wasm/ir/WasmElement$Mode$Declarative;", "Lorg/jetbrains/kotlin/wasm/ir/WasmElement$Mode$Passive;", "wasm.ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static abstract class Mode {
        public /* synthetic */ Mode(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX INFO: compiled from: Declarations.kt */
        @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/wasm/ir/WasmElement$Mode$Passive;", "Lorg/jetbrains/kotlin/wasm/ir/WasmElement$Mode;", "()V", "wasm.ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class Passive extends Mode {
            public static final Passive INSTANCE = new Passive();

            private Passive() {
                super(null);
            }
        }

        private Mode() {
        }

        /* JADX INFO: compiled from: Declarations.kt */
        @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u001b\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\u0002\u0010\u0007R\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/wasm/ir/WasmElement$Mode$Active;", "Lorg/jetbrains/kotlin/wasm/ir/WasmElement$Mode;", "table", "Lorg/jetbrains/kotlin/wasm/ir/WasmTable;", TypedValues.CycleType.S_WAVE_OFFSET, "", "Lorg/jetbrains/kotlin/wasm/ir/WasmInstr;", "(Lorg/jetbrains/kotlin/wasm/ir/WasmTable;Ljava/util/List;)V", "getOffset", "()Ljava/util/List;", "getTable", "()Lorg/jetbrains/kotlin/wasm/ir/WasmTable;", "wasm.ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class Active extends Mode {
            private final List<WasmInstr> offset;
            private final WasmTable table;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            public Active(WasmTable table, List<? extends WasmInstr> offset) {
                super(null);
                Intrinsics.checkNotNullParameter(table, "table");
                Intrinsics.checkNotNullParameter(offset, "offset");
                this.table = table;
                this.offset = offset;
            }

            public final List<WasmInstr> getOffset() {
                return this.offset;
            }

            public final WasmTable getTable() {
                return this.table;
            }
        }

        /* JADX INFO: compiled from: Declarations.kt */
        @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/wasm/ir/WasmElement$Mode$Declarative;", "Lorg/jetbrains/kotlin/wasm/ir/WasmElement$Mode;", "()V", "wasm.ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class Declarative extends Mode {
            public static final Declarative INSTANCE = new Declarative();

            private Declarative() {
                super(null);
            }
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public WasmElement(WasmType type, List<? extends WasmTable.Value> values, Mode mode) {
        super(null);
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(values, "values");
        Intrinsics.checkNotNullParameter(mode, "mode");
        this.type = type;
        this.values = values;
        this.mode = mode;
    }
}
