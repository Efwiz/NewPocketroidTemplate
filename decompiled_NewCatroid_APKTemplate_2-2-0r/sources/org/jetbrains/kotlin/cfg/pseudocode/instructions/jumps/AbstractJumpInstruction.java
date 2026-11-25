package org.jetbrains.kotlin.cfg.pseudocode.instructions.jumps;

import java.util.Collection;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.cfg.Label;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.BlockScope;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.Instruction;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.InstructionImpl;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.KtElementInstructionImpl;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.psi.KtElement;

/* JADX INFO: compiled from: AbstractJumpInstruction.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0018\u0002\n\u0000\b&\u0018\u00002\u00020\u00012\u00020\u0002B\u001d\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\u000e\u0010\u0017\u001a\u00020\f2\u0006\u0010\u0018\u001a\u00020\u0006J\b\u0010\u0019\u001a\u00020\u001aH\u0014J\u0018\u0010\u0019\u001a\u00020\u00002\u0006\u0010\u0018\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH$R\u001a\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000eR(\u0010\u0010\u001a\u0004\u0018\u00010\f2\b\u0010\u000f\u001a\u0004\u0018\u00010\f@FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016¨\u0006\u001b"}, d2 = {"Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/jumps/AbstractJumpInstruction;", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/KtElementInstructionImpl;", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/jumps/JumpInstruction;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/psi/KtElement;", "targetLabel", "Lorg/jetbrains/kotlin/cfg/Label;", "blockScope", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/BlockScope;", "(Lorg/jetbrains/kotlin/psi/KtElement;Lorg/jetbrains/kotlin/cfg/Label;Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/BlockScope;)V", "nextInstructions", "", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/Instruction;", "getNextInstructions", "()Ljava/util/Collection;", "value", "resolvedTarget", "getResolvedTarget", "()Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/Instruction;", "setResolvedTarget", "(Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/Instruction;)V", "getTargetLabel", "()Lorg/jetbrains/kotlin/cfg/Label;", ConfigConstants.CONFIG_RENAMELIMIT_COPY, "newLabel", "createCopy", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/InstructionImpl;", "cfg"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class AbstractJumpInstruction extends KtElementInstructionImpl implements JumpInstruction {
    private Instruction resolvedTarget;
    private final Label targetLabel;

    protected abstract AbstractJumpInstruction createCopy(Label newLabel, BlockScope blockScope);

    public final Label getTargetLabel() {
        return this.targetLabel;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AbstractJumpInstruction(KtElement element, Label targetLabel, BlockScope blockScope) {
        super(element, blockScope);
        Intrinsics.checkNotNullParameter(element, "element");
        Intrinsics.checkNotNullParameter(targetLabel, "targetLabel");
        Intrinsics.checkNotNullParameter(blockScope, "blockScope");
        this.targetLabel = targetLabel;
    }

    public final Instruction getResolvedTarget() {
        return this.resolvedTarget;
    }

    public final void setResolvedTarget(Instruction instruction) {
        this.resolvedTarget = outgoingEdgeTo(instruction);
    }

    public final Instruction copy(Label newLabel) {
        Intrinsics.checkNotNullParameter(newLabel, "newLabel");
        return updateCopyInfo(createCopy(newLabel, getBlockScope()));
    }

    @Override // org.jetbrains.kotlin.cfg.pseudocode.instructions.InstructionImpl
    protected InstructionImpl createCopy() {
        return createCopy(this.targetLabel, getBlockScope());
    }

    @Override // org.jetbrains.kotlin.cfg.pseudocode.instructions.Instruction
    public Collection<Instruction> getNextInstructions() {
        return CollectionsKt.listOfNotNull(this.resolvedTarget);
    }
}
