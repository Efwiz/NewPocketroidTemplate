package org.jetbrains.kotlin.cfg.pseudocode.instructions.jumps;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.cfg.Label;
import org.jetbrains.kotlin.cfg.pseudocode.PseudoValue;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.BlockScope;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.Instruction;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.InstructionImpl;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.InstructionVisitor;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.InstructionVisitorWithResult;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.KtElementInstructionImpl;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.psi.KtElement;
import org.mozilla.javascript.ES6Iterator;

/* JADX INFO: compiled from: NondeterministicJumpInstruction.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000r\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010%\n\u0002\b\n\n\u0002\u0010\u001e\n\u0002\b\u0003\n\u0002\u0010$\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010!\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u00012\u00020\u0002B-\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006\u0012\u0006\u0010\b\u001a\u00020\t\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u000b¢\u0006\u0002\u0010\fJ\u0010\u0010#\u001a\u00020$2\u0006\u0010%\u001a\u00020&H\u0016J!\u0010#\u001a\u0002H'\"\u0004\b\u0000\u0010'2\f\u0010%\u001a\b\u0012\u0004\u0012\u0002H'0(H\u0016¢\u0006\u0002\u0010)J\u0014\u0010*\u001a\u00020\u000e2\f\u0010+\u001a\b\u0012\u0004\u0012\u00020\u00070,J\b\u0010-\u001a\u00020.H\u0014J\u0016\u0010-\u001a\u00020.2\f\u0010+\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0002J\u0016\u0010/\u001a\u00020$2\u0006\u00100\u001a\u00020\u00072\u0006\u00101\u001a\u00020\u000eJ\b\u00102\u001a\u000203H\u0016R\u0010\u0010\r\u001a\u0004\u0018\u00010\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u000e0\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u000b0\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0013R$\u0010\u0015\u001a\u00020\u000e2\u0006\u0010\u0014\u001a\u00020\u000e8F@FX\u0086\u000e¢\u0006\f\u001a\u0004\b\u0016\u0010\u0017\"\u0004\b\u0018\u0010\u0019R\u001a\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u000e0\u001b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001c\u0010\u001dR \u0010\u001e\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u000e0\u001f8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b \u0010!R\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006¢\u0006\b\n\u0000\u001a\u0004\b\"\u0010\u0013¨\u00064"}, d2 = {"Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/jumps/NondeterministicJumpInstruction;", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/KtElementInstructionImpl;", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/jumps/JumpInstruction;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/psi/KtElement;", "targetLabels", "", "Lorg/jetbrains/kotlin/cfg/Label;", "blockScope", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/BlockScope;", "inputValue", "Lorg/jetbrains/kotlin/cfg/pseudocode/PseudoValue;", "(Lorg/jetbrains/kotlin/psi/KtElement;Ljava/util/List;Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/BlockScope;Lorg/jetbrains/kotlin/cfg/pseudocode/PseudoValue;)V", "_next", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/Instruction;", "_resolvedTargets", "", "inputValues", "getInputValues", "()Ljava/util/List;", "value", ES6Iterator.NEXT_METHOD, "getNext", "()Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/Instruction;", "setNext", "(Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/Instruction;)V", "nextInstructions", "", "getNextInstructions", "()Ljava/util/Collection;", "resolvedTargets", "", "getResolvedTargets", "()Ljava/util/Map;", "getTargetLabels", "accept", "", "visitor", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/InstructionVisitor;", "R", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/InstructionVisitorWithResult;", "(Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/InstructionVisitorWithResult;)Ljava/lang/Object;", ConfigConstants.CONFIG_RENAMELIMIT_COPY, "newTargetLabels", "", "createCopy", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/InstructionImpl;", "setResolvedTarget", CoroutineCodegenUtilKt.COROUTINE_LABEL_FIELD_NAME, "resolvedTarget", "toString", "", "cfg"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class NondeterministicJumpInstruction extends KtElementInstructionImpl implements JumpInstruction {
    private Instruction _next;
    private final Map<Label, Instruction> _resolvedTargets;
    private final PseudoValue inputValue;
    private final List<Label> targetLabels;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NondeterministicJumpInstruction(KtElement element, List<? extends Label> targetLabels, BlockScope blockScope, PseudoValue pseudoValue) {
        super(element, blockScope);
        Intrinsics.checkNotNullParameter(element, "element");
        Intrinsics.checkNotNullParameter(targetLabels, "targetLabels");
        Intrinsics.checkNotNullParameter(blockScope, "blockScope");
        this.inputValue = pseudoValue;
        this._resolvedTargets = new LinkedHashMap();
        this.targetLabels = new ArrayList(targetLabels);
    }

    public final List<Label> getTargetLabels() {
        return this.targetLabels;
    }

    private final Map<Label, Instruction> getResolvedTargets() {
        return this._resolvedTargets;
    }

    public final void setResolvedTarget(Label label, Instruction resolvedTarget) {
        Intrinsics.checkNotNullParameter(label, "label");
        Intrinsics.checkNotNullParameter(resolvedTarget, "resolvedTarget");
        Map<Label, Instruction> map = this._resolvedTargets;
        Instruction instructionOutgoingEdgeTo = outgoingEdgeTo(resolvedTarget);
        Intrinsics.checkNotNull(instructionOutgoingEdgeTo);
        map.put(label, instructionOutgoingEdgeTo);
    }

    public final Instruction getNext() {
        Instruction instruction = this._next;
        Intrinsics.checkNotNull(instruction);
        return instruction;
    }

    public final void setNext(Instruction value) {
        Intrinsics.checkNotNullParameter(value, "value");
        this._next = outgoingEdgeTo(value);
    }

    @Override // org.jetbrains.kotlin.cfg.pseudocode.instructions.Instruction
    public Collection<Instruction> getNextInstructions() {
        ArrayList arrayList = new ArrayList(getResolvedTargets().values());
        arrayList.add(getNext());
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.cfg.pseudocode.instructions.InstructionImpl, org.jetbrains.kotlin.cfg.pseudocode.instructions.Instruction
    public List<PseudoValue> getInputValues() {
        return CollectionsKt.listOfNotNull(this.inputValue);
    }

    @Override // org.jetbrains.kotlin.cfg.pseudocode.instructions.Instruction
    public void accept(InstructionVisitor visitor) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        visitor.visitNondeterministicJump(this);
    }

    @Override // org.jetbrains.kotlin.cfg.pseudocode.instructions.Instruction
    public <R> R accept(InstructionVisitorWithResult<? extends R> visitor) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        return visitor.visitNondeterministicJump(this);
    }

    public String toString() {
        return "jmp?(" + CollectionsKt.joinToString$default(this.targetLabels, ", ", null, null, 0, null, new Function1<Label, CharSequence>() { // from class: org.jetbrains.kotlin.cfg.pseudocode.instructions.jumps.NondeterministicJumpInstruction$toString$labels$1
            @Override // kotlin.jvm.functions.Function1
            public final CharSequence invoke(Label it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return it.getName();
            }
        }, 30, null) + (this.inputValue != null ? "|" + this.inputValue : "") + ')';
    }

    @Override // org.jetbrains.kotlin.cfg.pseudocode.instructions.InstructionImpl
    protected InstructionImpl createCopy() {
        return createCopy(this.targetLabels);
    }

    public final Instruction copy(List<Label> newTargetLabels) {
        Intrinsics.checkNotNullParameter(newTargetLabels, "newTargetLabels");
        return updateCopyInfo(createCopy(newTargetLabels));
    }

    private final InstructionImpl createCopy(List<? extends Label> newTargetLabels) {
        return new NondeterministicJumpInstruction(getElement(), newTargetLabels, getBlockScope(), this.inputValue);
    }
}
