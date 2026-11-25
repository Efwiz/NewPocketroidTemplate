package org.jetbrains.kotlin.fir.types;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.types.model.StubTypeMarker;

/* JADX INFO: compiled from: ConeTypeVariableAndStubTypes.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u00012\u00020\u0002B\u0017\b\u0004\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u0013\u0010\u0015\u001a\u00020\u00162\b\u0010\u0017\u001a\u0004\u0018\u00010\u0018H\u0096\u0002J\b\u0010\u0019\u001a\u00020\u001aH\u0016R\u0014\u0010\b\u001a\u00020\t8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0014\u0010\u0005\u001a\u00020\u0006X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u001c\u0010\u0010\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00120\u00118VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014\u0082\u0001\u0002\u001b\u001c¨\u0006\u001d"}, d2 = {"Lorg/jetbrains/kotlin/fir/types/ConeStubType;", "Lorg/jetbrains/kotlin/types/model/StubTypeMarker;", "Lorg/jetbrains/kotlin/fir/types/ConeSimpleKotlinType;", JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME, "Lorg/jetbrains/kotlin/fir/types/ConeStubTypeConstructor;", "nullability", "Lorg/jetbrains/kotlin/fir/types/ConeNullability;", "(Lorg/jetbrains/kotlin/fir/types/ConeStubTypeConstructor;Lorg/jetbrains/kotlin/fir/types/ConeNullability;)V", "attributes", "Lorg/jetbrains/kotlin/fir/types/ConeAttributes;", "getAttributes", "()Lorg/jetbrains/kotlin/fir/types/ConeAttributes;", "getConstructor", "()Lorg/jetbrains/kotlin/fir/types/ConeStubTypeConstructor;", "getNullability", "()Lorg/jetbrains/kotlin/fir/types/ConeNullability;", "typeArguments", "", "Lorg/jetbrains/kotlin/fir/types/ConeTypeProjection;", "getTypeArguments", "()[Lorg/jetbrains/kotlin/fir/types/ConeTypeProjection;", Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "Lorg/jetbrains/kotlin/fir/types/ConeStubTypeForChainInference;", "Lorg/jetbrains/kotlin/fir/types/ConeStubTypeForTypeVariableInSubtyping;", "cones"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class ConeStubType extends ConeSimpleKotlinType implements StubTypeMarker {
    private final ConeStubTypeConstructor constructor;
    private final ConeNullability nullability;

    public /* synthetic */ ConeStubType(ConeStubTypeConstructor coneStubTypeConstructor, ConeNullability coneNullability, DefaultConstructorMarker defaultConstructorMarker) {
        this(coneStubTypeConstructor, coneNullability);
    }

    public final ConeStubTypeConstructor getConstructor() {
        return this.constructor;
    }

    @Override // org.jetbrains.kotlin.fir.types.ConeKotlinType
    public ConeNullability getNullability() {
        return this.nullability;
    }

    private ConeStubType(ConeStubTypeConstructor coneStubTypeConstructor, ConeNullability coneNullability) {
        super(null);
        this.constructor = coneStubTypeConstructor;
        this.nullability = coneNullability;
    }

    @Override // org.jetbrains.kotlin.fir.types.ConeKotlinType
    public ConeAttributes getAttributes() {
        return ConeAttributes.INSTANCE.getEmpty();
    }

    @Override // org.jetbrains.kotlin.fir.types.ConeKotlinType, org.jetbrains.kotlin.fir.types.ConeKotlinTypeProjection
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!Intrinsics.areEqual(getClass(), other != null ? other.getClass() : null)) {
            return false;
        }
        Intrinsics.checkNotNull(other, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.types.ConeStubType");
        ConeStubType coneStubType = (ConeStubType) other;
        return Intrinsics.areEqual(this.constructor, coneStubType.constructor) && getNullability() == coneStubType.getNullability();
    }

    @Override // org.jetbrains.kotlin.fir.types.ConeKotlinType, org.jetbrains.kotlin.fir.types.ConeKotlinTypeProjection
    public int hashCode() {
        return (this.constructor.hashCode() * 31) + getNullability().hashCode();
    }

    @Override // org.jetbrains.kotlin.fir.types.ConeKotlinType
    public ConeTypeProjection[] getTypeArguments() {
        return new ConeTypeProjection[0];
    }
}
