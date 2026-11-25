package org.jetbrains.kotlin.fir.types.impl;

import com.google.firebase.analytics.FirebaseAnalytics;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.fir.types.FirQualifierPart;
import org.jetbrains.kotlin.fir.types.FirTypeArgumentList;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: FirQualifierPartImpl.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u00002\u00020\u0001B\u001f\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bR\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0016\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0014\u0010\u0006\u001a\u00020\u0007X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/fir/types/impl/FirQualifierPartImpl;", "Lorg/jetbrains/kotlin/fir/types/FirQualifierPart;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "name", "Lorg/jetbrains/kotlin/name/Name;", "typeArgumentList", "Lorg/jetbrains/kotlin/fir/types/FirTypeArgumentList;", "(Lorg/jetbrains/kotlin/KtSourceElement;Lorg/jetbrains/kotlin/name/Name;Lorg/jetbrains/kotlin/fir/types/FirTypeArgumentList;)V", "getName", "()Lorg/jetbrains/kotlin/name/Name;", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", "getTypeArgumentList", "()Lorg/jetbrains/kotlin/fir/types/FirTypeArgumentList;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirQualifierPartImpl implements FirQualifierPart {
    private final Name name;
    private final KtSourceElement source;
    private final FirTypeArgumentList typeArgumentList;

    public FirQualifierPartImpl(KtSourceElement ktSourceElement, Name name2, FirTypeArgumentList typeArgumentList) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(typeArgumentList, "typeArgumentList");
        this.source = ktSourceElement;
        this.name = name2;
        this.typeArgumentList = typeArgumentList;
    }

    @Override // org.jetbrains.kotlin.fir.types.FirQualifierPart
    public KtSourceElement getSource() {
        return this.source;
    }

    @Override // org.jetbrains.kotlin.fir.types.FirQualifierPart
    public Name getName() {
        return this.name;
    }

    @Override // org.jetbrains.kotlin.fir.types.FirQualifierPart
    public FirTypeArgumentList getTypeArgumentList() {
        return this.typeArgumentList;
    }
}
