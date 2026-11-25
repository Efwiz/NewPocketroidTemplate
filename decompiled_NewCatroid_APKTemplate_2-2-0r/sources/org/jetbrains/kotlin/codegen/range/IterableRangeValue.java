package org.jetbrains.kotlin.codegen.range;

import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.ExpressionCodegen;
import org.jetbrains.kotlin.codegen.range.forLoop.IteratorForLoopGenerator;
import org.jetbrains.kotlin.codegen.range.inExpression.CallBasedInExpressionGenerator;
import org.jetbrains.kotlin.codegen.range.inExpression.InExpressionGenerator;
import org.jetbrains.kotlin.psi.KtForExpression;
import org.jetbrains.kotlin.psi.KtSimpleNameExpression;

/* JADX INFO: compiled from: IterableRangeValue.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016J\u0018\u0010\t\u001a\u00020\n2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\fH\u0016¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/codegen/range/IterableRangeValue;", "Lorg/jetbrains/kotlin/codegen/range/RangeValue;", "()V", "createForLoopGenerator", "Lorg/jetbrains/kotlin/codegen/range/forLoop/IteratorForLoopGenerator;", "codegen", "Lorg/jetbrains/kotlin/codegen/ExpressionCodegen;", "forExpression", "Lorg/jetbrains/kotlin/psi/KtForExpression;", "createInExpressionGenerator", "Lorg/jetbrains/kotlin/codegen/range/inExpression/InExpressionGenerator;", "operatorReference", "Lorg/jetbrains/kotlin/psi/KtSimpleNameExpression;", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IterableRangeValue implements RangeValue {
    @Override // org.jetbrains.kotlin.codegen.range.RangeValue
    public IteratorForLoopGenerator createForLoopGenerator(ExpressionCodegen codegen, KtForExpression forExpression) {
        Intrinsics.checkNotNullParameter(codegen, "codegen");
        Intrinsics.checkNotNullParameter(forExpression, "forExpression");
        return new IteratorForLoopGenerator(codegen, forExpression);
    }

    @Override // org.jetbrains.kotlin.codegen.range.RangeValue
    public InExpressionGenerator createInExpressionGenerator(ExpressionCodegen codegen, KtSimpleNameExpression operatorReference) {
        Intrinsics.checkNotNullParameter(codegen, "codegen");
        Intrinsics.checkNotNullParameter(operatorReference, "operatorReference");
        return new CallBasedInExpressionGenerator(codegen, operatorReference);
    }
}
