package org.jetbrains.kotlin.codegen.range.forLoop;

import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.ExpressionCodegen;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtForExpression;

/* JADX INFO: compiled from: ForInProgressionExpressionLoopGenerator.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\b\u0010\t\u001a\u00020\nH\u0014R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/codegen/range/forLoop/ForInProgressionExpressionLoopGenerator;", "Lorg/jetbrains/kotlin/codegen/range/forLoop/AbstractForInProgressionLoopGenerator;", "codegen", "Lorg/jetbrains/kotlin/codegen/ExpressionCodegen;", "forExpression", "Lorg/jetbrains/kotlin/psi/KtForExpression;", "rangeExpression", "Lorg/jetbrains/kotlin/psi/KtExpression;", "(Lorg/jetbrains/kotlin/codegen/ExpressionCodegen;Lorg/jetbrains/kotlin/psi/KtForExpression;Lorg/jetbrains/kotlin/psi/KtExpression;)V", "storeProgressionParametersToLocalVars", "", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ForInProgressionExpressionLoopGenerator extends AbstractForInProgressionLoopGenerator {
    private final KtExpression rangeExpression;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ForInProgressionExpressionLoopGenerator(ExpressionCodegen codegen, KtForExpression forExpression, KtExpression rangeExpression) {
        super(codegen, forExpression);
        Intrinsics.checkNotNullParameter(codegen, "codegen");
        Intrinsics.checkNotNullParameter(forExpression, "forExpression");
        Intrinsics.checkNotNullParameter(rangeExpression, "rangeExpression");
        this.rangeExpression = rangeExpression;
    }

    @Override // org.jetbrains.kotlin.codegen.range.forLoop.AbstractForInProgressionLoopGenerator
    protected void storeProgressionParametersToLocalVars() {
        getCodegen().gen(this.rangeExpression, getAsmLoopRangeType(), getRangeKotlinType());
        getV().dup();
        getV().dup();
        String propertyGetterName = getPropertyGetterName(getRangeKotlinType(), "first");
        String propertyGetterName2 = getPropertyGetterName(getRangeKotlinType(), "last");
        String propertyGetterName3 = getPropertyGetterName(getRangeKotlinType(), "step");
        generateRangeOrProgressionProperty(getAsmLoopRangeType(), propertyGetterName, getAsmElementType(), getLoopParameterType(), getLoopParameterVar());
        generateRangeOrProgressionProperty(getAsmLoopRangeType(), propertyGetterName2, getAsmElementType(), getAsmElementType(), getEndVar());
        generateRangeOrProgressionProperty(getAsmLoopRangeType(), propertyGetterName3, getIncrementType(), getIncrementType(), getIncrementVar());
    }
}
