package org.jetbrains.kotlin.cfg.pseudocode.instructions.eval;

import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cfg.pseudocode.PseudoValue;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.BlockScope;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.InstructionWithNext;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue;

/* JADX INFO: compiled from: accessInstructions.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\b&\u0018\u00002\u00020\u00012\u00020\u0002B3\b\u0004\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0012\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\f0\n¢\u0006\u0002\u0010\rR \u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\f0\nX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/eval/AccessValueInstruction;", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/InstructionWithNext;", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/eval/InstructionWithReceivers;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/psi/KtElement;", "blockScope", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/BlockScope;", "target", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/eval/AccessTarget;", "receiverValues", "", "Lorg/jetbrains/kotlin/cfg/pseudocode/PseudoValue;", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValue;", "(Lorg/jetbrains/kotlin/psi/KtElement;Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/BlockScope;Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/eval/AccessTarget;Ljava/util/Map;)V", "getReceiverValues", "()Ljava/util/Map;", "getTarget", "()Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/eval/AccessTarget;", "cfg"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class AccessValueInstruction extends InstructionWithNext implements InstructionWithReceivers {
    private final Map<PseudoValue, ReceiverValue> receiverValues;
    private final AccessTarget target;

    public final AccessTarget getTarget() {
        return this.target;
    }

    @Override // org.jetbrains.kotlin.cfg.pseudocode.instructions.eval.InstructionWithReceivers
    public Map<PseudoValue, ReceiverValue> getReceiverValues() {
        return this.receiverValues;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    protected AccessValueInstruction(KtElement element, BlockScope blockScope, AccessTarget target, Map<PseudoValue, ? extends ReceiverValue> receiverValues) {
        super(element, blockScope);
        Intrinsics.checkNotNullParameter(element, "element");
        Intrinsics.checkNotNullParameter(blockScope, "blockScope");
        Intrinsics.checkNotNullParameter(target, "target");
        Intrinsics.checkNotNullParameter(receiverValues, "receiverValues");
        this.target = target;
        this.receiverValues = receiverValues;
    }
}
