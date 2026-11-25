package org.jetbrains.kotlin.ir.declarations;

import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.expressions.IrBlockBody;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrExpressionBody;

/* JADX INFO: compiled from: IrFactoryHelpers.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00008\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a(\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00042\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\u001a3\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00042\u0017\u0010\t\u001a\u0013\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u000b0\n¢\u0006\u0002\b\f\u001a\u0012\u0010\r\u001a\u00020\u000e*\u00020\u00022\u0006\u0010\u000f\u001a\u00020\u0010¨\u0006\u0011"}, d2 = {"createBlockBody", "Lorg/jetbrains/kotlin/ir/expressions/IrBlockBody;", "Lorg/jetbrains/kotlin/ir/declarations/IrFactory;", "startOffset", "", "endOffset", "statements", "", "Lorg/jetbrains/kotlin/ir/IrStatement;", "initializer", "Lkotlin/Function1;", "", "Lkotlin/ExtensionFunctionType;", "createExpressionBody", "Lorg/jetbrains/kotlin/ir/expressions/IrExpressionBody;", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "ir.tree"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class IrFactoryHelpersKt {
    public static final IrExpressionBody createExpressionBody(IrFactory irFactory, IrExpression expression) {
        Intrinsics.checkNotNullParameter(irFactory, "<this>");
        Intrinsics.checkNotNullParameter(expression, "expression");
        return irFactory.createExpressionBody(expression.getStartOffset(), expression.getEndOffset(), expression);
    }

    public static final IrBlockBody createBlockBody(IrFactory irFactory, int i, int i2, Function1<? super IrBlockBody, Unit> initializer) {
        Intrinsics.checkNotNullParameter(irFactory, "<this>");
        Intrinsics.checkNotNullParameter(initializer, "initializer");
        IrBlockBody irBlockBodyCreateBlockBody = irFactory.createBlockBody(i, i2);
        initializer.invoke(irBlockBodyCreateBlockBody);
        return irBlockBodyCreateBlockBody;
    }

    public static final IrBlockBody createBlockBody(IrFactory irFactory, int i, int i2, final List<? extends IrStatement> statements) {
        Intrinsics.checkNotNullParameter(irFactory, "<this>");
        Intrinsics.checkNotNullParameter(statements, "statements");
        return createBlockBody(irFactory, i, i2, new Function1<IrBlockBody, Unit>() { // from class: org.jetbrains.kotlin.ir.declarations.IrFactoryHelpersKt.createBlockBody.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(IrBlockBody irBlockBody) {
                invoke2(irBlockBody);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(IrBlockBody createBlockBody) {
                Intrinsics.checkNotNullParameter(createBlockBody, "$this$createBlockBody");
                createBlockBody.getStatements().addAll(statements);
            }
        });
    }
}
