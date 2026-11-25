package org.jetbrains.kotlin.codegen.range.forLoop;

import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.ExpressionCodegen;
import org.jetbrains.kotlin.codegen.range.comparison.ComparisonGenerator;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtForExpression;
import org.jetbrains.org.objectweb.asm.Type;

/* JADX INFO: compiled from: ForInRangeInstanceLoopGenerator.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u0001B-\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b¢\u0006\u0002\u0010\fJ\b\u0010\r\u001a\u00020\u000eH\u0014R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/codegen/range/forLoop/ForInRangeInstanceLoopGenerator;", "Lorg/jetbrains/kotlin/codegen/range/forLoop/AbstractForInRangeLoopGenerator;", "codegen", "Lorg/jetbrains/kotlin/codegen/ExpressionCodegen;", "forExpression", "Lorg/jetbrains/kotlin/psi/KtForExpression;", "rangeExpression", "Lorg/jetbrains/kotlin/psi/KtExpression;", "comparisonGenerator", "Lorg/jetbrains/kotlin/codegen/range/comparison/ComparisonGenerator;", "reversed", "", "(Lorg/jetbrains/kotlin/codegen/ExpressionCodegen;Lorg/jetbrains/kotlin/psi/KtForExpression;Lorg/jetbrains/kotlin/psi/KtExpression;Lorg/jetbrains/kotlin/codegen/range/comparison/ComparisonGenerator;Z)V", "storeRangeStartAndEnd", "", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ForInRangeInstanceLoopGenerator extends AbstractForInRangeLoopGenerator {
    private final KtExpression rangeExpression;
    private final boolean reversed;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ForInRangeInstanceLoopGenerator(ExpressionCodegen codegen, KtForExpression forExpression, KtExpression rangeExpression, ComparisonGenerator comparisonGenerator, boolean z) {
        super(codegen, forExpression, z ? -1 : 1, comparisonGenerator);
        Intrinsics.checkNotNullParameter(codegen, "codegen");
        Intrinsics.checkNotNullParameter(forExpression, "forExpression");
        Intrinsics.checkNotNullParameter(rangeExpression, "rangeExpression");
        Intrinsics.checkNotNullParameter(comparisonGenerator, "comparisonGenerator");
        this.rangeExpression = rangeExpression;
        this.reversed = z;
    }

    @Override // org.jetbrains.kotlin.codegen.range.forLoop.AbstractForInRangeLoopGenerator
    protected void storeRangeStartAndEnd() {
        Type typeAsmType = getCodegen().asmType(getRangeKotlinType());
        Intrinsics.checkNotNullExpressionValue(typeAsmType, "asmType(...)");
        getCodegen().gen(this.rangeExpression, typeAsmType, getRangeKotlinType());
        getV().dup();
        String propertyGetterName = getPropertyGetterName(getRangeKotlinType(), "first");
        String propertyGetterName2 = getPropertyGetterName(getRangeKotlinType(), "last");
        if (this.reversed) {
            generateRangeOrProgressionProperty(typeAsmType, propertyGetterName2, getAsmElementType(), getLoopParameterType(), getLoopParameterVar());
            generateRangeOrProgressionProperty(typeAsmType, propertyGetterName, getAsmElementType(), getAsmElementType(), getEndVar());
        } else {
            generateRangeOrProgressionProperty(typeAsmType, propertyGetterName, getAsmElementType(), getLoopParameterType(), getLoopParameterVar());
            generateRangeOrProgressionProperty(typeAsmType, propertyGetterName2, getAsmElementType(), getAsmElementType(), getEndVar());
        }
    }
}
