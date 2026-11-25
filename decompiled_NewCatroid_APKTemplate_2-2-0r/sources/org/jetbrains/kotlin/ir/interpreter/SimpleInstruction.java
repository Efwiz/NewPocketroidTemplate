package org.jetbrains.kotlin.ir.interpreter;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.ir.IrElement;

/* JADX INFO: compiled from: Instruction.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/ir/interpreter/SimpleInstruction;", "Lorg/jetbrains/kotlin/ir/interpreter/Instruction;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/ir/IrElement;", "(Lorg/jetbrains/kotlin/ir/IrElement;)V", "getElement", "()Lorg/jetbrains/kotlin/ir/IrElement;", "ir.interpreter"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SimpleInstruction implements Instruction {
    private final IrElement element;

    public SimpleInstruction(IrElement element) {
        Intrinsics.checkNotNullParameter(element, "element");
        this.element = element;
    }

    @Override // org.jetbrains.kotlin.ir.interpreter.Instruction
    public IrElement getElement() {
        return this.element;
    }
}
