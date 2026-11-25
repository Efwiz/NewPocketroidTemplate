package org.jetbrains.kotlin.backend.jvm.intrinsics;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.jvm.codegen.BlockInfo;
import org.jetbrains.kotlin.backend.jvm.codegen.BooleanValue;
import org.jetbrains.kotlin.backend.jvm.codegen.ExpressionCodegen;
import org.jetbrains.kotlin.backend.jvm.codegen.PromisedValue;
import org.jetbrains.kotlin.backend.jvm.codegen.PromisedValueKt;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionAccessExpression;
import org.jetbrains.org.objectweb.asm.Label;

/* JADX INFO: compiled from: Not.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001:\u0001\u000bB\u0007\b\u0002¢\u0006\u0002\u0010\u0002J \u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/intrinsics/Not;", "Lorg/jetbrains/kotlin/backend/jvm/intrinsics/IntrinsicMethod;", "()V", "invoke", "Lorg/jetbrains/kotlin/backend/jvm/intrinsics/Not$BooleanNegation;", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionAccessExpression;", "codegen", "Lorg/jetbrains/kotlin/backend/jvm/codegen/ExpressionCodegen;", "data", "Lorg/jetbrains/kotlin/backend/jvm/codegen/BlockInfo;", "BooleanNegation", "backend.jvm.codegen"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class Not extends IntrinsicMethod {
    public static final Not INSTANCE = new Not();

    /* JADX INFO: compiled from: Not.kt */
    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0001¢\u0006\u0002\u0010\u0003J\b\u0010\u0006\u001a\u00020\u0007H\u0016J\u0010\u0010\b\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\nH\u0016J\u0010\u0010\u000b\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\nH\u0016R\u0011\u0010\u0002\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\u0005¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/intrinsics/Not$BooleanNegation;", "Lorg/jetbrains/kotlin/backend/jvm/codegen/BooleanValue;", "value", "(Lorg/jetbrains/kotlin/backend/jvm/codegen/BooleanValue;)V", "getValue", "()Lorg/jetbrains/kotlin/backend/jvm/codegen/BooleanValue;", "discard", "", "jumpIfFalse", "target", "Lorg/jetbrains/org/objectweb/asm/Label;", "jumpIfTrue", "backend.jvm.codegen"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class BooleanNegation extends BooleanValue {
        private final BooleanValue value;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public BooleanNegation(BooleanValue value) {
            super(value.getCodegen());
            Intrinsics.checkNotNullParameter(value, "value");
            this.value = value;
        }

        public final BooleanValue getValue() {
            return this.value;
        }

        @Override // org.jetbrains.kotlin.backend.jvm.codegen.BooleanValue
        public void jumpIfFalse(Label target) {
            Intrinsics.checkNotNullParameter(target, "target");
            this.value.jumpIfTrue(target);
        }

        @Override // org.jetbrains.kotlin.backend.jvm.codegen.BooleanValue
        public void jumpIfTrue(Label target) {
            Intrinsics.checkNotNullParameter(target, "target");
            this.value.jumpIfFalse(target);
        }

        @Override // org.jetbrains.kotlin.backend.jvm.codegen.PromisedValue
        public void discard() {
            this.value.discard();
        }
    }

    private Not() {
    }

    @Override // org.jetbrains.kotlin.backend.jvm.intrinsics.IntrinsicMethod
    public BooleanNegation invoke(IrFunctionAccessExpression expression, ExpressionCodegen codegen, BlockInfo data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(codegen, "codegen");
        Intrinsics.checkNotNullParameter(data, "data");
        IrExpression dispatchReceiver = expression.getDispatchReceiver();
        Intrinsics.checkNotNull(dispatchReceiver);
        return new BooleanNegation(PromisedValueKt.coerceToBoolean((PromisedValue) dispatchReceiver.accept(codegen, data)));
    }
}
