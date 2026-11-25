package org.jetbrains.kotlin.ir.expressions;

import kotlin.Metadata;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;

/* JADX INFO: compiled from: IrBreakContinue.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u0018\u0010\t\u001a\u00020\nX¦\u000e¢\u0006\f\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000e¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/ir/expressions/IrBreakContinue;", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "()V", CoroutineCodegenUtilKt.COROUTINE_LABEL_FIELD_NAME, "", "getLabel", "()Ljava/lang/String;", "setLabel", "(Ljava/lang/String;)V", "loop", "Lorg/jetbrains/kotlin/ir/expressions/IrLoop;", "getLoop", "()Lorg/jetbrains/kotlin/ir/expressions/IrLoop;", "setLoop", "(Lorg/jetbrains/kotlin/ir/expressions/IrLoop;)V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class IrBreakContinue extends IrExpression {
    private String label;

    public abstract IrLoop getLoop();

    public abstract void setLoop(IrLoop irLoop);

    public final String getLabel() {
        return this.label;
    }

    public final void setLabel(String str) {
        this.label = str;
    }
}
