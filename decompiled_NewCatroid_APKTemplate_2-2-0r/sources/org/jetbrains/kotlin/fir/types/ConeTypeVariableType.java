package org.jetbrains.kotlin.fir.types;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: ConeTypeVariableAndStubTypes.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0013\u0010\u0014\u001a\u00020\u00152\b\u0010\u0016\u001a\u0004\u0018\u00010\u0017H\u0096\u0002J\b\u0010\u0018\u001a\u00020\u0019H\u0016R\u0014\u0010\u0006\u001a\u00020\u0007X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u001c\u0010\u000f\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00110\u00108VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0013¨\u0006\u001a"}, d2 = {"Lorg/jetbrains/kotlin/fir/types/ConeTypeVariableType;", "Lorg/jetbrains/kotlin/fir/types/ConeLookupTagBasedType;", "nullability", "Lorg/jetbrains/kotlin/fir/types/ConeNullability;", "lookupTag", "Lorg/jetbrains/kotlin/fir/types/ConeTypeVariableTypeConstructor;", "attributes", "Lorg/jetbrains/kotlin/fir/types/ConeAttributes;", "(Lorg/jetbrains/kotlin/fir/types/ConeNullability;Lorg/jetbrains/kotlin/fir/types/ConeTypeVariableTypeConstructor;Lorg/jetbrains/kotlin/fir/types/ConeAttributes;)V", "getAttributes", "()Lorg/jetbrains/kotlin/fir/types/ConeAttributes;", "getLookupTag", "()Lorg/jetbrains/kotlin/fir/types/ConeTypeVariableTypeConstructor;", "getNullability", "()Lorg/jetbrains/kotlin/fir/types/ConeNullability;", "typeArguments", "", "Lorg/jetbrains/kotlin/fir/types/ConeTypeProjection;", "getTypeArguments", "()[Lorg/jetbrains/kotlin/fir/types/ConeTypeProjection;", Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "cones"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ConeTypeVariableType extends ConeLookupTagBasedType {
    private final ConeAttributes attributes;
    private final ConeTypeVariableTypeConstructor lookupTag;
    private final ConeNullability nullability;

    @Override // org.jetbrains.kotlin.fir.types.ConeKotlinType
    public ConeNullability getNullability() {
        return this.nullability;
    }

    @Override // org.jetbrains.kotlin.fir.types.ConeLookupTagBasedType
    public ConeTypeVariableTypeConstructor getLookupTag() {
        return this.lookupTag;
    }

    public /* synthetic */ ConeTypeVariableType(ConeNullability coneNullability, ConeTypeVariableTypeConstructor coneTypeVariableTypeConstructor, ConeAttributes coneAttributes, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(coneNullability, coneTypeVariableTypeConstructor, (i & 4) != 0 ? ConeAttributes.INSTANCE.getEmpty() : coneAttributes);
    }

    @Override // org.jetbrains.kotlin.fir.types.ConeKotlinType
    public ConeAttributes getAttributes() {
        return this.attributes;
    }

    public ConeTypeVariableType(ConeNullability nullability, ConeTypeVariableTypeConstructor lookupTag, ConeAttributes attributes) {
        Intrinsics.checkNotNullParameter(nullability, "nullability");
        Intrinsics.checkNotNullParameter(lookupTag, "lookupTag");
        Intrinsics.checkNotNullParameter(attributes, "attributes");
        this.nullability = nullability;
        this.lookupTag = lookupTag;
        this.attributes = attributes;
    }

    @Override // org.jetbrains.kotlin.fir.types.ConeKotlinType, org.jetbrains.kotlin.fir.types.ConeKotlinTypeProjection
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ConeTypeVariableType)) {
            return false;
        }
        ConeTypeVariableType coneTypeVariableType = (ConeTypeVariableType) other;
        return getNullability() == coneTypeVariableType.getNullability() && Intrinsics.areEqual(getLookupTag(), coneTypeVariableType.getLookupTag());
    }

    @Override // org.jetbrains.kotlin.fir.types.ConeKotlinType, org.jetbrains.kotlin.fir.types.ConeKotlinTypeProjection
    public int hashCode() {
        return (getNullability().hashCode() * 31) + getLookupTag().hashCode();
    }

    @Override // org.jetbrains.kotlin.fir.types.ConeKotlinType
    public ConeTypeProjection[] getTypeArguments() {
        return new ConeTypeProjection[0];
    }
}
