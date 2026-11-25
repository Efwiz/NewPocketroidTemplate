package org.jetbrains.kotlin.cfg;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.Instruction;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.eval.CallInstruction;
import org.jetbrains.kotlin.name.SpecialNames;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;

/* JADX INFO: compiled from: ControlFlowInformationProviderImpl.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\n¢\u0006\u0002\b\u0004"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "instruction", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/Instruction;", "invoke"}, k = 3, mv = {1, 9, 0}, xi = 48)
public final class ControlFlowInformationProviderImpl$traverseCalls$1 extends Lambda implements Function1<Instruction, Unit> {
    final /* synthetic */ Function2<CallInstruction, ResolvedCall<?>, Unit> $onCall;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public ControlFlowInformationProviderImpl$traverseCalls$1(Function2<? super CallInstruction, ? super ResolvedCall<?>, Unit> function2) {
        super(1);
        this.$onCall = function2;
    }

    @Override // kotlin.jvm.functions.Function1
    public /* bridge */ /* synthetic */ Unit invoke(Instruction instruction) {
        invoke2(instruction);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
    public final void invoke2(Instruction instruction) {
        Intrinsics.checkNotNullParameter(instruction, "instruction");
        if (instruction instanceof CallInstruction) {
            this.$onCall.invoke(instruction, ((CallInstruction) instruction).getResolvedCall());
        }
    }
}
