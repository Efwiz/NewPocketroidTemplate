package org.jetbrains.kotlin.cfg.pseudocode.instructions.special;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.BlockScope;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.Instruction;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.InstructionImpl;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.InstructionVisitor;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.InstructionVisitorWithResult;
import org.jetbrains.kotlin.psi.KtElement;

/* JADX INFO: compiled from: SubroutineExitInstruction.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0010\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001cH\u0016J!\u0010\u0019\u001a\u0002H\u001d\"\u0004\b\u0000\u0010\u001d2\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u0002H\u001d0\u001eH\u0016¢\u0006\u0002\u0010\u001fJ\b\u0010 \u001a\u00020\u0001H\u0014J\b\u0010!\u001a\u00020\"H\u0016R\u0010\u0010\t\u001a\u0004\u0018\u00010\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u000bR\u001a\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\r8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R$\u0010\u0012\u001a\u00020\n2\u0006\u0010\u0011\u001a\u00020\n8F@FX\u0086\u000e¢\u0006\f\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018¨\u0006#"}, d2 = {"Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/special/SubroutineExitInstruction;", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/InstructionImpl;", "subroutine", "Lorg/jetbrains/kotlin/psi/KtElement;", "blockScope", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/BlockScope;", "isError", "", "(Lorg/jetbrains/kotlin/psi/KtElement;Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/BlockScope;Z)V", "_sink", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/special/SubroutineSinkInstruction;", "()Z", "nextInstructions", "", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/Instruction;", "getNextInstructions", "()Ljava/util/Collection;", "value", "sink", "getSink", "()Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/special/SubroutineSinkInstruction;", "setSink", "(Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/special/SubroutineSinkInstruction;)V", "getSubroutine", "()Lorg/jetbrains/kotlin/psi/KtElement;", "accept", "", "visitor", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/InstructionVisitor;", "R", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/InstructionVisitorWithResult;", "(Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/InstructionVisitorWithResult;)Ljava/lang/Object;", "createCopy", "toString", "", "cfg"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SubroutineExitInstruction extends InstructionImpl {
    private SubroutineSinkInstruction _sink;
    private final boolean isError;
    private final KtElement subroutine;

    public final KtElement getSubroutine() {
        return this.subroutine;
    }

    /* JADX INFO: renamed from: isError, reason: from getter */
    public final boolean getIsError() {
        return this.isError;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SubroutineExitInstruction(KtElement subroutine, BlockScope blockScope, boolean z) {
        super(blockScope);
        Intrinsics.checkNotNullParameter(subroutine, "subroutine");
        Intrinsics.checkNotNullParameter(blockScope, "blockScope");
        this.subroutine = subroutine;
        this.isError = z;
    }

    public final SubroutineSinkInstruction getSink() {
        SubroutineSinkInstruction subroutineSinkInstruction = this._sink;
        Intrinsics.checkNotNull(subroutineSinkInstruction);
        return subroutineSinkInstruction;
    }

    public final void setSink(SubroutineSinkInstruction value) {
        Intrinsics.checkNotNullParameter(value, "value");
        Instruction instructionOutgoingEdgeTo = outgoingEdgeTo(value);
        Intrinsics.checkNotNull(instructionOutgoingEdgeTo, "null cannot be cast to non-null type org.jetbrains.kotlin.cfg.pseudocode.instructions.special.SubroutineSinkInstruction");
        this._sink = (SubroutineSinkInstruction) instructionOutgoingEdgeTo;
    }

    @Override // org.jetbrains.kotlin.cfg.pseudocode.instructions.Instruction
    public Collection<Instruction> getNextInstructions() {
        Set setSingleton = Collections.singleton(getSink());
        Intrinsics.checkNotNullExpressionValue(setSingleton, "singleton(...)");
        return setSingleton;
    }

    @Override // org.jetbrains.kotlin.cfg.pseudocode.instructions.Instruction
    public void accept(InstructionVisitor visitor) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        visitor.visitSubroutineExit(this);
    }

    @Override // org.jetbrains.kotlin.cfg.pseudocode.instructions.Instruction
    public <R> R accept(InstructionVisitorWithResult<? extends R> visitor) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        return visitor.visitSubroutineExit(this);
    }

    public String toString() {
        return this.isError ? "<ERROR>" : "<END>";
    }

    @Override // org.jetbrains.kotlin.cfg.pseudocode.instructions.InstructionImpl
    protected InstructionImpl createCopy() {
        return new SubroutineExitInstruction(this.subroutine, getBlockScope(), this.isError);
    }
}
