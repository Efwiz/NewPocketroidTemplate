package org.jetbrains.kotlin.codegen.optimization.nullCheck;

import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.optimization.boxing.ProgressionIteratorBasicValue;
import org.jetbrains.org.objectweb.asm.tree.analysis.BasicValue;

/* JADX INFO: compiled from: nullabilityValues.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a\n\u0010\u0000\u001a\u00020\u0001*\u00020\u0002¨\u0006\u0003"}, d2 = {"getNullability", "Lorg/jetbrains/kotlin/codegen/optimization/nullCheck/Nullability;", "Lorg/jetbrains/org/objectweb/asm/tree/analysis/BasicValue;", Marker.BACKEND}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class NullabilityValuesKt {
    public static final Nullability getNullability(BasicValue basicValue) {
        Intrinsics.checkNotNullParameter(basicValue, "<this>");
        if (basicValue instanceof NullBasicValue) {
            return Nullability.NULL;
        }
        if (!(basicValue instanceof NotNullBasicValue) && !(basicValue instanceof ProgressionIteratorBasicValue)) {
            return Nullability.NULLABLE;
        }
        return Nullability.NOT_NULL;
    }
}
