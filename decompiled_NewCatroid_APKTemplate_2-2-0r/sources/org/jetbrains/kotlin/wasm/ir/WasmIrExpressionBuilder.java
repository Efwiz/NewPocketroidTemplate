package org.jetbrains.kotlin.wasm.ir;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.List;
import java.util.ListIterator;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.utils.addToStdlib.AddToStdlibKt;
import org.jetbrains.kotlin.wasm.ir.source.location.SourceLocation;

/* JADX INFO: compiled from: WasmIrExpressionBuilder.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u000b\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0005J-\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00142\u0006\u0010\u0019\u001a\u00020\u001a2\u000e\u0010\u001b\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u001d0\u001cH\u0002¢\u0006\u0002\u0010\u001eJ1\u0010\u001f\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00142\u0006\u0010\u0019\u001a\u00020\u001a2\u0012\u0010\u001b\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u001d0\u001c\"\u00020\u001dH\u0016¢\u0006\u0002\u0010\u001eJ\u0017\u0010 \u001a\u0004\u0018\u00010\u00072\u0006\u0010\u0018\u001a\u00020\u0014H\u0002¢\u0006\u0002\u0010!J\u0010\u0010\"\u001a\u00020\u00172\u0006\u0010#\u001a\u00020\u0007H\u0002R\u0012\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0082\u000e¢\u0006\u0004\n\u0002\u0010\bR\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u000e\u0010\u000b\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R$\u0010\r\u001a\u00020\u00072\u0006\u0010\f\u001a\u00020\u0007@VX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u0018\u0010\u0012\u001a\u00020\u0013*\u00020\u00148BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0015¨\u0006$"}, d2 = {"Lorg/jetbrains/kotlin/wasm/ir/WasmIrExpressionBuilder;", "Lorg/jetbrains/kotlin/wasm/ir/WasmExpressionBuilder;", "expression", "", "Lorg/jetbrains/kotlin/wasm/ir/WasmInstr;", "(Ljava/util/List;)V", "eatEverythingUntilLevel", "", "Ljava/lang/Integer;", "getExpression", "()Ljava/util/List;", "lastInstructionIndex", "value", "numberOfNestedBlocks", "getNumberOfNestedBlocks", "()I", "setNumberOfNestedBlocks", "(I)V", "isPseudoInstruction", "", "Lorg/jetbrains/kotlin/wasm/ir/WasmOp;", "(Lorg/jetbrains/kotlin/wasm/ir/WasmOp;)Z", "addInstruction", "", "op", FirebaseAnalytics.Param.LOCATION, "Lorg/jetbrains/kotlin/wasm/ir/source/location/SourceLocation;", "immediates", "", "Lorg/jetbrains/kotlin/wasm/ir/WasmImmediate;", "(Lorg/jetbrains/kotlin/wasm/ir/WasmOp;Lorg/jetbrains/kotlin/wasm/ir/source/location/SourceLocation;[Lorg/jetbrains/kotlin/wasm/ir/WasmImmediate;)V", "buildInstr", "getCurrentEatLevel", "(Lorg/jetbrains/kotlin/wasm/ir/WasmOp;)Ljava/lang/Integer;", "trimInstructionsUntil", "index", "wasm.ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class WasmIrExpressionBuilder extends WasmExpressionBuilder {
    private Integer eatEverythingUntilLevel;
    private final List<WasmInstr> expression;
    private int lastInstructionIndex;
    private int numberOfNestedBlocks;

    public final List<WasmInstr> getExpression() {
        return this.expression;
    }

    public WasmIrExpressionBuilder(List<WasmInstr> expression) {
        int iNextIndex;
        Intrinsics.checkNotNullParameter(expression, "expression");
        this.expression = expression;
        ListIterator<WasmInstr> listIterator = expression.listIterator(expression.size());
        while (listIterator.hasPrevious()) {
            if (!isPseudoInstruction(listIterator.previous().getOperator())) {
                iNextIndex = listIterator.nextIndex();
                this.lastInstructionIndex = iNextIndex;
            }
        }
        iNextIndex = -1;
        this.lastInstructionIndex = iNextIndex;
    }

    private final void addInstruction(WasmOp op, SourceLocation location, WasmImmediate[] immediates) {
        this.expression.add(new WasmInstrWithLocation(op, ArraysKt.toList(immediates), location));
        if (isPseudoInstruction(op)) {
            return;
        }
        this.lastInstructionIndex = CollectionsKt.getLastIndex(this.expression);
    }

    private final Integer getCurrentEatLevel(WasmOp op) {
        Integer num = this.eatEverythingUntilLevel;
        if (num == null) {
            return null;
        }
        int iIntValue = num.intValue();
        if (getNumberOfNestedBlocks() == iIntValue && WasmIrExpressionBuilderKt.isInCfgNode(op)) {
            this.eatEverythingUntilLevel = null;
            return null;
        }
        if (getNumberOfNestedBlocks() < iIntValue) {
            this.eatEverythingUntilLevel = null;
            return null;
        }
        return Integer.valueOf(iIntValue);
    }

    @Override // org.jetbrains.kotlin.wasm.ir.WasmExpressionBuilder
    public void buildInstr(WasmOp op, SourceLocation location, WasmImmediate... immediates) {
        Intrinsics.checkNotNullParameter(op, "op");
        Intrinsics.checkNotNullParameter(location, "location");
        Intrinsics.checkNotNullParameter(immediates, "immediates");
        Integer currentEatLevel = getCurrentEatLevel(op);
        if (currentEatLevel == null) {
            if (WasmIrExpressionBuilderKt.isOutCfgNode(op)) {
                this.eatEverythingUntilLevel = Integer.valueOf(getNumberOfNestedBlocks());
                addInstruction(op, location, immediates);
                return;
            }
        } else if (currentEatLevel.intValue() <= getNumberOfNestedBlocks()) {
            return;
        }
        int i = this.lastInstructionIndex;
        if (i == -1) {
            addInstruction(op, location, immediates);
            return;
        }
        WasmInstr wasmInstr = this.expression.get(i);
        WasmOp operator = wasmInstr.getOperator();
        if ((op == WasmOp.DROP || op == WasmOp.UNREACHABLE) && WasmIrExpressionBuilderKt.pureStacklessInstruction(operator)) {
            trimInstructionsUntil(this.lastInstructionIndex);
            return;
        }
        if (operator == WasmOp.LOCAL_SET && op == WasmOp.LOCAL_GET) {
            Object objFirstOrNull = CollectionsKt.firstOrNull((List<? extends Object>) wasmInstr.getImmediates());
            WasmImmediate.LocalIdx localIdx = objFirstOrNull instanceof WasmImmediate.LocalIdx ? (WasmImmediate.LocalIdx) objFirstOrNull : null;
            WasmSymbol<WasmLocal> value = localIdx != null ? localIdx.getValue() : null;
            if (value != null) {
                Object objFirstOrNull2 = ArraysKt.firstOrNull(immediates);
                WasmImmediate.LocalIdx localIdx2 = objFirstOrNull2 instanceof WasmImmediate.LocalIdx ? (WasmImmediate.LocalIdx) objFirstOrNull2 : null;
                if (Intrinsics.areEqual(localIdx2 != null ? localIdx2.getValue() : null, value)) {
                    trimInstructionsUntil(this.lastInstructionIndex);
                    addInstruction(WasmOp.LOCAL_TEE, location, immediates);
                    return;
                }
            }
        }
        addInstruction(op, location, immediates);
    }

    private final void trimInstructionsUntil(int index) {
        AddToStdlibKt.trimToSize(this.expression, index);
        this.lastInstructionIndex = index - 1;
    }

    @Override // org.jetbrains.kotlin.wasm.ir.WasmExpressionBuilder
    public int getNumberOfNestedBlocks() {
        return this.numberOfNestedBlocks;
    }

    @Override // org.jetbrains.kotlin.wasm.ir.WasmExpressionBuilder
    public void setNumberOfNestedBlocks(int i) {
        this.numberOfNestedBlocks = i;
    }

    private final boolean isPseudoInstruction(WasmOp wasmOp) {
        return wasmOp.getOpcode() == 65535;
    }
}
