package org.jetbrains.kotlin.fir.types.impl;

import com.google.firebase.analytics.FirebaseAnalytics;
import kotlin.Metadata;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.name.StandardClassIds;

/* JADX INFO: compiled from: FirImplicitBuiltinTypeRef.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u000f\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0004¨\u0006\u0005"}, d2 = {"Lorg/jetbrains/kotlin/fir/types/impl/FirImplicitDoubleTypeRef;", "Lorg/jetbrains/kotlin/fir/types/impl/FirImplicitBuiltinTypeRef;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "(Lorg/jetbrains/kotlin/KtSourceElement;)V", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirImplicitDoubleTypeRef extends FirImplicitBuiltinTypeRef {
    public FirImplicitDoubleTypeRef(KtSourceElement ktSourceElement) {
        super(ktSourceElement, StandardClassIds.INSTANCE.getDouble(), null, false, 12, null);
    }
}
