package org.jetbrains.kotlin.codegen.inline;

import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt;

/* JADX INFO: compiled from: SMAP.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0002\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000\"\u0015\u0010\u0003\u001a\u00020\u0004*\u00020\u00058F¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007¨\u0006\b"}, d2 = {"KOTLIN_DEBUG_STRATA_NAME", "", "KOTLIN_STRATA_NAME", "toRange", "Lkotlin/ranges/IntRange;", "Lorg/jetbrains/kotlin/codegen/inline/RangeMapping;", "getToRange", "(Lorg/jetbrains/kotlin/codegen/inline/RangeMapping;)Lkotlin/ranges/IntRange;", Marker.BACKEND}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class SMAPKt {
    public static final String KOTLIN_DEBUG_STRATA_NAME = "KotlinDebug";
    public static final String KOTLIN_STRATA_NAME = "Kotlin";

    public static final IntRange getToRange(RangeMapping rangeMapping) {
        Intrinsics.checkNotNullParameter(rangeMapping, "<this>");
        return RangesKt.until(rangeMapping.getDest(), rangeMapping.getDest() + rangeMapping.getRange());
    }
}
