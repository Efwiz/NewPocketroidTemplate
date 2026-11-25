package org.jetbrains.kotlin.fir.types;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.types.model.DefinitelyNotNullTypeMarker;

/* JADX INFO: compiled from: ConeTypes.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u0000 \u001c2\u00020\u00012\u00020\u0002:\u0001\u001cB\r\u0012\u0006\u0010\u0003\u001a\u00020\u0001¢\u0006\u0002\u0010\u0004J\t\u0010\u0014\u001a\u00020\u0001HÆ\u0003J\u0013\u0010\u0015\u001a\u00020\u00002\b\b\u0002\u0010\u0003\u001a\u00020\u0001HÆ\u0001J\u0013\u0010\u0016\u001a\u00020\u00172\b\u0010\u0018\u001a\u0004\u0018\u00010\u0019HÖ\u0003J\t\u0010\u001a\u001a\u00020\u001bHÖ\u0001R\u0014\u0010\u0005\u001a\u00020\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\t\u001a\u00020\n8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0003\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u001c\u0010\u000f\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00110\u00108VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0013¨\u0006\u001d"}, d2 = {"Lorg/jetbrains/kotlin/fir/types/ConeDefinitelyNotNullType;", "Lorg/jetbrains/kotlin/fir/types/ConeSimpleKotlinType;", "Lorg/jetbrains/kotlin/types/model/DefinitelyNotNullTypeMarker;", "original", "(Lorg/jetbrains/kotlin/fir/types/ConeSimpleKotlinType;)V", "attributes", "Lorg/jetbrains/kotlin/fir/types/ConeAttributes;", "getAttributes", "()Lorg/jetbrains/kotlin/fir/types/ConeAttributes;", "nullability", "Lorg/jetbrains/kotlin/fir/types/ConeNullability;", "getNullability", "()Lorg/jetbrains/kotlin/fir/types/ConeNullability;", "getOriginal", "()Lorg/jetbrains/kotlin/fir/types/ConeSimpleKotlinType;", "typeArguments", "", "Lorg/jetbrains/kotlin/fir/types/ConeTypeProjection;", "getTypeArguments", "()[Lorg/jetbrains/kotlin/fir/types/ConeTypeProjection;", "component1", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "Companion", "cones"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class ConeDefinitelyNotNullType extends ConeSimpleKotlinType implements DefinitelyNotNullTypeMarker {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final ConeSimpleKotlinType original;

    public static /* synthetic */ ConeDefinitelyNotNullType copy$default(ConeDefinitelyNotNullType coneDefinitelyNotNullType, ConeSimpleKotlinType coneSimpleKotlinType, int i, Object obj) {
        if ((i & 1) != 0) {
            coneSimpleKotlinType = coneDefinitelyNotNullType.original;
        }
        return coneDefinitelyNotNullType.copy(coneSimpleKotlinType);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final ConeSimpleKotlinType getOriginal() {
        return this.original;
    }

    public final ConeDefinitelyNotNullType copy(ConeSimpleKotlinType original) {
        Intrinsics.checkNotNullParameter(original, "original");
        return new ConeDefinitelyNotNullType(original);
    }

    @Override // org.jetbrains.kotlin.fir.types.ConeKotlinType, org.jetbrains.kotlin.fir.types.ConeKotlinTypeProjection
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        return (other instanceof ConeDefinitelyNotNullType) && Intrinsics.areEqual(this.original, ((ConeDefinitelyNotNullType) other).original);
    }

    @Override // org.jetbrains.kotlin.fir.types.ConeKotlinType, org.jetbrains.kotlin.fir.types.ConeKotlinTypeProjection
    public int hashCode() {
        return this.original.hashCode();
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ConeDefinitelyNotNullType(ConeSimpleKotlinType original) {
        super(null);
        Intrinsics.checkNotNullParameter(original, "original");
        this.original = original;
    }

    public final ConeSimpleKotlinType getOriginal() {
        return this.original;
    }

    @Override // org.jetbrains.kotlin.fir.types.ConeKotlinType
    public ConeTypeProjection[] getTypeArguments() {
        return this.original.getTypeArguments();
    }

    @Override // org.jetbrains.kotlin.fir.types.ConeKotlinType
    public ConeNullability getNullability() {
        return ConeNullability.NOT_NULL;
    }

    @Override // org.jetbrains.kotlin.fir.types.ConeKotlinType
    public ConeAttributes getAttributes() {
        return this.original.getAttributes();
    }

    /* JADX INFO: compiled from: ConeTypes.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/fir/types/ConeDefinitelyNotNullType$Companion;", "", "()V", "cones"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }
}
