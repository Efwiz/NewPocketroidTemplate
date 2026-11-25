package org.jetbrains.kotlin.fir.types.impl;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.fir.symbols.ConeTypeParameterLookupTag;
import org.jetbrains.kotlin.fir.types.ConeAttributes;
import org.jetbrains.kotlin.fir.types.ConeNullability;
import org.jetbrains.kotlin.fir.types.ConeTypeParameterType;
import org.jetbrains.kotlin.fir.types.ConeTypeProjection;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: ConeTypeParameterTypeImpl.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0013\u0010\u0016\u001a\u00020\u00052\b\u0010\u0017\u001a\u0004\u0018\u00010\u0018H\u0096\u0002J\b\u0010\u0019\u001a\u00020\u001aH\u0016R\u0014\u0010\u0006\u001a\u00020\u0007X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0014\u0010\r\u001a\u00020\u000eX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u001c\u0010\u0011\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00130\u00128VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015¨\u0006\u001b"}, d2 = {"Lorg/jetbrains/kotlin/fir/types/impl/ConeTypeParameterTypeImpl;", "Lorg/jetbrains/kotlin/fir/types/ConeTypeParameterType;", "lookupTag", "Lorg/jetbrains/kotlin/fir/symbols/ConeTypeParameterLookupTag;", "isNullable", "", "attributes", "Lorg/jetbrains/kotlin/fir/types/ConeAttributes;", "(Lorg/jetbrains/kotlin/fir/symbols/ConeTypeParameterLookupTag;ZLorg/jetbrains/kotlin/fir/types/ConeAttributes;)V", "getAttributes", "()Lorg/jetbrains/kotlin/fir/types/ConeAttributes;", "getLookupTag", "()Lorg/jetbrains/kotlin/fir/symbols/ConeTypeParameterLookupTag;", "nullability", "Lorg/jetbrains/kotlin/fir/types/ConeNullability;", "getNullability", "()Lorg/jetbrains/kotlin/fir/types/ConeNullability;", "typeArguments", "", "Lorg/jetbrains/kotlin/fir/types/ConeTypeProjection;", "getTypeArguments", "()[Lorg/jetbrains/kotlin/fir/types/ConeTypeProjection;", Namer.EQUALS_METHOD_NAME, "other", "", "hashCode", "", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ConeTypeParameterTypeImpl extends ConeTypeParameterType {
    private final ConeAttributes attributes;
    private final ConeTypeParameterLookupTag lookupTag;
    private final ConeNullability nullability;

    @Override // org.jetbrains.kotlin.fir.types.ConeLookupTagBasedType
    public ConeTypeParameterLookupTag getLookupTag() {
        return this.lookupTag;
    }

    public /* synthetic */ ConeTypeParameterTypeImpl(ConeTypeParameterLookupTag coneTypeParameterLookupTag, boolean z, ConeAttributes coneAttributes, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(coneTypeParameterLookupTag, z, (i & 4) != 0 ? ConeAttributes.INSTANCE.getEmpty() : coneAttributes);
    }

    @Override // org.jetbrains.kotlin.fir.types.ConeKotlinType
    public ConeAttributes getAttributes() {
        return this.attributes;
    }

    public ConeTypeParameterTypeImpl(ConeTypeParameterLookupTag lookupTag, boolean z, ConeAttributes attributes) {
        Intrinsics.checkNotNullParameter(lookupTag, "lookupTag");
        Intrinsics.checkNotNullParameter(attributes, "attributes");
        this.lookupTag = lookupTag;
        this.attributes = attributes;
        this.nullability = ConeNullability.INSTANCE.create(z);
    }

    @Override // org.jetbrains.kotlin.fir.types.ConeKotlinType
    public ConeTypeProjection[] getTypeArguments() {
        return ConeTypeProjection.INSTANCE.getEMPTY_ARRAY();
    }

    @Override // org.jetbrains.kotlin.fir.types.ConeKotlinType
    public ConeNullability getNullability() {
        return this.nullability;
    }

    @Override // org.jetbrains.kotlin.fir.types.ConeKotlinType, org.jetbrains.kotlin.fir.types.ConeKotlinTypeProjection
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!Intrinsics.areEqual(getClass(), other != null ? other.getClass() : null)) {
            return false;
        }
        Intrinsics.checkNotNull(other, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.types.impl.ConeTypeParameterTypeImpl");
        ConeTypeParameterTypeImpl coneTypeParameterTypeImpl = (ConeTypeParameterTypeImpl) other;
        return Intrinsics.areEqual(getLookupTag(), coneTypeParameterTypeImpl.getLookupTag()) && getNullability() == coneTypeParameterTypeImpl.getNullability();
    }

    @Override // org.jetbrains.kotlin.fir.types.ConeKotlinType, org.jetbrains.kotlin.fir.types.ConeKotlinTypeProjection
    public int hashCode() {
        return (getLookupTag().hashCode() * 31) + getNullability().hashCode();
    }
}
