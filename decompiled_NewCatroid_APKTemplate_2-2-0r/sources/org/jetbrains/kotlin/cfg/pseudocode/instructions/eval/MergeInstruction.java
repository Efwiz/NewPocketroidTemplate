package org.jetbrains.kotlin.cfg.pseudocode.instructions.eval;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cfg.pseudocode.PseudoValue;
import org.jetbrains.kotlin.cfg.pseudocode.PseudoValueFactory;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.BlockScope;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.InstructionVisitor;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.InstructionVisitorWithResult;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.psi.KtElement;

/* JADX INFO: compiled from: operationInstructions.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u0001B-\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\u0012\u0006\u0010\t\u001a\u00020\n¢\u0006\u0002\u0010\u000bB%\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007¢\u0006\u0002\u0010\fJ\u0010\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0016J!\u0010\u0010\u001a\u0002H\u0014\"\u0004\b\u0000\u0010\u00142\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u0002H\u00140\u0015H\u0016¢\u0006\u0002\u0010\u0016J\b\u0010\u0017\u001a\u00020\u0001H\u0014J\b\u0010\u0018\u001a\u00020\u0019H\u0016R\u0014\u0010\r\u001a\u00020\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000f¨\u0006\u001a"}, d2 = {"Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/eval/MergeInstruction;", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/eval/OperationInstruction;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/psi/KtElement;", "blockScope", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/BlockScope;", "inputValues", "", "Lorg/jetbrains/kotlin/cfg/pseudocode/PseudoValue;", "factory", "Lorg/jetbrains/kotlin/cfg/pseudocode/PseudoValueFactory;", "(Lorg/jetbrains/kotlin/psi/KtElement;Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/BlockScope;Ljava/util/List;Lorg/jetbrains/kotlin/cfg/pseudocode/PseudoValueFactory;)V", "(Lorg/jetbrains/kotlin/psi/KtElement;Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/BlockScope;Ljava/util/List;)V", "outputValue", "getOutputValue", "()Lorg/jetbrains/kotlin/cfg/pseudocode/PseudoValue;", "accept", "", "visitor", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/InstructionVisitor;", "R", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/InstructionVisitorWithResult;", "(Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/InstructionVisitorWithResult;)Ljava/lang/Object;", "createCopy", "toString", "", "cfg"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class MergeInstruction extends OperationInstruction {
    private MergeInstruction(KtElement ktElement, BlockScope blockScope, List<? extends PseudoValue> list) {
        super(ktElement, blockScope, list);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public MergeInstruction(KtElement element, BlockScope blockScope, List<? extends PseudoValue> inputValues, PseudoValueFactory factory) {
        this(element, blockScope, inputValues);
        Intrinsics.checkNotNullParameter(element, "element");
        Intrinsics.checkNotNullParameter(blockScope, "blockScope");
        Intrinsics.checkNotNullParameter(inputValues, "inputValues");
        Intrinsics.checkNotNullParameter(factory, "factory");
        OperationInstruction.setResult$default(this, factory, null, 2, null);
    }

    @Override // org.jetbrains.kotlin.cfg.pseudocode.instructions.eval.OperationInstruction, org.jetbrains.kotlin.cfg.pseudocode.instructions.eval.InstructionWithValue
    /* JADX INFO: renamed from: getOutputValue */
    public PseudoValue getResultValue() {
        PseudoValue resultValue = getResultValue();
        Intrinsics.checkNotNull(resultValue);
        return resultValue;
    }

    @Override // org.jetbrains.kotlin.cfg.pseudocode.instructions.Instruction
    public void accept(InstructionVisitor visitor) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        visitor.visitMerge(this);
    }

    @Override // org.jetbrains.kotlin.cfg.pseudocode.instructions.Instruction
    public <R> R accept(InstructionVisitorWithResult<? extends R> visitor) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        return visitor.visitMerge(this);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.cfg.pseudocode.instructions.InstructionImpl
    public OperationInstruction createCopy() {
        return new MergeInstruction(getElement(), getBlockScope(), getInputValues()).setResult(getResultValue());
    }

    public String toString() {
        return renderInstruction("merge", render(getElement()));
    }
}
