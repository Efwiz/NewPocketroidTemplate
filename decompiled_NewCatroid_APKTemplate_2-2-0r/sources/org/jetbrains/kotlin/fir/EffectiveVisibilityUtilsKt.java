package org.jetbrains.kotlin.fir;

import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.EffectiveVisibility;
import org.jetbrains.kotlin.descriptors.Visibilities;
import org.jetbrains.kotlin.descriptors.Visibility;
import org.jetbrains.kotlin.fir.symbols.ConeClassLikeLookupTag;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.utils.exceptions.FirExceptionUtilsKt;
import org.jetbrains.kotlin.utils.exceptions.ExceptionAttachmentBuilder;
import org.jetbrains.kotlin.utils.exceptions.KotlinIllegalArgumentExceptionWithAttachments;

/* JADX INFO: compiled from: EffectiveVisibilityUtils.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\"\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a(\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\b\u0010\u0003\u001a\u0004\u0018\u00010\u00042\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\u0006\u001a,\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\f\u0010\b\u001a\b\u0012\u0002\b\u0003\u0018\u00010\t2\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\n\u001a\u00020\u0006¨\u0006\u000b"}, d2 = {"toEffectiveVisibility", "Lorg/jetbrains/kotlin/descriptors/EffectiveVisibility;", "Lorg/jetbrains/kotlin/descriptors/Visibility;", "owner", "Lorg/jetbrains/kotlin/fir/symbols/ConeClassLikeLookupTag;", "forClass", "", "ownerIsPublishedApi", "ownerSymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassLikeSymbol;", "checkPublishedApi", "semantics"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class EffectiveVisibilityUtilsKt {
    public static /* synthetic */ EffectiveVisibility toEffectiveVisibility$default(Visibility visibility, FirClassLikeSymbol firClassLikeSymbol, boolean z, boolean z2, int i, Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        if ((i & 4) != 0) {
            z2 = false;
        }
        return toEffectiveVisibility(visibility, (FirClassLikeSymbol<?>) firClassLikeSymbol, z, z2);
    }

    public static final EffectiveVisibility toEffectiveVisibility(Visibility visibility, FirClassLikeSymbol<?> firClassLikeSymbol, boolean z, boolean z2) {
        Intrinsics.checkNotNullParameter(visibility, "<this>");
        return toEffectiveVisibility(visibility, firClassLikeSymbol != null ? firClassLikeSymbol.getLookupTag() : null, z, z2);
    }

    public static /* synthetic */ EffectiveVisibility toEffectiveVisibility$default(Visibility visibility, ConeClassLikeLookupTag coneClassLikeLookupTag, boolean z, boolean z2, int i, Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        if ((i & 4) != 0) {
            z2 = false;
        }
        return toEffectiveVisibility(visibility, coneClassLikeLookupTag, z, z2);
    }

    public static final EffectiveVisibility toEffectiveVisibility(Visibility visibility, ConeClassLikeLookupTag coneClassLikeLookupTag, boolean z, boolean z2) {
        Intrinsics.checkNotNullParameter(visibility, "<this>");
        EffectiveVisibility effectiveVisibilityCustomEffectiveVisibility = visibility.customEffectiveVisibility();
        if (effectiveVisibilityCustomEffectiveVisibility != null) {
            return effectiveVisibilityCustomEffectiveVisibility;
        }
        Visibility visibilityNormalize = visibility.normalize();
        if (Intrinsics.areEqual(visibilityNormalize, Visibilities.PrivateToThis.INSTANCE) ? true : Intrinsics.areEqual(visibilityNormalize, Visibilities.InvisibleFake.INSTANCE)) {
            return EffectiveVisibility.PrivateInClass.INSTANCE;
        }
        if (Intrinsics.areEqual(visibilityNormalize, Visibilities.Private.INSTANCE)) {
            return (coneClassLikeLookupTag == null && z) ? EffectiveVisibility.PrivateInFile.INSTANCE : EffectiveVisibility.PrivateInClass.INSTANCE;
        }
        if (Intrinsics.areEqual(visibilityNormalize, Visibilities.Protected.INSTANCE)) {
            return new EffectiveVisibility.Protected(coneClassLikeLookupTag);
        }
        if (Intrinsics.areEqual(visibilityNormalize, Visibilities.Internal.INSTANCE)) {
            if (z2) {
                return EffectiveVisibility.Public.INSTANCE;
            }
            if (z2) {
                throw new NoWhenBranchMatchedException();
            }
            return EffectiveVisibility.Internal.INSTANCE;
        }
        if (Intrinsics.areEqual(visibilityNormalize, Visibilities.Public.INSTANCE)) {
            return EffectiveVisibility.Public.INSTANCE;
        }
        if (Intrinsics.areEqual(visibilityNormalize, Visibilities.Local.INSTANCE)) {
            return EffectiveVisibility.Local.INSTANCE;
        }
        KotlinIllegalArgumentExceptionWithAttachments kotlinIllegalArgumentExceptionWithAttachments = new KotlinIllegalArgumentExceptionWithAttachments("Unknown visibility: " + visibility, null);
        ExceptionAttachmentBuilder exceptionAttachmentBuilder = new ExceptionAttachmentBuilder();
        FirExceptionUtilsKt.withFirLookupTagEntry(exceptionAttachmentBuilder, "owner", coneClassLikeLookupTag);
        kotlinIllegalArgumentExceptionWithAttachments.withAttachment("info.txt", exceptionAttachmentBuilder.buildString());
        throw kotlinIllegalArgumentExceptionWithAttachments;
    }
}
