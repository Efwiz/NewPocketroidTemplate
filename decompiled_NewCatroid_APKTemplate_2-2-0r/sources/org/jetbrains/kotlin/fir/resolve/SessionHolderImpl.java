package org.jetbrains.kotlin.fir.resolve;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: BodyResolveComponents.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\b\u0018\u0000 \u00162\u00020\u0001:\u0001\u0016B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J\t\u0010\f\u001a\u00020\u0005HÆ\u0003J\u001d\u0010\r\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011HÖ\u0003J\t\u0010\u0012\u001a\u00020\u0013HÖ\u0001J\t\u0010\u0014\u001a\u00020\u0015HÖ\u0001R\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/SessionHolderImpl;", "Lorg/jetbrains/kotlin/fir/resolve/SessionHolder;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "scopeSession", "Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "(Lorg/jetbrains/kotlin/fir/FirSession;Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;)V", "getScopeSession", "()Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "getSession", "()Lorg/jetbrains/kotlin/fir/FirSession;", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "toString", "", "Companion", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class SessionHolderImpl implements SessionHolder {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final ScopeSession scopeSession;
    private final FirSession session;

    public static /* synthetic */ SessionHolderImpl copy$default(SessionHolderImpl sessionHolderImpl, FirSession firSession, ScopeSession scopeSession, int i, Object obj) {
        if ((i & 1) != 0) {
            firSession = sessionHolderImpl.session;
        }
        if ((i & 2) != 0) {
            scopeSession = sessionHolderImpl.scopeSession;
        }
        return sessionHolderImpl.copy(firSession, scopeSession);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final FirSession getSession() {
        return this.session;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final ScopeSession getScopeSession() {
        return this.scopeSession;
    }

    public final SessionHolderImpl copy(FirSession session, ScopeSession scopeSession) {
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(scopeSession, "scopeSession");
        return new SessionHolderImpl(session, scopeSession);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof SessionHolderImpl)) {
            return false;
        }
        SessionHolderImpl sessionHolderImpl = (SessionHolderImpl) other;
        return Intrinsics.areEqual(this.session, sessionHolderImpl.session) && Intrinsics.areEqual(this.scopeSession, sessionHolderImpl.scopeSession);
    }

    public int hashCode() {
        return (this.session.hashCode() * 31) + this.scopeSession.hashCode();
    }

    public String toString() {
        return "SessionHolderImpl(session=" + this.session + ", scopeSession=" + this.scopeSession + ')';
    }

    /* JADX INFO: compiled from: BodyResolveComponents.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/SessionHolderImpl$Companion;", "", "()V", "createWithEmptyScopeSession", "Lorg/jetbrains/kotlin/fir/resolve/SessionHolderImpl;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final SessionHolderImpl createWithEmptyScopeSession(FirSession session) {
            Intrinsics.checkNotNullParameter(session, "session");
            return new SessionHolderImpl(session, new ScopeSession());
        }
    }

    public SessionHolderImpl(FirSession session, ScopeSession scopeSession) {
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(scopeSession, "scopeSession");
        this.session = session;
        this.scopeSession = scopeSession;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.SessionHolder
    public ScopeSession getScopeSession() {
        return this.scopeSession;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.SessionHolder
    public FirSession getSession() {
        return this.session;
    }
}
