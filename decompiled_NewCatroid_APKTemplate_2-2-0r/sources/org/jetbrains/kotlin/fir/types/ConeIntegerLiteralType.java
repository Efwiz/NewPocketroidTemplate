package org.jetbrains.kotlin.fir.types;

import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.types.model.TypeConstructorMarker;

/* JADX INFO: compiled from: ConeIntegerLiteralType.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u0000 &2\u00020\u00012\u00020\u0002:\u0001&B\u0017\b\u0004\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u0013\u0010\u001e\u001a\u00020\u00042\b\u0010\u001f\u001a\u0004\u0018\u00010 H\u0086\u0002J\u0014\u0010!\u001a\u00020\u00112\n\b\u0002\u0010\"\u001a\u0004\u0018\u00010#H&J\u0006\u0010$\u001a\u00020%R\u0011\u0010\b\u001a\u00020\t8F¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0003\u0010\fR\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0018\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00110\u0010X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0013R\u0018\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00110\u0015X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0017R\u001b\u0010\u0018\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u001a0\u0019¢\u0006\n\n\u0002\u0010\u001d\u001a\u0004\b\u001b\u0010\u001c\u0082\u0001\u0002'(¨\u0006)"}, d2 = {"Lorg/jetbrains/kotlin/fir/types/ConeIntegerLiteralType;", "Lorg/jetbrains/kotlin/fir/types/ConeSimpleKotlinType;", "Lorg/jetbrains/kotlin/types/model/TypeConstructorMarker;", "isUnsigned", "", "nullability", "Lorg/jetbrains/kotlin/fir/types/ConeNullability;", "(ZLorg/jetbrains/kotlin/fir/types/ConeNullability;)V", "attributes", "Lorg/jetbrains/kotlin/fir/types/ConeAttributes;", "getAttributes", "()Lorg/jetbrains/kotlin/fir/types/ConeAttributes;", "()Z", "getNullability", "()Lorg/jetbrains/kotlin/fir/types/ConeNullability;", "possibleTypes", "", "Lorg/jetbrains/kotlin/fir/types/ConeClassLikeType;", "getPossibleTypes", "()Ljava/util/Collection;", "supertypes", "", "getSupertypes", "()Ljava/util/List;", "typeArguments", "", "Lorg/jetbrains/kotlin/fir/types/ConeTypeProjection;", "getTypeArguments", "()[Lorg/jetbrains/kotlin/fir/types/ConeTypeProjection;", "[Lorg/jetbrains/kotlin/fir/types/ConeTypeProjection;", Namer.EQUALS_METHOD_NAME, "other", "", "getApproximatedType", "expectedType", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "hashCode", "", "Companion", "Lorg/jetbrains/kotlin/fir/types/ConeIntegerConstantOperatorType;", "Lorg/jetbrains/kotlin/fir/types/ConeIntegerLiteralConstantType;", "cones"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class ConeIntegerLiteralType extends ConeSimpleKotlinType implements TypeConstructorMarker {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final boolean isUnsigned;
    private final ConeNullability nullability;
    private final ConeTypeProjection[] typeArguments;

    public /* synthetic */ ConeIntegerLiteralType(boolean z, ConeNullability coneNullability, DefaultConstructorMarker defaultConstructorMarker) {
        this(z, coneNullability);
    }

    public abstract ConeClassLikeType getApproximatedType(ConeKotlinType expectedType);

    public abstract Collection<ConeClassLikeType> getPossibleTypes();

    public abstract List<ConeClassLikeType> getSupertypes();

    /* JADX INFO: renamed from: isUnsigned, reason: from getter */
    public final boolean getIsUnsigned() {
        return this.isUnsigned;
    }

    @Override // org.jetbrains.kotlin.fir.types.ConeKotlinType
    public final ConeNullability getNullability() {
        return this.nullability;
    }

    private ConeIntegerLiteralType(boolean z, ConeNullability coneNullability) {
        super(null);
        this.isUnsigned = z;
        this.nullability = coneNullability;
        this.typeArguments = new ConeTypeProjection[0];
    }

    @Override // org.jetbrains.kotlin.fir.types.ConeKotlinType
    public final ConeTypeProjection[] getTypeArguments() {
        return this.typeArguments;
    }

    @Override // org.jetbrains.kotlin.fir.types.ConeKotlinType
    public final ConeAttributes getAttributes() {
        return ConeAttributes.INSTANCE.getEmpty();
    }

    public static /* synthetic */ ConeClassLikeType getApproximatedType$default(ConeIntegerLiteralType coneIntegerLiteralType, ConeKotlinType coneKotlinType, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: getApproximatedType");
        }
        if ((i & 1) != 0) {
            coneKotlinType = null;
        }
        return coneIntegerLiteralType.getApproximatedType(coneKotlinType);
    }

    @Override // org.jetbrains.kotlin.fir.types.ConeKotlinType, org.jetbrains.kotlin.fir.types.ConeKotlinTypeProjection
    public final boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!Intrinsics.areEqual(getClass(), other != null ? other.getClass() : null)) {
            return false;
        }
        Intrinsics.checkNotNull(other, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.types.ConeIntegerLiteralType");
        ConeIntegerLiteralType coneIntegerLiteralType = (ConeIntegerLiteralType) other;
        return this.isUnsigned == coneIntegerLiteralType.isUnsigned && Intrinsics.areEqual(getPossibleTypes(), coneIntegerLiteralType.getPossibleTypes()) && this.nullability == coneIntegerLiteralType.nullability;
    }

    @Override // org.jetbrains.kotlin.fir.types.ConeKotlinType, org.jetbrains.kotlin.fir.types.ConeKotlinTypeProjection
    public final int hashCode() {
        return (getPossibleTypes().hashCode() * 31) + this.nullability.hashCode();
    }

    /* JADX INFO: compiled from: ConeIntegerLiteralType.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/fir/types/ConeIntegerLiteralType$Companion;", "", "()V", "cones"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }
}
