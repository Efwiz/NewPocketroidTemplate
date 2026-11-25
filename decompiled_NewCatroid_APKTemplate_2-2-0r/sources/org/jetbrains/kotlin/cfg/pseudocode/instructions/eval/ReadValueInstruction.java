package org.jetbrains.kotlin.cfg.pseudocode.instructions.eval;

import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cfg.pseudocode.PseudoValue;
import org.jetbrains.kotlin.cfg.pseudocode.PseudoValueFactory;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.BlockScope;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.InstructionImpl;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.InstructionVisitor;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.InstructionVisitorWithResult;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue;

/* JADX INFO: compiled from: accessInstructions.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000^\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u00012\u00020\u0002B;\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0012\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\f0\n\u0012\u0006\u0010\r\u001a\u00020\u000e¢\u0006\u0002\u0010\u000fB=\b\u0002\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0012\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\f0\n\u0012\b\u0010\u0010\u001a\u0004\u0018\u00010\u000b¢\u0006\u0002\u0010\u0011J\u0010\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001cH\u0016J!\u0010\u0019\u001a\u0002H\u001d\"\u0004\b\u0000\u0010\u001d2\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u0002H\u001d0\u001eH\u0016¢\u0006\u0002\u0010\u001fJ\b\u0010 \u001a\u00020!H\u0014J\b\u0010\"\u001a\u00020#H\u0016R\u0010\u0010\u0010\u001a\u0004\u0018\u00010\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u000b0\u00138VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015R\u0014\u0010\u0016\u001a\u00020\u000b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0018¨\u0006$"}, d2 = {"Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/eval/ReadValueInstruction;", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/eval/AccessValueInstruction;", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/eval/InstructionWithValue;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/psi/KtElement;", "blockScope", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/BlockScope;", "target", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/eval/AccessTarget;", "receiverValues", "", "Lorg/jetbrains/kotlin/cfg/pseudocode/PseudoValue;", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValue;", "factory", "Lorg/jetbrains/kotlin/cfg/pseudocode/PseudoValueFactory;", "(Lorg/jetbrains/kotlin/psi/KtElement;Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/BlockScope;Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/eval/AccessTarget;Ljava/util/Map;Lorg/jetbrains/kotlin/cfg/pseudocode/PseudoValueFactory;)V", "_outputValue", "(Lorg/jetbrains/kotlin/psi/KtElement;Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/BlockScope;Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/eval/AccessTarget;Ljava/util/Map;Lorg/jetbrains/kotlin/cfg/pseudocode/PseudoValue;)V", "inputValues", "", "getInputValues", "()Ljava/util/List;", "outputValue", "getOutputValue", "()Lorg/jetbrains/kotlin/cfg/pseudocode/PseudoValue;", "accept", "", "visitor", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/InstructionVisitor;", "R", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/InstructionVisitorWithResult;", "(Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/InstructionVisitorWithResult;)Ljava/lang/Object;", "createCopy", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/InstructionImpl;", "toString", "", "cfg"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ReadValueInstruction extends AccessValueInstruction implements InstructionWithValue {
    private PseudoValue _outputValue;

    private ReadValueInstruction(KtElement ktElement, BlockScope blockScope, AccessTarget accessTarget, Map<PseudoValue, ? extends ReceiverValue> map, PseudoValue pseudoValue) {
        super(ktElement, blockScope, accessTarget, map);
        this._outputValue = pseudoValue;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public ReadValueInstruction(KtElement element, BlockScope blockScope, AccessTarget target, Map<PseudoValue, ? extends ReceiverValue> receiverValues, PseudoValueFactory factory) {
        this(element, blockScope, target, receiverValues, (PseudoValue) null);
        Intrinsics.checkNotNullParameter(element, "element");
        Intrinsics.checkNotNullParameter(blockScope, "blockScope");
        Intrinsics.checkNotNullParameter(target, "target");
        Intrinsics.checkNotNullParameter(receiverValues, "receiverValues");
        Intrinsics.checkNotNullParameter(factory, "factory");
        this._outputValue = factory.newValue(element, this);
    }

    @Override // org.jetbrains.kotlin.cfg.pseudocode.instructions.InstructionImpl, org.jetbrains.kotlin.cfg.pseudocode.instructions.Instruction
    public List<PseudoValue> getInputValues() {
        return CollectionsKt.toList(getReceiverValues().keySet());
    }

    @Override // org.jetbrains.kotlin.cfg.pseudocode.instructions.eval.InstructionWithValue
    /* JADX INFO: renamed from: getOutputValue */
    public PseudoValue getResultValue() {
        PseudoValue pseudoValue = this._outputValue;
        Intrinsics.checkNotNull(pseudoValue);
        return pseudoValue;
    }

    @Override // org.jetbrains.kotlin.cfg.pseudocode.instructions.Instruction
    public void accept(InstructionVisitor visitor) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        visitor.visitReadValue(this);
    }

    @Override // org.jetbrains.kotlin.cfg.pseudocode.instructions.Instruction
    public <R> R accept(InstructionVisitorWithResult<? extends R> visitor) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        return visitor.visitReadValue(this);
    }

    public String toString() {
        VariableDescriptor resultingDescriptor;
        Name name2;
        String str = getReceiverValues().isEmpty() ? "" : "|" + CollectionsKt.joinToString$default(getReceiverValues().keySet(), null, null, null, 0, null, null, 63, null);
        AccessTarget target = getTarget();
        String strAsString = null;
        if (target instanceof AccessTarget.Declaration) {
            resultingDescriptor = ((AccessTarget.Declaration) getTarget()).getDescriptor();
        } else {
            resultingDescriptor = target instanceof AccessTarget.Call ? ((AccessTarget.Call) getTarget()).getResolvedCall().getResultingDescriptor() : null;
        }
        if (resultingDescriptor != null && (name2 = resultingDescriptor.getShortName()) != null) {
            strAsString = name2.asString();
        }
        String strRender = render(getElement());
        if (strAsString != null && !Intrinsics.areEqual(strAsString, strRender)) {
            strRender = strRender + ", " + strAsString;
        }
        return "r(" + strRender + str + ") -> " + getResultValue();
    }

    @Override // org.jetbrains.kotlin.cfg.pseudocode.instructions.InstructionImpl
    protected InstructionImpl createCopy() {
        return new ReadValueInstruction(getElement(), getBlockScope(), getTarget(), getReceiverValues(), getResultValue());
    }
}
