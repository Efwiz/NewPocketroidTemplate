package org.jetbrains.kotlin.cfg.pseudocode;

import kotlin.Metadata;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.eval.InstructionWithValue;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.psi.KtElement;

/* JADX INFO: compiled from: ControlFlowInstructionsGenerator.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u001d\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\u001c\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u00052\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007H\u0016¨\u0006\b"}, d2 = {"org/jetbrains/kotlin/cfg/pseudocode/ControlFlowInstructionsGenerator$ControlFlowInstructionsGeneratorWorker$valueFactory$1", "Lorg/jetbrains/kotlin/cfg/pseudocode/PseudoValueFactoryImpl;", "newValue", "Lorg/jetbrains/kotlin/cfg/pseudocode/PseudoValue;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/psi/KtElement;", "instruction", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/eval/InstructionWithValue;", "cfg"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ControlFlowInstructionsGenerator$ControlFlowInstructionsGeneratorWorker$valueFactory$1 extends PseudoValueFactoryImpl {
    final /* synthetic */ ControlFlowInstructionsGenerator.ControlFlowInstructionsGeneratorWorker this$0;

    ControlFlowInstructionsGenerator$ControlFlowInstructionsGeneratorWorker$valueFactory$1(ControlFlowInstructionsGenerator.ControlFlowInstructionsGeneratorWorker controlFlowInstructionsGeneratorWorker) {
        this.this$0 = controlFlowInstructionsGeneratorWorker;
    }

    @Override // org.jetbrains.kotlin.cfg.pseudocode.PseudoValueFactoryImpl, org.jetbrains.kotlin.cfg.pseudocode.PseudoValueFactory
    public PseudoValue newValue(KtElement element, InstructionWithValue instruction) {
        PseudoValue pseudoValueNewValue = super.newValue(element, instruction);
        if (element != null) {
            this.this$0.bindValue(pseudoValueNewValue, element);
        }
        return pseudoValueNewValue;
    }
}
