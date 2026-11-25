package org.jetbrains.kotlin.cfg.pseudocode.instructions.jumps;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cfg.Label;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.BlockScope;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.InstructionVisitor;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.InstructionVisitorWithResult;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.psi.KtElement;

/* JADX INFO: compiled from: UnconditionalJumpInstruction.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0016J!\u0010\t\u001a\u0002H\r\"\u0004\b\u0000\u0010\r2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u0002H\r0\u000eH\u0016¢\u0006\u0002\u0010\u000fJ\u0018\u0010\u0010\u001a\u00020\u00012\u0006\u0010\u0011\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0014J\b\u0010\u0012\u001a\u00020\u0013H\u0016¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/jumps/UnconditionalJumpInstruction;", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/jumps/AbstractJumpInstruction;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/psi/KtElement;", "targetLabel", "Lorg/jetbrains/kotlin/cfg/Label;", "blockScope", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/BlockScope;", "(Lorg/jetbrains/kotlin/psi/KtElement;Lorg/jetbrains/kotlin/cfg/Label;Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/BlockScope;)V", "accept", "", "visitor", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/InstructionVisitor;", "R", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/InstructionVisitorWithResult;", "(Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/InstructionVisitorWithResult;)Ljava/lang/Object;", "createCopy", "newLabel", "toString", "", "cfg"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class UnconditionalJumpInstruction extends AbstractJumpInstruction {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public UnconditionalJumpInstruction(KtElement element, Label targetLabel, BlockScope blockScope) {
        super(element, targetLabel, blockScope);
        Intrinsics.checkNotNullParameter(element, "element");
        Intrinsics.checkNotNullParameter(targetLabel, "targetLabel");
        Intrinsics.checkNotNullParameter(blockScope, "blockScope");
    }

    @Override // org.jetbrains.kotlin.cfg.pseudocode.instructions.Instruction
    public void accept(InstructionVisitor visitor) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        visitor.visitUnconditionalJump(this);
    }

    @Override // org.jetbrains.kotlin.cfg.pseudocode.instructions.Instruction
    public <R> R accept(InstructionVisitorWithResult<? extends R> visitor) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        return visitor.visitUnconditionalJump(this);
    }

    public String toString() {
        return "jmp(" + getTargetLabel().getName() + ')';
    }

    @Override // org.jetbrains.kotlin.cfg.pseudocode.instructions.jumps.AbstractJumpInstruction
    protected AbstractJumpInstruction createCopy(Label newLabel, BlockScope blockScope) {
        Intrinsics.checkNotNullParameter(newLabel, "newLabel");
        Intrinsics.checkNotNullParameter(blockScope, "blockScope");
        return new UnconditionalJumpInstruction(getElement(), newLabel, blockScope);
    }
}
