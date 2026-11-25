package org.jetbrains.kotlin.fir.types.impl;

import java.util.Arrays;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.symbols.ConeClassLikeLookupTag;
import org.jetbrains.kotlin.fir.types.ConeAttributes;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.ConeNullability;
import org.jetbrains.kotlin.fir.types.ConeTypeProjection;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.util.WeakPair;

/* JADX INFO: compiled from: Impl.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\u0018\u00002\u00020\u0001B/\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u000e\u0010\u0004\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00060\u0005\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\b\b\u0002\u0010\t\u001a\u00020\n¢\u0006\u0002\u0010\u000bJ\u0013\u0010\u001d\u001a\u00020\b2\b\u0010\u001e\u001a\u0004\u0018\u00010\u001fH\u0096\u0002J\b\u0010 \u001a\u00020!H\u0016R\u0014\u0010\t\u001a\u00020\nX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR&\u0010\u000e\u001a\u000e\u0012\u0002\b\u0003\u0012\u0004\u0012\u00020\u0001\u0018\u00010\u000fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0014\u0010\u0016\u001a\u00020\u0017X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u001e\u0010\u0004\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00060\u0005X\u0096\u0004¢\u0006\n\n\u0002\u0010\u001c\u001a\u0004\b\u001a\u0010\u001b¨\u0006\""}, d2 = {"Lorg/jetbrains/kotlin/fir/types/impl/ConeClassLikeTypeImpl;", "Lorg/jetbrains/kotlin/fir/types/ConeClassLikeType;", "lookupTag", "Lorg/jetbrains/kotlin/fir/symbols/ConeClassLikeLookupTag;", "typeArguments", "", "Lorg/jetbrains/kotlin/fir/types/ConeTypeProjection;", "isNullable", "", "attributes", "Lorg/jetbrains/kotlin/fir/types/ConeAttributes;", "(Lorg/jetbrains/kotlin/fir/symbols/ConeClassLikeLookupTag;[Lorg/jetbrains/kotlin/fir/types/ConeTypeProjection;ZLorg/jetbrains/kotlin/fir/types/ConeAttributes;)V", "getAttributes", "()Lorg/jetbrains/kotlin/fir/types/ConeAttributes;", "cachedExpandedType", "Lorg/jetbrains/kotlin/util/WeakPair;", "getCachedExpandedType", "()Lorg/jetbrains/kotlin/util/WeakPair;", "setCachedExpandedType", "(Lorg/jetbrains/kotlin/util/WeakPair;)V", "getLookupTag", "()Lorg/jetbrains/kotlin/fir/symbols/ConeClassLikeLookupTag;", "nullability", "Lorg/jetbrains/kotlin/fir/types/ConeNullability;", "getNullability", "()Lorg/jetbrains/kotlin/fir/types/ConeNullability;", "getTypeArguments", "()[Lorg/jetbrains/kotlin/fir/types/ConeTypeProjection;", "[Lorg/jetbrains/kotlin/fir/types/ConeTypeProjection;", Namer.EQUALS_METHOD_NAME, "other", "", "hashCode", "", "cones"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ConeClassLikeTypeImpl extends ConeClassLikeType {
    private final ConeAttributes attributes;
    private WeakPair<?, ConeClassLikeType> cachedExpandedType;
    private final ConeClassLikeLookupTag lookupTag;
    private final ConeNullability nullability;
    private final ConeTypeProjection[] typeArguments;

    @Override // org.jetbrains.kotlin.fir.types.ConeLookupTagBasedType
    public ConeClassLikeLookupTag getLookupTag() {
        return this.lookupTag;
    }

    @Override // org.jetbrains.kotlin.fir.types.ConeKotlinType
    public ConeTypeProjection[] getTypeArguments() {
        return this.typeArguments;
    }

    public /* synthetic */ ConeClassLikeTypeImpl(ConeClassLikeLookupTag coneClassLikeLookupTag, ConeTypeProjection[] coneTypeProjectionArr, boolean z, ConeAttributes coneAttributes, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(coneClassLikeLookupTag, coneTypeProjectionArr, z, (i & 8) != 0 ? ConeAttributes.INSTANCE.getEmpty() : coneAttributes);
    }

    @Override // org.jetbrains.kotlin.fir.types.ConeKotlinType
    public ConeAttributes getAttributes() {
        return this.attributes;
    }

    public ConeClassLikeTypeImpl(ConeClassLikeLookupTag lookupTag, ConeTypeProjection[] typeArguments, boolean z, ConeAttributes attributes) {
        Intrinsics.checkNotNullParameter(lookupTag, "lookupTag");
        Intrinsics.checkNotNullParameter(typeArguments, "typeArguments");
        Intrinsics.checkNotNullParameter(attributes, "attributes");
        this.lookupTag = lookupTag;
        this.typeArguments = typeArguments;
        this.attributes = attributes;
        this.nullability = ConeNullability.INSTANCE.create(z);
    }

    @Override // org.jetbrains.kotlin.fir.types.ConeKotlinType
    public ConeNullability getNullability() {
        return this.nullability;
    }

    public final WeakPair<?, ConeClassLikeType> getCachedExpandedType() {
        return this.cachedExpandedType;
    }

    public final void setCachedExpandedType(WeakPair<?, ConeClassLikeType> weakPair) {
        this.cachedExpandedType = weakPair;
    }

    @Override // org.jetbrains.kotlin.fir.types.ConeKotlinType, org.jetbrains.kotlin.fir.types.ConeKotlinTypeProjection
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!Intrinsics.areEqual(getClass(), other != null ? other.getClass() : null)) {
            return false;
        }
        Intrinsics.checkNotNull(other, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.types.impl.ConeClassLikeTypeImpl");
        ConeClassLikeTypeImpl coneClassLikeTypeImpl = (ConeClassLikeTypeImpl) other;
        return Intrinsics.areEqual(getLookupTag(), coneClassLikeTypeImpl.getLookupTag()) && Arrays.equals(getTypeArguments(), coneClassLikeTypeImpl.getTypeArguments()) && getNullability() == coneClassLikeTypeImpl.getNullability();
    }

    @Override // org.jetbrains.kotlin.fir.types.ConeKotlinType, org.jetbrains.kotlin.fir.types.ConeKotlinTypeProjection
    public int hashCode() {
        return (((getLookupTag().hashCode() * 31) + Arrays.hashCode(getTypeArguments())) * 31) + getNullability().hashCode();
    }
}
