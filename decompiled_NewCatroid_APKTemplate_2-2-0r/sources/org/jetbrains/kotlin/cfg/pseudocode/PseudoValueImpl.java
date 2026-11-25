package org.jetbrains.kotlin.cfg.pseudocode;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.eval.InstructionWithValue;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.psi.KtElement;

/* JADX INFO: compiled from: PseudoValueImpl.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\u0018\u00002\u00020\u0001B!\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\u0002\u0010\bJ\b\u0010\u000f\u001a\u00020\u0003H\u0016R\u0016\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0016\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/cfg/pseudocode/PseudoValueImpl;", "Lorg/jetbrains/kotlin/cfg/pseudocode/PseudoValue;", "debugName", "", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/psi/KtElement;", "createdAt", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/eval/InstructionWithValue;", "(Ljava/lang/String;Lorg/jetbrains/kotlin/psi/KtElement;Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/eval/InstructionWithValue;)V", "getCreatedAt", "()Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/eval/InstructionWithValue;", "getDebugName", "()Ljava/lang/String;", "getElement", "()Lorg/jetbrains/kotlin/psi/KtElement;", "toString", "cfg"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PseudoValueImpl implements PseudoValue {
    private final InstructionWithValue createdAt;
    private final String debugName;
    private final KtElement element;

    public PseudoValueImpl(String debugName, KtElement ktElement, InstructionWithValue instructionWithValue) {
        Intrinsics.checkNotNullParameter(debugName, "debugName");
        this.debugName = debugName;
        this.element = ktElement;
        this.createdAt = instructionWithValue;
    }

    @Override // org.jetbrains.kotlin.cfg.pseudocode.PseudoValue
    public String getDebugName() {
        return this.debugName;
    }

    @Override // org.jetbrains.kotlin.cfg.pseudocode.PseudoValue
    public KtElement getElement() {
        return this.element;
    }

    @Override // org.jetbrains.kotlin.cfg.pseudocode.PseudoValue
    public InstructionWithValue getCreatedAt() {
        return this.createdAt;
    }

    public String toString() {
        return getDebugName();
    }
}
