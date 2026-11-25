package org.jetbrains.kotlin.cfg.pseudocode.instructions.eval;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cfg.pseudocode.PseudoValue;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.BlockScope;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.InstructionImpl;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.InstructionVisitor;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.InstructionVisitorWithResult;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtNamedDeclaration;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue;

/* JADX INFO: compiled from: accessInstructions.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u0001BA\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0012\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u000b0\t\u0012\u0006\u0010\f\u001a\u00020\u0003\u0012\u0006\u0010\r\u001a\u00020\n¢\u0006\u0002\u0010\u000eJ\u0010\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018H\u0016J!\u0010\u0015\u001a\u0002H\u0019\"\u0004\b\u0000\u0010\u00192\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u0002H\u00190\u001aH\u0016¢\u0006\u0002\u0010\u001bJ\b\u0010\u001c\u001a\u00020\u001dH\u0014J\b\u0010\u001e\u001a\u00020\u001fH\u0016R\u001a\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\n0\u00108VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\f\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u000e\u0010\r\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006 "}, d2 = {"Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/eval/WriteValueInstruction;", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/eval/AccessValueInstruction;", "assignment", "Lorg/jetbrains/kotlin/psi/KtElement;", "blockScope", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/BlockScope;", "target", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/eval/AccessTarget;", "receiverValues", "", "Lorg/jetbrains/kotlin/cfg/pseudocode/PseudoValue;", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValue;", "lValue", "rValue", "(Lorg/jetbrains/kotlin/psi/KtElement;Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/BlockScope;Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/eval/AccessTarget;Ljava/util/Map;Lorg/jetbrains/kotlin/psi/KtElement;Lorg/jetbrains/kotlin/cfg/pseudocode/PseudoValue;)V", "inputValues", "", "getInputValues", "()Ljava/util/List;", "getLValue", "()Lorg/jetbrains/kotlin/psi/KtElement;", "accept", "", "visitor", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/InstructionVisitor;", "R", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/InstructionVisitorWithResult;", "(Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/InstructionVisitorWithResult;)Ljava/lang/Object;", "createCopy", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/InstructionImpl;", "toString", "", "cfg"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class WriteValueInstruction extends AccessValueInstruction {
    private final KtElement lValue;
    private final PseudoValue rValue;

    public final KtElement getLValue() {
        return this.lValue;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public WriteValueInstruction(KtElement assignment, BlockScope blockScope, AccessTarget target, Map<PseudoValue, ? extends ReceiverValue> receiverValues, KtElement lValue, PseudoValue rValue) {
        super(assignment, blockScope, target, receiverValues);
        Intrinsics.checkNotNullParameter(assignment, "assignment");
        Intrinsics.checkNotNullParameter(blockScope, "blockScope");
        Intrinsics.checkNotNullParameter(target, "target");
        Intrinsics.checkNotNullParameter(receiverValues, "receiverValues");
        Intrinsics.checkNotNullParameter(lValue, "lValue");
        Intrinsics.checkNotNullParameter(rValue, "rValue");
        this.lValue = lValue;
        this.rValue = rValue;
    }

    @Override // org.jetbrains.kotlin.cfg.pseudocode.instructions.InstructionImpl, org.jetbrains.kotlin.cfg.pseudocode.instructions.Instruction
    public List<PseudoValue> getInputValues() {
        Set<PseudoValue> setKeySet = getReceiverValues().keySet();
        Intrinsics.checkNotNull(setKeySet, "null cannot be cast to non-null type kotlin.collections.Collection<org.jetbrains.kotlin.cfg.pseudocode.PseudoValue>");
        return CollectionsKt.plus((Collection<? extends PseudoValue>) setKeySet, this.rValue);
    }

    @Override // org.jetbrains.kotlin.cfg.pseudocode.instructions.Instruction
    public void accept(InstructionVisitor visitor) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        visitor.visitWriteValue(this);
    }

    @Override // org.jetbrains.kotlin.cfg.pseudocode.instructions.Instruction
    public <R> R accept(InstructionVisitorWithResult<? extends R> visitor) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        return visitor.visitWriteValue(this);
    }

    public String toString() {
        String strRender;
        KtElement ktElement = this.lValue;
        KtNamedDeclaration ktNamedDeclaration = ktElement instanceof KtNamedDeclaration ? (KtNamedDeclaration) ktElement : null;
        if (ktNamedDeclaration == null || (strRender = ktNamedDeclaration.get_name()) == null) {
            strRender = render(this.lValue);
        }
        Intrinsics.checkNotNull(strRender);
        return "w(" + strRender + '|' + CollectionsKt.joinToString$default(getInputValues(), ", ", null, null, 0, null, null, 62, null) + ')';
    }

    @Override // org.jetbrains.kotlin.cfg.pseudocode.instructions.InstructionImpl
    protected InstructionImpl createCopy() {
        return new WriteValueInstruction(getElement(), getBlockScope(), getTarget(), getReceiverValues(), this.lValue, this.rValue);
    }
}
