package org.jetbrains.kotlin.ir.expressions;

import kotlin.Metadata;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;

/* JADX INFO: compiled from: IrLoop.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0001X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0004\u0010\u0005\"\u0004\b\u0006\u0010\u0007R\u0018\u0010\b\u001a\u00020\u0001X¦\u000e¢\u0006\f\u001a\u0004\b\t\u0010\u0005\"\u0004\b\n\u0010\u0007R\u001c\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u001a\u0010\u0011\u001a\u0004\u0018\u00010\u0012X¦\u000e¢\u0006\f\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/ir/expressions/IrLoop;", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "()V", "body", "getBody", "()Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "setBody", "(Lorg/jetbrains/kotlin/ir/expressions/IrExpression;)V", "condition", "getCondition", "setCondition", CoroutineCodegenUtilKt.COROUTINE_LABEL_FIELD_NAME, "", "getLabel", "()Ljava/lang/String;", "setLabel", "(Ljava/lang/String;)V", "origin", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin;", "getOrigin", "()Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin;", "setOrigin", "(Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin;)V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class IrLoop extends IrExpression {
    private IrExpression body;
    private String label;

    public abstract IrExpression getCondition();

    public abstract IrStatementOrigin getOrigin();

    public abstract void setCondition(IrExpression irExpression);

    public abstract void setOrigin(IrStatementOrigin irStatementOrigin);

    public final IrExpression getBody() {
        return this.body;
    }

    public final void setBody(IrExpression irExpression) {
        this.body = irExpression;
    }

    public final String getLabel() {
        return this.label;
    }

    public final void setLabel(String str) {
        this.label = str;
    }
}
