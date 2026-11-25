package org.jetbrains.kotlin.backend.jvm.intrinsics;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.jvm.codegen.BlockInfo;
import org.jetbrains.kotlin.backend.jvm.codegen.ExpressionCodegen;
import org.jetbrains.kotlin.backend.jvm.codegen.PromisedValue;
import org.jetbrains.kotlin.backend.jvm.codegen.PromisedValueKt;
import org.jetbrains.kotlin.backend.jvm.mapping.IrTypeMapper;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionAccessExpression;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.org.objectweb.asm.Type;

/* JADX INFO: compiled from: UnsafeCoerce.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J \u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/intrinsics/UnsafeCoerce;", "Lorg/jetbrains/kotlin/backend/jvm/intrinsics/IntrinsicMethod;", "()V", "invoke", "Lorg/jetbrains/kotlin/backend/jvm/codegen/PromisedValue;", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionAccessExpression;", "codegen", "Lorg/jetbrains/kotlin/backend/jvm/codegen/ExpressionCodegen;", "data", "Lorg/jetbrains/kotlin/backend/jvm/codegen/BlockInfo;", "backend.jvm.codegen"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class UnsafeCoerce extends IntrinsicMethod {
    public static final UnsafeCoerce INSTANCE = new UnsafeCoerce();

    private UnsafeCoerce() {
    }

    @Override // org.jetbrains.kotlin.backend.jvm.intrinsics.IntrinsicMethod
    public PromisedValue invoke(IrFunctionAccessExpression expression, ExpressionCodegen codegen, BlockInfo data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(codegen, "codegen");
        Intrinsics.checkNotNullParameter(data, "data");
        final IrType typeArgument = expression.getTypeArgument(0);
        Intrinsics.checkNotNull(typeArgument);
        IrType typeArgument2 = expression.getTypeArgument(1);
        Intrinsics.checkNotNull(typeArgument2);
        final Type typeMapType$default = IrTypeMapper.mapType$default(codegen.getTypeMapper(), typeArgument, null, null, false, 14, null);
        Type typeMapType$default2 = IrTypeMapper.mapType$default(codegen.getTypeMapper(), typeArgument2, null, null, false, 14, null);
        if (!Intrinsics.areEqual(typeMapType$default, typeMapType$default2)) {
            throw new IllegalArgumentException(("Inline class types should have the same representation: " + typeMapType$default + " != " + typeMapType$default2).toString());
        }
        IrExpression valueArgument = expression.getValueArgument(0);
        Intrinsics.checkNotNull(valueArgument);
        final PromisedValue promisedValue = (PromisedValue) valueArgument.accept(codegen, data);
        return new PromisedValue(codegen, typeMapType$default2, typeArgument2) { // from class: org.jetbrains.kotlin.backend.jvm.intrinsics.UnsafeCoerce.invoke.2
            @Override // org.jetbrains.kotlin.backend.jvm.codegen.PromisedValue
            public void materializeAt(Type target, IrType irTarget, boolean castForReified) {
                Intrinsics.checkNotNullParameter(target, "target");
                Intrinsics.checkNotNullParameter(irTarget, "irTarget");
                PromisedValueKt.materializeAt(promisedValue, typeMapType$default, typeArgument);
                super.materializeAt(target, irTarget, castForReified);
            }

            @Override // org.jetbrains.kotlin.backend.jvm.codegen.PromisedValue
            public void discard() {
                promisedValue.discard();
            }
        };
    }
}
