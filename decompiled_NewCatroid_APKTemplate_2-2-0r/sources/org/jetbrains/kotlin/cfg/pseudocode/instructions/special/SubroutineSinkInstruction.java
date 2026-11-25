package org.jetbrains.kotlin.cfg.pseudocode.instructions.special;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.BlockScope;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.Instruction;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.InstructionImpl;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.InstructionVisitor;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.InstructionVisitorWithResult;
import org.jetbrains.kotlin.psi.KtElement;

/* JADX INFO: compiled from: SubroutineSinkInstruction.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0010\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0016J!\u0010\u0010\u001a\u0002H\u0014\"\u0004\b\u0000\u0010\u00142\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u0002H\u00140\u0015H\u0016¢\u0006\u0002\u0010\u0016J\b\u0010\u0017\u001a\u00020\u0001H\u0014J\b\u0010\u0018\u001a\u00020\u0007H\u0016R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/special/SubroutineSinkInstruction;", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/InstructionImpl;", "subroutine", "Lorg/jetbrains/kotlin/psi/KtElement;", "blockScope", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/BlockScope;", "debugLabel", "", "(Lorg/jetbrains/kotlin/psi/KtElement;Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/BlockScope;Ljava/lang/String;)V", "nextInstructions", "", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/Instruction;", "getNextInstructions", "()Ljava/util/Collection;", "getSubroutine", "()Lorg/jetbrains/kotlin/psi/KtElement;", "accept", "", "visitor", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/InstructionVisitor;", "R", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/InstructionVisitorWithResult;", "(Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/InstructionVisitorWithResult;)Ljava/lang/Object;", "createCopy", "toString", "cfg"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SubroutineSinkInstruction extends InstructionImpl {
    private final String debugLabel;
    private final KtElement subroutine;

    public final KtElement getSubroutine() {
        return this.subroutine;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SubroutineSinkInstruction(KtElement subroutine, BlockScope blockScope, String debugLabel) {
        super(blockScope);
        Intrinsics.checkNotNullParameter(subroutine, "subroutine");
        Intrinsics.checkNotNullParameter(blockScope, "blockScope");
        Intrinsics.checkNotNullParameter(debugLabel, "debugLabel");
        this.subroutine = subroutine;
        this.debugLabel = debugLabel;
    }

    @Override // org.jetbrains.kotlin.cfg.pseudocode.instructions.Instruction
    public Collection<Instruction> getNextInstructions() {
        List listEmptyList = Collections.emptyList();
        Intrinsics.checkNotNullExpressionValue(listEmptyList, "emptyList(...)");
        return listEmptyList;
    }

    @Override // org.jetbrains.kotlin.cfg.pseudocode.instructions.Instruction
    public void accept(InstructionVisitor visitor) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        visitor.visitSubroutineSink(this);
    }

    @Override // org.jetbrains.kotlin.cfg.pseudocode.instructions.Instruction
    public <R> R accept(InstructionVisitorWithResult<? extends R> visitor) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        return visitor.visitSubroutineSink(this);
    }

    /* JADX INFO: renamed from: toString, reason: from getter */
    public String getDebugLabel() {
        return this.debugLabel;
    }

    @Override // org.jetbrains.kotlin.cfg.pseudocode.instructions.InstructionImpl
    protected InstructionImpl createCopy() {
        return new SubroutineSinkInstruction(this.subroutine, getBlockScope(), this.debugLabel);
    }
}
