package org.jetbrains.kotlin.cfg.pseudocode.instructions.special;

import java.util.ArrayList;
import java.util.Collection;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cfg.pseudocode.Pseudocode;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.BlockScope;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.Instruction;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.InstructionImpl;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.InstructionVisitor;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.InstructionVisitorWithResult;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.InstructionWithNext;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.psi.KtElement;

/* JADX INFO: compiled from: LocalFunctionDeclarationInstruction.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0016\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0010\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001aH\u0016J!\u0010\u0017\u001a\u0002H\u001b\"\u0004\b\u0000\u0010\u001b2\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u0002H\u001b0\u001cH\u0016¢\u0006\u0002\u0010\u001dJ\b\u0010\u001e\u001a\u00020\u001fH\u0014J\b\u0010 \u001a\u00020!H\u0016R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u001a\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR(\u0010\u0012\u001a\u0004\u0018\u00010\u00112\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011@FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016¨\u0006\""}, d2 = {"Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/special/LocalFunctionDeclarationInstruction;", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/InstructionWithNext;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/psi/KtElement;", "body", "Lorg/jetbrains/kotlin/cfg/pseudocode/Pseudocode;", "blockScope", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/BlockScope;", "(Lorg/jetbrains/kotlin/psi/KtElement;Lorg/jetbrains/kotlin/cfg/pseudocode/Pseudocode;Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/BlockScope;)V", "getBody", "()Lorg/jetbrains/kotlin/cfg/pseudocode/Pseudocode;", "nextInstructions", "", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/Instruction;", "getNextInstructions", "()Ljava/util/Collection;", "value", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/special/SubroutineSinkInstruction;", "sink", "getSink", "()Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/special/SubroutineSinkInstruction;", "setSink", "(Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/special/SubroutineSinkInstruction;)V", "accept", "", "visitor", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/InstructionVisitor;", "R", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/InstructionVisitorWithResult;", "(Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/InstructionVisitorWithResult;)Ljava/lang/Object;", "createCopy", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/InstructionImpl;", "toString", "", "cfg"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class LocalFunctionDeclarationInstruction extends InstructionWithNext {
    private final Pseudocode body;
    private SubroutineSinkInstruction sink;

    public final Pseudocode getBody() {
        return this.body;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LocalFunctionDeclarationInstruction(KtElement element, Pseudocode body, BlockScope blockScope) {
        super(element, blockScope);
        Intrinsics.checkNotNullParameter(element, "element");
        Intrinsics.checkNotNullParameter(body, "body");
        Intrinsics.checkNotNullParameter(blockScope, "blockScope");
        this.body = body;
    }

    public final SubroutineSinkInstruction getSink() {
        return this.sink;
    }

    public final void setSink(SubroutineSinkInstruction subroutineSinkInstruction) {
        this.sink = (SubroutineSinkInstruction) outgoingEdgeTo(subroutineSinkInstruction);
    }

    @Override // org.jetbrains.kotlin.cfg.pseudocode.instructions.InstructionWithNext, org.jetbrains.kotlin.cfg.pseudocode.instructions.Instruction
    public Collection<Instruction> getNextInstructions() {
        SubroutineSinkInstruction subroutineSinkInstruction = this.sink;
        if (subroutineSinkInstruction != null) {
            ArrayList arrayListArrayListOf = CollectionsKt.arrayListOf(subroutineSinkInstruction);
            arrayListArrayListOf.addAll(super.getNextInstructions());
            return arrayListArrayListOf;
        }
        return super.getNextInstructions();
    }

    @Override // org.jetbrains.kotlin.cfg.pseudocode.instructions.Instruction
    public void accept(InstructionVisitor visitor) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        visitor.visitLocalFunctionDeclarationInstruction(this);
    }

    @Override // org.jetbrains.kotlin.cfg.pseudocode.instructions.Instruction
    public <R> R accept(InstructionVisitorWithResult<? extends R> visitor) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        return visitor.visitLocalFunctionDeclarationInstruction(this);
    }

    public String toString() {
        return "d(" + render(getElement()) + ')';
    }

    @Override // org.jetbrains.kotlin.cfg.pseudocode.instructions.InstructionImpl
    protected InstructionImpl createCopy() {
        return new LocalFunctionDeclarationInstruction(getElement(), this.body.copy(), getBlockScope());
    }
}
