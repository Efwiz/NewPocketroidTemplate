package org.jetbrains.kotlin.backend.jvm.codegen;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.expressions.IrLoop;
import org.jetbrains.org.objectweb.asm.Label;

/* JADX INFO: compiled from: ExpressionCodegen.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005¢\u0006\u0002\u0010\u0007R\u0011\u0010\u0006\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\tR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/codegen/LoopInfo;", "Lorg/jetbrains/kotlin/backend/jvm/codegen/ExpressionInfo;", "loop", "Lorg/jetbrains/kotlin/ir/expressions/IrLoop;", "continueLabel", "Lorg/jetbrains/org/objectweb/asm/Label;", "breakLabel", "(Lorg/jetbrains/kotlin/ir/expressions/IrLoop;Lorg/jetbrains/org/objectweb/asm/Label;Lorg/jetbrains/org/objectweb/asm/Label;)V", "getBreakLabel", "()Lorg/jetbrains/org/objectweb/asm/Label;", "getContinueLabel", "getLoop", "()Lorg/jetbrains/kotlin/ir/expressions/IrLoop;", "backend.jvm.codegen"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class LoopInfo extends ExpressionInfo {
    private final Label breakLabel;
    private final Label continueLabel;
    private final IrLoop loop;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LoopInfo(IrLoop loop, Label continueLabel, Label breakLabel) {
        super(null);
        Intrinsics.checkNotNullParameter(loop, "loop");
        Intrinsics.checkNotNullParameter(continueLabel, "continueLabel");
        Intrinsics.checkNotNullParameter(breakLabel, "breakLabel");
        this.loop = loop;
        this.continueLabel = continueLabel;
        this.breakLabel = breakLabel;
    }

    public final Label getBreakLabel() {
        return this.breakLabel;
    }

    public final Label getContinueLabel() {
        return this.continueLabel;
    }

    public final IrLoop getLoop() {
        return this.loop;
    }
}
