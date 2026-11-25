package org.jetbrains.kotlin.fir.types;

import java.util.Collection;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.types.model.IntersectionTypeConstructorMarker;

/* JADX INFO: compiled from: ConeTypes.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\u0018\u00002\u00020\u00012\u00020\u0002B\u001f\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0007J\u0013\u0010\u001b\u001a\u00020\u001c2\b\u0010\u001d\u001a\u0004\u0018\u00010\u001eH\u0096\u0002J\b\u0010\u000e\u001a\u00020\u000fH\u0016R\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0014\u0010\n\u001a\u00020\u000bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0014\u0010\u0012\u001a\u00020\u00138VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015R\u001c\u0010\u0016\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00180\u00178VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0019\u0010\u001a¨\u0006\u001f"}, d2 = {"Lorg/jetbrains/kotlin/fir/types/ConeIntersectionType;", "Lorg/jetbrains/kotlin/fir/types/ConeSimpleKotlinType;", "Lorg/jetbrains/kotlin/types/model/IntersectionTypeConstructorMarker;", "intersectedTypes", "", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "alternativeType", "(Ljava/util/Collection;Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;)V", "getAlternativeType", "()Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "attributes", "Lorg/jetbrains/kotlin/fir/types/ConeAttributes;", "getAttributes", "()Lorg/jetbrains/kotlin/fir/types/ConeAttributes;", "hashCode", "", "getIntersectedTypes", "()Ljava/util/Collection;", "nullability", "Lorg/jetbrains/kotlin/fir/types/ConeNullability;", "getNullability", "()Lorg/jetbrains/kotlin/fir/types/ConeNullability;", "typeArguments", "", "Lorg/jetbrains/kotlin/fir/types/ConeTypeProjection;", "getTypeArguments", "()[Lorg/jetbrains/kotlin/fir/types/ConeTypeProjection;", Namer.EQUALS_METHOD_NAME, "", "other", "", "cones"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ConeIntersectionType extends ConeSimpleKotlinType implements IntersectionTypeConstructorMarker {
    private final ConeKotlinType alternativeType;
    private final ConeAttributes attributes;
    private int hashCode;
    private final Collection<ConeKotlinType> intersectedTypes;

    public /* synthetic */ ConeIntersectionType(Collection collection, ConeKotlinType coneKotlinType, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(collection, (i & 2) != 0 ? null : coneKotlinType);
    }

    public final Collection<ConeKotlinType> getIntersectedTypes() {
        return this.intersectedTypes;
    }

    public final ConeKotlinType getAlternativeType() {
        return this.alternativeType;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public ConeIntersectionType(Collection<? extends ConeKotlinType> intersectedTypes, ConeKotlinType coneKotlinType) {
        super(null);
        Intrinsics.checkNotNullParameter(intersectedTypes, "intersectedTypes");
        this.intersectedTypes = intersectedTypes;
        this.alternativeType = coneKotlinType;
        Iterator it = intersectedTypes.iterator();
        ConeAttributes attributes = ((ConeKotlinType) it.next()).getAttributes();
        while (it.hasNext()) {
            attributes = attributes.intersect(((ConeKotlinType) it.next()).getAttributes());
        }
        this.attributes = attributes;
    }

    @Override // org.jetbrains.kotlin.fir.types.ConeKotlinType
    public ConeNullability getNullability() {
        return ConeNullability.NOT_NULL;
    }

    @Override // org.jetbrains.kotlin.fir.types.ConeKotlinType
    public ConeAttributes getAttributes() {
        return this.attributes;
    }

    @Override // org.jetbrains.kotlin.fir.types.ConeKotlinType, org.jetbrains.kotlin.fir.types.ConeKotlinTypeProjection
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!Intrinsics.areEqual(getClass(), other != null ? other.getClass() : null)) {
            return false;
        }
        Intrinsics.checkNotNull(other, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.types.ConeIntersectionType");
        return Intrinsics.areEqual(this.intersectedTypes, ((ConeIntersectionType) other).intersectedTypes);
    }

    @Override // org.jetbrains.kotlin.fir.types.ConeKotlinType, org.jetbrains.kotlin.fir.types.ConeKotlinTypeProjection
    public int hashCode() {
        int i = this.hashCode;
        if (i != 0) {
            return i;
        }
        int iHashCode = this.intersectedTypes.hashCode();
        this.hashCode = iHashCode;
        return iHashCode;
    }

    @Override // org.jetbrains.kotlin.fir.types.ConeKotlinType
    public ConeTypeProjection[] getTypeArguments() {
        return new ConeTypeProjection[0];
    }
}
