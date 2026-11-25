package org.jetbrains.kotlin.backend.common.lower;

import java.util.ArrayList;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.BodyLoweringPass;
import org.jetbrains.kotlin.backend.common.CommonBackendContext;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.expressions.IrBody;
import org.jetbrains.kotlin.ir.expressions.IrErrorCallExpression;
import org.jetbrains.kotlin.ir.expressions.IrErrorExpression;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrTypeOperatorCall;
import org.jetbrains.kotlin.ir.expressions.impl.IrCompositeImpl;
import org.jetbrains.kotlin.ir.types.IrErrorType;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoidKt;

/* JADX INFO: compiled from: ErrorExpressionLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b&\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0018\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0018\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u0012\u001a\u00020\u0013H&R\u0014\u0010\u0005\u001a\u00020\u0006X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/ErrorExpressionLowering;", "Lorg/jetbrains/kotlin/backend/common/BodyLoweringPass;", "context", "Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;", "(Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;)V", "nothingType", "Lorg/jetbrains/kotlin/ir/types/IrType;", "getNothingType", "()Lorg/jetbrains/kotlin/ir/types/IrType;", "lower", "", "irBody", "Lorg/jetbrains/kotlin/ir/expressions/IrBody;", "container", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "transformErrorExpression", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "expression", "nodeKind", "", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class ErrorExpressionLowering implements BodyLoweringPass {
    private final IrType nothingType;

    public abstract IrExpression transformErrorExpression(IrExpression expression, String nodeKind);

    public ErrorExpressionLowering(CommonBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.nothingType = context.getIrBuiltIns().getNothingType();
    }

    protected final IrType getNothingType() {
        return this.nothingType;
    }

    @Override // org.jetbrains.kotlin.backend.common.BodyLoweringPass
    public void lower(IrBody irBody, IrDeclaration container) {
        Intrinsics.checkNotNullParameter(irBody, "irBody");
        Intrinsics.checkNotNullParameter(container, "container");
        IrElementTransformerVoidKt.transformChildrenVoid(irBody, new IrElementTransformerVoid() { // from class: org.jetbrains.kotlin.backend.common.lower.ErrorExpressionLowering.lower.1
            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
            public IrExpression visitErrorExpression(IrErrorExpression expression) {
                Intrinsics.checkNotNullParameter(expression, "expression");
                return ErrorExpressionLowering.this.transformErrorExpression(expression, "Error Expression");
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
            public IrExpression visitErrorCallExpression(IrErrorCallExpression expression) {
                Intrinsics.checkNotNullParameter(expression, "expression");
                IrElementTransformerVoidKt.transformChildrenVoid(expression, this);
                ArrayList arrayList = new ArrayList();
                ErrorExpressionLowering errorExpressionLowering = ErrorExpressionLowering.this;
                IrExpression explicitReceiver = expression.getExplicitReceiver();
                if (explicitReceiver != null) {
                    arrayList.add(explicitReceiver);
                }
                arrayList.addAll(expression.getArguments());
                arrayList.add(errorExpressionLowering.transformErrorExpression(expression, "Error Call"));
                return new IrCompositeImpl(expression.getStartOffset(), expression.getEndOffset(), ErrorExpressionLowering.this.getNothingType(), null, arrayList);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
            public IrExpression visitTypeOperator(IrTypeOperatorCall expression) {
                Intrinsics.checkNotNullParameter(expression, "expression");
                IrElementTransformerVoidKt.transformChildrenVoid(expression, this);
                if (expression.getTypeOperand() instanceof IrErrorType) {
                    ErrorExpressionLowering errorExpressionLowering = ErrorExpressionLowering.this;
                    return new IrCompositeImpl(expression.getStartOffset(), expression.getEndOffset(), errorExpressionLowering.getNothingType(), null, CollectionsKt.listOf((Object[]) new IrExpression[]{expression.getArgument(), errorExpressionLowering.transformErrorExpression(expression, "Error Type")}));
                }
                return expression;
            }
        });
    }
}
