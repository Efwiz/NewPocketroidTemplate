package org.jetbrains.kotlin.codegen.range.comparison;

import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.org.objectweb.asm.Type;

/* JADX INFO: compiled from: FloatingPointComparisonGenerator.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\"\u0011\u0010\u0000\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u0003\"\u0011\u0010\u0004\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0003¨\u0006\u0006"}, d2 = {"DoubleComparisonGenerator", "Lorg/jetbrains/kotlin/codegen/range/comparison/FloatingPointComparisonGenerator;", "getDoubleComparisonGenerator", "()Lorg/jetbrains/kotlin/codegen/range/comparison/FloatingPointComparisonGenerator;", "FloatComparisonGenerator", "getFloatComparisonGenerator", Marker.BACKEND}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FloatingPointComparisonGeneratorKt {
    private static final FloatingPointComparisonGenerator DoubleComparisonGenerator;
    private static final FloatingPointComparisonGenerator FloatComparisonGenerator;

    static {
        Type FLOAT_TYPE = Type.FLOAT_TYPE;
        Intrinsics.checkNotNullExpressionValue(FLOAT_TYPE, "FLOAT_TYPE");
        FloatComparisonGenerator = new FloatingPointComparisonGenerator(FLOAT_TYPE);
        Type DOUBLE_TYPE = Type.DOUBLE_TYPE;
        Intrinsics.checkNotNullExpressionValue(DOUBLE_TYPE, "DOUBLE_TYPE");
        DoubleComparisonGenerator = new FloatingPointComparisonGenerator(DOUBLE_TYPE);
    }

    public static final FloatingPointComparisonGenerator getFloatComparisonGenerator() {
        return FloatComparisonGenerator;
    }

    public static final FloatingPointComparisonGenerator getDoubleComparisonGenerator() {
        return DoubleComparisonGenerator;
    }
}
