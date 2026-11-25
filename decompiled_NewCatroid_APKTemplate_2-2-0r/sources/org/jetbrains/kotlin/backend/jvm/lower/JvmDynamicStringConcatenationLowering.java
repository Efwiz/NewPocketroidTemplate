package org.jetbrains.kotlin.backend.jvm.lower;

import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.FileLoweringPass;
import org.jetbrains.kotlin.backend.common.IrElementTransformerVoidWithContext;
import org.jetbrains.kotlin.backend.common.ScopeWithIr;
import org.jetbrains.kotlin.backend.jvm.JvmBackendContext;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrBuilder;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrBuilderKt;
import org.jetbrains.kotlin.ir.builders.ExpressionHelpersKt;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrStringConcatenation;
import org.jetbrains.kotlin.ir.expressions.impl.IrStringConcatenationImpl;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoidKt;

/* JADX INFO: compiled from: JvmStringConcatenationLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u00012\u00020\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/lower/JvmDynamicStringConcatenationLowering;", "Lorg/jetbrains/kotlin/backend/common/FileLoweringPass;", "Lorg/jetbrains/kotlin/backend/common/IrElementTransformerVoidWithContext;", "context", "Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "(Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;)V", "getContext", "()Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "lower", "", "irFile", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "visitStringConcatenation", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrStringConcatenation;", "backend.jvm.lower"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class JvmDynamicStringConcatenationLowering extends IrElementTransformerVoidWithContext implements FileLoweringPass {
    private final JvmBackendContext context;

    public final JvmBackendContext getContext() {
        return this.context;
    }

    public JvmDynamicStringConcatenationLowering(JvmBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
    }

    @Override // org.jetbrains.kotlin.backend.common.FileLoweringPass
    public void lower(IrFile irFile) {
        Intrinsics.checkNotNullParameter(irFile, "irFile");
        transformChildrenVoid(irFile);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrExpression visitStringConcatenation(IrStringConcatenation expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrStringConcatenation irStringConcatenation = expression;
        IrElementTransformerVoidKt.transformChildrenVoid(irStringConcatenation, this);
        JvmBackendContext jvmBackendContext = this.context;
        ScopeWithIr currentScope = getCurrentScope();
        Intrinsics.checkNotNull(currentScope);
        JvmIrBuilder jvmIrBuilderCreateJvmIrBuilder = JvmIrBuilderKt.createJvmIrBuilder(jvmBackendContext, currentScope, irStringConcatenation);
        List<IrExpression> arguments = expression.getArguments();
        if (arguments.isEmpty()) {
            return ExpressionHelpersKt.irString(jvmIrBuilderCreateJvmIrBuilder, "");
        }
        if (arguments.size() == 1) {
            IrExpression irExpressionLowerInlineClassArgument = JvmStringConcatenationLoweringKt.lowerInlineClassArgument(jvmIrBuilderCreateJvmIrBuilder, arguments.get(0));
            return irExpressionLowerInlineClassArgument == null ? JvmStringConcatenationLoweringKt.callToString(jvmIrBuilderCreateJvmIrBuilder, JvmStringConcatenationLoweringKt.unwrapImplicitNotNull(arguments.get(0))) : irExpressionLowerInlineClassArgument;
        }
        int startOffset = expression.getStartOffset();
        int endOffset = expression.getEndOffset();
        IrType type = expression.getType();
        List<IrExpression> list = arguments;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        for (IrExpression irExpression : list) {
            IrExpression irExpressionLowerInlineClassArgument2 = JvmStringConcatenationLoweringKt.lowerInlineClassArgument(jvmIrBuilderCreateJvmIrBuilder, irExpression);
            if (irExpressionLowerInlineClassArgument2 == null) {
                irExpressionLowerInlineClassArgument2 = JvmStringConcatenationLoweringKt.unwrapImplicitNotNull(irExpression);
            }
            arrayList.add(irExpressionLowerInlineClassArgument2);
        }
        return new IrStringConcatenationImpl(startOffset, endOffset, type, arrayList);
    }
}
