package org.jetbrains.kotlin.fir.types;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.types.model.FlexibleTypeMarker;

/* JADX INFO: compiled from: ConeTypes.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\b\u0016\u0018\u00002\u00020\u00012\u00020\u0002B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0004¢\u0006\u0002\u0010\u0006J\u0013\u0010\u0017\u001a\u00020\u00182\b\u0010\u0019\u001a\u0004\u0018\u00010\u001aH\u0086\u0002J\u0006\u0010\u001b\u001a\u00020\u001cR\u0011\u0010\u0007\u001a\u00020\b8F¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\r\u001a\u00020\u000e8F¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u0019\u0010\u0011\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00130\u00128F¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015R\u0011\u0010\u0005\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\f¨\u0006\u001d"}, d2 = {"Lorg/jetbrains/kotlin/fir/types/ConeFlexibleType;", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "Lorg/jetbrains/kotlin/types/model/FlexibleTypeMarker;", "lowerBound", "Lorg/jetbrains/kotlin/fir/types/ConeSimpleKotlinType;", "upperBound", "(Lorg/jetbrains/kotlin/fir/types/ConeSimpleKotlinType;Lorg/jetbrains/kotlin/fir/types/ConeSimpleKotlinType;)V", "attributes", "Lorg/jetbrains/kotlin/fir/types/ConeAttributes;", "getAttributes", "()Lorg/jetbrains/kotlin/fir/types/ConeAttributes;", "getLowerBound", "()Lorg/jetbrains/kotlin/fir/types/ConeSimpleKotlinType;", "nullability", "Lorg/jetbrains/kotlin/fir/types/ConeNullability;", "getNullability", "()Lorg/jetbrains/kotlin/fir/types/ConeNullability;", "typeArguments", "", "Lorg/jetbrains/kotlin/fir/types/ConeTypeProjection;", "getTypeArguments", "()[Lorg/jetbrains/kotlin/fir/types/ConeTypeProjection;", "getUpperBound", Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "cones"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class ConeFlexibleType extends ConeKotlinType implements FlexibleTypeMarker {
    private final ConeSimpleKotlinType lowerBound;
    private final ConeSimpleKotlinType upperBound;

    public final ConeSimpleKotlinType getLowerBound() {
        return this.lowerBound;
    }

    public final ConeSimpleKotlinType getUpperBound() {
        return this.upperBound;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ConeFlexibleType(ConeSimpleKotlinType lowerBound, ConeSimpleKotlinType upperBound) {
        super(null);
        Intrinsics.checkNotNullParameter(lowerBound, "lowerBound");
        Intrinsics.checkNotNullParameter(upperBound, "upperBound");
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
    }

    @Override // org.jetbrains.kotlin.fir.types.ConeKotlinType
    public final ConeTypeProjection[] getTypeArguments() {
        return this.lowerBound.getTypeArguments();
    }

    @Override // org.jetbrains.kotlin.fir.types.ConeKotlinType
    public final ConeNullability getNullability() {
        ConeNullability nullability = this.lowerBound.getNullability();
        if (nullability != this.upperBound.getNullability()) {
            nullability = null;
        }
        return nullability == null ? ConeNullability.UNKNOWN : nullability;
    }

    @Override // org.jetbrains.kotlin.fir.types.ConeKotlinType
    public final ConeAttributes getAttributes() {
        return this.lowerBound.getAttributes();
    }

    @Override // org.jetbrains.kotlin.fir.types.ConeKotlinType, org.jetbrains.kotlin.fir.types.ConeKotlinTypeProjection
    public final boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ConeFlexibleType)) {
            return false;
        }
        ConeFlexibleType coneFlexibleType = (ConeFlexibleType) other;
        return Intrinsics.areEqual(this.lowerBound, coneFlexibleType.lowerBound) && Intrinsics.areEqual(this.upperBound, coneFlexibleType.upperBound);
    }

    @Override // org.jetbrains.kotlin.fir.types.ConeKotlinType, org.jetbrains.kotlin.fir.types.ConeKotlinTypeProjection
    public final int hashCode() {
        return (this.lowerBound.hashCode() * 31) + this.upperBound.hashCode();
    }
}
