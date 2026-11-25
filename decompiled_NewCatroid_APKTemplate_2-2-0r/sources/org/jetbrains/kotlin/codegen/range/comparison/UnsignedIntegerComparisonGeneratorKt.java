package org.jetbrains.kotlin.codegen.range.comparison;

import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.org.objectweb.asm.Type;

/* JADX INFO: compiled from: UnsignedIntegerComparisonGenerator.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\"\u0011\u0010\u0000\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u0003\"\u0011\u0010\u0004\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0003¨\u0006\u0006"}, d2 = {"UIntComparisonGenerator", "Lorg/jetbrains/kotlin/codegen/range/comparison/UnsignedIntegerComparisonGenerator;", "getUIntComparisonGenerator", "()Lorg/jetbrains/kotlin/codegen/range/comparison/UnsignedIntegerComparisonGenerator;", "ULongComparisonGenerator", "getULongComparisonGenerator", Marker.BACKEND}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class UnsignedIntegerComparisonGeneratorKt {
    private static final UnsignedIntegerComparisonGenerator UIntComparisonGenerator;
    private static final UnsignedIntegerComparisonGenerator ULongComparisonGenerator;

    static {
        Type INT_TYPE = Type.INT_TYPE;
        Intrinsics.checkNotNullExpressionValue(INT_TYPE, "INT_TYPE");
        UIntComparisonGenerator = new UnsignedIntegerComparisonGenerator(INT_TYPE, "uintCompare");
        Type LONG_TYPE = Type.LONG_TYPE;
        Intrinsics.checkNotNullExpressionValue(LONG_TYPE, "LONG_TYPE");
        ULongComparisonGenerator = new UnsignedIntegerComparisonGenerator(LONG_TYPE, "ulongCompare");
    }

    public static final UnsignedIntegerComparisonGenerator getUIntComparisonGenerator() {
        return UIntComparisonGenerator;
    }

    public static final UnsignedIntegerComparisonGenerator getULongComparisonGenerator() {
        return ULongComparisonGenerator;
    }
}
