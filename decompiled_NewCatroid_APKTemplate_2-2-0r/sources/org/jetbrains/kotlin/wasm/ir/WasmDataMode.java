package org.jetbrains.kotlin.wasm.ir;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.wasm.ir.source.location.SourceLocation;

/* JADX INFO: compiled from: Declarations.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0002\u0003\u0004B\u0007\b\u0004¢\u0006\u0002\u0010\u0002\u0082\u0001\u0002\u0005\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/wasm/ir/WasmDataMode;", "", "()V", "Active", "Passive", "Lorg/jetbrains/kotlin/wasm/ir/WasmDataMode$Active;", "Lorg/jetbrains/kotlin/wasm/ir/WasmDataMode$Passive;", "wasm.ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class WasmDataMode {
    public /* synthetic */ WasmDataMode(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    private WasmDataMode() {
    }

    /* JADX INFO: compiled from: Declarations.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u0017\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005B\u001b\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006¢\u0006\u0002\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/wasm/ir/WasmDataMode$Active;", "Lorg/jetbrains/kotlin/wasm/ir/WasmDataMode;", "memoryIdx", "", TypedValues.CycleType.S_WAVE_OFFSET, "(II)V", "", "Lorg/jetbrains/kotlin/wasm/ir/WasmInstr;", "(ILjava/util/List;)V", "getMemoryIdx", "()I", "getOffset", "()Ljava/util/List;", "wasm.ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Active extends WasmDataMode {
        private final int memoryIdx;
        private final List<WasmInstr> offset;

        public final int getMemoryIdx() {
            return this.memoryIdx;
        }

        public final List<WasmInstr> getOffset() {
            return this.offset;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Active(int i, List<WasmInstr> offset) {
            super(null);
            Intrinsics.checkNotNullParameter(offset, "offset");
            this.memoryIdx = i;
            this.offset = offset;
        }

        public Active(int i, int i2) {
            ArrayList arrayList = new ArrayList();
            new WasmIrExpressionBuilder(arrayList).buildConstI32(i2, SourceLocation.INSTANCE.NoLocation("Offset value for WasmDataMode.Active "));
            Unit unit = Unit.INSTANCE;
            this(i, arrayList);
        }
    }

    /* JADX INFO: compiled from: Declarations.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/wasm/ir/WasmDataMode$Passive;", "Lorg/jetbrains/kotlin/wasm/ir/WasmDataMode;", "()V", "wasm.ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Passive extends WasmDataMode {
        public static final Passive INSTANCE = new Passive();

        private Passive() {
            super(null);
        }
    }
}
