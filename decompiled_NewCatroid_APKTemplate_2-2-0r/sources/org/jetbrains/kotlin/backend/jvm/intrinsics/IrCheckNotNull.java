package org.jetbrains.kotlin.backend.jvm.intrinsics;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.jvm.codegen.BlockInfo;
import org.jetbrains.kotlin.backend.jvm.codegen.ExpressionCodegen;
import org.jetbrains.kotlin.backend.jvm.codegen.MaterialValue;
import org.jetbrains.kotlin.backend.jvm.codegen.PromisedValue;
import org.jetbrains.kotlin.backend.jvm.codegen.PromisedValueKt;
import org.jetbrains.kotlin.codegen.AsmUtil;
import org.jetbrains.kotlin.codegen.inline.ReifiedTypeInliner;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionAccessExpression;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.org.objectweb.asm.Label;
import org.jetbrains.org.objectweb.asm.Type;

/* JADX INFO: compiled from: IrCheckNotNull.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J \u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J\f\u0010\u000b\u001a\u00020\f*\u00020\bH\u0002¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/intrinsics/IrCheckNotNull;", "Lorg/jetbrains/kotlin/backend/jvm/intrinsics/IntrinsicMethod;", "()V", "invoke", "Lorg/jetbrains/kotlin/backend/jvm/codegen/PromisedValue;", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionAccessExpression;", "codegen", "Lorg/jetbrains/kotlin/backend/jvm/codegen/ExpressionCodegen;", "data", "Lorg/jetbrains/kotlin/backend/jvm/codegen/BlockInfo;", "checkTopValueForNull", "", "backend.jvm.codegen"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IrCheckNotNull extends IntrinsicMethod {
    public static final IrCheckNotNull INSTANCE = new IrCheckNotNull();

    private IrCheckNotNull() {
    }

    @Override // org.jetbrains.kotlin.backend.jvm.intrinsics.IntrinsicMethod
    public PromisedValue invoke(IrFunctionAccessExpression expression, ExpressionCodegen codegen, BlockInfo data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(codegen, "codegen");
        Intrinsics.checkNotNullParameter(data, "data");
        IrExpression valueArgument = expression.getValueArgument(0);
        Intrinsics.checkNotNull(valueArgument);
        PromisedValue promisedValue = (PromisedValue) valueArgument.accept(codegen, data);
        return AsmUtil.isPrimitive(promisedValue.getType()) ? promisedValue : new PromisedValue(promisedValue, promisedValue.getType(), promisedValue.getIrType()) { // from class: org.jetbrains.kotlin.backend.jvm.intrinsics.IrCheckNotNull.invoke.1
            final /* synthetic */ PromisedValue $arg0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(this.$codegen, type, irType);
                this.$arg0 = promisedValue;
            }

            @Override // org.jetbrains.kotlin.backend.jvm.codegen.PromisedValue
            public void materializeAt(Type target, IrType irTarget, boolean castForReified) {
                Intrinsics.checkNotNullParameter(target, "target");
                Intrinsics.checkNotNullParameter(irTarget, "irTarget");
                MaterialValue materialValueMaterialized = PromisedValueKt.materialized(this.$arg0);
                IrCheckNotNull.INSTANCE.checkTopValueForNull(this.$codegen);
                materialValueMaterialized.materializeAt(target, irTarget, castForReified);
            }

            @Override // org.jetbrains.kotlin.backend.jvm.codegen.PromisedValue
            public void discard() {
                MaterialValue materialValueMaterialized = PromisedValueKt.materialized(this.$arg0);
                IrCheckNotNull.INSTANCE.checkTopValueForNull(this.$codegen);
                materialValueMaterialized.discard();
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void checkTopValueForNull(ExpressionCodegen expressionCodegen) {
        expressionCodegen.getMv().dup();
        if (expressionCodegen.getState().getConfig().getUnifiedNullChecks()) {
            expressionCodegen.getMv().invokestatic("kotlin/jvm/internal/Intrinsics", "checkNotNull", ReifiedTypeInliner.pluginIntrinsicsMarkerSignature, false);
            return;
        }
        Label label = new Label();
        expressionCodegen.getMv().ifnonnull(label);
        expressionCodegen.getMv().invokestatic("kotlin/jvm/internal/Intrinsics", "throwNpe", "()V", false);
        expressionCodegen.getMv().mark(label);
    }
}
