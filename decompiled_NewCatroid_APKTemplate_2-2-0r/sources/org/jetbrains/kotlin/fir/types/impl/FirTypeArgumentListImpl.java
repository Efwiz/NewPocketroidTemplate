package org.jetbrains.kotlin.fir.types.impl;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.fir.types.FirTypeArgumentList;
import org.jetbrains.kotlin.fir.types.FirTypeProjection;

/* JADX INFO: compiled from: FirQualifierPartImpl.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u000f\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0004R\u0016\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u001a\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/fir/types/impl/FirTypeArgumentListImpl;", "Lorg/jetbrains/kotlin/fir/types/FirTypeArgumentList;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "(Lorg/jetbrains/kotlin/KtSourceElement;)V", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", "typeArguments", "", "Lorg/jetbrains/kotlin/fir/types/FirTypeProjection;", "getTypeArguments", "()Ljava/util/List;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirTypeArgumentListImpl implements FirTypeArgumentList {
    private final KtSourceElement source;
    private final List<FirTypeProjection> typeArguments = new ArrayList();

    public FirTypeArgumentListImpl(KtSourceElement ktSourceElement) {
        this.source = ktSourceElement;
    }

    @Override // org.jetbrains.kotlin.fir.types.FirTypeArgumentList
    public KtSourceElement getSource() {
        return this.source;
    }

    @Override // org.jetbrains.kotlin.fir.types.FirTypeArgumentList
    public List<FirTypeProjection> getTypeArguments() {
        return this.typeArguments;
    }
}
