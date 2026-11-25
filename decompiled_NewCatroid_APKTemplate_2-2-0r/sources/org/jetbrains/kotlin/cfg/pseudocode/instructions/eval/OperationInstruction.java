package org.jetbrains.kotlin.cfg.pseudocode.instructions.eval;

import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cfg.pseudocode.PseudoValue;
import org.jetbrains.kotlin.cfg.pseudocode.PseudoValueFactory;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.BlockScope;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.InstructionWithNext;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.psi.KtElement;

/* JADX INFO: compiled from: operationInstructions.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\b&\u0018\u00002\u00020\u00012\u00020\u0002B%\b\u0004\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b¢\u0006\u0002\u0010\nJ\u0018\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00152\u0006\u0010\u0017\u001a\u00020\u0015H\u0004J\u0012\u0010\u0018\u001a\u00020\u00002\b\u0010\u0019\u001a\u0004\u0018\u00010\tH\u0004J\u001e\u0010\u0018\u001a\u00020\u00002\b\u0010\u001a\u001a\u0004\u0018\u00010\u001b2\n\b\u0002\u0010\u001c\u001a\u0004\u0018\u00010\u0004H\u0004R\u001a\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0016\u0010\r\u001a\u0004\u0018\u00010\t8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR\u001c\u0010\u0010\u001a\u0004\u0018\u00010\tX\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u000f\"\u0004\b\u0012\u0010\u0013¨\u0006\u001d"}, d2 = {"Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/eval/OperationInstruction;", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/InstructionWithNext;", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/eval/InstructionWithValue;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/psi/KtElement;", "blockScope", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/BlockScope;", "inputValues", "", "Lorg/jetbrains/kotlin/cfg/pseudocode/PseudoValue;", "(Lorg/jetbrains/kotlin/psi/KtElement;Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/BlockScope;Ljava/util/List;)V", "getInputValues", "()Ljava/util/List;", "outputValue", "getOutputValue", "()Lorg/jetbrains/kotlin/cfg/pseudocode/PseudoValue;", "resultValue", "getResultValue", "setResultValue", "(Lorg/jetbrains/kotlin/cfg/pseudocode/PseudoValue;)V", "renderInstruction", "", "name", "desc", "setResult", "value", "factory", "Lorg/jetbrains/kotlin/cfg/pseudocode/PseudoValueFactory;", "valueElement", "cfg"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class OperationInstruction extends InstructionWithNext implements InstructionWithValue {
    private final List<PseudoValue> inputValues;
    private PseudoValue resultValue;

    @Override // org.jetbrains.kotlin.cfg.pseudocode.instructions.InstructionImpl, org.jetbrains.kotlin.cfg.pseudocode.instructions.Instruction
    public List<PseudoValue> getInputValues() {
        return this.inputValues;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    protected OperationInstruction(KtElement element, BlockScope blockScope, List<? extends PseudoValue> inputValues) {
        super(element, blockScope);
        Intrinsics.checkNotNullParameter(element, "element");
        Intrinsics.checkNotNullParameter(blockScope, "blockScope");
        Intrinsics.checkNotNullParameter(inputValues, "inputValues");
        this.inputValues = inputValues;
    }

    protected final PseudoValue getResultValue() {
        return this.resultValue;
    }

    protected final void setResultValue(PseudoValue pseudoValue) {
        this.resultValue = pseudoValue;
    }

    /* JADX INFO: renamed from: getOutputValue, reason: from getter */
    public PseudoValue getResultValue() {
        return this.resultValue;
    }

    protected final String renderInstruction(String name2, String desc) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(desc, "desc");
        return name2 + '(' + desc + (!getInputValues().isEmpty() ? "|" + CollectionsKt.joinToString$default(getInputValues(), ", ", null, null, 0, null, null, 62, null) + ')' : Constant.AFTER_QUTO) + (this.resultValue != null ? " -> " + this.resultValue : "");
    }

    protected final OperationInstruction setResult(PseudoValue value) {
        this.resultValue = value;
        return this;
    }

    public static /* synthetic */ OperationInstruction setResult$default(OperationInstruction operationInstruction, PseudoValueFactory pseudoValueFactory, KtElement ktElement, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: setResult");
        }
        if ((i & 2) != 0) {
            ktElement = operationInstruction.getElement();
        }
        return operationInstruction.setResult(pseudoValueFactory, ktElement);
    }

    protected final OperationInstruction setResult(PseudoValueFactory factory, KtElement valueElement) {
        return setResult(factory != null ? factory.newValue(valueElement, this) : null);
    }
}
