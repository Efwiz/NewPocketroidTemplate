package org.jetbrains.kotlin.fir.types;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.types.model.CaptureStatus;
import org.jetbrains.kotlin.types.model.CapturedTypeMarker;
import org.jetbrains.kotlin.types.model.TypeParameterMarker;

/* JADX INFO: compiled from: ConeTypes.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\r\n\u0002\u0010\u0011\n\u0002\b\u000b\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\b\u0086\b\u0018\u00002\u00020\u00012\u00020\u0002B)\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n¢\u0006\u0002\u0010\u000bB=\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\b\b\u0002\u0010\f\u001a\u00020\r\u0012\u0006\u0010\u000e\u001a\u00020\u000f\u0012\b\b\u0002\u0010\u0010\u001a\u00020\u0011\u0012\b\b\u0002\u0010\u0012\u001a\u00020\u0013¢\u0006\u0002\u0010\u0014J\t\u0010$\u001a\u00020\u0004HÆ\u0003J\u000b\u0010%\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\t\u0010&\u001a\u00020\rHÆ\u0003J\t\u0010'\u001a\u00020\u000fHÆ\u0003J\t\u0010(\u001a\u00020\u0011HÆ\u0003J\t\u0010)\u001a\u00020\u0013HÆ\u0003JG\u0010*\u001a\u00020\u00002\b\b\u0002\u0010\u0003\u001a\u00020\u00042\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00062\b\b\u0002\u0010\f\u001a\u00020\r2\b\b\u0002\u0010\u000e\u001a\u00020\u000f2\b\b\u0002\u0010\u0010\u001a\u00020\u00112\b\b\u0002\u0010\u0012\u001a\u00020\u0013HÆ\u0001J\u0013\u0010+\u001a\u00020\u00132\b\u0010,\u001a\u0004\u0018\u00010-H\u0096\u0002J\b\u0010.\u001a\u00020/H\u0016R\u0014\u0010\u0010\u001a\u00020\u0011X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0011\u0010\u000e\u001a\u00020\u000f¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u0011\u0010\u0012\u001a\u00020\u0013¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u001bR\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001dR\u0014\u0010\f\u001a\u00020\rX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001fR\u001c\u0010 \u001a\n\u0012\u0006\b\u0001\u0012\u00020\b0!8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\"\u0010#¨\u00060"}, d2 = {"Lorg/jetbrains/kotlin/fir/types/ConeCapturedType;", "Lorg/jetbrains/kotlin/fir/types/ConeSimpleKotlinType;", "Lorg/jetbrains/kotlin/types/model/CapturedTypeMarker;", "captureStatus", "Lorg/jetbrains/kotlin/types/model/CaptureStatus;", "lowerType", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "projection", "Lorg/jetbrains/kotlin/fir/types/ConeTypeProjection;", "typeParameterMarker", "Lorg/jetbrains/kotlin/types/model/TypeParameterMarker;", "(Lorg/jetbrains/kotlin/types/model/CaptureStatus;Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;Lorg/jetbrains/kotlin/fir/types/ConeTypeProjection;Lorg/jetbrains/kotlin/types/model/TypeParameterMarker;)V", "nullability", "Lorg/jetbrains/kotlin/fir/types/ConeNullability;", JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME, "Lorg/jetbrains/kotlin/fir/types/ConeCapturedTypeConstructor;", "attributes", "Lorg/jetbrains/kotlin/fir/types/ConeAttributes;", "isProjectionNotNull", "", "(Lorg/jetbrains/kotlin/types/model/CaptureStatus;Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;Lorg/jetbrains/kotlin/fir/types/ConeNullability;Lorg/jetbrains/kotlin/fir/types/ConeCapturedTypeConstructor;Lorg/jetbrains/kotlin/fir/types/ConeAttributes;Z)V", "getAttributes", "()Lorg/jetbrains/kotlin/fir/types/ConeAttributes;", "getCaptureStatus", "()Lorg/jetbrains/kotlin/types/model/CaptureStatus;", "getConstructor", "()Lorg/jetbrains/kotlin/fir/types/ConeCapturedTypeConstructor;", "()Z", "getLowerType", "()Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "getNullability", "()Lorg/jetbrains/kotlin/fir/types/ConeNullability;", "typeArguments", "", "getTypeArguments", "()[Lorg/jetbrains/kotlin/fir/types/ConeTypeProjection;", "component1", "component2", "component3", "component4", "component5", "component6", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "other", "", "hashCode", "", "cones"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class ConeCapturedType extends ConeSimpleKotlinType implements CapturedTypeMarker {
    private final ConeAttributes attributes;
    private final CaptureStatus captureStatus;
    private final ConeCapturedTypeConstructor constructor;
    private final boolean isProjectionNotNull;
    private final ConeKotlinType lowerType;
    private final ConeNullability nullability;

    public static /* synthetic */ ConeCapturedType copy$default(ConeCapturedType coneCapturedType, CaptureStatus captureStatus, ConeKotlinType coneKotlinType, ConeNullability coneNullability, ConeCapturedTypeConstructor coneCapturedTypeConstructor, ConeAttributes coneAttributes, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            captureStatus = coneCapturedType.captureStatus;
        }
        if ((i & 2) != 0) {
            coneKotlinType = coneCapturedType.lowerType;
        }
        ConeKotlinType coneKotlinType2 = coneKotlinType;
        if ((i & 4) != 0) {
            coneNullability = coneCapturedType.nullability;
        }
        ConeNullability coneNullability2 = coneNullability;
        if ((i & 8) != 0) {
            coneCapturedTypeConstructor = coneCapturedType.constructor;
        }
        ConeCapturedTypeConstructor coneCapturedTypeConstructor2 = coneCapturedTypeConstructor;
        if ((i & 16) != 0) {
            coneAttributes = coneCapturedType.attributes;
        }
        ConeAttributes coneAttributes2 = coneAttributes;
        if ((i & 32) != 0) {
            z = coneCapturedType.isProjectionNotNull;
        }
        return coneCapturedType.copy(captureStatus, coneKotlinType2, coneNullability2, coneCapturedTypeConstructor2, coneAttributes2, z);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final CaptureStatus getCaptureStatus() {
        return this.captureStatus;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final ConeKotlinType getLowerType() {
        return this.lowerType;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final ConeNullability getNullability() {
        return this.nullability;
    }

    /* JADX INFO: renamed from: component4, reason: from getter */
    public final ConeCapturedTypeConstructor getConstructor() {
        return this.constructor;
    }

    /* JADX INFO: renamed from: component5, reason: from getter */
    public final ConeAttributes getAttributes() {
        return this.attributes;
    }

    /* JADX INFO: renamed from: component6, reason: from getter */
    public final boolean getIsProjectionNotNull() {
        return this.isProjectionNotNull;
    }

    public final ConeCapturedType copy(CaptureStatus captureStatus, ConeKotlinType lowerType, ConeNullability nullability, ConeCapturedTypeConstructor constructor, ConeAttributes attributes, boolean isProjectionNotNull) {
        Intrinsics.checkNotNullParameter(captureStatus, "captureStatus");
        Intrinsics.checkNotNullParameter(nullability, "nullability");
        Intrinsics.checkNotNullParameter(constructor, "constructor");
        Intrinsics.checkNotNullParameter(attributes, "attributes");
        return new ConeCapturedType(captureStatus, lowerType, nullability, constructor, attributes, isProjectionNotNull);
    }

    public final CaptureStatus getCaptureStatus() {
        return this.captureStatus;
    }

    public final ConeKotlinType getLowerType() {
        return this.lowerType;
    }

    public /* synthetic */ ConeCapturedType(CaptureStatus captureStatus, ConeKotlinType coneKotlinType, ConeNullability coneNullability, ConeCapturedTypeConstructor coneCapturedTypeConstructor, ConeAttributes coneAttributes, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(captureStatus, coneKotlinType, (i & 4) != 0 ? ConeNullability.NOT_NULL : coneNullability, coneCapturedTypeConstructor, (i & 16) != 0 ? ConeAttributes.INSTANCE.getEmpty() : coneAttributes, (i & 32) != 0 ? false : z);
    }

    @Override // org.jetbrains.kotlin.fir.types.ConeKotlinType
    public ConeNullability getNullability() {
        return this.nullability;
    }

    public final ConeCapturedTypeConstructor getConstructor() {
        return this.constructor;
    }

    @Override // org.jetbrains.kotlin.fir.types.ConeKotlinType
    public ConeAttributes getAttributes() {
        return this.attributes;
    }

    public final boolean isProjectionNotNull() {
        return this.isProjectionNotNull;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ConeCapturedType(CaptureStatus captureStatus, ConeKotlinType coneKotlinType, ConeNullability nullability, ConeCapturedTypeConstructor constructor, ConeAttributes attributes, boolean z) {
        super(null);
        Intrinsics.checkNotNullParameter(captureStatus, "captureStatus");
        Intrinsics.checkNotNullParameter(nullability, "nullability");
        Intrinsics.checkNotNullParameter(constructor, "constructor");
        Intrinsics.checkNotNullParameter(attributes, "attributes");
        this.captureStatus = captureStatus;
        this.lowerType = coneKotlinType;
        this.nullability = nullability;
        this.constructor = constructor;
        this.attributes = attributes;
        this.isProjectionNotNull = z;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public ConeCapturedType(CaptureStatus captureStatus, ConeKotlinType coneKotlinType, ConeTypeProjection projection, TypeParameterMarker typeParameterMarker) {
        Intrinsics.checkNotNullParameter(captureStatus, "captureStatus");
        Intrinsics.checkNotNullParameter(projection, "projection");
        Intrinsics.checkNotNullParameter(typeParameterMarker, "typeParameterMarker");
        ConeNullability coneNullability = null;
        this(captureStatus, coneKotlinType, coneNullability, new ConeCapturedTypeConstructor(projection, null, typeParameterMarker, 2, null), null, false, 52, null);
    }

    @Override // org.jetbrains.kotlin.fir.types.ConeKotlinType, org.jetbrains.kotlin.fir.types.ConeKotlinTypeProjection
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!Intrinsics.areEqual(getClass(), other != null ? other.getClass() : null)) {
            return false;
        }
        Intrinsics.checkNotNull(other, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.types.ConeCapturedType");
        ConeCapturedType coneCapturedType = (ConeCapturedType) other;
        return Intrinsics.areEqual(this.lowerType, coneCapturedType.lowerType) && Intrinsics.areEqual(this.constructor, coneCapturedType.constructor) && this.captureStatus == coneCapturedType.captureStatus && getNullability() == coneCapturedType.getNullability();
    }

    @Override // org.jetbrains.kotlin.fir.types.ConeKotlinType, org.jetbrains.kotlin.fir.types.ConeKotlinTypeProjection
    public int hashCode() {
        ConeKotlinType coneKotlinType = this.lowerType;
        return ((((((217 + (coneKotlinType != null ? coneKotlinType.hashCode() : 0)) * 31) + this.constructor.hashCode()) * 31) + this.captureStatus.hashCode()) * 31) + getNullability().hashCode();
    }

    @Override // org.jetbrains.kotlin.fir.types.ConeKotlinType
    public ConeTypeProjection[] getTypeArguments() {
        return new ConeTypeProjection[0];
    }
}
