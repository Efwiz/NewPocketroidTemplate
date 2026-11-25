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
import org.jetbrains.kotlin.psi.KtThrowExpression;

/* JADX INFO: compiled from: ThrowExceptionInstruction.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ\u0010\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0016J!\u0010\u000f\u001a\u0002H\u0013\"\u0004\b\u0000\u0010\u00132\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u0002H\u00130\u0014H\u0016¢\u0006\u0002\u0010\u0015J\u0018\u0010\u0016\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u00072\u0006\u0010\u0004\u001a\u00020\u0005H\u0014J\b\u0010\u0018\u001a\u00020\u0019H\u0016R\u001a\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\t0\f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001a"}, d2 = {"Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/jumps/ThrowExceptionInstruction;", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/jumps/AbstractJumpInstruction;", "expression", "Lorg/jetbrains/kotlin/psi/KtThrowExpression;", "blockScope", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/BlockScope;", "errorLabel", "Lorg/jetbrains/kotlin/cfg/Label;", "thrownValue", "Lorg/jetbrains/kotlin/cfg/pseudocode/PseudoValue;", "(Lorg/jetbrains/kotlin/psi/KtThrowExpression;Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/BlockScope;Lorg/jetbrains/kotlin/cfg/Label;Lorg/jetbrains/kotlin/cfg/pseudocode/PseudoValue;)V", "inputValues", "", "getInputValues", "()Ljava/util/List;", "accept", "", "visitor", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/InstructionVisitor;", "R", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/InstructionVisitorWithResult;", "(Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/InstructionVisitorWithResult;)Ljava/lang/Object;", "createCopy", "newLabel", "toString", "", "cfg"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ThrowExceptionInstruction extends AbstractJumpInstruction {
    private final PseudoValue thrownValue;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ThrowExceptionInstruction(KtThrowExpression expression, BlockScope blockScope, Label errorLabel, PseudoValue thrownValue) {
        super(expression, errorLabel, blockScope);
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(blockScope, "blockScope");
        Intrinsics.checkNotNullParameter(errorLabel, "errorLabel");
        Intrinsics.checkNotNullParameter(thrownValue, "thrownValue");
        this.thrownValue = thrownValue;
    }

    @Override // org.jetbrains.kotlin.cfg.pseudocode.instructions.InstructionImpl, org.jetbrains.kotlin.cfg.pseudocode.instructions.Instruction
    public List<PseudoValue> getInputValues() {
        List<PseudoValue> listSingletonList = Collections.singletonList(this.thrownValue);
        Intrinsics.checkNotNullExpressionValue(listSingletonList, "singletonList(...)");
        return listSingletonList;
    }

    @Override // org.jetbrains.kotlin.cfg.pseudocode.instructions.Instruction
    public void accept(InstructionVisitor visitor) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        visitor.visitThrowExceptionInstruction(this);
    }

    @Override // org.jetbrains.kotlin.cfg.pseudocode.instructions.Instruction
    public <R> R accept(InstructionVisitorWithResult<? extends R> visitor) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        return visitor.visitThrowExceptionInstruction(this);
    }

    public String toString() {
        return "throw (" + getElement().getText() + '|' + this.thrownValue + ')';
    }

    @Override // org.jetbrains.kotlin.cfg.pseudocode.instructions.jumps.AbstractJumpInstruction
    protected AbstractJumpInstruction createCopy(Label newLabel, BlockScope blockScope) {
        Intrinsics.checkNotNullParameter(newLabel, "newLabel");
        Intrinsics.checkNotNullParameter(blockScope, "blockScope");
        KtElement element = getElement();
        Intrinsics.checkNotNull(element, "null cannot be cast to non-null type org.jetbrains.kotlin.psi.KtThrowExpression");
        return new ThrowExceptionInstruction((KtThrowExpression) element, blockScope, newLabel, this.thrownValue);
    }
}
