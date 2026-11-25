package org.jetbrains.kotlin.cfg.pseudocode.instructions.jumps;

import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cfg.Label;
import org.jetbrains.kotlin.cfg.pseudocode.PseudoValue;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.BlockScope;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.Instruction;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.InstructionVisitor;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.InstructionVisitorWithResult;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.psi.KtElement;

/* JADX INFO: compiled from: ConditionalJumpInstruction.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0010\u001e\n\u0002\b\u000e\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u0001B/\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u000b¢\u0006\u0002\u0010\fJ\u0010\u0010#\u001a\u00020$2\u0006\u0010%\u001a\u00020&H\u0016J!\u0010#\u001a\u0002H'\"\u0004\b\u0000\u0010'2\f\u0010%\u001a\b\u0012\u0004\u0012\u0002H'0(H\u0016¢\u0006\u0002\u0010)J\u0018\u0010*\u001a\u00020\u00012\u0006\u0010+\u001a\u00020\t2\u0006\u0010\u0006\u001a\u00020\u0007H\u0014J\b\u0010,\u001a\u00020-H\u0016R\u0010\u0010\r\u001a\u0004\u0018\u00010\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u000f\u001a\u0004\u0018\u00010\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u000b0\u00118VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0013R\u001a\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u000e0\u00158VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0017R$\u0010\u0019\u001a\u00020\u000e2\u0006\u0010\u0018\u001a\u00020\u000e8F@FX\u0086\u000e¢\u0006\f\u001a\u0004\b\u001a\u0010\u001b\"\u0004\b\u001c\u0010\u001dR$\u0010\u001e\u001a\u00020\u000e2\u0006\u0010\u0018\u001a\u00020\u000e8F@FX\u0086\u000e¢\u0006\f\u001a\u0004\b\u001f\u0010\u001b\"\u0004\b \u0010\u001dR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b!\u0010\"¨\u0006."}, d2 = {"Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/jumps/ConditionalJumpInstruction;", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/jumps/AbstractJumpInstruction;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/psi/KtElement;", "onTrue", "", "blockScope", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/BlockScope;", "targetLabel", "Lorg/jetbrains/kotlin/cfg/Label;", "conditionValue", "Lorg/jetbrains/kotlin/cfg/pseudocode/PseudoValue;", "(Lorg/jetbrains/kotlin/psi/KtElement;ZLorg/jetbrains/kotlin/cfg/pseudocode/instructions/BlockScope;Lorg/jetbrains/kotlin/cfg/Label;Lorg/jetbrains/kotlin/cfg/pseudocode/PseudoValue;)V", "_nextOnFalse", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/Instruction;", "_nextOnTrue", "inputValues", "", "getInputValues", "()Ljava/util/List;", "nextInstructions", "", "getNextInstructions", "()Ljava/util/Collection;", "value", "nextOnFalse", "getNextOnFalse", "()Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/Instruction;", "setNextOnFalse", "(Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/Instruction;)V", "nextOnTrue", "getNextOnTrue", "setNextOnTrue", "getOnTrue", "()Z", "accept", "", "visitor", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/InstructionVisitor;", "R", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/InstructionVisitorWithResult;", "(Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/InstructionVisitorWithResult;)Ljava/lang/Object;", "createCopy", "newLabel", "toString", "", "cfg"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ConditionalJumpInstruction extends AbstractJumpInstruction {
    private Instruction _nextOnFalse;
    private Instruction _nextOnTrue;
    private final PseudoValue conditionValue;
    private final boolean onTrue;

    public final boolean getOnTrue() {
        return this.onTrue;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ConditionalJumpInstruction(KtElement element, boolean z, BlockScope blockScope, Label targetLabel, PseudoValue pseudoValue) {
        super(element, targetLabel, blockScope);
        Intrinsics.checkNotNullParameter(element, "element");
        Intrinsics.checkNotNullParameter(blockScope, "blockScope");
        Intrinsics.checkNotNullParameter(targetLabel, "targetLabel");
        this.onTrue = z;
        this.conditionValue = pseudoValue;
    }

    public final Instruction getNextOnTrue() {
        Instruction instruction = this._nextOnTrue;
        Intrinsics.checkNotNull(instruction);
        return instruction;
    }

    public final void setNextOnTrue(Instruction value) {
        Intrinsics.checkNotNullParameter(value, "value");
        this._nextOnTrue = outgoingEdgeTo(value);
    }

    public final Instruction getNextOnFalse() {
        Instruction instruction = this._nextOnFalse;
        Intrinsics.checkNotNull(instruction);
        return instruction;
    }

    public final void setNextOnFalse(Instruction value) {
        Intrinsics.checkNotNullParameter(value, "value");
        this._nextOnFalse = outgoingEdgeTo(value);
    }

    @Override // org.jetbrains.kotlin.cfg.pseudocode.instructions.jumps.AbstractJumpInstruction, org.jetbrains.kotlin.cfg.pseudocode.instructions.Instruction
    public Collection<Instruction> getNextInstructions() {
        return CollectionsKt.listOf((Object[]) new Instruction[]{getNextOnFalse(), getNextOnTrue()});
    }

    @Override // org.jetbrains.kotlin.cfg.pseudocode.instructions.InstructionImpl, org.jetbrains.kotlin.cfg.pseudocode.instructions.Instruction
    public List<PseudoValue> getInputValues() {
        return CollectionsKt.listOfNotNull(this.conditionValue);
    }

    @Override // org.jetbrains.kotlin.cfg.pseudocode.instructions.Instruction
    public void accept(InstructionVisitor visitor) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        visitor.visitConditionalJump(this);
    }

    @Override // org.jetbrains.kotlin.cfg.pseudocode.instructions.Instruction
    public <R> R accept(InstructionVisitorWithResult<? extends R> visitor) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        return visitor.visitConditionalJump(this);
    }

    public String toString() {
        String str;
        String str2 = this.onTrue ? "jt" : "jf";
        PseudoValue pseudoValue = this.conditionValue;
        if (pseudoValue == null || (str = "|" + pseudoValue) == null) {
            str = "";
        }
        return str2 + '(' + getTargetLabel().getName() + str + ')';
    }

    @Override // org.jetbrains.kotlin.cfg.pseudocode.instructions.jumps.AbstractJumpInstruction
    protected AbstractJumpInstruction createCopy(Label newLabel, BlockScope blockScope) {
        Intrinsics.checkNotNullParameter(newLabel, "newLabel");
        Intrinsics.checkNotNullParameter(blockScope, "blockScope");
        return new ConditionalJumpInstruction(getElement(), this.onTrue, blockScope, newLabel, this.conditionValue);
    }
}
