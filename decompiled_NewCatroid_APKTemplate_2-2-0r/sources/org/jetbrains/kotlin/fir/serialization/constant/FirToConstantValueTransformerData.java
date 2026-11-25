package org.jetbrains.kotlin.fir.serialization.constant;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: FirToConstantValueTransformer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0080\b\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J\u000b\u0010\f\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\u001f\u0010\r\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005HÆ\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001J\t\u0010\u0013\u001a\u00020\u0014HÖ\u0001R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/fir/serialization/constant/FirToConstantValueTransformerData;", "", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "constValueProvider", "Lorg/jetbrains/kotlin/fir/serialization/constant/ConstValueProvider;", "(Lorg/jetbrains/kotlin/fir/FirSession;Lorg/jetbrains/kotlin/fir/serialization/constant/ConstValueProvider;)V", "getConstValueProvider", "()Lorg/jetbrains/kotlin/fir/serialization/constant/ConstValueProvider;", "getSession", "()Lorg/jetbrains/kotlin/fir/FirSession;", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", "fir-serialization"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class FirToConstantValueTransformerData {
    private final ConstValueProvider constValueProvider;
    private final FirSession session;

    public static /* synthetic */ FirToConstantValueTransformerData copy$default(FirToConstantValueTransformerData firToConstantValueTransformerData, FirSession firSession, ConstValueProvider constValueProvider, int i, Object obj) {
        if ((i & 1) != 0) {
            firSession = firToConstantValueTransformerData.session;
        }
        if ((i & 2) != 0) {
            constValueProvider = firToConstantValueTransformerData.constValueProvider;
        }
        return firToConstantValueTransformerData.copy(firSession, constValueProvider);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final FirSession getSession() {
        return this.session;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final ConstValueProvider getConstValueProvider() {
        return this.constValueProvider;
    }

    public final FirToConstantValueTransformerData copy(FirSession session, ConstValueProvider constValueProvider) {
        Intrinsics.checkNotNullParameter(session, "session");
        return new FirToConstantValueTransformerData(session, constValueProvider);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof FirToConstantValueTransformerData)) {
            return false;
        }
        FirToConstantValueTransformerData firToConstantValueTransformerData = (FirToConstantValueTransformerData) other;
        return Intrinsics.areEqual(this.session, firToConstantValueTransformerData.session) && Intrinsics.areEqual(this.constValueProvider, firToConstantValueTransformerData.constValueProvider);
    }

    public int hashCode() {
        int iHashCode = this.session.hashCode() * 31;
        ConstValueProvider constValueProvider = this.constValueProvider;
        return iHashCode + (constValueProvider == null ? 0 : constValueProvider.hashCode());
    }

    public String toString() {
        return "FirToConstantValueTransformerData(session=" + this.session + ", constValueProvider=" + this.constValueProvider + ')';
    }

    public FirToConstantValueTransformerData(FirSession session, ConstValueProvider constValueProvider) {
        Intrinsics.checkNotNullParameter(session, "session");
        this.session = session;
        this.constValueProvider = constValueProvider;
    }

    public final FirSession getSession() {
        return this.session;
    }

    public final ConstValueProvider getConstValueProvider() {
        return this.constValueProvider;
    }
}
