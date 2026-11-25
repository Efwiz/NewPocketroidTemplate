package org.jetbrains.kotlin.cfg.pseudocode.instructions.eval;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cfg.pseudocode.PseudoValue;
import org.jetbrains.kotlin.cfg.pseudocode.PseudoValueFactory;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.BlockScope;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.InstructionVisitor;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.InstructionVisitorWithResult;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue;

/* JADX INFO: compiled from: operationInstructions.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u00012\u00020\u0002BU\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\n\u0010\u0007\u001a\u0006\u0012\u0002\b\u00030\b\u0012\u0012\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\f0\n\u0012\u0012\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u000e0\n\u0012\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010¢\u0006\u0002\u0010\u0011BK\b\u0002\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\n\u0010\u0007\u001a\u0006\u0012\u0002\b\u00030\b\u0012\u0012\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\f0\n\u0012\u0012\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u000e0\n¢\u0006\u0002\u0010\u0012J\u0010\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001bH\u0016J!\u0010\u0018\u001a\u0002H\u001c\"\u0004\b\u0000\u0010\u001c2\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u0002H\u001c0\u001dH\u0016¢\u0006\u0002\u0010\u001eJ\b\u0010\u001f\u001a\u00020\u0001H\u0014J\b\u0010 \u001a\u00020!H\u0016R\u001d\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u000e0\n¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R \u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\f0\nX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0014R\u0015\u0010\u0007\u001a\u0006\u0012\u0002\b\u00030\b¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017¨\u0006\""}, d2 = {"Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/eval/CallInstruction;", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/eval/OperationInstruction;", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/eval/InstructionWithReceivers;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/psi/KtElement;", "blockScope", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/BlockScope;", "resolvedCall", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "receiverValues", "", "Lorg/jetbrains/kotlin/cfg/pseudocode/PseudoValue;", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValue;", "arguments", "Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;", "factory", "Lorg/jetbrains/kotlin/cfg/pseudocode/PseudoValueFactory;", "(Lorg/jetbrains/kotlin/psi/KtElement;Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/BlockScope;Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;Ljava/util/Map;Ljava/util/Map;Lorg/jetbrains/kotlin/cfg/pseudocode/PseudoValueFactory;)V", "(Lorg/jetbrains/kotlin/psi/KtElement;Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/BlockScope;Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;Ljava/util/Map;Ljava/util/Map;)V", "getArguments", "()Ljava/util/Map;", "getReceiverValues", "getResolvedCall", "()Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "accept", "", "visitor", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/InstructionVisitor;", "R", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/InstructionVisitorWithResult;", "(Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/InstructionVisitorWithResult;)Ljava/lang/Object;", "createCopy", "toString", "", "cfg"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CallInstruction extends OperationInstruction implements InstructionWithReceivers {
    private final Map<PseudoValue, ValueParameterDescriptor> arguments;
    private final Map<PseudoValue, ReceiverValue> receiverValues;
    private final ResolvedCall<?> resolvedCall;

    public final ResolvedCall<?> getResolvedCall() {
        return this.resolvedCall;
    }

    @Override // org.jetbrains.kotlin.cfg.pseudocode.instructions.eval.InstructionWithReceivers
    public Map<PseudoValue, ReceiverValue> getReceiverValues() {
        return this.receiverValues;
    }

    public final Map<PseudoValue, ValueParameterDescriptor> getArguments() {
        return this.arguments;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /* JADX WARN: Multi-variable type inference failed */
    private CallInstruction(KtElement ktElement, BlockScope blockScope, ResolvedCall<?> resolvedCall, Map<PseudoValue, ? extends ReceiverValue> map, Map<PseudoValue, ? extends ValueParameterDescriptor> map2) {
        Set setKeySet = map.keySet();
        Intrinsics.checkNotNull(setKeySet, "null cannot be cast to non-null type kotlin.collections.Collection<org.jetbrains.kotlin.cfg.pseudocode.PseudoValue>");
        super(ktElement, blockScope, CollectionsKt.plus((Collection) setKeySet, (Iterable) map2.keySet()));
        this.resolvedCall = resolvedCall;
        this.receiverValues = map;
        this.arguments = map2;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public CallInstruction(KtElement element, BlockScope blockScope, ResolvedCall<?> resolvedCall, Map<PseudoValue, ? extends ReceiverValue> receiverValues, Map<PseudoValue, ? extends ValueParameterDescriptor> arguments, PseudoValueFactory pseudoValueFactory) {
        this(element, blockScope, resolvedCall, receiverValues, arguments);
        Intrinsics.checkNotNullParameter(element, "element");
        Intrinsics.checkNotNullParameter(blockScope, "blockScope");
        Intrinsics.checkNotNullParameter(resolvedCall, "resolvedCall");
        Intrinsics.checkNotNullParameter(receiverValues, "receiverValues");
        Intrinsics.checkNotNullParameter(arguments, "arguments");
        OperationInstruction.setResult$default(this, pseudoValueFactory, null, 2, null);
    }

    @Override // org.jetbrains.kotlin.cfg.pseudocode.instructions.Instruction
    public void accept(InstructionVisitor visitor) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        visitor.visitCallInstruction(this);
    }

    @Override // org.jetbrains.kotlin.cfg.pseudocode.instructions.Instruction
    public <R> R accept(InstructionVisitorWithResult<? extends R> visitor) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        return visitor.visitCallInstruction(this);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.cfg.pseudocode.instructions.InstructionImpl
    public OperationInstruction createCopy() {
        return new CallInstruction(getElement(), getBlockScope(), this.resolvedCall, getReceiverValues(), this.arguments).setResult(getResultValue());
    }

    public String toString() {
        StringBuilder sbAppend = new StringBuilder().append(render(getElement())).append(", ");
        CallableDescriptor resultingDescriptor = this.resolvedCall.getResultingDescriptor();
        Intrinsics.checkNotNull(resultingDescriptor);
        return renderInstruction("call", sbAppend.append(resultingDescriptor.getName()).toString());
    }
}
