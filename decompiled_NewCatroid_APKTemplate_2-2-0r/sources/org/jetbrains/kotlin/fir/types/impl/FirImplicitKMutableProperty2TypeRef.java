package org.jetbrains.kotlin.fir.types.impl;

import com.google.firebase.analytics.FirebaseAnalytics;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.fir.types.ConeTypeProjection;
import org.jetbrains.kotlin.name.StandardClassIds;

/* JADX INFO: compiled from: FirImplicitBuiltinTypeRef.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B'\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u0006\u0010\u0007\u001a\u00020\u0005¢\u0006\u0002\u0010\b¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/fir/types/impl/FirImplicitKMutableProperty2TypeRef;", "Lorg/jetbrains/kotlin/fir/types/impl/FirImplicitBuiltinTypeRef;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "dispatchReceiverTypeArgument", "Lorg/jetbrains/kotlin/fir/types/ConeTypeProjection;", "extensionReceiverTypeArgument", "propertyTypeArgument", "(Lorg/jetbrains/kotlin/KtSourceElement;Lorg/jetbrains/kotlin/fir/types/ConeTypeProjection;Lorg/jetbrains/kotlin/fir/types/ConeTypeProjection;Lorg/jetbrains/kotlin/fir/types/ConeTypeProjection;)V", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirImplicitKMutableProperty2TypeRef extends FirImplicitBuiltinTypeRef {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FirImplicitKMutableProperty2TypeRef(KtSourceElement ktSourceElement, ConeTypeProjection dispatchReceiverTypeArgument, ConeTypeProjection extensionReceiverTypeArgument, ConeTypeProjection propertyTypeArgument) {
        super(ktSourceElement, StandardClassIds.INSTANCE.getKMutableProperty2(), new ConeTypeProjection[]{dispatchReceiverTypeArgument, extensionReceiverTypeArgument, propertyTypeArgument}, false, 8, null);
        Intrinsics.checkNotNullParameter(dispatchReceiverTypeArgument, "dispatchReceiverTypeArgument");
        Intrinsics.checkNotNullParameter(extensionReceiverTypeArgument, "extensionReceiverTypeArgument");
        Intrinsics.checkNotNullParameter(propertyTypeArgument, "propertyTypeArgument");
    }
}
