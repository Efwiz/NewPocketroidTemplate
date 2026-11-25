package org.jetbrains.kotlin.cfg;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.psi.KtLoopExpression;

/* JADX INFO: compiled from: LoopInfo.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\f\u0018\u00002\u00020\u0001B5\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u0006\u0010\u0007\u001a\u00020\u0005\u0012\u0006\u0010\b\u001a\u00020\u0005\u0012\u0006\u0010\t\u001a\u00020\u0005¢\u0006\u0002\u0010\nR\u0011\u0010\u0007\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\b\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\fR\u0011\u0010\t\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\fR\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/cfg/LoopInfo;", "Lorg/jetbrains/kotlin/cfg/BreakableBlockInfo;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/psi/KtLoopExpression;", "entryPoint", "Lorg/jetbrains/kotlin/cfg/Label;", "exitPoint", "bodyEntryPoint", "bodyExitPoint", "conditionEntryPoint", "(Lorg/jetbrains/kotlin/psi/KtLoopExpression;Lorg/jetbrains/kotlin/cfg/Label;Lorg/jetbrains/kotlin/cfg/Label;Lorg/jetbrains/kotlin/cfg/Label;Lorg/jetbrains/kotlin/cfg/Label;Lorg/jetbrains/kotlin/cfg/Label;)V", "getBodyEntryPoint", "()Lorg/jetbrains/kotlin/cfg/Label;", "getBodyExitPoint", "getConditionEntryPoint", "getElement", "()Lorg/jetbrains/kotlin/psi/KtLoopExpression;", "cfg"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class LoopInfo extends BreakableBlockInfo {
    private final Label bodyEntryPoint;
    private final Label bodyExitPoint;
    private final Label conditionEntryPoint;
    private final KtLoopExpression element;

    @Override // org.jetbrains.kotlin.cfg.BreakableBlockInfo
    public KtLoopExpression getElement() {
        return this.element;
    }

    public final Label getBodyEntryPoint() {
        return this.bodyEntryPoint;
    }

    public final Label getBodyExitPoint() {
        return this.bodyExitPoint;
    }

    public final Label getConditionEntryPoint() {
        return this.conditionEntryPoint;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LoopInfo(KtLoopExpression element, Label entryPoint, Label exitPoint, Label bodyEntryPoint, Label bodyExitPoint, Label conditionEntryPoint) {
        super(element, entryPoint, exitPoint);
        Intrinsics.checkNotNullParameter(element, "element");
        Intrinsics.checkNotNullParameter(entryPoint, "entryPoint");
        Intrinsics.checkNotNullParameter(exitPoint, "exitPoint");
        Intrinsics.checkNotNullParameter(bodyEntryPoint, "bodyEntryPoint");
        Intrinsics.checkNotNullParameter(bodyExitPoint, "bodyExitPoint");
        Intrinsics.checkNotNullParameter(conditionEntryPoint, "conditionEntryPoint");
        this.element = element;
        this.bodyEntryPoint = bodyEntryPoint;
        this.bodyExitPoint = bodyExitPoint;
        this.conditionEntryPoint = conditionEntryPoint;
        markReferablePoints(bodyEntryPoint, bodyExitPoint, conditionEntryPoint);
    }
}
