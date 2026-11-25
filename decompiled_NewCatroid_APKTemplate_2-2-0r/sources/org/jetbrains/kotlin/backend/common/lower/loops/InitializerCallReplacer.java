package org.jetbrains.kotlin.backend.common.lower.loops;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.util.DumpIrTreeOptions;
import org.jetbrains.kotlin.ir.util.RenderIrElementKt;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid;

/* JADX INFO: compiled from: HeaderProcessor.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u000b\u001a\u00020\u00032\u0006\u0010\f\u001a\u00020\u0006H\u0016R\u001c\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/loops/InitializerCallReplacer;", "Lorg/jetbrains/kotlin/ir/visitors/IrElementTransformerVoid;", "replacement", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "(Lorg/jetbrains/kotlin/ir/expressions/IrExpression;)V", "initializerCall", "Lorg/jetbrains/kotlin/ir/expressions/IrCall;", "getInitializerCall", "()Lorg/jetbrains/kotlin/ir/expressions/IrCall;", "setInitializerCall", "(Lorg/jetbrains/kotlin/ir/expressions/IrCall;)V", "visitCall", "expression", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class InitializerCallReplacer extends IrElementTransformerVoid {
    private IrCall initializerCall;
    private final IrExpression replacement;

    public InitializerCallReplacer(IrExpression replacement) {
        Intrinsics.checkNotNullParameter(replacement, "replacement");
        this.replacement = replacement;
    }

    public final IrCall getInitializerCall() {
        return this.initializerCall;
    }

    public final void setInitializerCall(IrCall irCall) {
        this.initializerCall = irCall;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrExpression visitCall(IrCall expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        if (this.initializerCall != null) {
            StringBuilder sb = new StringBuilder("Multiple initializer calls found. First: ");
            IrCall irCall = this.initializerCall;
            Intrinsics.checkNotNull(irCall);
            throw new IllegalStateException(sb.append(RenderIrElementKt.render$default(irCall, (DumpIrTreeOptions) null, 1, (Object) null)).append("\nSecond: ").append(RenderIrElementKt.render$default(expression, (DumpIrTreeOptions) null, 1, (Object) null)).toString());
        }
        this.initializerCall = expression;
        return this.replacement;
    }
}
