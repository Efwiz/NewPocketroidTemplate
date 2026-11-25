package org.jetbrains.kotlin.cfg.pseudocode.instructions.jumps;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cfg.Label;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.BlockScope;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.InstructionVisitor;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.InstructionVisitorWithResult;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.psi.KtElement;

/* JADX INFO: compiled from: ReturnNoValueInstruction.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\u0003¢\u0006\u0002\u0010\tJ\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J!\u0010\f\u001a\u0002H\u0010\"\u0004\b\u0000\u0010\u00102\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u0002H\u00100\u0011H\u0016¢\u0006\u0002\u0010\u0012J\u0018\u0010\u0013\u001a\u00020\u00012\u0006\u0010\u0014\u001a\u00020\u00072\u0006\u0010\u0004\u001a\u00020\u0005H\u0014J\b\u0010\u0015\u001a\u00020\u0016H\u0016R\u0011\u0010\b\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/jumps/ReturnNoValueInstruction;", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/jumps/AbstractJumpInstruction;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/psi/KtElement;", "blockScope", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/BlockScope;", "targetLabel", "Lorg/jetbrains/kotlin/cfg/Label;", "subroutine", "(Lorg/jetbrains/kotlin/psi/KtElement;Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/BlockScope;Lorg/jetbrains/kotlin/cfg/Label;Lorg/jetbrains/kotlin/psi/KtElement;)V", "getSubroutine", "()Lorg/jetbrains/kotlin/psi/KtElement;", "accept", "", "visitor", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/InstructionVisitor;", "R", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/InstructionVisitorWithResult;", "(Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/InstructionVisitorWithResult;)Ljava/lang/Object;", "createCopy", "newLabel", "toString", "", "cfg"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ReturnNoValueInstruction extends AbstractJumpInstruction {
    private final KtElement subroutine;

    public final KtElement getSubroutine() {
        return this.subroutine;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ReturnNoValueInstruction(KtElement element, BlockScope blockScope, Label targetLabel, KtElement subroutine) {
        super(element, targetLabel, blockScope);
        Intrinsics.checkNotNullParameter(element, "element");
        Intrinsics.checkNotNullParameter(blockScope, "blockScope");
        Intrinsics.checkNotNullParameter(targetLabel, "targetLabel");
        Intrinsics.checkNotNullParameter(subroutine, "subroutine");
        this.subroutine = subroutine;
    }

    @Override // org.jetbrains.kotlin.cfg.pseudocode.instructions.Instruction
    public void accept(InstructionVisitor visitor) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        visitor.visitReturnNoValue(this);
    }

    @Override // org.jetbrains.kotlin.cfg.pseudocode.instructions.Instruction
    public <R> R accept(InstructionVisitorWithResult<? extends R> visitor) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        return visitor.visitReturnNoValue(this);
    }

    public String toString() {
        return "ret " + getTargetLabel();
    }

    @Override // org.jetbrains.kotlin.cfg.pseudocode.instructions.jumps.AbstractJumpInstruction
    protected AbstractJumpInstruction createCopy(Label newLabel, BlockScope blockScope) {
        Intrinsics.checkNotNullParameter(newLabel, "newLabel");
        Intrinsics.checkNotNullParameter(blockScope, "blockScope");
        return new ReturnNoValueInstruction(getElement(), blockScope, newLabel, this.subroutine);
    }
}
