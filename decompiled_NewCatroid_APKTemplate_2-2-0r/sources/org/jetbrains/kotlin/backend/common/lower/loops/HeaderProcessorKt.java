package org.jetbrains.kotlin.backend.common.lower.loops;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.CommonBackendContext;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.declarations.IrVariable;

/* JADX INFO: compiled from: HeaderProcessor.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0012\u0010\u0002\u001a\u00020\u0003*\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0080T¢\u0006\u0002\n\u0000¨\u0006\u0007"}, d2 = {"inductionVariableName", "", "isInductionVariable", "", "Lorg/jetbrains/kotlin/ir/IrStatement;", "context", "Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;", "ir.backend.common"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class HeaderProcessorKt {
    public static final String inductionVariableName = "inductionVariable";

    public static final boolean isInductionVariable(IrStatement irStatement, CommonBackendContext context) {
        Intrinsics.checkNotNullParameter(irStatement, "<this>");
        Intrinsics.checkNotNullParameter(context, "context");
        if (irStatement instanceof IrVariable) {
            IrVariable irVariable = (IrVariable) irStatement;
            if (Intrinsics.areEqual(irVariable.getOrigin(), context.getInductionVariableOrigin()) && Intrinsics.areEqual(irVariable.getName().asString(), inductionVariableName)) {
                return true;
            }
        }
        return false;
    }
}
