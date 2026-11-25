package org.jetbrains.kotlin.ir.interpreter;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.ir.IrElement;

/* JADX INFO: compiled from: Instruction.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0000\u0018\u00002\u00020\u0001B\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0005R\u0016\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/ir/interpreter/CustomInstruction;", "Lorg/jetbrains/kotlin/ir/interpreter/Instruction;", "evaluate", "Lkotlin/Function0;", "", "(Lkotlin/jvm/functions/Function0;)V", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/ir/IrElement;", "getElement", "()Lorg/jetbrains/kotlin/ir/IrElement;", "getEvaluate", "()Lkotlin/jvm/functions/Function0;", "ir.interpreter"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CustomInstruction implements Instruction {
    private final IrElement element;
    private final Function0<Unit> evaluate;

    public CustomInstruction(Function0<Unit> evaluate) {
        Intrinsics.checkNotNullParameter(evaluate, "evaluate");
        this.evaluate = evaluate;
    }

    public final Function0<Unit> getEvaluate() {
        return this.evaluate;
    }

    @Override // org.jetbrains.kotlin.ir.interpreter.Instruction
    public IrElement getElement() {
        return this.element;
    }
}
