package org.jetbrains.kotlin.cfg.pseudocode.instructions.jumps;

import java.util.Collections;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cfg.Label;
import org.jetbrains.kotlin.cfg.pseudocode.PseudoValue;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.BlockScope;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.InstructionVisitor;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.InstructionVisitorWithResult;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtReturnExpression;

/* JADX INFO: compiled from: ReturnValueInstruction.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u0001B-\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b¢\u0006\u0002\u0010\fJ\u0010\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001cH\u0016J!\u0010\u0019\u001a\u0002H\u001d\"\u0004\b\u0000\u0010\u001d2\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u0002H\u001d0\u001eH\u0016¢\u0006\u0002\u0010\u001fJ\u0018\u0010 \u001a\u00020\u00012\u0006\u0010!\u001a\u00020\u00072\u0006\u0010\u0004\u001a\u00020\u0005H\u0014J\b\u0010\"\u001a\u00020#H\u0016R\u001a\u0010\r\u001a\b\u0012\u0004\u0012\u00020\t0\u000e8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u0013\u0010\u0011\u001a\u0004\u0018\u00010\u0012¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0011\u0010\b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0011\u0010\n\u001a\u00020\u000b¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018¨\u0006$"}, d2 = {"Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/jumps/ReturnValueInstruction;", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/jumps/AbstractJumpInstruction;", "returnExpression", "Lorg/jetbrains/kotlin/psi/KtExpression;", "blockScope", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/BlockScope;", "targetLabel", "Lorg/jetbrains/kotlin/cfg/Label;", "returnedValue", "Lorg/jetbrains/kotlin/cfg/pseudocode/PseudoValue;", "subroutine", "Lorg/jetbrains/kotlin/psi/KtElement;", "(Lorg/jetbrains/kotlin/psi/KtExpression;Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/BlockScope;Lorg/jetbrains/kotlin/cfg/Label;Lorg/jetbrains/kotlin/cfg/pseudocode/PseudoValue;Lorg/jetbrains/kotlin/psi/KtElement;)V", "inputValues", "", "getInputValues", "()Ljava/util/List;", "returnExpressionIfAny", "Lorg/jetbrains/kotlin/psi/KtReturnExpression;", "getReturnExpressionIfAny", "()Lorg/jetbrains/kotlin/psi/KtReturnExpression;", "getReturnedValue", "()Lorg/jetbrains/kotlin/cfg/pseudocode/PseudoValue;", "getSubroutine", "()Lorg/jetbrains/kotlin/psi/KtElement;", "accept", "", "visitor", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/InstructionVisitor;", "R", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/InstructionVisitorWithResult;", "(Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/InstructionVisitorWithResult;)Ljava/lang/Object;", "createCopy", "newLabel", "toString", "", "cfg"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ReturnValueInstruction extends AbstractJumpInstruction {
    private final KtReturnExpression returnExpressionIfAny;
    private final PseudoValue returnedValue;
    private final KtElement subroutine;

    public final PseudoValue getReturnedValue() {
        return this.returnedValue;
    }

    public final KtElement getSubroutine() {
        return this.subroutine;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ReturnValueInstruction(KtExpression returnExpression, BlockScope blockScope, Label targetLabel, PseudoValue returnedValue, KtElement subroutine) {
        super(returnExpression, targetLabel, blockScope);
        Intrinsics.checkNotNullParameter(returnExpression, "returnExpression");
        Intrinsics.checkNotNullParameter(blockScope, "blockScope");
        Intrinsics.checkNotNullParameter(targetLabel, "targetLabel");
        Intrinsics.checkNotNullParameter(returnedValue, "returnedValue");
        Intrinsics.checkNotNullParameter(subroutine, "subroutine");
        this.returnedValue = returnedValue;
        this.subroutine = subroutine;
        KtElement element = getElement();
        this.returnExpressionIfAny = element instanceof KtReturnExpression ? (KtReturnExpression) element : null;
    }

    @Override // org.jetbrains.kotlin.cfg.pseudocode.instructions.InstructionImpl, org.jetbrains.kotlin.cfg.pseudocode.instructions.Instruction
    public List<PseudoValue> getInputValues() {
        List<PseudoValue> listSingletonList = Collections.singletonList(this.returnedValue);
        Intrinsics.checkNotNullExpressionValue(listSingletonList, "singletonList(...)");
        return listSingletonList;
    }

    @Override // org.jetbrains.kotlin.cfg.pseudocode.instructions.Instruction
    public void accept(InstructionVisitor visitor) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        visitor.visitReturnValue(this);
    }

    @Override // org.jetbrains.kotlin.cfg.pseudocode.instructions.Instruction
    public <R> R accept(InstructionVisitorWithResult<? extends R> visitor) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        return visitor.visitReturnValue(this);
    }

    public String toString() {
        return "ret(*|" + this.returnedValue + ") " + getTargetLabel();
    }

    @Override // org.jetbrains.kotlin.cfg.pseudocode.instructions.jumps.AbstractJumpInstruction
    protected AbstractJumpInstruction createCopy(Label newLabel, BlockScope blockScope) {
        Intrinsics.checkNotNullParameter(newLabel, "newLabel");
        Intrinsics.checkNotNullParameter(blockScope, "blockScope");
        KtElement element = getElement();
        Intrinsics.checkNotNull(element, "null cannot be cast to non-null type org.jetbrains.kotlin.psi.KtExpression");
        return new ReturnValueInstruction((KtExpression) element, blockScope, newLabel, this.returnedValue, this.subroutine);
    }

    public final KtReturnExpression getReturnExpressionIfAny() {
        return this.returnExpressionIfAny;
    }
}
