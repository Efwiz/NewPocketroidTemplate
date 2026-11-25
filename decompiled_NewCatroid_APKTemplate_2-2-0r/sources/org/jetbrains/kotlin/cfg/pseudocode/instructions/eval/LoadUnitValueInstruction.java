package org.jetbrains.kotlin.cfg.pseudocode.instructions.eval;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.BlockScope;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.InstructionImpl;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.InstructionVisitor;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.InstructionVisitorWithResult;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.InstructionWithNext;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtExpression;

/* JADX INFO: compiled from: LoadUnitValueInstruction.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J!\u0010\u0007\u001a\u0002H\u000b\"\u0004\b\u0000\u0010\u000b2\f\u0010\t\u001a\b\u0012\u0004\u0012\u0002H\u000b0\fH\u0016¢\u0006\u0002\u0010\rJ\b\u0010\u000e\u001a\u00020\u000fH\u0014J\b\u0010\u0010\u001a\u00020\u0011H\u0016¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/eval/LoadUnitValueInstruction;", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/InstructionWithNext;", "expression", "Lorg/jetbrains/kotlin/psi/KtExpression;", "blockScope", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/BlockScope;", "(Lorg/jetbrains/kotlin/psi/KtExpression;Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/BlockScope;)V", "accept", "", "visitor", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/InstructionVisitor;", "R", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/InstructionVisitorWithResult;", "(Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/InstructionVisitorWithResult;)Ljava/lang/Object;", "createCopy", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/InstructionImpl;", "toString", "", "cfg"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class LoadUnitValueInstruction extends InstructionWithNext {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LoadUnitValueInstruction(KtExpression expression, BlockScope blockScope) {
        super(expression, blockScope);
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(blockScope, "blockScope");
    }

    @Override // org.jetbrains.kotlin.cfg.pseudocode.instructions.Instruction
    public void accept(InstructionVisitor visitor) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        visitor.visitLoadUnitValue(this);
    }

    @Override // org.jetbrains.kotlin.cfg.pseudocode.instructions.Instruction
    public <R> R accept(InstructionVisitorWithResult<? extends R> visitor) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        return visitor.visitLoadUnitValue(this);
    }

    public String toString() {
        return "read (Unit)";
    }

    @Override // org.jetbrains.kotlin.cfg.pseudocode.instructions.InstructionImpl
    protected InstructionImpl createCopy() {
        KtElement element = getElement();
        Intrinsics.checkNotNull(element, "null cannot be cast to non-null type org.jetbrains.kotlin.psi.KtExpression");
        return new LoadUnitValueInstruction((KtExpression) element, getBlockScope());
    }
}
