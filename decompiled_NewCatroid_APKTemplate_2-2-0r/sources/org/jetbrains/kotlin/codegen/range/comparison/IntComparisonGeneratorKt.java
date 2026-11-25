package org.jetbrains.kotlin.codegen.range.comparison;

import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.org.objectweb.asm.Type;

/* JADX INFO: compiled from: IntComparisonGenerator.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\"\u0011\u0010\u0000\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u0003\"\u0011\u0010\u0004\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0003¨\u0006\u0006"}, d2 = {"CharComparisonGenerator", "Lorg/jetbrains/kotlin/codegen/range/comparison/IntegerComparisonGenerator;", "getCharComparisonGenerator", "()Lorg/jetbrains/kotlin/codegen/range/comparison/IntegerComparisonGenerator;", "IntComparisonGenerator", "getIntComparisonGenerator", Marker.BACKEND}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class IntComparisonGeneratorKt {
    private static final IntegerComparisonGenerator CharComparisonGenerator;
    private static final IntegerComparisonGenerator IntComparisonGenerator;

    static {
        Type INT_TYPE = Type.INT_TYPE;
        Intrinsics.checkNotNullExpressionValue(INT_TYPE, "INT_TYPE");
        IntComparisonGenerator = new IntegerComparisonGenerator(INT_TYPE);
        Type CHAR_TYPE = Type.CHAR_TYPE;
        Intrinsics.checkNotNullExpressionValue(CHAR_TYPE, "CHAR_TYPE");
        CharComparisonGenerator = new IntegerComparisonGenerator(CHAR_TYPE);
    }

    public static final IntegerComparisonGenerator getIntComparisonGenerator() {
        return IntComparisonGenerator;
    }

    public static final IntegerComparisonGenerator getCharComparisonGenerator() {
        return CharComparisonGenerator;
    }
}
