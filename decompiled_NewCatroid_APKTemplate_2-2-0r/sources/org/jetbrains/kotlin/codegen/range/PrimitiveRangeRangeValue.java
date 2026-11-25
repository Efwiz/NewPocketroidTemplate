package org.jetbrains.kotlin.codegen.range;

import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.ExpressionCodegen;
import org.jetbrains.kotlin.codegen.range.comparison.ComparisonGeneratorKt;
import org.jetbrains.kotlin.codegen.range.forLoop.ForInRangeInstanceLoopGenerator;
import org.jetbrains.kotlin.codegen.range.forLoop.ForLoopGenerator;
import org.jetbrains.kotlin.codegen.range.inExpression.CallBasedInExpressionGenerator;
import org.jetbrains.kotlin.codegen.range.inExpression.InExpressionGenerator;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtForExpression;
import org.jetbrains.kotlin.psi.KtSimpleNameExpression;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.RangeUtilKt;

/* JADX INFO: compiled from: PrimitiveRangeRangeValue.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0018\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J\u0018\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J\u0018\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016J\u0018\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/codegen/range/PrimitiveRangeRangeValue;", "Lorg/jetbrains/kotlin/codegen/range/ReversableRangeValue;", "rangeExpression", "Lorg/jetbrains/kotlin/psi/KtExpression;", "(Lorg/jetbrains/kotlin/psi/KtExpression;)V", "createForInReversedLoopGenerator", "Lorg/jetbrains/kotlin/codegen/range/forLoop/ForLoopGenerator;", "codegen", "Lorg/jetbrains/kotlin/codegen/ExpressionCodegen;", "forExpression", "Lorg/jetbrains/kotlin/psi/KtForExpression;", "createForLoopGenerator", "Lorg/jetbrains/kotlin/codegen/range/forLoop/ForInRangeInstanceLoopGenerator;", "createInExpressionGenerator", "Lorg/jetbrains/kotlin/codegen/range/inExpression/InExpressionGenerator;", "operatorReference", "Lorg/jetbrains/kotlin/psi/KtSimpleNameExpression;", "getRangeElementType", "Lorg/jetbrains/kotlin/types/KotlinType;", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PrimitiveRangeRangeValue implements ReversableRangeValue {
    private final KtExpression rangeExpression;

    public PrimitiveRangeRangeValue(KtExpression rangeExpression) {
        Intrinsics.checkNotNullParameter(rangeExpression, "rangeExpression");
        this.rangeExpression = rangeExpression;
    }

    @Override // org.jetbrains.kotlin.codegen.range.RangeValue
    public ForInRangeInstanceLoopGenerator createForLoopGenerator(ExpressionCodegen codegen, KtForExpression forExpression) {
        Intrinsics.checkNotNullParameter(codegen, "codegen");
        Intrinsics.checkNotNullParameter(forExpression, "forExpression");
        return new ForInRangeInstanceLoopGenerator(codegen, forExpression, this.rangeExpression, ComparisonGeneratorKt.getComparisonGeneratorForKotlinType(getRangeElementType(codegen, forExpression)), false);
    }

    @Override // org.jetbrains.kotlin.codegen.range.RangeValue
    public InExpressionGenerator createInExpressionGenerator(ExpressionCodegen codegen, KtSimpleNameExpression operatorReference) {
        Intrinsics.checkNotNullParameter(codegen, "codegen");
        Intrinsics.checkNotNullParameter(operatorReference, "operatorReference");
        return new CallBasedInExpressionGenerator(codegen, operatorReference);
    }

    @Override // org.jetbrains.kotlin.codegen.range.ReversableRangeValue
    public ForLoopGenerator createForInReversedLoopGenerator(ExpressionCodegen codegen, KtForExpression forExpression) {
        Intrinsics.checkNotNullParameter(codegen, "codegen");
        Intrinsics.checkNotNullParameter(forExpression, "forExpression");
        return new ForInRangeInstanceLoopGenerator(codegen, forExpression, this.rangeExpression, ComparisonGeneratorKt.getComparisonGeneratorForKotlinType(getRangeElementType(codegen, forExpression)), true);
    }

    private final KotlinType getRangeElementType(ExpressionCodegen codegen, KtForExpression forExpression) {
        KtExpression loopRange = forExpression.getLoopRange();
        if (loopRange == null) {
            throw new AssertionError("No loop range expression: " + forExpression.getText());
        }
        KotlinType type = codegen.getBindingContext().getType(loopRange);
        if (type == null) {
            throw new AssertionError("No type for loop range expression: " + loopRange.getText());
        }
        KotlinType rangeOrProgressionElementType$default = RangeUtilKt.getRangeOrProgressionElementType$default(type, null, 2, null);
        if (rangeOrProgressionElementType$default != null) {
            return rangeOrProgressionElementType$default;
        }
        throw new AssertionError("Unexpected range type: " + type);
    }
}
