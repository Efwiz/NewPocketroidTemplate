package org.jetbrains.kotlin.fir.scopes.impl;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.common.Constants;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProvider;
import org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProviderKt;
import org.jetbrains.kotlin.fir.scopes.FirScope;

/* JADX INFO: compiled from: FirAbstractProviderBasedScope.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\b&\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/fir/scopes/impl/FirAbstractProviderBasedScope;", "Lorg/jetbrains/kotlin/fir/scopes/FirScope;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "lookupInFir", "", "(Lorg/jetbrains/kotlin/fir/FirSession;Z)V", Constants.EXTRA_PROVIDER, "Lorg/jetbrains/kotlin/fir/resolve/providers/FirSymbolProvider;", "getProvider", "()Lorg/jetbrains/kotlin/fir/resolve/providers/FirSymbolProvider;", "getSession", "()Lorg/jetbrains/kotlin/fir/FirSession;", "providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FirAbstractProviderBasedScope extends FirScope {
    private final FirSymbolProvider provider;
    private final FirSession session;

    public FirAbstractProviderBasedScope(FirSession session, boolean z) {
        FirSymbolProvider dependenciesSymbolProvider;
        Intrinsics.checkNotNullParameter(session, "session");
        this.session = session;
        if (z) {
            dependenciesSymbolProvider = FirSymbolProviderKt.getSymbolProvider(session);
        } else {
            dependenciesSymbolProvider = FirSymbolProviderKt.getDependenciesSymbolProvider(session);
        }
        this.provider = dependenciesSymbolProvider;
    }

    public /* synthetic */ FirAbstractProviderBasedScope(FirSession firSession, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(firSession, (i & 2) != 0 ? true : z);
    }

    public final FirSession getSession() {
        return this.session;
    }

    public final FirSymbolProvider getProvider() {
        return this.provider;
    }
}
