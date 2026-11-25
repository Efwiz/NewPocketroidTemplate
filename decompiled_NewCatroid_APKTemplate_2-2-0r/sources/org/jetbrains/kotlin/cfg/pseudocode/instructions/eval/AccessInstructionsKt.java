package org.jetbrains.kotlin.cfg.pseudocode.instructions.eval;

import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;

/* JADX INFO: compiled from: accessInstructions.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\"\u0017\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004¨\u0006\u0005"}, d2 = {"accessedDescriptor", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/eval/AccessTarget;", "getAccessedDescriptor", "(Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/eval/AccessTarget;)Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "cfg"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class AccessInstructionsKt {
    public static final CallableDescriptor getAccessedDescriptor(AccessTarget accessTarget) {
        Intrinsics.checkNotNullParameter(accessTarget, "<this>");
        if (accessTarget instanceof AccessTarget.Declaration) {
            return ((AccessTarget.Declaration) accessTarget).getDescriptor();
        }
        if (accessTarget instanceof AccessTarget.Call) {
            return ((AccessTarget.Call) accessTarget).getResolvedCall().getResultingDescriptor();
        }
        if (accessTarget instanceof AccessTarget.BlackBox) {
            return null;
        }
        throw new NoWhenBranchMatchedException();
    }
}
