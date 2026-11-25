package org.jetbrains.kotlin.backend.jvm.intrinsics;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.jvm.codegen.BlockInfo;
import org.jetbrains.kotlin.backend.jvm.codegen.BooleanValue;
import org.jetbrains.kotlin.backend.jvm.codegen.ExpressionCodegen;
import org.jetbrains.kotlin.backend.jvm.codegen.PromisedValue;
import org.jetbrains.kotlin.backend.jvm.codegen.PromisedValueKt;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrUtilsKt;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionAccessExpression;
import org.jetbrains.org.objectweb.asm.Label;

/* JADX INFO: compiled from: AndAnd.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001:\u0001\u000bB\u0007\b\u0002¢\u0006\u0002\u0010\u0002J \u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/intrinsics/AndAnd;", "Lorg/jetbrains/kotlin/backend/jvm/intrinsics/IntrinsicMethod;", "()V", "invoke", "Lorg/jetbrains/kotlin/backend/jvm/codegen/PromisedValue;", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionAccessExpression;", "codegen", "Lorg/jetbrains/kotlin/backend/jvm/codegen/ExpressionCodegen;", "data", "Lorg/jetbrains/kotlin/backend/jvm/codegen/BlockInfo;", "BooleanConjunction", "backend.jvm.codegen"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class AndAnd extends IntrinsicMethod {
    public static final AndAnd INSTANCE = new AndAnd();

    /* JADX INFO: compiled from: AndAnd.kt */
    @Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0002\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\b\u0010\u000f\u001a\u00020\u0010H\u0016J\u0010\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u0012\u001a\u00020\u0013H\u0016J\u0010\u0010\u0014\u001a\u00020\u00102\u0006\u0010\u0012\u001a\u00020\u0013H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u000bR\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/intrinsics/AndAnd$BooleanConjunction;", "Lorg/jetbrains/kotlin/backend/jvm/codegen/BooleanValue;", "arg0", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "arg1", "codegen", "Lorg/jetbrains/kotlin/backend/jvm/codegen/ExpressionCodegen;", "data", "Lorg/jetbrains/kotlin/backend/jvm/codegen/BlockInfo;", "(Lorg/jetbrains/kotlin/ir/expressions/IrExpression;Lorg/jetbrains/kotlin/ir/expressions/IrExpression;Lorg/jetbrains/kotlin/backend/jvm/codegen/ExpressionCodegen;Lorg/jetbrains/kotlin/backend/jvm/codegen/BlockInfo;)V", "getArg0", "()Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "getArg1", "getData", "()Lorg/jetbrains/kotlin/backend/jvm/codegen/BlockInfo;", "discard", "", "jumpIfFalse", "target", "Lorg/jetbrains/org/objectweb/asm/Label;", "jumpIfTrue", "backend.jvm.codegen"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class BooleanConjunction extends BooleanValue {
        private final IrExpression arg0;
        private final IrExpression arg1;
        private final BlockInfo data;

        public final IrExpression getArg0() {
            return this.arg0;
        }

        public final IrExpression getArg1() {
            return this.arg1;
        }

        public final BlockInfo getData() {
            return this.data;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public BooleanConjunction(IrExpression arg0, IrExpression arg1, ExpressionCodegen codegen, BlockInfo data) {
            super(codegen);
            Intrinsics.checkNotNullParameter(arg0, "arg0");
            Intrinsics.checkNotNullParameter(arg1, "arg1");
            Intrinsics.checkNotNullParameter(codegen, "codegen");
            Intrinsics.checkNotNullParameter(data, "data");
            this.arg0 = arg0;
            this.arg1 = arg1;
            this.data = data;
        }

        @Override // org.jetbrains.kotlin.backend.jvm.codegen.BooleanValue
        public void jumpIfFalse(Label target) {
            Intrinsics.checkNotNullParameter(target, "target");
            PromisedValueKt.coerceToBoolean((PromisedValue) this.arg0.accept(getCodegen(), this.data)).jumpIfFalse(target);
            PromisedValueKt.coerceToBoolean((PromisedValue) this.arg1.accept(getCodegen(), this.data)).jumpIfFalse(target);
        }

        @Override // org.jetbrains.kotlin.backend.jvm.codegen.BooleanValue
        public void jumpIfTrue(Label target) {
            Intrinsics.checkNotNullParameter(target, "target");
            Label label = new Label();
            PromisedValueKt.coerceToBoolean((PromisedValue) this.arg0.accept(getCodegen(), this.data)).jumpIfFalse(label);
            PromisedValueKt.coerceToBoolean((PromisedValue) this.arg1.accept(getCodegen(), this.data)).jumpIfTrue(target);
            getMv().visitLabel(label);
        }

        @Override // org.jetbrains.kotlin.backend.jvm.codegen.PromisedValue
        public void discard() {
            Label label = new Label();
            PromisedValueKt.coerceToBoolean((PromisedValue) this.arg0.accept(getCodegen(), this.data)).jumpIfFalse(label);
            ((PromisedValue) this.arg1.accept(getCodegen(), this.data)).discard();
            getMv().visitLabel(label);
        }
    }

    private AndAnd() {
    }

    @Override // org.jetbrains.kotlin.backend.jvm.intrinsics.IntrinsicMethod
    public PromisedValue invoke(IrFunctionAccessExpression expression, ExpressionCodegen codegen, BlockInfo data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(codegen, "codegen");
        Intrinsics.checkNotNullParameter(data, "data");
        List<IrExpression> listReceiverAndArgs = JvmIrUtilsKt.receiverAndArgs(expression);
        return new BooleanConjunction(listReceiverAndArgs.get(0), listReceiverAndArgs.get(1), codegen, data);
    }
}
