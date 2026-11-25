package org.jetbrains.kotlin.cfg.pseudocode.instructions.special;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cfg.pseudocode.Pseudocode;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.BlockScope;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.InstructionImpl;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.InstructionVisitor;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.InstructionVisitorWithResult;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.contracts.description.EventOccurrencesRange;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.psi.KtElement;

/* JADX INFO: compiled from: InlinedLocalFunctionDeclarationInstruction.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ\u0010\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016J!\u0010\r\u001a\u0002H\u0011\"\u0004\b\u0000\u0010\u00112\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u0002H\u00110\u0012H\u0016¢\u0006\u0002\u0010\u0013J\b\u0010\u0014\u001a\u00020\u0015H\u0014J\b\u0010\u0016\u001a\u00020\u0017H\u0016R\u0011\u0010\b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f¨\u0006\u0018"}, d2 = {"Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/special/InlinedLocalFunctionDeclarationInstruction;", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/special/LocalFunctionDeclarationInstruction;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/psi/KtElement;", "body", "Lorg/jetbrains/kotlin/cfg/pseudocode/Pseudocode;", "blockScope", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/BlockScope;", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/contracts/description/EventOccurrencesRange;", "(Lorg/jetbrains/kotlin/psi/KtElement;Lorg/jetbrains/kotlin/cfg/pseudocode/Pseudocode;Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/BlockScope;Lorg/jetbrains/kotlin/contracts/description/EventOccurrencesRange;)V", "getKind", "()Lorg/jetbrains/kotlin/contracts/description/EventOccurrencesRange;", "accept", "", "visitor", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/InstructionVisitor;", "R", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/InstructionVisitorWithResult;", "(Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/InstructionVisitorWithResult;)Ljava/lang/Object;", "createCopy", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/InstructionImpl;", "toString", "", "cfg"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class InlinedLocalFunctionDeclarationInstruction extends LocalFunctionDeclarationInstruction {
    private final EventOccurrencesRange kind;

    public final EventOccurrencesRange getKind() {
        return this.kind;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public InlinedLocalFunctionDeclarationInstruction(KtElement element, Pseudocode body, BlockScope blockScope, EventOccurrencesRange kind) {
        super(element, body, blockScope);
        Intrinsics.checkNotNullParameter(element, "element");
        Intrinsics.checkNotNullParameter(body, "body");
        Intrinsics.checkNotNullParameter(blockScope, "blockScope");
        Intrinsics.checkNotNullParameter(kind, "kind");
        this.kind = kind;
    }

    @Override // org.jetbrains.kotlin.cfg.pseudocode.instructions.special.LocalFunctionDeclarationInstruction, org.jetbrains.kotlin.cfg.pseudocode.instructions.InstructionImpl
    protected InstructionImpl createCopy() {
        return new InlinedLocalFunctionDeclarationInstruction(getElement(), getBody().copy(), getBlockScope(), this.kind);
    }

    @Override // org.jetbrains.kotlin.cfg.pseudocode.instructions.special.LocalFunctionDeclarationInstruction, org.jetbrains.kotlin.cfg.pseudocode.instructions.Instruction
    public void accept(InstructionVisitor visitor) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        visitor.visitInlinedLocalFunctionDeclarationInstruction(this);
    }

    @Override // org.jetbrains.kotlin.cfg.pseudocode.instructions.special.LocalFunctionDeclarationInstruction, org.jetbrains.kotlin.cfg.pseudocode.instructions.Instruction
    public <R> R accept(InstructionVisitorWithResult<? extends R> visitor) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        return visitor.visitInlinedFunctionDeclarationInstruction(this);
    }

    @Override // org.jetbrains.kotlin.cfg.pseudocode.instructions.special.LocalFunctionDeclarationInstruction
    public String toString() {
        return "inlined(" + render(getElement()) + ')';
    }
}
