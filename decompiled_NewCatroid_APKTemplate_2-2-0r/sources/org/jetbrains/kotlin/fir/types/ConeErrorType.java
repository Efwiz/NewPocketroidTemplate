package org.jetbrains.kotlin.fir.types;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.diagnostics.ConeDiagnostic;
import org.jetbrains.kotlin.fir.diagnostics.ConeDiagnosticWithNullability;
import org.jetbrains.kotlin.fir.symbols.ConeClassLikeLookupTag;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.name.ClassId;

/* JADX INFO: compiled from: ConeTypes.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\u0018\u00002\u00020\u0001B3\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\u0010\b\u0002\u0010\u0006\u001a\n\u0012\u0006\b\u0001\u0012\u00020\b0\u0007\u0012\b\b\u0002\u0010\t\u001a\u00020\n¢\u0006\u0002\u0010\u000bJ\u0013\u0010\u001c\u001a\u00020\u00052\b\u0010\u001d\u001a\u0004\u0018\u00010\u001eH\u0096\u0002J\b\u0010\u001f\u001a\u00020 H\u0016R\u0014\u0010\t\u001a\u00020\nX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\u0010R\u0014\u0010\u0011\u001a\u00020\u00128VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014R\u0014\u0010\u0015\u001a\u00020\u00168VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0018R\u001e\u0010\u0006\u001a\n\u0012\u0006\b\u0001\u0012\u00020\b0\u0007X\u0096\u0004¢\u0006\n\n\u0002\u0010\u001b\u001a\u0004\b\u0019\u0010\u001a¨\u0006!"}, d2 = {"Lorg/jetbrains/kotlin/fir/types/ConeErrorType;", "Lorg/jetbrains/kotlin/fir/types/ConeClassLikeType;", "diagnostic", "Lorg/jetbrains/kotlin/fir/diagnostics/ConeDiagnostic;", "isUninferredParameter", "", "typeArguments", "", "Lorg/jetbrains/kotlin/fir/types/ConeTypeProjection;", "attributes", "Lorg/jetbrains/kotlin/fir/types/ConeAttributes;", "(Lorg/jetbrains/kotlin/fir/diagnostics/ConeDiagnostic;Z[Lorg/jetbrains/kotlin/fir/types/ConeTypeProjection;Lorg/jetbrains/kotlin/fir/types/ConeAttributes;)V", "getAttributes", "()Lorg/jetbrains/kotlin/fir/types/ConeAttributes;", "getDiagnostic", "()Lorg/jetbrains/kotlin/fir/diagnostics/ConeDiagnostic;", "()Z", "lookupTag", "Lorg/jetbrains/kotlin/fir/symbols/ConeClassLikeLookupTag;", "getLookupTag", "()Lorg/jetbrains/kotlin/fir/symbols/ConeClassLikeLookupTag;", "nullability", "Lorg/jetbrains/kotlin/fir/types/ConeNullability;", "getNullability", "()Lorg/jetbrains/kotlin/fir/types/ConeNullability;", "getTypeArguments", "()[Lorg/jetbrains/kotlin/fir/types/ConeTypeProjection;", "[Lorg/jetbrains/kotlin/fir/types/ConeTypeProjection;", Namer.EQUALS_METHOD_NAME, "other", "", "hashCode", "", "cones"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ConeErrorType extends ConeClassLikeType {
    private final ConeAttributes attributes;
    private final ConeDiagnostic diagnostic;
    private final boolean isUninferredParameter;
    private final ConeTypeProjection[] typeArguments;

    @Override // org.jetbrains.kotlin.fir.types.ConeKotlinType, org.jetbrains.kotlin.fir.types.ConeKotlinTypeProjection
    public boolean equals(Object other) {
        return this == other;
    }

    public final ConeDiagnostic getDiagnostic() {
        return this.diagnostic;
    }

    /* JADX INFO: renamed from: isUninferredParameter, reason: from getter */
    public final boolean getIsUninferredParameter() {
        return this.isUninferredParameter;
    }

    public /* synthetic */ ConeErrorType(ConeDiagnostic coneDiagnostic, boolean z, ConeTypeProjection[] coneTypeProjectionArr, ConeAttributes coneAttributes, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(coneDiagnostic, (i & 2) != 0 ? false : z, (i & 4) != 0 ? ConeTypeProjection.INSTANCE.getEMPTY_ARRAY() : coneTypeProjectionArr, (i & 8) != 0 ? ConeAttributes.INSTANCE.getEmpty() : coneAttributes);
    }

    @Override // org.jetbrains.kotlin.fir.types.ConeKotlinType
    public ConeTypeProjection[] getTypeArguments() {
        return this.typeArguments;
    }

    @Override // org.jetbrains.kotlin.fir.types.ConeKotlinType
    public ConeAttributes getAttributes() {
        return this.attributes;
    }

    public ConeErrorType(ConeDiagnostic diagnostic, boolean z, ConeTypeProjection[] typeArguments, ConeAttributes attributes) {
        Intrinsics.checkNotNullParameter(diagnostic, "diagnostic");
        Intrinsics.checkNotNullParameter(typeArguments, "typeArguments");
        Intrinsics.checkNotNullParameter(attributes, "attributes");
        this.diagnostic = diagnostic;
        this.isUninferredParameter = z;
        this.typeArguments = typeArguments;
        this.attributes = attributes;
    }

    @Override // org.jetbrains.kotlin.fir.types.ConeLookupTagBasedType
    public ConeClassLikeLookupTag getLookupTag() {
        ClassId classIdFromString = ClassId.fromString("<error>");
        Intrinsics.checkNotNullExpressionValue(classIdFromString, "fromString(...)");
        return new ConeClassLikeErrorLookupTag(classIdFromString);
    }

    @Override // org.jetbrains.kotlin.fir.types.ConeKotlinType
    public ConeNullability getNullability() {
        ConeDiagnostic coneDiagnostic = this.diagnostic;
        if (coneDiagnostic instanceof ConeDiagnosticWithNullability) {
            return ((ConeDiagnosticWithNullability) coneDiagnostic).getIsNullable() ? ConeNullability.NULLABLE : ConeNullability.NOT_NULL;
        }
        return ConeNullability.UNKNOWN;
    }

    @Override // org.jetbrains.kotlin.fir.types.ConeKotlinType, org.jetbrains.kotlin.fir.types.ConeKotlinTypeProjection
    public int hashCode() {
        return System.identityHashCode(this);
    }
}
