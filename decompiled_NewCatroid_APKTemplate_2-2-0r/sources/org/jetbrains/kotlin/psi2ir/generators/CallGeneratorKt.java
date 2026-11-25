package org.jetbrains.kotlin.psi2ir.generators;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrStatementOrigin;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.psiUtil.PsiUtilsKt;
import org.jetbrains.kotlin.psi2ir.intermediate.CallBuilder;

/* JADX INFO: compiled from: CallGenerator.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000(\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\u001a(\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00012\u0006\u0010\u0004\u001a\u00020\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0007H\u0000\u001a<\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\u0004\u001a\u00020\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00072\b\b\u0002\u0010\n\u001a\u00020\u000b2\b\b\u0002\u0010\f\u001a\u00020\u000bH\u0000¨\u0006\r"}, d2 = {"generateCall", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "Lorg/jetbrains/kotlin/psi2ir/generators/CallGenerator;", "irExpression", "call", "Lorg/jetbrains/kotlin/psi2ir/intermediate/CallBuilder;", "origin", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin;", "ktElement", "Lorg/jetbrains/kotlin/psi/KtElement;", "startOffset", "", "endOffset", "ir.psi2ir"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class CallGeneratorKt {
    public static /* synthetic */ IrExpression generateCall$default(CallGenerator callGenerator, KtElement ktElement, CallBuilder callBuilder, IrStatementOrigin irStatementOrigin, int i, int i2, int i3, Object obj) {
        if ((i3 & 4) != 0) {
            irStatementOrigin = null;
        }
        IrStatementOrigin irStatementOrigin2 = irStatementOrigin;
        if ((i3 & 8) != 0) {
            i = PsiUtilsKt.getStartOffsetSkippingComments(ktElement);
        }
        int i4 = i;
        if ((i3 & 16) != 0) {
            i2 = PsiUtilsKt.getEndOffset(ktElement);
        }
        return generateCall(callGenerator, ktElement, callBuilder, irStatementOrigin2, i4, i2);
    }

    public static final IrExpression generateCall(CallGenerator callGenerator, KtElement ktElement, CallBuilder call, IrStatementOrigin irStatementOrigin, int i, int i2) {
        Intrinsics.checkNotNullParameter(callGenerator, "<this>");
        Intrinsics.checkNotNullParameter(ktElement, "ktElement");
        Intrinsics.checkNotNullParameter(call, "call");
        return callGenerator.generateCall(i, i2, call, irStatementOrigin);
    }

    public static /* synthetic */ IrExpression generateCall$default(CallGenerator callGenerator, IrExpression irExpression, CallBuilder callBuilder, IrStatementOrigin irStatementOrigin, int i, Object obj) {
        if ((i & 4) != 0) {
            irStatementOrigin = null;
        }
        return generateCall(callGenerator, irExpression, callBuilder, irStatementOrigin);
    }

    public static final IrExpression generateCall(CallGenerator callGenerator, IrExpression irExpression, CallBuilder call, IrStatementOrigin irStatementOrigin) {
        Intrinsics.checkNotNullParameter(callGenerator, "<this>");
        Intrinsics.checkNotNullParameter(irExpression, "irExpression");
        Intrinsics.checkNotNullParameter(call, "call");
        return callGenerator.generateCall(irExpression.getStartOffset(), irExpression.getEndOffset(), call, irStatementOrigin);
    }
}
