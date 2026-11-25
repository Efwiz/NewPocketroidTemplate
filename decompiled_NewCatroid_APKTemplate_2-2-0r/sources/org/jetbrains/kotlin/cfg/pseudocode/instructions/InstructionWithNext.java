package org.jetbrains.kotlin.cfg.pseudocode.instructions;

import java.util.Collection;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.psi.KtElement;
import org.mozilla.javascript.ES6Iterator;

/* JADX INFO: compiled from: InstructionWithNext.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u001e\n\u0002\b\u0003\b&\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R(\u0010\t\u001a\u0004\u0018\u00010\b2\b\u0010\u0007\u001a\u0004\u0018\u00010\b@FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR\u001a\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\b0\u000f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/InstructionWithNext;", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/KtElementInstructionImpl;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/psi/KtElement;", "blockScope", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/BlockScope;", "(Lorg/jetbrains/kotlin/psi/KtElement;Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/BlockScope;)V", "value", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/Instruction;", ES6Iterator.NEXT_METHOD, "getNext", "()Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/Instruction;", "setNext", "(Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/Instruction;)V", "nextInstructions", "", "getNextInstructions", "()Ljava/util/Collection;", "cfg"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class InstructionWithNext extends KtElementInstructionImpl {
    private Instruction next;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public InstructionWithNext(KtElement element, BlockScope blockScope) {
        super(element, blockScope);
        Intrinsics.checkNotNullParameter(element, "element");
        Intrinsics.checkNotNullParameter(blockScope, "blockScope");
    }

    public final Instruction getNext() {
        return this.next;
    }

    public final void setNext(Instruction instruction) {
        this.next = outgoingEdgeTo(instruction);
    }

    @Override // org.jetbrains.kotlin.cfg.pseudocode.instructions.Instruction
    public Collection<Instruction> getNextInstructions() {
        return CollectionsKt.listOfNotNull(this.next);
    }
}
