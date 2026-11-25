package org.jetbrains.kotlin.codegen.range;

import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import org.jetbrains.kotlin.codegen.ExpressionCodegen;
import org.jetbrains.kotlin.codegen.range.forLoop.ForLoopGenerator;
import org.jetbrains.kotlin.psi.KtForExpression;

/* JADX INFO: compiled from: RangeValue.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0018\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\bÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/codegen/range/ReversableRangeValue;", "Lorg/jetbrains/kotlin/codegen/range/RangeValue;", "createForInReversedLoopGenerator", "Lorg/jetbrains/kotlin/codegen/range/forLoop/ForLoopGenerator;", "codegen", "Lorg/jetbrains/kotlin/codegen/ExpressionCodegen;", "forExpression", "Lorg/jetbrains/kotlin/psi/KtForExpression;", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface ReversableRangeValue extends RangeValue {
    ForLoopGenerator createForInReversedLoopGenerator(ExpressionCodegen codegen, KtForExpression forExpression);
}
