package org.jetbrains.kotlin.fir.analysis.checkers;

import com.google.firebase.analytics.FirebaseAnalytics;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: FirHelpers.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0019\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0006J\u000b\u0010\u000b\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\f\u001a\u0004\u0018\u00010\u0005HÆ\u0003J!\u0010\r\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005HÆ\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001J\t\u0010\u0013\u001a\u00020\u0014HÖ\u0001R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/FirTypeRefSource;", "", "typeRef", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "(Lorg/jetbrains/kotlin/fir/types/FirTypeRef;Lorg/jetbrains/kotlin/KtSourceElement;)V", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", "getTypeRef", "()Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class FirTypeRefSource {
    private final KtSourceElement source;
    private final FirTypeRef typeRef;

    public static /* synthetic */ FirTypeRefSource copy$default(FirTypeRefSource firTypeRefSource, FirTypeRef firTypeRef, KtSourceElement ktSourceElement, int i, Object obj) {
        if ((i & 1) != 0) {
            firTypeRef = firTypeRefSource.typeRef;
        }
        if ((i & 2) != 0) {
            ktSourceElement = firTypeRefSource.source;
        }
        return firTypeRefSource.copy(firTypeRef, ktSourceElement);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final FirTypeRef getTypeRef() {
        return this.typeRef;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final KtSourceElement getSource() {
        return this.source;
    }

    public final FirTypeRefSource copy(FirTypeRef typeRef, KtSourceElement source) {
        return new FirTypeRefSource(typeRef, source);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof FirTypeRefSource)) {
            return false;
        }
        FirTypeRefSource firTypeRefSource = (FirTypeRefSource) other;
        return Intrinsics.areEqual(this.typeRef, firTypeRefSource.typeRef) && Intrinsics.areEqual(this.source, firTypeRefSource.source);
    }

    public int hashCode() {
        FirTypeRef firTypeRef = this.typeRef;
        int iHashCode = (firTypeRef == null ? 0 : firTypeRef.hashCode()) * 31;
        KtSourceElement ktSourceElement = this.source;
        return iHashCode + (ktSourceElement != null ? ktSourceElement.hashCode() : 0);
    }

    public String toString() {
        return "FirTypeRefSource(typeRef=" + this.typeRef + ", source=" + this.source + ')';
    }

    public FirTypeRefSource(FirTypeRef firTypeRef, KtSourceElement ktSourceElement) {
        this.typeRef = firTypeRef;
        this.source = ktSourceElement;
    }

    public final KtSourceElement getSource() {
        return this.source;
    }

    public final FirTypeRef getTypeRef() {
        return this.typeRef;
    }
}
