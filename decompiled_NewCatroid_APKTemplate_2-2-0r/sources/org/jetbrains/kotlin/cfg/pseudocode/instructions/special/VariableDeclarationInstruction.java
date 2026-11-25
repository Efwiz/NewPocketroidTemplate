package org.jetbrains.kotlin.cfg.pseudocode.instructions.special;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.BlockScope;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.InstructionImpl;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.InstructionVisitor;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.InstructionVisitorWithResult;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.InstructionWithNext;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtEnumEntry;
import org.jetbrains.kotlin.psi.KtObjectDeclaration;
import org.jetbrains.kotlin.psi.KtParameter;
import org.jetbrains.kotlin.psi.KtVariableDeclaration;

/* JADX INFO: compiled from: VariableDeclarationInstruction.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0016J!\u0010\n\u001a\u0002H\u000e\"\u0004\b\u0000\u0010\u000e2\f\u0010\f\u001a\b\u0012\u0004\u0012\u0002H\u000e0\u000fH\u0016¢\u0006\u0002\u0010\u0010J\b\u0010\u0011\u001a\u00020\u0012H\u0014J\b\u0010\u0013\u001a\u00020\u0014H\u0016R\u0011\u0010\u0007\u001a\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\b\u0010\t¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/special/VariableDeclarationInstruction;", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/InstructionWithNext;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "blockScope", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/BlockScope;", "(Lorg/jetbrains/kotlin/psi/KtDeclaration;Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/BlockScope;)V", "variableDeclarationElement", "getVariableDeclarationElement", "()Lorg/jetbrains/kotlin/psi/KtDeclaration;", "accept", "", "visitor", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/InstructionVisitor;", "R", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/InstructionVisitorWithResult;", "(Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/InstructionVisitorWithResult;)Ljava/lang/Object;", "createCopy", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/InstructionImpl;", "toString", "", "cfg"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class VariableDeclarationInstruction extends InstructionWithNext {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public VariableDeclarationInstruction(KtDeclaration element, BlockScope blockScope) {
        super(element, blockScope);
        Intrinsics.checkNotNullParameter(element, "element");
        Intrinsics.checkNotNullParameter(blockScope, "blockScope");
        if ((element instanceof KtVariableDeclaration) || (element instanceof KtParameter) || (element instanceof KtEnumEntry)) {
            return;
        }
        boolean z = element instanceof KtObjectDeclaration;
    }

    public final KtDeclaration getVariableDeclarationElement() {
        KtElement element = getElement();
        Intrinsics.checkNotNull(element, "null cannot be cast to non-null type org.jetbrains.kotlin.psi.KtDeclaration");
        return (KtDeclaration) element;
    }

    @Override // org.jetbrains.kotlin.cfg.pseudocode.instructions.Instruction
    public void accept(InstructionVisitor visitor) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        visitor.visitVariableDeclarationInstruction(this);
    }

    @Override // org.jetbrains.kotlin.cfg.pseudocode.instructions.Instruction
    public <R> R accept(InstructionVisitorWithResult<? extends R> visitor) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        return visitor.visitVariableDeclarationInstruction(this);
    }

    public String toString() {
        return "v(" + render(getElement()) + ')';
    }

    @Override // org.jetbrains.kotlin.cfg.pseudocode.instructions.InstructionImpl
    protected InstructionImpl createCopy() {
        return new VariableDeclarationInstruction(getVariableDeclarationElement(), getBlockScope());
    }
}
