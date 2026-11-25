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
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.psi.KtElement;

/* JADX INFO: compiled from: operationInstructions.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u0001B?\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r¢\u0006\u0002\u0010\u000eB+\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b\u0012\u0006\u0010\n\u001a\u00020\u000b¢\u0006\u0002\u0010\u000fJ\u0010\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001cH\u0016J!\u0010\u0019\u001a\u0002H\u001d\"\u0004\b\u0000\u0010\u001d2\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u0002H\u001d0\u001eH\u0016¢\u0006\u0002\u0010\u001fJ\b\u0010 \u001a\u00020\u0001H\u0014J\b\u0010!\u001a\u00020\"H\u0016R\u0011\u0010\n\u001a\u00020\u000b¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0014\u0010\u0012\u001a\u00020\t8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014R\u0011\u0010\u0015\u001a\u00020\u00168F¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0018¨\u0006#"}, d2 = {"Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/eval/MagicInstruction;", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/eval/OperationInstruction;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/psi/KtElement;", "valueElement", "blockScope", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/BlockScope;", "inputValues", "", "Lorg/jetbrains/kotlin/cfg/pseudocode/PseudoValue;", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/eval/MagicKind;", "factory", "Lorg/jetbrains/kotlin/cfg/pseudocode/PseudoValueFactory;", "(Lorg/jetbrains/kotlin/psi/KtElement;Lorg/jetbrains/kotlin/psi/KtElement;Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/BlockScope;Ljava/util/List;Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/eval/MagicKind;Lorg/jetbrains/kotlin/cfg/pseudocode/PseudoValueFactory;)V", "(Lorg/jetbrains/kotlin/psi/KtElement;Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/BlockScope;Ljava/util/List;Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/eval/MagicKind;)V", "getKind", "()Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/eval/MagicKind;", "outputValue", "getOutputValue", "()Lorg/jetbrains/kotlin/cfg/pseudocode/PseudoValue;", "synthetic", "", "getSynthetic", "()Z", "accept", "", "visitor", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/InstructionVisitor;", "R", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/InstructionVisitorWithResult;", "(Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/InstructionVisitorWithResult;)Ljava/lang/Object;", "createCopy", "toString", "", "cfg"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class MagicInstruction extends OperationInstruction {
    private final MagicKind kind;

    public final MagicKind getKind() {
        return this.kind;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MagicInstruction(KtElement element, BlockScope blockScope, List<? extends PseudoValue> inputValues, MagicKind kind) {
        super(element, blockScope, inputValues);
        Intrinsics.checkNotNullParameter(element, "element");
        Intrinsics.checkNotNullParameter(blockScope, "blockScope");
        Intrinsics.checkNotNullParameter(inputValues, "inputValues");
        Intrinsics.checkNotNullParameter(kind, "kind");
        this.kind = kind;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public MagicInstruction(KtElement element, KtElement ktElement, BlockScope blockScope, List<? extends PseudoValue> inputValues, MagicKind kind, PseudoValueFactory factory) {
        this(element, blockScope, inputValues, kind);
        Intrinsics.checkNotNullParameter(element, "element");
        Intrinsics.checkNotNullParameter(blockScope, "blockScope");
        Intrinsics.checkNotNullParameter(inputValues, "inputValues");
        Intrinsics.checkNotNullParameter(kind, "kind");
        Intrinsics.checkNotNullParameter(factory, "factory");
        setResult(factory, ktElement);
    }

    public final boolean getSynthetic() {
        return getResultValue().getElement() == null;
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
        visitor.visitMagic(this);
    }

    @Override // org.jetbrains.kotlin.cfg.pseudocode.instructions.Instruction
    public <R> R accept(InstructionVisitorWithResult<? extends R> visitor) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        return visitor.visitMagic(this);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.cfg.pseudocode.instructions.InstructionImpl
    public OperationInstruction createCopy() {
        return new MagicInstruction(getElement(), getBlockScope(), getInputValues(), this.kind).setResult(getResultValue());
    }

    public String toString() {
        return renderInstruction("magic[" + this.kind + ']', render(getElement()));
    }
}
