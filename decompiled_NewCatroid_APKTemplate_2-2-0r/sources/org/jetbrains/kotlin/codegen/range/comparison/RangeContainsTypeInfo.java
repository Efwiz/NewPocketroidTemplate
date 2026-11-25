package org.jetbrains.kotlin.codegen.range.comparison;

import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: ComparisonGenerator.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/codegen/range/comparison/RangeContainsTypeInfo;", "", "rangeElementType", "Lorg/jetbrains/kotlin/types/KotlinType;", "valueParameterType", "(Lorg/jetbrains/kotlin/types/KotlinType;Lorg/jetbrains/kotlin/types/KotlinType;)V", "getRangeElementType", "()Lorg/jetbrains/kotlin/types/KotlinType;", "getValueParameterType", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class RangeContainsTypeInfo {
    private final KotlinType rangeElementType;
    private final KotlinType valueParameterType;

    public RangeContainsTypeInfo(KotlinType rangeElementType, KotlinType valueParameterType) {
        Intrinsics.checkNotNullParameter(rangeElementType, "rangeElementType");
        Intrinsics.checkNotNullParameter(valueParameterType, "valueParameterType");
        this.rangeElementType = rangeElementType;
        this.valueParameterType = valueParameterType;
    }

    public final KotlinType getRangeElementType() {
        return this.rangeElementType;
    }

    public final KotlinType getValueParameterType() {
        return this.valueParameterType;
    }
}
