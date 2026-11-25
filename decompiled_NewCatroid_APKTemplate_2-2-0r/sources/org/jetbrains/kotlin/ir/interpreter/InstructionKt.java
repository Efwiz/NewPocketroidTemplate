package org.jetbrains.kotlin.ir.interpreter;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.interpreter.stack.CallStack;

/* JADX INFO: compiled from: Instruction.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0014\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u0016\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\b\u0010\u0003\u001a\u0004\u0018\u00010\u0004H\u0000\u001a\u0014\u0010\u0005\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004H\u0000¨\u0006\u0006"}, d2 = {"pushCompoundInstruction", "", "Lorg/jetbrains/kotlin/ir/interpreter/stack/CallStack;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/ir/IrElement;", "pushSimpleInstruction", "ir.interpreter"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class InstructionKt {
    public static final void pushSimpleInstruction(CallStack callStack, IrElement element) {
        Intrinsics.checkNotNullParameter(callStack, "<this>");
        Intrinsics.checkNotNullParameter(element, "element");
        callStack.pushInstruction(new SimpleInstruction(element));
    }

    public static final void pushCompoundInstruction(CallStack callStack, IrElement irElement) {
        Intrinsics.checkNotNullParameter(callStack, "<this>");
        callStack.pushInstruction(new CompoundInstruction(irElement));
    }
}
