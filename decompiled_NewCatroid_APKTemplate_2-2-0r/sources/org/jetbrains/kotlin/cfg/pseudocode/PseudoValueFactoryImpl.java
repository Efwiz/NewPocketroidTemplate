package org.jetbrains.kotlin.cfg.pseudocode;

import kotlin.Metadata;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.eval.InstructionWithValue;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.psi.KtElement;

/* JADX INFO: compiled from: PseudoValueImpl.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0016\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001c\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\b2\b\u0010\t\u001a\u0004\u0018\u00010\nH\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/cfg/pseudocode/PseudoValueFactoryImpl;", "Lorg/jetbrains/kotlin/cfg/pseudocode/PseudoValueFactory;", "()V", "lastIndex", "", "newValue", "Lorg/jetbrains/kotlin/cfg/pseudocode/PseudoValue;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/psi/KtElement;", "instruction", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/eval/InstructionWithValue;", "cfg"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class PseudoValueFactoryImpl implements PseudoValueFactory {
    private int lastIndex;

    @Override // org.jetbrains.kotlin.cfg.pseudocode.PseudoValueFactory
    public PseudoValue newValue(KtElement element, InstructionWithValue instruction) {
        StringBuilder sbAppend = new StringBuilder().append(instruction != null ? "" : "!").append("<v");
        int i = this.lastIndex;
        this.lastIndex = i + 1;
        return new PseudoValueImpl(sbAppend.append(i).append('>').toString(), element, instruction);
    }
}
